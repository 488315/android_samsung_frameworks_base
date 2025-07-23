package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SplitScreenController.ISplitScreenImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(SplitScreenController.ISplitScreenImpl iSplitScreenImpl, ISplitScreenListener$Stub$Proxy iSplitScreenListener$Stub$Proxy) {
        this.f$0 = iSplitScreenImpl;
        this.f$1 = iSplitScreenListener$Stub$Proxy;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.ISplitScreenImpl iSplitScreenImpl = this.f$0;
                iSplitScreenImpl.mSelectListener.register((ISplitSelectListener$Stub$Proxy) this.f$1);
                break;
            default:
                SplitScreenController.ISplitScreenImpl iSplitScreenImpl2 = this.f$0;
                iSplitScreenImpl2.mListener.register((ISplitScreenListener$Stub$Proxy) this.f$1);
                break;
        }
    }

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda1(SplitScreenController.ISplitScreenImpl iSplitScreenImpl, ISplitSelectListener$Stub$Proxy iSplitSelectListener$Stub$Proxy) {
        this.f$0 = iSplitScreenImpl;
        this.f$1 = iSplitSelectListener$Stub$Proxy;
    }
}
