package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.communal.data.repository.CommunalSceneRepository;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class GlanceableHubQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final CommunalSceneRepository communalRepository;
    public final String key = BcSmartspaceDataPlugin.UI_SURFACE_GLANCEABLE_HUB;
    public final int pickerIconResourceId = R.drawable.ic_widgets;
    public final Lazy lockScreenState$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.GlanceableHubQuickAffordanceConfig$lockScreenState$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Flags.FEATURE_FLAGS.getClass();
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);
        }
    });

    public GlanceableHubQuickAffordanceConfig(CommunalSceneRepository communalSceneRepository) {
        this.communalRepository = communalSceneRepository;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return (Flow) this.lockScreenState$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        ((CommunalSceneRepositoryImpl) this.communalRepository).changeScene(CommunalScenes.Communal, null);
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return "Glanceable hub";
    }
}
