package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SubscriptionPlan;

/* loaded from: classes3.dex */
public interface INetworkPolicyListener extends IInterface {

    public static class Default implements INetworkPolicyListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onMeteredIfacesChanged(String[] strArr) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onRestrictBackgroundChanged(boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidPoliciesChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidRulesChanged(int i, int i2) throws RemoteException {
        }
    }

    void onBlockedReasonChanged(int i, int i2, int i3) throws RemoteException;

    void onMeteredIfacesChanged(String[] strArr) throws RemoteException;

    void onRestrictBackgroundChanged(boolean z) throws RemoteException;

    void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws RemoteException;

    void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) throws RemoteException;

    void onUidPoliciesChanged(int i, int i2) throws RemoteException;

    void onUidRulesChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkPolicyListener {
        public static final String DESCRIPTOR = "android.net.INetworkPolicyListener";
        static final int TRANSACTION_onBlockedReasonChanged = 7;
        static final int TRANSACTION_onMeteredIfacesChanged = 2;
        static final int TRANSACTION_onRestrictBackgroundChanged = 3;
        static final int TRANSACTION_onSubscriptionOverride = 5;
        static final int TRANSACTION_onSubscriptionPlansChanged = 6;
        static final int TRANSACTION_onUidPoliciesChanged = 4;
        static final int TRANSACTION_onUidRulesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkPolicyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetworkPolicyListener)) {
                return (INetworkPolicyListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUidRulesChanged";
                case 2:
                    return "onMeteredIfacesChanged";
                case 3:
                    return "onRestrictBackgroundChanged";
                case 4:
                    return "onUidPoliciesChanged";
                case 5:
                    return "onSubscriptionOverride";
                case 6:
                    return "onSubscriptionPlansChanged";
                case 7:
                    return "onBlockedReasonChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidRulesChanged(readInt, readInt2);
                    return true;
                case 2:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onMeteredIfacesChanged(createStringArray);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRestrictBackgroundChanged(readBoolean);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidPoliciesChanged(readInt3, readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSubscriptionOverride(readInt5, readInt6, readInt7, createIntArray);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) parcel.createTypedArray(SubscriptionPlan.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSubscriptionPlansChanged(readInt8, subscriptionPlanArr);
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBlockedReasonChanged(readInt9, readInt10, readInt11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkPolicyListener {
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

            @Override // android.net.INetworkPolicyListener
            public void onUidRulesChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onMeteredIfacesChanged(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onRestrictBackgroundChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onUidPoliciesChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(subscriptionPlanArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onBlockedReasonChanged(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
