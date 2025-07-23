package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.Comparator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda26 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Integer.compare(((VolumePanelRow) obj).getPriority(), ((VolumePanelRow) obj2).getPriority());
    }
}
