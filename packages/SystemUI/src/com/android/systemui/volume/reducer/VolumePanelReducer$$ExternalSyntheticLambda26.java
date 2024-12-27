package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.Comparator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda26 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(((VolumePanelRow) obj).getPriority(), ((VolumePanelRow) obj2).getPriority());
    }
}
