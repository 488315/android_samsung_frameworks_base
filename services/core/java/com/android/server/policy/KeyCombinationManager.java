package com.android.server.policy;

import android.os.Handler;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.InputDevice;
import android.view.KeyEvent;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.input.InputManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class KeyCombinationManager {
  public static int[] HW_KEY_LIST_EXCEPT_POWER;
  public static final Set KEYCODE_BLOCK_DELAY_DISPATCHING_KEY_LIST;
  public final Handler mHandler;
  public InputManagerInternal mInputManagerInternal;
  public TwoKeysCombinationRule mTriggeredRule;
  public final ArrayList mRemovePendingRules = new ArrayList();
  public final SparseArray mDownKeyEvent = new SparseArray(2);
  public final SparseLongArray mDownTimes = new SparseLongArray(2);
  public final ArrayList mRules = new ArrayList();
  public final Object mLock = new Object();
  public final ArrayList mActiveRules = new ArrayList();
  public final Runnable mResetKeyDownEventRunnable =
      new Runnable() { // from class:
                       // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
          KeyCombinationManager.this.lambda$new$6();
        }
      };

  public abstract class TwoKeysCombinationRule {
    public int mKeyCode1;
    public int mKeyCode2;

    public abstract void cancel();

    public void cancel(KeyEvent keyEvent, boolean z) {}

    public abstract void execute();

    public long getKeyInterceptDelayMs() {
      return 150L;
    }

    public boolean preCondition() {
      return true;
    }

    public TwoKeysCombinationRule(int i, int i2) {
      this.mKeyCode1 = i;
      this.mKeyCode2 = i2;
    }

    public boolean shouldInterceptKey(int i) {
      return preCondition() && (i == this.mKeyCode1 || i == this.mKeyCode2);
    }

    public boolean shouldInterceptKeys(SparseLongArray sparseLongArray) {
      long uptimeMillis = SystemClock.uptimeMillis();
      return sparseLongArray.get(this.mKeyCode1) > 0
          && sparseLongArray.get(this.mKeyCode2) > 0
          && uptimeMillis <= sparseLongArray.get(this.mKeyCode1) + 150
          && uptimeMillis <= sparseLongArray.get(this.mKeyCode2) + 150;
    }

    public String toString() {
      return KeyEvent.keyCodeToString(this.mKeyCode1)
          + " + "
          + KeyEvent.keyCodeToString(this.mKeyCode2);
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!(obj instanceof TwoKeysCombinationRule)) {
        return false;
      }
      TwoKeysCombinationRule twoKeysCombinationRule = (TwoKeysCombinationRule) obj;
      int i = this.mKeyCode1;
      int i2 = twoKeysCombinationRule.mKeyCode1;
      if (i == i2 && this.mKeyCode2 == twoKeysCombinationRule.mKeyCode2) {
        return true;
      }
      return i == twoKeysCombinationRule.mKeyCode2 && this.mKeyCode2 == i2;
    }

    public int hashCode() {
      return (this.mKeyCode1 * 31) + this.mKeyCode2;
    }
  }

  public KeyCombinationManager(Handler handler, InputManagerInternal inputManagerInternal) {
    this.mHandler = handler;
    if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
      this.mInputManagerInternal = inputManagerInternal;
    }
  }

  public void addRule(TwoKeysCombinationRule twoKeysCombinationRule) {
    if (this.mRules.contains(twoKeysCombinationRule)) {
      throw new IllegalArgumentException("Rule : " + twoKeysCombinationRule + " already exists.");
    }
    this.mRules.add(twoKeysCombinationRule);
  }

  public boolean interceptKey(KeyEvent keyEvent, boolean z) {
    boolean interceptKeyLocked;
    synchronized (this.mLock) {
      interceptKeyLocked = interceptKeyLocked(keyEvent, z);
    }
    return interceptKeyLocked;
  }

  public final boolean interceptKeyLocked(KeyEvent keyEvent, final boolean z) {
    boolean z2 = keyEvent.getAction() == 0;
    final int keyCode = keyEvent.getKeyCode();
    int size = this.mActiveRules.size();
    long eventTime = keyEvent.getEventTime();
    if (z && z2) {
      if (this.mDownTimes.size() > 0) {
        if (this.mDownTimes.get(keyCode) != 0 && keyEvent.getRepeatCount() > 0) {
          return false;
        }
        if (size > 0 && eventTime > this.mDownTimes.valueAt(0) + 150) {
          forAllRules(
              this.mActiveRules,
              new Consumer() { // from class:
                               // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  ((KeyCombinationManager.TwoKeysCombinationRule) obj).cancel();
                }
              });
          this.mActiveRules.clear();
          return false;
        }
        if (size == 0) {
          this.mDownTimes.clear();
          Log.d("KeyCombinationManager", "There is no active rule. mDownTimes clear");
          if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
            this.mHandler.removeCallbacks(this.mResetKeyDownEventRunnable);
            this.mHandler.postDelayed(this.mResetKeyDownEventRunnable, 5000L);
          }
          return false;
        }
      }
      if (this.mDownTimes.get(keyCode) != 0) {
        return false;
      }
      this.mDownTimes.put(keyCode, eventTime);
      if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
        this.mDownKeyEvent.put(keyCode, new KeyEvent(keyEvent));
      }
      if (this.mDownTimes.size() == 1) {
        this.mTriggeredRule = null;
        forAllRules(
            this.mRules,
            new Consumer() { // from class:
                             // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda3
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                KeyCombinationManager.this.lambda$interceptKeyLocked$1(
                    keyCode, (KeyCombinationManager.TwoKeysCombinationRule) obj);
              }
            });
      } else {
        if (this.mTriggeredRule != null) {
          return true;
        }
        forAllActiveRules(
            new ToBooleanFunction() { // from class:
                                      // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda4
              public final boolean apply(Object obj) {
                boolean lambda$interceptKeyLocked$2;
                lambda$interceptKeyLocked$2 =
                    KeyCombinationManager.this.lambda$interceptKeyLocked$2(
                        (KeyCombinationManager.TwoKeysCombinationRule) obj);
                return lambda$interceptKeyLocked$2;
              }
            });
        this.mActiveRules.clear();
        TwoKeysCombinationRule twoKeysCombinationRule = this.mTriggeredRule;
        if (twoKeysCombinationRule != null) {
          this.mActiveRules.add(twoKeysCombinationRule);
          return true;
        }
      }
    } else if (!z && z2) {
      if (this.mDownTimes.size() == 0 && this.mTriggeredRule != null) {
        Log.d("KeyCombinationManager", "interceptKeyLocked mTriggeredRule is null");
        this.mTriggeredRule = null;
      }
    } else {
      this.mDownTimes.delete(keyCode);
      if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
        this.mDownKeyEvent.delete(keyCode);
      }
      final KeyEvent keyEvent2 = new KeyEvent(keyEvent);
      if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
        for (int size2 = this.mRemovePendingRules.size() - 1; size2 >= 0; size2--) {
          final TwoKeysCombinationRule twoKeysCombinationRule2 =
              (TwoKeysCombinationRule) this.mRemovePendingRules.get(size2);
          if (twoKeysCombinationRule2.shouldInterceptKey(keyCode)) {
            this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda5
                  @Override // java.lang.Runnable
                  public final void run() {
                    KeyCombinationManager.TwoKeysCombinationRule.this.cancel(keyEvent2, z);
                  }
                });
          }
          this.mRemovePendingRules.remove(size2);
        }
      }
      for (int i = size - 1; i >= 0; i--) {
        final TwoKeysCombinationRule twoKeysCombinationRule3 =
            (TwoKeysCombinationRule) this.mActiveRules.get(i);
        if (twoKeysCombinationRule3.shouldInterceptKey(keyCode)) {
          if (CoreRune.FW_SCREENSHOT_BY_SIDE_KEY_COMBINATION) {
            this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda6
                  @Override // java.lang.Runnable
                  public final void run() {
                    KeyCombinationManager.TwoKeysCombinationRule.this.cancel(keyEvent2, z);
                  }
                });
            this.mRemovePendingRules.add(twoKeysCombinationRule3);
          }
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                  KeyCombinationManager.TwoKeysCombinationRule.this.cancel();
                }
              });
          this.mActiveRules.remove(i);
        }
      }
    }
    return false;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$interceptKeyLocked$1(
      int i, TwoKeysCombinationRule twoKeysCombinationRule) {
    if (twoKeysCombinationRule.shouldInterceptKey(i)) {
      this.mActiveRules.add(twoKeysCombinationRule);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$interceptKeyLocked$2(
      final TwoKeysCombinationRule twoKeysCombinationRule) {
    if (!twoKeysCombinationRule.shouldInterceptKeys(this.mDownTimes)) {
      return false;
    }
    Log.v("KeyCombinationManager", "Performing combination rule : " + twoKeysCombinationRule);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda8
          @Override // java.lang.Runnable
          public final void run() {
            KeyCombinationManager.TwoKeysCombinationRule.this.execute();
          }
        });
    this.mTriggeredRule = twoKeysCombinationRule;
    return true;
  }

  public long getKeyInterceptTimeout(int i) {
    long j = 0;
    if (KEYCODE_BLOCK_DELAY_DISPATCHING_KEY_LIST.contains(Integer.valueOf(i))) {
      return 0L;
    }
    synchronized (this.mLock) {
      if (this.mDownTimes.get(i) == 0) {
        return 0L;
      }
      Iterator it = this.mActiveRules.iterator();
      while (it.hasNext()) {
        TwoKeysCombinationRule twoKeysCombinationRule = (TwoKeysCombinationRule) it.next();
        if (twoKeysCombinationRule.shouldInterceptKey(i)) {
          j = Math.max(j, twoKeysCombinationRule.getKeyInterceptDelayMs());
        }
      }
      return this.mDownTimes.get(i) + Math.min(j, 150L);
    }
  }

  public boolean isKeyConsumed(KeyEvent keyEvent) {
    synchronized (this.mLock) {
      boolean z = false;
      if ((keyEvent.getFlags() & 1024) != 0) {
        return false;
      }
      TwoKeysCombinationRule twoKeysCombinationRule = this.mTriggeredRule;
      if (twoKeysCombinationRule != null
          && twoKeysCombinationRule.shouldInterceptKey(keyEvent.getKeyCode())) {
        z = true;
      }
      return z;
    }
  }

  public boolean isPowerKeyIntercepted() {
    synchronized (this.mLock) {
      if (forAllActiveRules(
          new ToBooleanFunction() { // from class:
                                    // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda1
            public final boolean apply(Object obj) {
              boolean lambda$isPowerKeyIntercepted$5;
              lambda$isPowerKeyIntercepted$5 =
                  KeyCombinationManager.lambda$isPowerKeyIntercepted$5(
                      (KeyCombinationManager.TwoKeysCombinationRule) obj);
              return lambda$isPowerKeyIntercepted$5;
            }
          })) {
        return this.mDownTimes.size() > 1 || this.mDownTimes.get(26) == 0;
      }
      return false;
    }
  }

  public static /* synthetic */ boolean lambda$isPowerKeyIntercepted$5(
      TwoKeysCombinationRule twoKeysCombinationRule) {
    return twoKeysCombinationRule.shouldInterceptKey(26);
  }

  public final void forAllRules(ArrayList arrayList, Consumer consumer) {
    int size = arrayList.size();
    for (int i = 0; i < size; i++) {
      consumer.accept((TwoKeysCombinationRule) arrayList.get(i));
    }
  }

  public final boolean forAllActiveRules(ToBooleanFunction toBooleanFunction) {
    int size = this.mActiveRules.size();
    for (int i = 0; i < size; i++) {
      if (toBooleanFunction.apply((TwoKeysCombinationRule) this.mActiveRules.get(i))) {
        return true;
      }
    }
    return false;
  }

  static {
    ArraySet arraySet = new ArraySet();
    KEYCODE_BLOCK_DELAY_DISPATCHING_KEY_LIST = arraySet;
    arraySet.add(
        Integer.valueOf(
            FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED));
    arraySet.add(4);
    HW_KEY_LIST_EXCEPT_POWER = new int[] {24, 25};
  }

  public boolean isKeyPressed(int i) {
    return this.mDownTimes.get(i) != 0;
  }

  public int isOtherKeyPressed() {
    for (int i : HW_KEY_LIST_EXCEPT_POWER) {
      if (this.mDownKeyEvent.get(i) != null) {
        return i;
      }
    }
    return 0;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$6() {
    InputDevice device;
    if (this.mActiveRules.size() != 0) {
      return;
    }
    boolean z = true;
    for (int i = 0; i < this.mDownKeyEvent.size(); i++) {
      KeyEvent keyEvent = (KeyEvent) this.mDownKeyEvent.valueAt(i);
      if (keyEvent != null
          && (device = keyEvent.getDevice()) != null
          && !device.isExternal()
          && !device.isVirtual()) {
        int keyCode = keyEvent.getKeyCode();
        InputManagerInternal inputManagerInternal = this.mInputManagerInternal;
        int keyCodeState =
            inputManagerInternal != null
                ? inputManagerInternal.getKeyCodeState(-1, -256, keyCode)
                : -1;
        Log.d(
            "KeyCombinationManager",
            "mResetKeyDownTimesRunnable keyCode=" + keyCode + " state=" + keyCodeState);
        if (keyCodeState > 0) {
          z = false;
        }
      }
    }
    if (z) {
      Log.d("KeyCombinationManager", "Reset keyDownTimes clear");
      this.mDownKeyEvent.clear();
    }
  }

  public void dump(final String str, final PrintWriter printWriter) {
    printWriter.println(str + "KeyCombination rules:");
    forAllRules(
        this.mRules,
        new Consumer() { // from class:
                         // com.android.server.policy.KeyCombinationManager$$ExternalSyntheticLambda9
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            KeyCombinationManager.lambda$dump$7(
                printWriter, str, (KeyCombinationManager.TwoKeysCombinationRule) obj);
          }
        });
    if (CoreRune.FW_QUINTUPLE_PRESS_POWER_EMERGENCY_SOS) {
      for (int i = 0; i < this.mDownTimes.size(); i++) {
        printWriter.print(str + " mDownTimes keyCode=" + this.mDownTimes.keyAt(i));
        StringBuilder sb = new StringBuilder();
        sb.append(" time=");
        sb.append(this.mDownTimes.valueAt(i));
        printWriter.println(sb.toString());
      }
      for (int i2 = 0; i2 < this.mDownKeyEvent.size(); i2++) {
        printWriter.println(str + " mDownKeyEvent event=" + this.mDownKeyEvent.valueAt(i2));
      }
    }
  }

  public static /* synthetic */ void lambda$dump$7(
      PrintWriter printWriter, String str, TwoKeysCombinationRule twoKeysCombinationRule) {
    printWriter.println(str + "  " + twoKeysCombinationRule);
  }
}
