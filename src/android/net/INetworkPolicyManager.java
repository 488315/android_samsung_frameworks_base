package android.net;

import android.Manifest;
import android.app.ActivityThread;
import android.net.INetworkPolicyListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.telephony.SubscriptionPlan;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public interface INetworkPolicyManager extends IInterface {

    public static class Default implements INetworkPolicyManager {
        @Override // android.net.INetworkPolicyManager
        public void addUidPolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void factoryReset(String str) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public int[] getAllFirewallRuleMobileData() throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean getFirewallRuleMobileData(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean getFirewallRuleWifi(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public int getMultipathPreference(Network network) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public NetworkPolicy[] getNetworkPolicies(String str) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean getRestrictBackground() throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundByCaller() throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public SubscriptionPlan[] getSubscriptionPlans(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public String getSubscriptionPlansOwner(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public int getUidPolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int[] getUidsWithPolicy(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean isUidNetworkingBlocked(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean isUidRestrictedOnMeteredNetworks(int i) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public void notifyStatsProviderWarningOrLimitReached() throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void removeUidPolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setDeviceIdleMode(boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleMobileData(int i, boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleMobileDataMap(Map map) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleWifi(int i, boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleWifiMap(Map map) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setRestrictBackground(boolean z) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setUidPolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setWifiMeteredOverride(String str, int i) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
        }
    }

    void addUidPolicy(int i, int i2) throws RemoteException;

    void factoryReset(String str) throws RemoteException;

    int[] getAllFirewallRuleMobileData() throws RemoteException;

    boolean getFirewallRuleMobileData(int i) throws RemoteException;

    boolean getFirewallRuleWifi(int i) throws RemoteException;

    int getMultipathPreference(Network network) throws RemoteException;

    NetworkPolicy[] getNetworkPolicies(String str) throws RemoteException;

    boolean getRestrictBackground() throws RemoteException;

    int getRestrictBackgroundByCaller() throws RemoteException;

    int getRestrictBackgroundStatus(int i) throws RemoteException;

    SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) throws RemoteException;

    SubscriptionPlan[] getSubscriptionPlans(int i, String str) throws RemoteException;

    String getSubscriptionPlansOwner(int i) throws RemoteException;

    int getUidPolicy(int i) throws RemoteException;

    int[] getUidsWithPolicy(int i) throws RemoteException;

    boolean isUidNetworkingBlocked(int i, boolean z) throws RemoteException;

    boolean isUidRestrictedOnMeteredNetworks(int i) throws RemoteException;

    void notifyStatsProviderWarningOrLimitReached() throws RemoteException;

    void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException;

    void removeUidPolicy(int i, int i2) throws RemoteException;

    void setDeviceIdleMode(boolean z) throws RemoteException;

    void setFirewallRuleMobileData(int i, boolean z) throws RemoteException;

    void setFirewallRuleMobileDataMap(Map map) throws RemoteException;

    void setFirewallRuleWifi(int i, boolean z) throws RemoteException;

    void setFirewallRuleWifiMap(Map map) throws RemoteException;

    void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) throws RemoteException;

    void setRestrictBackground(boolean z) throws RemoteException;

    void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str) throws RemoteException;

    void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) throws RemoteException;

    void setUidPolicy(int i, int i2) throws RemoteException;

    void setWifiMeteredOverride(String str, int i) throws RemoteException;

    void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException;

    void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException;

    public static abstract class Stub extends Binder implements INetworkPolicyManager {
        public static final String DESCRIPTOR = "android.net.INetworkPolicyManager";
        static final int TRANSACTION_addUidPolicy = 2;
        static final int TRANSACTION_factoryReset = 24;
        static final int TRANSACTION_getAllFirewallRuleMobileData = 27;
        static final int TRANSACTION_getFirewallRuleMobileData = 32;
        static final int TRANSACTION_getFirewallRuleWifi = 33;
        static final int TRANSACTION_getMultipathPreference = 17;
        static final int TRANSACTION_getNetworkPolicies = 9;
        static final int TRANSACTION_getRestrictBackground = 12;
        static final int TRANSACTION_getRestrictBackgroundByCaller = 13;
        static final int TRANSACTION_getRestrictBackgroundStatus = 14;
        static final int TRANSACTION_getSubscriptionPlan = 18;
        static final int TRANSACTION_getSubscriptionPlans = 20;
        static final int TRANSACTION_getSubscriptionPlansOwner = 22;
        static final int TRANSACTION_getUidPolicy = 4;
        static final int TRANSACTION_getUidsWithPolicy = 5;
        static final int TRANSACTION_isUidNetworkingBlocked = 25;
        static final int TRANSACTION_isUidRestrictedOnMeteredNetworks = 26;
        static final int TRANSACTION_notifyStatsProviderWarningOrLimitReached = 19;
        static final int TRANSACTION_registerListener = 6;
        static final int TRANSACTION_removeUidPolicy = 3;
        static final int TRANSACTION_setDeviceIdleMode = 15;
        static final int TRANSACTION_setFirewallRuleMobileData = 28;
        static final int TRANSACTION_setFirewallRuleMobileDataMap = 30;
        static final int TRANSACTION_setFirewallRuleWifi = 29;
        static final int TRANSACTION_setFirewallRuleWifiMap = 31;
        static final int TRANSACTION_setNetworkPolicies = 8;
        static final int TRANSACTION_setRestrictBackground = 11;
        static final int TRANSACTION_setSubscriptionOverride = 23;
        static final int TRANSACTION_setSubscriptionPlans = 21;
        static final int TRANSACTION_setUidPolicy = 1;
        static final int TRANSACTION_setWifiMeteredOverride = 16;
        static final int TRANSACTION_snoozeLimit = 10;
        static final int TRANSACTION_unregisterListener = 7;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 32;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static INetworkPolicyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetworkPolicyManager)) {
                return (INetworkPolicyManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setUidPolicy";
                case 2:
                    return "addUidPolicy";
                case 3:
                    return "removeUidPolicy";
                case 4:
                    return "getUidPolicy";
                case 5:
                    return "getUidsWithPolicy";
                case 6:
                    return "registerListener";
                case 7:
                    return "unregisterListener";
                case 8:
                    return "setNetworkPolicies";
                case 9:
                    return "getNetworkPolicies";
                case 10:
                    return "snoozeLimit";
                case 11:
                    return "setRestrictBackground";
                case 12:
                    return "getRestrictBackground";
                case 13:
                    return "getRestrictBackgroundByCaller";
                case 14:
                    return "getRestrictBackgroundStatus";
                case 15:
                    return "setDeviceIdleMode";
                case 16:
                    return "setWifiMeteredOverride";
                case 17:
                    return "getMultipathPreference";
                case 18:
                    return "getSubscriptionPlan";
                case 19:
                    return "notifyStatsProviderWarningOrLimitReached";
                case 20:
                    return "getSubscriptionPlans";
                case 21:
                    return "setSubscriptionPlans";
                case 22:
                    return "getSubscriptionPlansOwner";
                case 23:
                    return "setSubscriptionOverride";
                case 24:
                    return "factoryReset";
                case 25:
                    return "isUidNetworkingBlocked";
                case 26:
                    return "isUidRestrictedOnMeteredNetworks";
                case 27:
                    return "getAllFirewallRuleMobileData";
                case 28:
                    return "setFirewallRuleMobileData";
                case 29:
                    return "setFirewallRuleWifi";
                case 30:
                    return "setFirewallRuleMobileDataMap";
                case 31:
                    return "setFirewallRuleWifiMap";
                case 32:
                    return "getFirewallRuleMobileData";
                case 33:
                    return "getFirewallRuleWifi";
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
                    setUidPolicy(i3, i4);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidPolicy(i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidPolicy(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int uidPolicy = getUidPolicy(i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidPolicy);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] uidsWithPolicy = getUidsWithPolicy(i10);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uidsWithPolicy);
                    return true;
                case 6:
                    INetworkPolicyListener iNetworkPolicyListenerAsInterface = INetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(iNetworkPolicyListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    INetworkPolicyListener iNetworkPolicyListenerAsInterface2 = INetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(iNetworkPolicyListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    NetworkPolicy[] networkPolicyArr = (NetworkPolicy[]) parcel.createTypedArray(NetworkPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkPolicies(networkPolicyArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NetworkPolicy[] networkPolicies = getNetworkPolicies(string);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(networkPolicies, 1);
                    return true;
                case 10:
                    NetworkTemplate networkTemplate = (NetworkTemplate) parcel.readTypedObject(NetworkTemplate.CREATOR);
                    parcel.enforceNoDataAvail();
                    snoozeLimit(networkTemplate);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRestrictBackground(z);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean restrictBackground = getRestrictBackground();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(restrictBackground);
                    return true;
                case 13:
                    int restrictBackgroundByCaller = getRestrictBackgroundByCaller();
                    parcel2.writeNoException();
                    parcel2.writeInt(restrictBackgroundByCaller);
                    return true;
                case 14:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int restrictBackgroundStatus = getRestrictBackgroundStatus(i11);
                    parcel2.writeNoException();
                    parcel2.writeInt(restrictBackgroundStatus);
                    return true;
                case 15:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceIdleMode(z2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string2 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiMeteredOverride(string2, i12);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    int multipathPreference = getMultipathPreference(network);
                    parcel2.writeNoException();
                    parcel2.writeInt(multipathPreference);
                    return true;
                case 18:
                    NetworkTemplate networkTemplate2 = (NetworkTemplate) parcel.readTypedObject(NetworkTemplate.CREATOR);
                    parcel.enforceNoDataAvail();
                    SubscriptionPlan subscriptionPlan = getSubscriptionPlan(networkTemplate2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(subscriptionPlan, 1);
                    return true;
                case 19:
                    notifyStatsProviderWarningOrLimitReached();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i13 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionPlan[] subscriptionPlans = getSubscriptionPlans(i13, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(subscriptionPlans, 1);
                    return true;
                case 21:
                    int i14 = parcel.readInt();
                    SubscriptionPlan[] subscriptionPlanArr = (SubscriptionPlan[]) parcel.createTypedArray(SubscriptionPlan.CREATOR);
                    long j = parcel.readLong();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionPlans(i14, subscriptionPlanArr, j, string4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String subscriptionPlansOwner = getSubscriptionPlansOwner(i15);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionPlansOwner);
                    return true;
                case 23:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    long j2 = parcel.readLong();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionOverride(i16, i17, i18, iArrCreateIntArray, j2, string5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    factoryReset(string6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i19 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidNetworkingBlocked = isUidNetworkingBlocked(i19, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidNetworkingBlocked);
                    return true;
                case 26:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidRestrictedOnMeteredNetworks = isUidRestrictedOnMeteredNetworks(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidRestrictedOnMeteredNetworks);
                    return true;
                case 27:
                    int[] allFirewallRuleMobileData = getAllFirewallRuleMobileData();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allFirewallRuleMobileData);
                    return true;
                case 28:
                    int i21 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleMobileData(i21, z4);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i22 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallRuleWifi(i22, z5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    setFirewallRuleMobileDataMap(hashMap);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    HashMap hashMap2 = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    setFirewallRuleWifiMap(hashMap2);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean firewallRuleMobileData = getFirewallRuleMobileData(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(firewallRuleMobileData);
                    return true;
                case 33:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean firewallRuleWifi = getFirewallRuleWifi(i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(firewallRuleWifi);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INetworkPolicyManager {
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

            @Override // android.net.INetworkPolicyManager
            public void setUidPolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void addUidPolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void removeUidPolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getUidPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int[] getUidsWithPolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNetworkPolicyListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNetworkPolicyListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(networkPolicyArr, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public NetworkPolicy[] getNetworkPolicies(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NetworkPolicy[]) parcelObtain2.createTypedArray(NetworkPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(networkTemplate, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setRestrictBackground(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getRestrictBackground() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundByCaller() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setDeviceIdleMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setWifiMeteredOverride(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getMultipathPreference(Network network) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(network, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public SubscriptionPlan getSubscriptionPlan(NetworkTemplate networkTemplate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(networkTemplate, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubscriptionPlan) parcelObtain2.readTypedObject(SubscriptionPlan.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void notifyStatsProviderWarningOrLimitReached() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public SubscriptionPlan[] getSubscriptionPlans(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubscriptionPlan[]) parcelObtain2.createTypedArray(SubscriptionPlan.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(subscriptionPlanArr, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public String getSubscriptionPlansOwner(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void factoryReset(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidNetworkingBlocked(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidRestrictedOnMeteredNetworks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int[] getAllFirewallRuleMobileData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleMobileData(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleWifi(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleMobileDataMap(Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleWifiMap(Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getFirewallRuleMobileData(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getFirewallRuleWifi(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void addUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void removeUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidsWithPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setNetworkPolicies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getNetworkPolicies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void snoozeLimit_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackground_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackgroundByCaller_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_NETWORK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setDeviceIdleMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setWifiMeteredOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void factoryReset_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isUidRestrictedOnMeteredNetworks_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }
    }
}
