package com.android.systemui.cover;

import android.util.Log;
import com.android.systemui.cover.CoverScreenManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.cover.CoverScreenManager$DisplayWindowListenerImpl$$ExternalSyntheticLambda0 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1207x3990e23b implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CoverScreenManager.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ RunnableC1207x3990e23b(CoverScreenManager.DisplayWindowListenerImpl displayWindowListenerImpl, int i, int i2) {
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
                    CoverScreenManager.HandlerC12092 handlerC12092 = coverScreenManager2.mHandler;
                    if (handlerC12092.hasMessages(1000)) {
                        Log.d("CoverScreenManager", "onDisplayRemoved : cover display removed");
                        handlerC12092.removeMessages(1000);
                        handlerC12092.sendEmptyMessage(1000);
                        break;
                    }
                }
                break;
        }
    }
}
