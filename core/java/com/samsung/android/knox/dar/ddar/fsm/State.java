package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes5.dex */
public enum State {
    IDLE,
    DEVICE_UNLOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_LOCK,
    DEVICE_UNLOCK_DATA_LOCK;

    /* renamed from: com.samsung.android.knox.dar.ddar.fsm.State$1 */
    static /* synthetic */ class C51421 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State = iArr;
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State[State.DEVICE_UNLOCK_DATA_UNLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State[State.DEVICE_LOCK_DATA_UNLOCK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State[State.DEVICE_LOCK_DATA_LOCK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State[State.DEVICE_UNLOCK_DATA_LOCK.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @Override // java.lang.Enum
    public String toString() {
        switch (C51421.$SwitchMap$com$samsung$android$knox$dar$ddar$fsm$State[ordinal()]) {
            case 1:
                return "IDLE";
            case 2:
                return "DEVICE_UNLOCK_DATA_UNLOCK";
            case 3:
                return "DEVICE_LOCK_DATA_UNLOCK";
            case 4:
                return "DEVICE_LOCK_DATA_LOCK";
            case 5:
                return "DEVICE_UNLOCK_DATA_LOCK";
            default:
                return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
    }
}
