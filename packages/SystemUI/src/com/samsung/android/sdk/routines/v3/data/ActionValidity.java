package com.samsung.android.sdk.routines.v3.data;

import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ActionValidity {
    public final int customReasonCode;
    public final ValidityType type;
    public final Validity validity;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default extends ActionValidity {
        public Default(Validity validity) {
            super(validity, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum Validity {
        VALID(1),
        /* JADX INFO: Fake field, exist only in values array */
        INVALID_PARAMETER(-1),
        NOT_AVAILABLE(-2),
        /* JADX INFO: Fake field, exist only in values array */
        NOT_SUPPORTED(-3);

        public final int value;

        Validity(int i) {
            this.value = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ValidityType {
        DEFAULT,
        CUSTOM_ERROR
    }

    public /* synthetic */ ActionValidity(Validity validity, int i) {
        this(validity);
    }

    public /* synthetic */ ActionValidity(ValidityType validityType, int i, int i2) {
        this(validityType, i);
    }

    public ActionValidity(Validity validity) {
        this.type = ValidityType.DEFAULT;
        this.validity = validity;
        this.customReasonCode = 0;
    }

    public ActionValidity(ValidityType validityType, int i) {
        if (i < 1 || i > 16777215) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "ActionValidity: Out of range of custom code:", "RoutineSDK");
            i = 1;
        }
        this.type = validityType;
        this.validity = Validity.NOT_AVAILABLE;
        this.customReasonCode = i;
    }
}
