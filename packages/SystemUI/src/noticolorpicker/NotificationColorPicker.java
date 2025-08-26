package noticolorpicker;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.text.Spanned;
import android.util.IndentingPrintWriter;
import android.util.Log;
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
import android.widget.RelativeLayout;
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
import com.android.systemui.statusbar.notification.row.wrapper.NotificationMessagingTemplateViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
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
            boolean zIsColorized = statusBarNotification.getNotification().isColorized();
            boolean zIsCustom = isCustom(expandableNotificationRow);
            boolean z = expandableNotificationRow.mIsSummaryWithChildren;
            boolean z2 = expandableNotificationRow.mEntry.isPromotedState() && expandableNotificationRow.mEntry.isOngoingActivity();
            boolean zIsProgressStyle = expandableNotificationRow.mEntry.isProgressStyle();
            if ((!zIsColorized && !zIsCustom && !z2 && !zIsProgressStyle) || z) {
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
        NotificationRowIconView notificationRowIconViewFindViewById = view.findViewById(android.R.id.icon);
        if (!(notificationRowIconViewFindViewById instanceof NotificationRowIconView) || (notificationRowIconView = notificationRowIconViewFindViewById) == null || notificationRowIconView.getTag(R.id.use_app_icon) == null) {
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

    public final void applyShadow(View view) throws Resources.NotFoundException {
        Bitmap bitmapCreateScaledBitmap;
        CachingIconView cachingIconViewFindViewById = view.findViewById(android.R.id.dvorak);
        if (cachingIconViewFindViewById == null || cachingIconViewFindViewById.getDrawable() == null) {
            return;
        }
        cachingIconViewFindViewById.setColorFilter(this.mContext.getColor(R.color.notification_icon_stroke_color));
        Drawable drawableMutate = cachingIconViewFindViewById.getDrawable().mutate();
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_application_icon_stroke_size);
        Drawable drawableMutate2 = drawableMutate.mutate();
        if (drawableMutate2 == null) {
            bitmapCreateScaledBitmap = null;
        } else {
            Bitmap bitmapCreateBitmap = (drawableMutate2.getIntrinsicWidth() <= 0 || drawableMutate2.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(drawableMutate2.getIntrinsicWidth(), drawableMutate2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_circle);
            Drawable drawableMutate3 = drawableMutate2.mutate();
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawableMutate3.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawableMutate3.draw(canvas);
            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, dimensionPixelSize2, dimensionPixelSize2, true);
        }
        if (bitmapCreateScaledBitmap == null) {
            return;
        }
        cachingIconViewFindViewById.setColorFilter((ColorFilter) null);
        Canvas canvas2 = new Canvas(bitmapCreateScaledBitmap);
        drawableMutate.setBounds(dimensionPixelSize, dimensionPixelSize, canvas2.getWidth() - dimensionPixelSize, canvas2.getHeight() - dimensionPixelSize);
        drawableMutate.draw(canvas2);
        cachingIconViewFindViewById.setImageBitmap(bitmapCreateScaledBitmap);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        final IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new Runnable() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationColorPicker notificationColorPicker = this.f$0;
                IndentingPrintWriter indentingPrintWriter = indentingPrintWriterAsIndenting;
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

    public final int getAppPrimaryColor(ExpandableNotificationRow expandableNotificationRow) throws Resources.NotFoundException {
        int iResolveHeaderAppIconColor = resolveHeaderAppIconColor(expandableNotificationRow);
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        if (NotiRune.NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME && settingsHelper.isWallpaperThemeSettingsOn() && (expandableNotificationRow.mEntry.mSbn.getNotification().color == 0 || settingsHelper.isApplyWallpaperThemeToNotif())) {
            iResolveHeaderAppIconColor = settingsHelper.isColorPaletteGrayEnabled() ? this.mContext.getColor(R.color.qs_tile_icon_on_dim_tint_color) : this.mContext.getColor(R.color.open_theme_noti_header_color);
        }
        if (DeviceState.isOpenTheme(this.mContext)) {
            iResolveHeaderAppIconColor = this.mContext.getColor(R.color.open_theme_noti_header_color);
        }
        if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            iResolveHeaderAppIconColor = this.mContext.getResources().getColor(R.color.qp_notification_primary_color);
        }
        return (iResolveHeaderAppIconColor == 0 || iResolveHeaderAppIconColor == 1) ? ContrastColorUtil.resolveDefaultColor(this.mContext, -1, false) : iResolveHeaderAppIconColor;
    }

    public final int getExpandButtonColor(boolean z, boolean z2) throws Resources.NotFoundException {
        if (isDarkMode$1() && this.mReduceTransparencyAndBlurOn) {
            return this.mContext.getResources().getColor(R.color.notification_expand_button_color_reduce_transparency_and_blur);
        }
        int textColor = getTextColor(2, z, z2);
        return Color.argb(153, Color.red(textColor), Color.green(textColor), Color.blue(textColor));
    }

    public final int getGutsTextColor() {
        return DeviceState.isOpenTheme(this.mContext) ? getTextColor(0, false, true) : this.mContext.getResources().getColor(R.color.notification_guts_common_text_color);
    }

    public final int getNotificationBgColor() throws Resources.NotFoundException {
        int notificationDefaultBgColor = getNotificationDefaultBgColor();
        this.mCustomedAlpha = Color.alpha(notificationDefaultBgColor);
        int iArgb = Color.argb(255, Color.red(notificationDefaultBgColor), Color.green(notificationDefaultBgColor), Color.blue(notificationDefaultBgColor));
        if (DeviceState.isOpenTheme(this.mContext)) {
            int color = this.mContext.getResources().getColor(R.color.open_theme_notification_bg_color, null);
            this.mCustomedAlpha = Color.alpha(color);
            iArgb = Color.argb(255, Color.red(color), Color.green(color), Color.blue(color));
        }
        if (!this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            return iArgb;
        }
        int color2 = this.mContext.getResources().getColor(R.color.qp_notification_background_color, null);
        this.mCustomedAlpha = Color.alpha(color2);
        return Color.argb(255, Color.red(color2), Color.green(color2), Color.blue(color2));
    }

    public final int getNotificationDefaultBgColor() {
        return this.mReduceTransparencyAndBlurOn ? this.mContext.getResources().getColor(R.color.notification_material_background_color_reduce_transparency_and_blur, null) : this.mContext.getResources().getColor(R.color.notification_material_background_color, null);
    }

    public final int getTextColor(int i, boolean z, boolean z2) throws Resources.NotFoundException {
        if (!z2) {
            return this.mContext.getResources().getColor(R.color.notification_no_background_header_text_color);
        }
        boolean zIsNeedToInvertinNightMode = isNeedToInvertinNightMode(z);
        if (z && !isNeedToInvert()) {
            z = false;
        }
        if (i == 0) {
            Context context = this.mContext;
            int color = z ? context.getColor(android.R.color.system_theme_app_light) : ContrastColorUtil.resolvePrimaryColor(context, 0, zIsNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(this.mContext)) {
                color = this.mContext.getResources().getColor(R.color.open_theme_notification_title_text_color, null);
            }
            return this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? this.mContext.getResources().getColor(R.color.qp_notification_title_color) : color;
        }
        if (i == 1) {
            Context context2 = this.mContext;
            int color2 = z ? context2.getColor(android.R.color.system_under_surface_light) : ContrastColorUtil.resolveSecondaryColor(context2, 0, zIsNeedToInvertinNightMode);
            if (DeviceState.isOpenTheme(this.mContext)) {
                color2 = this.mContext.getResources().getColor(R.color.open_theme_notification_content_text_color, null);
            }
            return this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) ? this.mContext.getResources().getColor(R.color.qp_notification_content_color) : color2;
        }
        if (i != 2) {
            return ContrastColorUtil.resolveDefaultColor(this.mContext, 0, zIsNeedToInvertinNightMode);
        }
        int color3 = (z || zIsNeedToInvertinNightMode) ? this.mContext.getColor(android.R.color.tab_highlight_material) : this.mContext.getColor(android.R.color.tab_indicator_material);
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

    public final boolean isNeedToInvert() throws Resources.NotFoundException {
        return !this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on) && !DeviceState.isOpenTheme(this.mContext) && ContrastColorUtil.shouldInvertTextColor(((float) ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getLockNoticardOpacity()) * 0.01f, ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isWhiteKeyguardWallpaper()) && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getActiveThemePackage() == null;
    }

    public final boolean isNeedToInvertinNightMode(boolean z) throws Resources.NotFoundException {
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
        Bitmap bitmapCreateScaledBitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmapCreateScaledBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int intrinsicWidth = drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 1;
            int intrinsicHeight = drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 1;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
            bitmapCreateScaledBitmap = bitmapCreateBitmap;
        }
        if (bitmapCreateScaledBitmap != null) {
            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, i, i2, true);
        }
        return new BitmapDrawable(this.mContext.getResources(), bitmapCreateScaledBitmap);
    }

    public final int resolveContrastColor(int i, boolean z, ExpandableNotificationRow expandableNotificationRow) {
        int i2 = (!expandableNotificationRow.mIsMinimized || expandableNotificationRow.isInsignificant()) ? expandableNotificationRow.mEntry.mSbn.getNotification().color : 0;
        int iResolveDefaultColor = i2 == 0 ? ContrastColorUtil.resolveDefaultColor(this.mContext, 0, z) : ContrastColorUtil.resolveContrastColor(this.mContext, i2, i, z);
        if (Color.alpha(iResolveDefaultColor) < 255) {
            iResolveDefaultColor = ContrastColorUtil.compositeColors(iResolveDefaultColor, i);
        }
        if (!z) {
            return iResolveDefaultColor;
        }
        Context context = this.mContext;
        return ContrastColorUtil.resolveContrastColor(context, iResolveDefaultColor, context.getColor(R.color.notification_app_icon_color), !z);
    }

    public final int resolveHeaderAppIconColor(ExpandableNotificationRow expandableNotificationRow) {
        return (!expandableNotificationRow.mEntry.mSbn.getNotification().isColorized() || expandableNotificationRow.mBgTint == 0 || (expandableNotificationRow.mIsMinimized && !expandableNotificationRow.isInsignificant())) ? resolveContrastColor(getNotificationDefaultBgColor(), isNightModeOn(), expandableNotificationRow) : getTextColor(0, expandableNotificationRow.mDimmed, true);
    }

    public final void setNonGrayScaleIconBackground(ImageView imageView, boolean z) {
        int color = this.mContext.getColor(R.color.notification_non_grayscale_border_color);
        int color2 = this.mContext.getColor(R.color.notification_non_grayscale_fill_color);
        if (z) {
            int iAlpha = (Color.alpha(color) * 3) / 10;
            color2 = Color.argb((Color.alpha(color2) * 3) / 10, Color.red(color2), Color.green(color2), Color.blue(color2));
            color = Color.argb(iAlpha, Color.red(color), Color.green(color), Color.blue(color));
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

    /* JADX WARN: Removed duplicated region for block: B:222:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x045c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateAllTextViewColors(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        char c;
        View view;
        ExpandableNotificationRow expandableNotificationRow2;
        View view2;
        NotificationColorPicker notificationColorPicker = this;
        ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
        boolean z2 = z;
        if (expandableNotificationRow3 == null) {
            return;
        }
        boolean zIsGrayScaleIcon = isGrayScaleIcon(expandableNotificationRow);
        if (!isNeedToUpdated(expandableNotificationRow3)) {
            int childCount = expandableNotificationRow3.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = expandableNotificationRow3.getChildAt(i);
                if (childAt instanceof NotificationContentView) {
                    NotificationContentView notificationContentView = (NotificationContentView) childAt;
                    HybridNotificationView singleLineView = notificationContentView.getSingleLineView();
                    if (singleLineView != null) {
                        notificationColorPicker.updateSingleLine(singleLineView, z2);
                    }
                    View view3 = notificationContentView.mContractedChild;
                    int iResolveHeaderAppIconColor = resolveHeaderAppIconColor(expandableNotificationRow);
                    if (view3 != null) {
                        if (isUseAppIcon(view3)) {
                            notificationColorPicker.updateSmallIconForCustom(view3, iResolveHeaderAppIconColor, zIsGrayScaleIcon);
                        } else if ((expandableNotificationRow3.mIsMinimized && !expandableNotificationRow3.isInsignificant()) || !expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() && (view3 instanceof CallLayout))) {
                            notificationColorPicker.updateSmallIconForCustom(view3, iResolveHeaderAppIconColor, zIsGrayScaleIcon);
                        }
                        if (isCustom(expandableNotificationRow3)) {
                            notificationColorPicker.updateExpandButtonForCustom(view3, expandableNotificationRow3);
                        }
                    }
                    View view4 = notificationContentView.mExpandedChild;
                    if (view4 != null) {
                        if (isUseAppIcon(view4) || !expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() && (view4 instanceof CallLayout))) {
                            notificationColorPicker.updateSmallIconForCustom(view4, iResolveHeaderAppIconColor, zIsGrayScaleIcon);
                        }
                        if (isCustom(expandableNotificationRow3)) {
                            notificationColorPicker.updateExpandButtonForCustom(view4, expandableNotificationRow3);
                        }
                        notificationColorPicker.updateExpandedViewMargins(view4);
                    }
                    View view5 = notificationContentView.mHeadsUpChild;
                    if (view5 != null) {
                        if (isUseAppIcon(view5) || !expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() || (expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized() && (view5 instanceof CallLayout))) {
                            notificationColorPicker.updateSmallIconForCustom(view5, iResolveHeaderAppIconColor, zIsGrayScaleIcon);
                        }
                        if (isCustom(expandableNotificationRow3)) {
                            notificationColorPicker.updateExpandButtonForCustom(view5, expandableNotificationRow3);
                        }
                    }
                    if (notificationContentView.getId() == R.id.expandedPublic && (view2 = notificationContentView.mContractedChild) != null) {
                        if (expandableNotificationRow3.mEntry.mSbn.getNotification().isColorized()) {
                            iResolveHeaderAppIconColor = notificationColorPicker.resolveContrastColor(notificationColorPicker.getNotificationDefaultBgColor(), notificationColorPicker.isNightModeOn(), expandableNotificationRow3);
                        }
                        notificationColorPicker.updateSmallIconForCustom(view2, iResolveHeaderAppIconColor, zIsGrayScaleIcon);
                        if (isCustom(expandableNotificationRow3)) {
                            notificationColorPicker.updateExpandButtonForCustom(view2, expandableNotificationRow3);
                        }
                    }
                }
            }
            return;
        }
        int appPrimaryColor = getAppPrimaryColor(expandableNotificationRow);
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow3.mChildrenContainer;
        int i2 = android.R.id.beforeDescendants;
        char c2 = 882;
        if (notificationChildrenContainer != null) {
            NotificationHeaderView notificationHeaderView = notificationChildrenContainer.mGroupHeader;
            if (notificationHeaderView != null) {
                notificationColorPicker.updateHeader(notificationHeaderView, expandableNotificationRow3, true);
            }
            NotificationHeaderView notificationHeaderView2 = notificationChildrenContainer.mNotificationHeaderExpanded;
            if (notificationHeaderView2 != null) {
                notificationColorPicker.updateHeader(notificationHeaderView2, expandableNotificationRow3, false);
            }
            NotificationHeaderView notificationHeaderView3 = notificationChildrenContainer.mMinimizedGroupHeader;
            if (notificationHeaderView3 != null) {
                notificationColorPicker.updateHeader(notificationHeaderView3, expandableNotificationRow3, true);
                notificationColorPicker.setPrimaryColor((TextView) notificationChildrenContainer.mMinimizedGroupHeader.findViewById(android.R.id.inter_word), z2);
                notificationColorPicker.setPrimaryColor((TextView) notificationChildrenContainer.mMinimizedGroupHeader.findViewById(android.R.id.beforeDescendants), z2);
            }
        }
        if (!expandableNotificationRow3.mEntry.isOngoingActivity() || expandableNotificationRow3.mEntry.isPromotedState() || expandableNotificationRow3.mEntry.isProgressStyle()) {
            int childCount2 = expandableNotificationRow3.getChildCount();
            int i3 = 0;
            while (i3 < childCount2) {
                View childAt2 = expandableNotificationRow3.getChildAt(i3);
                if (childAt2 instanceof NotificationContentView) {
                    NotificationContentView notificationContentView2 = (NotificationContentView) childAt2;
                    View view6 = notificationContentView2.mContractedChild;
                    HybridNotificationView singleLineView2 = notificationContentView2.getSingleLineView();
                    View view7 = notificationContentView2.mExpandedChild;
                    View view8 = notificationContentView2.mHeadsUpChild;
                    NotificationViewWrapper visibleWrapper = notificationContentView2.getVisibleWrapper(0);
                    NotificationViewWrapper visibleWrapper2 = notificationContentView2.getVisibleWrapper(1);
                    NotificationViewWrapper visibleWrapper3 = notificationContentView2.getVisibleWrapper(2);
                    if (visibleWrapper instanceof NotificationBigTextTemplateViewWrapper) {
                        notificationColorPicker = this;
                        expandableNotificationRow3 = expandableNotificationRow;
                        z2 = z;
                    } else {
                        if (!(visibleWrapper instanceof NotificationMessagingTemplateViewWrapper)) {
                            notificationColorPicker.updateBase(view6, appPrimaryColor, zIsGrayScaleIcon, z2, expandableNotificationRow3);
                            notificationColorPicker = this;
                            expandableNotificationRow2 = expandableNotificationRow;
                            z2 = z;
                            view = view6;
                        }
                        notificationColorPicker.updateBig(view7, appPrimaryColor, zIsGrayScaleIcon, visibleWrapper2, z2, expandableNotificationRow2);
                        notificationColorPicker.updateBig(view8, appPrimaryColor, zIsGrayScaleIcon, visibleWrapper3, z2, expandableNotificationRow2);
                        notificationColorPicker.updateSingleLine(singleLineView2, z2);
                        if (view == null) {
                            TextView textView = (TextView) view.findViewById(android.R.id.beforeDescendants);
                            if (textView != null) {
                                textView.setTextColor(notificationColorPicker.getTextColor(0, z2, true));
                            }
                            c = 882;
                            TextView textView2 = (TextView) view.findViewById(android.R.id.inter_word);
                            if (textView2 != null) {
                                notificationColorPicker.updateSpanned(textView2, z2);
                                textView2.setTextColor(notificationColorPicker.getTextColor(0, z2, true));
                            }
                        } else {
                            c = 882;
                        }
                        if (view7 == null) {
                            notificationColorPicker.updateExpandedViewMargins(view7);
                        }
                    }
                    notificationColorPicker.updateBig(view6, appPrimaryColor, zIsGrayScaleIcon, visibleWrapper, z2, expandableNotificationRow3);
                    view = view6;
                    notificationColorPicker = this;
                    expandableNotificationRow2 = expandableNotificationRow;
                    z2 = z;
                    notificationColorPicker.updateBig(view7, appPrimaryColor, zIsGrayScaleIcon, visibleWrapper2, z2, expandableNotificationRow2);
                    notificationColorPicker.updateBig(view8, appPrimaryColor, zIsGrayScaleIcon, visibleWrapper3, z2, expandableNotificationRow2);
                    notificationColorPicker.updateSingleLine(singleLineView2, z2);
                    if (view == null) {
                    }
                    if (view7 == null) {
                    }
                } else {
                    c = c2;
                }
                i3++;
                expandableNotificationRow3 = expandableNotificationRow;
                c2 = c;
            }
            return;
        }
        int childCount3 = expandableNotificationRow3.getChildCount();
        int i4 = 0;
        while (i4 < childCount3) {
            View childAt3 = expandableNotificationRow3.getChildAt(i4);
            if ((childAt3 instanceof NotificationContentView) && childAt3.getId() == R.id.expanded) {
                NotificationContentView notificationContentView3 = (NotificationContentView) childAt3;
                View view9 = notificationContentView3.mContractedChild;
                if (view9 != null) {
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    String key = expandableNotificationRow3.getKey();
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(key);
                    if (ongoingActivityDataByKey != null) {
                        notificationColorPicker.updateHeader(view9, expandableNotificationRow3, true);
                        boolean z3 = expandableNotificationRow3.mDimmed;
                        TextView textView3 = (TextView) view9.findViewById(i2);
                        if (textView3 != null) {
                            textView3.setTextColor(notificationColorPicker.getTextColor(0, z3, true));
                        }
                        TextView textView4 = (TextView) view9.findViewById(android.R.id.inter_word);
                        if (textView4 != null) {
                            notificationColorPicker.updateSpanned(textView4, z3);
                            textView4.setTextColor(notificationColorPicker.getTextColor(0, z3, true));
                        }
                        NotificationRowIconView notificationRowIconView = (NotificationRowIconView) view9.findViewWithTag("ongoingCollapsedPrimaryIcon");
                        boolean z4 = expandableNotificationRow3.mEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
                        if (notificationRowIconView != null && (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled() || z4)) {
                            notificationRowIconView.setColorFilter(notificationColorPicker.mContext.getColor(R.color.notification_app_icon_color));
                            notificationColorPicker.updateSmallIcon(view9, expandableNotificationRow3, notificationRowIconView);
                        }
                        TextView textView5 = (TextView) view9.findViewWithTag("collapsedPrimary");
                        if (textView5 != null) {
                            textView5.setTextColor(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                        }
                        TextView textView6 = (TextView) view9.findViewWithTag("collapsedSecondary");
                        if (textView6 != null) {
                            textView6.setTextColor(notificationColorPicker.getTextColor(1, expandableNotificationRow3.mDimmed, true));
                        }
                        if (ongoingActivityDataByKey.mChronometerView != null) {
                            int i5 = ongoingActivityDataByKey.mChronometerPosition == 1 ? 1 : 0;
                            View viewFindViewWithTag = view9.findViewWithTag(ongoingActivityDataByKey.mChronometerTag);
                            if (viewFindViewWithTag != null && (viewFindViewWithTag instanceof TextView)) {
                                ((TextView) viewFindViewWithTag).setTextColor(notificationColorPicker.getTextColor(i5 ^ 1, expandableNotificationRow3.mDimmed, true));
                            }
                        }
                        LinearLayout linearLayout = (LinearLayout) view9.findViewById(R.id.ongoing_activity_expand_icon_buttons);
                        if (linearLayout != null && linearLayout.getChildCount() > 0) {
                            for (int i6 = 0; i6 < linearLayout.getChildCount(); i6++) {
                                ((ImageView) linearLayout.getChildAt(i6)).setColorFilter(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                            }
                        }
                    }
                }
                View view10 = notificationContentView3.mExpandedChild;
                if (view10 != null) {
                    OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
                    String key2 = expandableNotificationRow3.getKey();
                    ongoingActivityDataHelper2.getClass();
                    OngoingActivityData ongoingActivityDataByKey2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(key2);
                    if (ongoingActivityDataByKey2 != null) {
                        notificationColorPicker.updateHeader(view10, expandableNotificationRow3, true);
                        NotificationRowIconView notificationRowIconView2 = (NotificationRowIconView) view10.findViewWithTag("ongoingExpandPrimaryIcon");
                        boolean z5 = expandableNotificationRow3.mEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
                        if (notificationRowIconView2 != null && (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled() || z5)) {
                            notificationRowIconView2.setColorFilter(notificationColorPicker.mContext.getColor(R.color.notification_app_icon_color));
                            notificationColorPicker.updateSmallIcon(view10, expandableNotificationRow3, notificationRowIconView2);
                        }
                        TextView textView7 = (TextView) view10.findViewWithTag("expandPrimary");
                        if (textView7 != null) {
                            textView7.setTextColor(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                        }
                        TextView textView8 = (TextView) view10.findViewWithTag("expandSecondary");
                        if (textView8 != null) {
                            textView8.setTextColor(notificationColorPicker.getTextColor(1, expandableNotificationRow3.mDimmed, true));
                        }
                        TextView textView9 = (TextView) view10.findViewWithTag("description");
                        if (textView9 != null) {
                            textView9.setTextColor(notificationColorPicker.getTextColor(1, expandableNotificationRow3.mDimmed, true));
                        }
                        if (ongoingActivityDataByKey2.mChronometerView != null) {
                            int i7 = ongoingActivityDataByKey2.mChronometerPosition == 1 ? 1 : 0;
                            View viewFindViewWithTag2 = view10.findViewWithTag(ongoingActivityDataByKey2.mChronometerTag);
                            if (viewFindViewWithTag2 != null && (viewFindViewWithTag2 instanceof TextView)) {
                                ((TextView) viewFindViewWithTag2).setTextColor(notificationColorPicker.getTextColor(i7 ^ 1, expandableNotificationRow3.mDimmed, true));
                            }
                        }
                        LinearLayout linearLayout2 = (LinearLayout) view10.findViewById(R.id.ongoing_activity_expand_icon_buttons);
                        if (linearLayout2 != null && linearLayout2.getChildCount() > 0) {
                            for (int i8 = 0; i8 < linearLayout2.getChildCount(); i8++) {
                                if (linearLayout2.getChildAt(i8) instanceof ImageView) {
                                    ((ImageView) linearLayout2.getChildAt(i8)).setColorFilter(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                                }
                            }
                        }
                        LinearLayout linearLayout3 = (LinearLayout) view10.findViewById(R.id.ongoing_activity_expand_icon_buttons_bottom);
                        if (linearLayout3 != null && linearLayout3.getChildCount() > 0) {
                            for (int i9 = 0; i9 < linearLayout3.getChildCount(); i9++) {
                                if (linearLayout3.getChildAt(i9) instanceof ImageView) {
                                    ((ImageView) linearLayout3.getChildAt(i9)).setColorFilter(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                                }
                            }
                        }
                        LinearLayout linearLayout4 = (LinearLayout) view10.findViewWithTag("textButton");
                        if (linearLayout4 != null && linearLayout4.getChildCount() > 0) {
                            for (int i10 = 0; i10 < linearLayout4.getChildCount(); i10++) {
                                if (linearLayout4.getChildAt(i10) instanceof TextView) {
                                    ((TextView) linearLayout4.getChildAt(i10)).setTextColor(notificationColorPicker.getTextColor(0, expandableNotificationRow3.mDimmed, true));
                                }
                            }
                        }
                    }
                }
            }
            i4++;
            i2 = android.R.id.beforeDescendants;
        }
    }

    public final void updateBase(View view, int i, boolean z, boolean z2, ExpandableNotificationRow expandableNotificationRow) throws Resources.NotFoundException {
        final NotificationColorPicker notificationColorPicker;
        final int i2;
        final boolean z3;
        if (view == null) {
            return;
        }
        View viewFindViewById = view.findViewById(R.id.smart_reply_view);
        if (viewFindViewById instanceof SmartReplyView) {
            ((SmartReplyView) viewFindViewById).updateButtonColorOnUiModeChanged();
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
                public final void accept(Object obj) throws Resources.NotFoundException {
                    NotificationColorPicker notificationColorPicker2 = this.f$0;
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
                            int dimensionPixelSize = notificationColorPicker2.mContext.getResources().getDimensionPixelSize(android.R.dimen.toast_elevation);
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
            View viewFindViewById2 = messagingLayout.findViewById(android.R.id.remote_input);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setPadding(notificationColorPicker.mContext.getResources().getDimensionPixelSize(R.dimen.notification_big_messaging_header_padding_start), viewFindViewById2.getPaddingTop(), viewFindViewById2.getPaddingRight(), viewFindViewById2.getPaddingBottom());
            }
        }
        int iResolveContrastColor = notificationColorPicker.resolveContrastColor(notificationColorPicker.mContext.getColor(R.color.notification_material_background_color), notificationColorPicker.isNightModeOn(), expandableNotificationRow);
        if (view instanceof CallLayout) {
            CallLayout callLayout = (CallLayout) view;
            callLayout.setLayoutColor(iResolveContrastColor);
            if (z3) {
                callLayout.setNotificationBackgroundColor(i2);
            }
        }
        notificationColorPicker.updateMediaActions(view, z2);
        View viewFindViewById3 = view.findViewById(android.R.id.progress);
        if (viewFindViewById3 instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) viewFindViewById3;
            ColorStateList colorStateListValueOf = ColorStateList.valueOf(notificationColorPicker.mContext.getColor(R.color.notification_progress_tint));
            ColorStateList colorStateListValueOf2 = ColorStateList.valueOf(notificationColorPicker.mContext.getColor(R.color.notification_progress_background_tint));
            if (DeviceState.isOpenTheme(notificationColorPicker.mContext) || notificationColorPicker.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                colorStateListValueOf = ColorStateList.valueOf(notificationColorPicker.getTextColor(0, z2, true));
                colorStateListValueOf2 = ColorStateList.valueOf(notificationColorPicker.getTextColor(2, z2, true));
            }
            progressBar.setProgressBackgroundTintList(colorStateListValueOf2);
            progressBar.setProgressTintList(colorStateListValueOf);
            progressBar.setIndeterminateTintList(colorStateListValueOf);
        }
        notificationColorPicker.updateHeader(view, expandableNotificationRow, true);
        TextView textView2 = (TextView) view.findViewById(android.R.id.title);
        if (textView2 != null) {
            notificationColorPicker.updateSpanned(textView2, z2);
            textView2.setTextColor(notificationColorPicker.getTextColor(0, z2, true));
        }
        TextView textView3 = (TextView) view.findViewById(16909932);
        if (textView3 != null) {
            notificationColorPicker.updateSpanned(textView3, z2);
            textView3.setTextColor(notificationColorPicker.getTextColor(1, z2, true));
            textView3.setLineSpacing(notificationColorPicker.mContext.getResources().getDimensionPixelSize(android.R.dimen.toast_elevation), textView3.getLineSpacingMultiplier());
        }
    }

    public final void updateBig(View view, int i, boolean z, NotificationViewWrapper notificationViewWrapper, boolean z2, ExpandableNotificationRow expandableNotificationRow) throws Resources.NotFoundException {
        TextView textView;
        boolean zIsEmphasizedMode;
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
                        zIsEmphasizedMode = notificationActionListLayout.isEmphasizedMode();
                    } catch (Exception e) {
                        Log.e("NotificationColorPicker", "Failed to check emphasized mode.");
                        e.printStackTrace();
                        zIsEmphasizedMode = false;
                    }
                    button.setTextColor(zIsEmphasizedMode ? -1 : getTextColor(0, z2, true));
                    if (button.getBackground() instanceof RippleDrawable) {
                        ((RippleDrawable) button.getBackground()).setColor(ColorStateList.valueOf(this.mContext.getColor(android.R.color.system_on_theme_app_ring_dark)));
                    }
                }
            }
            ImageView imageView = (ImageView) frameLayout.findViewById(android.R.id.clamp);
            imageView.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView.setBackground(this.mContext.getDrawable(android.R.drawable.pointer_wait_33));
            ImageView imageView2 = (ImageView) frameLayout.findViewById(16909855);
            imageView2.setImageTintList(ColorStateList.valueOf(getTextColor(1, z2, true)));
            imageView2.setBackground(this.mContext.getDrawable(android.R.drawable.pointer_wait_33));
        }
        updateMediaActions(view, z2);
        View viewFindViewById = view.findViewById(R.id.smart_reply_view);
        if (viewFindViewById instanceof SmartReplyView) {
            ((SmartReplyView) viewFindViewById).updateButtonColorOnUiModeChanged();
        }
        if ((notificationViewWrapper instanceof NotificationBigTextTemplateViewWrapper) && (textView = (TextView) view.findViewById(android.R.id.choice)) != null) {
            updateSpanned(textView, z2);
            textView.setTextColor(getTextColor(1, z2, true));
            textView.setLineSpacing(this.mContext.getResources().getDimensionPixelSize(android.R.dimen.toast_elevation), textView.getLineSpacingMultiplier());
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

    public final void updateExpandButtonForCustom(View view, ExpandableNotificationRow expandableNotificationRow) {
        NotificationExpandButton notificationExpandButtonFindViewById;
        if (expandableNotificationRow == null || view == null || (notificationExpandButtonFindViewById = view.findViewById(android.R.id.flagRetrieveInteractiveWindows)) == null) {
            return;
        }
        notificationExpandButtonFindViewById.setDefaultTextColor(getExpandButtonColor(expandableNotificationRow.mDimmed, true));
    }

    public final void updateExpandedViewMargins(View view) {
        LinearLayout linearLayout;
        try {
            if (view instanceof ConversationLayout) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.findViewById(android.R.id.remoteMessaging).getLayoutParams();
                layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_conversation_expanded_margin_top);
                view.findViewById(android.R.id.remoteMessaging).setLayoutParams(layoutParams);
                return;
            }
            try {
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) view.findViewById(android.R.id.resolver_tab_divider).getLayoutParams();
                layoutParams2.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_expanded_margin_top);
                view.findViewById(android.R.id.resolver_tab_divider).setLayoutParams(layoutParams2);
            } catch (Exception unused) {
            }
            if (!(view instanceof CallLayout)) {
                try {
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view.findViewById(android.R.id.remote_input_tag).getLayoutParams();
                    layoutParams3.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_expanded_margin_top_title);
                    view.findViewById(android.R.id.remote_input_tag).setLayoutParams(layoutParams3);
                } catch (Exception unused2) {
                }
                try {
                    FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view.findViewById(android.R.id.remote_input_tag).getLayoutParams();
                    layoutParams4.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_expanded_margin_top_title);
                    view.findViewById(android.R.id.remote_input_tag).setLayoutParams(layoutParams4);
                } catch (Exception unused3) {
                }
            }
            FrameLayout frameLayout = (FrameLayout) view.findViewById(android.R.id.animator);
            if (frameLayout == null || (linearLayout = (LinearLayout) frameLayout.findViewById(android.R.id.animation)) == null || linearLayout.getChildCount() <= 0) {
                FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) view.findViewById(android.R.id.remoteMessaging).getLayoutParams();
                layoutParams5.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_expanded_margin_bottom);
                view.findViewById(android.R.id.remoteMessaging).setLayoutParams(layoutParams5);
            }
        } catch (Exception unused4) {
        }
    }

    public final void updateHeader(View view, final ExpandableNotificationRow expandableNotificationRow, boolean z) {
        boolean zIsUseAppIcon;
        Drawable drawable;
        int i;
        Drawable drawable2;
        if (view == null || expandableNotificationRow == null) {
            return;
        }
        int appPrimaryColor = getAppPrimaryColor(expandableNotificationRow);
        boolean zIsGrayScaleIcon = isGrayScaleIcon(expandableNotificationRow);
        boolean z2 = expandableNotificationRow.mDimmed;
        ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
        if (imageView != null) {
            zIsUseAppIcon = isUseAppIcon(imageView);
            if (zIsUseAppIcon) {
                if (imageView.getBackground() != null) {
                    imageView.setColorFilter((ColorFilter) null);
                    imageView.setBackground(null);
                }
            } else if (zIsGrayScaleIcon) {
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
            zIsUseAppIcon = false;
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
        } else if (zIsUseAppIcon) {
            drawable = null;
            imageView4.setColorFilter((ColorFilter) null);
            Drawable drawableNewDrawable = imageView.getDrawable().getConstantState().newDrawable();
            drawableNewDrawable.mutate().setAlpha(76);
            imageView4.setImageDrawable(drawableNewDrawable);
        } else {
            drawable = null;
            if (zIsGrayScaleIcon) {
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                    imageView4.setImageDrawable(this.mContext.getDrawable(R.drawable.squircle));
                }
                imageView4.setColorFilter(Color.argb((Color.alpha(appPrimaryColor) * 3) / 10, Color.red(appPrimaryColor), Color.green(appPrimaryColor), Color.blue(appPrimaryColor)), PorterDuff.Mode.SRC_IN);
            } else {
                setNonGrayScaleIconBackground(imageView4, true);
            }
        }
        ImageView imageView5 = (ImageView) view.findViewById(android.R.id.tag_top_override);
        if (imageView5 != null) {
            float f = -10;
            imageView5.setColorFilter(new ColorMatrixColorFilter(new float[]{1.0f, 0.0f, 0.0f, 0.0f, f, 0.0f, 1.0f, 0.0f, 0.0f, f, 0.0f, 0.0f, 1.0f, 0.0f, f, 0.0f, 0.0f, 0.0f, 1.0f, f}));
        }
        NotificationExpandButton notificationExpandButtonFindViewById = view.findViewById(android.R.id.flagRetrieveInteractiveWindows);
        if (notificationExpandButtonFindViewById != null && z) {
            notificationExpandButtonFindViewById.setDefaultTextColor(getExpandButtonColor(z2, z));
        }
        TextView textView = (TextView) view.findViewById(android.R.id.input_minute);
        if (textView != null) {
            textView.setTextColor(getExpandButtonColor(z2, z));
        }
        TextView textView2 = (TextView) view.findViewById(android.R.id.beforeDescendants);
        if (textView2 != null) {
            updateSpanned(textView2, z2);
            i = 2;
            textView2.setTextColor(getTextColor(2, z2, z));
        } else {
            i = 2;
        }
        TextView textView3 = (TextView) view.findViewById(android.R.id.internalOnly);
        if (textView3 != null) {
            textView3.setTextColor(getTextColor(i, z2, z));
        }
        TextView textView4 = (TextView) view.findViewById(android.R.id.internalEmpty);
        if (textView4 != null) {
            updateSpanned(textView4, z2);
            textView4.setTextColor(getTextColor(i, z2, z));
        }
        TextView textView5 = (TextView) view.findViewById(android.R.id.internal);
        if (textView5 != null) {
            textView5.setTextColor(getTextColor(i, z2, z));
        }
        TextView textView6 = (TextView) view.findViewById(android.R.id.inter_word);
        if (textView6 != null) {
            updateSpanned(textView6, z2);
            textView6.setTextColor(getTextColor(i, z2, z));
        }
        TextView textView7 = (TextView) view.findViewById(16909972);
        if (textView7 != null) {
            textView7.setTextColor(getTextColor(i, z2, z));
        }
        DateTimeView dateTimeViewFindViewById = view.findViewById(16909968);
        if (dateTimeViewFindViewById != null) {
            int textColor = getTextColor(i, z2, z);
            dateTimeViewFindViewById.setTextColor(Color.argb((int) (Color.alpha(textColor) * 0.7f), Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }
        View viewFindViewById = view.findViewById(android.R.id.conversation_face_pile_bottom);
        if (viewFindViewById instanceof Chronometer) {
            Chronometer chronometer = (Chronometer) viewFindViewById;
            if (dateTimeViewFindViewById != null) {
                chronometer.setTextColor(getTextColor(1, z2, z));
            }
        }
        TextView textView8 = (TextView) view.findViewById(android.R.id.end);
        if (textView8 != null) {
            updateSpanned(textView8, z2);
            textView8.setTextColor(getTextColor(0, z2, z));
        }
        TextView textView9 = (TextView) view.findViewById(android.R.id.exclude);
        if (textView9 != null) {
            textView9.setBackground(this.mContext.getDrawable(android.R.drawable.edit_query_background_selected));
        }
        TextView textView10 = (TextView) view.findViewById(android.R.id.balanced);
        if (textView10 != null) {
            textView10.setTextColor(getTextColor(1, z2, z));
        }
        TextView textView11 = (TextView) view.findViewById(16910061);
        if (textView11 != null) {
            textView11.setTextColor(getTextColor(1, z2, z));
        }
        TextView textView12 = (TextView) view.findViewById(16910063);
        if (textView12 != null) {
            updateSpanned(textView12, z2);
            textView12.setTextColor(getTextColor(1, z2, z));
        }
        ImageView imageView6 = (ImageView) view.findViewById(16910062);
        if (imageView6 != null) {
            imageView6.setColorFilter(getTextColor(1, z2, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView7 = (ImageView) view.findViewById(android.R.id.smallIcon);
        if (imageView7 != null) {
            if (expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier() == 0) {
                drawable2 = drawable;
            } else {
                drawable2 = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getDrawable((((UserManager) expandableNotificationRow.mEntry.mSbn.getPackageContext(this.mContext).getSystemService(UserManager.class)).isManagedProfile() || ((UserManager) expandableNotificationRow.mEntry.mSbn.getPackageContext(this.mContext).getSystemService(UserManager.class)).isPrivateProfile()) ? "WORK_PROFILE_ICON" : PeripheralBarcodeConstants.Symbology.UNDEFINED, "SOLID_COLORED", "NOTIFICATION", new Supplier() { // from class: noticolorpicker.NotificationColorPicker$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(expandableNotificationRow.mEntry.mSbn.getUser().getIdentifier()), 0);
                    }
                });
            }
            imageView7.setImageDrawable(drawable2);
        }
        ImageView imageView8 = (ImageView) view.findViewById(android.R.id.serial_number);
        if (imageView8 != null) {
            imageView8.setColorFilter(getTextColor(1, z2, z), PorterDuff.Mode.SRC_IN);
        }
        ImageView imageView9 = (ImageView) view.findViewById(android.R.id.autofill_dataset_icon);
        if (imageView9 != null) {
            imageView9.setColorFilter(getTextColor(1, z2, z), PorterDuff.Mode.SRC_IN);
        }
        ImageButton imageButton = (ImageButton) view.findViewById(android.R.id.game);
        if (imageButton != null) {
            imageButton.setColorFilter(getTextColor(1, z2, z), PorterDuff.Mode.SRC_IN);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006d A[Catch: NameNotFoundException -> 0x0119, TryCatch #0 {NameNotFoundException -> 0x0119, blocks: (B:8:0x001f, B:10:0x0052, B:12:0x0058, B:14:0x0060, B:28:0x0086, B:30:0x0099, B:32:0x00a7, B:34:0x00ad, B:37:0x00cf, B:39:0x00ee, B:41:0x00f4, B:43:0x00fa, B:44:0x00fe, B:46:0x0111, B:35:0x00c4, B:36:0x00cb, B:48:0x0115, B:19:0x006d, B:21:0x0075, B:23:0x007d), top: B:54:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIconTag(View view, ExpandableNotificationRow expandableNotificationRow) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        NotificationRowIconView notificationRowIconView;
        boolean z;
        if (view == null || expandableNotificationRow == null || (notificationRowIconView = (NotificationRowIconView) view.findViewById(android.R.id.icon)) == null) {
            return;
        }
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
            updateSmallIcon(view, expandableNotificationRow, notificationRowIconView);
            return;
        }
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            String packageName = expandableNotificationRow.mEntry.mSbn.getPackageName();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
            List<LauncherActivityInfo> activityList = ((LauncherApps) expandableNotificationRow.getContext().getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
            if ((applicationInfo.flags & 129) == 0 || !activityList.isEmpty()) {
                z = (packageName.equals("android") || packageName.equals("com.android.systemui") || applicationInfo.icon == 0) ? false : true;
            } else {
                if (!((packageName.startsWith("com.samsung") || packageName.startsWith("com.sec")) ? false : true)) {
                }
            }
            if (z) {
                z = !expandableNotificationRow.mEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
            }
            if (!z) {
                updateSmallIcon(view, expandableNotificationRow, notificationRowIconView);
                return;
            }
            Drawable drawableSemGetBadgedIconForIconTray = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isColorThemeAppIconSettingsOn() ? !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(this.mContext.getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 48) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
            notificationRowIconView.setColorFilter((ColorFilter) null);
            notificationRowIconView.setBackground((Drawable) null);
            notificationRowIconView.setPadding(0, 0, 0, 0);
            int dimensionPixelSize = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_squircle);
            int maxDrawableWidth = notificationRowIconView.getMaxDrawableWidth() > 0 ? notificationRowIconView.getMaxDrawableWidth() : dimensionPixelSize;
            if (notificationRowIconView.getMaxDrawableHeight() > 0) {
                dimensionPixelSize = notificationRowIconView.getMaxDrawableHeight();
            }
            notificationRowIconView.setImageDrawable(resizeDrawable(drawableSemGetBadgedIconForIconTray, maxDrawableWidth, dimensionPixelSize));
            notificationRowIconView.setTag(R.id.use_app_icon, Boolean.TRUE);
            if (view instanceof ConversationLayout) {
                applyShadow(view);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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

    public final void updateSmallIcon(View view, ExpandableNotificationRow expandableNotificationRow, NotificationRowIconView notificationRowIconView) throws Resources.NotFoundException {
        int i;
        if ((view instanceof ConversationLayout) || (view instanceof CallLayout)) {
            notificationRowIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
        } else {
            int dimensionPixelSize = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_icon_circle_padding);
            notificationRowIconView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            Icon smallIcon = expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon();
            int dimensionPixelSize2 = expandableNotificationRow.getContext().getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_squircle);
            Drawable drawableLoadDrawable = smallIcon.loadDrawable(notificationRowIconView.getContext());
            if (drawableLoadDrawable == null || (drawableLoadDrawable instanceof Animatable) || (drawableLoadDrawable instanceof Animatable2) || drawableLoadDrawable.getIntrinsicHeight() <= (i = dimensionPixelSize2 * 2) || drawableLoadDrawable.getIntrinsicWidth() <= i) {
                notificationRowIconView.setImageIcon(expandableNotificationRow.mEntry.mSbn.getNotification().getSmallIcon());
            } else {
                int maxDrawableWidth = notificationRowIconView.getMaxDrawableWidth() > 0 ? notificationRowIconView.getMaxDrawableWidth() : dimensionPixelSize2;
                if (notificationRowIconView.getMaxDrawableHeight() > 0) {
                    dimensionPixelSize2 = notificationRowIconView.getMaxDrawableHeight();
                }
                notificationRowIconView.setImageDrawable(resizeDrawable(drawableLoadDrawable, maxDrawableWidth, dimensionPixelSize2));
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSpanned(TextView textView, boolean z) {
        String string;
        CharSequence text = textView.getText();
        if (text == null || !(text instanceof Spanned)) {
            return;
        }
        Object tag = textView.getTag(R.id.spannded_notification_text);
        if ((tag == null ? null : (CharSequence) tag) != null) {
            String string2 = textView.getText().toString();
            Object tag2 = textView.getTag(R.id.spannded_notification_text);
            if ((tag2 == null ? null : (CharSequence) tag2) == null) {
                string = null;
            } else {
                Object tag3 = textView.getTag(R.id.spannded_notification_text);
                string = (tag3 == null ? null : (CharSequence) tag3).toString();
            }
            if (!string2.equals(string)) {
                textView.setTag(R.id.spannded_notification_text, textView.getText());
            }
        }
        if (DeviceState.isOpenTheme(this.mContext) || this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
            setTextAsync(textView, ContrastColorUtil.clearColorSpans(text));
            return;
        }
        if ((z && isNeedToInvert()) || isNeedToInvertinNightMode(z)) {
            setTextAsync(textView, ContrastColorUtil.clearColorSpans(text));
        } else {
            Object tag4 = textView.getTag(R.id.spannded_notification_text);
            setTextAsync(textView, tag4 != null ? (CharSequence) tag4 : null);
        }
    }
}
