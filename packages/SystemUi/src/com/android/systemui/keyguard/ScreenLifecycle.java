package com.android.systemui.keyguard;

import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScreenLifecycle extends SecLifecycle implements Dumpable {
    public int mScreenState = 0;

    public ScreenLifecycle(DumpManager dumpManager) {
        dumpManager.registerDumpable("ScreenLifecycle", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "ScreenLifecycle:", "  mScreenState=");
        m75m.append(this.mScreenState);
        printWriter.println(m75m.toString());
    }

    @Override // com.android.systemui.keyguard.SecLifecycle
    public final int getScreenState() {
        return this.mScreenState;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Observer {
        default void onScreenInternalTurningOff() {
        }

        default void onScreenInternalTurningOn() {
        }

        default void onScreenTurnedOff() {
        }

        default void onScreenTurnedOn() {
        }

        default void onScreenTurningOff() {
        }

        default void onScreenTurningOn() {
        }
    }
}
