package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((TouchMonitor) obj2).mMaxBounds = (Rect) obj;
                break;
            case 1:
                ((InputChannelCompat$InputEventListener) obj).onInputEvent((InputEvent) obj2);
                break;
            default:
                ((Consumer) obj2).accept((GestureDetector.OnGestureListener) obj);
                break;
        }
    }
}
