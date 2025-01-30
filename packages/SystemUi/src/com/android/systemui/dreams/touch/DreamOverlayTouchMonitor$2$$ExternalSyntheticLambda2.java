package com.android.systemui.dreams.touch;

import android.view.GestureDetector;
import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((InputChannelCompat$InputEventListener) obj).onInputEvent((InputEvent) this.f$0);
                break;
            default:
                ((Consumer) this.f$0).accept((GestureDetector.OnGestureListener) obj);
                break;
        }
    }
}
