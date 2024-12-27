package com.android.systemui.cover;

import android.util.Log;
import com.android.systemui.cover.CoverScreenManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class CoverScreenManager$DisplayWindowListenerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CoverScreenManager.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ CoverScreenManager$DisplayWindowListenerImpl$$ExternalSyntheticLambda0(CoverScreenManager.DisplayWindowListenerImpl displayWindowListenerImpl, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayWindowListenerImpl;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CoverScreenManager.DisplayWindowListenerImpl displayWindowListenerImpl = this.f$0;
                int i = this.f$1;
                CoverScreenManager coverScreenManager = displayWindowListenerImpl.this$0;
                coverScreenManager.getClass();
                if (i == 4) {
                    Log.d("CoverScreenManager", "onDisplayAdded : cover display added");
                    coverScreenManager.mHandler.sendEmptyMessage(1001);
                    break;
                }
                break;
            default:
                CoverScreenManager.DisplayWindowListenerImpl displayWindowListenerImpl2 = this.f$0;
                int i2 = this.f$1;
                CoverScreenManager coverScreenManager2 = displayWindowListenerImpl2.this$0;
                if (i2 != 4) {
                    coverScreenManager2.getClass();
                    break;
                } else {
                    CoverScreenManager.AnonymousClass2 anonymousClass2 = coverScreenManager2.mHandler;
                    if (anonymousClass2.hasMessages(1000)) {
                        Log.d("CoverScreenManager", "onDisplayRemoved : cover display removed");
                        anonymousClass2.removeMessages(1000);
                        anonymousClass2.sendEmptyMessage(1000);
                        break;
                    }
                }
                break;
        }
    }
}
