package com.android.internal.telephony;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISub extends IInterface {

    public static class Default implements ISub {
        @Override // com.android.internal.telephony.ISub
        public int addSubInfo(String str, String str2, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void addSubscriptionsIntoGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean canDisablePhysicalSubscription() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public ParcelUuid createSubscriptionGroup(int[] iArr, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveDataSubscriptionId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int[] getActiveSubIdList(boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveSubInfoCount(String str, String str2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveSubInfoCountMax() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public SubscriptionInfo getActiveSubscriptionInfo(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public SubscriptionInfo getActiveSubscriptionInfoForIccId(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getActiveSubscriptionInfoList(String str, String str2, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getAllSubInfoList(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultDataSubId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSmsSubId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSmsSubIdAsUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSubId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSubIdAsUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultVoiceSubId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultVoiceSubIdAsUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getEnabledSubscriptionId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getOpportunisticSubscriptions(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getPhoneId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public String getPhoneNumber(int i, int i2, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public String getPhoneNumberFromFirstAvailableSource(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getPreferredDataSubscriptionId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getSlotIndex(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getSubId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public String getSubscriptionProperty(int i, String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public UserHandle getSubscriptionUserHandle(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isActiveSubId(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionAssociatedWithCallingUser(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionAssociatedWithUser(int i, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean removeSubInfo(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public void removeSubscriptionsFromGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void requestEmbeddedSubscriptionInfoListRefresh(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setDataRoaming(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultDataSubId(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultSmsSubId(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultVoiceSubId(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setDeviceToDeviceStatusSharing(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDeviceToDeviceStatusSharingContacts(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDisplayNameUsingSrc(String str, int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDisplayNumber(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setGroupOwner(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setIconTint(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setOpportunistic(boolean z, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setPhoneNumber(int i, int i2, String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setSubscriptionProperty(int i, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setSubscriptionUserHandle(UserHandle userHandle, int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setTransferStatus(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setUiccApplicationsEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setUsageSetting(int i, int i2, String str) throws RemoteException {
            return 0;
        }
    }

    int addSubInfo(String str, String str2, int i, int i2) throws RemoteException;

    void addSubscriptionsIntoGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException;

    boolean canDisablePhysicalSubscription() throws RemoteException;

    ParcelUuid createSubscriptionGroup(int[] iArr, String str) throws RemoteException;

    List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String str) throws RemoteException;

    int getActiveDataSubscriptionId() throws RemoteException;

    int[] getActiveSubIdList(boolean z) throws RemoteException;

    int getActiveSubInfoCount(String str, String str2, boolean z) throws RemoteException;

    int getActiveSubInfoCountMax() throws RemoteException;

    SubscriptionInfo getActiveSubscriptionInfo(int i, String str, String str2) throws RemoteException;

    SubscriptionInfo getActiveSubscriptionInfoForIccId(String str, String str2, String str3) throws RemoteException;

    SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, String str, String str2) throws RemoteException;

    List<SubscriptionInfo> getActiveSubscriptionInfoList(String str, String str2, boolean z) throws RemoteException;

    List<SubscriptionInfo> getAllSubInfoList(String str, String str2) throws RemoteException;

    List<SubscriptionInfo> getAvailableSubscriptionInfoList(String str, String str2) throws RemoteException;

    int getDefaultDataSubId() throws RemoteException;

    int getDefaultSmsSubId() throws RemoteException;

    int getDefaultSmsSubIdAsUser(int i) throws RemoteException;

    int getDefaultSubId() throws RemoteException;

    int getDefaultSubIdAsUser(int i) throws RemoteException;

    int getDefaultVoiceSubId() throws RemoteException;

    int getDefaultVoiceSubIdAsUser(int i) throws RemoteException;

    int getEnabledSubscriptionId(int i) throws RemoteException;

    List<SubscriptionInfo> getOpportunisticSubscriptions(String str, String str2) throws RemoteException;

    int getPhoneId(int i) throws RemoteException;

    String getPhoneNumber(int i, int i2, String str, String str2) throws RemoteException;

    String getPhoneNumberFromFirstAvailableSource(int i, String str, String str2) throws RemoteException;

    int getPreferredDataSubscriptionId() throws RemoteException;

    int getSlotIndex(int i) throws RemoteException;

    int getSubId(int i) throws RemoteException;

    List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) throws RemoteException;

    String getSubscriptionProperty(int i, String str, String str2, String str3) throws RemoteException;

    UserHandle getSubscriptionUserHandle(int i) throws RemoteException;

    List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid, String str, String str2) throws RemoteException;

    boolean isActiveSubId(int i, String str, String str2) throws RemoteException;

    boolean isSubscriptionAssociatedWithCallingUser(int i, String str, String str2) throws RemoteException;

    boolean isSubscriptionAssociatedWithUser(int i, UserHandle userHandle) throws RemoteException;

    boolean isSubscriptionEnabled(int i) throws RemoteException;

    boolean removeSubInfo(String str, int i) throws RemoteException;

    void removeSubscriptionsFromGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException;

    void requestEmbeddedSubscriptionInfoListRefresh(int i) throws RemoteException;

    void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws RemoteException;

    int setDataRoaming(int i, int i2) throws RemoteException;

    void setDefaultDataSubId(int i) throws RemoteException;

    void setDefaultSmsSubId(int i) throws RemoteException;

    void setDefaultVoiceSubId(int i) throws RemoteException;

    int setDeviceToDeviceStatusSharing(int i, int i2) throws RemoteException;

    int setDeviceToDeviceStatusSharingContacts(String str, int i) throws RemoteException;

    int setDisplayNameUsingSrc(String str, int i, int i2) throws RemoteException;

    int setDisplayNumber(String str, int i) throws RemoteException;

    void setGroupOwner(int i, String str) throws RemoteException;

    int setIconTint(int i, int i2) throws RemoteException;

    int setOpportunistic(boolean z, int i, String str) throws RemoteException;

    void setPhoneNumber(int i, int i2, String str, String str2, String str3) throws RemoteException;

    void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException;

    void setSubscriptionProperty(int i, String str, String str2) throws RemoteException;

    int setSubscriptionUserHandle(UserHandle userHandle, int i) throws RemoteException;

    void setTransferStatus(int i, int i2) throws RemoteException;

    void setUiccApplicationsEnabled(boolean z, int i) throws RemoteException;

    int setUsageSetting(int i, int i2, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISub {
        public static final String DESCRIPTOR = "com.android.internal.telephony.ISub";
        static final int TRANSACTION_addSubInfo = 11;
        static final int TRANSACTION_addSubscriptionsIntoGroup = 23;
        static final int TRANSACTION_canDisablePhysicalSubscription = 45;
        static final int TRANSACTION_createSubscriptionGroup = 18;
        static final int TRANSACTION_getAccessibleSubscriptionInfoList = 9;
        static final int TRANSACTION_getActiveDataSubscriptionId = 44;
        static final int TRANSACTION_getActiveSubIdList = 38;
        static final int TRANSACTION_getActiveSubInfoCount = 6;
        static final int TRANSACTION_getActiveSubInfoCountMax = 7;
        static final int TRANSACTION_getActiveSubscriptionInfo = 2;
        static final int TRANSACTION_getActiveSubscriptionInfoForIccId = 3;
        static final int TRANSACTION_getActiveSubscriptionInfoForSimSlotIndex = 4;
        static final int TRANSACTION_getActiveSubscriptionInfoList = 5;
        static final int TRANSACTION_getAllSubInfoList = 1;
        static final int TRANSACTION_getAvailableSubscriptionInfoList = 8;
        static final int TRANSACTION_getDefaultDataSubId = 30;
        static final int TRANSACTION_getDefaultSmsSubId = 35;
        static final int TRANSACTION_getDefaultSmsSubIdAsUser = 36;
        static final int TRANSACTION_getDefaultSubId = 27;
        static final int TRANSACTION_getDefaultSubIdAsUser = 28;
        static final int TRANSACTION_getDefaultVoiceSubId = 32;
        static final int TRANSACTION_getDefaultVoiceSubIdAsUser = 33;
        static final int TRANSACTION_getEnabledSubscriptionId = 42;
        static final int TRANSACTION_getOpportunisticSubscriptions = 21;
        static final int TRANSACTION_getPhoneId = 29;
        static final int TRANSACTION_getPhoneNumber = 49;
        static final int TRANSACTION_getPhoneNumberFromFirstAvailableSource = 50;
        static final int TRANSACTION_getPreferredDataSubscriptionId = 20;
        static final int TRANSACTION_getSlotIndex = 25;
        static final int TRANSACTION_getSubId = 26;
        static final int TRANSACTION_getSubscriptionInfoListAssociatedWithUser = 58;
        static final int TRANSACTION_getSubscriptionProperty = 40;
        static final int TRANSACTION_getSubscriptionUserHandle = 55;
        static final int TRANSACTION_getSubscriptionsInGroup = 24;
        static final int TRANSACTION_isActiveSubId = 43;
        static final int TRANSACTION_isSubscriptionAssociatedWithCallingUser = 56;
        static final int TRANSACTION_isSubscriptionAssociatedWithUser = 57;
        static final int TRANSACTION_isSubscriptionEnabled = 41;
        static final int TRANSACTION_removeSubInfo = 12;
        static final int TRANSACTION_removeSubscriptionsFromGroup = 22;
        static final int TRANSACTION_requestEmbeddedSubscriptionInfoListRefresh = 10;
        static final int TRANSACTION_restoreAllSimSpecificSettingsFromBackup = 59;
        static final int TRANSACTION_setDataRoaming = 16;
        static final int TRANSACTION_setDefaultDataSubId = 31;
        static final int TRANSACTION_setDefaultSmsSubId = 37;
        static final int TRANSACTION_setDefaultVoiceSubId = 34;
        static final int TRANSACTION_setDeviceToDeviceStatusSharing = 47;
        static final int TRANSACTION_setDeviceToDeviceStatusSharingContacts = 48;
        static final int TRANSACTION_setDisplayNameUsingSrc = 14;
        static final int TRANSACTION_setDisplayNumber = 15;
        static final int TRANSACTION_setGroupOwner = 53;
        static final int TRANSACTION_setIconTint = 13;
        static final int TRANSACTION_setOpportunistic = 17;
        static final int TRANSACTION_setPhoneNumber = 51;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 19;
        static final int TRANSACTION_setSubscriptionProperty = 39;
        static final int TRANSACTION_setSubscriptionUserHandle = 54;
        static final int TRANSACTION_setTransferStatus = 60;
        static final int TRANSACTION_setUiccApplicationsEnabled = 46;
        static final int TRANSACTION_setUsageSetting = 52;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 59;
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

        public static ISub asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISub)) {
                return (ISub) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllSubInfoList";
                case 2:
                    return "getActiveSubscriptionInfo";
                case 3:
                    return "getActiveSubscriptionInfoForIccId";
                case 4:
                    return "getActiveSubscriptionInfoForSimSlotIndex";
                case 5:
                    return "getActiveSubscriptionInfoList";
                case 6:
                    return "getActiveSubInfoCount";
                case 7:
                    return "getActiveSubInfoCountMax";
                case 8:
                    return "getAvailableSubscriptionInfoList";
                case 9:
                    return "getAccessibleSubscriptionInfoList";
                case 10:
                    return "requestEmbeddedSubscriptionInfoListRefresh";
                case 11:
                    return "addSubInfo";
                case 12:
                    return "removeSubInfo";
                case 13:
                    return "setIconTint";
                case 14:
                    return "setDisplayNameUsingSrc";
                case 15:
                    return "setDisplayNumber";
                case 16:
                    return "setDataRoaming";
                case 17:
                    return "setOpportunistic";
                case 18:
                    return "createSubscriptionGroup";
                case 19:
                    return "setPreferredDataSubscriptionId";
                case 20:
                    return "getPreferredDataSubscriptionId";
                case 21:
                    return "getOpportunisticSubscriptions";
                case 22:
                    return "removeSubscriptionsFromGroup";
                case 23:
                    return "addSubscriptionsIntoGroup";
                case 24:
                    return "getSubscriptionsInGroup";
                case 25:
                    return "getSlotIndex";
                case 26:
                    return "getSubId";
                case 27:
                    return "getDefaultSubId";
                case 28:
                    return "getDefaultSubIdAsUser";
                case 29:
                    return "getPhoneId";
                case 30:
                    return "getDefaultDataSubId";
                case 31:
                    return "setDefaultDataSubId";
                case 32:
                    return "getDefaultVoiceSubId";
                case 33:
                    return "getDefaultVoiceSubIdAsUser";
                case 34:
                    return "setDefaultVoiceSubId";
                case 35:
                    return "getDefaultSmsSubId";
                case 36:
                    return "getDefaultSmsSubIdAsUser";
                case 37:
                    return "setDefaultSmsSubId";
                case 38:
                    return "getActiveSubIdList";
                case 39:
                    return "setSubscriptionProperty";
                case 40:
                    return "getSubscriptionProperty";
                case 41:
                    return "isSubscriptionEnabled";
                case 42:
                    return "getEnabledSubscriptionId";
                case 43:
                    return "isActiveSubId";
                case 44:
                    return "getActiveDataSubscriptionId";
                case 45:
                    return "canDisablePhysicalSubscription";
                case 46:
                    return "setUiccApplicationsEnabled";
                case 47:
                    return "setDeviceToDeviceStatusSharing";
                case 48:
                    return "setDeviceToDeviceStatusSharingContacts";
                case 49:
                    return "getPhoneNumber";
                case 50:
                    return "getPhoneNumberFromFirstAvailableSource";
                case 51:
                    return "setPhoneNumber";
                case 52:
                    return "setUsageSetting";
                case 53:
                    return "setGroupOwner";
                case 54:
                    return "setSubscriptionUserHandle";
                case 55:
                    return "getSubscriptionUserHandle";
                case 56:
                    return "isSubscriptionAssociatedWithCallingUser";
                case 57:
                    return "isSubscriptionAssociatedWithUser";
                case 58:
                    return "getSubscriptionInfoListAssociatedWithUser";
                case 59:
                    return "restoreAllSimSpecificSettingsFromBackup";
                case 60:
                    return "setTransferStatus";
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> allSubInfoList = getAllSubInfoList(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allSubInfoList, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfo = getActiveSubscriptionInfo(readInt, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfo, 1);
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfoForIccId = getActiveSubscriptionInfoForIccId(readString5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForIccId, 1);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = getActiveSubscriptionInfoForSimSlotIndex(readInt2, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForSimSlotIndex, 1);
                    return true;
                case 5:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> activeSubscriptionInfoList = getActiveSubscriptionInfoList(readString10, readString11, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeSubscriptionInfoList, 1);
                    return true;
                case 6:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int activeSubInfoCount = getActiveSubInfoCount(readString12, readString13, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCount);
                    return true;
                case 7:
                    int activeSubInfoCountMax = getActiveSubInfoCountMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCountMax);
                    return true;
                case 8:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> availableSubscriptionInfoList = getAvailableSubscriptionInfoList(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableSubscriptionInfoList, 1);
                    return true;
                case 9:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> accessibleSubscriptionInfoList = getAccessibleSubscriptionInfoList(readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accessibleSubscriptionInfoList, 1);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestEmbeddedSubscriptionInfoListRefresh(readInt3);
                    return true;
                case 11:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addSubInfo = addSubInfo(readString17, readString18, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(addSubInfo);
                    return true;
                case 12:
                    String readString19 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeSubInfo = removeSubInfo(readString19, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeSubInfo);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconTint = setIconTint(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconTint);
                    return true;
                case 14:
                    String readString20 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNameUsingSrc = setDisplayNameUsingSrc(readString20, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNameUsingSrc);
                    return true;
                case 15:
                    String readString21 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNumber = setDisplayNumber(readString21, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNumber);
                    return true;
                case 16:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataRoaming = setDataRoaming(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataRoaming);
                    return true;
                case 17:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int opportunistic = setOpportunistic(readBoolean3, readInt14, readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(opportunistic);
                    return true;
                case 18:
                    int[] createIntArray = parcel.createIntArray();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelUuid createSubscriptionGroup = createSubscriptionGroup(createIntArray, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createSubscriptionGroup, 1);
                    return true;
                case 19:
                    int readInt15 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    ISetOpportunisticDataCallback asInterface = ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPreferredDataSubscriptionId(readInt15, readBoolean4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int preferredDataSubscriptionId = getPreferredDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDataSubscriptionId);
                    return true;
                case 21:
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> opportunisticSubscriptions = getOpportunisticSubscriptions(readString24, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opportunisticSubscriptions, 1);
                    return true;
                case 22:
                    int[] createIntArray2 = parcel.createIntArray();
                    ParcelUuid parcelUuid = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSubscriptionsFromGroup(createIntArray2, parcelUuid, readString26);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int[] createIntArray3 = parcel.createIntArray();
                    ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSubscriptionsIntoGroup(createIntArray3, parcelUuid2, readString27);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    ParcelUuid parcelUuid3 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> subscriptionsInGroup = getSubscriptionsInGroup(parcelUuid3, readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionsInGroup, 1);
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int slotIndex = getSlotIndex(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(slotIndex);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subId = getSubId(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(subId);
                    return true;
                case 27:
                    int defaultSubId = getDefaultSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubId);
                    return true;
                case 28:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSubIdAsUser = getDefaultSubIdAsUser(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubIdAsUser);
                    return true;
                case 29:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int phoneId = getPhoneId(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneId);
                    return true;
                case 30:
                    int defaultDataSubId = getDefaultDataSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultDataSubId);
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultDataSubId(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int defaultVoiceSubId = getDefaultVoiceSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubId);
                    return true;
                case 33:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultVoiceSubIdAsUser = getDefaultVoiceSubIdAsUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubIdAsUser);
                    return true;
                case 34:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultVoiceSubId(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int defaultSmsSubId = getDefaultSmsSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubId);
                    return true;
                case 36:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSmsSubIdAsUser = getDefaultSmsSubIdAsUser(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubIdAsUser);
                    return true;
                case 37:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultSmsSubId(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] activeSubIdList = getActiveSubIdList(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(activeSubIdList);
                    return true;
                case 39:
                    int readInt25 = parcel.readInt();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionProperty(readInt25, readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt26 = parcel.readInt();
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriptionProperty = getSubscriptionProperty(readInt26, readString32, readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionProperty);
                    return true;
                case 41:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionEnabled = isSubscriptionEnabled(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionEnabled);
                    return true;
                case 42:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enabledSubscriptionId = getEnabledSubscriptionId(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(enabledSubscriptionId);
                    return true;
                case 43:
                    int readInt29 = parcel.readInt();
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isActiveSubId = isActiveSubId(readInt29, readString35, readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActiveSubId);
                    return true;
                case 44:
                    int activeDataSubscriptionId = getActiveDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeDataSubscriptionId);
                    return true;
                case 45:
                    boolean canDisablePhysicalSubscription = canDisablePhysicalSubscription();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canDisablePhysicalSubscription);
                    return true;
                case 46:
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUiccApplicationsEnabled(readBoolean6, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharing = setDeviceToDeviceStatusSharing(readInt31, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharing);
                    return true;
                case 48:
                    String readString37 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharingContacts = setDeviceToDeviceStatusSharingContacts(readString37, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharingContacts);
                    return true;
                case 49:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String phoneNumber = getPhoneNumber(readInt34, readInt35, readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumber);
                    return true;
                case 50:
                    int readInt36 = parcel.readInt();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String phoneNumberFromFirstAvailableSource = getPhoneNumberFromFirstAvailableSource(readInt36, readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumberFromFirstAvailableSource);
                    return true;
                case 51:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPhoneNumber(readInt37, readInt38, readString42, readString43, readString44);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int usageSetting = setUsageSetting(readInt39, readInt40, readString45);
                    parcel2.writeNoException();
                    parcel2.writeInt(usageSetting);
                    return true;
                case 53:
                    int readInt41 = parcel.readInt();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGroupOwner(readInt41, readString46);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionUserHandle = setSubscriptionUserHandle(userHandle, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionUserHandle);
                    return true;
                case 55:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserHandle subscriptionUserHandle2 = getSubscriptionUserHandle(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(subscriptionUserHandle2, 1);
                    return true;
                case 56:
                    int readInt44 = parcel.readInt();
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionAssociatedWithCallingUser = isSubscriptionAssociatedWithCallingUser(readInt44, readString47, readString48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionAssociatedWithCallingUser);
                    return true;
                case 57:
                    int readInt45 = parcel.readInt();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionAssociatedWithUser = isSubscriptionAssociatedWithUser(readInt45, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionAssociatedWithUser);
                    return true;
                case 58:
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> subscriptionInfoListAssociatedWithUser = getSubscriptionInfoListAssociatedWithUser(userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionInfoListAssociatedWithUser, 1);
                    return true;
                case 59:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    restoreAllSimSpecificSettingsFromBackup(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTransferStatus(readInt46, readInt47);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISub {
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

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getAllSubInfoList(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfo(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubscriptionInfo) obtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfoForIccId(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubscriptionInfo) obtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubscriptionInfo) obtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getActiveSubscriptionInfoList(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCount(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCountMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void requestEmbeddedSubscriptionInfoListRefresh(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int addSubInfo(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean removeSubInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setIconTint(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNameUsingSrc(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNumber(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDataRoaming(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setOpportunistic(boolean z, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public ParcelUuid createSubscriptionGroup(int[] iArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelUuid) obtain2.readTypedObject(ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSetOpportunisticDataCallback);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPreferredDataSubscriptionId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getOpportunisticSubscriptions(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void removeSubscriptionsFromGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void addSubscriptionsIntoGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSlotIndex(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubIdAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPhoneId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultDataSubId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultDataSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubIdAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultVoiceSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubIdAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultSmsSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int[] getActiveSubIdList(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setSubscriptionProperty(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getSubscriptionProperty(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getEnabledSubscriptionId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isActiveSubId(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveDataSubscriptionId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean canDisablePhysicalSubscription() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setUiccApplicationsEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharing(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharingContacts(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getPhoneNumber(int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getPhoneNumberFromFirstAvailableSource(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPhoneNumber(int i, int i2, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setUsageSetting(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setGroupOwner(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setSubscriptionUserHandle(UserHandle userHandle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public UserHandle getSubscriptionUserHandle(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return (UserHandle) obtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithCallingUser(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithUser(int i, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setTransferStatus(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setTransferStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_EMBEDDED_SUBSCRIPTIONS, getCallingPid(), getCallingUid());
        }
    }
}
