package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.DelegateRegistrationState;
import android.telephony.ims.FeatureTagState;
import android.telephony.ims.SipDelegateConfiguration;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.telephony.ims.aidl.ISipDelegate;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISipDelegateConnectionStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegateConnectionStateCallback";

    public static class Default implements ISipDelegateConnectionStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onCreated(ISipDelegate iSipDelegate) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onDestroyed(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onFeatureTagStatusChanged(DelegateRegistrationState delegateRegistrationState, List<FeatureTagState> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException {
        }
    }

    void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException;

    void onCreated(ISipDelegate iSipDelegate) throws RemoteException;

    void onDestroyed(int i) throws RemoteException;

    void onFeatureTagStatusChanged(DelegateRegistrationState delegateRegistrationState, List<FeatureTagState> list) throws RemoteException;

    void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipDelegateConnectionStateCallback {
        static final int TRANSACTION_onConfigurationChanged = 4;
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onDestroyed = 5;
        static final int TRANSACTION_onFeatureTagStatusChanged = 2;
        static final int TRANSACTION_onImsConfigurationChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISipDelegateConnectionStateCallback.DESCRIPTOR);
        }

        public static ISipDelegateConnectionStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISipDelegateConnectionStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISipDelegateConnectionStateCallback)) {
                return (ISipDelegateConnectionStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCreated";
            }
            if (i == 2) {
                return "onFeatureTagStatusChanged";
            }
            if (i == 3) {
                return "onImsConfigurationChanged";
            }
            if (i == 4) {
                return "onConfigurationChanged";
            }
            if (i != 5) {
                return null;
            }
            return "onDestroyed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipDelegateConnectionStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISipDelegate asInterface = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCreated(asInterface);
            } else if (i == 2) {
                DelegateRegistrationState delegateRegistrationState = (DelegateRegistrationState) parcel.readTypedObject(DelegateRegistrationState.CREATOR);
                ArrayList createTypedArrayList = parcel.createTypedArrayList(FeatureTagState.CREATOR);
                parcel.enforceNoDataAvail();
                onFeatureTagStatusChanged(delegateRegistrationState, createTypedArrayList);
            } else if (i == 3) {
                SipDelegateImsConfiguration sipDelegateImsConfiguration = (SipDelegateImsConfiguration) parcel.readTypedObject(SipDelegateImsConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                onImsConfigurationChanged(sipDelegateImsConfiguration);
            } else if (i == 4) {
                SipDelegateConfiguration sipDelegateConfiguration = (SipDelegateConfiguration) parcel.readTypedObject(SipDelegateConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                onConfigurationChanged(sipDelegateConfiguration);
            } else if (i == 5) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDestroyed(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISipDelegateConnectionStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegateConnectionStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
            public void onCreated(ISipDelegate iSipDelegate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDelegate);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
            public void onFeatureTagStatusChanged(DelegateRegistrationState delegateRegistrationState, List<FeatureTagState> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(delegateRegistrationState, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
            public void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateImsConfiguration, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
            public void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateConfiguration, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
            public void onDestroyed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateConnectionStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
