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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DexTouchpadController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DexTouchpadController f$0;

    public /* synthetic */ DexTouchpadController$$ExternalSyntheticLambda0(DexTouchpadController dexTouchpadController, int i) {
        this.$r8$classId = i;
        this.f$0 = dexTouchpadController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DexTouchpadController dexTouchpadController = this.f$0;
        switch (i) {
            case 0:
                final TouchpadNotificationManager touchpadNotificationManager = dexTouchpadController.mTouchpadNotificationManager;
                touchpadNotificationManager.getClass();
                HashMap hashMap = new HashMap(touchpadNotificationManager.mActiveNotifications);
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        TouchpadNotificationManager.this.remove((NotificationType) obj);
                    }
                });
                hashMap.clear();
                break;
            case 1:
                NavBarIconManager navBarIconManager = dexTouchpadController.mNavBarIconManager;
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
            case 2:
                dexTouchpadController.mTouchpadNotificationManager.show(NotificationType.TOUCHPAD);
                break;
            default:
                dexTouchpadController.mNavBarIconManager.remove();
                break;
        }
    }
}
