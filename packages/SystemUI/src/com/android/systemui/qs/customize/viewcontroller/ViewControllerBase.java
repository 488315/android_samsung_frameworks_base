package com.android.systemui.qs.customize.viewcontroller;

import android.view.View;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ViewControllerBase extends ViewController {
    public Runnable doneCallback;
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
        this.doneCallback = runnable;
    }

    public void configChanged() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
    }

    public void resolveMessage(Integer num) {
    }
}
