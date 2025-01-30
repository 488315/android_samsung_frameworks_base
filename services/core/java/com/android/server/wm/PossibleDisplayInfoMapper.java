package com.android.server.wm;

import android.hardware.display.DisplayManagerInternal;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.DisplayInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class PossibleDisplayInfoMapper {
  public final SparseArray mDisplayInfos = new SparseArray();
  public final DisplayManagerInternal mDisplayManagerInternal;

  public PossibleDisplayInfoMapper(DisplayManagerInternal displayManagerInternal) {
    this.mDisplayManagerInternal = displayManagerInternal;
  }

  public List getPossibleDisplayInfos(int i) {
    updatePossibleDisplayInfos(i);
    if (!this.mDisplayInfos.contains(i)) {
      return new ArrayList();
    }
    return List.copyOf((Collection) this.mDisplayInfos.get(i));
  }

  public void updatePossibleDisplayInfos(int i) {
    updateDisplayInfos(this.mDisplayManagerInternal.getPossibleDisplayInfo(i));
  }

  public void removePossibleDisplayInfos(int i) {
    this.mDisplayInfos.remove(i);
  }

  public final void updateDisplayInfos(Set set) {
    this.mDisplayInfos.clear();
    Iterator it = set.iterator();
    while (it.hasNext()) {
      DisplayInfo displayInfo = (DisplayInfo) it.next();
      Set set2 = (Set) this.mDisplayInfos.get(displayInfo.displayId, new ArraySet());
      set2.add(displayInfo);
      this.mDisplayInfos.put(displayInfo.displayId, set2);
    }
  }
}
