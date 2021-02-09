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

import java.util.HashMap;
import java.util.Map;

public class LegRaiseExerciseName {
    public static final int HANGING_KNEE_RAISE = 0;
    public static final int HANGING_LEG_RAISE = 1;
    public static final int WEIGHTED_HANGING_LEG_RAISE = 2;
    public static final int HANGING_SINGLE_LEG_RAISE = 3;
    public static final int WEIGHTED_HANGING_SINGLE_LEG_RAISE = 4;
    public static final int KETTLEBELL_LEG_RAISES = 5;
    public static final int LEG_LOWERING_DRILL = 6;
    public static final int WEIGHTED_LEG_LOWERING_DRILL = 7;
    public static final int LYING_STRAIGHT_LEG_RAISE = 8;
    public static final int WEIGHTED_LYING_STRAIGHT_LEG_RAISE = 9;
    public static final int MEDICINE_BALL_LEG_DROPS = 10;
    public static final int QUADRUPED_LEG_RAISE = 11;
    public static final int WEIGHTED_QUADRUPED_LEG_RAISE = 12;
    public static final int REVERSE_LEG_RAISE = 13;
    public static final int WEIGHTED_REVERSE_LEG_RAISE = 14;
    public static final int REVERSE_LEG_RAISE_ON_SWISS_BALL = 15;
    public static final int WEIGHTED_REVERSE_LEG_RAISE_ON_SWISS_BALL = 16;
    public static final int SINGLE_LEG_LOWERING_DRILL = 17;
    public static final int WEIGHTED_SINGLE_LEG_LOWERING_DRILL = 18;
    public static final int WEIGHTED_HANGING_KNEE_RAISE = 19;
    public static final int LATERAL_STEPOVER = 20;
    public static final int WEIGHTED_LATERAL_STEPOVER = 21;
    public static final int INVALID = Fit.UINT16_INVALID;

    private static final Map<Integer, String> stringMap;

    static {
        stringMap = new HashMap<Integer, String>();
        stringMap.put(HANGING_KNEE_RAISE, "HANGING_KNEE_RAISE");
        stringMap.put(HANGING_LEG_RAISE, "HANGING_LEG_RAISE");
        stringMap.put(WEIGHTED_HANGING_LEG_RAISE, "WEIGHTED_HANGING_LEG_RAISE");
        stringMap.put(HANGING_SINGLE_LEG_RAISE, "HANGING_SINGLE_LEG_RAISE");
        stringMap.put(WEIGHTED_HANGING_SINGLE_LEG_RAISE, "WEIGHTED_HANGING_SINGLE_LEG_RAISE");
        stringMap.put(KETTLEBELL_LEG_RAISES, "KETTLEBELL_LEG_RAISES");
        stringMap.put(LEG_LOWERING_DRILL, "LEG_LOWERING_DRILL");
        stringMap.put(WEIGHTED_LEG_LOWERING_DRILL, "WEIGHTED_LEG_LOWERING_DRILL");
        stringMap.put(LYING_STRAIGHT_LEG_RAISE, "LYING_STRAIGHT_LEG_RAISE");
        stringMap.put(WEIGHTED_LYING_STRAIGHT_LEG_RAISE, "WEIGHTED_LYING_STRAIGHT_LEG_RAISE");
        stringMap.put(MEDICINE_BALL_LEG_DROPS, "MEDICINE_BALL_LEG_DROPS");
        stringMap.put(QUADRUPED_LEG_RAISE, "QUADRUPED_LEG_RAISE");
        stringMap.put(WEIGHTED_QUADRUPED_LEG_RAISE, "WEIGHTED_QUADRUPED_LEG_RAISE");
        stringMap.put(REVERSE_LEG_RAISE, "REVERSE_LEG_RAISE");
        stringMap.put(WEIGHTED_REVERSE_LEG_RAISE, "WEIGHTED_REVERSE_LEG_RAISE");
        stringMap.put(REVERSE_LEG_RAISE_ON_SWISS_BALL, "REVERSE_LEG_RAISE_ON_SWISS_BALL");
        stringMap.put(WEIGHTED_REVERSE_LEG_RAISE_ON_SWISS_BALL, "WEIGHTED_REVERSE_LEG_RAISE_ON_SWISS_BALL");
        stringMap.put(SINGLE_LEG_LOWERING_DRILL, "SINGLE_LEG_LOWERING_DRILL");
        stringMap.put(WEIGHTED_SINGLE_LEG_LOWERING_DRILL, "WEIGHTED_SINGLE_LEG_LOWERING_DRILL");
        stringMap.put(WEIGHTED_HANGING_KNEE_RAISE, "WEIGHTED_HANGING_KNEE_RAISE");
        stringMap.put(LATERAL_STEPOVER, "LATERAL_STEPOVER");
        stringMap.put(WEIGHTED_LATERAL_STEPOVER, "WEIGHTED_LATERAL_STEPOVER");
    }


    /**
     * Retrieves the String Representation of the Value
     * @return The string representation of the value, or empty if unknown
     */
    public static String getStringFromValue( Integer value ) {
        if( stringMap.containsKey( value ) ) {
            return stringMap.get( value );
        }

        return "";
    }

    /**
     * Retrieves a value given a string representation
     * @return The value or INVALID if unkwown
     */
    public static Integer getValueFromString( String value ) {
        for( Map.Entry<Integer, String> entry : stringMap.entrySet() ) {
            if( entry.getValue().equals( value ) ) {
                return entry.getKey();
            }
        }

        return INVALID;
    }

}
