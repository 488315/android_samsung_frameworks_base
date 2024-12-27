package com.android.internal.policy;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IKeyguardService extends IInterface {
    void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback)
            throws RemoteException;

    void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence)
            throws RemoteException;

    void dismissKeyguardToLaunch(Intent intent) throws RemoteException;

    void doKeyguardTimeout(Bundle bundle) throws RemoteException;

    void onBootCompleted() throws RemoteException;

    void onDreamingStarted() throws RemoteException;

    void onDreamingStopped() throws RemoteException;

    void onFinishedBootAnim() throws RemoteException;

    void onFinishedGoingToSleep(int i, boolean z) throws RemoteException;

    void onFinishedWakingUp() throws RemoteException;

    void onScreenTurnedOff() throws RemoteException;

    void onScreenTurnedOn() throws RemoteException;

    void onScreenTurningOff() throws RemoteException;

    void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) throws RemoteException;

    void onShortPowerPressedGoHome() throws RemoteException;

    void onStartedGoingToSleep(int i) throws RemoteException;

    void onStartedWakingUp(int i, boolean z) throws RemoteException;

    void onSystemKeyPressed(int i) throws RemoteException;

    void onSystemReady() throws RemoteException;

    void setCoverOccluded(boolean z) throws RemoteException;

    void setCurrentUser(int i) throws RemoteException;

    void setDexOccluded(boolean z) throws RemoteException;

    void setKeyguardEnabled(boolean z) throws RemoteException;

    void setOccluded(boolean z, boolean z2) throws RemoteException;

    void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent)
            throws RemoteException;

    void setSwitchingUser(boolean z) throws RemoteException;

    void showDismissibleKeyguard() throws RemoteException;

    void startFingerprintAuthentication() throws RemoteException;

    void startKeyguardExitAnimation(long j, long j2) throws RemoteException;

    void startedEarlyWakingUp(int i) throws RemoteException;

    void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) throws RemoteException;

    public static class Default implements IKeyguardService {
        @Override // com.android.internal.policy.IKeyguardService
        public void setOccluded(boolean isOccluded, boolean animate) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void addStateMonitorCallback(IKeyguardStateCallback callback)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void verifyUnlock(IKeyguardExitCallback callback) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void dismiss(IKeyguardDismissCallback callback, CharSequence message)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onDreamingStarted() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onDreamingStopped() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onStartedGoingToSleep(int pmSleepReason) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedGoingToSleep(int pmSleepReason, boolean cameraGestureTriggered)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onStartedWakingUp(int pmWakeReason, boolean cameraGestureTriggered)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedWakingUp() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurningOn(IKeyguardDrawnCallback callback) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurnedOn() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurningOff() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurnedOff() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setKeyguardEnabled(boolean enabled) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onSystemReady() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void doKeyguardTimeout(Bundle options) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setSwitchingUser(boolean switching) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setCurrentUser(int userId) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onBootCompleted() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void startKeyguardExitAnimation(long startTime, long fadeoutDuration)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onShortPowerPressedGoHome() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void dismissKeyguardToLaunch(Intent intentToLaunch) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onSystemKeyPressed(int keycode) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void showDismissibleKeyguard() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent fillInIntent)
                throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void startFingerprintAuthentication() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedBootAnim() throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void startedEarlyWakingUp(int reason) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setDexOccluded(boolean isOccluded) throws RemoteException {}

        @Override // com.android.internal.policy.IKeyguardService
        public void setCoverOccluded(boolean isOccluded) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeyguardService {
        public static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardService";
        static final int TRANSACTION_addStateMonitorCallback = 2;
        static final int TRANSACTION_dismiss = 4;
        static final int TRANSACTION_dismissKeyguardToLaunch = 23;
        static final int TRANSACTION_doKeyguardTimeout = 17;
        static final int TRANSACTION_onBootCompleted = 20;
        static final int TRANSACTION_onDreamingStarted = 5;
        static final int TRANSACTION_onDreamingStopped = 6;
        static final int TRANSACTION_onFinishedBootAnim = 28;
        static final int TRANSACTION_onFinishedGoingToSleep = 8;
        static final int TRANSACTION_onFinishedWakingUp = 10;
        static final int TRANSACTION_onScreenTurnedOff = 14;
        static final int TRANSACTION_onScreenTurnedOn = 12;
        static final int TRANSACTION_onScreenTurningOff = 13;
        static final int TRANSACTION_onScreenTurningOn = 11;
        static final int TRANSACTION_onShortPowerPressedGoHome = 22;
        static final int TRANSACTION_onStartedGoingToSleep = 7;
        static final int TRANSACTION_onStartedWakingUp = 9;
        static final int TRANSACTION_onSystemKeyPressed = 24;
        static final int TRANSACTION_onSystemReady = 16;
        static final int TRANSACTION_setCoverOccluded = 31;
        static final int TRANSACTION_setCurrentUser = 19;
        static final int TRANSACTION_setDexOccluded = 30;
        static final int TRANSACTION_setKeyguardEnabled = 15;
        static final int TRANSACTION_setOccluded = 1;
        static final int TRANSACTION_setPendingIntentAfterUnlock = 26;
        static final int TRANSACTION_setSwitchingUser = 18;
        static final int TRANSACTION_showDismissibleKeyguard = 25;
        static final int TRANSACTION_startFingerprintAuthentication = 27;
        static final int TRANSACTION_startKeyguardExitAnimation = 21;
        static final int TRANSACTION_startedEarlyWakingUp = 29;
        static final int TRANSACTION_verifyUnlock = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardService)) {
                return (IKeyguardService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setOccluded";
                case 2:
                    return "addStateMonitorCallback";
                case 3:
                    return "verifyUnlock";
                case 4:
                    return "dismiss";
                case 5:
                    return "onDreamingStarted";
                case 6:
                    return "onDreamingStopped";
                case 7:
                    return "onStartedGoingToSleep";
                case 8:
                    return "onFinishedGoingToSleep";
                case 9:
                    return "onStartedWakingUp";
                case 10:
                    return "onFinishedWakingUp";
                case 11:
                    return "onScreenTurningOn";
                case 12:
                    return "onScreenTurnedOn";
                case 13:
                    return "onScreenTurningOff";
                case 14:
                    return "onScreenTurnedOff";
                case 15:
                    return "setKeyguardEnabled";
                case 16:
                    return "onSystemReady";
                case 17:
                    return "doKeyguardTimeout";
                case 18:
                    return "setSwitchingUser";
                case 19:
                    return "setCurrentUser";
                case 20:
                    return "onBootCompleted";
                case 21:
                    return "startKeyguardExitAnimation";
                case 22:
                    return "onShortPowerPressedGoHome";
                case 23:
                    return "dismissKeyguardToLaunch";
                case 24:
                    return "onSystemKeyPressed";
                case 25:
                    return "showDismissibleKeyguard";
                case 26:
                    return "setPendingIntentAfterUnlock";
                case 27:
                    return "startFingerprintAuthentication";
                case 28:
                    return "onFinishedBootAnim";
                case 29:
                    return "startedEarlyWakingUp";
                case 30:
                    return "setDexOccluded";
                case 31:
                    return "setCoverOccluded";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setOccluded(_arg0, _arg1);
                    return true;
                case 2:
                    IKeyguardStateCallback _arg02 =
                            IKeyguardStateCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addStateMonitorCallback(_arg02);
                    return true;
                case 3:
                    IKeyguardExitCallback _arg03 =
                            IKeyguardExitCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    verifyUnlock(_arg03);
                    return true;
                case 4:
                    IKeyguardDismissCallback _arg04 =
                            IKeyguardDismissCallback.Stub.asInterface(data.readStrongBinder());
                    CharSequence _arg12 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    dismiss(_arg04, _arg12);
                    return true;
                case 5:
                    onDreamingStarted();
                    return true;
                case 6:
                    onDreamingStopped();
                    return true;
                case 7:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    onStartedGoingToSleep(_arg05);
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    boolean _arg13 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onFinishedGoingToSleep(_arg06, _arg13);
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    boolean _arg14 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onStartedWakingUp(_arg07, _arg14);
                    return true;
                case 10:
                    onFinishedWakingUp();
                    return true;
                case 11:
                    IKeyguardDrawnCallback _arg08 =
                            IKeyguardDrawnCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onScreenTurningOn(_arg08);
                    return true;
                case 12:
                    onScreenTurnedOn();
                    return true;
                case 13:
                    onScreenTurningOff();
                    return true;
                case 14:
                    onScreenTurnedOff();
                    return true;
                case 15:
                    boolean _arg09 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setKeyguardEnabled(_arg09);
                    return true;
                case 16:
                    onSystemReady();
                    return true;
                case 17:
                    Bundle _arg010 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    doKeyguardTimeout(_arg010);
                    return true;
                case 18:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSwitchingUser(_arg011);
                    return true;
                case 19:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    setCurrentUser(_arg012);
                    return true;
                case 20:
                    onBootCompleted();
                    return true;
                case 21:
                    long _arg013 = data.readLong();
                    long _arg15 = data.readLong();
                    data.enforceNoDataAvail();
                    startKeyguardExitAnimation(_arg013, _arg15);
                    return true;
                case 22:
                    onShortPowerPressedGoHome();
                    return true;
                case 23:
                    Intent _arg014 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    dismissKeyguardToLaunch(_arg014);
                    return true;
                case 24:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    onSystemKeyPressed(_arg015);
                    return true;
                case 25:
                    showDismissibleKeyguard();
                    return true;
                case 26:
                    PendingIntent _arg016 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    Intent _arg16 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    setPendingIntentAfterUnlock(_arg016, _arg16);
                    return true;
                case 27:
                    startFingerprintAuthentication();
                    return true;
                case 28:
                    onFinishedBootAnim();
                    return true;
                case 29:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    startedEarlyWakingUp(_arg017);
                    return true;
                case 30:
                    boolean _arg018 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDexOccluded(_arg018);
                    return true;
                case 31:
                    boolean _arg019 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCoverOccluded(_arg019);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyguardService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setOccluded(boolean isOccluded, boolean animate) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isOccluded);
                    _data.writeBoolean(animate);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void addStateMonitorCallback(IKeyguardStateCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void verifyUnlock(IKeyguardExitCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void dismiss(IKeyguardDismissCallback callback, CharSequence message)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onDreamingStarted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onDreamingStopped() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onStartedGoingToSleep(int pmSleepReason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pmSleepReason);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedGoingToSleep(int pmSleepReason, boolean cameraGestureTriggered)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pmSleepReason);
                    _data.writeBoolean(cameraGestureTriggered);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onStartedWakingUp(int pmWakeReason, boolean cameraGestureTriggered)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pmWakeReason);
                    _data.writeBoolean(cameraGestureTriggered);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedWakingUp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurningOn(IKeyguardDrawnCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurnedOn() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurningOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurnedOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setKeyguardEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onSystemReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void doKeyguardTimeout(Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setSwitchingUser(boolean switching) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(switching);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setCurrentUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onBootCompleted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startKeyguardExitAnimation(long startTime, long fadeoutDuration)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(startTime);
                    _data.writeLong(fadeoutDuration);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onShortPowerPressedGoHome() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void dismissKeyguardToLaunch(Intent intentToLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intentToLaunch, 0);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onSystemKeyPressed(int keycode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keycode);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void showDismissibleKeyguard() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setPendingIntentAfterUnlock(
                    PendingIntent pendingIntent, Intent fillInIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    _data.writeTypedObject(fillInIntent, 0);
                    this.mRemote.transact(26, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startFingerprintAuthentication() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedBootAnim() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startedEarlyWakingUp(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setDexOccluded(boolean isOccluded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isOccluded);
                    this.mRemote.transact(30, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setCoverOccluded(boolean isOccluded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isOccluded);
                    this.mRemote.transact(31, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }
    }
}
