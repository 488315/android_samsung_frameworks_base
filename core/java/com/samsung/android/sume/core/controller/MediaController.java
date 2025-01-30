package com.samsung.android.sume.core.controller;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Request;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface MediaController<T> {

  public interface OnEventListener {
    void onEvent(Event event);
  }

  void release();

  T request(Request request);

  T run(List<MediaBuffer> list, List<MediaBuffer> list2);

  void setOnEventListener(OnEventListener onEventListener);

  default T run(MediaBuffer mediabuffer) {
    return run(
        new ArrayList<MediaBuffer>(
            mediabuffer) { // from class: com.samsung.android.sume.core.controller.MediaController.1
          final /* synthetic */ MediaBuffer val$mediabuffer;

          {
            this.val$mediabuffer = mediabuffer;
            add(mediabuffer);
          }
        },
        new ArrayList());
  }

  default T run(MediaBuffer inBuffer, MediaBuffer outBuffer) {
    return run(
        new ArrayList<MediaBuffer>(
            inBuffer) { // from class: com.samsung.android.sume.core.controller.MediaController.2
          final /* synthetic */ MediaBuffer val$inBuffer;

          {
            this.val$inBuffer = inBuffer;
            add(inBuffer);
          }
        },
        new ArrayList<MediaBuffer>(
            outBuffer) { // from class: com.samsung.android.sume.core.controller.MediaController.3
          final /* synthetic */ MediaBuffer val$outBuffer;

          {
            this.val$outBuffer = outBuffer;
            add(outBuffer);
          }
        });
  }

  default T run(List<MediaBuffer> inBuffers) {
    return run(inBuffers, new ArrayList());
  }
}
