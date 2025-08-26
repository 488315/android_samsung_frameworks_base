package com.android.internal.telecom;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.CallScreeningService;

/* loaded from: classes4.dex */
public interface ICallScreeningAdapter extends IInterface {

    public static class Default implements ICallScreeningAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallScreeningAdapter
        public void onScreeningResponse(String str, ComponentName componentName, CallScreeningService.ParcelableCallResponse parcelableCallResponse) throws RemoteException {
        }
    }

    void onScreeningResponse(String str, ComponentName componentName, CallScreeningService.ParcelableCallResponse parcelableCallResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallScreeningAdapter {
        public static final String DESCRIPTOR = "com.android.internal.telecom.ICallScreeningAdapter";
        static final int TRANSACTION_onScreeningResponse = 1;

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

        public static ICallScreeningAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallScreeningAdapter)) {
                return (ICallScreeningAdapter) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onScreeningResponse";
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
                String string = parcel.readString();
                ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                CallScreeningService.ParcelableCallResponse parcelableCallResponse = (CallScreeningService.ParcelableCallResponse) parcel.readTypedObject(CallScreeningService.ParcelableCallResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onScreeningResponse(string, componentName, parcelableCallResponse);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICallScreeningAdapter {
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

            @Override // com.android.internal.telecom.ICallScreeningAdapter
            public void onScreeningResponse(String str, ComponentName componentName, CallScreeningService.ParcelableCallResponse parcelableCallResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(parcelableCallResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
