package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
        switch (this.$r8$classId) {
            case 0:
                if (volumePanelRow.getStreamType() == 20) {
                }
                break;
            case 1:
                if (volumePanelRow.getStreamType() == 2) {
                }
                break;
            default:
                if (!volumePanelRow.isVisible() || volumePanelRow.getStreamType() == 10) {
                }
                break;
        }
        return false;
    }
}
