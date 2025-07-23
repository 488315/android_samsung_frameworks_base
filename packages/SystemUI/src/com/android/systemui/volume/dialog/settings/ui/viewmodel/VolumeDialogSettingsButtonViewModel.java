package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.airbnb.lottie.LottieDrawable;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonViewModel {
    public final Context context;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 drawables;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 icon;
    public final VolumeDialogSettingsButtonInteractor interactor;
    public final ReadonlyStateFlow isVisible;
    public final MediaDeviceSessionInteractor mediaDeviceSessionInteractor;
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Drawables {
        public final Drawable idle;
        public final LottieDrawable playing;
        public final LottieDrawable start;
        public final LottieDrawable stop;

        public Drawables(LottieDrawable lottieDrawable, LottieDrawable lottieDrawable2, LottieDrawable lottieDrawable3, Drawable drawable) {
            this.start = lottieDrawable;
            this.playing = lottieDrawable2;
            this.stop = lottieDrawable3;
            this.idle = drawable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Drawables)) {
                return false;
            }
            Drawables drawables = (Drawables) obj;
            return Intrinsics.areEqual(this.start, drawables.start) && Intrinsics.areEqual(this.playing, drawables.playing) && Intrinsics.areEqual(this.stop, drawables.stop) && Intrinsics.areEqual(this.idle, drawables.idle);
        }

        public final int hashCode() {
            return this.idle.hashCode() + ((this.stop.hashCode() + ((this.playing.hashCode() + (this.start.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "Drawables(start=" + this.start + ", playing=" + this.playing + ", stop=" + this.stop + ", idle=" + this.idle + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PlaybackStates {
        public final boolean isCurrentActive;
        public final Boolean isPreviousActive;

        public PlaybackStates(Boolean bool, boolean z) {
            this.isPreviousActive = bool;
            this.isCurrentActive = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PlaybackStates)) {
                return false;
            }
            PlaybackStates playbackStates = (PlaybackStates) obj;
            return Intrinsics.areEqual(this.isPreviousActive, playbackStates.isPreviousActive) && this.isCurrentActive == playbackStates.isCurrentActive;
        }

        public final int hashCode() {
            Boolean bool = this.isPreviousActive;
            return Boolean.hashCode(this.isCurrentActive) + ((bool == null ? 0 : bool.hashCode()) * 31);
        }

        public final String toString() {
            return "PlaybackStates(isPreviousActive=" + this.isPreviousActive + ", isCurrentActive=" + this.isCurrentActive + ")";
        }
    }

    public VolumeDialogSettingsButtonViewModel(Context context, CoroutineContext coroutineContext, CoroutineScope coroutineScope, MediaOutputInteractor mediaOutputInteractor, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, VolumeDialogSettingsButtonInteractor volumeDialogSettingsButtonInteractor, UiEventLogger uiEventLogger) {
        this.context = context;
        this.mediaDeviceSessionInteractor = mediaDeviceSessionInteractor;
        this.interactor = volumeDialogSettingsButtonInteractor;
        this.uiEventLogger = uiEventLogger;
        Flow flowOn = FlowKt.flowOn(FlowKt.buffer$default(new SafeFlow(new VolumeDialogSettingsButtonViewModel$drawables$1(this, null)), 0, 3), coroutineContext);
        SharingStarted.Companion.getClass();
        this.drawables = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.Eagerly, null));
        this.isVisible = volumeDialogSettingsButtonInteractor.isVisible;
        this.icon = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, new SafeFlow(new VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1(FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(FlowKt.buffer(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, FlowKt.transformLatest(ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), new VolumeDialogSettingsButtonViewModel$special$$inlined$flatMapLatest$1(null, this)), new VolumeDialogSettingsButtonViewModel$icon$2(null))), 1, BufferOverflow.DROP_OLDEST), new VolumeDialogSettingsButtonViewModel$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent), null, this)), new VolumeDialogSettingsButtonViewModel$icon$5(null)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d4, code lost:
    
        if (r10.emit(r9, r0) != r1) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b1, code lost:
    
        if (r10.emit(r9, r0) != r1) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a2, code lost:
    
        if (r10.emit(r11, r0) == r1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c5, code lost:
    
        if (r10.emit(r11, r0) == r1) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x007a, code lost:
    
        if (r12 == r1) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$emitDrawables(com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel r9, kotlinx.coroutines.flow.FlowCollector r10, com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel.PlaybackStates r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel.access$emitDrawables(com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel, kotlinx.coroutines.flow.FlowCollector, com.android.systemui.volume.dialog.settings.ui.viewmodel.VolumeDialogSettingsButtonViewModel$PlaybackStates, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
