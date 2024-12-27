package com.android.systemui.controls.management;

import android.util.Log;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.management.adapter.SecStructureAdapter;
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

public final class SecControlsFavoritingActivity$refreshStructureOrdering$2 implements Runnable {
    public final /* synthetic */ AllStructureModel $model;
    public final /* synthetic */ List $orderList;
    public final /* synthetic */ Function0 $update;
    public final /* synthetic */ SecControlsFavoritingActivity this$0;

    public SecControlsFavoritingActivity$refreshStructureOrdering$2(SecControlsFavoritingActivity secControlsFavoritingActivity, AllStructureModel allStructureModel, List<? extends CharSequence> list, Function0 function0) {
        this.this$0 = secControlsFavoritingActivity;
        this.$model = allStructureModel;
        this.$orderList = list;
        this.$update = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        SecControlsFavoritingActivity secControlsFavoritingActivity = this.this$0;
        AllStructureModel allStructureModel = this.$model;
        List list = this.$orderList;
        List list2 = allStructureModel.elements;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list2).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof ControlWrapper) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next2 = it2.next();
            if (!Intrinsics.areEqual(((ControlWrapper) next2).structureName, allStructureModel.removedString)) {
                arrayList2.add(next2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            ControlWrapper controlWrapper = (ControlWrapper) it3.next();
            arrayList3.add(Integer.valueOf(((ArrayList) allStructureModel.elements).indexOf(controlWrapper)));
            linkedHashMap.put(controlWrapper.structureName, controlWrapper);
        }
        ((ArrayList) allStructureModel.elements).removeAll(arrayList2);
        ArrayList arrayList4 = new ArrayList(list);
        ArrayList arrayList5 = new ArrayList();
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            Object next3 = it4.next();
            if (!arrayList4.contains(((ControlWrapper) next3).structureName)) {
                arrayList5.add(next3);
            }
        }
        Iterator it5 = arrayList5.iterator();
        while (true) {
            i = 0;
            if (!it5.hasNext()) {
                break;
            }
            ControlWrapper controlWrapper2 = (ControlWrapper) it5.next();
            List list3 = controlWrapper2.controlsModel.controls;
            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                Iterator it6 = list3.iterator();
                while (it6.hasNext()) {
                    if (((ControlStatus) it6.next()).control.getCustomControl().getLayoutType() == 1) {
                        arrayList4.add(0, controlWrapper2.structureName);
                        Log.d("StructureModel", "changeStructureOrder SmallType Reorder");
                        break;
                    }
                }
            }
            arrayList4.add(controlWrapper2.structureName);
        }
        Iterator it7 = arrayList4.iterator();
        while (it7.hasNext()) {
            ControlWrapper controlWrapper3 = (ControlWrapper) linkedHashMap.get((CharSequence) it7.next());
            if (controlWrapper3 != null && i < arrayList3.size()) {
                ((ArrayList) allStructureModel.elements).add(((Number) arrayList3.get(i)).intValue(), controlWrapper3);
                i++;
            }
        }
        List list4 = allStructureModel.elements;
        ArrayList arrayList6 = new ArrayList();
        Iterator it8 = ((ArrayList) list4).iterator();
        while (it8.hasNext()) {
            Object next4 = it8.next();
            if (next4 instanceof ControlWrapper) {
                arrayList6.add(next4);
            }
        }
        ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
        Iterator it9 = arrayList6.iterator();
        while (it9.hasNext()) {
            arrayList7.add(((ControlWrapper) it9.next()).structureName);
        }
        Log.d("StructureModel", "changeStructureOrder after=" + arrayList7 + "}");
        secControlsFavoritingActivity.currentOrder = arrayList4;
        this.$update.invoke();
        SecStructureAdapter secStructureAdapter = this.this$0.structureAdapter;
        if (secStructureAdapter == null) {
            secStructureAdapter = null;
        }
        secStructureAdapter.notifyDataSetChanged();
    }
}
