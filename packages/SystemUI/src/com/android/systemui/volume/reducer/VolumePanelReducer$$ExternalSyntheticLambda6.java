package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Predicate;

public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda6(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
        switch (i) {
            case 0:
                if (volumePanelRow.getStreamType() == i2) {
                }
                break;
            case 1:
                if (volumePanelRow.getStreamType() == i2) {
                }
                break;
            case 2:
                if (volumePanelRow.getStreamType() == i2) {
                }
                break;
            default:
                if (volumePanelRow.getStreamType() == i2) {
                }
                break;
        }
        return false;
    }
}
