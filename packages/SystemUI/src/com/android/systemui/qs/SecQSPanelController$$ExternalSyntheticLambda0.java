package com.android.systemui.qs;

import android.util.Log;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSPanelController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQSPanelController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                BarOrderInteractor barOrderInteractor = (BarOrderInteractor) obj;
                BarOrderRepository barOrderRepository = barOrderInteractor.repository;
                List split$default = StringsKt__StringsKt.split$default(barOrderRepository.getDefaultBarOrderList(), new String[]{","}, 0, 6);
                Log.d("BarOrderRepository", "resetBarOrder");
                barOrderRepository.setBarOrder(barOrderInteractor.toFilteredNonEditBars(split$default));
                barOrderInteractor.sendOrderStatusLog();
                barOrderRepository.setCollapsedBarRow(2);
                barOrderInteractor.sendCollapsedRowStatusLog();
                break;
            default:
                ArrayList arrayList = ((QSPanelHost) obj).mBarController.mExpandedBarItems;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    ((BarItemImpl) obj2).makeCloneBar();
                }
                break;
        }
    }
}
