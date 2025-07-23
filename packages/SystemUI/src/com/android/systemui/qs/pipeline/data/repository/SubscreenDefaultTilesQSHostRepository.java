package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SubscreenDefaultTilesQSHostRepository implements DefaultTilesRepository {
    public final Resources resources;

    public SubscreenDefaultTilesQSHostRepository(Resources resources) {
        this.resources = resources;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository
    public final List getDefaultTiles() {
        Iterable iterable;
        List split = new Regex(",").split(this.resources.getString(R.string.sec_sub_quick_settings_tiles_default));
        if (!split.isEmpty()) {
            ListIterator listIterator = split.listIterator(split.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    iterable = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                    break;
                }
            }
        }
        iterable = EmptyList.INSTANCE;
        Iterable<String> iterable2 = iterable;
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable2, 10));
        for (String str : iterable2) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str));
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
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
