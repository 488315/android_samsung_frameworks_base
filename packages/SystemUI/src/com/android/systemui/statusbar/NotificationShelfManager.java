package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableTextView;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.Arrays;

public final class NotificationShelfManager {
    public final ConfigurationController configurationController;
    public final NotificationShelfManager$configurationListener$1 configurationListener;
    public final Context context;
    public LaunchableTextView mClearAllButton;
    public int mIconContainerPaddingEnd;
    public NotificationIconContainer mNotificationIconContainer;
    public LaunchableTextView mSettingButton;
    public int mShelfPaddingHorizontal;
    public LinearLayout mShelfTextArea;
    public int mShelfTextAreaHeight;
    public int mShelfTextAreaPaddingBottom;
    public int mShelfTextAreaPaddingTop;
    public NotificationPanelViewController notificationPanelController;
    private final SettingsHelper settingsHelper;
    public NotificationShelf shelf;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;
    public final float OPAQUE_ALPHA = 1.0f;
    public final float DISABLED_ALPHA = 0.3f;
    public final Interpolator mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    public final long ALPHA_DURATION = 300;

    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1] */
    public NotificationShelfManager(SettingsHelper settingsHelper, Context context, ConfigurationController configurationController, StatusBarStateController statusBarStateController) {
        this.settingsHelper = settingsHelper;
        this.context = context;
        this.configurationController = configurationController;
        this.statusBarStateController = statusBarStateController;
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND)};
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                NotificationShelfManager notificationShelfManager = NotificationShelfManager.this;
                notificationShelfManager.updateResources();
                String string = notificationShelfManager.context.getResources().getString(R.string.accessibility_button);
                String string2 = notificationShelfManager.context.getResources().getString(R.string.noti_setting_text);
                String string3 = notificationShelfManager.context.getResources().getString(R.string.clear_all_text);
                LaunchableTextView launchableTextView = notificationShelfManager.mSettingButton;
                if (launchableTextView != null) {
                    launchableTextView.setContentDescription(string2 + "," + string);
                }
                LaunchableTextView launchableTextView2 = notificationShelfManager.mSettingButton;
                if (launchableTextView2 != null) {
                    launchableTextView2.setText(string2);
                }
                LaunchableTextView launchableTextView3 = notificationShelfManager.mClearAllButton;
                if (launchableTextView3 != null) {
                    launchableTextView3.setContentDescription(string3 + "," + string);
                }
                LaunchableTextView launchableTextView4 = notificationShelfManager.mClearAllButton;
                if (launchableTextView4 != null) {
                    launchableTextView4.setText(string3);
                }
                notificationShelfManager.updateClearButton();
                notificationShelfManager.updateAccessibility();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                NotificationShelfManager notificationShelfManager = NotificationShelfManager.this;
                notificationShelfManager.updateResources();
                notificationShelfManager.updateClearButton();
                notificationShelfManager.updateAccessibility();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                NotificationShelfManager notificationShelfManager = NotificationShelfManager.this;
                notificationShelfManager.updateResources();
                notificationShelfManager.updateClearButton();
                notificationShelfManager.updateAccessibility();
            }
        };
        settingsHelper.registerCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.NotificationShelfManager.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                NotificationShelfManager.this.updateAccessibility();
            }
        }, (Uri[]) Arrays.copyOf(uriArr, 2));
    }

    public final int getShelfHeight() {
        boolean z = this.statusBarState == 1;
        boolean z2 = this.context.getResources().getConfiguration().orientation == 2;
        this.mShelfTextAreaHeight = this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_height);
        this.mShelfTextAreaPaddingTop = z ? 0 : (!QpRune.QUICK_DATA_USAGE_LABEL || DeviceState.getActiveSimCount(this.context) <= 0) ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_top_padding) : this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_top_padding_with_data_usage_label);
        this.mShelfTextAreaPaddingBottom = z ? 0 : z2 ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_swipe_nav_on_landscape) : this.settingsHelper.isNavigationBarGestureWhileHidden() ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_swipe_nav) : (!QpRune.QUICK_DATA_USAGE_LABEL || DeviceState.getActiveSimCount(this.context) <= 0 || this.settingsHelper.isNavigationBarGestureWhileHidden()) ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_button_nav) : this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_data_usage_label_with_button_nav);
        this.mShelfPaddingHorizontal = this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_padding_horizontal);
        return this.mShelfTextAreaHeight + this.mShelfTextAreaPaddingTop + this.mShelfTextAreaPaddingBottom;
    }

    public final void startButtonAnimation(final View view, final boolean z, boolean z2) {
        if (view != null) {
            float f = 0.0f;
            if (z) {
                if (view.isEnabled()) {
                    f = this.OPAQUE_ALPHA;
                } else if (view.getId() != R.id.clear_all && view.getId() == R.id.noti_setting) {
                    f = this.DISABLED_ALPHA;
                }
            }
            view.animate().cancel();
            NotificationShelf notificationShelf = this.shelf;
            if (notificationShelf == null || notificationShelf.getVisibility() != 0) {
                view.setVisibility(z ? 0 : 4);
                view.setAlpha(f);
            } else {
                if (view.getAlpha() == f) {
                    if (view.getVisibility() == (z ? 0 : 4)) {
                        return;
                    }
                }
                view.animate().setDuration(z2 ? this.ALPHA_DURATION : 0L).setInterpolator(this.mSineInOut33).alpha(f).withStartAction(new Runnable() { // from class: com.android.systemui.statusbar.NotificationShelfManager$startButtonAnimation$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (z) {
                            view.setVisibility(0);
                        }
                    }
                }).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.NotificationShelfManager$startButtonAnimation$1$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (z) {
                            return;
                        }
                        view.setVisibility(4);
                    }
                }).start();
            }
        }
    }

    public final void updateAccessibility() {
        boolean z = !this.settingsHelper.isEmergencyMode();
        LaunchableTextView launchableTextView = this.mSettingButton;
        if (launchableTextView != null) {
            launchableTextView.setEnabled(z);
            startButtonAnimation(launchableTextView, true, false);
        }
        if (this.settingsHelper.isShowButtonBackground()) {
            LaunchableTextView launchableTextView2 = this.mSettingButton;
            if (launchableTextView2 != null) {
                launchableTextView2.setBackground(null);
            }
            LaunchableTextView launchableTextView3 = this.mClearAllButton;
            if (launchableTextView3 != null) {
                launchableTextView3.setBackground(null);
                return;
            }
            return;
        }
        LaunchableTextView launchableTextView4 = this.mSettingButton;
        if (launchableTextView4 != null) {
            launchableTextView4.setBackground(launchableTextView4.getContext().getDrawable(R.drawable.shelf_button_show_button_background_state_hide));
        }
        LaunchableTextView launchableTextView5 = this.mClearAllButton;
        if (launchableTextView5 != null) {
            launchableTextView5.setBackground(launchableTextView5.getContext().getDrawable(R.drawable.shelf_button_show_button_background_state_hide));
        }
    }

    public final void updateClearButton() {
        boolean z;
        LaunchableTextView launchableTextView = this.mClearAllButton;
        if (launchableTextView != null) {
            NotificationPanelViewController notificationPanelViewController = this.notificationPanelController;
            if (notificationPanelViewController != null) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.getClass();
                FooterViewRefactor.assertInLegacyMode();
                z = notificationStackScrollLayoutController.hasNotifications(0, true);
            } else {
                z = true;
            }
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                z = NotiCenterPlugin.clearableNotifications;
            }
            launchableTextView.setEnabled(z);
            startButtonAnimation(launchableTextView, launchableTextView.isEnabled(), true);
        }
    }

    public final void updateResources() {
        updateShelfHeight();
        int i = QpRune.QUICK_TABLET ? R.dimen.bottom_bar_button_text_size_for_tablet : R.dimen.bottom_bar_button_text_size;
        FontSizeUtils.updateFontSize(this.mSettingButton, i, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mClearAllButton, i, 0.8f, 1.3f);
    }

    public final void updateShelfHeight() {
        int shelfHeight = getShelfHeight();
        NotificationShelf notificationShelf = this.shelf;
        if (notificationShelf != null) {
            notificationShelf.getLayoutParams().height = shelfHeight;
            notificationShelf.setPaddingRelative(this.mShelfPaddingHorizontal, notificationShelf.getPaddingTop(), this.mShelfPaddingHorizontal, notificationShelf.getPaddingBottom());
        }
        LinearLayout linearLayout = this.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.getLayoutParams().height = shelfHeight;
            linearLayout.setPaddingRelative(linearLayout.getPaddingStart(), this.mShelfTextAreaPaddingTop, linearLayout.getPaddingEnd(), this.mShelfTextAreaPaddingBottom);
        }
        LaunchableTextView launchableTextView = this.mSettingButton;
        if (launchableTextView != null) {
            launchableTextView.getLayoutParams().height = this.mShelfTextAreaHeight;
        }
        LaunchableTextView launchableTextView2 = this.mClearAllButton;
        if (launchableTextView2 != null) {
            launchableTextView2.getLayoutParams().height = this.mShelfTextAreaHeight;
        }
        NotificationIconContainer notificationIconContainer = this.mNotificationIconContainer;
        if (notificationIconContainer != null) {
            ViewGroup.LayoutParams layoutParams = notificationIconContainer.getLayoutParams();
            if (layoutParams == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            layoutParams2.height = this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_height);
            layoutParams2.width = this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_width);
            layoutParams2.gravity = 17;
            notificationIconContainer.setLayoutParams(layoutParams2);
        }
    }

    public final void updateShelfTextArea() {
        boolean z = this.statusBarState == 1;
        LinearLayout linearLayout = this.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.setVisibility(z ? 8 : 0);
        }
        NotificationIconContainer notificationIconContainer = this.mNotificationIconContainer;
        if (notificationIconContainer == null) {
            return;
        }
        notificationIconContainer.setVisibility(z ? 0 : 8);
    }
}
