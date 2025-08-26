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

/* loaded from: classes2.dex */
public final class SubscreenDefaultTilesQSHostRepository implements DefaultTilesRepository {
    public final Resources resources;

    public SubscreenDefaultTilesQSHostRepository(Resources resources) {
        this.resources = resources;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository
    public final List getDefaultTiles() throws Resources.NotFoundException {
        Iterable iterableTake;
        List listSplit = new Regex(",").split(this.resources.getString(R.string.sec_sub_quick_settings_tiles_default));
        if (listSplit.isEmpty()) {
            iterableTake = EmptyList.INSTANCE;
        } else {
            ListIterator listIterator = listSplit.listIterator(listSplit.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    iterableTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    break;
                }
            }
            iterableTake = EmptyList.INSTANCE;
        }
        Iterable<String> iterable = iterableTake;
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        for (String str : iterable) {
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
