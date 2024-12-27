package com.samsung.android.sume.core.controller;

import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Request;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface MediaController<T> {

    public interface OnEventListener {
        void onEvent(Event event);
    }

    void release();

    T request(Request request);

    T run(List<MediaBuffer> list, List<MediaBuffer> list2);

    void setOnEventListener(OnEventListener onEventListener);

    default T run(final MediaBuffer mediabuffer) {
        return run(
                new ArrayList<
                        MediaBuffer>() { // from class:
                                         // com.samsung.android.sume.core.controller.MediaController.1
                    {
                        add(mediabuffer);
                    }
                },
                new ArrayList());
    }

    default T run(final MediaBuffer inBuffer, final MediaBuffer outBuffer) {
        return run(
                new ArrayList<
                        MediaBuffer>() { // from class:
                                         // com.samsung.android.sume.core.controller.MediaController.2
                    {
                        add(inBuffer);
                    }
                },
                new ArrayList<
                        MediaBuffer>() { // from class:
                                         // com.samsung.android.sume.core.controller.MediaController.3
                    {
                        add(outBuffer);
                    }
                });
    }

    default T run(List<MediaBuffer> inBuffers) {
        return run(inBuffers, new ArrayList());
    }
}
