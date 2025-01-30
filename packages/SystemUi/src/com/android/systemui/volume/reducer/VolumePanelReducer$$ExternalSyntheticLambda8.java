package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                List list = (List) this.f$0;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                if (!volumePanelRow.isDynamic() || list.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda13(volumePanelRow, 0))) {
                    break;
                }
                break;
            case 1:
                List list2 = (List) this.f$0;
                VolumeStreamState volumeStreamState = (VolumeStreamState) obj;
                if (volumeStreamState.isDynamic() && list2.stream().noneMatch(new VolumePanelReducer$$ExternalSyntheticLambda8(volumeStreamState, 2))) {
                    break;
                }
                break;
            default:
                if (((VolumePanelRow) obj).getStreamType() == ((VolumeStreamState) this.f$0).getStreamType()) {
                    break;
                }
                break;
        }
        return true;
    }
}
