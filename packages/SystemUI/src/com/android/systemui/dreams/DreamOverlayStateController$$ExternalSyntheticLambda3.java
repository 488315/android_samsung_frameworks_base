package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStateController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda3(DreamOverlayStateController dreamOverlayStateController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStateController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayStateController dreamOverlayStateController = this.f$0;
                DreamOverlayStateController.Callback callback = (DreamOverlayStateController.Callback) this.f$1;
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
            case 1:
                DreamOverlayStateController dreamOverlayStateController2 = this.f$0;
                DreamOverlayStateController.Callback callback3 = (DreamOverlayStateController.Callback) this.f$1;
                dreamOverlayStateController2.getClass();
                Objects.requireNonNull(callback3, "Callback must not be null. b/128895449");
                if (!dreamOverlayStateController2.mCallbacks.stream().anyMatch(new DreamOverlayStateController$$ExternalSyntheticLambda4(callback3, 1))) {
                    dreamOverlayStateController2.mCallbacks.add(dreamOverlayStateController2.mWeakReferenceFactory.create(callback3));
                    if (!((HashSet) dreamOverlayStateController2.mComplications).isEmpty()) {
                        callback3.onComplicationsChanged();
                        break;
                    }
                }
                break;
            default:
                this.f$0.notifyCallbacksLocked((Consumer) this.f$1);
                break;
        }
    }
}
