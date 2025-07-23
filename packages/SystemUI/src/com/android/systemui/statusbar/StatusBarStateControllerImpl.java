package com.android.systemui.statusbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Debug;
import android.os.SystemProperties;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.format.DateFormat;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
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
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarStateControllerImpl implements SysuiStatusBarStateController, CallbackController {
    public static final AnonymousClass1 SET_DARK_AMOUNT_PROPERTY;
    public static final Comparator sComparator;
    public ValueAnimator mDarkAnimator;
    public float mDozeAmount;
    public float mDozeAmountTarget;
    public boolean mIsDozing;
    public boolean mIsDreaming;
    public boolean mIsExpanded;
    public final JavaAdapter mJavaAdapter;
    public final Lazy mKeyguardClockInteractorLazy;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HistoricalState {
        public int mLastState;
        public int mNewState;
        public long mTimestamp;
        public boolean mUpcoming;

        public /* synthetic */ HistoricalState(int i) {
            this();
        }

        public final String toString() {
            if (this.mTimestamp == 0) {
                return "Empty ".concat(getClass().getSimpleName());
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

        private HistoricalState() {
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
    }

    public StatusBarStateControllerImpl(UiEventLogger uiEventLogger, JavaAdapter javaAdapter, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, Lazy lazy8, Lazy lazy9) {
        int i = 0;
        this.mUiEventLogger = uiEventLogger;
        this.mJavaAdapter = javaAdapter;
        this.mKeyguardTransitionInteractorLazy = lazy2;
        this.mShadeInteractorLazy = lazy3;
        this.mKeyguardClockInteractorLazy = lazy7;
        for (int i2 = 0; i2 < 32; i2++) {
            this.mHistoricalRecords[i2] = new HistoricalState(i);
        }
    }

    public final void addListenerInternalLocked(StatusBarStateController.StateListener stateListener, int i) {
        ArrayList arrayList = this.mListeners;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (((SysuiStatusBarStateController.RankedListener) obj).mListener.equals(stateListener)) {
                return;
            }
        }
        this.mListeners.add(new SysuiStatusBarStateController.RankedListener(stateListener, i));
        this.mListeners.sort(sComparator);
    }

    public ObjectAnimator createDarkAnimator() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SET_DARK_AMOUNT_PROPERTY, this.mDozeAmountTarget);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.setDuration(500L);
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
        StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder(" mLeaveOpenOnKeyguardHide="), this.mLeaveOpenOnKeyguardHide, printWriter, " mKeyguardRequested="), this.mKeyguardRequested, printWriter, " mIsDozing="), this.mIsDozing, printWriter, " mIsDreaming="), this.mIsDreaming, printWriter, " mListeners{");
        m2.append(this.mListeners.size());
        m2.append("}=");
        printWriter.println(m2.toString());
        ArrayList arrayList = this.mListeners;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            printWriter.println("    " + ((SysuiStatusBarStateController.RankedListener) obj).mListener);
        }
        printWriter.println(" Historical states:");
        int i3 = 0;
        while (true) {
            historicalStateArr = this.mHistoricalRecords;
            if (i >= 32) {
                break;
            }
            if (historicalStateArr[i].mTimestamp != 0) {
                i3++;
            }
            i++;
        }
        for (int i4 = this.mHistoryIndex + 32; i4 >= ((this.mHistoryIndex + 32) - i3) + 1; i4 += -1) {
            printWriter.println("  (" + (((this.mHistoryIndex + 32) - i4) + 1) + ")" + historicalStateArr[i4 & 31]);
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
        TrackTracer.instantForGroup(i, "statusBar", "state");
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
            try {
                String concat = getClass().getSimpleName().concat("#setDozeAmount");
                DejankUtils.startDetectingBlockingIpcs(concat);
                ArrayList arrayList = new ArrayList(this.mListeners);
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SysuiStatusBarStateController.RankedListener) obj).mListener.onDozeAmountChanged(this.mDozeAmount, interpolation);
                }
                DejankUtils.stopDetectingBlockingIpcs(concat);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setLeaveOpenOnKeyguardHide(boolean z) {
        if (this.mLeaveOpenOnKeyguardHide != z) {
            Log.d("SbStateController", "leaveOpen : " + z);
            Log.d("SbStateController", Debug.getCallers(5, ""));
        }
        this.mLeaveOpenOnKeyguardHide = z;
    }

    public final boolean setState(int i, boolean z) {
        int i2 = SceneContainerFlag.$r8$clinit;
        if (i > 2 || i < 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid state "));
        }
        int i3 = 0;
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
                String str = getClass().getSimpleName() + "#setState(" + i + ")";
                DejankUtils.startDetectingBlockingIpcs(str);
                ArrayList arrayList = new ArrayList(this.mListeners);
                int size = arrayList.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj = arrayList.get(i4);
                    i4++;
                    ((SysuiStatusBarStateController.RankedListener) obj).mListener.onStatePreChange(this.mState, i);
                }
                this.mLastState = this.mState;
                this.mState = i;
                updateUpcomingState(i);
                UiEventLogger uiEventLogger = this.mUiEventLogger;
                int i5 = this.mState;
                uiEventLogger.log(i5 != 0 ? i5 != 1 ? i5 != 2 ? StatusBarStateEvent.STATUS_BAR_STATE_UNKNOWN : StatusBarStateEvent.STATUS_BAR_STATE_SHADE_LOCKED : StatusBarStateEvent.STATUS_BAR_STATE_KEYGUARD : StatusBarStateEvent.STATUS_BAR_STATE_SHADE);
                Trace.instantForTrack(4096L, "UI Events", "StatusBarState " + str);
                ArrayList arrayList2 = new ArrayList(this.mListeners);
                int size2 = arrayList2.size();
                int i6 = 0;
                while (i6 < size2) {
                    Object obj2 = arrayList2.get(i6);
                    i6++;
                    ((SysuiStatusBarStateController.RankedListener) obj2).mListener.onStateChanged(this.mState);
                }
                ArrayList arrayList3 = new ArrayList(this.mListeners);
                int size3 = arrayList3.size();
                while (i3 < size3) {
                    Object obj3 = arrayList3.get(i3);
                    i3++;
                    ((SysuiStatusBarStateController.RankedListener) obj3).mListener.onStatePostChange();
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
        KeyguardTransitionInteractor keyguardTransitionInteractor = (KeyguardTransitionInteractor) this.mKeyguardTransitionInteractorLazy.get();
        SceneKey sceneKey = Scenes.Communal;
        Flow isFinishedIn = keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE);
        final int i = 0;
        Consumer consumer = new Consumer(this) { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ StatusBarStateControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = 0;
                int i3 = i;
                StatusBarStateControllerImpl statusBarStateControllerImpl = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        Comparator comparator = StatusBarStateControllerImpl.sComparator;
                        statusBarStateControllerImpl.getClass();
                        if (bool.booleanValue()) {
                            if (!statusBarStateControllerImpl.mSettingHelper.isRemoveAnimation() || !statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                                statusBarStateControllerImpl.setLeaveOpenOnKeyguardHide(false);
                                break;
                            }
                        }
                        break;
                    default:
                        if (statusBarStateControllerImpl.mIsExpanded != bool.booleanValue()) {
                            statusBarStateControllerImpl.mIsExpanded = bool.booleanValue();
                            String concat = statusBarStateControllerImpl.getClass().getSimpleName().concat("#setIsExpanded");
                            DejankUtils.startDetectingBlockingIpcs(concat);
                            ArrayList arrayList = new ArrayList(statusBarStateControllerImpl.mListeners);
                            int size = arrayList.size();
                            while (i2 < size) {
                                Object obj2 = arrayList.get(i2);
                                i2++;
                                ((SysuiStatusBarStateController.RankedListener) obj2).mListener.onExpandedChanged(statusBarStateControllerImpl.mIsExpanded);
                            }
                            DejankUtils.stopDetectingBlockingIpcs(concat);
                            break;
                        }
                        break;
                }
            }
        };
        JavaAdapter javaAdapter = this.mJavaAdapter;
        javaAdapter.alwaysCollectFlow(isFinishedIn, consumer);
        final int i2 = 1;
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) ((ShadeInteractor) this.mShadeInteractorLazy.get())).baseShadeInteractor.isAnyExpanded(), new Consumer(this) { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda3
            public final /* synthetic */ StatusBarStateControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = 0;
                int i3 = i2;
                StatusBarStateControllerImpl statusBarStateControllerImpl = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        Comparator comparator = StatusBarStateControllerImpl.sComparator;
                        statusBarStateControllerImpl.getClass();
                        if (bool.booleanValue()) {
                            if (!statusBarStateControllerImpl.mSettingHelper.isRemoveAnimation() || !statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide) {
                                statusBarStateControllerImpl.setLeaveOpenOnKeyguardHide(false);
                                break;
                            }
                        }
                        break;
                    default:
                        if (statusBarStateControllerImpl.mIsExpanded != bool.booleanValue()) {
                            statusBarStateControllerImpl.mIsExpanded = bool.booleanValue();
                            String concat = statusBarStateControllerImpl.getClass().getSimpleName().concat("#setIsExpanded");
                            DejankUtils.startDetectingBlockingIpcs(concat);
                            ArrayList arrayList = new ArrayList(statusBarStateControllerImpl.mListeners);
                            int size = arrayList.size();
                            while (i22 < size) {
                                Object obj2 = arrayList.get(i22);
                                i22++;
                                ((SysuiStatusBarStateController.RankedListener) obj2).mListener.onExpandedChanged(statusBarStateControllerImpl.mIsExpanded);
                            }
                            DejankUtils.stopDetectingBlockingIpcs(concat);
                            break;
                        }
                        break;
                }
            }
        });
        int i3 = SceneContainerFlag.$r8$clinit;
    }

    public final void updateUpcomingState(int i) {
        if (this.mUpcomingState != i) {
            this.mUpcomingState = i;
            ArrayList arrayList = new ArrayList(this.mListeners);
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ((SysuiStatusBarStateController.RankedListener) obj).mListener.onUpcomingStateChanged(this.mUpcomingState);
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
                    StatusBarStateController.StateListener stateListener2 = StatusBarStateController.StateListener.this;
                    Comparator comparator = StatusBarStateControllerImpl.sComparator;
                    return ((SysuiStatusBarStateController.RankedListener) obj).mListener.equals(stateListener2);
                }
            });
        }
    }
}
