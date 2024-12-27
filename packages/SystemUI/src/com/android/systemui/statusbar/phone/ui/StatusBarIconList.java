package com.android.systemui.statusbar.phone.ui;

import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StatusBarIconList {
    public final ArrayList mSlots;
    public final List mViewOnlySlots;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Slot {
        public StatusBarIconHolder mHolder;
        public final String mName;
        public ArrayList mSubSlots;

        public Slot(String str, StatusBarIconHolder statusBarIconHolder) {
            this.mName = str;
            this.mHolder = statusBarIconHolder;
        }

        public void clear() {
            this.mHolder = null;
            if (this.mSubSlots != null) {
                this.mSubSlots = null;
            }
        }

        public final StatusBarIconHolder getHolderForTag(int i) {
            if (i == 0) {
                return this.mHolder;
            }
            ArrayList arrayList = this.mSubSlots;
            if (arrayList == null) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it.next();
                if (statusBarIconHolder.tag == i) {
                    return statusBarIconHolder;
                }
            }
            return null;
        }

        public final List getHolderListInViewOrder() {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = this.mSubSlots;
            if (arrayList2 != null) {
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    arrayList.add((StatusBarIconHolder) this.mSubSlots.get(size));
                }
            }
            StatusBarIconHolder statusBarIconHolder = this.mHolder;
            if (statusBarIconHolder != null) {
                arrayList.add(statusBarIconHolder);
            }
            return arrayList;
        }

        public final int getIndexForTag(int i) {
            for (int i2 = 0; i2 < this.mSubSlots.size(); i2++) {
                if (((StatusBarIconHolder) this.mSubSlots.get(i2)).tag == i) {
                    return i2;
                }
            }
            return -1;
        }

        public final boolean hasIconsInSlot() {
            if (this.mHolder != null) {
                return true;
            }
            ArrayList arrayList = this.mSubSlots;
            return arrayList != null && arrayList.size() > 0;
        }

        public final void removeForTag(int i) {
            if (i == 0) {
                this.mHolder = null;
                return;
            }
            int indexForTag = getIndexForTag(i);
            if (indexForTag != -1) {
                this.mSubSlots.remove(indexForTag);
            }
        }

        public final String toString() {
            String str;
            StatusBarIconHolder statusBarIconHolder = this.mHolder;
            if (this.mSubSlots == null) {
                str = "";
            } else {
                str = "| " + this.mSubSlots.size() + " subSlots: " + ((String) this.mSubSlots.stream().map(new StatusBarIconList$Slot$$ExternalSyntheticLambda0()).collect(Collectors.joining("|")));
            }
            return String.format("(%s) holder=%s %s", this.mName, statusBarIconHolder, str);
        }
    }

    public StatusBarIconList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        this.mSlots = arrayList;
        this.mViewOnlySlots = Collections.unmodifiableList(arrayList);
        for (String str : strArr) {
            this.mSlots.add(new Slot(str, null));
        }
    }

    public final int findOrInsertSlot(String str) {
        int size = this.mSlots.size();
        for (int i = 0; i < size; i++) {
            if (((Slot) this.mSlots.get(i)).mName.equals(str)) {
                return i;
            }
        }
        this.mSlots.add(0, new Slot(str, null));
        return 0;
    }

    public final StatusBarIconHolder getIconHolder(int i, String str) {
        return ((Slot) this.mSlots.get(findOrInsertSlot(str))).getHolderForTag(i);
    }

    public final Slot getSlot(String str) {
        return (Slot) this.mSlots.get(findOrInsertSlot(str));
    }

    public final int getViewIndex(int i, String str) {
        int findOrInsertSlot = findOrInsertSlot(str);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= findOrInsertSlot) {
                break;
            }
            Slot slot = (Slot) this.mSlots.get(i3);
            if (slot.hasIconsInSlot()) {
                int i5 = slot.mHolder == null ? 0 : 1;
                ArrayList arrayList = slot.mSubSlots;
                if (arrayList != null) {
                    i5 += arrayList.size();
                }
                i4 += i5;
            }
            i3++;
        }
        Slot slot2 = (Slot) this.mSlots.get(findOrInsertSlot);
        ArrayList arrayList2 = slot2.mSubSlots;
        if (arrayList2 != null) {
            i2 = arrayList2.size();
            if (i != 0) {
                i2 = (i2 - slot2.getIndexForTag(i)) - 1;
            }
        }
        return i4 + i2;
    }
}
