package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.SystemClock;
import android.util.secutil.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import com.android.systemui.util.concurrency.RepeatableExecutorImpl;
import com.android.systemui.util.concurrency.RepeatableExecutorImpl.ExecutionToken;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecSeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0);
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
    public SecMediaControlPanel$$ExternalSyntheticLambda1 sessionDestroyCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Progress {
        public final int duration;
        public final Integer elapsedTime;
        public final boolean enabled;
        public final boolean playing;
        public final boolean scrubbing;
        public final boolean seekAvailable;

        public Progress(boolean z, boolean z2, boolean z3, boolean z4, Integer num, int i) {
            this.enabled = z;
            this.seekAvailable = z2;
            this.playing = z3;
            this.scrubbing = z4;
            this.elapsedTime = num;
            this.duration = i;
        }

        public static Progress copy$default(Progress progress, boolean z, Integer num, int i) {
            boolean z2 = (i & 1) != 0 ? progress.enabled : false;
            boolean z3 = (i & 2) != 0 ? progress.seekAvailable : false;
            boolean z4 = (i & 4) != 0 ? progress.playing : false;
            if ((i & 8) != 0) {
                z = progress.scrubbing;
            }
            boolean z5 = z;
            if ((i & 16) != 0) {
                num = progress.elapsedTime;
            }
            Integer num2 = num;
            int i2 = (i & 32) != 0 ? progress.duration : 0;
            progress.getClass();
            return new Progress(z2, z3, z4, z5, num2, i2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Progress)) {
                return false;
            }
            Progress progress = (Progress) obj;
            return this.enabled == progress.enabled && this.seekAvailable == progress.seekAvailable && this.playing == progress.playing && this.scrubbing == progress.scrubbing && Intrinsics.areEqual(this.elapsedTime, progress.elapsedTime) && this.duration == progress.duration;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            boolean z = this.enabled;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = i * 31;
            boolean z2 = this.seekAvailable;
            int i3 = z2;
            if (z2 != 0) {
                i3 = 1;
            }
            int i4 = (i2 + i3) * 31;
            boolean z3 = this.playing;
            int i5 = z3;
            if (z3 != 0) {
                i5 = 1;
            }
            int i6 = (i4 + i5) * 31;
            boolean z4 = this.scrubbing;
            int i7 = (i6 + (z4 ? 1 : z4 ? 1 : 0)) * 31;
            Integer num = this.elapsedTime;
            return Integer.hashCode(this.duration) + ((i7 + (num == null ? 0 : num.hashCode())) * 31);
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.duration, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final SecSeekBarViewModel viewModel;

        public SeekBarChangeListener(SecSeekBarViewModel secSeekBarViewModel) {
            this.viewModel = secSeekBarViewModel;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
                final long j = i;
                secSeekBarViewModel.onSeekBarPreesedValue = j;
                secSeekBarViewModel.getClass();
                ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekProgress$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                        if (secSeekBarViewModel2.scrubbing) {
                            secSeekBarViewModel2.set_data(SecSeekBarViewModel.Progress.copy$default(secSeekBarViewModel2._data, false, Integer.valueOf((int) j), 47));
                        }
                    }
                });
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
            secSeekBarViewModel.getClass();
            ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekStarting$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecSeekBarViewModel.access$setScrubbing(SecSeekBarViewModel.this, true);
                    SecSeekBarViewModel.this.isFalseSeek = false;
                }
            });
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            final SecSeekBarViewModel secSeekBarViewModel = this.viewModel;
            final long progress = seekBar.getProgress();
            secSeekBarViewModel.getClass();
            ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeek$1
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
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0023");
                    SecSeekBarViewModel secSeekBarViewModel3 = SecSeekBarViewModel.this;
                    secSeekBarViewModel3.playbackState = null;
                    SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel3, false);
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r8v3, types: [com.android.systemui.media.SecSeekBarViewModel$callback$1] */
    public SecSeekBarViewModel(RepeatableExecutor repeatableExecutor, DelayableExecutor delayableExecutor) {
        this.bgExecutor = repeatableExecutor;
        this.mainExecutor = delayableExecutor;
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.postValue(this._data);
        this._progress = mutableLiveData;
        this.callback = new MediaController.Callback() { // from class: com.android.systemui.media.SecSeekBarViewModel$callback$1
            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                SecSeekBarViewModel.this.playbackState = playbackState;
                if (playbackState != null) {
                    boolean z = false;
                    Integer num = 0;
                    if (!num.equals(SecSeekBarViewModel.this.playbackState)) {
                        SecSeekBarViewModel.this.checkIfPollingNeeded();
                        final SecSeekBarViewModel secSeekBarViewModel = SecSeekBarViewModel.this;
                        MediaController mediaController = secSeekBarViewModel.controller;
                        final PlaybackState playbackState2 = mediaController != null ? mediaController.getPlaybackState() : null;
                        if (playbackState2 != null) {
                            PlaybackState playbackState3 = secSeekBarViewModel.lastState;
                            if (playbackState3 != null && playbackState3.equals(playbackState2)) {
                                return;
                            }
                            Integer num2 = 0;
                            if (num2.equals(secSeekBarViewModel.playbackState)) {
                                return;
                            }
                            PlaybackState playbackState4 = secSeekBarViewModel.lastState;
                            if (Math.abs(playbackState4 != null ? playbackState4.getPosition() - playbackState2.getPosition() : 0L) < 1500) {
                                PlaybackState playbackState5 = secSeekBarViewModel.lastState;
                                if (playbackState5 != null && playbackState5.getState() == playbackState2.getState()) {
                                    z = true;
                                }
                                if (z) {
                                    PlaybackState playbackState6 = secSeekBarViewModel.lastState;
                                    if (Math.abs(playbackState6 != null ? playbackState6.getLastPositionUpdateTime() - playbackState2.getLastPositionUpdateTime() : 0L) < 1500) {
                                        secSeekBarViewModel.lastState = playbackState2;
                                        return;
                                    }
                                }
                            }
                            PlaybackState playbackState7 = secSeekBarViewModel.lastState;
                            Long valueOf = playbackState7 != null ? Long.valueOf(playbackState7.getPosition()) : null;
                            Long valueOf2 = Long.valueOf(playbackState2.getPosition());
                            PlaybackState playbackState8 = secSeekBarViewModel.lastState;
                            Integer valueOf3 = playbackState8 != null ? Integer.valueOf(playbackState8.getState()) : null;
                            Integer valueOf4 = Integer.valueOf(playbackState2.getState());
                            PlaybackState playbackState9 = secSeekBarViewModel.lastState;
                            Long valueOf5 = playbackState9 != null ? Long.valueOf(playbackState9.getLastPositionUpdateTime()) : null;
                            Log.d("CapsuleValue", "last position : " + valueOf + ", after position : " + valueOf2 + ", last state : " + valueOf3 + ", after state : " + valueOf4 + " last update : " + valueOf5 + " after update : " + Long.valueOf(playbackState2.getLastPositionUpdateTime()));
                            secSeekBarViewModel.lastState = playbackState2;
                            ((ExecutorImpl) secSeekBarViewModel.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfCapsuleUpdateNeeded$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CoverMusicCapsuleController coverMusicCapsuleController = SecSeekBarViewModel.this.coverMusicCapsuleController;
                                    if (coverMusicCapsuleController != null) {
                                        coverMusicCapsuleController.updateEqualizerState(playbackState2);
                                    }
                                }
                            });
                            return;
                        }
                        return;
                    }
                }
                SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                secSeekBarViewModel2.getClass();
                ((RepeatableExecutorImpl) secSeekBarViewModel2.bgExecutor).execute(new SecSeekBarViewModel$clearController$1(secSeekBarViewModel2));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                final SecSeekBarViewModel secSeekBarViewModel = SecSeekBarViewModel.this;
                ((ExecutorImpl) secSeekBarViewModel.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$callback$1$onSessionDestroyed$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecMediaControlPanel$$ExternalSyntheticLambda1 secMediaControlPanel$$ExternalSyntheticLambda1 = SecSeekBarViewModel.this.sessionDestroyCallback;
                        if (secMediaControlPanel$$ExternalSyntheticLambda1 != null) {
                            secMediaControlPanel$$ExternalSyntheticLambda1.f$0.removePlayer();
                        }
                    }
                });
                SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                secSeekBarViewModel2.getClass();
                ((RepeatableExecutorImpl) secSeekBarViewModel2.bgExecutor).execute(new SecSeekBarViewModel$clearController$1(secSeekBarViewModel2));
            }
        };
        this.listening = true;
    }

    public static final void access$checkPlaybackPosition(SecSeekBarViewModel secSeekBarViewModel) {
        Integer num;
        PlaybackState playbackState;
        int i = secSeekBarViewModel._data.duration;
        MediaController mediaController = secSeekBarViewModel.controller;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            num = null;
        } else {
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
        }
        if (num == null || Intrinsics.areEqual(secSeekBarViewModel._data.elapsedTime, num)) {
            return;
        }
        int i2 = (int) secSeekBarViewModel.onSeekBarPreesedValue;
        if (i2 == 0) {
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, false, num, 47));
        } else {
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, false, Integer.valueOf(i2), 47));
            secSeekBarViewModel.onSeekBarPreesedValue = 0L;
        }
    }

    public static final void access$setScrubbing(SecSeekBarViewModel secSeekBarViewModel, boolean z) {
        if (secSeekBarViewModel.scrubbing != z) {
            secSeekBarViewModel.scrubbing = z;
            secSeekBarViewModel.checkIfPollingNeeded();
            secSeekBarViewModel.set_data(Progress.copy$default(secSeekBarViewModel._data, z, null, 55));
        }
    }

    public final void checkIfPollingNeeded() {
        boolean z = false;
        if (this.listening && !this.scrubbing) {
            PlaybackState playbackState = this.playbackState;
            if (playbackState != null && (playbackState.getState() == 3 || playbackState.getState() == 4 || playbackState.getState() == 5)) {
                z = true;
            }
        }
        if (!z) {
            Runnable runnable = this.cancel;
            if (runnable != null) {
                runnable.run();
            }
            this.cancel = null;
            return;
        }
        if (this.cancel == null) {
            RepeatableExecutor repeatableExecutor = this.bgExecutor;
            Runnable runnable2 = new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecSeekBarViewModel.access$checkPlaybackPosition(SecSeekBarViewModel.this);
                }
            };
            repeatableExecutor.getClass();
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            final RepeatableExecutorImpl.ExecutionToken executionToken = ((RepeatableExecutorImpl) repeatableExecutor).new ExecutionToken(runnable2, 100L, timeUnit);
            synchronized (executionToken.mLock) {
                executionToken.mCancel = ((ExecutorImpl) RepeatableExecutorImpl.this.mExecutor).executeDelayed(executionToken, 0L, timeUnit);
            }
            this.cancel = new Runnable() { // from class: com.android.systemui.util.concurrency.RepeatableExecutorImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RepeatableExecutorImpl.ExecutionToken executionToken2 = RepeatableExecutorImpl.ExecutionToken.this;
                    synchronized (executionToken2.mLock) {
                        ExecutorImpl.ExecutionToken executionToken3 = executionToken2.mCancel;
                        if (executionToken3 != null) {
                            executionToken3.run();
                            executionToken2.mCancel = null;
                        }
                    }
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onSeekFalse$1
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
            this.detector.mImpl.mDetector.onTouchEvent(motionEvent);
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
