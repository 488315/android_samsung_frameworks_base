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
import android.permission.IOnPermissionsChangeListener;
import android.permission.IPermissionManager;
import android.permission.PermissionManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes3.dex */
public interface IPermissionManager extends IInterface {
    public static final String DESCRIPTOR = "android.permission.IPermissionManager";

    public static class Default implements IPermissionManager {
        @Override // android.permission.IPermissionManager
        public boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public boolean addPermission(PermissionInfo permissionInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public int checkPermission(String str, String str2, String str3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public int checkUidPermission(int i, String str, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public ParceledListSlice getAllPermissionGroups(int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public Map<String, PermissionManager.PermissionState> getAllPermissionStates(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public List<String> getAllowlistedRestrictedPermissions(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public List<String> getAutoRevokeExemptionGrantedPackages(int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public List<String> getAutoRevokeExemptionRequestedPackages(int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public int getPermissionFlags(String str, String str2, String str3, int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public int getPermissionRequestState(String str, String str2, int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public int getRegisteredAttributionSourceCount(int i) throws RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public void grantRuntimePermission(String str, String str2, String str3, int i) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public boolean isAutoRevokeExempted(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public IBinder registerAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void removePermission(String str) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public boolean setAutoRevokeExempted(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void startOneTimePermissionSession(String str, int i, int i2, long j, long j2) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void stopOneTimePermissionSession(String str, int i) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) throws RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException {
        }
    }

    boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException;

    void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException;

    boolean addPermission(PermissionInfo permissionInfo, boolean z) throws RemoteException;

    int checkPermission(String str, String str2, String str3, int i) throws RemoteException;

    int checkUidPermission(int i, String str, int i2) throws RemoteException;

    ParceledListSlice getAllPermissionGroups(int i) throws RemoteException;

    Map<String, PermissionManager.PermissionState> getAllPermissionStates(String str, String str2, int i) throws RemoteException;

    List<String> getAllowlistedRestrictedPermissions(String str, int i, int i2) throws RemoteException;

    List<String> getAutoRevokeExemptionGrantedPackages(int i) throws RemoteException;

    List<String> getAutoRevokeExemptionRequestedPackages(int i) throws RemoteException;

    int getPermissionFlags(String str, String str2, String str3, int i) throws RemoteException;

    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException;

    int getPermissionRequestState(String str, String str2, int i) throws RemoteException;

    int getRegisteredAttributionSourceCount(int i) throws RemoteException;

    List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException;

    void grantRuntimePermission(String str, String str2, String str3, int i) throws RemoteException;

    boolean isAutoRevokeExempted(String str, int i) throws RemoteException;

    boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2) throws RemoteException;

    boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException;

    ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException;

    IBinder registerAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException;

    boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException;

    void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException;

    void removePermission(String str) throws RemoteException;

    void revokePostNotificationPermissionWithoutKillForTest(String str, int i) throws RemoteException;

    void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) throws RemoteException;

    boolean setAutoRevokeExempted(String str, boolean z, int i) throws RemoteException;

    boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2) throws RemoteException;

    void startOneTimePermissionSession(String str, int i, int i2, long j, long j2) throws RemoteException;

    void stopOneTimePermissionSession(String str, int i) throws RemoteException;

    void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) throws RemoteException;

    void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements IPermissionManager {
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
        static final int TRANSACTION_getPermissionRequestState = 33;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 32;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IPermissionManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IPermissionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPermissionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPermissionManager)) {
                return (IPermissionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 33:
                    return "getPermissionRequestState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPermissionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPermissionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allPermissionGroups = getAllPermissionGroups(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPermissionGroups, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionInfo permissionInfo = getPermissionInfo(readString2, readString3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionInfo, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice queryPermissionsByGroup = queryPermissionsByGroup(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryPermissionsByGroup, 1);
                    return true;
                case 5:
                    PermissionInfo permissionInfo2 = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean addPermission = addPermission(permissionInfo2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermission);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(readString5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int permissionFlags = getPermissionFlags(readString6, readString7, readString8, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionFlags);
                    return true;
                case 8:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlags(readString9, readString10, readInt6, readInt7, readBoolean2, readString11, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlagsForAllApps(readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IOnPermissionsChangeListener asInterface = IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnPermissionsChangeListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IOnPermissionsChangeListener asInterface2 = IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnPermissionsChangeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(readString12, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowlistedRestrictedPermissions);
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addAllowlistedRestrictedPermission = addAllowlistedRestrictedPermission(readString13, readString14, readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAllowlistedRestrictedPermission);
                    return true;
                case 14:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeAllowlistedRestrictedPermission = removeAllowlistedRestrictedPermission(readString15, readString16, readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAllowlistedRestrictedPermission);
                    return true;
                case 15:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(readString17, readString18, readString19, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(readString20, readString21, readString22, readInt19, readString23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString24 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokePostNotificationPermissionWithoutKillForTest(readString24, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(readString25, readString26, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowRequestPermissionRationale);
                    return true;
                case 19:
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionRevokedByPolicy = isPermissionRevokedByPolicy(readString27, readString28, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionRevokedByPolicy);
                    return true;
                case 20:
                    List<SplitPermissionInfoParcelable> splitPermissions = getSplitPermissions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(splitPermissions, 1);
                    return true;
                case 21:
                    String readString29 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    startOneTimePermissionSession(readString29, readInt25, readInt26, readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String readString30 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOneTimePermissionSession(readString30, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> autoRevokeExemptionRequestedPackages = getAutoRevokeExemptionRequestedPackages(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionRequestedPackages);
                    return true;
                case 24:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> autoRevokeExemptionGrantedPackages = getAutoRevokeExemptionGrantedPackages(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionGrantedPackages);
                    return true;
                case 25:
                    String readString31 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean autoRevokeExempted = setAutoRevokeExempted(readString31, readBoolean3, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRevokeExempted);
                    return true;
                case 26:
                    String readString32 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAutoRevokeExempted = isAutoRevokeExempted(readString32, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoRevokeExempted);
                    return true;
                case 27:
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder registerAttributionSource = registerAttributionSource(attributionSourceState);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(registerAttributionSource);
                    return true;
                case 28:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int registeredAttributionSourceCount = getRegisteredAttributionSourceCount(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(registeredAttributionSourceCount);
                    return true;
                case 29:
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRegisteredAttributionSource = isRegisteredAttributionSource(attributionSourceState2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRegisteredAttributionSource);
                    return true;
                case 30:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString33, readString34, readString35, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 31:
                    int readInt34 = parcel.readInt();
                    String readString36 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidPermission = checkUidPermission(readInt34, readString36, readInt35);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidPermission);
                    return true;
                case 32:
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, PermissionManager.PermissionState> allPermissionStates = getAllPermissionStates(readString37, readString38, readInt36);
                    parcel2.writeNoException();
                    if (allPermissionStates == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allPermissionStates.size());
                        allPermissionStates.forEach(new BiConsumer() { // from class: android.permission.IPermissionManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPermissionManager.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (PermissionManager.PermissionState) obj2);
                            }
                        });
                    }
                    return true;
                case 33:
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int permissionRequestState = getPermissionRequestState(readString39, readString40, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionRequestState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, PermissionManager.PermissionState permissionState) {
            parcel.writeString(str);
            parcel.writeTypedObject(permissionState, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IPermissionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPermissionManager.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionManager
            public ParceledListSlice getAllPermissionGroups(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PermissionGroupInfo) obtain2.readTypedObject(PermissionGroupInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PermissionInfo) obtain2.readTypedObject(PermissionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addPermission(PermissionInfo permissionInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removePermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionFlags(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAllowlistedRestrictedPermissions(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void grantRuntimePermission(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SplitPermissionInfoParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void startOneTimePermissionSession(String str, int i, int i2, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void stopOneTimePermissionSession(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionRequestedPackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionGrantedPackages(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean setAutoRevokeExempted(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isAutoRevokeExempted(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public IBinder registerAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getRegisteredAttributionSourceCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkPermission(String str, String str2, String str3, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkUidPermission(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public Map<String, PermissionManager.PermissionState> getAllPermissionStates(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.permission.IPermissionManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (PermissionManager.PermissionState) Parcel.this.readTypedObject(PermissionManager.PermissionState.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionRequestState(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void startOneTimePermissionSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS, getCallingPid(), getCallingUid());
        }

        protected void stopOneTimePermissionSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS, getCallingPid(), getCallingUid());
        }
    }
}
