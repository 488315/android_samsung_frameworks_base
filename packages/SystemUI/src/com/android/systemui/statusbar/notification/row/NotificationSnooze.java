package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.util.PluralMessageFormaterKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class NotificationSnooze extends LinearLayout implements NotificationGuts.GutsContent, View.OnClickListener {
    public int mCollapsedHeight;
    public NotificationSnoozeOption mDefaultOption;
    public View mDivider;
    public AnimatorSet mExpandAnimation;
    public ImageView mExpandButton;
    public boolean mExpanded;
    public NotificationGuts mGutsContainer;
    public final MetricsLogger mMetricsLogger;
    public KeyValueListParser mParser;
    public StatusBarNotification mSbn;
    public NotificationSwipeActionHelper.SnoozeOption mSelectedOption;
    public TextView mSelectedOptionText;
    public NotificationSwipeActionHelper mSnoozeListener;
    public ViewGroup mSnoozeOptionContainer;
    public List<NotificationSwipeActionHelper.SnoozeOption> mSnoozeOptions;
    public View mSnoozeView;
    public boolean mSnoozing;
    public TextView mUndoButton;
    public static final LogMaker OPTIONS_OPEN_LOG = new LogMaker(1142).setType(1);
    public static final LogMaker OPTIONS_CLOSE_LOG = new LogMaker(1142).setType(2);
    public static final LogMaker UNDO_LOG = new LogMaker(1141).setType(4);
    public static final int[] sAccessibilityActions = {R.id.action_snooze_shorter, R.id.action_snooze_short, R.id.action_snooze_long, R.id.action_snooze_longer};

    public class NotificationSnoozeOption implements NotificationSwipeActionHelper.SnoozeOption {
        public final AccessibilityNodeInfo.AccessibilityAction mAction;
        public final CharSequence mConfirmation;
        public final SnoozeCriterion mCriterion;
        public final CharSequence mDescription;
        public final int mMinutesToSnoozeFor;

        public NotificationSnoozeOption(NotificationSnooze notificationSnooze, SnoozeCriterion snoozeCriterion, int i, CharSequence charSequence, CharSequence charSequence2, AccessibilityNodeInfo.AccessibilityAction accessibilityAction) {
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

    public NotificationSnooze(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMetricsLogger = new MetricsLogger();
        this.mParser = new KeyValueListParser(',');
    }

    public final void createOptionViews() {
        this.mSnoozeOptionContainer.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        for (int i = 0; i < this.mSnoozeOptions.size(); i++) {
            NotificationSwipeActionHelper.SnoozeOption snoozeOption = this.mSnoozeOptions.get(i);
            TextView textView = (TextView) layoutInflater.inflate(R.layout.notification_snooze_option, this.mSnoozeOptionContainer, false);
            this.mSnoozeOptionContainer.addView(textView);
            textView.setText(snoozeOption.getDescription());
            textView.setTag(snoozeOption);
            textView.setOnClickListener(this);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return this.mExpanded ? getHeight() : this.mCollapsedHeight;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        setSelected(this.mDefaultOption, false);
        showSnoozeOptions(false);
        return this;
    }

    public NotificationSwipeActionHelper.SnoozeOption getDefaultOption() {
        return this.mDefaultOption;
    }

    public ArrayList<NotificationSwipeActionHelper.SnoozeOption> getDefaultSnoozeOptions() {
        NotificationSnoozeOption notificationSnoozeOption;
        NotificationSnooze notificationSnooze;
        Resources resources = getContext().getResources();
        ArrayList<NotificationSwipeActionHelper.SnoozeOption> arrayList = new ArrayList<>();
        try {
            this.mParser.setString(Settings.Global.getString(getContext().getContentResolver(), "notification_snooze_options"));
        } catch (IllegalArgumentException unused) {
            Log.e("NotificationSnooze", "Bad snooze constants");
        }
        int i = this.mParser.getInt("default", resources.getInteger(R.integer.config_notification_snooze_time_default));
        int[] intArray = this.mParser.getIntArray("options_array", resources.getIntArray(R.array.config_notification_snooze_times));
        int i2 = 0;
        while (i2 < intArray.length) {
            int[] iArr = sAccessibilityActions;
            if (i2 >= iArr.length) {
                break;
            }
            int i3 = intArray[i2];
            int i4 = iArr[i2];
            Resources resources2 = this.getResources();
            boolean z = i3 >= 60;
            String strIcuMessageFormat = PluralMessageFormaterKt.icuMessageFormat(resources2, z ? R.string.snoozeHourOptions : R.string.snoozeMinuteOptions, z ? i3 / 60 : i3);
            String str = String.format(resources2.getString(R.string.snoozed_for_time), strIcuMessageFormat);
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(i4, strIcuMessageFormat);
            int iIndexOf = str.indexOf(strIcuMessageFormat);
            if (iIndexOf == -1) {
                notificationSnooze = this;
                notificationSnoozeOption = new NotificationSnoozeOption(notificationSnooze, null, i3, strIcuMessageFormat, str, accessibilityAction);
            } else {
                SpannableString spannableString = new SpannableString(str);
                spannableString.setSpan(new StyleSpan(1, resources2.getConfiguration().fontWeightAdjustment), iIndexOf, strIcuMessageFormat.length() + iIndexOf, 0);
                notificationSnooze = this;
                notificationSnoozeOption = new NotificationSnoozeOption(notificationSnooze, null, i3, strIcuMessageFormat, spannableString, accessibilityAction);
            }
            if (i2 == 0 || i3 == i) {
                notificationSnooze.mDefaultOption = notificationSnoozeOption;
            }
            arrayList.add(notificationSnoozeOption);
            i2++;
            this = notificationSnooze;
        }
        return arrayList;
    }

    public final String getExpandActionString() {
        return ((LinearLayout) this).mContext.getString(this.mExpanded ? android.R.string.keyguard_password_entry_touch_hint : android.R.string.keyguard_password_enter_puk_prompt);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        NotificationSwipeActionHelper.SnoozeOption snoozeOption;
        if (!z) {
            this.mSelectedOption = null;
            showSnoozeOptions(false);
            return false;
        }
        if (this.mExpanded && !z2) {
            showSnoozeOptions(false);
            return true;
        }
        NotificationSwipeActionHelper notificationSwipeActionHelper = this.mSnoozeListener;
        if (notificationSwipeActionHelper == null || (snoozeOption = this.mSelectedOption) == null) {
            setSelected(this.mSnoozeOptions.get(0), false);
            return false;
        }
        this.mSnoozing = true;
        notificationSwipeActionHelper.snooze(this.mSbn, snoozeOption);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean isLeavebehind() {
        return true;
    }

    public final void logOptionSelection(int i, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        this.mMetricsLogger.write(new LogMaker(i).setType(4).addTaggedData(1140, Integer.valueOf(this.mSnoozeOptions.indexOf(snoozeOption))).addTaggedData(1139, Long.valueOf(TimeUnit.MINUTES.toMillis(snoozeOption.getMinutesToSnoozeFor()))));
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        logOptionSelection(1137, this.mDefaultOption);
        dispatchConfigurationChanged(getResources().getConfiguration());
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        NotificationGuts notificationGuts = this.mGutsContainer;
        if (notificationGuts != null) {
            notificationGuts.resetFalsingCheck();
        }
        int id = view.getId();
        NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) view.getTag();
        if (snoozeOption != null) {
            setSelected(snoozeOption, true);
            return;
        }
        if (id == R.id.notification_snooze) {
            showSnoozeOptions(!this.mExpanded);
            this.mSnoozeView.sendAccessibilityEvent(8);
            this.mMetricsLogger.write(!this.mExpanded ? OPTIONS_OPEN_LOG : OPTIONS_CLOSE_LOG);
        } else {
            this.mSelectedOption = null;
            showSnoozeOptions(false);
            this.mGutsContainer.closeControls(view, false);
            this.mMetricsLogger.write(UNDO_LOG);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mCollapsedHeight = getResources().getDimensionPixelSize(R.dimen.snooze_snackbar_min_height);
        View viewFindViewById = findViewById(R.id.notification_snooze);
        this.mSnoozeView = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        this.mSelectedOptionText = (TextView) findViewById(R.id.snooze_option_default);
        TextView textView = (TextView) findViewById(R.id.undo);
        this.mUndoButton = textView;
        textView.setOnClickListener(this);
        this.mUndoButton.setContentDescription(getContext().getString(R.string.snooze_undo_content_description));
        this.mExpandButton = (ImageView) findViewById(R.id.expand_button);
        View viewFindViewById2 = findViewById(R.id.divider);
        this.mDivider = viewFindViewById2;
        viewFindViewById2.setAlpha(0.0f);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.snooze_options);
        this.mSnoozeOptionContainer = viewGroup;
        viewGroup.setVisibility(4);
        this.mSnoozeOptionContainer.setAlpha(0.0f);
        this.mSnoozeOptions = getDefaultSnoozeOptions();
        createOptionViews();
        setSelected(this.mDefaultOption, false);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze_undo, getResources().getString(R.string.snooze_undo)));
        int size = this.mSnoozeOptions.size();
        for (int i = 0; i < size; i++) {
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = this.mSnoozeOptions.get(i).getAccessibilityAction();
            if (accessibilityAction != null) {
                accessibilityNodeInfo.addAction(accessibilityAction);
            }
        }
        this.mSnoozeView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.notification.row.NotificationSnooze.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo2) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo2);
                NotificationSnooze notificationSnooze = NotificationSnooze.this;
                LogMaker logMaker = NotificationSnooze.OPTIONS_OPEN_LOG;
                accessibilityNodeInfo2.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, notificationSnooze.getExpandActionString()));
            }
        });
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i == R.id.action_snooze_undo) {
            TextView textView = this.mUndoButton;
            this.mSelectedOption = null;
            showSnoozeOptions(false);
            this.mGutsContainer.closeControls(textView, false);
            return true;
        }
        for (int i2 = 0; i2 < this.mSnoozeOptions.size(); i2++) {
            NotificationSwipeActionHelper.SnoozeOption snoozeOption = this.mSnoozeOptions.get(i2);
            if (snoozeOption.getAccessibilityAction() != null && snoozeOption.getAccessibilityAction().getId() == i) {
                setSelected(snoozeOption, true);
                this.mSnoozeView.sendAccessibilityEvent(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                return true;
            }
        }
        return false;
    }

    public final boolean requestAccessibilityFocus() {
        if (this.mExpanded) {
            return super.requestAccessibilityFocus();
        }
        this.mSnoozeView.requestAccessibilityFocus();
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    public void setKeyValueListParser(KeyValueListParser keyValueListParser) {
        this.mParser = keyValueListParser;
    }

    public void setSelected(NotificationSwipeActionHelper.SnoozeOption snoozeOption, boolean z) {
        if (snoozeOption != this.mSelectedOption) {
            this.mSelectedOption = snoozeOption;
            this.mSelectedOptionText.setText(snoozeOption.getConfirmation());
            updateContentDescription();
        }
        showSnoozeOptions(false);
        int childCount = this.mSnoozeOptionContainer.getChildCount();
        int i = 0;
        while (true) {
            int i2 = 8;
            if (i >= childCount) {
                break;
            }
            View childAt = this.mSnoozeOptionContainer.getChildAt(i);
            if (childAt.getTag() != this.mSelectedOption) {
                i2 = 0;
            }
            childAt.setVisibility(i2);
            i++;
        }
        if (z) {
            this.mSnoozeView.sendAccessibilityEvent(8);
            logOptionSelection(1138, snoozeOption);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return true;
    }

    public void showSnoozeOptions(final boolean z) {
        NotificationGuts.OnHeightChangedListener onHeightChangedListener;
        this.mExpandButton.setImageResource(z ? android.R.drawable.ic_jog_dial_decline : android.R.drawable.ic_maps_indicator_current_position_anim);
        this.mExpandButton.setContentDescription(getExpandActionString());
        if (this.mExpanded != z) {
            this.mExpanded = z;
            updateContentDescription();
            AnimatorSet animatorSet = this.mExpandAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            View view = this.mDivider;
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), z ? 1.0f : 0.0f);
            ViewGroup viewGroup = this.mSnoozeOptionContainer;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, viewGroup.getAlpha(), z ? 1.0f : 0.0f);
            this.mSnoozeOptionContainer.setVisibility(0);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.mExpandAnimation = animatorSet2;
            animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
            this.mExpandAnimation.setDuration(150L);
            this.mExpandAnimation.setInterpolator(z ? Interpolators.ALPHA_IN : Interpolators.ALPHA_OUT);
            this.mExpandAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.NotificationSnooze.2
                public boolean cancelled = false;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (z || this.cancelled) {
                        return;
                    }
                    NotificationSnooze.this.mSnoozeOptionContainer.setVisibility(4);
                    NotificationSnooze.this.mSnoozeOptionContainer.setAlpha(0.0f);
                }
            });
            this.mExpandAnimation.start();
            NotificationGuts notificationGuts = this.mGutsContainer;
            if (notificationGuts == null || (onHeightChangedListener = notificationGuts.mHeightListener) == null) {
                return;
            }
            onHeightChangedListener.onHeightChanged();
        }
    }

    public final void updateContentDescription() {
        View view = this.mSnoozeView;
        StringBuilder sb = new StringBuilder();
        sb.append(((LinearLayout) this).mContext.getString(this.mExpanded ? android.R.string.fingerprint_error_power_pressed : android.R.string.fingerprint_error_not_match));
        sb.append("\u2029");
        sb.append((Object) this.mSelectedOptionText.getText());
        view.setContentDescription(sb.toString());
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return this.mSnoozing;
    }
}
