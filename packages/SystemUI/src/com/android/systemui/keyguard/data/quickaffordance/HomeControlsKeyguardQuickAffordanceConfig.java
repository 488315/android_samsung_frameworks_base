package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context appContext;
    public final ControlsComponent component;
    public final Context context;
    public final ChannelFlowTransformLatest lockScreenState;
    public final String key = BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN;
    public final Lazy pickerIconResourceId$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HomeControlsKeyguardQuickAffordanceConfig.this.component.controlsTileResourceConfiguration.getClass();
            return Integer.valueOf(R.drawable.controls_icon);
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public HomeControlsKeyguardQuickAffordanceConfig(Context context, ControlsComponent controlsComponent) {
        this.context = context;
        this.component = controlsComponent;
        this.appContext = context.getApplicationContext();
        this.lockScreenState = FlowKt.transformLatest(controlsComponent.canShowWhileLockedSetting, new HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return ((Number) this.pickerIconResourceId$delegate.getValue()).intValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(new Intent(this.appContext, (Class<?>) ControlsActivity.class).addFlags(335544320).putExtra("extra_animate", true), ((Boolean) this.component.canShowWhileLockedSetting.$$delegate_0.getValue()).booleanValue());
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        Context context = this.context;
        this.component.controlsTileResourceConfiguration.getClass();
        return context.getString(R.string.quick_controls_title);
    }
}
