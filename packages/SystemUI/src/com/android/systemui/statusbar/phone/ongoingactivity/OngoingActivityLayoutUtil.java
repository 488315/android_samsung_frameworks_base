package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DateTimeView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.NotificationRowIconView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public final class OngoingActivityLayoutUtil {
    public static final OngoingActivityLayoutUtil INSTANCE = new OngoingActivityLayoutUtil();

    private OngoingActivityLayoutUtil() {
    }

    public static Chronometer findChronometer(View view, String str) {
        if (!(view.findViewWithTag(str) instanceof LinearLayout)) {
            return null;
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewWithTag(str);
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = linearLayout.getChildAt(i);
            if (childAt instanceof Chronometer) {
                return (Chronometer) childAt;
            }
        }
        return null;
    }

    public static int getENRCardWidth(Context context) {
        return ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(context) - (((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getNotificationSidePadding(context, true) * 2);
    }

    public static int getOngoingCardWidth(Context context) {
        int dimensionPixelSize;
        if (context.getResources().getConfiguration().smallestScreenWidthDp > 600 || DeviceState.isTablet()) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_width_tablet);
        } else {
            dimensionPixelSize = (int) (DeviceState.getDisplayWidth(context) * ((!BasicRune.BASIC_FOLDABLE_TYPE_FOLD || DeviceState.isSubDisplay(context)) ? context.getResources().getFloat(R.dimen.ongoing_activity_card_width) : context.getResources().getFloat(R.dimen.ongoing_activity_card_width_fold)));
        }
        return dimensionPixelSize % 2 != 0 ? dimensionPixelSize + 1 : dimensionPixelSize;
    }

    public static boolean isActionStyle(OngoingActivityData ongoingActivityData) {
        return ongoingActivityData.mActions != null && ongoingActivityData.mActionType == 0;
    }

    public static boolean isManagedProfile(StatusBarNotification statusBarNotification, Context context, int i) {
        UserManager userManager = (UserManager) statusBarNotification.getPackageContext(context).getSystemService(UserManager.class);
        Boolean boolValueOf = userManager != null ? Boolean.valueOf(userManager.isManagedProfile(i)) : null;
        boolValueOf.getClass();
        if (boolValueOf.booleanValue()) {
            return true;
        }
        UserManager userManager2 = (UserManager) statusBarNotification.getPackageContext(context).getSystemService(UserManager.class);
        Boolean boolValueOf2 = userManager2 != null ? Boolean.valueOf(userManager2.isPrivateProfile()) : null;
        boolValueOf2.getClass();
        return boolValueOf2.booleanValue();
    }

    public static boolean isPrimaryChronometer(OngoingActivityData ongoingActivityData) {
        return ongoingActivityData.mChronometerView != null && ongoingActivityData.mChronometerPosition == 1;
    }

    public static void refactorRonLayout(RemoteViews remoteViews, OngoingActivityData ongoingActivityData, Context context) {
        setOngoingNotificationIcon(ongoingActivityData, remoteViews, android.R.id.icon, context);
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        boolean z = notificationEntry.mSbn.getNotification().getLargeIcon() != null;
        Resources resources = context.getResources();
        float f = resources.getDisplayMetrics().density;
        if (z) {
            remoteViews.setFloat(android.R.id.remote_input, "setTopLineExtraMarginEndDp", (resources.getDimension(17105811) / f) + (resources.getDimension(17105808) / f) + (resources.getDimension(android.R.dimen.toast_width) / f));
        } else {
            remoteViews.setFloat(android.R.id.remote_input, "setTopLineExtraMarginEndDp", resources.getDimension(android.R.dimen.toast_width) / f);
        }
        boolean z2 = notificationEntry.mSbn.getNotification().getLargeIcon() != null;
        Resources resources2 = context.getResources();
        float f2 = resources2.getDisplayMetrics().density;
        if (z2) {
            remoteViews.setViewLayoutMargin(android.R.id.title, 5, (resources2.getDimension(17105811) / f2) + (resources2.getDimension(17105808) / f2) + (resources2.getDimension(android.R.dimen.toast_width) / f2), 1);
        } else {
            remoteViews.setViewLayoutMargin(android.R.id.title, 5, resources2.getDimension(android.R.dimen.toast_width) / f2, 1);
        }
        if (notificationEntry.mSbn.getNotification().getLargeIcon() != null) {
            remoteViews.setViewLayoutMargin(android.R.id.tag_top_override, 5, r12.getDimensionPixelSize(android.R.dimen.toast_width) / context.getResources().getDisplayMetrics().density, 1);
        }
    }

    public static void setExpandButtonColor(RemoteViews remoteViews) {
        if (remoteViews != null) {
            int textColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(2, false, true);
            remoteViews.setInt(android.R.id.flagRetrieveInteractiveWindows, "setDefaultTextColor", Color.argb(153, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setOngoingNotificationIcon(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, int i, Context context) {
        boolean z;
        RemoteViews remoteViews2;
        int i2;
        boolean zIsShowNotificationAppIconEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled();
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        if (!notificationEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon")) {
            String packageName = notificationEntry.mSbn.getPackageName();
            z = packageName.startsWith("android") || packageName.startsWith("com.android.systemui");
        }
        Icon icon = (!zIsShowNotificationAppIconEnabled || z) ? ongoingActivityData.mCardIcon : ongoingActivityData.mAppIcon;
        if (icon != null) {
            remoteViews.setImageViewIcon(i, icon);
            boolean zIsGrayscaleIcon = ContrastColorUtil.getInstance(context).isGrayscaleIcon(icon.loadDrawable(context));
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = INSTANCE;
            if (!zIsShowNotificationAppIconEnabled) {
                ongoingActivityLayoutUtil.getClass();
                updateSmallIconView(ongoingActivityData, remoteViews, i, context, zIsGrayscaleIcon);
            } else {
                if (!z) {
                    remoteViews.setInt(i, "setBackgroundResource", 0);
                    remoteViews2 = remoteViews;
                    i2 = i;
                    remoteViews2.setViewPadding(i2, 0, 0, 0, 0);
                    remoteViews2.setViewVisibility(i2, 0);
                }
                if (zIsGrayscaleIcon) {
                    remoteViews.setInt(i, "setBackgroundResource", R.drawable.squircle);
                } else {
                    remoteViews.setInt(i, "setBackgroundResource", R.drawable.nongrayscale_small_icon_outline_squirecle);
                }
                ongoingActivityLayoutUtil.getClass();
                updateSmallIconView(ongoingActivityData, remoteViews, i, context, zIsGrayscaleIcon);
            }
            remoteViews2 = remoteViews;
            i2 = i;
            remoteViews2.setViewVisibility(i2, 0);
        }
    }

    public static void updateNowbarSports(Context context, View view, OngoingActivityData ongoingActivityData, OngoingType ongoingType) {
        String string;
        int subScreenCardWidth = (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && ongoingType == OngoingType.SUB) ? ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).getSubScreenCardWidth(context) : ongoingType == OngoingType.ENR ? getENRCardWidth(context) : getOngoingCardWidth(context);
        if (ongoingActivityData.mCustomExpandedCardView != null) {
            Iterator it = Arrays.asList("sports_expand_margin_start", "sports_expand_margin_end").iterator();
            while (it.hasNext()) {
                View viewFindViewWithTag = view.findViewWithTag((String) it.next());
                if (viewFindViewWithTag != null) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewWithTag.getLayoutParams();
                    layoutParams.width = (int) (subScreenCardWidth * 0.06f);
                    viewFindViewWithTag.setLayoutParams(layoutParams);
                }
            }
            Iterator it2 = Arrays.asList("start_team_progress", "draw_progress", "end_team_progress").iterator();
            while (it2.hasNext()) {
                View viewFindViewWithTag2 = view.findViewWithTag((String) it2.next());
                if (viewFindViewWithTag2 != null) {
                    CharSequence contentDescription = viewFindViewWithTag2.getContentDescription();
                    Float floatOrNull = (contentDescription == null || (string = contentDescription.toString()) == null) ? null : StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(string);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewFindViewWithTag2.getLayoutParams();
                    layoutParams2.weight = floatOrNull != null ? floatOrNull.floatValue() : layoutParams2.weight;
                    viewFindViewWithTag2.setLayoutParams(layoutParams2);
                }
            }
        }
    }

    public static void updateOngoingChronometer(View view, OngoingActivityData ongoingActivityData, boolean z) {
        String str = ongoingActivityData.mChronometerTag;
        boolean zIsPromotedState = ongoingActivityData.mNotificationEntry.isPromotedState();
        boolean zIsActionStyle = isActionStyle(ongoingActivityData);
        Context context = view.getContext();
        boolean z2 = view.findViewWithTag(str) instanceof Chronometer;
        int i = R.color.ongoing_activity_card_item_sub_text_color;
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = INSTANCE;
        if (z2) {
            Chronometer chronometer = (Chronometer) view.findViewWithTag(str);
            if (chronometer != null) {
                ongoingActivityLayoutUtil.getClass();
                chronometer.getLayoutParams().width = -1;
                chronometer.setEllipsize(TextUtils.TruncateAt.END);
                if (isPrimaryChronometer(ongoingActivityData)) {
                    chronometer.setId(R.id.tag_for_ongoing_chronometer_expand_primary);
                    chronometer.setTextColor(context.getColor(R.color.ongoing_activity_card_item_main_text_color));
                    updatePrimaryChronometerFont(context, chronometer, zIsPromotedState, zIsActionStyle, z);
                }
                if (ongoingActivityData.mChronometerView == null || ongoingActivityData.mChronometerPosition != 2) {
                    return;
                }
                chronometer.setId(R.id.tag_for_ongoing_chronometer_expand_secondary);
                if (zIsActionStyle) {
                    i = R.color.ongoing_activity_action_style_secondary_text_color;
                }
                chronometer.setTextColor(context.getColor(i));
                updateSecondaryChronometerFont(context, chronometer, z);
                return;
            }
            return;
        }
        if (isPrimaryChronometer(ongoingActivityData)) {
            Chronometer chronometerFindChronometer = findChronometer(view, "expandPrimaryContainer");
            if (chronometerFindChronometer == null) {
                chronometerFindChronometer = findChronometer(view, "collapsedPrimaryContainer");
            }
            if (chronometerFindChronometer != null) {
                ongoingActivityLayoutUtil.getClass();
                chronometerFindChronometer.getLayoutParams().width = -1;
                chronometerFindChronometer.setEllipsize(TextUtils.TruncateAt.END);
                chronometerFindChronometer.setId(R.id.tag_for_ongoing_chronometer_expand_primary);
                chronometerFindChronometer.setTextColor(context.getColor(R.color.ongoing_activity_card_item_main_text_color));
                updatePrimaryChronometerFont(context, chronometerFindChronometer, zIsPromotedState, zIsActionStyle, z);
            }
        }
        if (ongoingActivityData.mChronometerView == null || ongoingActivityData.mChronometerPosition != 2) {
            return;
        }
        Chronometer chronometerFindChronometer2 = findChronometer(view, "expandSecondaryContainer");
        if (chronometerFindChronometer2 == null) {
            chronometerFindChronometer2 = findChronometer(view, "collapsedSecondaryContainer");
        }
        if (chronometerFindChronometer2 != null) {
            ongoingActivityLayoutUtil.getClass();
            chronometerFindChronometer2.getLayoutParams().width = -1;
            chronometerFindChronometer2.setEllipsize(TextUtils.TruncateAt.END);
            chronometerFindChronometer2.setId(R.id.tag_for_ongoing_chronometer_expand_secondary);
            if (zIsActionStyle) {
                i = R.color.ongoing_activity_action_style_secondary_text_color;
            }
            chronometerFindChronometer2.setTextColor(context.getColor(i));
            updateSecondaryChronometerFont(context, chronometerFindChronometer2, z);
        }
    }

    public static void updateOngoingDescription(View view) {
        TextView textView = (TextView) view.findViewWithTag("description");
        if (textView != null) {
            textView.setMaxLines(textView.getContext().getResources().getDimensionPixelSize(R.dimen.oa_expanded_view_big_text_max_height) / textView.getLineHeight());
            textView.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    public static void updateOngoingHeader(View view, final OngoingActivityData ongoingActivityData) throws Resources.NotFoundException {
        final View viewFindViewById = view.findViewById(android.R.id.remote_input);
        if (viewFindViewById != null) {
            final Context context = view.getContext();
            final int dimensionPixelSize = view.getResources().getDimensionPixelSize(17105823);
            final int dimensionPixelSize2 = view.getResources().getDimensionPixelSize(17105819);
            final boolean zIsNightModeActive = context.getResources().getConfiguration().isNightModeActive();
            viewFindViewById.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil$updateOngoingHeader$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    Icon icon;
                    if (viewFindViewById.getParent() == null || ((View) viewFindViewById.getParent()).getId() != R.id.expandedPublic) {
                        return;
                    }
                    TextView textView = (TextView) viewFindViewById.findViewById(android.R.id.beforeDescendants);
                    if (textView != null) {
                        Context context2 = context;
                        int i = dimensionPixelSize;
                        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                        context2.getClass();
                        ongoingActivityLayoutUtil.getClass();
                        OngoingActivityLayoutUtil.updateOngoingNotificationFont(textView, i, null);
                    }
                    DateTimeView dateTimeViewFindViewById = viewFindViewById.findViewById(16909968);
                    if (dateTimeViewFindViewById != null) {
                        Context context3 = context;
                        int i2 = dimensionPixelSize2;
                        boolean z = zIsNightModeActive;
                        OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
                        context3.getClass();
                        ongoingActivityLayoutUtil2.getClass();
                        OngoingActivityLayoutUtil.updateOngoingNotificationFont(dateTimeViewFindViewById, i2, null);
                        dateTimeViewFindViewById.setTextColor(Color.parseColor(z ? "#B3FAFAFF" : "#B3252528"));
                    }
                    NotificationRowIconView notificationRowIconViewFindViewById = viewFindViewById.findViewById(android.R.id.icon);
                    if (notificationRowIconViewFindViewById == null || (icon = ongoingActivityData.mAodRemoteAppIcon) == null) {
                        return;
                    }
                    notificationRowIconViewFindViewById.setImageIcon(icon);
                }
            });
        }
    }

    public static void updateOngoingNotificationFont(TextView textView, int i, Typeface typeface) {
        textView.setIncludeFontPadding(false);
        textView.setTextSize(0, i);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }

    public static void updatePrimaryChronometerFont(Context context, Chronometer chronometer, boolean z, boolean z2, boolean z3) throws Resources.NotFoundException {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(!z ? z3 ? R.dimen.ongoing_activity_sub_screen_card_item_main_text_size : R.dimen.ongoing_activity_card_item_main_text_size : z2 ? z3 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_chronometer_text_size : R.dimen.ongoing_activity_card_item_promoted_main_chronometer_text_size : z3 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_text_size : R.dimen.ongoing_activity_card_item_promoted_main_text_size);
        Typeface typefaceCreate = Typeface.create(Typeface.create("sec-num-fixed", 0), (z && z2) ? 400 : VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        if (!z || !z2) {
            updateOngoingNotificationFont(chronometer, dimensionPixelSize, typefaceCreate);
        } else {
            chronometer.setTypeface(typefaceCreate);
            chronometer.setTextSize(0, dimensionPixelSize * 1.0f);
        }
    }

    public static void updateSecondaryChronometerFont(Context context, Chronometer chronometer, boolean z) {
        updateOngoingNotificationFont(chronometer, context.getResources().getDimensionPixelSize(z ? R.dimen.ongoing_activity_sub_screen_card_item_sub_text_size : R.dimen.ongoing_activity_card_item_sub_text_size), Typeface.create(Typeface.create("sec", 0), 400, false));
    }

    public static void updateSmallIconView(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, int i, Context context, boolean z) {
        if (z) {
            Integer num = ongoingActivityData.mCardIconBg;
            if (num != null) {
                int iIntValue = num.intValue();
                if (iIntValue == 0) {
                    iIntValue = -1;
                }
                remoteViews.setDrawableTint(i, true, iIntValue, PorterDuff.Mode.SRC_IN);
            }
            int color = context.getColor(R.color.ongoing_activity_card_item_notification_icon_color);
            remoteViews.setInt(i, "setColorFilter", Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
        }
    }
}
