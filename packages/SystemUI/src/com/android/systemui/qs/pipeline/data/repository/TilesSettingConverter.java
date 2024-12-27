package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public final class TilesSettingConverter {
    public static final TilesSettingConverter INSTANCE = new TilesSettingConverter();

    private TilesSettingConverter() {
    }

    public static List toTilesList(String str) {
        List<String> split$default = StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6);
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
        for (String str2 : split$default) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str2));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (!Intrinsics.areEqual((TileSpec) next, TileSpec.Invalid.INSTANCE)) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }
}
