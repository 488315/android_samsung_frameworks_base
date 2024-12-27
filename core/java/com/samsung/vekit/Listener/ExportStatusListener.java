package com.samsung.vekit.Listener;

public interface ExportStatusListener extends NativeInterfaceListener {
    void onExportCompleted();

    void onExportPaused();

    void onExportStarted();
}
