package com.android.internal.telecom;

import android.os.OutcomeReceiver;
import android.telecom.CallAttributes;
import android.telecom.CallControl;
import android.telecom.CallControlCallback;
import android.telecom.CallEventCallback;
import android.telecom.CallException;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class TransactionalCall {
    private final CallAttributes mCallAttributes;
    private CallControl mCallControl;
    private final CallControlCallback mCallControlCallback;
    private final String mCallId;
    private final CallEventCallback mCallStateCallback;
    private final Executor mExecutor;
    private final OutcomeReceiver<CallControl, CallException> mPendingControl;

    public TransactionalCall(String str, CallAttributes callAttributes, Executor executor, OutcomeReceiver<CallControl, CallException> outcomeReceiver, CallControlCallback callControlCallback, CallEventCallback callEventCallback) {
        this.mCallId = str;
        this.mCallAttributes = callAttributes;
        this.mExecutor = executor;
        this.mPendingControl = outcomeReceiver;
        this.mCallControlCallback = callControlCallback;
        this.mCallStateCallback = callEventCallback;
    }

    public void setCallControl(CallControl callControl) {
        this.mCallControl = callControl;
    }

    public CallControl getCallControl() {
        return this.mCallControl;
    }

    public String getCallId() {
        return this.mCallId;
    }

    public CallAttributes getCallAttributes() {
        return this.mCallAttributes;
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    public OutcomeReceiver<CallControl, CallException> getPendingControl() {
        return this.mPendingControl;
    }

    public CallControlCallback getCallControlCallback() {
        return this.mCallControlCallback;
    }

    public CallEventCallback getCallStateCallback() {
        return this.mCallStateCallback;
    }
}
