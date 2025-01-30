package com.android.systemui.settings.multisim;

import com.android.systemui.settings.multisim.MultiSIMController;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiSIMController$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MultiSIMController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((WeakReference) obj).get() != ((MultiSIMController.MultiSIMDataChangedCallback) this.f$0)) {
                    break;
                }
                break;
            case 1:
                if (((WeakReference) obj).get() != ((MultiSIMController.MultiSIMVisibilityChangedCallback) this.f$0)) {
                    break;
                }
                break;
            default:
                break;
        }
        return false;
    }
}
