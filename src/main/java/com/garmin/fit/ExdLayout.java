////////////////////////////////////////////////////////////////////////////////
// The following FIT Protocol software provided may be used with FIT protocol
// devices only and remains the copyrighted property of Garmin Canada Inc.
// The software is being provided on an "as-is" basis and as an accommodation,
// and therefore all warranties, representations, or guarantees of any kind
// (whether express, implied or statutory) including, without limitation,
// warranties of merchantability, non-infringement, or fitness for a particular
// purpose, are specifically disclaimed.
//
// Copyright 2021 Garmin Canada Inc.
////////////////////////////////////////////////////////////////////////////////
// ****WARNING****  This file is auto-generated!  Do NOT edit this file.
// Profile Version = 21.47Release
// Tag = production/akw/21.47.00-0-geec27411
////////////////////////////////////////////////////////////////////////////////


package com.garmin.fit;


public enum ExdLayout {
    FULL_SCREEN((short)0),
    HALF_VERTICAL((short)1),
    HALF_HORIZONTAL((short)2),
    HALF_VERTICAL_RIGHT_SPLIT((short)3),
    HALF_HORIZONTAL_BOTTOM_SPLIT((short)4),
    FULL_QUARTER_SPLIT((short)5),
    HALF_VERTICAL_LEFT_SPLIT((short)6),
    HALF_HORIZONTAL_TOP_SPLIT((short)7),
    DYNAMIC((short)8),
    INVALID((short)255);

    protected short value;

    private ExdLayout(short value) {
        this.value = value;
    }

    public static ExdLayout getByValue(final Short value) {
        for (final ExdLayout type : ExdLayout.values()) {
            if (value == type.value)
                return type;
        }

        return ExdLayout.INVALID;
    }

    /**
     * Retrieves the String Representation of the Value
     * @return The string representation of the value
     */
    public static String getStringFromValue( ExdLayout value ) {
        return value.name();
    }

    public short getValue() {
        return value;
    }


}
