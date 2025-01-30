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
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RadioButton;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.row.SnoozeOptionManager;
import com.android.systemui.util.PluralMessageFormaterKt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SnoozeOptionManager {
    public static final int[] sAccessibilityActions = {R.id.action_snooze_shorter, R.id.action_snooze_short, R.id.action_snooze_long, R.id.action_snooze_longer};
    public final Context mContext;
    public NotificationSnoozeOption mDefaultOption;
    public ViewGroup mParent;
    public NotificationSwipeActionHelper.SnoozeOption mSelectedOption;
    public NotificationSwipeActionHelper mSnoozeListener;
    public ViewGroup mSnoozeOptionContainer;
    public List mSnoozeOptions;
    public boolean mSnoozing;
    public final MetricsLogger mMetricsLogger = new MetricsLogger();
    public final KeyValueListParser mParser = new KeyValueListParser(',');

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationSnoozeOption implements NotificationSwipeActionHelper.SnoozeOption {
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

    public final void createOptionViews() {
        this.mSnoozeOptionContainer.removeAllViews();
        final LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.mSnoozeOptions.stream().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.SnoozeOptionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SnoozeOptionManager snoozeOptionManager = SnoozeOptionManager.this;
                NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) obj;
                RadioButton radioButton = (RadioButton) layoutInflater.inflate(R.layout.sec_notification_snooze_option, snoozeOptionManager.mSnoozeOptionContainer, false);
                snoozeOptionManager.mSnoozeOptionContainer.addView(radioButton);
                radioButton.setText(snoozeOption.getDescription());
                radioButton.setTag(snoozeOption);
                radioButton.setOnClickListener((SecNotificationSnooze) snoozeOptionManager.mParent);
                SnoozeOptionManager.NotificationSnoozeOption notificationSnoozeOption = snoozeOptionManager.mDefaultOption;
                if (notificationSnoozeOption == null || !snoozeOption.equals(notificationSnoozeOption)) {
                    return;
                }
                radioButton.setChecked(true);
            }
        });
    }

    public final ArrayList getDefaultSnoozeOptions() {
        NotificationSnoozeOption notificationSnoozeOption;
        KeyValueListParser keyValueListParser = this.mParser;
        Context context = this.mContext;
        Resources resources = context.getResources();
        ArrayList arrayList = new ArrayList();
        try {
            keyValueListParser.setString(Settings.Global.getString(context.getContentResolver(), "notification_snooze_options"));
        } catch (IllegalArgumentException unused) {
            Log.e("NotificationSnooze", "Bad snooze constants");
        }
        int i = keyValueListParser.getInt("default", resources.getInteger(R.integer.config_notification_snooze_time_default));
        int[] intArray = keyValueListParser.getIntArray("options_array", resources.getIntArray(R.array.config_notification_snooze_times));
        int min = Math.min(intArray.length, 4);
        for (int i2 = 0; i2 < min; i2++) {
            int i3 = intArray[i2];
            int i4 = sAccessibilityActions[i2];
            Resources resources2 = context.getResources();
            boolean z = i3 >= 60;
            String icuMessageFormat = PluralMessageFormaterKt.icuMessageFormat(context.getResources(), z ? R.string.snoozeHourOptions : R.string.snoozeMinuteOptions, z ? i3 / 60 : i3);
            String format = String.format(resources2.getString(R.string.snoozed_for_time), icuMessageFormat);
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(i4, icuMessageFormat);
            int indexOf = format.indexOf(icuMessageFormat);
            if (indexOf == -1) {
                notificationSnoozeOption = new NotificationSnoozeOption(this, null, i3, icuMessageFormat, format, accessibilityAction);
            } else {
                SpannableString spannableString = new SpannableString(format);
                spannableString.setSpan(new StyleSpan(1), indexOf, icuMessageFormat.length() + indexOf, 0);
                notificationSnoozeOption = new NotificationSnoozeOption(this, null, i3, icuMessageFormat, spannableString, accessibilityAction);
            }
            if (i2 == 0 || i3 == i) {
                this.mDefaultOption = notificationSnoozeOption;
            }
            arrayList.add(notificationSnoozeOption);
        }
        return arrayList;
    }

    public final void logOptionSelection(int i, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        this.mMetricsLogger.write(new LogMaker(i).setType(4).addTaggedData(1140, Integer.valueOf(this.mSnoozeOptions.indexOf(snoozeOption))).addTaggedData(1139, Long.valueOf(TimeUnit.MINUTES.toMillis(snoozeOption.getMinutesToSnoozeFor()))));
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
