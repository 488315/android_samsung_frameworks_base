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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkPolicyListener)) {
                return (INetworkPolicyListener) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidRulesChanged(i3, i4);
                    return true;
                case 2:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onMeteredIfacesChanged(strArrCreateStringArray);
                    return true;
                case 3:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRestrictBackgroundChanged(z);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidPoliciesChanged(i5, i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSubscriptionOverride(i7, i8, i9, iArrCreateIntArray);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) parcel.createTypedArray(SubscriptionPlan.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSubscriptionPlansChanged(i10, subscriptionPlanArr);
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBlockedReasonChanged(i11, i12, i13);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onMeteredIfacesChanged(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onRestrictBackgroundChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onUidPoliciesChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionPlansChanged(int i, SubscriptionPlan[] subscriptionPlanArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(subscriptionPlanArr, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onBlockedReasonChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
