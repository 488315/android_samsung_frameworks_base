package android.service.autofill.augmented;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.augmented.IFillCallback;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.InlineSuggestionsRequest;

/* loaded from: classes3.dex */
public interface IAugmentedAutofillService extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.augmented.IAugmentedAutofillService";

    public static class Default implements IAugmentedAutofillService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onConnected(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDestroyAllFillWindowsRequest() throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) throws RemoteException {
        }
    }

    void onConnected(boolean z, boolean z2) throws RemoteException;

    void onDestroyAllFillWindowsRequest() throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAugmentedAutofillService {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDestroyAllFillWindowsRequest = 4;
        static final int TRANSACTION_onDisconnected = 2;
        static final int TRANSACTION_onFillRequest = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IAugmentedAutofillService.DESCRIPTOR);
        }

        public static IAugmentedAutofillService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAugmentedAutofillService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAugmentedAutofillService)) {
                return (IAugmentedAutofillService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnected";
            }
            if (i == 2) {
                return "onDisconnected";
            }
            if (i == 3) {
                return "onFillRequest";
            }
            if (i != 4) {
                return null;
            }
            return "onDestroyAllFillWindowsRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAugmentedAutofillService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAugmentedAutofillService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onConnected(z, z2);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                IBinder strongBinder = parcel.readStrongBinder();
                int i4 = parcel.readInt();
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                AutofillValue autofillValue = (AutofillValue) parcel.readTypedObject(AutofillValue.CREATOR);
                long j = parcel.readLong();
                InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) parcel.readTypedObject(InlineSuggestionsRequest.CREATOR);
                IFillCallback iFillCallbackAsInterface = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onFillRequest(i3, strongBinder, i4, componentName, autofillId, autofillValue, j, inlineSuggestionsRequest, iFillCallbackAsInterface);
            } else if (i == 4) {
                onDestroyAllFillWindowsRequest();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAugmentedAutofillService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAugmentedAutofillService.DESCRIPTOR;
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onConnected(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeTypedObject(autofillValue, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(inlineSuggestionsRequest, 0);
                    parcelObtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDestroyAllFillWindowsRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
