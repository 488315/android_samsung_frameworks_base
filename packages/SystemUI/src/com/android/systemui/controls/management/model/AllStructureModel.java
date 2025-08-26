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

    /* JADX WARN: Removed duplicated region for block: B:96:0x02be  */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.controls.management.model.AllStructureModel$favoriteControlChangeCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AllStructureModel(Resources resources, List<ControlStatus> list, List<String> list2, StructureModel.StructureModelCallback structureModelCallback, boolean z) throws Resources.NotFoundException {
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
            Object arrayList2 = linkedHashMap2.get(charSequence);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                linkedHashMap2.put(charSequence, arrayList2);
            }
            ((List) arrayList2).add(next);
        }
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            CharSequence charSequence2 = (CharSequence) entry.getKey();
            List list3 = (List) entry.getValue();
            HashSet hashSet = new HashSet();
            Iterator it2 = list3.iterator();
            while (it2.hasNext()) {
                hashSet.add(((ControlStatus) it2.next()).control.getControlId());
            }
            Object arrayList3 = linkedHashMap.get(charSequence2);
            if (arrayList3 == null) {
                arrayList3 = new ArrayList();
                linkedHashMap.put(charSequence2, arrayList3);
            }
            List list4 = (List) arrayList3;
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : list2) {
                if (hashSet.contains((String) obj)) {
                    arrayList4.add(obj);
                }
            }
            list4.addAll(arrayList4);
        }
        this.favoriteIds = linkedHashMap;
        this.favoriteControlChangeCallback = new StructureModel.StructureModelCallback() { // from class: com.android.systemui.controls.management.model.AllStructureModel$favoriteControlChangeCallback$1
            @Override // com.android.systemui.controls.management.model.StructureModel.StructureModelCallback
            public final void onControlInfoChange(ControlInfoForStructure controlInfoForStructure) {
                CharSequence charSequence3 = controlInfoForStructure.structureName;
                String str = controlInfoForStructure.controlId;
                boolean z2 = controlInfoForStructure.favorite;
                AllStructureModel allStructureModel = this.this$0;
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
        ArrayList arrayList5 = new ArrayList();
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.control_management_list_padding);
        String string = this.resources.getString(R.string.controls_favorite_other_structure_zone_header);
        String string2 = this.resources.getString(R.string.controls_add_controls_sub_title);
        List list6 = list5;
        ArrayList arrayList6 = new ArrayList();
        for (Object obj2 : list6) {
            if (((ControlStatus) obj2).removed) {
                arrayList6.add(obj2);
            }
        }
        List listMinus = CollectionsKt___CollectionsKt.minus((Iterable) list6, (Iterable) CollectionsKt___CollectionsKt.toSet(arrayList6));
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Object obj3 : listMinus) {
            String structure2 = ((ControlStatus) obj3).control.getStructure();
            structure2 = structure2 == null ? "" : structure2;
            Object arrayList7 = linkedHashMap3.get(structure2);
            if (arrayList7 == null) {
                arrayList7 = new ArrayList();
                linkedHashMap3.put(structure2, arrayList7);
            }
            ((List) arrayList7).add(obj3);
        }
        if (this.isLoading) {
            arrayList5.add(new LoadingWrapper(string2));
        } else {
            arrayList5.add(new SubtitleWrapper(string2));
            int i = 0;
            if (!arrayList6.isEmpty()) {
                ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                int size = arrayList6.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj4 = arrayList6.get(i2);
                    i2++;
                    arrayList8.add(((ControlStatus) obj4).control.getControlId());
                }
                AllControlsModel allControlsModel = new AllControlsModel(this.resources, this.removedString, arrayList6, arrayList8, string, true, this.favoriteControlChangeCallback);
                arrayList5.add(new PaddingWrapper(dimensionPixelSize));
                arrayList5.add(new ControlWrapper(this.removedString, allControlsModel, null, false, 12, null));
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
                    arrayList5.add(new PaddingWrapper(dimensionPixelSize));
                    arrayList5.add(new ControlWrapper(charSequence3, allControlsModel3, null, false, 12, null));
                }
            }
            if (allControlsModel2 != null) {
                int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.control_management_list_first_top_padding);
                ArrayList arrayList9 = new ArrayList();
                int size2 = arrayList5.size();
                while (i < size2) {
                    Object obj5 = arrayList5.get(i);
                    i++;
                    if (obj5 instanceof ControlWrapper) {
                        arrayList9.add(obj5);
                    }
                }
                if (arrayList9.isEmpty()) {
                    List list10 = allControlsModel2.controls;
                    if ((list10 instanceof Collection) && list10.isEmpty()) {
                        arrayList5.add(new PaddingWrapper(dimensionPixelSize));
                        arrayList5.add(new ControlWrapper(allControlsModel2.categoryHeader, allControlsModel2, string, allControlsModel2.needCategoryHeader));
                    } else {
                        Iterator it4 = list10.iterator();
                        while (it4.hasNext()) {
                            if (((ControlStatus) it4.next()).control.getZone() != null) {
                                arrayList5.add(new PaddingWrapper(dimensionPixelSize2));
                                break;
                            }
                        }
                        arrayList5.add(new PaddingWrapper(dimensionPixelSize));
                        arrayList5.add(new ControlWrapper(allControlsModel2.categoryHeader, allControlsModel2, string, allControlsModel2.needCategoryHeader));
                    }
                }
            }
        }
        this.elements = arrayList5;
    }

    @Override // com.android.systemui.controls.management.model.StructureModel
    public final List getElements() {
        return this.elements;
    }

    public final List getFavorites() {
        ControlInfo controlInfoFromControl;
        Object next;
        List listFlatten = CollectionsKt__IterablesKt.flatten(((LinkedHashMap) this.favoriteIds).values());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) listFlatten;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String str = (String) obj;
            Iterator it = this.controls.iterator();
            while (true) {
                controlInfoFromControl = null;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Intrinsics.areEqual(((ControlStatus) next).control.getControlId(), str)) {
                    break;
                }
            }
            ControlStatus controlStatus = (ControlStatus) next;
            Control control = controlStatus != null ? controlStatus.control : null;
            if (control != null) {
                ControlInfo.Companion.getClass();
                controlInfoFromControl = ControlInfo.Companion.fromControl(control);
            }
            if (controlInfoFromControl != null) {
                arrayList.add(controlInfoFromControl);
            }
        }
        return arrayList;
    }
}
