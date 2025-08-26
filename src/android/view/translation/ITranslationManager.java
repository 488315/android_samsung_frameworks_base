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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITranslationManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITranslationManager)) {
                return (ITranslationManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationCapabilitiesRequest(i3, i4, resultReceiver, i5);
                    return true;
                case 2:
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTranslationCapabilityCallback(iRemoteCallbackAsInterface, i6);
                    return true;
                case 3:
                    IRemoteCallback iRemoteCallbackAsInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTranslationCapabilityCallback(iRemoteCallbackAsInterface2, i7);
                    return true;
                case 4:
                    TranslationContext translationContext = (TranslationContext) parcel.readTypedObject(TranslationContext.CREATOR);
                    int i8 = parcel.readInt();
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(translationContext, i8, iResultReceiverAsInterface, i9);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    TranslationSpec translationSpec = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    TranslationSpec translationSpec2 = (TranslationSpec) parcel.readTypedObject(TranslationSpec.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i11 = parcel.readInt();
                    UiTranslationSpec uiTranslationSpec = (UiTranslationSpec) parcel.readTypedObject(UiTranslationSpec.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateUiTranslationState(i10, translationSpec, translationSpec2, arrayListCreateTypedArrayList, strongBinder, i11, uiTranslationSpec, i12);
                    return true;
                case 6:
                    IRemoteCallback iRemoteCallbackAsInterface3 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTranslationStateCallback(iRemoteCallbackAsInterface3, i13);
                    return true;
                case 7:
                    IRemoteCallback iRemoteCallbackAsInterface4 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterUiTranslationStateCallback(iRemoteCallbackAsInterface4, i14);
                    return true;
                case 8:
                    IResultReceiver iResultReceiverAsInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getServiceSettingsActivity(iResultReceiverAsInterface2, i15);
                    return true;
                case 9:
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTranslationFinished(z, strongBinder2, componentName, i16);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterTranslationCapabilityCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onSessionCreated(TranslationContext translationContext, int i, IResultReceiver iResultReceiver, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(translationContext, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void updateUiTranslationState(int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, List<AutofillId> list, IBinder iBinder, int i2, UiTranslationSpec uiTranslationSpec, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(translationSpec, 0);
                    parcelObtain.writeTypedObject(translationSpec2, 0);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(uiTranslationSpec, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void registerUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void unregisterUiTranslationStateCallback(IRemoteCallback iRemoteCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void getServiceSettingsActivity(IResultReceiver iResultReceiver, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.translation.ITranslationManager
            public void onTranslationFinished(boolean z, IBinder iBinder, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITranslationManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
