package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ShadowDelegateUtil;
import com.samsung.android.widget.SemTipPopup;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShelfManager implements SettingsHelper.OnChangedCallback, ConfigurationController.ConfigurationListener {
    public final ConfigurationController configurationController;
    public final Context context;
    public boolean isAnimationEndedAndVisible;
    public boolean isFullyExpanded;
    public TextView mClearAllButton;
    public int mIconContainerPaddingEnd;
    public LinearLayout mNotiSettingContainer;
    public ImageView mNotiSettingIcon;
    public SwitchableDoubleShadowTextView mNotiSettingText;
    public SecShelfNotificationIconContainer mNotificationIconContainer;
    public LinearLayout mShelfTextArea;
    public NotificationPanelViewController notificationPanelController;
    public final SettingsHelper settingsHelper;
    public NotificationShelf shelf;
    public int statusBarState;
    public final StatusBarStateController statusBarStateController;
    public final float OPAQUE_ALPHA = 1.0f;
    public final float DISABLED_ALPHA = 0.3f;
    public final float ALPHA_ACCEL_INTERPOLATOR = 2.0f;
    public final long ALPHA_DURATION = 150;

    public NotificationShelfManager(SettingsHelper settingsHelper, Context context, ConfigurationController configurationController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController) {
        this.settingsHelper = settingsHelper;
        this.context = context;
        this.configurationController = configurationController;
        this.statusBarStateController = statusBarStateController;
        Uri[] uriArr = {Settings.System.getUriFor("emergency_mode"), Settings.System.getUriFor("show_button_background")};
        settingsHelper.registerCallback(this, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        shadeExpansionStateManager.addExpansionListener(new ShadeExpansionListener() { // from class: com.android.systemui.statusbar.NotificationShelfManager.1
            @Override // com.android.systemui.shade.ShadeExpansionListener
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                boolean z = shadeExpansionChangeEvent.fraction == 1.0f;
                NotificationShelfManager notificationShelfManager = NotificationShelfManager.this;
                if (notificationShelfManager.isFullyExpanded != z) {
                    notificationShelfManager.isFullyExpanded = z;
                    notificationShelfManager.startButtonAnimation(notificationShelfManager.mNotiSettingContainer, z);
                    notificationShelfManager.startButtonAnimation(notificationShelfManager.mClearAllButton, notificationShelfManager.isFullyExpanded);
                }
            }
        });
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        boolean z = !this.settingsHelper.isEmergencyMode();
        LinearLayout linearLayout = this.mNotiSettingContainer;
        if (linearLayout != null) {
            linearLayout.setEnabled(z);
            if (linearLayout.getVisibility() == 0) {
                if (z) {
                    linearLayout.setAlpha(1.0f);
                } else {
                    linearLayout.setAlpha(0.3f);
                }
            }
        }
        updateShelfButtonBackground();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        Context context = this.context;
        String string = context.getResources().getString(R.string.accessibility_button);
        String string2 = context.getResources().getString(R.string.noti_setting_text);
        String string3 = context.getResources().getString(R.string.clear_all_text);
        LinearLayout linearLayout = this.mNotiSettingContainer;
        if (linearLayout != null) {
            linearLayout.setContentDescription(string2 + "," + string);
        }
        TextView textView = this.mClearAllButton;
        if (textView != null) {
            textView.setContentDescription(string3 + "," + string);
        }
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mNotiSettingText;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setText(R.string.noti_setting_text);
        }
        TextView textView2 = this.mClearAllButton;
        if (textView2 != null) {
            textView2.setText(R.string.clear_all_text);
        }
        updateShelfButtonBackground();
        ((ShelfToolTipManager) Dependency.get(ShelfToolTipManager.class)).releaseToolTip();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        updateShelfHeight();
        float constrain = MathUtils.constrain(this.context.getResources().getConfiguration().fontScale, 0.8f, 1.3f);
        ImageView imageView = this.mNotiSettingIcon;
        if (imageView != null) {
            imageView.getLayoutParams().width = (int) (imageView.getContext().getResources().getDimensionPixelSize(R.dimen.notification_settings_icon_size) * constrain);
            imageView.getLayoutParams().height = (int) (imageView.getContext().getResources().getDimensionPixelSize(R.dimen.notification_settings_icon_size) * constrain);
            int dimensionPixelSize = (int) (imageView.getContext().getResources().getDimensionPixelSize(R.dimen.notification_settings_icon_padding) * constrain);
            imageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        }
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mNotiSettingText;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setPaddingRelative(switchableDoubleShadowTextView.getPaddingStart(), switchableDoubleShadowTextView.getPaddingTop(), switchableDoubleShadowTextView.getContext().getResources().getDimensionPixelSize(R.dimen.notification_settings_text_padding_end), switchableDoubleShadowTextView.getPaddingBottom());
        }
        int i = QpRune.QUICK_TABLET ? R.dimen.bottom_bar_button_text_size_for_tablet : R.dimen.bottom_bar_button_text_size;
        FontSizeUtils.updateFontSize(this.mNotiSettingText, i, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mClearAllButton, i, 0.8f, 1.3f);
        updateShelfButtonBackground();
        updateClearAllOnShelf();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mNotificationIconContainer;
        if (secShelfNotificationIconContainer != null) {
            secShelfNotificationIconContainer.onDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        updateTextColor();
        updateShelfButtonBackground();
    }

    public final void startButtonAnimation(final View view, final boolean z) {
        if (view == null) {
            return;
        }
        view.animate().cancel();
        if (z) {
            view.setVisibility(0);
        }
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = 0.0f;
        if (z) {
            if (view.isEnabled()) {
                ref$FloatRef.element = this.OPAQUE_ALPHA;
            } else {
                ref$FloatRef.element = this.DISABLED_ALPHA;
            }
        }
        view.animate().alpha(ref$FloatRef.element).setDuration(this.ALPHA_DURATION).setInterpolator(new AccelerateInterpolator(this.ALPHA_ACCEL_INTERPOLATOR)).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.NotificationShelfManager$startButtonAnimation$1
            /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationEnd(Animator animator) {
                boolean z2;
                if (!z) {
                    view.setVisibility(4);
                }
                view.setAlpha(ref$FloatRef.element);
                if (view.getId() == R.id.noti_setting_container && z) {
                    final ShelfToolTipManager shelfToolTipManager = (ShelfToolTipManager) Dependency.get(ShelfToolTipManager.class);
                    if (shelfToolTipManager.mNotiSettingTip == null) {
                        if (!shelfToolTipManager.alreadyToolTipShown && !shelfToolTipManager.isTappedNotiSettings && !shelfToolTipManager.mIsQsExpanded && shelfToolTipManager.mJustBeginToOpen) {
                            if ((shelfToolTipManager.panelExpandedCount == shelfToolTipManager.THRESHOLD_COUNT) && shelfToolTipManager.hasBottomClippedNotiRow()) {
                                z2 = true;
                                if (z2) {
                                    LinearLayout linearLayout = shelfToolTipManager.mNotiSettingContainer;
                                    SemTipPopup semTipPopup = linearLayout != null ? new SemTipPopup(linearLayout) : null;
                                    shelfToolTipManager.mNotiSettingTip = semTipPopup;
                                    if (semTipPopup != null) {
                                        semTipPopup.setMessage(shelfToolTipManager.mContext.getString(R.string.noti_setting_tooltip_text));
                                    }
                                    SemTipPopup semTipPopup2 = shelfToolTipManager.mNotiSettingTip;
                                    if (semTipPopup2 != null) {
                                        semTipPopup2.setExpanded(true);
                                    }
                                    SemTipPopup semTipPopup3 = shelfToolTipManager.mNotiSettingTip;
                                    if (semTipPopup3 != null) {
                                        semTipPopup3.setOutsideTouchEnabled(false);
                                    }
                                    SemTipPopup semTipPopup4 = shelfToolTipManager.mNotiSettingTip;
                                    if (semTipPopup4 != null) {
                                        semTipPopup4.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.systemui.ShelfToolTipManager$tryToShowToolTip$2
                                            public final void onStateChanged(int i) {
                                                if (i == 0) {
                                                    Prefs.putBoolean(ShelfToolTipManager.this.mContext, "NotificationSettingsToolTipShown", true);
                                                    ShelfToolTipManager shelfToolTipManager2 = ShelfToolTipManager.this;
                                                    shelfToolTipManager2.alreadyToolTipShown = true;
                                                    shelfToolTipManager2.mNotiSettingTip = null;
                                                }
                                            }
                                        });
                                    }
                                    shelfToolTipManager.calculatePosition();
                                    SemTipPopup semTipPopup5 = shelfToolTipManager.mNotiSettingTip;
                                    if (semTipPopup5 != null) {
                                        semTipPopup5.show(1);
                                    }
                                }
                            }
                        }
                        z2 = false;
                        if (z2) {
                        }
                    }
                }
                if (view.getId() == R.id.clear_all) {
                    this.isAnimationEndedAndVisible = z;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }
        }).start();
    }

    public final void updateClearAllOnShelf() {
        TextView textView = this.mClearAllButton;
        if (textView != null) {
            NotificationPanelViewController notificationPanelViewController = this.notificationPanelController;
            Intrinsics.checkNotNull(notificationPanelViewController);
            boolean hasNotifications = notificationPanelViewController.mNotificationStackScrollLayoutController.hasNotifications(0, true);
            NotiCenterPlugin.INSTANCE.getClass();
            if (NotiCenterPlugin.isNotiCenterPluginConnected() && NotiCenterPlugin.noclearEnabled) {
                hasNotifications = NotiCenterPlugin.clearableNotifications;
            }
            textView.setEnabled(hasNotifications);
            if (this.isAnimationEndedAndVisible && textView.getVisibility() == 0) {
                if (hasNotifications) {
                    textView.setAlpha(1.0f);
                } else {
                    textView.setAlpha(0.3f);
                }
            }
        }
    }

    public final void updateShelfButtonBackground() {
        if (this.settingsHelper.isShowButtonBackground()) {
            ImageView imageView = this.mNotiSettingIcon;
            if (imageView != null) {
                Drawable drawable = imageView.getContext().getDrawable(R.drawable.notification_setting_icon);
                Intrinsics.checkNotNull(drawable);
                imageView.setImageDrawable(drawable);
                imageView.setColorFilter(imageView.getContext().getColor(android.R.color.tab_indicator_text_v4));
                imageView.setAlpha(1.0f);
            }
            SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mNotiSettingText;
            if (switchableDoubleShadowTextView != null) {
                switchableDoubleShadowTextView.shadowEnabled = false;
                switchableDoubleShadowTextView.setTextColor(switchableDoubleShadowTextView.getContext().getColor(android.R.color.tab_indicator_text_v4));
                switchableDoubleShadowTextView.setAlpha(1.0f);
            }
            LinearLayout linearLayout = this.mNotiSettingContainer;
            if (linearLayout != null) {
                linearLayout.setBackground(linearLayout.getContext().getDrawable(R.drawable.shelf_button_show_button_highlight_background));
            }
            TextView textView = this.mClearAllButton;
            if (textView != null) {
                textView.setTextColor(textView.getContext().getColor(android.R.color.tab_indicator_text_v4));
                textView.setBackground(textView.getContext().getDrawable(R.drawable.shelf_button_show_button_highlight_background));
                int dimensionPixelSize = textView.getContext().getResources().getDimensionPixelSize(R.dimen.notification_shelf_clear_button_side_padding);
                textView.setPadding(dimensionPixelSize, textView.getPaddingTop(), dimensionPixelSize, textView.getPaddingBottom());
                return;
            }
            return;
        }
        ImageView imageView2 = this.mNotiSettingIcon;
        if (imageView2 != null) {
            ShadowDelegateUtil shadowDelegateUtil = ShadowDelegateUtil.INSTANCE;
            Drawable drawable2 = imageView2.getContext().getDrawable(R.drawable.notification_setting_icon);
            Intrinsics.checkNotNull(drawable2);
            float dimension = imageView2.getContext().getResources().getDimension(R.dimen.notification_shelf_shadow_radius);
            int dimensionPixelSize2 = imageView2.getContext().getResources().getDimensionPixelSize(R.dimen.notification_settings_icon_size);
            shadowDelegateUtil.getClass();
            imageView2.setImageDrawable(ShadowDelegateUtil.createShadowDrawable(drawable2, dimension, 0.235f, dimensionPixelSize2));
            imageView2.clearColorFilter();
            imageView2.setAlpha(0.85f);
        }
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView2 = this.mNotiSettingText;
        if (switchableDoubleShadowTextView2 != null) {
            switchableDoubleShadowTextView2.shadowEnabled = true;
            switchableDoubleShadowTextView2.setTextColor(switchableDoubleShadowTextView2.getContext().getColor(R.color.notification_shelf_setting_text_color));
            switchableDoubleShadowTextView2.setAlpha(0.85f);
        }
        LinearLayout linearLayout2 = this.mNotiSettingContainer;
        if (linearLayout2 != null) {
            linearLayout2.setBackground(linearLayout2.getContext().getDrawable(R.drawable.notification_shelf_setting_button_ripple_button));
        }
        TextView textView2 = this.mClearAllButton;
        if (textView2 != null) {
            textView2.setTextColor(textView2.getContext().getResources().getColor(R.color.notification_shelf_clear_text_color));
            textView2.setBackground(textView2.getContext().getDrawable(R.drawable.shelf_button_show_button_background_state_hide));
        }
    }

    public final void updateShelfHeight() {
        NotificationShelf notificationShelf = this.shelf;
        if (notificationShelf != null) {
            notificationShelf.getLayoutParams().height = notificationShelf.getContext().getResources().getDimensionPixelSize(this.statusBarState != 1 ? QpRune.QUICK_TABLET ? R.dimen.sec_notification_shelf_height_tablet : R.dimen.sec_notification_shelf_height : R.dimen.notification_shelf_height_for_lockscreen);
        }
        LinearLayout linearLayout = this.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.getLayoutParams().height = linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_height);
        }
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mNotificationIconContainer;
        if (secShelfNotificationIconContainer != null) {
            ViewGroup.LayoutParams layoutParams = secShelfNotificationIconContainer.getLayoutParams();
            if (layoutParams == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
            Context context = this.context;
            layoutParams2.height = context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_height);
            layoutParams2.width = context.getResources().getDimensionPixelSize(R.dimen.sec_notification_shelf_width);
            layoutParams2.gravity = 17;
            secShelfNotificationIconContainer.setLayoutParams(layoutParams2);
        }
    }

    public final void updateShelfTextAreaVisibility() {
        boolean z = this.statusBarState == 1;
        LinearLayout linearLayout = this.mShelfTextArea;
        if (linearLayout != null) {
            linearLayout.setVisibility(z ? 8 : 0);
        }
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mNotificationIconContainer;
        if (secShelfNotificationIconContainer == null) {
            return;
        }
        secShelfNotificationIconContainer.setVisibility(z ? 0 : 8);
    }

    public final void updateTextColor() {
        TextView textView = this.mClearAllButton;
        Context context = this.context;
        if (textView != null) {
            textView.setTextColor(context.getColor(R.color.notification_shelf_clear_text_color));
        }
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mNotiSettingText;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setTextColor(context.getColor(R.color.notification_shelf_setting_text_color));
        }
    }
}
