package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                List list = (List) obj2;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                if (!volumePanelRow.isDynamic() || list.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda29(volumePanelRow, 3))) {
                }
                break;
            case 1:
                List list2 = (List) obj2;
                VolumeStreamState volumeStreamState = (VolumeStreamState) obj;
                if (!volumeStreamState.isDynamic() || !list2.stream().noneMatch(new VolumePanelReducer$$ExternalSyntheticLambda1(volumeStreamState, 2))) {
                }
                break;
            default:
                if (((VolumePanelRow) obj).getStreamType() == ((VolumeStreamState) obj2).getStreamType()) {
                }
                break;
        }
        return false;
    }
}
