package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    public static Map favMap = MapsKt__MapsKt.emptyMap();

    private Favorites() {
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo) {
        boolean z;
        StructureInfo structureInfo;
        boolean z2;
        boolean z3;
        Object obj;
        ArrayList arrayList = (ArrayList) getControlsForComponent(componentName);
        boolean z4 = false;
        if (!arrayList.isEmpty()) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((ControlInfo) it.next()).controlId, controlInfo.controlId)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z) {
            return false;
        }
        List list = (List) favMap.get(componentName);
        if (list != null) {
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it2.next();
                if (Intrinsics.areEqual(((StructureInfo) obj).structure, charSequence)) {
                    break;
                }
            }
            structureInfo = (StructureInfo) obj;
            if (structureInfo != null) {
                z2 = false;
                StructureInfo copy$default = StructureInfo.copy$default(structureInfo, CollectionsKt___CollectionsKt.plus(structureInfo.controls, controlInfo));
                if (BasicRune.CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST) {
                    List list2 = copy$default.controls;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator it3 = list2.iterator();
                        while (it3.hasNext()) {
                            if (!(((ControlInfo) it3.next()).customControlInfo.layoutType == 1)) {
                                z3 = false;
                                break;
                            }
                        }
                    }
                    z3 = true;
                    if (z2 && z3) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap(favMap);
                        ArrayList arrayList2 = new ArrayList();
                        ComponentName componentName2 = copy$default.componentName;
                        List structuresForComponent = getStructuresForComponent(componentName2);
                        if (!(structuresForComponent instanceof Collection) || !structuresForComponent.isEmpty()) {
                            Iterator it4 = structuresForComponent.iterator();
                            while (true) {
                                if (!it4.hasNext()) {
                                    break;
                                }
                                if (Intrinsics.areEqual(((StructureInfo) it4.next()).structure, copy$default.structure)) {
                                    z4 = true;
                                    break;
                                }
                            }
                        }
                        if (!z4) {
                            arrayList2.add(copy$default);
                            arrayList2.addAll(structuresForComponent);
                            linkedHashMap.put(componentName2, arrayList2);
                            favMap = linkedHashMap;
                            Log.d("Favorites", "addNewStructureFirst favMap.size = " + linkedHashMap.size() + ", favMap = " + favMap);
                        }
                        return true;
                    }
                }
                replaceControls(copy$default);
                return true;
            }
        }
        structureInfo = new StructureInfo(componentName, charSequence, EmptyList.INSTANCE);
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            structureInfo.customStructureInfo.active = true;
        }
        z2 = BasicRune.CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST;
        StructureInfo copy$default2 = StructureInfo.copy$default(structureInfo, CollectionsKt___CollectionsKt.plus(structureInfo.controls, controlInfo));
        if (BasicRune.CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST) {
        }
        replaceControls(copy$default2);
        return true;
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
                ControlInfo controlInfo = new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType());
                if (BasicRune.CONTROLS_LAYOUT_TYPE) {
                    controlInfo.customControlInfo.layoutType = control.getCustomControl().getLayoutType();
                }
                Unit unit = Unit.INSTANCE;
                INSTANCE.getClass();
                if (addFavorite(componentName, charSequence2, controlInfo)) {
                    z = true;
                }
            }
        }
        return z;
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
        if (BasicRune.CONTROLS_SAMSUNG_STYLE && z) {
            setActiveFlag(componentName, false);
        }
        int size = favMap.size();
        Map map = favMap;
        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("removeStructures isUpdateFlag = ", z, ", favMap.size = ", size, ", favMap = ");
        m66m.append(map);
        Log.d("Favorites", m66m.toString());
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
                ((StructureInfo) it.next()).customStructureInfo.active = z;
            }
        }
        Log.d("Favorites", "setActiveFlag = " + favMap.get(componentName));
    }

    public static boolean updateControls(ComponentName componentName, List list) {
        Pair pair;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj : list) {
            linkedHashMap.put(((Control) obj).getControlId(), obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        boolean z = false;
        for (StructureInfo structureInfo : getStructuresForComponent(componentName)) {
            for (ControlInfo controlInfo : structureInfo.controls) {
                Control control = (Control) linkedHashMap.get(controlInfo.controlId);
                CharSequence charSequence = structureInfo.structure;
                if (control != null) {
                    if (!Intrinsics.areEqual(control.getTitle(), controlInfo.controlTitle) || !Intrinsics.areEqual(control.getSubtitle(), controlInfo.controlSubtitle) || control.getDeviceType() != controlInfo.deviceType) {
                        z = true;
                        controlInfo = new ControlInfo(controlInfo.controlId, control.getTitle(), control.getSubtitle(), control.getDeviceType(), controlInfo.customControlInfo);
                    }
                    CharSequence structure = control.getStructure();
                    if (structure == null) {
                        structure = "";
                    }
                    if (!Intrinsics.areEqual(charSequence, structure)) {
                        z = true;
                    }
                    pair = new Pair(structure, controlInfo);
                } else {
                    pair = new Pair(charSequence, controlInfo);
                }
                CharSequence charSequence2 = (CharSequence) pair.component1();
                ControlInfo controlInfo2 = (ControlInfo) pair.component2();
                Object obj2 = linkedHashMap2.get(charSequence2);
                if (obj2 == null) {
                    obj2 = new ArrayList();
                    linkedHashMap2.put(charSequence2, obj2);
                }
                ((List) obj2).add(controlInfo2);
                if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    linkedHashMap3.put(charSequence2, Boolean.TRUE);
                }
            }
        }
        if (!z) {
            return false;
        }
        ArrayList arrayList = new ArrayList(linkedHashMap2.size());
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            CharSequence charSequence3 = (CharSequence) entry.getKey();
            StructureInfo structureInfo2 = new StructureInfo(componentName, charSequence3, (List) entry.getValue());
            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                Boolean bool = (Boolean) linkedHashMap3.get(charSequence3);
                structureInfo2.customStructureInfo.active = bool != null ? bool.booleanValue() : false;
            }
            arrayList.add(structureInfo2);
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(favMap);
        linkedHashMap4.put(componentName, arrayList);
        favMap = linkedHashMap4;
        Log.d("Favorites", "updateControls favMap.size = " + linkedHashMap4.size() + ", favMap = " + favMap);
        return true;
    }
}
