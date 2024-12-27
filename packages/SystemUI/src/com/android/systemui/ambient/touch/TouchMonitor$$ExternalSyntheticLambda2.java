package com.android.systemui.ambient.touch;

import android.view.GestureDetector;
import android.view.InputEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((TouchMonitor) obj2).getClass();
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
