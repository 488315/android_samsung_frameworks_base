package android.hardware.biometrics.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVirtualHal extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$biometrics$fingerprint$IVirtualHal".replace('$', '.');
    public static final String HASH = "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
    public static final int STATUS_INVALID_PARAMETER = 1;
    public static final int VERSION = 4;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

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

    void setNavigationGuesture(boolean z) throws RemoteException;

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

    public static class Default implements IVirtualHal {
        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setEnrollments(int[] id) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setEnrollmentHit(int hit_id) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setNextEnrollment(NextEnrollment next_enrollment) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setAuthenticatorId(long id) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setChallenge(long challenge) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationAuthenticateFails(boolean fail) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationAuthenticateLatency(int[] latencyMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationAuthenticateDuration(int durationMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationAuthenticateError(int error) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquired) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationEnrollError(int error) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationEnrollLatency(int[] latencyMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationDetectInteractionLatency(int[] latencyMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationDetectInteractionError(int error) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationDetectInteractionDuration(int durationMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setOperationDetectInteractionAcquired(AcquiredInfoAndVendorCode[] acquired) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setLockout(boolean lockout) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setLockoutEnable(boolean enable) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setLockoutTimedThreshold(int threshold) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setLockoutTimedDuration(int durationMs) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setLockoutPermanentThreshold(int threshold) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void resetConfigurations() throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setType(byte type) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setSensorId(int id) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setSensorStrength(byte strength) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setMaxEnrollmentPerUser(int max) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setSensorLocation(SensorLocation loc) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setNavigationGuesture(boolean v) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setDetectInteraction(boolean v) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setDisplayTouch(boolean v) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public void setControlIllumination(boolean v) throws RemoteException {
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.biometrics.fingerprint.IVirtualHal
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVirtualHal {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
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
        static final int TRANSACTION_setNavigationGuesture = 28;
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

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IVirtualHal asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualHal)) {
                return (IVirtualHal) iin;
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
                    return "setNavigationGuesture";
                case 29:
                    return "setDetectInteraction";
                case 30:
                    return "setDisplayTouch";
                case 31:
                    return "setControlIllumination";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int[] _arg0 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setEnrollments(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    setEnrollmentHit(_arg02);
                    return true;
                case 3:
                    NextEnrollment _arg03 = (NextEnrollment) data.readTypedObject(NextEnrollment.CREATOR);
                    data.enforceNoDataAvail();
                    setNextEnrollment(_arg03);
                    return true;
                case 4:
                    long _arg04 = data.readLong();
                    data.enforceNoDataAvail();
                    setAuthenticatorId(_arg04);
                    return true;
                case 5:
                    long _arg05 = data.readLong();
                    data.enforceNoDataAvail();
                    setChallenge(_arg05);
                    return true;
                case 6:
                    boolean _arg06 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setOperationAuthenticateFails(_arg06);
                    return true;
                case 7:
                    int[] _arg07 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setOperationAuthenticateLatency(_arg07);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    setOperationAuthenticateDuration(_arg08);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    setOperationAuthenticateError(_arg09);
                    return true;
                case 10:
                    AcquiredInfoAndVendorCode[] _arg010 = (AcquiredInfoAndVendorCode[]) data.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
                    data.enforceNoDataAvail();
                    setOperationAuthenticateAcquired(_arg010);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    setOperationEnrollError(_arg011);
                    return true;
                case 12:
                    int[] _arg012 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setOperationEnrollLatency(_arg012);
                    return true;
                case 13:
                    int[] _arg013 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setOperationDetectInteractionLatency(_arg013);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    setOperationDetectInteractionError(_arg014);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    setOperationDetectInteractionDuration(_arg015);
                    return true;
                case 16:
                    AcquiredInfoAndVendorCode[] _arg016 = (AcquiredInfoAndVendorCode[]) data.createTypedArray(AcquiredInfoAndVendorCode.CREATOR);
                    data.enforceNoDataAvail();
                    setOperationDetectInteractionAcquired(_arg016);
                    return true;
                case 17:
                    boolean _arg017 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLockout(_arg017);
                    return true;
                case 18:
                    boolean _arg018 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLockoutEnable(_arg018);
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    setLockoutTimedThreshold(_arg019);
                    return true;
                case 20:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    setLockoutTimedDuration(_arg020);
                    return true;
                case 21:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    setLockoutPermanentThreshold(_arg021);
                    return true;
                case 22:
                    resetConfigurations();
                    return true;
                case 23:
                    byte _arg022 = data.readByte();
                    data.enforceNoDataAvail();
                    setType(_arg022);
                    return true;
                case 24:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    setSensorId(_arg023);
                    return true;
                case 25:
                    byte _arg024 = data.readByte();
                    data.enforceNoDataAvail();
                    setSensorStrength(_arg024);
                    return true;
                case 26:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    setMaxEnrollmentPerUser(_arg025);
                    return true;
                case 27:
                    SensorLocation _arg026 = (SensorLocation) data.readTypedObject(SensorLocation.CREATOR);
                    data.enforceNoDataAvail();
                    setSensorLocation(_arg026);
                    return true;
                case 28:
                    boolean _arg027 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setNavigationGuesture(_arg027);
                    return true;
                case 29:
                    boolean _arg028 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDetectInteraction(_arg028);
                    return true;
                case 30:
                    boolean _arg029 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDisplayTouch(_arg029);
                    return true;
                case 31:
                    boolean _arg030 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setControlIllumination(_arg030);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualHal {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setEnrollments(int[] id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(id);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setEnrollments is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setEnrollmentHit(int hit_id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(hit_id);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setEnrollmentHit is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setNextEnrollment(NextEnrollment next_enrollment) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(next_enrollment, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setNextEnrollment is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setAuthenticatorId(long id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(id);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setAuthenticatorId is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setChallenge(long challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeLong(challenge);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setChallenge is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationAuthenticateFails(boolean fail) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(fail);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationAuthenticateFails is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationAuthenticateLatency(int[] latencyMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(latencyMs);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationAuthenticateLatency is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationAuthenticateDuration(int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(durationMs);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationAuthenticateDuration is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationAuthenticateError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(error);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationAuthenticateError is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationAuthenticateAcquired(AcquiredInfoAndVendorCode[] acquired) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedArray(acquired, 0);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationAuthenticateAcquired is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationEnrollError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(error);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationEnrollError is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationEnrollLatency(int[] latencyMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(latencyMs);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationEnrollLatency is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationDetectInteractionLatency(int[] latencyMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(latencyMs);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationDetectInteractionLatency is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationDetectInteractionError(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(error);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationDetectInteractionError is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationDetectInteractionDuration(int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(durationMs);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationDetectInteractionDuration is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setOperationDetectInteractionAcquired(AcquiredInfoAndVendorCode[] acquired) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedArray(acquired, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setOperationDetectInteractionAcquired is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setLockout(boolean lockout) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(lockout);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setLockout is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setLockoutEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(enable);
                    boolean _status = this.mRemote.transact(18, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setLockoutEnable is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setLockoutTimedThreshold(int threshold) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(threshold);
                    boolean _status = this.mRemote.transact(19, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setLockoutTimedThreshold is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setLockoutTimedDuration(int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(durationMs);
                    boolean _status = this.mRemote.transact(20, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setLockoutTimedDuration is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setLockoutPermanentThreshold(int threshold) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(threshold);
                    boolean _status = this.mRemote.transact(21, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setLockoutPermanentThreshold is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void resetConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = this.mRemote.transact(22, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method resetConfigurations is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setType(byte type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(type);
                    boolean _status = this.mRemote.transact(23, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setType is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setSensorId(int id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(id);
                    boolean _status = this.mRemote.transact(24, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSensorId is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setSensorStrength(byte strength) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(strength);
                    boolean _status = this.mRemote.transact(25, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSensorStrength is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setMaxEnrollmentPerUser(int max) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(max);
                    boolean _status = this.mRemote.transact(26, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setMaxEnrollmentPerUser is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setSensorLocation(SensorLocation loc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeTypedObject(loc, 0);
                    boolean _status = this.mRemote.transact(27, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setSensorLocation is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setNavigationGuesture(boolean v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(v);
                    boolean _status = this.mRemote.transact(28, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setNavigationGuesture is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setDetectInteraction(boolean v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(v);
                    boolean _status = this.mRemote.transact(29, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDetectInteraction is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setDisplayTouch(boolean v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(v);
                    boolean _status = this.mRemote.transact(30, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setDisplayTouch is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public void setControlIllumination(boolean v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(v);
                    boolean _status = this.mRemote.transact(31, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method setControlIllumination is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.biometrics.fingerprint.IVirtualHal
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
