package com.android.internal.inputmethod;

import android.util.proto.ProtoOutputStream;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodManagerGlobal;
import com.android.internal.inputmethod.ImeTracing;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
class ImeTracingClientImpl extends ImeTracing {
    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(ProtoOutputStream protoOutputStream, int i) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(PrintWriter printWriter) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(PrintWriter printWriter) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(String str, ImeTracing.ServiceDumper serviceDumper) {
    }

    ImeTracingClientImpl() {
        sEnabled = InputMethodManagerGlobal.isImeTraceEnabled();
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(String str, InputMethodManager inputMethodManager, byte[] bArr) {
        if (isEnabled() && isAvailable()) {
            synchronized (this.mDumpInProgressLock) {
                if (this.mDumpInProgress) {
                    return;
                }
                this.mDumpInProgress = true;
                try {
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                    inputMethodManager.dumpDebug(protoOutputStream, bArr);
                    sendToService(protoOutputStream.getBytes(), 0, str);
                } finally {
                    this.mDumpInProgress = false;
                }
            }
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(String str, ImeTracing.ServiceDumper serviceDumper, byte[] bArr) {
        if (isEnabled() && isAvailable()) {
            synchronized (this.mDumpInProgressLock) {
                if (this.mDumpInProgress) {
                    return;
                }
                this.mDumpInProgress = true;
                try {
                    ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                    serviceDumper.dumpToProto(protoOutputStream, bArr);
                    sendToService(protoOutputStream.getBytes(), 1, str);
                } finally {
                    this.mDumpInProgress = false;
                }
            }
        }
    }
}
