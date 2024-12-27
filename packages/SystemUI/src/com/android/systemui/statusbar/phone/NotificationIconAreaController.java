package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.StatusBarIconView;
import java.util.List;

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
