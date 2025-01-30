package com.android.keyguard;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPresentationDisabler implements Dumpable {
    public static final int[] KEYS = {24, 25};
    public final SettingsHelper mSettingsHelper;
    public final VibratorHelper mVibratorHelper;
    public final int[] mDownCount = {0, 0};
    public long mLastDownTime = 0;
    public boolean mKeyEnabled = false;

    public KeyguardPresentationDisabler(VibratorHelper vibratorHelper, DumpManager dumpManager, SettingsHelper settingsHelper) {
        this.mVibratorHelper = vibratorHelper;
        this.mSettingsHelper = settingsHelper;
        dumpManager.registerNsDumpable("KeyguardPresentationDisabler", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  - mKeyEnabled: " + this.mKeyEnabled);
        if (this.mKeyEnabled) {
            printWriter.println(" / ".concat(LogUtil.makeTimeStr(this.mLastDownTime)));
        } else {
            printWriter.print('\n');
        }
    }
}
