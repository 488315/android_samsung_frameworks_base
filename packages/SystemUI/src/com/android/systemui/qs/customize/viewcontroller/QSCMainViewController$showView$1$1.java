package com.android.systemui.qs.customize.viewcontroller;

/* loaded from: classes2.dex */
public final class QSCMainViewController$showView$1$1 implements Runnable {
    public final /* synthetic */ QSCMainViewController this$0;

    public QSCMainViewController$showView$1$1(QSCMainViewController qSCMainViewController) {
        this.this$0 = qSCMainViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.backKeyEvent();
    }
}
