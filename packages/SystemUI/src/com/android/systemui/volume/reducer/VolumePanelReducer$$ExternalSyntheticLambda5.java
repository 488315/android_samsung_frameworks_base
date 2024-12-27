package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda5(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                return new VolumePanelRow.Builder().setStreamType(((VolumeStreamState) obj).getStreamType()).isImportant(true).isDynamic(true).originalPriority(i2).build();
            case 1:
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return volumePanelRow.getStreamType() == i2 ? new VolumePanelRow.Builder(volumePanelRow).isActiveShow(true).build() : volumePanelRow;
            case 2:
                VolumePanelRow volumePanelRow2 = (VolumePanelRow) obj;
                return (volumePanelRow2.isVisible() && volumePanelRow2.getStreamType() == 10) ? new VolumePanelRow.Builder(volumePanelRow2).priority(i2).build() : volumePanelRow2;
            case 3:
                VolumePanelRow volumePanelRow3 = (VolumePanelRow) obj;
                return Boolean.valueOf(volumePanelRow3.getRealLevel() == VolumePanelReducer.getImpliedLevel(volumePanelRow3.getStreamType(), volumePanelRow3.getLevelMax(), i2));
            default:
                VolumePanelRow volumePanelRow4 = (VolumePanelRow) obj;
                return Boolean.valueOf(volumePanelRow4.getRealLevel() != VolumePanelReducer.getImpliedLevel(volumePanelRow4.getStreamType(), volumePanelRow4.getLevelMax(), i2));
        }
    }
}
