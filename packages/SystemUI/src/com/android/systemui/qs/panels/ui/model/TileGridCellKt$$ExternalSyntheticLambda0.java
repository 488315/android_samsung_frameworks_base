package com.android.systemui.qs.panels.ui.model;

import com.android.systemui.qs.panels.shared.model.SizedTile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileGridCellKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ TileGridCellKt$$ExternalSyntheticLambda0(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue() + this.f$0;
        List list = (List) obj2;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            TileGridCell tileGridCell = new TileGridCell((SizedTile) it.next(), intValue, i);
            i += tileGridCell.width;
            arrayList.add(tileGridCell);
        }
        Iterator it2 = list.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            i2 += ((SizedTile) it2.next()).getWidth();
        }
        int i3 = this.f$1 - i2;
        ArrayList arrayList2 = new ArrayList(arrayList);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList2.add(new SpacerGridCell(intValue, 0L, 2, null));
        }
        return arrayList2;
    }
}
