package com.samsung.android.sdk.routines.v3.data;

import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class ActionResult {
    public final int customErrorCode;
    public final ParameterValues outputValues;
    public final ResultCode resultCode;

    public class Default extends ActionResult {
        public Default(ResultCode resultCode, ParameterValues parameterValues) {
            super(resultCode, parameterValues, 0);
        }

        public Default(ResultCode resultCode) {
            super(resultCode, (ParameterValues) null, 0);
        }
    }

    public class Error extends ActionResult {
        public Error(int i, ParameterValues parameterValues) {
            super(i, parameterValues, 0);
        }

        public Error(int i) {
            super(i, (ParameterValues) null, 0);
        }
    }

    public enum ResultCode {
        SUCCESS(1),
        /* JADX INFO: Fake field, exist only in values array */
        SUSPENDED(2),
        /* JADX INFO: Fake field, exist only in values array */
        FAIL_INVALID_PARAMETER(-1),
        FAIL_NOT_AVAILABLE(-2),
        FAIL_NOT_SUPPORTED(-3),
        FAIL_TIMEOUT(-4);

        public final int value;

        ResultCode(int i) {
            this.value = i;
        }
    }

    public /* synthetic */ ActionResult(int i, ParameterValues parameterValues, int i2) {
        this(i, parameterValues);
    }

    public /* synthetic */ ActionResult(ResultCode resultCode, ParameterValues parameterValues, int i) {
        this(resultCode, parameterValues);
    }

    public ActionResult(ResultCode resultCode, ParameterValues parameterValues) {
        this.resultCode = resultCode;
        this.outputValues = parameterValues;
        this.customErrorCode = -1;
    }

    public ActionResult(int i, ParameterValues parameterValues) {
        if (i < 1 || i > 16777215) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "ActionResult: Out of range of custom code:", "RoutineSDK");
            i = 1;
        }
        this.resultCode = ResultCode.FAIL_NOT_AVAILABLE;
        this.outputValues = parameterValues;
        this.customErrorCode = i;
    }
}
