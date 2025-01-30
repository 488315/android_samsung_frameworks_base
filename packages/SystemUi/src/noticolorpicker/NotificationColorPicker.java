package noticolorpicker;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.text.Spanned;
import android.util.Log;
import android.util.Pools;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DateTimeView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.CallLayout;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridConversationNotificationView;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationBigTextTemplateViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationMessagingTemplateViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.p045ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NotificationColorPicker {
    public final Context mContext;
    public int mCustomedAlpha;

    public NotificationColorPicker(Context context) {
        this.mContext = context;
    }

    public static CharSequence getSpanned(TextView textView) {
        Object tag = textView.getTag(R.id.spannded_notification_text);
        if (tag == null) {
            return null;
        }
        return (CharSequence) tag;
    }

    public static boolean isCustom(ExpandableNotificationRow expandableNotificationRow) {
        if (expandableNotificationRow == null) {
            return false;
        }
        return expandableNotificationRow.mIsCustomNotification || expandableNotificationRow.mIsCustomBigNotification || expandableNotificationRow.mIsCustomHeadsUpNotification || expandableNotificationRow.mIsCustomPublicNotification;
    }

    public static boolean isNeedToUpdated(ExpandableNotificationRow expandableNotificationRow) {
        StatusBarNotification statusBarNotification;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null) {
            boolean isColorized = statusBarNotification.getNotification().isColorized();
            boolean isCustom = isCustom(expandableNotificationRow);
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            if ((!isColorized && !isCustom) || z) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUseAppIcon(View view) {
        CachingIconView cachingIconView;
        if (view != null) {
            CachingIconView findViewById = view.findViewById(android.R.id.icon);
            if ((findViewById instanceof CachingIconView) && (cachingIconView = findViewById) != null && cachingIconView.getTag(R.id.use_app_icon) != null) {
                return ((Boolean) cachingIconView.getTag(R.id.use_app_icon)).booleanValue();
            }
        }
        return false;
    }

    public final int getAppPrimaryColor(ExpandableNotificationRow expandableNotificationRow) {
        int resolveHeaderAppIconColor = resolveHeaderAppIconColor(expandableNotificationRow);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        boolean z = NotiRune.NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME;
        Context context = this.mContext;
        if (z) {
            settingsHelper.getClass();
            if ((CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && settingsHelper.mItemLists.get("wallpapertheme_state").getIntValue() == 1) && (expandableNotificationRow.mEntry.mSbn.getNotification().color == 0 || settingsHelper.isApplyWallpaperThemeToNotif())) {
                resolveHeaderAppIconColor = settingsHelper.mItemLists.get("wallpapertheme_color_isgray").getIntValue() == 1 ? context.getColor(R.color.qs_tile_icon_on_dim_tint_color) : context.getColor(R.color.open_theme_noti_header_color);
            }
        }
        if (DeviceState.isOpenTheme(context)) {
            resolveHeaderAppIconColor = context.getColor(R.color.open_theme_noti_header_color);
        }
        if (context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            resolveHeaderAppIconColor = context.getResources().getColor(R.color.qp_notification_primary_color);
        }
        return (resolveHeaderAppIconColor == 0 || resolveHeaderAppIconColor == 1) ? ContrastColorUtil.resolveDefaultColor(context, -1, false) : resolveHeaderAppIconColor;
    }

    public final int getGutsTextColor() {
        Context context = this.mContext;
        int color = context.getResources().getColor(R.color.notification_guts_common_text_color);
        if (DeviceState.isOpenTheme(context)) {
            color = getTextColor(0, false, true);
        }
        return context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? context.getResources().getColor(R.color.sec_qs_header_tint_color, null) : color;
    }

    public final int getNotificationBgColor$1() {
        int notificationDefaultBgColor = getNotificationDefaultBgColor();
        this.mCustomedAlpha = Color.alpha(notificationDefaultBgColor);
        int argb = Color.argb(255, Color.red(notificationDefaultBgColor), Color.green(notificationDefaultBgColor), Color.blue(notificationDefaultBgColor));
        Context context = this.mContext;
        if (DeviceState.isOpenTheme(context)) {
            int color = context.getResources().getColor(R.color.open_theme_notification_bg_color, null);
            this.mCustomedAlpha = Color.alpha(color);
            argb = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
        }
        if (!context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            return argb;
        }
        int color2 = context.getResources().getColor(R.color.qp_notification_background_color, null);
        this.mCustomedAlpha = Color.alpha(color2);
        return Color.argb(255, Color.red(color2), Color.green(color2), Color.blue(color2));
    }

    public final int getNotificationDefaultBgColor() {
        return this.mContext.getResources().getColor(R.color.notification_material_background_color, null);
    }

    public final int getTextColor(int i, boolean z, boolean z2) {
        Context context = this.mContext;
        if (!z2) {
            return context.getResources().getColor(R.color.notification_no_background_header_text_color);
        }
        boolean isNeedToInvertinNightMode = isNeedToInvertinNightMode(z);
        if (z && !isNeedToInvert()) {
            z = false;
        }
        if (i == 0) {
            int color = z ? context.getColor(android.R.color.primary_text_secondary_when_activated_material_inverse) : ContrastColorUtil.resolvePrimaryColor(context, 0, isNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(context)) {
                color = context.getResources().getColor(R.color.open_theme_notification_title_text_color, null);
            }
            return context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? context.getResources().getColor(R.color.qp_notification_title_color) : color;
        }
        if (i == 1) {
            int color2 = z ? context.getColor(android.R.color.profile_badge_2_dark) : ContrastColorUtil.resolveSecondaryColor(context, 0, isNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(context)) {
                color2 = context.getResources().getColor(R.color.open_theme_notification_content_text_color, null);
            }
            return context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? context.getResources().getColor(R.color.qp_notification_content_color) : color2;
        }
        if (i != 2) {
            return ContrastColorUtil.resolveDefaultColor(context, 0, isNeedToInvertinNightMode);
        }
        int color3 = (z || isNeedToInvertinNightMode) ? context.getColor(android.R.color.quaternary_device_default_settings) : context.getColor(android.R.color.quaternary_material_settings);
        if (DeviceState.isOpenTheme(context)) {
            color3 = context.getResources().getColor(R.color.open_theme_notification_content_text_color, null);
        }
        return context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? context.getResources().getColor(R.color.qp_notification_content_color) : color3;
    }

    public final boolean isGrayScaleIcon(ExpandableNotificationRow expandableNotificationRow) {
        return NotificationUtils.isGrayscale(expandableNotificationRow.mEntry.mIcons.mStatusBarIcon, ContrastColorUtil.getInstance(this.mContext));
    }

    public final boolean isNeedToInvert() {
        int lockNoticardOpacity = ((SettingsHelper) Dependency.get(SettingsHelper.class)).getLockNoticardOpacity();
        Context context = this.mContext;
        return !(context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) || DeviceState.isOpenTheme(context)) && ContrastColorUtil.shouldInvertTextColor(((float) lockNoticardOpacity) * 0.01f, ((SettingsHelper) Dependency.get(SettingsHelper.class)).isWhiteKeyguardWallpaper()) && ((SettingsHelper) Dependency.get(SettingsHelper.class)).getActiveThemePackage() == null;
    }

    public final boolean isNeedToInvertinNightMode(boolean z) {
        Context context = this.mContext;
        boolean z2 = true;
        boolean z3 = (context.getResources().getConfiguration().uiMode & 48) == 32;
        if (!context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) && !DeviceState.isOpenTheme(context)) {
            z2 = false;
        }
        if (!z2 && z && z3 && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isWhiteKeyguardWallpaper() && ((SettingsHelper) Dependency.get(SettingsHelper.class)).getLockNoticardOpacity() * 0.01f < 0.25f) {
            return false;
        }
        return z3;
    }

    public final boolean isNightModeOn() {
        return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final int resolveContrastColor(int i, boolean z, ExpandableNotificationRow expandableNotificationRow) {
        int i2 = expandableNotificationRow.mIsLowPriority ? 0 : expandableNotificationRow.mEntry.mSbn.getNotification().color;
        Context context = this.mContext;
        int resolveDefaultColor = i2 == 0 ? ContrastColorUtil.resolveDefaultColor(context, 0, z) : ContrastColorUtil.resolveContrastColor(context, i2, i, z);
        if (Color.alpha(resolveDefaultColor) < 255) {
            resolveDefaultColor = ContrastColorUtil.compositeColors(resolveDefaultColor, i);
        }
        return z ? ContrastColorUtil.resolveContrastColor(context, resolveDefaultColor, context.getColor(R.color.notification_app_icon_color), !z) : resolveDefaultColor;
    }

    public final int resolveHeaderAppIconColor(ExpandableNotificationRow expandableNotificationRow) {
        return (!(expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() && expandableNotificationRow.mBgTint != 0) || expandableNotificationRow.mIsLowPriority) ? resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow) : getTextColor(0, expandableNotificationRow.mDimmed, true);
    }

    public final void setNonGrayScaleIconBackground(ImageView imageView, boolean z) {
        Context context = this.mContext;
        int color = context.getColor(R.color.notification_non_grayscale_border_color);
        int color2 = context.getColor(R.color.notification_non_grayscale_fill_color);
        if (z) {
            int alpha = (Color.alpha(color) * 3) / 10;
            color2 = Color.argb((Color.alpha(color2) * 3) / 10, Color.red(color2), Color.green(color2), Color.blue(color2));
            color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }
        if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
            Drawable drawable = context.getDrawable(R.drawable.squircle);
            drawable.setColorFilter(color2, PorterDuff.Mode.SRC_IN);
            Drawable drawable2 = context.getDrawable(R.drawable.squircle_tray_stroke);
            drawable2.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            imageView.setBackground(new LayerDrawable(new Drawable[]{drawable, drawable2}));
            return;
        }
        if (z) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.notification_icon_circle));
            GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable().mutate();
            gradientDrawable.setColor(color2);
            gradientDrawable.setStroke(context.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
            return;
        }
        imageView.setBackground(context.getDrawable(R.drawable.notification_icon_circle));
        GradientDrawable gradientDrawable2 = (GradientDrawable) imageView.getBackground().mutate();
        gradientDrawable2.setColor(color2);
        gradientDrawable2.setStroke(context.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
    }

    public final void setPrimaryColor(TextView textView, boolean z) {
        if (textView != null) {
            updateSpanned(textView, z);
            textView.setTextColor(getTextColor(0, z, true));
        }
    }

    public final void updateAllTextViewColors(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        int i;
        int i2;
        char c;
        int i3;
        int i4;
        View view;
        View view2;
        HybridNotificationView hybridNotificationView;
        View view3;
        View view4;
        if (expandableNotificationRow == null) {
            return;
        }
        boolean isGrayScaleIcon = isGrayScaleIcon(expandableNotificationRow);
        int i5 = 0;
        if (!isNeedToUpdated(expandableNotificationRow)) {
            int childCount = expandableNotificationRow.getChildCount();
            while (i5 < childCount) {
                View childAt = expandableNotificationRow.getChildAt(i5);
                if (childAt instanceof NotificationContentView) {
                    NotificationContentView notificationContentView = (NotificationContentView) childAt;
                    HybridNotificationView hybridNotificationView2 = notificationContentView.mSingleLineView;
                    if (hybridNotificationView2 != null) {
                        updateSingleLine(hybridNotificationView2, z);
                    }
                    View view5 = notificationContentView.mContractedChild;
                    int resolveHeaderAppIconColor = resolveHeaderAppIconColor(expandableNotificationRow);
                    if (view5 != null) {
                        if (isUseAppIcon(view5)) {
                            updateSmallIconForCustom(view5, resolveHeaderAppIconColor, isGrayScaleIcon);
                        } else if (expandableNotificationRow.mIsLowPriority || !expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() && (view5 instanceof CallLayout))) {
                            updateSmallIconForCustom(view5, resolveHeaderAppIconColor, isGrayScaleIcon);
                        }
                    }
                    View view6 = notificationContentView.mExpandedChild;
                    if (view6 != null) {
                        if (isUseAppIcon(view6)) {
                            updateSmallIconForCustom(view6, resolveHeaderAppIconColor, isGrayScaleIcon);
                        } else if (!expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() && (view6 instanceof CallLayout))) {
                            updateSmallIconForCustom(view6, resolveHeaderAppIconColor, isGrayScaleIcon);
                        }
                    }
                    View view7 = notificationContentView.mHeadsUpChild;
                    if (view7 != null) {
                        if (isUseAppIcon(view7)) {
                            updateSmallIconForCustom(view7, resolveHeaderAppIconColor, isGrayScaleIcon);
                        } else if (!expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() && (view7 instanceof CallLayout))) {
                            updateSmallIconForCustom(view7, resolveHeaderAppIconColor, isGrayScaleIcon);
                        }
                    }
                    if (notificationContentView.getId() == R.id.expandedPublic && (view4 = notificationContentView.mContractedChild) != null) {
                        if (expandableNotificationRow.mEntry.mSbn.getNotification().isColorized()) {
                            resolveHeaderAppIconColor = resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow);
                        }
                        updateSmallIconForCustom(view4, resolveHeaderAppIconColor, isGrayScaleIcon);
                    }
                }
                i5++;
            }
            return;
        }
        int appPrimaryColor = getAppPrimaryColor(expandableNotificationRow);
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        char c2 = 835;
        int i6 = 1;
        if (notificationChildrenContainer != null) {
            NotificationHeaderView notificationHeaderView = notificationChildrenContainer.mNotificationHeader;
            if (notificationHeaderView != null) {
                updateHeader(notificationHeaderView, expandableNotificationRow, true);
            }
            NotificationHeaderView notificationHeaderView2 = notificationChildrenContainer.mNotificationHeaderExpanded;
            if (notificationHeaderView2 != null) {
                updateHeader(notificationHeaderView2, expandableNotificationRow, false);
            }
            NotificationHeaderView notificationHeaderView3 = notificationChildrenContainer.mNotificationHeaderLowPriority;
            if (notificationHeaderView3 != null) {
                updateHeader(notificationHeaderView3, expandableNotificationRow, true);
                setPrimaryColor((TextView) notificationChildrenContainer.mNotificationHeaderLowPriority.findViewById(android.R.id.image), z);
                setPrimaryColor((TextView) notificationChildrenContainer.mNotificationHeaderLowPriority.findViewById(android.R.id.audio), z);
            }
        }
        int childCount2 = expandableNotificationRow.getChildCount();
        int i7 = 0;
        while (i7 < childCount2) {
            View childAt2 = expandableNotificationRow.getChildAt(i7);
            if (childAt2 instanceof NotificationContentView) {
                NotificationContentView notificationContentView2 = (NotificationContentView) childAt2;
                View view8 = notificationContentView2.mContractedChild;
                HybridNotificationView hybridNotificationView3 = notificationContentView2.mSingleLineView;
                View view9 = notificationContentView2.mExpandedChild;
                View view10 = notificationContentView2.mHeadsUpChild;
                NotificationViewWrapper visibleWrapper = notificationContentView2.getVisibleWrapper(i5);
                NotificationViewWrapper visibleWrapper2 = notificationContentView2.getVisibleWrapper(i6);
                NotificationViewWrapper visibleWrapper3 = notificationContentView2.getVisibleWrapper(2);
                if ((((visibleWrapper instanceof NotificationBigTextTemplateViewWrapper) || (visibleWrapper instanceof NotificationMessagingTemplateViewWrapper)) ? i6 : 0) != 0) {
                    view = view10;
                    view2 = view9;
                    hybridNotificationView = hybridNotificationView3;
                    view3 = view8;
                    i4 = i7;
                    i = childCount2;
                    updateBig(view8, appPrimaryColor, isGrayScaleIcon, visibleWrapper, z, expandableNotificationRow);
                } else {
                    view = view10;
                    view2 = view9;
                    hybridNotificationView = hybridNotificationView3;
                    view3 = view8;
                    i4 = i7;
                    i = childCount2;
                    updateBase(view3, appPrimaryColor, isGrayScaleIcon, z, expandableNotificationRow);
                }
                updateBig(view2, appPrimaryColor, isGrayScaleIcon, visibleWrapper2, z, expandableNotificationRow);
                updateBig(view, appPrimaryColor, isGrayScaleIcon, visibleWrapper3, z, expandableNotificationRow);
                updateSingleLine(hybridNotificationView, z);
                View view11 = view3;
                if (view11 != null) {
                    TextView textView = (TextView) view11.findViewById(android.R.id.audio);
                    i2 = 0;
                    i3 = 1;
                    if (textView != null) {
                        textView.setTextColor(getTextColor(0, z, true));
                    }
                    c = 835;
                    TextView textView2 = (TextView) view11.findViewById(android.R.id.image);
                    if (textView2 != null) {
                        updateSpanned(textView2, z);
                        textView2.setTextColor(getTextColor(0, z, true));
                    }
                } else {
                    c = 835;
                    i2 = 0;
                    i3 = 1;
                }
            } else {
                i = childCount2;
                i2 = i5;
                c = c2;
                i3 = i6;
                i4 = i7;
            }
            i7 = i4 + 1;
            c2 = c;
            i5 = i2;
            i6 = i3;
            childCount2 = i;
        }
    }

    public final void updateBase(View view, final int i, final boolean z, boolean z2, ExpandableNotificationRow expandableNotificationRow) {
        if (view == null) {
            return;
        }
        View findViewById = view.findViewById(R.id.smart_reply_view);
        if (findViewById instanceof SmartReplyView) {
            ((SmartReplyView) findViewById).updateButtonColorOnUiModeChanged();
        }
        final int textColor = getTextColor(0, z2, true);
        final int textColor2 = getTextColor(1, z2, true);
        if (view instanceof IMessagingLayout) {
            ConversationLayout conversationLayout = (IMessagingLayout) view;
            conversationLayout.getMessagingGroups().stream().filter(new NotificationColorPicker$$ExternalSyntheticLambda0()).forEach(new Consumer() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = textColor;
                    int i3 = textColor2;
                    boolean z3 = z;
                    int i4 = i;
                    MessagingGroup messagingGroup = (MessagingGroup) obj;
                    messagingGroup.setTextColors(i2, i3);
                    if (z3) {
                        messagingGroup.setLayoutColor(i4);
                    }
                }
            });
            if (conversationLayout instanceof ConversationLayout) {
                conversationLayout.setSenderTextColor(textColor);
            }
        }
        Context context = this.mContext;
        int color = context.getColor(R.color.notification_material_background_color);
        resolveContrastColor(color, isNightModeOn(), expandableNotificationRow);
        if (view instanceof ConversationLayout) {
            ConversationLayout conversationLayout2 = (ConversationLayout) view;
            conversationLayout2.setLayoutColor(color);
            conversationLayout2.setNotificationBackgroundColor(i);
            ImageView imageView = (ImageView) conversationLayout2.findViewById(android.R.id.icon);
            if (imageView != null) {
                if (isUseAppIcon(imageView)) {
                    conversationLayout2.findViewById(android.R.id.crossfade).setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    imageView.setLayoutParams(layoutParams);
                } else {
                    conversationLayout2.findViewById(android.R.id.crossfade).setVisibility(0);
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.notification_shelf_tooltip_bottom);
                    layoutParams2.setMargins(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                    imageView.setLayoutParams(layoutParams2);
                    imageView.setColorFilter(context.getColor(R.color.notification_app_icon_color), PorterDuff.Mode.SRC_IN);
                }
            }
            ImageView imageView2 = (ImageView) conversationLayout2.findViewById(android.R.id.crosshair);
            if (imageView2 != null && imageView2.getVisibility() == 0) {
                int color2 = context.getColor(android.R.color.decor_button_light_color);
                if (isUseAppIcon(conversationLayout2)) {
                    imageView2.setImageDrawable((VectorDrawable) context.getDrawable(R.drawable.squircle_tray_stroke_small));
                    imageView2.setColorFilter(color2);
                } else {
                    imageView2.setColorFilter((ColorFilter) null);
                    imageView2.setImageDrawable(context.getDrawable(android.R.drawable.button_inset));
                    ((GradientDrawable) imageView2.getDrawable().mutate()).setStroke(context.getResources().getDimensionPixelSize(R.dimen.importance_ring_stroke_width), color2);
                }
            }
            ViewParent parent = conversationLayout2.getParent();
            if (parent != null && (parent instanceof NotificationContentView)) {
                NotificationContentView notificationContentView = (NotificationContentView) parent;
                for (int i2 = 0; i2 < notificationContentView.getChildCount(); i2++) {
                    if (notificationContentView.getChildAt(i2) instanceof HybridConversationNotificationView) {
                        HybridConversationNotificationView hybridConversationNotificationView = (HybridConversationNotificationView) notificationContentView.getChildAt(i2);
                        ((ImageView) hybridConversationNotificationView.findViewById(android.R.id.costsMoney)).setImageIcon(conversationLayout2.getConversationIcon());
                        TextView textView = (TextView) hybridConversationNotificationView.findViewById(R.id.conversation_notification_sender);
                        if (textView != null) {
                            textView.setTextColor(textColor2);
                        }
                    }
                }
            }
        } else if (view instanceof MessagingLayout) {
            ((MessagingLayout) view).setLayoutColor(color);
        }
        int resolveContrastColor = resolveContrastColor(context.getColor(R.color.notification_material_background_color), isNightModeOn(), expandableNotificationRow);
        if (view instanceof CallLayout) {
            CallLayout callLayout = (CallLayout) view;
            callLayout.setLayoutColor(resolveContrastColor);
            if (z) {
                callLayout.setNotificationBackgroundColor(i);
            }
        }
        updateMediaActions(view, z2);
        View findViewById2 = view.findViewById(android.R.id.progress);
        if (findViewById2 instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) findViewById2;
            ColorStateList valueOf = ColorStateList.valueOf(context.getColor(R.color.notification_progress_tint));
            ColorStateList valueOf2 = ColorStateList.valueOf(context.getColor(R.color.notification_progress_background_tint));
            if (DeviceState.isOpenTheme(context) || context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                valueOf = ColorStateList.valueOf(getTextColor(0, z2, true));
                valueOf2 = ColorStateList.valueOf(getTextColor(2, z2, true));
            }
            progressBar.setProgressBackgroundTintList(valueOf2);
            progressBar.setProgressTintList(valueOf);
            progressBar.setIndeterminateTintList(valueOf);
        }
        updateHeader(view, expandableNotificationRow, true);
        TextView textView2 = (TextView) view.findViewById(android.R.id.title);
        if (textView2 != null) {
            updateSpanned(textView2, z2);
            textView2.setTextColor(getTextColor(0, z2, true));
        }
        TextView textView3 = (TextView) view.findViewById(16909857);
        if (textView3 != null) {
            updateSpanned(textView3, z2);
            textView3.setTextColor(getTextColor(1, z2, true));
        }
    }

    public final void updateBig(View view, int i, boolean z, NotificationViewWrapper notificationViewWrapper, boolean z2, ExpandableNotificationRow expandableNotificationRow) {
        TextView textView;
        boolean z3;
        if (view == null) {
            return;
        }
        FrameLayout frameLayout = (FrameLayout) view.findViewById(android.R.id.actions_container_layout);
        if (frameLayout != null) {
            NotificationActionListLayout notificationActionListLayout = (LinearLayout) frameLayout.findViewById(android.R.id.actions_container);
            Context context = this.mContext;
            if (notificationActionListLayout != null) {
                Drawable drawable = context.getDrawable(android.R.drawable.perm_group_activity_recognition);
                if (DeviceState.isOpenTheme(context) || context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                    int textColor = getTextColor(1, z2, true);
                    drawable.setColorFilter(Color.argb(63, Color.red(textColor), Color.green(textColor), Color.blue(textColor)), PorterDuff.Mode.SRC_IN);
                }
                notificationActionListLayout.setDividerDrawable(drawable);
                int childCount = notificationActionListLayout.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    Button button = (Button) notificationActionListLayout.getChildAt(i2);
                    try {
                        z3 = notificationActionListLayout.isEmphasizedMode();
                    } catch (Exception e) {
                        Log.e("NotificationColorPicker", "Failed to check emphasized mode.");
                        e.printStackTrace();
                        z3 = false;
                    }
                    button.setTextColor(z3 ? -1 : getTextColor(0, z2, true));
                    if (button.getBackground() instanceof RippleDrawable) {
                        ((RippleDrawable) button.getBackground()).setColor(ColorStateList.valueOf(context.getColor(android.R.color.primary_text_leanback_formwizard_default_dark)));
                    }
                }
            }
            ImageView imageView = (ImageView) frameLayout.findViewById(android.R.id.button7);
            imageView.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView.setBackground(context.getDrawable(android.R.drawable.perm_group_app_info));
            ImageView imageView2 = (ImageView) frameLayout.findViewById(android.R.id.zoomIn);
            imageView2.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView2.setBackground(context.getDrawable(android.R.drawable.perm_group_app_info));
        }
        updateMediaActions(view, z2);
        View findViewById = view.findViewById(R.id.smart_reply_view);
        if (findViewById instanceof SmartReplyView) {
            ((SmartReplyView) findViewById).updateButtonColorOnUiModeChanged();
        }
        if ((notificationViewWrapper instanceof NotificationBigTextTemplateViewWrapper) && (textView = (TextView) view.findViewById(android.R.id.bubble_button)) != null) {
            updateSpanned(textView, z2);
            textView.setTextColor(getTextColor(1, z2, true));
        }
        for (int i3 = 0; i3 < 7; i3++) {
            TextView textView2 = (TextView) view.findViewById(NotificationColorSet.INBOX_ROWS[i3]);
            if (textView2 != null) {
                updateSpanned(textView2, z2);
                textView2.setTextColor(getTextColor(1, z2, true));
            }
        }
        updateBase(view, i, z, z2, expandableNotificationRow);
    }

    public final void updateHeader(View view, final ExpandableNotificationRow expandableNotificationRow, boolean z) {
        boolean z2;
        Drawable drawable;
        Drawable drawable2;
        if (view == null || expandableNotificationRow == null) {
            return;
        }
        int appPrimaryColor = getAppPrimaryColor(expandableNotificationRow);
        boolean isGrayScaleIcon = isGrayScaleIcon(expandableNotificationRow);
        boolean z3 = expandableNotificationRow.mDimmed;
        ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
        Context context = this.mContext;
        if (imageView != null) {
            z2 = isUseAppIcon(imageView);
            if (z2) {
                if (imageView.getBackground() != null) {
                    imageView.setColorFilter((ColorFilter) null);
                    imageView.setBackground(null);
                }
            } else if (isGrayScaleIcon) {
                int color = context.getColor(R.color.notification_app_icon_color);
                if (context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                    imageView.setColorFilter(Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)), PorterDuff.Mode.SRC_IN);
                } else {
                    imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
                if (imageView.getBackground() != null) {
                    if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                        imageView.setBackground(context.getDrawable(R.drawable.notification_icon_circle));
                    }
                    imageView.getBackground().setColorFilter(appPrimaryColor, PorterDuff.Mode.SRC_IN);
                }
            } else if (imageView.getBackground() != null) {
                setNonGrayScaleIconBackground(imageView, false);
            }
        } else {
            z2 = false;
        }
        ImageView imageView2 = (ImageView) view.findViewById(android.R.id.horizontal);
        if (imageView2 == null) {
            drawable = null;
        } else if (z2) {
            drawable = null;
            imageView2.setColorFilter((ColorFilter) null);
            Drawable newDrawable = imageView.getDrawable().getConstantState().newDrawable();
            newDrawable.mutate().setAlpha(76);
            imageView2.setImageDrawable(newDrawable);
        } else {
            drawable = null;
            if (isGrayScaleIcon) {
                if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView2.setImageDrawable(context.getDrawable(R.drawable.notification_icon_circle));
                }
                imageView2.setColorFilter(Color.argb((Color.alpha(appPrimaryColor) * 3) / 10, Color.red(appPrimaryColor), Color.green(appPrimaryColor), Color.blue(appPrimaryColor)), PorterDuff.Mode.SRC_IN);
            } else {
                setNonGrayScaleIconBackground(imageView2, true);
            }
        }
        ImageView imageView3 = (ImageView) view.findViewById(android.R.id.snooze_button);
        if (imageView3 != null) {
            float f = -20;
            imageView3.setColorFilter(new ColorMatrixColorFilter(new float[]{1.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 1.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 1.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 1.0f, f}));
        }
        NotificationExpandButton findViewById = view.findViewById(android.R.id.feedbackAudible);
        if (findViewById != null && z) {
            int textColor = getTextColor(2, z3, z);
            findViewById.setDefaultTextColor(Color.argb(178, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
            findViewById.setHighlightTextColor(getTextColor(1, z3, z));
        }
        TextView textView = (TextView) view.findViewById(android.R.id.audio);
        if (textView != null) {
            updateSpanned(textView, z3);
            textView.setTextColor(getTextColor(2, z3, z));
        }
        TextView textView2 = (TextView) view.findViewById(android.R.id.immersive_cling_back_bg_light);
        if (textView2 != null) {
            textView2.setTextColor(getTextColor(2, z3, z));
        }
        TextView textView3 = (TextView) view.findViewById(android.R.id.immersive_cling_back_bg);
        if (textView3 != null) {
            updateSpanned(textView3, z3);
            textView3.setTextColor(getTextColor(2, z3, z));
        }
        TextView textView4 = (TextView) view.findViewById(android.R.id.imageView);
        if (textView4 != null) {
            textView4.setTextColor(getTextColor(2, z3, z));
        }
        TextView textView5 = (TextView) view.findViewById(android.R.id.image);
        if (textView5 != null) {
            updateSpanned(textView5, z3);
            textView5.setTextColor(getTextColor(2, z3, z));
        }
        TextView textView6 = (TextView) view.findViewById(16909895);
        if (textView6 != null) {
            textView6.setTextColor(getTextColor(2, z3, z));
        }
        DateTimeView findViewById2 = view.findViewById(16909891);
        if (findViewById2 != null) {
            findViewById2.setTextColor(getTextColor(2, z3, z));
        }
        View findViewById3 = view.findViewById(android.R.id.clock);
        if (findViewById3 instanceof Chronometer) {
            Chronometer chronometer = (Chronometer) findViewById3;
            if (findViewById2 != null) {
                chronometer.setTextColor(getTextColor(1, z3, z));
            }
        }
        TextView textView7 = (TextView) view.findViewById(android.R.id.cycle);
        if (textView7 != null) {
            updateSpanned(textView7, z3);
            textView7.setTextColor(getTextColor(0, z3, z));
        }
        TextView textView8 = (TextView) view.findViewById(android.R.id.atThumb);
        if (textView8 != null) {
            textView8.setTextColor(getTextColor(1, z3, z));
        }
        TextView textView9 = (TextView) view.findViewById(16909986);
        if (textView9 != null) {
            textView9.setTextColor(getTextColor(1, z3, z));
        }
        TextView textView10 = (TextView) view.findViewById(16909988);
        if (textView10 != null) {
            updateSpanned(textView10, z3);
            textView10.setTextColor(getTextColor(1, z3, z));
        }
        ImageView imageView4 = (ImageView) view.findViewById(16909987);
        if (imageView4 != null) {
            imageView4.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView5 = (ImageView) view.findViewById(android.R.id.search_go_btn);
        if (imageView5 != null) {
            if (expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier() == 0) {
                drawable2 = drawable;
            } else {
                drawable2 = ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getResources().getDrawable(((UserManager) expandableNotificationRow.mEntry.mSbn.getPackageContext(context).getSystemService(UserManager.class)).isManagedProfile() ? "WORK_PROFILE_ICON" : PeripheralBarcodeConstants.Symbology.UNDEFINED, "SOLID_COLORED", "NOTIFICATION", new Supplier() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return NotificationColorPicker.this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier()), 0);
                    }
                });
            }
            imageView5.setImageDrawable(drawable2);
        }
        ImageView imageView6 = (ImageView) view.findViewById(android.R.id.retailDemo);
        if (imageView6 != null) {
            imageView6.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView7 = (ImageView) view.findViewById(android.R.id.alignBounds);
        if (imageView7 != null) {
            imageView7.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageButton imageButton = (ImageButton) view.findViewById(android.R.id.firstStrongLtr);
        if (imageButton != null) {
            imageButton.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
    }

    public final void updateIconTag(View view, ExpandableNotificationRow expandableNotificationRow) {
        CachingIconView cachingIconView;
        Drawable semGetApplicationIconForIconTray;
        Context context = this.mContext;
        if (view == null || expandableNotificationRow == null || (cachingIconView = (CachingIconView) view.findViewById(android.R.id.icon)) == null) {
            return;
        }
        Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
        cachingIconView.setTag(R.id.image_icon_tag, expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
        if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
            updateSmallIcon(view, expandableNotificationRow, cachingIconView);
            return;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = expandableNotificationRow.mEntry.mSbn.getPackageName();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
            if (!((packageName.equals("android") || packageName.equals("com.android.systemui") || applicationInfo.icon == 0) ? false : true)) {
                updateSmallIcon(view, expandableNotificationRow, cachingIconView);
                return;
            }
            SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
            settingsHelper.getClass();
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && settingsHelper.mItemLists.get("colortheme_app_icon").getIntValue() == 1) {
                List<LauncherActivityInfo> activityList = ((LauncherApps) context.getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
                semGetApplicationIconForIconTray = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(context.getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
            } else {
                semGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
            }
            cachingIconView.setColorFilter((ColorFilter) null);
            cachingIconView.setBackground((Drawable) null);
            cachingIconView.setPadding(0, 0, 0, 0);
            cachingIconView.setImageDrawable(semGetApplicationIconForIconTray);
            cachingIconView.setTag(R.id.use_app_icon, Boolean.TRUE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final void updateMediaActions(View view, boolean z) {
        LinearLayout linearLayout;
        if (view == null || (linearLayout = (LinearLayout) view.findViewById(android.R.id.no_drop)) == null) {
            return;
        }
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (imageView != null) {
                imageView.setColorFilter(getTextColor(0, z, true), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public final void updateSingleLine(HybridNotificationView hybridNotificationView, boolean z) {
        if (hybridNotificationView == null) {
            return;
        }
        TextView textView = (TextView) hybridNotificationView.findViewById(R.id.notification_title);
        if (textView != null) {
            updateSpanned(textView, z);
            textView.setTextColor(getTextColor(0, z, true));
        }
        TextView textView2 = (TextView) hybridNotificationView.findViewById(R.id.notification_text);
        if (textView2 != null) {
            updateSpanned(textView2, z);
            textView2.setTextColor(getTextColor(1, z, true));
        }
    }

    public final void updateSmallIcon(View view, ExpandableNotificationRow expandableNotificationRow, CachingIconView cachingIconView) {
        if ((view instanceof ConversationLayout) || (view instanceof CallLayout)) {
            cachingIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
        } else {
            int dimensionPixelSize = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_icon_circle_padding);
            cachingIconView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            cachingIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
            if (isGrayScaleIcon(expandableNotificationRow)) {
                if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    cachingIconView.setBackground(expandableNotificationRow.getContext().getDrawable(R.drawable.notification_icon_circle));
                }
                if (cachingIconView.getBackground() != null) {
                    cachingIconView.getBackground().setColorFilter(expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() ? resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow) : getAppPrimaryColor(expandableNotificationRow), PorterDuff.Mode.SRC_IN);
                }
            } else {
                setNonGrayScaleIconBackground(cachingIconView, false);
            }
        }
        cachingIconView.setTag(R.id.use_app_icon, Boolean.FALSE);
    }

    public final void updateSmallIconForCustom(View view, int i, boolean z) {
        ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
        if (imageView != null) {
            if (isUseAppIcon(imageView)) {
                if (imageView.getBackground() != null) {
                    imageView.setColorFilter((ColorFilter) null);
                    imageView.setBackground(null);
                    return;
                }
                return;
            }
            if (!z) {
                if (imageView.getBackground() != null) {
                    setNonGrayScaleIconBackground(imageView, false);
                    return;
                }
                return;
            }
            Context context = this.mContext;
            imageView.setColorFilter(context.getColor(R.color.notification_app_icon_color_for_custom), PorterDuff.Mode.SRC_IN);
            if (imageView.getBackground() != null) {
                if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView.setBackground(context.getDrawable(R.drawable.notification_icon_circle));
                }
                imageView.getBackground().setColorFilter(i, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        if (r4.getText().toString().equals(getSpanned(r4) == null ? null : getSpanned(r4).toString()) == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSpanned(TextView textView, boolean z) {
        CharSequence text = textView.getText();
        if (text != null && (text instanceof Spanned)) {
            if (getSpanned(textView) != null) {
            }
            textView.setTag(R.id.spannded_notification_text, textView.getText());
            Context context = this.mContext;
            if (DeviceState.isOpenTheme(context) || context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                textView.setText(ContrastColorUtil.clearColorSpans(text));
            } else if ((z && isNeedToInvert()) || isNeedToInvertinNightMode(z)) {
                textView.setText(ContrastColorUtil.clearColorSpans(text));
            } else {
                textView.setText(getSpanned(textView));
            }
        }
    }
}
