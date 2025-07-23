package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.ManagedProfileController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ManagedProfileControllerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ManagedProfileController.Callback callback = (ManagedProfileController.Callback) obj;
        switch (this.$r8$classId) {
            case 0:
                callback.onManagedProfileRemoved();
                break;
            default:
                callback.onManagedProfileChanged();
                break;
        }
    }
}
