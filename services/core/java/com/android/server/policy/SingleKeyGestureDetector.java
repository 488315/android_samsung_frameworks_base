package com.android.server.policy;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.rune.InputRune;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Set;

public final class SingleKeyGestureDetector {
    public static final Set KEYCODE_KEY_DISPATCHING_ALLOWLIST;
    public static final long MULTI_PRESS_TIMEOUT;
    public static long sDefaultLongPressTimeout;
    public static long sDefaultMultiPressTimeout;
    public static long sDefaultVeryLongPressTimeout;
    public SingleKeyRule mActiveRule;
    public final SparseArray mCustomRules;
    public int mDownKeyCode;
    public final HandleLongPressInfo mHandleLongPressInfo;
    public boolean mHandledByLongPress;
    public final KeyHandler mHandler;
    public int mKeyPressCounter;
    public volatile long mKeyReleaseTime;
    public long mLastDownTime;
    public long mLastUpTime;
    public long mTriggeredMultiPressTime;
    public static final boolean DEBUG = PhoneWindowManager.DEBUG_INPUT;
    public static KeyCustomizationManager mKeyCustomizationPolicy = null;
    public boolean mBeganFromNonInteractive = false;
    public boolean mBeganFromDefaultDisplayOn = false;

    public final class HandleLongPressInfo {
        public int mKeyCode = 0;
        public final ArrayList mTypeList = new ArrayList(2);

        public final void reset() {
            Log.i("SingleKeyGesture", "HandleLongPressInfo reset Callers=" + Debug.getCallers(10));
            this.mKeyCode = 0;
            this.mTypeList.clear();
        }

        public final String toString() {
            return "KeyCode="
                    + KeyEvent.keyCodeToString(this.mKeyCode)
                    + ", size="
                    + this.mTypeList.size();
        }
    }

    public final class KeyHandler extends Handler {
        public KeyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            MessageObject messageObject = (MessageObject) message.obj;
            SingleKeyRule singleKeyRule = messageObject.activeRule;
            KeyEvent keyEvent = messageObject.event;
            int i = messageObject.policyFlags;
            int i2 = messageObject.longPressType;
            if (singleKeyRule == null) {
                Log.wtf("SingleKeyGesture", "No active rule.");
                return;
            }
            int i3 = messageObject.keyCode;
            int i4 = messageObject.pressCount;
            int i5 = messageObject.displayId;
            int i6 = message.what;
            SingleKeyGestureDetector singleKeyGestureDetector = SingleKeyGestureDetector.this;
            if (i6 == 0) {
                if (SingleKeyGestureDetector.DEBUG) {
                    Log.i(
                            "SingleKeyGesture",
                            "Detect long press "
                                    + KeyEvent.keyCodeToString(i3)
                                    + " type="
                                    + i2
                                    + " info : "
                                    + singleKeyGestureDetector.mHandleLongPressInfo);
                }
                HandleLongPressInfo handleLongPressInfo =
                        singleKeyGestureDetector.mHandleLongPressInfo;
                int i7 = handleLongPressInfo.mKeyCode;
                HandleLongPressInfo handleLongPressInfo2 =
                        singleKeyGestureDetector.mHandleLongPressInfo;
                if (i3 == i7 && handleLongPressInfo.mTypeList.size() == 1) {
                    Log.i("SingleKeyGesture", "The long press already have been consumed");
                    handleLongPressInfo2.reset();
                    return;
                }
                if ((handleLongPressInfo2.mKeyCode != 0
                                || handleLongPressInfo2.mTypeList.size() != 0)
                        && (i3 != handleLongPressInfo2.mKeyCode
                                || handleLongPressInfo2.mTypeList.size() >= 2)) {
                    handleLongPressInfo2.reset();
                }
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i3, i2, "addInfo, keyCode=", " type=", "SingleKeyGesture");
                handleLongPressInfo2.mKeyCode = i3;
                handleLongPressInfo2.mTypeList.add(Integer.valueOf(i2));
                singleKeyRule.onLongPress(singleKeyGestureDetector.mLastDownTime, keyEvent, i);
                return;
            }
            if (i6 == 1) {
                if (SingleKeyGestureDetector.DEBUG) {
                    Log.i(
                            "SingleKeyGesture",
                            "Detect very long press " + KeyEvent.keyCodeToString(i3));
                }
                long j = singleKeyGestureDetector.mLastDownTime;
                singleKeyRule.onVeryLongPress();
                return;
            }
            if (i6 != 2) {
                if (i6 != 3) {
                    return;
                }
                if (SingleKeyGestureDetector.DEBUG) {
                    Log.i(
                            "SingleKeyGesture",
                            "Detect key up " + KeyEvent.keyCodeToString(i3) + " on display " + i5);
                }
                singleKeyRule.onKeyUp(i4, i5, singleKeyGestureDetector.mLastDownTime);
                return;
            }
            if (SingleKeyGestureDetector.DEBUG) {
                StringBuilder sb = new StringBuilder("Detect press ");
                sb.append(KeyEvent.keyCodeToString(i3));
                sb.append(" on display ");
                sb.append(i5);
                sb.append(", count ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(sb, i4, "SingleKeyGesture");
            }
            if (i4 != 1) {
                singleKeyRule.onMultiPress(
                        singleKeyGestureDetector.mLastDownTime, i4, i5, keyEvent);
            } else if (singleKeyGestureDetector.mTriggeredMultiPressTime > keyEvent.getDownTime()) {
                Log.d(
                        "SingleKeyGesture",
                        "SinglePress downTime is older than multiPress triggered time.");
            } else {
                singleKeyRule.onPress(singleKeyGestureDetector.mLastDownTime, keyEvent, i5);
            }
        }
    }

    public final class MessageObject extends Record {
        public final SingleKeyRule activeRule;
        public final int displayId;
        public final KeyEvent event;
        public final int keyCode;
        public final int longPressType;
        public final int policyFlags;
        public final int pressCount;

        public MessageObject(
                SingleKeyRule singleKeyRule,
                int i,
                int i2,
                int i3,
                KeyEvent keyEvent,
                int i4,
                int i5) {
            this.activeRule = singleKeyRule;
            this.keyCode = i;
            this.pressCount = i2;
            this.displayId = i3;
            this.event = keyEvent;
            this.policyFlags = i4;
            this.longPressType = i5;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE, MessageObject.class, Object.class),
                                    MessageObject.class,
                                    "activeRule;keyCode;pressCount;displayId;event;policyFlags;longPressType",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->event:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->policyFlags:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->longPressType:I")
                            .dynamicInvoker()
                            .invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(Integer.TYPE, MessageObject.class),
                                    MessageObject.class,
                                    "activeRule;keyCode;pressCount;displayId;event;policyFlags;longPressType",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->event:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->policyFlags:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->longPressType:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(String.class, MessageObject.class),
                                    MessageObject.class,
                                    "activeRule;keyCode;pressCount;displayId;event;policyFlags;longPressType",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->activeRule:Lcom/android/server/policy/SingleKeyGestureDetector$SingleKeyRule;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->keyCode:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->pressCount:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->displayId:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->event:Landroid/view/KeyEvent;",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->policyFlags:I",
                                    "FIELD:Lcom/android/server/policy/SingleKeyGestureDetector$MessageObject;->longPressType:I")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }
    }

    public abstract class SingleKeyRule {
        public final int mKeyCode;
        public boolean mIsKeyLongPressed = false;
        public long extensionMultiPressTimeout = 0;

        public SingleKeyRule(int i) {
            this.mKeyCode = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SingleKeyRule)
                    && this.mKeyCode == ((SingleKeyRule) obj).mKeyCode;
        }

        public long getLongPressTimeoutMs() {
            return SingleKeyGestureDetector.sDefaultLongPressTimeout;
        }

        public int getMaxMultiPressCount() {
            KeyCustomizationManager keyCustomizationManager =
                    SingleKeyGestureDetector.mKeyCustomizationPolicy;
            int i = this.mKeyCode;
            if (keyCustomizationManager.hasLastInfo(64, i)) {
                return 5;
            }
            if (SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(32, i)) {
                return 4;
            }
            if (SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(16, i)) {
                return 3;
            }
            return SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(8, i) ? 2 : 1;
        }

        public final int hashCode() {
            return this.mKeyCode;
        }

        public void onKeyDown(KeyEvent keyEvent) {
            this.mIsKeyLongPressed = false;
            sendBroadcastIfNeeded(keyEvent);
        }

        public void onKeyUp(int i, int i2, long j) {}

        public void onKeyUp(KeyEvent keyEvent) {
            sendBroadcastIfNeeded(keyEvent);
        }

        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            SingleKeyGestureDetector.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
        }

        public void onMultiPress(long j, int i, int i2, KeyEvent keyEvent) {
            SingleKeyGestureDetector.mKeyCustomizationPolicy.launchMultiPressAction(keyEvent, i);
        }

        public abstract void onPress(long j, KeyEvent keyEvent, int i);

        public void onVeryLongPress() {}

        public final void sendBroadcastIfNeeded(KeyEvent keyEvent) {
            Set set = SingleKeyGestureDetector.KEYCODE_KEY_DISPATCHING_ALLOWLIST;
            int i = this.mKeyCode;
            if ((!((ArraySet) set).contains(Integer.valueOf(i))
                            || (keyEvent.getFlags() & 268435456) == 0)
                    && SingleKeyGestureDetector.mKeyCustomizationPolicy.getLastAction(3, i) == 2) {
                SingleKeyGestureDetector.mKeyCustomizationPolicy.launchPressSendBroadcast(
                        keyEvent, i, this.mIsKeyLongPressed);
            }
        }

        public abstract boolean supportLongPress();

        public boolean supportVeryLongPress() {
            return false;
        }

        public final String toString() {
            return "KeyCode="
                    + KeyEvent.keyCodeToString(this.mKeyCode)
                    + ", LongPress="
                    + supportLongPress()
                    + ", VeryLongPress="
                    + supportVeryLongPress()
                    + ", MaxMultiPressCount="
                    + getMaxMultiPressCount();
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        KEYCODE_KEY_DISPATCHING_ALLOWLIST = arraySet;
        arraySet.add(4);
        arraySet.add(24);
        arraySet.add(25);
        arraySet.add(79);
        MULTI_PRESS_TIMEOUT = 170L;
    }

    public SingleKeyGestureDetector(Looper looper) {
        new ArrayList();
        this.mCustomRules = new SparseArray();
        this.mActiveRule = null;
        this.mDownKeyCode = 0;
        this.mHandledByLongPress = false;
        this.mLastDownTime = 0L;
        this.mLastUpTime = 0L;
        this.mHandleLongPressInfo = new HandleLongPressInfo();
        this.mKeyReleaseTime = 0L;
        this.mHandler = new KeyHandler(looper);
    }

    public final void addRule(SingleKeyRule singleKeyRule) {
        SparseArray sparseArray = this.mCustomRules;
        int i = singleKeyRule.mKeyCode;
        if (!sparseArray.contains(i)) {
            this.mCustomRules.put(i, singleKeyRule);
            return;
        }
        throw new IllegalArgumentException("Rule : " + singleKeyRule + " already exists.");
    }

    public final boolean hasRule(int i) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mCustomRules.get(i);
        return singleKeyRule != null && i == singleKeyRule.mKeyCode;
    }

    public final void reset() {
        if (this.mActiveRule != null) {
            if (this.mDownKeyCode != 0) {
                this.mHandler.removeMessages(0);
                this.mHandler.removeMessages(1);
            }
            if (this.mKeyPressCounter > 0) {
                this.mHandler.removeMessages(2);
                this.mKeyPressCounter = 0;
            }
            this.mActiveRule = null;
            this.mLastUpTime = 0L;
            this.mTriggeredMultiPressTime = 0L;
            if (InputRune.PWM_SKIP_TOO_FAST_DOUBLE_PRESS) {
                this.mKeyReleaseTime = 0L;
            }
        }
        this.mHandledByLongPress = false;
        this.mDownKeyCode = 0;
    }
}
