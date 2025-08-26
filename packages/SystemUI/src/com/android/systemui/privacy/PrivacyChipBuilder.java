package com.android.systemui.privacy;

import android.content.Context;
import com.android.systemui.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class PrivacyChipBuilder {
    public final Context context;
    public final String lastSeparator;
    public final String separator;
    public final List types;

    public PrivacyChipBuilder(Context context, List<PrivacyItem> list) {
        this.context = context;
        this.separator = context.getString(R.string.ongoing_privacy_dialog_separator);
        this.lastSeparator = context.getString(R.string.ongoing_privacy_dialog_last_separator);
        List<PrivacyItem> list2 = list;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PrivacyItem privacyItem : list2) {
            PrivacyApplication privacyApplication = privacyItem.application;
            Object arrayList = linkedHashMap.get(privacyApplication);
            if (arrayList == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(privacyApplication, arrayList);
            }
            ((List) arrayList).add(privacyItem.privacyType);
        }
        final int i = 0;
        final int i2 = 1;
        CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.privacy.PrivacyChipBuilder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Pair pair = (Pair) obj;
                switch (i) {
                    case 0:
                        return Integer.valueOf(-((List) pair.getSecond()).size());
                    default:
                        return CollectionsKt___CollectionsKt.minOrNull((Iterable) pair.getSecond());
                }
            }
        }, new Function1() { // from class: com.android.systemui.privacy.PrivacyChipBuilder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Pair pair = (Pair) obj;
                switch (i2) {
                    case 0:
                        return Integer.valueOf(-((List) pair.getSecond()).size());
                    default:
                        return CollectionsKt___CollectionsKt.minOrNull((Iterable) pair.getSecond());
                }
            }
        }));
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(((PrivacyItem) it.next()).privacyType);
        }
        this.types = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList2));
    }

    public final String joinTypes() throws IOException {
        int size = this.types.size();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return ((PrivacyType) this.types.get(0)).getName(this.context);
        }
        List list = this.types;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((PrivacyType) it.next()).getName(this.context));
        }
        List listSubList = arrayList.subList(0, arrayList.size() - 1);
        StringBuilder sb = new StringBuilder();
        CollectionsKt___CollectionsKt.joinTo$default(listSubList, sb, this.separator, null, 124);
        sb.append(this.lastSeparator);
        sb.append(CollectionsKt___CollectionsKt.last(arrayList));
        return sb.toString();
    }
}
