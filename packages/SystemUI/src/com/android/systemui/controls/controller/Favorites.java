package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    public static Map favMap = MapsKt__MapsKt.emptyMap();

    private Favorites() {
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo) {
        boolean z;
        StructureInfo structureInfo;
        Object next;
        ArrayList arrayList = (ArrayList) getControlsForComponent(componentName);
        boolean z2 = false;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (Intrinsics.areEqual(((ControlInfo) obj).controlId, controlInfo.controlId)) {
                    return false;
                }
            }
        }
        List list = (List) favMap.get(componentName);
        if (list != null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Intrinsics.areEqual(((StructureInfo) next).structure, charSequence)) {
                    break;
                }
            }
            structureInfo = (StructureInfo) next;
            if (structureInfo != null) {
                z = false;
            } else {
                StructureInfo structureInfo2 = new StructureInfo(componentName, charSequence, EmptyList.INSTANCE, false, 8, null);
                structureInfo2.active = true;
                z = true;
                structureInfo = structureInfo2;
            }
        }
        StructureInfo structureInfoCopy$default = StructureInfo.copy$default(structureInfo, CollectionsKt___CollectionsKt.plus(structureInfo.controls, controlInfo));
        List list2 = structureInfoCopy$default.controls;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            z2 = true;
        } else {
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                if (((ControlInfo) it2.next()).layoutType != 1) {
                    break;
                }
            }
            z2 = true;
        }
        if (!z || !z2) {
            replaceControls(structureInfoCopy$default);
            return true;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(favMap);
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentName2 = structureInfoCopy$default.componentName;
        List structuresForComponent = getStructuresForComponent(componentName2);
        List list3 = structuresForComponent;
        if (!(list3 instanceof Collection) || !list3.isEmpty()) {
            Iterator it3 = list3.iterator();
            while (it3.hasNext()) {
                if (Intrinsics.areEqual(((StructureInfo) it3.next()).structure, structureInfoCopy$default.structure)) {
                    return true;
                }
            }
        }
        arrayList2.add(structureInfoCopy$default);
        arrayList2.addAll(structuresForComponent);
        linkedHashMap.put(componentName2, arrayList2);
        favMap = linkedHashMap;
        Log.d("Favorites", "addNewStructureFirst favMap.size = " + linkedHashMap.size() + ", favMap = " + favMap);
        return true;
    }

    public static boolean addFavorites(ComponentName componentName, ArrayList arrayList) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CharSequence structure = ((Control) obj).getStructure();
            Object arrayList2 = linkedHashMap.get(structure);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                linkedHashMap.put(structure, arrayList2);
            }
            ((List) arrayList2).add(obj);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            CharSequence charSequence = (CharSequence) entry.getKey();
            for (Control control : (List) entry.getValue()) {
                CharSequence charSequence2 = charSequence == null ? "" : charSequence;
                ControlInfo controlInfo = new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType(), 0, 16, null);
                controlInfo.layoutType = control.getCustomControl().getLayoutType();
                Unit unit = Unit.INSTANCE;
                INSTANCE.getClass();
                if (addFavorite(componentName, charSequence2, controlInfo)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public static boolean getActiveFlag(ComponentName componentName) {
        List list = (List) favMap.get(componentName);
        if (list == null) {
            return false;
        }
        List list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            if (((StructureInfo) it.next()).active) {
                return true;
            }
        }
        return false;
    }

    public static List getAllStructures() {
        Map map = favMap;
        ArrayList arrayList = new ArrayList();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll((Iterable) ((Map.Entry) it.next()).getValue(), arrayList);
        }
        return arrayList;
    }

    public static List getControlsForComponent(ComponentName componentName) {
        List structuresForComponent = getStructuresForComponent(componentName);
        ArrayList arrayList = new ArrayList();
        Iterator it = structuresForComponent.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll(((StructureInfo) it.next()).controls, arrayList);
        }
        return arrayList;
    }

    public static List getStructuresForComponent(ComponentName componentName) {
        List list = (List) favMap.get(componentName);
        return list == null ? EmptyList.INSTANCE : list;
    }

    public static boolean removeStructures(ComponentName componentName, boolean z) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(favMap);
        boolean z2 = linkedHashMap.remove(componentName) != null;
        favMap = linkedHashMap;
        if (z) {
            setActiveFlag(componentName, false);
        }
        int size = favMap.size();
        Map map = favMap;
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("removeStructures isUpdateFlag = ", size, ", favMap.size = ", z, ", favMap = ");
        sbM.append(map);
        Log.d("Favorites", sbM.toString());
        return z2;
    }

    public static void replaceControls(StructureInfo structureInfo) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(favMap);
        ArrayList arrayList = new ArrayList();
        ComponentName componentName = structureInfo.componentName;
        boolean z = false;
        for (StructureInfo structureInfo2 : getStructuresForComponent(componentName)) {
            if (Intrinsics.areEqual(structureInfo2.structure, structureInfo.structure)) {
                z = true;
                structureInfo2 = structureInfo;
            }
            if (!structureInfo2.controls.isEmpty()) {
                arrayList.add(structureInfo2);
            }
        }
        if (!z && !structureInfo.controls.isEmpty()) {
            arrayList.add(structureInfo);
        }
        linkedHashMap.put(componentName, arrayList);
        favMap = linkedHashMap;
        Log.d("Favorites", "replaceControls favMap.size = " + linkedHashMap.size() + ", favMap = " + favMap);
    }

    public static void setActiveFlag(ComponentName componentName, boolean z) {
        List list = (List) favMap.get(componentName);
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((StructureInfo) it.next()).active = z;
            }
        }
        Log.d("Favorites", "setActiveFlag = " + favMap.get(componentName));
    }

    public static boolean updateControls(ComponentName componentName, List list) {
        Pair pair;
        List list2 = list;
        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        if (iMapCapacity < 16) {
            iMapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
        for (Object obj : list2) {
            linkedHashMap.put(((Control) obj).getControlId(), obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        boolean z = false;
        for (StructureInfo structureInfo : getStructuresForComponent(componentName)) {
            for (ControlInfo controlInfo : structureInfo.controls) {
                Control control = (Control) linkedHashMap.get(controlInfo.controlId);
                if (control != null) {
                    if (!Intrinsics.areEqual(control.getTitle(), controlInfo.controlTitle) || !Intrinsics.areEqual(control.getSubtitle(), controlInfo.controlSubtitle) || control.getDeviceType() != controlInfo.deviceType) {
                        z = true;
                        controlInfo = new ControlInfo(controlInfo.controlId, control.getTitle(), control.getSubtitle(), control.getDeviceType(), controlInfo.layoutType);
                    }
                    CharSequence structure = control.getStructure();
                    if (structure == null) {
                        structure = "";
                    }
                    if (!Intrinsics.areEqual(structureInfo.structure, structure)) {
                        z = true;
                    }
                    pair = new Pair(structure, controlInfo);
                } else {
                    pair = new Pair(structureInfo.structure, controlInfo);
                }
                CharSequence charSequence = (CharSequence) pair.component1();
                ControlInfo controlInfo2 = (ControlInfo) pair.component2();
                Object arrayList = linkedHashMap2.get(charSequence);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap2.put(charSequence, arrayList);
                }
                ((List) arrayList).add(controlInfo2);
                linkedHashMap3.put(charSequence, Boolean.TRUE);
            }
        }
        if (!z) {
            return false;
        }
        ArrayList arrayList2 = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            CharSequence charSequence2 = (CharSequence) entry.getKey();
            StructureInfo structureInfo2 = new StructureInfo(componentName, charSequence2, (List) entry.getValue(), false, 8, null);
            Boolean bool = (Boolean) linkedHashMap3.get(charSequence2);
            structureInfo2.active = bool != null ? bool.booleanValue() : false;
            arrayList2.add(structureInfo2);
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(favMap);
        linkedHashMap4.put(componentName, arrayList2);
        favMap = linkedHashMap4;
        Log.d("Favorites", "updateControls favMap.size = " + linkedHashMap4.size() + ", favMap = " + favMap);
        return true;
    }
}
