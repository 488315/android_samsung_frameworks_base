package com.android.internal.telephony;

import com.android.internal.telephony.PhoneConstants;

/* loaded from: classes4.dex */
public class PhoneConstantConversions {
    public static int convertCallState(PhoneConstants.State state) {
        int i = AnonymousClass1.$SwitchMap$com$android$internal$telephony$PhoneConstants$State[state.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return i2;
    }

    public static PhoneConstants.State convertCallState(int i) {
        if (i == 1) {
            return PhoneConstants.State.RINGING;
        }
        if (i == 2) {
            return PhoneConstants.State.OFFHOOK;
        }
        return PhoneConstants.State.IDLE;
    }

    /* renamed from: com.android.internal.telephony.PhoneConstantConversions$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$PhoneConstants$DataState;
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$PhoneConstants$State;

        static {
            int[] iArr = new int[PhoneConstants.DataState.values().length];
            $SwitchMap$com$android$internal$telephony$PhoneConstants$DataState = iArr;
            try {
                iArr[PhoneConstants.DataState.CONNECTING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$PhoneConstants$DataState[PhoneConstants.DataState.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$PhoneConstants$DataState[PhoneConstants.DataState.SUSPENDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$PhoneConstants$DataState[PhoneConstants.DataState.DISCONNECTING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[PhoneConstants.State.values().length];
            $SwitchMap$com$android$internal$telephony$PhoneConstants$State = iArr2;
            try {
                iArr2[PhoneConstants.State.RINGING.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$PhoneConstants$State[PhoneConstants.State.OFFHOOK.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static int convertDataState(PhoneConstants.DataState dataState) {
        int i = AnonymousClass1.$SwitchMap$com$android$internal$telephony$PhoneConstants$DataState[dataState.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 0;
                    }
                }
            }
        }
        return i2;
    }

    public static PhoneConstants.DataState convertDataState(int i) {
        if (i == 1) {
            return PhoneConstants.DataState.CONNECTING;
        }
        if (i == 2) {
            return PhoneConstants.DataState.CONNECTED;
        }
        if (i == 3) {
            return PhoneConstants.DataState.SUSPENDED;
        }
        if (i == 4) {
            return PhoneConstants.DataState.DISCONNECTING;
        }
        return PhoneConstants.DataState.DISCONNECTED;
    }
}
