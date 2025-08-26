package com.android.systemui.model;

import com.android.systemui.dump.DumpManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class SysUIStateOverride extends SysUiStateImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SysUiState defaultDisplayState;
    public final SysUIStateOverride$$ExternalSyntheticLambda0 defaultFlagsChangedCallback;
    public final int displayId;
    public long lastSentFlags;
    public final StateChange override;
    public final SysUIStateDispatcher stateDispatcher;

    public interface Factory {
        SysUIStateOverride create(int i);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.model.SysUIStateOverride$$ExternalSyntheticLambda0] */
    public SysUIStateOverride(int i, SceneContainerPlugin sceneContainerPlugin, DumpManager dumpManager, SysUiState sysUiState, SysUIStateDispatcher sysUIStateDispatcher) {
        super(i, sceneContainerPlugin, dumpManager, sysUIStateDispatcher);
        this.displayId = i;
        this.defaultDisplayState = sysUiState;
        this.stateDispatcher = sysUIStateDispatcher;
        this.override = new StateChange();
        this.lastSentFlags = sysUiState.getFlags();
        this.defaultFlagsChangedCallback = new Function2() { // from class: com.android.systemui.model.SysUIStateOverride$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Long) obj).longValue();
                int iIntValue = ((Integer) obj2).intValue();
                int i2 = SysUIStateOverride.$r8$clinit;
                if (iIntValue == 0) {
                    this.f$0.commitUpdate();
                }
                return Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.model.SysUiStateImpl, com.android.systemui.model.SysUiState
    public final void commitUpdate() {
        if (getFlags() != this.lastSentFlags) {
            this.stateDispatcher.dispatchSysUIStateChange(this.displayId, getFlags());
            this.lastSentFlags = getFlags();
        }
    }

    @Override // com.android.systemui.model.SysUiStateImpl, com.android.systemui.model.SysUiState
    public final void destroy() {
        super.destroy();
        this.stateDispatcher.listeners.remove(new SysUIStateOverride$sam$com_android_systemui_model_SysUiState_SysUiStateCallback$0(this.defaultFlagsChangedCallback));
    }

    @Override // com.android.systemui.model.SysUiStateImpl, com.android.systemui.model.SysUiState
    public final int getDisplayId() {
        return this.displayId;
    }

    @Override // com.android.systemui.model.SysUiStateImpl, com.android.systemui.model.SysUiState
    public final long getFlags() {
        long flags = this.defaultDisplayState.getFlags();
        StateChange stateChange = this.override;
        return (flags | stateChange.flagsToSet) & (~stateChange.flagsToClear);
    }

    @Override // com.android.systemui.model.SysUiStateImpl, com.android.systemui.model.SysUiState
    public final SysUiState setFlag(long j, boolean z) {
        this.override.setFlag(j, z);
        return this;
    }

    @Override // com.android.systemui.model.SysUiStateImpl
    public final void start() {
        super.start();
        this.stateDispatcher.listeners.add(new SysUIStateOverride$sam$com_android_systemui_model_SysUiState_SysUiStateCallback$0(this.defaultFlagsChangedCallback));
    }
}
