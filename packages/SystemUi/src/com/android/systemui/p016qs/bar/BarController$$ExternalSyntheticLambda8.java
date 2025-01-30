package com.android.systemui.p016qs.bar;

import android.content.res.Configuration;
import com.android.systemui.p016qs.bar.BarController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BarController barController = (BarController) this.f$0;
                barController.getClass();
                ((BarItemImpl) obj).setCallback(new BarController.C20874(barController));
                break;
            default:
                ((BarItemImpl) obj).onConfigChanged((Configuration) this.f$0);
                break;
        }
    }
}
