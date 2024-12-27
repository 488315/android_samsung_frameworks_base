package com.android.systemui.dreams;

import com.android.systemui.complication.Complication;
import com.android.systemui.dreams.DreamOverlayStateController;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DreamOverlayStateController$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((DreamOverlayStateController) obj2).getClass();
                ((Complication) obj).getClass();
                return true;
            default:
                return ((WeakReference) obj).get() == ((DreamOverlayStateController.Callback) obj2);
        }
    }
}
