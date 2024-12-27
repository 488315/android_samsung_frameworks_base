package com.android.systemui.keyguard;

import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.HashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class DisplayLifecycle$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayLifecycle f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DisplayLifecycle$$ExternalSyntheticLambda4(DisplayLifecycle displayLifecycle, int i, int i2) {
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
                final int i2 = 1;
                displayLifecycle.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i3 = i2;
                        int i4 = i;
                        DisplayLifecycle.Observer observer = (DisplayLifecycle.Observer) obj;
                        switch (i3) {
                            case 0:
                                observer.onDisplayChanged(i4);
                                break;
                            case 1:
                                observer.onDisplayAdded(i4);
                                break;
                            default:
                                observer.onDisplayRemoved(i4);
                                break;
                        }
                    }
                });
                break;
            case 1:
                DisplayLifecycle displayLifecycle2 = this.f$0;
                final int i3 = this.f$1;
                displayLifecycle2.getClass();
                final int i4 = 2;
                displayLifecycle2.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i4;
                        int i42 = i3;
                        DisplayLifecycle.Observer observer = (DisplayLifecycle.Observer) obj;
                        switch (i32) {
                            case 0:
                                observer.onDisplayChanged(i42);
                                break;
                            case 1:
                                observer.onDisplayAdded(i42);
                                break;
                            default:
                                observer.onDisplayRemoved(i42);
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
                final int i6 = 0;
                displayLifecycle3.dispatch(new Consumer() { // from class: com.android.systemui.keyguard.DisplayLifecycle$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i32 = i6;
                        int i42 = i5;
                        DisplayLifecycle.Observer observer = (DisplayLifecycle.Observer) obj;
                        switch (i32) {
                            case 0:
                                observer.onDisplayChanged(i42);
                                break;
                            case 1:
                                observer.onDisplayAdded(i42);
                                break;
                            default:
                                observer.onDisplayRemoved(i42);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
