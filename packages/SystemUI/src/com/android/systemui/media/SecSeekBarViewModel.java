package com.android.systemui.media;

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
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import kotlin.jvm.internal.Intrinsics;

public final class SecSeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0, false);
    public final MutableLiveData _progress;
    public final RepeatableExecutor bgExecutor;
    public final SecSeekBarViewModel$callback$1 callback;
    public Runnable cancel;
    public MediaController controller;
    public CoverMusicCapsuleController coverMusicCapsuleController;
    public boolean isFalseSeek;
    public PlaybackState lastState;
    public boolean listening;
    public final DelayableExecutor mainExecutor;
    public long onSeekBarPreesedValue;
    public PlaybackState playbackState;
    public boolean scrubbing;

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

        public static Progress copy$default(Progress progress, boolean z, Integer num, boolean z2, int i) {
            boolean z3 = (i & 1) != 0 ? progress.enabled : false;
            boolean z4 = progress.seekAvailable;
            boolean z5 = progress.playing;
            if ((i & 8) != 0) {
                z = progress.scrubbing;
            }
            boolean z6 = z;
            if ((i & 16) != 0) {
                num = progress.elapsedTime;
            }
            Integer num2 = num;
            int i2 = progress.duration;
            if ((i & 64) != 0) {
                z2 = progress.listening;
            }
            progress.getClass();
            return new Progress(z3, z4, z5, z6, num2, i2, z2);
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
            return Boolean.hashCode(this.listening) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.duration, (m + (num == null ? 0 : num.hashCode())) * 31, 31);
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
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.listening, ")");
        }
    }

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
                            secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, Integer.valueOf((int) j), false, 111));
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
                        SecSeekBarViewModel.access$checkPlaybackPosition(SecSeekBarViewModel.this);
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

    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.systemui.media.SecSeekBarViewModel$callback$1] */
    public SecSeekBarViewModel(RepeatableExecutor repeatableExecutor, DelayableExecutor delayableExecutor) {
        this.bgExecutor = repeatableExecutor;
        this.mainExecutor = delayableExecutor;
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.SecSeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                PlaybackState playbackState2;
                SecSeekBarViewModel.this.playbackState = playbackState;
                if (playbackState != null) {
                    Integer num = 0;
                    if (!num.equals(SecSeekBarViewModel.this.playbackState)) {
                        SecSeekBarViewModel.this.checkIfPollingNeeded();
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
                                if (Math.abs(playbackState5 != null ? playbackState5.getPosition() - playbackState3.getPosition() : 0L) < 1500 && (playbackState2 = secSeekBarViewModel.lastState) != null && playbackState2.getState() == playbackState3.getState()) {
                                    PlaybackState playbackState6 = secSeekBarViewModel.lastState;
                                    if (Math.abs(playbackState6 != null ? playbackState6.getLastPositionUpdateTime() - playbackState3.getLastPositionUpdateTime() : 0L) < 1500) {
                                        secSeekBarViewModel.lastState = playbackState3;
                                        return;
                                    }
                                }
                                PlaybackState playbackState7 = secSeekBarViewModel.lastState;
                                Long valueOf = playbackState7 != null ? Long.valueOf(playbackState7.getPosition()) : null;
                                Long valueOf2 = Long.valueOf(playbackState3.getPosition());
                                PlaybackState playbackState8 = secSeekBarViewModel.lastState;
                                Integer valueOf3 = playbackState8 != null ? Integer.valueOf(playbackState8.getState()) : null;
                                Integer valueOf4 = Integer.valueOf(playbackState3.getState());
                                PlaybackState playbackState9 = secSeekBarViewModel.lastState;
                                Long valueOf5 = playbackState9 != null ? Long.valueOf(playbackState9.getLastPositionUpdateTime()) : null;
                                Log.d("CapsuleValue", "last position : " + valueOf + ", after position : " + valueOf2 + ", last state : " + valueOf3 + ", after state : " + valueOf4 + " last update : " + valueOf5 + " after update : " + Long.valueOf(playbackState3.getLastPositionUpdateTime()));
                                secSeekBarViewModel.lastState = playbackState3;
                                secSeekBarViewModel.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfCapsuleUpdateNeeded$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CoverMusicCapsuleController coverMusicCapsuleController = SecSeekBarViewModel.this.coverMusicCapsuleController;
                                        if (coverMusicCapsuleController != null) {
                                            coverMusicCapsuleController.updateEqualizerState(playbackState3);
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

    public static final void access$checkPlaybackPosition(SecSeekBarViewModel secSeekBarViewModel) {
        Integer num;
        int i = secSeekBarViewModel._data.duration;
        PlaybackState playbackState = secSeekBarViewModel.playbackState;
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
                    position = j;
                }
            }
            num = Integer.valueOf((int) position);
        } else {
            num = null;
        }
        if (num == null || Intrinsics.areEqual(secSeekBarViewModel._data.elapsedTime, num)) {
            return;
        }
        int i2 = (int) secSeekBarViewModel.onSeekBarPreesedValue;
        if (i2 == 0) {
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, false, num, false, 111));
        } else {
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, false, Integer.valueOf(i2), false, 111));
            secSeekBarViewModel.onSeekBarPreesedValue = 0L;
        }
    }

    public static final void access$setScrubbing(SecSeekBarViewModel secSeekBarViewModel, boolean z) {
        if (secSeekBarViewModel.scrubbing != z) {
            secSeekBarViewModel.scrubbing = z;
            secSeekBarViewModel.checkIfPollingNeeded();
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, z, null, false, 119));
        }
    }

    public final void checkIfPollingNeeded() {
        PlaybackState playbackState;
        boolean z = this.listening && !this.scrubbing && (playbackState = this.playbackState) != null && (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5);
        MediaController mediaController = this.controller;
        MediaSession.Token sessionToken = mediaController != null ? mediaController.getSessionToken() : null;
        final int hashCode = sessionToken != null ? sessionToken.hashCode() : 0;
        if (!z) {
            Runnable runnable = this.cancel;
            if (runnable != null) {
                runnable.run();
            }
            this.cancel = null;
            return;
        }
        if (this.cancel == null) {
            Trace.beginAsyncSection("SeekBarPollingPosition", hashCode);
            final Runnable executeRepeatedly = this.bgExecutor.executeRepeatedly(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$cancelPolling$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecSeekBarViewModel.access$checkPlaybackPosition(SecSeekBarViewModel.this);
                }
            }, 0L, 100L);
            this.cancel = new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$1
                @Override // java.lang.Runnable
                public final void run() {
                    executeRepeatedly.run();
                    Trace.endAsyncSection("SeekBarPollingPosition", hashCode);
                }
            };
        }
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
            if (!view.equals(this.bar)) {
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
