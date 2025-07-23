package android.hardware.biometrics.face.virtualhal;

import android.hardware.biometrics.face.IFace;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVirtualHal extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.face.virtualhal.IVirtualHal";
    public static final int STATUS_INVALID_PARAMETER = 1;

    public static class Default implements IVirtualHal {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public IFace getFaceHal() throws RemoteException {
            return null;
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void resetConfigurations() throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setAuthenticatorId(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setChallenge(long j) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setEnrollmentHit(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setEnrollments(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockout(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockoutEnable(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockoutPermanentThreshold(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockoutTimedDuration(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockoutTimedEnable(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setLockoutTimedThreshold(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationAuthenticateDuration(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationAuthenticateError(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationAuthenticateFails(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationAuthenticateLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationDetectInteractionFails(boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setOperationEnrollLatency(int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setSensorStrength(byte b) throws RemoteException {
        }

        @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
        public void setType(byte b) throws RemoteException {
        }
    }

    IFace getFaceHal() throws RemoteException;

    void resetConfigurations() throws RemoteException;

    void setAuthenticatorId(long j) throws RemoteException;

    void setChallenge(long j) throws RemoteException;

    void setEnrollmentHit(int i) throws RemoteException;

    void setEnrollments(int[] iArr) throws RemoteException;

    void setLockout(boolean z) throws RemoteException;

    void setLockoutEnable(boolean z) throws RemoteException;

    void setLockoutPermanentThreshold(int i) throws RemoteException;

    void setLockoutTimedDuration(int i) throws RemoteException;

    void setLockoutTimedEnable(boolean z) throws RemoteException;

    void setLockoutTimedThreshold(int i) throws RemoteException;

    void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException;

    void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException;

    void setOperationAuthenticateDuration(int i) throws RemoteException;

    void setOperationAuthenticateError(int i) throws RemoteException;

    void setOperationAuthenticateFails(boolean z) throws RemoteException;

    void setOperationAuthenticateLatency(int[] iArr) throws RemoteException;

    void setOperationDetectInteractionFails(boolean z) throws RemoteException;

    void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException;

    void setOperationEnrollLatency(int[] iArr) throws RemoteException;

    void setSensorStrength(byte b) throws RemoteException;

    void setType(byte b) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualHal {
        static final int TRANSACTION_getFaceHal = 23;
        static final int TRANSACTION_resetConfigurations = 20;
        static final int TRANSACTION_setAuthenticatorId = 4;
        static final int TRANSACTION_setChallenge = 5;
        static final int TRANSACTION_setEnrollmentHit = 2;
        static final int TRANSACTION_setEnrollments = 1;
        static final int TRANSACTION_setLockout = 14;
        static final int TRANSACTION_setLockoutEnable = 15;
        static final int TRANSACTION_setLockoutPermanentThreshold = 19;
        static final int TRANSACTION_setLockoutTimedDuration = 18;
        static final int TRANSACTION_setLockoutTimedEnable = 16;
        static final int TRANSACTION_setLockoutTimedThreshold = 17;
        static final int TRANSACTION_setNextEnrollment = 3;
        static final int TRANSACTION_setOperationAuthenticateAcquired = 10;
        static final int TRANSACTION_setOperationAuthenticateDuration = 8;
        static final int TRANSACTION_setOperationAuthenticateError = 9;
        static final int TRANSACTION_setOperationAuthenticateFails = 6;
        static final int TRANSACTION_setOperationAuthenticateLatency = 7;
        static final int TRANSACTION_setOperationDetectInteractionFails = 13;
        static final int TRANSACTION_setOperationDetectInteractionLatency = 12;
        static final int TRANSACTION_setOperationEnrollLatency = 11;
        static final int TRANSACTION_setSensorStrength = 22;
        static final int TRANSACTION_setType = 21;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
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
                    return "setOperationEnrollLatency";
                case 12:
                    return "setOperationDetectInteractionLatency";
                case 13:
                    return "setOperationDetectInteractionFails";
                case 14:
                    return "setLockout";
                case 15:
                    return "setLockoutEnable";
                case 16:
                    return "setLockoutTimedEnable";
                case 17:
                    return "setLockoutTimedThreshold";
                case 18:
                    return "setLockoutTimedDuration";
                case 19:
                    return "setLockoutPermanentThreshold";
                case 20:
                    return "resetConfigurations";
                case 21:
                    return "setType";
                case 22:
                    return "setSensorStrength";
                case 23:
                    return "getFaceHal";
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
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationEnrollLatency(createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionLatency(createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionFails(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockout(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockoutEnable(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedEnable(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedThreshold(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedDuration(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutPermanentThreshold(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    resetConfigurations();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setType(readByte);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setSensorStrength(readByte2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IFace faceHal = getFaceHal();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(faceHal);
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationEnrollLatency(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException {
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationDetectInteractionFails(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockout(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedThreshold(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedDuration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutPermanentThreshold(int i) throws RemoteException {
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

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void resetConfigurations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setType(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setSensorStrength(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public IFace getFaceHal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return IFace.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
