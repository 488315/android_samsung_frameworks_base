package com.android.systemui.dextouchpad;

import android.util.Log;
import android.widget.RemoteViews;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.manager.NavBarIconManager;
import com.android.systemui.dextouchpad.manager.NavBarIconManager$$ExternalSyntheticLambda0;
import com.android.systemui.dextouchpad.manager.notification.NotificationType;
import com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager;
import com.android.systemui.dextouchpad.util.Features;
import java.util.HashMap;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class DexTouchpadController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DexTouchpadController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NavBarIconManager navBarIconManager = ((DexTouchpadController) obj).mNavBarIconManager;
                navBarIconManager.getClass();
                if (Features.DEBUG) {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("show, hasIcon="), navBarIconManager.mHasNavBarIcon, "DexTouchpadNavBarIconManager");
                }
                if (navBarIconManager.mHasNavBarIcon) {
                    navBarIconManager.remove();
                }
                NavBarIconManager$$ExternalSyntheticLambda0 navBarIconManager$$ExternalSyntheticLambda0 = navBarIconManager.mRemoteView;
                if (navBarIconManager$$ExternalSyntheticLambda0 == null) {
                    Log.e("DexTouchpadNavBarIconManager", "show(), remote view is null");
                    break;
                } else {
                    RemoteViews remoteViews = (RemoteViews) navBarIconManager$$ExternalSyntheticLambda0.apply(navBarIconManager.mContext);
                    navBarIconManager.mHasNavBarIcon = true;
                    navBarIconManager.mSemStatusBarManager.setNavigationBarShortcut("com.android.systemui.dextouchpad.activity.TouchpadActivity", remoteViews, 0, 7);
                    break;
                }
            case 1:
                ((DexTouchpadController) obj).mTouchpadNotificationManager.show(NotificationType.TOUCHPAD);
                break;
            case 2:
                ((DexTouchpadController) obj).mNavBarIconManager.remove();
                break;
            default:
                final TouchpadNotificationManager touchpadNotificationManager = (TouchpadNotificationManager) obj;
                touchpadNotificationManager.getClass();
                HashMap map = new HashMap(touchpadNotificationManager.mActiveNotifications);
                map.forEach(new BiConsumer() { // from class: com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        touchpadNotificationManager.remove((NotificationType) obj2);
                    }
                });
                map.clear();
                break;
        }
    }
}
