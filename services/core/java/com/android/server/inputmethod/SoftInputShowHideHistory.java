package com.android.server.inputmethod;

import android.os.SystemClock;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.InputMethodDebug;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public final class SoftInputShowHideHistory {
    public static final AtomicInteger sSequenceNumber = new AtomicInteger(0);
    public final Entry[] mEntries = new Entry[16];
    public int mNextIndex = 0;

    public final class Entry {
        public final ClientState mClientState;
        public final EditorInfo mEditorInfo;
        public final String mFocusedWindowName;
        public final int mFocusedWindowSoftInputMode;
        public final String mImeControlTargetName;
        public final String mImeSurfaceParentName;
        public final String mImeTargetNameFromWm;
        public final boolean mInFullscreenMode;
        public final int mReason;
        public final String mRequestWindowName;
        public final int mSequenceNumber =
                SoftInputShowHideHistory.sSequenceNumber.getAndIncrement();
        public final long mTimestamp = SystemClock.uptimeMillis();
        public final long mWallTime = System.currentTimeMillis();

        public Entry(
                ClientState clientState,
                EditorInfo editorInfo,
                String str,
                int i,
                int i2,
                boolean z,
                String str2,
                String str3,
                String str4,
                String str5) {
            this.mClientState = clientState;
            this.mEditorInfo = editorInfo;
            this.mFocusedWindowName = str;
            this.mFocusedWindowSoftInputMode = i;
            this.mReason = i2;
            this.mInFullscreenMode = z;
            this.mRequestWindowName = str2;
            this.mImeControlTargetName = str3;
            this.mImeTargetNameFromWm = str4;
            this.mImeSurfaceParentName = str5;
        }
    }

    public final void dump(PrintWriter printWriter) {
        DateTimeFormatter withZone =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
                        .withZone(ZoneId.systemDefault());
        int i = 0;
        while (true) {
            Entry[] entryArr = this.mEntries;
            if (i >= entryArr.length) {
                return;
            }
            Entry entry = entryArr[(this.mNextIndex + i) % entryArr.length];
            if (entry != null) {
                StringBuilder m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "    ", "SoftInputShowHide #");
                m.append(entry.mSequenceNumber);
                m.append(":");
                printWriter.println(m.toString());
                printWriter.print("    ");
                printWriter.println(
                        "  time="
                                + withZone.format(Instant.ofEpochMilli(entry.mWallTime))
                                + " (timestamp="
                                + entry.mTimestamp
                                + ")");
                StringBuilder m2 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "    ", "  reason=");
                m2.append(InputMethodDebug.softInputDisplayReasonToString(entry.mReason));
                printWriter.print(m2.toString());
                printWriter.println(" inFullscreenMode=" + entry.mInFullscreenMode);
                printWriter.print("    ");
                printWriter.println("  requestClient=" + entry.mClientState);
                printWriter.print("    ");
                printWriter.println("  focusedWindowName=" + entry.mFocusedWindowName);
                printWriter.print("    ");
                printWriter.println("  requestWindowName=" + entry.mRequestWindowName);
                printWriter.print("    ");
                printWriter.println("  imeControlTargetName=" + entry.mImeControlTargetName);
                printWriter.print("    ");
                printWriter.println("  imeTargetNameFromWm=" + entry.mImeTargetNameFromWm);
                printWriter.print("    ");
                printWriter.println("  imeSurfaceParentName=" + entry.mImeSurfaceParentName);
                printWriter.print("    ");
                printWriter.print("  editorInfo:");
                if (entry.mEditorInfo != null) {
                    printWriter.print(" inputType=" + entry.mEditorInfo.inputType);
                    printWriter.print(" privateImeOptions=" + entry.mEditorInfo.privateImeOptions);
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            new StringBuilder(" fieldId (viewId)="),
                            entry.mEditorInfo.fieldId,
                            printWriter);
                } else {
                    printWriter.println(" null");
                }
                StringBuilder m3 =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter, "    ", "  focusedWindowSoftInputMode=");
                m3.append(
                        InputMethodDebug.softInputModeToString(entry.mFocusedWindowSoftInputMode));
                printWriter.println(m3.toString());
            }
            i++;
        }
    }
}
