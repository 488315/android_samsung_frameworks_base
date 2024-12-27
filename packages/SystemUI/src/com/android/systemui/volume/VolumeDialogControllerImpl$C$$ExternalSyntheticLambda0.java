package com.android.systemui.volume;

import com.android.systemui.plugins.VolumeDialogController;
import java.util.Map;

public final /* synthetic */ class VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Map.Entry f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Boolean f$2;

    public /* synthetic */ VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(Map.Entry entry, boolean z, Boolean bool, int i) {
        this.$r8$classId = i;
        this.f$0 = entry;
        this.f$1 = z;
        this.f$2 = bool;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Map.Entry entry = this.f$0;
                boolean z = this.f$1;
                ((VolumeDialogController.Callbacks) entry.getKey()).onCaptionEnabledStateChanged(Boolean.valueOf(z), this.f$2);
                break;
            default:
                Map.Entry entry2 = this.f$0;
                boolean z2 = this.f$1;
                ((VolumeDialogController.Callbacks) entry2.getKey()).onCaptionComponentStateChanged(Boolean.valueOf(z2), this.f$2);
                break;
        }
    }
}
