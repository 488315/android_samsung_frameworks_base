package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class TilesSettingConverter {
    public static final TilesSettingConverter INSTANCE = new TilesSettingConverter();

    private TilesSettingConverter() {
    }

    public static List toTilesList(String str) {
        int i = 0;
        List<String> listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6);
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
        for (String str2 : listSplit$default) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str2));
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (!Intrinsics.areEqual((TileSpec) obj, TileSpec.Invalid.INSTANCE)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static Set toTilesSet(String str) {
        int i = 0;
        List<String> listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6);
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
        for (String str2 : listSplit$default) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str2));
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (!Intrinsics.areEqual((TileSpec) obj, TileSpec.Invalid.INSTANCE)) {
                arrayList2.add(obj);
            }
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList2);
    }

    public static List toTilesList(Resources resources, String str) {
        int i = 0;
        List<String> listSplit$default = StringsKt__StringsKt.split$default(str, new String[]{","}, 0, 6);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
        for (String str2 : listSplit$default) {
            TileSpec.Companion companion = TileSpec.Companion;
            TileNameConverter.INSTANCE.getClass();
            String tileSpec = TileNameConverter.toTileSpec(resources, str2);
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(tileSpec));
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (!Intrinsics.areEqual((TileSpec) obj, TileSpec.Invalid.INSTANCE)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }
}
