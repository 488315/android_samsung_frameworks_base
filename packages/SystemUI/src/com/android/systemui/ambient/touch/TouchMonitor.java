package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.InputSessionComponent;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.shared.Flags;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.util.display.DisplayHelper;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TouchMonitor {
    public final HashSet mActiveTouchSessions;
    public final Executor mBackgroundExecutor;
    public InputSession mCurrentInputSession;
    public final DisplayHelper mDisplayHelper;
    public final int mDisplayId;
    public AnonymousClass2 mGestureExclusionListener;
    public final Collection mHandlers;
    public boolean mInitialized;
    public final AnonymousClass3 mInputEventListener;
    public final InputSessionComponent.Factory mInputSessionFactory;
    public final Lifecycle mLifecycle;
    public final AnonymousClass1 mLifecycleObserver;
    public final Executor mMainExecutor;
    public final TouchMonitor$$ExternalSyntheticLambda2 mMaxBoundsConsumer;
    public final AnonymousClass4 mOnGestureListener;
    public boolean mStopMonitoringPending;
    public final IWindowManager mWindowManagerService;
    public final String TAG = "DreamOverlayTouchMonitor";
    public Rect mExclusionRect = null;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Evaluator {
        boolean evaluate(GestureDetector.OnGestureListener onGestureListener);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TouchSessionImpl {
        public final Rect mBounds;
        public final TouchSessionImpl mPredecessor;
        public final TouchMonitor mTouchMonitor;
        public final HashSet mEventListeners = new HashSet();
        public final HashSet mGestureListeners = new HashSet();
        public final HashSet mCallbacks = new HashSet();

        /* renamed from: -$$Nest$monRemoved, reason: not valid java name */
        public static void m884$$Nest$monRemoved(TouchSessionImpl touchSessionImpl) {
            touchSessionImpl.mEventListeners.clear();
            touchSessionImpl.mGestureListeners.clear();
            Iterator it = touchSessionImpl.mCallbacks.iterator();
            while (it.hasNext()) {
                ((TouchHandler$TouchSession$Callback) it.next()).onRemoved();
                it.remove();
            }
        }

        public TouchSessionImpl(TouchMonitor touchMonitor, Rect rect, TouchSessionImpl touchSessionImpl) {
            this.mPredecessor = touchSessionImpl;
            this.mTouchMonitor = touchMonitor;
            this.mBounds = rect;
        }

        public final void pop() {
            final TouchMonitor touchMonitor = this.mTouchMonitor;
            touchMonitor.getClass();
            CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda8
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                    final TouchMonitor touchMonitor2 = TouchMonitor.this;
                    Executor executor = touchMonitor2.mMainExecutor;
                    final TouchMonitor.TouchSessionImpl touchSessionImpl = this;
                    executor.execute(new Runnable() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            TouchMonitor touchMonitor3 = TouchMonitor.this;
                            TouchMonitor.TouchSessionImpl touchSessionImpl2 = touchSessionImpl;
                            CallbackToFutureAdapter.Completer completer2 = completer;
                            if (touchMonitor3.mActiveTouchSessions.remove(touchSessionImpl2)) {
                                TouchMonitor.TouchSessionImpl.m884$$Nest$monRemoved(touchSessionImpl2);
                                TouchMonitor.TouchSessionImpl touchSessionImpl3 = touchSessionImpl2.mPredecessor;
                                if (touchSessionImpl3 != null) {
                                    touchMonitor3.mActiveTouchSessions.add(touchSessionImpl3);
                                }
                                completer2.set(touchSessionImpl3);
                            }
                            if (touchMonitor3.mActiveTouchSessions.isEmpty() && touchMonitor3.mStopMonitoringPending) {
                                touchMonitor3.stopMonitoring(false);
                            }
                        }
                    });
                    return "DreamOverlayTouchMonitor::pop";
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.systemui.ambient.touch.TouchMonitor$1] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.ambient.touch.TouchMonitor$3] */
    /* JADX WARN: Type inference failed for: r7v7, types: [com.android.systemui.ambient.touch.TouchMonitor$4] */
    public TouchMonitor(Executor executor, Executor executor2, Lifecycle lifecycle, InputSessionComponent.Factory factory, DisplayHelper displayHelper, ConfigurationInteractor configurationInteractor, Set<TouchHandler> set, IWindowManager iWindowManager, int i) {
        new TouchMonitor$$ExternalSyntheticLambda2(this, 0);
        this.mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.android.systemui.ambient.touch.TouchMonitor.1
            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onDestroy$1() {
                TouchMonitor.this.stopMonitoring(true);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onPause$1() {
                TouchMonitor.this.stopMonitoring(false);
            }

            @Override // androidx.lifecycle.DefaultLifecycleObserver
            public final void onResume$1() {
                TouchMonitor touchMonitor = TouchMonitor.this;
                touchMonitor.stopMonitoring(true);
                Flags.FEATURE_FLAGS.getClass();
                touchMonitor.mBackgroundExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda0(touchMonitor, 2));
                touchMonitor.mCurrentInputSession = touchMonitor.mInputSessionFactory.create("dreamOverlay", touchMonitor.mInputEventListener, touchMonitor.mOnGestureListener, true).getInputSession();
            }
        };
        this.mActiveTouchSessions = new HashSet();
        this.mInputEventListener = new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.TouchMonitor.3
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                TouchMonitor touchMonitor = TouchMonitor.this;
                if (touchMonitor.mActiveTouchSessions.isEmpty()) {
                    HashMap hashMap = new HashMap();
                    for (TouchHandler touchHandler : touchMonitor.mHandlers) {
                        if (touchHandler.isEnabled().booleanValue()) {
                            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                            Rect maxBounds = touchMonitor.mDisplayHelper.getMaxBounds(inputEvent.getDisplayId(), 2038);
                            Region obtain = Region.obtain();
                            Flags.FEATURE_FLAGS.getClass();
                            touchHandler.getTouchInitiationRegion(maxBounds, obtain, touchMonitor.mExclusionRect);
                            if (!obtain.isEmpty()) {
                                if (inputEvent instanceof MotionEvent) {
                                    MotionEvent motionEvent = (MotionEvent) inputEvent;
                                    if (!obtain.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()))) {
                                    }
                                }
                            }
                            TouchSessionImpl touchSessionImpl = new TouchSessionImpl(touchMonitor, maxBounds, null);
                            touchMonitor.mActiveTouchSessions.add(touchSessionImpl);
                            hashMap.put(touchHandler, touchSessionImpl);
                        }
                    }
                    hashMap.forEach(new TouchMonitor$3$$ExternalSyntheticLambda0());
                }
                touchMonitor.mActiveTouchSessions.stream().map(new TouchMonitor$3$$ExternalSyntheticLambda1(0)).flatMap(new TouchMonitor$3$$ExternalSyntheticLambda1(1)).forEach(new TouchMonitor$$ExternalSyntheticLambda2(inputEvent, 1));
            }
        };
        this.mOnGestureListener = new GestureDetector.OnGestureListener() { // from class: com.android.systemui.ambient.touch.TouchMonitor.4
            public final boolean evaluate(final Evaluator evaluator) {
                final HashSet hashSet = new HashSet();
                boolean anyMatch = TouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.ambient.touch.TouchMonitor$4$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        final TouchMonitor.Evaluator evaluator2 = TouchMonitor.Evaluator.this;
                        Set set2 = hashSet;
                        TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) obj;
                        boolean anyMatch2 = touchSessionImpl.mGestureListeners.stream().map(new Function() { // from class: com.android.systemui.ambient.touch.TouchMonitor$4$$ExternalSyntheticLambda10
                            @Override // java.util.function.Function
                            public final Object apply(Object obj2) {
                                return Boolean.valueOf(TouchMonitor.Evaluator.this.evaluate((GestureDetector.OnGestureListener) obj2));
                            }
                        }).anyMatch(new TouchMonitor$4$$ExternalSyntheticLambda7(1));
                        if (anyMatch2) {
                            set2.add(touchSessionImpl);
                        }
                        return Boolean.valueOf(anyMatch2);
                    }
                }).anyMatch(new TouchMonitor$4$$ExternalSyntheticLambda7(0));
                if (anyMatch) {
                    TouchMonitor touchMonitor = TouchMonitor.this;
                    Collection<?> collection = (Collection) touchMonitor.mActiveTouchSessions.stream().filter(new Predicate() { // from class: com.android.systemui.ambient.touch.TouchMonitor$$ExternalSyntheticLambda5
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return !hashSet.contains((TouchMonitor.TouchSessionImpl) obj);
                        }
                    }).collect(Collectors.toCollection(new TouchMonitor$$ExternalSyntheticLambda6()));
                    collection.forEach(new TouchMonitor$$ExternalSyntheticLambda3(1));
                    touchMonitor.mActiveTouchSessions.removeAll(collection);
                }
                return anyMatch;
            }

            public final void observe(Consumer consumer) {
                TouchMonitor.this.mActiveTouchSessions.stream().map(new TouchMonitor$3$$ExternalSyntheticLambda1(2)).flatMap(new TouchMonitor$3$$ExternalSyntheticLambda1(1)).forEach(new TouchMonitor$$ExternalSyntheticLambda2(consumer, 2));
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
        this.mDisplayId = i;
        this.mHandlers = set;
        this.mInputSessionFactory = factory;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mLifecycle = lifecycle;
        this.mDisplayHelper = displayHelper;
        this.mWindowManagerService = iWindowManager;
    }

    public final void stopMonitoring(boolean z) {
        this.mExclusionRect = null;
        Flags.FEATURE_FLAGS.getClass();
        this.mBackgroundExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda0(this, 0));
        if (this.mCurrentInputSession == null) {
            return;
        }
        if (!this.mActiveTouchSessions.isEmpty() && !z) {
            this.mStopMonitoringPending = true;
            return;
        }
        this.mMainExecutor.execute(new TouchMonitor$$ExternalSyntheticLambda0(this, 1));
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
    }
}
