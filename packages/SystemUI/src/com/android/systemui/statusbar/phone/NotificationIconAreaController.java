package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.StatusBarIconView;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface NotificationIconAreaController {
    View getNotificationInnerAreaView();

    void onDensityOrFontScaleChanged(Context context);

    void onThemeChanged();

    void setAnimationsEnabled(boolean z);

    void setIsolatedIconLocation(Rect rect, boolean z);

    void setKeyguardNotifIcon(NotificationIconContainer notificationIconContainer);

    void setKeyguardNotifIconTint(int i);

    void setShelfIcons(NotificationIconContainer notificationIconContainer);

    void setupAodIcons();

    void showIconIsolated(StatusBarIconView statusBarIconView, boolean z);

    void updateAodNotificationIcons();

    void updateNotificationIcons(List list);

    void updateStatusBarIcons();
}
