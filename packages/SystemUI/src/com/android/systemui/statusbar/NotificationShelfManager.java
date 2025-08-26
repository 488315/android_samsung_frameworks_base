package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NotificationShelfManager {
    public boolean clearButtonVisible;
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
    private final SettingsHelper.OnChangedCallback panelSplitChangeCallback;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    private final SettingsHelper settingsHelper;
    public NotificationShelf shelf;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;
    public final Interpolator mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    public final long ALPHA_DURATION = 300;

    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1] */
    public NotificationShelfManager(SettingsHelper settingsHelper, Context context, ConfigurationController configurationController, StatusBarStateController statusBarStateController, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.settingsHelper = settingsHelper;
        this.context = context;
        this.configurationController = configurationController;
        this.statusBarStateController = statusBarStateController;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND)};
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                final NotificationShelfManager notificationShelfManager = this.this$0;
                NotificationShelfManager.access$updateRunnable(notificationShelfManager, new Runnable() { // from class: com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1$onConfigChanged$1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        notificationShelfManager.updateResources();
                        NotificationShelfManager notificationShelfManager2 = notificationShelfManager;
                        String string = notificationShelfManager2.context.getResources().getString(R.string.accessibility_button);
                        String string2 = notificationShelfManager2.context.getResources().getString(R.string.noti_setting_text);
                        String string3 = notificationShelfManager2.context.getResources().getString(R.string.clear_all_text);
                        LaunchableTextView launchableTextView = notificationShelfManager2.mSettingButton;
                        if (launchableTextView != null) {
                            launchableTextView.setContentDescription(string2 + "," + string);
                        }
                        LaunchableTextView launchableTextView2 = notificationShelfManager2.mSettingButton;
                        if (launchableTextView2 != null) {
                            launchableTextView2.setText(string2);
                        }
                        LaunchableTextView launchableTextView3 = notificationShelfManager2.mClearAllButton;
                        if (launchableTextView3 != null) {
                            launchableTextView3.setContentDescription(string3 + "," + string);
                        }
                        LaunchableTextView launchableTextView4 = notificationShelfManager2.mClearAllButton;
                        if (launchableTextView4 != null) {
                            launchableTextView4.setText(string3);
                        }
                        notificationShelfManager.updateClearButton();
                        notificationShelfManager.updateAccessibility();
                    }
                });
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                final NotificationShelfManager notificationShelfManager = this.this$0;
                NotificationShelfManager.access$updateRunnable(notificationShelfManager, new Runnable() { // from class: com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1$onDensityOrFontScaleChanged$1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        notificationShelfManager.updateResources();
                        notificationShelfManager.updateClearButton();
                        notificationShelfManager.updateAccessibility();
                    }
                });
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                final NotificationShelfManager notificationShelfManager = this.this$0;
                NotificationShelfManager.access$updateRunnable(notificationShelfManager, new Runnable() { // from class: com.android.systemui.statusbar.NotificationShelfManager$configurationListener$1$onUiModeChanged$1
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        notificationShelfManager.updateResources();
                        notificationShelfManager.updateClearButton();
                        notificationShelfManager.updateAccessibility();
                    }
                });
            }
        };
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.NotificationShelfManager$panelSplitChangeCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri == null || !Intrinsics.areEqual(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL), uri)) {
                    return;
                }
                this.this$0.updateShelfLayout();
            }
        };
        this.panelSplitChangeCallback = onChangedCallback;
        settingsHelper.registerCallback(new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.NotificationShelfManager.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                NotificationShelfManager.this.updateAccessibility();
            }
        }, (Uri[]) Arrays.copyOf(uriArr, 2));
        settingsHelper.registerCallback(onChangedCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL));
    }

    public static final void access$updateRunnable(final NotificationShelfManager notificationShelfManager, final Runnable runnable) {
        ViewTreeObserver viewTreeObserver;
        NotificationShelf notificationShelf = notificationShelfManager.shelf;
        if (notificationShelf == null || (viewTreeObserver = notificationShelf.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager$updateRunnable$1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver2;
                NotificationShelf notificationShelf2 = this.this$0.shelf;
                if (notificationShelf2 != null && (viewTreeObserver2 = notificationShelf2.getViewTreeObserver()) != null) {
                    viewTreeObserver2.removeOnGlobalLayoutListener(this);
                }
                runnable.run();
            }
        });
    }

    public final int getPanelShelfHeight() {
        updateShelfHeightResource(2);
        return this.mShelfTextAreaHeight + this.mShelfTextAreaPaddingTop + this.mShelfTextAreaPaddingBottom;
    }

    public final void startButtonAnimation(final View view, final boolean z, boolean z2) {
        if (view != null) {
            float f = 0.0f;
            if (z) {
                if (view.isEnabled()) {
                    f = 1.0f;
                } else if (view.getId() != R.id.clear_all && view.getId() == R.id.noti_setting) {
                    f = 0.3f;
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
            int color = this.context.getColor(17171370);
            LaunchableTextView launchableTextView2 = this.mSettingButton;
            if (launchableTextView2 != null) {
                launchableTextView2.setBackground(launchableTextView2.getContext().getDrawable(R.drawable.shelf_button_show_button_highlight_background));
                launchableTextView2.setTextColor(color);
            }
            LaunchableTextView launchableTextView3 = this.mClearAllButton;
            if (launchableTextView3 != null) {
                launchableTextView3.setBackground(launchableTextView3.getContext().getDrawable(R.drawable.shelf_button_show_button_highlight_background));
                launchableTextView3.setTextColor(color);
                return;
            }
            return;
        }
        int color2 = this.context.getColor(R.color.notification_shelf_button_text_color);
        LaunchableTextView launchableTextView4 = this.mSettingButton;
        if (launchableTextView4 != null) {
            launchableTextView4.setBackground(launchableTextView4.getContext().getDrawable(R.drawable.shelf_button_show_button_background_state_hide));
            launchableTextView4.setTextColor(color2);
        }
        LaunchableTextView launchableTextView5 = this.mClearAllButton;
        if (launchableTextView5 != null) {
            launchableTextView5.setBackground(launchableTextView5.getContext().getDrawable(R.drawable.shelf_button_show_button_background_state_hide));
            launchableTextView5.setTextColor(color2);
        }
    }

    public final void updateClearButton() {
        LaunchableTextView launchableTextView = this.mClearAllButton;
        if (launchableTextView != null) {
            boolean z = this.clearButtonVisible;
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                z = NotiCenterPlugin.clearableNotifications;
            }
            launchableTextView.setEnabled(z);
            startButtonAnimation(launchableTextView, launchableTextView.isEnabled(), true);
        }
    }

    public final void updateResources() throws Resources.NotFoundException {
        updateShelfLayout();
        int i = (QpRune.QUICK_TABLET || this.secQsUiDisplayModeInteractor.isTablet()) ? R.dimen.bottom_bar_button_text_size_for_tablet : R.dimen.bottom_bar_button_text_size;
        FontSizeUtils.updateFontSize(this.mSettingButton, i, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mClearAllButton, i, 0.8f, 1.3f);
    }

    public final void updateShelfHeightResource(int i) {
        int dimensionPixelSize = 0;
        boolean z = i == 1;
        boolean z2 = this.context.getResources().getConfiguration().orientation == 2;
        this.mShelfTextAreaHeight = z ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_height_on_keyguard) : this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_height);
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        this.mShelfTextAreaPaddingTop = z ? 0 : (QpRune.QUICK_TABLET || secQsUiDisplayModeInteractor.isTablet()) ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_top_padding_on_tablet) : this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_top_padding);
        if (!z && ((!QpRune.QUICK_DATA_USAGE_LABEL || DeviceState.getActiveSimCount(this.context) <= 0 || this.settingsHelper.isPanelSplit()) && (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER || !secQsUiDisplayModeInteractor.isTablet()))) {
            dimensionPixelSize = z2 ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_swipe_nav_on_landscape) : this.settingsHelper.isNavigationBarGestureWhileHidden() ? this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_swipe_nav) : this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_button_nav);
        }
        this.mShelfTextAreaPaddingBottom = dimensionPixelSize;
        this.mShelfPaddingHorizontal = this.context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_padding_horizontal);
    }

    public final void updateShelfLayout() {
        updateShelfHeightResource(this.statusBarState);
        int i = this.mShelfTextAreaHeight + this.mShelfTextAreaPaddingTop + this.mShelfTextAreaPaddingBottom;
        NotificationShelf notificationShelf = this.shelf;
        if (notificationShelf != null) {
            if (!QpRune.QUICK_DATA_USAGE_LABEL || DeviceState.getActiveSimCount(notificationShelf.getContext()) <= 0 || this.settingsHelper.isPanelSplit()) {
                notificationShelf.getLayoutParams().height = i;
            } else {
                notificationShelf.getLayoutParams().height = notificationShelf.getContext().getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_text_area_bottom_padding_with_data_usage_view) + i;
            }
            notificationShelf.setPaddingRelative(this.mShelfPaddingHorizontal, notificationShelf.getPaddingTop(), this.mShelfPaddingHorizontal, notificationShelf.getPaddingBottom());
        }
        LinearLayout linearLayout = this.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.getLayoutParams().height = i;
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
        if (notificationIconContainer != null) {
            notificationIconContainer.setVisibility(z ? 0 : 8);
        }
    }
}
