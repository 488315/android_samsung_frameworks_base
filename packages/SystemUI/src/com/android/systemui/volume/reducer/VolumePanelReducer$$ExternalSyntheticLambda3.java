package com.android.systemui.volume.reducer;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
