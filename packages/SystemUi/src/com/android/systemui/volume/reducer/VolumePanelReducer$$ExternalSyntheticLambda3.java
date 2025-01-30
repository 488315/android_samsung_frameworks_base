package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda3(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return Boolean.valueOf(volumePanelRow.getRealLevel() == VolumePanelReducer.getImpliedLevel(volumePanelRow.getStreamType(), volumePanelRow.getLevelMax(), this.f$0));
            case 1:
                VolumePanelRow volumePanelRow2 = (VolumePanelRow) obj;
                return Boolean.valueOf(volumePanelRow2.getRealLevel() != VolumePanelReducer.getImpliedLevel(volumePanelRow2.getStreamType(), volumePanelRow2.getLevelMax(), this.f$0));
            case 2:
                VolumePanelRow volumePanelRow3 = (VolumePanelRow) obj;
                return (volumePanelRow3.isVisible() && volumePanelRow3.getStreamType() == 10) ? new VolumePanelRow.Builder(volumePanelRow3).priority(this.f$0).build() : volumePanelRow3;
            case 3:
                return new VolumePanelRow.Builder().setStreamType(((VolumeStreamState) obj).getStreamType()).isImportant(true).isDynamic(true).originalPriority(this.f$0).build();
            default:
                VolumePanelRow volumePanelRow4 = (VolumePanelRow) obj;
                return volumePanelRow4.getStreamType() == this.f$0 ? new VolumePanelRow.Builder(volumePanelRow4).isActiveShow(true).build() : volumePanelRow4;
        }
    }
}
