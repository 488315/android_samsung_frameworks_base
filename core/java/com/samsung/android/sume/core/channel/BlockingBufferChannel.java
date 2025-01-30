package com.samsung.android.sume.core.channel;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes4.dex */
public class BlockingBufferChannel implements BufferChannel {
  private static final String TAG = Def.tagOf((Class<?>) BlockingBufferChannel.class);
  private BlockingQueue<MediaBuffer> queue = new LinkedBlockingQueue();

  @Override // com.samsung.android.sume.core.channel.Channel
  public void send(MediaBuffer data) {
    try {
      Log.m94d(TAG, "send buffer[" + this.queue.size() + "]: " + data);
      this.queue.put(data);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.channel.Channel
  public MediaBuffer receive() {
    try {
      try {
        return this.queue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
        Log.m94d(
            TAG, "receive buffer[" + this.queue.size() + NavigationBarInflaterView.SIZE_MOD_END);
        return null;
      }
    } finally {
      Log.m94d(TAG, "receive buffer[" + this.queue.size() + NavigationBarInflaterView.SIZE_MOD_END);
    }
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public void close() {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public void cancel() {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public boolean isClosedForSend() {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override // com.samsung.android.sume.core.channel.Channel
  public boolean isClosedForReceive() {
    throw new UnsupportedOperationException("not implemented yet");
  }

  @Override // com.samsung.android.sume.core.channel.BufferChannel
  public void setCapacity(int capacity) {
    this.queue = new LinkedBlockingQueue(capacity);
  }
}
