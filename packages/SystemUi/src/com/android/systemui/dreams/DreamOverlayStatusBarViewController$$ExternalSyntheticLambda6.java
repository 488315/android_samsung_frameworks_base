package com.android.systemui.dreams;

import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ DreamOverlayStatusBarViewController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i, boolean z, String str) {
        this.f$0 = dreamOverlayStatusBarViewController;
        this.f$1 = i;
        this.f$2 = z;
        this.f$3 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = this.f$0;
        int i = this.f$1;
        boolean z2 = this.f$2;
        String str = this.f$3;
        if (dreamOverlayStatusBarViewController.mIsAttached) {
            DreamOverlayStatusBarView dreamOverlayStatusBarView = (DreamOverlayStatusBarView) dreamOverlayStatusBarViewController.mView;
            View view = (View) ((HashMap) dreamOverlayStatusBarView.mStatusIcons).get(Integer.valueOf(i));
            if (view == null) {
                return;
            }
            if (z2 && str != null) {
                view.setContentDescription(str);
            }
            view.setVisibility(z2 ? 0 : 8);
            ViewGroup viewGroup = dreamOverlayStatusBarView.mSystemStatusViewGroup;
            int i2 = 0;
            while (true) {
                if (i2 >= dreamOverlayStatusBarView.mSystemStatusViewGroup.getChildCount()) {
                    z = false;
                    break;
                } else {
                    if (dreamOverlayStatusBarView.mSystemStatusViewGroup.getChildAt(i2).getVisibility() == 0) {
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
            viewGroup.setVisibility(z ? 0 : 8);
        }
    }
}
