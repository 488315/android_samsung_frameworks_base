package com.android.server.am;

import android.app.IApplicationThread;
import android.os.RemoteException;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$MainHandler$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ProcessRecord processRecord = (ProcessRecord) obj;
        IApplicationThread iApplicationThread = processRecord.mThread;
        if (iApplicationThread != null) {
            try {
                iApplicationThread.updateTimeZone();
            } catch (RemoteException unused) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Failed to update time zone for: "), processRecord.info.processName, "ActivityManager");
            }
        }
    }
}
