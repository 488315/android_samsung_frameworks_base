package com.android.systemui.plugins.statusbar;

import android.content.Context;
import android.graphics.Point;
import android.service.notification.StatusBarNotification;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Dependencies({@DependsOn(target = OnMenuEventListener.class), @DependsOn(target = MenuItem.class), @DependsOn(target = NotificationSwipeActionHelper.class), @DependsOn(target = NotificationSwipeActionHelper.SnoozeOption.class)})
@ProvidesInterface(action = NotificationMenuRowPlugin.ACTION, version = 5)
/* loaded from: classes2.dex */
public interface NotificationMenuRowPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_NOTIFICATION_MENU_ROW";
    public static final int VERSION = 5;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 1)
    public interface MenuItem {
        public static final int VERSION = 1;

        String getContentDescription();

        View getGutsView();

        View getMenuView();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @ProvidesInterface(version = 1)
    public interface OnMenuEventListener {
        public static final int VERSION = 1;

        void onMenuClicked(View view, int i, int i2, MenuItem menuItem);

        void onMenuReset(View view);

        void onMenuShown(View view);
    }

    boolean canBeDismissed();

    void createMenu(ViewGroup viewGroup, StatusBarNotification statusBarNotification);

    MenuItem getFeedbackMenuItem(Context context);

    boolean getHasPopped();

    MenuItem getLongpressMenuItem(Context context);

    ArrayList<MenuItem> getMenuItems(Context context);

    int getMenuSnapTarget();

    View getMenuView();

    default Point getRevealAnimationOrigin() {
        return new Point(0, 0);
    }

    MenuItem getSnoozeMenuItem(Context context);

    boolean isMenuVisible();

    boolean isSnappedAndOnSameSide();

    boolean isSwipedEnoughToShowMenu();

    boolean isTowardsMenu(float f);

    boolean isWithinSnapMenuThreshold();

    default MenuItem menuItemToExposeOnSnap() {
        return null;
    }

    void onDismiss();

    default boolean onInterceptTouchEvent(View view, MotionEvent motionEvent) {
        return false;
    }

    void onNotificationUpdated(StatusBarNotification statusBarNotification);

    void onParentHeightUpdate();

    void onParentTranslationUpdate(float f);

    void onSnapClosed();

    void onSnapOpen();

    void onTouchEnd();

    void onTouchMove(float f);

    void onTouchStart();

    void resetMenu();

    void setAppName(String str);

    void setMenuClickListener(OnMenuEventListener onMenuEventListener);

    void setMenuItems(ArrayList<MenuItem> arrayList);

    default boolean shouldShowGutsOnSnapOpen() {
        return false;
    }

    boolean shouldShowMenu();

    boolean shouldSnapBack();

    default boolean shouldUseDefaultMenuItems() {
        return false;
    }

    default void onConfigurationChanged() {
    }
}
