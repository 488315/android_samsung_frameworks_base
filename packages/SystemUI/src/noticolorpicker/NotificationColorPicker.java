package noticolorpicker;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.PrecomputedText;
import android.util.IndentingPrintWriter;
import android.util.Log;
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
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.NotificationActionListLayout;
import com.android.internal.widget.NotificationExpandButton;
import com.android.internal.widget.NotificationRowIconView;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridConversationNotificationView;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.PrecomputedTextView;
import com.android.systemui.statusbar.notification.row.TextPrecomputer;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationBigTextTemplateViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NotificationColorPicker implements Dumpable {
    public final Context mContext;
    public int mCustomedAlpha;
    public final ArrayList mThemeHistory = new ArrayList();
    private SettingsHelper.OnChangedCallback mOpenThemeChangedListener = new SettingsHelper.OnChangedCallback() { // from class: noticolorpicker.NotificationColorPicker.1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            StringBuilder sb = new StringBuilder();
            NotificationColorPicker notificationColorPicker = NotificationColorPicker.this;
            notificationColorPicker.getClass();
            Calendar calendar = Calendar.getInstance();
            sb.append(String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] ", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))));
            sb.append("theme (" + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getActiveThemePackage() + ") / dark (" + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isDarkTheme() + ") : ");
            StringBuilder sb2 = new StringBuilder("{Background #");
            sb2.append(Integer.toHexString(notificationColorPicker.mContext.getColor(R.color.open_theme_notification_bg_color)));
            sb.append(sb2.toString());
            sb.append(", Primary #" + Integer.toHexString(notificationColorPicker.mContext.getColor(R.color.open_theme_notification_title_text_color)));
            sb.append(", Secondary #" + Integer.toHexString(notificationColorPicker.mContext.getColor(R.color.open_theme_notification_content_text_color)));
            sb.append(", Header #" + Integer.toHexString(notificationColorPicker.mContext.getColor(R.color.open_theme_noti_header_color)) + "}");
            notificationColorPicker.mThemeHistory.add(sb.toString());
            if (notificationColorPicker.mThemeHistory.size() > 30) {
                notificationColorPicker.mThemeHistory.remove(0);
            }
            Log.d("NotificationColorPicker", sb.toString());
        }
    };
    public boolean mReduceTransparencyAndBlurOn = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled();

    public NotificationColorPicker(Context context, DumpManager dumpManager) {
        this.mContext = context;
        dumpManager.registerNormalDumpable("NotificationColorPicker", this);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mOpenThemeChangedListener, Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE), Settings.System.getUriFor(SettingsHelper.INDEX_DARK_THEME));
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
            boolean z2 = expandableNotificationRow.mEntry.isPromotedState() && expandableNotificationRow.mEntry.isOngoingActivity();
            boolean isProgressStyle = expandableNotificationRow.mEntry.isProgressStyle();
            if ((!isColorized && !isCustom && !z2 && !isProgressStyle) || z) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUseAppIcon(View view) {
        NotificationRowIconView notificationRowIconView;
        if (view == null) {
            return false;
        }
        NotificationRowIconView findViewById = view.findViewById(android.R.id.icon);
        if (!(findViewById instanceof NotificationRowIconView) || (notificationRowIconView = findViewById) == null || notificationRowIconView.getTag(R.id.use_app_icon) == null) {
            return false;
        }
        return ((Boolean) notificationRowIconView.getTag(R.id.use_app_icon)).booleanValue();
    }

    public static void setTextAsync(TextView textView, CharSequence charSequence) {
        if (textView instanceof PrecomputedTextView) {
            PrecomputedTextView precomputedTextView = (PrecomputedTextView) textView;
            precomputedTextView.getClass();
            TextPrecomputer.precompute$default(precomputedTextView, precomputedTextView, charSequence).run();
        } else {
            try {
                textView.setText(PrecomputedText.create(charSequence, textView.getTextMetricsParams()));
            } catch (IllegalArgumentException e) {
                Log.wtf("NotificationColorPicker", "PrecomputedText setText failed for TextView:$textView", e);
                textView.setText(charSequence);
            }
        }
    }

    public final void applyShawdow(View view) {
        Bitmap bitmap;
        CachingIconView findViewById = view.findViewById(android.R.id.dvorak);
        if (findViewById == null || findViewById.getDrawable() == null) {
            return;
        }
        findViewById.setColorFilter(this.mContext.getColor(R.color.notification_icon_stroke_color));
        Drawable mutate = findViewById.getDrawable().mutate();
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_application_icon_stroke_size);
        Drawable mutate2 = mutate.mutate();
        if (mutate2 == null || mutate2.getIntrinsicWidth() <= 0 || mutate2.getIntrinsicHeight() <= 0) {
            bitmap = null;
        } else {
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_circle);
            Drawable mutate3 = mutate2.mutate();
            Bitmap createBitmap = Bitmap.createBitmap(mutate2.getIntrinsicWidth(), mutate2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            mutate3.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            mutate3.draw(canvas);
            bitmap = Bitmap.createScaledBitmap(createBitmap, dimensionPixelSize2, dimensionPixelSize2, true);
        }
        findViewById.setColorFilter((ColorFilter) null);
        Canvas canvas2 = new Canvas(bitmap);
        mutate.setBounds(dimensionPixelSize, dimensionPixelSize, canvas2.getWidth() - dimensionPixelSize, canvas2.getHeight() - dimensionPixelSize);
        mutate.draw(canvas2);
        findViewById.setImageBitmap(bitmap);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationColorPicker notificationColorPicker = NotificationColorPicker.this;
                IndentingPrintWriter indentingPrintWriter = asIndenting;
                notificationColorPicker.getClass();
                indentingPrintWriter.println("[History] :");
                ArrayList arrayList = notificationColorPicker.mThemeHistory;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    String str = (String) obj;
                    if (str != null) {
                        indentingPrintWriter.println("   ".concat(str));
                    }
                }
            }
        });
    }

    public final int getAppPrimaryColor(ExpandableNotificationRow expandableNotificationRow) {
        int resolveHeaderAppIconColor = resolveHeaderAppIconColor(expandableNotificationRow);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        if (NotiRune.NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME && settingsHelper.isWallpaperThemeSettingsOn() && (expandableNotificationRow.mEntry.mSbn.getNotification().color == 0 || settingsHelper.isApplyWallpaperThemeToNotif())) {
            resolveHeaderAppIconColor = settingsHelper.isColorPaletteGrayEnabled() ? this.mContext.getColor(R.color.qs_tile_icon_on_dim_tint_color) : this.mContext.getColor(R.color.open_theme_noti_header_color);
        }
        if (DeviceState.isOpenTheme(this.mContext)) {
            resolveHeaderAppIconColor = this.mContext.getColor(R.color.open_theme_noti_header_color);
        }
        if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            resolveHeaderAppIconColor = this.mContext.getResources().getColor(R.color.qp_notification_primary_color);
        }
        return (resolveHeaderAppIconColor == 0 || resolveHeaderAppIconColor == 1) ? ContrastColorUtil.resolveDefaultColor(this.mContext, -1, false) : resolveHeaderAppIconColor;
    }

    public final int getExpandButtonColor(boolean z, boolean z2) {
        if (isDarkMode$1() && this.mReduceTransparencyAndBlurOn) {
            return this.mContext.getResources().getColor(R.color.notification_expand_button_color_reduce_transparency_and_blur);
        }
        int textColor = getTextColor(2, z, z2);
        return Color.argb(153, Color.red(textColor), Color.green(textColor), Color.blue(textColor));
    }

    public final int getGutsTextColor() {
        return DeviceState.isOpenTheme(this.mContext) ? getTextColor(0, false, true) : this.mContext.getResources().getColor(R.color.notification_guts_common_text_color);
    }

    public final int getNotificationBgColor() {
        int notificationDefaultBgColor = getNotificationDefaultBgColor();
        this.mCustomedAlpha = Color.alpha(notificationDefaultBgColor);
        int argb = Color.argb(255, Color.red(notificationDefaultBgColor), Color.green(notificationDefaultBgColor), Color.blue(notificationDefaultBgColor));
        if (DeviceState.isOpenTheme(this.mContext)) {
            int color = this.mContext.getResources().getColor(R.color.open_theme_notification_bg_color, null);
            this.mCustomedAlpha = Color.alpha(color);
            argb = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
        }
        if (!this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            return argb;
        }
        int color2 = this.mContext.getResources().getColor(R.color.qp_notification_background_color, null);
        this.mCustomedAlpha = Color.alpha(color2);
        return Color.argb(255, Color.red(color2), Color.green(color2), Color.blue(color2));
    }

    public final int getNotificationDefaultBgColor() {
        return this.mReduceTransparencyAndBlurOn ? this.mContext.getResources().getColor(R.color.notification_material_background_color_reduce_transparency_and_blur, null) : this.mContext.getResources().getColor(R.color.notification_material_background_color, null);
    }

    public final int getTextColor(int i, boolean z, boolean z2) {
        if (!z2) {
            return this.mContext.getResources().getColor(R.color.notification_no_background_header_text_color);
        }
        boolean isNeedToInvertinNightMode = isNeedToInvertinNightMode(z);
        if (z && !isNeedToInvert()) {
            z = false;
        }
        if (i == 0) {
            Context context = this.mContext;
            int color = z ? context.getColor(android.R.color.system_theme_app_light) : ContrastColorUtil.resolvePrimaryColor(context, 0, isNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(this.mContext)) {
                color = this.mContext.getResources().getColor(R.color.open_theme_notification_title_text_color, null);
            }
            return this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? this.mContext.getResources().getColor(R.color.qp_notification_title_color) : color;
        }
        if (i == 1) {
            Context context2 = this.mContext;
            int color2 = z ? context2.getColor(android.R.color.system_under_surface_light) : ContrastColorUtil.resolveSecondaryColor(context2, 0, isNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(this.mContext)) {
                color2 = this.mContext.getResources().getColor(R.color.open_theme_notification_content_text_color, null);
            }
            return this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? this.mContext.getResources().getColor(R.color.qp_notification_content_color) : color2;
        }
        if (i != 2) {
            return ContrastColorUtil.resolveDefaultColor(this.mContext, 0, isNeedToInvertinNightMode);
        }
        int color3 = (z || isNeedToInvertinNightMode) ? this.mContext.getColor(android.R.color.tab_highlight_material) : this.mContext.getColor(android.R.color.tab_indicator_material);
        if (DeviceState.isOpenTheme(this.mContext)) {
            color3 = this.mContext.getResources().getColor(R.color.open_theme_notification_content_text_color, null);
        }
        return this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? this.mContext.getResources().getColor(R.color.qp_notification_content_color) : color3;
    }

    public final boolean isDarkMode$1() {
        return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final boolean isGrayScaleIcon(ExpandableNotificationRow expandableNotificationRow) {
        return NotificationUtils.isGrayscale(expandableNotificationRow.mEntry.mIcons.mStatusBarIcon, ContrastColorUtil.getInstance(this.mContext));
    }

    public final boolean isNeedToInvert() {
        return !this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) && !DeviceState.isOpenTheme(this.mContext) && ContrastColorUtil.shouldInvertTextColor(((float) ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getLockNoticardOpacity()) * 0.01f, ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isWhiteKeyguardWallpaper()) && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getActiveThemePackage() == null;
    }

    public final boolean isNeedToInvertinNightMode(boolean z) {
        boolean z2 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) || DeviceState.isOpenTheme(this.mContext) || !z || !z2 || !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isWhiteKeyguardWallpaper() || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getLockNoticardOpacity() * 0.01f >= 0.25f) {
            return z2;
        }
        return false;
    }

    public final boolean isNightModeOn() {
        return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final Drawable resizeDrawable(Drawable drawable, int i, int i2) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int intrinsicWidth = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 1;
            int intrinsicHeight = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 1;
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
            bitmap = createBitmap;
        }
        return new BitmapDrawable(this.mContext.getResources(), Bitmap.createScaledBitmap(bitmap, i, i2, true));
    }

    public final int resolveContrastColor(int i, boolean z, ExpandableNotificationRow expandableNotificationRow) {
        int i2 = (!expandableNotificationRow.mIsMinimized || expandableNotificationRow.isInsignificant()) ? expandableNotificationRow.mEntry.mSbn.getNotification().color : 0;
        int resolveDefaultColor = i2 == 0 ? ContrastColorUtil.resolveDefaultColor(this.mContext, 0, z) : ContrastColorUtil.resolveContrastColor(this.mContext, i2, i, z);
        if (Color.alpha(resolveDefaultColor) < 255) {
            resolveDefaultColor = ContrastColorUtil.compositeColors(resolveDefaultColor, i);
        }
        if (!z) {
            return resolveDefaultColor;
        }
        Context context = this.mContext;
        return ContrastColorUtil.resolveContrastColor(context, resolveDefaultColor, context.getColor(R.color.notification_app_icon_color), !z);
    }

    public final int resolveHeaderAppIconColor(ExpandableNotificationRow expandableNotificationRow) {
        return (!expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() || expandableNotificationRow.mBgTint == 0 || (expandableNotificationRow.mIsMinimized && !expandableNotificationRow.isInsignificant())) ? resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow) : getTextColor(0, expandableNotificationRow.mDimmed, true);
    }

    public final void setNonGrayScaleIconBackground(ImageView imageView, boolean z) {
        int color = this.mContext.getColor(R.color.notification_non_grayscale_border_color);
        int color2 = this.mContext.getColor(R.color.notification_non_grayscale_fill_color);
        if (z) {
            int alpha = (Color.alpha(color) * 3) / 10;
            color2 = Color.argb((Color.alpha(color2) * 3) / 10, Color.red(color2), Color.green(color2), Color.blue(color2));
            color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
            Drawable drawable = this.mContext.getDrawable(R.drawable.squircle);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(color2, mode);
            Drawable drawable2 = this.mContext.getDrawable(R.drawable.squircle_tray_stroke);
            drawable2.setColorFilter(color, mode);
            imageView.setBackground(new LayerDrawable(new Drawable[]{drawable, drawable2}));
            return;
        }
        if (z) {
            imageView.setImageDrawable(this.mContext.getDrawable(R.drawable.notification_icon_circle));
            GradientDrawable gradientDrawable = (GradientDrawable) imageView.getDrawable().mutate();
            gradientDrawable.setColor(color2);
            gradientDrawable.setStroke(this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
            return;
        }
        imageView.setBackground(this.mContext.getDrawable(R.drawable.notification_icon_circle));
        GradientDrawable gradientDrawable2 = (GradientDrawable) imageView.getBackground().mutate();
        gradientDrawable2.setColor(color2);
        gradientDrawable2.setStroke(this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_icon_border_width), color);
    }

    public final void setPrimaryColor(TextView textView, boolean z) {
        if (textView != null) {
            updateSpanned(textView, z);
            textView.setTextColor(getTextColor(0, z, true));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:227:0x0402  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0427  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAllTextViewColors(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 1079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: noticolorpicker.NotificationColorPicker.updateAllTextViewColors(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, boolean):void");
    }

    public final void updateBase(View view, int i, boolean z, boolean z2, ExpandableNotificationRow expandableNotificationRow) {
        final NotificationColorPicker notificationColorPicker;
        final int i2;
        final boolean z3;
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
            notificationColorPicker = this;
            i2 = i;
            z3 = z;
            conversationLayout.getMessagingGroups().stream().filter(new NotificationColorPicker$$ExternalSyntheticLambda1()).forEach(new Consumer() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationColorPicker notificationColorPicker2 = NotificationColorPicker.this;
                    int i3 = textColor;
                    int i4 = textColor2;
                    boolean z4 = z3;
                    int i5 = i2;
                    MessagingGroup messagingGroup = (MessagingGroup) obj;
                    notificationColorPicker2.getClass();
                    messagingGroup.setTextColors(i3, i4);
                    List messages = messagingGroup.getMessages();
                    for (int i6 = 0; i6 < messages.size(); i6++) {
                        if (((MessagingMessage) messages.get(i6)).getView() instanceof TextView) {
                            int dimensionPixelSize = notificationColorPicker2.mContext.getResources().getDimensionPixelSize(android.R.dimen.timepicker_time_label_size);
                            TextView textView = (TextView) ((MessagingMessage) messages.get(i6)).getView();
                            textView.setLineSpacing(dimensionPixelSize, textView.getLineSpacingMultiplier());
                        }
                    }
                    if (z4) {
                        messagingGroup.setLayoutColor(i5);
                    }
                }
            });
            if (conversationLayout instanceof ConversationLayout) {
                conversationLayout.setSenderTextColor(textColor);
            }
        } else {
            notificationColorPicker = this;
            i2 = i;
            z3 = z;
        }
        int color = notificationColorPicker.mContext.getColor(R.color.notification_material_background_color);
        notificationColorPicker.resolveContrastColor(color, notificationColorPicker.isNightModeOn(), expandableNotificationRow);
        if (view instanceof ConversationLayout) {
            ConversationLayout conversationLayout2 = (ConversationLayout) view;
            conversationLayout2.setLayoutColor(color);
            conversationLayout2.setNotificationBackgroundColor(i2);
            ImageView imageView = (ImageView) conversationLayout2.findViewById(android.R.id.icon);
            if (imageView != null) {
                if (isUseAppIcon(imageView)) {
                    conversationLayout2.findViewById(android.R.id.editable).setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 0);
                    imageView.setLayoutParams(layoutParams);
                } else {
                    conversationLayout2.findViewById(android.R.id.editable).setVisibility(0);
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                    int dimensionPixelSize = notificationColorPicker.mContext.getResources().getDimensionPixelSize(R.dimen.notification_shelf_tooltip_bottom);
                    layoutParams2.setMargins(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                    imageView.setLayoutParams(layoutParams2);
                    imageView.setColorFilter(notificationColorPicker.mContext.getColor(R.color.notification_app_icon_color), PorterDuff.Mode.SRC_IN);
                }
            }
            ImageView imageView2 = (ImageView) conversationLayout2.findViewById(android.R.id.edittext_container);
            if (imageView2 != null && imageView2.getVisibility() == 0) {
                int color2 = notificationColorPicker.mContext.getColor(android.R.color.dim_foreground_inverse_holo_dark);
                if (isUseAppIcon(conversationLayout2)) {
                    imageView2.setImageDrawable((VectorDrawable) notificationColorPicker.mContext.getDrawable(R.drawable.squircle_tray_stroke_small));
                    imageView2.setColorFilter(color2);
                } else {
                    imageView2.setColorFilter((ColorFilter) null);
                    imageView2.setImageDrawable(notificationColorPicker.mContext.getDrawable(android.R.drawable.edit_query_background_pressed));
                    ((GradientDrawable) imageView2.getDrawable().mutate()).setStroke(notificationColorPicker.mContext.getResources().getDimensionPixelSize(R.dimen.importance_ring_stroke_width), color2);
                }
            }
            ViewParent parent = conversationLayout2.getParent();
            if (parent != null && (parent instanceof NotificationContentView)) {
                NotificationContentView notificationContentView = (NotificationContentView) parent;
                for (int i3 = 0; i3 < notificationContentView.getChildCount(); i3++) {
                    if (notificationContentView.getChildAt(i3) instanceof HybridConversationNotificationView) {
                        HybridConversationNotificationView hybridConversationNotificationView = (HybridConversationNotificationView) notificationContentView.getChildAt(i3);
                        ((ImageView) hybridConversationNotificationView.findViewById(android.R.id.dvorak)).setImageIcon(conversationLayout2.getConversationIcon());
                        TextView textView = (TextView) hybridConversationNotificationView.findViewById(R.id.conversation_notification_sender);
                        if (textView != null) {
                            textView.setTextColor(textColor2);
                        }
                    }
                }
            }
        } else if (view instanceof MessagingLayout) {
            MessagingLayout messagingLayout = (MessagingLayout) view;
            messagingLayout.setLayoutColor(color);
            View findViewById2 = messagingLayout.findViewById(android.R.id.remote_input);
            if (findViewById2 != null) {
                findViewById2.setPadding(notificationColorPicker.mContext.getResources().getDimensionPixelSize(R.dimen.notification_big_messaging_header_padding_start), findViewById2.getPaddingTop(), findViewById2.getPaddingRight(), findViewById2.getPaddingBottom());
            }
        }
        int resolveContrastColor = notificationColorPicker.resolveContrastColor(notificationColorPicker.mContext.getColor(R.color.notification_material_background_color), notificationColorPicker.isNightModeOn(), expandableNotificationRow);
        if (view instanceof CallLayout) {
            CallLayout callLayout = (CallLayout) view;
            callLayout.setLayoutColor(resolveContrastColor);
            if (z3) {
                callLayout.setNotificationBackgroundColor(i2);
            }
        }
        notificationColorPicker.updateMediaActions(view, z2);
        View findViewById3 = view.findViewById(android.R.id.progress);
        if (findViewById3 instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) findViewById3;
            ColorStateList valueOf = ColorStateList.valueOf(notificationColorPicker.mContext.getColor(R.color.notification_progress_tint));
            ColorStateList valueOf2 = ColorStateList.valueOf(notificationColorPicker.mContext.getColor(R.color.notification_progress_background_tint));
            if (DeviceState.isOpenTheme(notificationColorPicker.mContext) || notificationColorPicker.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                valueOf = ColorStateList.valueOf(notificationColorPicker.getTextColor(0, z2, true));
                valueOf2 = ColorStateList.valueOf(notificationColorPicker.getTextColor(2, z2, true));
            }
            progressBar.setProgressBackgroundTintList(valueOf2);
            progressBar.setProgressTintList(valueOf);
            progressBar.setIndeterminateTintList(valueOf);
        }
        notificationColorPicker.updateHeader(view, expandableNotificationRow, true);
        TextView textView2 = (TextView) view.findViewById(android.R.id.title);
        if (textView2 != null) {
            notificationColorPicker.updateSpanned(textView2, z2);
            textView2.setTextColor(notificationColorPicker.getTextColor(0, z2, true));
        }
        TextView textView3 = (TextView) view.findViewById(16909931);
        if (textView3 != null) {
            notificationColorPicker.updateSpanned(textView3, z2);
            textView3.setTextColor(notificationColorPicker.getTextColor(1, z2, true));
            textView3.setLineSpacing(notificationColorPicker.mContext.getResources().getDimensionPixelSize(android.R.dimen.timepicker_time_label_size), textView3.getLineSpacingMultiplier());
        }
    }

    public final void updateBig(View view, int i, boolean z, NotificationViewWrapper notificationViewWrapper, boolean z2, ExpandableNotificationRow expandableNotificationRow) {
        TextView textView;
        boolean z3;
        if (view == null) {
            return;
        }
        FrameLayout frameLayout = (FrameLayout) view.findViewById(android.R.id.animator);
        if (frameLayout != null) {
            NotificationActionListLayout notificationActionListLayout = (LinearLayout) frameLayout.findViewById(android.R.id.animation);
            if (notificationActionListLayout != null) {
                Drawable drawable = this.mContext.getDrawable(android.R.drawable.pointer_wait_31);
                if (DeviceState.isOpenTheme(this.mContext) || this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
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
                        ((RippleDrawable) button.getBackground()).setColor(ColorStateList.valueOf(this.mContext.getColor(android.R.color.system_on_theme_app_ring_dark)));
                    }
                }
            }
            ImageView imageView = (ImageView) frameLayout.findViewById(android.R.id.clamp);
            imageView.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView.setBackground(this.mContext.getDrawable(android.R.drawable.pointer_wait_33));
            ImageView imageView2 = (ImageView) frameLayout.findViewById(16909854);
            imageView2.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView2.setBackground(this.mContext.getDrawable(android.R.drawable.pointer_wait_33));
        }
        updateMediaActions(view, z2);
        View findViewById = view.findViewById(R.id.smart_reply_view);
        if (findViewById instanceof SmartReplyView) {
            ((SmartReplyView) findViewById).updateButtonColorOnUiModeChanged();
        }
        if ((notificationViewWrapper instanceof NotificationBigTextTemplateViewWrapper) && (textView = (TextView) view.findViewById(android.R.id.choice)) != null) {
            updateSpanned(textView, z2);
            textView.setTextColor(getTextColor(1, z2, true));
            textView.setLineSpacing(this.mContext.getResources().getDimensionPixelSize(android.R.dimen.timepicker_time_label_size), textView.getLineSpacingMultiplier());
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
        int i;
        Drawable drawable2;
        if (view == null || expandableNotificationRow == null) {
            return;
        }
        int appPrimaryColor = getAppPrimaryColor(expandableNotificationRow);
        boolean isGrayScaleIcon = isGrayScaleIcon(expandableNotificationRow);
        boolean z3 = expandableNotificationRow.mDimmed;
        ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
        if (imageView != null) {
            z2 = isUseAppIcon(imageView);
            if (z2) {
                if (imageView.getBackground() != null) {
                    imageView.setColorFilter((ColorFilter) null);
                    imageView.setBackground(null);
                }
            } else if (isGrayScaleIcon) {
                int color = this.mContext.getColor(R.color.notification_app_icon_color);
                if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                    imageView.setColorFilter(Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)), PorterDuff.Mode.SRC_IN);
                } else {
                    imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView.setBackground(this.mContext.getDrawable(R.drawable.squircle));
                } else {
                    imageView.setBackground(this.mContext.getDrawable(R.drawable.notification_icon_circle));
                }
                imageView.getBackground().setColorFilter(appPrimaryColor, PorterDuff.Mode.SRC_IN);
            } else if (imageView.getBackground() != null) {
                setNonGrayScaleIconBackground(imageView, false);
            }
        } else {
            z2 = false;
        }
        if (expandableNotificationRow.isInsignificantSummary()) {
            ImageView imageView2 = (ImageView) view.findViewById(android.R.id.mediaProcessing);
            if (imageView2 != null) {
                imageView2.setBackground(null);
                imageView2.setColorFilter((ColorFilter) null);
                imageView2.setImageDrawable(null);
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView2.setBackground(this.mContext.getDrawable(R.drawable.squircle));
                } else {
                    imageView2.setBackground(this.mContext.getDrawable(R.drawable.notification_icon_circle));
                }
                int color2 = this.mContext.getColor(R.color.notification_insignificant_icon_shadow_color);
                imageView2.getBackground().setColorFilter(Color.argb(isDarkMode$1() ? 70 : 25, Color.red(color2), Color.green(color2), Color.blue(color2)), PorterDuff.Mode.SRC_IN);
            }
            FrameLayout frameLayout = (FrameLayout) view.findViewById(android.R.id.mediaPlayback);
            if (frameLayout != null) {
                ImageView imageView3 = (ImageView) frameLayout.findViewById(android.R.id.icon);
                if (DeviceState.isOpenTheme(this.mContext) || this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                    if (imageView3 != null) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView3.getLayoutParams();
                        layoutParams.setMargins(this.mContext.getResources().getDimensionPixelSize(R.dimen.insignificant_notification_icon_margin), 0, 0, 0);
                        imageView3.setLayoutParams(layoutParams);
                    }
                    if (imageView2 != null) {
                        imageView2.setVisibility(8);
                    }
                } else {
                    if (imageView3 != null) {
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView3.getLayoutParams();
                        layoutParams2.setMargins(0, 0, 0, 0);
                        imageView3.setLayoutParams(layoutParams2);
                    }
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                }
            }
        }
        ImageView imageView4 = (ImageView) view.findViewById(android.R.id.input_separator);
        if (imageView4 == null) {
            drawable = null;
        } else if (z2) {
            drawable = null;
            imageView4.setColorFilter((ColorFilter) null);
            Drawable newDrawable = imageView.getDrawable().getConstantState().newDrawable();
            newDrawable.mutate().setAlpha(76);
            imageView4.setImageDrawable(newDrawable);
        } else {
            drawable = null;
            if (isGrayScaleIcon) {
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView4.setImageDrawable(this.mContext.getDrawable(R.drawable.squircle));
                }
                imageView4.setColorFilter(Color.argb((Color.alpha(appPrimaryColor) * 3) / 10, Color.red(appPrimaryColor), Color.green(appPrimaryColor), Color.blue(appPrimaryColor)), PorterDuff.Mode.SRC_IN);
            } else {
                setNonGrayScaleIconBackground(imageView4, true);
            }
        }
        ImageView imageView5 = (ImageView) view.findViewById(android.R.id.tag_top_animator);
        if (imageView5 != null) {
            float f = -10;
            imageView5.setColorFilter(new ColorMatrixColorFilter(new float[]{1.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 1.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 1.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 1.0f, f}));
        }
        NotificationExpandButton findViewById = view.findViewById(android.R.id.flagRetrieveInteractiveWindows);
        if (findViewById != null && z) {
            findViewById.setDefaultTextColor(getExpandButtonColor(z3, z));
        }
        TextView textView = (TextView) view.findViewById(android.R.id.input_minute);
        if (textView != null) {
            textView.setTextColor(getExpandButtonColor(z3, z));
        }
        TextView textView2 = (TextView) view.findViewById(android.R.id.beforeDescendants);
        if (textView2 != null) {
            updateSpanned(textView2, z3);
            i = 2;
            textView2.setTextColor(getTextColor(2, z3, z));
        } else {
            i = 2;
        }
        TextView textView3 = (TextView) view.findViewById(android.R.id.internalOnly);
        if (textView3 != null) {
            textView3.setTextColor(getTextColor(i, z3, z));
        }
        TextView textView4 = (TextView) view.findViewById(android.R.id.internalEmpty);
        if (textView4 != null) {
            updateSpanned(textView4, z3);
            textView4.setTextColor(getTextColor(i, z3, z));
        }
        TextView textView5 = (TextView) view.findViewById(android.R.id.internal);
        if (textView5 != null) {
            textView5.setTextColor(getTextColor(i, z3, z));
        }
        TextView textView6 = (TextView) view.findViewById(android.R.id.inter_word);
        if (textView6 != null) {
            updateSpanned(textView6, z3);
            textView6.setTextColor(getTextColor(i, z3, z));
        }
        TextView textView7 = (TextView) view.findViewById(16909971);
        if (textView7 != null) {
            textView7.setTextColor(getTextColor(i, z3, z));
        }
        DateTimeView findViewById2 = view.findViewById(16909967);
        if (findViewById2 != null) {
            int textColor = getTextColor(i, z3, z);
            findViewById2.setTextColor(Color.argb((int) (Color.alpha(textColor) * 0.7f), Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }
        View findViewById3 = view.findViewById(android.R.id.conversation_face_pile_bottom);
        if (findViewById3 instanceof Chronometer) {
            Chronometer chronometer = (Chronometer) findViewById3;
            if (findViewById2 != null) {
                chronometer.setTextColor(getTextColor(1, z3, z));
            }
        }
        TextView textView8 = (TextView) view.findViewById(android.R.id.end);
        if (textView8 != null) {
            updateSpanned(textView8, z3);
            textView8.setTextColor(getTextColor(0, z3, z));
        }
        TextView textView9 = (TextView) view.findViewById(android.R.id.exclude);
        if (textView9 != null) {
            textView9.setBackground(this.mContext.getDrawable(android.R.drawable.edit_query_background_selected));
        }
        TextView textView10 = (TextView) view.findViewById(android.R.id.balanced);
        if (textView10 != null) {
            textView10.setTextColor(getTextColor(1, z3, z));
        }
        TextView textView11 = (TextView) view.findViewById(16910060);
        if (textView11 != null) {
            textView11.setTextColor(getTextColor(1, z3, z));
        }
        TextView textView12 = (TextView) view.findViewById(16910062);
        if (textView12 != null) {
            updateSpanned(textView12, z3);
            textView12.setTextColor(getTextColor(1, z3, z));
        }
        ImageView imageView6 = (ImageView) view.findViewById(16910061);
        if (imageView6 != null) {
            imageView6.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView7 = (ImageView) view.findViewById(android.R.id.small);
        if (imageView7 != null) {
            if (expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier() == 0) {
                drawable2 = drawable;
            } else {
                drawable2 = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getDrawable((((UserManager) expandableNotificationRow.mEntry.mSbn.getPackageContext(this.mContext).getSystemService(UserManager.class)).isManagedProfile() || ((UserManager) expandableNotificationRow.mEntry.mSbn.getPackageContext(this.mContext).getSystemService(UserManager.class)).isPrivateProfile()) ? "WORK_PROFILE_ICON" : PeripheralBarcodeConstants.Symbology.UNDEFINED, "SOLID_COLORED", "NOTIFICATION", new Supplier() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return NotificationColorPicker.this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier()), 0);
                    }
                });
            }
            imageView7.setImageDrawable(drawable2);
        }
        ImageView imageView8 = (ImageView) view.findViewById(android.R.id.sequentially);
        if (imageView8 != null) {
            imageView8.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView9 = (ImageView) view.findViewById(android.R.id.autofill_dataset_icon);
        if (imageView9 != null) {
            imageView9.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
        ImageButton imageButton = (ImageButton) view.findViewById(android.R.id.game);
        if (imageButton != null) {
            imageButton.setColorFilter(getTextColor(1, z3, z), PorterDuff.Mode.SRC_IN);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
    
        if (((r3.startsWith("com.samsung") || r3.startsWith("com.sec")) ? false : true) == false) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0086 A[Catch: NameNotFoundException -> 0x0119, TryCatch #0 {NameNotFoundException -> 0x0119, blocks: (B:11:0x001f, B:13:0x0052, B:15:0x0058, B:17:0x0060, B:23:0x0086, B:25:0x0099, B:27:0x00a7, B:29:0x00ad, B:30:0x00cf, B:32:0x00ee, B:33:0x00f4, B:35:0x00fa, B:36:0x00fe, B:38:0x0111, B:42:0x00c4, B:43:0x00cb, B:44:0x0115, B:47:0x006d, B:49:0x0075, B:51:0x007d), top: B:10:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0099 A[Catch: NameNotFoundException -> 0x0119, TryCatch #0 {NameNotFoundException -> 0x0119, blocks: (B:11:0x001f, B:13:0x0052, B:15:0x0058, B:17:0x0060, B:23:0x0086, B:25:0x0099, B:27:0x00a7, B:29:0x00ad, B:30:0x00cf, B:32:0x00ee, B:33:0x00f4, B:35:0x00fa, B:36:0x00fe, B:38:0x0111, B:42:0x00c4, B:43:0x00cb, B:44:0x0115, B:47:0x006d, B:49:0x0075, B:51:0x007d), top: B:10:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0115 A[Catch: NameNotFoundException -> 0x0119, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0119, blocks: (B:11:0x001f, B:13:0x0052, B:15:0x0058, B:17:0x0060, B:23:0x0086, B:25:0x0099, B:27:0x00a7, B:29:0x00ad, B:30:0x00cf, B:32:0x00ee, B:33:0x00f4, B:35:0x00fa, B:36:0x00fe, B:38:0x0111, B:42:0x00c4, B:43:0x00cb, B:44:0x0115, B:47:0x006d, B:49:0x0075, B:51:0x007d), top: B:10:0x001f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIconTag(android.view.View r10, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: noticolorpicker.NotificationColorPicker.updateIconTag(android.view.View, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow):void");
    }

    public final void updateMediaActions(View view, boolean z) {
        LinearLayout linearLayout;
        if (view == null || (linearLayout = (LinearLayout) view.findViewById(android.R.id.option2)) == null) {
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

    public final void updateSmallIcon(View view, ExpandableNotificationRow expandableNotificationRow, NotificationRowIconView notificationRowIconView) {
        int i;
        if ((view instanceof ConversationLayout) || (view instanceof CallLayout)) {
            notificationRowIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
        } else {
            int dimensionPixelSize = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_icon_circle_padding);
            notificationRowIconView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            Icon smallIcon = expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon();
            int dimensionPixelSize2 = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_squircle);
            Drawable loadDrawable = smallIcon.loadDrawable(notificationRowIconView.getContext());
            if (loadDrawable == null || (loadDrawable instanceof Animatable) || (loadDrawable instanceof Animatable2) || loadDrawable.getIntrinsicHeight() <= (i = dimensionPixelSize2 * 2) || loadDrawable.getIntrinsicWidth() <= i) {
                notificationRowIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
            } else {
                int maxDrawableWidth = notificationRowIconView.getMaxDrawableWidth() > 0 ? notificationRowIconView.getMaxDrawableWidth() : dimensionPixelSize2;
                if (notificationRowIconView.getMaxDrawableHeight() > 0) {
                    dimensionPixelSize2 = notificationRowIconView.getMaxDrawableHeight();
                }
                notificationRowIconView.setImageDrawable(resizeDrawable(loadDrawable, maxDrawableWidth, dimensionPixelSize2));
            }
            if (isGrayScaleIcon(expandableNotificationRow)) {
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    notificationRowIconView.setBackground(expandableNotificationRow.getContext().getDrawable(R.drawable.squircle));
                } else {
                    notificationRowIconView.setBackground(expandableNotificationRow.getContext().getDrawable(R.drawable.notification_icon_circle));
                }
                if (notificationRowIconView.getBackground() != null) {
                    notificationRowIconView.getBackground().setColorFilter(expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() ? resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow) : getAppPrimaryColor(expandableNotificationRow), PorterDuff.Mode.SRC_IN);
                }
            } else {
                setNonGrayScaleIconBackground(notificationRowIconView, false);
            }
        }
        notificationRowIconView.setTag(R.id.use_app_icon, Boolean.FALSE);
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
            int color = this.mContext.getColor(R.color.notification_app_icon_color_for_custom);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            imageView.setColorFilter(color, mode);
            if (imageView.getBackground() != null) {
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView.setBackground(this.mContext.getDrawable(R.drawable.squircle));
                }
                imageView.getBackground().setColorFilter(i, mode);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:
    
        if (r2.equals(r4) == false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSpanned(android.widget.TextView r6, boolean r7) {
        /*
            r5 = this;
            java.lang.CharSequence r0 = r6.getText()
            if (r0 == 0) goto L8e
            boolean r1 = r0 instanceof android.text.Spanned
            if (r1 == 0) goto L8e
            r1 = 2131364991(0x7f0a0c7f, float:1.8349835E38)
            java.lang.Object r2 = r6.getTag(r1)
            r3 = 0
            if (r2 != 0) goto L16
            r2 = r3
            goto L18
        L16:
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2
        L18:
            if (r2 == 0) goto L44
            java.lang.CharSequence r2 = r6.getText()
            java.lang.String r2 = r2.toString()
            java.lang.Object r4 = r6.getTag(r1)
            if (r4 != 0) goto L2a
            r4 = r3
            goto L2c
        L2a:
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
        L2c:
            if (r4 != 0) goto L30
            r4 = r3
            goto L3e
        L30:
            java.lang.Object r4 = r6.getTag(r1)
            if (r4 != 0) goto L38
            r4 = r3
            goto L3a
        L38:
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
        L3a:
            java.lang.String r4 = r4.toString()
        L3e:
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L4b
        L44:
            java.lang.CharSequence r2 = r6.getText()
            r6.setTag(r1, r2)
        L4b:
            android.content.Context r2 = r5.mContext
            boolean r2 = com.android.systemui.util.DeviceState.isOpenTheme(r2)
            if (r2 != 0) goto L87
            android.content.Context r2 = r5.mContext
            android.content.res.Resources r2 = r2.getResources()
            r4 = 2131034257(0x7f050091, float:1.7679026E38)
            boolean r2 = r2.getBoolean(r4)
            if (r2 == 0) goto L63
            goto L87
        L63:
            if (r7 == 0) goto L6b
            boolean r2 = r5.isNeedToInvert()
            if (r2 != 0) goto L71
        L6b:
            boolean r5 = r5.isNeedToInvertinNightMode(r7)
            if (r5 == 0) goto L79
        L71:
            java.lang.CharSequence r5 = com.android.internal.util.ContrastColorUtil.clearColorSpans(r0)
            setTextAsync(r6, r5)
            return
        L79:
            java.lang.Object r5 = r6.getTag(r1)
            if (r5 != 0) goto L80
            goto L83
        L80:
            r3 = r5
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
        L83:
            setTextAsync(r6, r3)
            return
        L87:
            java.lang.CharSequence r5 = com.android.internal.util.ContrastColorUtil.clearColorSpans(r0)
            setTextAsync(r6, r5)
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: noticolorpicker.NotificationColorPicker.updateSpanned(android.widget.TextView, boolean):void");
    }
}
