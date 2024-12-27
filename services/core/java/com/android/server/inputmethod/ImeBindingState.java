package com.android.server.inputmethod;

import android.os.IBinder;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.InputMethodDebug;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
