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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IEnterpriseDeviceManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean activateDevicePermissions(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean addAuthorizedUid(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int addPseudoAdminForParent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean disableConstrainedState(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enableWipe(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceActiveAdminPermission(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceCaller(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceDeviceOwnerAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceKnoxV2Permission(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean enforceKnoxV2VerifyCaller(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceWpcod(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void enforceZtFwCaller(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ComponentName getActiveAdminComponent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<ComponentName> getActiveAdmins(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(EnterpriseDeviceAdminInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean getAdminRemovable(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getAdminUidForAuthorizedUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getAuthorizedUidForAdminUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getConstrainedState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseDeviceManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public String getKPUPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public List<String> getMamPermissions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int getUserStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasAnyActiveAdmin() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasDelegatedPermission(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean hasGrantedPolicy(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminActive(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminRemovable(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isAdminRemovableInternal(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isCallerValidKPU(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isCameraEnabledNative(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isEmailAdminPkg(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isKPUPlatformSigned(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isMdmAdminPresent() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isMdmAdminPresentAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isPermissionIncludedOnManifest(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isPossibleTransferOwenerShip(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isRestrictedByConstrainedState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isUidDeviceOrProfileOwner(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean isUserSelectable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean keychainMarkedReset(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean migrateKnoxPoliciesForWpcod(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean packageHasActiveAdmins(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean packageHasActiveAdminsAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public String readUmcEnrollmentData(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void reconcileAdmin(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void removeActiveAdmin(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void removeActiveAdminFromDpm(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean removeAuthorizedUid(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void sendIntent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean sendKnoxAnalyticsDeviceStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setActiveAdmin(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setActiveAdminSilent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setAndroidLogProperty(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public int setB2BMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void setUserSelectable(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void startDualDARServices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public void updateNotificationExemption(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnterpriseDeviceManager.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEnterpriseDeviceManager)) ? new Proxy(iBinder) : (IEnterpriseDeviceManager) queryLocalInterface;
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
                    boolean isAdminActive = isAdminActive(componentName);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminActive);
                    return true;
                case 2:
                    ComponentName activeAdminComponent = getActiveAdminComponent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeAdminComponent, 1);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> activeAdmins = getActiveAdmins(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdmins, 1);
                    return true;
                case 4:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActiveAdmin(componentName2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasGrantedPolicy = hasGrantedPolicy(componentName3, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasGrantedPolicy);
                    return true;
                case 6:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeActiveAdmin(componentName4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean adminRemovable = setAdminRemovable(contextInfo, readBoolean2, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adminRemovable);
                    return true;
                case 8:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean adminRemovable2 = getAdminRemovable(contextInfo2, readString2);
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
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    enforceActiveAdminPermission(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeActiveAdminFromDpm(componentName6, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean hasAnyActiveAdmin = hasAnyActiveAdmin();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasAnyActiveAdmin);
                    return true;
                case 13:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean packageHasActiveAdmins = packageHasActiveAdmins(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageHasActiveAdmins);
                    return true;
                case 14:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAdminRemovable = isAdminRemovable(componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminRemovable);
                    return true;
                case 15:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdminRemovableInternal = isAdminRemovableInternal(componentName8, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminRemovableInternal);
                    return true;
                case 16:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int b2BMode = setB2BMode(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(b2BMode);
                    return true;
                case 17:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceActiveAdminPermissionByContext = enforceActiveAdminPermissionByContext(contextInfo3, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceActiveAdminPermissionByContext, 1);
                    return true;
                case 18:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforcePermissionByContext = enforcePermissionByContext(contextInfo4, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforcePermissionByContext, 1);
                    return true;
                case 19:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceContainerOwnerShipPermissionByContext = enforceContainerOwnerShipPermissionByContext(contextInfo5, createStringArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceContainerOwnerShipPermissionByContext, 1);
                    return true;
                case 20:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceOwnerOnlyAndActiveAdminPermission = enforceOwnerOnlyAndActiveAdminPermission(contextInfo6, createStringArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceOwnerOnlyAndActiveAdminPermission, 1);
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
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<EnterpriseDeviceAdminInfo> activeAdminsInfo = getActiveAdminsInfo(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeAdminsInfo, 1);
                    return true;
                case 24:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reconcileAdmin(componentName11, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    Parcelable.Creator creator = ComponentName.CREATOR;
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(creator);
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(creator);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    transferOwnerShip(componentName12, componentName13, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPossibleTransferOwenerShip = isPossibleTransferOwenerShip(componentName14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPossibleTransferOwenerShip);
                    return true;
                case 27:
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean activateDevicePermissions = activateDevicePermissions(createStringArrayList6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activateDevicePermissions);
                    return true;
                case 28:
                    String readString4 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean packageHasActiveAdminsAsUser = packageHasActiveAdminsAsUser(readString4, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageHasActiveAdminsAsUser);
                    return true;
                case 29:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableConstrainedState = enableConstrainedState(contextInfo8, readString5, readString6, readString7, readString8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableConstrainedState);
                    return true;
                case 30:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableConstrainedState = disableConstrainedState(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableConstrainedState);
                    return true;
                case 31:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRestrictedByConstrainedState = isRestrictedByConstrainedState(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRestrictedByConstrainedState);
                    return true;
                case 32:
                    int constrainedState = getConstrainedState();
                    parcel2.writeNoException();
                    parcel2.writeInt(constrainedState);
                    return true;
                case 33:
                    boolean sendKnoxAnalyticsDeviceStatus = sendKnoxAnalyticsDeviceStatus();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendKnoxAnalyticsDeviceStatus);
                    return true;
                case 34:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendIntent(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addAuthorizedUid = addAuthorizedUid(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAuthorizedUid);
                    return true;
                case 36:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeAuthorizedUid = removeAuthorizedUid(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAuthorizedUid);
                    return true;
                case 37:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int authorizedUidForAdminUid = getAuthorizedUidForAdminUid(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(authorizedUidForAdminUid);
                    return true;
                case 38:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int adminUidForAuthorizedUid = getAdminUidForAuthorizedUid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(adminUidForAuthorizedUid);
                    return true;
                case 39:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableWipe = enableWipe(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableWipe);
                    return true;
                case 40:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean writeUmcEnrollmentData = writeUmcEnrollmentData(contextInfo11, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeUmcEnrollmentData);
                    return true;
                case 41:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String readUmcEnrollmentData = readUmcEnrollmentData(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeString(readUmcEnrollmentData);
                    return true;
                case 42:
                    boolean isMdmAdminPresent = isMdmAdminPresent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMdmAdminPresent);
                    return true;
                case 43:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMdmAdminPresentAsUser = isMdmAdminPresentAsUser(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMdmAdminPresentAsUser);
                    return true;
                case 44:
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo adminContextIfCallerInCertWhiteList = getAdminContextIfCallerInCertWhiteList(createStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminContextIfCallerInCertWhiteList, 1);
                    return true;
                case 45:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    byte[] captureUmcLogs = captureUmcLogs(contextInfo13, readString10, createStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(captureUmcLogs);
                    return true;
                case 46:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCameraEnabledNative = isCameraEnabledNative(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraEnabledNative);
                    return true;
                case 47:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceCaller(contextInfo15, readString11);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceZtFwCaller(contextInfo16, readString12);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAndroidLogProperty(readString13);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList9 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceDoPoOnlyPermissionByContext = enforceDoPoOnlyPermissionByContext(contextInfo17, createStringArrayList9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceDoPoOnlyPermissionByContext, 1);
                    return true;
                case 51:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList10 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceDeviceOwnerAndActiveAdminPermission = enforceDeviceOwnerAndActiveAdminPermission(contextInfo18, createStringArrayList10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceDeviceOwnerAndActiveAdminPermission, 1);
                    return true;
                case 52:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceKnoxV2Permission(readString14, readString15);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enforceKnoxV2VerifyCaller = enforceKnoxV2VerifyCaller(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceKnoxV2VerifyCaller);
                    return true;
                case 54:
                    String readString16 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDelegatedPermission = hasDelegatedPermission(readString16, readInt20, readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDelegatedPermission);
                    return true;
                case 55:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateNotificationExemption(contextInfo19, readString18);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatus = getUserStatus(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatus);
                    return true;
                case 57:
                    String kPUPackageName = getKPUPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(kPUPackageName);
                    return true;
                case 58:
                    String readString19 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKPUPlatformSigned = isKPUPlatformSigned(readString19, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKPUPlatformSigned);
                    return true;
                case 59:
                    startDualDARServices();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCallerValidKPU = isCallerValidKPU(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerValidKPU);
                    return true;
                case 61:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidDeviceOrProfileOwner = isUidDeviceOrProfileOwner(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidDeviceOrProfileOwner);
                    return true;
                case 62:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionIncludedOnManifest = isPermissionIncludedOnManifest(readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionIncludedOnManifest);
                    return true;
                case 63:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addPseudoAdminForParent = addPseudoAdminForParent(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPseudoAdminForParent);
                    return true;
                case 64:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean migrateKnoxPoliciesForWpcod = migrateKnoxPoliciesForWpcod(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(migrateKnoxPoliciesForWpcod);
                    return true;
                case 65:
                    int readInt26 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enforceWpcod(readInt26, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserSelectable = isUserSelectable(readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSelectable);
                    return true;
                case 67:
                    int readInt27 = parcel.readInt();
                    String readString22 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(readInt27, readString22, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean keychainMarkedReset = keychainMarkedReset(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keychainMarkedReset);
                    return true;
                case 69:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEmailAdminPkg = isEmailAdminPkg(readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailAdminPkg);
                    return true;
                case 70:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> mamPermissions = getMamPermissions(readString24);
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
