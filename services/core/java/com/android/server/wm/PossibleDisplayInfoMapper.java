package com.android.server.wm;

import android.hardware.display.DisplayManagerInternal;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.DisplayInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class PossibleDisplayInfoMapper {
    public final SparseArray mDisplayInfos = new SparseArray();
    public final DisplayManagerInternal mDisplayManagerInternal;

    public PossibleDisplayInfoMapper(DisplayManagerInternal displayManagerInternal) {
        this.mDisplayManagerInternal = displayManagerInternal;
    }

    public final List getPossibleDisplayInfos(int i) {
        Set<DisplayInfo> possibleDisplayInfo =
                this.mDisplayManagerInternal.getPossibleDisplayInfo(i);
        this.mDisplayInfos.clear();
        for (DisplayInfo displayInfo : possibleDisplayInfo) {
            Set set = (Set) this.mDisplayInfos.get(displayInfo.displayId, new ArraySet());
            set.add(displayInfo);
            this.mDisplayInfos.put(displayInfo.displayId, set);
        }
        return !this.mDisplayInfos.contains(i)
                ? new ArrayList()
                : List.copyOf((Collection) this.mDisplayInfos.get(i));
    }
}
