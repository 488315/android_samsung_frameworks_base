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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAugmentedAutofillService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAugmentedAutofillService)) {
                return (IAugmentedAutofillService) queryLocalInterface;
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
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onConnected(readBoolean, readBoolean2);
            } else if (i == 2) {
                onDisconnected();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                IBinder readStrongBinder = parcel.readStrongBinder();
                int readInt2 = parcel.readInt();
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                AutofillValue autofillValue = (AutofillValue) parcel.readTypedObject(AutofillValue.CREATOR);
                long readLong = parcel.readLong();
                InlineSuggestionsRequest inlineSuggestionsRequest = (InlineSuggestionsRequest) parcel.readTypedObject(InlineSuggestionsRequest.CREATOR);
                IFillCallback asInterface = IFillCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onFillRequest(readInt, readStrongBinder, readInt2, componentName, autofillId, autofillValue, readLong, inlineSuggestionsRequest, asInterface);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDisconnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onFillRequest(int i, IBinder iBinder, int i2, ComponentName componentName, AutofillId autofillId, AutofillValue autofillValue, long j, InlineSuggestionsRequest inlineSuggestionsRequest, IFillCallback iFillCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(inlineSuggestionsRequest, 0);
                    obtain.writeStrongInterface(iFillCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IAugmentedAutofillService
            public void onDestroyAllFillWindowsRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAugmentedAutofillService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
