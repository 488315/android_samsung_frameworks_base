package com.android.systemui.statusbar.policy;

import android.os.Parcelable;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ZenModeControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Parcelable f$0;

    public /* synthetic */ ZenModeControllerImpl$$ExternalSyntheticLambda0(Parcelable parcelable, int i) {
        this.$r8$classId = i;
        this.f$0 = parcelable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ZenModeController.Callback) obj).onConfigChanged$1();
                break;
            case 1:
                ((ZenModeController.Callback) obj).getClass();
                break;
            default:
                ((ZenModeController.Callback) obj).getClass();
                break;
        }
    }
}
