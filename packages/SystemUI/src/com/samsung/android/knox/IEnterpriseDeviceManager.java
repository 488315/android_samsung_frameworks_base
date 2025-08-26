package com.samsung.android.knox;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEnterpriseDeviceManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IEnterpriseDeviceManager";

    boolean activateDevicePermissions(List<String> list) throws RemoteException;

    boolean addAuthorizedUid(int i, int i2) throws RemoteException;

    int addPseudoAdminForParent(int i) throws RemoteException;

    byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException;

    boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException;

    boolean enableWipe(ContextInfo contextInfo) throws RemoteException;

    void enforceActiveAdminPermission(List<String> list) throws RemoteException;

    ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException;

    void enforceCaller(ContextInfo contextInfo, String str) throws RemoteException;

    void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) throws RemoteException;

    ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException;

    ContextInfo enforceDeviceOwnerAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException;

    ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException;

    void enforceKnoxV2Permission(String str, String str2) throws RemoteException;

    boolean enforceKnoxV2VerifyCaller(int i) throws RemoteException;

    ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException;

    ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException;

    void enforceWpcod(int i, boolean z) throws RemoteException;

    void enforceZtFwCaller(ContextInfo contextInfo, String str) throws RemoteException;

    ComponentName getActiveAdminComponent() throws RemoteException;

    List<ComponentName> getActiveAdmins(int i) throws RemoteException;

    List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) throws RemoteException;

    ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) throws RemoteException;

    boolean getAdminRemovable(ContextInfo contextInfo, String str) throws RemoteException;

    int getAdminUidForAuthorizedUid(int i) throws RemoteException;

    int getAuthorizedUidForAdminUid(int i) throws RemoteException;

    int getConstrainedState() throws RemoteException;

    String getKPUPackageName() throws RemoteException;

    List<String> getMamPermissions(String str) throws RemoteException;

    void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException;

    int getUserStatus(int i) throws RemoteException;

    boolean hasAnyActiveAdmin() throws RemoteException;

    boolean hasDelegatedPermission(String str, int i, String str2) throws RemoteException;

    boolean hasGrantedPolicy(ComponentName componentName, int i) throws RemoteException;

    boolean isAdminActive(ComponentName componentName) throws RemoteException;

    boolean isAdminRemovable(ComponentName componentName) throws RemoteException;

    boolean isAdminRemovableInternal(ComponentName componentName, int i) throws RemoteException;

    boolean isCallerValidKPU(ContextInfo contextInfo) throws RemoteException;

    boolean isCameraEnabledNative(ContextInfo contextInfo) throws RemoteException;

    boolean isEmailAdminPkg(String str) throws RemoteException;

    boolean isKPUPlatformSigned(String str, int i) throws RemoteException;

    boolean isMdmAdminPresent() throws RemoteException;

    boolean isMdmAdminPresentAsUser(int i) throws RemoteException;

    boolean isPermissionIncludedOnManifest(String str) throws RemoteException;

    boolean isPossibleTransferOwenerShip(ComponentName componentName) throws RemoteException;

    boolean isRestrictedByConstrainedState(int i) throws RemoteException;

    boolean isUidDeviceOrProfileOwner(int i) throws RemoteException;

    boolean isUserSelectable(String str) throws RemoteException;

    boolean keychainMarkedReset(ContextInfo contextInfo) throws RemoteException;

    boolean migrateKnoxPoliciesForWpcod(int i) throws RemoteException;

    boolean packageHasActiveAdmins(String str) throws RemoteException;

    boolean packageHasActiveAdminsAsUser(String str, int i) throws RemoteException;

    String readUmcEnrollmentData(ContextInfo contextInfo) throws RemoteException;

    void reconcileAdmin(ComponentName componentName, int i) throws RemoteException;

    void removeActiveAdmin(ComponentName componentName) throws RemoteException;

    void removeActiveAdminFromDpm(ComponentName componentName, int i) throws RemoteException;

    boolean removeAuthorizedUid(int i, int i2) throws RemoteException;

    void sendIntent(int i) throws RemoteException;

    boolean sendKnoxAnalyticsDeviceStatus() throws RemoteException;

    void setActiveAdmin(ComponentName componentName, boolean z) throws RemoteException;

    void setActiveAdminSilent(ComponentName componentName) throws RemoteException;

    boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) throws RemoteException;

    void setAndroidLogProperty(String str) throws RemoteException;

    int setB2BMode(boolean z) throws RemoteException;

    void setUserSelectable(int i, String str, boolean z) throws RemoteException;

    void startDualDARServices() throws RemoteException;

    void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) throws RemoteException;

    void updateNotificationExemption(ContextInfo contextInfo, String str) throws RemoteException;

    boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) throws RemoteException;

    public class Default implements IEnterpriseDeviceManager {
        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean activateDevicePermissions(List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean addAuthorizedUid(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int addPseudoAdminForParent(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean enableWipe(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforceDeviceOwnerAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean enforceKnoxV2VerifyCaller(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ComponentName getActiveAdminComponent() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean getAdminRemovable(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int getAdminUidForAuthorizedUid(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int getAuthorizedUidForAdminUid(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int getConstrainedState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public String getKPUPackageName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public List<String> getMamPermissions(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int getUserStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean hasAnyActiveAdmin() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean hasDelegatedPermission(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean hasGrantedPolicy(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isAdminActive(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isAdminRemovable(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isAdminRemovableInternal(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isCallerValidKPU(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isCameraEnabledNative(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isEmailAdminPkg(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isKPUPlatformSigned(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isMdmAdminPresent() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isMdmAdminPresentAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isPermissionIncludedOnManifest(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isPossibleTransferOwenerShip(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isRestrictedByConstrainedState(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isUidDeviceOrProfileOwner(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean isUserSelectable(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean keychainMarkedReset(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean migrateKnoxPoliciesForWpcod(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean packageHasActiveAdmins(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean packageHasActiveAdminsAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public String readUmcEnrollmentData(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean removeAuthorizedUid(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean sendKnoxAnalyticsDeviceStatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public int setB2BMode(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void startDualDARServices() throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceActiveAdminPermission(List<String> list) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void removeActiveAdmin(ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void sendIntent(int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void setActiveAdminSilent(ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void setAndroidLogProperty(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceCaller(ContextInfo contextInfo, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceKnoxV2Permission(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceWpcod(int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void enforceZtFwCaller(ContextInfo contextInfo, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void reconcileAdmin(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void removeActiveAdminFromDpm(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void setActiveAdmin(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void updateNotificationExemption(ContextInfo contextInfo, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void setUserSelectable(int i, String str, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IEnterpriseDeviceManager {
        public static final int TRANSACTION_activateDevicePermissions = 27;
        public static final int TRANSACTION_addAuthorizedUid = 35;
        public static final int TRANSACTION_addPseudoAdminForParent = 63;
        public static final int TRANSACTION_captureUmcLogs = 45;
        public static final int TRANSACTION_disableConstrainedState = 30;
        public static final int TRANSACTION_enableConstrainedState = 29;
        public static final int TRANSACTION_enableWipe = 39;
        public static final int TRANSACTION_enforceActiveAdminPermission = 10;
        public static final int TRANSACTION_enforceActiveAdminPermissionByContext = 17;
        public static final int TRANSACTION_enforceCaller = 47;
        public static final int TRANSACTION_enforceComponentCheck = 21;
        public static final int TRANSACTION_enforceContainerOwnerShipPermissionByContext = 19;
        public static final int TRANSACTION_enforceDeviceOwnerAndActiveAdminPermission = 51;
        public static final int TRANSACTION_enforceDoPoOnlyPermissionByContext = 50;
        public static final int TRANSACTION_enforceKnoxV2Permission = 52;
        public static final int TRANSACTION_enforceKnoxV2VerifyCaller = 53;
        public static final int TRANSACTION_enforceOwnerOnlyAndActiveAdminPermission = 20;
        public static final int TRANSACTION_enforcePermissionByContext = 18;
        public static final int TRANSACTION_enforceWpcod = 65;
        public static final int TRANSACTION_enforceZtFwCaller = 48;
        public static final int TRANSACTION_getActiveAdminComponent = 2;
        public static final int TRANSACTION_getActiveAdmins = 3;
        public static final int TRANSACTION_getActiveAdminsInfo = 23;
        public static final int TRANSACTION_getAdminContextIfCallerInCertWhiteList = 44;
        public static final int TRANSACTION_getAdminRemovable = 8;
        public static final int TRANSACTION_getAdminUidForAuthorizedUid = 38;
        public static final int TRANSACTION_getAuthorizedUidForAdminUid = 37;
        public static final int TRANSACTION_getConstrainedState = 32;
        public static final int TRANSACTION_getKPUPackageName = 57;
        public static final int TRANSACTION_getMamPermissions = 70;
        public static final int TRANSACTION_getRemoveWarning = 9;
        public static final int TRANSACTION_getUserStatus = 56;
        public static final int TRANSACTION_hasAnyActiveAdmin = 12;
        public static final int TRANSACTION_hasDelegatedPermission = 54;
        public static final int TRANSACTION_hasGrantedPolicy = 5;
        public static final int TRANSACTION_isAdminActive = 1;
        public static final int TRANSACTION_isAdminRemovable = 14;
        public static final int TRANSACTION_isAdminRemovableInternal = 15;
        public static final int TRANSACTION_isCallerValidKPU = 60;
        public static final int TRANSACTION_isCameraEnabledNative = 46;
        public static final int TRANSACTION_isEmailAdminPkg = 69;
        public static final int TRANSACTION_isKPUPlatformSigned = 58;
        public static final int TRANSACTION_isMdmAdminPresent = 42;
        public static final int TRANSACTION_isMdmAdminPresentAsUser = 43;
        public static final int TRANSACTION_isPermissionIncludedOnManifest = 62;
        public static final int TRANSACTION_isPossibleTransferOwenerShip = 26;
        public static final int TRANSACTION_isRestrictedByConstrainedState = 31;
        public static final int TRANSACTION_isUidDeviceOrProfileOwner = 61;
        public static final int TRANSACTION_isUserSelectable = 66;
        public static final int TRANSACTION_keychainMarkedReset = 68;
        public static final int TRANSACTION_migrateKnoxPoliciesForWpcod = 64;
        public static final int TRANSACTION_packageHasActiveAdmins = 13;
        public static final int TRANSACTION_packageHasActiveAdminsAsUser = 28;
        public static final int TRANSACTION_readUmcEnrollmentData = 41;
        public static final int TRANSACTION_reconcileAdmin = 24;
        public static final int TRANSACTION_removeActiveAdmin = 6;
        public static final int TRANSACTION_removeActiveAdminFromDpm = 11;
        public static final int TRANSACTION_removeAuthorizedUid = 36;
        public static final int TRANSACTION_sendIntent = 34;
        public static final int TRANSACTION_sendKnoxAnalyticsDeviceStatus = 33;
        public static final int TRANSACTION_setActiveAdmin = 4;
        public static final int TRANSACTION_setActiveAdminSilent = 22;
        public static final int TRANSACTION_setAdminRemovable = 7;
        public static final int TRANSACTION_setAndroidLogProperty = 49;
        public static final int TRANSACTION_setB2BMode = 16;
        public static final int TRANSACTION_setUserSelectable = 67;
        public static final int TRANSACTION_startDualDARServices = 59;
        public static final int TRANSACTION_transferOwnerShip = 25;
        public static final int TRANSACTION_updateNotificationExemption = 55;
        public static final int TRANSACTION_writeUmcEnrollmentData = 40;

        class Proxy implements IEnterpriseDeviceManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean activateDevicePermissions(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean addAuthorizedUid(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int addPseudoAdminForParent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enableWipe(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceActiveAdminPermission(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceCaller(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceDeviceOwnerAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceKnoxV2Permission(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enforceKnoxV2VerifyCaller(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceWpcod(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceZtFwCaller(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ComponentName getActiveAdminComponent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(EnterpriseDeviceAdminInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ContextInfo) parcelObtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean getAdminRemovable(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getAdminUidForAuthorizedUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getAuthorizedUidForAdminUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getConstrainedState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseDeviceManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public String getKPUPackageName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<String> getMamPermissions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getUserStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasAnyActiveAdmin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasDelegatedPermission(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasGrantedPolicy(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminActive(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminRemovable(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminRemovableInternal(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isCallerValidKPU(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isCameraEnabledNative(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isEmailAdminPkg(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isKPUPlatformSigned(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isMdmAdminPresent() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isMdmAdminPresentAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isPermissionIncludedOnManifest(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isPossibleTransferOwenerShip(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isRestrictedByConstrainedState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isUidDeviceOrProfileOwner(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isUserSelectable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean keychainMarkedReset(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean migrateKnoxPoliciesForWpcod(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean packageHasActiveAdmins(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean packageHasActiveAdminsAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public String readUmcEnrollmentData(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void reconcileAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void removeActiveAdmin(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void removeActiveAdminFromDpm(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean removeAuthorizedUid(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void sendIntent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean sendKnoxAnalyticsDeviceStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setActiveAdmin(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setActiveAdminSilent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setAndroidLogProperty(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int setB2BMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setUserSelectable(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void startDualDARServices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(componentName2, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void updateNotificationExemption(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnterpriseDeviceManager.DESCRIPTOR);
        }

        public static IEnterpriseDeviceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEnterpriseDeviceManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEnterpriseDeviceManager)) ? new Proxy(iBinder) : (IEnterpriseDeviceManager) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseDeviceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseDeviceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminActive = isAdminActive(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminActive);
                    return true;
                case 2:
                    ComponentName activeAdminComponent = getActiveAdminComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeAdminComponent, 1);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> activeAdmins = getActiveAdmins(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdmins, 1);
                    return true;
                case 4:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActiveAdmin(componentName2, z);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasGrantedPolicy = hasGrantedPolicy(componentName3, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasGrantedPolicy);
                    return true;
                case 6:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeActiveAdmin(componentName4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean adminRemovable = setAdminRemovable(contextInfo, z2, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adminRemovable);
                    return true;
                case 8:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean adminRemovable2 = getAdminRemovable(contextInfo2, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adminRemovable2);
                    return true;
                case 9:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRemoveWarning(componentName5, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    enforceActiveAdminPermission(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeActiveAdminFromDpm(componentName6, i5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean zHasAnyActiveAdmin = hasAnyActiveAdmin();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasAnyActiveAdmin);
                    return true;
                case 13:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zPackageHasActiveAdmins = packageHasActiveAdmins(string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPackageHasActiveAdmins);
                    return true;
                case 14:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminRemovable = isAdminRemovable(componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminRemovable);
                    return true;
                case 15:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdminRemovableInternal = isAdminRemovableInternal(componentName8, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdminRemovableInternal);
                    return true;
                case 16:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int b2BMode = setB2BMode(z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(b2BMode);
                    return true;
                case 17:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforceActiveAdminPermissionByContext = enforceActiveAdminPermissionByContext(contextInfo3, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforceActiveAdminPermissionByContext, 1);
                    return true;
                case 18:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforcePermissionByContext = enforcePermissionByContext(contextInfo4, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforcePermissionByContext, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforceContainerOwnerShipPermissionByContext = enforceContainerOwnerShipPermissionByContext(contextInfo5, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforceContainerOwnerShipPermissionByContext, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforceOwnerOnlyAndActiveAdminPermission = enforceOwnerOnlyAndActiveAdminPermission(contextInfo6, arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforceOwnerOnlyAndActiveAdminPermission, 1);
                    return true;
                case 21:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    enforceComponentCheck(contextInfo7, componentName9);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActiveAdminSilent(componentName10);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<EnterpriseDeviceAdminInfo> activeAdminsInfo = getActiveAdminsInfo(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdminsInfo, 1);
                    return true;
                case 24:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reconcileAdmin(componentName11, i8);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    Parcelable.Creator creator = ComponentName.CREATOR;
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(creator);
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(creator);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    transferOwnerShip(componentName12, componentName13, i9);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsPossibleTransferOwenerShip = isPossibleTransferOwenerShip(componentName14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPossibleTransferOwenerShip);
                    return true;
                case 27:
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zActivateDevicePermissions = activateDevicePermissions(arrayListCreateStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivateDevicePermissions);
                    return true;
                case 28:
                    String string4 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPackageHasActiveAdminsAsUser = packageHasActiveAdminsAsUser(string4, i10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPackageHasActiveAdminsAsUser);
                    return true;
                case 29:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableConstrainedState = enableConstrainedState(contextInfo8, string5, string6, string7, string8, i11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableConstrainedState);
                    return true;
                case 30:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableConstrainedState = disableConstrainedState(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableConstrainedState);
                    return true;
                case 31:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRestrictedByConstrainedState = isRestrictedByConstrainedState(i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRestrictedByConstrainedState);
                    return true;
                case 32:
                    int constrainedState = getConstrainedState();
                    parcel2.writeNoException();
                    parcel2.writeInt(constrainedState);
                    return true;
                case 33:
                    boolean zSendKnoxAnalyticsDeviceStatus = sendKnoxAnalyticsDeviceStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendKnoxAnalyticsDeviceStatus);
                    return true;
                case 34:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendIntent(i13);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddAuthorizedUid = addAuthorizedUid(i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddAuthorizedUid);
                    return true;
                case 36:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAuthorizedUid = removeAuthorizedUid(i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAuthorizedUid);
                    return true;
                case 37:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int authorizedUidForAdminUid = getAuthorizedUidForAdminUid(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(authorizedUidForAdminUid);
                    return true;
                case 38:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int adminUidForAuthorizedUid = getAdminUidForAuthorizedUid(i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(adminUidForAuthorizedUid);
                    return true;
                case 39:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zEnableWipe = enableWipe(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableWipe);
                    return true;
                case 40:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zWriteUmcEnrollmentData = writeUmcEnrollmentData(contextInfo11, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWriteUmcEnrollmentData);
                    return true;
                case 41:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String umcEnrollmentData = readUmcEnrollmentData(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeString(umcEnrollmentData);
                    return true;
                case 42:
                    boolean zIsMdmAdminPresent = isMdmAdminPresent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMdmAdminPresent);
                    return true;
                case 43:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMdmAdminPresentAsUser = isMdmAdminPresentAsUser(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMdmAdminPresentAsUser);
                    return true;
                case 44:
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo adminContextIfCallerInCertWhiteList = getAdminContextIfCallerInCertWhiteList(arrayListCreateStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminContextIfCallerInCertWhiteList, 1);
                    return true;
                case 45:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    byte[] bArrCaptureUmcLogs = captureUmcLogs(contextInfo13, string10, arrayListCreateStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrCaptureUmcLogs);
                    return true;
                case 46:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCameraEnabledNative = isCameraEnabledNative(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraEnabledNative);
                    return true;
                case 47:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceCaller(contextInfo15, string11);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceZtFwCaller(contextInfo16, string12);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAndroidLogProperty(string13);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList9 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforceDoPoOnlyPermissionByContext = enforceDoPoOnlyPermissionByContext(contextInfo17, arrayListCreateStringArrayList9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforceDoPoOnlyPermissionByContext, 1);
                    return true;
                case 51:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList10 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo contextInfoEnforceDeviceOwnerAndActiveAdminPermission = enforceDeviceOwnerAndActiveAdminPermission(contextInfo18, arrayListCreateStringArrayList10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(contextInfoEnforceDeviceOwnerAndActiveAdminPermission, 1);
                    return true;
                case 52:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceKnoxV2Permission(string14, string15);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnforceKnoxV2VerifyCaller = enforceKnoxV2VerifyCaller(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnforceKnoxV2VerifyCaller);
                    return true;
                case 54:
                    String string16 = parcel.readString();
                    int i22 = parcel.readInt();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasDelegatedPermission = hasDelegatedPermission(string16, i22, string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasDelegatedPermission);
                    return true;
                case 55:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateNotificationExemption(contextInfo19, string18);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatus = getUserStatus(i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatus);
                    return true;
                case 57:
                    String kPUPackageName = getKPUPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(kPUPackageName);
                    return true;
                case 58:
                    String string19 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsKPUPlatformSigned = isKPUPlatformSigned(string19, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsKPUPlatformSigned);
                    return true;
                case 59:
                    startDualDARServices();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCallerValidKPU = isCallerValidKPU(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerValidKPU);
                    return true;
                case 61:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsUidDeviceOrProfileOwner = isUidDeviceOrProfileOwner(i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUidDeviceOrProfileOwner);
                    return true;
                case 62:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPermissionIncludedOnManifest = isPermissionIncludedOnManifest(string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPermissionIncludedOnManifest);
                    return true;
                case 63:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddPseudoAdminForParent = addPseudoAdminForParent(i26);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddPseudoAdminForParent);
                    return true;
                case 64:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMigrateKnoxPoliciesForWpcod = migrateKnoxPoliciesForWpcod(i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMigrateKnoxPoliciesForWpcod);
                    return true;
                case 65:
                    int i28 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enforceWpcod(i28, z4);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsUserSelectable = isUserSelectable(string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUserSelectable);
                    return true;
                case 67:
                    int i29 = parcel.readInt();
                    String string22 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(i29, string22, z5);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zKeychainMarkedReset = keychainMarkedReset(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zKeychainMarkedReset);
                    return true;
                case 69:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEmailAdminPkg = isEmailAdminPkg(string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEmailAdminPkg);
                    return true;
                case 70:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> mamPermissions = getMamPermissions(string24);
                    parcel2.writeNoException();
                    parcel2.writeStringList(mamPermissions);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
