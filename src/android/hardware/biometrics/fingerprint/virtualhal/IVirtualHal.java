package android.hardware.biometrics.fingerprint.virtualhal;

import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.biometrics.fingerprint.SensorLocation;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVirtualHal extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal";
    public static final int STATUS_INVALID_PARAMETER = 1;

    public static class Default implements IVirtualHal {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public IFingerprint getFingerprintHal() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void resetConfigurations() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setAuthenticatorId(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setChallenge(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setControlIllumination(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setDetectInteraction(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setDisplayTouch(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setEnrollmentHit(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setEnrollments(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setLockout(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setLockoutEnable(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setLockoutPermanentThreshold(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setLockoutTimedDuration(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setLockoutTimedThreshold(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setMaxEnrollmentPerUser(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setNavigationGesture(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationAuthenticateDuration(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationAuthenticateError(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationAuthenticateFails(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationAuthenticateLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationDetectInteractionAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationDetectInteractionDuration(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationDetectInteractionError(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationEnrollError(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setOperationEnrollLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setSensorId(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setSensorLocation(SensorLocation sensorLocation) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setSensorStrength(byte b) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
        public void setType(byte b) throws RemoteException {
        }
    }

    IFingerprint getFingerprintHal() throws RemoteException;

    void resetConfigurations() throws RemoteException;

    void setAuthenticatorId(long j) throws RemoteException;

    void setChallenge(long j) throws RemoteException;

    void setControlIllumination(boolean z) throws RemoteException;

    void setDetectInteraction(boolean z) throws RemoteException;

    void setDisplayTouch(boolean z) throws RemoteException;

    void setEnrollmentHit(int i) throws RemoteException;

    void setEnrollments(int[] iArr) throws RemoteException;

    void setLockout(boolean z) throws RemoteException;

    void setLockoutEnable(boolean z) throws RemoteException;

    void setLockoutPermanentThreshold(int i) throws RemoteException;

    void setLockoutTimedDuration(int i) throws RemoteException;

    void setLockoutTimedThreshold(int i) throws RemoteException;

    void setMaxEnrollmentPerUser(int i) throws RemoteException;

    void setNavigationGesture(boolean z) throws RemoteException;

    void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException;

    void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException;

    void setOperationAuthenticateDuration(int i) throws RemoteException;

    void setOperationAuthenticateError(int i) throws RemoteException;

    void setOperationAuthenticateFails(boolean z) throws RemoteException;

    void setOperationAuthenticateLatency(int[] iArr) throws RemoteException;

    void setOperationDetectInteractionAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException;

    void setOperationDetectInteractionDuration(int i) throws RemoteException;

    void setOperationDetectInteractionError(int i) throws RemoteException;

    void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException;

    void setOperationEnrollError(int i) throws RemoteException;

    void setOperationEnrollLatency(int[] iArr) throws RemoteException;

    void setSensorId(int i) throws RemoteException;

    void setSensorLocation(SensorLocation sensorLocation) throws RemoteException;

    void setSensorStrength(byte b) throws RemoteException;

    void setType(byte b) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualHal {
        static final int TRANSACTION_getFingerprintHal = 32;
        static final int TRANSACTION_resetConfigurations = 22;
        static final int TRANSACTION_setAuthenticatorId = 4;
        static final int TRANSACTION_setChallenge = 5;
        static final int TRANSACTION_setControlIllumination = 31;
        static final int TRANSACTION_setDetectInteraction = 29;
        static final int TRANSACTION_setDisplayTouch = 30;
        static final int TRANSACTION_setEnrollmentHit = 2;
        static final int TRANSACTION_setEnrollments = 1;
        static final int TRANSACTION_setLockout = 17;
        static final int TRANSACTION_setLockoutEnable = 18;
        static final int TRANSACTION_setLockoutPermanentThreshold = 21;
        static final int TRANSACTION_setLockoutTimedDuration = 20;
        static final int TRANSACTION_setLockoutTimedThreshold = 19;
        static final int TRANSACTION_setMaxEnrollmentPerUser = 26;
        static final int TRANSACTION_setNavigationGesture = 28;
        static final int TRANSACTION_setNextEnrollment = 3;
        static final int TRANSACTION_setOperationAuthenticateAcquired = 10;
        static final int TRANSACTION_setOperationAuthenticateDuration = 8;
        static final int TRANSACTION_setOperationAuthenticateError = 9;
        static final int TRANSACTION_setOperationAuthenticateFails = 6;
        static final int TRANSACTION_setOperationAuthenticateLatency = 7;
        static final int TRANSACTION_setOperationDetectInteractionAcquired = 16;
        static final int TRANSACTION_setOperationDetectInteractionDuration = 15;
        static final int TRANSACTION_setOperationDetectInteractionError = 14;
        static final int TRANSACTION_setOperationDetectInteractionLatency = 13;
        static final int TRANSACTION_setOperationEnrollError = 11;
        static final int TRANSACTION_setOperationEnrollLatency = 12;
        static final int TRANSACTION_setSensorId = 24;
        static final int TRANSACTION_setSensorLocation = 27;
        static final int TRANSACTION_setSensorStrength = 25;
        static final int TRANSACTION_setType = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }

        public Stub() {
            attachInterface(this, IVirtualHal.DESCRIPTOR);
        }

        public static IVirtualHal asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualHal.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualHal)) {
                return (IVirtualHal) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setEnrollments";
                case 2:
                    return "setEnrollmentHit";
                case 3:
                    return "setNextEnrollment";
                case 4:
                    return "setAuthenticatorId";
                case 5:
                    return "setChallenge";
                case 6:
                    return "setOperationAuthenticateFails";
                case 7:
                    return "setOperationAuthenticateLatency";
                case 8:
                    return "setOperationAuthenticateDuration";
                case 9:
                    return "setOperationAuthenticateError";
                case 10:
                    return "setOperationAuthenticateAcquired";
                case 11:
                    return "setOperationEnrollError";
                case 12:
                    return "setOperationEnrollLatency";
                case 13:
                    return "setOperationDetectInteractionLatency";
                case 14:
                    return "setOperationDetectInteractionError";
                case 15:
                    return "setOperationDetectInteractionDuration";
                case 16:
                    return "setOperationDetectInteractionAcquired";
                case 17:
                    return "setLockout";
                case 18:
                    return "setLockoutEnable";
                case 19:
                    return "setLockoutTimedThreshold";
                case 20:
                    return "setLockoutTimedDuration";
                case 21:
                    return "setLockoutPermanentThreshold";
                case 22:
                    return "resetConfigurations";
                case 23:
                    return "setType";
                case 24:
                    return "setSensorId";
                case 25:
                    return "setSensorStrength";
                case 26:
                    return "setMaxEnrollmentPerUser";
                case 27:
                    return "setSensorLocation";
                case 28:
                    return "setNavigationGesture";
                case 29:
                    return "setDetectInteraction";
                case 30:
                    return "setDisplayTouch";
                case 31:
                    return "setControlIllumination";
                case 32:
                    return "getFingerprintHal";
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
                parcel.enforceInterface(IVirtualHal.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualHal.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEnrollments(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEnrollmentHit(readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    NextEnrollment nextEnrollment = (NextEnrollment) parcel.readTypedObject(NextEnrollment.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNextEnrollment(nextEnrollment);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAuthenticatorId(readLong);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setChallenge(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateFails(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateLatency(createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateDuration(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateError(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr = (AcquiredInfoAndVendorCode[]) parcel.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateAcquired(acquiredInfoAndVendorCodeArr);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationEnrollError(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationEnrollLatency(createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionLatency(createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionError(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionDuration(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr2 = (AcquiredInfoAndVendorCode[]) parcel.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionAcquired(acquiredInfoAndVendorCodeArr2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockout(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockoutEnable(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedThreshold(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedDuration(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutPermanentThreshold(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    resetConfigurations();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setType(readByte);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSensorId(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setSensorStrength(readByte2);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaxEnrollmentPerUser(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    SensorLocation sensorLocation = (SensorLocation) parcel.readTypedObject(SensorLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSensorLocation(sensorLocation);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationGesture(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDetectInteraction(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisplayTouch(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setControlIllumination(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IFingerprint fingerprintHal = getFingerprintHal();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(fingerprintHal);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVirtualHal {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualHal.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setEnrollments(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setEnrollmentHit(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeTypedObject(nextEnrollment, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setAuthenticatorId(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setChallenge(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationAuthenticateFails(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationAuthenticateLatency(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationAuthenticateDuration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationAuthenticateError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeTypedArray(acquiredInfoAndVendorCodeArr, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationEnrollError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationEnrollLatency(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationDetectInteractionError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationDetectInteractionDuration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setOperationDetectInteractionAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeTypedArray(acquiredInfoAndVendorCodeArr, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setLockout(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setLockoutEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setLockoutTimedThreshold(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setLockoutTimedDuration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setLockoutPermanentThreshold(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void resetConfigurations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setType(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setSensorId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setSensorStrength(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setMaxEnrollmentPerUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setSensorLocation(SensorLocation sensorLocation) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeTypedObject(sensorLocation, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setNavigationGesture(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setDetectInteraction(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setDisplayTouch(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public void setControlIllumination(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.virtualhal.IVirtualHal
            public IFingerprint getFingerprintHal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return IFingerprint.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
