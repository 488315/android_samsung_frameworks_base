package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class SlideShowPlayer {

  public interface ISlideShowPlayerEventListener {
    void onSlideShowPlayerStateChanged(
        SlideShowPlayerState slideShowPlayerState, int i, ERROR error);
  }

  public interface ISlideShowPlayerResponseListener {
    void onSetBGMListResponseReceived(ArrayList<Item> arrayList, ERROR error);

    void onSetBGMVolumeResponseReceived(int i, ERROR error);

    void onSetListResponseReceived(String str, ArrayList<Item> arrayList, ERROR error);

    void onStartResponseReceived(int i, ERROR error);

    void onStopResponseReceived(ERROR error);
  }

  public enum SlideShowPlayerState {
    STOPPED,
    BUFFERING,
    PLAYING,
    UNKNOWN
  }

  public abstract void setBGMList(ArrayList<Item> arrayList);

  public abstract void setBGMVolume(int i);

  public abstract void setEventListener(
      ISlideShowPlayerEventListener iSlideShowPlayerEventListener);

  public abstract void setList(String str, ArrayList<Item> arrayList);

  public abstract void setResponseListener(
      ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener);

  public abstract void start(int i);

  public abstract void stop();

  protected SlideShowPlayer() {}
}
