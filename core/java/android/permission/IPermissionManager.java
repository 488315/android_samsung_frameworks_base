package android.permission;

import android.Manifest;
import android.app.ActivityThread;
import android.content.AttributionSourceState;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.permission.SplitPermissionInfoParcelable;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public interface IPermissionManager extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IPermissionManager";

    boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2)
            throws RemoteException;

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener)
            throws RemoteException;

    boolean addPermission(PermissionInfo permissionInfo, boolean z) throws RemoteException;

    int checkPermission(String str, String str2, String str3, int i) throws RemoteException;

    int checkUidPermission(int i, String str, int i2) throws RemoteException;

    ParceledListSlice getAllPermissionGroups(int i) throws RemoteException;

    Map<String, PermissionManager.PermissionState> getAllPermissionStates(
            String str, String str2, int i) throws RemoteException;

    List<String> getAllowlistedRestrictedPermissions(String str, int i, int i2)
            throws RemoteException;

    List<String> getAutoRevokeExemptionGrantedPackages(int i) throws RemoteException;

    List<String> getAutoRevokeExemptionRequestedPackages(int i) throws RemoteException;

    int getPermissionFlags(String str, String str2, String str3, int i) throws RemoteException;

    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException;

    int getRegisteredAttributionSourceCount(int i) throws RemoteException;

    List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException;

    void grantRuntimePermission(String str, String str2, String str3, int i) throws RemoteException;

    boolean isAutoRevokeExempted(String str, int i) throws RemoteException;

    boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2)
            throws RemoteException;

    boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState)
            throws RemoteException;

    ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException;

    IBinder registerAttributionSource(AttributionSourceState attributionSourceState)
            throws RemoteException;

    boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2)
            throws RemoteException;

    void removeOnPermissionsChangeListener(
            IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException;

    void removePermission(String str) throws RemoteException;

    void revokePostNotificationPermissionWithoutKillForTest(String str, int i)
            throws RemoteException;

    void revokeRuntimePermission(String str, String str2, String str3, int i, String str4)
            throws RemoteException;

    boolean setAutoRevokeExempted(String str, boolean z, int i) throws RemoteException;

    boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2)
            throws RemoteException;

    void startOneTimePermissionSession(String str, int i, int i2, long j, long j2)
            throws RemoteException;

    void stopOneTimePermissionSession(String str, int i) throws RemoteException;

    void updatePermissionFlags(
            String str, String str2, int i, int i2, boolean z, String str3, int i3)
            throws RemoteException;

    void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException;

    public static class Default implements IPermissionManager {
        @Override // android.permission.IPermissionManager
        public ParceledListSlice getAllPermissionGroups(int flags) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public PermissionGroupInfo getPermissionGroupInfo(String groupName, int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public PermissionInfo getPermissionInfo(
                String permissionName, String packageName, int flags) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public ParceledListSlice queryPermissionsByGroup(String groupName, int flags)
                throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean addPermission(PermissionInfo permissionInfo, boolean async)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void removePermission(String permissionName) throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public int getPermissionFlags(
                String packageName, String permissionName, String persistentDeviceId, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlags(
                String packageName,
                String permissionName,
                int flagMask,
                int flagValues,
                boolean checkAdjustPolicyFlagPermission,
                String persistentDeviceId,
                int userId)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void addOnPermissionsChangeListener(IOnPermissionsChangeListener listener)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener listener)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public List<String> getAllowlistedRestrictedPermissions(
                String packageName, int flags, int userId) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean addAllowlistedRestrictedPermission(
                String packageName, String permissionName, int flags, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean removeAllowlistedRestrictedPermission(
                String packageName, String permissionName, int flags, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void grantRuntimePermission(
                String packageName, String permissionName, String persistentDeviceId, int userId)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void revokeRuntimePermission(
                String packageName,
                String permissionName,
                String persistentDeviceId,
                int userId,
                String reason)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void revokePostNotificationPermissionWithoutKillForTest(
                String packageName, int userId) throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public boolean shouldShowRequestPermissionRationale(
                String packageName, String permissionName, int deviceId, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isPermissionRevokedByPolicy(
                String packageName, String permissionName, int deviceId, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public void startOneTimePermissionSession(
                String packageName,
                int deviceId,
                int userId,
                long timeout,
                long revokeAfterKilledDelay)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public void stopOneTimePermissionSession(String packageName, int userId)
                throws RemoteException {}

        @Override // android.permission.IPermissionManager
        public List<String> getAutoRevokeExemptionRequestedPackages(int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public List<String> getAutoRevokeExemptionGrantedPackages(int userId)
                throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean setAutoRevokeExempted(String packageName, boolean exempted, int userId)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isAutoRevokeExempted(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public IBinder registerAttributionSource(AttributionSourceState source)
                throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public int getRegisteredAttributionSourceCount(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public boolean isRegisteredAttributionSource(AttributionSourceState source)
                throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public int checkPermission(
                String packageName, String permissionName, String persistentDeviceId, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public int checkUidPermission(int uid, String permissionName, int deviceId)
                throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public Map<String, PermissionManager.PermissionState> getAllPermissionStates(
                String packageName, String persistentDeviceId, int userId) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPermissionManager {
        static final int TRANSACTION_addAllowlistedRestrictedPermission = 13;
        static final int TRANSACTION_addOnPermissionsChangeListener = 10;
        static final int TRANSACTION_addPermission = 5;
        static final int TRANSACTION_checkPermission = 30;
        static final int TRANSACTION_checkUidPermission = 31;
        static final int TRANSACTION_getAllPermissionGroups = 1;
        static final int TRANSACTION_getAllPermissionStates = 32;
        static final int TRANSACTION_getAllowlistedRestrictedPermissions = 12;
        static final int TRANSACTION_getAutoRevokeExemptionGrantedPackages = 24;
        static final int TRANSACTION_getAutoRevokeExemptionRequestedPackages = 23;
        static final int TRANSACTION_getPermissionFlags = 7;
        static final int TRANSACTION_getPermissionGroupInfo = 2;
        static final int TRANSACTION_getPermissionInfo = 3;
        static final int TRANSACTION_getRegisteredAttributionSourceCount = 28;
        static final int TRANSACTION_getSplitPermissions = 20;
        static final int TRANSACTION_grantRuntimePermission = 15;
        static final int TRANSACTION_isAutoRevokeExempted = 26;
        static final int TRANSACTION_isPermissionRevokedByPolicy = 19;
        static final int TRANSACTION_isRegisteredAttributionSource = 29;
        static final int TRANSACTION_queryPermissionsByGroup = 4;
        static final int TRANSACTION_registerAttributionSource = 27;
        static final int TRANSACTION_removeAllowlistedRestrictedPermission = 14;
        static final int TRANSACTION_removeOnPermissionsChangeListener = 11;
        static final int TRANSACTION_removePermission = 6;
        static final int TRANSACTION_revokePostNotificationPermissionWithoutKillForTest = 17;
        static final int TRANSACTION_revokeRuntimePermission = 16;
        static final int TRANSACTION_setAutoRevokeExempted = 25;
        static final int TRANSACTION_shouldShowRequestPermissionRationale = 18;
        static final int TRANSACTION_startOneTimePermissionSession = 21;
        static final int TRANSACTION_stopOneTimePermissionSession = 22;
        static final int TRANSACTION_updatePermissionFlags = 8;
        static final int TRANSACTION_updatePermissionFlagsForAllApps = 9;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IPermissionManager.DESCRIPTOR);
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

        public static IPermissionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPermissionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IPermissionManager)) {
                return (IPermissionManager) iin;
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
                    return "getAllPermissionGroups";
                case 2:
                    return "getPermissionGroupInfo";
                case 3:
                    return "getPermissionInfo";
                case 4:
                    return "queryPermissionsByGroup";
                case 5:
                    return "addPermission";
                case 6:
                    return "removePermission";
                case 7:
                    return "getPermissionFlags";
                case 8:
                    return "updatePermissionFlags";
                case 9:
                    return "updatePermissionFlagsForAllApps";
                case 10:
                    return "addOnPermissionsChangeListener";
                case 11:
                    return "removeOnPermissionsChangeListener";
                case 12:
                    return "getAllowlistedRestrictedPermissions";
                case 13:
                    return "addAllowlistedRestrictedPermission";
                case 14:
                    return "removeAllowlistedRestrictedPermission";
                case 15:
                    return "grantRuntimePermission";
                case 16:
                    return "revokeRuntimePermission";
                case 17:
                    return "revokePostNotificationPermissionWithoutKillForTest";
                case 18:
                    return "shouldShowRequestPermissionRationale";
                case 19:
                    return "isPermissionRevokedByPolicy";
                case 20:
                    return "getSplitPermissions";
                case 21:
                    return "startOneTimePermissionSession";
                case 22:
                    return "stopOneTimePermissionSession";
                case 23:
                    return "getAutoRevokeExemptionRequestedPackages";
                case 24:
                    return "getAutoRevokeExemptionGrantedPackages";
                case 25:
                    return "setAutoRevokeExempted";
                case 26:
                    return "isAutoRevokeExempted";
                case 27:
                    return "registerAttributionSource";
                case 28:
                    return "getRegisteredAttributionSourceCount";
                case 29:
                    return "isRegisteredAttributionSource";
                case 30:
                    return "checkPermission";
                case 31:
                    return "checkUidPermission";
                case 32:
                    return "getAllPermissionStates";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, final Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IPermissionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPermissionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result = getAllPermissionGroups(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    PermissionGroupInfo _result2 = getPermissionGroupInfo(_arg02, _arg1);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    PermissionInfo _result3 = getPermissionInfo(_arg03, _arg12, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result4 = queryPermissionsByGroup(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 5:
                    PermissionInfo _arg05 =
                            (PermissionInfo) data.readTypedObject(PermissionInfo.CREATOR);
                    boolean _arg14 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result5 = addPermission(_arg05, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    removePermission(_arg06);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    String _arg15 = data.readString();
                    String _arg22 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result6 = getPermissionFlags(_arg07, _arg15, _arg22, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg16 = data.readString();
                    int _arg23 = data.readInt();
                    int _arg32 = data.readInt();
                    boolean _arg4 = data.readBoolean();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    updatePermissionFlags(_arg08, _arg16, _arg23, _arg32, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    int _arg17 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    updatePermissionFlagsForAllApps(_arg09, _arg17, _arg24);
                    reply.writeNoException();
                    return true;
                case 10:
                    IOnPermissionsChangeListener _arg010 =
                            IOnPermissionsChangeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addOnPermissionsChangeListener(_arg010);
                    reply.writeNoException();
                    return true;
                case 11:
                    IOnPermissionsChangeListener _arg011 =
                            IOnPermissionsChangeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeOnPermissionsChangeListener(_arg011);
                    reply.writeNoException();
                    return true;
                case 12:
                    String _arg012 = data.readString();
                    int _arg18 = data.readInt();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result7 =
                            getAllowlistedRestrictedPermissions(_arg012, _arg18, _arg25);
                    reply.writeNoException();
                    reply.writeStringList(_result7);
                    return true;
                case 13:
                    String _arg013 = data.readString();
                    String _arg19 = data.readString();
                    int _arg26 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 =
                            addAllowlistedRestrictedPermission(_arg013, _arg19, _arg26, _arg33);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    String _arg110 = data.readString();
                    int _arg27 = data.readInt();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 =
                            removeAllowlistedRestrictedPermission(_arg014, _arg110, _arg27, _arg34);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 15:
                    String _arg015 = data.readString();
                    String _arg111 = data.readString();
                    String _arg28 = data.readString();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    grantRuntimePermission(_arg015, _arg111, _arg28, _arg35);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg016 = data.readString();
                    String _arg112 = data.readString();
                    String _arg29 = data.readString();
                    int _arg36 = data.readInt();
                    String _arg42 = data.readString();
                    data.enforceNoDataAvail();
                    revokeRuntimePermission(_arg016, _arg112, _arg29, _arg36, _arg42);
                    reply.writeNoException();
                    return true;
                case 17:
                    String _arg017 = data.readString();
                    int _arg113 = data.readInt();
                    data.enforceNoDataAvail();
                    revokePostNotificationPermissionWithoutKillForTest(_arg017, _arg113);
                    reply.writeNoException();
                    return true;
                case 18:
                    String _arg018 = data.readString();
                    String _arg114 = data.readString();
                    int _arg210 = data.readInt();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 =
                            shouldShowRequestPermissionRationale(_arg018, _arg114, _arg210, _arg37);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 19:
                    String _arg019 = data.readString();
                    String _arg115 = data.readString();
                    int _arg211 = data.readInt();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 =
                            isPermissionRevokedByPolicy(_arg019, _arg115, _arg211, _arg38);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 20:
                    List<SplitPermissionInfoParcelable> _result12 = getSplitPermissions();
                    reply.writeNoException();
                    reply.writeTypedList(_result12, 1);
                    return true;
                case 21:
                    String _arg020 = data.readString();
                    int _arg116 = data.readInt();
                    int _arg212 = data.readInt();
                    long _arg39 = data.readLong();
                    long _arg43 = data.readLong();
                    data.enforceNoDataAvail();
                    startOneTimePermissionSession(_arg020, _arg116, _arg212, _arg39, _arg43);
                    reply.writeNoException();
                    return true;
                case 22:
                    String _arg021 = data.readString();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    stopOneTimePermissionSession(_arg021, _arg117);
                    reply.writeNoException();
                    return true;
                case 23:
                    int _arg022 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result13 = getAutoRevokeExemptionRequestedPackages(_arg022);
                    reply.writeNoException();
                    reply.writeStringList(_result13);
                    return true;
                case 24:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result14 = getAutoRevokeExemptionGrantedPackages(_arg023);
                    reply.writeNoException();
                    reply.writeStringList(_result14);
                    return true;
                case 25:
                    String _arg024 = data.readString();
                    boolean _arg118 = data.readBoolean();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = setAutoRevokeExempted(_arg024, _arg118, _arg213);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 26:
                    String _arg025 = data.readString();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result16 = isAutoRevokeExempted(_arg025, _arg119);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 27:
                    AttributionSourceState _arg026 =
                            (AttributionSourceState)
                                    data.readTypedObject(AttributionSourceState.CREATOR);
                    data.enforceNoDataAvail();
                    IBinder _result17 = registerAttributionSource(_arg026);
                    reply.writeNoException();
                    reply.writeStrongBinder(_result17);
                    return true;
                case 28:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result18 = getRegisteredAttributionSourceCount(_arg027);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 29:
                    AttributionSourceState _arg028 =
                            (AttributionSourceState)
                                    data.readTypedObject(AttributionSourceState.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result19 = isRegisteredAttributionSource(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 30:
                    String _arg029 = data.readString();
                    String _arg120 = data.readString();
                    String _arg214 = data.readString();
                    int _arg310 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result20 = checkPermission(_arg029, _arg120, _arg214, _arg310);
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 31:
                    int _arg030 = data.readInt();
                    String _arg121 = data.readString();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result21 = checkUidPermission(_arg030, _arg121, _arg215);
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 32:
                    String _arg031 = data.readString();
                    String _arg122 = data.readString();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    Map<String, PermissionManager.PermissionState> _result22 =
                            getAllPermissionStates(_arg031, _arg122, _arg216);
                    reply.writeNoException();
                    if (_result22 == null) {
                        reply.writeInt(-1);
                    } else {
                        reply.writeInt(_result22.size());
                        _result22.forEach(
                                new BiConsumer() { // from class:
                                                   // android.permission.IPermissionManager$Stub$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        IPermissionManager.Stub.lambda$onTransact$0(
                                                Parcel.this,
                                                (String) obj,
                                                (PermissionManager.PermissionState) obj2);
                                    }
                                });
                    }
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(
                Parcel reply, String k, PermissionManager.PermissionState v) {
            reply.writeString(k);
            reply.writeTypedObject(v, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IPermissionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPermissionManager.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionManager
            public ParceledListSlice getAllPermissionGroups(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionGroupInfo getPermissionGroupInfo(String groupName, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(groupName);
                    _data.writeInt(flags);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    PermissionGroupInfo _result =
                            (PermissionGroupInfo)
                                    _reply.readTypedObject(PermissionGroupInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionInfo getPermissionInfo(
                    String permissionName, String packageName, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(permissionName);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    PermissionInfo _result =
                            (PermissionInfo) _reply.readTypedObject(PermissionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public ParceledListSlice queryPermissionsByGroup(String groupName, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(groupName);
                    _data.writeInt(flags);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addPermission(PermissionInfo permissionInfo, boolean async)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeTypedObject(permissionInfo, 0);
                    _data.writeBoolean(async);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removePermission(String permissionName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(permissionName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionFlags(
                    String packageName,
                    String permissionName,
                    String persistentDeviceId,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlags(
                    String packageName,
                    String permissionName,
                    int flagMask,
                    int flagValues,
                    boolean checkAdjustPolicyFlagPermission,
                    String persistentDeviceId,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(flagMask);
                    _data.writeInt(flagValues);
                    _data.writeBoolean(checkAdjustPolicyFlagPermission);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(flagMask);
                    _data.writeInt(flagValues);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void addOnPermissionsChangeListener(IOnPermissionsChangeListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAllowlistedRestrictedPermissions(
                    String packageName, int flags, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addAllowlistedRestrictedPermission(
                    String packageName, String permissionName, int flags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean removeAllowlistedRestrictedPermission(
                    String packageName, String permissionName, int flags, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void grantRuntimePermission(
                    String packageName,
                    String permissionName,
                    String persistentDeviceId,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokeRuntimePermission(
                    String packageName,
                    String permissionName,
                    String persistentDeviceId,
                    int userId,
                    String reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    _data.writeString(reason);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokePostNotificationPermissionWithoutKillForTest(
                    String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean shouldShowRequestPermissionRationale(
                    String packageName, String permissionName, int deviceId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(deviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isPermissionRevokedByPolicy(
                    String packageName, String permissionName, int deviceId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeInt(deviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<SplitPermissionInfoParcelable> getSplitPermissions()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    List<SplitPermissionInfoParcelable> _result =
                            _reply.createTypedArrayList(SplitPermissionInfoParcelable.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void startOneTimePermissionSession(
                    String packageName,
                    int deviceId,
                    int userId,
                    long timeout,
                    long revokeAfterKilledDelay)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(deviceId);
                    _data.writeInt(userId);
                    _data.writeLong(timeout);
                    _data.writeLong(revokeAfterKilledDelay);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void stopOneTimePermissionSession(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionRequestedPackages(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionGrantedPackages(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean setAutoRevokeExempted(String packageName, boolean exempted, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(exempted);
                    _data.writeInt(userId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isAutoRevokeExempted(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public IBinder registerAttributionSource(AttributionSourceState source)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeTypedObject(source, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getRegisteredAttributionSourceCount(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isRegisteredAttributionSource(AttributionSourceState source)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeTypedObject(source, 0);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkPermission(
                    String packageName,
                    String permissionName,
                    String persistentDeviceId,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permissionName);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkUidPermission(int uid, String permissionName, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(permissionName);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public Map<String, PermissionManager.PermissionState> getAllPermissionStates(
                    String packageName, String persistentDeviceId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(persistentDeviceId);
                    _data.writeInt(userId);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int N = _reply.readInt();
                    final Map<String, PermissionManager.PermissionState> _result =
                            N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N)
                            .forEach(
                                    new IntConsumer() { // from class:
                                                        // android.permission.IPermissionManager$Stub$Proxy$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            IPermissionManager.Stub.Proxy
                                                    .lambda$getAllPermissionStates$0(
                                                            Parcel.this, _result, i);
                                        }
                                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$getAllPermissionStates$0(
                    Parcel _reply, Map _result, int i) {
                String k = _reply.readString();
                PermissionManager.PermissionState v =
                        (PermissionManager.PermissionState)
                                _reply.readTypedObject(PermissionManager.PermissionState.CREATOR);
                _result.put(k, v);
            }
        }

        protected void startOneTimePermissionSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS,
                    getCallingPid(),
                    getCallingUid());
        }

        protected void stopOneTimePermissionSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS,
                    getCallingPid(),
                    getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }
    }
}
