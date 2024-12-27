package com.android.internal.telephony;


/* loaded from: classes5.dex */
public class PhoneConstantConversions {
    public static int convertCallState(PhoneConstants.State state) {
        switch (state) {
            case RINGING:
                return 1;
            case OFFHOOK:
                return 2;
            default:
                return 0;
        }
    }

    public static PhoneConstants.State convertCallState(int state) {
        switch (state) {
            case 1:
                return PhoneConstants.State.RINGING;
            case 2:
                return PhoneConstants.State.OFFHOOK;
            default:
                return PhoneConstants.State.IDLE;
        }
    }

    public static int convertDataState(PhoneConstants.DataState state) {
        switch (state) {
            case CONNECTING:
                return 1;
            case CONNECTED:
                return 2;
            case SUSPENDED:
                return 3;
            case DISCONNECTING:
                return 4;
            default:
                return 0;
        }
    }

    public static PhoneConstants.DataState convertDataState(int state) {
        switch (state) {
            case 1:
                return PhoneConstants.DataState.CONNECTING;
            case 2:
                return PhoneConstants.DataState.CONNECTED;
            case 3:
                return PhoneConstants.DataState.SUSPENDED;
            case 4:
                return PhoneConstants.DataState.DISCONNECTING;
            default:
                return PhoneConstants.DataState.DISCONNECTED;
        }
    }
}
