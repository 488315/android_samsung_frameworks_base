package com.samsung.android.allshare.extension;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Icon;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.media.AVPlayer;
import com.samsung.android.allshare.media.ContentInfo;
import com.samsung.android.allshare.media.MediaInfo;
import com.sec.android.allshare.iface.message.AllShareEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class SECAVPlayer extends AVPlayer implements AVPlayer.IAVPlayerEventListener, AVPlayer.IAVPlayerExtensionEventListener, AVPlayer.IAVPlayerPlaybackResponseListener, AVPlayer.IAVPlayerExtensionResponseListener {
    private static final String TAG_CLASS = "SECAVPlayer";
    private AVPlayer mAVPlayer;
    private Handler mHandlerPlayInfo;
    private Handler mStopTimer;
    private int mVolumeDelta = 0;
    boolean mRequestVolume = false;
    boolean mChangeMute = false;
    boolean mRequestChangeMute = false;
    private State mState = new State();
    private Runnable mNotifyStopRunnable = new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.1
        @Override // java.lang.Runnable
        public void run() {
            DLog.i_api("SECAVPLAYER", " mNotifyStopRunnable : " + SECAVPlayer.this.mState.currentState);
            if (SECAVPlayer.this.mState.currentState == SECAVPlayerState.STOPPED) {
                SECAVPlayer.this.notifyOnStop();
            }
        }
    };
    private ISECAVPlayerStateListener mSECLabListener = null;
    private ISECAVPlayerExtensionEventListener mSECExtensionListener = null;
    private boolean mIsPlayInfoThreadRunning = false;
    private Runnable mRunnablePlayInfo = new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.2
        @Override // java.lang.Runnable
        public void run() {
            SECAVPlayer.this.mAVPlayer.getPlayPosition();
            SECAVPlayer.this.mAVPlayer.getState();
            if (SECAVPlayer.this.mState.getMediaInfo() == null || SECAVPlayer.this.mState.getMediaInfo().getDuration() <= 0) {
                SECAVPlayer.this.mAVPlayer.getMediaInfo();
            }
            if (SECAVPlayer.this.mIsPlayInfoThreadRunning) {
                SECAVPlayer.this.mHandlerPlayInfo.postDelayed(SECAVPlayer.this.mRunnablePlayInfo, 1000L);
            }
        }
    };
    private boolean mIsSubscriberRequested = false;
    private AVPlayer.IAVPlayerPlaybackResponseListener mAVPlayerPlaybackResponseListener = null;
    private AVPlayer.IAVPlayerVolumeResponseListener mAVPlayerVolumeResponseListener = null;
    private AVPlayer.IAVPlayerExtensionResponseListener mAVPlayerExtensionResponseListener = null;
    private AVPlayer.IAVPlayerVolumeResponseListener mSECAvPlayerVolumeResponseListener = new AVPlayer.IAVPlayerVolumeResponseListener() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.4
        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onGetVolumeResponseReceived(int i, ERROR error) {
            SECAVPlayer.this.mRequestVolume = false;
            if (error == ERROR.SUCCESS) {
                DLog.i_api("SECAVPLAYER", " onGetVolumeResponseReceived - " + i);
                SECAVPlayer.this.setVolumeDelta(i);
            } else {
                SECAVPlayer.this.mVolumeDelta = 0;
                DLog.i_api("SECAVPLAYER", " onGetVolumeResponseReceived - " + error);
            }
            if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
                SECAVPlayer.this.mAVPlayerVolumeResponseListener.onGetVolumeResponseReceived(i, error);
            }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onSetVolumeResponseReceived(int i, ERROR error) {
            if (error == ERROR.SUCCESS) {
                DLog.i_api("SECAVPLAYER", " onSetVolumeResponseReceived - " + i);
            } else {
                DLog.i_api("SECAVPLAYER", " onSetVolumeResponseReceived - " + error);
            }
            if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
                SECAVPlayer.this.mAVPlayerVolumeResponseListener.onSetVolumeResponseReceived(i, error);
            }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onGetMuteResponseReceived(boolean z, ERROR error) {
            SECAVPlayer.this.mRequestChangeMute = false;
            if (error == ERROR.SUCCESS) {
                DLog.i_api("SECAVPLAYER", " onGetMuteResponseReceived - " + z);
                if (SECAVPlayer.this.mChangeMute) {
                    SECAVPlayer.this.mAVPlayer.setMute(!z);
                }
            } else {
                DLog.i_api("SECAVPLAYER", " onGetMuteResponseReceived - " + error);
            }
            SECAVPlayer.this.mChangeMute = false;
            if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
                SECAVPlayer.this.mAVPlayerVolumeResponseListener.onGetMuteResponseReceived(z, error);
            }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onSetMuteResponseReceived(boolean z, ERROR error) {
            if (error == ERROR.SUCCESS) {
                DLog.i_api("SECAVPLAYER", " onSetMuteResponseReceived - " + z);
            } else {
                DLog.i_api("SECAVPLAYER", " onSetMuteResponseReceived - " + error);
            }
            if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
                SECAVPlayer.this.mAVPlayerVolumeResponseListener.onSetMuteResponseReceived(z, error);
            }
        }
    };

    public interface ISECAVPlayerExtensionEventListener {
        void onAspectRatio(String str, ERROR error);

        void onAvailableCaptions(List<Caption> list, ERROR error);

        void onEnabledCaptions(List<Caption> list, ERROR error);
    }

    public interface ISECAVPlayerStateListener {
        void onBuffering();

        void onError(ERROR error);

        void onFinish();

        void onPause();

        void onPlay();

        void onProgress(long j);

        void onStop();
    }

    private enum SECAVPlayerState {
        STOPPED,
        FINISHED,
        BUFFERING,
        PLAYING,
        PAUSE,
        UNKNOWN
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setEventListener(AVPlayer.IAVPlayerEventListener iAVPlayerEventListener) {
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setExtensionEventListener(AVPlayer.IAVPlayerExtensionEventListener iAVPlayerExtensionEventListener) {
    }

    private static class State {
        private SECAVPlayerState currentState;
        private long mItemDuration;
        private long mLastPos;
        private MediaInfo mMediaInfo;
        private AtomicBoolean mNearlyFinished;
        private boolean mPlayRequested;

        private State() {
            this.mMediaInfo = null;
            this.mLastPos = -1L;
            this.mPlayRequested = false;
            this.mNearlyFinished = new AtomicBoolean(false);
            this.mItemDuration = 0L;
            this.currentState = SECAVPlayerState.UNKNOWN;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNewPlayState(long j) {
            this.mLastPos = 0L;
            this.mMediaInfo = null;
            this.mItemDuration = 0L;
            this.mPlayRequested = false;
            this.mNearlyFinished.set(false);
            this.currentState = SECAVPlayerState.UNKNOWN;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPlayRequested(boolean z) {
            this.mPlayRequested = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPlayRequested() {
            return this.mPlayRequested;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNearlyFinished(boolean z) {
            this.mNearlyFinished.set(z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean compareAndSetNearlyFinished(boolean z, boolean z2) {
            return this.mNearlyFinished.compareAndSet(z, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMediaInfo(MediaInfo mediaInfo) {
            this.mMediaInfo = mediaInfo;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLastPos(long j) {
            if (j > 0) {
                this.mLastPos = j;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getLastPos() {
            return this.mLastPos;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MediaInfo getMediaInfo() {
            return this.mMediaInfo;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setItemDuration(long j) {
            this.mItemDuration = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getItemDuration() {
            return this.mItemDuration;
        }
    }

    public SECAVPlayer(AVPlayer aVPlayer, Context context) {
        this.mAVPlayer = null;
        this.mStopTimer = null;
        this.mHandlerPlayInfo = null;
        this.mAVPlayer = aVPlayer;
        aVPlayer.setEventListener(this);
        this.mAVPlayer.setExtensionEventListener(this);
        this.mAVPlayer.setResponseListener(this);
        this.mAVPlayer.setExtensionResponseListener(this);
        this.mAVPlayer.setVolumeResponseListener(this.mSECAvPlayerVolumeResponseListener);
        this.mHandlerPlayInfo = new Handler(context.getMainLooper());
        this.mStopTimer = new Handler(context.getMainLooper());
    }

    private synchronized void guessMeaningOfStopState(ERROR error, boolean z) {
        long lastPos = this.mState.getLastPos();
        if (lastPos == 0) {
            DLog.i_api("SECAVPLAYER", " STOP (not notified)");
            this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
            this.mStopTimer.postDelayed(this.mNotifyStopRunnable, 5000L);
            return;
        }
        MediaInfo mediaInfo = this.mState.getMediaInfo();
        long itemDuration = this.mState.getItemDuration();
        if (mediaInfo != null && mediaInfo.getDuration() > 0) {
            itemDuration = mediaInfo.getDuration();
        }
        if (itemDuration <= 0) {
            DLog.i_api("SECAVPLAYER", " mediaDuration : " + itemDuration);
            notifyOnStop();
            return;
        }
        int i = itemDuration > 30 ? 10 : (int) (itemDuration * 0.5d);
        DLog.i_api("SECAVPLAYER", " mediaDuration : " + itemDuration);
        DLog.i_api("SECAVPLAYER", " curPos : " + lastPos);
        notifyOnStop();
        if (Math.abs(itemDuration - lastPos) <= i) {
            this.mState.setNearlyFinished(true);
            getPlayPosition();
        }
    }

    public void setSmartAVPlayerEventListener(ISECAVPlayerStateListener iSECAVPlayerStateListener) {
        this.mSECLabListener = iSECAVPlayerStateListener;
    }

    public void setSECAVPlayerExtensionEventListener(ISECAVPlayerExtensionEventListener iSECAVPlayerExtensionEventListener) {
        this.mSECExtensionListener = iSECAVPlayerExtensionEventListener;
    }

    public long getLastReceivedPlayPosition() {
        return this.mState.getLastPos();
    }

    public MediaInfo getLastReceivedMediaInfo() {
        return this.mState.getMediaInfo();
    }

    protected void notifyOnBuffering() {
        this.mState.currentState = SECAVPlayerState.BUFFERING;
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnBuffering");
            this.mSECLabListener.onBuffering();
        }
    }

    protected void notifyOnPlay() {
        this.mState.currentState = SECAVPlayerState.PLAYING;
        this.mState.setNearlyFinished(false);
        if (!this.mIsPlayInfoThreadRunning) {
            this.mHandlerPlayInfo.post(this.mRunnablePlayInfo);
            this.mIsPlayInfoThreadRunning = true;
        }
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnPlay");
            this.mSECLabListener.onPlay();
        }
    }

    protected void notifyOnProgress(long j) {
        ISECAVPlayerStateListener iSECAVPlayerStateListener = this.mSECLabListener;
        if (iSECAVPlayerStateListener != null) {
            iSECAVPlayerStateListener.onProgress(j);
        }
    }

    protected void notifyOnPause() {
        this.mState.currentState = SECAVPlayerState.PAUSE;
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnPause");
            this.mSECLabListener.onPause();
        }
    }

    protected void notifyOnStop() {
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnStop");
            this.mSECLabListener.onStop();
        }
    }

    protected void notifyOnFinish() {
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnFinish");
            this.mSECLabListener.onFinish();
        }
    }

    protected void notifyOnError(ERROR error) {
        this.mState.currentState = SECAVPlayerState.UNKNOWN;
        this.mState.setPlayRequested(false);
        this.mState.setNearlyFinished(false);
        if (this.mSECLabListener != null) {
            DLog.i_api("SECAVPLAYER", " OnError");
            this.mSECLabListener.onError(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onGetPlayPositionResponseReceived(long j, ERROR error) {
        if (this.mAVPlayerPlaybackResponseListener == null || !this.mIsPlayInfoThreadRunning || error.equals(ERROR.INVALID_DEVICE)) {
            this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
            this.mIsPlayInfoThreadRunning = false;
        }
        long itemDuration = this.mState.getItemDuration();
        MediaInfo mediaInfo = this.mState.getMediaInfo();
        if (mediaInfo != null && mediaInfo.getDuration() > 0) {
            itemDuration = mediaInfo.getDuration();
        }
        if ((j == 0 || j == itemDuration || j == itemDuration - 1) && this.mState.compareAndSetNearlyFinished(true, false)) {
            this.mState.setLastPos(itemDuration);
            notifyOnProgress(itemDuration);
            DLog.i_api("SECAVPLAYER", " finish : " + j + " - " + itemDuration);
            notifyOnFinish();
            return;
        }
        if (this.mState.getLastPos() != j && error.equals(ERROR.SUCCESS)) {
            this.mState.setLastPos(j);
            notifyOnProgress(j);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onSeekResponseReceived(long j, ERROR error) {
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener != null) {
            iAVPlayerPlaybackResponseListener.onSeekResponseReceived(j, error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onGetMediaInfoResponseReceived(MediaInfo mediaInfo, ERROR error) {
        if (error.equals(ERROR.SUCCESS)) {
            if (mediaInfo == null || mediaInfo.equals(this.mState.getMediaInfo())) {
                return;
            }
            if (mediaInfo.getDuration() > 0) {
                this.mState.setMediaInfo(mediaInfo);
            }
        }
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener == null || !this.mIsSubscriberRequested) {
            return;
        }
        this.mIsSubscriberRequested = false;
        iAVPlayerPlaybackResponseListener.onGetMediaInfoResponseReceived(mediaInfo, error);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onPlayResponseReceived(Item item, ContentInfo contentInfo, ERROR error) {
        if (ERROR.SUCCESS.equals(error)) {
            if (this.mState.getMediaInfo() == null || this.mState.getMediaInfo().getDuration() <= 0) {
                this.mAVPlayer.getMediaInfo();
            }
        } else {
            this.mState.setPlayRequested(false);
        }
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener != null) {
            iAVPlayerPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onStopResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener != null) {
            iAVPlayerPlaybackResponseListener.onStopResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onResumeResponseReceived(ERROR error) {
        if (ERROR.SUCCESS.equals(error) && !this.mIsPlayInfoThreadRunning) {
            this.mHandlerPlayInfo.post(this.mRunnablePlayInfo);
            this.mIsPlayInfoThreadRunning = true;
        }
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener != null) {
            iAVPlayerPlaybackResponseListener.onResumeResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onPauseResponseReceived(ERROR error) {
        if (ERROR.SUCCESS.equals(error)) {
            this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
            this.mIsPlayInfoThreadRunning = false;
        }
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlayerPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener != null) {
            iAVPlayerPlaybackResponseListener.onPauseResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
    public void onGetStateResponseReceived(AVPlayer.AVPlayerState aVPlayerState, ERROR error) {
        if (error == ERROR.SUCCESS) {
            onDeviceChanged(aVPlayerState, error);
        }
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        return this.mAVPlayer.getDeviceDomain();
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        return this.mAVPlayer.getDeviceType();
    }

    @Override // com.samsung.android.allshare.Device
    public String getID() {
        return this.mAVPlayer.getID();
    }

    @Override // com.samsung.android.allshare.Device
    public String getIPAddress() {
        return this.mAVPlayer.getIPAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public Uri getIcon() {
        return this.mAVPlayer.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        AVPlayer aVPlayer = this.mAVPlayer;
        if (aVPlayer == null) {
            return new ArrayList<>();
        }
        return aVPlayer.getIconList();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getMediaInfo() {
        this.mIsSubscriberRequested = true;
        this.mAVPlayer.getMediaInfo();
        new Handler().postDelayed(new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.3
            @Override // java.lang.Runnable
            public void run() {
                if (SECAVPlayer.this.mIsSubscriberRequested) {
                    SECAVPlayer.this.mIsSubscriberRequested = false;
                    if (SECAVPlayer.this.mAVPlayerPlaybackResponseListener != null) {
                        SECAVPlayer.this.mAVPlayerPlaybackResponseListener.onGetMediaInfoResponseReceived(null, ERROR.FAIL);
                    } else {
                        DLog.w_api(SECAVPlayer.TAG_CLASS, "getMediaInfo timeout over 3sec, but no way to response FAIL");
                    }
                }
            }
        }, 3000L);
    }

    @Override // com.samsung.android.allshare.Device
    public String getModelName() {
        return this.mAVPlayer.getModelName();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getMute() {
        this.mAVPlayer.getMute();
    }

    @Override // com.samsung.android.allshare.Device
    public String getName() {
        return this.mAVPlayer.getName();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getPlayPosition() {
        DLog.i_api("SECAVPLAYER", "@@@getPlayPosition");
        this.mAVPlayer.getPlayPosition();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public AVPlayer.AVPlayerState getPlayerState() {
        return this.mAVPlayer.getPlayerState();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getVolume() {
        this.mAVPlayer.getVolume();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportAudio() {
        return this.mAVPlayer.isSupportAudio();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportVideo() {
        return this.mAVPlayer.isSupportVideo();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void play(Item item, ContentInfo contentInfo) {
        this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
        this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
        this.mIsPlayInfoThreadRunning = false;
        if (item != null) {
            this.mState.setItemDuration(item.getDuration());
        }
        if (contentInfo == null) {
            this.mState.setNewPlayState(0L);
        } else {
            this.mState.setNewPlayState(contentInfo.getStartingPosition());
        }
        if (getPlayerState().equals(AVPlayer.AVPlayerState.STOPPED)) {
            this.mState.currentState = SECAVPlayerState.STOPPED;
        }
        this.mState.setPlayRequested(true);
        this.mState.setNearlyFinished(false);
        DLog.i_api("SECAVPLAYER", " play");
        this.mAVPlayer.play(item, contentInfo);
        notifyOnProgress(0L);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void pause() {
        this.mAVPlayer.pause();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void resume() {
        this.mAVPlayer.resume();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void seek(long j) {
        this.mAVPlayer.seek(j);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setMute(boolean z) {
        this.mAVPlayer.setMute(z);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setResponseListener(AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener) {
        this.mAVPlayerPlaybackResponseListener = iAVPlayerPlaybackResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setVolumeResponseListener(AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener) {
        this.mAVPlayerVolumeResponseListener = iAVPlayerVolumeResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setExtensionResponseListener(AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener) {
        this.mAVPlayerExtensionResponseListener = iAVPlayerExtensionResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setVolume(int i) {
        this.mAVPlayer.setVolume(i);
    }

    public void volumeUp() {
        DLog.i_api("SECAVPLAYER", " volumeUp");
        this.mVolumeDelta++;
        if (!this.mRequestVolume) {
            this.mRequestVolume = true;
            this.mAVPlayer.getVolume();
        }
    }

    public void volumeDown() {
        DLog.i_api("SECAVPLAYER", " volumeDown");
        this.mVolumeDelta--;
        if (!this.mRequestVolume) {
            this.mRequestVolume = true;
            this.mAVPlayer.getVolume();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0008 A[PHI: r1
      0x0008: PHI (r1v4 int) = (r1v0 int), (r1v1 int) binds: [B:3:0x0006, B:6:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVolumeDelta(int i) {
        int i2 = this.mVolumeDelta + i;
        int i3 = 0;
        this.mVolumeDelta = 0;
        if (i2 < 0) {
            i2 = i3;
        } else {
            i3 = 100;
            if (i2 > 100) {
            }
        }
        if (i != i2) {
            DLog.i_api("SECAVPLAYER", " setVolumeDelta - " + i2);
            this.mAVPlayer.setVolume(i2);
        }
    }

    public void changeMute() {
        DLog.i_api("SECAVPLAYER", " changeMute");
        this.mChangeMute = !this.mChangeMute;
        if (!this.mRequestChangeMute) {
            this.mRequestChangeMute = true;
            this.mAVPlayer.getMute();
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void stop() {
        this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
        this.mIsPlayInfoThreadRunning = false;
        this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
        this.mState.setPlayRequested(false);
        DLog.i_api("SECAVPLAYER", " stop");
        this.mAVPlayer.stop();
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        return this.mAVPlayer.getNIC();
    }

    private void updateCurrentStatus() {
        AVPlayer aVPlayer = this.mAVPlayer;
        if (aVPlayer != null) {
            int i = AnonymousClass5.$SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[aVPlayer.getPlayerState().ordinal()];
            if (i == 1) {
                this.mState.currentState = SECAVPlayerState.STOPPED;
                return;
            }
            if (i == 2) {
                this.mState.currentState = SECAVPlayerState.BUFFERING;
                return;
            }
            if (i == 3) {
                this.mState.currentState = SECAVPlayerState.PLAYING;
            } else if (i == 4) {
                this.mState.currentState = SECAVPlayerState.PAUSE;
            } else if (i != 5) {
                this.mState.currentState = SECAVPlayerState.UNKNOWN;
            }
        }
    }

    /* renamed from: com.samsung.android.allshare.extension.SECAVPlayer$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState;

        static {
            int[] iArr = new int[AVPlayer.AVPlayerState.values().length];
            $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState = iArr;
            try {
                iArr[AVPlayer.AVPlayerState.STOPPED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[AVPlayer.AVPlayerState.BUFFERING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[AVPlayer.AVPlayerState.PLAYING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[AVPlayer.AVPlayerState.PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[AVPlayer.AVPlayerState.CONTENT_CHANGED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[AVPlayer.AVPlayerState.FINISHED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerEventListener
    public void onDeviceChanged(AVPlayer.AVPlayerState aVPlayerState, ERROR error) {
        this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
        DLog.i_api("SECAVPLAYER", "onDeviceChanged: " + aVPlayerState);
        switch (AnonymousClass5.$SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState[aVPlayerState.ordinal()]) {
            case 1:
                if (!error.equals(ERROR.SUCCESS)) {
                    this.mState.currentState = SECAVPlayerState.STOPPED;
                    notifyOnError(error);
                } else if (this.mState.isPlayRequested()) {
                    guessMeaningOfStopState(error, true);
                    this.mState.currentState = SECAVPlayerState.STOPPED;
                } else {
                    notifyOnStop();
                }
                this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
                this.mIsPlayInfoThreadRunning = false;
                break;
            case 2:
                if (this.mState.currentState != SECAVPlayerState.UNKNOWN) {
                    notifyOnBuffering();
                    break;
                }
                break;
            case 3:
                if (this.mState.isPlayRequested()) {
                    notifyOnPlay();
                    break;
                }
                break;
            case 4:
                if (this.mState.currentState != SECAVPlayerState.UNKNOWN) {
                    notifyOnPause();
                    break;
                }
                break;
            case 5:
                if (this.mState.isPlayRequested()) {
                    notifyOnError(ERROR.FAIL);
                    this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
                    this.mIsPlayInfoThreadRunning = false;
                    break;
                }
                break;
            case 6:
                long itemDuration = this.mState.getItemDuration();
                MediaInfo mediaInfo = this.mState.getMediaInfo();
                if (mediaInfo != null && mediaInfo.getDuration() > 0) {
                    itemDuration = mediaInfo.getDuration();
                }
                if (this.mState.compareAndSetNearlyFinished(true, false)) {
                    this.mState.setLastPos(itemDuration);
                    notifyOnProgress(itemDuration);
                    DLog.i_api("SECAVPLAYER", " FINISHED : " + itemDuration);
                    notifyOnFinish();
                    break;
                }
                break;
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionEventListener
    public void onExtensionEvent(String str, String str2, ERROR error) throws XmlPullParserException, IOException {
        DLog.i_api("SECAVPLAYER", "onExtensionEvent: " + str);
        if (AllShareEvent.EVENT_RENDERER_ASPECT_RATIO.equals(str)) {
            DLog.i_api("SECAVPLAYER", "event onAspectRatio");
            this.mSECExtensionListener.onAspectRatio(str2, error);
            return;
        }
        ArrayList arrayList = new ArrayList();
        List<Caption> caption = Caption.parseCaption(str2);
        if (caption == null) {
            error = ERROR.FAIL;
        } else {
            for (Caption caption2 : caption) {
                String captionFilePathFromURI = getCaptionFilePathFromURI(caption2.getCaptionUri());
                if (captionFilePathFromURI != null && !captionFilePathFromURI.isEmpty()) {
                    caption2.setCaptionUri(captionFilePathFromURI);
                }
                arrayList.add(caption2);
                DLog.i_api(TAG_CLASS, "onExtensionEvent : [caption]" + caption2.toString());
            }
        }
        if (AllShareEvent.EVENT_RENDERER_CAPTIONS.equals(str)) {
            DLog.i_api("SECAVPLAYER", "event onAvailableCaptions");
            this.mSECExtensionListener.onAvailableCaptions(arrayList, error);
        } else if (AllShareEvent.EVENT_RENDERER_ENABLED_CAPTIONS.equals(str)) {
            DLog.i_api("SECAVPLAYER", "event onEnabledCaptions");
            this.mSECExtensionListener.onEnabledCaptions(arrayList, error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getState() {
        DLog.w_api(TAG_CLASS, "getState is not working(SECAVPlayer)");
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportRedirect() {
        return this.mAVPlayer.isSupportRedirect();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    @Deprecated
    public boolean isRedirectSupportable() {
        return this.mAVPlayer.isSupportRedirect();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void prepare(Item item) {
        this.mAVPlayer.prepare(item);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportDynamicBuffering() {
        return this.mAVPlayer.isSupportDynamicBuffering();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void skipDynamicBuffering() {
        this.mAVPlayer.skipDynamicBuffering();
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        return this.mAVPlayer.isSeekableOnPaused();
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        return this.mAVPlayer.isWholeHomeAudio();
    }

    @Override // com.samsung.android.allshare.Device
    public String getP2pMacAddress() {
        return this.mAVPlayer.getP2pMacAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        return this.mAVPlayer.getScreenSharingInfo();
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String str, int i) {
        this.mAVPlayer.requestMobileToTV(str, i);
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        return this.mAVPlayer.getSecProductP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType informationType) {
        return this.mAVPlayer.getProductCapInfo(informationType);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType informationType) {
        return this.mAVPlayer.getScreenSharingInfo(informationType);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setAspectRatio(String str) {
        this.mAVPlayer.setAspectRatio(str);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void requestAspectRatioState() {
        this.mAVPlayer.requestAspectRatioState();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void move360View(float f, float f2) {
        this.mAVPlayer.move360View(f, f2);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void zoom360View(float f) {
        this.mAVPlayer.zoom360View(f);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void reset360View() {
        this.mAVPlayer.reset360View();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void controlCaption(Caption.CaptionOperation captionOperation, Caption caption) {
        this.mAVPlayer.controlCaption(captionOperation, caption);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void requestCaptionState() {
        this.mAVPlayer.requestCaptionState();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportAspectRatio() {
        return this.mAVPlayer.isSupportAspectRatio();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupport360View() {
        return this.mAVPlayer.isSupport360View();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportCaptionControl() {
        return this.mAVPlayer.isSupportCaptionControl();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public String getCaptionFilePathFromURI(String str) {
        return this.mAVPlayer.getCaptionFilePathFromURI(str);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onSetAspectRatioResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onSetAspectRatioResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onAspectRatioStateResponseReceived(String str, ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onAspectRatioStateResponseReceived(str, error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onMove360ViewResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onMove360ViewResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onZoom360ViewResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onZoom360ViewResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onReset360ViewResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onReset360ViewResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onControlCaptionResponseReceived(ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onControlCaptionResponseReceived(error);
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
    public void onCaptionStateResponseReceived(List<Caption> list, List<Caption> list2, ERROR error) {
        AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
        if (iAVPlayerExtensionResponseListener != null) {
            iAVPlayerExtensionResponseListener.onCaptionStateResponseReceived(list, list2, error);
        }
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int i) {
        return this.mAVPlayer.isSupportedByType(i);
    }
}
