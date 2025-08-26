package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import android.metrics.LogMaker;
import android.provider.Settings;
import android.service.notification.SnoozeCriterion;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.KeyValueListParser;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RadioButton;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.util.PluralMessageFormaterKt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SnoozeOptionManager {
    public static final int[] sAccessibilityActions = {R.id.action_snooze_shorter, R.id.action_snooze_short, R.id.action_snooze_long, R.id.action_snooze_longer};
    public final Context mContext;
    public NotificationSnoozeOption mDefaultOption;
    public SecNotificationSnooze mParent;
    public NotificationSwipeActionHelper.SnoozeOption mSelectedOption;
    public NotificationSwipeActionHelper mSnoozeListener;
    public ViewGroup mSnoozeOptionContainer;
    public List mSnoozeOptions;
    public boolean mSnoozing;
    public final MetricsLogger mMetricsLogger = new MetricsLogger();
    public final KeyValueListParser mParser = new KeyValueListParser(',');

    public class NotificationSnoozeOption implements NotificationSwipeActionHelper.SnoozeOption {
        public final AccessibilityNodeInfo.AccessibilityAction mAction;
        public final CharSequence mConfirmation;
        public final SnoozeCriterion mCriterion;
        public final CharSequence mDescription;
        public final int mMinutesToSnoozeFor;

        public NotificationSnoozeOption(SnoozeOptionManager snoozeOptionManager, SnoozeCriterion snoozeCriterion, int i, CharSequence charSequence, CharSequence charSequence2, AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
            this.mCriterion = snoozeCriterion;
            this.mMinutesToSnoozeFor = i;
            this.mDescription = charSequence;
            this.mConfirmation = charSequence2;
            this.mAction = accessibilityAction;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper.SnoozeOption
        public final AccessibilityNodeInfo.AccessibilityAction getAccessibilityAction() {
            return this.mAction;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper.SnoozeOption
        public final CharSequence getConfirmation() {
            return this.mConfirmation;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper.SnoozeOption
        public final CharSequence getDescription() {
            return this.mDescription;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper.SnoozeOption
        public final int getMinutesToSnoozeFor() {
            return this.mMinutesToSnoozeFor;
        }

        @Override // com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper.SnoozeOption
        public final SnoozeCriterion getSnoozeCriterion() {
            return this.mCriterion;
        }
    }

    public SnoozeOptionManager(Context context) {
        this.mContext = context;
    }

    public final ArrayList getDefaultSnoozeOptions() {
        SnoozeOptionManager snoozeOptionManager;
        NotificationSnoozeOption notificationSnoozeOption;
        Resources resources = this.mContext.getResources();
        ArrayList arrayList = new ArrayList();
        try {
            this.mParser.setString(Settings.Global.getString(this.mContext.getContentResolver(), "notification_snooze_options"));
        } catch (IllegalArgumentException unused) {
            Log.e("NotificationSnooze", "Bad snooze constants");
        }
        int i = this.mParser.getInt("default", resources.getInteger(R.integer.config_notification_snooze_time_default));
        int[] intArray = this.mParser.getIntArray("options_array", resources.getIntArray(R.array.config_notification_snooze_times));
        int iMin = Math.min(intArray.length, 4);
        int i2 = 0;
        while (i2 < iMin) {
            int i3 = intArray[i2];
            int i4 = sAccessibilityActions[i2];
            Resources resources2 = this.mContext.getResources();
            boolean z = i3 >= 60;
            String strIcuMessageFormat = PluralMessageFormaterKt.icuMessageFormat(this.mContext.getResources(), z ? R.string.snoozeHourOptions : R.string.snoozeMinuteOptions, z ? i3 / 60 : i3);
            String str = String.format(resources2.getString(R.string.snoozed_for_time), strIcuMessageFormat);
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(i4, strIcuMessageFormat);
            int iIndexOf = str.indexOf(strIcuMessageFormat);
            if (iIndexOf == -1) {
                snoozeOptionManager = this;
                notificationSnoozeOption = new NotificationSnoozeOption(snoozeOptionManager, null, i3, strIcuMessageFormat, str, accessibilityAction);
            } else {
                snoozeOptionManager = this;
                SpannableString spannableString = new SpannableString(str);
                spannableString.setSpan(new StyleSpan(1), iIndexOf, strIcuMessageFormat.length() + iIndexOf, 0);
                notificationSnoozeOption = new NotificationSnoozeOption(snoozeOptionManager, null, i3, strIcuMessageFormat, spannableString, accessibilityAction);
            }
            if (i2 == 0 || i3 == i) {
                snoozeOptionManager.mDefaultOption = notificationSnoozeOption;
            }
            arrayList.add(notificationSnoozeOption);
            i2++;
            this = snoozeOptionManager;
        }
        return arrayList;
    }

    public final void logOptionSelection(int i, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        this.mMetricsLogger.write(new LogMaker(i).setType(4).addTaggedData(1140, Integer.valueOf(((ArrayList) this.mSnoozeOptions).indexOf(snoozeOption))).addTaggedData(1139, Long.valueOf(TimeUnit.MINUTES.toMillis(snoozeOption.getMinutesToSnoozeFor()))));
    }

    public final void setSelected(NotificationSwipeActionHelper.SnoozeOption snoozeOption, boolean z) {
        this.mSelectedOption = snoozeOption;
        if (z) {
            logOptionSelection(1138, snoozeOption);
        }
        if (this.mSnoozeOptions == null || this.mSnoozeOptionContainer == null || this.mSnoozing) {
            return;
        }
        for (int i = 0; i < ((ArrayList) this.mSnoozeOptions).size(); i++) {
            RadioButton radioButton = (RadioButton) this.mSnoozeOptionContainer.getChildAt(i);
            if (radioButton.getTag().equals(this.mSelectedOption)) {
                radioButton.setChecked(true);
            }
        }
    }
}
