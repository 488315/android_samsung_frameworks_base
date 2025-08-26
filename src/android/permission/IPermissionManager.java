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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPermissionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPermissionManager)) {
                return (IPermissionManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice allPermissionGroups = getAllPermissionGroups(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPermissionGroups, 1);
                    return true;
                case 2:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(string, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PermissionInfo permissionInfo = getPermissionInfo(string2, string3, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionInfo, 1);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceQueryPermissionsByGroup = queryPermissionsByGroup(string4, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceQueryPermissionsByGroup, 1);
                    return true;
                case 5:
                    PermissionInfo permissionInfo2 = (PermissionInfo) parcel.readTypedObject(PermissionInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAddPermission = addPermission(permissionInfo2, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddPermission);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(string5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int permissionFlags = getPermissionFlags(string6, string7, string8, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionFlags);
                    return true;
                case 8:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    String string11 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlags(string9, string10, i8, i9, z2, string11, i10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlagsForAllApps(i11, i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IOnPermissionsChangeListener iOnPermissionsChangeListenerAsInterface = IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnPermissionsChangeListener(iOnPermissionsChangeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IOnPermissionsChangeListener iOnPermissionsChangeListenerAsInterface2 = IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnPermissionsChangeListener(iOnPermissionsChangeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(string12, i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowlistedRestrictedPermissions);
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddAllowlistedRestrictedPermission = addAllowlistedRestrictedPermission(string13, string14, i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAllowlistedRestrictedPermission);
                    return true;
                case 14:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAllowlistedRestrictedPermission = removeAllowlistedRestrictedPermission(string15, string16, i18, i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAllowlistedRestrictedPermission);
                    return true;
                case 15:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(string17, string18, string19, i20);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    int i21 = parcel.readInt();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(string20, string21, string22, i21, string23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string24 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokePostNotificationPermissionWithoutKillForTest(string24, i22);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(string25, string26, i23, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldShowRequestPermissionRationale);
                    return true;
                case 19:
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPermissionRevokedByPolicy = isPermissionRevokedByPolicy(string27, string28, i25, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPermissionRevokedByPolicy);
                    return true;
                case 20:
                    List<SplitPermissionInfoParcelable> splitPermissions = getSplitPermissions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(splitPermissions, 1);
                    return true;
                case 21:
                    String string29 = parcel.readString();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    startOneTimePermissionSession(string29, i27, i28, j, j2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String string30 = parcel.readString();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOneTimePermissionSession(string30, i29);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> autoRevokeExemptionRequestedPackages = getAutoRevokeExemptionRequestedPackages(i30);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionRequestedPackages);
                    return true;
                case 24:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> autoRevokeExemptionGrantedPackages = getAutoRevokeExemptionGrantedPackages(i31);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionGrantedPackages);
                    return true;
                case 25:
                    String string31 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean autoRevokeExempted = setAutoRevokeExempted(string31, z3, i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRevokeExempted);
                    return true;
                case 26:
                    String string32 = parcel.readString();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAutoRevokeExempted = isAutoRevokeExempted(string32, i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAutoRevokeExempted);
                    return true;
                case 27:
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    IBinder iBinderRegisterAttributionSource = registerAttributionSource(attributionSourceState);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderRegisterAttributionSource);
                    return true;
                case 28:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int registeredAttributionSourceCount = getRegisteredAttributionSourceCount(i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(registeredAttributionSourceCount);
                    return true;
                case 29:
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRegisteredAttributionSource = isRegisteredAttributionSource(attributionSourceState2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRegisteredAttributionSource);
                    return true;
                case 30:
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckPermission = checkPermission(string33, string34, string35, i35);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckPermission);
                    return true;
                case 31:
                    int i36 = parcel.readInt();
                    String string36 = parcel.readString();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iCheckUidPermission = checkUidPermission(i36, string36, i37);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckUidPermission);
                    return true;
                case 32:
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map<String, PermissionManager.PermissionState> allPermissionStates = getAllPermissionStates(string37, string38, i38);
                    parcel2.writeNoException();
                    if (allPermissionStates == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allPermissionStates.size());
                        allPermissionStates.forEach(new BiConsumer() { // from class: android.permission.IPermissionManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IPermissionManager.Stub.lambda$onTransact$0(parcel2, (String) obj, (PermissionManager.PermissionState) obj2);
                            }
                        });
                    }
                    return true;
                case 33:
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int permissionRequestState = getPermissionRequestState(string39, string40, i39);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PermissionGroupInfo) parcelObtain2.readTypedObject(PermissionGroupInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public PermissionInfo getPermissionInfo(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PermissionInfo) parcelObtain2.readTypedObject(PermissionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public ParceledListSlice queryPermissionsByGroup(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addPermission(PermissionInfo permissionInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(permissionInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removePermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionFlags(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlags(String str, String str2, int i, int i2, boolean z, String str3, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAllowlistedRestrictedPermissions(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean removeAllowlistedRestrictedPermission(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void grantRuntimePermission(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokeRuntimePermission(String str, String str2, String str3, int i, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokePostNotificationPermissionWithoutKillForTest(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean shouldShowRequestPermissionRationale(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isPermissionRevokedByPolicy(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<SplitPermissionInfoParcelable> getSplitPermissions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SplitPermissionInfoParcelable.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void startOneTimePermissionSession(String str, int i, int i2, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void stopOneTimePermissionSession(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionRequestedPackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public List<String> getAutoRevokeExemptionGrantedPackages(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean setAutoRevokeExempted(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isAutoRevokeExempted(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public IBinder registerAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getRegisteredAttributionSourceCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isRegisteredAttributionSource(AttributionSourceState attributionSourceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkPermission(String str, String str2, String str3, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkUidPermission(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public Map<String, PermissionManager.PermissionState> getAllPermissionStates(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    final HashMap map = i2 < 0 ? null : new HashMap();
                    IntStream.range(0, i2).forEach(new IntConsumer() { // from class: android.permission.IPermissionManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), (PermissionManager.PermissionState) parcel.readTypedObject(PermissionManager.PermissionState.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionRequestState(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPermissionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
