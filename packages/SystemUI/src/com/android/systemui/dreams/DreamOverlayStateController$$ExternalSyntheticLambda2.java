package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ DreamOverlayStateController.Callback f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda2(DreamOverlayStateController dreamOverlayStateController, DreamOverlayStateController.Callback callback, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStateController;
        this.f$1 = callback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStateController dreamOverlayStateController = this.f$0;
                DreamOverlayStateController.Callback callback = this.f$1;
                dreamOverlayStateController.getClass();
                Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
                Iterator it = dreamOverlayStateController.mCallbacks.iterator();
                while (it.hasNext()) {
                    DreamOverlayStateController.Callback callback2 = (DreamOverlayStateController.Callback) ((WeakReference) it.next()).get();
                    if (callback2 == null || callback2 == callback) {
                        it.remove();
                    }
                }
                break;
            default:
                DreamOverlayStateController dreamOverlayStateController2 = this.f$0;
                DreamOverlayStateController.Callback callback3 = this.f$1;
                dreamOverlayStateController2.getClass();
                Objects.requireNonNull(callback3, "Callback must not be null. b/128895449");
                if (!dreamOverlayStateController2.mCallbacks.stream().anyMatch(new DreamOverlayStateController$$ExternalSyntheticLambda3(callback3, 1))) {
                    dreamOverlayStateController2.mCallbacks.add(dreamOverlayStateController2.mWeakReferenceFactory.create(callback3));
                    if (!((HashSet) dreamOverlayStateController2.mComplications).isEmpty()) {
                        callback3.onComplicationsChanged();
                        break;
                    }
                }
                break;
        }
    }
}
