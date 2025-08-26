package com.android.systemui.statusbar.phone.ongoingactivity;

import android.app.Notification;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.DrawableKt;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.function.Supplier;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
public final class OngoingActivityLayoutCreatorImpl {
    public final Context context;

    public OngoingActivityLayoutCreatorImpl(Context context) {
        this.context = context;
    }

    public static void bindSecondaryInfoIcon(RemoteViews remoteViews, Icon icon) {
        if (icon == null) {
            remoteViews.setViewVisibility(R.id.ongoing_activity_secondary_info_icon, 8);
            return;
        }
        remoteViews.setImageViewIcon(R.id.ongoing_activity_secondary_info_icon, icon);
        remoteViews.setViewVisibility(R.id.ongoing_activity_secondary_info_icon, 0);
        remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 4, 0);
    }

    public static boolean canShowSecondaryIcon(OngoingActivityData ongoingActivityData) {
        if (StringsKt__StringsKt.trim(ongoingActivityData.mDescription).toString().length() != 0) {
            return false;
        }
        if (StringsKt__StringsKt.trim(ongoingActivityData.mSecondaryInfo).toString().length() > 0) {
            return true;
        }
        return ongoingActivityData.mChronometerView != null && ongoingActivityData.mChronometerPosition == 2;
    }

    public static boolean isBottomBtnActionStyle(Context context) {
        if (context.getResources().getConfiguration().fontScale < 1.3f) {
            OngoingActivityLayoutUtil.INSTANCE.getClass();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = context.getResources().getConfiguration().orientation == 1 ? displayMetrics.widthPixels : displayMetrics.heightPixels;
            if (context.getResources().getConfiguration().densityDpi < (i >= 1440 ? 680 : (721 > i || i >= 1081) ? 340 : 510)) {
                return false;
            }
        }
        return true;
    }

    public static float upToLargeFontSize(int i) {
        OngoingActivityLayoutUtil.INSTANCE.getClass();
        return i;
    }

    public static void updateExpandViewFontSize$default(OngoingActivityLayoutCreatorImpl ongoingActivityLayoutCreatorImpl, RemoteViews remoteViews, boolean z) {
        Context context = ongoingActivityLayoutCreatorImpl.context;
        ongoingActivityLayoutCreatorImpl.getClass();
        float fUpToLargeFontSize = upToLargeFontSize(context.getResources().getDimensionPixelSize(R.dimen.oa_common_view_info_chip_size));
        remoteViews.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
        remoteViews.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
        remoteViews.setTextViewTextSize(R.id.ongoing_activity_expand_primary, 0, upToLargeFontSize(context.getResources().getDimensionPixelSize(z ? R.dimen.ongoing_activity_card_item_promoted_main_text_size : R.dimen.ongoing_activity_card_item_main_text_size)));
        float fUpToLargeFontSize2 = upToLargeFontSize(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_sub_text_size));
        remoteViews.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text, 0, fUpToLargeFontSize2);
        remoteViews.setTextViewTextSize(R.id.ongoing_activity_expand_description, 0, fUpToLargeFontSize2);
        float fUpToLargeFontSize3 = upToLargeFontSize(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_header_app_label_text_size));
        remoteViews.setTextViewTextSize(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 0, fUpToLargeFontSize3);
        remoteViews.setTextViewTextSize(android.R.id.beforeDescendants, 0, fUpToLargeFontSize3);
        remoteViews.setTextViewTextSize(16909968, 0, upToLargeFontSize(context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_header_time_text_size)));
    }

    public final void bindProfileBadge(RemoteViews remoteViews, int i) {
        DevicePolicyResourcesManager resources;
        OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
        Context context = this.context;
        ongoingActivityLayoutUtil.getClass();
        Drawable drawable = null;
        if (i != 0) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
            final Drawable userBadgeForDensityNoBackground = context.getPackageManager().getUserBadgeForDensityNoBackground(new UserHandle(i), 0);
            Supplier<Drawable> supplier = userBadgeForDensityNoBackground == null ? null : new Supplier() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil$getDefaultProfileBadgeDrawable$supplier$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return userBadgeForDensityNoBackground;
                }
            };
            if (supplier != null && devicePolicyManager != null && (resources = devicePolicyManager.getResources()) != null) {
                drawable = resources.getDrawable("WORK_PROFILE_ICON", "SOLID_COLORED", "NOTIFICATION", supplier);
            }
        }
        if (drawable != null) {
            remoteViews.setImageViewBitmap(android.R.id.smallIcon, DrawableKt.toBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), null));
            remoteViews.setViewVisibility(android.R.id.smallIcon, 0);
        }
    }

    public final void bindTime(RemoteViews remoteViews, Long l) {
        remoteViews.setLong(16909968, "setTime", l != null ? l.longValue() : System.currentTimeMillis());
        remoteViews.setTextColor(16909968, this.context.getColor(R.color.ongoing_activity_expand_header_text_color));
        remoteViews.setViewVisibility(16909968, 0);
    }

    public final RemoteViews createExpandView(OngoingActivityData ongoingActivityData, OngoingType ongoingType) throws Resources.NotFoundException {
        ArrayList arrayList;
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.ongoing_expand_view);
        NotificationEntry notificationEntry = ongoingActivityData.mNotificationEntry;
        if (!notificationEntry.mIsRon.booleanValue()) {
            RemoteViews remoteViews2 = ongoingActivityData.mCustomExpandedCardView;
            if (remoteViews2 != null) {
                remoteViews.addView(R.id.ongoing_activity_expand_custom_content, remoteViews2);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_custom_content, 0);
                Log.i("{OngoingActivityLayoutCreator}", "createExpandView: use full custom view (" + ongoingActivityData.mNotiID + ")");
                remoteViews.removeAllViewsExceptId(R.id.ongoing_activity_expand_view, R.id.ongoing_activity_expand_custom_content);
                return remoteViews;
            }
            OngoingActivityLayoutUtil.INSTANCE.getClass();
            boolean z = OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData) && !isBottomBtnActionStyle(this.context);
            boolean zIsPromotedState = notificationEntry.isPromotedState();
            remoteViews.addView(R.id.ongoing_activity_expand_view, z ? new RemoteViews(this.context.getPackageName(), R.layout.ongoing_action_style_view) : new RemoteViews(this.context.getPackageName(), R.layout.ongoing_normal_view));
            OngoingActivityLayoutUtil.setOngoingNotificationIcon(ongoingActivityData, remoteViews, R.id.ongoing_activity_expand_left_image, this.context);
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Context context = this.context;
            int i = ongoingActivityData.mUserId;
            boolean zIsManagedProfile = OngoingActivityLayoutUtil.isManagedProfile(statusBarNotification, context, i);
            if (!zIsPromotedState || zIsManagedProfile) {
                if (z) {
                    remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_left_image, 3, R.dimen.oa_expanded_view_first_icon_bottom_margin);
                }
                remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_primary_container, 1, 0);
                remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_right_image, 1, 0);
            } else if (z) {
                remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_expand_secondary_container, 1, 0);
            }
            if (ongoingActivityData.mActions == null) {
                remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 3, R.dimen.oa_expanded_view_no_action_content_bottom_margin);
            }
            float dimension = this.context.getResources().getDimension(R.dimen.ongoing_activity_card_item_header_app_label_text_size);
            remoteViews.setTextViewText(android.R.id.beforeDescendants, ongoingActivityData.mAppName);
            remoteViews.setTextViewTextSize(android.R.id.beforeDescendants, 0, dimension);
            remoteViews.setTextColor(android.R.id.beforeDescendants, this.context.getColor(R.color.ongoing_activity_expand_header_text_color));
            bindTime(remoteViews, ongoingActivityData.mWhen);
            String str = ongoingActivityData.mPrimaryInfo;
            if (str.length() > 0) {
                remoteViews.setTextViewText(R.id.ongoing_activity_expand_primary, str);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_primary, 0);
            }
            CharSequence charSequence = ongoingActivityData.mSecondaryInfo;
            if (StringsKt__StringsKt.trim(charSequence).toString().length() > 0) {
                if (OngoingActivityLayoutUtil.isPrimaryChronometer(ongoingActivityData) && OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData) && zIsPromotedState) {
                    remoteViews.removeAllViewsExceptId(R.id.ongoing_activity_expand_secondary_text_container, R.id.ongoing_activity_expand_secondary_text_below_chronometer);
                    remoteViews.setTextViewText(R.id.ongoing_activity_expand_secondary_text_below_chronometer, charSequence);
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_text_below_chronometer, 0);
                } else {
                    remoteViews.removeAllViewsExceptId(R.id.ongoing_activity_expand_secondary_text_container, R.id.ongoing_activity_expand_secondary_text);
                    remoteViews.setTextViewText(R.id.ongoing_activity_expand_secondary_text, charSequence);
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_text, 0);
                }
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, 0);
            }
            CharSequence charSequence2 = ongoingActivityData.mDescription;
            if (StringsKt__StringsKt.trim(charSequence2).toString().length() > 0) {
                remoteViews.setTextViewText(R.id.ongoing_activity_expand_description, charSequence2);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_description, 0);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, 8);
            }
            if (canShowSecondaryIcon(ongoingActivityData)) {
                bindSecondaryInfoIcon(remoteViews, ongoingActivityData.mSecondaryInfoIcon);
            } else {
                remoteViews.setViewVisibility(R.id.ongoing_activity_secondary_info_icon, 8);
            }
            Icon icon = ongoingActivityData.mSecondIcon;
            if (icon != null) {
                remoteViews.setImageViewIcon(R.id.ongoing_activity_expand_right_image, icon);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_right_image, 0);
            }
            int i2 = ongoingActivityData.mProgress;
            if (i2 > -1) {
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_upper_progress_container, 0);
                remoteViews.setViewVisibility(R.id.ongoing_activity_expand_progress_right_icon, 8);
                Parcelable[] parcelableArr = ongoingActivityData.mProgressSegments;
                if (parcelableArr.length > 0) {
                    float f = i2;
                    int i3 = ongoingActivityData.mProgressMax;
                    if (i3 > 0) {
                        f = (f * 100) / i3;
                    }
                    remoteViews.setImageViewBitmap(R.id.ongoing_activity_expand_tinted_upper_progress, new OngoingSeekBarCreator(this.context, parcelableArr, f, ongoingActivityData.mProgressSegmentIcon, ongoingActivityData.mProgressColor).makeImage(ongoingType));
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_tinted_upper_progress, 0);
                } else {
                    int i4 = ongoingActivityData.mProgressMax;
                    remoteViews.setProgressBar(R.id.ongoing_activity_expand_upper_progress, i4 != 0 ? i4 : 100, i2, ongoingActivityData.mProgressIndeterminate);
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_upper_progress, 0);
                }
            }
            int i5 = ongoingActivityData.mActionType;
            if (i5 != -1 && (arrayList = ongoingActivityData.mActions) != null) {
                if (i5 == 0) {
                    updateIconButtonRemoteView(arrayList, remoteViews, isBottomBtnActionStyle(this.context));
                    remoteViews.setTextColor(R.id.ongoing_activity_expand_secondary_text, this.context.getColor(R.color.ongoing_activity_action_style_secondary_text_color));
                    remoteViews.setInt(R.id.ongoing_activity_expand_primary, "setMaxLines", 1);
                    remoteViews.setInt(R.id.ongoing_activity_expand_secondary_text, "setMaxLines", 1);
                }
                if (i5 == 1) {
                    int size = arrayList.size();
                    for (int i6 = 0; i6 < size; i6++) {
                        remoteViews.addView(R.id.ongoing_activity_expand_buttons, makeTextButtonRemoteView((Notification.Action) arrayList.get(i6), this.context, false));
                    }
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_buttons, 0);
                }
            }
            if (!zIsPromotedState) {
                updateNotiExpandButtonView(ongoingActivityData, remoteViews, isBottomBtnActionStyle(this.context));
            }
            if (zIsManagedProfile) {
                bindProfileBadge(remoteViews, i);
            } else if (zIsPromotedState) {
                remoteViews.setViewVisibility(R.id.ongoing_activity_expanded_header_container, 8);
            }
            updateExpandViewFontSize$default(this, remoteViews, zIsPromotedState);
            RemoteViews remoteViews3 = ongoingActivityData.mChronometerView;
            if (remoteViews3 != null) {
                int i7 = ongoingActivityData.mChronometerPosition;
                if (i7 == 1) {
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_primary, 8);
                    remoteViews.addView(R.id.ongoing_activity_expand_primary_container, remoteViews3);
                }
                if (i7 == 2) {
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_container, 0);
                    remoteViews.removeAllViews(R.id.ongoing_activity_expand_secondary_text_container);
                    remoteViews.setViewVisibility(R.id.ongoing_activity_expand_secondary_text_container, 8);
                    remoteViews.addView(R.id.ongoing_activity_expand_secondary_container, remoteViews3);
                }
            }
        }
        return remoteViews;
    }

    public final RemoteViews makeIconButtons(ArrayList arrayList) {
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.ongoing_activity_icon_button_container);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            RemoteViews remoteViews2 = new RemoteViews(this.context.getPackageName(), R.layout.ongoing_activity_button);
            remoteViews2.setImageViewIcon(R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).getIcon());
            remoteViews2.setOnClickPendingIntent(R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).actionIntent);
            remoteViews2.setContentDescription(R.id.ongoing_action_button, ((Notification.Action) arrayList.get(i)).title);
            remoteViews.addView(R.id.ongoing_activity_expand_icon_buttons, remoteViews2);
        }
        return remoteViews;
    }

    public final RemoteViews makeTextButtonRemoteView(Notification.Action action, Context context, boolean z) {
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.ongoing_activity_text_button);
        remoteViews.setTextViewText(R.id.ongoing_action_text_button, action.title);
        remoteViews.setTextViewTextSize(R.id.ongoing_action_text_button, 0, upToLargeFontSize(context.getResources().getDimensionPixelSize(z ? R.dimen.ongoing_activity_sub_screen_card_item_sub_text_size : R.dimen.ongoing_activity_card_item_sub_text_size)));
        remoteViews.setOnClickPendingIntent(R.id.ongoing_action_text_button, action.actionIntent);
        return remoteViews;
    }

    public final void updateCollapsedViewFontSize(RemoteViews remoteViews) {
        float fUpToLargeFontSize = upToLargeFontSize(this.context.getResources().getDimensionPixelSize(R.dimen.oa_common_view_info_chip_size));
        remoteViews.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
        remoteViews.setViewLayoutWidth(R.id.ongoing_activity_secondary_info_icon, fUpToLargeFontSize, 0);
        remoteViews.setTextViewTextSize(android.R.id.title, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_main_text_size)));
        remoteViews.setTextViewTextSize(R.id.ongoing_activity_collapsed_secondary_text, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_sub_text_size)));
        remoteViews.setTextViewTextSize(android.R.id.beforeDescendants, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_header_app_label_text_size)));
        remoteViews.setTextViewTextSize(16909968, 0, upToLargeFontSize(this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_activity_card_item_header_time_text_size)));
    }

    public final void updateIconButtonRemoteView(ArrayList arrayList, RemoteViews remoteViews, boolean z) {
        RemoteViews remoteViewsMakeIconButtons = makeIconButtons(arrayList);
        remoteViews.removeAllViews(R.id.ongoing_activity_expand_icon_buttons_bottom);
        remoteViews.removeAllViews(R.id.ongoing_activity_expand_icon_buttons_end);
        if (!z) {
            remoteViews.addView(R.id.ongoing_activity_expand_icon_buttons_end, remoteViewsMakeIconButtons);
            remoteViews.setViewVisibility(R.id.ongoing_activity_expand_icon_buttons_end, 0);
            remoteViews.setViewVisibility(R.id.ongoing_activity_expand_icon_buttons_bottom, 8);
        } else {
            remoteViews.addView(R.id.ongoing_activity_expand_icon_buttons_bottom, remoteViewsMakeIconButtons);
            remoteViews.setViewVisibility(R.id.ongoing_activity_expand_icon_buttons_bottom, 0);
            remoteViews.setViewVisibility(R.id.ongoing_activity_expand_icon_buttons_end, 8);
            remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 3, 0);
            remoteViews.setViewLayoutMarginDimen(R.id.ongoing_activity_contents_vertical_margin_parent, 1, 0);
        }
    }

    public final void updateNotiExpandButtonView(OngoingActivityData ongoingActivityData, RemoteViews remoteViews, boolean z) {
        if (ongoingActivityData.mNotificationEntry.isPromotedState()) {
            return;
        }
        remoteViews.removeAllViews(R.id.ongoing_activity_notification_expand_button_end_container);
        remoteViews.removeAllViews(R.id.ongoing_activity_notification_expand_button_top_container);
        RemoteViews remoteViews2 = new RemoteViews(this.context.getPackageName(), R.layout.ongoing_activity_notification_expand_button);
        OngoingActivityLayoutUtil.INSTANCE.getClass();
        if (!OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData) || z) {
            remoteViews.addView(R.id.ongoing_activity_notification_expand_button_top_container, remoteViews2);
        } else {
            remoteViews.addView(R.id.ongoing_activity_notification_expand_button_end_container, remoteViews2);
        }
        remoteViews.setBoolean(android.R.id.flagRetrieveInteractiveWindows, "setExpanded", true);
        OngoingActivityLayoutUtil.setExpandButtonColor(remoteViews);
    }
}
