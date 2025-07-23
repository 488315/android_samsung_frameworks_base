package com.android.systemui.controls.management.model;

import android.content.res.Resources;
import android.service.controls.Control;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.model.StructureModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AllStructureModel implements StructureModel {
    public final List controls;
    public final List elements;
    public final AllStructureModel$favoriteControlChangeCallback$1 favoriteControlChangeCallback;
    public final StructureModel.StructureModelCallback favoriteControlChangeMainCallback;
    public final Map favoriteIds;
    public final boolean isLoading;
    public final String removedString;
    public final Resources resources;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.controls.management.model.AllStructureModel$favoriteControlChangeCallback$1] */
    public AllStructureModel(Resources resources, List<ControlStatus> list, List<String> list2, StructureModel.StructureModelCallback structureModelCallback, boolean z) {
        ArrayList arrayList;
        this.resources = resources;
        this.controls = list;
        this.favoriteControlChangeMainCallback = structureModelCallback;
        this.isLoading = z;
        this.removedString = resources.getString(R.string.sec_controls_removed);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            CharSequence structure = ((ControlStatus) next).control.getStructure();
            CharSequence charSequence = structure != null ? structure : "";
            Object obj = linkedHashMap2.get(charSequence);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap2.put(charSequence, obj);
            }
            ((List) obj).add(next);
        }
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            CharSequence charSequence2 = (CharSequence) entry.getKey();
            List list3 = (List) entry.getValue();
            HashSet hashSet = new HashSet();
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                hashSet.add(((ControlStatus) it2.next()).control.getControlId());
            }
            Object obj2 = linkedHashMap.get(charSequence2);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(charSequence2, obj2);
            }
            List list4 = (List) obj2;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : list2) {
                if (hashSet.contains((String) obj3)) {
                    arrayList2.add(obj3);
                }
            }
            list4.addAll(arrayList2);
        }
        this.favoriteIds = linkedHashMap;
        this.favoriteControlChangeCallback = new StructureModel.StructureModelCallback() { // from class: com.android.systemui.controls.management.model.AllStructureModel$favoriteControlChangeCallback$1
            @Override // com.android.systemui.controls.management.model.StructureModel.StructureModelCallback
            public final void onControlInfoChange(ControlInfoForStructure controlInfoForStructure) {
                CharSequence charSequence3 = controlInfoForStructure.structureName;
                String str = controlInfoForStructure.controlId;
                boolean z2 = controlInfoForStructure.favorite;
                AllStructureModel allStructureModel = AllStructureModel.this;
                if (z2) {
                    if (!((LinkedHashMap) allStructureModel.favoriteIds).keySet().contains(charSequence3)) {
                        allStructureModel.favoriteIds.put(charSequence3, new ArrayList());
                    }
                    ((List) MapsKt__MapsKt.getValue(charSequence3, allStructureModel.favoriteIds)).add(str);
                } else {
                    List list5 = (List) ((LinkedHashMap) allStructureModel.favoriteIds).get(charSequence3);
                    if (list5 != null) {
                        list5.remove(str);
                    }
                    if (!allStructureModel.favoriteIds.containsKey(charSequence3)) {
                        allStructureModel.favoriteIds.remove(charSequence3);
                    }
                }
                allStructureModel.favoriteControlChangeMainCallback.onControlInfoChange(controlInfoForStructure);
            }
        };
        List list5 = this.controls;
        ArrayList arrayList3 = new ArrayList();
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.control_management_list_padding);
        String string = this.resources.getString(R.string.controls_favorite_other_structure_zone_header);
        String string2 = this.resources.getString(R.string.controls_add_controls_sub_title);
        List list6 = list5;
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : list6) {
            if (((ControlStatus) obj4).removed) {
                arrayList4.add(obj4);
            }
        }
        List minus = CollectionsKt___CollectionsKt.minus((Iterable) list6, (Iterable) CollectionsKt___CollectionsKt.toSet(arrayList4));
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Object obj5 : minus) {
            String structure2 = ((ControlStatus) obj5).control.getStructure();
            structure2 = structure2 == null ? "" : structure2;
            Object obj6 = linkedHashMap3.get(structure2);
            if (obj6 == null) {
                obj6 = new ArrayList();
                linkedHashMap3.put(structure2, obj6);
            }
            ((List) obj6).add(obj5);
        }
        if (this.isLoading) {
            arrayList3.add(new LoadingWrapper(string2));
        } else {
            arrayList3.add(new SubtitleWrapper(string2));
            int i = 0;
            if (!arrayList4.isEmpty()) {
                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                int size = arrayList4.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj7 = arrayList4.get(i2);
                    i2++;
                    arrayList5.add(((ControlStatus) obj7).control.getControlId());
                }
                AllControlsModel allControlsModel = new AllControlsModel(this.resources, this.removedString, arrayList4, arrayList5, string, true, this.favoriteControlChangeCallback);
                arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                arrayList3.add(new ControlWrapper(this.removedString, allControlsModel, null, false, 12, null));
            }
            AllControlsModel allControlsModel2 = null;
            for (CharSequence charSequence3 : linkedHashMap3.keySet()) {
                List list7 = (List) MapsKt__MapsKt.getValue(charSequence3, linkedHashMap3);
                List list8 = (List) ((LinkedHashMap) this.favoriteIds).get(charSequence3);
                if (list8 != null) {
                    List list9 = list8;
                    arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list9, 10));
                    Iterator it3 = list9.iterator();
                    while (it3.hasNext()) {
                        arrayList.add((String) it3.next());
                    }
                } else {
                    arrayList = new ArrayList();
                }
                AllControlsModel allControlsModel3 = new AllControlsModel(this.resources, charSequence3.toString(), list7, arrayList, string, (linkedHashMap3.keySet().size() == 1 && TextUtils.isEmpty(charSequence3)) ? false : true, this.favoriteControlChangeCallback);
                if (TextUtils.isEmpty(charSequence3)) {
                    allControlsModel2 = allControlsModel3;
                } else {
                    arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                    arrayList3.add(new ControlWrapper(charSequence3, allControlsModel3, null, false, 12, null));
                }
            }
            if (allControlsModel2 != null) {
                int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.control_management_list_first_top_padding);
                ArrayList arrayList6 = new ArrayList();
                int size2 = arrayList3.size();
                while (i < size2) {
                    Object obj8 = arrayList3.get(i);
                    i++;
                    if (obj8 instanceof ControlWrapper) {
                        arrayList6.add(obj8);
                    }
                }
                if (arrayList6.isEmpty()) {
                    List list10 = allControlsModel2.controls;
                    if (!(list10 instanceof Collection) || !list10.isEmpty()) {
                        Iterator it4 = list10.iterator();
                        while (it4.hasNext()) {
                            if (((ControlStatus) it4.next()).control.getZone() != null) {
                                arrayList3.add(new PaddingWrapper(dimensionPixelSize2));
                                break;
                            }
                        }
                    }
                }
                arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                arrayList3.add(new ControlWrapper(allControlsModel2.categoryHeader, allControlsModel2, string, allControlsModel2.needCategoryHeader));
            }
        }
        this.elements = arrayList3;
    }

    @Override // com.android.systemui.controls.management.model.StructureModel
    public final List getElements() {
        return this.elements;
    }

    public final List getFavorites() {
        ControlInfo controlInfo;
        Object obj;
        List flatten = CollectionsKt__IterablesKt.flatten(((LinkedHashMap) this.favoriteIds).values());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) flatten;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList2.get(i);
            i++;
            String str = (String) obj2;
            Iterator it = this.controls.iterator();
            while (true) {
                controlInfo = null;
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
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
}
