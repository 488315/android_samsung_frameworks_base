package com.android.systemui.statusbar.phone.ongoingactivity;

import android.R;
import android.app.Notification;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.MathUtils;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.DrawableKt;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingActivityLayoutCreatorImpl {
    public final Context context;

    public OngoingActivityLayoutCreatorImpl(Context context) {
        this.context = context;
    }

    public static boolean canShowSecondaryIcon(OngoingActivityData ongoingActivityData) {
        return StringsKt__StringsKt.trim(ongoingActivityData.mDescription).toString().length() == 0 && (StringsKt__StringsKt.trim(ongoingActivityData.mSecondaryInfo).toString().length() > 0 || (ongoingActivityData.mChronometerView != null && ongoingActivityData.mChronometerPosition == 2));
    }

    public final void bindProfileBadge(RemoteViews remoteViews, final int i) {
        DevicePolicyManager devicePolicyManager;
        DevicePolicyResourcesManager resources;
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        final Context context = this.context;
        ongoingActivityLayoutUtil.getClass();
        Drawable drawable = null;
        if (i != 0 && (devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)) != null && (resources = devicePolicyManager.getResources()) != null) {
            drawable = resources.getDrawable("WORK_PROFILE_ICON", "SOLID_COLORED", "NOTIFICATION", new Supplier() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil$getDefaultProfileBadgeDrawable$supplier$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return context.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(i), 0);
                }
            });
        }
        if (drawable != null) {
            remoteViews.setImageViewBitmap(R.id.secondary, DrawableKt.toBitmap$default(drawable));
            remoteViews.setViewVisibility(R.id.secondary, 0);
        }
    }

    public final void bindTime(RemoteViews remoteViews, Long l) {
        float dimension = this.context.getResources().getDimension(com.android.systemui.R.dimen.ongoing_activity_card_item_header_time_text_size);
        remoteViews.setLong(16909905, "setTime", l != null ? l.longValue() : System.currentTimeMillis());
        remoteViews.setTextViewTextSize(16909905, 0, dimension);
        remoteViews.setTextColor(16909905, this.context.getColor(com.android.systemui.R.color.ongoing_activity_expand_header_text_color));
        remoteViews.setViewVisibility(16909905, 0);
    }

    public final RemoteViews createCollapsedView(OngoingActivityData ongoingActivityData) {
        ArrayList arrayList;
        Unit unit;
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), com.android.systemui.R.layout.ongoing_collapsed_view);
        setOngoingNotificationIcon(ongoingActivityData, remoteViews, com.android.systemui.R.id.ongoing_activity_collapsed_icon);
        String str = ongoingActivityData.mPrimaryInfo;
        if (str.length() > 0) {
            remoteViews.setTextViewText(R.id.title, str);
            bindTime(remoteViews, ongoingActivityData.mWhen);
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.context;
            int i = ongoingActivityData.mUserId;
            ongoingActivityLayoutUtil.getClass();
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            if (userManager != null ? userManager.isManagedProfile(i) : false) {
                bindProfileBadge(remoteViews, i);
            }
        }
        CharSequence charSequence = ongoingActivityData.mSecondaryInfo;
        if (StringsKt__StringsKt.trim(charSequence).toString().length() > 0) {
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_container, 0);
            remoteViews.setTextViewText(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, charSequence);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, 0);
        }
        CharSequence charSequence2 = ongoingActivityData.mDescription;
        if (StringsKt__StringsKt.trim(charSequence2).toString().length() > 0) {
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_container, 0);
            remoteViews.setTextViewText(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, charSequence2);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, 0);
        }
        if (canShowSecondaryIcon(ongoingActivityData)) {
            Icon icon = ongoingActivityData.mSecondaryInfoIcon;
            if (icon != null) {
                remoteViews.setImageViewIcon(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_icon, icon);
                remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_icon, 0);
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_icon, 8);
            }
        } else {
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_icon, 8);
        }
        int i2 = ongoingActivityData.mActionType;
        if (i2 != -1 && (arrayList = ongoingActivityData.mActions) != null && i2 == 0) {
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                RemoteViews remoteViews2 = new RemoteViews(this.context.getPackageName(), com.android.systemui.R.layout.ongoing_activity_button);
                remoteViews2.setImageViewIcon(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i3)).getIcon());
                remoteViews2.setOnClickPendingIntent(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i3)).actionIntent);
                remoteViews2.setContentDescription(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i3)).title);
                remoteViews2.setViewLayoutMarginDimen(com.android.systemui.R.id.ongoing_action_button, 4, i3 == 0 ? com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_horizontal_margin : com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_start_margin);
                remoteViews.addView(com.android.systemui.R.id.ongoing_activity_collapsed_icon_buttons_end, remoteViews2);
                i3++;
            }
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_icon_buttons_end, 0);
            remoteViews.setViewVisibility(16909905, 8);
            remoteViews.setViewVisibility(R.id.secondary, 8);
            remoteViews.setTextColor(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, this.context.getColor(com.android.systemui.R.color.ongoing_activity_action_style_secondary_text_color));
            remoteViews.setInt(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, "setMaxLines", 1);
        }
        remoteViews.setInt(com.android.systemui.R.id.ongoing_activity_collapsed_expand_button, "setColorFilter", this.context.getColor(com.android.systemui.R.color.ongoing_activity_expand_button_color));
        RemoteViews remoteViews3 = ongoingActivityData.mChronometerView;
        if (remoteViews3 != null) {
            int i4 = ongoingActivityData.mChronometerPosition;
            if (i4 == 1) {
                remoteViews.setViewVisibility(R.id.progressContainer, 8);
                remoteViews.addView(com.android.systemui.R.id.ongoing_activity_collapsed_primary_container, remoteViews3, 0);
            }
            if (i4 == 2) {
                remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_container, 0);
                remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_text, 8);
                remoteViews.addView(com.android.systemui.R.id.ongoing_activity_collapsed_secondary_container, remoteViews3);
            }
        }
        return remoteViews;
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x03b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.widget.RemoteViews createExpandView(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData r30) {
        /*
            Method dump skipped, instructions count: 985
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutCreatorImpl.createExpandView(com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData):android.widget.RemoteViews");
    }

    public final boolean isBottomBtnActionStyle() {
        return this.context.getResources().getConfiguration().fontScale >= 1.3f;
    }

    public final RemoteViews makeTextButtonRemoteView(Notification.Action action) {
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), com.android.systemui.R.layout.ongoing_activity_text_button);
        remoteViews.setTextViewText(com.android.systemui.R.id.ongoing_action_text_button, action.title);
        remoteViews.setTextViewTextSize(com.android.systemui.R.id.ongoing_action_text_button, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.ongoing_activity_card_item_sub_text_size)));
        remoteViews.setOnClickPendingIntent(com.android.systemui.R.id.ongoing_action_text_button, action.actionIntent);
        return remoteViews;
    }

    public final void setOngoingNotificationIcon(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, int i) {
        boolean isShowNotificationAppIconEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled();
        boolean z = ongoingActivityData.mNotificationEntry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
        Icon icon = (!isShowNotificationAppIconEnabled || z) ? ongoingActivityData.mCardIcon : ongoingActivityData.mAppIcon;
        if (icon != null) {
            remoteViews.setImageViewIcon(i, icon);
            if (!isShowNotificationAppIconEnabled) {
                updateSmallIconView(ongoingActivityData, remoteViews, i);
            } else if (z) {
                remoteViews.setInt(i, "setBackgroundResource", com.android.systemui.R.drawable.squircle);
                updateSmallIconView(ongoingActivityData, remoteViews, i);
            } else {
                remoteViews.setInt(i, "setBackgroundResource", 0);
                remoteViews.setViewPadding(i, 0, 0, 0, 0);
            }
            remoteViews.setViewVisibility(i, 0);
        }
    }

    public final float upToLargeFontSize(int i) {
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        Context context = this.context;
        ongoingActivityLayoutUtil.getClass();
        float f = context.getResources().getConfiguration().fontScale;
        return (i / f) * MathUtils.constrain(f, 0.8f, 1.3f);
    }

    public final void updateExpandViewFontSize(RemoteViews remoteViews, boolean z) {
        remoteViews.setTextViewTextSize(com.android.systemui.R.id.ongoing_activity_expand_primary, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(z ? com.android.systemui.R.dimen.ongoing_activity_card_item_main_large_text_size : com.android.systemui.R.dimen.ongoing_activity_card_item_main_text_size)));
        float upToLargeFontSize = upToLargeFontSize(this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.ongoing_activity_card_item_sub_text_size));
        remoteViews.setTextViewTextSize(com.android.systemui.R.id.ongoing_activity_expand_secondary_text, 0, upToLargeFontSize);
        remoteViews.setTextViewTextSize(com.android.systemui.R.id.ongoing_activity_expand_description, 0, upToLargeFontSize);
        remoteViews.setTextViewTextSize(R.id.authtoken_type, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.ongoing_activity_card_item_header_app_label_text_size)));
        remoteViews.setTextViewTextSize(16909905, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.ongoing_activity_card_item_header_time_text_size)));
    }

    public final void updateIconButtonRemoteView(ArrayList arrayList, RemoteViews remoteViews) {
        RemoteViews remoteViews2 = new RemoteViews(this.context.getPackageName(), com.android.systemui.R.layout.ongoing_activity_icon_button_container);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            RemoteViews remoteViews3 = new RemoteViews(this.context.getPackageName(), com.android.systemui.R.layout.ongoing_activity_button);
            remoteViews3.setImageViewIcon(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).getIcon());
            remoteViews3.setOnClickPendingIntent(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).actionIntent);
            remoteViews3.setContentDescription(com.android.systemui.R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).title);
            boolean isBottomBtnActionStyle = isBottomBtnActionStyle();
            int i2 = com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_horizontal_margin;
            if (isBottomBtnActionStyle) {
                remoteViews3.setViewLayoutMarginDimen(com.android.systemui.R.id.ongoing_action_button, 4, com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_horizontal_margin);
                remoteViews3.setViewLayoutMarginDimen(com.android.systemui.R.id.ongoing_action_button, 5, com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_horizontal_margin);
            } else {
                if (i != 0) {
                    i2 = com.android.systemui.R.dimen.ongoing_activity_expanded_action_button_start_margin;
                }
                remoteViews3.setViewLayoutMarginDimen(com.android.systemui.R.id.ongoing_action_button, 4, i2);
            }
            remoteViews2.addView(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons, remoteViews3);
        }
        remoteViews.removeAllViews(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_bottom);
        remoteViews.removeAllViews(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_end);
        if (isBottomBtnActionStyle()) {
            remoteViews.addView(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_bottom, remoteViews2);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_bottom, 0);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_end, 8);
        } else {
            remoteViews.addView(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_end, remoteViews2);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_end, 0);
            remoteViews.setViewVisibility(com.android.systemui.R.id.ongoing_activity_expand_icon_buttons_bottom, 8);
        }
    }

    public final void updateSmallIconView(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, int i) {
        Integer num = ongoingActivityData.mCardIconBg;
        if (num != null) {
            int intValue = num.intValue();
            if (intValue == 0) {
                intValue = -1;
            }
            remoteViews.setDrawableTint(i, true, intValue, PorterDuff.Mode.SRC_IN);
        }
        int color = this.context.getColor(com.android.systemui.R.color.notification_app_icon_color);
        remoteViews.setInt(i, "setColorFilter", Color.argb(255, Color.red(color), Color.green(color), Color.blue(color)));
    }
}
