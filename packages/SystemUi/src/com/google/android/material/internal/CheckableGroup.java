package com.google.android.material.internal;

import com.google.android.material.chip.ChipGroup;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CheckableGroup {
    public final Map checkables = new HashMap();
    public final Set checkedIds = new HashSet();
    public ChipGroup.C42481 onCheckedStateChangeListener;
    public boolean selectionRequired;
    public boolean singleSelection;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.android.material.internal.CheckableGroup$1 */
    public final class C42941 {
        public C42941() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkInternal(MaterialCheckable materialCheckable) {
        int i;
        MaterialCheckable materialCheckable2;
        int id = materialCheckable.getId();
        Set set = this.checkedIds;
        if (((HashSet) set).contains(Integer.valueOf(id))) {
            return false;
        }
        Map map = this.checkables;
        if (this.singleSelection) {
            HashSet hashSet = (HashSet) set;
            if (!hashSet.isEmpty()) {
                i = ((Integer) hashSet.iterator().next()).intValue();
                materialCheckable2 = (MaterialCheckable) ((HashMap) map).get(Integer.valueOf(i));
                if (materialCheckable2 != null) {
                    uncheckInternal(materialCheckable2, false);
                }
                boolean add = ((HashSet) set).add(Integer.valueOf(id));
                if (!materialCheckable.isChecked()) {
                    materialCheckable.setChecked(true);
                }
                return add;
            }
        }
        i = -1;
        materialCheckable2 = (MaterialCheckable) ((HashMap) map).get(Integer.valueOf(i));
        if (materialCheckable2 != null) {
        }
        boolean add2 = ((HashSet) set).add(Integer.valueOf(id));
        if (!materialCheckable.isChecked()) {
        }
        return add2;
    }

    public final void onCheckedStateChanged() {
        ChipGroup.C42481 c42481 = this.onCheckedStateChangeListener;
        if (c42481 != null) {
            new HashSet(this.checkedIds);
            int i = ChipGroup.$r8$clinit;
            ChipGroup.this.getClass();
        }
    }

    public final boolean uncheckInternal(MaterialCheckable materialCheckable, boolean z) {
        int id = materialCheckable.getId();
        Set set = this.checkedIds;
        if (!((HashSet) set).contains(Integer.valueOf(id))) {
            return false;
        }
        if (z && ((HashSet) set).size() == 1) {
            if (((HashSet) set).contains(Integer.valueOf(id))) {
                materialCheckable.setChecked(true);
                return false;
            }
        }
        boolean remove = ((HashSet) set).remove(Integer.valueOf(id));
        if (materialCheckable.isChecked()) {
            materialCheckable.setChecked(false);
        }
        return remove;
    }
}
