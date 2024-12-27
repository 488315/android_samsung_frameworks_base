package com.android.server.am;

import android.app.IApplicationThread;
import android.os.RemoteException;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
