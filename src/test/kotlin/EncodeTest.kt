import com.garmin.fit.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.io.InputStream

class EncodeTest {
    @Test
    fun testEncodeWhenStringDeveloperField() {
        val fileName = "build/test.fit"
        val encode = FileEncoder(java.io.File(fileName), Fit.ProtocolVersion.V2_0)
        val appId = byteArrayOf(0x0, 0x1, 0x2, 0x3)
        val developerIdMesg = DeveloperDataIdMesg()
        for (i in appId.indices) {
            developerIdMesg.setApplicationId(i, appId[i])
        }
        developerIdMesg.developerDataIndex = 0.toShort()
        encode.write(developerIdMesg)

        val fieldDescMesg = FieldDescriptionMesg()
        fieldDescMesg.developerDataIndex = 0.toShort()
        fieldDescMesg.fieldDefinitionNumber = 0.toShort()
        fieldDescMesg.fitBaseTypeId = Fit.BASE_TYPE_STRING.toShort()
        fieldDescMesg.setFieldName(0, "str_dev_field")
        encode.write(fieldDescMesg)

        val deviceInfo = DeviceInfoMesg()
        val strDevField = DeveloperField(fieldDescMesg, developerIdMesg)
        deviceInfo.addDeveloperField(strDevField)

        // record1
        strDevField.value = "a"
        encode.write(deviceInfo)

        // record2
        strDevField.value = "aa"
        encode.write(deviceInfo)

        encode.close()

        val decode = Decode()
        val input = FileInputStream(fileName)
        assertTrue(decode.checkFileIntegrity(input as InputStream?))
    }
}
