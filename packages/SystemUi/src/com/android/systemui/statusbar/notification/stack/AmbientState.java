package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AmbientState implements Dumpable, PanelScreenShotLogger.LogProvider {
    public float mAppearFraction;
    public boolean mAppearing;
    public final StackScrollAlgorithm.BypassController mBypassController;
    public boolean mClearAllInProgress;
    public int mContentHeight;
    public float mCurrentScrollVelocity;
    public boolean mDimmed;
    public boolean mDozing;
    public float mExpandingVelocity;
    public boolean mExpansionChanging;
    public float mExpansionFraction;
    public final FeatureFlags mFeatureFlags;
    public float mFractionToShade;
    public float mHideAmount;
    public boolean mHideSensitive;
    public boolean mIsClosing;
    public boolean mIsExpandAnimating;
    public boolean mIsFlinging;
    public boolean mIsFullyExpanding;
    public boolean mIsSmallScreen;
    public boolean mIsSwipingUp;
    public final LargeScreenShadeInterpolator mLargeScreenShadeInterpolator;
    public ExpandableView mLastVisibleBackgroundChild;
    public int mLayoutHeight;
    public int mLayoutMaxHeight;
    public int mLayoutMinHeight;
    public float mMaxHeadsUpTranslation;
    public float mNotificationScrimTop;
    public Runnable mOnPulseHeightChangedListener;
    public float mOverExpansion;
    public float mOverScrollBottomAmount;
    public float mOverScrollTopAmount;
    public boolean mPanelTracking;
    public boolean mPulsing;
    public ExpandableNotificationRow mPulsingRow;
    public int mScrollY;
    public final StackScrollAlgorithm.SectionProvider mSectionProvider;
    public boolean mShadeExpanded;
    public NotificationShelf mShelf;
    public float mStackEndHeight;
    public int mStackTopMargin;
    public float mStackTranslation;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public int mStatusBarState;
    public int mTopPadding;
    public ExpandableNotificationRow mTrackedHeadsUpRow;
    public boolean mUnlockHintRunning;
    public int mZDistanceBetweenElements;
    public float mPulseHeight = 100000.0f;
    public float mDozeAmount = 0.0f;
    public float mStackY = 0.0f;
    public float mStackHeight = 0.0f;
    public boolean mIsFlingRequiredAfterLockScreenSwipeUp = false;
    public float mNotificationTopRatio = 0.0f;

    public AmbientState(Context context, DumpManager dumpManager, StackScrollAlgorithm.SectionProvider sectionProvider, StackScrollAlgorithm.BypassController bypassController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, LargeScreenShadeInterpolator largeScreenShadeInterpolator, FeatureFlags featureFlags) {
        this.mSectionProvider = sectionProvider;
        this.mBypassController = bypassController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mLargeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.mFeatureFlags = featureFlags;
        this.mZDistanceBetweenElements = Math.max(1, context.getResources().getDimensionPixelSize(R.dimen.z_distance_between_notifications));
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m81m = LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("mTopPadding="), this.mTopPadding, printWriter, "mStackTopMargin="), this.mStackTopMargin, printWriter, "mStackTranslation="), this.mStackTranslation, printWriter, "mLayoutMinHeight="), this.mLayoutMinHeight, printWriter, "mLayoutMaxHeight="), this.mLayoutMaxHeight, printWriter, "mLayoutHeight="), this.mLayoutHeight, printWriter, "mContentHeight="), this.mContentHeight, printWriter, "mHideSensitive="), this.mHideSensitive, printWriter, "mShadeExpanded="), this.mShadeExpanded, printWriter, "mClearAllInProgress="), this.mClearAllInProgress, printWriter, "mDimmed="), this.mDimmed, printWriter, "mStatusBarState="), this.mStatusBarState, printWriter, "mExpansionChanging="), this.mExpansionChanging, printWriter, "mPanelFullWidth="), this.mIsSmallScreen, printWriter, "mPulsing="), this.mPulsing, printWriter, "mPulseHeight="), this.mPulseHeight, printWriter, "mTrackedHeadsUpRow.key=");
        ExpandableNotificationRow expandableNotificationRow = this.mTrackedHeadsUpRow;
        m81m.append(expandableNotificationRow == null ? "null" : NotificationUtils.logKey(expandableNotificationRow.mEntry));
        printWriter.println(m81m.toString());
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(LockIconView$$ExternalSyntheticOutline0.m81m(new StringBuilder("mMaxHeadsUpTranslation="), this.mMaxHeadsUpTranslation, printWriter, "mUnlockHintRunning="), this.mUnlockHintRunning, printWriter, "mDozeAmount="), this.mDozeAmount, printWriter, "mDozing="), this.mDozing, printWriter, "mFractionToShade="), this.mFractionToShade, printWriter, "mHideAmount="), this.mHideAmount, printWriter, "mAppearFraction="), this.mAppearFraction, printWriter, "mAppearing="), this.mAppearing, printWriter, "mExpansionFraction="), this.mExpansionFraction, printWriter, "mExpandingVelocity="), this.mExpandingVelocity, printWriter, "mOverScrollTopAmount="), this.mOverScrollTopAmount, printWriter, "mOverScrollBottomAmount="), this.mOverScrollBottomAmount, printWriter, "mOverExpansion="), this.mOverExpansion, printWriter, "mStackHeight="), this.mStackHeight, printWriter, "mStackEndHeight="), this.mStackEndHeight, printWriter, "mStackY="), this.mStackY, printWriter, "mScrollY="), this.mScrollY, printWriter, "mCurrentScrollVelocity="), this.mCurrentScrollVelocity, printWriter, "mIsSwipingUp="), this.mIsSwipingUp, printWriter, "mPanelTracking="), this.mPanelTracking, printWriter, "mIsFlinging="), this.mIsFlinging, printWriter, "mIsFlingRequiredAfterLockScreenSwipeUp="), this.mIsFlingRequiredAfterLockScreenSwipeUp, printWriter, "mZDistanceBetweenElements=");
        m64m.append(this.mZDistanceBetweenElements);
        printWriter.println(m64m.toString());
        printWriter.println("mBaseZHeight=0");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(new StringBuilder("mIsClosing="), this.mIsClosing, printWriter);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("AmbientState", arrayList);
        PanelScreenShotLogger.addLogItem(arrayList, "mTopPadding", Integer.valueOf(this.mTopPadding));
        PanelScreenShotLogger.addLogItem(arrayList, "mStackTopMargin", Integer.valueOf(this.mStackTopMargin));
        PanelScreenShotLogger.addLogItem(arrayList, "mStackTranslation", Float.valueOf(this.mStackTranslation));
        PanelScreenShotLogger.addLogItem(arrayList, "mLayoutMinHeight", Integer.valueOf(this.mLayoutMinHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mLayoutMaxHeight", Integer.valueOf(this.mLayoutMaxHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mLayoutHeight", Integer.valueOf(this.mLayoutHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mContentHeight", Integer.valueOf(this.mContentHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mHideSensitive", Boolean.valueOf(this.mHideSensitive));
        PanelScreenShotLogger.addLogItem(arrayList, "mShadeExpanded", Boolean.valueOf(this.mShadeExpanded));
        PanelScreenShotLogger.addLogItem(arrayList, "mClearAllInProgress", Boolean.valueOf(this.mClearAllInProgress));
        PanelScreenShotLogger.addLogItem(arrayList, "mDimmed", Boolean.valueOf(this.mDimmed));
        PanelScreenShotLogger.addLogItem(arrayList, "mStatusBarState", Integer.valueOf(this.mStatusBarState));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpansionChanging", Boolean.valueOf(this.mExpansionChanging));
        PanelScreenShotLogger.addLogItem(arrayList, "mPanelFullWidth", Boolean.valueOf(this.mIsSmallScreen));
        PanelScreenShotLogger.addLogItem(arrayList, "mPulsing", Boolean.valueOf(this.mPulsing));
        PanelScreenShotLogger.addLogItem(arrayList, "mPulseHeight", Float.valueOf(this.mPulseHeight));
        ExpandableNotificationRow expandableNotificationRow = this.mTrackedHeadsUpRow;
        PanelScreenShotLogger.addLogItem(arrayList, "mTrackedHeadsUpRow.key=", expandableNotificationRow == null ? "null" : NotificationUtils.logKey(expandableNotificationRow.mEntry));
        PanelScreenShotLogger.addLogItem(arrayList, "mMaxHeadsUpTranslation", Float.valueOf(this.mMaxHeadsUpTranslation));
        PanelScreenShotLogger.addLogItem(arrayList, "mUnlockHintRunning", Boolean.valueOf(this.mUnlockHintRunning));
        PanelScreenShotLogger.addLogItem(arrayList, "mDozeAmount", Float.valueOf(this.mDozeAmount));
        PanelScreenShotLogger.addLogItem(arrayList, "mDozing", Boolean.valueOf(this.mDozing));
        PanelScreenShotLogger.addLogItem(arrayList, "mFractionToShade", Float.valueOf(this.mFractionToShade));
        PanelScreenShotLogger.addLogItem(arrayList, "mAppearFraction", Float.valueOf(this.mAppearFraction));
        PanelScreenShotLogger.addLogItem(arrayList, "mAppearing", Boolean.valueOf(this.mAppearing));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpansionFraction", Float.valueOf(this.mExpansionFraction));
        PanelScreenShotLogger.addLogItem(arrayList, "mExpandingVelocity", Float.valueOf(this.mExpandingVelocity));
        PanelScreenShotLogger.addLogItem(arrayList, "mOverScrollTopAmount", Float.valueOf(this.mOverScrollTopAmount));
        PanelScreenShotLogger.addLogItem(arrayList, "mOverScrollBottomAmount", Float.valueOf(this.mOverScrollBottomAmount));
        PanelScreenShotLogger.addLogItem(arrayList, "mOverExpansion", Float.valueOf(this.mOverExpansion));
        PanelScreenShotLogger.addLogItem(arrayList, "mStackHeight", Float.valueOf(this.mStackHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mStackEndHeight", Float.valueOf(this.mStackEndHeight));
        PanelScreenShotLogger.addLogItem(arrayList, "mStackY", Float.valueOf(this.mStackY));
        PanelScreenShotLogger.addLogItem(arrayList, "mScrollY", Integer.valueOf(this.mScrollY));
        PanelScreenShotLogger.addLogItem(arrayList, "mCurrentScrollVelocity", Float.valueOf(this.mCurrentScrollVelocity));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsSwipingUp", Boolean.valueOf(this.mIsSwipingUp));
        PanelScreenShotLogger.addLogItem(arrayList, "mPanelTracking", Boolean.valueOf(this.mPanelTracking));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsFlinging", Boolean.valueOf(this.mIsFlinging));
        PanelScreenShotLogger.addLogItem(arrayList, "mIsFlingRequiredAfterLockScreenSwipeUp", Boolean.valueOf(this.mIsFlingRequiredAfterLockScreenSwipeUp));
        PanelScreenShotLogger.addLogItem(arrayList, "mZDistanceBetweenElements", Integer.valueOf(this.mZDistanceBetweenElements));
        PanelScreenShotLogger.addLogItem(arrayList, "mBaseZHeight", 0);
        return arrayList;
    }

    public final int getInnerHeight$1() {
        return (this.mDozeAmount != 1.0f || isPulseExpanding()) ? Math.max(this.mLayoutMinHeight, Math.min(this.mLayoutHeight, this.mContentHeight) - this.mTopPadding) : this.mShelf.getHeight();
    }

    public final ExpandableNotificationRow getTrackedHeadsUpRow() {
        ExpandableNotificationRow expandableNotificationRow = this.mTrackedHeadsUpRow;
        if (expandableNotificationRow == null || !expandableNotificationRow.isAboveShelf()) {
            return null;
        }
        return this.mTrackedHeadsUpRow;
    }

    public final boolean isDozingAndNotPulsing(ExpandableView expandableView) {
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        if (this.mDozing) {
            return !(this.mPulsing && expandableNotificationRow.mEntry.mIsAlerting);
        }
        return false;
    }

    public boolean isFlingRequiredAfterLockScreenSwipeUp() {
        return this.mIsFlingRequiredAfterLockScreenSwipeUp;
    }

    public final boolean isFullyHidden() {
        return this.mHideAmount == 1.0f;
    }

    public final boolean isHiddenAtAll() {
        return this.mHideAmount != 0.0f;
    }

    public final boolean isOnKeyguard() {
        return this.mStatusBarState == 1;
    }

    public final boolean isPulseExpanding() {
        return (this.mPulseHeight == 100000.0f || this.mDozeAmount == 0.0f || this.mHideAmount == 1.0f) ? false : true;
    }

    public void setFlingRequiredAfterLockScreenSwipeUp(boolean z) {
        this.mIsFlingRequiredAfterLockScreenSwipeUp = z;
    }
}
