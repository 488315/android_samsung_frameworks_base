package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda29 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumePanelRow f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda29(VolumePanelRow volumePanelRow, int i) {
        this.$r8$classId = i;
        this.f$0 = volumePanelRow;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        VolumePanelRow volumePanelRow = this.f$0;
        switch (i) {
            case 0:
                if (((VolumeStreamState) obj).getStreamType() == volumePanelRow.getStreamType()) {
                }
                break;
            case 1:
                if (((Integer) obj).intValue() == volumePanelRow.getStreamType()) {
                }
                break;
            case 2:
                if (((Integer) obj).intValue() == volumePanelRow.getStreamType()) {
                }
                break;
            default:
                if (((VolumeStreamState) obj).getStreamType() == volumePanelRow.getStreamType()) {
                }
                break;
        }
        return false;
    }
}
