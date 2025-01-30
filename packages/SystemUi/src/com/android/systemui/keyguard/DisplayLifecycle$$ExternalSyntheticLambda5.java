package com.android.systemui.keyguard;

import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayLifecycle$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayLifecycle f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DisplayLifecycle$$ExternalSyntheticLambda5(DisplayLifecycle displayLifecycle, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayLifecycle;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayLifecycle displayLifecycle = this.f$0;
                final int i = this.f$1;
                displayLifecycle.getClass();
                final int i2 = 0;
                displayLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                ((DisplayLifecycle.Observer) obj).onDisplayChanged(i);
                                break;
                            case 1:
                                ((DisplayLifecycle.Observer) obj).onDisplayRemoved(i);
                                break;
                            default:
                                ((DisplayLifecycle.Observer) obj).onDisplayAdded(i);
                                break;
                        }
                    }
                });
                break;
            case 1:
                DisplayLifecycle displayLifecycle2 = this.f$0;
                final int i3 = this.f$1;
                displayLifecycle2.getClass();
                final int i4 = 1;
                displayLifecycle2.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i4) {
                            case 0:
                                ((DisplayLifecycle.Observer) obj).onDisplayChanged(i3);
                                break;
                            case 1:
                                ((DisplayLifecycle.Observer) obj).onDisplayRemoved(i3);
                                break;
                            default:
                                ((DisplayLifecycle.Observer) obj).onDisplayAdded(i3);
                                break;
                        }
                    }
                });
                android.util.Log.d("DisplayLifecycle", "removeDisplay id = " + i3);
                ((HashMap) displayLifecycle2.mDisplayHash).remove(Integer.valueOf(i3));
                displayLifecycle2.mDisplaySizeHash.remove(i3);
                displayLifecycle2.mDisplayRealSizeHash.remove(i3);
                displayLifecycle2.mDisplayMetricsHash.remove(i3);
                displayLifecycle2.mDisplayRotationHash.delete(i3);
                break;
            default:
                DisplayLifecycle displayLifecycle3 = this.f$0;
                final int i5 = this.f$1;
                displayLifecycle3.getClass();
                final int i6 = 2;
                displayLifecycle3.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        switch (i6) {
                            case 0:
                                ((DisplayLifecycle.Observer) obj).onDisplayChanged(i5);
                                break;
                            case 1:
                                ((DisplayLifecycle.Observer) obj).onDisplayRemoved(i5);
                                break;
                            default:
                                ((DisplayLifecycle.Observer) obj).onDisplayAdded(i5);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
