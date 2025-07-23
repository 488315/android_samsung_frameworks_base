package com.android.systemui.model;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.shared.system.QuickStepContract;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SysUiStateImpl implements SysUiState {
    public long _flags;
    public final int displayId;
    public final DumpManager dumpManager;
    public final StateChange stateChange = new StateChange();
    public final SysUIStateDispatcher stateDispatcher;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SysUiStateImpl create(int i);
    }

    static {
        new Companion(null);
    }

    public SysUiStateImpl(int i, SceneContainerPlugin sceneContainerPlugin, DumpManager dumpManager, SysUIStateDispatcher sysUIStateDispatcher) {
        this.displayId = i;
        this.dumpManager = dumpManager;
        this.stateDispatcher = sysUIStateDispatcher;
    }

    public final void addCallback(SysUiState.SysUiStateCallback sysUiStateCallback) {
        this.stateDispatcher.listeners.add(sysUiStateCallback);
        sysUiStateCallback.onSystemUiStateChanged(getDisplayId(), getFlags());
    }

    @Override // com.android.systemui.model.SysUiState
    public void commitUpdate() {
        long flags = getFlags();
        StateChange stateChange = this.stateChange;
        long j = (flags | stateChange.flagsToSet) & (~stateChange.flagsToClear);
        if (j != getFlags()) {
            this._flags = j;
            this.stateDispatcher.dispatchSysUIStateChange(getDisplayId(), j);
        }
        stateChange.flagsToSet = 0L;
        stateChange.flagsToClear = 0L;
    }

    @Override // com.android.systemui.model.SysUiState
    public void destroy() {
        this.dumpManager.unregisterDumpable("SysUiStateImpl-ForDisplay=" + getDisplayId());
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysUiState state:");
        printWriter.print("  mSysUiStateFlags=");
        printWriter.println(getFlags());
        printWriter.println("    " + QuickStepContract.getSystemUiStateString(getFlags()));
        printWriter.print("    backGestureDisabled=");
        printWriter.println(QuickStepContract.isBackGestureDisabled(getFlags(), false));
        printWriter.print("    assistantGestureDisabled=");
        printWriter.println(QuickStepContract.isAssistantGestureDisabled(getFlags()));
        printWriter.print("    pendingStateChanges=");
        printWriter.println(this.stateChange.toString());
    }

    @Override // com.android.systemui.model.SysUiState
    public int getDisplayId() {
        return this.displayId;
    }

    @Override // com.android.systemui.model.SysUiState
    public long getFlags() {
        return this._flags;
    }

    @Override // com.android.systemui.model.SysUiState
    public SysUiState setFlag(long j, boolean z) {
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue() && Long.bitCount(j) > 1) {
            throw new IllegalStateException("Flags should be a single bit.");
        }
        this.stateChange.setFlag(j, z);
        return this;
    }

    public void start() {
        this.dumpManager.registerNormalDumpable("SysUiStateImpl-ForDisplay=" + getDisplayId(), this);
    }
}
