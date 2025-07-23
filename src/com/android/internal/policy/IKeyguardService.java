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
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;

/* loaded from: classes5.dex */
public interface IKeyguardService extends IInterface {

    public static class Default implements IKeyguardService {
        @Override // com.android.internal.policy.IKeyguardService
        public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void dismissKeyguardToLaunch(Intent intent) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void doKeyguardTimeout(Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onBootCompleted() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onDreamingStarted() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onDreamingStopped() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedBootAnim() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedGoingToSleep(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onFinishedWakingUp() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurnedOff() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurnedOn() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurningOff() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onShortPowerPressedGoHome() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onStartedGoingToSleep(int i) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onStartedWakingUp(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onSystemKeyPressed(int i) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void onSystemReady() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setCoverOccluded(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setCurrentUser(int i) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setDexOccluded(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setKeyguardEnabled(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setOccluded(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void setSwitchingUser(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void showDismissibleKeyguard() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void startFingerprintAuthentication() throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void startKeyguardExitAnimation(long j, long j2) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void startedEarlyWakingUp(int i) throws RemoteException {
        }

        @Override // com.android.internal.policy.IKeyguardService
        public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) throws RemoteException {
        }
    }

    void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) throws RemoteException;

    void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException;

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

    void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException;

    void setSwitchingUser(boolean z) throws RemoteException;

    void showDismissibleKeyguard() throws RemoteException;

    void startFingerprintAuthentication() throws RemoteException;

    void startKeyguardExitAnimation(long j, long j2) throws RemoteException;

    void startedEarlyWakingUp(int i) throws RemoteException;

    void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyguardService {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyguardService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyguardService)) {
                return (IKeyguardService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOccluded(readBoolean, readBoolean2);
                    return true;
                case 2:
                    IKeyguardStateCallback asInterface = IKeyguardStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addStateMonitorCallback(asInterface);
                    return true;
                case 3:
                    IKeyguardExitCallback asInterface2 = IKeyguardExitCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    verifyUnlock(asInterface2);
                    return true;
                case 4:
                    IKeyguardDismissCallback asInterface3 = IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismiss(asInterface3, charSequence);
                    return true;
                case 5:
                    onDreamingStarted();
                    return true;
                case 6:
                    onDreamingStopped();
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStartedGoingToSleep(readInt);
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFinishedGoingToSleep(readInt2, readBoolean3);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onStartedWakingUp(readInt3, readBoolean4);
                    return true;
                case 10:
                    onFinishedWakingUp();
                    return true;
                case 11:
                    IKeyguardDrawnCallback asInterface4 = IKeyguardDrawnCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onScreenTurningOn(asInterface4);
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
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setKeyguardEnabled(readBoolean5);
                    return true;
                case 16:
                    onSystemReady();
                    return true;
                case 17:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    doKeyguardTimeout(bundle);
                    return true;
                case 18:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSwitchingUser(readBoolean6);
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentUser(readInt4);
                    return true;
                case 20:
                    onBootCompleted();
                    return true;
                case 21:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    startKeyguardExitAnimation(readLong, readLong2);
                    return true;
                case 22:
                    onShortPowerPressedGoHome();
                    return true;
                case 23:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguardToLaunch(intent);
                    return true;
                case 24:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSystemKeyPressed(readInt5);
                    return true;
                case 25:
                    showDismissibleKeyguard();
                    return true;
                case 26:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPendingIntentAfterUnlock(pendingIntent, intent2);
                    return true;
                case 27:
                    startFingerprintAuthentication();
                    return true;
                case 28:
                    onFinishedBootAnim();
                    return true;
                case 29:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startedEarlyWakingUp(readInt6);
                    return true;
                case 30:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDexOccluded(readBoolean7);
                    return true;
                case 31:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCoverOccluded(readBoolean8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeyguardService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setOccluded(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardStateCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardExitCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onDreamingStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onDreamingStopped() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onStartedGoingToSleep(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedGoingToSleep(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onStartedWakingUp(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedWakingUp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardDrawnCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurnedOn() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurningOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onScreenTurnedOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setKeyguardEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onSystemReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void doKeyguardTimeout(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setSwitchingUser(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setCurrentUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onBootCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startKeyguardExitAnimation(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onShortPowerPressedGoHome() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void dismissKeyguardToLaunch(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onSystemKeyPressed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void showDismissibleKeyguard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startFingerprintAuthentication() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void onFinishedBootAnim() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void startedEarlyWakingUp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setDexOccluded(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.policy.IKeyguardService
            public void setCoverOccluded(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
