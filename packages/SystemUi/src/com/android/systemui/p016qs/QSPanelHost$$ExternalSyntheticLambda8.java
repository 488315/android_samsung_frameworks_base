package com.android.systemui.p016qs;

import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.p016qs.bar.BarItemImpl;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPanelHost$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSPanelHost$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((ToBooleanFunction) this.f$0).apply((BarItemImpl) obj);
            default:
                return ((QSTile) obj).getDetailAdapter() == ((DetailAdapter) this.f$0);
        }
    }
}
