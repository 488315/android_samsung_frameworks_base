package com.android.settingslib.notification;

import android.R;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.policy.PhoneWindow;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EnableZenModeDialog {
    protected static final int COUNTDOWN_ALARM_CONDITION_INDEX = 2;
    protected static final int COUNTDOWN_CONDITION_INDEX = 1;
    public static final boolean DEBUG = Log.isLoggable("EnableZenModeDialog", 3);
    public static final int DEFAULT_BUCKET_INDEX;
    protected static final int FOREVER_CONDITION_INDEX = 0;
    public static final int MAX_BUCKET_MINUTES;
    public static final int[] MINUTE_BUCKETS;
    public static final int MIN_BUCKET_MINUTES;
    public final int MAX_MANUAL_DND_OPTIONS;
    public AlarmManager mAlarmManager;
    public int mBucketIndex;
    public final boolean mCancelIsNeutral;
    protected Context mContext;
    protected Uri mForeverId;
    protected LayoutInflater mLayoutInflater;
    public final ZenModeDialogMetricsLogger mMetricsLogger;
    protected NotificationManager mNotificationManager;
    public final int mThemeResId;
    public int mUserId;
    protected TextView mZenAlarmWarning;
    public RadioGroup mZenRadioGroup;
    protected LinearLayout mZenRadioGroupContent;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ConditionTag {
        public Condition condition;
        public TextView line1;
        public TextView line2;
        public View lines;

        /* renamed from: rb */
        public RadioButton f223rb;
    }

    /* renamed from: -$$Nest$monClickTimeButton, reason: not valid java name */
    public static void m367$$Nest$monClickTimeButton(EnableZenModeDialog enableZenModeDialog, View view, ConditionTag conditionTag, boolean z, int i) {
        Condition timeCondition;
        enableZenModeDialog.mMetricsLogger.logOnClickTimeButton(z);
        int[] iArr = MINUTE_BUCKETS;
        int length = iArr.length;
        int i2 = enableZenModeDialog.mBucketIndex;
        if (i2 == -1) {
            Condition condition = conditionTag.condition;
            timeCondition = null;
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(condition != null ? condition.id : null);
            long currentTimeMillis = System.currentTimeMillis();
            for (int i3 = 0; i3 < length; i3++) {
                int i4 = z ? i3 : (length - 1) - i3;
                int i5 = iArr[i4];
                long j = currentTimeMillis + (VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS * i5);
                if ((z && j > tryParseCountdownConditionId) || (!z && j < tryParseCountdownConditionId)) {
                    enableZenModeDialog.mBucketIndex = i4;
                    timeCondition = ZenModeConfig.toTimeCondition(enableZenModeDialog.mContext, j, i5, ActivityManager.getCurrentUser(), false);
                    break;
                }
            }
            if (timeCondition == null) {
                int i6 = DEFAULT_BUCKET_INDEX;
                enableZenModeDialog.mBucketIndex = i6;
                timeCondition = ZenModeConfig.toTimeCondition(enableZenModeDialog.mContext, iArr[i6], ActivityManager.getCurrentUser());
            }
        } else {
            int max = Math.max(0, Math.min(length - 1, i2 + (z ? 1 : -1)));
            enableZenModeDialog.mBucketIndex = max;
            timeCondition = ZenModeConfig.toTimeCondition(enableZenModeDialog.mContext, iArr[max], ActivityManager.getCurrentUser());
        }
        enableZenModeDialog.bind(timeCondition, view, i);
        String computeAlarmWarningText = enableZenModeDialog.computeAlarmWarningText(conditionTag.condition);
        enableZenModeDialog.mZenAlarmWarning.setText(computeAlarmWarningText);
        enableZenModeDialog.mZenAlarmWarning.setVisibility(computeAlarmWarningText == null ? 8 : 0);
        conditionTag.f223rb.setChecked(true);
    }

    static {
        int[] iArr = ZenModeConfig.MINUTE_BUCKETS;
        MINUTE_BUCKETS = iArr;
        MIN_BUCKET_MINUTES = iArr[0];
        MAX_BUCKET_MINUTES = iArr[iArr.length - 1];
        DEFAULT_BUCKET_INDEX = Arrays.binarySearch(iArr, 60);
    }

    public EnableZenModeDialog(Context context) {
        this(context, 0);
    }

    public void bind(Condition condition, final View view, final int i) {
        if (condition == null) {
            throw new IllegalArgumentException("condition must not be null");
        }
        boolean z = condition.state == 1;
        final ConditionTag conditionTag = view.getTag() != null ? (ConditionTag) view.getTag() : new ConditionTag();
        view.setTag(conditionTag);
        RadioButton radioButton = conditionTag.f223rb;
        boolean z2 = radioButton == null;
        if (radioButton == null) {
            conditionTag.f223rb = (RadioButton) this.mZenRadioGroup.getChildAt(i);
        }
        conditionTag.condition = condition;
        final Uri uri = condition.id;
        if (DEBUG) {
            Log.d("EnableZenModeDialog", "bind i=" + this.mZenRadioGroupContent.indexOfChild(view) + " first=" + z2 + " condition=" + uri);
        }
        conditionTag.f223rb.setEnabled(z);
        conditionTag.f223rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.settingslib.notification.EnableZenModeDialog.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                if (z3) {
                    conditionTag.f223rb.setChecked(true);
                    if (EnableZenModeDialog.DEBUG) {
                        Log.d("EnableZenModeDialog", "onCheckedChanged " + uri);
                    }
                    EnableZenModeDialog.this.mMetricsLogger.logOnConditionSelected();
                    EnableZenModeDialog enableZenModeDialog = EnableZenModeDialog.this;
                    String computeAlarmWarningText = enableZenModeDialog.computeAlarmWarningText(conditionTag.condition);
                    enableZenModeDialog.mZenAlarmWarning.setText(computeAlarmWarningText);
                    enableZenModeDialog.mZenAlarmWarning.setVisibility(computeAlarmWarningText == null ? 8 : 0);
                }
                conditionTag.line1.setStateDescription(z3 ? compoundButton.getContext().getString(17042601) : null);
            }
        });
        if (conditionTag.lines == null) {
            conditionTag.lines = view.findViewById(R.id.content);
        }
        if (conditionTag.line1 == null) {
            conditionTag.line1 = (TextView) view.findViewById(R.id.text1);
        }
        if (conditionTag.line2 == null) {
            conditionTag.line2 = (TextView) view.findViewById(R.id.text2);
        }
        String str = !TextUtils.isEmpty(condition.line1) ? condition.line1 : condition.summary;
        String str2 = condition.line2;
        conditionTag.line1.setText(str);
        if (TextUtils.isEmpty(str2)) {
            conditionTag.line2.setVisibility(8);
        } else {
            conditionTag.line2.setVisibility(0);
            conditionTag.line2.setText(str2);
        }
        conditionTag.lines.setEnabled(z);
        conditionTag.lines.setAlpha(z ? 1.0f : 0.4f);
        conditionTag.lines.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.notification.EnableZenModeDialog.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                conditionTag.f223rb.setChecked(true);
            }
        });
        long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(uri);
        ImageView imageView = (ImageView) view.findViewById(R.id.button1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.button2);
        View findViewById = view.findViewById(com.android.systemui.R.id.divider_view);
        if (i != 1 || tryParseCountdownConditionId <= 0) {
            if (imageView != null) {
                ((ViewGroup) view).removeView(imageView);
            }
            if (imageView2 != null) {
                ((ViewGroup) view).removeView(imageView2);
            }
        } else {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.notification.EnableZenModeDialog.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EnableZenModeDialog.m367$$Nest$monClickTimeButton(EnableZenModeDialog.this, view, conditionTag, false, i);
                    conditionTag.lines.setAccessibilityLiveRegion(1);
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.notification.EnableZenModeDialog.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    EnableZenModeDialog.m367$$Nest$monClickTimeButton(EnableZenModeDialog.this, view, conditionTag, true, i);
                    conditionTag.lines.setAccessibilityLiveRegion(1);
                }
            });
            int i2 = this.mBucketIndex;
            if (i2 > -1) {
                imageView.setEnabled(i2 > 0);
                imageView2.setEnabled(this.mBucketIndex < MINUTE_BUCKETS.length - 1);
            } else {
                imageView.setEnabled(tryParseCountdownConditionId - System.currentTimeMillis() > ((long) (MIN_BUCKET_MINUTES * VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS)));
                imageView2.setEnabled(!Objects.equals(condition.summary, ZenModeConfig.toTimeCondition(this.mContext, MAX_BUCKET_MINUTES, ActivityManager.getCurrentUser()).summary));
            }
            imageView.setAlpha(imageView.isEnabled() ? 1.0f : 0.5f);
            imageView2.setAlpha(imageView2.isEnabled() ? 1.0f : 0.5f);
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
        }
        view.setVisibility(0);
    }

    public void bindConditions(Condition condition) {
        bind(forever(), this.mZenRadioGroupContent.getChildAt(0), 0);
        if (condition == null) {
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
            return;
        }
        if (isForever(condition)) {
            getConditionTagAt(0).f223rb.setChecked(true);
            bindGenericCountdown();
            bindNextAlarm(getTimeUntilNextAlarmCondition());
        } else if (isAlarm(condition)) {
            bindGenericCountdown();
            bindNextAlarm(condition);
            getConditionTagAt(2).f223rb.setChecked(true);
        } else if (isCountdown(condition)) {
            bindNextAlarm(getTimeUntilNextAlarmCondition());
            bind(condition, this.mZenRadioGroupContent.getChildAt(1), 1);
            getConditionTagAt(1).f223rb.setChecked(true);
        } else {
            Slog.d("EnableZenModeDialog", "Invalid manual condition: " + condition);
        }
    }

    public void bindGenericCountdown() {
        int i = DEFAULT_BUCKET_INDEX;
        this.mBucketIndex = i;
        bind(ZenModeConfig.toTimeCondition(this.mContext, MINUTE_BUCKETS[i], ActivityManager.getCurrentUser()), this.mZenRadioGroupContent.getChildAt(1), 1);
    }

    public void bindNextAlarm(Condition condition) {
        View childAt = this.mZenRadioGroupContent.getChildAt(2);
        if (condition != null) {
            bind(condition, childAt, 2);
        }
        ConditionTag conditionTag = (ConditionTag) childAt.getTag();
        boolean z = (conditionTag == null || conditionTag.condition == null) ? false : true;
        this.mZenRadioGroup.getChildAt(2).setVisibility(z ? 0 : 8);
        childAt.setVisibility(z ? 0 : 8);
    }

    public String computeAlarmWarningText(Condition condition) {
        int i = 0;
        if ((this.mNotificationManager.getNotificationPolicy().priorityCategories & 32) != 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(this.mUserId);
        long triggerTime = nextAlarmClock != null ? nextAlarmClock.getTriggerTime() : 0L;
        if (triggerTime < currentTimeMillis) {
            return null;
        }
        if (condition == null || isForever(condition)) {
            i = com.android.systemui.R.string.zen_alarm_warning_indef;
        } else {
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(condition.id);
            if (tryParseCountdownConditionId > currentTimeMillis && triggerTime < tryParseCountdownConditionId) {
                i = com.android.systemui.R.string.zen_alarm_warning;
            }
        }
        if (i == 0) {
            return null;
        }
        return this.mContext.getResources().getString(i, getTime(triggerTime, currentTimeMillis));
    }

    public final AlertDialog createDialog() {
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        this.mForeverId = Condition.newId(this.mContext).appendPath("forever").build();
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        this.mUserId = this.mContext.getUserId();
        AlertDialog.Builder positiveButton = new AlertDialog.Builder(this.mContext, this.mThemeResId).setTitle(com.android.systemui.R.string.zen_mode_settings_turn_on_dialog_title).setPositiveButton(com.android.systemui.R.string.zen_mode_enable_dialog_turn_on, new DialogInterface.OnClickListener() { // from class: com.android.settingslib.notification.EnableZenModeDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ConditionTag conditionTagAt = EnableZenModeDialog.this.getConditionTagAt(EnableZenModeDialog.this.mZenRadioGroup.getCheckedRadioButtonId());
                if (EnableZenModeDialog.this.isForever(conditionTagAt.condition)) {
                    EnableZenModeDialog.this.mMetricsLogger.logOnEnableZenModeForever();
                } else if (EnableZenModeDialog.this.isAlarm(conditionTagAt.condition)) {
                    EnableZenModeDialog.this.mMetricsLogger.logOnEnableZenModeUntilAlarm();
                } else if (EnableZenModeDialog.this.isCountdown(conditionTagAt.condition)) {
                    EnableZenModeDialog.this.mMetricsLogger.logOnEnableZenModeUntilCountdown();
                } else {
                    Slog.d("EnableZenModeDialog", "Invalid manual condition: " + conditionTagAt.condition);
                }
                EnableZenModeDialog enableZenModeDialog = EnableZenModeDialog.this;
                NotificationManager notificationManager = enableZenModeDialog.mNotificationManager;
                Condition condition = conditionTagAt.condition;
                Uri uri = null;
                if (!enableZenModeDialog.isForever(condition) && condition != null) {
                    uri = condition.id;
                }
                notificationManager.setZenMode(1, uri, "EnableZenModeDialog");
            }
        });
        if (this.mCancelIsNeutral) {
            positiveButton.setNeutralButton(com.android.systemui.R.string.cancel, (DialogInterface.OnClickListener) null);
        } else {
            positiveButton.setNegativeButton(com.android.systemui.R.string.cancel, (DialogInterface.OnClickListener) null);
        }
        if (this.mLayoutInflater == null) {
            this.mLayoutInflater = new PhoneWindow(this.mContext).getLayoutInflater();
        }
        View inflate = this.mLayoutInflater.inflate(com.android.systemui.R.layout.zen_mode_turn_on_dialog_container, (ViewGroup) null);
        ScrollView scrollView = (ScrollView) inflate.findViewById(com.android.systemui.R.id.container);
        this.mZenRadioGroup = (RadioGroup) scrollView.findViewById(com.android.systemui.R.id.zen_radio_buttons);
        this.mZenRadioGroupContent = (LinearLayout) scrollView.findViewById(com.android.systemui.R.id.zen_radio_buttons_content);
        this.mZenAlarmWarning = (TextView) scrollView.findViewById(com.android.systemui.R.id.zen_alarm_warning);
        int i = 0;
        while (true) {
            int i2 = this.MAX_MANUAL_DND_OPTIONS;
            if (i >= i2) {
                break;
            }
            View inflate2 = this.mLayoutInflater.inflate(com.android.systemui.R.layout.zen_mode_radio_button, (ViewGroup) this.mZenRadioGroup, false);
            this.mZenRadioGroup.addView(inflate2);
            inflate2.setId(i);
            View inflate3 = this.mLayoutInflater.inflate(com.android.systemui.R.layout.zen_mode_condition, (ViewGroup) this.mZenRadioGroupContent, false);
            inflate3.setId(i2 + i);
            this.mZenRadioGroupContent.addView(inflate3);
            i++;
        }
        int childCount = this.mZenRadioGroupContent.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            this.mZenRadioGroupContent.getChildAt(i3).setVisibility(8);
        }
        this.mZenAlarmWarning.setVisibility(8);
        bindConditions(forever());
        positiveButton.setView(inflate);
        return positiveButton.create();
    }

    public final Condition forever() {
        return new Condition(Condition.newId(this.mContext).appendPath("forever").build(), this.mContext.getString(17043441), "", "", 0, 1, 0);
    }

    public ConditionTag getConditionTagAt(int i) {
        return (ConditionTag) this.mZenRadioGroupContent.getChildAt(i).getTag();
    }

    public String getTime(long j, long j2) {
        boolean z = j - j2 < 86400000;
        boolean is24HourFormat = DateFormat.is24HourFormat(this.mContext, ActivityManager.getCurrentUser());
        return this.mContext.getResources().getString(z ? com.android.systemui.R.string.alarm_template : com.android.systemui.R.string.alarm_template_far, DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), z ? is24HourFormat ? "Hm" : "hma" : is24HourFormat ? "EEEHm" : "EEEhma"), j));
    }

    public Condition getTimeUntilNextAlarmCondition() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(11, 0);
        gregorianCalendar.set(12, 0);
        gregorianCalendar.set(13, 0);
        gregorianCalendar.set(14, 0);
        gregorianCalendar.add(5, 6);
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(this.mUserId);
        long triggerTime = nextAlarmClock != null ? nextAlarmClock.getTriggerTime() : 0L;
        if (triggerTime <= 0) {
            return null;
        }
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(triggerTime);
        gregorianCalendar2.set(11, 0);
        gregorianCalendar2.set(12, 0);
        gregorianCalendar2.set(13, 0);
        gregorianCalendar2.set(14, 0);
        if (gregorianCalendar.compareTo((Calendar) gregorianCalendar2) >= 0) {
            return ZenModeConfig.toNextAlarmCondition(this.mContext, triggerTime, ActivityManager.getCurrentUser());
        }
        return null;
    }

    public boolean isAlarm(Condition condition) {
        return condition != null && ZenModeConfig.isValidCountdownToAlarmConditionId(condition.id);
    }

    public boolean isCountdown(Condition condition) {
        return condition != null && ZenModeConfig.isValidCountdownConditionId(condition.id);
    }

    public final boolean isForever(Condition condition) {
        return condition != null && this.mForeverId.equals(condition.id);
    }

    public EnableZenModeDialog(Context context, int i) {
        this(context, i, false, new ZenModeDialogMetricsLogger(context));
    }

    public EnableZenModeDialog(Context context, int i, boolean z, ZenModeDialogMetricsLogger zenModeDialogMetricsLogger) {
        this.mBucketIndex = -1;
        this.MAX_MANUAL_DND_OPTIONS = 3;
        this.mContext = context;
        this.mThemeResId = i;
        this.mCancelIsNeutral = z;
        this.mMetricsLogger = zenModeDialogMetricsLogger;
    }
}
