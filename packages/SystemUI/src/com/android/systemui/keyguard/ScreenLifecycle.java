package com.android.systemui.keyguard;

import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
