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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIrisDaemon.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIrisDaemon)) {
                return (IIrisDaemon) queryLocalInterface;
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
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int authenticate = authenticate(readLong, readInt, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(authenticate);
                    return true;
                case 2:
                    int cancelAuthentication = cancelAuthentication();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelAuthentication);
                    return true;
                case 3:
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enroll = enroll(createByteArray2, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(enroll);
                    return true;
                case 4:
                    int cancelEnrollment = cancelEnrollment();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelEnrollment);
                    return true;
                case 5:
                    long preEnroll = preEnroll();
                    parcel2.writeNoException();
                    parcel2.writeLong(preEnroll);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remove = remove(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(remove);
                    return true;
                case 7:
                    long authenticatorId = getAuthenticatorId();
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int activeGroup = setActiveGroup(readInt6, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeGroup);
                    return true;
                case 9:
                    long openHal = openHal();
                    parcel2.writeNoException();
                    parcel2.writeLong(openHal);
                    return true;
                case 10:
                    int closeHal = closeHal();
                    parcel2.writeNoException();
                    parcel2.writeInt(closeHal);
                    return true;
                case 11:
                    IIrisDaemonCallback asInterface = IIrisDaemonCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    init(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int postEnroll = postEnroll();
                    parcel2.writeNoException();
                    parcel2.writeInt(postEnroll);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    if (readInt8 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt8);
                    }
                    byte[] bArr = readInt8 < 0 ? null : new byte[readInt8];
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int request = request(readInt7, createByteArray4, bArr, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(request);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 14:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    processIRImage(parcelFileDescriptor, readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendIRProperty(readString, readString2, readString3, readString4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int enumerate = enumerate();
                    parcel2.writeNoException();
                    parcel2.writeInt(enumerate);
                    return true;
                case 17:
                    int cancelEnumeration = cancelEnumeration();
                    parcel2.writeNoException();
                    parcel2.writeInt(cancelEnumeration);
                    return true;
                case 18:
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    processFrontImage(parcelFileDescriptor2, readInt13, readInt14, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int previewTarget = setPreviewTarget(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(previewTarget);
                    return true;
                case 20:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder createInputSurface = createInputSurface(readInt16, readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(createInputSurface);
                    return true;
                case 21:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int release = release(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(release);
                    return true;
                case 22:
                    int releasePreviewSurface = releasePreviewSurface();
                    parcel2.writeNoException();
                    parcel2.writeInt(releasePreviewSurface);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelAuthentication() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enroll(byte[] bArr, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnrollment() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long preEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int remove(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long getAuthenticatorId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setActiveGroup(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long openHal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int closeHal() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void init(IIrisDaemonCallback iIrisDaemonCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeStrongInterface(iIrisDaemonCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int postEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int request(int i, byte[] bArr, byte[] bArr2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processIRImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void sendIRProperty(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enumerate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnumeration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processFrontImage(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setPreviewTarget(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public IBinder createInputSurface(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int release(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int releasePreviewSurface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
