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
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    public static Map favMap = MapsKt__MapsKt.emptyMap();

    private Favorites() {
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean addFavorite(android.content.ComponentName r11, java.lang.CharSequence r12, com.android.systemui.controls.controller.ControlInfo r13) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.controller.Favorites.addFavorite(android.content.ComponentName, java.lang.CharSequence, com.android.systemui.controls.controller.ControlInfo):boolean");
    }

    public static boolean addFavorites(ComponentName componentName, ArrayList arrayList) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : arrayList) {
            CharSequence structure = ((Control) obj).getStructure();
            Object obj2 = linkedHashMap.get(structure);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(structure, obj2);
            }
            ((List) obj2).add(obj);
        }
        boolean z = false;
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
            CollectionsKt__MutableCollectionsKt.addAll((List) ((Map.Entry) it.next()).getValue(), arrayList);
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
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("removeStructures isUpdateFlag = ", size, ", favMap.size = ", z, ", favMap = ");
        m.append(map);
        Log.d("Favorites", m.toString());
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
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
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
                Object obj2 = linkedHashMap2.get(charSequence);
                if (obj2 == null) {
                    obj2 = new ArrayList();
                    linkedHashMap2.put(charSequence, obj2);
                }
                ((List) obj2).add(controlInfo2);
                linkedHashMap3.put(charSequence, Boolean.TRUE);
            }
        }
        if (!z) {
            return false;
        }
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            CharSequence charSequence2 = (CharSequence) entry.getKey();
            StructureInfo structureInfo2 = new StructureInfo(componentName, charSequence2, (List) entry.getValue(), false, 8, null);
            Boolean bool = (Boolean) linkedHashMap3.get(charSequence2);
            structureInfo2.active = bool != null ? bool.booleanValue() : false;
            arrayList.add(structureInfo2);
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(favMap);
        linkedHashMap4.put(componentName, arrayList);
        favMap = linkedHashMap4;
        Log.d("Favorites", "updateControls favMap.size = " + linkedHashMap4.size() + ", favMap = " + favMap);
        return true;
    }
}
