package com.android.systemui.media;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.SystemClock;
import android.os.Trace;
import android.util.secutil.Log;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecSeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0, false);
    public final MutableLiveData _progress;
    public final RepeatableExecutor bgExecutor;
    public final SecSeekBarViewModel$callback$1 callback;
    public SecSeekBarViewModel$checkIfPollingNeeded$1 cancel;
    public MediaController controller;
    public CoverMusicCapsuleController coverMusicCapsuleController;
    public boolean isFalseSeek;
    public PlaybackState lastState;
    public boolean listening;
    public final DelayableExecutor mainExecutor;
    public OAMusicChipController oaMusicChipController;
    public long onSeekBarPreesedValue;
    public PlaybackState playbackState;
    public boolean scrubbing;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.enabled) * 31, 31, this.seekAvailable), 31, this.playing), 31, this.scrubbing);
            Integer num = this.elapsedTime;
            return Boolean.hashCode(this.listening) + ReorderTile$$ExternalSyntheticOutline0.m(this.duration, (m + (num == null ? 0 : num.hashCode())) * 31, 31);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final SecSeekBarViewModel viewModel;

        public SeekBarChangeListener(SecSeekBarViewModel secSeekBarViewModel) {
            this.viewModel = secSeekBarViewModel;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(final SeekBar seekBar, int i, boolean z) {
            if (z) {
                final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
                final long j = i;
                secSeekBarViewModel.onSeekBarPreesedValue = j;
                secSeekBarViewModel.getClass();
                secSeekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekProgress$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (SecSeekBarViewModel.this.scrubbing) {
                            if (j == seekBar.getMax() || j == 0) {
                                seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                            }
                            SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                            secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, false, Integer.valueOf((int) j), 0, false, 111));
                        }
                    }
                });
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekStarting$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecSeekBarViewModel.access$setScrubbing(SecSeekBarViewModel.this, true);
                    SecSeekBarViewModel.this.isFalseSeek = false;
                }
            });
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_SEEK_BAR_INTERACTION);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
            final long progress = seekBar.getProgress();
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeek$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.TransportControls transportControls;
                    SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                    if (secSeekBarViewModel2.isFalseSeek) {
                        SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel2, false);
                        SecSeekBarViewModel.this.checkPlaybackPosition();
                        return;
                    }
                    MediaController mediaController = secSeekBarViewModel2.controller;
                    if (mediaController != null && (transportControls = mediaController.getTransportControls()) != null) {
                        transportControls.seekTo(progress);
                    }
                    SecSeekBarViewModel secSeekBarViewModel3 = SecSeekBarViewModel.this;
                    secSeekBarViewModel3.playbackState = null;
                    SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel3, false);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.media.SecSeekBarViewModel$callback$1] */
    public SecSeekBarViewModel(RepeatableExecutor repeatableExecutor, DelayableExecutor delayableExecutor) {
        this.bgExecutor = repeatableExecutor;
        this.mainExecutor = delayableExecutor;
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.SecSeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                Pair enabledStateAndDuration = SecSeekBarViewModel.this.getEnabledStateAndDuration(mediaMetadata);
                boolean booleanValue = ((Boolean) enabledStateAndDuration.component1()).booleanValue();
                int intValue = ((Number) enabledStateAndDuration.component2()).intValue();
                SecSeekBarViewModel secSeekBarViewModel = SecSeekBarViewModel.this;
                SecSeekBarViewModel.Progress progress = secSeekBarViewModel._data;
                if (progress.duration != intValue) {
                    secSeekBarViewModel.set_data(SecSeekBarViewModel.Progress.copy$default(progress, booleanValue, false, null, intValue, false, 94));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                PlaybackState playbackState2;
                SecSeekBarViewModel.this.playbackState = playbackState;
                if (playbackState != null) {
                    Integer num = 0;
                    if (!num.equals(SecSeekBarViewModel.this.playbackState)) {
                        SecSeekBarViewModel.this.checkIfPollingNeeded(true);
                        final SecSeekBarViewModel secSeekBarViewModel = SecSeekBarViewModel.this;
                        final PlaybackState playbackState3 = secSeekBarViewModel.playbackState;
                        if (playbackState3 != null) {
                            PlaybackState playbackState4 = secSeekBarViewModel.lastState;
                            if (playbackState4 == null || !playbackState4.equals(playbackState3)) {
                                Integer num2 = 0;
                                if (num2.equals(secSeekBarViewModel.playbackState)) {
                                    return;
                                }
                                PlaybackState playbackState5 = secSeekBarViewModel.lastState;
                                if (Intrinsics.areEqual(playbackState5 != null ? Float.valueOf(playbackState5.getPlaybackSpeed()) : null, playbackState3.getPlaybackSpeed())) {
                                    PlaybackState playbackState6 = secSeekBarViewModel.lastState;
                                    if (Math.abs(playbackState6 != null ? playbackState6.getPosition() - playbackState3.getPosition() : 0L) < 1500 && (playbackState2 = secSeekBarViewModel.lastState) != null && playbackState2.getState() == playbackState3.getState()) {
                                        PlaybackState playbackState7 = secSeekBarViewModel.lastState;
                                        if (Math.abs(playbackState7 != null ? playbackState7.getLastPositionUpdateTime() - playbackState3.getLastPositionUpdateTime() : 0L) < 1500) {
                                            secSeekBarViewModel.lastState = playbackState3;
                                            return;
                                        }
                                    }
                                }
                                PlaybackState playbackState8 = secSeekBarViewModel.lastState;
                                Long valueOf = playbackState8 != null ? Long.valueOf(playbackState8.getPosition()) : null;
                                long position = playbackState3.getPosition();
                                PlaybackState playbackState9 = secSeekBarViewModel.lastState;
                                Integer valueOf2 = playbackState9 != null ? Integer.valueOf(playbackState9.getState()) : null;
                                int state = playbackState3.getState();
                                PlaybackState playbackState10 = secSeekBarViewModel.lastState;
                                Long valueOf3 = playbackState10 != null ? Long.valueOf(playbackState10.getLastPositionUpdateTime()) : null;
                                long lastPositionUpdateTime = playbackState3.getLastPositionUpdateTime();
                                PlaybackState playbackState11 = secSeekBarViewModel.lastState;
                                Float valueOf4 = playbackState11 != null ? Float.valueOf(playbackState11.getPlaybackSpeed()) : null;
                                Log.d("CapsuleValue", "last position : " + valueOf + ", after position : " + position + ", last state : " + valueOf2 + ", after state : " + state + " last update : " + valueOf3 + " after update : " + lastPositionUpdateTime + " last speed : " + valueOf4 + " after speed : " + playbackState3.getPlaybackSpeed());
                                secSeekBarViewModel.lastState = playbackState3;
                                Runnable runnable = new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfCapsuleUpdateNeeded$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CoverMusicCapsuleController coverMusicCapsuleController = SecSeekBarViewModel.this.coverMusicCapsuleController;
                                        if (coverMusicCapsuleController != null) {
                                            coverMusicCapsuleController.updateEqualizerState(playbackState3);
                                        }
                                    }
                                };
                                DelayableExecutor delayableExecutor2 = secSeekBarViewModel.mainExecutor;
                                delayableExecutor2.execute(runnable);
                                delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfCapsuleUpdateNeeded$2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        OAMusicChipController oAMusicChipController = SecSeekBarViewModel.this.oaMusicChipController;
                                        if (oAMusicChipController != null) {
                                            oAMusicChipController.updatePlaybackState(playbackState3);
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
                SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$clearController$1(secSeekBarViewModel2));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                SecSeekBarViewModel secSeekBarViewModel = SecSeekBarViewModel.this;
                secSeekBarViewModel.getClass();
                secSeekBarViewModel.bgExecutor.execute(new SecSeekBarViewModel$clearController$1(secSeekBarViewModel));
            }
        };
    }

    public static final void access$setScrubbing(SecSeekBarViewModel secSeekBarViewModel, boolean z) {
        if (secSeekBarViewModel.scrubbing != z) {
            secSeekBarViewModel.scrubbing = z;
            secSeekBarViewModel.checkIfPollingNeeded(true);
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, false, z, null, 0, false, 119));
        }
    }

    public final void checkIfPollingNeeded(boolean z) {
        PlaybackState playbackState;
        boolean z2 = this.listening && !this.scrubbing && (playbackState = this.playbackState) != null && (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5);
        MediaController mediaController = this.controller;
        MediaSession.Token sessionToken = mediaController != null ? mediaController.getSessionToken() : null;
        int hashCode = sessionToken != null ? sessionToken.hashCode() : 0;
        if (z2) {
            if (this.cancel == null) {
                Trace.beginAsyncSection("SeekBarPollingPosition", hashCode);
                this.cancel = new SecSeekBarViewModel$checkIfPollingNeeded$1(this.bgExecutor.executeRepeatedly(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$cancelPolling$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecSeekBarViewModel.this.checkPlaybackPosition();
                    }
                }, 0L, 100L), hashCode);
                return;
            }
            return;
        }
        if (z) {
            checkPlaybackPosition();
            SecSeekBarViewModel$checkIfPollingNeeded$1 secSeekBarViewModel$checkIfPollingNeeded$1 = this.cancel;
            if (secSeekBarViewModel$checkIfPollingNeeded$1 != null) {
                secSeekBarViewModel$checkIfPollingNeeded$1.run();
            }
            this.cancel = null;
        }
    }

    public final void checkPlaybackPosition() {
        Integer num;
        int i = this._data.duration;
        PlaybackState playbackState = this.playbackState;
        if (playbackState != null) {
            long j = i;
            long position = playbackState.getPosition();
            if (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5) {
                long lastPositionUpdateTime = playbackState.getLastPositionUpdateTime();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (lastPositionUpdateTime > 0) {
                    long position2 = playbackState.getPosition() + ((long) (playbackState.getPlaybackSpeed() * (elapsedRealtime - lastPositionUpdateTime)));
                    if (j < 0 || position2 <= j) {
                        j = position2 < 0 ? 0L : position2;
                    }
                    num = Integer.valueOf((int) j);
                }
            }
            j = position;
            num = Integer.valueOf((int) j);
        } else {
            num = null;
        }
        Integer num2 = num;
        if (num2 == null || Intrinsics.areEqual(this._data.elapsedTime, num2)) {
            return;
        }
        int i2 = (int) this.onSeekBarPreesedValue;
        if (i2 == 0) {
            set_data(Progress.copy$default(this._data, false, false, num2, 0, false, 111));
        } else {
            set_data(Progress.copy$default(this._data, false, false, Integer.valueOf(i2), 0, false, 111));
            this.onSeekBarPreesedValue = 0L;
        }
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
        SecSeekBarViewModel$callback$1 secSeekBarViewModel$callback$1 = this.callback;
        if (mediaController3 != null) {
            mediaController3.unregisterCallback(secSeekBarViewModel$callback$1);
        }
        if (mediaController != null) {
            mediaController.registerCallback(secSeekBarViewModel$callback$1);
        }
        this.controller = mediaController;
    }

    public final void set_data(Progress progress) {
        this._data = progress;
        this._progress.postValue(progress);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SeekBarTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {
        public final SeekBar bar;
        public final GestureDetectorCompat detector;
        public final int flingVelocity;
        public boolean isThumbTouched;
        public boolean shouldGoToSeekBar;
        public final SecSeekBarViewModel viewModel;

        public SeekBarTouchListener(SecSeekBarViewModel secSeekBarViewModel, SeekBar seekBar) {
            this.viewModel = secSeekBarViewModel;
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
            int round = (int) (Math.round(d) - height);
            int round2 = (int) (Math.round(d) + height);
            int round3 = Math.round(motionEvent.getX());
            boolean z = false;
            if (round <= round3 && round3 <= round2) {
                z = true;
            }
            this.shouldGoToSeekBar = z;
            this.isThumbTouched = z;
            if (z && (parent = this.bar.getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            return this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isThumbTouched && (Math.abs(f) > this.flingVelocity || Math.abs(f2) > this.flingVelocity)) {
                final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
                secSeekBarViewModel.getClass();
                secSeekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekFalse$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                        if (secSeekBarViewModel2.scrubbing) {
                            secSeekBarViewModel2.isFalseSeek = true;
                        }
                    }
                });
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
            return !this.shouldGoToSeekBar;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onShowPress(MotionEvent motionEvent) {
        }
    }
}
