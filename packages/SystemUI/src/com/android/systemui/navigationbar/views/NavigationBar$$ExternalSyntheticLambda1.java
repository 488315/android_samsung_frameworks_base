package com.android.systemui.navigationbar.views;

import android.util.Log;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda1(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NavigationBar navigationBar = this.f$0;
        switch (i) {
            case 0:
                navigationBar.repositionNavigationBar(navigationBar.mCurrentRotation);
                break;
            default:
                Long l = (Long) obj;
                Log.d("NavigationBar", "ACTION_DOWN original duration: " + l);
                navigationBar.mHandler.postDelayed(navigationBar.mOnVariableDurationHomeLongClick, l.longValue());
                break;
        }
    }
}
