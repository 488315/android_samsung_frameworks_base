package android.service.autofill;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.view.autofill.AutofillValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IAutofillFieldClassificationService extends IInterface {

    public static class Default implements IAutofillFieldClassificationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IAutofillFieldClassificationService
        public void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] strArr, String[] strArr2, String str, Bundle bundle, Map map, Map map2) throws RemoteException {
        }
    }

    void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] strArr, String[] strArr2, String str, Bundle bundle, Map map, Map map2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAutofillFieldClassificationService {
        public static final String DESCRIPTOR = "android.service.autofill.IAutofillFieldClassificationService";
        static final int TRANSACTION_calculateScores = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutofillFieldClassificationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAutofillFieldClassificationService)) {
                return (IAutofillFieldClassificationService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "calculateScores";
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
            if (i == 1) {
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AutofillValue.CREATOR);
                String[] createStringArray = parcel.createStringArray();
                String[] createStringArray2 = parcel.createStringArray();
                String readString = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                ClassLoader classLoader = getClass().getClassLoader();
                HashMap readHashMap = parcel.readHashMap(classLoader);
                HashMap readHashMap2 = parcel.readHashMap(classLoader);
                parcel.enforceNoDataAvail();
                calculateScores(remoteCallback, createTypedArrayList, createStringArray, createStringArray2, readString, bundle, readHashMap, readHashMap2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAutofillFieldClassificationService {
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

            @Override // android.service.autofill.IAutofillFieldClassificationService
            public void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] strArr, String[] strArr2, String str, Bundle bundle, Map map, Map map2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeMap(map);
                    obtain.writeMap(map2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
