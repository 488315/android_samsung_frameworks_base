package com.android.server.accessibility.gestures;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Region;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilityWindowManager;
import com.android.server.accessibility.BaseEventStreamTransformation;
import com.android.server.accessibility.EventStreamTransformation;
import com.android.server.accessibility.Flags;
import com.android.server.accessibility.gestures.GestureManifold;
import com.android.server.accessibility.gestures.TouchState;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TouchExplorer extends BaseEventStreamTransformation implements GestureManifold.Listener {
    public static final boolean DEBUG = Log.isLoggable("TouchExplorer", 3);
    public static boolean SEC_DEBUG;
    public final AccessibilityManagerService mAms;
    public final Context mContext;
    public int mDetermineUserIntentTimeout;
    public final EventDispatcher mDispatcher;
    public final int mDisplayId;
    public final int mDoubleTapSlop;
    public int mDraggingPointerId;
    public final float mEdgeSwipeHeightPixels;
    public final ExitGestureDetectionModeDelayed mExitGestureDetectionModeDelayed;
    public Region mGestureDetectionPassthroughRegion;
    public final GestureManifold mGestureDetector;
    public final Handler mHandler;
    public final TouchState.ReceivedPointerTracker mReceivedPointerTracker;
    public final SendHoverExitDelayed mSendHoverEnterAndMoveDelayed;
    public final SendHoverExitDelayed mSendHoverExitDelayed;
    public final SendAccessibilityEventDelayed mSendTouchExplorationEndDelayed;
    public final SendAccessibilityEventDelayed mSendTouchInteractionEndDelayed;
    public final TouchState mState;
    public Region mTouchExplorationPassthroughRegion;
    public final int mTouchSlop;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExitGestureDetectionModeDelayed implements Runnable {
        public ExitGestureDetectionModeDelayed() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            TouchExplorer.this.mDispatcher.sendAccessibilityEvent(524288);
            TouchExplorer touchExplorer = TouchExplorer.this;
            MotionEvent motionEvent = touchExplorer.mState.mLastReceivedEvent;
            if (motionEvent != null) {
                touchExplorer.clear(33554432, motionEvent);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SendAccessibilityEventDelayed implements Runnable {
        public int mDelay;
        public final int mEventType;

        public SendAccessibilityEventDelayed(int i, int i2) {
            this.mEventType = i;
            this.mDelay = i2;
        }

        public final void cancel() {
            TouchExplorer.this.mHandler.removeCallbacks(this);
        }

        public final void forceSendAndRemove() {
            if (isPending()) {
                run();
                cancel();
            }
        }

        public final boolean isPending() {
            return TouchExplorer.this.mHandler.hasCallbacks(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            TouchExplorer.this.mDispatcher.sendAccessibilityEvent(this.mEventType);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SendHoverExitDelayed implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public int mPointerIdBits;
        public int mPolicyFlags;
        public Object mPrototype;
        public Object mRawEvent;
        public final /* synthetic */ TouchExplorer this$0;

        public SendHoverExitDelayed(TouchExplorer touchExplorer, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = touchExplorer;
                    this.mPrototype = new ArrayList();
                    this.mRawEvent = new ArrayList();
                    break;
                default:
                    this.this$0 = touchExplorer;
                    break;
            }
        }

        public void addEvent(MotionEvent motionEvent, MotionEvent motionEvent2) {
            ((ArrayList) ((List) this.mPrototype)).add(MotionEvent.obtain(motionEvent));
            ((ArrayList) ((List) this.mRawEvent)).add(MotionEvent.obtain(motionEvent2));
        }

        public final void cancel() {
            switch (this.$r8$classId) {
                case 0:
                    if (isPending()) {
                        this.this$0.mHandler.removeCallbacks(this);
                        MotionEvent motionEvent = (MotionEvent) this.mPrototype;
                        if (motionEvent != null) {
                            motionEvent.recycle();
                        }
                        MotionEvent motionEvent2 = (MotionEvent) this.mRawEvent;
                        if (motionEvent2 != null) {
                            motionEvent2.recycle();
                        }
                        this.mPrototype = null;
                        this.mRawEvent = null;
                        this.mPointerIdBits = -1;
                        this.mPolicyFlags = 0;
                        break;
                    }
                    break;
                default:
                    if (isPending()) {
                        this.this$0.mHandler.removeCallbacks(this);
                        clear();
                        break;
                    }
                    break;
            }
        }

        public void clear() {
            this.mPointerIdBits = -1;
            this.mPolicyFlags = 0;
            for (int size = ((ArrayList) ((List) this.mPrototype)).size() - 1; size >= 0; size--) {
                ((MotionEvent) ((ArrayList) ((List) this.mPrototype)).remove(size)).recycle();
            }
            for (int size2 = ((ArrayList) ((List) this.mRawEvent)).size() - 1; size2 >= 0; size2--) {
                ((MotionEvent) ((ArrayList) ((List) this.mRawEvent)).remove(size2)).recycle();
            }
        }

        public final boolean isPending() {
            switch (this.$r8$classId) {
            }
            return this.this$0.mHandler.hasCallbacks(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    if (TouchExplorer.DEBUG) {
                        Slog.d("SendHoverExitDelayed", "Injecting motion event: ACTION_HOVER_EXIT");
                    }
                    this.this$0.mDispatcher.sendMotionEvent(10, this.mPointerIdBits, this.mPolicyFlags, (MotionEvent) this.mPrototype, (MotionEvent) this.mRawEvent);
                    if (!this.this$0.mSendTouchExplorationEndDelayed.isPending()) {
                        this.this$0.mSendTouchExplorationEndDelayed.cancel();
                        SendAccessibilityEventDelayed sendAccessibilityEventDelayed = this.this$0.mSendTouchExplorationEndDelayed;
                        TouchExplorer.this.mHandler.postDelayed(sendAccessibilityEventDelayed, sendAccessibilityEventDelayed.mDelay);
                    }
                    if (this.this$0.mSendTouchInteractionEndDelayed.isPending()) {
                        this.this$0.mSendTouchInteractionEndDelayed.cancel();
                        SendAccessibilityEventDelayed sendAccessibilityEventDelayed2 = this.this$0.mSendTouchInteractionEndDelayed;
                        TouchExplorer.this.mHandler.postDelayed(sendAccessibilityEventDelayed2, sendAccessibilityEventDelayed2.mDelay);
                    }
                    MotionEvent motionEvent = (MotionEvent) this.mPrototype;
                    if (motionEvent != null) {
                        motionEvent.recycle();
                    }
                    MotionEvent motionEvent2 = (MotionEvent) this.mRawEvent;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                    }
                    this.mPrototype = null;
                    this.mRawEvent = null;
                    this.mPointerIdBits = -1;
                    this.mPolicyFlags = 0;
                    break;
                default:
                    if (Integer.bitCount(this.this$0.mReceivedPointerTracker.mReceivedPointersDown) > 1) {
                        Slog.e("TouchExplorer", "Attempted touch exploration with " + Integer.bitCount(this.this$0.mReceivedPointerTracker.mReceivedPointersDown) + " pointers down.");
                        break;
                    } else if (((ArrayList) ((List) this.mPrototype)).size() != 0) {
                        Flags.sendHoverEventsBasedOnEventStream();
                        this.this$0.sendHoverExitAndTouchExplorationGestureEndIfNeeded(this.mPolicyFlags);
                        this.this$0.mDispatcher.sendAccessibilityEvent(512);
                        if (this.this$0.mGestureDetector.mSendMotionEventsEnabled) {
                            this.this$0.dispatchGesture(new AccessibilityGestureEvent(-2, this.this$0.mState.mLastReceivedEvent.getDisplayId(), this.this$0.mGestureDetector.mEvents));
                        }
                        if (!((ArrayList) ((List) this.mPrototype)).isEmpty() && !((ArrayList) ((List) this.mRawEvent)).isEmpty()) {
                            this.this$0.mDispatcher.sendMotionEvent(9, this.mPointerIdBits, this.mPolicyFlags, (MotionEvent) ((ArrayList) ((List) this.mPrototype)).get(0), (MotionEvent) ((ArrayList) ((List) this.mRawEvent)).get(0));
                            if (TouchExplorer.DEBUG) {
                                Slog.d("SendHoverEnterAndMoveDelayed", "Injecting motion event: ACTION_HOVER_ENTER");
                            }
                            int size = ((ArrayList) ((List) this.mPrototype)).size();
                            for (int i = 1; i < size; i++) {
                                this.this$0.mDispatcher.sendMotionEvent(7, this.mPointerIdBits, this.mPolicyFlags, (MotionEvent) ((ArrayList) ((List) this.mPrototype)).get(i), (MotionEvent) ((ArrayList) ((List) this.mRawEvent)).get(i));
                                if (TouchExplorer.DEBUG) {
                                    Slog.d("SendHoverEnterAndMoveDelayed", "Injecting motion event: ACTION_HOVER_MOVE");
                                }
                            }
                        }
                        clear();
                        break;
                    }
                    break;
            }
        }
    }

    public TouchExplorer(Context context, AccessibilityManagerService accessibilityManagerService, GestureManifold gestureManifold, Handler handler) {
        this.mDisplayId = -1;
        this.mContext = context;
        int displayId = context.getDisplayId();
        this.mDisplayId = displayId;
        this.mAms = accessibilityManagerService;
        TouchState touchState = new TouchState(displayId, accessibilityManagerService);
        this.mState = touchState;
        this.mReceivedPointerTracker = touchState.mReceivedPointerTracker;
        this.mDispatcher = new EventDispatcher(context, accessibilityManagerService, this.mNext, touchState);
        this.mDetermineUserIntentTimeout = ViewConfiguration.getDoubleTapTimeout();
        this.mDoubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mEdgeSwipeHeightPixels = (context.getResources().getDisplayMetrics().ydpi / 2.54f) * 0.25f;
        this.mHandler = handler;
        this.mExitGestureDetectionModeDelayed = new ExitGestureDetectionModeDelayed();
        this.mSendHoverEnterAndMoveDelayed = new SendHoverExitDelayed(this, 1);
        this.mSendHoverExitDelayed = new SendHoverExitDelayed(this, 0);
        int i = this.mDetermineUserIntentTimeout;
        this.mSendTouchExplorationEndDelayed = new SendAccessibilityEventDelayed(1024, i);
        this.mSendTouchInteractionEndDelayed = new SendAccessibilityEventDelayed(2097152, i);
        if (gestureManifold == null) {
            this.mGestureDetector = new GestureManifold(context, this, touchState);
        } else {
            this.mGestureDetector = gestureManifold;
        }
        this.mGestureDetectionPassthroughRegion = new Region();
        this.mTouchExplorationPassthroughRegion = new Region();
    }

    public static void checkForMalformedEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 0) {
            throw new IllegalArgumentException("Invalid pointer count: " + motionEvent.getPointerCount());
        }
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            try {
                motionEvent.getPointerId(i);
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                if (Float.isNaN(x) || Float.isNaN(y) || x < FullScreenMagnificationGestureHandler.MAX_SCALE || y < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
                }
            } catch (Exception e) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Encountered exception getting details of pointer ", " / ");
                m.append(motionEvent.getPointerCount());
                throw new IllegalArgumentException(m.toString(), e);
            }
        }
    }

    public final void clear(int i, MotionEvent motionEvent) {
        TouchState touchState = this.mState;
        if (!touchState.isTouchExploring()) {
            Flags.sendHoverEventsBasedOnEventStream();
        }
        sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        this.mDraggingPointerId = -1;
        EventDispatcher eventDispatcher = this.mDispatcher;
        eventDispatcher.getClass();
        int pointerIdBits = motionEvent.getPointerIdBits();
        int pointerCount = motionEvent.getPointerCount();
        int i2 = pointerIdBits;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId = motionEvent.getPointerId(i3);
            TouchState touchState2 = eventDispatcher.mState;
            if (touchState2.isInjectedPointerDown(pointerId)) {
                eventDispatcher.sendMotionEvent(eventDispatcher.computeInjectionAction(6, i3), i2, i, motionEvent, touchState2.mLastReceivedEvent);
                i2 = (~(1 << pointerId)) & i2;
            }
        }
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        ExitGestureDetectionModeDelayed exitGestureDetectionModeDelayed = this.mExitGestureDetectionModeDelayed;
        TouchExplorer.this.mHandler.removeCallbacks(exitGestureDetectionModeDelayed);
        this.mSendTouchExplorationEndDelayed.cancel();
        this.mSendTouchInteractionEndDelayed.cancel();
        this.mGestureDetector.clear();
        eventDispatcher.mLongPressingPointerId = -1;
        eventDispatcher.mLongPressingPointerDeltaX = 0;
        eventDispatcher.mLongPressingPointerDeltaY = 0;
        touchState.clear();
        this.mAms.onTouchInteractionEnd();
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        MotionEvent motionEvent;
        if (i == 4098 && (motionEvent = this.mState.mLastReceivedEvent) != null) {
            clear(33554432, motionEvent);
        }
        super.clearEvents(i);
    }

    public final MotionEvent computeDownEventForDrag(MotionEvent motionEvent) {
        int i;
        if (this.mState.isTouchExploring() || (i = this.mDraggingPointerId) == -1 || motionEvent == null) {
            return null;
        }
        TouchState.ReceivedPointerTracker receivedPointerTracker = this.mReceivedPointerTracker;
        float receivedPointerDownX = receivedPointerTracker.getReceivedPointerDownX(i);
        float receivedPointerDownY = receivedPointerTracker.getReceivedPointerDownY(this.mDraggingPointerId);
        long j = receivedPointerTracker.mReceivedPointers[this.mDraggingPointerId].mTime;
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = receivedPointerDownX;
        pointerCoords.y = receivedPointerDownY;
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = this.mDraggingPointerId;
        pointerProperties.toolType = 1;
        MotionEvent obtain = MotionEvent.obtain(j, j, 0, 1, new MotionEvent.PointerProperties[]{pointerProperties}, new MotionEvent.PointerCoords[]{pointerCoords}, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags());
        motionEvent.setDownTime(j);
        return obtain;
    }

    public final void computeDraggingPointerIdIfNeeded(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            this.mDraggingPointerId = -1;
            return;
        }
        int i = this.mDraggingPointerId;
        if (i == -1 || motionEvent.findPointerIndex(motionEvent.findPointerIndex(i)) < 0) {
            float x = motionEvent.getX(0);
            float y = motionEvent.getY(0);
            int pointerId = motionEvent.getPointerId(0);
            float x2 = motionEvent.getX(1);
            float y2 = motionEvent.getY(1);
            int pointerId2 = motionEvent.getPointerId(1);
            if (getDistanceToClosestEdge(x, y) >= getDistanceToClosestEdge(x2, y2)) {
                pointerId = pointerId2;
            }
            this.mDraggingPointerId = pointerId;
        }
    }

    public final void dispatchGesture(AccessibilityGestureEvent accessibilityGestureEvent) {
        if (DEBUG) {
            Slog.d("TouchExplorer", "Dispatching gesture event:" + accessibilityGestureEvent.toString());
        }
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            try {
                if (!accessibilityManagerService.notifyGestureLocked(accessibilityGestureEvent, false)) {
                    accessibilityManagerService.notifyGestureLocked(accessibilityGestureEvent, true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final float getDistanceToClosestEdge(float f, float f2) {
        long j = this.mContext.getResources().getDisplayMetrics().widthPixels;
        long j2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        float f3 = j - f;
        if (f >= f3) {
            f = f3;
        }
        if (f > f2) {
            f = f2;
        }
        float f4 = j2 - f2;
        return f > f4 ? f4 : f;
    }

    public final void handleActionDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        AccessibilityWindowManager accessibilityWindowManager = this.mAms.mA11yWindowManager;
        synchronized (accessibilityWindowManager.mLock) {
            accessibilityWindowManager.mTouchInteractionInProgress = true;
        }
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverEnterAndMoveDelayed.clear();
        this.mSendHoverExitDelayed.cancel();
        if (!this.mState.isTouchExploring()) {
            Flags.sendHoverEventsBasedOnEventStream();
        }
        sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        if (this.mState.mState == 0) {
            if (this.mSendHoverEnterAndMoveDelayed.isPending()) {
                this.mSendHoverEnterAndMoveDelayed.addEvent(motionEvent, motionEvent2);
            } else {
                int primaryPointerId = 1 << this.mReceivedPointerTracker.getPrimaryPointerId();
                if (this.mState.mServiceDetectsGestures) {
                    SendHoverExitDelayed sendHoverExitDelayed = this.mSendHoverEnterAndMoveDelayed;
                    sendHoverExitDelayed.mPointerIdBits = primaryPointerId;
                    sendHoverExitDelayed.mPolicyFlags = i;
                    sendHoverExitDelayed.addEvent(motionEvent, motionEvent2);
                } else {
                    SendHoverExitDelayed sendHoverExitDelayed2 = this.mSendHoverEnterAndMoveDelayed;
                    sendHoverExitDelayed2.cancel();
                    sendHoverExitDelayed2.addEvent(motionEvent, motionEvent2);
                    sendHoverExitDelayed2.mPointerIdBits = primaryPointerId;
                    sendHoverExitDelayed2.mPolicyFlags = i;
                    sendHoverExitDelayed2.this$0.mHandler.postDelayed(sendHoverExitDelayed2, r0.mDetermineUserIntentTimeout);
                }
            }
            this.mSendTouchExplorationEndDelayed.forceSendAndRemove();
            this.mSendTouchInteractionEndDelayed.forceSendAndRemove();
            this.mDispatcher.sendAccessibilityEvent(1048576);
            if (this.mTouchExplorationPassthroughRegion.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mState.setState(4);
                MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                this.mDispatcher.sendMotionEvent(obtainNoHistory.getAction(), -1, i, obtainNoHistory, motionEvent2);
                this.mSendHoverEnterAndMoveDelayed.cancel();
            } else if (this.mGestureDetectionPassthroughRegion.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                SendHoverExitDelayed sendHoverExitDelayed3 = this.mSendHoverEnterAndMoveDelayed;
                if (sendHoverExitDelayed3.isPending()) {
                    sendHoverExitDelayed3.run();
                    sendHoverExitDelayed3.cancel();
                }
            }
        } else {
            this.mSendTouchInteractionEndDelayed.cancel();
        }
        if (this.mState.mServiceDetectsGestures) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
        }
    }

    public final void handleActionMoveStateTouchInteracting(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        MotionEvent obtain;
        TouchState.ReceivedPointerTracker receivedPointerTracker = this.mReceivedPointerTracker;
        int findPointerIndex = motionEvent.findPointerIndex(receivedPointerTracker.getPrimaryPointerId());
        TouchState touchState = this.mState;
        boolean z = touchState.mServiceDetectsGestures;
        SendHoverExitDelayed sendHoverExitDelayed = this.mSendHoverEnterAndMoveDelayed;
        if (z) {
            this.mAms.sendMotionEventToListeningServices(motionEvent2);
            sendHoverExitDelayed.addEvent(motionEvent, motionEvent2);
            return;
        }
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount == 1) {
            if (sendHoverExitDelayed.isPending()) {
                sendHoverExitDelayed.addEvent(motionEvent, motionEvent2);
                return;
            }
            return;
        }
        int i2 = this.mDisplayId;
        EventDispatcher eventDispatcher = this.mDispatcher;
        GestureManifold gestureManifold = this.mGestureDetector;
        if (pointerCount == 2) {
            boolean z2 = false;
            if (!gestureManifold.mMultiFingerGesturesEnabled || gestureManifold.mTwoFingerPassthroughEnabled) {
                sendHoverExitDelayed.cancel();
                this.mSendHoverExitDelayed.cancel();
                if (gestureManifold.mMultiFingerGesturesEnabled && gestureManifold.mTwoFingerPassthroughEnabled) {
                    if (findPointerIndex < 0) {
                        return;
                    }
                    int i3 = 0;
                    while (i3 < motionEvent.getPointerCount()) {
                        int pointerId = motionEvent.getPointerId(i3);
                        if (!((receivedPointerTracker.mReceivedPointersDown & (1 << pointerId)) != 0 ? true : z2)) {
                            NandswapManager$$ExternalSyntheticOutline0.m(pointerId, "Invalid pointer id: ", "TouchExplorer");
                        }
                        EventDispatcher eventDispatcher2 = eventDispatcher;
                        if (Math.hypot(receivedPointerTracker.getReceivedPointerDownX(pointerId) - motionEvent2.getX(i3), receivedPointerTracker.getReceivedPointerDownY(pointerId) - motionEvent2.getY(i3)) < this.mTouchSlop * 2) {
                            return;
                        }
                        i3++;
                        eventDispatcher = eventDispatcher2;
                        z2 = false;
                    }
                }
                EventDispatcher eventDispatcher3 = eventDispatcher;
                MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                if (!isDraggingGesture(obtainNoHistory)) {
                    if (gestureManifold.mSendMotionEventsEnabled) {
                        dispatchGesture(new AccessibilityGestureEvent(-1, i2, gestureManifold.mEvents));
                    }
                    touchState.setState(4);
                    eventDispatcher3.sendDownForAllNotInjectedPointers(i, obtainNoHistory);
                    return;
                }
                if (gestureManifold.mSendMotionEventsEnabled) {
                    dispatchGesture(new AccessibilityGestureEvent(-1, i2, gestureManifold.mEvents));
                }
                computeDraggingPointerIdIfNeeded(obtainNoHistory);
                int i4 = 1 << this.mDraggingPointerId;
                obtainNoHistory.setEdgeFlags(receivedPointerTracker.mLastReceivedDownEdgeFlags);
                MotionEvent computeDownEventForDrag = computeDownEventForDrag(obtainNoHistory);
                if (computeDownEventForDrag != null) {
                    this.mDispatcher.sendMotionEvent(0, i4, i, computeDownEventForDrag, motionEvent2);
                    this.mDispatcher.sendMotionEvent(2, i4, i, obtainNoHistory, motionEvent2);
                } else {
                    this.mDispatcher.sendMotionEvent(0, i4, i, obtainNoHistory, motionEvent2);
                }
                touchState.setState(3);
                return;
            }
            return;
        }
        if (!gestureManifold.mMultiFingerGesturesEnabled) {
            if (gestureManifold.mSendMotionEventsEnabled) {
                dispatchGesture(new AccessibilityGestureEvent(-1, i2, gestureManifold.mEvents));
            }
            touchState.setState(4);
            eventDispatcher.sendDownForAllNotInjectedPointers(i, MotionEvent.obtainNoHistory(motionEvent));
            return;
        }
        if (!gestureManifold.mTwoFingerPassthroughEnabled || motionEvent.getPointerCount() != 3) {
            return;
        }
        long j = this.mContext.getResources().getDisplayMetrics().heightPixels;
        int i5 = 0;
        while (true) {
            int pointerCount2 = motionEvent.getPointerCount();
            boolean z3 = DEBUG;
            if (i5 >= pointerCount2) {
                if (z3) {
                    Slog.d("TouchExplorer", "Three-finger edge swipe detected.");
                }
                if (gestureManifold.mSendMotionEventsEnabled) {
                    dispatchGesture(new AccessibilityGestureEvent(-1, i2, gestureManifold.mEvents));
                }
                touchState.setState(4);
                gestureManifold.clear();
                if (touchState.isTouchExploring()) {
                    eventDispatcher.sendDownForAllNotInjectedPointers(i, motionEvent);
                    return;
                }
                eventDispatcher.getClass();
                int pointerCount3 = motionEvent.getPointerCount();
                int pointerCount4 = motionEvent.getPointerCount();
                TouchState touchState2 = eventDispatcher.mState;
                if (pointerCount4 != Integer.bitCount(touchState2.mReceivedPointerTracker.mReceivedPointersDown)) {
                    Slog.w("EventDispatcher", "The pointer count doesn't match the received count.");
                    obtain = MotionEvent.obtain(motionEvent);
                } else {
                    MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount4];
                    MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[pointerCount4];
                    for (int i6 = 0; i6 < pointerCount4; i6++) {
                        int pointerId2 = motionEvent.getPointerId(i6);
                        TouchState.ReceivedPointerTracker receivedPointerTracker2 = touchState2.mReceivedPointerTracker;
                        float receivedPointerDownX = receivedPointerTracker2.getReceivedPointerDownX(pointerId2);
                        float receivedPointerDownY = receivedPointerTracker2.getReceivedPointerDownY(pointerId2);
                        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                        pointerCoordsArr[i6] = pointerCoords;
                        pointerCoords.x = receivedPointerDownX;
                        pointerCoords.y = receivedPointerDownY;
                        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
                        pointerPropertiesArr[i6] = pointerProperties;
                        pointerProperties.id = pointerId2;
                        pointerProperties.toolType = 1;
                    }
                    obtain = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getDownTime(), motionEvent.getAction(), pointerCount4, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags());
                }
                MotionEvent motionEvent3 = obtain;
                int i7 = 0;
                for (int i8 = 0; i8 < pointerCount3; i8++) {
                    int pointerId3 = motionEvent.getPointerId(i8);
                    if (!touchState2.isInjectedPointerDown(pointerId3)) {
                        int i9 = i7 | (1 << pointerId3);
                        eventDispatcher.sendMotionEvent(eventDispatcher.computeInjectionAction(0, i8), i9, i, motionEvent3, touchState2.mLastReceivedEvent);
                        i7 = i9;
                    }
                }
                return;
            }
            float receivedPointerDownY2 = receivedPointerTracker.getReceivedPointerDownY(motionEvent.getPointerId(i5));
            if (receivedPointerDownY2 < j - this.mEdgeSwipeHeightPixels) {
                if (z3) {
                    Slog.d("TouchExplorer", "The pointer is not on the bottom edge" + receivedPointerDownY2);
                    return;
                }
                return;
            }
            i5++;
        }
    }

    public final void handleActionPointerDown(int i, MotionEvent motionEvent) {
        SendHoverExitDelayed sendHoverExitDelayed = this.mSendHoverEnterAndMoveDelayed;
        if (sendHoverExitDelayed.isPending()) {
            sendHoverExitDelayed.cancel();
            this.mSendHoverExitDelayed.cancel();
        } else {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        if (this.mState.mServiceDetectsGestures) {
            this.mAms.sendMotionEventToListeningServices(motionEvent);
        }
    }

    public final void handleActionUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        TouchState touchState = this.mState;
        boolean z = touchState.mServiceDetectsGestures;
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (z && touchState.mState == 1) {
            accessibilityManagerService.sendMotionEventToListeningServices(motionEvent2);
        }
        accessibilityManagerService.onTouchInteractionEnd();
        int pointerId = 1 << motionEvent.getPointerId(motionEvent.getActionIndex());
        SendHoverExitDelayed sendHoverExitDelayed = this.mSendHoverEnterAndMoveDelayed;
        if (sendHoverExitDelayed.isPending()) {
            if (sendHoverExitDelayed.isPending()) {
                sendHoverExitDelayed.this$0.mHandler.removeCallbacks(sendHoverExitDelayed);
                sendHoverExitDelayed.this$0.mHandler.postDelayed(sendHoverExitDelayed, r2.mDetermineUserIntentTimeout);
            }
            SendHoverExitDelayed sendHoverExitDelayed2 = this.mSendHoverExitDelayed;
            sendHoverExitDelayed2.cancel();
            sendHoverExitDelayed2.mPrototype = MotionEvent.obtain(motionEvent);
            sendHoverExitDelayed2.mRawEvent = MotionEvent.obtain(motionEvent2);
            sendHoverExitDelayed2.mPointerIdBits = pointerId;
            sendHoverExitDelayed2.mPolicyFlags = i;
            sendHoverExitDelayed2.this$0.mHandler.postDelayed(sendHoverExitDelayed2, r7.mDetermineUserIntentTimeout);
        } else {
            sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        }
        SendAccessibilityEventDelayed sendAccessibilityEventDelayed = this.mSendTouchInteractionEndDelayed;
        if (sendAccessibilityEventDelayed.isPending()) {
            return;
        }
        TouchExplorer.this.mHandler.postDelayed(sendAccessibilityEventDelayed, sendAccessibilityEventDelayed.mDelay);
    }

    public final boolean isDraggingGesture(MotionEvent motionEvent) {
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        float x2 = motionEvent.getX(1);
        float y2 = motionEvent.getY(1);
        TouchState.ReceivedPointerTracker receivedPointerTracker = this.mReceivedPointerTracker;
        float receivedPointerDownX = receivedPointerTracker.getReceivedPointerDownX(0);
        float receivedPointerDownY = receivedPointerTracker.getReceivedPointerDownY(0);
        float receivedPointerDownX2 = receivedPointerTracker.getReceivedPointerDownX(1);
        float receivedPointerDownY2 = receivedPointerTracker.getReceivedPointerDownY(1);
        float f = x - receivedPointerDownX;
        float f2 = y - receivedPointerDownY;
        if (f != FullScreenMagnificationGestureHandler.MAX_SCALE || f2 != FullScreenMagnificationGestureHandler.MAX_SCALE) {
            float hypot = (float) Math.hypot(f, f2);
            if (hypot > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f /= hypot;
            }
            if (hypot > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                f2 /= hypot;
            }
            float f3 = x2 - receivedPointerDownX2;
            float f4 = y2 - receivedPointerDownY2;
            if (f3 != FullScreenMagnificationGestureHandler.MAX_SCALE || f4 != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                float hypot2 = (float) Math.hypot(f3, f4);
                if (hypot2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    f3 /= hypot2;
                }
                if (hypot2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    f4 /= hypot2;
                }
                if ((f2 * f4) + (f * f3) < 0.52532196f) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int i;
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onAccessibilityEvent", 12288L, "event=" + accessibilityEvent);
        }
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 256 && !this.mSendHoverExitDelayed.isPending()) {
            Flags.sendA11yEventsBasedOnState();
            SendAccessibilityEventDelayed sendAccessibilityEventDelayed = this.mSendTouchExplorationEndDelayed;
            boolean isPending = sendAccessibilityEventDelayed.isPending();
            EventDispatcher eventDispatcher = this.mDispatcher;
            if (isPending) {
                sendAccessibilityEventDelayed.cancel();
                eventDispatcher.sendAccessibilityEvent(1024);
            }
            SendAccessibilityEventDelayed sendAccessibilityEventDelayed2 = this.mSendTouchInteractionEndDelayed;
            if (sendAccessibilityEventDelayed2.isPending()) {
                sendAccessibilityEventDelayed2.cancel();
                eventDispatcher.sendAccessibilityEvent(2097152);
            }
        }
        if (eventType == 32768) {
            Iterator it = ((ArrayList) this.mGestureDetector.mGestures).iterator();
            while (it.hasNext()) {
                GestureMatcher gestureMatcher = (GestureMatcher) it.next();
                if (gestureMatcher.mGestureId == 17) {
                    Slog.d("TouchExplorer", "clear GESTURE_DOUBLE_TAP");
                    gestureMatcher.clear();
                }
            }
        }
        TouchState touchState = this.mState;
        touchState.getClass();
        int eventType2 = accessibilityEvent.getEventType();
        if (eventType2 != 32) {
            if (eventType2 == 128 || eventType2 == 256) {
                touchState.mLastTouchedWindowId = accessibilityEvent.getWindowId();
            } else if (eventType2 != 32768) {
                if (eventType2 == 2097152) {
                    AccessibilityWindowManager accessibilityWindowManager = touchState.mAms.mA11yWindowManager;
                    if (accessibilityWindowManager.mHasProxy && (i = accessibilityWindowManager.mLastNonProxyTopFocusedDisplayId) != accessibilityWindowManager.mTopFocusedDisplayId) {
                        accessibilityWindowManager.mWindowManagerInternal.moveDisplayToTopIfAllowed(i);
                    }
                }
            }
            super.onAccessibilityEvent(accessibilityEvent);
        }
        MotionEvent motionEvent = touchState.mLastInjectedHoverEventForClick;
        if (motionEvent != null) {
            motionEvent.recycle();
            touchState.mLastInjectedHoverEventForClick = null;
        }
        touchState.mLastTouchedWindowId = -1;
        super.onAccessibilityEvent(accessibilityEvent);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        MotionEvent motionEvent = this.mState.mLastReceivedEvent;
        if (motionEvent != null) {
            clear(33554432, motionEvent);
        }
    }

    public final void onDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onDoubleTap", 12288L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        accessibilityManagerService.onTouchInteractionEnd();
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        GestureManifold gestureManifold = this.mGestureDetector;
        if (gestureManifold.mSendMotionEventsEnabled) {
            dispatchGesture(new AccessibilityGestureEvent(17, this.mDisplayId, gestureManifold.mEvents));
        }
        SendAccessibilityEventDelayed sendAccessibilityEventDelayed = this.mSendTouchExplorationEndDelayed;
        if (sendAccessibilityEventDelayed.isPending()) {
            sendAccessibilityEventDelayed.forceSendAndRemove();
        }
        EventDispatcher eventDispatcher = this.mDispatcher;
        eventDispatcher.sendAccessibilityEvent(2097152);
        this.mSendTouchInteractionEndDelayed.cancel();
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK;
        AccessibilityNodeInfo accessibilityFocusNotLocked = accessibilityManagerService.getInteractionBridge().getAccessibilityFocusNotLocked();
        if ((accessibilityFocusNotLocked == null || !accessibilityFocusNotLocked.getActionList().contains(accessibilityAction)) ? false : accessibilityFocusNotLocked.performAction(accessibilityAction.getId())) {
            return;
        }
        Slog.e("TouchExplorer", "ACTION_CLICK failed. Dispatching motion events to simulate click.");
        if (motionEvent == null || motionEvent2 == null) {
            return;
        }
        int actionIndex = motionEvent.getActionIndex();
        motionEvent.getPointerId(actionIndex);
        int computeClickLocation = eventDispatcher.computeClickLocation(eventDispatcher.mTempPoint);
        if (computeClickLocation == 0) {
            Slog.e("EventDispatcher", "Unable to compute click location.");
            return;
        }
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        motionEvent.getPointerProperties(actionIndex, pointerProperties);
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = r2.x;
        pointerCoords.y = r2.y;
        MotionEvent obtain = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), 0, 1, new MotionEvent.PointerProperties[]{pointerProperties}, new MotionEvent.PointerCoords[]{pointerCoords}, 0, 0, 1.0f, 1.0f, motionEvent.getDeviceId(), 0, motionEvent.getSource(), motionEvent.getDisplayId(), motionEvent.getFlags());
        boolean z = computeClickLocation == 1;
        int pointerId = 1 << obtain.getPointerId(obtain.getActionIndex());
        obtain.setTargetAccessibilityFocus(z);
        eventDispatcher.sendMotionEvent(0, pointerId, i, obtain, motionEvent2);
        obtain.setTargetAccessibilityFocus(z);
        eventDispatcher.sendMotionEvent(1, pointerId, i, obtain, motionEvent2);
        obtain.recycle();
    }

    public final void onDoubleTapAndHold(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onDoubleTapAndHold", 12288L, "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        EventDispatcher eventDispatcher = this.mDispatcher;
        Point point = eventDispatcher.mTempPoint;
        if (eventDispatcher.computeClickLocation(point) == 0 || motionEvent == null) {
            return;
        }
        int actionIndex = motionEvent.getActionIndex();
        eventDispatcher.mLongPressingPointerId = motionEvent.getPointerId(actionIndex);
        eventDispatcher.mLongPressingPointerDeltaX = ((int) motionEvent.getX(actionIndex)) - point.x;
        eventDispatcher.mLongPressingPointerDeltaY = ((int) motionEvent.getY(actionIndex)) - point.y;
        eventDispatcher.sendDownForAllNotInjectedPointers(i, motionEvent);
        sendHoverExitAndTouchExplorationGestureEndIfNeeded(i);
        GestureManifold gestureManifold = this.mGestureDetector;
        if (gestureManifold.mSendMotionEventsEnabled) {
            dispatchGesture(new AccessibilityGestureEvent(18, this.mDisplayId, gestureManifold.mEvents));
        }
        this.mState.setState(4);
    }

    public final void onGestureCompleted(AccessibilityGestureEvent accessibilityGestureEvent) {
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onGestureCompleted", 12288L, "event=" + accessibilityGestureEvent);
        }
        if (DEBUG) {
            Slog.d("TouchExplorer", "onGestureCompleted() : " + accessibilityGestureEvent.getGestureId());
        }
        if (accessibilityGestureEvent.getGestureId() == 1000) {
            if (AccessibilityUtils.isSetupWizard(this.mContext)) {
                Slog.d("TouchExplorer", "stop talkback by GESTURE_TO_STOP_TALKBACK");
                A11yLogger.insertLog(this.mContext, "A11Y9006");
                accessibilityManagerService.setScreenReaderEnabled(false);
                return;
            }
            return;
        }
        accessibilityManagerService.onTouchInteractionEnd();
        EventDispatcher eventDispatcher = this.mDispatcher;
        eventDispatcher.sendAccessibilityEvent(524288);
        eventDispatcher.sendAccessibilityEvent(2097152);
        ExitGestureDetectionModeDelayed exitGestureDetectionModeDelayed = this.mExitGestureDetectionModeDelayed;
        TouchExplorer.this.mHandler.removeCallbacks(exitGestureDetectionModeDelayed);
        this.mSendTouchInteractionEndDelayed.cancel();
        dispatchGesture(accessibilityGestureEvent);
    }

    public final void onGestureStarted() {
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        if (accessibilityManagerService.mTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityManagerService.mTraceManager.logTrace("TouchExplorer.onGestureStarted", 12288L);
        }
        if (DEBUG) {
            Slog.d("TouchExplorer", "onGestureStarted()");
        }
        this.mSendHoverEnterAndMoveDelayed.cancel();
        this.mSendHoverExitDelayed.cancel();
        ExitGestureDetectionModeDelayed exitGestureDetectionModeDelayed = this.mExitGestureDetectionModeDelayed;
        TouchExplorer.this.mHandler.postDelayed(exitGestureDetectionModeDelayed, 2000L);
        this.mDispatcher.sendAccessibilityEvent(262144);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x012b, code lost:
    
        if (r16.mGestureDetectionPassthroughRegion.contains(r3, r13) == false) goto L54;
     */
    @Override // com.android.server.accessibility.EventStreamTransformation
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMotionEvent(android.view.MotionEvent r17, android.view.MotionEvent r18, int r19) {
        /*
            Method dump skipped, instructions count: 1086
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.gestures.TouchExplorer.onMotionEvent(android.view.MotionEvent, android.view.MotionEvent, int):void");
    }

    public final void sendHoverExitAndTouchExplorationGestureEndIfNeeded(int i) {
        TouchState touchState = this.mState;
        MotionEvent motionEvent = touchState.mLastInjectedHoverEvent;
        if (motionEvent == null || motionEvent.getActionMasked() == 10) {
            return;
        }
        int pointerIdBits = motionEvent.getPointerIdBits();
        SendAccessibilityEventDelayed sendAccessibilityEventDelayed = this.mSendTouchExplorationEndDelayed;
        if (!sendAccessibilityEventDelayed.isPending()) {
            TouchExplorer.this.mHandler.postDelayed(sendAccessibilityEventDelayed, sendAccessibilityEventDelayed.mDelay);
        }
        this.mDispatcher.sendMotionEvent(10, pointerIdBits, i, motionEvent, touchState.mLastReceivedEvent);
    }

    public final void sendTouchExplorationGestureStartAndHoverEnterIfNeeded(int i) {
        TouchState touchState = this.mState;
        if (!touchState.isTouchExploring()) {
            this.mDispatcher.sendAccessibilityEvent(512);
        }
        MotionEvent motionEvent = touchState.mLastInjectedHoverEvent;
        if (motionEvent == null || motionEvent.getActionMasked() != 10) {
            return;
        }
        this.mDispatcher.sendMotionEvent(9, motionEvent.getPointerIdBits(), i, motionEvent, touchState.mLastReceivedEvent);
    }

    @Override // com.android.server.accessibility.BaseEventStreamTransformation, com.android.server.accessibility.EventStreamTransformation
    public final void setNext(EventStreamTransformation eventStreamTransformation) {
        this.mDispatcher.mReceiver = eventStreamTransformation;
        this.mNext = eventStreamTransformation;
    }

    public final void setServiceDetectsGestures(boolean z) {
        TouchState touchState = this.mState;
        touchState.getClass();
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("serviceDetectsGestures: ", "TouchState", z);
        }
        touchState.mServiceDetectsGesturesRequested = z;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TouchExplorer { mTouchState: ");
        sb.append(this.mState);
        sb.append(", mDetermineUserIntentTimeout: ");
        sb.append(this.mDetermineUserIntentTimeout);
        sb.append(", mDoubleTapSlop: ");
        sb.append(this.mDoubleTapSlop);
        sb.append(", mDraggingPointerId: ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mDraggingPointerId, sb, " }");
    }
}
