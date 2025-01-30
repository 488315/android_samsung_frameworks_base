package com.android.server.accessibility;

import android.content.Context;
import android.graphics.Region;
import android.os.PowerManager;
import android.os.IInstalld;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.InputEvent;
import android.view.InputFilter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.accessibility.autoaction.AutoActionController;
import com.android.server.accessibility.gestures.TouchExplorer;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationPromptController;
import com.android.server.policy.WindowManagerPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringJoiner;

/* loaded from: classes.dex */
public class AccessibilityInputFilter extends InputFilter implements EventStreamTransformation {
  public static final String TAG = AccessibilityInputFilter.class.getSimpleName();
  public final AccessibilityManagerService mAms;
  public AutoActionController mAutoActionController;
  public AutoclickController mAutoclickController;
  public SamsungBounceKeys mBounceKeys;
  public int mCombinedGenericMotionEventSources;
  public final Context mContext;
  public int mEnabledFeatures;
  public final SparseArray mEventHandler;
  public GenericMotionEventStreamState mGenericMotionEventStreamState;
  public boolean mInstalled;
  public KeyboardInterceptor mKeyboardInterceptor;
  public EventStreamState mKeyboardStreamState;
  public final SparseArray mMagnificationGestureHandler;
  public final SparseArray mMotionEventInjectors;
  public final SparseArray mMouseStreamStates;
  public final PowerManager mPm;
  public SparseArray mServiceDetectsGestures;
  public SamsungSlowKeys mSlowKeys;
  public SamsungStickyKeys mStickyKeys;
  public SamsungTapDuration mTapDuration;
  public SamsungTouchBlocker mTouchBlocker;
  public final SparseArray mTouchExplorer;
  public final SparseArray mTouchScreenStreamStates;
  public int mUserId;

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void clearEvents(int i) {}

  @Override // com.android.server.accessibility.EventStreamTransformation
  public EventStreamTransformation getNext() {
    return null;
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {}

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void setNext(EventStreamTransformation eventStreamTransformation) {}

  public AccessibilityInputFilter(
      Context context, AccessibilityManagerService accessibilityManagerService) {
    this(context, accessibilityManagerService, new SparseArray(0));
  }

  public AccessibilityInputFilter(
      Context context,
      AccessibilityManagerService accessibilityManagerService,
      SparseArray sparseArray) {
    super(context.getMainLooper());
    this.mTouchExplorer = new SparseArray(0);
    this.mMagnificationGestureHandler = new SparseArray(0);
    this.mMotionEventInjectors = new SparseArray(0);
    this.mServiceDetectsGestures = new SparseArray();
    this.mMouseStreamStates = new SparseArray(0);
    this.mTouchScreenStreamStates = new SparseArray(0);
    this.mCombinedGenericMotionEventSources = 0;
    this.mContext = context;
    this.mAms = accessibilityManagerService;
    this.mPm = (PowerManager) context.getSystemService("power");
    this.mEventHandler = sparseArray;
  }

  public void onInstalled() {
    this.mInstalled = true;
    disableFeatures();
    enableFeatures();
    this.mAms.onInputFilterInstalled(true);
    super.onInstalled();
  }

  public void onUninstalled() {
    this.mInstalled = false;
    disableFeatures();
    this.mAms.onInputFilterInstalled(false);
    super.onUninstalled();
  }

  public void onDisplayAdded(Display display) {
    enableFeaturesForDisplayIfInstalled(display);
  }

  public void onDisplayRemoved(int i) {
    disableFeaturesForDisplayIfInstalled(i);
  }

  public void setSecDebug(boolean z) {
    int size = this.mTouchExplorer.size();
    for (int i = 0; i < size; i++) {
      TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.valueAt(i);
      if (touchExplorer != null) {
        touchExplorer.setSecDebug(z);
      }
    }
  }

  public void onInputEvent(InputEvent inputEvent, int i) {
    if (this.mAms.getTraceManager().isA11yTracingEnabledForTypes(4096L)) {
      this.mAms
          .getTraceManager()
          .logTrace(TAG + ".onInputEvent", 4096L, "event=" + inputEvent + ";policyFlags=" + i);
    }
    if (this.mEventHandler.size() == 0) {
      super.onInputEvent(inputEvent, i);
      return;
    }
    EventStreamState eventStreamState = getEventStreamState(inputEvent);
    if (eventStreamState == null) {
      super.onInputEvent(inputEvent, i);
      return;
    }
    int source = inputEvent.getSource();
    int displayId = inputEvent.getDisplayId();
    if ((1073741824 & i) == 0) {
      eventStreamState.reset();
      clearEventStreamHandler(displayId, source);
      super.onInputEvent(inputEvent, i);
      return;
    }
    if (eventStreamState.updateInputSource(inputEvent.getSource())) {
      clearEventStreamHandler(displayId, source);
    }
    if (!eventStreamState.inputSourceValid()) {
      super.onInputEvent(inputEvent, i);
      return;
    }
    if (inputEvent instanceof MotionEvent) {
      if ((this.mEnabledFeatures & (-2130699301)) != 0) {
        processMotionEvent(eventStreamState, (MotionEvent) inputEvent, i);
        return;
      } else {
        super.onInputEvent(inputEvent, i);
        return;
      }
    }
    if (inputEvent instanceof KeyEvent) {
      processKeyEvent(eventStreamState, (KeyEvent) inputEvent, i);
    }
  }

  public final EventStreamState getEventStreamState(InputEvent inputEvent) {
    if (inputEvent instanceof MotionEvent) {
      int displayId = inputEvent.getDisplayId();
      if (this.mGenericMotionEventStreamState == null) {
        this.mGenericMotionEventStreamState = new GenericMotionEventStreamState();
      }
      MotionEvent motionEvent = (MotionEvent) inputEvent;
      if (this.mGenericMotionEventStreamState.shouldProcessMotionEvent(motionEvent)) {
        return this.mGenericMotionEventStreamState;
      }
      if (inputEvent.isFromSource(8194)) {
        EventStreamState eventStreamState =
            (EventStreamState) this.mMouseStreamStates.get(displayId);
        if (eventStreamState != null) {
          return eventStreamState;
        }
        MouseEventStreamState mouseEventStreamState = new MouseEventStreamState();
        this.mMouseStreamStates.put(displayId, mouseEventStreamState);
        return mouseEventStreamState;
      }
      if (inputEvent.isFromSource(4098)) {
        if (motionEvent.getToolType(0) == 2) {
          int i = this.mEnabledFeatures;
          if ((i & 2) != 0 || i == 8 || i == 2048 || i == 4096) {
            return null;
          }
        }
        EventStreamState eventStreamState2 =
            (EventStreamState) this.mTouchScreenStreamStates.get(displayId);
        if (eventStreamState2 != null) {
          return eventStreamState2;
        }
        TouchScreenEventStreamState touchScreenEventStreamState = new TouchScreenEventStreamState();
        this.mTouchScreenStreamStates.put(displayId, touchScreenEventStreamState);
        return touchScreenEventStreamState;
      }
    } else if ((inputEvent instanceof KeyEvent)
        && inputEvent.isFromSource(
            FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP)) {
      if (this.mKeyboardStreamState == null) {
        this.mKeyboardStreamState = new KeyboardEventStreamState();
      }
      return this.mKeyboardStreamState;
    }
    return null;
  }

  public final void clearEventStreamHandler(int i, int i2) {
    EventStreamTransformation eventStreamTransformation =
        (EventStreamTransformation) this.mEventHandler.get(i);
    if (eventStreamTransformation != null) {
      eventStreamTransformation.clearEvents(i2);
    }
  }

  public final void processMotionEvent(
      EventStreamState eventStreamState, MotionEvent motionEvent, int i) {
    if (!eventStreamState.shouldProcessScroll() && motionEvent.getActionMasked() == 8) {
      super.onInputEvent(motionEvent, i);
    } else if (eventStreamState.shouldProcessMotionEvent(motionEvent)) {
      handleMotionEvent(motionEvent, i);
    }
  }

  public final void processKeyEvent(EventStreamState eventStreamState, KeyEvent keyEvent, int i) {
    if (!eventStreamState.shouldProcessKeyEvent(keyEvent)) {
      super.onInputEvent(keyEvent, i);
    } else {
      ((EventStreamTransformation) this.mEventHandler.get(0)).onKeyEvent(keyEvent, i);
    }
  }

  public final void handleMotionEvent(MotionEvent motionEvent, int i) {
    if (motionEvent.getToolType(0) != 2) {
      this.mPm.userActivity(motionEvent.getEventTime(), false);
    }
    MotionEvent obtain = MotionEvent.obtain(motionEvent);
    int displayId = motionEvent.getDisplayId();
    EventStreamTransformation eventStreamTransformation =
        (EventStreamTransformation)
            this.mEventHandler.get(isDisplayIdValid(displayId) ? displayId : 0);
    if (eventStreamTransformation != null) {
      eventStreamTransformation.onMotionEvent(obtain, motionEvent, i);
    }
    obtain.recycle();
  }

  public final boolean isDisplayIdValid(int i) {
    return this.mEventHandler.get(i) != null;
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    if (this.mInstalled) {
      sendInputEvent(motionEvent, i);
      return;
    }
    Slog.d(TAG, "onMotionEvent mInstalled : " + this.mInstalled);
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void onKeyEvent(KeyEvent keyEvent, int i) {
    if (this.mInstalled) {
      sendInputEvent(keyEvent, i);
      return;
    }
    Slog.d(TAG, "onKeyEvent mInstalled : " + this.mInstalled);
  }

  public void setUserAndEnabledFeatures(int i, int i2) {
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

  public void notifyAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    for (int i = 0; i < this.mEventHandler.size(); i++) {
      EventStreamTransformation eventStreamTransformation =
          (EventStreamTransformation) this.mEventHandler.valueAt(i);
      if (eventStreamTransformation != null) {
        eventStreamTransformation.onAccessibilityEvent(accessibilityEvent);
      }
    }
  }

  public void notifyAccessibilityButtonClicked(int i) {
    MagnificationGestureHandler magnificationGestureHandler;
    if (this.mMagnificationGestureHandler.size() == 0
        || (magnificationGestureHandler =
                (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(i))
            == null) {
      return;
    }
    magnificationGestureHandler.notifyShortcutTriggered();
  }

  public void magnificationDisactivate(int i) {
    MagnificationGestureHandler magnificationGestureHandler;
    if (this.mMagnificationGestureHandler.size() == 0
        || (magnificationGestureHandler =
                (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(i))
            == null) {
      return;
    }
    magnificationGestureHandler.magnificationDisactivate();
  }

  public final void enableFeatures() {
    resetAllStreamState();
    ArrayList validDisplayList = this.mAms.getValidDisplayList();
    for (int size = validDisplayList.size() - 1; size >= 0; size--) {
      enableFeaturesForDisplay((Display) validDisplayList.get(size));
    }
    enableDisplayIndependentFeatures();
  }

  public final void enableFeaturesForDisplay(Display display) {
    Context createDisplayContext = this.mContext.createDisplayContext(display);
    int displayId = display.getDisplayId();
    if (this.mAms.isDisplayProxyed(displayId)) {
      return;
    }
    if (!this.mServiceDetectsGestures.contains(displayId)) {
      this.mServiceDetectsGestures.put(displayId, Boolean.FALSE);
    }
    if ((this.mEnabledFeatures & 8) != 0) {
      if (this.mAutoclickController == null) {
        this.mAutoclickController =
            new AutoclickController(this.mContext, this.mUserId, this.mAms.getTraceManager());
      }
      addFirstEventHandler(displayId, this.mAutoclickController);
    }
    if ((this.mEnabledFeatures & 2) != 0) {
      TouchExplorer touchExplorer = new TouchExplorer(createDisplayContext, this.mAms);
      if ((this.mEnabledFeatures & 128) != 0) {
        touchExplorer.setServiceHandlesDoubleTap(true);
      }
      if ((this.mEnabledFeatures & 256) != 0) {
        touchExplorer.setMultiFingerGesturesEnabled(true);
      }
      if ((this.mEnabledFeatures & 512) != 0) {
        touchExplorer.setTwoFingerPassthroughEnabled(true);
      }
      if ((this.mEnabledFeatures & 1024) != 0) {
        touchExplorer.setSendMotionEventsEnabled(true);
      }
      int i = this.mEnabledFeatures;
      boolean z = (i & 1) != 0;
      boolean z2 = (i & 64) != 0;
      if (z || z2) {
        touchExplorer.adjustDetermineUserIntentTimeoutWithDelay();
      }
      touchExplorer.setServiceDetectsGestures(
          ((Boolean) this.mServiceDetectsGestures.get(displayId)).booleanValue());
      addFirstEventHandler(displayId, touchExplorer);
      this.mTouchExplorer.put(displayId, touchExplorer);
    }
    if ((this.mEnabledFeatures & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
      addFirstEventHandler(
          displayId,
          new BaseEventStreamTransformation() { // from class:
            // com.android.server.accessibility.AccessibilityInputFilter.1
            @Override // com.android.server.accessibility.EventStreamTransformation
            public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i2) {
              if (AccessibilityInputFilter.this.anyServiceWantsGenericMotionEvent(motionEvent2)
                  && AccessibilityInputFilter.this.mAms.sendMotionEventToListeningServices(
                      motionEvent2)) {
                return;
              }
              super.onMotionEvent(motionEvent, motionEvent2, i2);
            }
          });
    }
    int i2 = this.mEnabledFeatures;
    if ((i2 & 32) != 0 || (i2 & 1) != 0 || (i2 & 64) != 0) {
      MagnificationGestureHandler createMagnificationGestureHandler =
          createMagnificationGestureHandler(displayId, createDisplayContext);
      addFirstEventHandler(displayId, createMagnificationGestureHandler);
      this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
    }
    if ((this.mEnabledFeatures & 16) != 0) {
      MotionEventInjector motionEventInjector =
          new MotionEventInjector(this.mContext.getMainLooper(), this.mAms.getTraceManager());
      addFirstEventHandler(displayId, motionEventInjector);
      this.mMotionEventInjectors.put(displayId, motionEventInjector);
    }
    if ((this.mEnabledFeatures & Integer.MIN_VALUE) != 0) {
      SamsungTapDuration samsungTapDuration = new SamsungTapDuration(this.mContext, this.mUserId);
      this.mTapDuration = samsungTapDuration;
      addFirstEventHandler(displayId, samsungTapDuration);
    }
    if ((this.mEnabledFeatures & 16777216) != 0) {
      SamsungTouchBlocker samsungTouchBlocker =
          new SamsungTouchBlocker(this.mContext, this.mUserId);
      this.mTouchBlocker = samsungTouchBlocker;
      addFirstEventHandler(displayId, samsungTouchBlocker);
    }
    int i3 = this.mEnabledFeatures;
    if ((i3 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0
        && (i3 & IInstalld.FLAG_USE_QUOTA) == 0) {
      return;
    }
    AutoActionController autoActionController =
        new AutoActionController(this.mContext, this.mUserId);
    this.mAutoActionController = autoActionController;
    addFirstEventHandler(displayId, autoActionController);
  }

  public final void enableDisplayIndependentFeatures() {
    if ((this.mEnabledFeatures & 16) != 0) {
      this.mAms.setMotionEventInjectors(this.mMotionEventInjectors);
    }
    if ((this.mEnabledFeatures & 4) != 0) {
      KeyboardInterceptor keyboardInterceptor =
          new KeyboardInterceptor(
              this.mAms, (WindowManagerPolicy) LocalServices.getService(WindowManagerPolicy.class));
      this.mKeyboardInterceptor = keyboardInterceptor;
      addFirstEventHandler(0, keyboardInterceptor);
    }
    if ((this.mEnabledFeatures & 33554432) != 0) {
      SamsungStickyKeys samsungStickyKeys = new SamsungStickyKeys(this.mContext, this.mUserId);
      this.mStickyKeys = samsungStickyKeys;
      addFirstEventHandler(0, samsungStickyKeys);
    }
    if ((this.mEnabledFeatures & 67108864) != 0) {
      SamsungBounceKeys samsungBounceKeys = new SamsungBounceKeys(this.mContext, this.mUserId);
      this.mBounceKeys = samsungBounceKeys;
      addFirstEventHandler(0, samsungBounceKeys);
    }
    if ((this.mEnabledFeatures & 134217728) != 0) {
      SamsungSlowKeys samsungSlowKeys = new SamsungSlowKeys(this.mContext, this.mUserId);
      this.mSlowKeys = samsungSlowKeys;
      addFirstEventHandler(0, samsungSlowKeys);
    }
  }

  public final void addFirstEventHandler(
      int i, EventStreamTransformation eventStreamTransformation) {
    EventStreamTransformation eventStreamTransformation2 =
        (EventStreamTransformation) this.mEventHandler.get(i);
    if (eventStreamTransformation2 != null) {
      eventStreamTransformation.setNext(eventStreamTransformation2);
    } else {
      eventStreamTransformation.setNext(this);
    }
    this.mEventHandler.put(i, eventStreamTransformation);
  }

  public final void disableFeatures() {
    ArrayList validDisplayList = this.mAms.getValidDisplayList();
    for (int size = validDisplayList.size() - 1; size >= 0; size--) {
      disableFeaturesForDisplay(((Display) validDisplayList.get(size)).getDisplayId());
    }
    this.mAms.setMotionEventInjectors(null);
    disableDisplayIndependentFeatures();
    resetAllStreamState();
  }

  public final void disableFeaturesForDisplay(int i) {
    MotionEventInjector motionEventInjector =
        (MotionEventInjector) this.mMotionEventInjectors.get(i);
    if (motionEventInjector != null) {
      motionEventInjector.onDestroy();
      this.mMotionEventInjectors.remove(i);
    }
    TouchExplorer touchExplorer = (TouchExplorer) this.mTouchExplorer.get(i);
    if (touchExplorer != null) {
      touchExplorer.onDestroy();
      this.mTouchExplorer.remove(i);
    }
    MagnificationGestureHandler magnificationGestureHandler =
        (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(i);
    if (magnificationGestureHandler != null) {
      magnificationGestureHandler.onDestroy();
      this.mMagnificationGestureHandler.remove(i);
    }
    if (((EventStreamTransformation) this.mEventHandler.get(i)) != null) {
      this.mEventHandler.remove(i);
    }
  }

  public void enableFeaturesForDisplayIfInstalled(Display display) {
    if (this.mInstalled) {
      resetStreamStateForDisplay(display.getDisplayId());
      enableFeaturesForDisplay(display);
    }
  }

  public void disableFeaturesForDisplayIfInstalled(int i) {
    if (this.mInstalled) {
      disableFeaturesForDisplay(i);
      resetStreamStateForDisplay(i);
    }
  }

  public final void disableDisplayIndependentFeatures() {
    AutoclickController autoclickController = this.mAutoclickController;
    if (autoclickController != null) {
      autoclickController.onDestroy();
      this.mAutoclickController = null;
    }
    KeyboardInterceptor keyboardInterceptor = this.mKeyboardInterceptor;
    if (keyboardInterceptor != null) {
      keyboardInterceptor.onDestroy();
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
    SamsungStickyKeys samsungStickyKeys = this.mStickyKeys;
    if (samsungStickyKeys != null) {
      samsungStickyKeys.onDestroy();
      this.mStickyKeys = null;
    }
    SamsungBounceKeys samsungBounceKeys = this.mBounceKeys;
    if (samsungBounceKeys != null) {
      samsungBounceKeys.onDestroy();
      this.mBounceKeys = null;
    }
    SamsungSlowKeys samsungSlowKeys = this.mSlowKeys;
    if (samsungSlowKeys != null) {
      samsungSlowKeys.onDestroy();
      this.mSlowKeys = null;
    }
  }

  public final MagnificationGestureHandler createMagnificationGestureHandler(
      int i, Context context) {
    int i2 = this.mEnabledFeatures;
    boolean z = (i2 & 1) != 0;
    boolean z2 = (i2 & 64) != 0;
    if (this.mAms.getMagnificationMode(i) == 2) {
      return new WindowMagnificationGestureHandler(
          context.createWindowContext(2039, null),
          this.mAms.getWindowMagnificationMgr(),
          this.mAms.getTraceManager(),
          this.mAms.getMagnificationController(),
          z,
          z2,
          i);
    }
    return new FullScreenMagnificationGestureHandler(
        context.createWindowContext(2027, null),
        this.mAms.getMagnificationController().getFullScreenMagnificationController(),
        this.mAms.getTraceManager(),
        this.mAms.getMagnificationController(),
        z,
        z2,
        new WindowMagnificationPromptController(context, this.mUserId),
        i);
  }

  public void resetAllStreamState() {
    ArrayList validDisplayList = this.mAms.getValidDisplayList();
    for (int size = validDisplayList.size() - 1; size >= 0; size--) {
      resetStreamStateForDisplay(((Display) validDisplayList.get(size)).getDisplayId());
    }
    EventStreamState eventStreamState = this.mKeyboardStreamState;
    if (eventStreamState != null) {
      eventStreamState.reset();
    }
  }

  public void resetStreamStateForDisplay(int i) {
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

  public void refreshMagnificationMode(Display display) {
    int displayId = display.getDisplayId();
    MagnificationGestureHandler magnificationGestureHandler =
        (MagnificationGestureHandler) this.mMagnificationGestureHandler.get(displayId);
    if (magnificationGestureHandler == null
        || magnificationGestureHandler.getMode() == this.mAms.getMagnificationMode(displayId)) {
      return;
    }
    magnificationGestureHandler.onDestroy();
    MagnificationGestureHandler createMagnificationGestureHandler =
        createMagnificationGestureHandler(displayId, this.mContext.createDisplayContext(display));
    switchEventStreamTransformation(
        displayId, magnificationGestureHandler, createMagnificationGestureHandler);
    this.mMagnificationGestureHandler.put(displayId, createMagnificationGestureHandler);
  }

  public final void switchEventStreamTransformation(
      int i,
      EventStreamTransformation eventStreamTransformation,
      EventStreamTransformation eventStreamTransformation2) {
    EventStreamTransformation eventStreamTransformation3 =
        (EventStreamTransformation) this.mEventHandler.get(i);
    if (eventStreamTransformation3 == null) {
      return;
    }
    if (eventStreamTransformation3 == eventStreamTransformation) {
      eventStreamTransformation2.setNext(eventStreamTransformation.getNext());
      this.mEventHandler.put(i, eventStreamTransformation2);
      return;
    }
    while (eventStreamTransformation3 != null) {
      if (eventStreamTransformation3.getNext() == eventStreamTransformation) {
        eventStreamTransformation3.setNext(eventStreamTransformation2);
        eventStreamTransformation2.setNext(eventStreamTransformation.getNext());
        return;
      }
      eventStreamTransformation3 = eventStreamTransformation3.getNext();
    }
  }

  public abstract class EventStreamState {
    public int mSource = -1;

    public boolean shouldProcessKeyEvent(KeyEvent keyEvent) {
      return false;
    }

    public boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
      return false;
    }

    public boolean shouldProcessScroll() {
      return false;
    }

    public boolean updateInputSource(int i) {
      if (this.mSource == i) {
        return false;
      }
      reset();
      this.mSource = i;
      return true;
    }

    public boolean inputSourceValid() {
      return this.mSource >= 0;
    }

    public void reset() {
      this.mSource = -1;
    }
  }

  public class MouseEventStreamState extends EventStreamState {
    public boolean mMotionSequenceStarted;

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public final boolean shouldProcessScroll() {
      return true;
    }

    public MouseEventStreamState() {
      reset();
    }

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public final void reset() {
      super.reset();
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
  }

  public class TouchScreenEventStreamState extends EventStreamState {
    public boolean mHoverSequenceStarted;
    public boolean mTouchSequenceStarted;

    public TouchScreenEventStreamState() {
      reset();
    }

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public final void reset() {
      super.reset();
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

  public class GenericMotionEventStreamState extends EventStreamState {
    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public boolean shouldProcessScroll() {
      return true;
    }

    public GenericMotionEventStreamState() {}

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public boolean shouldProcessMotionEvent(MotionEvent motionEvent) {
      return AccessibilityInputFilter.this.anyServiceWantsGenericMotionEvent(motionEvent);
    }
  }

  public final boolean anyServiceWantsGenericMotionEvent(MotionEvent motionEvent) {
    if (!motionEvent.isFromSource(4098) || (this.mEnabledFeatures & 2) == 0) {
      return (this.mCombinedGenericMotionEventSources & (motionEvent.getSource() & (-256))) != 0;
    }
    return false;
  }

  public void setCombinedGenericMotionEventSources(int i) {
    this.mCombinedGenericMotionEventSources = i;
  }

  public class KeyboardEventStreamState extends EventStreamState {
    public SparseBooleanArray mEventSequenceStartedMap = new SparseBooleanArray();

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public boolean inputSourceValid() {
      return true;
    }

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public boolean updateInputSource(int i) {
      return false;
    }

    public KeyboardEventStreamState() {
      reset();
    }

    @Override // com.android.server.accessibility.AccessibilityInputFilter.EventStreamState
    public final void reset() {
      super.reset();
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
  }

  public void setGestureDetectionPassthroughRegion(int i, Region region) {
    if (region == null || !this.mTouchExplorer.contains(i)) {
      return;
    }
    ((TouchExplorer) this.mTouchExplorer.get(i)).setGestureDetectionPassthroughRegion(region);
  }

  public void setTouchExplorationPassthroughRegion(int i, Region region) {
    if (region == null || !this.mTouchExplorer.contains(i)) {
      return;
    }
    ((TouchExplorer) this.mTouchExplorer.get(i)).setTouchExplorationPassthroughRegion(region);
  }

  public void setServiceDetectsGesturesEnabled(int i, boolean z) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).setServiceDetectsGestures(z);
    }
    this.mServiceDetectsGestures.put(i, Boolean.valueOf(z));
  }

  public void resetServiceDetectsGestures() {
    this.mServiceDetectsGestures.clear();
  }

  public void requestTouchExploration(int i) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).requestTouchExploration();
    }
  }

  public void requestDragging(int i, int i2) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).requestDragging(i2);
    }
  }

  public void requestDelegating(int i) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).requestDelegating();
    }
  }

  public void onDoubleTap(int i) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).onDoubleTap();
    }
  }

  public void onDoubleTapAndHold(int i) {
    if (this.mTouchExplorer.contains(i)) {
      ((TouchExplorer) this.mTouchExplorer.get(i)).onDoubleTapAndHold();
    }
  }

  @Override // com.android.server.accessibility.EventStreamTransformation
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    MagnificationGestureHandler magnificationGestureHandler;
    TouchExplorer touchExplorer;
    if (this.mEventHandler == null) {
      return;
    }
    printWriter.append("A11yInputFilter Info : ");
    printWriter.println();
    ArrayList validDisplayList = this.mAms.getValidDisplayList();
    for (int i = 0; i < validDisplayList.size(); i++) {
      int displayId = ((Display) validDisplayList.get(i)).getDisplayId();
      EventStreamTransformation eventStreamTransformation =
          (EventStreamTransformation) this.mEventHandler.get(displayId);
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
            stringJoiner.add(AutoclickController.LOG_TAG);
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
      printWriter.append(
          (CharSequence)
              ("mEnabledFeatures : "
                  + String.format("0x%08X", Integer.valueOf(this.mEnabledFeatures))));
      if (this.mTouchExplorer.size() > 0
          && (touchExplorer = (TouchExplorer) this.mTouchExplorer.valueAt(0)) != null) {
        touchExplorer.dump(fileDescriptor, printWriter, strArr);
      }
      if (this.mMagnificationGestureHandler.size() > 0
          && (magnificationGestureHandler =
                  (MagnificationGestureHandler) this.mMagnificationGestureHandler.valueAt(0))
              != null) {
        magnificationGestureHandler.dump(fileDescriptor, printWriter, strArr);
      }
      AutoclickController autoclickController = this.mAutoclickController;
      if (autoclickController != null) {
        autoclickController.dump(fileDescriptor, printWriter, strArr);
      }
      AutoActionController autoActionController = this.mAutoActionController;
      if (autoActionController != null) {
        autoActionController.dump(fileDescriptor, printWriter, strArr);
      }
      KeyboardInterceptor keyboardInterceptor = this.mKeyboardInterceptor;
      if (keyboardInterceptor != null) {
        keyboardInterceptor.dump(fileDescriptor, printWriter, strArr);
      }
      SamsungTapDuration samsungTapDuration = this.mTapDuration;
      if (samsungTapDuration != null) {
        samsungTapDuration.dump(fileDescriptor, printWriter, strArr);
      }
      SamsungTouchBlocker samsungTouchBlocker = this.mTouchBlocker;
      if (samsungTouchBlocker != null) {
        samsungTouchBlocker.dump(fileDescriptor, printWriter, strArr);
      }
      SamsungStickyKeys samsungStickyKeys = this.mStickyKeys;
      if (samsungStickyKeys != null) {
        samsungStickyKeys.dump(fileDescriptor, printWriter, strArr);
      }
      SamsungBounceKeys samsungBounceKeys = this.mBounceKeys;
      if (samsungBounceKeys != null) {
        samsungBounceKeys.dump(fileDescriptor, printWriter, strArr);
      }
      SamsungSlowKeys samsungSlowKeys = this.mSlowKeys;
      if (samsungSlowKeys != null) {
        samsungSlowKeys.dump(fileDescriptor, printWriter, strArr);
      }
    } catch (Exception unused) {
    }
  }
}
