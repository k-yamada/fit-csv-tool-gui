import com.garmin.fit.*
import com.garmin.fit.csv.MesgCSVWriter
import com.garmin.fit.csv.MesgDataCSVWriter
import com.garmin.fit.csv.MesgFilter
import com.garmin.fit.test.Tests
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

class FitToCSV(val inFilePath: String) {
    private val DATA_OR_DEFINITION_SEARCH_COUNT = 2
    private var out = ""
    private val mesgDefinitionsToOutput = HashSet<String>()
    private val dataMessagesToOutput = HashSet<String>()
    private var dataWriter: MesgDataCSVWriter? = null
    private lateinit var mesgWriter: MesgCSVWriter
    private var mesgFilter: MesgFilter? = null
    private val printBytesAsHex = false
    private val test = false
    private val checkIntegrity = false
    private val showInvalidValues = false
    private val invalidsToEmpty = false
    private val hideUnknownData = false
    private val generateDataFile = false
    private val enumsAsStrings = false
    private val removeExpandedFields = false
    private val excludeMesgList = false // by default, the --defn and --data are include filters.

    private val nextArgumentDefinition = 0
    private val nextArgumentData = 0
    private var numUnknownFields = 0
    private var numUnknownMesgs = 0

    private val decode = Decode()
    private val protocolVersion = Fit.ProtocolVersion.V1_0

    fun execute(): String {
        var result = ""
        out = inFilePath.substring(0, inFilePath.length - 4) + ".csv"
        if (out.length >= 4 && out.substring(out.length - 4, out.length).compareTo(".csv") == 0) {
            out = out.substring(0, out.length - 4) // Remove .csv extension.
        }

        if (checkIntegrity) {
            try {
                if (!decode.checkFileIntegrity(FileInputStream(inFilePath) as InputStream?)) {
                    if (!decode.invalidFileDataSize) {
                        throw RuntimeException("FIT file integrity failure.")
                    } else {
                        println("FIT file integrity failure. Invalid file size in header.")
                        println("Trying to continue...")
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        if (test) {
            val tests = Tests()
            println("Running FIT verification tests...")
            if (tests.run(inFilePath)) {
                println("Passed FIT verification.")
            } else {
                println("Failed FIT verification.")
            }
        }

        //CSV Writer writes all messages to the csv file
        setupCsvWriter()
        if (generateDataFile) {
            //Data Writer writes the data filtered messages
            setupDataWriter()
        }
        val fileInputStream = FileInputStream(inFilePath)
        if (showInvalidValues == true) {
            decode.showInvalidValues()
        }
        mesgFilter = MesgFilter()
        if (excludeMesgList) {
            mesgFilter?.setMesgDefinitionsToIgnore(mesgDefinitionsToOutput)
            mesgFilter?.setDataMessagesToIgnore(dataMessagesToOutput)
        } else {
            mesgFilter?.setMesgDefinitionsToOutput(mesgDefinitionsToOutput)
            mesgFilter?.setDataMessagesToOutput(dataMessagesToOutput)
        }
        registerListenersForCsvWriter()
        registerListenersForDataWriter()
        while (decode.bytesAvailable(fileInputStream as InputStream)) { // Try to read a file while more data is available.
            try {
                decode.read(fileInputStream as InputStream)
                decode.nextFile() // Initialize to read next file (if any).
            } catch (e: FitRuntimeException) {
                if (decode.invalidFileDataSize) {
                    // The exception might be due to a bad file size written
                    // by a device. Retry the decoding process.
                    decode.nextFile()
                    continue
                } else {
                    // An actual exception has occurred.
                    throw e
                }
            }
        }
        cleanupCsvWriter()
        cleanupDataWriter()
        if (!mesgWriter.csvHasData()) {
            result += "Warning: No CSV has been written as this file does not contain FIT message data\n"
        }
        numUnknownFields = mesgWriter.numUnknownFields
        numUnknownMesgs = mesgWriter.numUnknownMesgs


        if (hideUnknownData) {
            result += "Hid $numUnknownFields unknown field(s) and $numUnknownMesgs unknown message(s).\n"
        }

        result += "FIT binary file $inFilePath decoded to $out*.csv files.\n"
        return result
    }

    private fun setupCsvWriter() {
        mesgWriter = MesgCSVWriter("$out.csv")
        if (invalidsToEmpty) {
            mesgWriter.showInvalidsAsEmptyCells()
        }
        if (hideUnknownData) {
            mesgWriter.hideUnknownData()
        }
        if (enumsAsStrings) {
            mesgWriter.enumsAsStrings()
        }
        if (removeExpandedFields) {
            mesgWriter.removeExpandedFields()
        }
        mesgWriter.setPrintByteAsHex(printBytesAsHex)
    }

    private fun registerListenersForCsvWriter() {
        mesgFilter!!.addListener(mesgWriter as MesgDefinitionListener)
        mesgFilter!!.addListener(mesgWriter as MesgListener)
        decode.addListener(mesgFilter as MesgDefinitionListener?)
        decode.addListener(mesgFilter as MesgListener?)
    }

    private fun cleanupCsvWriter() {
        mesgWriter.close()
    }

    private fun setupDataWriter() {
        dataWriter = MesgDataCSVWriter(out + "_data.csv")
        if (invalidsToEmpty) {
            dataWriter?.showInvalidsAsEmptyCells()
        }
        if (hideUnknownData) {
            dataWriter?.hideUnknownData()
        }
        if (enumsAsStrings) {
            mesgWriter.enumsAsStrings()
        }
        if (removeExpandedFields) {
            dataWriter?.removeExpandedFields()
        }
    }

    private fun registerListenersForDataWriter() {
        if (dataWriter != null) {
            mesgFilter!!.addListener(dataWriter as MesgListener?)
        }
    }

    private fun cleanupDataWriter() {
        if (dataWriter != null) {
            dataWriter!!.close()
        }
    }
}
