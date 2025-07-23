package com.android.systemui.controls.management.model;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.systemui.R;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.adapter.StatelessControlAdapter;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AllControlsModel {
    public StatelessControlAdapter adapter;
    public final CharSequence categoryHeader;
    public final List controls;
    public final List elements;
    public final CharSequence emptyStructureZoneString;
    public final StructureModel.StructureModelCallback favoriteControlChangedCallback;
    public final List favoriteIds;
    public final boolean needCategoryHeader;
    public final Resources resources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.control_management_list_padding);
        int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.control_zone_top_margin);
        List list4 = list3;
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
        Iterator it2 = list4.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((ControlStatus) it2.next()).control.getControlId());
        }
        List list5 = this.favoriteIds;
        List sorted = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList4));
        List sorted2 = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(list5));
        boolean z2 = true;
        int i = 0;
        boolean z3 = sorted.size() == sorted2.size() && sorted.containsAll(sorted2);
        this.resources.getString(R.string.sec_controls_removed);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list4) {
            if (((ControlStatus) obj2).removed) {
                arrayList5.add(obj2);
            }
        }
        boolean areEqual = Intrinsics.areEqual(list3, arrayList5);
        arrayList2.add(new SecStructureNameWrapper(this.categoryHeader, z3, TextUtils.isEmpty(this.categoryHeader) ? this.emptyStructureZoneString : this.categoryHeader, this.needCategoryHeader));
        if (areEqual) {
            ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
            int size = arrayList5.size();
            while (i < size) {
                Object obj3 = arrayList5.get(i);
                i++;
                arrayList6.add(new SecControlStatusWrapper((ControlStatus) obj3));
            }
            arrayList2.addAll(arrayList6);
            arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize));
        } else {
            OrderedMap orderedMap = new OrderedMap(new ArrayMap());
            for (Object obj4 : list4) {
                String zone = ((ControlStatus) obj4).control.getZone();
                zone = zone == null ? "" : zone;
                Object obj5 = orderedMap.map.get(zone);
                if (obj5 == null) {
                    obj5 = new ArrayList();
                    orderedMap.put(zone, obj5);
                }
                ((List) obj5).add(obj4);
            }
            ArrayList arrayList7 = (ArrayList) orderedMap.orderedKeys;
            int size2 = arrayList7.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj6 = arrayList7.get(i2);
                i2++;
                CharSequence charSequence3 = (CharSequence) obj6;
                TransformingSequence transformingSequence = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Iterable) MapsKt__MapsKt.getValue(charSequence3, orderedMap)), new AllControlsModel$$ExternalSyntheticLambda0());
                if (TextUtils.isEmpty(charSequence3)) {
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList3, transformingSequence);
                } else {
                    if (z2) {
                        z2 = false;
                    } else {
                        arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize2));
                    }
                    charSequence3.getClass();
                    arrayList2.add(new SecZoneNameWrapper(charSequence3));
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList2, transformingSequence);
                }
            }
            if (!arrayList3.isEmpty()) {
                ArrayList arrayList8 = new ArrayList();
                int size3 = arrayList2.size();
                while (i < size3) {
                    Object obj7 = arrayList2.get(i);
                    i++;
                    if (obj7 instanceof SecZoneNameWrapper) {
                        arrayList8.add(obj7);
                    }
                }
                if (!arrayList8.isEmpty()) {
                    arrayList2.add(new SecZoneNameWrapper(this.emptyStructureZoneString));
                }
                arrayList2.addAll(arrayList3);
            }
            arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize));
        }
        this.elements = arrayList2;
    }

    public final void changeFavoriteStatus(String str, boolean z) {
        Object obj;
        Object obj2;
        ArrayList arrayList = (ArrayList) this.elements;
        int size = arrayList.size();
        boolean z2 = false;
        int i = 0;
        while (true) {
            obj = null;
            if (i >= size) {
                obj2 = null;
                break;
            }
            obj2 = arrayList.get(i);
            i++;
            SecElementWrapper secElementWrapper = (SecElementWrapper) obj2;
            if ((secElementWrapper instanceof SecControlStatusWrapper) && Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper).controlStatus.control.getControlId(), str)) {
                break;
            }
        }
        setControlFavoriteStatus((SecControlStatusWrapper) obj2, z);
        CharSequence charSequence = this.categoryHeader;
        ArrayList arrayList2 = (ArrayList) this.elements;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size2) {
                break;
            }
            Object obj3 = arrayList2.get(i2);
            i2++;
            SecElementWrapper secElementWrapper2 = (SecElementWrapper) obj3;
            if ((secElementWrapper2 instanceof SecStructureNameWrapper) && Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper2).structureName, charSequence)) {
                obj = obj3;
                break;
            }
        }
        SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj;
        if (secStructureNameWrapper != null) {
            int indexOf = ((ArrayList) this.elements).indexOf(secStructureNameWrapper);
            int i3 = indexOf + 1;
            Iterator it = CollectionsKt___CollectionsKt.drop(this.elements, i3).iterator();
            int i4 = 0;
            while (true) {
                if (!it.hasNext()) {
                    i4 = -1;
                    break;
                } else if (((SecElementWrapper) it.next()) instanceof SecStructureNameWrapper) {
                    break;
                } else {
                    i4++;
                }
            }
            if (i4 == -1) {
                i4 = ((ArrayList) this.elements).size();
            }
            List take = CollectionsKt___CollectionsKt.take(CollectionsKt___CollectionsKt.drop(this.elements, i3), i4 - i3);
            ArrayList arrayList3 = new ArrayList();
            for (Object obj4 : take) {
                if (obj4 instanceof SecControlStatusWrapper) {
                    arrayList3.add(obj4);
                }
            }
            if (!arrayList3.isEmpty()) {
                int size3 = arrayList3.size();
                int i5 = 0;
                while (i5 < size3) {
                    Object obj5 = arrayList3.get(i5);
                    i5++;
                    if (!((SecControlStatusWrapper) obj5).controlStatus.favorite) {
                        break;
                    }
                }
            }
            z2 = true;
            secStructureNameWrapper.favorite = z2;
            StatelessControlAdapter statelessControlAdapter = this.adapter;
            if (statelessControlAdapter != null) {
                statelessControlAdapter.notifyItemChanged(indexOf, new Object());
            }
        }
    }

    public final void setControlFavoriteStatus(SecControlStatusWrapper secControlStatusWrapper, boolean z) {
        if (secControlStatusWrapper == null) {
            return;
        }
        ControlStatus controlStatus = secControlStatusWrapper.controlStatus;
        if (z == controlStatus.favorite) {
            return;
        }
        if (z) {
            ((ArrayList) this.favoriteIds).add(secControlStatusWrapper.getControlId());
        } else {
            ((ArrayList) this.favoriteIds).remove(secControlStatusWrapper.getControlId());
        }
        CharSequence structure = controlStatus.control.getStructure();
        if (structure == null) {
            structure = "";
        }
        this.favoriteControlChangedCallback.onControlInfoChange(new ControlInfoForStructure(structure, secControlStatusWrapper.getControlId(), z));
        controlStatus.favorite = z;
        int indexOf = ((ArrayList) this.elements).indexOf(secControlStatusWrapper);
        StatelessControlAdapter statelessControlAdapter = this.adapter;
        if (statelessControlAdapter != null) {
            statelessControlAdapter.notifyItemChanged(indexOf, new Object());
        }
    }
}
