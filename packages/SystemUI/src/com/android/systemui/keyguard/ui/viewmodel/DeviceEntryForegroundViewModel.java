package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class DeviceEntryForegroundViewModel {
    public final Context context;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShowingAodOrDozing;
    public final ChannelFlowTransformLatest padding;
    public final ChannelFlowTransformLatest useAodIconVariant;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 viewModel;

    public final class ForegroundIconViewModel {
        public final int padding;
        public final int tint;
        public final DeviceEntryIconView.IconType type;
        public final boolean useAodVariant;

        public ForegroundIconViewModel(DeviceEntryIconView.IconType iconType, boolean z, int i, int i2) {
            this.type = iconType;
            this.useAodVariant = z;
            this.tint = i;
            this.padding = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ForegroundIconViewModel)) {
                return false;
            }
            ForegroundIconViewModel foregroundIconViewModel = (ForegroundIconViewModel) obj;
            return this.type == foregroundIconViewModel.type && this.useAodVariant == foregroundIconViewModel.useAodVariant && this.tint == foregroundIconViewModel.tint && this.padding == foregroundIconViewModel.padding;
        }

        public final int hashCode() {
            return Integer.hashCode(this.padding) + ReorderTile$$ExternalSyntheticOutline0.m(this.tint, TransitionData$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.useAodVariant), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ForegroundIconViewModel(type=");
            sb.append(this.type);
            sb.append(", useAodVariant=");
            sb.append(this.useAodVariant);
            sb.append(", tint=");
            sb.append(this.tint);
            sb.append(", padding=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.padding, ")", sb);
        }
    }

    public DeviceEntryForegroundViewModel(Context context, ConfigurationInteractor configurationInteractor, DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, DeviceEntryIconViewModel deviceEntryIconViewModel, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        this.context = context;
        this.isShowingAodOrDozing = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.startedKeyguardTransitionStep, keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.DOZING), new DeviceEntryForegroundViewModel$isShowingAodOrDozing$1(null));
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$1(null, this));
        this.useAodIconVariant = channelFlowTransformLatestTransformLatest;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.transformLatest(channelFlowTransformLatestTransformLatest, new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$2(null, deviceEntryIconViewModel, configurationInteractor, this)));
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsSupported, new DeviceEntryForegroundViewModel$special$$inlined$flatMapLatest$3(null, udfpsOverlayInteractor, this, configurationInteractor));
        this.padding = channelFlowTransformLatestTransformLatest2;
        this.viewModel = FlowKt.combine(deviceEntryIconViewModel.iconType, channelFlowTransformLatestTransformLatest, flowDistinctUntilChanged, channelFlowTransformLatestTransformLatest2, new DeviceEntryForegroundViewModel$viewModel$1(null));
    }
}
