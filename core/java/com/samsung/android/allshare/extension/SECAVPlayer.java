package com.samsung.android.allshare.extension;

import android.content.Context;
import android.net.Uri;
import android.p009os.Handler;
import android.telecom.TelecomManager;
import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Icon;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.media.AVPlayer;
import com.samsung.android.allshare.media.ContentInfo;
import com.samsung.android.allshare.media.MediaInfo;
import com.samsung.android.allshare.media.PlaylistPlayer;
import com.sec.android.allshare.iface.message.AllShareEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes5.dex */
public class SECAVPlayer extends AVPlayer
    implements AVPlayer.IAVPlayerEventListener,
        AVPlayer.IAVPlayerExtensionEventListener,
        AVPlayer.IAVPlayerPlaybackResponseListener,
        AVPlayer.IAVPlayerExtensionResponseListener {
  private static final String TAG_CLASS = "SECAVPlayer";
  private AVPlayer mAVPlayer;
  private Handler mHandlerPlayInfo;
  private Handler mStopTimer;
  private int mVolumeDelta = 0;
  boolean mRequestVolume = false;
  boolean mChangeMute = false;
  boolean mRequestChangeMute = false;
  private State mState = new State();
  private Runnable mNotifyStopRunnable =
      new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.1
        @Override // java.lang.Runnable
        public void run() {
          DLog.i_api(
              "SECAVPLAYER", " mNotifyStopRunnable : " + SECAVPlayer.this.mState.currentState);
          if (SECAVPlayer.this.mState.currentState == SECAVPlayerState.STOPPED) {
            SECAVPlayer.this.notifyOnStop();
          }
        }
      };
  private ISECAVPlayerStateListener mSECLabListener = null;
  private ISECAVPlayerExtensionEventListener mSECExtensionListener = null;
  private boolean mIsPlayInfoThreadRunning = false;
  private Runnable mRunnablePlayInfo =
      new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.2
        @Override // java.lang.Runnable
        public void run() {
          SECAVPlayer.this.mAVPlayer.getPlayPosition();
          SECAVPlayer.this.mAVPlayer.getState();
          if (SECAVPlayer.this.mState.getMediaInfo() == null
              || SECAVPlayer.this.mState.getMediaInfo().getDuration() <= 0) {
            SECAVPlayer.this.mAVPlayer.getMediaInfo();
          }
          if (SECAVPlayer.this.mIsPlayInfoThreadRunning) {
            SECAVPlayer.this.mHandlerPlayInfo.postDelayed(
                SECAVPlayer.this.mRunnablePlayInfo, 1000L);
          }
        }
      };
  private boolean mIsSubscriberRequested = false;
  private AVPlayer.IAVPlayerPlaybackResponseListener mAVPlayerPlaybackResponseListener = null;
  private AVPlayer.IAVPlayerVolumeResponseListener mAVPlayerVolumeResponseListener = null;
  private AVPlayer.IAVPlayerExtensionResponseListener mAVPlayerExtensionResponseListener = null;
  private AVPlayer.IAVPlayerVolumeResponseListener mSECAvPlayerVolumeResponseListener =
      new AVPlayer
          .IAVPlayerVolumeResponseListener() { // from class:
                                               // com.samsung.android.allshare.extension.SECAVPlayer.4
        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onGetVolumeResponseReceived(int volume, ERROR error) {
          SECAVPlayer.this.mRequestVolume = false;
          if (error == ERROR.SUCCESS) {
            DLog.i_api("SECAVPLAYER", " onGetVolumeResponseReceived - " + volume);
            SECAVPlayer.this.setVolumeDelta(volume);
          } else {
            SECAVPlayer.this.mVolumeDelta = 0;
            DLog.i_api("SECAVPLAYER", " onGetVolumeResponseReceived - " + error);
          }
          if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
            SECAVPlayer.this.mAVPlayerVolumeResponseListener.onGetVolumeResponseReceived(
                volume, error);
          }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onSetVolumeResponseReceived(int volume, ERROR error) {
          if (error == ERROR.SUCCESS) {
            DLog.i_api("SECAVPLAYER", " onSetVolumeResponseReceived - " + volume);
          } else {
            DLog.i_api("SECAVPLAYER", " onSetVolumeResponseReceived - " + error);
          }
          if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
            SECAVPlayer.this.mAVPlayerVolumeResponseListener.onSetVolumeResponseReceived(
                volume, error);
          }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onGetMuteResponseReceived(boolean ret, ERROR error) {
          SECAVPlayer.this.mRequestChangeMute = false;
          if (error == ERROR.SUCCESS) {
            DLog.i_api("SECAVPLAYER", " onGetMuteResponseReceived - " + ret);
            if (SECAVPlayer.this.mChangeMute) {
              SECAVPlayer.this.mAVPlayer.setMute(!ret);
            }
          } else {
            DLog.i_api("SECAVPLAYER", " onGetMuteResponseReceived - " + error);
          }
          SECAVPlayer.this.mChangeMute = false;
          if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
            SECAVPlayer.this.mAVPlayerVolumeResponseListener.onGetMuteResponseReceived(ret, error);
          }
        }

        @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerVolumeResponseListener
        public void onSetMuteResponseReceived(boolean ret, ERROR error) {
          if (error == ERROR.SUCCESS) {
            DLog.i_api("SECAVPLAYER", " onSetMuteResponseReceived - " + ret);
          } else {
            DLog.i_api("SECAVPLAYER", " onSetMuteResponseReceived - " + error);
          }
          if (SECAVPlayer.this.mAVPlayerVolumeResponseListener != null) {
            SECAVPlayer.this.mAVPlayerVolumeResponseListener.onSetMuteResponseReceived(ret, error);
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
    public void setNewPlayState(long pos) {
      this.mLastPos = 0L;
      this.mMediaInfo = null;
      this.mItemDuration = 0L;
      this.mPlayRequested = false;
      this.mNearlyFinished.set(false);
      this.currentState = SECAVPlayerState.UNKNOWN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlayRequested(boolean flag) {
      this.mPlayRequested = flag;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPlayRequested() {
      return this.mPlayRequested;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNearlyFinished(boolean flag) {
      this.mNearlyFinished.set(flag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean compareAndSetNearlyFinished(boolean expected, boolean updated) {
      return this.mNearlyFinished.compareAndSet(expected, updated);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMediaInfo(MediaInfo info) {
      this.mMediaInfo = info;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLastPos(long pos) {
      if (pos > 0) {
        this.mLastPos = pos;
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
    public void setItemDuration(long duration) {
      this.mItemDuration = duration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getItemDuration() {
      return this.mItemDuration;
    }
  }

  public SECAVPlayer(AVPlayer player, Context context) {
    this.mAVPlayer = null;
    this.mStopTimer = null;
    this.mHandlerPlayInfo = null;
    this.mAVPlayer = player;
    player.setEventListener(this);
    this.mAVPlayer.setExtensionEventListener(this);
    this.mAVPlayer.setResponseListener(this);
    this.mAVPlayer.setExtensionResponseListener(this);
    this.mAVPlayer.setVolumeResponseListener(this.mSECAvPlayerVolumeResponseListener);
    this.mHandlerPlayInfo = new Handler(context.getMainLooper());
    this.mStopTimer = new Handler(context.getMainLooper());
  }

  private synchronized void guessMeaningOfStopState(ERROR error, boolean fromEvent) {
    int endingMargin;
    long curPos = this.mState.getLastPos();
    if (curPos == 0) {
      DLog.i_api("SECAVPLAYER", " STOP (not notified)");
      this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
      this.mStopTimer.postDelayed(this.mNotifyStopRunnable, 5000L);
      return;
    }
    MediaInfo mediaInfo = this.mState.getMediaInfo();
    long mediaDuration = this.mState.getItemDuration();
    if (mediaInfo != null && mediaInfo.getDuration() > 0) {
      mediaDuration = mediaInfo.getDuration();
    }
    if (mediaDuration <= 0) {
      DLog.i_api("SECAVPLAYER", " mediaDuration : " + mediaDuration);
      notifyOnStop();
      return;
    }
    if (mediaDuration > 30) {
      endingMargin = 10;
    } else {
      endingMargin = (int) (mediaDuration * 0.5d);
    }
    DLog.i_api("SECAVPLAYER", " mediaDuration : " + mediaDuration);
    DLog.i_api("SECAVPLAYER", " curPos : " + curPos);
    notifyOnStop();
    if (Math.abs(mediaDuration - curPos) <= endingMargin) {
      this.mState.setNearlyFinished(true);
      getPlayPosition();
    }
  }

  public void setSmartAVPlayerEventListener(ISECAVPlayerStateListener listener) {
    this.mSECLabListener = listener;
  }

  public void setSECAVPlayerExtensionEventListener(ISECAVPlayerExtensionEventListener listener) {
    this.mSECExtensionListener = listener;
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

  protected void notifyOnProgress(long pos) {
    ISECAVPlayerStateListener iSECAVPlayerStateListener = this.mSECLabListener;
    if (iSECAVPlayerStateListener != null) {
      iSECAVPlayerStateListener.onProgress(pos);
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

  protected void notifyOnError(ERROR e) {
    this.mState.currentState = SECAVPlayerState.UNKNOWN;
    this.mState.setPlayRequested(false);
    this.mState.setNearlyFinished(false);
    if (this.mSECLabListener != null) {
      DLog.i_api("SECAVPLAYER", " OnError");
      this.mSECLabListener.onError(e);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onGetPlayPositionResponseReceived(long position, ERROR err) {
    if (this.mAVPlayerPlaybackResponseListener == null
        || !this.mIsPlayInfoThreadRunning
        || err.equals(ERROR.INVALID_DEVICE)) {
      this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
      this.mIsPlayInfoThreadRunning = false;
    }
    long mediaDuration = this.mState.getItemDuration();
    MediaInfo mediaInfo = this.mState.getMediaInfo();
    if (mediaInfo != null && mediaInfo.getDuration() > 0) {
      mediaDuration = mediaInfo.getDuration();
    }
    if ((position == 0 || position == mediaDuration || position == mediaDuration - 1)
        && this.mState.compareAndSetNearlyFinished(true, false)) {
      this.mState.setLastPos(mediaDuration);
      notifyOnProgress(mediaDuration);
      DLog.i_api("SECAVPLAYER", " finish : " + position + " - " + mediaDuration);
      notifyOnFinish();
      return;
    }
    if (this.mState.getLastPos() == position || !err.equals(ERROR.SUCCESS)) {
      return;
    }
    this.mState.setLastPos(position);
    notifyOnProgress(position);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onSeekResponseReceived(long requestedPosition, ERROR err) {
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null) {
      iAVPlayerPlaybackResponseListener.onSeekResponseReceived(requestedPosition, err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onGetMediaInfoResponseReceived(MediaInfo mediaInfo, ERROR err) {
    if (err.equals(ERROR.SUCCESS)) {
      if (mediaInfo == null || mediaInfo.equals(this.mState.getMediaInfo())) {
        return;
      }
      if (mediaInfo.getDuration() > 0) {
        this.mState.setMediaInfo(mediaInfo);
      }
    }
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null && this.mIsSubscriberRequested) {
      this.mIsSubscriberRequested = false;
      iAVPlayerPlaybackResponseListener.onGetMediaInfoResponseReceived(mediaInfo, err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onPlayResponseReceived(Item ai, ContentInfo ci, ERROR err) {
    if (ERROR.SUCCESS.equals(err)) {
      if (this.mState.getMediaInfo() == null || this.mState.getMediaInfo().getDuration() <= 0) {
        this.mAVPlayer.getMediaInfo();
      }
    } else {
      this.mState.setPlayRequested(false);
    }
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null) {
      iAVPlayerPlaybackResponseListener.onPlayResponseReceived(ai, ci, err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onStopResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null) {
      iAVPlayerPlaybackResponseListener.onStopResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onResumeResponseReceived(ERROR err) {
    if (ERROR.SUCCESS.equals(err) && !this.mIsPlayInfoThreadRunning) {
      this.mHandlerPlayInfo.post(this.mRunnablePlayInfo);
      this.mIsPlayInfoThreadRunning = true;
    }
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null) {
      iAVPlayerPlaybackResponseListener.onResumeResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onPauseResponseReceived(ERROR err) {
    if (ERROR.SUCCESS.equals(err)) {
      this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
      this.mIsPlayInfoThreadRunning = false;
    }
    AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
        this.mAVPlayerPlaybackResponseListener;
    if (iAVPlayerPlaybackResponseListener != null) {
      iAVPlayerPlaybackResponseListener.onPauseResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerPlaybackResponseListener
  public void onGetStateResponseReceived(AVPlayer.AVPlayerState state, ERROR err) {
    if (err == ERROR.SUCCESS) {
      onDeviceChanged(state, err);
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
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
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
    new Handler()
        .postDelayed(
            new Runnable() { // from class: com.samsung.android.allshare.extension.SECAVPlayer.3
              @Override // java.lang.Runnable
              public void run() {
                if (SECAVPlayer.this.mIsSubscriberRequested) {
                  SECAVPlayer.this.mIsSubscriberRequested = false;
                  if (SECAVPlayer.this.mAVPlayerPlaybackResponseListener != null) {
                    SECAVPlayer.this.mAVPlayerPlaybackResponseListener
                        .onGetMediaInfoResponseReceived(null, ERROR.FAIL);
                  } else {
                    DLog.w_api(
                        SECAVPlayer.TAG_CLASS,
                        "getMediaInfo timeout over 3sec, but no way to response FAIL");
                  }
                }
              }
            },
            TelecomManager.VERY_SHORT_CALL_TIME_MS);
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
  public void play(Item ai, ContentInfo ci) {
    this.mHandlerPlayInfo.removeCallbacks(this.mRunnablePlayInfo);
    this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
    this.mIsPlayInfoThreadRunning = false;
    if (ai != null) {
      this.mState.setItemDuration(ai.getDuration());
    }
    if (ci == null) {
      this.mState.setNewPlayState(0L);
    } else {
      this.mState.setNewPlayState(ci.getStartingPosition());
    }
    if (getPlayerState().equals(AVPlayer.AVPlayerState.STOPPED)) {
      this.mState.currentState = SECAVPlayerState.STOPPED;
    }
    this.mState.setPlayRequested(true);
    this.mState.setNearlyFinished(false);
    DLog.i_api("SECAVPLAYER", " play");
    this.mAVPlayer.play(ai, ci);
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
  public void seek(long targetTime) {
    this.mAVPlayer.seek(targetTime);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setEventListener(AVPlayer.IAVPlayerEventListener l) {}

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setExtensionEventListener(AVPlayer.IAVPlayerExtensionEventListener l) {}

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setMute(boolean m) {
    this.mAVPlayer.setMute(m);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setResponseListener(AVPlayer.IAVPlayerPlaybackResponseListener l) {
    this.mAVPlayerPlaybackResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setVolumeResponseListener(AVPlayer.IAVPlayerVolumeResponseListener l) {
    this.mAVPlayerVolumeResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setExtensionResponseListener(AVPlayer.IAVPlayerExtensionResponseListener l) {
    this.mAVPlayerExtensionResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setVolume(int level) {
    this.mAVPlayer.setVolume(level);
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
  public void setVolumeDelta(int currentVolume) {
    int setVolume = this.mVolumeDelta + currentVolume;
    this.mVolumeDelta = 0;
    if (setVolume < 0) {
      setVolume = 0;
    } else if (setVolume > 100) {
      setVolume = 100;
    }
    if (currentVolume != setVolume) {
      DLog.i_api("SECAVPLAYER", " setVolumeDelta - " + setVolume);
      this.mAVPlayer.setVolume(setVolume);
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
      AVPlayer.AVPlayerState state = aVPlayer.getPlayerState();
      switch (C49015.f2955x3a716371[state.ordinal()]) {
        case 1:
          this.mState.currentState = SECAVPlayerState.STOPPED;
          break;
        case 2:
          this.mState.currentState = SECAVPlayerState.BUFFERING;
          break;
        case 3:
          this.mState.currentState = SECAVPlayerState.PLAYING;
          break;
        case 4:
          this.mState.currentState = SECAVPlayerState.PAUSE;
          break;
        case 5:
          break;
        default:
          this.mState.currentState = SECAVPlayerState.UNKNOWN;
          break;
      }
    }
  }

  /* renamed from: com.samsung.android.allshare.extension.SECAVPlayer$5 */
  static /* synthetic */ class C49015 {

    /* renamed from: $SwitchMap$com$samsung$android$allshare$media$AVPlayer$AVPlayerState */
    static final /* synthetic */ int[] f2955x3a716371;

    static {
      int[] iArr = new int[AVPlayer.AVPlayerState.values().length];
      f2955x3a716371 = iArr;
      try {
        iArr[AVPlayer.AVPlayerState.STOPPED.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f2955x3a716371[AVPlayer.AVPlayerState.BUFFERING.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        f2955x3a716371[AVPlayer.AVPlayerState.PLAYING.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        f2955x3a716371[AVPlayer.AVPlayerState.PAUSED.ordinal()] = 4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        f2955x3a716371[AVPlayer.AVPlayerState.CONTENT_CHANGED.ordinal()] = 5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        f2955x3a716371[AVPlayer.AVPlayerState.FINISHED.ordinal()] = 6;
      } catch (NoSuchFieldError e6) {
      }
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerEventListener
  public void onDeviceChanged(AVPlayer.AVPlayerState state, ERROR err) {
    this.mStopTimer.removeCallbacks(this.mNotifyStopRunnable);
    DLog.i_api("SECAVPLAYER", "onDeviceChanged: " + state);
    switch (C49015.f2955x3a716371[state.ordinal()]) {
      case 1:
        if (!err.equals(ERROR.SUCCESS)) {
          this.mState.currentState = SECAVPlayerState.STOPPED;
          notifyOnError(err);
        } else if (this.mState.isPlayRequested()) {
          guessMeaningOfStopState(err, true);
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
        long mediaDuration = this.mState.getItemDuration();
        MediaInfo mediaInfo = this.mState.getMediaInfo();
        if (mediaInfo != null && mediaInfo.getDuration() > 0) {
          mediaDuration = mediaInfo.getDuration();
        }
        if (this.mState.compareAndSetNearlyFinished(true, false)) {
          this.mState.setLastPos(mediaDuration);
          notifyOnProgress(mediaDuration);
          DLog.i_api("SECAVPLAYER", " FINISHED : " + mediaDuration);
          notifyOnFinish();
          break;
        }
        break;
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionEventListener
  public void onExtensionEvent(String eventName, String eventValue, ERROR err) {
    DLog.i_api("SECAVPLAYER", "onExtensionEvent: " + eventName);
    if (AllShareEvent.EVENT_RENDERER_ASPECT_RATIO.equals(eventName)) {
      DLog.i_api("SECAVPLAYER", "event onAspectRatio");
      this.mSECExtensionListener.onAspectRatio(eventValue, err);
      return;
    }
    List<Caption> list = new ArrayList<>();
    List<Caption> tempList = Caption.parseCaption(eventValue);
    if (tempList == null) {
      err = ERROR.FAIL;
    } else {
      for (Caption caption : tempList) {
        String captionUri = getCaptionFilePathFromURI(caption.getCaptionUri());
        if (captionUri != null && !captionUri.isEmpty()) {
          caption.setCaptionUri(captionUri);
        }
        list.add(caption);
        DLog.i_api(TAG_CLASS, "onExtensionEvent : [caption]" + caption.toString());
      }
    }
    if (AllShareEvent.EVENT_RENDERER_CAPTIONS.equals(eventName)) {
      DLog.i_api("SECAVPLAYER", "event onAvailableCaptions");
      this.mSECExtensionListener.onAvailableCaptions(list, err);
    } else if (AllShareEvent.EVENT_RENDERER_ENABLED_CAPTIONS.equals(eventName)) {
      DLog.i_api("SECAVPLAYER", "event onEnabledCaptions");
      this.mSECExtensionListener.onEnabledCaptions(list, err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void getState() {
    DLog.w_api(TAG_CLASS, "getState is not working(SECAVPlayer)");
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public PlaylistPlayer getPlaylistPlayer() {
    return this.mAVPlayer.getPlaylistPlayer();
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
  public void prepare(Item ai) {
    this.mAVPlayer.prepare(ai);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public boolean isSupportDynamicBuffering() {
    return this.mAVPlayer.isSupportDynamicBuffering();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void skipDynamicBuffering() {
    this.mAVPlayer.skipDynamicBuffering();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void SetWHAResponseListener(AVPlayer.IAVPlayerWHAResponseListener listener) {
    this.mAVPlayer.SetWHAResponseListener(listener);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void GetWHADeviceStatusInfo() {
    this.mAVPlayer.GetWHADeviceStatusInfo();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void CreateWHAParty() {
    this.mAVPlayer.CreateWHAParty();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void JoinWHAParty(String partyID) {
    this.mAVPlayer.JoinWHAParty(partyID);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void LeaveWHAParty() {
    this.mAVPlayer.LeaveWHAParty();
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
  public void requestMobileToTV(String ip, int port) {
    this.mAVPlayer.requestMobileToTV(ip, port);
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
  public String getProductCapInfo(Device.InformationType infoType) {
    return this.mAVPlayer.getProductCapInfo(infoType);
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingInfo(Device.InformationType infoType) {
    return this.mAVPlayer.getScreenSharingInfo(infoType);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setAspectRatio(String aspectRatio) {
    this.mAVPlayer.setAspectRatio(aspectRatio);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void requestAspectRatioState() {
    this.mAVPlayer.requestAspectRatioState();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void move360View(float latitudeOffset, float longitudeOffset) {
    this.mAVPlayer.move360View(latitudeOffset, longitudeOffset);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void zoom360View(float ScaleFactor) {
    this.mAVPlayer.zoom360View(ScaleFactor);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void reset360View() {
    this.mAVPlayer.reset360View();
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void controlCaption(Caption.CaptionOperation operation, Caption caption) {
    this.mAVPlayer.controlCaption(operation, caption);
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
  public String getCaptionFilePathFromURI(String uri) {
    return this.mAVPlayer.getCaptionFilePathFromURI(uri);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onSetAspectRatioResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onSetAspectRatioResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onAspectRatioStateResponseReceived(String aspectRatio, ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onAspectRatioStateResponseReceived(aspectRatio, err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onMove360ViewResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onMove360ViewResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onZoom360ViewResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onZoom360ViewResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onReset360ViewResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onReset360ViewResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onControlCaptionResponseReceived(ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onControlCaptionResponseReceived(err);
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer.IAVPlayerExtensionResponseListener
  public void onCaptionStateResponseReceived(
      List<Caption> availableCaptions, List<Caption> enabledCaptions, ERROR err) {
    AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
        this.mAVPlayerExtensionResponseListener;
    if (iAVPlayerExtensionResponseListener != null) {
      iAVPlayerExtensionResponseListener.onCaptionStateResponseReceived(
          availableCaptions, enabledCaptions, err);
    }
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isSupportedByType(int type) {
    return this.mAVPlayer.isSupportedByType(type);
  }
}
