package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelReducer$$ExternalSyntheticLambda6 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((VolumePanelRow) obj).getStreamType() != 2) {
                    break;
                }
                break;
            case 1:
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                if (!volumePanelRow.isVisible() || volumePanelRow.getStreamType() == 10) {
                    break;
                }
                break;
            default:
                if (((VolumePanelRow) obj).getStreamType() != 20) {
                    break;
                }
                break;
        }
        return false;
    }
}
