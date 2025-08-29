package com.android.systemui.volume.dialog.domain.interactor;

import android.os.Handler;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogCallbacksInteractor {
    public final Handler bgHandler;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 event;
    public final VolumeDialogController volumeDialogController;

    public VolumeDialogCallbacksInteractor(VolumeDialogController volumeDialogController, CoroutineScope coroutineScope, Handler handler) {
        this.volumeDialogController = volumeDialogController;
        this.bgHandler = handler;
        Flow flowBuffer = FlowKt.buffer(FlowKt.callbackFlow(new VolumeDialogCallbacksInteractor$event$1(this, null)), 16, BufferOverflow.DROP_OLDEST);
        SharingStarted.Companion.getClass();
        this.event = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumeDialogCallbacksInteractor$event$2(null), FlowKt.shareIn(flowBuffer, coroutineScope, SharingStarted.Companion.Eagerly, 0));
    }

    public final class VolumeDialogEventModelProducer implements VolumeDialogController.Callbacks {
        public final ProducerScope scope;

        public VolumeDialogEventModelProducer(ProducerScope producerScope) {
            this.scope = producerScope;
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.AccessibilityModeChanged(Intrinsics.areEqual(bool, Boolean.TRUE)));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final /* bridge */ /* synthetic */ void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            bool.booleanValue();
            bool2.booleanValue();
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final /* bridge */ /* synthetic */ void onCaptionEnabledStateChanged(Boolean bool, Boolean bool2) {
            bool.booleanValue();
            bool2.booleanValue();
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onDismissRequested(int i) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.DismissRequested(i));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onLayoutDirectionChanged(int i) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.LayoutDirectionChanged(i));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onScreenOff() {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(VolumeDialogEventModel.ScreenOff.INSTANCE);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowCsdWarning(int i, int i2) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.ShowCsdWarning(i, i2));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowRequested(int i, boolean z, int i2) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.ShowRequested(i, z, i2));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSafetyWarning(int i) {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.ShowSafetyWarning(i));
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSilentHint() {
            VolumeDialogCallbacksInteractor.this.volumeDialogController.setRingerMode(2, false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVibrateHint() {
            VolumeDialogCallbacksInteractor.this.volumeDialogController.setRingerMode(0, false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onStateChanged(VolumeDialogController.State state) {
            if (state != null) {
                ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(new VolumeDialogEventModel.StateChanged(state));
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onVolumeChangedFromKey() {
            ((ChannelCoroutine) this.scope).mo3475trySendJP2dKIU(VolumeDialogEventModel.VolumeChangedFromKey.INSTANCE);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onConfigurationChanged() {
        }
    }
}
