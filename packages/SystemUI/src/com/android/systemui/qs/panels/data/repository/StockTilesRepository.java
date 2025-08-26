package com.android.systemui.qs.panels.data.repository;

import android.R;
import android.content.res.Resources;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class StockTilesRepository {
    public final boolean shouldRemoveRbcTile;
    public final List stockTiles;

    public StockTilesRepository(Resources resources) {
        this.shouldRemoveRbcTile = resources.getBoolean(R.bool.config_isDesktopModeSupported);
        int i = 0;
        List listSplit$default = StringsKt__StringsKt.split$default(resources.getString(com.android.systemui.R.string.quick_settings_tiles_stock), new String[]{","}, 0, 6);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            String str = (String) obj;
            if (!this.shouldRemoveRbcTile || !str.equals("reduce_brightness")) {
                arrayList.add(obj);
            }
        }
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            companion.getClass();
            arrayList2.add(TileSpec.Companion.create((String) obj2));
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj3 = arrayList2.get(i);
            i++;
            if (!(((TileSpec) obj3) instanceof TileSpec.Invalid)) {
                arrayList3.add(obj3);
            }
        }
        this.stockTiles = arrayList3;
    }
}
