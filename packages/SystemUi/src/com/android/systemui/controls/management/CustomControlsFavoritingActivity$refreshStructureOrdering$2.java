package com.android.systemui.controls.management;

import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.adapter.CustomStructureAdapter;
import com.android.systemui.controls.management.model.AllStructureModel;
import com.android.systemui.controls.management.model.ControlWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsFavoritingActivity$refreshStructureOrdering$2 implements Runnable {
    public final /* synthetic */ AllStructureModel $model;
    public final /* synthetic */ List $orderList;
    public final /* synthetic */ Function0 $update;
    public final /* synthetic */ CustomControlsFavoritingActivity this$0;

    public CustomControlsFavoritingActivity$refreshStructureOrdering$2(CustomControlsFavoritingActivity customControlsFavoritingActivity, AllStructureModel allStructureModel, List<? extends CharSequence> list, Function0 function0) {
        this.this$0 = customControlsFavoritingActivity;
        this.$model = allStructureModel;
        this.$orderList = list;
        this.$update = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        boolean z;
        CustomControlsFavoritingActivity customControlsFavoritingActivity = this.this$0;
        AllStructureModel allStructureModel = this.$model;
        List list = this.$orderList;
        List list2 = allStructureModel.elements;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list2;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof ControlWrapper) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next2 = it2.next();
            if (!Intrinsics.areEqual(((ControlWrapper) next2).structureName, allStructureModel.removedString)) {
                arrayList3.add(next2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            ControlWrapper controlWrapper = (ControlWrapper) it3.next();
            arrayList4.add(Integer.valueOf(arrayList2.indexOf(controlWrapper)));
            linkedHashMap.put(controlWrapper.structureName, controlWrapper);
        }
        arrayList2.removeAll(arrayList3);
        ArrayList arrayList5 = new ArrayList(list);
        ArrayList arrayList6 = new ArrayList();
        Iterator it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            Object next3 = it4.next();
            if (!arrayList5.contains(((ControlWrapper) next3).structureName)) {
                arrayList6.add(next3);
            }
        }
        Iterator it5 = arrayList6.iterator();
        while (true) {
            i = 0;
            if (!it5.hasNext()) {
                break;
            }
            ControlWrapper controlWrapper2 = (ControlWrapper) it5.next();
            if (BasicRune.CONTROLS_SMALL_TYPE_NEW_STRUCTURE_ORDER_FIRST) {
                List list3 = controlWrapper2.controlsModel.controls;
                if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                    Iterator it6 = list3.iterator();
                    while (it6.hasNext()) {
                        z = true;
                        if (((ControlStatus) it6.next()).control.getCustomControl().getLayoutType() == 1) {
                            break;
                        }
                    }
                }
                z = false;
                CharSequence charSequence = controlWrapper2.structureName;
                if (z) {
                    arrayList5.add(0, charSequence);
                    Log.d("StructureModel", "changeStructureOrder SmallType Reorder");
                } else {
                    arrayList5.add(charSequence);
                }
            } else {
                arrayList5.add(controlWrapper2.structureName);
            }
        }
        Iterator it7 = arrayList5.iterator();
        while (it7.hasNext()) {
            ControlWrapper controlWrapper3 = (ControlWrapper) linkedHashMap.get((CharSequence) it7.next());
            if (controlWrapper3 != null && i < arrayList4.size()) {
                arrayList2.add(((Number) arrayList4.get(i)).intValue(), controlWrapper3);
                i++;
            }
        }
        ArrayList arrayList7 = new ArrayList();
        for (Object obj : arrayList2) {
            if (obj instanceof ControlWrapper) {
                arrayList7.add(obj);
            }
        }
        ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList7, 10));
        Iterator it8 = arrayList7.iterator();
        while (it8.hasNext()) {
            arrayList8.add(((ControlWrapper) it8.next()).structureName);
        }
        Log.d("StructureModel", "changeStructureOrder after=" + arrayList8 + "}");
        customControlsFavoritingActivity.currentOrder = arrayList5;
        this.$update.invoke();
        CustomStructureAdapter customStructureAdapter = this.this$0.customAdapter;
        if (customStructureAdapter == null) {
            customStructureAdapter = null;
        }
        customStructureAdapter.notifyDataSetChanged();
    }
}
