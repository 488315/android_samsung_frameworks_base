package com.android.server.policy;

import android.os.Handler;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.KeyEvent;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.input.InputManagerService;
import com.android.server.policy.KeyCombinationManager;
import com.android.server.policy.PhoneWindowManager;
import com.samsung.android.rune.InputRune;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public final class KeyCombinationManager {
    public static final int[] HW_KEY_LIST_EXCEPT_POWER;
    public static final Set KEYCODE_BLOCK_DELAY_DISPATCHING_KEY_LIST;
    public final Handler mHandler;
    public final InputManagerService.LocalService mInputManagerInternal;
    public TwoKeysCombinationRule mTriggeredRule;
    public final ArrayList mRemovePendingRules = new ArrayList();
    public final SparseArray mDownKeyEvent = new SparseArray(2);
    public final SparseLongArray mDownTimes = new SparseLongArray(2);
    public final ArrayList mRules = new ArrayList();
    public final Object mLock = new Object();
    public final ArrayList mActiveRules = new ArrayList();
    public final KeyCombinationManager$$ExternalSyntheticLambda5 mResetKeyDownEventRunnable = new KeyCombinationManager$$ExternalSyntheticLambda5(2, this);

    public abstract class TwoKeysCombinationRule {
        public final int mKeyCode1;
        public final int mKeyCode2;

        public TwoKeysCombinationRule(int i, int i2) {
            this.mKeyCode1 = i;
            this.mKeyCode2 = i2;
        }

        public abstract void cancel();

        public void cancel(KeyEvent keyEvent, boolean z) {
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TwoKeysCombinationRule)) {
                return false;
            }
            TwoKeysCombinationRule twoKeysCombinationRule = (TwoKeysCombinationRule) obj;
            int i = twoKeysCombinationRule.mKeyCode1;
            int i2 = this.mKeyCode2;
            int i3 = this.mKeyCode1;
            int i4 = twoKeysCombinationRule.mKeyCode2;
            if (i3 == i && i2 == i4) {
                return true;
            }
            return i3 == i4 && i2 == i;
        }

        public abstract void execute();

        public long getKeyInterceptDelayMs() {
            return 150L;
        }

        public final int hashCode() {
            return (this.mKeyCode1 * 31) + this.mKeyCode2;
        }

        public boolean preCondition() {
            return true;
        }

        public final boolean shouldInterceptKey(int i) {
            return preCondition() && (i == this.mKeyCode1 || i == this.mKeyCode2);
        }

        public final String toString() {
            return KeyEvent.keyCodeToString(this.mKeyCode1) + " + " + KeyEvent.keyCodeToString(this.mKeyCode2);
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        KEYCODE_BLOCK_DELAY_DISPATCHING_KEY_LIST = arraySet;
        arraySet.add(Integer.valueOf(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED));
        arraySet.add(4);
        HW_KEY_LIST_EXCEPT_POWER = new int[]{24, 25};
    }

    public KeyCombinationManager(PhoneWindowManager.PolicyHandler policyHandler, InputManagerService.LocalService localService) {
        this.mHandler = policyHandler;
        if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
            this.mInputManagerInternal = localService;
        }
    }

    public static void forAllRules(ArrayList arrayList, Consumer consumer) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            consumer.accept((TwoKeysCombinationRule) arrayList.get(i));
        }
    }

    public final void addRule(TwoKeysCombinationRule twoKeysCombinationRule) {
        if (!this.mRules.contains(twoKeysCombinationRule)) {
            this.mRules.add(twoKeysCombinationRule);
            return;
        }
        throw new IllegalArgumentException("Rule : " + twoKeysCombinationRule + " already exists.");
    }

    public final boolean interceptKeyLocked(final KeyEvent keyEvent, final boolean z) {
        boolean z2 = keyEvent.getAction() == 0;
        final int keyCode = keyEvent.getKeyCode();
        int size = this.mActiveRules.size();
        long eventTime = keyEvent.getEventTime();
        Handler handler = this.mHandler;
        if (z && z2) {
            if (this.mDownTimes.size() > 0) {
                if (this.mDownTimes.get(keyCode) != 0 && keyEvent.getRepeatCount() > 0) {
                    return false;
                }
                if (size > 0 && eventTime > this.mDownTimes.valueAt(0) + 150) {
                    forAllRules(this.mActiveRules, new KeyCombinationManager$$ExternalSyntheticLambda0());
                    this.mActiveRules.clear();
                    return false;
                }
                if (size == 0) {
                    this.mDownTimes.clear();
                    Log.d("KeyCombinationManager", "There is no active rule. mDownTimes clear");
                    if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
                        KeyCombinationManager$$ExternalSyntheticLambda5 keyCombinationManager$$ExternalSyntheticLambda5 = this.mResetKeyDownEventRunnable;
                        handler.removeCallbacks(keyCombinationManager$$ExternalSyntheticLambda5);
                        handler.postDelayed(keyCombinationManager$$ExternalSyntheticLambda5, 5000L);
                    }
                    return false;
                }
            }
            if (this.mDownTimes.get(keyCode) != 0) {
                return false;
            }
            this.mDownTimes.put(keyCode, eventTime);
            if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
                this.mDownKeyEvent.put(keyCode, new KeyEvent(keyEvent));
            }
            if (this.mDownTimes.size() == 1) {
                this.mTriggeredRule = null;
                forAllRules(this.mRules, new Consumer() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        KeyCombinationManager keyCombinationManager = KeyCombinationManager.this;
                        int i = keyCode;
                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule = (KeyCombinationManager.TwoKeysCombinationRule) obj;
                        keyCombinationManager.getClass();
                        if (twoKeysCombinationRule.shouldInterceptKey(i)) {
                            keyCombinationManager.mActiveRules.add(twoKeysCombinationRule);
                        }
                    }
                });
            } else {
                if (this.mTriggeredRule != null) {
                    return true;
                }
                ?? r1 = new ToBooleanFunction() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda2
                    public final boolean apply(Object obj) {
                        KeyCombinationManager keyCombinationManager = KeyCombinationManager.this;
                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule = (KeyCombinationManager.TwoKeysCombinationRule) obj;
                        SparseLongArray sparseLongArray = keyCombinationManager.mDownTimes;
                        twoKeysCombinationRule.getClass();
                        long uptimeMillis = SystemClock.uptimeMillis();
                        int i = twoKeysCombinationRule.mKeyCode1;
                        if (sparseLongArray.get(i) > 0) {
                            int i2 = twoKeysCombinationRule.mKeyCode2;
                            if (sparseLongArray.get(i2) > 0 && uptimeMillis <= sparseLongArray.get(i) + 150 && uptimeMillis <= sparseLongArray.get(i2) + 150) {
                                Log.v("KeyCombinationManager", "Performing combination rule : " + twoKeysCombinationRule);
                                keyCombinationManager.mHandler.post(new KeyCombinationManager$$ExternalSyntheticLambda5(1, twoKeysCombinationRule));
                                keyCombinationManager.mTriggeredRule = twoKeysCombinationRule;
                                return true;
                            }
                        }
                        return false;
                    }
                };
                int size2 = this.mActiveRules.size();
                for (int i = 0; i < size2 && !r1.apply((TwoKeysCombinationRule) this.mActiveRules.get(i)); i++) {
                }
                this.mActiveRules.clear();
                TwoKeysCombinationRule twoKeysCombinationRule = this.mTriggeredRule;
                if (twoKeysCombinationRule != null) {
                    this.mActiveRules.add(twoKeysCombinationRule);
                    return true;
                }
            }
        } else if (z || !z2) {
            this.mDownTimes.delete(keyCode);
            if (InputRune.PWM_SIDE_KEY_QUINTUPLE_PRESS_EMERGENCY_SOS) {
                this.mDownKeyEvent.delete(keyCode);
            }
            if (InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY) {
                for (int size3 = this.mRemovePendingRules.size() - 1; size3 >= 0; size3--) {
                    final TwoKeysCombinationRule twoKeysCombinationRule2 = (TwoKeysCombinationRule) this.mRemovePendingRules.get(size3);
                    if (twoKeysCombinationRule2.shouldInterceptKey(keyCode)) {
                        final int i2 = 0;
                        handler.post(new Runnable() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i2) {
                                    case 0:
                                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule3 = twoKeysCombinationRule2;
                                        KeyEvent keyEvent2 = keyEvent;
                                        twoKeysCombinationRule3.cancel(new KeyEvent(keyEvent2), z);
                                        break;
                                    default:
                                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule4 = twoKeysCombinationRule2;
                                        KeyEvent keyEvent3 = keyEvent;
                                        twoKeysCombinationRule4.cancel(new KeyEvent(keyEvent3), z);
                                        break;
                                }
                            }
                        });
                    }
                    this.mRemovePendingRules.remove(size3);
                }
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                final TwoKeysCombinationRule twoKeysCombinationRule3 = (TwoKeysCombinationRule) this.mActiveRules.get(i3);
                if (twoKeysCombinationRule3.shouldInterceptKey(keyCode)) {
                    if (InputRune.PWM_KEY_COMBINATION_SCREENSHOT_SIDE_KEY) {
                        final int i4 = 1;
                        handler.post(new Runnable() { // from class: com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i4) {
                                    case 0:
                                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule32 = twoKeysCombinationRule3;
                                        KeyEvent keyEvent2 = keyEvent;
                                        twoKeysCombinationRule32.cancel(new KeyEvent(keyEvent2), z);
                                        break;
                                    default:
                                        KeyCombinationManager.TwoKeysCombinationRule twoKeysCombinationRule4 = twoKeysCombinationRule3;
                                        KeyEvent keyEvent3 = keyEvent;
                                        twoKeysCombinationRule4.cancel(new KeyEvent(keyEvent3), z);
                                        break;
                                }
                            }
                        });
                        this.mRemovePendingRules.add(twoKeysCombinationRule3);
                    }
                    handler.post(new KeyCombinationManager$$ExternalSyntheticLambda5(0, twoKeysCombinationRule3));
                    this.mActiveRules.remove(i3);
                }
            }
        } else if (this.mDownTimes.size() == 0 && this.mTriggeredRule != null) {
            Log.d("KeyCombinationManager", "interceptKeyLocked mTriggeredRule is null");
            this.mTriggeredRule = null;
        }
        return false;
    }

    public final boolean isKeyConsumed(KeyEvent keyEvent) {
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if ((keyEvent.getFlags() & 1024) != 0) {
                    return false;
                }
                TwoKeysCombinationRule twoKeysCombinationRule = this.mTriggeredRule;
                if (twoKeysCombinationRule != null && twoKeysCombinationRule.shouldInterceptKey(keyEvent.getKeyCode())) {
                    z = true;
                }
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isPowerKeyIntercepted() {
        boolean z;
        synchronized (this.mLock) {
            try {
                int size = this.mActiveRules.size();
                boolean z2 = false;
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z = false;
                        break;
                    }
                    if (((TwoKeysCombinationRule) this.mActiveRules.get(i)).shouldInterceptKey(26)) {
                        z = true;
                        break;
                    }
                    i++;
                }
                if (!z) {
                    return false;
                }
                if (this.mDownTimes.size() <= 1) {
                    if (this.mDownTimes.get(26) == 0) {
                    }
                    return z2;
                }
                z2 = true;
                return z2;
            } finally {
            }
        }
    }
}
