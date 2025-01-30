package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda13 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumePanelRow f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda13(VolumePanelRow volumePanelRow, int i) {
        this.$r8$classId = i;
        this.f$0 = volumePanelRow;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((VolumeStreamState) obj).getStreamType() != this.f$0.getStreamType()) {
                    break;
                }
                break;
            case 1:
                if (((Integer) obj).intValue() != this.f$0.getStreamType()) {
                    break;
                }
                break;
            case 2:
                if (((Integer) obj).intValue() != this.f$0.getStreamType()) {
                    break;
                }
                break;
            default:
                if (((VolumeStreamState) obj).getStreamType() != this.f$0.getStreamType()) {
                    break;
                }
                break;
        }
        return false;
    }
}
