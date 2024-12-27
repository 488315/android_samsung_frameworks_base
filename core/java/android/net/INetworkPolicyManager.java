package android.net;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.telephony.SubscriptionPlan;

import java.util.Map;

/* loaded from: classes3.dex */
public interface INetworkPolicyManager extends IInterface {
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

    void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, String str)
            throws RemoteException;

    void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, long j, String str)
            throws RemoteException;

    void setUidPolicy(int i, int i2) throws RemoteException;

    void setWifiMeteredOverride(String str, int i) throws RemoteException;

    void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException;

    void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException;

    public static class Default implements INetworkPolicyManager {
        @Override // android.net.INetworkPolicyManager
        public void setUidPolicy(int uid, int policy) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void addUidPolicy(int uid, int policy) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void removeUidPolicy(int uid, int policy) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public int getUidPolicy(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int[] getUidsWithPolicy(int policy) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void registerListener(INetworkPolicyListener listener) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void unregisterListener(INetworkPolicyListener listener) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setNetworkPolicies(NetworkPolicy[] policies) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public NetworkPolicy[] getNetworkPolicies(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void snoozeLimit(NetworkTemplate template) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setRestrictBackground(boolean restrictBackground) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public boolean getRestrictBackground() throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundByCaller() throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundStatus(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public void setDeviceIdleMode(boolean enabled) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setWifiMeteredOverride(String networkId, int meteredOverride)
                throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public int getMultipathPreference(Network network) throws RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public SubscriptionPlan getSubscriptionPlan(NetworkTemplate template)
                throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void notifyStatsProviderWarningOrLimitReached() throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public SubscriptionPlan[] getSubscriptionPlans(int subId, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionPlans(
                int subId,
                SubscriptionPlan[] plans,
                long expirationDurationMillis,
                String callingPackage)
                throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public String getSubscriptionPlansOwner(int subId) throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionOverride(
                int subId,
                int overrideMask,
                int overrideValue,
                int[] networkTypes,
                long expirationDurationMillis,
                String callingPackage)
                throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void factoryReset(String subscriber) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork)
                throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean isUidRestrictedOnMeteredNetworks(int uid) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public int[] getAllFirewallRuleMobileData() throws RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleMobileDataMap(Map uidAllowedMap) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public void setFirewallRuleWifiMap(Map uidAllowedMap) throws RemoteException {}

        @Override // android.net.INetworkPolicyManager
        public boolean getFirewallRuleMobileData(int uid) throws RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean getFirewallRuleWifi(int uid) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements INetworkPolicyManager {
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

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static INetworkPolicyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof INetworkPolicyManager)) {
                return (INetworkPolicyManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    setUidPolicy(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    addUidPolicy(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    removeUidPolicy(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getUidPolicy(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result2 = getUidsWithPolicy(_arg05);
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 6:
                    INetworkPolicyListener _arg06 =
                            INetworkPolicyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerListener(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    INetworkPolicyListener _arg07 =
                            INetworkPolicyListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterListener(_arg07);
                    reply.writeNoException();
                    return true;
                case 8:
                    NetworkPolicy[] _arg08 =
                            (NetworkPolicy[]) data.createTypedArray(NetworkPolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setNetworkPolicies(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    NetworkPolicy[] _result3 = getNetworkPolicies(_arg09);
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case 10:
                    NetworkTemplate _arg010 =
                            (NetworkTemplate) data.readTypedObject(NetworkTemplate.CREATOR);
                    data.enforceNoDataAvail();
                    snoozeLimit(_arg010);
                    reply.writeNoException();
                    return true;
                case 11:
                    boolean _arg011 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setRestrictBackground(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    boolean _result4 = getRestrictBackground();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 13:
                    int _result5 = getRestrictBackgroundByCaller();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 14:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result6 = getRestrictBackgroundStatus(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 15:
                    boolean _arg013 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDeviceIdleMode(_arg013);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg014 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    setWifiMeteredOverride(_arg014, _arg14);
                    reply.writeNoException();
                    return true;
                case 17:
                    Network _arg015 = (Network) data.readTypedObject(Network.CREATOR);
                    data.enforceNoDataAvail();
                    int _result7 = getMultipathPreference(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 18:
                    NetworkTemplate _arg016 =
                            (NetworkTemplate) data.readTypedObject(NetworkTemplate.CREATOR);
                    data.enforceNoDataAvail();
                    SubscriptionPlan _result8 = getSubscriptionPlan(_arg016);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 19:
                    notifyStatsProviderWarningOrLimitReached();
                    reply.writeNoException();
                    return true;
                case 20:
                    int _arg017 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    SubscriptionPlan[] _result9 = getSubscriptionPlans(_arg017, _arg15);
                    reply.writeNoException();
                    reply.writeTypedArray(_result9, 1);
                    return true;
                case 21:
                    int _arg018 = data.readInt();
                    SubscriptionPlan[] _arg16 =
                            (SubscriptionPlan[]) data.createTypedArray(SubscriptionPlan.CREATOR);
                    long _arg2 = data.readLong();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    setSubscriptionPlans(_arg018, _arg16, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result10 = getSubscriptionPlansOwner(_arg019);
                    reply.writeNoException();
                    reply.writeString(_result10);
                    return true;
                case 23:
                    int _arg020 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg22 = data.readInt();
                    int[] _arg32 = data.createIntArray();
                    long _arg4 = data.readLong();
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    setSubscriptionOverride(_arg020, _arg17, _arg22, _arg32, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg021 = data.readString();
                    data.enforceNoDataAvail();
                    factoryReset(_arg021);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg022 = data.readInt();
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result11 = isUidNetworkingBlocked(_arg022, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 26:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = isUidRestrictedOnMeteredNetworks(_arg023);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 27:
                    int[] _result13 = getAllFirewallRuleMobileData();
                    reply.writeNoException();
                    reply.writeIntArray(_result13);
                    return true;
                case 28:
                    int _arg024 = data.readInt();
                    boolean _arg19 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallRuleMobileData(_arg024, _arg19);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _arg025 = data.readInt();
                    boolean _arg110 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFirewallRuleWifi(_arg025, _arg110);
                    reply.writeNoException();
                    return true;
                case 30:
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg026 = data.readHashMap(cl);
                    data.enforceNoDataAvail();
                    setFirewallRuleMobileDataMap(_arg026);
                    reply.writeNoException();
                    return true;
                case 31:
                    ClassLoader cl2 = getClass().getClassLoader();
                    Map _arg027 = data.readHashMap(cl2);
                    data.enforceNoDataAvail();
                    setFirewallRuleWifiMap(_arg027);
                    reply.writeNoException();
                    return true;
                case 32:
                    int _arg028 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = getFirewallRuleMobileData(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 33:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = getFirewallRuleWifi(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements INetworkPolicyManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkPolicyManager
            public void setUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void addUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void removeUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getUidPolicy(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int[] getUidsWithPolicy(int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(policy);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void registerListener(INetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void unregisterListener(INetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setNetworkPolicies(NetworkPolicy[] policies) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(policies, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public NetworkPolicy[] getNetworkPolicies(String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    NetworkPolicy[] _result =
                            (NetworkPolicy[]) _reply.createTypedArray(NetworkPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void snoozeLimit(NetworkTemplate template) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(template, 0);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setRestrictBackground(boolean restrictBackground) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(restrictBackground);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getRestrictBackground() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundByCaller() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundStatus(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setDeviceIdleMode(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setWifiMeteredOverride(String networkId, int meteredOverride)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(networkId);
                    _data.writeInt(meteredOverride);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getMultipathPreference(Network network) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(network, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public SubscriptionPlan getSubscriptionPlan(NetworkTemplate template)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(template, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    SubscriptionPlan _result =
                            (SubscriptionPlan) _reply.readTypedObject(SubscriptionPlan.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void notifyStatsProviderWarningOrLimitReached() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public SubscriptionPlan[] getSubscriptionPlans(int subId, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    SubscriptionPlan[] _result =
                            (SubscriptionPlan[]) _reply.createTypedArray(SubscriptionPlan.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionPlans(
                    int subId,
                    SubscriptionPlan[] plans,
                    long expirationDurationMillis,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeTypedArray(plans, 0);
                    _data.writeLong(expirationDurationMillis);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public String getSubscriptionPlansOwner(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionOverride(
                    int subId,
                    int overrideMask,
                    int overrideValue,
                    int[] networkTypes,
                    long expirationDurationMillis,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(overrideMask);
                    _data.writeInt(overrideValue);
                    _data.writeIntArray(networkTypes);
                    _data.writeLong(expirationDurationMillis);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void factoryReset(String subscriber) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subscriber);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(meteredNetwork);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidRestrictedOnMeteredNetworks(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int[] getAllFirewallRuleMobileData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleMobileData(int uid, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleWifi(int uid, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeBoolean(allow);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleMobileDataMap(Map uidAllowedMap) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(uidAllowedMap);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setFirewallRuleWifiMap(Map uidAllowedMap) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(uidAllowedMap);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getFirewallRuleMobileData(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getFirewallRuleWifi(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void setUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void addUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void removeUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidsWithPolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setNetworkPolicies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getNetworkPolicies_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void snoozeLimit_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackground_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackgroundByCaller_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.ACCESS_NETWORK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setDeviceIdleMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setWifiMeteredOverride_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void factoryReset_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isUidRestrictedOnMeteredNetworks_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 32;
        }
    }
}
