package com.android.systemui.controls.management;

import android.service.controls.Control;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AllModel implements ControlsModel {
    public final List controls;
    public final ControlsModel.ControlsModelCallback controlsModelCallback;
    public final List elements;
    public final CharSequence emptyZoneString;
    public final List favoriteIds;
    public boolean modified;

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

    public AllModel(List<ControlStatus> list, List<String> list2, CharSequence charSequence, ControlsModel.ControlsModelCallback controlsModelCallback) {
        this.controls = list;
        this.emptyZoneString = charSequence;
        this.controlsModelCallback = controlsModelCallback;
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
        OrderedMap orderedMap = new OrderedMap(new ArrayMap());
        for (Object obj2 : list3) {
            String zone = ((ControlStatus) obj2).control.getZone();
            zone = zone == null ? "" : zone;
            Object obj3 = orderedMap.get(zone);
            if (obj3 == null) {
                obj3 = new ArrayList();
                orderedMap.put(zone, obj3);
            }
            ((List) obj3).add(obj2);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = ((ArrayList) orderedMap.orderedKeys).iterator();
        TransformingSequence transformingSequence = null;
        while (it2.hasNext()) {
            CharSequence charSequence2 = (CharSequence) it2.next();
            TransformingSequence transformingSequence2 = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1((Iterable) MapsKt__MapsKt.getValue(orderedMap, charSequence2)), new Function1() { // from class: com.android.systemui.controls.management.AllModel$createWrappers$values$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj4) {
                    return new ControlStatusWrapper((ControlStatus) obj4);
                }
            });
            if (TextUtils.isEmpty(charSequence2)) {
                transformingSequence = transformingSequence2;
            } else {
                arrayList2.add(new ZoneNameWrapper(charSequence2));
                CollectionsKt__MutableCollectionsKt.addAll(arrayList2, transformingSequence2);
            }
        }
        if (transformingSequence != null) {
            if (orderedMap.size() != 1) {
                arrayList2.add(new ZoneNameWrapper(this.emptyZoneString));
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, transformingSequence);
        }
        this.elements = arrayList2;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final void changeFavoriteStatus(String str, boolean z) {
        boolean z2;
        Object obj;
        ControlStatus controlStatus;
        Iterator it = this.elements.iterator();
        while (true) {
            z2 = false;
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            ElementWrapper elementWrapper = (ElementWrapper) obj;
            if ((elementWrapper instanceof ControlStatusWrapper) && Intrinsics.areEqual(((ControlStatusWrapper) elementWrapper).controlStatus.control.getControlId(), str)) {
                break;
            }
        }
        ControlStatusWrapper controlStatusWrapper = (ControlStatusWrapper) obj;
        if (controlStatusWrapper != null && (controlStatus = controlStatusWrapper.controlStatus) != null && z == controlStatus.favorite) {
            z2 = true;
        }
        if (z2) {
            return;
        }
        List list = this.favoriteIds;
        if (z ? ((ArrayList) list).add(str) : ((ArrayList) list).remove(str)) {
            boolean z3 = this.modified;
            ControlsModel.ControlsModelCallback controlsModelCallback = this.controlsModelCallback;
            if (!z3) {
                this.modified = true;
                controlsModelCallback.onFirstChange();
            }
            controlsModelCallback.onChange();
        }
        if (controlStatusWrapper != null) {
            controlStatusWrapper.controlStatus.favorite = z;
        }
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final List getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final List getFavorites() {
        ControlInfo controlInfo;
        Object obj;
        List list = this.favoriteIds;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Iterator it2 = this.controls.iterator();
            while (true) {
                controlInfo = null;
                if (!it2.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it2.next();
                if (Intrinsics.areEqual(((ControlStatus) obj).control.getControlId(), str)) {
                    break;
                }
            }
            ControlStatus controlStatus = (ControlStatus) obj;
            Control control = controlStatus != null ? controlStatus.control : null;
            if (control != null) {
                ControlInfo.Companion.getClass();
                controlInfo = ControlInfo.Companion.fromControl(control);
            }
            if (controlInfo != null) {
                arrayList.add(controlInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public final /* bridge */ /* synthetic */ ControlsModel.MoveHelper getMoveHelper() {
        return null;
    }
}
