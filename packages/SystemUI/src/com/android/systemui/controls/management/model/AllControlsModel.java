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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AllControlsModel {
    public RecyclerView.Adapter adapter;
    public final CharSequence categoryHeader;
    public final List controls;
    public final List elements;
    public final CharSequence emptyStructureZoneString;
    public final StructureModel.StructureModelCallback favoriteControlChangedCallback;
    public final List favoriteIds;
    public final boolean needCategoryHeader;
    public final Resources resources;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        boolean z2 = sorted.size() == sorted2.size() && sorted.containsAll(sorted2);
        this.resources.getString(R.string.sec_controls_removed);
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list4) {
            if (((ControlStatus) obj2).removed) {
                arrayList5.add(obj2);
            }
        }
        boolean areEqual = Intrinsics.areEqual(list3, arrayList5);
        arrayList2.add(new SecStructureNameWrapper(this.categoryHeader, z2, TextUtils.isEmpty(this.categoryHeader) ? this.emptyStructureZoneString : this.categoryHeader, this.needCategoryHeader));
        if (areEqual) {
            ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 10));
            Iterator it3 = arrayList5.iterator();
            while (it3.hasNext()) {
                arrayList6.add(new SecControlStatusWrapper((ControlStatus) it3.next()));
            }
            arrayList2.addAll(arrayList6);
            arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize));
        } else {
            OrderedMap orderedMap = new OrderedMap(new ArrayMap());
            for (Object obj3 : list4) {
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
                CharSequence charSequence3 = (CharSequence) it4.next();
                TransformingSequence transformingSequence = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Iterable) MapsKt__MapsKt.getValue(charSequence3, orderedMap)), new Function1() { // from class: com.android.systemui.controls.management.model.AllControlsModel$createWrappers$values$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj5) {
                        return new SecControlStatusWrapper((ControlStatus) obj5);
                    }
                });
                if (TextUtils.isEmpty(charSequence3)) {
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList3, transformingSequence);
                } else {
                    if (z3) {
                        z3 = false;
                    } else {
                        arrayList2.add(new VerticalPaddingWrapper(dimensionPixelSize2));
                    }
                    Intrinsics.checkNotNull(charSequence3);
                    arrayList2.add(new SecZoneNameWrapper(charSequence3));
                    CollectionsKt__MutableCollectionsKt.addAll(arrayList2, transformingSequence);
                }
            }
            if (!arrayList3.isEmpty()) {
                ArrayList arrayList7 = new ArrayList();
                Iterator it5 = arrayList2.iterator();
                while (it5.hasNext()) {
                    Object next = it5.next();
                    if (next instanceof SecZoneNameWrapper) {
                        arrayList7.add(next);
                    }
                }
                if (!arrayList7.isEmpty()) {
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
        Iterator it = this.elements.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            SecElementWrapper secElementWrapper = (SecElementWrapper) obj2;
            if ((secElementWrapper instanceof SecControlStatusWrapper) && Intrinsics.areEqual(((SecControlStatusWrapper) secElementWrapper).controlStatus.control.getControlId(), str)) {
                break;
            }
        }
        setControlFavoriteStatus((SecControlStatusWrapper) obj2, z);
        CharSequence charSequence = this.categoryHeader;
        Iterator it2 = ((ArrayList) this.elements).iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            SecElementWrapper secElementWrapper2 = (SecElementWrapper) next;
            if ((secElementWrapper2 instanceof SecStructureNameWrapper) && Intrinsics.areEqual(((SecStructureNameWrapper) secElementWrapper2).structureName, charSequence)) {
                obj = next;
                break;
            }
        }
        SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj;
        if (secStructureNameWrapper != null) {
            int indexOf = ((ArrayList) this.elements).indexOf(secStructureNameWrapper);
            int i = indexOf + 1;
            Iterator it3 = CollectionsKt___CollectionsKt.drop(this.elements, i).iterator();
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                if (!it3.hasNext()) {
                    i2 = -1;
                    break;
                } else if (((SecElementWrapper) it3.next()) instanceof SecStructureNameWrapper) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                i2 = ((ArrayList) this.elements).size();
            }
            List take = CollectionsKt___CollectionsKt.take(CollectionsKt___CollectionsKt.drop(this.elements, i), i2 - i);
            ArrayList arrayList = new ArrayList();
            for (Object obj3 : take) {
                if (obj3 instanceof SecControlStatusWrapper) {
                    arrayList.add(obj3);
                }
            }
            if (!arrayList.isEmpty()) {
                Iterator it4 = arrayList.iterator();
                while (it4.hasNext()) {
                    if (!((SecControlStatusWrapper) it4.next()).controlStatus.favorite) {
                        break;
                    }
                }
            }
            z2 = true;
            secStructureNameWrapper.favorite = z2;
            RecyclerView.Adapter adapter = this.adapter;
            if (adapter != null) {
                adapter.notifyItemChanged(indexOf, new Object());
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
        RecyclerView.Adapter adapter = this.adapter;
        if (adapter != null) {
            adapter.notifyItemChanged(indexOf, new Object());
        }
    }
}
