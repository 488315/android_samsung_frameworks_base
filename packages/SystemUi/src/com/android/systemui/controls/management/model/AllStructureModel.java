package com.android.systemui.controls.management.model;

import android.content.res.Resources;
import android.service.controls.Control;
import android.text.TextUtils;
import com.android.systemui.BasicRune;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AllStructureModel implements StructureModel {
    public final List controls;
    public final List elements;
    public final AllStructureModel$favoriteControlChangeCallback$1 favoriteControlChangeCallback;
    public final StructureModel.StructureModelCallback favoriteControlChangeMainCallback;
    public final Map favoriteIds;
    public final boolean isLoading;
    public final String removedString;
    public final Resources resources;

    /* JADX WARN: Code restructure failed: missing block: B:124:0x02c7, code lost:
    
        if (r12 != false) goto L110;
     */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.controls.management.model.AllStructureModel$favoriteControlChangeCallback$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AllStructureModel(Resources resources, List<ControlStatus> list, List<String> list2, StructureModel.StructureModelCallback structureModelCallback, boolean z) {
        int i;
        boolean z2;
        ArrayList arrayList;
        this.resources = resources;
        this.controls = list;
        this.favoriteControlChangeMainCallback = structureModelCallback;
        this.isLoading = z;
        this.removedString = resources.getString(R.string.controls_custom_removed);
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
                boolean z3 = controlInfoForStructure.favorite;
                AllStructureModel allStructureModel = AllStructureModel.this;
                CharSequence charSequence3 = controlInfoForStructure.structureName;
                String str = controlInfoForStructure.controlId;
                if (z3) {
                    boolean contains = ((LinkedHashMap) allStructureModel.favoriteIds).keySet().contains(charSequence3);
                    Map map = allStructureModel.favoriteIds;
                    if (!contains) {
                        map.put(charSequence3, new ArrayList());
                    }
                    ((List) MapsKt__MapsKt.getValue(map, charSequence3)).add(str);
                } else {
                    List list5 = (List) ((LinkedHashMap) allStructureModel.favoriteIds).get(charSequence3);
                    if (list5 != null) {
                        list5.remove(str);
                    }
                    Map map2 = allStructureModel.favoriteIds;
                    if (!map2.containsKey(charSequence3)) {
                        map2.remove(charSequence3);
                    }
                }
                allStructureModel.favoriteControlChangeMainCallback.onControlInfoChange(controlInfoForStructure);
            }
        };
        List list5 = this.controls;
        ArrayList arrayList3 = new ArrayList();
        Resources resources2 = this.resources;
        int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.controls_custom_management_list_padding);
        String string = resources2.getString(R.string.controls_favorite_other_structure_zone_header);
        String string2 = resources2.getString(R.string.controls_add_controls_subtitle);
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : list5) {
            if (((ControlStatus) obj4).removed) {
                arrayList4.add(obj4);
            }
        }
        List minus = CollectionsKt___CollectionsKt.minus((Iterable) list5, (Iterable) arrayList4);
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
        if (BasicRune.CONTROLS_LOADING_DEVICES && this.isLoading) {
            arrayList3.add(new LoadingWrapper(string2));
        } else {
            arrayList3.add(new SubtitleWrapper(string2));
            if (!arrayList4.isEmpty()) {
                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
                Iterator it3 = arrayList4.iterator();
                while (it3.hasNext()) {
                    arrayList5.add(((ControlStatus) it3.next()).getControlId());
                }
                Resources resources3 = this.resources;
                String str = this.removedString;
                i = 10;
                AllControlsModel allControlsModel = new AllControlsModel(resources3, str, arrayList4, arrayList5, string, true, this.favoriteControlChangeCallback);
                arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                arrayList3.add(new ControlWrapper(str, allControlsModel, null, false, 12, null));
            } else {
                i = 10;
            }
            AllControlsModel allControlsModel2 = null;
            for (CharSequence charSequence3 : linkedHashMap3.keySet()) {
                List list6 = (List) MapsKt__MapsKt.getValue(linkedHashMap3, charSequence3);
                List list7 = (List) this.favoriteIds.get(charSequence3);
                if (list7 != null) {
                    ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list7, i));
                    Iterator it4 = list7.iterator();
                    while (it4.hasNext()) {
                        arrayList6.add((String) it4.next());
                    }
                    arrayList = arrayList6;
                } else {
                    arrayList = new ArrayList();
                }
                AllControlsModel allControlsModel3 = allControlsModel2;
                AllControlsModel allControlsModel4 = new AllControlsModel(this.resources, charSequence3.toString(), list6, arrayList, string, (linkedHashMap3.keySet().size() == 1 && TextUtils.isEmpty(charSequence3)) ? false : true, this.favoriteControlChangeCallback);
                if (TextUtils.isEmpty(charSequence3)) {
                    allControlsModel2 = allControlsModel4;
                } else {
                    arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                    arrayList3.add(new ControlWrapper(charSequence3, allControlsModel4, null, false, 12, null));
                    allControlsModel2 = allControlsModel3;
                }
                i = 10;
            }
            AllControlsModel allControlsModel5 = allControlsModel2;
            boolean z3 = true;
            if (allControlsModel5 != null) {
                int dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.controls_custom_management_list_first_top_padding);
                ArrayList arrayList7 = new ArrayList();
                Iterator it5 = arrayList3.iterator();
                while (it5.hasNext()) {
                    Object next2 = it5.next();
                    if (next2 instanceof ControlWrapper) {
                        arrayList7.add(next2);
                    }
                }
                if (arrayList7.isEmpty()) {
                    List list8 = allControlsModel5.controls;
                    if (!(list8 instanceof Collection) || !list8.isEmpty()) {
                        Iterator it6 = list8.iterator();
                        while (it6.hasNext()) {
                            if (((ControlStatus) it6.next()).control.getZone() != null) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                    z2 = false;
                }
                z3 = false;
                if (z3) {
                    arrayList3.add(new PaddingWrapper(dimensionPixelSize2));
                } else {
                    arrayList3.add(new PaddingWrapper(dimensionPixelSize));
                }
                arrayList3.add(new ControlWrapper(allControlsModel5.categoryHeader, allControlsModel5, string, allControlsModel5.needCategoryHeader));
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
        Iterator it = ((ArrayList) flatten).iterator();
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
}
