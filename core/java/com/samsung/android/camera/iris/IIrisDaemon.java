package com.samsung.android.camera.iris;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.RemoteException;
import com.samsung.android.camera.iris.IIrisDaemonCallback;

/* loaded from: classes5.dex */
public interface IIrisDaemon extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisDaemon";

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

    public static class Default implements IIrisDaemon {
        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int authenticate(long sessionId, int groupId, byte[] fidoRequestData) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelAuthentication() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int enroll(byte[] token, int groupId, int timeout) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelEnrollment() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long preEnroll() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int remove(int irisId, int groupId) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long getAuthenticatorId() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int setActiveGroup(int groupId, byte[] path) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public long openHal() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int closeHal() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void init(IIrisDaemonCallback callback) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int postEnroll() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int request(int cmd, byte[] inputBuf, byte[] outputBuf, int inParam) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void processIRImage(ParcelFileDescriptor fd, int width, int height, int rotation) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void sendIRProperty(String key, String currentValue, String minValue, String maxValue) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int enumerate() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int cancelEnumeration() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public void processFrontImage(ParcelFileDescriptor frontFd, int width, int height, int rotation) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int setPreviewTarget(IBinder binder) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public IBinder createInputSurface(int type, int width, int height) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int release(int cameraId) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemon
        public int releasePreviewSurface() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, IIrisDaemon.DESCRIPTOR);
        }

        public static IIrisDaemon asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIrisDaemon.DESCRIPTOR);
            if (iin != null && (iin instanceof IIrisDaemon)) {
                return (IIrisDaemon) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            byte[] _arg2;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IIrisDaemon.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IIrisDaemon.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            long _arg0 = data.readLong();
                            int _arg1 = data.readInt();
                            byte[] _arg22 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result = authenticate(_arg0, _arg1, _arg22);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case 2:
                            int _result2 = cancelAuthentication();
                            reply.writeNoException();
                            reply.writeInt(_result2);
                            return true;
                        case 3:
                            byte[] _arg02 = data.createByteArray();
                            int _arg12 = data.readInt();
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result3 = enroll(_arg02, _arg12, _arg23);
                            reply.writeNoException();
                            reply.writeInt(_result3);
                            return true;
                        case 4:
                            int _result4 = cancelEnrollment();
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 5:
                            long _result5 = preEnroll();
                            reply.writeNoException();
                            reply.writeLong(_result5);
                            return true;
                        case 6:
                            int _arg03 = data.readInt();
                            int _arg13 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result6 = remove(_arg03, _arg13);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 7:
                            long _result7 = getAuthenticatorId();
                            reply.writeNoException();
                            reply.writeLong(_result7);
                            return true;
                        case 8:
                            int _arg04 = data.readInt();
                            byte[] _arg14 = data.createByteArray();
                            data.enforceNoDataAvail();
                            int _result8 = setActiveGroup(_arg04, _arg14);
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 9:
                            long _result9 = openHal();
                            reply.writeNoException();
                            reply.writeLong(_result9);
                            return true;
                        case 10:
                            int _result10 = closeHal();
                            reply.writeNoException();
                            reply.writeInt(_result10);
                            return true;
                        case 11:
                            IIrisDaemonCallback _arg05 = IIrisDaemonCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            init(_arg05);
                            reply.writeNoException();
                            return true;
                        case 12:
                            int _result11 = postEnroll();
                            reply.writeNoException();
                            reply.writeInt(_result11);
                            return true;
                        case 13:
                            int _arg06 = data.readInt();
                            byte[] _arg15 = data.createByteArray();
                            int _arg2_length = data.readInt();
                            if (_arg2_length < 0) {
                                _arg2 = null;
                            } else {
                                _arg2 = new byte[_arg2_length];
                            }
                            int _arg3 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result12 = request(_arg06, _arg15, _arg2, _arg3);
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            reply.writeByteArray(_arg2);
                            return true;
                        case 14:
                            ParcelFileDescriptor _arg07 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            int _arg16 = data.readInt();
                            int _arg24 = data.readInt();
                            int _arg32 = data.readInt();
                            data.enforceNoDataAvail();
                            processIRImage(_arg07, _arg16, _arg24, _arg32);
                            reply.writeNoException();
                            return true;
                        case 15:
                            String _arg08 = data.readString();
                            String _arg17 = data.readString();
                            String _arg25 = data.readString();
                            String _arg33 = data.readString();
                            data.enforceNoDataAvail();
                            sendIRProperty(_arg08, _arg17, _arg25, _arg33);
                            reply.writeNoException();
                            return true;
                        case 16:
                            int _result13 = enumerate();
                            reply.writeNoException();
                            reply.writeInt(_result13);
                            return true;
                        case 17:
                            int _result14 = cancelEnumeration();
                            reply.writeNoException();
                            reply.writeInt(_result14);
                            return true;
                        case 18:
                            ParcelFileDescriptor _arg09 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                            int _arg18 = data.readInt();
                            int _arg26 = data.readInt();
                            int _arg34 = data.readInt();
                            data.enforceNoDataAvail();
                            processFrontImage(_arg09, _arg18, _arg26, _arg34);
                            reply.writeNoException();
                            return true;
                        case 19:
                            IBinder _arg010 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            int _result15 = setPreviewTarget(_arg010);
                            reply.writeNoException();
                            reply.writeInt(_result15);
                            return true;
                        case 20:
                            int _arg011 = data.readInt();
                            int _arg19 = data.readInt();
                            int _arg27 = data.readInt();
                            data.enforceNoDataAvail();
                            IBinder _result16 = createInputSurface(_arg011, _arg19, _arg27);
                            reply.writeNoException();
                            reply.writeStrongBinder(_result16);
                            return true;
                        case 21:
                            int _arg012 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result17 = release(_arg012);
                            reply.writeNoException();
                            reply.writeInt(_result17);
                            return true;
                        case 22:
                            int _result18 = releasePreviewSurface();
                            reply.writeNoException();
                            reply.writeInt(_result18);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IIrisDaemon {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisDaemon.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int authenticate(long sessionId, int groupId, byte[] fidoRequestData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeLong(sessionId);
                    _data.writeInt(groupId);
                    _data.writeByteArray(fidoRequestData);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelAuthentication() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enroll(byte[] token, int groupId, int timeout) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeByteArray(token);
                    _data.writeInt(groupId);
                    _data.writeInt(timeout);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnrollment() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long preEnroll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int remove(int irisId, int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeInt(irisId);
                    _data.writeInt(groupId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long getAuthenticatorId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setActiveGroup(int groupId, byte[] path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeByteArray(path);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public long openHal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int closeHal() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void init(IIrisDaemonCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int postEnroll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int request(int cmd, byte[] inputBuf, byte[] outputBuf, int inParam) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeByteArray(inputBuf);
                    _data.writeInt(outputBuf.length);
                    _data.writeInt(inParam);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(outputBuf);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processIRImage(ParcelFileDescriptor fd, int width, int height, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(rotation);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void sendIRProperty(String key, String currentValue, String minValue, String maxValue) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(currentValue);
                    _data.writeString(minValue);
                    _data.writeString(maxValue);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int enumerate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int cancelEnumeration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public void processFrontImage(ParcelFileDescriptor frontFd, int width, int height, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeTypedObject(frontFd, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(rotation);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int setPreviewTarget(IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public IBinder createInputSurface(int type, int width, int height) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int release(int cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    _data.writeInt(cameraId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemon
            public int releasePreviewSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIrisDaemon.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
