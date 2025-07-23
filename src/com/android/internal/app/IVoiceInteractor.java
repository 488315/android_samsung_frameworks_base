package com.android.internal.app;

import android.app.VoiceInteractor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;

/* loaded from: classes5.dex */
public interface IVoiceInteractor extends IInterface {

    public static class Default implements IVoiceInteractor {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public void notifyDirectActionsChanged(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException {
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startAbortVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startCommand(String str, IVoiceInteractorCallback iVoiceInteractorCallback, String str2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startCompleteVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startConfirmation(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public IVoiceInteractorRequest startPickOption(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.app.IVoiceInteractor
        public boolean[] supportsCommands(String str, String[] strArr) throws RemoteException {
            return null;
        }
    }

    void notifyDirectActionsChanged(int i, IBinder iBinder) throws RemoteException;

    void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException;

    IVoiceInteractorRequest startAbortVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException;

    IVoiceInteractorRequest startCommand(String str, IVoiceInteractorCallback iVoiceInteractorCallback, String str2, Bundle bundle) throws RemoteException;

    IVoiceInteractorRequest startCompleteVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException;

    IVoiceInteractorRequest startConfirmation(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException;

    IVoiceInteractorRequest startPickOption(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException;

    boolean[] supportsCommands(String str, String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractor {
        public static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractor";
        static final int TRANSACTION_notifyDirectActionsChanged = 7;
        static final int TRANSACTION_setKillCallback = 8;
        static final int TRANSACTION_startAbortVoice = 4;
        static final int TRANSACTION_startCommand = 5;
        static final int TRANSACTION_startCompleteVoice = 3;
        static final int TRANSACTION_startConfirmation = 1;
        static final int TRANSACTION_startPickOption = 2;
        static final int TRANSACTION_supportsCommands = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractor)) {
                return (IVoiceInteractor) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    IVoiceInteractorCallback asInterface = IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    VoiceInteractor.Prompt prompt = (VoiceInteractor.Prompt) parcel.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractorRequest startConfirmation = startConfirmation(readString, asInterface, prompt, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startConfirmation);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    IVoiceInteractorCallback asInterface2 = IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    VoiceInteractor.Prompt prompt2 = (VoiceInteractor.Prompt) parcel.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    VoiceInteractor.PickOptionRequest.Option[] optionArr = (VoiceInteractor.PickOptionRequest.Option[]) parcel.createTypedArray(VoiceInteractor.PickOptionRequest.Option.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractorRequest startPickOption = startPickOption(readString2, asInterface2, prompt2, optionArr, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startPickOption);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    IVoiceInteractorCallback asInterface3 = IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    VoiceInteractor.Prompt prompt3 = (VoiceInteractor.Prompt) parcel.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractorRequest startCompleteVoice = startCompleteVoice(readString3, asInterface3, prompt3, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startCompleteVoice);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    IVoiceInteractorCallback asInterface4 = IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    VoiceInteractor.Prompt prompt4 = (VoiceInteractor.Prompt) parcel.readTypedObject(VoiceInteractor.Prompt.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractorRequest startAbortVoice = startAbortVoice(readString4, asInterface4, prompt4, bundle4);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startAbortVoice);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    IVoiceInteractorCallback asInterface5 = IVoiceInteractorCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString6 = parcel.readString();
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    IVoiceInteractorRequest startCommand = startCommand(readString5, asInterface5, readString6, bundle5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(startCommand);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    boolean[] supportsCommands = supportsCommands(readString7, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeBooleanArray(supportsCommands);
                    return true;
                case 7:
                    int readInt = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    notifyDirectActionsChanged(readInt, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ICancellationSignal asInterface6 = ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setKillCallback(asInterface6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractor {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startConfirmation(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startPickOption(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] optionArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedArray(optionArr, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startCompleteVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startAbortVoice(String str, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeTypedObject(prompt, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public IVoiceInteractorRequest startCommand(String str, IVoiceInteractorCallback iVoiceInteractorCallback, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoiceInteractorCallback);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoiceInteractorRequest.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public boolean[] supportsCommands(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createBooleanArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void notifyDirectActionsChanged(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void setKillCallback(ICancellationSignal iCancellationSignal) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
