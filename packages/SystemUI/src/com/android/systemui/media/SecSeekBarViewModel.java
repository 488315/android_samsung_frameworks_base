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

/* loaded from: classes2.dex */
public final class SecSeekBarViewModel {
    public Progress _data = new Progress(false, false, false, false, null, 0, false);
    public final MutableLiveData _progress;
    public final RepeatableExecutor bgExecutor;
    public final SecSeekBarViewModel$callback$1 callback;
    public AnonymousClass1 cancel;
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
                        if (secSeekBarViewModel.scrubbing) {
                            if (j == seekBar.getMax() || j == 0) {
                                seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                            }
                            SecSeekBarViewModel secSeekBarViewModel2 = secSeekBarViewModel;
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
                    SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel, true);
                    secSeekBarViewModel.isFalseSeek = false;
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
                    SecSeekBarViewModel secSeekBarViewModel2 = secSeekBarViewModel;
                    if (secSeekBarViewModel2.isFalseSeek) {
                        SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel2, false);
                        secSeekBarViewModel.checkPlaybackPosition();
                        return;
                    }
                    MediaController mediaController = secSeekBarViewModel2.controller;
                    if (mediaController != null && (transportControls = mediaController.getTransportControls()) != null) {
                        transportControls.seekTo(progress);
                    }
                    SecSeekBarViewModel secSeekBarViewModel3 = secSeekBarViewModel;
                    secSeekBarViewModel3.playbackState = null;
                    SecSeekBarViewModel.access$setScrubbing(secSeekBarViewModel3, false);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$1, reason: invalid class name */
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
                Pair enabledStateAndDuration = this.this$0.getEnabledStateAndDuration(mediaMetadata);
                boolean zBooleanValue = ((Boolean) enabledStateAndDuration.component1()).booleanValue();
                int iIntValue = ((Number) enabledStateAndDuration.component2()).intValue();
                SecSeekBarViewModel secSeekBarViewModel = this.this$0;
                SecSeekBarViewModel.Progress progress = secSeekBarViewModel._data;
                if (progress.duration != iIntValue) {
                    secSeekBarViewModel.set_data(SecSeekBarViewModel.Progress.copy$default(progress, zBooleanValue, false, null, iIntValue, false, 94));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                PlaybackState playbackState2;
                this.this$0.playbackState = playbackState;
                if (playbackState != null) {
                    Integer num = 0;
                    if (!num.equals(this.this$0.playbackState)) {
                        this.this$0.checkIfPollingNeeded(true);
                        final SecSeekBarViewModel secSeekBarViewModel = this.this$0;
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
                                Long lValueOf = playbackState8 != null ? Long.valueOf(playbackState8.getPosition()) : null;
                                long position = playbackState3.getPosition();
                                PlaybackState playbackState9 = secSeekBarViewModel.lastState;
                                Integer numValueOf = playbackState9 != null ? Integer.valueOf(playbackState9.getState()) : null;
                                int state = playbackState3.getState();
                                PlaybackState playbackState10 = secSeekBarViewModel.lastState;
                                Long lValueOf2 = playbackState10 != null ? Long.valueOf(playbackState10.getLastPositionUpdateTime()) : null;
                                long lastPositionUpdateTime = playbackState3.getLastPositionUpdateTime();
                                PlaybackState playbackState11 = secSeekBarViewModel.lastState;
                                Float fValueOf = playbackState11 != null ? Float.valueOf(playbackState11.getPlaybackSpeed()) : null;
                                Log.d("CapsuleValue", "last position : " + lValueOf + ", after position : " + position + ", last state : " + numValueOf + ", after state : " + state + " last update : " + lValueOf2 + " after update : " + lastPositionUpdateTime + " last speed : " + fValueOf + " after speed : " + playbackState3.getPlaybackSpeed());
                                secSeekBarViewModel.lastState = playbackState3;
                                Runnable runnable = new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfCapsuleUpdateNeeded$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CoverMusicCapsuleController coverMusicCapsuleController = secSeekBarViewModel.coverMusicCapsuleController;
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
                                        OAMusicChipController oAMusicChipController = secSeekBarViewModel.oaMusicChipController;
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
                SecSeekBarViewModel secSeekBarViewModel2 = this.this$0;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$clearController$1(secSeekBarViewModel2));
            }

            @Override // android.media.session.MediaController.Callback
            public final void onSessionDestroyed() {
                SecSeekBarViewModel secSeekBarViewModel = this.this$0;
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
        int iHashCode = sessionToken != null ? sessionToken.hashCode() : 0;
        if (z2) {
            if (this.cancel == null) {
                Trace.beginAsyncSection("SeekBarPollingPosition", iHashCode);
                this.cancel = new AnonymousClass1(this.bgExecutor.executeRepeatedly(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$checkIfPollingNeeded$cancelPolling$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.$tmp0.checkPlaybackPosition();
                    }
                }, 0L, 100L), iHashCode);
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
        int i2 = (int) this.onSeekBarPreesedValue;
        if (i2 == 0) {
            set_data(Progress.copy$default(this._data, false, false, num, 0, false, 111));
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
            int iRound = (int) (Math.round(d) - height);
            int iRound2 = (int) (Math.round(d) + height);
            int iRound3 = Math.round(motionEvent.getX());
            boolean z = false;
            if (iRound <= iRound3 && iRound3 <= iRound2) {
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
                        SecSeekBarViewModel secSeekBarViewModel2 = secSeekBarViewModel;
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
