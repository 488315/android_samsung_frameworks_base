package com.android.server.inputmethod;

import android.os.IBinder;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.InputMethodDebug;

public final class ImeBindingState {
    public final IBinder mFocusedWindow;
    public final ClientState mFocusedWindowClient;
    public final EditorInfo mFocusedWindowEditorInfo;
    public final int mFocusedWindowSoftInputMode;

    public ImeBindingState(IBinder iBinder, int i, ClientState clientState, EditorInfo editorInfo) {
        this.mFocusedWindow = iBinder;
        this.mFocusedWindowSoftInputMode = i;
        this.mFocusedWindowClient = clientState;
        this.mFocusedWindowEditorInfo = editorInfo;
    }

    public final void dump(Printer printer) {
        PrintWriterPrinter printWriterPrinter = (PrintWriterPrinter) printer;
        printWriterPrinter.println("  mFocusedWindow()=" + this.mFocusedWindow);
        printWriterPrinter.println(
                "  softInputMode="
                        + InputMethodDebug.softInputModeToString(this.mFocusedWindowSoftInputMode));
        printWriterPrinter.println("  mFocusedWindowClient=" + this.mFocusedWindowClient);
    }
}
