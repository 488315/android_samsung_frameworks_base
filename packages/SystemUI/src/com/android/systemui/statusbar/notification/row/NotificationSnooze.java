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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
    public List mSnoozeOptions;
    public View mSnoozeView;
    public boolean mSnoozing;
    public TextView mUndoButton;
    public static final LogMaker OPTIONS_OPEN_LOG = new LogMaker(1142).setType(1);
    public static final LogMaker OPTIONS_CLOSE_LOG = new LogMaker(1142).setType(2);
    public static final LogMaker UNDO_LOG = new LogMaker(1141).setType(4);
    public static final int[] sAccessibilityActions = {R.id.action_snooze_shorter, R.id.action_snooze_short, R.id.action_snooze_long, R.id.action_snooze_longer};

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotificationSnoozeOption implements NotificationSwipeActionHelper.SnoozeOption {
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
            NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) this.mSnoozeOptions.get(i);
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
        Resources resources = getContext().getResources();
        ArrayList<NotificationSwipeActionHelper.SnoozeOption> arrayList = new ArrayList<>();
        try {
            this.mParser.setString(Settings.Global.getString(getContext().getContentResolver(), "notification_snooze_options"));
        } catch (IllegalArgumentException unused) {
            Log.e("NotificationSnooze", "Bad snooze constants");
        }
        int i = this.mParser.getInt("default", resources.getInteger(R.integer.config_notification_snooze_time_default));
        int[] intArray = this.mParser.getIntArray("options_array", resources.getIntArray(R.array.config_notification_snooze_times));
        for (int i2 = 0; i2 < intArray.length; i2++) {
            int[] iArr = sAccessibilityActions;
            if (i2 >= iArr.length) {
                break;
            }
            int i3 = intArray[i2];
            int i4 = iArr[i2];
            Resources resources2 = getResources();
            boolean z = i3 >= 60;
            String icuMessageFormat = PluralMessageFormaterKt.icuMessageFormat(resources2, z ? R.string.snoozeHourOptions : R.string.snoozeMinuteOptions, z ? i3 / 60 : i3);
            String format = String.format(resources2.getString(R.string.snoozed_for_time), icuMessageFormat);
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(i4, icuMessageFormat);
            int indexOf = format.indexOf(icuMessageFormat);
            if (indexOf == -1) {
                notificationSnoozeOption = new NotificationSnoozeOption(this, null, i3, icuMessageFormat, format, accessibilityAction);
            } else {
                SpannableString spannableString = new SpannableString(format);
                spannableString.setSpan(new StyleSpan(1, resources2.getConfiguration().fontWeightAdjustment), indexOf, icuMessageFormat.length() + indexOf, 0);
                notificationSnoozeOption = new NotificationSnoozeOption(this, null, i3, icuMessageFormat, spannableString, accessibilityAction);
            }
            if (i2 == 0 || i3 == i) {
                this.mDefaultOption = notificationSnoozeOption;
            }
            arrayList.add(notificationSnoozeOption);
        }
        return arrayList;
    }

    public final String getExpandActionString() {
        return ((LinearLayout) this).mContext.getString(this.mExpanded ? android.R.string.js_dialog_before_unload_negative_button : android.R.string.js_dialog_before_unload);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        NotificationSwipeActionHelper.SnoozeOption snoozeOption;
        if (this.mExpanded && !z2) {
            showSnoozeOptions(false);
            return true;
        }
        NotificationSwipeActionHelper notificationSwipeActionHelper = this.mSnoozeListener;
        if (notificationSwipeActionHelper == null || (snoozeOption = this.mSelectedOption) == null) {
            setSelected((NotificationSwipeActionHelper.SnoozeOption) this.mSnoozeOptions.get(0), false);
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
        View findViewById = findViewById(R.id.notification_snooze);
        this.mSnoozeView = findViewById;
        findViewById.setOnClickListener(this);
        this.mSelectedOptionText = (TextView) findViewById(R.id.snooze_option_default);
        TextView textView = (TextView) findViewById(R.id.undo);
        this.mUndoButton = textView;
        textView.setOnClickListener(this);
        this.mUndoButton.setContentDescription(getContext().getString(R.string.snooze_undo_content_description));
        this.mExpandButton = (ImageView) findViewById(R.id.expand_button);
        View findViewById2 = findViewById(R.id.divider);
        this.mDivider = findViewById2;
        findViewById2.setAlpha(0.0f);
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
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = ((NotificationSwipeActionHelper.SnoozeOption) this.mSnoozeOptions.get(i)).getAccessibilityAction();
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
            NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) this.mSnoozeOptions.get(i2);
            if (snoozeOption.getAccessibilityAction() != null && snoozeOption.getAccessibilityAction().getId() == i) {
                setSelected(snoozeOption, true);
                this.mSnoozeView.sendAccessibilityEvent(32768);
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

    public final void setSelected(NotificationSwipeActionHelper.SnoozeOption snoozeOption, boolean z) {
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

    public final void showSnoozeOptions(final boolean z) {
        NotificationGuts.OnHeightChangedListener onHeightChangedListener;
        this.mExpandButton.setImageResource(z ? android.R.drawable.ic_doc_document : android.R.drawable.ic_jog_dial_sound_on);
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
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), z ? 1.0f : 0.0f);
            ViewGroup viewGroup = this.mSnoozeOptionContainer;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(viewGroup, (Property<ViewGroup, Float>) property, viewGroup.getAlpha(), z ? 1.0f : 0.0f);
            this.mSnoozeOptionContainer.setVisibility(0);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.mExpandAnimation = animatorSet2;
            animatorSet2.playTogether(ofFloat, ofFloat2);
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
        sb.append(((LinearLayout) this).mContext.getString(this.mExpanded ? android.R.string.fingerprint_acquired_imager_dirty : android.R.string.fingerprint_acquired_already_enrolled));
        sb.append("\u2029");
        sb.append((Object) this.mSelectedOptionText.getText());
        view.setContentDescription(sb.toString());
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return this.mSnoozing;
    }
}
