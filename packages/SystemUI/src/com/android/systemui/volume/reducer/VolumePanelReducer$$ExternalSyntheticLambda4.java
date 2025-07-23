package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(volumePanelRow.getOriginalPriority());
            case 1:
                return new VolumePanelRow.Builder(volumePanelRow).isActiveShow(false).build();
            case 2:
                return Integer.valueOf(volumePanelRow.getPriority());
            default:
                return Boolean.valueOf(volumePanelRow.isTracking());
        }
    }
}
