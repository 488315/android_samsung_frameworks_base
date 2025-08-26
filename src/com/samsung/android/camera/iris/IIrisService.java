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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIrisService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIrisService)) {
                return (IIrisService) iInterfaceQueryLocalInterface;
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    long j = parcel.readLong();
                    int i7 = parcel.readInt();
                    IIrisServiceReceiver iIrisServiceReceiverAsInterface = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    authenticate(strongBinder, strongBinder2, i3, i4, i5, i6, j, i7, iIrisServiceReceiverAsInterface, i8, string, bundle, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    long j2 = parcel.readLong();
                    int i13 = parcel.readInt();
                    IIrisServiceReceiver iIrisServiceReceiverAsInterface2 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i14 = parcel.readInt();
                    String string2 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    prompt_authenticate(strongBinder3, strongBinder4, i9, i10, i11, i12, j2, i13, iIrisServiceReceiverAsInterface2, i14, string2, bundle2, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(strongBinder5, string3);
                    parcel2.writeNoException();
                    break;
                case 4:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i19 = parcel.readInt();
                    IIrisServiceReceiver iIrisServiceReceiverAsInterface3 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i20 = parcel.readInt();
                    String string4 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    enroll(strongBinder6, strongBinder7, i15, i16, i17, i18, bArrCreateByteArray3, i19, iIrisServiceReceiverAsInterface3, i20, string4, bundle3);
                    parcel2.writeNoException();
                    break;
                case 5:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(strongBinder8);
                    parcel2.writeNoException();
                    break;
                case 6:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    IIrisServiceReceiver iIrisServiceReceiverAsInterface4 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    remove(strongBinder9, i21, i22, i23, iIrisServiceReceiverAsInterface4);
                    parcel2.writeNoException();
                    break;
                case 7:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rename(i24, i25, string5);
                    parcel2.writeNoException();
                    break;
                case 8:
                    int i26 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Iris> enrolledIrises = getEnrolledIrises(i26, string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledIrises, 1);
                    break;
                case 9:
                    long j3 = parcel.readLong();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHardwareDetected = isHardwareDetected(j3, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareDetected);
                    break;
                case 10:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    long jPreEnroll = preEnroll(strongBinder10);
                    parcel2.writeNoException();
                    parcel2.writeLong(jPreEnroll);
                    break;
                case 11:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int iPostEnroll = postEnroll(strongBinder11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPostEnroll);
                    break;
                case 12:
                    int i27 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledIrises = hasEnrolledIrises(i27, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledIrises);
                    break;
                case 13:
                    int i28 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasDisabledIris = hasDisabledIris(i28, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDisabledIris);
                    break;
                case 14:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(string10);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    break;
                case 15:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i29 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    int i30 = parcel.readInt();
                    if (i30 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i30);
                    }
                    byte[] bArr = i30 < 0 ? null : new byte[i30];
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    IIrisServiceReceiver iIrisServiceReceiverAsInterface5 = IIrisServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iRequest = request(strongBinder12, i29, bArrCreateByteArray4, bArr, i31, i32, iIrisServiceReceiverAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequest);
                    parcel2.writeByteArray(bArr);
                    break;
                case 16:
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    resetTimeout(bArrCreateByteArray5);
                    parcel2.writeNoException();
                    break;
                case 17:
                    IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallbackAsInterface = IIrisServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(iIrisServiceLockoutResetCallbackAsInterface);
                    parcel2.writeNoException();
                    break;
                case 18:
                    int i33 = parcel.readInt();
                    String string11 = parcel.readString();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIrisViewType(i33, string11, i34);
                    parcel2.writeNoException();
                    break;
                case 19:
                    int i35 = parcel.readInt();
                    String string12 = parcel.readString();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableIRImageCallback(i35, string12, i36);
                    parcel2.writeNoException();
                    break;
                case 20:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setActiveUser(i37);
                    parcel2.writeNoException();
                    break;
                case 21:
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    long j4 = parcel.readLong();
                    int i38 = parcel.readInt();
                    IBiometricSensorReceiver iBiometricSensorReceiverAsInterface = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(z, strongBinder13, j4, i38, iBiometricSensorReceiverAsInterface, string13, i39, i40, i41, i42, bundle4);
                    parcel2.writeNoException();
                    break;
                case 22:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(i43);
                    parcel2.writeNoException();
                    break;
                case 23:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    String string14 = parcel.readString();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(strongBinder14, string14, i44, i45, i46, z2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeStrongInterface(iIrisServiceReceiver);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prompt_authenticate(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, long j, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeStrongInterface(iIrisServiceReceiver);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthentication(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enroll(IBinder iBinder, IBinder iBinder2, int i, int i2, int i3, int i4, byte[] bArr, int i5, IIrisServiceReceiver iIrisServiceReceiver, int i6, String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeStrongInterface(iIrisServiceReceiver);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelEnrollment(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void remove(IBinder iBinder, int i, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iIrisServiceReceiver);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void rename(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public List<Iris> getEnrolledIrises(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Iris.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean isHardwareDetected(long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long preEnroll(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int postEnroll(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasEnrolledIrises(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public boolean hasDisabledIris(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public long getAuthenticatorId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public int request(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, IIrisServiceReceiver iIrisServiceReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iIrisServiceReceiver);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i4 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i4;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void resetTimeout(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void addLockoutResetCallback(IIrisServiceLockoutResetCallback iIrisServiceLockoutResetCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIrisServiceLockoutResetCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setIrisViewType(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void enableIRImageCallback(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void setActiveUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void prepareForAuthentication(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, int i2, int i3, int i4, int i5, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBiometricSensorReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void startPreparedClient(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisService
            public void cancelAuthenticationFromService(IBinder iBinder, String str, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
