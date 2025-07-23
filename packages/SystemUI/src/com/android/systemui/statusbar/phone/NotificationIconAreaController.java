package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.statusbar.StatusBarIconView;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationIconAreaController {
    void dump(PrintWriter printWriter);

    View getNotificationInnerAreaView();

    int getShowingIconCount();

    void onDensityOrFontScaleChanged(Context context);

    void onThemeChanged();

    void setAnimationsEnabled(boolean z);

    void setIsolatedIconLocation(Rect rect, boolean z);

    void setKeyguardNotifIcon(NotificationIconContainer notificationIconContainer);

    void setKeyguardNotifIconTint(int i);

    void setShelfIcons(SecShelfNotificationIconContainer secShelfNotificationIconContainer);

    void setupAodIcons();

    void showIconIsolated(StatusBarIconView statusBarIconView, boolean z);

    void updateAodNotificationIcons();

    void updateNotificationIcons(List list);

    void updateStatusBarIcons();
}
