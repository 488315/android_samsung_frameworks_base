package com.android.server.policy;

import android.R;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes3.dex */
public final class SingleKeyGestureDetector {
    public static final boolean DEBUG = PhoneWindowManager.DEBUG_INPUT;
    public static final Set KEYCODE_KEY_DISPATCHING_ALLOWLIST;
    public static final long MULTI_PRESS_TIMEOUT;
    public static KeyCustomizationManager mKeyCustomizationPolicy;
    public static long sDefaultLongPressTimeout;
    public static long sDefaultMultiPressTimeout;
    public static long sDefaultVeryLongPressTimeout;
    public int mKeyPressCounter;
    public long mTriggeredMultiPressTime;
    public boolean mBeganFromNonInteractive = false;
    public final SparseArray mRules = new SparseArray();
    public SingleKeyRule mActiveRule = null;
    public int mDownKeyCode = 0;
    public boolean mHandledByLongPress = false;
    public long mLastDownTime = 0;
    public long mLastUpTime = 0;
    public final HandleLongPressInfo mHandleLongPressInfo = new HandleLongPressInfo();
    public volatile long mKeyReleaseTime = 0;
    public final Handler mHandler = new KeyHandler();

    static {
        ArraySet arraySet = new ArraySet();
        KEYCODE_KEY_DISPATCHING_ALLOWLIST = arraySet;
        arraySet.add(4);
        arraySet.add(24);
        arraySet.add(25);
        arraySet.add(79);
        MULTI_PRESS_TIMEOUT = 170L;
    }

    public abstract class SingleKeyRule {
        public final int mKeyCode;
        public boolean mIsKeyLongPressed = false;
        public long extensionLongPressTimeout = 0;
        public long extensionMultiPressTimeout = 0;

        public abstract void onPress(long j, KeyEvent keyEvent);

        public void onVeryLongPress(long j) {
        }

        public boolean supportVeryLongPress() {
            return false;
        }

        public SingleKeyRule(int i) {
            this.mKeyCode = i;
        }

        public final boolean shouldInterceptKey(int i) {
            return i == this.mKeyCode;
        }

        public boolean supportLongPress() {
            int i = this.mKeyCode;
            return i == 3 || i == 4 || i == 24 || i == 25 || i == 79 || i == 187 || i == 1015 || i == 1079;
        }

        public int getMaxMultiPressCount() {
            if (SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(64, this.mKeyCode)) {
                return 5;
            }
            if (SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(32, this.mKeyCode)) {
                return 4;
            }
            if (SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(16, this.mKeyCode)) {
                return 3;
            }
            return SingleKeyGestureDetector.mKeyCustomizationPolicy.hasLastInfo(8, this.mKeyCode) ? 2 : 1;
        }

        public void onMultiPress(long j, int i, KeyEvent keyEvent) {
            SingleKeyGestureDetector.mKeyCustomizationPolicy.launchMultiPressAction(keyEvent, i);
        }

        public long getLongPressTimeoutMs() {
            long j = this.extensionLongPressTimeout;
            return j != 0 ? j : SingleKeyGestureDetector.sDefaultLongPressTimeout;
        }

        public void onLongPress(long j, KeyEvent keyEvent, int i) {
            this.mIsKeyLongPressed = true;
            SingleKeyGestureDetector.mKeyCustomizationPolicy.launchLongPressAction(keyEvent);
        }

        public long getVeryLongPressTimeoutMs() {
            return SingleKeyGestureDetector.sDefaultVeryLongPressTimeout;
        }

        public void onKeyDown(KeyEvent keyEvent) {
            this.mIsKeyLongPressed = false;
            sendBroadcastIfNeeded(keyEvent);
        }

        public void onKeyUp(KeyEvent keyEvent) {
            sendBroadcastIfNeeded(keyEvent);
        }

        public final void sendBroadcastIfNeeded(KeyEvent keyEvent) {
            if ((!SingleKeyGestureDetector.KEYCODE_KEY_DISPATCHING_ALLOWLIST.contains(Integer.valueOf(this.mKeyCode)) || (keyEvent.getFlags() & 268435456) == 0) && SingleKeyGestureDetector.mKeyCustomizationPolicy.getLastAction(3, this.mKeyCode) == 2) {
                SingleKeyGestureDetector.mKeyCustomizationPolicy.launchPressSendBroadcast(keyEvent, this.mKeyCode, this.mIsKeyLongPressed);
            }
        }

        public void setLongPressTimeoutMs(long j) {
            if (j == getLongPressTimeoutMs()) {
                return;
            }
            this.extensionLongPressTimeout = j;
        }

        public boolean hasExtensionLongPressTimeout() {
            long j = this.extensionLongPressTimeout;
            return j != 0 && SingleKeyGestureDetector.sDefaultLongPressTimeout < j;
        }

        public long getMultiPressTimeoutMs() {
            long j = this.extensionMultiPressTimeout;
            return j != 0 ? j : SingleKeyGestureDetector.sDefaultMultiPressTimeout;
        }

        public void setMultiPressTimeoutMs(long j) {
            if (j == getMultiPressTimeoutMs()) {
                return;
            }
            if (j == SingleKeyGestureDetector.sDefaultMultiPressTimeout) {
                j = 0;
            }
            this.extensionMultiPressTimeout = j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("KeyCode=");
            sb.append(KeyEvent.keyCodeToString(this.mKeyCode));
            sb.append(", LongPress=");
            sb.append(supportLongPress());
            if (supportVeryLongPress()) {
                sb.append(", VeryLongPress=true");
            }
            sb.append(", MaxMultiPressCount=");
            sb.append(getMaxMultiPressCount());
            if (this.extensionLongPressTimeout != 0) {
                sb.append(", ExtLongPressTimeout=");
                sb.append(this.extensionLongPressTimeout);
            }
            if (this.extensionMultiPressTimeout != 0) {
                sb.append(", ExtMultiPressTimeout=");
                sb.append(this.extensionMultiPressTimeout);
            }
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SingleKeyRule) && this.mKeyCode == ((SingleKeyRule) obj).mKeyCode;
        }

        public int hashCode() {
            return this.mKeyCode;
        }
    }

    public static SingleKeyGestureDetector get(Context context, KeyCustomizationManager keyCustomizationManager) {
        SingleKeyGestureDetector singleKeyGestureDetector = new SingleKeyGestureDetector();
        sDefaultLongPressTimeout = context.getResources().getInteger(R.integer.config_letterboxDefaultPositionForVerticalReachability);
        sDefaultVeryLongPressTimeout = context.getResources().getInteger(R.integer.config_volte_replacement_rat);
        mKeyCustomizationPolicy = keyCustomizationManager;
        sDefaultMultiPressTimeout = MULTI_PRESS_TIMEOUT;
        return singleKeyGestureDetector;
    }

    public void addRule(SingleKeyRule singleKeyRule) {
        if (this.mRules.contains(singleKeyRule.mKeyCode)) {
            throw new IllegalArgumentException("Rule : " + singleKeyRule + " already exists.");
        }
        this.mRules.put(singleKeyRule.mKeyCode, singleKeyRule);
    }

    public void removeRule(int i) {
        this.mRules.remove(i);
    }

    public void interceptKey(KeyEvent keyEvent, boolean z, int i) {
        if (keyEvent.getAction() == 0) {
            int i2 = this.mDownKeyCode;
            if (i2 == 0 || i2 != keyEvent.getKeyCode()) {
                this.mBeganFromNonInteractive = !z;
            }
            interceptKeyDown(keyEvent, i);
            return;
        }
        interceptKeyUp(keyEvent, i);
    }

    public final void interceptKeyDown(KeyEvent keyEvent, int i) {
        SingleKeyRule singleKeyRule;
        int keyCode = keyEvent.getKeyCode();
        Log.d("SingleKeyGesture", "interceptKeyDown, info : " + this.mHandleLongPressInfo);
        if (keyEvent.getRepeatCount() == 0) {
            this.mHandleLongPressInfo.initIfNeeded(keyCode);
        }
        int i2 = this.mDownKeyCode;
        if (i2 == keyCode) {
            if (this.mActiveRule == null || (keyEvent.getFlags() & 128) == 0 || !this.mActiveRule.supportLongPress() || this.mHandledByLongPress || this.mActiveRule.hasExtensionLongPressTimeout()) {
                return;
            }
            Log.i("SingleKeyGesture", "Long press key " + KeyEvent.keyCodeToString(keyCode));
            this.mHandledByLongPress = true;
            this.mHandler.removeMessages(0);
            this.mHandler.removeMessages(1);
            Message obtainMessage = this.mHandler.obtainMessage(0, keyCode, 0, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i, 1));
            obtainMessage.setAsynchronous(true);
            this.mHandler.sendMessage(obtainMessage);
            return;
        }
        if (i2 != 0 || ((singleKeyRule = this.mActiveRule) != null && !singleKeyRule.shouldInterceptKey(keyCode))) {
            if (DEBUG) {
                Log.i("SingleKeyGesture", "Press another key " + KeyEvent.keyCodeToString(keyCode));
            }
            reset();
        }
        this.mDownKeyCode = keyCode;
        if (this.mActiveRule == null) {
            SingleKeyRule singleKeyRule2 = (SingleKeyRule) this.mRules.get(keyCode);
            if (singleKeyRule2 != null && singleKeyRule2.shouldInterceptKey(keyCode)) {
                if (DEBUG) {
                    Log.i("SingleKeyGesture", "Intercept key by rule " + singleKeyRule2);
                }
                this.mActiveRule = singleKeyRule2;
            }
            this.mLastDownTime = 0L;
        }
        SingleKeyRule singleKeyRule3 = this.mActiveRule;
        if (singleKeyRule3 == null) {
            return;
        }
        singleKeyRule3.onKeyDown(keyEvent);
        long downTime = keyEvent.getDownTime() - this.mLastUpTime;
        this.mLastDownTime = keyEvent.getDownTime();
        if (downTime >= this.mActiveRule.getMultiPressTimeoutMs()) {
            this.mKeyPressCounter = 1;
        } else {
            this.mKeyPressCounter++;
        }
        this.mLastUpTime = 0L;
        int i3 = this.mKeyPressCounter;
        if (i3 == 1) {
            this.mTriggeredMultiPressTime = 0L;
        }
        if (i3 == 1) {
            if (this.mActiveRule.supportLongPress()) {
                Log.d("SingleKeyGesture", "interceptKeyDown send long press msg");
                Message obtainMessage2 = this.mHandler.obtainMessage(0, keyCode, 0, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i, 2));
                obtainMessage2.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage2, this.mActiveRule.getLongPressTimeoutMs());
            }
            if (this.mActiveRule.supportVeryLongPress()) {
                Message obtainMessage3 = this.mHandler.obtainMessage(1, keyCode, 0, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i));
                obtainMessage3.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage3, this.mActiveRule.getVeryLongPressTimeoutMs());
                return;
            }
            return;
        }
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        if (CoreRune.FW_SKIP_TOO_FAST_DOUBLE_PRESS) {
            boolean z = (16777216 & i) != 0;
            long eventTime = keyEvent.getEventTime();
            if (!z && this.mKeyReleaseTime != 0 && eventTime < this.mKeyReleaseTime + 50) {
                this.mKeyPressCounter--;
                Log.d("SingleKeyGesture", "interceptKeyDown keyCode=" + keyCode + ", mKeyReleaseTime(" + this.mKeyReleaseTime + ") -  eventTime(" + eventTime + ")=" + (eventTime - this.mKeyReleaseTime));
            }
        }
        if (this.mKeyPressCounter > 1) {
            this.mTriggeredMultiPressTime = keyEvent.getDownTime();
            Log.i("SingleKeyGesture", "Trigger multi press, mTriggeredMultiPressTime=" + this.mTriggeredMultiPressTime);
        }
        if (this.mActiveRule.getMaxMultiPressCount() <= 1 || this.mKeyPressCounter != this.mActiveRule.getMaxMultiPressCount()) {
            return;
        }
        if (DEBUG) {
            Log.i("SingleKeyGesture", "Trigger multi press " + this.mActiveRule.toString() + " for it reached the max count " + this.mKeyPressCounter);
        }
        Message obtainMessage4 = this.mHandler.obtainMessage(2, keyCode, this.mKeyPressCounter, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i));
        obtainMessage4.setAsynchronous(true);
        this.mHandler.sendMessage(obtainMessage4);
    }

    public final boolean interceptKeyUp(KeyEvent keyEvent, int i) {
        Log.d("SingleKeyGesture", "interceptKeyUp, longPress=" + this.mHandledByLongPress + " info : " + this.mHandleLongPressInfo);
        this.mDownKeyCode = 0;
        if (this.mActiveRule == null) {
            Log.d("SingleKeyGesture", "interceptKeyUp, mActiveRule is null");
            return false;
        }
        if (!this.mHandledByLongPress) {
            long eventTime = keyEvent.getEventTime();
            if (eventTime < this.mLastDownTime + this.mActiveRule.getLongPressTimeoutMs()) {
                Log.d("SingleKeyGesture", "interceptKeyUp remove long press msg");
                this.mHandler.removeMessages(0);
            } else {
                this.mHandledByLongPress = this.mActiveRule.supportLongPress();
            }
            if (eventTime < this.mLastDownTime + this.mActiveRule.getVeryLongPressTimeoutMs()) {
                this.mHandler.removeMessages(1);
            } else {
                this.mHandledByLongPress = this.mActiveRule.supportVeryLongPress() | this.mHandledByLongPress;
            }
        }
        int keyCode = keyEvent.getKeyCode();
        SingleKeyRule singleKeyRule = this.mActiveRule;
        if (keyCode == singleKeyRule.mKeyCode) {
            singleKeyRule.onKeyUp(keyEvent);
        }
        if (this.mHandledByLongPress || keyEvent.isCanceled()) {
            this.mHandledByLongPress = false;
            this.mKeyPressCounter = 0;
            this.mActiveRule = null;
            return true;
        }
        int keyCode2 = keyEvent.getKeyCode();
        SingleKeyRule singleKeyRule2 = this.mActiveRule;
        if (keyCode2 == singleKeyRule2.mKeyCode) {
            if (singleKeyRule2.getMaxMultiPressCount() == 1) {
                if (DEBUG) {
                    Log.i("SingleKeyGesture", "press key " + KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                }
                Message obtainMessage = this.mHandler.obtainMessage(2, this.mActiveRule.mKeyCode, 1, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i));
                obtainMessage.setAsynchronous(true);
                this.mHandler.sendMessage(obtainMessage);
                this.mActiveRule = null;
                return true;
            }
            boolean z = (16777216 & i) != 0;
            long eventTime2 = keyEvent.getEventTime() - this.mLastDownTime;
            if (!z && eventTime2 > 300) {
                Log.i("SingleKeyGesture", "multi-press key " + keyEvent.getKeyCode() + " count=" + this.mKeyPressCounter + " longInterval=" + eventTime2);
                Message obtainMessage2 = this.mHandler.obtainMessage(2, this.mActiveRule.mKeyCode, this.mKeyPressCounter, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i));
                obtainMessage2.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage2, MULTI_PRESS_TIMEOUT);
                this.mActiveRule = null;
                return true;
            }
            if (this.mKeyPressCounter < this.mActiveRule.getMaxMultiPressCount()) {
                Message obtainMessage3 = this.mHandler.obtainMessage(2, this.mActiveRule.mKeyCode, this.mKeyPressCounter, getKeyHandlerObj(new KeyEvent(keyEvent), this.mActiveRule, i));
                obtainMessage3.setAsynchronous(true);
                this.mHandler.sendMessageDelayed(obtainMessage3, this.mActiveRule.getMultiPressTimeoutMs());
            }
            this.mLastUpTime = keyEvent.getEventTime();
            if (CoreRune.FW_SKIP_TOO_FAST_DOUBLE_PRESS && !z) {
                this.mKeyReleaseTime = keyEvent.getEventTime();
            }
            return true;
        }
        reset();
        return false;
    }

    public int getKeyPressCounter(int i) {
        SingleKeyRule singleKeyRule = this.mActiveRule;
        if (singleKeyRule == null || singleKeyRule.mKeyCode != i) {
            return 0;
        }
        return this.mKeyPressCounter;
    }

    public void reset() {
        Log.d("SingleKeyGesture", "reset counter=" + this.mKeyPressCounter + " mActiveRule=" + this.mActiveRule + " Callers(5)=" + Debug.getCallers(5));
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
            if (CoreRune.FW_SKIP_TOO_FAST_DOUBLE_PRESS) {
                this.mKeyReleaseTime = 0L;
            }
        }
        this.mHandledByLongPress = false;
        this.mDownKeyCode = 0;
    }

    public boolean isKeyIntercepted(int i) {
        SingleKeyRule singleKeyRule = this.mActiveRule;
        return singleKeyRule != null && singleKeyRule.shouldInterceptKey(i);
    }

    public boolean beganFromNonInteractive() {
        return this.mBeganFromNonInteractive;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + "SingleKey rules:");
        for (int i = 0; i < this.mRules.size(); i++) {
            printWriter.println(str + "  " + ((SingleKeyRule) this.mRules.valueAt(i)));
        }
    }

    public class KeyHandler extends Handler {
        public KeyHandler() {
            super(Looper.myLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            KeyHandlerObj keyHandlerObj = (KeyHandlerObj) message.obj;
            if (keyHandlerObj == null) {
                Log.e("SingleKeyGesture", "singleKeyHandlerObj is null");
                return;
            }
            SingleKeyRule singleKeyRule = keyHandlerObj.getSingleKeyRule();
            KeyEvent keyEvent = keyHandlerObj.getKeyEvent();
            int policyFlags = keyHandlerObj.getPolicyFlags();
            int longPressType = keyHandlerObj.getLongPressType();
            if (singleKeyRule == null) {
                Log.wtf("SingleKeyGesture", "No active rule.");
                return;
            }
            int i = message.arg1;
            int i2 = message.arg2;
            int i3 = message.what;
            if (i3 == 0) {
                boolean unused = SingleKeyGestureDetector.DEBUG;
                Log.i("SingleKeyGesture", "Detect long press " + KeyEvent.keyCodeToString(i) + " type=" + longPressType + " info : " + SingleKeyGestureDetector.this.mHandleLongPressInfo);
                if (SingleKeyGestureDetector.this.mHandleLongPressInfo.hasInfo(i)) {
                    Log.i("SingleKeyGesture", "The long press already have been consumed");
                    SingleKeyGestureDetector.this.mHandleLongPressInfo.reset();
                    return;
                } else {
                    SingleKeyGestureDetector.this.mHandleLongPressInfo.addInfo(i, longPressType);
                    singleKeyRule.onLongPress(SingleKeyGestureDetector.this.mLastDownTime, keyEvent, policyFlags);
                    return;
                }
            }
            if (i3 == 1) {
                if (SingleKeyGestureDetector.DEBUG) {
                    Log.i("SingleKeyGesture", "Detect very long press " + KeyEvent.keyCodeToString(i));
                }
                singleKeyRule.onVeryLongPress(SingleKeyGestureDetector.this.mLastDownTime);
                return;
            }
            if (i3 != 2) {
                return;
            }
            boolean unused2 = SingleKeyGestureDetector.DEBUG;
            Log.i("SingleKeyGesture", "Detect press " + KeyEvent.keyCodeToString(i) + ", count " + i2);
            if (i2 != 1) {
                singleKeyRule.onMultiPress(SingleKeyGestureDetector.this.mLastDownTime, i2, keyEvent);
            } else if (SingleKeyGestureDetector.this.mTriggeredMultiPressTime > keyEvent.getDownTime()) {
                Log.d("SingleKeyGesture", "SinglePress downTime is older than multiPress triggered time.");
            } else {
                singleKeyRule.onPress(SingleKeyGestureDetector.this.mLastDownTime, keyEvent);
            }
        }
    }

    public class KeyHandlerObj {
        public final KeyEvent event;
        public final int longPressType;
        public final int policyFlags;
        public final SingleKeyRule rule;

        public KeyHandlerObj(KeyEvent keyEvent, SingleKeyRule singleKeyRule, int i, int i2) {
            this.event = keyEvent;
            this.rule = singleKeyRule;
            this.policyFlags = i;
            this.longPressType = i2;
        }

        public KeyEvent getKeyEvent() {
            return this.event;
        }

        public SingleKeyRule getSingleKeyRule() {
            return this.rule;
        }

        public int getPolicyFlags() {
            return this.policyFlags;
        }

        public int getLongPressType() {
            return this.longPressType;
        }
    }

    public final KeyHandlerObj getKeyHandlerObj(KeyEvent keyEvent, SingleKeyRule singleKeyRule, int i) {
        return new KeyHandlerObj(keyEvent, singleKeyRule, i, 0);
    }

    public final KeyHandlerObj getKeyHandlerObj(KeyEvent keyEvent, SingleKeyRule singleKeyRule, int i, int i2) {
        return new KeyHandlerObj(keyEvent, singleKeyRule, i, i2);
    }

    public class HandleLongPressInfo {
        public int mKeyCode = 0;
        public final ArrayList mTypeList = new ArrayList(2);

        public void addInfo(int i, int i2) {
            if (!isEmpty() && (!equals(i) || getTypeSize() >= 2)) {
                reset();
            }
            Log.d("SingleKeyGesture", "addInfo, keyCode=" + i + " type=" + i2);
            this.mKeyCode = i;
            this.mTypeList.add(Integer.valueOf(i2));
        }

        public boolean hasInfo(int i) {
            return equals(i) && getTypeSize() == 1;
        }

        public boolean equals(int i) {
            return i == this.mKeyCode;
        }

        public boolean isEmpty() {
            return this.mKeyCode == 0 && this.mTypeList.size() == 0;
        }

        public final int getTypeSize() {
            return this.mTypeList.size();
        }

        public void reset() {
            Log.i("SingleKeyGesture", "HandleLongPressInfo reset Callers=" + Debug.getCallers(10));
            this.mKeyCode = 0;
            this.mTypeList.clear();
        }

        public void initIfNeeded(int i) {
            if (equals(i)) {
                Log.d("SingleKeyGesture", "init HandleLongPressInfo");
                reset();
            }
        }

        public String toString() {
            return "KeyCode=" + KeyEvent.keyCodeToString(this.mKeyCode) + ", size=" + this.mTypeList.size();
        }
    }

    public boolean hasRule(int i) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return false;
        }
        return singleKeyRule.shouldInterceptKey(i);
    }

    public void setLongPressTimeout(int i, long j) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return;
        }
        singleKeyRule.setLongPressTimeoutMs(j);
    }

    public void initLongPressTimeout(int i) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return;
        }
        singleKeyRule.setLongPressTimeoutMs(0L);
    }

    public void setMultiPressTimeout(int i, long j) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return;
        }
        singleKeyRule.setMultiPressTimeoutMs(j);
    }

    public void initMultiPressTimeout(int i) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return;
        }
        singleKeyRule.setMultiPressTimeoutMs(0L);
    }

    public long getMultiPressTimeout(int i) {
        SingleKeyRule singleKeyRule = (SingleKeyRule) this.mRules.get(i);
        if (singleKeyRule == null) {
            return 0L;
        }
        return singleKeyRule.getMultiPressTimeoutMs();
    }

    public void setBeganFromNonInteractive() {
        if (DEBUG) {
            Log.d("SingleKeyGesture", "setBeganFromNonInteractive old=" + this.mBeganFromNonInteractive);
        }
        this.mBeganFromNonInteractive = true;
    }
}
