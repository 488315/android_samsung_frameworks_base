package com.android.internal.app;

import android.app.VoiceInteractor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVoiceInteractor extends IInterface {
    void notifyDirectActionsChanged(int i, IBinder iBinder) throws RemoteException;

    void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException;

    IVoiceInteractorRequest startAbortVoice(
            String str,
            IVoiceInteractorCallback iVoiceInteractorCallback,
            VoiceInteractor.Prompt prompt,
            Bundle bundle)
            throws RemoteException;

    IVoiceInteractorRequest startCommand(
            String str,
            IVoiceInteractorCallback iVoiceInteractorCallback,
            String str2,
            Bundle bundle)
            throws RemoteException;

    IVoiceInteractorRequest startCompleteVoice(
            String str,
            IVoiceInteractorCallback iVoiceInteractorCallback,
            VoiceInteractor.Prompt prompt,
            Bundle bundle)
            throws RemoteException;

    IVoiceInteractorRequest startConfirmation(
            String str,
            IVoiceInteractorCallback iVoiceInteractorCallback,
            VoiceInteractor.Prompt prompt,
            Bundle bundle)
            throws RemoteException;

    IVoiceInteractorRequest startPickOption(
            String str,
            IVoiceInteractorCallback iVoiceInteractorCallback,
            VoiceInteractor.Prompt prompt,
            VoiceInteractor.PickOptionRequest.Option[] optionArr,
            Bundle bundle)
            throws RemoteException;

    boolean[] supportsCommands(String str, String[] strArr) throws RemoteException;

    public static class Default implements IVoiceInteractor {
        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startConfirmation(
                String callingPackage,
                IVoiceInteractorCallback callback,
                VoiceInteractor.Prompt prompt,
                Bundle extras)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startPickOption(
                String callingPackage,
                IVoiceInteractorCallback callback,
                VoiceInteractor.Prompt prompt,
                VoiceInteractor.PickOptionRequest.Option[] options,
                Bundle extras)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startCompleteVoice(
                String callingPackage,
                IVoiceInteractorCallback callback,
                VoiceInteractor.Prompt prompt,
                Bundle extras)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startAbortVoice(
                String callingPackage,
                IVoiceInteractorCallback callback,
                VoiceInteractor.Prompt prompt,
                Bundle extras)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startCommand(
                String callingPackage,
                IVoiceInteractorCallback callback,
                String command,
                Bundle extras)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public boolean[] supportsCommands(String callingPackage, String[] commands)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public void notifyDirectActionsChanged(int taskId, IBinder assistToken)
                throws RemoteException {}

        @Override // com.android.internal.app.IVoiceInteractor
        public void setKillCallback(ICancellationSignal callback) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVoiceInteractor {
        public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractor";
        static final int TRANSACTION_notifyDirectActionsChanged = 7;
        static final int TRANSACTION_setKillCallback = 8;
        static final int TRANSACTION_startAbortVoice = 4;
        static final int TRANSACTION_startCommand = 5;
        static final int TRANSACTION_startCompleteVoice = 3;
        static final int TRANSACTION_startConfirmation = 1;
        static final int TRANSACTION_startPickOption = 2;
        static final int TRANSACTION_supportsCommands = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractor asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IVoiceInteractor)) {
                return (IVoiceInteractor) iin;
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
                    return "startConfirmation";
                case 2:
                    return "startPickOption";
                case 3:
                    return "startCompleteVoice";
                case 4:
                    return "startAbortVoice";
                case 5:
                    return "startCommand";
                case 6:
                    return "supportsCommands";
                case 7:
                    return "notifyDirectActionsChanged";
                case 8:
                    return "setKillCallback";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    IVoiceInteractorCallback _arg1 =
                            IVoiceInteractorCallback.Stub.asInterface(data.readStrongBinder());
                    VoiceInteractor.Prompt _arg2 =
                            (VoiceInteractor.Prompt)
                                    data.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle _arg3 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IVoiceInteractorRequest _result = startConfirmation(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    IVoiceInteractorCallback _arg12 =
                            IVoiceInteractorCallback.Stub.asInterface(data.readStrongBinder());
                    VoiceInteractor.Prompt _arg22 =
                            (VoiceInteractor.Prompt)
                                    data.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    VoiceInteractor.PickOptionRequest.Option[] _arg32 =
                            (VoiceInteractor.PickOptionRequest.Option[])
                                    data.createTypedArray(
                                            VoiceInteractor.PickOptionRequest.Option.CREATOR);
                    Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IVoiceInteractorRequest _result2 =
                            startPickOption(_arg02, _arg12, _arg22, _arg32, _arg4);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    IVoiceInteractorCallback _arg13 =
                            IVoiceInteractorCallback.Stub.asInterface(data.readStrongBinder());
                    VoiceInteractor.Prompt _arg23 =
                            (VoiceInteractor.Prompt)
                                    data.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle _arg33 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IVoiceInteractorRequest _result3 =
                            startCompleteVoice(_arg03, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    IVoiceInteractorCallback _arg14 =
                            IVoiceInteractorCallback.Stub.asInterface(data.readStrongBinder());
                    VoiceInteractor.Prompt _arg24 =
                            (VoiceInteractor.Prompt)
                                    data.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle _arg34 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IVoiceInteractorRequest _result4 =
                            startAbortVoice(_arg04, _arg14, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    IVoiceInteractorCallback _arg15 =
                            IVoiceInteractorCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg25 = data.readString();
                    Bundle _arg35 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    IVoiceInteractorRequest _result5 = startCommand(_arg05, _arg15, _arg25, _arg35);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    String[] _arg16 = data.createStringArray();
                    data.enforceNoDataAvail();
                    boolean[] _result6 = supportsCommands(_arg06, _arg16);
                    reply.writeNoException();
                    reply.writeBooleanArray(_result6);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    IBinder _arg17 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    notifyDirectActionsChanged(_arg07, _arg17);
                    reply.writeNoException();
                    return true;
                case 8:
                    ICancellationSignal _arg08 =
                            ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setKillCallback(_arg08);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVoiceInteractor {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startConfirmation(
                    String callingPackage,
                    IVoiceInteractorCallback callback,
                    VoiceInteractor.Prompt prompt,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedObject(prompt, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    IVoiceInteractorRequest _result =
                            IVoiceInteractorRequest.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startPickOption(
                    String callingPackage,
                    IVoiceInteractorCallback callback,
                    VoiceInteractor.Prompt prompt,
                    VoiceInteractor.PickOptionRequest.Option[] options,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedObject(prompt, 0);
                    _data.writeTypedArray(options, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IVoiceInteractorRequest _result =
                            IVoiceInteractorRequest.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startCompleteVoice(
                    String callingPackage,
                    IVoiceInteractorCallback callback,
                    VoiceInteractor.Prompt prompt,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedObject(prompt, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    IVoiceInteractorRequest _result =
                            IVoiceInteractorRequest.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startAbortVoice(
                    String callingPackage,
                    IVoiceInteractorCallback callback,
                    VoiceInteractor.Prompt prompt,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(callback);
                    _data.writeTypedObject(prompt, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    IVoiceInteractorRequest _result =
                            IVoiceInteractorRequest.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startCommand(
                    String callingPackage,
                    IVoiceInteractorCallback callback,
                    String command,
                    Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(callback);
                    _data.writeString(command);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    IVoiceInteractorRequest _result =
                            IVoiceInteractorRequest.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public boolean[] supportsCommands(String callingPackage, String[] commands)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStringArray(commands);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean[] _result = _reply.createBooleanArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void notifyDirectActionsChanged(int taskId, IBinder assistToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    _data.writeStrongBinder(assistToken);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void setKillCallback(ICancellationSignal callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
