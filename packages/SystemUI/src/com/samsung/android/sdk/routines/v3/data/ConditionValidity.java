package com.samsung.android.sdk.routines.v3.data;

import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ConditionValidity {
    public final ValidityType type;
    public final int validityCode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Error extends ConditionValidity {
        public Error(int i) {
            super(ValidityType.CUSTOM_ERROR, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Valid extends ConditionValidity {
        public Valid() {
            super(ValidityType.GENERAL, 1, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ValidityType {
        GENERAL,
        CUSTOM_ERROR,
        CUSTOM_WARNING
    }

    public /* synthetic */ ConditionValidity(ValidityType validityType, int i, int i2) {
        this(validityType, i);
    }

    public ConditionValidity(ValidityType validityType, int i) {
        if (validityType != ValidityType.GENERAL && (i < 1 || i > 16777215)) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "ConditionValidity: Out of range of custom code:", "RoutineSDK");
            i = 1;
        }
        this.type = validityType;
        this.validityCode = i;
    }
}
