package com.android.systemui.statusbar.notification.stack.domain.interactor;

import android.content.Context;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import dagger.Lazy;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SharedNotificationContainerInteractor {
    public final StateFlowImpl _bottomPosition;
    public final StateFlowImpl _naviBarHeight;
    public final StateFlowImpl _notificationStackChanged;
    public final StateFlowImpl _nsslWidth;
    public final StateFlowImpl _topPosition;
    public final ReadonlyStateFlow bottomPosition;
    public final Flow configurationBasedDimensions;
    public final Context context;
    public final ReadonlyStateFlow naviBarHeight;
    public final Flow notificationStackChanged;
    public final ReadonlyStateFlow nsslWidth;
    public final Lazy splitShadeStateController;
    public final ReadonlyStateFlow topPosition;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 useExtraShelfSpace;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConfigurationBasedDimensions {
        public final int keyguardSplitShadeTopMargin;
        public final int marginBottom;
        public final int marginHorizontal;
        public final int marginTop;
        public final int marginTopLargeScreen;
        public final int panelWidth;
        public final float transitionX;
        public final SecQsUiDisplayModeInteractor.UiDisplayMode uiDisplayMode;
        public final boolean useLargeScreenHeader;
        public final boolean useSplitShade;

        public ConfigurationBasedDimensions(boolean z, boolean z2, int i, int i2, int i3, int i4, int i5, int i6, float f, SecQsUiDisplayModeInteractor.UiDisplayMode uiDisplayMode) {
            this.useSplitShade = z;
            this.useLargeScreenHeader = z2;
            this.marginHorizontal = i;
            this.marginBottom = i2;
            this.marginTop = i3;
            this.marginTopLargeScreen = i4;
            this.keyguardSplitShadeTopMargin = i5;
            this.panelWidth = i6;
            this.transitionX = f;
            this.uiDisplayMode = uiDisplayMode;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.useSplitShade == configurationBasedDimensions.useSplitShade && this.useLargeScreenHeader == configurationBasedDimensions.useLargeScreenHeader && this.marginHorizontal == configurationBasedDimensions.marginHorizontal && this.marginBottom == configurationBasedDimensions.marginBottom && this.marginTop == configurationBasedDimensions.marginTop && this.marginTopLargeScreen == configurationBasedDimensions.marginTopLargeScreen && this.keyguardSplitShadeTopMargin == configurationBasedDimensions.keyguardSplitShadeTopMargin && this.panelWidth == configurationBasedDimensions.panelWidth && Float.compare(this.transitionX, configurationBasedDimensions.transitionX) == 0 && this.uiDisplayMode == configurationBasedDimensions.uiDisplayMode;
        }

        public final int hashCode() {
            return this.uiDisplayMode.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.transitionX, ReorderTile$$ExternalSyntheticOutline0.m(this.panelWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.keyguardSplitShadeTopMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.marginTopLargeScreen, ReorderTile$$ExternalSyntheticOutline0.m(this.marginTop, ReorderTile$$ExternalSyntheticOutline0.m(this.marginBottom, ReorderTile$$ExternalSyntheticOutline0.m(this.marginHorizontal, TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.useSplitShade) * 31, 31, this.useLargeScreenHeader), 31), 31), 31), 31), 31), 31), 31);
        }

        public final String toString() {
            return "ConfigurationBasedDimensions(useSplitShade=" + this.useSplitShade + ", useLargeScreenHeader=" + this.useLargeScreenHeader + ", marginHorizontal=" + this.marginHorizontal + ", marginBottom=" + this.marginBottom + ", marginTop=" + this.marginTop + ", marginTopLargeScreen=" + this.marginTopLargeScreen + ", keyguardSplitShadeTopMargin=" + this.keyguardSplitShadeTopMargin + ", panelWidth=" + this.panelWidth + ", transitionX=" + this.transitionX + ", uiDisplayMode=" + this.uiDisplayMode + ")";
        }
    }

    public SharedNotificationContainerInteractor(Context context, Lazy lazy, ConfigurationInteractor configurationInteractor, KeyguardInteractor keyguardInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, Lazy lazy2) {
        this.context = context;
        this.splitShadeStateController = lazy;
        Float valueOf = Float.valueOf(0.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._topPosition = MutableStateFlow;
        this.topPosition = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._bottomPosition = MutableStateFlow2;
        this.bottomPosition = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(0L);
        this._notificationStackChanged = MutableStateFlow3;
        this.notificationStackChanged = FlowKt.debounce(MutableStateFlow3, 20L);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(0);
        this._naviBarHeight = MutableStateFlow4;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow4);
        this.naviBarHeight = asStateFlow;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(0);
        this._nsslWidth = MutableStateFlow5;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow5);
        this.nsslWidth = asStateFlow2;
        this.configurationBasedDimensions = FlowKt.distinctUntilChanged(FlowKt.combine(((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange, asStateFlow, asStateFlow2, new SharedNotificationContainerInteractor$configurationBasedDimensions$1(this, lazy2, null)));
        this.useExtraShelfSpace = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.ambientIndicationVisible, deviceEntryUdfpsInteractor.isUdfpsSupported, new SharedNotificationContainerInteractor$useExtraShelfSpace$1(null));
    }
}
