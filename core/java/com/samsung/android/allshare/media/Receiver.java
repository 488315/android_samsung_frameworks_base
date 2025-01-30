package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ERROR;
import com.samsung.android.allshare.Item;

/* loaded from: classes5.dex */
public abstract class Receiver {

  public interface IProgressUpdateEventListener {
    void onCompleted(Item item, ERROR error);

    void onProgressUpdated(long j, long j2, Item item, ERROR error);
  }

  public interface IReceiverResponseListener {
    void onCancelResponseReceived(Item item, ERROR error);

    void onReceiveResponseReceived(Item item, ERROR error);
  }

  public abstract void cancel(Item item);

  public abstract void receive(Item item);

  public abstract void setProgressUpdateEventListener(
      IProgressUpdateEventListener iProgressUpdateEventListener);

  public abstract void setReceiverResponseListener(
      IReceiverResponseListener iReceiverResponseListener);

  protected Receiver() {}
}
