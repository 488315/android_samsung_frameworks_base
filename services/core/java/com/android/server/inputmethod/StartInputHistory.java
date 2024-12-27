package com.android.server.inputmethod;

import android.app.ActivityManager;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.InputMethodDebug;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class StartInputHistory {
    public final Entry[] mEntries;
    public int mNextIndex;

    public final class Entry {
        public int mClientBindSequenceNumber;
        public EditorInfo mEditorInfo;
        public int mImeDisplayId;
        public String mImeId;
        public String mImeTokenString;
        public int mImeUserId;
        public boolean mRestarting;
        public int mSequenceNumber;
        public int mStartInputReason;
        public int mTargetDisplayId;
        public int mTargetUserId;
        public int mTargetWindowSoftInputMode;
        public String mTargetWindowString;
        public long mTimestamp;
        public long mWallTime;

        public final void set(StartInputInfo startInputInfo) {
            this.mSequenceNumber = startInputInfo.mSequenceNumber;
            this.mTimestamp = startInputInfo.mTimestamp;
            this.mWallTime = startInputInfo.mWallTime;
            this.mImeUserId = startInputInfo.mImeUserId;
            this.mImeTokenString = String.valueOf(startInputInfo.mImeToken);
            this.mImeDisplayId = startInputInfo.mImeDisplayId;
            this.mImeId = startInputInfo.mImeId;
            this.mStartInputReason = startInputInfo.mStartInputReason;
            this.mRestarting = startInputInfo.mRestarting;
            this.mTargetUserId = startInputInfo.mTargetUserId;
            this.mTargetDisplayId = startInputInfo.mTargetDisplayId;
            this.mTargetWindowString = String.valueOf(startInputInfo.mTargetWindow);
            this.mEditorInfo = startInputInfo.mEditorInfo;
            this.mTargetWindowSoftInputMode = startInputInfo.mTargetWindowSoftInputMode;
            this.mClientBindSequenceNumber = startInputInfo.mClientBindSequenceNumber;
        }
    }

    public StartInputHistory() {
        this.mEntries = new Entry[ActivityManager.isLowRamDeviceStatic() ? 5 : 32];
        this.mNextIndex = 0;
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
                                printWriter, "    ", "StartInput #");
                m.append(entry.mSequenceNumber);
                m.append(":");
                printWriter.println(m.toString());
                printWriter.print("    ");
                printWriter.println(
                        "  time="
                                + withZone.format(Instant.ofEpochMilli(entry.mWallTime))
                                + " (timestamp="
                                + entry.mTimestamp
                                + ") reason="
                                + InputMethodDebug.startInputReasonToString(entry.mStartInputReason)
                                + " restarting="
                                + entry.mRestarting);
                printWriter.print("    ");
                StringBuilder sb = new StringBuilder("  imeToken=");
                sb.append(entry.mImeTokenString);
                sb.append(" [");
                sb.append(entry.mImeId);
                sb.append("]");
                printWriter.print(sb.toString());
                printWriter.print(" imeUserId=" + entry.mImeUserId);
                printWriter.println(" imeDisplayId=" + entry.mImeDisplayId);
                printWriter.print("    ");
                printWriter.println(
                        "  targetWin="
                                + entry.mTargetWindowString
                                + " ["
                                + entry.mEditorInfo.packageName
                                + "] targetUserId="
                                + entry.mTargetUserId
                                + " targetDisplayId="
                                + entry.mTargetDisplayId
                                + " clientBindSeq="
                                + entry.mClientBindSequenceNumber);
                printWriter.print("    ");
                StringBuilder sb2 = new StringBuilder("  softInputMode=");
                sb2.append(
                        InputMethodDebug.softInputModeToString(entry.mTargetWindowSoftInputMode));
                printWriter.println(sb2.toString());
                printWriter.print("    ");
                StringBuilder sb3 = new StringBuilder("  inputType=0x");
                BatteryService$$ExternalSyntheticOutline0.m(
                        entry.mEditorInfo.inputType, sb3, " imeOptions=0x");
                BatteryService$$ExternalSyntheticOutline0.m(
                        entry.mEditorInfo.imeOptions, sb3, " fieldId=0x");
                BatteryService$$ExternalSyntheticOutline0.m(
                        entry.mEditorInfo.fieldId, sb3, " fieldName=");
                sb3.append(entry.mEditorInfo.fieldName);
                sb3.append(" actionId=");
                sb3.append(entry.mEditorInfo.actionId);
                sb3.append(" actionLabel=");
                sb3.append((Object) entry.mEditorInfo.actionLabel);
                printWriter.println(sb3.toString());
            }
            i++;
        }
    }
}
