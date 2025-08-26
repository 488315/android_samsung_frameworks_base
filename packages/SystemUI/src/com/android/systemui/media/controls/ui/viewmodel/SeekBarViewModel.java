package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Intent;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.SystemClock;
import android.os.Trace;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.R;
import com.android.systemui.media.NotificationMediaManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda0;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda1;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda3;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0, false);
    public final MutableLiveData _progress;
    public final RepeatableExecutor bgExecutor;
    public final SeekBarViewModel$callback$1 callback;
    public AnonymousClass1 cancel;
    public MediaControlPanel$$ExternalSyntheticLambda0 contentDescriptionListener;
    public MediaController controller;
    public MediaControlPanel$$ExternalSyntheticLambda0 enabledChangeListener;
    public final FalsingManager falsingManager;
    public MotionEvent firstMotionEvent;
    public boolean isFalseSeek;
    public MotionEvent lastMotionEvent;
    public boolean listening;
    public MediaControlPanel$$ExternalSyntheticLambda1 logSeek;
    public PlaybackState playbackState;
    public boolean scrubbing;
    public MediaControlPanel$$ExternalSyntheticLambda0 scrubbingChangeListener;

    public final class Progress {
        public final int duration;
        public final Integer elapsedTime;
        public final boolean enabled;
        public final boolean listening;
        public final boolean playing;
        public final boolean scrubbing;
        public final boolean seekAvailable;

        public Progress(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i, boolean z5) {
            this.enabled = z;
            this.seekAvailable = z2;
            this.playing = z3;
            this.scrubbing = z4;
            this.elapsedTime = num;
            this.duration = i;
            this.listening = z5;
        }

        public static Progress copy$default(Progress progress, boolean z, boolean z2, Integer num, int i, boolean z3, int i2) {
            if ((i2 & 1) != 0) {
                z = progress.enabled;
            }
            boolean z4 = z;
            boolean z5 = progress.seekAvailable;
            boolean z6 = progress.playing;
            if ((i2 & 8) != 0) {
                z2 = progress.scrubbing;
            }
            boolean z7 = z2;
            if ((i2 & 16) != 0) {
                num = progress.elapsedTime;
            }
            Integer num2 = num;
            if ((i2 & 32) != 0) {
                i = progress.duration;
            }
            int i3 = i;
            if ((i2 & 64) != 0) {
                z3 = progress.listening;
            }
            progress.getClass();
            return new Progress(z4, z5, z6, z7, num2, i3, z3);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Progress)) {
                return false;
            }
            Progress progress = (Progress) obj;
            return this.enabled == progress.enabled && this.seekAvailable == progress.seekAvailable && this.playing == progress.playing && this.scrubbing == progress.scrubbing && Intrinsics.areEqual(this.elapsedTime, progress.elapsedTime) && this.duration == progress.duration && this.listening == progress.listening;
        }

        public final int hashCode() {
            int iM = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.enabled) * 31, 31, this.seekAvailable), 31, this.playing), 31, this.scrubbing);
            Integer num = this.elapsedTime;
            return Boolean.hashCode(this.listening) + ReorderTile$$ExternalSyntheticOutline0.m(this.duration, (iM + (num == null ? 0 : num.hashCode())) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Progress(enabled=");
            sb.append(this.enabled);
            sb.append(", seekAvailable=");
            sb.append(this.seekAvailable);
            sb.append(", playing=");
            sb.append(this.playing);
            sb.append(", scrubbing=");
            sb.append(this.scrubbing);
            sb.append(", elapsedTime=");
            sb.append(this.elapsedTime);
            sb.append(", duration=");
            sb.append(this.duration);
            sb.append(", listening=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.listening, ")");
        }
    }

    public final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final FalsingManager falsingManager;
        public final SeekBarViewModel viewModel;

        public SeekBarChangeListener(SeekBarViewModel seekBarViewModel, FalsingManager falsingManager) {
            this.viewModel = seekBarViewModel;
            this.falsingManager = falsingManager;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                final SeekBarViewModel seekBarViewModel = this.viewModel;
                final long j = i;
                seekBarViewModel.getClass();
                seekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$onSeekProgress$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SeekBarViewModel seekBarViewModel2 = seekBarViewModel;
                        if (seekBarViewModel2.scrubbing) {
                            seekBarViewModel2.set_data(SeekBarViewModel.Progress.copy$default(seekBarViewModel2._data, false, false, Integer.valueOf((int) j), 0, false, 111));
                        } else {
                            seekBarViewModel2.bgExecutor.execute(new SeekBarViewModel$onSeek$1(seekBarViewModel2, j));
                        }
                    }
                });
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            final SeekBarViewModel seekBarViewModel = this.viewModel;
            seekBarViewModel.getClass();
            seekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$onSeekStarting$1
                @Override // java.lang.Runnable
                public final void run() {
                    SeekBarViewModel.access$setScrubbing(seekBarViewModel, true);
                    seekBarViewModel.isFalseSeek = false;
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0042  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0038  */
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onStopTrackingTouch(SeekBar seekBar) {
            SeekBarViewModel seekBarViewModel = this.viewModel;
            MotionEvent motionEvent = seekBarViewModel.firstMotionEvent;
            if (motionEvent != null && seekBarViewModel.lastMotionEvent != null) {
                float x = motionEvent.getX();
                MotionEvent motionEvent2 = seekBarViewModel.lastMotionEvent;
                motionEvent2.getClass();
                float fAbs = Math.abs(x - motionEvent2.getX());
                MotionEvent motionEvent3 = seekBarViewModel.firstMotionEvent;
                motionEvent3.getClass();
                float y = motionEvent3.getY();
                MotionEvent motionEvent4 = seekBarViewModel.lastMotionEvent;
                motionEvent4.getClass();
                if (fAbs >= Math.abs(y - motionEvent4.getY())) {
                }
            } else if (this.falsingManager.isFalseTouch(18)) {
                SeekBarViewModel seekBarViewModel2 = this.viewModel;
                seekBarViewModel2.getClass();
                seekBarViewModel2.bgExecutor.execute(new SeekBarViewModel$onSeekFalse$1(seekBarViewModel2));
            }
            SeekBarViewModel seekBarViewModel3 = this.viewModel;
            long progress = seekBar.getProgress();
            seekBarViewModel3.getClass();
            seekBarViewModel3.bgExecutor.execute(new SeekBarViewModel$onSeek$1(seekBarViewModel3, progress));
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ Runnable $cancelPolling;
        public final /* synthetic */ int $traceCookie;

        public AnonymousClass1(Runnable runnable, int i) {
            this.$cancelPolling = runnable;
            this.$traceCookie = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.$cancelPolling.run();
            Trace.endAsyncSection("SeekBarPollingPosition", this.$traceCookie);
        }
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$callback$1] */
    public SeekBarViewModel(RepeatableExecutor repeatableExecutor, FalsingManager falsingManager) {
        this.bgExecutor = repeatableExecutor;
        this.falsingManager = falsingManager;
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                Pair enabledStateAndDuration = this.this$0.getEnabledStateAndDuration(mediaMetadata);
                boolean zBooleanValue = ((Boolean) enabledStateAndDuration.component1()).booleanValue();
                int iIntValue = ((Number) enabledStateAndDuration.component2()).intValue();
                SeekBarViewModel seekBarViewModel = this.this$0;
                SeekBarViewModel.Progress progress = seekBarViewModel._data;
                if (progress.duration != iIntValue) {
                    seekBarViewModel.set_data(SeekBarViewModel.Progress.copy$default(progress, zBooleanValue, false, null, iIntValue, false, 94));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                this.this$0.playbackState = playbackState;
                if (playbackState != null) {
                    Integer num = 0;
                    if (!num.equals(this.this$0.playbackState)) {
                        this.this$0.checkIfPollingNeeded(true);
                        return;
                    }
                }
                SeekBarViewModel seekBarViewModel = this.this$0;
                seekBarViewModel.getClass();
                seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$clearController$1(seekBarViewModel));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                SeekBarViewModel seekBarViewModel = this.this$0;
                seekBarViewModel.getClass();
                seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$clearController$1(seekBarViewModel));
            }
        };
        this.listening = true;
    }

    public static final CharSequence access$formatTimeContentDescription(SeekBarViewModel seekBarViewModel, int i) {
        seekBarViewModel.getClass();
        long j = i / 1000;
        long j2 = j >= 3600 ? j / PluginLock.VERSION : 0L;
        long j3 = j - (PluginLock.VERSION * j2);
        long j4 = j3 >= 60 ? j3 / 60 : 0L;
        long j5 = j3 - (60 * j4);
        ArrayList arrayList = new ArrayList();
        if (j2 > 0) {
            arrayList.add(new Measure(Long.valueOf(j2), MeasureUnit.HOUR));
        }
        if (j4 > 0) {
            arrayList.add(new Measure(Long.valueOf(j4), MeasureUnit.MINUTE));
        }
        arrayList.add(new Measure(Long.valueOf(j5), MeasureUnit.SECOND));
        MeasureFormat measureFormat = MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE);
        Measure[] measureArr = (Measure[]) arrayList.toArray(new Measure[0]);
        return measureFormat.formatMeasures((Measure[]) Arrays.copyOf(measureArr, measureArr.length));
    }

    public static final void access$setScrubbing(SeekBarViewModel seekBarViewModel, boolean z) {
        MediaControlPanel mediaControlPanel;
        MediaData mediaData;
        if (seekBarViewModel.scrubbing != z) {
            seekBarViewModel.scrubbing = z;
            seekBarViewModel.checkIfPollingNeeded(true);
            MediaControlPanel$$ExternalSyntheticLambda0 mediaControlPanel$$ExternalSyntheticLambda0 = seekBarViewModel.scrubbingChangeListener;
            if (mediaControlPanel$$ExternalSyntheticLambda0 != null && (mediaData = (mediaControlPanel = mediaControlPanel$$ExternalSyntheticLambda0.f$0).mMediaData) != null && mediaData.semanticActions != null && z != mediaControlPanel.mIsScrubbing) {
                mediaControlPanel.mIsScrubbing = z;
                mediaControlPanel.mMainExecutor.execute(new MediaControlPanel$$ExternalSyntheticLambda3(mediaControlPanel, 1));
            }
            seekBarViewModel.set_data(Progress.copy$default(seekBarViewModel._data, false, z, null, 0, false, 119));
        }
    }

    public final void checkIfPollingNeeded(boolean z) {
        PlaybackState playbackState;
        boolean z2 = this.listening && !this.scrubbing && (playbackState = this.playbackState) != null && (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5);
        MediaController mediaController = this.controller;
        MediaSession.Token sessionToken = mediaController != null ? mediaController.getSessionToken() : null;
        int iHashCode = sessionToken != null ? sessionToken.hashCode() : 0;
        if (z2) {
            if (this.cancel == null) {
                Trace.beginAsyncSection("SeekBarPollingPosition", iHashCode);
                this.cancel = new AnonymousClass1(this.bgExecutor.executeRepeatedly(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$checkIfPollingNeeded$cancelPolling$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.$tmp0.checkPlaybackPosition();
                    }
                }, 0L, 500L), iHashCode);
                return;
            }
            return;
        }
        if (z) {
            checkPlaybackPosition();
            AnonymousClass1 anonymousClass1 = this.cancel;
            if (anonymousClass1 != null) {
                anonymousClass1.run();
            }
            this.cancel = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkPlaybackPosition() {
        Integer numValueOf;
        int i = this._data.duration;
        PlaybackState playbackState = this.playbackState;
        if (playbackState != null) {
            long j = i;
            long position = playbackState.getPosition();
            if (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5) {
                long lastPositionUpdateTime = playbackState.getLastPositionUpdateTime();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (lastPositionUpdateTime > 0) {
                    long position2 = playbackState.getPosition() + ((long) (playbackState.getPlaybackSpeed() * (jElapsedRealtime - lastPositionUpdateTime)));
                    if (j < 0 || position2 <= j) {
                        j = position2 < 0 ? 0L : position2;
                    }
                } else {
                    j = position;
                }
                numValueOf = Integer.valueOf((int) j);
            }
        } else {
            numValueOf = null;
        }
        Integer num = numValueOf;
        if (num == null || Intrinsics.areEqual(this._data.elapsedTime, num)) {
            return;
        }
        set_data(Progress.copy$default(this._data, false, false, num, 0, false, 111));
    }

    public final Pair getEnabledStateAndDuration(MediaMetadata mediaMetadata) {
        boolean z = false;
        int i = mediaMetadata != null ? (int) mediaMetadata.getLong("android.media.metadata.DURATION") : 0;
        PlaybackState playbackState = this.playbackState;
        if (playbackState != null && ((playbackState == null || playbackState.getState() != 0) && i > 0)) {
            z = true;
        }
        return new Pair(Boolean.valueOf(z), Integer.valueOf(i));
    }

    public final void setController(MediaController mediaController) {
        MediaController mediaController2 = this.controller;
        if (Intrinsics.areEqual(mediaController2 != null ? mediaController2.getSessionToken() : null, mediaController != null ? mediaController.getSessionToken() : null)) {
            return;
        }
        MediaController mediaController3 = this.controller;
        SeekBarViewModel$callback$1 seekBarViewModel$callback$1 = this.callback;
        if (mediaController3 != null) {
            mediaController3.unregisterCallback(seekBarViewModel$callback$1);
        }
        if (mediaController != null) {
            mediaController.registerCallback(seekBarViewModel$callback$1);
        }
        this.controller = mediaController;
    }

    public final void set_data(final Progress progress) {
        MediaControlPanel$$ExternalSyntheticLambda0 mediaControlPanel$$ExternalSyntheticLambda0;
        boolean z = this._data.enabled;
        boolean z2 = progress.enabled;
        boolean z3 = z2 != z;
        this._data = progress;
        if (z3 && (mediaControlPanel$$ExternalSyntheticLambda0 = this.enabledChangeListener) != null) {
            MediaControlPanel mediaControlPanel = mediaControlPanel$$ExternalSyntheticLambda0.f$0;
            if (z2 != mediaControlPanel.mIsSeekBarEnabled) {
                mediaControlPanel.mIsSeekBarEnabled = z2;
                mediaControlPanel.updateSeekBarVisibility();
                mediaControlPanel.mMainExecutor.execute(new MediaControlPanel$$ExternalSyntheticLambda3(mediaControlPanel, 0));
            }
        }
        this._progress.postValue(progress);
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$_data$1
            @Override // java.lang.Runnable
            public final void run() {
                final CharSequence charSequenceAccess$formatTimeContentDescription;
                final CharSequence charSequenceAccess$formatTimeContentDescription2 = SeekBarViewModel.access$formatTimeContentDescription(this.this$0, progress.duration);
                Integer num = progress.elapsedTime;
                if (num == null || (charSequenceAccess$formatTimeContentDescription = SeekBarViewModel.access$formatTimeContentDescription(this.this$0, num.intValue())) == null) {
                    charSequenceAccess$formatTimeContentDescription = "";
                }
                MediaControlPanel$$ExternalSyntheticLambda0 mediaControlPanel$$ExternalSyntheticLambda02 = this.this$0.contentDescriptionListener;
                if (mediaControlPanel$$ExternalSyntheticLambda02 != null) {
                    Intent intent = MediaControlPanel.SETTINGS_INTENT;
                    final MediaControlPanel mediaControlPanel2 = mediaControlPanel$$ExternalSyntheticLambda02.f$0;
                    mediaControlPanel2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaControlPanel mediaControlPanel3 = mediaControlPanel2;
                            CharSequence charSequence = charSequenceAccess$formatTimeContentDescription;
                            CharSequence charSequence2 = charSequenceAccess$formatTimeContentDescription2;
                            SeekBar seekBar = mediaControlPanel3.mSeekBarObserver.holder.seekBar;
                            seekBar.setContentDescription(seekBar.getContext().getString(R.string.controls_media_seekbar_description, charSequence, charSequence2));
                        }
                    });
                }
            }
        });
    }

    public final void updateController(MediaController mediaController) {
        setController(mediaController);
        MediaController mediaController2 = this.controller;
        this.playbackState = mediaController2 != null ? mediaController2.getPlaybackState() : null;
        MediaController mediaController3 = this.controller;
        Pair enabledStateAndDuration = getEnabledStateAndDuration(mediaController3 != null ? mediaController3.getMetadata() : null);
        boolean zBooleanValue = ((Boolean) enabledStateAndDuration.component1()).booleanValue();
        int iIntValue = ((Number) enabledStateAndDuration.component2()).intValue();
        PlaybackState playbackState = this.playbackState;
        boolean z = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
        PlaybackState playbackState2 = this.playbackState;
        Integer numValueOf = playbackState2 != null ? Integer.valueOf((int) playbackState2.getPosition()) : null;
        PlaybackState playbackState3 = this.playbackState;
        set_data(new Progress(zBooleanValue, z, NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0), this.scrubbing, numValueOf, iIntValue, this.listening));
        checkIfPollingNeeded(false);
    }

    public final class SeekBarTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public final SeekBar bar;
        public final GestureDetectorCompat detector;
        public final int flingVelocity;
        public boolean shouldGoToSeekBar;
        public final SeekBarViewModel viewModel;

        public SeekBarTouchListener(SeekBarViewModel seekBarViewModel, SeekBar seekBar) {
            this.viewModel = seekBarViewModel;
            this.bar = seekBar;
            this.detector = new GestureDetectorCompat(seekBar.getContext(), this);
            this.flingVelocity = ViewConfiguration.get(seekBar.getContext()).getScaledMinimumFlingVelocity() * 10;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            ViewParent parent;
            int paddingLeft = this.bar.getPaddingLeft();
            int paddingRight = this.bar.getPaddingRight();
            int progress = this.bar.getProgress();
            int max = this.bar.getMax() - this.bar.getMin();
            double min = max > 0 ? (progress - this.bar.getMin()) / max : 0.0d;
            int width = (this.bar.getWidth() - paddingLeft) - paddingRight;
            double d = this.bar.isLayoutRtl() ? ((1 - min) * width) + paddingLeft : (width * min) + paddingLeft;
            long height = this.bar.getHeight() / 2;
            int iRound = (int) (Math.round(d) - height);
            int iRound2 = (int) (Math.round(d) + height);
            int iRound3 = Math.round(motionEvent.getX());
            boolean z = iRound3 >= iRound && iRound3 <= iRound2;
            this.shouldGoToSeekBar = z;
            if (z && (parent = this.bar.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            this.viewModel.firstMotionEvent = motionEvent.copy();
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (Math.abs(f) > this.flingVelocity || Math.abs(f2) > this.flingVelocity) {
                SeekBarViewModel seekBarViewModel = this.viewModel;
                seekBarViewModel.getClass();
                seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$onSeekFalse$1(seekBarViewModel));
            }
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            this.shouldGoToSeekBar = true;
            return true;
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (!Intrinsics.areEqual(view, this.bar)) {
                return false;
            }
            this.detector.mDetector.onTouchEvent(motionEvent);
            this.viewModel.lastMotionEvent = motionEvent.copy();
            return !this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onShowPress(MotionEvent motionEvent) {
        }
    }

    public static /* synthetic */ void getFirstMotionEvent$annotations() {
    }

    public static /* synthetic */ void getLastMotionEvent$annotations() {
    }
}
