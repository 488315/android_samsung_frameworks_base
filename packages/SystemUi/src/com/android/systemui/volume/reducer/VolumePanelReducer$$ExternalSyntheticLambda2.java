package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ VolumePanelReducer$$ExternalSyntheticLambda2(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((VolumePanelRow) obj).getStreamType() != this.f$0) {
                    break;
                }
                break;
            case 1:
                if (((VolumePanelRow) obj).getStreamType() != this.f$0) {
                    break;
                }
                break;
            case 2:
                if (((VolumePanelRow) obj).getStreamType() != this.f$0) {
                    break;
                }
                break;
            default:
                if (((VolumePanelRow) obj).getStreamType() != this.f$0) {
                    break;
                }
                break;
        }
        return false;
    }
}
