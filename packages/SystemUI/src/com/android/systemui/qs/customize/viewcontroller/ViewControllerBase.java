package com.android.systemui.qs.customize.viewcontroller;

import android.view.View;
import com.android.systemui.util.ViewController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ViewControllerBase extends ViewController {
    public QSCMainViewController$showView$1$1 doneCallback;
    public boolean isShown;
    public Integer message;
    public final View view;

    public ViewControllerBase(View view) {
        super(view);
        this.view = view;
    }

    public void close() {
        if (this.isShown) {
            this.isShown = false;
        }
    }

    public void show(Runnable runnable) {
        if (this.isShown) {
            return;
        }
        this.isShown = true;
        this.mView.setVisibility(0);
        this.doneCallback = (QSCMainViewController$showView$1$1) runnable;
    }

    public void resolveMessage(Integer num) {
    }

    public void windowInsetChanged(int i) {
    }

    public void configChanged() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
    }
}
