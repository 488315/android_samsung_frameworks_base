package com.samsung.android.camera.iris;

import android.hardware.biometrics.IBiometricSensorReceiver;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback;
import com.samsung.android.camera.iris.IIrisServiceReceiver;
import java.util.List;

/* loaded from: classes6.dex */
public interface IIrisService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisService";

    public static class Default implements IIrisService {
        @Override // com.samsung.android.camera.iris.IIrisService
        public void addLockoutResetCallback(IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelAuthentication(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelAuthenticationFromService(IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void cancelEnrollment(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void enableIRImageCallback(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void enroll(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, byte[] bArr, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public long getAuthenticatorId(String str) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public List<Iris> getEnrolledIrises(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean hasDisabledIris(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean hasEnrolledIrises(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public boolean isHardwareDetected(long j, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public int postEnroll(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public long preEnroll(IBinder iBinder) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, int i2, int i3, int i4, int i5, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void prompt_authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void remove(IBinder iBinder, int i, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void rename(int i, int i2, String str) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public int request(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void resetTimeout(byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void setActiveUser(int i) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void setIrisViewType(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisService
        public void startPreparedClient(int i) throws RemoteException {
        }
    }

    void addLockoutResetCallback(IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallback) throws RemoteException;

    void authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str) throws RemoteException;

    void cancelAuthenticationFromService(IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void cancelEnrollment(IBinder iBinder) throws RemoteException;

    void enableIRImageCallback(int i, String str, int i2) throws RemoteException;

    void enroll(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, byte[] bArr, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle) throws RemoteException;

    long getAuthenticatorId(String str) throws RemoteException;

    List<Iris> getEnrolledIrises(int i, String str) throws RemoteException;

    boolean hasDisabledIris(int i, String str) throws RemoteException;

    boolean hasEnrolledIrises(int i, String str) throws RemoteException;

    boolean isHardwareDetected(long j, String str) throws RemoteException;

    int postEnroll(IBinder iBinder) throws RemoteException;

    long preEnroll(IBinder iBinder) throws RemoteException;

    void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, int i2, int i3, int i4, int i5, Bundle bundle) throws RemoteException;

    void prompt_authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException;

    void remove(IBinder iBinder, int i, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException;

    void rename(int i, int i2, String str) throws RemoteException;

    int request(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException;

    void resetTimeout(byte[] bArr) throws RemoteException;

    void setActiveUser(int i) throws RemoteException;

    void setIrisViewType(int i, String str, int i2) throws RemoteException;

    void startPreparedClient(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IIrisService {
        static final int TRANSACTION_addLockoutResetCallback = 17;
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_cancelAuthentication = 3;
        static final int TRANSACTION_cancelAuthenticationFromService = 23;
        static final int TRANSACTION_cancelEnrollment = 5;
        static final int TRANSACTION_enableIRImageCallback = 19;
        static final int TRANSACTION_enroll = 4;
        static final int TRANSACTION_getAuthenticatorId = 14;
        static final int TRANSACTION_getEnrolledIrises = 8;
        static final int TRANSACTION_hasDisabledIris = 13;
        static final int TRANSACTION_hasEnrolledIrises = 12;
        static final int TRANSACTION_isHardwareDetected = 9;
        static final int TRANSACTION_postEnroll = 11;
        static final int TRANSACTION_preEnroll = 10;
        static final int TRANSACTION_prepareForAuthentication = 21;
        static final int TRANSACTION_prompt_authenticate = 2;
        static final int TRANSACTION_remove = 6;
        static final int TRANSACTION_rename = 7;
        static final int TRANSACTION_request = 15;
        static final int TRANSACTION_resetTimeout = 16;
        static final int TRANSACTION_setActiveUser = 20;
        static final int TRANSACTION_setIrisViewType = 18;
        static final int TRANSACTION_startPreparedClient = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub() {
            attachInterface(this, IIrisService.DESCRIPTOR);
        }

        public static IIrisService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIrisService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIrisService)) {
                return (IIrisService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authenticate";
                case 2:
                    return "prompt_authenticate";
                case 3:
                    return "cancelAuthentication";
                case 4:
                    return "enroll";
                case 5:
                    return "cancelEnrollment";
                case 6:
                    return "remove";
                case 7:
                    return "rename";
                case 8:
                    return "getEnrolledIrises";
                case 9:
                    return "isHardwareDetected";
                case 10:
                    return "preEnroll";
                case 11:
                    return "postEnroll";
                case 12:
                    return "hasEnrolledIrises";
                case 13:
                    return "hasDisabledIris";
                case 14:
                    return "getAuthenticatorId";
                case 15:
                    return "request";
                case 16:
                    return "resetTimeout";
                case 17:
                    return "addLockoutResetCallback";
                case 18:
                    return "setIrisViewType";
                case 19:
                    return "enableIRImageCallback";
                case 20:
                    return "setActiveUser";
                case 21:
                    return "prepareForAuthentication";
                case 22:
                    return "startPreparedClient";
                case 23:
                    return "cancelAuthenticationFromService";
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
                parcel.enforceInterface(IIrisService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIrisService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    IIrisServiceReceiver asInterface = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    authenticate(readStrongBinder, readStrongBinder2, readInt, readInt2, readInt3, readInt4, readLong, readInt5, asInterface, readInt6, readString, bundle, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt11 = parcel.readInt();
                    IIrisServiceReceiver asInterface2 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    String readString2 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    prompt_authenticate(readStrongBinder3, readStrongBinder4, readInt7, readInt8, readInt9, readInt10, readLong2, readInt11, asInterface2, readInt12, readString2, bundle2, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder5, readString3);
                    parcel2.writeNoException();
                    break;
                case 4:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt17 = parcel.readInt();
                    IIrisServiceReceiver asInterface3 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    String readString4 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    enroll(readStrongBinder6, readStrongBinder7, readInt13, readInt14, readInt15, readInt16, createByteArray3, readInt17, asInterface3, readInt18, readString4, bundle3);
                    parcel2.writeNoException();
                    break;
                case 5:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(readStrongBinder8);
                    parcel2.writeNoException();
                    break;
                case 6:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    IIrisServiceReceiver asInterface4 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder9, readInt19, readInt20, readInt21, asInterface4);
                    parcel2.writeNoException();
                    break;
                case 7:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rename(readInt22, readInt23, readString5);
                    parcel2.writeNoException();
                    break;
                case 8:
                    int readInt24 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Iris> enrolledIrises = getEnrolledIrises(readInt24, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledIrises, 1);
                    break;
                case 9:
                    long readLong3 = parcel.readLong();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readLong3, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    break;
                case 10:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    long preEnroll = preEnroll(readStrongBinder10);
                    parcel2.writeNoException();
                    parcel2.writeLong(preEnroll);
                    break;
                case 11:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int postEnroll = postEnroll(readStrongBinder11);
                    parcel2.writeNoException();
                    parcel2.writeInt(postEnroll);
                    break;
                case 12:
                    int readInt25 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledIrises = hasEnrolledIrises(readInt25, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledIrises);
                    break;
                case 13:
                    int readInt26 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDisabledIris = hasDisabledIris(readInt26, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDisabledIris);
                    break;
                case 14:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readString10);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    break;
                case 15:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt27 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt28 = parcel.readInt();
                    if (readInt28 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt28);
                    }
                    byte[] bArr = readInt28 < 0 ? null : new byte[readInt28];
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    IIrisServiceReceiver asInterface5 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int request = request(readStrongBinder12, readInt27, createByteArray4, bArr, readInt29, readInt30, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(request);
                    parcel2.writeByteArray(bArr);
                    break;
                case 16:
                    byte[] createByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetTimeout(createByteArray5);
                    parcel2.writeNoException();
                    break;
                case 17:
                    IIrisServiceLockoutResetCallback asInterface6 = IIrisServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(asInterface6);
                    parcel2.writeNoException();
                    break;
                case 18:
                    int readInt31 = parcel.readInt();
                    String readString11 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIrisViewType(readInt31, readString11, readInt32);
                    parcel2.writeNoException();
                    break;
                case 19:
                    int readInt33 = parcel.readInt();
                    String readString12 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIRImageCallback(readInt33, readString12, readInt34);
                    parcel2.writeNoException();
                    break;
                case 20:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setActiveUser(readInt35);
                    parcel2.writeNoException();
                    break;
                case 21:
                    boolean readBoolean = parcel.readBoolean();
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    long readLong4 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    IBiometricSensorReceiver asInterface7 = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readBoolean, readStrongBinder13, readLong4, readInt36, asInterface7, readString13, readInt37, readInt38, readInt39, readInt40, bundle4);
                    parcel2.writeNoException();
                    break;
                case 22:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(readInt41);
                    parcel2.writeNoException();
                    break;
                case 23:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    String readString14 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readStrongBinder14, readString14, readInt42, readInt43, readInt44, readBoolean2);
                    parcel2.writeNoException();
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIrisService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisService.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j);
                    obtain.writeInt(i5);
                    obtain.writeStrongInterface(iIrisServiceReceiver);
                    obtain.writeInt(i6);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prompt_authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeLong(j);
                    obtain.writeInt(i5);
                    obtain.writeStrongInterface(iIrisServiceReceiver);
                    obtain.writeInt(i6);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthentication(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enroll(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, byte[] bArr, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i5);
                    obtain.writeStrongInterface(iIrisServiceReceiver);
                    obtain.writeInt(i6);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelEnrollment(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void remove(IBinder iBinder, int i, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iIrisServiceReceiver);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void rename(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public List<Iris> getEnrolledIrises(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Iris.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean isHardwareDetected(long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long preEnroll(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int postEnroll(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasEnrolledIrises(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasDisabledIris(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long getAuthenticatorId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int request(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iIrisServiceReceiver);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void resetTimeout(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void addLockoutResetCallback(IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongInterface(iIrisServiceLockoutResetCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setIrisViewType(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enableIRImageCallback(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setActiveUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, int i2, int i3, int i4, int i5, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void startPreparedClient(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthenticationFromService(IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
