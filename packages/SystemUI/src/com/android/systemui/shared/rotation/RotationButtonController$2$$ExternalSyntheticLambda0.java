package com.android.systemui.shared.rotation;

import android.app.ActivityManager;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RotationButtonController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ RotationButtonController$2$$ExternalSyntheticLambda0(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RotationButtonController.AnonymousClass2 anonymousClass2 = (RotationButtonController.AnonymousClass2) this.f$0;
                anonymousClass2.this$0.onRotationWatcherChanged(this.f$1);
                break;
            default:
                final RotationButtonController.TaskStackListenerImpl taskStackListenerImpl = (RotationButtonController.TaskStackListenerImpl) this.f$0;
                final int i = this.f$1;
                taskStackListenerImpl.getClass();
                Optional.ofNullable(ActivityManagerWrapper.sInstance).map(new RotationButtonController$TaskStackListenerImpl$$ExternalSyntheticLambda1()).ifPresent(new Consumer() { // from class: com.android.systemui.shared.rotation.RotationButtonController$TaskStackListenerImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RotationButtonController.TaskStackListenerImpl taskStackListenerImpl2 = RotationButtonController.TaskStackListenerImpl.this;
                        int i2 = i;
                        taskStackListenerImpl2.getClass();
                        if (((ActivityManager.RunningTaskInfo) obj).id == i2) {
                            taskStackListenerImpl2.this$0.mMainThreadHandler.post(new RotationButtonController$$ExternalSyntheticLambda0(taskStackListenerImpl2, 4));
                        }
                    }
                });
                break;
        }
    }
}
