package android.service.voice;

import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IVoiceActionCheckCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IVoiceInteractionService extends IInterface {

    public static class Default implements IVoiceInteractionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void detectorRemoteExceptionOccurred(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void launchVoiceAssistFromKeyguard() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void prepareToShowSession(Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void ready() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void showSessionFailed(Bundle bundle) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void shutdown() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionService
        public void soundModelsChanged() throws RemoteException {
        }
    }

    void detectorRemoteExceptionOccurred(IBinder iBinder, int i) throws RemoteException;

    void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException;

    void launchVoiceAssistFromKeyguard() throws RemoteException;

    void prepareToShowSession(Bundle bundle, int i) throws RemoteException;

    void ready() throws RemoteException;

    void showSessionFailed(Bundle bundle) throws RemoteException;

    void shutdown() throws RemoteException;

    void soundModelsChanged() throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionService {
        public static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionService";
        static final int TRANSACTION_detectorRemoteExceptionOccurred = 8;
        static final int TRANSACTION_getActiveServiceSupportedActions = 5;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 4;
        static final int TRANSACTION_prepareToShowSession = 6;
        static final int TRANSACTION_ready = 1;
        static final int TRANSACTION_showSessionFailed = 7;
        static final int TRANSACTION_shutdown = 3;
        static final int TRANSACTION_soundModelsChanged = 2;

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

        public static IVoiceInteractionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionService)) {
                return (IVoiceInteractionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "ready";
                case 2:
                    return "soundModelsChanged";
                case 3:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 4:
                    return "launchVoiceAssistFromKeyguard";
                case 5:
                    return "getActiveServiceSupportedActions";
                case 6:
                    return "prepareToShowSession";
                case 7:
                    return "showSessionFailed";
                case 8:
                    return "detectorRemoteExceptionOccurred";
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
                    ready();
                    return true;
                case 2:
                    soundModelsChanged();
                    return true;
                case 3:
                    shutdown();
                    return true;
                case 4:
                    launchVoiceAssistFromKeyguard();
                    return true;
                case 5:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    IVoiceActionCheckCallback asInterface = IVoiceActionCheckCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getActiveServiceSupportedActions(createStringArrayList, asInterface);
                    return true;
                case 6:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareToShowSession(bundle, readInt);
                    return true;
                case 7:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showSessionFailed(bundle2);
                    return true;
                case 8:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    detectorRemoteExceptionOccurred(readStrongBinder, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractionService {
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

            @Override // android.service.voice.IVoiceInteractionService
            public void ready() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void soundModelsChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void shutdown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iVoiceActionCheckCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void prepareToShowSession(Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void showSessionFailed(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionService
            public void detectorRemoteExceptionOccurred(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
