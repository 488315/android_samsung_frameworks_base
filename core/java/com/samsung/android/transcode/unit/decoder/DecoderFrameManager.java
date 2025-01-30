package com.samsung.android.transcode.unit.decoder;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class DecoderFrameManager implements DecoderReleaseListener {
  private final ArrayList<DecodedFrame> decodedFrameQueue = new ArrayList<>();

  @Override // com.samsung.android.transcode.unit.decoder.DecoderReleaseListener
  public synchronized void notifyFrameDecoded(DecodedFrame decodedFrame) {
    if (decodedFrame != null) {
      this.decodedFrameQueue.add(decodedFrame);
    }
  }

  public synchronized DecodedFrame dequeueFrame() {
    return this.decodedFrameQueue.isEmpty() ? null : this.decodedFrameQueue.remove(0);
  }

  public synchronized boolean isDecodedFramesPrepared() {
    return !this.decodedFrameQueue.isEmpty();
  }

  public synchronized int queSize() {
    return this.decodedFrameQueue.isEmpty() ? 0 : this.decodedFrameQueue.size();
  }
}
