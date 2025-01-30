package com.android.systemui.controls.management.model;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.model.StructureModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AllControlsModel implements CustomControlsModel {
    public RecyclerView.Adapter adapter;
    public final CharSequence categoryHeader;
    public final List controls;
    public final List elements;
    public final CharSequence emptyStructureZoneString;
    public final StructureModel.StructureModelCallback favoriteControlChangedCallback;
    public final List favoriteIds;
    public final boolean needCategoryHeader;
    public final Resources resources;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OrderedMap implements Map, KMutableMap {
        public final Map map;
        public final List orderedKeys = new ArrayList();

        public OrderedMap(Map<Object, Object> map) {
            this.map = map;
        }

        @Override // java.util.Map
        public final void clear() {
            ((ArrayList) this.orderedKeys).clear();
            this.map.clear();
        }

        @Override // java.util.Map
        public final boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.Map
        public final boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.Map
        public final Set entrySet() {
            return this.map.entrySet();
        }

        @Override // java.util.Map
        public final Object get(Object obj) {
            return this.map.get(obj);
        }

        @Override // java.util.Map
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Map
        public final Set keySet() {
            return this.map.keySet();
        }

        @Override // java.util.Map
        public final Object put(Object obj, Object obj2) {
            if (!this.map.containsKey(obj)) {
                ((ArrayList) this.orderedKeys).add(obj);
            }
            return this.map.put(obj, obj2);
        }

        @Override // java.util.Map
        public final void putAll(Map map) {
            this.map.putAll(map);
        }

        @Override // java.util.Map
        public final Object remove(Object obj) {
            Object remove = this.map.remove(obj);
            if (remove != null) {
                ((ArrayList) this.orderedKeys).remove(obj);
            }
            return remove;
        }

        @Override // java.util.Map
        public final int size() {
            return this.map.size();
        }

        @Override // java.util.Map
        public final Collection values() {
            return this.map.values();
        }
    }

    public AllControlsModel(Resources resources, CharSequence charSequence, List<ControlStatus> list, List<String> list2, CharSequence charSequence2, boolean z, StructureModel.StructureModelCallback structureModelCallback) {
        this.resources = resources;
        this.categoryHeader = charSequence;
        this.controls = list;
        this.emptyStructureZoneString = charSequence2;
        this.needCategoryHeader = z;
        this.favoriteControlChangedCallback = structureModelCallback;
        HashSet hashSet = new HashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            hashSet.add(((ControlStatus) it.next()).control.getControlId());
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (hashSet.contains((String) obj)) {
                arrayList.add(obj);
            }
        }
        this.favoriteIds = new ArrayList(arrayList);
        List list3 = this.controls;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Resources resources2 = this.resources;
        int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.controls_custom_management_list_padding);
        int dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.control_zone_top_margin);
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it2 = list3.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((ControlStatus) it2.next()).getControlId());
        }
        List sorted = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList4));
        List sorted2 = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(this.favoriteIds));
        boolean z2 = sorted.size() == sorted2.size() && sorted.containsAll(sorted2);
        resources2.getString(R.string.controls_custom_removed);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list3) {
            if (((ControlStatus) obj2).removed) {
                arrayList5.add(obj2);
            }
        }
        boolean areEqual = Intrinsics.areEqual(list3, arrayList5);
        CharSequence charSequence3 = this.categoryHeader;
        boolean isEmpty = TextUtils.isEmpty(charSequence3);
        CharSequence charSequence4 = this.emptyStructureZoneString;
        arrayList2.add(new CustomStructureNameWrapper(charSequence3, z2, isEmpty ? charSequence4 : charSequence3, this.needCategoryHeader));
        if (areEqual) {
            ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
            Iterator it3 = arrayList5.iterator();
            while (it3.hasNext()) {
                arrayList6.add(new CustomControlStatusWrapper((ControlStatus) it3.next()));
            }
            arrayList2.addAll(arrayList6);
            arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize));
        } else {
            OrderedMap orderedMap = new OrderedMap(new ArrayMap());
            for (Object obj3 : list3) {
                String zone = ((ControlStatus) obj3).control.getZone();
                zone = zone == null ? "" : zone;
                Object obj4 = orderedMap.get(zone);
                if (obj4 == null) {
                    obj4 = new ArrayList();
                    orderedMap.put(zone, obj4);
                }
                ((List) obj4).add(obj3);
            }
            Iterator it4 = ((ArrayList) orderedMap.orderedKeys).iterator();
            boolean z3 = true;
            while (it4.hasNext()) {
                CharSequence charSequence5 = (CharSequence) it4.next();
                TransformingSequence transformingSequence = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Iterable) MapsKt__MapsKt.getValue(orderedMap, charSequence5)), new Function1() { // from class: com.android.systemui.controls.management.model.AllControlsModel$createWrappers$values$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj5) {
                        return new CustomControlStatusWrapper((ControlStatus) obj5);
                    }
                });
                if (TextUtils.isEmpty(charSequence5)) {
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList3, transformingSequence);
                } else {
                    if (z3) {
                        z3 = false;
                    } else {
                        arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize2));
                    }
                    arrayList2.add(new CustomZoneNameWrapper(charSequence5));
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList2, transformingSequence);
                }
            }
            if (!arrayList3.isEmpty()) {
                ArrayList arrayList7 = new ArrayList();
                Iterator it5 = arrayList2.iterator();
                while (it5.hasNext()) {
                    Object next = it5.next();
                    if (next instanceof CustomZoneNameWrapper) {
                        arrayList7.add(next);
                    }
                }
                if (!arrayList7.isEmpty()) {
                    arrayList2.add(new CustomZoneNameWrapper(charSequence4));
                }
                arrayList2.addAll(arrayList3);
            }
            arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize));
        }
        this.elements = arrayList2;
    }

    public final void changeFavoriteStatus(String str, boolean z) {
        boolean z2;
        Object obj;
        Object obj2;
        List list = this.elements;
        Iterator it = list.iterator();
        while (true) {
            z2 = false;
            obj = null;
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            CustomElementWrapper customElementWrapper = (CustomElementWrapper) obj2;
            if ((customElementWrapper instanceof CustomControlStatusWrapper) && Intrinsics.areEqual(((CustomControlStatusWrapper) customElementWrapper).controlStatus.control.getControlId(), str)) {
                break;
            }
        }
        setControlFavoriteStatus((CustomControlStatusWrapper) obj2, z);
        ArrayList arrayList = (ArrayList) list;
        Iterator it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            CustomElementWrapper customElementWrapper2 = (CustomElementWrapper) next;
            if ((customElementWrapper2 instanceof CustomStructureNameWrapper) && Intrinsics.areEqual(((CustomStructureNameWrapper) customElementWrapper2).structureName, this.categoryHeader)) {
                obj = next;
                break;
            }
        }
        CustomStructureNameWrapper customStructureNameWrapper = (CustomStructureNameWrapper) obj;
        if (customStructureNameWrapper != null) {
            int indexOf = arrayList.indexOf(customStructureNameWrapper);
            int i = indexOf + 1;
            Iterator it3 = CollectionsKt___CollectionsKt.drop(arrayList, i).iterator();
            int i2 = 0;
            while (true) {
                if (!it3.hasNext()) {
                    i2 = -1;
                    break;
                } else if (((CustomElementWrapper) it3.next()) instanceof CustomStructureNameWrapper) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                i2 = arrayList.size();
            }
            List take = CollectionsKt___CollectionsKt.take(CollectionsKt___CollectionsKt.drop(arrayList, i), i2 - i);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : take) {
                if (obj3 instanceof CustomControlStatusWrapper) {
                    arrayList2.add(obj3);
                }
            }
            if (!arrayList2.isEmpty()) {
                Iterator it4 = arrayList2.iterator();
                while (it4.hasNext()) {
                    if (!((CustomControlStatusWrapper) it4.next()).controlStatus.favorite) {
                        break;
                    }
                }
            }
            z2 = true;
            customStructureNameWrapper.favorite = z2;
            RecyclerView.Adapter adapter = this.adapter;
            if (adapter != null) {
                adapter.notifyItemChanged(indexOf, new Object());
            }
        }
    }

    public final void setControlFavoriteStatus(CustomControlStatusWrapper customControlStatusWrapper, boolean z) {
        if (customControlStatusWrapper == null) {
            return;
        }
        ControlStatus controlStatus = customControlStatusWrapper.controlStatus;
        if (z == controlStatus.favorite) {
            return;
        }
        List list = this.favoriteIds;
        if (z) {
            ((ArrayList) list).add(customControlStatusWrapper.getControlId());
        } else {
            ((ArrayList) list).remove(customControlStatusWrapper.getControlId());
        }
        CharSequence structure = controlStatus.control.getStructure();
        if (structure == null) {
            structure = "";
        }
        this.favoriteControlChangedCallback.onControlInfoChange(new ControlInfoForStructure(structure, customControlStatusWrapper.getControlId(), z));
        controlStatus.favorite = z;
        int indexOf = ((ArrayList) this.elements).indexOf(customControlStatusWrapper);
        RecyclerView.Adapter adapter = this.adapter;
        if (adapter != null) {
            adapter.notifyItemChanged(indexOf, new Object());
        }
    }
}
