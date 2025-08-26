package com.samsung.android.camera.iris;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.camera.iris.IIrisDaemonCallback;

/* loaded from: classes6.dex */
public interface IIrisDaemon extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisDaemon";

    public static class Default implements IIrisDaemon {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int authenticate(long j, int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelAuthentication() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelEnrollment() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelEnumeration() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int closeHal() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public IBinder createInputSurface(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int enroll(byte[] bArr, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int enumerate() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long getAuthenticatorId() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void init(IIrisDaemonCallback iIrisDaemonCallback) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long openHal() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int postEnroll() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long preEnroll() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void processFrontImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void processIRImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int release(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int releasePreviewSurface() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int remove(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int request(int i, byte[] bArr, byte[] bArr2, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void sendIRProperty(String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int setActiveGroup(int i, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int setPreviewTarget(IBinder iBinder) throws RemoteException {
            return 0;
        }
    }

    int authenticate(long j, int i, byte[] bArr) throws RemoteException;

    int cancelAuthentication() throws RemoteException;

    int cancelEnrollment() throws RemoteException;

    int cancelEnumeration() throws RemoteException;

    int closeHal() throws RemoteException;

    IBinder createInputSurface(int i, int i2, int i3) throws RemoteException;

    int enroll(byte[] bArr, int i, int i2) throws RemoteException;

    int enumerate() throws RemoteException;

    long getAuthenticatorId() throws RemoteException;

    void init(IIrisDaemonCallback iIrisDaemonCallback) throws RemoteException;

    long openHal() throws RemoteException;

    int postEnroll() throws RemoteException;

    long preEnroll() throws RemoteException;

    void processFrontImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException;

    void processIRImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException;

    int release(int i) throws RemoteException;

    int releasePreviewSurface() throws RemoteException;

    int remove(int i, int i2) throws RemoteException;

    int request(int i, byte[] bArr, byte[] bArr2, int i2) throws RemoteException;

    void sendIRProperty(String str, String str2, String str3, String str4) throws RemoteException;

    int setActiveGroup(int i, byte[] bArr) throws RemoteException;

    int setPreviewTarget(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IIrisDaemon {
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_cancelAuthentication = 2;
        static final int TRANSACTION_cancelEnrollment = 4;
        static final int TRANSACTION_cancelEnumeration = 17;
        static final int TRANSACTION_closeHal = 10;
        static final int TRANSACTION_createInputSurface = 20;
        static final int TRANSACTION_enroll = 3;
        static final int TRANSACTION_enumerate = 16;
        static final int TRANSACTION_getAuthenticatorId = 7;
        static final int TRANSACTION_init = 11;
        static final int TRANSACTION_openHal = 9;
        static final int TRANSACTION_postEnroll = 12;
        static final int TRANSACTION_preEnroll = 5;
        static final int TRANSACTION_processFrontImage = 18;
        static final int TRANSACTION_processIRImage = 14;
        static final int TRANSACTION_release = 21;
        static final int TRANSACTION_releasePreviewSurface = 22;
        static final int TRANSACTION_remove = 6;
        static final int TRANSACTION_request = 13;
        static final int TRANSACTION_sendIRProperty = 15;
        static final int TRANSACTION_setActiveGroup = 8;
        static final int TRANSACTION_setPreviewTarget = 19;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, IIrisDaemon.DESCRIPTOR);
        }

        public static IIrisDaemon asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIrisDaemon.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIrisDaemon)) {
                return (IIrisDaemon) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authenticate";
                case 2:
                    return "cancelAuthentication";
                case 3:
                    return "enroll";
                case 4:
                    return "cancelEnrollment";
                case 5:
                    return "preEnroll";
                case 6:
                    return "remove";
                case 7:
                    return "getAuthenticatorId";
                case 8:
                    return "setActiveGroup";
                case 9:
                    return "openHal";
                case 10:
                    return "closeHal";
                case 11:
                    return "init";
                case 12:
                    return "postEnroll";
                case 13:
                    return "request";
                case 14:
                    return "processIRImage";
                case 15:
                    return "sendIRProperty";
                case 16:
                    return "enumerate";
                case 17:
                    return "cancelEnumeration";
                case 18:
                    return "processFrontImage";
                case 19:
                    return "setPreviewTarget";
                case 20:
                    return "createInputSurface";
                case 21:
                    return "release";
                case 22:
                    return "releasePreviewSurface";
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
                parcel.enforceInterface(IIrisDaemon.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIrisDaemon.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iAuthenticate = authenticate(j, i3, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAuthenticate);
                    return true;
                case 2:
                    int iCancelAuthentication = cancelAuthentication();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelAuthentication);
                    return true;
                case 3:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iEnroll = enroll(bArrCreateByteArray2, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnroll);
                    return true;
                case 4:
                    int iCancelEnrollment = cancelEnrollment();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelEnrollment);
                    return true;
                case 5:
                    long jPreEnroll = preEnroll();
                    parcel2.writeNoException();
                    parcel2.writeLong(jPreEnroll);
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRemove = remove(i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemove);
                    return true;
                case 7:
                    long authenticatorId = getAuthenticatorId();
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int activeGroup = setActiveGroup(i8, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeGroup);
                    return true;
                case 9:
                    long jOpenHal = openHal();
                    parcel2.writeNoException();
                    parcel2.writeLong(jOpenHal);
                    return true;
                case 10:
                    int iCloseHal = closeHal();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCloseHal);
                    return true;
                case 11:
                    IIrisDaemonCallback iIrisDaemonCallbackAsInterface = IIrisDaemonCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    init(iIrisDaemonCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int iPostEnroll = postEnroll();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPostEnroll);
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    int i10 = parcel.readInt();
                    if (i10 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i10);
                    }
                    byte[] bArr = i10 < 0 ? null : new byte[i10];
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRequest = request(i9, bArrCreateByteArray4, bArr, i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRequest);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 14:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    processIRImage(parcelFileDescriptor, i12, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendIRProperty(string, string2, string3, string4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int iEnumerate = enumerate();
                    parcel2.writeNoException();
                    parcel2.writeInt(iEnumerate);
                    return true;
                case 17:
                    int iCancelEnumeration = cancelEnumeration();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCancelEnumeration);
                    return true;
                case 18:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    processFrontImage(parcelFileDescriptor2, i15, i16, i17);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int previewTarget = setPreviewTarget(strongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(previewTarget);
                    return true;
                case 20:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder iBinderCreateInputSurface = createInputSurface(i18, i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderCreateInputSurface);
                    return true;
                case 21:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iRelease = release(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRelease);
                    return true;
                case 22:
                    int iReleasePreviewSurface = releasePreviewSurface();
                    parcel2.writeNoException();
                    parcel2.writeInt(iReleasePreviewSurface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIrisDaemon {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisDaemon.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int authenticate(long j, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelAuthentication() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enroll(byte[] bArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnrollment() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long preEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int remove(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long getAuthenticatorId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setActiveGroup(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long openHal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int closeHal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void init(IIrisDaemonCallback iIrisDaemonCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIrisDaemonCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int postEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int request(int i, byte[] bArr, byte[] bArr2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i3 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i3;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processIRImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void sendIRProperty(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enumerate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnumeration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processFrontImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setPreviewTarget(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public IBinder createInputSurface(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int release(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int releasePreviewSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
