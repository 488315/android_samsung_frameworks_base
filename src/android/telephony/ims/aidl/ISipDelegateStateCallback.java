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
public interface ISipDelegateStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipDelegateStateCallback";

    public static class Default implements ISipDelegateStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onCreated(ISipDelegate iSipDelegate, List<FeatureTagState> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onDestroyed(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onFeatureTagRegistrationChanged(DelegateRegistrationState delegateRegistrationState) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
        public void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException {
        }
    }

    void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException;

    void onCreated(ISipDelegate iSipDelegate, List<FeatureTagState> list) throws RemoteException;

    void onDestroyed(int i) throws RemoteException;

    void onFeatureTagRegistrationChanged(DelegateRegistrationState delegateRegistrationState) throws RemoteException;

    void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipDelegateStateCallback {
        static final int TRANSACTION_onConfigurationChanged = 4;
        static final int TRANSACTION_onCreated = 1;
        static final int TRANSACTION_onDestroyed = 5;
        static final int TRANSACTION_onFeatureTagRegistrationChanged = 2;
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
            attachInterface(this, ISipDelegateStateCallback.DESCRIPTOR);
        }

        public static ISipDelegateStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISipDelegateStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISipDelegateStateCallback)) {
                return (ISipDelegateStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCreated";
            }
            if (i == 2) {
                return "onFeatureTagRegistrationChanged";
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
                parcel.enforceInterface(ISipDelegateStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipDelegateStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISipDelegate asInterface = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                ArrayList createTypedArrayList = parcel.createTypedArrayList(FeatureTagState.CREATOR);
                parcel.enforceNoDataAvail();
                onCreated(asInterface, createTypedArrayList);
            } else if (i == 2) {
                DelegateRegistrationState delegateRegistrationState = (DelegateRegistrationState) parcel.readTypedObject(DelegateRegistrationState.CREATOR);
                parcel.enforceNoDataAvail();
                onFeatureTagRegistrationChanged(delegateRegistrationState);
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

        private static class Proxy implements ISipDelegateStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipDelegateStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onCreated(ISipDelegate iSipDelegate, List<FeatureTagState> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onFeatureTagRegistrationChanged(DelegateRegistrationState delegateRegistrationState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(delegateRegistrationState, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateImsConfiguration, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDelegateConfiguration, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipDelegateStateCallback
            public void onDestroyed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipDelegateStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
