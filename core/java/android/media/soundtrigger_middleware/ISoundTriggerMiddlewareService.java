package android.media.soundtrigger_middleware;

import android.media.permission.Identity;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerMiddlewareService extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService";

    ISoundTriggerModule attachAsMiddleman(int i, Identity identity, Identity identity2, ISoundTriggerCallback iSoundTriggerCallback, boolean z) throws RemoteException;

    ISoundTriggerModule attachAsOriginator(int i, Identity identity, ISoundTriggerCallback iSoundTriggerCallback) throws RemoteException;

    void attachFakeHalInjection(ISoundTriggerInjection iSoundTriggerInjection) throws RemoteException;

    SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity identity, Identity identity2) throws RemoteException;

    SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException;

    public static class Default implements ISoundTriggerMiddlewareService {
        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity middlemanIdentity, Identity originatorIdentity) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public ISoundTriggerModule attachAsOriginator(int handle, Identity identity, ISoundTriggerCallback callback) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public ISoundTriggerModule attachAsMiddleman(int handle, Identity middlemanIdentity, Identity originatorIdentity, ISoundTriggerCallback callback, boolean isTrusted) throws RemoteException {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
        public void attachFakeHalInjection(ISoundTriggerInjection injection) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerMiddlewareService {
        static final int TRANSACTION_attachAsMiddleman = 4;
        static final int TRANSACTION_attachAsOriginator = 3;
        static final int TRANSACTION_attachFakeHalInjection = 5;
        static final int TRANSACTION_listModulesAsMiddleman = 2;
        static final int TRANSACTION_listModulesAsOriginator = 1;

        public Stub() {
            attachInterface(this, ISoundTriggerMiddlewareService.DESCRIPTOR);
        }

        public static ISoundTriggerMiddlewareService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISoundTriggerMiddlewareService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundTriggerMiddlewareService)) {
                return (ISoundTriggerMiddlewareService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISoundTriggerMiddlewareService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISoundTriggerMiddlewareService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Identity _arg0 = (Identity) data.readTypedObject(Identity.CREATOR);
                    data.enforceNoDataAvail();
                    SoundTriggerModuleDescriptor[] _result = listModulesAsOriginator(_arg0);
                    reply.writeNoException();
                    reply.writeTypedArray(_result, 1);
                    return true;
                case 2:
                    Identity _arg02 = (Identity) data.readTypedObject(Identity.CREATOR);
                    Identity _arg1 = (Identity) data.readTypedObject(Identity.CREATOR);
                    data.enforceNoDataAvail();
                    SoundTriggerModuleDescriptor[] _result2 = listModulesAsMiddleman(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeTypedArray(_result2, 1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    Identity _arg12 = (Identity) data.readTypedObject(Identity.CREATOR);
                    ISoundTriggerCallback _arg2 = ISoundTriggerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    ISoundTriggerModule _result3 = attachAsOriginator(_arg03, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    Identity _arg13 = (Identity) data.readTypedObject(Identity.CREATOR);
                    Identity _arg22 = (Identity) data.readTypedObject(Identity.CREATOR);
                    ISoundTriggerCallback _arg3 = ISoundTriggerCallback.Stub.asInterface(data.readStrongBinder());
                    boolean _arg4 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ISoundTriggerModule _result4 = attachAsMiddleman(_arg04, _arg13, _arg22, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 5:
                    ISoundTriggerInjection _arg05 = ISoundTriggerInjection.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    attachFakeHalInjection(_arg05);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISoundTriggerMiddlewareService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundTriggerMiddlewareService.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public SoundTriggerModuleDescriptor[] listModulesAsOriginator(Identity identity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    _data.writeTypedObject(identity, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    SoundTriggerModuleDescriptor[] _result = (SoundTriggerModuleDescriptor[]) _reply.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public SoundTriggerModuleDescriptor[] listModulesAsMiddleman(Identity middlemanIdentity, Identity originatorIdentity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    _data.writeTypedObject(middlemanIdentity, 0);
                    _data.writeTypedObject(originatorIdentity, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    SoundTriggerModuleDescriptor[] _result = (SoundTriggerModuleDescriptor[]) _reply.createTypedArray(SoundTriggerModuleDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsOriginator(int handle, Identity identity, ISoundTriggerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    _data.writeInt(handle);
                    _data.writeTypedObject(identity, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ISoundTriggerModule _result = ISoundTriggerModule.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public ISoundTriggerModule attachAsMiddleman(int handle, Identity middlemanIdentity, Identity originatorIdentity, ISoundTriggerCallback callback, boolean isTrusted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    _data.writeInt(handle);
                    _data.writeTypedObject(middlemanIdentity, 0);
                    _data.writeTypedObject(originatorIdentity, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeBoolean(isTrusted);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ISoundTriggerModule _result = ISoundTriggerModule.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService
            public void attachFakeHalInjection(ISoundTriggerInjection injection) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundTriggerMiddlewareService.DESCRIPTOR);
                    _data.writeStrongInterface(injection);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
