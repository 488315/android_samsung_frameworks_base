package android.view.translation;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.autofill.AutofillId;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ITranslationManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.translation.ITranslationManager";

    public static class Default implements ITranslationManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.translation.ITranslationManager
        public void getServiceSettingsActivity(IResultReceiver iResultReceiver, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void onSessionCreated(TranslationContext translationContext, int i, IResultReceiver iResultReceiver, int i2) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver, int i3) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void onTranslationFinished(boolean z, IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void registerTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void registerUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void unregisterTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void unregisterUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
        }

        @Override // android.view.translation.ITranslationManager
        public void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, IBinder iBinder, int i2, UiTranslationSpec uiTranslationSpec, int i3) throws RemoteException {
        }
    }

    void getServiceSettingsActivity(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void onSessionCreated(TranslationContext translationContext, int i, IResultReceiver iResultReceiver, int i2) throws RemoteException;

    void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver, int i3) throws RemoteException;

    void onTranslationFinished(boolean z, IBinder iBinder, ComponentName componentName, int i) throws RemoteException;

    void registerTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void registerUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void unregisterTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void unregisterUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException;

    void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, IBinder iBinder, int i2, UiTranslationSpec uiTranslationSpec, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements ITranslationManager {
        static final int TRANSACTION_getServiceSettingsActivity = 8;
        static final int TRANSACTION_onSessionCreated = 4;
        static final int TRANSACTION_onTranslationCapabilitiesRequest = 1;
        static final int TRANSACTION_onTranslationFinished = 9;
        static final int TRANSACTION_registerTranslationCapabilityCallback = 2;
        static final int TRANSACTION_registerUiTranslationStateCallback = 6;
        static final int TRANSACTION_unregisterTranslationCapabilityCallback = 3;
        static final int TRANSACTION_unregisterUiTranslationStateCallback = 7;
        static final int TRANSACTION_updateUiTranslationState = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ITranslationManager.DESCRIPTOR);
        }

        public static ITranslationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITranslationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITranslationManager)) {
                return (ITranslationManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTranslationCapabilitiesRequest";
                case 2:
                    return "registerTranslationCapabilityCallback";
                case 3:
                    return "unregisterTranslationCapabilityCallback";
                case 4:
                    return "onSessionCreated";
                case 5:
                    return "updateUiTranslationState";
                case 6:
                    return "registerUiTranslationStateCallback";
                case 7:
                    return "unregisterUiTranslationStateCallback";
                case 8:
                    return "getServiceSettingsActivity";
                case 9:
                    return "onTranslationFinished";
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
                parcel.enforceInterface(ITranslationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITranslationManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationCapabilitiesRequest(readInt, readInt2, resultReceiver, readInt3);
                    return true;
                case 2:
                    IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTranslationCapabilityCallback(asInterface, readInt4);
                    return true;
                case 3:
                    IRemoteCallback asInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTranslationCapabilityCallback(asInterface2, readInt5);
                    return true;
                case 4:
                    TranslationContext translationContext = (TranslationContext) parcel.readTypedObject(TranslationContext.CREATOR);
                    int readInt6 = parcel.readInt();
                    IResultReceiver asInterface3 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(translationContext, readInt6, asInterface3, readInt7);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    TranslationSpec translationSpec = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    TranslationSpec translationSpec2 = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt9 = parcel.readInt();
                    UiTranslationSpec uiTranslationSpec = (UiTranslationSpec) parcel.readTypedObject(UiTranslationSpec.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateUiTranslationState(readInt8, translationSpec, translationSpec2, createTypedArrayList, readStrongBinder, readInt9, uiTranslationSpec, readInt10);
                    return true;
                case 6:
                    IRemoteCallback asInterface4 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTranslationStateCallback(asInterface4, readInt11);
                    return true;
                case 7:
                    IRemoteCallback asInterface5 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterUiTranslationStateCallback(asInterface5, readInt12);
                    return true;
                case 8:
                    IResultReceiver asInterface6 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(asInterface6, readInt13);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationFinished(readBoolean, readStrongBinder2, componentName, readInt14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITranslationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITranslationManager.DESCRIPTOR;
            }

            @Override // android.view.translation.ITranslationManager
            public void onTranslationCapabilitiesRequest(int i, int i2, ResultReceiver resultReceiver, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onSessionCreated(TranslationContext translationContext, int i, IResultReceiver iResultReceiver, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeTypedObject(translationContext, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, IBinder iBinder, int i2, UiTranslationSpec uiTranslationSpec, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(translationSpec, 0);
                    obtain.writeTypedObject(translationSpec2, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uiTranslationSpec, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void getServiceSettingsActivity(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onTranslationFinished(boolean z, IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
