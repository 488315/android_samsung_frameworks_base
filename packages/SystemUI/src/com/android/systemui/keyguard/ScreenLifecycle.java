package com.android.systemui.keyguard;

import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class ScreenLifecycle extends SecLifecycle implements Dumpable {
    public int mScreenState = 0;

    public ScreenLifecycle(DumpManager dumpManager) {
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MagnificationImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "ScreenLifecycle:", "  mScreenState="), this.mScreenState, printWriter);
    }

    @Override // com.android.systemui.keyguard.SecLifecycle
    public final int getScreenState() {
        return this.mScreenState;
    }

    public interface Observer {
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
