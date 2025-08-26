package com.android.systemui.volume.dialog.settings.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.airbnb.lottie.LottieDrawable;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__DistinctKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonViewModel {
    public final Context context;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 drawables;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 icon;
    public final VolumeDialogSettingsButtonInteractor interactor;
    public final ReadonlyStateFlow isVisible;
    public final MediaDeviceSessionInteractor mediaDeviceSessionInteractor;
    public final UiEventLogger uiEventLogger;

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
        Flow flowFlowOn = FlowKt.flowOn(FlowKt.buffer$default(new SafeFlow(new VolumeDialogSettingsButtonViewModel$drawables$1(this, null)), 0, 3), coroutineContext);
        SharingStarted.Companion.getClass();
        this.drawables = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.Eagerly, null));
        this.isVisible = volumeDialogSettingsButtonInteractor.isVisible;
        this.icon = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, new SafeFlow(new VolumeDialogSettingsButtonViewModel$special$$inlined$transform$1(FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(FlowKt.buffer(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(null, FlowKt.transformLatest(ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), new VolumeDialogSettingsButtonViewModel$special$$inlined$flatMapLatest$1(null, this)), new VolumeDialogSettingsButtonViewModel$icon$2(null))), 1, BufferOverflow.DROP_OLDEST), new VolumeDialogSettingsButtonViewModel$$ExternalSyntheticLambda0(), FlowKt__DistinctKt.defaultAreEquivalent), null, this)), new VolumeDialogSettingsButtonViewModel$icon$5(null)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b1, code lost:
    
        if (r10.emit(r9, r0) != r1) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d4, code lost:
    
        if (r10.emit(r9, r0) != r1) goto L48;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$emitDrawables(VolumeDialogSettingsButtonViewModel volumeDialogSettingsButtonViewModel, FlowCollector flowCollector, PlaybackStates playbackStates, ContinuationImpl continuationImpl) {
        VolumeDialogSettingsButtonViewModel$emitDrawables$1 volumeDialogSettingsButtonViewModel$emitDrawables$1;
        Drawables drawables;
        volumeDialogSettingsButtonViewModel.getClass();
        if (continuationImpl instanceof VolumeDialogSettingsButtonViewModel$emitDrawables$1) {
            volumeDialogSettingsButtonViewModel$emitDrawables$1 = (VolumeDialogSettingsButtonViewModel$emitDrawables$1) continuationImpl;
            int i = volumeDialogSettingsButtonViewModel$emitDrawables$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                volumeDialogSettingsButtonViewModel$emitDrawables$1.label = i - Integer.MIN_VALUE;
            } else {
                volumeDialogSettingsButtonViewModel$emitDrawables$1 = new VolumeDialogSettingsButtonViewModel$emitDrawables$1(volumeDialogSettingsButtonViewModel, continuationImpl);
            }
        }
        Object objFirst = volumeDialogSettingsButtonViewModel$emitDrawables$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = volumeDialogSettingsButtonViewModel$emitDrawables$1.label;
        if (i2 == 0) {
            kotlin.ResultKt.throwOnFailure(objFirst);
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = flowCollector;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = playbackStates;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 1;
            objFirst = FlowKt.first(volumeDialogSettingsButtonViewModel.drawables, volumeDialogSettingsButtonViewModel$emitDrawables$1);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            playbackStates = (PlaybackStates) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1;
            flowCollector = (FlowCollector) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0;
            kotlin.ResultKt.throwOnFailure(objFirst);
        } else if (i2 == 2) {
            drawables = (Drawables) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1;
            flowCollector = (FlowCollector) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0;
            kotlin.ResultKt.throwOnFailure(objFirst);
            LottieDrawable lottieDrawable = drawables.playing;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 3;
        } else {
            if (i2 == 3) {
                kotlin.ResultKt.throwOnFailure(objFirst);
                return Unit.INSTANCE;
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kotlin.ResultKt.throwOnFailure(objFirst);
                return Unit.INSTANCE;
            }
            drawables = (Drawables) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1;
            flowCollector = (FlowCollector) volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0;
            kotlin.ResultKt.throwOnFailure(objFirst);
            Drawable drawable = drawables.idle;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 5;
        }
        drawables = (Drawables) objFirst;
        Boolean bool = playbackStates.isPreviousActive;
        boolean z = playbackStates.isCurrentActive;
        boolean z2 = (bool == null || bool.equals(Boolean.valueOf(z))) ? false : true;
        if (z) {
            if (z2) {
                LottieDrawable lottieDrawable2 = drawables.start;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = flowCollector;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = drawables;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 2;
                if (flowCollector.emit(lottieDrawable2, volumeDialogSettingsButtonViewModel$emitDrawables$1) != coroutineSingletons) {
                }
            }
            LottieDrawable lottieDrawable3 = drawables.playing;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 3;
        } else {
            if (z2) {
                LottieDrawable lottieDrawable4 = drawables.stop;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = flowCollector;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = drawables;
                volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 4;
                if (flowCollector.emit(lottieDrawable4, volumeDialogSettingsButtonViewModel$emitDrawables$1) != coroutineSingletons) {
                }
            }
            Drawable drawable2 = drawables.idle;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$0 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.L$1 = null;
            volumeDialogSettingsButtonViewModel$emitDrawables$1.label = 5;
        }
        return coroutineSingletons;
    }
}
