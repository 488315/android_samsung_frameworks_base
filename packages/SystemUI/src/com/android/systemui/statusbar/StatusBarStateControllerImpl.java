package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.SystemProperties;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.format.DateFormat;
import android.util.FloatProperty;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlinx.coroutines.flow.Flow;

public final class StatusBarStateControllerImpl implements SysuiStatusBarStateController, CallbackController {
    public static final AnonymousClass1 SET_DARK_AMOUNT_PROPERTY;
    public static final Comparator sComparator;
    public KeyguardClockSwitch mClockSwitchView;
    public ValueAnimator mDarkAnimator;
    public float mDozeAmount;
    public float mDozeAmountTarget;
    public final Lazy mInteractionJankMonitorLazy;
    public boolean mIsDozing;
    public boolean mIsDreaming;
    public boolean mIsExpanded;
    public final JavaAdapter mJavaAdapter;
    public boolean mKeyguardRequested;
    public final Lazy mKeyguardTransitionInteractorLazy;
    public int mLastState;
    public boolean mLeaveOpenOnKeyguardHide;
    public LooperSlowLogController mLooperSlowLogController;
    public boolean mPulsing;
    SettingsHelper mSettingHelper;
    public final Lazy mShadeInteractorLazy;
    public int mState;
    public final UiEventLogger mUiEventLogger;
    public int mUpcomingState;
    public View mView;
    public final ArrayList mListeners = new ArrayList();
    public int mHistoryIndex = 0;
    public final HistoricalState[] mHistoricalRecords = new HistoricalState[32];
    public Interpolator mDozeInterpolator = Interpolators.FAST_OUT_SLOW_IN;

    public final class HistoricalState {
        public int mLastState;
        public int mNewState;
        public long mTimestamp;
        public boolean mUpcoming;

        private HistoricalState() {
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
            Comparator comparator = StatusBarStateControllerImpl.sComparator;
            sb.append(StatusBarState.toString(i));
            sb.append(") lastState=");
            sb.append(this.mLastState);
            sb.append("(");
            sb.append(StatusBarState.toString(this.mLastState));
            sb.append(") timestamp=");
            sb.append(DateFormat.format("MM-dd HH:mm:ss", this.mTimestamp));
            return sb.toString();
        }

        public /* synthetic */ HistoricalState(int i) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.StatusBarStateControllerImpl$1] */
    static {
        SystemProperties.getBoolean("persist.debug.immersive_apps", false);
        sComparator = Comparator.comparingInt(new StatusBarStateControllerImpl$$ExternalSyntheticLambda1());
        SET_DARK_AMOUNT_PROPERTY = new FloatProperty("mDozeAmount") { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((StatusBarStateControllerImpl) obj).mDozeAmount);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                Comparator comparator = StatusBarStateControllerImpl.sComparator;
                ((StatusBarStateControllerImpl) obj).setDozeAmountInternal(f);
            }
        };
        Map.of(Scenes.Lockscreen, 1, Scenes.Bouncer, 1, Scenes.Communal, 1, Scenes.Shade, 2, Scenes.NotificationsShade, 2, Scenes.QuickSettings, 2, Scenes.QuickSettingsShade, 2, Scenes.Gone, 0);
    }

    public StatusBarStateControllerImpl(UiEventLogger uiEventLogger, Lazy lazy, JavaAdapter javaAdapter, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6) {
        int i = 0;
        this.mUiEventLogger = uiEventLogger;
        this.mInteractionJankMonitorLazy = lazy;
        this.mJavaAdapter = javaAdapter;
        this.mKeyguardTransitionInteractorLazy = lazy2;
        this.mShadeInteractorLazy = lazy3;
        for (int i2 = 0; i2 < 32; i2++) {
            this.mHistoricalRecords[i2] = new HistoricalState(i);
        }
    }

    public final void addListenerInternalLocked(StatusBarStateController.StateListener stateListener, int i) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            if (((SysuiStatusBarStateController.RankedListener) it.next()).mListener.equals(stateListener)) {
                return;
            }
        }
        this.mListeners.add(new SysuiStatusBarStateController.RankedListener(stateListener, i));
        this.mListeners.sort(sComparator);
    }

    public final void beginInteractionJankMonitor() {
        View view;
        boolean z = this.mIsDozing;
        boolean z2 = (z && this.mDozeAmount == 0.0f) || (!z && this.mDozeAmount == 1.0f);
        InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) this.mInteractionJankMonitorLazy.get();
        if (interactionJankMonitor == null || (view = this.mView) == null || !view.isAttachedToWindow()) {
            return;
        }
        if (z2) {
            Choreographer.getInstance().postCallback(1, new Runnable() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    StatusBarStateControllerImpl.this.beginInteractionJankMonitor();
                }
            }, null);
        } else {
            interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(this.mIsDozing ? 24 : 23, this.mView).setTag(getClockId()).setDeferMonitorForAnimationStart(false));
        }
    }

    public ObjectAnimator createDarkAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SET_DARK_AMOUNT_PROPERTY, this.mDozeAmountTarget);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.setDuration(500L);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) statusBarStateControllerImpl.mInteractionJankMonitorLazy.get();
                if (interactionJankMonitor == null) {
                    return;
                }
                interactionJankMonitor.cancel(statusBarStateControllerImpl.mIsDozing ? 24 : 23);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                Comparator comparator = StatusBarStateControllerImpl.sComparator;
                InteractionJankMonitor interactionJankMonitor = (InteractionJankMonitor) statusBarStateControllerImpl.mInteractionJankMonitorLazy.get();
                if (interactionJankMonitor == null) {
                    return;
                }
                interactionJankMonitor.end(statusBarStateControllerImpl.mIsDozing ? 24 : 23);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = StatusBarStateControllerImpl.this;
                Comparator comparator = StatusBarStateControllerImpl.sComparator;
                statusBarStateControllerImpl.beginInteractionJankMonitor();
            }
        });
        ofFloat.start();
        return ofFloat;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        HistoricalState[] historicalStateArr;
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "StatusBarStateController: ", " mState=");
        m.append(this.mState);
        m.append(" (");
        m.append(StatusBarState.toString(this.mState));
        m.append(")");
        printWriter.println(m.toString());
        printWriter.println(" mLastState=" + this.mLastState + " (" + StatusBarState.toString(this.mLastState) + ")");
        StringBuilder m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder(" mLeaveOpenOnKeyguardHide="), this.mLeaveOpenOnKeyguardHide, printWriter, " mKeyguardRequested="), this.mKeyguardRequested, printWriter, " mIsDozing="), this.mIsDozing, printWriter, " mIsDreaming="), this.mIsDreaming, printWriter, " mListeners{");
        m2.append(this.mListeners.size());
        m2.append("}=");
        printWriter.println(m2.toString());
        Iterator it = this.mListeners.iterator();
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

    public final String getClockId() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        KeyguardClockSwitch keyguardClockSwitch = this.mClockSwitchView;
        if (keyguardClockSwitch == null) {
            Log.e("SbStateController", "Clock container was missing");
            return "CLOCK_MISSING";
        }
        ClockController clockController = keyguardClockSwitch.mClock;
        return clockController == null ? "CLOCK_MISSING" : clockController.getConfig().getId();
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
        InteractionJankMonitor interactionJankMonitor;
        if (Float.compare(f, this.mDozeAmount) == 0) {
            return;
        }
        this.mDozeAmount = f;
        float interpolation = this.mDozeInterpolator.getInterpolation(f);
        if (interpolation > 0.99f && this.mIsDozing && (interactionJankMonitor = (InteractionJankMonitor) this.mInteractionJankMonitorLazy.get()) != null) {
            interactionJankMonitor.end(this.mIsDozing ? 24 : 23);
        }
        synchronized (this.mListeners) {
            try {
                DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setDozeAmount");
                Iterator it = new ArrayList(this.mListeners).iterator();
                while (it.hasNext()) {
                    ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onDozeAmountChanged(this.mDozeAmount, interpolation);
                }
                DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setDozeAmount");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setState(int i, boolean z) {
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        if (i > 2 || i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid state "));
        }
        if (!z && i == this.mState && i == this.mUpcomingState) {
            return false;
        }
        com.android.systemui.keyguard.Log.d("SbStateController", "setState: %s -> %s", StatusBarState.toString(this.mState), StatusBarState.toString(i));
        if (i != this.mUpcomingState) {
            Log.d("SbStateController", "setState: requested state " + StatusBarState.toString(i) + "!= upcomingState: " + StatusBarState.toString(this.mUpcomingState) + ". This usually means the status bar state transition was interrupted before the upcoming state could be applied.");
        }
        recordHistoricalState(i, this.mState, false);
        if (this.mState == 0 && i == 2) {
            Log.e("SbStateController", "Invalid state transition: SHADE -> SHADE_LOCKED", new Throwable());
        }
        synchronized (this.mListeners) {
            try {
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
                int i3 = this.mState;
                uiEventLogger.log(i3 != 0 ? i3 != 1 ? i3 != 2 ? StatusBarStateEvent.STATUS_BAR_STATE_UNKNOWN : StatusBarStateEvent.STATUS_BAR_STATE_SHADE_LOCKED : StatusBarStateEvent.STATUS_BAR_STATE_KEYGUARD : StatusBarStateEvent.STATUS_BAR_STATE_SHADE);
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
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flow isFinishedInState = ((KeyguardTransitionInteractor) this.mKeyguardTransitionInteractorLazy.get()).isFinishedInState(KeyguardState.GONE);
        final int i = 0;
        Consumer consumer = new Consumer(this) { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ StatusBarStateControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                StatusBarStateControllerImpl statusBarStateControllerImpl = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i2) {
                    case 0:
                        statusBarStateControllerImpl.getClass();
                        if (bool.booleanValue()) {
                            if (!statusBarStateControllerImpl.mSettingHelper.isRemoveAnimation() || !statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
                                break;
                            }
                        }
                        break;
                    default:
                        if (statusBarStateControllerImpl.mIsExpanded != bool.booleanValue()) {
                            statusBarStateControllerImpl.mIsExpanded = bool.booleanValue();
                            DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                            Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                            while (it.hasNext()) {
                                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onExpandedChanged(statusBarStateControllerImpl.mIsExpanded);
                            }
                            DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                            break;
                        }
                        break;
                }
            }
        };
        JavaAdapter javaAdapter = this.mJavaAdapter;
        javaAdapter.alwaysCollectFlow(isFinishedInState, consumer);
        final int i2 = 1;
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) ((ShadeInteractor) this.mShadeInteractorLazy.get())).baseShadeInteractor.isAnyExpanded(), new Consumer(this) { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda4
            public final /* synthetic */ StatusBarStateControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                StatusBarStateControllerImpl statusBarStateControllerImpl = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i22) {
                    case 0:
                        statusBarStateControllerImpl.getClass();
                        if (bool.booleanValue()) {
                            if (!statusBarStateControllerImpl.mSettingHelper.isRemoveAnimation() || !statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = false;
                                break;
                            }
                        }
                        break;
                    default:
                        if (statusBarStateControllerImpl.mIsExpanded != bool.booleanValue()) {
                            statusBarStateControllerImpl.mIsExpanded = bool.booleanValue();
                            DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                            Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                            while (it.hasNext()) {
                                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onExpandedChanged(statusBarStateControllerImpl.mIsExpanded);
                            }
                            DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsExpanded");
                            break;
                        }
                        break;
                }
            }
        });
        int i3 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
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
