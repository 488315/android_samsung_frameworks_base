package android.view.autofill;

import android.app.assist.AssistStructure;
import android.content.Context;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.autofill.IAutofillWindowPresenter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAugmentedAutofillManagerClient extends IInterface {
    public static final String DESCRIPTOR = "android.view.autofill.IAugmentedAutofillManagerClient";

    public static class Default implements IAugmentedAutofillManagerClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public Rect getViewCoordinates(AutofillId autofillId) throws RemoteException {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public AssistStructure.ViewNodeParcelable getViewNodeParcelable(AutofillId autofillId) throws RemoteException {
            return null;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public boolean requestAutofill(int i, AutofillId autofillId) throws RemoteException {
            return false;
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAugmentedAutofillManagerClient
        public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
        }
    }

    void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException;

    Rect getViewCoordinates(AutofillId autofillId) throws RemoteException;

    AssistStructure.ViewNodeParcelable getViewNodeParcelable(AutofillId autofillId) throws RemoteException;

    boolean requestAutofill(int i, AutofillId autofillId) throws RemoteException;

    void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException;

    void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException;

    public static abstract class Stub extends Binder implements IAugmentedAutofillManagerClient {
        static final int TRANSACTION_autofill = 3;
        static final int TRANSACTION_getViewCoordinates = 1;
        static final int TRANSACTION_getViewNodeParcelable = 2;
        static final int TRANSACTION_requestAutofill = 6;
        static final int TRANSACTION_requestHideFillUi = 5;
        static final int TRANSACTION_requestShowFillUi = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IAugmentedAutofillManagerClient.DESCRIPTOR);
        }

        public static IAugmentedAutofillManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAugmentedAutofillManagerClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAugmentedAutofillManagerClient)) {
                return (IAugmentedAutofillManagerClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getViewCoordinates";
                case 2:
                    return "getViewNodeParcelable";
                case 3:
                    return Context.AUTOFILL_MANAGER_SERVICE;
                case 4:
                    return "requestShowFillUi";
                case 5:
                    return "requestHideFillUi";
                case 6:
                    return "requestAutofill";
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
                parcel.enforceInterface(IAugmentedAutofillManagerClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAugmentedAutofillManagerClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect viewCoordinates = getViewCoordinates(autofillId);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(viewCoordinates, 1);
                    return true;
                case 2:
                    AutofillId autofillId2 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    AssistStructure.ViewNodeParcelable viewNodeParcelable = getViewNodeParcelable(autofillId2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(viewNodeParcelable, 1);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AutofillValue.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    autofill(i3, arrayListCreateTypedArrayList, arrayListCreateTypedArrayList2, z);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    AutofillId autofillId3 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IAutofillWindowPresenter iAutofillWindowPresenterAsInterface = IAutofillWindowPresenter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestShowFillUi(i4, autofillId3, i5, i6, rect, iAutofillWindowPresenterAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    AutofillId autofillId4 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUi(i7, autofillId4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    AutofillId autofillId5 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestAutofill = requestAutofill(i8, autofillId5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestAutofill);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAugmentedAutofillManagerClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAugmentedAutofillManagerClient.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public Rect getViewCoordinates(AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public AssistStructure.ViewNodeParcelable getViewNodeParcelable(AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AssistStructure.ViewNodeParcelable) parcelObtain2.readTypedObject(AssistStructure.ViewNodeParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeStrongInterface(iAutofillWindowPresenter);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAugmentedAutofillManagerClient
            public boolean requestAutofill(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAugmentedAutofillManagerClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
