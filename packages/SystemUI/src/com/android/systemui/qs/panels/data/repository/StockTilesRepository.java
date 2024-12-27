package com.android.systemui.qs.panels.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StockTilesRepository {
    public final List stockTiles;

    public StockTilesRepository(Resources resources) {
        List<String> split$default = StringsKt__StringsKt.split$default(resources.getString(R.string.quick_settings_tiles_stock), new String[]{","}, 0, 6);
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
        for (String str : split$default) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (!(((TileSpec) next) instanceof TileSpec.Invalid)) {
                arrayList2.add(next);
            }
        }
        this.stockTiles = arrayList2;
    }
}
