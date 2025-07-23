package com.google.android.material.internal;

import com.google.android.material.chip.ChipGroup;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CheckableGroup {
    public final Map checkables = new HashMap();
    public final Set checkedIds = new HashSet();
    public ChipGroup.AnonymousClass1 onCheckedStateChangeListener;
    public boolean selectionRequired;
    public boolean singleSelection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.material.internal.CheckableGroup$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public final boolean checkInternal(MaterialCheckable materialCheckable) {
        int id = materialCheckable.getId();
        if (((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
            return false;
        }
        MaterialCheckable materialCheckable2 = (MaterialCheckable) ((HashMap) this.checkables).get(Integer.valueOf((!this.singleSelection || ((HashSet) this.checkedIds).isEmpty()) ? -1 : ((Integer) ((HashSet) this.checkedIds).iterator().next()).intValue()));
        if (materialCheckable2 != null) {
            uncheckInternal(materialCheckable2, false);
        }
        boolean add = ((HashSet) this.checkedIds).add(Integer.valueOf(id));
        if (!materialCheckable.isChecked()) {
            materialCheckable.setChecked(true);
        }
        return add;
    }

    public final void onCheckedStateChanged() {
        ChipGroup.AnonymousClass1 anonymousClass1 = this.onCheckedStateChangeListener;
        if (anonymousClass1 != null) {
            new HashSet(this.checkedIds);
            int i = ChipGroup.$r8$clinit;
            ChipGroup.this.getClass();
        }
    }

    public final boolean uncheckInternal(MaterialCheckable materialCheckable, boolean z) {
        int id = materialCheckable.getId();
        if (!((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
            return false;
        }
        if (z && ((HashSet) this.checkedIds).size() == 1) {
            if (((HashSet) this.checkedIds).contains(Integer.valueOf(id))) {
                materialCheckable.setChecked(true);
                return false;
            }
        }
        boolean remove = ((HashSet) this.checkedIds).remove(Integer.valueOf(id));
        if (materialCheckable.isChecked()) {
            materialCheckable.setChecked(false);
        }
        return remove;
    }
}
