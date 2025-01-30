package com.android.systemui.classifier;

import android.net.Uri;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.sec.ims.configuration.DATA;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BrightLineFalsingManager implements FalsingManager {
    public static final boolean DEBUG = Log.isLoggable("FalsingManager", 3);
    public static final Queue RECENT_INFO_LOG = new ArrayDeque(41);
    public static final Queue RECENT_SWIPES = new ArrayDeque(21);
    public final AccessibilityManager mAccessibilityManager;
    public final C11402 mBeliefListener;
    public final Collection mClassifiers;
    public final FalsingDataProvider mDataProvider;
    public boolean mDestroyed;
    public final DoubleTapClassifier mDoubleTapClassifier;
    public final List mFalsingBeliefListeners = new ArrayList();
    public final List mFalsingTapListeners = new ArrayList();
    public final FeatureFlags mFeatureFlags;
    public final C11413 mGestureFinalizedListener;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public FalsingManager.ProximityEvent mLastProximityEvent;
    public final LongTapClassifier mLongTapClassifier;
    public final MetricsLogger mMetricsLogger;
    public int mPriorInteractionType;
    public Collection mPriorResults;
    public final C11391 mSessionListener;
    public final SingleTapClassifier mSingleTapClassifier;
    public final boolean mTestHarness;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$1 */
    public final class C11391 implements FalsingDataProvider.SessionListener {
        public C11391() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$2 */
    public final class C11402 implements HistoryTracker.BeliefListener {
        public C11402() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$3 */
    public final class C11413 {
        public C11413() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DebugSwipeRecord {
        public final int mInteractionType;
        public final boolean mIsFalse;
        public final List mRecentMotionEvents;

        public DebugSwipeRecord(boolean z, int i, List<XYDt> list) {
            this.mIsFalse = z;
            this.mInteractionType = i;
            this.mRecentMotionEvents = list;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class XYDt {
        public final int mDT;

        /* renamed from: mX */
        public final int f236mX;

        /* renamed from: mY */
        public final int f237mY;

        public XYDt(int i, int i2, int i3) {
            this.f236mX = i;
            this.f237mY = i2;
            this.mDT = i3;
        }

        public final String toString() {
            return this.f236mX + "," + this.f237mY + "," + this.mDT;
        }
    }

    public BrightLineFalsingManager(FalsingDataProvider falsingDataProvider, MetricsLogger metricsLogger, Set<FalsingClassifier> set, SingleTapClassifier singleTapClassifier, LongTapClassifier longTapClassifier, DoubleTapClassifier doubleTapClassifier, HistoryTracker historyTracker, KeyguardStateController keyguardStateController, AccessibilityManager accessibilityManager, boolean z, FeatureFlags featureFlags) {
        C11391 c11391 = new C11391();
        this.mSessionListener = c11391;
        C11402 c11402 = new C11402();
        this.mBeliefListener = c11402;
        C11413 c11413 = new C11413();
        this.mGestureFinalizedListener = c11413;
        this.mPriorInteractionType = 7;
        this.mDataProvider = falsingDataProvider;
        this.mMetricsLogger = metricsLogger;
        this.mClassifiers = set;
        this.mSingleTapClassifier = singleTapClassifier;
        this.mLongTapClassifier = longTapClassifier;
        this.mDoubleTapClassifier = doubleTapClassifier;
        this.mHistoryTracker = historyTracker;
        this.mKeyguardStateController = keyguardStateController;
        this.mAccessibilityManager = accessibilityManager;
        this.mTestHarness = z;
        this.mFeatureFlags = featureFlags;
        ((ArrayList) falsingDataProvider.mSessionListeners).add(c11391);
        ((ArrayList) falsingDataProvider.mGestureFinalizedListeners).add(c11413);
        ((ArrayList) historyTracker.mBeliefListeners).add(c11402);
    }

    public static Collection getPassedResult(double d) {
        return Collections.singleton(FalsingClassifier.Result.passed(d));
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        ((ArrayList) this.mFalsingBeliefListeners).add(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        ((ArrayList) this.mFalsingTapListeners).add(falsingTapListener);
    }

    public final void checkDestroyed() {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void cleanupInternal() {
        this.mDestroyed = true;
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        ((ArrayList) falsingDataProvider.mSessionListeners).remove(this.mSessionListener);
        ((ArrayList) falsingDataProvider.mGestureFinalizedListeners).remove(this.mGestureFinalizedListener);
        this.mClassifiers.forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(1));
        ((ArrayList) this.mFalsingBeliefListeners).clear();
        ((ArrayList) this.mHistoryTracker.mBeliefListeners).remove(this.mBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BRIGHTLINE FALSING MANAGER");
        indentingPrintWriter.print("classifierEnabled=");
        indentingPrintWriter.println(1);
        indentingPrintWriter.print("mJustUnlockedWithFace=");
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        indentingPrintWriter.println(falsingDataProvider.mJustUnlockedWithFace ? 1 : 0);
        indentingPrintWriter.print("isDocked=");
        if (((BatteryControllerImpl) falsingDataProvider.mBatteryController).mWirelessCharging) {
            i = 1;
        } else {
            falsingDataProvider.mDockManager.getClass();
            i = 0;
        }
        indentingPrintWriter.println(i);
        indentingPrintWriter.print("width=");
        indentingPrintWriter.println(falsingDataProvider.mWidthPixels);
        indentingPrintWriter.print("height=");
        indentingPrintWriter.println(falsingDataProvider.mHeightPixels);
        indentingPrintWriter.println();
        ArrayDeque arrayDeque = (ArrayDeque) RECENT_SWIPES;
        if (arrayDeque.size() != 0) {
            indentingPrintWriter.println("Recent swipes:");
            indentingPrintWriter.increaseIndent();
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                DebugSwipeRecord debugSwipeRecord = (DebugSwipeRecord) it.next();
                debugSwipeRecord.getClass();
                StringJoiner stringJoiner = new StringJoiner(",");
                stringJoiner.add(Integer.toString(1)).add(debugSwipeRecord.mIsFalse ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN).add(Integer.toString(debugSwipeRecord.mInteractionType));
                Iterator it2 = debugSwipeRecord.mRecentMotionEvents.iterator();
                while (it2.hasNext()) {
                    stringJoiner.add(((XYDt) it2.next()).toString());
                }
                indentingPrintWriter.println(stringJoiner.toString());
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No recent swipes");
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Recent falsing info:");
        indentingPrintWriter.increaseIndent();
        Iterator it3 = ((ArrayDeque) RECENT_INFO_LOG).iterator();
        while (it3.hasNext()) {
            indentingPrintWriter.println((String) it3.next());
        }
        indentingPrintWriter.println();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isClassifierEnabled() {
        return true;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseDoubleTap() {
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        HistoryTracker historyTracker = this.mHistoryTracker;
        historyTracker.falseBelief();
        historyTracker.falseConfidence();
        FalsingClassifier.Result calculateFalsingResult = this.mDoubleTapClassifier.calculateFalsingResult(7);
        this.mPriorResults = Collections.singleton(calculateFalsingResult);
        calculateFalsingResult.getReason();
        return calculateFalsingResult.mFalsed;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseLongTap(int i) {
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        double d = 0.0d;
        if (i != 0) {
            if (i == 1) {
                d = 0.1d;
            } else if (i == 2) {
                d = 0.3d;
            } else if (i == 3) {
                d = 0.6d;
            }
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        FalsingClassifier.Result isTap = this.mLongTapClassifier.isTap(falsingDataProvider.getRecentMotionEvents().isEmpty() ? falsingDataProvider.mPriorMotionEvents : falsingDataProvider.getRecentMotionEvents(), d);
        this.mPriorResults = Collections.singleton(isTap);
        boolean z = isTap.mFalsed;
        if (z) {
            return z;
        }
        if (falsingDataProvider.mJustUnlockedWithFace) {
            this.mPriorResults = getPassedResult(1.0d);
        } else {
            this.mPriorResults = getPassedResult(0.1d);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0044  */
    @Override // com.android.systemui.plugins.FalsingManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isFalseTap(int i) {
        double d;
        boolean z;
        checkDestroyed();
        int i2 = 0;
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        if (i != 0) {
            if (i == 1) {
                d = 0.1d;
            } else if (i == 2) {
                d = 0.3d;
            } else if (i == 3) {
                d = 0.6d;
            }
            FalsingDataProvider falsingDataProvider = this.mDataProvider;
            FalsingClassifier.Result isTap = this.mSingleTapClassifier.isTap(!falsingDataProvider.getRecentMotionEvents().isEmpty() ? falsingDataProvider.mPriorMotionEvents : falsingDataProvider.getRecentMotionEvents(), d);
            this.mPriorResults = Collections.singleton(isTap);
            z = isTap.mFalsed;
            if (!z) {
                return z;
            }
            if (falsingDataProvider.mJustUnlockedWithFace) {
                this.mPriorResults = getPassedResult(1.0d);
                return false;
            }
            if (!isFalseDoubleTap()) {
                return false;
            }
            if (this.mHistoryTracker.falseBelief() <= 0.7d) {
                this.mPriorResults = getPassedResult(0.1d);
                return false;
            }
            this.mPriorResults = Collections.singleton(FalsingClassifier.Result.falsed(0.0d, "BrightLineFalsingManager", "bad history"));
            ((ArrayList) this.mFalsingTapListeners).forEach(new BrightLineFalsingManager$$ExternalSyntheticLambda0(i2));
            return true;
        }
        d = 0.0d;
        FalsingDataProvider falsingDataProvider2 = this.mDataProvider;
        FalsingClassifier.Result isTap2 = this.mSingleTapClassifier.isTap(!falsingDataProvider2.getRecentMotionEvents().isEmpty() ? falsingDataProvider2.mPriorMotionEvents : falsingDataProvider2.getRecentMotionEvents(), d);
        this.mPriorResults = Collections.singleton(isTap2);
        z = isTap2.mFalsed;
        if (!z) {
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isFalseTouch(final int i) {
        checkDestroyed();
        this.mPriorInteractionType = i;
        if (skipFalsing(i)) {
            this.mPriorResults = getPassedResult(1.0d);
            return false;
        }
        final boolean[] zArr = {false};
        this.mPriorResults = (Collection) this.mClassifiers.stream().map(new Function() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
                int i2 = i;
                boolean[] zArr2 = zArr;
                HistoryTracker historyTracker = brightLineFalsingManager.mHistoryTracker;
                historyTracker.falseBelief();
                historyTracker.falseConfidence();
                FalsingClassifier.Result calculateFalsingResult = ((FalsingClassifier) obj).calculateFalsingResult(i2);
                zArr2[0] = zArr2[0] | calculateFalsingResult.mFalsed;
                return calculateFalsingResult;
            }
        }).collect(Collectors.toList());
        if (i == 18) {
            zArr[0] = isFalseTap(2) & zArr[0];
        }
        return zArr[0];
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isProximityNear() {
        FalsingManager.ProximityEvent proximityEvent = this.mLastProximityEvent;
        return proximityEvent != null && proximityEvent.getCovered();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isReportingEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isSimpleTap() {
        checkDestroyed();
        this.mPriorResults = Collections.singleton(this.mSingleTapClassifier.isTap(this.mDataProvider.getRecentMotionEvents(), 0.0d));
        return !r0.mFalsed;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean isUnlockingDisabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onProximityEvent(final FalsingManager.ProximityEvent proximityEvent) {
        this.mLastProximityEvent = proximityEvent;
        this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingClassifier) obj).onProximityEvent(FalsingManager.ProximityEvent.this);
            }
        });
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        ((ArrayList) this.mFalsingBeliefListeners).remove(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        ((ArrayList) this.mFalsingTapListeners).remove(falsingTapListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final Uri reportRejectedTouch() {
        return null;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final boolean shouldEnforceBouncer() {
        return false;
    }

    public final boolean skipFalsing(int i) {
        boolean z;
        if (i == 16 || !((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing || this.mTestHarness) {
            return true;
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        if (falsingDataProvider.mJustUnlockedWithFace) {
            return true;
        }
        if (((BatteryControllerImpl) falsingDataProvider.mBatteryController).mWirelessCharging) {
            z = true;
        } else {
            falsingDataProvider.mDockManager.getClass();
            z = false;
        }
        if (z || this.mAccessibilityManager.isTouchExplorationEnabled() || falsingDataProvider.mA11YAction) {
            return true;
        }
        if (((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.FALSING_OFF_FOR_UNFOLDED)) {
            if (falsingDataProvider.mIsFoldableDevice && Boolean.FALSE.equals(falsingDataProvider.mFoldStateListener.getFolded())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public final void onSuccessfulUnlock() {
    }
}
