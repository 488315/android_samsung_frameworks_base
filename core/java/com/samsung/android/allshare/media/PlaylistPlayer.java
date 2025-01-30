package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ERROR;

/* loaded from: classes5.dex */
public abstract class PlaylistPlayer {

  public interface IPlaylistPlayerEventListener {
    void onPlaylistPlayerStateChanged(PlayerState playerState, ERROR error);
  }

  public interface IPlaylistPlayerPlaybackResponseListener {
    void onGetPlayPositionResponseReceived(int i, ERROR error);

    void onNextResponseReceived(ERROR error);

    void onPauseResponseReceived(ERROR error);

    void onPlayResponseReceived(Playlist playlist, int i, ERROR error);

    void onPreviousResponseReceived(ERROR error);

    void onResumeResponseReceived(ERROR error);

    void onSeekResponseReceived(int i, ERROR error);

    void onStopResponseReceived(ERROR error);
  }

  public enum PlayerState {
    STOPPED,
    BUFFERING,
    PLAYING,
    PAUSED,
    CONTENT_CHANGED,
    UNKNOWN
  }

  public abstract void getPlayPosition();

  public abstract boolean isSeekable();

  public abstract boolean isSupportAudio();

  public abstract boolean isSupportImage();

  public abstract boolean isSupportNavigateInPause();

  public abstract boolean isSupportSetAutoFlipMode();

  public abstract boolean isSupportVideo();

  public abstract void next();

  public abstract void pause();

  public abstract void play(Playlist playlist, int i);

  public abstract void previous();

  public abstract void resume();

  public abstract void seek(int i);

  public abstract void setAutoFlipMode(boolean z);

  public abstract void setPlaybackResponseListener(
      IPlaylistPlayerPlaybackResponseListener iPlaylistPlayerPlaybackResponseListener);

  public abstract void setPlaylistPlayerEventListener(
      IPlaylistPlayerEventListener iPlaylistPlayerEventListener);

  public abstract void setQuickNavigate(boolean z);

  public abstract void stop();

  protected PlaylistPlayer() {}
}
