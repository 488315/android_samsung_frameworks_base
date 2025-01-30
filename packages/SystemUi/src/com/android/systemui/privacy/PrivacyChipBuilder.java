package com.android.systemui.privacy;

import android.content.Context;
import com.android.systemui.R;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PrivacyItem privacyItem : list) {
            PrivacyApplication privacyApplication = privacyItem.application;
            Object obj = linkedHashMap.get(privacyApplication);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(privacyApplication, obj);
            }
            ((List) obj).add(privacyItem.privacyType);
        }
        CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), ComparisonsKt__ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.privacy.PrivacyChipBuilder.3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return Integer.valueOf(-((List) ((Pair) obj2).getSecond()).size());
            }
        }, new Function1() { // from class: com.android.systemui.privacy.PrivacyChipBuilder.4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return CollectionsKt___CollectionsKt.minOrNull((Iterable) ((Pair) obj2).getSecond());
            }
        }));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((PrivacyItem) it.next()).privacyType);
        }
        this.types = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList));
    }

    public final String joinTypes() {
        List list = this.types;
        int size = list.size();
        if (size == 0) {
            return "";
        }
        Context context = this.context;
        if (size == 1) {
            return ((PrivacyType) list.get(0)).getName(context);
        }
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((PrivacyType) it.next()).getName(context));
        }
        List subList = arrayList.subList(0, arrayList.size() - 1);
        StringBuilder sb = new StringBuilder();
        CollectionsKt___CollectionsKt.joinTo(subList, sb, this.separator, "", "", -1, "...", null);
        sb.append(this.lastSeparator);
        sb.append(CollectionsKt___CollectionsKt.last(arrayList));
        return sb.toString();
    }
}
