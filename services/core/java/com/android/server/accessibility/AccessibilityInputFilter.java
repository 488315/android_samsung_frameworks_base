package com.android.server.accessibility;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.InputFilter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.autoaction.AutoActionController;
import com.android.server.accessibility.gestures.GestureManifold;
import com.android.server.accessibility.gestures.TouchExplorer;
import com.android.server.accessibility.gestures.TouchState;
import com.android.server.accessibility.magnification.FullScreenMagnificationController;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.FullScreenMagnificationVibrationHelper;
import com.android.server.accessibility.magnification.MagnificationConnectionManager;
import com.android.server.accessibility.magnification.MagnificationController;
import com.android.server.accessibility.magnification.MagnificationGestureHandler;
import com.android.server.accessibility.magnification.OneFingerPanningSettingsProvider;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationPromptController;
import com.android.server.policy.WindowManagerPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessibilityInputFilter extends InputFilter implements EventStreamTransformation {
    public final AccessibilityManagerService mAms;
    public AutoActionController mAutoActionController;
    public AutoclickController mAutoclickController;
    public int mCombinedGenericMotionEventSources;
    public int mCombinedMotionEventObservedSources;
    public final Context mContext;
    public int mEnabledFeatures;
    public final SparseArray mEventHandler;
    public GenericMotionEventStreamState mGenericMotionEventStreamState;
    public boolean mInstalled;
    public KeyboardInterceptor mKeyboardInterceptor;
    public KeyboardEventStreamState mKeyboardStreamState;
    public final SparseArray mMagnificationGestureHandler;
    public final SparseArray mMotionEventInjectors;
    public final SparseArray mMouseStreamStates;
    public final PowerManager mPm;
    public final SparseArray mServiceDetectsGestures;
    public SamsungTapDuration mTapDuration;
    public SamsungTouchBlocker mTouchBlocker;
    public final SparseArray mTouchExplorer;
    public final SparseArray mTouchScreenStreamStates;
    public int mUserId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class EventStreamState {
        public int mSource = -1;

        public boolean inputSourceValid() {
            return this.mSource >= 0;
        }

        public void reset() {
            this.mSource = -1;
        }

        public boolean shouldProcessKeyEvent(KeyEvent keyEvent) {
            return false;
        }

        public boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean shouldProcessScroll() {
            return this instanceof GenericMotionEventStreamState;
        }

        public boolean updateInputSource(int i) {
            if (this.mSource == i) {
                return false;
            }
            reset();
            this.mSource = i;
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GenericMotionEventStreamState extends EventStreamState {
        public GenericMotionEventStreamState() {
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
            return AccessibilityInputFilter.m114$$Nest$manyServiceWantsGenericMotionEvent(AccessibilityInputFilter.this, motionEvent);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyboardEventStreamState extends EventStreamState {
        public SparseBooleanArray mEventSequenceStartedMap;

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean inputSourceValid() {
            return true;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            this.mSource = -1;
            this.mEventSequenceStartedMap.clear();
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessKeyEvent(KeyEvent keyEvent) {
            int deviceId = keyEvent.getDeviceId();
            if (this.mEventSequenceStartedMap.get(deviceId, false)) {
                return true;
            }
            boolean z = keyEvent.getAction() == 0;
            this.mEventSequenceStartedMap.put(deviceId, z);
            return z;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean updateInputSource(int i) {
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MouseEventStreamState extends EventStreamState {
        public boolean mMotionSequenceStarted;

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            this.mSource = -1;
            this.mMotionSequenceStarted = false;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
            boolean z = true;
            if (this.mMotionSequenceStarted) {
                return true;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0 && actionMasked != 7) {
                z = false;
            }
            this.mMotionSequenceStarted = z;
            return z;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessScroll() {
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TouchScreenEventStreamState extends EventStreamState {
        public boolean mHoverSequenceStarted;
        public boolean mTouchSequenceStarted;

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final void reset() {
            this.mSource = -1;
            this.mTouchSequenceStarted = false;
            this.mHoverSequenceStarted = false;
        }

        @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
        public final boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
            boolean z;
            if (motionEvent.isTouchEvent()) {
                if (this.mTouchSequenceStarted) {
                    return true;
                }
                z = motionEvent.getActionMasked() == 0;
                this.mTouchSequenceStarted = z;
                return z;
            }
            if (this.mHoverSequenceStarted) {
                return true;
            }
            z = motionEvent.getActionMasked() == 9;
            this.mHoverSequenceStarted = z;
            return z;
        }
    }

    /* renamed from: -$$Nest$manyServiceWantsGenericMotionEvent, reason: not valid java name */
    public static boolean m114$$Nest$manyServiceWantsGenericMotionEvent(AccessibilityInputFilter accessibilityInputFilter, MotionEvent motionEvent) {
        accessibilityInputFilter.getClass();
        if (!motionEvent.isFromSource(4098) || (accessibilityInputFilter.mEnabledFeatures & 2) == 0) {
            return (accessibilityInputFilter.mCombinedGenericMotionEventSources & (motionEvent.getSource() & (-256))) != 0;
        }
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityInputFilter(Context context, AccessibilityManagerService accessibilityManagerService) {
        super(context.getMainLooper());
        SparseArray sparseArray = new SparseArray(0);
        this.mTouchExplorer = new SparseArray(0);
        this.mMagnificationGestureHandler = new SparseArray(0);
        this.mMotionEventInjectors = new SparseArray(0);
        this.mServiceDetectsGestures = new SparseArray();
        this.mMouseStreamStates = new SparseArray(0);
        this.mTouchScreenStreamStates = new SparseArray(0);
        this.mCombinedGenericMotionEventSources = 0;
        this.mCombinedMotionEventObservedSources = 0;
        this.mContext = context;
        this.mAms = accessibilityManagerService;
        this.mPm = (PowerManager) context.getSystemService("power");
        this.mEventHandler = sparseArray;
    }

    public final void addFirstEventHandler(int i, EventStreamTransformation eventStreamTransformation) {
        EventStreamTransformation eventStreamTransformation2 = (EventStreamTransformation) this.mEventHandler.get(i);
        if (eventStreamTransformation2 != null) {
            eventStreamTransformation.setNext(eventStreamTransformation2);
        } else {
            eventStreamTransformation.setNext(this);
        }
        this.mEventHandler.put(i, eventStreamTransformation);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
    }

    public final MagnificationGestureHandler createMagnificationGestureHandler(Context context, int i) {
        int magnificationModeLocked;
        int i2 = this.mEnabledFeatures;
        boolean z = (i2 & 1) != 0;
        boolean z2 = (i2 & 4096) != 0;
        boolean z3 = (i2 & 64) != 0;
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            magnificationModeLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId).getMagnificationModeLocked(i);
        }
        if (magnificationModeLocked == 2) {
            Context createWindowContext = context.createWindowContext(2039, null);
            MagnificationConnectionManager magnificationConnectionManager = this.mAms.getMagnificationConnectionManager();
            AccessibilityManagerService accessibilityManagerService2 = this.mAms;
            return new WindowMagnificationGestureHandler(createWindowContext, magnificationConnectionManager, accessibilityManagerService2.mTraceManager, accessibilityManagerService2.mMagnificationController, z, z2, z3, i);
        }
        Context createWindowContext2 = context.createWindowContext(2027, null);
        FullScreenMagnificationVibrationHelper fullScreenMagnificationVibrationHelper = new FullScreenMagnificationVibrationHelper(createWindowContext2);
        FullScreenMagnificationController fullScreenMagnificationController = this.mAms.mMagnificationController.getFullScreenMagnificationController();
        AccessibilityManagerService accessibilityManagerService3 = this.mAms;
        AccessibilityTraceManager accessibilityTraceManager = accessibilityManagerService3.mTraceManager;
        MagnificationController magnificationController = accessibilityManagerService3.mMagnificationController;
        WindowMagnificationPromptController windowMagnificationPromptController = new WindowMagnificationPromptController(context, this.mUserId);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(createWindowContext2);
        Flags.enableMagnificationOneFingerPanningGesture();
        OneFingerPanningSettingsProvider oneFingerPanningSettingsProvider = new OneFingerPanningSettingsProvider();
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        oneFingerPanningSettingsProvider.mCached = atomicBoolean;
        atomicBoolean.set(OneFingerPanningSettingsProvider.isOneFingerPanningEnabledDefault(createWindowContext2));
        return new FullScreenMagnificationGestureHandler(createWindowContext2, fullScreenMagnificationController, accessibilityTraceManager, magnificationController, z, z2, z3, windowMagnificationPromptController, i, fullScreenMagnificationVibrationHelper, null, viewConfiguration, oneFingerPanningSettingsProvider);
    }

    public final void disableFeatures() {
        ArrayList validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            disableFeaturesForDisplay(((Display) validDisplayList.get(size)).getDisplayId());
        }
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            accessibilityManagerService.mMotionEventInjectors = null;
            accessibilityManagerService.mLock.notifyAll();
        }
        AutoclickController autoclickController = this.mAutoclickController;
        if (autoclickController != null) {
            autoclickController.onDestroy();
            this.mAutoclickController = null;
        }
        if (this.mKeyboardInterceptor != null) {
            this.mKeyboardInterceptor = null;
        }
        AutoActionController autoActionController = this.mAutoActionController;
        if (autoActionController != null) {
            autoActionController.onDestroy();
            this.mAutoActionController = null;
        }
        SamsungTapDuration samsungTapDuration = this.mTapDuration;
        if (samsungTapDuration != null) {
            samsungTapDuration.onDestroy();
            this.mTapDuration = null;
        }
        SamsungTouchBlocker samsungTouchBlocker = this.mTouchBlocker;
        if (samsungTouchBlocker != null) {
            samsungTouchBlocker.onDestroy();
            this.mTouchBlocker = null;
        }
        resetAllStreamState();
    }

    public final void disableFeaturesForDisplay(int i) {
        MotionEventInjector motionEventInjector = (MotionEventInjector) this.mMotionEventInjectors.get(i);
        if (motionEventInjector != null) {
            motionEventInjector.onDestroy();
            this.mMotionEventInjectors.remove(i);
        }
        TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.get(i);
        if (touchExplorer != null) {
            MotionEvent motionEvent = touchExplorer.mState.mLastReceivedEvent;
            if (motionEvent != null) {
                touchExplorer.clear(33554432, motionEvent);
            }
            this.mTouchExplorer.remove(i);
        }
        MagnificationGestureHandler magnificationGestureHandler = (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(i);
        if (magnificationGestureHandler != null) {
            magnificationGestureHandler.onDestroy();
            this.mMagnificationGestureHandler.remove(i);
        }
        if (((EventStreamTransformation) this.mEventHandler.get(i)) != null) {
            this.mEventHandler.remove(i);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mEventHandler == null) {
            return;
        }
        printWriter.append("A11yInputFilter Info : ");
        printWriter.println();
        ArrayList validDisplayList = this.mAms.getValidDisplayList();
        for (int i = 0; i < validDisplayList.size(); i++) {
            int displayId = ((Display) validDisplayList.get(i)).getDisplayId();
            EventStreamTransformation eventStreamTransformation = (EventStreamTransformation) this.mEventHandler.get(displayId);
            if (eventStreamTransformation != null) {
                printWriter.append("Enabled features of Display [");
                printWriter.append((CharSequence) Integer.toString(displayId));
                printWriter.append("] = ");
                StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
                while (eventStreamTransformation != null) {
                    if (eventStreamTransformation instanceof MagnificationGestureHandler) {
                        stringJoiner.add("MagnificationGesture");
                    } else if (eventStreamTransformation instanceof KeyboardInterceptor) {
                        stringJoiner.add("KeyboardInterceptor");
                    } else if (eventStreamTransformation instanceof TouchExplorer) {
                        stringJoiner.add("TouchExplorer");
                    } else if (eventStreamTransformation instanceof AutoclickController) {
                        stringJoiner.add("AutoclickController");
                    } else if (eventStreamTransformation instanceof MotionEventInjector) {
                        stringJoiner.add("MotionEventInjector");
                    }
                    eventStreamTransformation = eventStreamTransformation.getNext();
                }
                printWriter.append((CharSequence) stringJoiner.toString());
            }
            printWriter.println();
        }
        try {
            printWriter.append((CharSequence) ("mEnabledFeatures : " + String.format("0x%08X", Integer.valueOf(this.mEnabledFeatures))));
            if (this.mTouchExplorer.size() > 0) {
            }
            if (this.mMagnificationGestureHandler.size() > 0) {
            }
        } catch (Exception unused) {
        }
    }

    public final void enableFeatures() {
        resetAllStreamState();
        ArrayList validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            enableFeaturesForDisplay((Display) validDisplayList.get(size));
        }
        if ((this.mEnabledFeatures & 16) != 0) {
            AccessibilityManagerService accessibilityManagerService = this.mAms;
            SparseArray sparseArray = this.mMotionEventInjectors;
            synchronized (accessibilityManagerService.mLock) {
                accessibilityManagerService.mMotionEventInjectors = sparseArray;
                accessibilityManagerService.mLock.notifyAll();
            }
        }
        if ((this.mEnabledFeatures & 4) != 0) {
            KeyboardInterceptor keyboardInterceptor = new KeyboardInterceptor(this.mAms, (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class));
            this.mKeyboardInterceptor = keyboardInterceptor;
            addFirstEventHandler(0, keyboardInterceptor);
        }
    }

    public final void enableFeaturesForDisplay(Display display) {
        Context createDisplayContext = this.mContext.createDisplayContext(display);
        int displayId = display.getDisplayId();
        if (this.mAms.mProxyManager.isProxyedDisplay(displayId)) {
            return;
        }
        if (!this.mServiceDetectsGestures.contains(displayId)) {
            this.mServiceDetectsGestures.put(displayId, Boolean.FALSE);
        }
        if ((this.mEnabledFeatures & 8) != 0) {
            if (this.mAutoclickController == null) {
                this.mAutoclickController = new AutoclickController(this.mContext, this.mUserId, this.mAms.mTraceManager);
            }
            addFirstEventHandler(displayId, this.mAutoclickController);
        }
        if ((this.mEnabledFeatures & 2) != 0) {
            TouchExplorer touchExplorer = new TouchExplorer(createDisplayContext, this.mAms, null, new Handler(createDisplayContext.getMainLooper()));
            int i = this.mEnabledFeatures;
            int i2 = i & 128;
            GestureManifold gestureManifold = touchExplorer.mGestureDetector;
            if (i2 != 0) {
                gestureManifold.mServiceHandlesDoubleTap = true;
            }
            if ((i & 256) != 0 && !gestureManifold.mMultiFingerGesturesEnabled) {
                gestureManifold.mMultiFingerGesturesEnabled = true;
                ((ArrayList) gestureManifold.mGestures).addAll(gestureManifold.mMultiFingerGestures);
            }
            if ((this.mEnabledFeatures & 512) != 0 && !gestureManifold.mTwoFingerPassthroughEnabled) {
                gestureManifold.mTwoFingerPassthroughEnabled = true;
                ((ArrayList) gestureManifold.mMultiFingerGestures).removeAll(gestureManifold.mTwoFingerSwipes);
                ((ArrayList) gestureManifold.mGestures).removeAll(gestureManifold.mTwoFingerSwipes);
            }
            int i3 = this.mEnabledFeatures;
            if ((i3 & 1024) != 0) {
                gestureManifold.mSendMotionEventsEnabled = true;
            }
            boolean z = (i3 & 1) != 0;
            boolean z2 = (i3 & 64) != 0;
            if (z || z2) {
                int i4 = touchExplorer.mDetermineUserIntentTimeout + 50;
                touchExplorer.mDetermineUserIntentTimeout = i4;
                touchExplorer.mSendTouchExplorationEndDelayed.mDelay = i4;
                touchExplorer.mSendTouchInteractionEndDelayed.mDelay = i4;
            }
            touchExplorer.setServiceDetectsGestures(((Boolean) this.mServiceDetectsGestures.get(displayId)).booleanValue());
            addFirstEventHandler(displayId, touchExplorer);
            this.mTouchExplorer.put(displayId, touchExplorer);
        }
        if ((this.mEnabledFeatures & 2048) != 0) {
            addFirstEventHandler(displayId, new BaseEventStreamTransformation() { // from class: com.android.server.accessibility.AccessibilityInputFilter.1
                /* JADX WARN: Code restructure failed: missing block: B:9:0x002e, code lost:
                
                    if (((r0.mCombinedMotionEventObservedSources & r0.mCombinedGenericMotionEventSources) & (r6.getSource() & (-256))) != 0) goto L13;
                 */
                @Override // com.android.server.accessibility.EventStreamTransformation
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onMotionEvent(android.view.MotionEvent r6, android.view.MotionEvent r7, int r8) {
                    /*
                        r5 = this;
                        com.android.server.accessibility.AccessibilityInputFilter r0 = com.android.server.accessibility.AccessibilityInputFilter.this
                        boolean r1 = com.android.server.accessibility.AccessibilityInputFilter.m114$$Nest$manyServiceWantsGenericMotionEvent(r0, r6)
                        r2 = 1
                        if (r1 == 0) goto L32
                        com.android.server.accessibility.AccessibilityManagerService r1 = r0.mAms
                        boolean r1 = r1.sendMotionEventToListeningServices(r6)
                        r1 = r1 ^ r2
                        r0.getClass()
                        r3 = 4098(0x1002, float:5.743E-42)
                        boolean r3 = r6.isFromSource(r3)
                        if (r3 == 0) goto L22
                        int r3 = r0.mEnabledFeatures
                        r3 = r3 & 2
                        if (r3 == 0) goto L22
                        goto L31
                    L22:
                        int r3 = r6.getSource()
                        r3 = r3 & (-256(0xffffffffffffff00, float:NaN))
                        int r4 = r0.mCombinedGenericMotionEventSources
                        int r0 = r0.mCombinedMotionEventObservedSources
                        r0 = r0 & r4
                        r0 = r0 & r3
                        if (r0 == 0) goto L31
                        goto L32
                    L31:
                        r2 = r1
                    L32:
                        if (r2 == 0) goto L37
                        super.onMotionEvent(r6, r7, r8)
                    L37:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityInputFilter.AnonymousClass1.onMotionEvent(android.view.MotionEvent, android.view.MotionEvent, int):void");
                }
            });
        }
        int i5 = this.mEnabledFeatures;
        if ((i5 & 32) != 0 || (i5 & 1) != 0 || (i5 & 4096) != 0 || (i5 & 64) != 0) {
            MagnificationGestureHandler createMagnificationGestureHandler = createMagnificationGestureHandler(createDisplayContext, displayId);
            addFirstEventHandler(displayId, createMagnificationGestureHandler);
            this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
        }
        if ((this.mEnabledFeatures & 16) != 0) {
            MotionEventInjector motionEventInjector = new MotionEventInjector(this.mContext.getMainLooper(), this.mAms.mTraceManager);
            addFirstEventHandler(displayId, motionEventInjector);
            this.mMotionEventInjectors.put(displayId, motionEventInjector);
        }
        if ((this.mEnabledFeatures & Integer.MIN_VALUE) != 0) {
            SamsungTapDuration samsungTapDuration = new SamsungTapDuration(this.mContext, this.mUserId);
            this.mTapDuration = samsungTapDuration;
            addFirstEventHandler(displayId, samsungTapDuration);
        }
        if ((this.mEnabledFeatures & 16777216) != 0) {
            SamsungTouchBlocker samsungTouchBlocker = new SamsungTouchBlocker(this.mContext, this.mUserId);
            this.mTouchBlocker = samsungTouchBlocker;
            addFirstEventHandler(displayId, samsungTouchBlocker);
        }
        int i6 = this.mEnabledFeatures;
        if ((i6 & 2048) == 0 && (i6 & 4096) == 0) {
            return;
        }
        AutoActionController autoActionController = new AutoActionController(this.mContext, this.mUserId);
        this.mAutoActionController = autoActionController;
        addFirstEventHandler(displayId, autoActionController);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final EventStreamTransformation getNext() {
        return null;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:83:0x00a1, code lost:
    
        if (r5 != 4096) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onInputEvent(android.view.InputEvent r8, int r9) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityInputFilter.onInputEvent(android.view.InputEvent, int):void");
    }

    public final void onInstalled() {
        this.mInstalled = true;
        disableFeatures();
        enableFeatures();
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            accessibilityManagerService.mInputFilterInstalled = true;
            accessibilityManagerService.mLock.notifyAll();
        }
        super.onInstalled();
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onKeyEvent(KeyEvent keyEvent, int i) {
        if (this.mInstalled) {
            sendInputEvent(keyEvent, i);
        } else {
            Slog.w("AccessibilityInputFilter", "onKeyEvent called before input filter installed!");
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mInstalled) {
            sendInputEvent(motionEvent, i);
        } else {
            Slog.w("AccessibilityInputFilter", "onMotionEvent called before input filter installed!");
        }
    }

    public final void onUninstalled() {
        this.mInstalled = false;
        disableFeatures();
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            accessibilityManagerService.mInputFilterInstalled = false;
            accessibilityManagerService.mLock.notifyAll();
        }
        super.onUninstalled();
    }

    public final void refreshMagnificationMode(Display display) {
        int magnificationModeLocked;
        int displayId = display.getDisplayId();
        MagnificationGestureHandler magnificationGestureHandler = (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(displayId);
        if (magnificationGestureHandler == null) {
            return;
        }
        int mode = magnificationGestureHandler.getMode();
        AccessibilityManagerService accessibilityManagerService = this.mAms;
        synchronized (accessibilityManagerService.mLock) {
            magnificationModeLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId).getMagnificationModeLocked(displayId);
        }
        if (mode == magnificationModeLocked) {
            return;
        }
        magnificationGestureHandler.onDestroy();
        MagnificationGestureHandler createMagnificationGestureHandler = createMagnificationGestureHandler(this.mContext.createDisplayContext(display), displayId);
        EventStreamTransformation eventStreamTransformation = (EventStreamTransformation) this.mEventHandler.get(displayId);
        if (eventStreamTransformation != null) {
            if (eventStreamTransformation == magnificationGestureHandler) {
                createMagnificationGestureHandler.mNext = magnificationGestureHandler.mNext;
                this.mEventHandler.put(displayId, createMagnificationGestureHandler);
            } else {
                while (true) {
                    if (eventStreamTransformation == null) {
                        break;
                    }
                    if (eventStreamTransformation.getNext() == magnificationGestureHandler) {
                        eventStreamTransformation.setNext(createMagnificationGestureHandler);
                        createMagnificationGestureHandler.mNext = magnificationGestureHandler.mNext;
                        break;
                    }
                    eventStreamTransformation = eventStreamTransformation.getNext();
                }
            }
        }
        this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
    }

    public final void requestDelegating(int i) {
        if (this.mTouchExplorer.contains(i)) {
            TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.get(i);
            TouchState touchState = touchExplorer.mState;
            if (touchState.mServiceDetectsGestures) {
                if (touchState.isTouchExploring()) {
                    TouchExplorer.SendHoverExitDelayed sendHoverExitDelayed = touchExplorer.mSendHoverExitDelayed;
                    if (sendHoverExitDelayed.isPending() && sendHoverExitDelayed.isPending()) {
                        sendHoverExitDelayed.run();
                        sendHoverExitDelayed.cancel();
                    }
                    TouchExplorer.SendAccessibilityEventDelayed sendAccessibilityEventDelayed = touchExplorer.mSendTouchExplorationEndDelayed;
                    if (sendAccessibilityEventDelayed.isPending()) {
                        sendAccessibilityEventDelayed.forceSendAndRemove();
                    }
                }
                int i2 = touchState.mState;
                if (!(i2 == 1) && i2 != 3) {
                    Slog.e("TouchExplorer", "Error: Trying to delegate from " + TouchState.getStateSymbolicName(touchState.mState));
                    return;
                }
                MotionEvent motionEvent = touchState.mLastReceivedEvent;
                MotionEvent motionEvent2 = touchState.mLastReceivedRawEvent;
                if (motionEvent == null || motionEvent2 == null) {
                    Slog.d("TouchExplorer", "Unable to start delegating: unable to get last received event.");
                    return;
                }
                int i3 = touchState.mLastReceivedPolicyFlags;
                if (i2 == 3) {
                    touchExplorer.mDispatcher.sendMotionEvent(1, 1 << touchExplorer.mDraggingPointerId, i3, motionEvent, motionEvent2);
                }
                touchState.setState(4);
                touchExplorer.mDispatcher.sendDownForAllNotInjectedPointers(i3, motionEvent);
            }
        }
    }

    public final void requestDragging(int i, int i2) {
        if (this.mTouchExplorer.contains(i)) {
            TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.get(i);
            TouchState touchState = touchExplorer.mState;
            if (touchState.mServiceDetectsGestures) {
                if (i2 >= 0 && i2 <= 32) {
                    TouchState.ReceivedPointerTracker receivedPointerTracker = touchExplorer.mReceivedPointerTracker;
                    if (((1 << i2) & receivedPointerTracker.mReceivedPointersDown) != 0) {
                        if (touchState.isTouchExploring()) {
                            TouchExplorer.SendHoverExitDelayed sendHoverExitDelayed = touchExplorer.mSendHoverExitDelayed;
                            if (sendHoverExitDelayed.isPending() && sendHoverExitDelayed.isPending()) {
                                sendHoverExitDelayed.run();
                                sendHoverExitDelayed.cancel();
                            }
                            TouchExplorer.SendAccessibilityEventDelayed sendAccessibilityEventDelayed = touchExplorer.mSendTouchExplorationEndDelayed;
                            if (sendAccessibilityEventDelayed.isPending()) {
                                sendAccessibilityEventDelayed.forceSendAndRemove();
                            }
                        }
                        if (touchState.mState != 1) {
                            Slog.e("TouchExplorer", "Error: Trying to drag from " + TouchState.getStateSymbolicName(touchState.mState));
                            return;
                        }
                        touchExplorer.mDraggingPointerId = i2;
                        if (TouchExplorer.DEBUG) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Drag requested on pointer "), touchExplorer.mDraggingPointerId, "TouchExplorer");
                        }
                        MotionEvent motionEvent = touchState.mLastReceivedEvent;
                        MotionEvent motionEvent2 = touchState.mLastReceivedRawEvent;
                        if (motionEvent == null || motionEvent2 == null) {
                            Slog.e("TouchExplorer", "Unable to start dragging: unable to get last event.");
                            return;
                        }
                        int i3 = touchState.mLastReceivedPolicyFlags;
                        int i4 = 1 << touchExplorer.mDraggingPointerId;
                        motionEvent.setEdgeFlags(receivedPointerTracker.mLastReceivedDownEdgeFlags);
                        MotionEvent computeDownEventForDrag = touchExplorer.computeDownEventForDrag(motionEvent);
                        touchState.setState(3);
                        if (computeDownEventForDrag == null) {
                            touchExplorer.mDispatcher.sendMotionEvent(0, i4, i3, motionEvent, motionEvent2);
                            return;
                        } else {
                            touchExplorer.mDispatcher.sendMotionEvent(0, i4, i3, computeDownEventForDrag, motionEvent2);
                            touchExplorer.mDispatcher.sendMotionEvent(2, i4, i3, motionEvent, motionEvent2);
                            return;
                        }
                    }
                }
                NandswapManager$$ExternalSyntheticOutline0.m(i2, "Trying to drag with invalid pointer: ", "TouchExplorer");
            }
        }
    }

    public final void requestTouchExploration(int i) {
        MotionEvent motionEvent;
        if (this.mTouchExplorer.contains(i)) {
            TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.get(i);
            if (TouchExplorer.DEBUG) {
                touchExplorer.getClass();
                Slog.d("TouchExplorer", "Starting touch explorer from service.");
            }
            TouchState touchState = touchExplorer.mState;
            if (touchState.mServiceDetectsGestures) {
                if (touchState.mState == 1) {
                    Handler handler = touchExplorer.mHandler;
                    TouchExplorer.SendHoverExitDelayed sendHoverExitDelayed = touchExplorer.mSendHoverEnterAndMoveDelayed;
                    handler.removeCallbacks(sendHoverExitDelayed);
                    TouchState.ReceivedPointerTracker receivedPointerTracker = touchExplorer.mReceivedPointerTracker;
                    int primaryPointerId = receivedPointerTracker.getPrimaryPointerId();
                    if (primaryPointerId == -1 && (motionEvent = touchState.mLastReceivedEvent) != null) {
                        primaryPointerId = motionEvent.getPointerId(0);
                    }
                    if (primaryPointerId == -1) {
                        Slog.e("TouchExplorer", "Unable to find a valid pointer for touch exploration.");
                        return;
                    }
                    touchExplorer.sendHoverExitAndTouchExplorationGestureEndIfNeeded(primaryPointerId);
                    int i2 = touchState.mLastReceivedPolicyFlags;
                    sendHoverExitDelayed.mPointerIdBits = 1 << primaryPointerId;
                    sendHoverExitDelayed.mPolicyFlags = i2;
                    sendHoverExitDelayed.run();
                    sendHoverExitDelayed.clear();
                    if (Integer.bitCount(receivedPointerTracker.mReceivedPointersDown) == 0) {
                        touchExplorer.sendHoverExitAndTouchExplorationGestureEndIfNeeded(i2);
                    }
                }
            }
        }
    }

    public final void resetAllStreamState() {
        ArrayList validDisplayList = this.mAms.getValidDisplayList();
        for (int size = validDisplayList.size() - 1; size >= 0; size--) {
            resetStreamStateForDisplay(((Display) validDisplayList.get(size)).getDisplayId());
        }
        KeyboardEventStreamState keyboardEventStreamState = this.mKeyboardStreamState;
        if (keyboardEventStreamState != null) {
            keyboardEventStreamState.reset();
        }
    }

    public final void resetStreamStateForDisplay(int i) {
        EventStreamState eventStreamState = (EventStreamState) this.mTouchScreenStreamStates.get(i);
        if (eventStreamState != null) {
            eventStreamState.reset();
            this.mTouchScreenStreamStates.remove(i);
        }
        EventStreamState eventStreamState2 = (EventStreamState) this.mMouseStreamStates.get(i);
        if (eventStreamState2 != null) {
            eventStreamState2.reset();
            this.mMouseStreamStates.remove(i);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void setNext(EventStreamTransformation eventStreamTransformation) {
    }

    public final void setUserAndEnabledFeatures(int i, int i2) {
        if (this.mEnabledFeatures == i2 && this.mUserId == i) {
            return;
        }
        if (this.mInstalled) {
            disableFeatures();
        }
        this.mUserId = i;
        this.mEnabledFeatures = i2;
        if (this.mInstalled) {
            enableFeatures();
        }
    }
}
