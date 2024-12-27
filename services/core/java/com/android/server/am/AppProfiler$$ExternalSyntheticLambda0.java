package com.android.server.am;

import android.app.IApplicationThread;
import android.os.RemoteException;

import java.util.function.Consumer;

public final /* synthetic */ class AppProfiler$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        IApplicationThread iApplicationThread;
        ProcessRecord processRecord = (ProcessRecord) obj;
        ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        ProcessStateRecord processStateRecord = processRecord.mState;
        if (processStateRecord.mProcStateChanged) {
            processStateRecord.mProcStateChanged = false;
        }
        int i = processStateRecord.mCurProcState;
        if (((i < 7 || i >= 16) && !processStateRecord.mSystemNoUi)
                || !processProfileRecord.mPendingUiClean
                || (iApplicationThread = processRecord.mThread) == null) {
            return;
        }
        try {
            iApplicationThread.scheduleTrimMemory(20);
            ProcessProfileRecord processProfileRecord2 = processRecord.mProfile;
            processProfileRecord2.mPendingUiClean = false;
            processProfileRecord2.mApp.mWindowProcessController.mPendingUiClean = false;
        } catch (RemoteException unused) {
        }
    }
}
