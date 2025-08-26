package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.ManagedProfileController;
import java.util.function.Consumer;

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
