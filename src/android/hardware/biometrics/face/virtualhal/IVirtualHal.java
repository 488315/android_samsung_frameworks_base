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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualHal.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualHal)) {
                return (IVirtualHal) iInterfaceQueryLocalInterface;
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
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEnrollments(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEnrollmentHit(i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    NextEnrollment nextEnrollment = (NextEnrollment) parcel.readTypedObject(NextEnrollment.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNextEnrollment(nextEnrollment);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAuthenticatorId(j);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setChallenge(j2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateFails(z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateLatency(iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateDuration(i4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateError(i5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr = (AcquiredInfoAndVendorCode[]) parcel.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOperationAuthenticateAcquired(acquiredInfoAndVendorCodeArr);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationEnrollLatency(iArrCreateIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionLatency(iArrCreateIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOperationDetectInteractionFails(z2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockout(z3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockoutEnable(z4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedEnable(z5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedThreshold(i6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutTimedDuration(i7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockoutPermanentThreshold(i8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    resetConfigurations();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setType(b);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    byte b2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setSensorStrength(b2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setEnrollmentHit(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setNextEnrollment(NextEnrollment nextEnrollment) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeTypedObject(nextEnrollment, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setAuthenticatorId(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setChallenge(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationAuthenticateFails(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationAuthenticateLatency(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationAuthenticateDuration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationAuthenticateError(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquiredInfoAndVendorCodeArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeTypedArray(acquiredInfoAndVendorCodeArr, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationEnrollLatency(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationDetectInteractionLatency(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setOperationDetectInteractionFails(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockout(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedThreshold(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutTimedDuration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setLockoutPermanentThreshold(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void resetConfigurations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setType(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public void setSensorStrength(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.face.virtualhal.IVirtualHal
            public IFace getFaceHal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualHal.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IFace.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
