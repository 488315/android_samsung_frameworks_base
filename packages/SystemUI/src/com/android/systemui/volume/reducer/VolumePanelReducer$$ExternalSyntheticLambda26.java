package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.Comparator;

public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda26 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(((VolumePanelRow) obj).getPriority(), ((VolumePanelRow) obj2).getPriority());
    }
}
