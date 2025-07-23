package com.android.systemui.dump;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SystemUIAuxiliaryDumpService extends Service {
    public final DumpHandler mDumpHandler;

    public SystemUIAuxiliaryDumpService(DumpHandler dumpHandler) {
        this.mDumpHandler = dumpHandler;
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mDumpHandler.dump(fileDescriptor, printWriter, new String[]{"--dump-priority", "NORMAL"});
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }
}
