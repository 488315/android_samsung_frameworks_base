package com.android.systemui.media.controls.ui.view;

import android.view.MotionEvent;
import com.android.systemui.Gefingerpoken;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaCarouselScrollHandler$touchListener$1 implements Gefingerpoken {
    public final /* synthetic */ MediaCarouselScrollHandler this$0;

    public MediaCarouselScrollHandler$touchListener$1(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        this.this$0 = mediaCarouselScrollHandler;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        motionEvent.getClass();
        return this.this$0.gestureDetector.mDetector.onTouchEvent(motionEvent);
    }
}
