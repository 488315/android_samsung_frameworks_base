package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Type.ErrorType;

/* loaded from: classes6.dex */
public interface ExportStatusListener extends NativeInterfaceListener {
    void onCodecReclaim(long j);

    void onError(ErrorType errorType, long j);

    void onExportCompleted();

    void onExportPaused();

    void onExportStarted();
}
