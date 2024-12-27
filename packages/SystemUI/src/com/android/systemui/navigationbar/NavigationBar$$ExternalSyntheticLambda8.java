package com.android.systemui.navigationbar;

import android.util.Log;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda8(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NavigationBar navigationBar = this.f$0;
        switch (i) {
            case 0:
                Long l = (Long) obj;
                navigationBar.getClass();
                Log.d("NavigationBar", "ACTION_DOWN original duration: " + l);
                navigationBar.mHandler.postDelayed(navigationBar.mOnVariableDurationHomeLongClick, l.longValue());
                break;
            default:
                navigationBar.repositionNavigationBar(navigationBar.mCurrentRotation);
                break;
        }
    }
}
