package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.SystemProperties;
import android.os.Trace;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.format.DateFormat;
import android.util.FloatProperty;
import android.view.Choreographer;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.Log;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeFullExpansionListener;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarStateControllerImpl implements SysuiStatusBarStateController, CallbackController, Dumpable {
    public ValueAnimator mDarkAnimator;
    public float mDozeAmount;
    public float mDozeAmountTarget;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsDozing;
    public boolean mIsDreaming;
    public boolean mIsExpanded;
    public boolean mKeyguardRequested;
    public int mLastState;
    public boolean mLeaveOpenOnKeyguardHide;
    public LooperSlowLogController mLooperSlowLogController;
    public boolean mPulsing;
    public int mState;
    public final UiEventLogger mUiEventLogger;
    public int mUpcomingState;
    public View mView;
    public static final boolean DEBUG_IMMERSIVE_APPS = SystemProperties.getBoolean("persist.debug.immersive_apps", false);
    public static final Comparator sComparator = Comparator.comparingInt(new StatusBarStateControllerImpl$$ExternalSyntheticLambda2());
    public static final C26121 SET_DARK_AMOUNT_PROPERTY = new FloatProperty("mDozeAmount") { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((StatusBarStateControllerImpl) obj).mDozeAmount);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            boolean z = StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS;
            ((StatusBarStateControllerImpl) obj).setDozeAmountInternal(f);
        }
    };
    public final ArrayList mListeners = new ArrayList();
    public int mHistoryIndex = 0;
    public final HistoricalState[] mHistoricalRecords = new HistoricalState[32];
    public boolean mIsFullscreen = false;
    public Interpolator mDozeInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HistoricalState {
        public int mLastState;
        public int mNewState;
        public long mTimestamp;
        public boolean mUpcoming;

        private HistoricalState() {
        }

        public /* synthetic */ HistoricalState(int i) {
            this();
        }

        public final String toString() {
            if (this.mTimestamp == 0) {
                return "Empty ".concat(HistoricalState.class.getSimpleName());
            }
            StringBuilder sb = new StringBuilder();
            if (this.mUpcoming) {
                sb.append("upcoming-");
            }
            sb.append("newState=");
            sb.append(this.mNewState);
            sb.append("(");
            int i = this.mNewState;
            boolean z = StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS;
            sb.append(StatusBarState.toString(i));
            sb.append(") lastState=");
            sb.append(this.mLastState);
            sb.append("(");
            sb.append(StatusBarState.toString(this.mLastState));
            sb.append(") timestamp=");
            sb.append(DateFormat.format("MM-dd HH:mm:ss", this.mTimestamp));
            return sb.toString();
        }
    }

    public StatusBarStateControllerImpl(UiEventLogger uiEventLogger, DumpManager dumpManager, InteractionJankMonitor interactionJankMonitor, ShadeExpansionStateManager shadeExpansionStateManager) {
        int i = 0;
        this.mUiEventLogger = uiEventLogger;
        this.mInteractionJankMonitor = interactionJankMonitor;
        for (int i2 = 0; i2 < 32; i2++) {
            this.mHistoricalRecords[i2] = new HistoricalState(i);
        }
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                Boolean valueOf = Boolean.valueOf(z);
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                if (statusBarStateControllerImpl.mIsExpanded != valueOf.booleanValue()) {
                    statusBarStateControllerImpl.mIsExpanded = valueOf.booleanValue();
                    DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                    Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                    while (it.hasNext()) {
                        ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onExpandedChanged(statusBarStateControllerImpl.mIsExpanded);
                    }
                    DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                }
            }
        });
        dumpManager.registerDumpable(this);
    }

    public final void addListenerInternalLocked(StatusBarStateController.StateListener stateListener, int i) {
        ArrayList arrayList = this.mListeners;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((SysuiStatusBarStateController.RankedListener) it.next()).mListener.equals(stateListener)) {
                return;
            }
        }
        arrayList.add(new SysuiStatusBarStateController.RankedListener(stateListener, i));
        arrayList.sort(sComparator);
    }

    public final void beginInteractionJankMonitor() {
        View view;
        boolean z = this.mIsDozing;
        boolean z2 = (z && this.mDozeAmount == 0.0f) || (!z && this.mDozeAmount == 1.0f);
        InteractionJankMonitor interactionJankMonitor = this.mInteractionJankMonitor;
        if (interactionJankMonitor == null || (view = this.mView) == null || !view.isAttachedToWindow()) {
            return;
        }
        if (z2) {
            Choreographer.getInstance().postCallback(1, new Runnable() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarStateControllerImpl.this.beginInteractionJankMonitor();
                }
            }, null);
        } else {
            interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(this.mIsDozing ? 24 : 23, this.mView).setDeferMonitorForAnimationStart(false));
        }
    }

    public ObjectAnimator createDarkAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SET_DARK_AMOUNT_PROPERTY, this.mDozeAmountTarget);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
        ofFloat.setDuration(500L);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                InteractionJankMonitor interactionJankMonitor = statusBarStateControllerImpl.mInteractionJankMonitor;
                if (interactionJankMonitor == null) {
                    return;
                }
                interactionJankMonitor.cancel(statusBarStateControllerImpl.mIsDozing ? 24 : 23);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                InteractionJankMonitor interactionJankMonitor = statusBarStateControllerImpl.mInteractionJankMonitor;
                if (interactionJankMonitor == null) {
                    return;
                }
                interactionJankMonitor.end(statusBarStateControllerImpl.mIsDozing ? 24 : 23);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                boolean z = StatusBarStateControllerImpl.DEBUG_IMMERSIVE_APPS;
                statusBarStateControllerImpl.beginInteractionJankMonitor();
            }
        });
        ofFloat.start();
        return ofFloat;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        HistoricalState[] historicalStateArr;
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "StatusBarStateController: ", " mState=");
        m75m.append(this.mState);
        m75m.append(" (");
        m75m.append(StatusBarState.toString(this.mState));
        m75m.append(")");
        printWriter.println(m75m.toString());
        printWriter.println(" mLastState=" + this.mLastState + " (" + StatusBarState.toString(this.mLastState) + ")");
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder(" mLeaveOpenOnKeyguardHide="), this.mLeaveOpenOnKeyguardHide, printWriter, " mKeyguardRequested="), this.mKeyguardRequested, printWriter, " mIsDozing="), this.mIsDozing, printWriter, " mIsDreaming="), this.mIsDreaming, printWriter, " mListeners{");
        ArrayList arrayList = this.mListeners;
        m64m.append(arrayList.size());
        m64m.append("}=");
        printWriter.println(m64m.toString());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            printWriter.println("    " + ((SysuiStatusBarStateController.RankedListener) it.next()).mListener);
        }
        printWriter.println(" Historical states:");
        int i = 0;
        int i2 = 0;
        while (true) {
            historicalStateArr = this.mHistoricalRecords;
            if (i >= 32) {
                break;
            }
            if (historicalStateArr[i].mTimestamp != 0) {
                i2++;
            }
            i++;
        }
        for (int i3 = this.mHistoryIndex + 32; i3 >= ((this.mHistoryIndex + 32) - i2) + 1; i3 += -1) {
            printWriter.println("  (" + (((this.mHistoryIndex + 32) - i3) + 1) + ")" + historicalStateArr[i3 & 31]);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final float getDozeAmount() {
        return this.mDozeAmount;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final int getState() {
        return this.mState;
    }

    public final boolean goingToFullShade() {
        return this.mState == 0 && this.mLeaveOpenOnKeyguardHide;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final boolean isDozing() {
        return this.mIsDozing;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final boolean isDreaming() {
        return this.mIsDreaming;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final boolean isExpanded() {
        return this.mIsExpanded;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController
    public final boolean isPulsing() {
        return this.mPulsing;
    }

    public final void recordHistoricalState(int i, int i2, boolean z) {
        Trace.traceCounter(4096L, "statusBarState", i);
        int i3 = (this.mHistoryIndex + 1) % 32;
        this.mHistoryIndex = i3;
        HistoricalState historicalState = this.mHistoricalRecords[i3];
        historicalState.mNewState = i;
        historicalState.mLastState = i2;
        historicalState.mTimestamp = System.currentTimeMillis();
        historicalState.mUpcoming = z;
    }

    public final void setDozeAmountInternal(float f) {
        if (Float.compare(f, this.mDozeAmount) == 0) {
            return;
        }
        this.mDozeAmount = f;
        float interpolation = this.mDozeInterpolator.getInterpolation(f);
        synchronized (this.mListeners) {
            DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setDozeAmount");
            Iterator it = new ArrayList(this.mListeners).iterator();
            while (it.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onDozeAmountChanged(this.mDozeAmount, interpolation);
            }
            DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setDozeAmount");
        }
    }

    public final boolean setState(int i, boolean z) {
        if (i > 2 || i < 0) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid state ", i));
        }
        if (!z && i == this.mState && i == this.mUpcomingState) {
            return false;
        }
        Log.m139d("SbStateController", "setState: %s -> %s", StatusBarState.toString(this.mState), StatusBarState.toString(i));
        if (i != this.mUpcomingState) {
            android.util.Log.d("SbStateController", "setState: requested state " + StatusBarState.toString(i) + "!= upcomingState: " + StatusBarState.toString(this.mUpcomingState) + ". This usually means the status bar state transition was interrupted before the upcoming state could be applied.");
        }
        recordHistoricalState(i, this.mState, false);
        if (this.mState == 0 && i == 2) {
            android.util.Log.e("SbStateController", "Invalid state transition: SHADE -> SHADE_LOCKED", new Throwable());
        }
        synchronized (this.mListeners) {
            String str = "StatusBarStateControllerImpl#setState(" + i + ")";
            DejankUtils.startDetectingBlockingIpcs(str);
            Iterator it = new ArrayList(this.mListeners).iterator();
            while (it.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onStatePreChange(this.mState, i);
            }
            this.mLastState = this.mState;
            this.mState = i;
            updateUpcomingState(i);
            UiEventLogger uiEventLogger = this.mUiEventLogger;
            int i2 = this.mState;
            uiEventLogger.log(i2 != 0 ? i2 != 1 ? i2 != 2 ? StatusBarStateEvent.STATUS_BAR_STATE_UNKNOWN : StatusBarStateEvent.STATUS_BAR_STATE_SHADE_LOCKED : StatusBarStateEvent.STATUS_BAR_STATE_KEYGUARD : StatusBarStateEvent.STATUS_BAR_STATE_SHADE);
            Trace.instantForTrack(4096L, "UI Events", "StatusBarState " + str);
            Iterator it2 = new ArrayList(this.mListeners).iterator();
            while (it2.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it2.next()).mListener.onStateChanged(this.mState);
            }
            Iterator it3 = new ArrayList(this.mListeners).iterator();
            while (it3.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it3.next()).mListener.onStatePostChange();
            }
            DejankUtils.stopDetectingBlockingIpcs(str);
        }
        return true;
    }

    public final void updateUpcomingState(int i) {
        if (this.mUpcomingState != i) {
            this.mUpcomingState = i;
            Iterator it = new ArrayList(this.mListeners).iterator();
            while (it.hasNext()) {
                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onUpcomingStateChanged(this.mUpcomingState);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(StatusBarStateController.StateListener stateListener) {
        synchronized (this.mListeners) {
            addListenerInternalLocked(stateListener, Integer.MAX_VALUE);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(final StatusBarStateController.StateListener stateListener) {
        synchronized (this.mListeners) {
            this.mListeners.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((SysuiStatusBarStateController.RankedListener) obj).mListener.equals(StatusBarStateController.StateListener.this);
                }
            });
        }
    }
}
