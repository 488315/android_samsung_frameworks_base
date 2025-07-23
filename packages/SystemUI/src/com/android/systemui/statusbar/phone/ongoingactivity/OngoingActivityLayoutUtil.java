package com.android.systemui.statusbar.phone.ongoingactivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DateTimeView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceState;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Boolean valueOf = userManager != null ? Boolean.valueOf(userManager.isManagedProfile(i)) : null;
        valueOf.getClass();
        if (valueOf.booleanValue()) {
            return true;
        }
        UserManager userManager2 = (UserManager) statusBarNotification.getPackageContext(context).getSystemService(UserManager.class);
        Boolean valueOf2 = userManager2 != null ? Boolean.valueOf(userManager2.isPrivateProfile()) : null;
        valueOf2.getClass();
        return valueOf2.booleanValue();
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
            remoteViews.setFloat(android.R.id.remote_input, "setTopLineExtraMarginEndDp", (resources.getDimension(17105810) / f) + (resources.getDimension(17105807) / f) + (resources.getDimension(android.R.dimen.toast_text_size) / f));
        } else {
            remoteViews.setFloat(android.R.id.remote_input, "setTopLineExtraMarginEndDp", resources.getDimension(android.R.dimen.toast_text_size) / f);
        }
        boolean z2 = notificationEntry.mSbn.getNotification().getLargeIcon() != null;
        Resources resources2 = context.getResources();
        float f2 = resources2.getDisplayMetrics().density;
        if (z2) {
            remoteViews.setViewLayoutMargin(android.R.id.title, 5, (resources2.getDimension(17105810) / f2) + (resources2.getDimension(17105807) / f2) + (resources2.getDimension(android.R.dimen.toast_text_size) / f2), 1);
        } else {
            remoteViews.setViewLayoutMargin(android.R.id.title, 5, resources2.getDimension(android.R.dimen.toast_text_size) / f2, 1);
        }
        if (notificationEntry.mSbn.getNotification().getLargeIcon() != null) {
            remoteViews.setViewLayoutMargin(android.R.id.tag_top_animator, 5, r12.getDimensionPixelSize(android.R.dimen.toast_text_size) / context.getResources().getDisplayMetrics().density, 1);
        }
    }

    public static void setExpandButtonColor(RemoteViews remoteViews) {
        if (remoteViews != null) {
            int textColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getTextColor(2, false, true);
            remoteViews.setInt(android.R.id.flagRetrieveInteractiveWindows, "setDefaultTextColor", Color.argb(153, Color.red(textColor), Color.green(textColor), Color.blue(textColor)));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setOngoingNotificationIcon(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData r10, android.widget.RemoteViews r11, int r12, android.content.Context r13) {
        /*
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.util.SettingsHelper> r1 = com.android.systemui.util.SettingsHelper.class
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.util.SettingsHelper r0 = (com.android.systemui.util.SettingsHelper) r0
            boolean r0 = r0.isShowNotificationAppIconEnabled()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r10.mNotificationEntry
            android.service.notification.StatusBarNotification r2 = r1.mSbn
            android.app.Notification r2 = r2.getNotification()
            android.os.Bundle r2 = r2.extras
            java.lang.String r3 = "android.showSmallIcon"
            boolean r2 = r2.getBoolean(r3)
            r3 = 0
            if (r2 != 0) goto L3a
            android.service.notification.StatusBarNotification r1 = r1.mSbn
            java.lang.String r1 = r1.getPackageName()
            java.lang.String r2 = "android"
            boolean r2 = r1.startsWith(r2)
            if (r2 != 0) goto L3a
            java.lang.String r2 = "com.android.systemui"
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L38
            goto L3a
        L38:
            r1 = r3
            goto L3b
        L3a:
            r1 = 1
        L3b:
            if (r0 == 0) goto L42
            if (r1 != 0) goto L42
            android.graphics.drawable.Icon r2 = r10.mAppIcon
            goto L44
        L42:
            android.graphics.drawable.Icon r2 = r10.mCardIcon
        L44:
            if (r2 == 0) goto L77
            r11.setImageViewIcon(r12, r2)
            com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil r2 = com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil.INSTANCE
            if (r0 != 0) goto L56
            r2.getClass()
            updateSmallIconView(r10, r11, r12, r13)
        L53:
            r4 = r11
            r5 = r12
            goto L74
        L56:
            java.lang.String r0 = "setBackgroundResource"
            if (r1 == 0) goto L68
            r1 = 2131235715(0x7f081383, float:1.8087632E38)
            r11.setInt(r12, r0, r1)
            r2.getClass()
            updateSmallIconView(r10, r11, r12, r13)
            goto L53
        L68:
            r11.setInt(r12, r0, r3)
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r4 = r11
            r5 = r12
            r4.setViewPadding(r5, r6, r7, r8, r9)
        L74:
            r4.setViewVisibility(r5, r3)
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil.setOngoingNotificationIcon(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData, android.widget.RemoteViews, int, android.content.Context):void");
    }

    public static void updateNowbarSports(Context context, View view, OngoingActivityData ongoingActivityData, OngoingType ongoingType) {
        String obj;
        int subScreenCardWidth = (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && ongoingType == OngoingType.SUB) ? ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).getSubScreenCardWidth(context) : ongoingType == OngoingType.ENR ? getENRCardWidth(context) : getOngoingCardWidth(context);
        if (ongoingActivityData.mCustomExpandedCardView != null) {
            Iterator it = Arrays.asList("sports_expand_margin_start", "sports_expand_margin_end").iterator();
            while (it.hasNext()) {
                View findViewWithTag = view.findViewWithTag((String) it.next());
                if (findViewWithTag != null) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewWithTag.getLayoutParams();
                    layoutParams.width = (int) (subScreenCardWidth * 0.06f);
                    findViewWithTag.setLayoutParams(layoutParams);
                }
            }
            Iterator it2 = Arrays.asList("start_team_progress", "draw_progress", "end_team_progress").iterator();
            while (it2.hasNext()) {
                View findViewWithTag2 = view.findViewWithTag((String) it2.next());
                if (findViewWithTag2 != null) {
                    CharSequence contentDescription = findViewWithTag2.getContentDescription();
                    Float floatOrNull = (contentDescription == null || (obj = contentDescription.toString()) == null) ? null : StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(obj);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) findViewWithTag2.getLayoutParams();
                    layoutParams2.weight = floatOrNull != null ? floatOrNull.floatValue() : layoutParams2.weight;
                    findViewWithTag2.setLayoutParams(layoutParams2);
                }
            }
        }
    }

    public static void updateOngoingChronometer(View view, OngoingActivityData ongoingActivityData, boolean z) {
        String str = ongoingActivityData.mChronometerTag;
        boolean isPromotedState = ongoingActivityData.mNotificationEntry.isPromotedState();
        boolean isActionStyle = isActionStyle(ongoingActivityData);
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
                    updatePrimaryChronometerFont(context, chronometer, isPromotedState, isActionStyle, z);
                }
                if (ongoingActivityData.mChronometerView == null || ongoingActivityData.mChronometerPosition != 2) {
                    return;
                }
                chronometer.setId(R.id.tag_for_ongoing_chronometer_expand_secondary);
                if (isActionStyle) {
                    i = R.color.ongoing_activity_action_style_secondary_text_color;
                }
                chronometer.setTextColor(context.getColor(i));
                updateSecondaryChronometerFont(context, chronometer, z);
                return;
            }
            return;
        }
        if (isPrimaryChronometer(ongoingActivityData)) {
            Chronometer findChronometer = findChronometer(view, "expandPrimaryContainer");
            if (findChronometer == null) {
                findChronometer = findChronometer(view, "collapsedPrimaryContainer");
            }
            if (findChronometer != null) {
                ongoingActivityLayoutUtil.getClass();
                findChronometer.getLayoutParams().width = -1;
                findChronometer.setEllipsize(TextUtils.TruncateAt.END);
                findChronometer.setId(R.id.tag_for_ongoing_chronometer_expand_primary);
                findChronometer.setTextColor(context.getColor(R.color.ongoing_activity_card_item_main_text_color));
                updatePrimaryChronometerFont(context, findChronometer, isPromotedState, isActionStyle, z);
            }
        }
        if (ongoingActivityData.mChronometerView == null || ongoingActivityData.mChronometerPosition != 2) {
            return;
        }
        Chronometer findChronometer2 = findChronometer(view, "expandSecondaryContainer");
        if (findChronometer2 == null) {
            findChronometer2 = findChronometer(view, "collapsedSecondaryContainer");
        }
        if (findChronometer2 != null) {
            ongoingActivityLayoutUtil.getClass();
            findChronometer2.getLayoutParams().width = -1;
            findChronometer2.setEllipsize(TextUtils.TruncateAt.END);
            findChronometer2.setId(R.id.tag_for_ongoing_chronometer_expand_secondary);
            if (isActionStyle) {
                i = R.color.ongoing_activity_action_style_secondary_text_color;
            }
            findChronometer2.setTextColor(context.getColor(i));
            updateSecondaryChronometerFont(context, findChronometer2, z);
        }
    }

    public static void updateOngoingHeader(View view) {
        final View findViewById = view.findViewById(android.R.id.remote_input);
        if (findViewById != null) {
            final Context context = view.getContext();
            final int dimensionPixelSize = view.getResources().getDimensionPixelSize(17105822);
            final int dimensionPixelSize2 = view.getResources().getDimensionPixelSize(17105818);
            final boolean isNightModeActive = context.getResources().getConfiguration().isNightModeActive();
            findViewById.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil$updateOngoingHeader$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (findViewById.getParent() == null || ((View) findViewById.getParent()).getId() != R.id.expandedPublic) {
                        return;
                    }
                    TextView textView = (TextView) findViewById.findViewById(android.R.id.beforeDescendants);
                    if (textView != null) {
                        Context context2 = context;
                        int i = dimensionPixelSize;
                        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                        context2.getClass();
                        ongoingActivityLayoutUtil.getClass();
                        OngoingActivityLayoutUtil.updateOngoingNotificationFont(textView, i, null);
                    }
                    DateTimeView findViewById2 = findViewById.findViewById(16909967);
                    if (findViewById2 != null) {
                        Context context3 = context;
                        int i2 = dimensionPixelSize2;
                        boolean z = isNightModeActive;
                        OngoingActivityLayoutUtil ongoingActivityLayoutUtil2 = OngoingActivityLayoutUtil.INSTANCE;
                        context3.getClass();
                        ongoingActivityLayoutUtil2.getClass();
                        OngoingActivityLayoutUtil.updateOngoingNotificationFont(findViewById2, i2, null);
                        findViewById2.setTextColor(Color.parseColor(z ? "#B3FAFAFF" : "#B3252528"));
                    }
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

    public static void updatePrimaryChronometerFont(Context context, Chronometer chronometer, boolean z, boolean z2, boolean z3) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(!z ? z3 ? R.dimen.ongoing_activity_sub_screen_card_item_main_text_size : R.dimen.ongoing_activity_card_item_main_text_size : z2 ? z3 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_chronometer_text_size : R.dimen.ongoing_activity_card_item_promoted_main_chronometer_text_size : z3 ? R.dimen.ongoing_activity_sub_screen_card_item_promoted_main_text_size : R.dimen.ongoing_activity_card_item_promoted_main_text_size);
        Typeface create = Typeface.create(Typeface.create("sec-num-fixed", 0), (z && z2) ? 400 : VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        if (!z || !z2) {
            updateOngoingNotificationFont(chronometer, dimensionPixelSize, create);
        } else {
            chronometer.setTypeface(create);
            chronometer.setTextSize(0, dimensionPixelSize * 1.0f);
        }
    }

    public static void updateSecondaryChronometerFont(Context context, Chronometer chronometer, boolean z) {
        updateOngoingNotificationFont(chronometer, context.getResources().getDimensionPixelSize(z ? R.dimen.ongoing_activity_sub_screen_card_item_sub_text_size : R.dimen.ongoing_activity_card_item_sub_text_size), Typeface.create(Typeface.create("sec", 0), 400, false));
    }

    public static void updateSmallIconView(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, int i, Context context) {
        Integer num = ongoingActivityData.mCardIconBg;
        if (num != null) {
            int intValue = num.intValue();
            if (intValue == 0) {
                intValue = -1;
            }
            remoteViews.setDrawableTint(i, true, intValue, PorterDuff.Mode.SRC_IN);
        }
        int color = context.getColor(R.color.ongoing_activity_card_item_notification_icon_color);
        remoteViews.setInt(i, "setColorFilter", Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
    }
}
