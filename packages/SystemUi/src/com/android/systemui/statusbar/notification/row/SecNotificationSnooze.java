package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.SnoozeOptionManager;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NotificationSAUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecNotificationSnooze extends LinearLayout implements NotificationGuts.GutsContent, View.OnClickListener, GutContentInitializer {
    public TextView mCancel;
    public final Context mContext;
    public TextView mDone;
    public NotificationEntry mEntry;
    public NotificationGuts mGutsContainer;
    public final MetricsLogger mMetricsLogger;
    public StatusBarNotification mSbn;
    public final SnoozeOptionManager mSnoozeOptionManager;
    public static final LogMaker OPTIONS_OPEN_LOG = new LogMaker(1142).setType(1);
    public static final LogMaker OPTIONS_CLOSE_LOG = new LogMaker(1142).setType(2);
    public static final LogMaker UNDO_LOG = new LogMaker(1141).setType(4);

    public SecNotificationSnooze(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMetricsLogger = new MetricsLogger();
        this.mSnoozeOptionManager = new SnoozeOptionManager(context);
        this.mContext = context;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return getHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
        snoozeOptionManager.setSelected(snoozeOptionManager.mDefaultOption, false);
        return this;
    }

    public NotificationSwipeActionHelper.SnoozeOption getDefaultOption() {
        return this.mSnoozeOptionManager.mDefaultOption;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer
    public final boolean initializeGutContentView(final ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        Drawable defaultActivityIcon;
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        final AtomicReference atomicReference = new AtomicReference();
        Optional.ofNullable(((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).getShadeViewController()).map(new SecNotificationSnooze$$ExternalSyntheticLambda1()).ifPresent(new SecNotificationSnooze$$ExternalSyntheticLambda2(atomicReference, 0));
        Optional.ofNullable((NotificationListContainer) atomicReference.get()).ifPresent(new SecNotificationSnooze$$ExternalSyntheticLambda2(this, 1));
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        this.mEntry = notificationEntry;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        this.mSbn = statusBarNotification;
        if (statusBarNotification != null) {
            PackageManager packageManagerForUser = CentralSurfaces.getPackageManagerForUser(statusBarNotification.getUser().getIdentifier(), this.mContext);
            String str = "";
            try {
                ApplicationInfo applicationInfo = packageManagerForUser.getApplicationInfo(this.mSbn.getPackageName(), 795136);
                if (applicationInfo != null) {
                    str = String.valueOf(packageManagerForUser.getApplicationLabel(applicationInfo));
                    defaultActivityIcon = packageManagerForUser.getApplicationIcon(applicationInfo);
                } else {
                    defaultActivityIcon = null;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                defaultActivityIcon = packageManagerForUser.getDefaultActivityIcon();
            }
            ((ImageView) findViewById(R.id.snooze_header_icon)).setImageDrawable(defaultActivityIcon);
            ((TextView) findViewById(R.id.snooze_header_title)).setText(str);
        }
        List snoozeCriteria = expandableNotificationRow.mEntry.mRanking.getSnoozeCriteria();
        int gutsTextColor = ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getGutsTextColor();
        if (gutsTextColor != 0) {
            setBackgroundColor(this.mContext.getResources().getColor(R.color.sec_notification_guts_header_bg_color));
            if (DeviceState.isOpenTheme(this.mContext)) {
                setBackgroundColor(this.mContext.getResources().getColor(R.color.open_theme_notification_bg_color));
                z = true;
            } else {
                z = false;
            }
            if (this.mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                setBackgroundColor(this.mContext.getResources().getColor(R.color.qp_notification_guts_color));
                z = true;
            }
            TextView textView = (TextView) findViewById(R.id.snooze_header_title);
            if (textView != null) {
                textView.setTextColor(gutsTextColor);
            }
            View findViewById = findViewById(R.id.header_divider);
            if (findViewById != null) {
                findViewById.setBackgroundColor(z ? ColorUtils.setAlphaComponent(gutsTextColor, 128) : getResources().getColor(R.color.notification_guts_non_selected, null));
            }
            TextView textView2 = (TextView) findViewById(R.id.snooze_guide_text);
            if (textView2 != null) {
                textView2.setTextColor(gutsTextColor);
            }
            SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
            if (snoozeOptionManager.mSnoozeOptionContainer != null) {
                for (int i = 0; i < snoozeOptionManager.mSnoozeOptionContainer.getChildCount(); i++) {
                    View childAt = snoozeOptionManager.mSnoozeOptionContainer.getChildAt(i);
                    if (childAt instanceof RadioButton) {
                        ((RadioButton) childAt).setTextColor(gutsTextColor);
                    }
                }
            }
            TextView textView3 = this.mCancel;
            if (textView3 != null) {
                textView3.setTextColor(gutsTextColor);
                ViewGroup viewGroup = (ViewGroup) this.mCancel.getParent();
                if (viewGroup != null && (viewGroup instanceof LinearLayout)) {
                    Drawable mutate = getContext().getDrawable(R.drawable.notification_guts_button_divider).mutate();
                    mutate.setTint(androidx.core.graphics.ColorUtils.setAlphaComponent(gutsTextColor, 76));
                    mutate.setTintMode(PorterDuff.Mode.SRC);
                    ((LinearLayout) viewGroup).setDividerDrawable(mutate);
                }
            }
            TextView textView4 = this.mDone;
            if (textView4 != null) {
                textView4.setTextColor(gutsTextColor);
            }
        }
        if (snoozeCriteria != null) {
            SnoozeOptionManager snoozeOptionManager2 = this.mSnoozeOptionManager;
            ((ArrayList) snoozeOptionManager2.mSnoozeOptions).clear();
            snoozeOptionManager2.mSnoozeOptions = snoozeOptionManager2.getDefaultSnoozeOptions();
            int min = Math.min(1, snoozeCriteria.size());
            for (int i2 = 0; i2 < min; i2++) {
                SnoozeCriterion snoozeCriterion = (SnoozeCriterion) snoozeCriteria.get(i2);
                ((ArrayList) snoozeOptionManager2.mSnoozeOptions).add(new SnoozeOptionManager.NotificationSnoozeOption(snoozeOptionManager2, snoozeCriterion, 0, snoozeCriterion.getExplanation(), snoozeCriterion.getConfirmation(), new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze_assistant_suggestion_1, snoozeCriterion.getExplanation())));
            }
            snoozeOptionManager2.createOptionViews();
        }
        notificationGuts.mHeightListener = new NotificationGuts.OnHeightChangedListener() { // from class: com.android.systemui.statusbar.notification.row.SecNotificationSnooze$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.OnHeightChangedListener
            public final void onHeightChanged() {
                LogMaker logMaker = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                Optional.ofNullable((NotificationListContainer) atomicReference.get()).ifPresent(new SecNotificationSnooze$$ExternalSyntheticLambda2(expandableNotificationRow, 2));
            }
        };
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean isLeavebehind() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mMetricsLogger.write(OPTIONS_OPEN_LOG);
        SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
        snoozeOptionManager.logOptionSelection(1137, snoozeOptionManager.mDefaultOption);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        NotificationGuts notificationGuts = this.mGutsContainer;
        if (notificationGuts != null) {
            notificationGuts.resetFalsingCheck();
        }
        view.getId();
        NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) view.getTag();
        if (snoozeOption != null) {
            this.mSnoozeOptionManager.setSelected(snoozeOption, true);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
        snoozeOptionManager.mParent = this;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.snooze_options);
        snoozeOptionManager.mSnoozeOptionContainer = viewGroup;
        viewGroup.setOnClickListener((SecNotificationSnooze) snoozeOptionManager.mParent);
        snoozeOptionManager.mSnoozeOptions = snoozeOptionManager.getDefaultSnoozeOptions();
        snoozeOptionManager.createOptionViews();
        SnoozeOptionManager snoozeOptionManager2 = this.mSnoozeOptionManager;
        final int i = 0;
        snoozeOptionManager2.setSelected(snoozeOptionManager2.mDefaultOption, false);
        TextView textView = (TextView) findViewById(R.id.snooze_cancel);
        this.mCancel = textView;
        textView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.SecNotificationSnooze$$ExternalSyntheticLambda0
            public final /* synthetic */ SecNotificationSnooze f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationSwipeActionHelper.SnoozeOption snoozeOption;
                switch (i) {
                    case 0:
                        SecNotificationSnooze secNotificationSnooze = this.f$0;
                        secNotificationSnooze.mGutsContainer.closeControls(view, false);
                        secNotificationSnooze.mMetricsLogger.write(SecNotificationSnooze.UNDO_LOG);
                        break;
                    default:
                        SecNotificationSnooze secNotificationSnooze2 = this.f$0;
                        SnoozeOptionManager snoozeOptionManager3 = secNotificationSnooze2.mSnoozeOptionManager;
                        NotificationEntry notificationEntry = secNotificationSnooze2.mEntry;
                        NotificationSwipeActionHelper notificationSwipeActionHelper = snoozeOptionManager3.mSnoozeListener;
                        if (notificationSwipeActionHelper != null && (snoozeOption = snoozeOptionManager3.mSelectedOption) != null) {
                            snoozeOptionManager3.mSnoozing = true;
                            notificationSwipeActionHelper.snooze(notificationEntry.mSbn, snoozeOption);
                            if (snoozeOptionManager3.mSelectedOption instanceof SnoozeOptionManager.NotificationSnoozeOption) {
                                NotificationSAUtil.sendCancelLog(notificationEntry, "QPNE0007");
                            }
                        }
                        secNotificationSnooze2.mMetricsLogger.write(SecNotificationSnooze.OPTIONS_CLOSE_LOG);
                        secNotificationSnooze2.mGutsContainer.closeControls(view, false);
                        break;
                }
            }
        });
        TextView textView2 = (TextView) findViewById(R.id.snooze_save);
        this.mDone = textView2;
        final int i2 = 1;
        textView2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.statusbar.notification.row.SecNotificationSnooze$$ExternalSyntheticLambda0
            public final /* synthetic */ SecNotificationSnooze f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationSwipeActionHelper.SnoozeOption snoozeOption;
                switch (i2) {
                    case 0:
                        SecNotificationSnooze secNotificationSnooze = this.f$0;
                        secNotificationSnooze.mGutsContainer.closeControls(view, false);
                        secNotificationSnooze.mMetricsLogger.write(SecNotificationSnooze.UNDO_LOG);
                        break;
                    default:
                        SecNotificationSnooze secNotificationSnooze2 = this.f$0;
                        SnoozeOptionManager snoozeOptionManager3 = secNotificationSnooze2.mSnoozeOptionManager;
                        NotificationEntry notificationEntry = secNotificationSnooze2.mEntry;
                        NotificationSwipeActionHelper notificationSwipeActionHelper = snoozeOptionManager3.mSnoozeListener;
                        if (notificationSwipeActionHelper != null && (snoozeOption = snoozeOptionManager3.mSelectedOption) != null) {
                            snoozeOptionManager3.mSnoozing = true;
                            notificationSwipeActionHelper.snooze(notificationEntry.mSbn, snoozeOption);
                            if (snoozeOptionManager3.mSelectedOption instanceof SnoozeOptionManager.NotificationSnoozeOption) {
                                NotificationSAUtil.sendCancelLog(notificationEntry, "QPNE0007");
                            }
                        }
                        secNotificationSnooze2.mMetricsLogger.write(SecNotificationSnooze.OPTIONS_CLOSE_LOG);
                        secNotificationSnooze2.mGutsContainer.closeControls(view, false);
                        break;
                }
            }
        });
        TextView textView3 = this.mCancel;
        if (textView3 != null) {
            textView3.semSetButtonShapeEnabled(true);
        }
        TextView textView4 = this.mDone;
        if (textView4 != null) {
            textView4.semSetButtonShapeEnabled(true);
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        NotificationGuts notificationGuts = this.mGutsContainer;
        if (notificationGuts != null && notificationGuts.mExposed && accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.getText().add(this.mSnoozeOptionManager.mSelectedOption.getConfirmation());
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snooze_undo, getResources().getString(R.string.snooze_undo)));
        SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
        snoozeOptionManager.mSnoozeOptions.size();
        Iterator it = snoozeOptionManager.mSnoozeOptions.iterator();
        while (it.hasNext()) {
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = ((NotificationSwipeActionHelper.SnoozeOption) it.next()).getAccessibilityAction();
            if (accessibilityAction != null) {
                accessibilityNodeInfo.addAction(accessibilityAction);
            }
        }
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        boolean z;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        SnoozeOptionManager snoozeOptionManager = this.mSnoozeOptionManager;
        Iterator it = ((ArrayList) snoozeOptionManager.mSnoozeOptions).iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            NotificationSwipeActionHelper.SnoozeOption snoozeOption = (NotificationSwipeActionHelper.SnoozeOption) it.next();
            if (snoozeOption.getAccessibilityAction() != null && snoozeOption.getAccessibilityAction().getId() == i) {
                snoozeOptionManager.setSelected(snoozeOption, true);
                z = true;
                break;
            }
        }
        return z;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return this.mSnoozeOptionManager.mSnoozing;
    }
}
