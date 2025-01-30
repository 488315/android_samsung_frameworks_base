package com.android.systemui.dreams.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.WindowManager;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda4;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.dagger.InputSessionComponent;
import com.android.systemui.dreams.touch.scrim.ScrimManager;
import com.android.systemui.dreams.touch.scrim.ScrimManager$$ExternalSyntheticLambda1;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayTouchMonitor {
    public InputSession mCurrentInputSession;
    public final DisplayHelper mDisplayHelper;
    public final Executor mExecutor;
    public final Collection mHandlers;
    public final InputSessionComponent.Factory mInputSessionFactory;
    public final Lifecycle mLifecycle;
    public boolean mStopMonitoringPending;
    public final LifecycleObserver mLifecycleObserver = new DefaultLifecycleObserver() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.1
        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public final void onDestroy$1() {
            DreamOverlayTouchMonitor.this.stopMonitoring(true);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public final void onPause$1() {
            DreamOverlayTouchMonitor.this.stopMonitoring(false);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
        public final void onResume$1() {
            DreamOverlayTouchMonitor dreamOverlayTouchMonitor = DreamOverlayTouchMonitor.this;
            dreamOverlayTouchMonitor.stopMonitoring(true);
            dreamOverlayTouchMonitor.mCurrentInputSession = dreamOverlayTouchMonitor.mInputSessionFactory.create("dreamOverlay", dreamOverlayTouchMonitor.mInputEventListener, dreamOverlayTouchMonitor.mOnGestureListener, true).getInputSession();
        }
    };
    public final HashSet mActiveTouchSessions = new HashSet();
    public final C12892 mInputEventListener = new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.2
        @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
        public final void onInputEvent(InputEvent inputEvent) {
            DreamOverlayTouchMonitor dreamOverlayTouchMonitor = DreamOverlayTouchMonitor.this;
            boolean isEmpty = dreamOverlayTouchMonitor.mActiveTouchSessions.isEmpty();
            HashSet hashSet = dreamOverlayTouchMonitor.mActiveTouchSessions;
            if (isEmpty) {
                HashMap hashMap = new HashMap();
                for (DreamTouchHandler dreamTouchHandler : dreamOverlayTouchMonitor.mHandlers) {
                    int displayId = inputEvent.getDisplayId();
                    DisplayHelper displayHelper = dreamOverlayTouchMonitor.mDisplayHelper;
                    Rect bounds = ((WindowManager) displayHelper.mContext.createDisplayContext(displayHelper.mDisplayManager.getDisplay(displayId)).createWindowContext(2038, null).getSystemService(WindowManager.class)).getMaximumWindowMetrics().getBounds();
                    Region obtain = Region.obtain();
                    dreamTouchHandler.getTouchInitiationRegion(bounds, obtain);
                    if (!obtain.isEmpty()) {
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (!obtain.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()))) {
                            }
                        }
                    }
                    TouchSessionImpl touchSessionImpl = new TouchSessionImpl(dreamOverlayTouchMonitor, bounds, null);
                    hashSet.add(touchSessionImpl);
                    hashMap.put(dreamTouchHandler, touchSessionImpl);
                }
                hashMap.forEach(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda0());
            }
            Stream stream = hashSet.stream();
            int i = 0;
            stream.map(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(i)).flatMap(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(1)).forEach(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2(inputEvent, i));
        }
    };
    public final GestureDetectorOnGestureListenerC12903 mOnGestureListener = new GestureDetector.OnGestureListener() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor.3
        public final boolean evaluate(final Evaluator evaluator) {
            final HashSet hashSet = new HashSet();
            boolean anyMatch = DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    final DreamOverlayTouchMonitor.Evaluator evaluator2 = DreamOverlayTouchMonitor.Evaluator.this;
                    Set set = hashSet;
                    DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) obj;
                    boolean anyMatch2 = touchSessionImpl.mGestureListeners.stream().map(new Function() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5
                        @Override // java.util.function.Function
                        public final Object apply(Object obj2) {
                            return Boolean.valueOf(DreamOverlayTouchMonitor.Evaluator.this.evaluate((GestureDetector.OnGestureListener) obj2));
                        }
                    }).anyMatch(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda4(1));
                    if (anyMatch2) {
                        set.add(touchSessionImpl);
                    }
                    return Boolean.valueOf(anyMatch2);
                }
            }).anyMatch(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda4(0));
            if (anyMatch) {
                HashSet hashSet2 = DreamOverlayTouchMonitor.this.mActiveTouchSessions;
                Collection<?> collection = (Collection) hashSet2.stream().filter(new Predicate() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return !hashSet.contains((DreamOverlayTouchMonitor.TouchSessionImpl) obj);
                    }
                }).collect(Collectors.toCollection(new DreamOverlayStateController$$ExternalSyntheticLambda4()));
                collection.forEach(new DreamOverlayTouchMonitor$$ExternalSyntheticLambda1(1));
                hashSet2.removeAll(collection);
            }
            return anyMatch;
        }

        public final void observe(DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1 dreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1) {
            DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(2)).flatMap(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda1(3)).forEach(new DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda2(dreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(motionEvent, 0));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda0(motionEvent, motionEvent2, f, f2, 0));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            observe(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1(motionEvent, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda0(motionEvent, motionEvent2, f, f2, 1));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onShowPress(MotionEvent motionEvent) {
            observe(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1(motionEvent, 0));
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(motionEvent, 1));
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Evaluator {
        boolean evaluate(GestureDetector.OnGestureListener onGestureListener);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TouchSessionImpl implements DreamTouchHandler.TouchSession {
        public final Rect mBounds;
        public final TouchSessionImpl mPredecessor;
        public final DreamOverlayTouchMonitor mTouchMonitor;
        public final HashSet mEventListeners = new HashSet();
        public final HashSet mGestureListeners = new HashSet();
        public final HashSet mCallbacks = new HashSet();

        /* renamed from: -$$Nest$monRemoved, reason: not valid java name */
        public static void m1532$$Nest$monRemoved(TouchSessionImpl touchSessionImpl) {
            touchSessionImpl.mEventListeners.clear();
            touchSessionImpl.mGestureListeners.clear();
            Iterator it = touchSessionImpl.mCallbacks.iterator();
            while (it.hasNext()) {
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = ((BouncerSwipeTouchHandler$$ExternalSyntheticLambda0) it.next()).f$0;
                VelocityTracker velocityTracker = bouncerSwipeTouchHandler.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    bouncerSwipeTouchHandler.mVelocityTracker = null;
                }
                ScrimManager scrimManager = bouncerSwipeTouchHandler.mScrimManager;
                scrimManager.getClass();
                scrimManager.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager, bouncerSwipeTouchHandler.mScrimManagerCallback, 1));
                bouncerSwipeTouchHandler.mCapture = null;
                ((NotificationShadeWindowControllerImpl) bouncerSwipeTouchHandler.mNotificationShadeWindowController).setForcePluginOpen(bouncerSwipeTouchHandler, false);
                it.remove();
            }
        }

        public TouchSessionImpl(DreamOverlayTouchMonitor dreamOverlayTouchMonitor, Rect rect, TouchSessionImpl touchSessionImpl) {
            this.mPredecessor = touchSessionImpl;
            this.mTouchMonitor = dreamOverlayTouchMonitor;
            this.mBounds = rect;
        }

        public final CallbackToFutureAdapter.SafeFuture pop() {
            final DreamOverlayTouchMonitor dreamOverlayTouchMonitor = this.mTouchMonitor;
            dreamOverlayTouchMonitor.getClass();
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda3
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                    final DreamOverlayTouchMonitor dreamOverlayTouchMonitor2 = DreamOverlayTouchMonitor.this;
                    dreamOverlayTouchMonitor2.getClass();
                    final DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = this;
                    dreamOverlayTouchMonitor2.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamOverlayTouchMonitor dreamOverlayTouchMonitor3 = DreamOverlayTouchMonitor.this;
                            DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl2 = touchSessionImpl;
                            CallbackToFutureAdapter.Completer completer2 = completer;
                            HashSet hashSet = dreamOverlayTouchMonitor3.mActiveTouchSessions;
                            if (hashSet.remove(touchSessionImpl2)) {
                                DreamOverlayTouchMonitor.TouchSessionImpl.m1532$$Nest$monRemoved(touchSessionImpl2);
                                DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl3 = touchSessionImpl2.mPredecessor;
                                if (touchSessionImpl3 != null) {
                                    hashSet.add(touchSessionImpl3);
                                }
                                completer2.set(touchSessionImpl3);
                            }
                            if (hashSet.isEmpty() && dreamOverlayTouchMonitor3.mStopMonitoringPending) {
                                dreamOverlayTouchMonitor3.stopMonitoring(false);
                            }
                        }
                    });
                    return "DreamOverlayTouchMonitor::pop";
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3] */
    public DreamOverlayTouchMonitor(Executor executor, Lifecycle lifecycle, InputSessionComponent.Factory factory, DisplayHelper displayHelper, Set<DreamTouchHandler> set) {
        this.mHandlers = set;
        this.mInputSessionFactory = factory;
        this.mExecutor = executor;
        this.mLifecycle = lifecycle;
        this.mDisplayHelper = displayHelper;
    }

    public final void stopMonitoring(boolean z) {
        if (this.mCurrentInputSession == null) {
            return;
        }
        if (!this.mActiveTouchSessions.isEmpty() && !z) {
            this.mStopMonitoringPending = true;
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayTouchMonitor.this.mActiveTouchSessions.forEach(new DreamOverlayTouchMonitor$$ExternalSyntheticLambda1(0));
            }
        });
        InputSession inputSession = this.mCurrentInputSession;
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = inputSession.mInputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
        }
        InputMonitorCompat inputMonitorCompat = inputSession.mInputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.mInputMonitor.dispose();
        }
        this.mCurrentInputSession = null;
        this.mStopMonitoringPending = false;
    }
}
