package com.samsung.android.knox;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEnterpriseDeviceManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IEnterpriseDeviceManager";

    boolean activateDevicePermissions(List<String> list);

    boolean addAuthorizedUid(int i, int i2);

    int addPseudoAdminForParent(int i);

    byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list);

    boolean disableConstrainedState(ContextInfo contextInfo);

    boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i);

    void enforceActiveAdminPermission(List<String> list);

    ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list);

    void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName);

    ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list);

    ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list);

    void enforceKnoxV2Permission(String str, String str2);

    boolean enforceKnoxV2VerifyCaller(int i);

    ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list);

    ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list);

    void enforceWpcod(int i, boolean z);

    ComponentName getActiveAdminComponent();

    List<ComponentName> getActiveAdmins(int i);

    List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i);

    ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list);

    boolean getAdminRemovable(ContextInfo contextInfo, String str);

    int getAdminUidForAuthorizedUid(int i);

    int getAuthorizedUidForAdminUid(int i);

    int getConstrainedState();

    String getKPUPackageName();

    void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback);

    int getUserStatus(int i);

    boolean hasAnyActiveAdmin();

    boolean hasDelegatedPermission(String str, int i, String str2);

    boolean hasGrantedPolicy(ComponentName componentName, int i);

    boolean isAdminActive(ComponentName componentName);

    boolean isAdminRemovable(ComponentName componentName);

    boolean isAdminRemovableInternal(ComponentName componentName, int i);

    boolean isCallerValidKPU(ContextInfo contextInfo);

    boolean isCameraEnabledNative(ContextInfo contextInfo);

    boolean isEmailAdminPkg(String str);

    boolean isKPUPlatformSigned(String str, int i);

    boolean isMdmAdminPresent();

    boolean isMdmAdminPresentAsUser(int i);

    boolean isPossibleTransferOwenerShip(ComponentName componentName);

    boolean isRestrictedByConstrainedState(int i);

    boolean isUserSelectable(String str);

    boolean keychainMarkedReset(ContextInfo contextInfo);

    boolean migrateKnoxPoliciesForWpcod(int i);

    boolean packageHasActiveAdmins(String str);

    boolean packageHasActiveAdminsAsUser(String str, int i);

    String readUmcEnrollmentData(ContextInfo contextInfo);

    void reconcileAdmin(ComponentName componentName, int i);

    void removeActiveAdmin(ComponentName componentName);

    void removeActiveAdminFromDpm(ComponentName componentName, int i);

    boolean removeAuthorizedUid(int i, int i2);

    void sendIntent(int i);

    void setActiveAdmin(ComponentName componentName, boolean z);

    void setActiveAdminSilent(ComponentName componentName);

    boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str);

    int setB2BMode(boolean z);

    void setUserSelectable(int i, String str, boolean z);

    void startDualDARServices();

    void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i);

    void updateNotificationExemption(ContextInfo contextInfo, String str);

    boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IEnterpriseDeviceManager {
        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean activateDevicePermissions(List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean addAuthorizedUid(int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int addPseudoAdminForParent(int i) {
            return 0;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean disableConstrainedState(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean enforceKnoxV2VerifyCaller(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ComponentName getActiveAdminComponent() {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final List<ComponentName> getActiveAdmins(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean getAdminRemovable(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int getAdminUidForAuthorizedUid(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int getAuthorizedUidForAdminUid(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int getConstrainedState() {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final String getKPUPackageName() {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int getUserStatus(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean hasAnyActiveAdmin() {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean hasDelegatedPermission(String str, int i, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean hasGrantedPolicy(ComponentName componentName, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isAdminActive(ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isAdminRemovable(ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isAdminRemovableInternal(ComponentName componentName, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isCallerValidKPU(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isCameraEnabledNative(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isEmailAdminPkg(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isKPUPlatformSigned(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isMdmAdminPresent() {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isMdmAdminPresentAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isPossibleTransferOwenerShip(ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isRestrictedByConstrainedState(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean isUserSelectable(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean keychainMarkedReset(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean migrateKnoxPoliciesForWpcod(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean packageHasActiveAdmins(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean packageHasActiveAdminsAsUser(String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final String readUmcEnrollmentData(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean removeAuthorizedUid(int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final int setB2BMode(boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void startDualDARServices() {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void enforceActiveAdminPermission(List<String> list) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void removeActiveAdmin(ComponentName componentName) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void sendIntent(int i) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void setActiveAdminSilent(ComponentName componentName) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void enforceKnoxV2Permission(String str, String str2) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void enforceWpcod(int i, boolean z) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void reconcileAdmin(ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void removeActiveAdminFromDpm(ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void setActiveAdmin(ComponentName componentName, boolean z) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void updateNotificationExemption(ContextInfo contextInfo, String str) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void setUserSelectable(int i, String str, boolean z) {
        }

        @Override // com.samsung.android.knox.IEnterpriseDeviceManager
        public final void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEnterpriseDeviceManager {
        public static final int TRANSACTION_activateDevicePermissions = 27;
        public static final int TRANSACTION_addAuthorizedUid = 34;
        public static final int TRANSACTION_addPseudoAdminForParent = 55;
        public static final int TRANSACTION_captureUmcLogs = 43;
        public static final int TRANSACTION_disableConstrainedState = 30;
        public static final int TRANSACTION_enableConstrainedState = 29;
        public static final int TRANSACTION_enforceActiveAdminPermission = 10;
        public static final int TRANSACTION_enforceActiveAdminPermissionByContext = 17;
        public static final int TRANSACTION_enforceComponentCheck = 21;
        public static final int TRANSACTION_enforceContainerOwnerShipPermissionByContext = 19;
        public static final int TRANSACTION_enforceDoPoOnlyPermissionByContext = 45;
        public static final int TRANSACTION_enforceKnoxV2Permission = 46;
        public static final int TRANSACTION_enforceKnoxV2VerifyCaller = 47;
        public static final int TRANSACTION_enforceOwnerOnlyAndActiveAdminPermission = 20;
        public static final int TRANSACTION_enforcePermissionByContext = 18;
        public static final int TRANSACTION_enforceWpcod = 57;
        public static final int TRANSACTION_getActiveAdminComponent = 2;
        public static final int TRANSACTION_getActiveAdmins = 3;
        public static final int TRANSACTION_getActiveAdminsInfo = 23;
        public static final int TRANSACTION_getAdminContextIfCallerInCertWhiteList = 42;
        public static final int TRANSACTION_getAdminRemovable = 8;
        public static final int TRANSACTION_getAdminUidForAuthorizedUid = 37;
        public static final int TRANSACTION_getAuthorizedUidForAdminUid = 36;
        public static final int TRANSACTION_getConstrainedState = 32;
        public static final int TRANSACTION_getKPUPackageName = 51;
        public static final int TRANSACTION_getRemoveWarning = 9;
        public static final int TRANSACTION_getUserStatus = 50;
        public static final int TRANSACTION_hasAnyActiveAdmin = 12;
        public static final int TRANSACTION_hasDelegatedPermission = 48;
        public static final int TRANSACTION_hasGrantedPolicy = 5;
        public static final int TRANSACTION_isAdminActive = 1;
        public static final int TRANSACTION_isAdminRemovable = 14;
        public static final int TRANSACTION_isAdminRemovableInternal = 15;
        public static final int TRANSACTION_isCallerValidKPU = 54;
        public static final int TRANSACTION_isCameraEnabledNative = 44;
        public static final int TRANSACTION_isEmailAdminPkg = 61;
        public static final int TRANSACTION_isKPUPlatformSigned = 52;
        public static final int TRANSACTION_isMdmAdminPresent = 40;
        public static final int TRANSACTION_isMdmAdminPresentAsUser = 41;
        public static final int TRANSACTION_isPossibleTransferOwenerShip = 26;
        public static final int TRANSACTION_isRestrictedByConstrainedState = 31;
        public static final int TRANSACTION_isUserSelectable = 58;
        public static final int TRANSACTION_keychainMarkedReset = 60;
        public static final int TRANSACTION_migrateKnoxPoliciesForWpcod = 56;
        public static final int TRANSACTION_packageHasActiveAdmins = 13;
        public static final int TRANSACTION_packageHasActiveAdminsAsUser = 28;
        public static final int TRANSACTION_readUmcEnrollmentData = 39;
        public static final int TRANSACTION_reconcileAdmin = 24;
        public static final int TRANSACTION_removeActiveAdmin = 6;
        public static final int TRANSACTION_removeActiveAdminFromDpm = 11;
        public static final int TRANSACTION_removeAuthorizedUid = 35;
        public static final int TRANSACTION_sendIntent = 33;
        public static final int TRANSACTION_setActiveAdmin = 4;
        public static final int TRANSACTION_setActiveAdminSilent = 22;
        public static final int TRANSACTION_setAdminRemovable = 7;
        public static final int TRANSACTION_setB2BMode = 16;
        public static final int TRANSACTION_setUserSelectable = 59;
        public static final int TRANSACTION_startDualDARServices = 53;
        public static final int TRANSACTION_transferOwnerShip = 25;
        public static final int TRANSACTION_updateNotificationExemption = 49;
        public static final int TRANSACTION_writeUmcEnrollmentData = 38;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEnterpriseDeviceManager {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean activateDevicePermissions(List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean addAuthorizedUid(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final int addPseudoAdminForParent(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final byte[] captureUmcLogs(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean disableConstrainedState(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean enableConstrainedState(ContextInfo contextInfo, String str, String str2, String str3, String str4, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void enforceActiveAdminPermission(List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ContextInfo enforceContainerOwnerShipPermissionByContext(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ContextInfo enforceDoPoOnlyPermissionByContext(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void enforceKnoxV2Permission(String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean enforceKnoxV2VerifyCaller(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void enforceWpcod(int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final ComponentName getActiveAdminComponent() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final List<ComponentName> getActiveAdmins(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ContextInfo) obtain2.readTypedObject(ContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean getAdminRemovable(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int getAdminUidForAuthorizedUid(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int getAuthorizedUidForAdminUid(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final int getConstrainedState() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final String getInterfaceDescriptor() {
                return IEnterpriseDeviceManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final String getKPUPackageName() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int getUserStatus(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean hasAnyActiveAdmin() {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean hasDelegatedPermission(String str, int i, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean hasGrantedPolicy(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isAdminActive(ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isAdminRemovable(ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isAdminRemovableInternal(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isCallerValidKPU(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isCameraEnabledNative(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isEmailAdminPkg(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isKPUPlatformSigned(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isMdmAdminPresent() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isMdmAdminPresentAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean isPossibleTransferOwenerShip(ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isRestrictedByConstrainedState(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean isUserSelectable(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean keychainMarkedReset(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean migrateKnoxPoliciesForWpcod(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean packageHasActiveAdmins(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean packageHasActiveAdminsAsUser(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final String readUmcEnrollmentData(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void reconcileAdmin(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void removeActiveAdmin(ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void removeActiveAdminFromDpm(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean removeAuthorizedUid(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void sendIntent(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void setActiveAdmin(ComponentName componentName, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setActiveAdminSilent(ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final boolean setAdminRemovable(ContextInfo contextInfo, boolean z, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int setB2BMode(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void setUserSelectable(int i, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void startDualDARServices() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final void transferOwnerShip(ComponentName componentName, ComponentName componentName2, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final void updateNotificationExemption(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IEnterpriseDeviceManager
            public final boolean writeUmcEnrollmentData(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseDeviceManager.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
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
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
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
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendIntent(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addAuthorizedUid = addAuthorizedUid(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAuthorizedUid);
                    return true;
                case 35:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeAuthorizedUid = removeAuthorizedUid(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAuthorizedUid);
                    return true;
                case 36:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int authorizedUidForAdminUid = getAuthorizedUidForAdminUid(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(authorizedUidForAdminUid);
                    return true;
                case 37:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int adminUidForAuthorizedUid = getAdminUidForAuthorizedUid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(adminUidForAuthorizedUid);
                    return true;
                case 38:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean writeUmcEnrollmentData = writeUmcEnrollmentData(contextInfo10, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeUmcEnrollmentData);
                    return true;
                case 39:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String readUmcEnrollmentData = readUmcEnrollmentData(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeString(readUmcEnrollmentData);
                    return true;
                case 40:
                    boolean isMdmAdminPresent = isMdmAdminPresent();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMdmAdminPresent);
                    return true;
                case 41:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMdmAdminPresentAsUser = isMdmAdminPresentAsUser(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMdmAdminPresentAsUser);
                    return true;
                case 42:
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo adminContextIfCallerInCertWhiteList = getAdminContextIfCallerInCertWhiteList(createStringArrayList7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adminContextIfCallerInCertWhiteList, 1);
                    return true;
                case 43:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    byte[] captureUmcLogs = captureUmcLogs(contextInfo12, readString10, createStringArrayList8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(captureUmcLogs);
                    return true;
                case 44:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCameraEnabledNative = isCameraEnabledNative(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraEnabledNative);
                    return true;
                case 45:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList9 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    ContextInfo enforceDoPoOnlyPermissionByContext = enforceDoPoOnlyPermissionByContext(contextInfo14, createStringArrayList9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enforceDoPoOnlyPermissionByContext, 1);
                    return true;
                case 46:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enforceKnoxV2Permission(readString11, readString12);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enforceKnoxV2VerifyCaller = enforceKnoxV2VerifyCaller(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforceKnoxV2VerifyCaller);
                    return true;
                case 48:
                    String readString13 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasDelegatedPermission = hasDelegatedPermission(readString13, readInt20, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasDelegatedPermission);
                    return true;
                case 49:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateNotificationExemption(contextInfo15, readString15);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatus = getUserStatus(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatus);
                    return true;
                case 51:
                    String kPUPackageName = getKPUPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(kPUPackageName);
                    return true;
                case 52:
                    String readString16 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKPUPlatformSigned = isKPUPlatformSigned(readString16, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKPUPlatformSigned);
                    return true;
                case 53:
                    startDualDARServices();
                    parcel2.writeNoException();
                    return true;
                case 54:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCallerValidKPU = isCallerValidKPU(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerValidKPU);
                    return true;
                case 55:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addPseudoAdminForParent = addPseudoAdminForParent(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPseudoAdminForParent);
                    return true;
                case 56:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean migrateKnoxPoliciesForWpcod = migrateKnoxPoliciesForWpcod(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(migrateKnoxPoliciesForWpcod);
                    return true;
                case 57:
                    int readInt25 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enforceWpcod(readInt25, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserSelectable = isUserSelectable(readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSelectable);
                    return true;
                case 59:
                    int readInt26 = parcel.readInt();
                    String readString18 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUserSelectable(readInt26, readString18, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean keychainMarkedReset = keychainMarkedReset(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(keychainMarkedReset);
                    return true;
                case 61:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEmailAdminPkg = isEmailAdminPkg(readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailAdminPkg);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
