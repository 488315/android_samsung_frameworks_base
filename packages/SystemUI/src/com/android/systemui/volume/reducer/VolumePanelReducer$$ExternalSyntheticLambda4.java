package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Function;

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
