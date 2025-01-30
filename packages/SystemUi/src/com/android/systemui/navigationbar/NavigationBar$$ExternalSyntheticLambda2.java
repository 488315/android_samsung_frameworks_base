package com.android.systemui.navigationbar;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda2(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NavigationBar navigationBar = this.f$0;
                navigationBar.mHandler.postDelayed(navigationBar.mOnVariableDurationHomeLongClick, ((Long) obj).longValue());
                break;
            default:
                NavigationBar navigationBar2 = this.f$0;
                navigationBar2.repositionNavigationBar(navigationBar2.mCurrentRotation);
                break;
        }
    }
}
