package com.android.systemui.keyguard.data.quickaffordance;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.p005ui.ControlsActivity;
import com.android.systemui.controls.p005ui.ControlsUiController;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HomeControlsKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context appContext;
    public final ControlsComponent component;
    public final Context context;
    public final ChannelFlowTransformLatest lockScreenState;
    public final String key = BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN;
    public final Lazy pickerIconResourceId$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$pickerIconResourceId$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HomeControlsKeyguardQuickAffordanceConfig.this.component.controlsTileResourceConfiguration.getClass();
            return Integer.valueOf(R.drawable.controls_icon);
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public HomeControlsKeyguardQuickAffordanceConfig(Context context, ControlsComponent controlsComponent) {
        this.context = context;
        this.component = controlsComponent;
        this.appContext = context.getApplicationContext();
        this.lockScreenState = FlowKt.transformLatest(controlsComponent.canShowWhileLockedSetting, new C1557xe5f1df46(null, this));
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
        ControlsComponent controlsComponent = this.component;
        if (!controlsComponent.featureEnabled) {
            return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
        }
        ControlsListingController controlsListingController = (ControlsListingController) controlsComponent.getControlsListingController().orElse(null);
        List currentServices = controlsListingController != null ? ((ControlsListingControllerImpl) controlsListingController).getCurrentServices() : null;
        ControlsController controlsController = (ControlsController) controlsComponent.getControlsController().orElse(null);
        boolean z = controlsController != null && (((ArrayList) ((ControlsControllerImpl) controlsController).getFavorites()).isEmpty() ^ true);
        controlsComponent.controlsTileResourceConfiguration.getClass();
        if (currentServices != null) {
            currentServices.isEmpty();
        }
        boolean z2 = currentServices == null || currentServices.isEmpty();
        Context context = this.context;
        if (z2) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(context.getString(R.string.home_quick_affordance_unavailable_install_the_app), null, null);
        }
        if (z) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
        }
        Class resolveActivity = ((CustomControlsUiControllerImpl) ((ControlsUiController) (controlsComponent.featureEnabled ? Optional.of(controlsComponent.lazyControlsUiController.get()) : Optional.empty()).get())).resolveActivity();
        String string = context.getString(R.string.home_quick_affordance_unavailable_configure_the_app);
        String string2 = context.getString(R.string.controls_open_app);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) resolveActivity));
        intent.putExtra("extra_animate", true);
        Unit unit = Unit.INSTANCE;
        if (string2 != null) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(string, string2, intent);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(new Intent(this.appContext, (Class<?>) ControlsActivity.class).addFlags(335544320).putExtra("extra_animate", true), ((Boolean) this.component.canShowWhileLockedSetting.getValue()).booleanValue());
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        this.component.controlsTileResourceConfiguration.getClass();
        return this.context.getString(R.string.quick_controls_title);
    }
}
