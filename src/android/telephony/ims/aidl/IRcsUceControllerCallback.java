package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactUceCapability;
import android.telephony.ims.SipDetails;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IRcsUceControllerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IRcsUceControllerCallback";

    public static class Default implements IRcsUceControllerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onCapabilitiesReceived(List<RcsContactUceCapability> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onComplete(SipDetails sipDetails) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
        public void onError(int i, long j, SipDetails sipDetails) throws RemoteException {
        }
    }

    void onCapabilitiesReceived(List<RcsContactUceCapability> list) throws RemoteException;

    void onComplete(SipDetails sipDetails) throws RemoteException;

    void onError(int i, long j, SipDetails sipDetails) throws RemoteException;

    public static abstract class Stub extends Binder implements IRcsUceControllerCallback {
        static final int TRANSACTION_onCapabilitiesReceived = 1;
        static final int TRANSACTION_onComplete = 2;
        static final int TRANSACTION_onError = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IRcsUceControllerCallback.DESCRIPTOR);
        }

        public static IRcsUceControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRcsUceControllerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRcsUceControllerCallback)) {
                return (IRcsUceControllerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCapabilitiesReceived";
            }
            if (i == 2) {
                return "onComplete";
            }
            if (i != 3) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRcsUceControllerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRcsUceControllerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(RcsContactUceCapability.CREATOR);
                parcel.enforceNoDataAvail();
                onCapabilitiesReceived(arrayListCreateTypedArrayList);
            } else if (i == 2) {
                SipDetails sipDetails = (SipDetails) parcel.readTypedObject(SipDetails.CREATOR);
                parcel.enforceNoDataAvail();
                onComplete(sipDetails);
            } else if (i == 3) {
                int i3 = parcel.readInt();
                long j = parcel.readLong();
                SipDetails sipDetails2 = (SipDetails) parcel.readTypedObject(SipDetails.CREATOR);
                parcel.enforceNoDataAvail();
                onError(i3, j, sipDetails2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRcsUceControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRcsUceControllerCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onCapabilitiesReceived(List<RcsContactUceCapability> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onComplete(SipDetails sipDetails) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IRcsUceControllerCallback
            public void onError(int i, long j, SipDetails sipDetails) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IRcsUceControllerCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
