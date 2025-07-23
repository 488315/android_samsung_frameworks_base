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

    default T run(MediaBuffer mediaBuffer) {
        return run(new ArrayList<MediaBuffer>(mediaBuffer) { // from class: com.samsung.android.sume.core.controller.MediaController.1
            final /* synthetic */ MediaBuffer val$mediabuffer;

            {
                this.val$mediabuffer = mediaBuffer;
                add(mediaBuffer);
            }
        }, new ArrayList());
    }

    default T run(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) {
        return run(new ArrayList<MediaBuffer>(mediaBuffer) { // from class: com.samsung.android.sume.core.controller.MediaController.2
            final /* synthetic */ MediaBuffer val$inBuffer;

            {
                this.val$inBuffer = mediaBuffer;
                add(mediaBuffer);
            }
        }, new ArrayList<MediaBuffer>(mediaBuffer2) { // from class: com.samsung.android.sume.core.controller.MediaController.3
            final /* synthetic */ MediaBuffer val$outBuffer;

            {
                this.val$outBuffer = mediaBuffer2;
                add(mediaBuffer2);
            }
        });
    }

    default T run(List<MediaBuffer> list) {
        return run(list, new ArrayList());
    }
}
