package com.android.wm.shell.pip2.phone;

import android.graphics.Rect;
import com.android.wm.shell.ShellTaskOrganizer;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipTaskListener f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PipTaskListener$$ExternalSyntheticLambda1(PipTaskListener pipTaskListener, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = pipTaskListener;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PipTaskListener pipTaskListener = this.f$0;
                ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) this.f$1;
                pipTaskListener.getClass();
                shellTaskOrganizer.addListenerForType(pipTaskListener, -4);
                break;
            default:
                PipTaskListener pipTaskListener2 = this.f$0;
                pipTaskListener2.mPipScheduler.scheduleFinishResizePip((Rect) this.f$1);
                break;
        }
    }
}
