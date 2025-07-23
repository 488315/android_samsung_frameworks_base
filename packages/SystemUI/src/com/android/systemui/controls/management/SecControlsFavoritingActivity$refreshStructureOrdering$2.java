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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        SecControlsFavoritingActivity secControlsFavoritingActivity = this.this$0;
        AllStructureModel allStructureModel = this.$model;
        List list = this.$orderList;
        List list2 = allStructureModel.elements;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list2;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            if (obj instanceof ControlWrapper) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList.get(i3);
            i3++;
            if (!Intrinsics.areEqual(((ControlWrapper) obj2).structureName, allStructureModel.removedString)) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size3 = arrayList3.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj3 = arrayList3.get(i4);
            i4++;
            ControlWrapper controlWrapper = (ControlWrapper) obj3;
            arrayList4.add(Integer.valueOf(((ArrayList) allStructureModel.elements).indexOf(controlWrapper)));
            linkedHashMap.put(controlWrapper.structureName, controlWrapper);
        }
        ((ArrayList) allStructureModel.elements).removeAll(arrayList3);
        ArrayList arrayList5 = new ArrayList(list);
        ArrayList arrayList6 = new ArrayList();
        int size4 = arrayList3.size();
        int i5 = 0;
        while (i5 < size4) {
            Object obj4 = arrayList3.get(i5);
            i5++;
            if (!arrayList5.contains(((ControlWrapper) obj4).structureName)) {
                arrayList6.add(obj4);
            }
        }
        int size5 = arrayList6.size();
        int i6 = 0;
        while (i6 < size5) {
            Object obj5 = arrayList6.get(i6);
            i6++;
            ControlWrapper controlWrapper2 = (ControlWrapper) obj5;
            List list3 = controlWrapper2.controlsModel.controls;
            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                Iterator it = list3.iterator();
                while (it.hasNext()) {
                    if (((ControlStatus) it.next()).control.getCustomControl().getLayoutType() == 1) {
                        arrayList5.add(0, controlWrapper2.structureName);
                        Log.d("StructureModel", "changeStructureOrder SmallType Reorder");
                        break;
                    }
                }
            }
            arrayList5.add(controlWrapper2.structureName);
        }
        int size6 = arrayList5.size();
        int i7 = 0;
        int i8 = 0;
        while (i8 < size6) {
            Object obj6 = arrayList5.get(i8);
            i8++;
            ControlWrapper controlWrapper3 = (ControlWrapper) linkedHashMap.get((CharSequence) obj6);
            if (controlWrapper3 != null && i7 < arrayList4.size()) {
                ((ArrayList) allStructureModel.elements).add(((Number) arrayList4.get(i7)).intValue(), controlWrapper3);
                i7++;
            }
        }
        List list4 = allStructureModel.elements;
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = (ArrayList) list4;
        int size7 = arrayList8.size();
        int i9 = 0;
        while (i9 < size7) {
            Object obj7 = arrayList8.get(i9);
            i9++;
            if (obj7 instanceof ControlWrapper) {
                arrayList7.add(obj7);
            }
        }
        ArrayList arrayList9 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList7, 10));
        int size8 = arrayList7.size();
        while (i < size8) {
            Object obj8 = arrayList7.get(i);
            i++;
            arrayList9.add(((ControlWrapper) obj8).structureName);
        }
        Log.d("StructureModel", "changeStructureOrder after=" + arrayList9 + "}");
        secControlsFavoritingActivity.currentOrder = arrayList5;
        this.$update.invoke();
        SecStructureAdapter secStructureAdapter = this.this$0.structureAdapter;
        if (secStructureAdapter == null) {
            secStructureAdapter = null;
        }
        secStructureAdapter.notifyDataSetChanged();
    }
}
