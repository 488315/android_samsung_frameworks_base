package com.android.systemui.keyguard;

import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

public final class ScreenLifecycle extends SecLifecycle implements Dumpable {
    public int mScreenState = 0;

    public ScreenLifecycle(DumpManager dumpManager) {
        dumpManager.registerDumpable("ScreenLifecycle", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ScreenLifecycle:", "  mScreenState=");
        m.append(this.mScreenState);
        printWriter.println(m.toString());
    }

    @Override // com.android.systemui.keyguard.SecLifecycle
    public final int getScreenState() {
        return this.mScreenState;
    }

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
