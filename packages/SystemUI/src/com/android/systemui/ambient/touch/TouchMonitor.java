package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.GestureDetector;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.InputSessionComponent;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.display.DisplayHelper;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TouchMonitor {
    public static int sNextInstanceId;
    public final Executor mBackgroundExecutor;
    public Job mBoundsFlow;
    public final ConfigurationInteractor mConfigurationInteractor;
    public InputSession mCurrentInputSession;
    public final int mDisplayId;
    public AnonymousClass2 mGestureExclusionListener;
    public final Collection mHandlers;
    public boolean mInitialized;
    public final InputSessionComponent.Factory mInputSessionFactory;
    public final Lifecycle mLifecycle;
    public final Logger mLogger;
    public final String mLoggingName;
    public final Executor mMainExecutor;
    public Rect mMaxBounds;
    public boolean mStopMonitoringPending;
    public final IWindowManager mWindowManagerService;
    public Rect mExclusionRect = new Rect();
    public final TouchMonitor$$ExternalSyntheticLambda0 mMaxBoundsConsumer = new TouchMonitor$$ExternalSyntheticLambda0(this, 0);
    public final AnonymousClass1 mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.android.systemui.ambient.touch.TouchMonitor.1
        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public final void onDestroy$1() {
            TouchMonitor.this.destroy();
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public final void onPause$1() {
            TouchMonitor.this.stopMonitoring(false);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        public final void onResume$1() {
            TouchMonitor.this.startMonitoring();
        }
    };
    public final HashSet mActiveTouchSessions = new HashSet();
    public final AnonymousClass3 mInputEventListener = new AnonymousClass3();
    public final AnonymousClass4 mOnGestureListener = new GestureDetector.OnGestureListener() { // from class: com.android.systemui.ambient.touch.TouchMonitor.4
        public final boolean evaluate(final Evaluator evaluator) {
            final HashSet hashSet = new HashSet();
            boolean anyMatch = TouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.ambient.touch.TouchMonitor$4$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    final TouchMonitor.Evaluator evaluator2 = TouchMonitor.Evaluator.this;
                    Set set = hashSet;
                    TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) obj;
                    boolean anyMatch2 = touchSessionImpl.mGestureListeners.stream().map(new Function() { // from class: com.android.systemui.ambient.touch.TouchMonitor$4$$ExternalSyntheticLambda10
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            return Boolean.valueOf(TouchMonitor.Evaluator.this.evaluate((GestureDetector.OnGestureListener) obj2));
                        }
                    }).anyMatch(new TouchMonitor$4$$ExternalSyntheticLambda7(1));
                    if (anyMatch2) {
                        ((HashSet) set).add(touchSessionImpl);
                    }
                    return Boolean.valueOf(anyMatch2);
                }
            }).anyMatch(new TouchMonitor$4$$ExternalSyntheticLambda7(0));
            if (anyMatch) {
                TouchMonitor touchMonitor = TouchMonitor.this;
                Collection<?> collection = (Collection) touchMonitor.mActiveTouchSessions.stream().filter(new Predicate() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda9
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return !hashSet.contains((TouchMonitor.TouchSessionImpl) obj);
                    }
                }).collect(Collectors.toCollection(new TouchMonitor$$ExternalSyntheticLambda10()));
                collection.forEach(new TouchMonitor$$ExternalSyntheticLambda6(1));
                touchMonitor.mActiveTouchSessions.removeAll(collection);
            }
            return anyMatch;
        }

        public final void observe(Consumer consumer) {
            TouchMonitor.this.mActiveTouchSessions.stream().map(new TouchMonitor$$ExternalSyntheticLambda7(4)).flatMap(new TouchMonitor$$ExternalSyntheticLambda7(3)).forEach(new TouchMonitor$$ExternalSyntheticLambda0(consumer, 2));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return evaluate(new TouchMonitor$4$$ExternalSyntheticLambda2(motionEvent, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new TouchMonitor$4$$ExternalSyntheticLambda4(motionEvent, motionEvent2, f, f2, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            observe(new TouchMonitor$4$$ExternalSyntheticLambda0(motionEvent, 0));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new TouchMonitor$4$$ExternalSyntheticLambda4(motionEvent, motionEvent2, f, f2, 0));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onShowPress(MotionEvent motionEvent) {
            observe(new TouchMonitor$4$$ExternalSyntheticLambda0(motionEvent, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return evaluate(new TouchMonitor$4$$ExternalSyntheticLambda2(motionEvent, 0));
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.ambient.touch.TouchMonitor$2, reason: invalid class name */
    public class AnonymousClass2 extends ISystemGestureExclusionListener.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass2() {
        }

        public final void onSystemGestureExclusionChanged(int i, Region region, Region region2) {
            Rect bounds = region.getBounds();
            if (TouchMonitor.this.mExclusionRect.equals(bounds)) {
                return;
            }
            TouchMonitor touchMonitor = TouchMonitor.this;
            touchMonitor.mExclusionRect = bounds;
            touchMonitor.mLogger.i(new TouchMonitor$$ExternalSyntheticLambda2(2), new TouchMonitor$$ExternalSyntheticLambda3(bounds, 2));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.ambient.touch.TouchMonitor$3, reason: invalid class name */
    public class AnonymousClass3 implements InputChannelCompat$InputEventListener {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
        public final void onInputEvent(final InputEvent inputEvent) {
            TouchMonitor touchMonitor = TouchMonitor.this;
            if (touchMonitor.mActiveTouchSessions.isEmpty()) {
                HashMap hashMap = new HashMap();
                for (TouchHandler touchHandler : touchMonitor.mHandlers) {
                    if (touchHandler.isEnabled().booleanValue()) {
                        Rect rect = touchMonitor.mMaxBounds;
                        Region obtain = Region.obtain();
                        touchHandler.getTouchInitiationRegion(rect, obtain, touchMonitor.mExclusionRect);
                        if (!obtain.isEmpty()) {
                            if (inputEvent instanceof MotionEvent) {
                                MotionEvent motionEvent = (MotionEvent) inputEvent;
                                if (!obtain.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()))) {
                                }
                            }
                        }
                        TouchSessionImpl touchSessionImpl = new TouchSessionImpl(touchMonitor, rect, null);
                        touchMonitor.mActiveTouchSessions.add(touchSessionImpl);
                        hashMap.put(touchHandler, touchSessionImpl);
                    }
                }
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.ambient.touch.TouchMonitor$3$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        TouchMonitor.AnonymousClass3 anonymousClass3 = TouchMonitor.AnonymousClass3.this;
                        InputEvent inputEvent2 = inputEvent;
                        final TouchHandler touchHandler2 = (TouchHandler) obj;
                        final TouchHandler.TouchSession touchSession = (TouchHandler.TouchSession) obj2;
                        anonymousClass3.getClass();
                        if (inputEvent2 instanceof MotionEvent) {
                            MotionEvent motionEvent2 = (MotionEvent) inputEvent2;
                            final int round = Math.round(motionEvent2.getX());
                            final int round2 = Math.round(motionEvent2.getY());
                            TouchMonitor.this.mLogger.i(new TouchMonitor$$ExternalSyntheticLambda2(3), new Function1() { // from class: com.android.systemui.ambient.touch.TouchMonitor$3$$ExternalSyntheticLambda5
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj3) {
                                    LogMessage logMessage = (LogMessage) obj3;
                                    logMessage.setStr1(TouchHandler.this.getClass().getSimpleName());
                                    logMessage.setLong1(round);
                                    logMessage.setLong2(round2);
                                    logMessage.setInt1(touchSession.hashCode());
                                    return Unit.INSTANCE;
                                }
                            });
                        }
                        touchHandler2.onSessionStart(touchSession);
                    }
                });
            }
            touchMonitor.mActiveTouchSessions.stream().map(new TouchMonitor$$ExternalSyntheticLambda7(2)).flatMap(new TouchMonitor$$ExternalSyntheticLambda7(3)).forEach(new TouchMonitor$$ExternalSyntheticLambda0(inputEvent, 1));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Evaluator {
        boolean evaluate(GestureDetector.OnGestureListener onGestureListener);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TouchSessionImpl implements TouchHandler.TouchSession {
        public final Rect mBounds;
        public final TouchSessionImpl mPredecessor;
        public final TouchMonitor mTouchMonitor;
        public final HashSet mEventListeners = new HashSet();
        public final HashSet mGestureListeners = new HashSet();
        public final HashSet mCallbacks = new HashSet();

        /* renamed from: -$$Nest$monRemoved, reason: not valid java name */
        public static void m1010$$Nest$monRemoved(TouchSessionImpl touchSessionImpl) {
            touchSessionImpl.mEventListeners.clear();
            touchSessionImpl.mGestureListeners.clear();
            Iterator it = touchSessionImpl.mCallbacks.iterator();
            while (it.hasNext()) {
                ((TouchHandler.TouchSession.Callback) it.next()).onRemoved();
                it.remove();
            }
        }

        public TouchSessionImpl(TouchMonitor touchMonitor, Rect rect, TouchSessionImpl touchSessionImpl) {
            this.mPredecessor = touchSessionImpl;
            this.mTouchMonitor = touchMonitor;
            this.mBounds = rect;
        }

        public final CallbackToFutureAdapter.SafeFuture pop() {
            final TouchMonitor touchMonitor = this.mTouchMonitor;
            touchMonitor.getClass();
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda12
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                    final TouchMonitor touchMonitor2 = TouchMonitor.this;
                    Executor executor = touchMonitor2.mMainExecutor;
                    final TouchMonitor.TouchSessionImpl touchSessionImpl = this;
                    executor.execute(new Runnable() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            TouchMonitor touchMonitor3 = TouchMonitor.this;
                            TouchMonitor.TouchSessionImpl touchSessionImpl2 = touchSessionImpl;
                            CallbackToFutureAdapter.Completer completer2 = completer;
                            touchMonitor3.mLogger.i(new TouchMonitor$$ExternalSyntheticLambda2(1), new TouchMonitor$$ExternalSyntheticLambda3(touchSessionImpl2, 1));
                            if (touchMonitor3.mActiveTouchSessions.remove(touchSessionImpl2)) {
                                TouchMonitor.TouchSessionImpl.m1010$$Nest$monRemoved(touchSessionImpl2);
                                TouchMonitor.TouchSessionImpl touchSessionImpl3 = touchSessionImpl2.mPredecessor;
                                if (touchSessionImpl3 != null) {
                                    touchMonitor3.mActiveTouchSessions.add(touchSessionImpl3);
                                }
                                completer2.set(touchSessionImpl3);
                            }
                            if (touchMonitor3.mActiveTouchSessions.isEmpty() && touchMonitor3.mInitialized) {
                                if (touchMonitor3.mStopMonitoringPending) {
                                    touchMonitor3.stopMonitoring(false);
                                } else {
                                    touchMonitor3.startMonitoring();
                                }
                            }
                        }
                    });
                    return "DreamOverlayTouchMonitor::pop";
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.ambient.touch.TouchMonitor$1] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.ambient.touch.TouchMonitor$4] */
    public TouchMonitor(Executor executor, Executor executor2, Lifecycle lifecycle, InputSessionComponent.Factory factory, DisplayHelper displayHelper, ConfigurationInteractor configurationInteractor, Set<TouchHandler> set, IWindowManager iWindowManager, int i, String str, LogBuffer logBuffer) {
        this.mDisplayId = i;
        this.mHandlers = set;
        this.mInputSessionFactory = factory;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mLifecycle = lifecycle;
        this.mWindowManagerService = iWindowManager;
        this.mConfigurationInteractor = configurationInteractor;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ":TouchMonitor[");
        int i2 = sNextInstanceId;
        sNextInstanceId = i2 + 1;
        String m2 = ReorderTile$$ExternalSyntheticOutline0.m(i2, "]", m);
        this.mLoggingName = m2;
        this.mLogger = new Logger(logBuffer, m2);
    }

    public final void destroy() {
        if (this.mInitialized) {
            stopMonitoring(true);
            this.mLifecycle.removeObserver(this.mLifecycleObserver);
            this.mBoundsFlow.cancel(new CancellationException());
            Iterator it = this.mHandlers.iterator();
            while (it.hasNext()) {
                ((TouchHandler) it.next()).onDestroy();
            }
            this.mInitialized = false;
        }
    }

    public final void init() {
        if (this.mInitialized) {
            throw new IllegalStateException("TouchMonitor already initialized");
        }
        AnonymousClass1 anonymousClass1 = this.mLifecycleObserver;
        Lifecycle lifecycle = this.mLifecycle;
        lifecycle.addObserver(anonymousClass1);
        this.mBoundsFlow = JavaAdapterKt.collectFlow(lifecycle, ((ConfigurationInteractorImpl) this.mConfigurationInteractor).maxBounds, this.mMaxBoundsConsumer);
        this.mInitialized = true;
    }

    public final void startMonitoring() {
        boolean z = this.mInitialized;
        Logger logger = this.mLogger;
        if (!z) {
            logger.w("attempting to startMonitoring when not initialized");
            return;
        }
        logger.i("startMonitoring(): monitoring started");
        stopMonitoring(true);
        this.mBackgroundExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda1(this, 2));
        this.mCurrentInputSession = ((DaggerReferenceGlobalRootComponent.InputSessionComponentImpl) this.mInputSessionFactory.create(this.mLoggingName, this.mInputEventListener, this.mOnGestureListener, true)).getInputSession();
    }

    public final void stopMonitoring(boolean z) {
        this.mExclusionRect = new Rect();
        this.mBackgroundExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda1(this, 0));
        if (this.mCurrentInputSession == null) {
            return;
        }
        boolean isEmpty = this.mActiveTouchSessions.isEmpty();
        Logger logger = this.mLogger;
        if (!isEmpty && !z) {
            logger.i(new TouchMonitor$$ExternalSyntheticLambda2(0), new TouchMonitor$$ExternalSyntheticLambda3(this, 0));
            this.mStopMonitoringPending = true;
            return;
        }
        this.mMainExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda1(this, 1));
        InputSession inputSession = this.mCurrentInputSession;
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = inputSession.mInputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
        }
        InputMonitorCompat inputMonitorCompat = inputSession.mInputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.dispose();
        }
        this.mCurrentInputSession = null;
        this.mStopMonitoringPending = false;
        logger.i("stopMonitoring(): monitoring finished");
    }
}
