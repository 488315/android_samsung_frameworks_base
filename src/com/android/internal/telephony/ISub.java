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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISub)) {
                return (ISub) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> allSubInfoList = getAllSubInfoList(string, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allSubInfoList, 1);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfo = getActiveSubscriptionInfo(i3, string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfo, 1);
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfoForIccId = getActiveSubscriptionInfoForIccId(string5, string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForIccId, 1);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = getActiveSubscriptionInfoForSimSlotIndex(i4, string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForSimSlotIndex, 1);
                    return true;
                case 5:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> activeSubscriptionInfoList = getActiveSubscriptionInfoList(string10, string11, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeSubscriptionInfoList, 1);
                    return true;
                case 6:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int activeSubInfoCount = getActiveSubInfoCount(string12, string13, z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCount);
                    return true;
                case 7:
                    int activeSubInfoCountMax = getActiveSubInfoCountMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCountMax);
                    return true;
                case 8:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> availableSubscriptionInfoList = getAvailableSubscriptionInfoList(string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableSubscriptionInfoList, 1);
                    return true;
                case 9:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> accessibleSubscriptionInfoList = getAccessibleSubscriptionInfoList(string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accessibleSubscriptionInfoList, 1);
                    return true;
                case 10:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestEmbeddedSubscriptionInfoListRefresh(i5);
                    return true;
                case 11:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddSubInfo = addSubInfo(string17, string18, i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddSubInfo);
                    return true;
                case 12:
                    String string19 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveSubInfo = removeSubInfo(string19, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveSubInfo);
                    return true;
                case 13:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconTint = setIconTint(i9, i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconTint);
                    return true;
                case 14:
                    String string20 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNameUsingSrc = setDisplayNameUsingSrc(string20, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNameUsingSrc);
                    return true;
                case 15:
                    String string21 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNumber = setDisplayNumber(string21, i13);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNumber);
                    return true;
                case 16:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataRoaming = setDataRoaming(i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataRoaming);
                    return true;
                case 17:
                    boolean z3 = parcel.readBoolean();
                    int i16 = parcel.readInt();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int opportunistic = setOpportunistic(z3, i16, string22);
                    parcel2.writeNoException();
                    parcel2.writeInt(opportunistic);
                    return true;
                case 18:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParcelUuid parcelUuidCreateSubscriptionGroup = createSubscriptionGroup(iArrCreateIntArray, string23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parcelUuidCreateSubscriptionGroup, 1);
                    return true;
                case 19:
                    int i17 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    ISetOpportunisticDataCallback iSetOpportunisticDataCallbackAsInterface = ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPreferredDataSubscriptionId(i17, z4, iSetOpportunisticDataCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int preferredDataSubscriptionId = getPreferredDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDataSubscriptionId);
                    return true;
                case 21:
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> opportunisticSubscriptions = getOpportunisticSubscriptions(string24, string25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opportunisticSubscriptions, 1);
                    return true;
                case 22:
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    ParcelUuid parcelUuid = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSubscriptionsFromGroup(iArrCreateIntArray2, parcelUuid, string26);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    ParcelUuid parcelUuid2 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSubscriptionsIntoGroup(iArrCreateIntArray3, parcelUuid2, string27);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    ParcelUuid parcelUuid3 = (ParcelUuid) parcel.readTypedObject(ParcelUuid.CREATOR);
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> subscriptionsInGroup = getSubscriptionsInGroup(parcelUuid3, string28, string29);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionsInGroup, 1);
                    return true;
                case 25:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int slotIndex = getSlotIndex(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(slotIndex);
                    return true;
                case 26:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subId = getSubId(i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(subId);
                    return true;
                case 27:
                    int defaultSubId = getDefaultSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubId);
                    return true;
                case 28:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSubIdAsUser = getDefaultSubIdAsUser(i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubIdAsUser);
                    return true;
                case 29:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int phoneId = getPhoneId(i21);
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneId);
                    return true;
                case 30:
                    int defaultDataSubId = getDefaultDataSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultDataSubId);
                    return true;
                case 31:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultDataSubId(i22);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int defaultVoiceSubId = getDefaultVoiceSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubId);
                    return true;
                case 33:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultVoiceSubIdAsUser = getDefaultVoiceSubIdAsUser(i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubIdAsUser);
                    return true;
                case 34:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultVoiceSubId(i24);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int defaultSmsSubId = getDefaultSmsSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubId);
                    return true;
                case 36:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSmsSubIdAsUser = getDefaultSmsSubIdAsUser(i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubIdAsUser);
                    return true;
                case 37:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultSmsSubId(i26);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] activeSubIdList = getActiveSubIdList(z5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(activeSubIdList);
                    return true;
                case 39:
                    int i27 = parcel.readInt();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionProperty(i27, string30, string31);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i28 = parcel.readInt();
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    String string34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriptionProperty = getSubscriptionProperty(i28, string32, string33, string34);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionProperty);
                    return true;
                case 41:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSubscriptionEnabled = isSubscriptionEnabled(i29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubscriptionEnabled);
                    return true;
                case 42:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enabledSubscriptionId = getEnabledSubscriptionId(i30);
                    parcel2.writeNoException();
                    parcel2.writeInt(enabledSubscriptionId);
                    return true;
                case 43:
                    int i31 = parcel.readInt();
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsActiveSubId = isActiveSubId(i31, string35, string36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActiveSubId);
                    return true;
                case 44:
                    int activeDataSubscriptionId = getActiveDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeDataSubscriptionId);
                    return true;
                case 45:
                    boolean zCanDisablePhysicalSubscription = canDisablePhysicalSubscription();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanDisablePhysicalSubscription);
                    return true;
                case 46:
                    boolean z6 = parcel.readBoolean();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUiccApplicationsEnabled(z6, i32);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharing = setDeviceToDeviceStatusSharing(i33, i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharing);
                    return true;
                case 48:
                    String string37 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharingContacts = setDeviceToDeviceStatusSharingContacts(string37, i35);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharingContacts);
                    return true;
                case 49:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String phoneNumber = getPhoneNumber(i36, i37, string38, string39);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumber);
                    return true;
                case 50:
                    int i38 = parcel.readInt();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String phoneNumberFromFirstAvailableSource = getPhoneNumberFromFirstAvailableSource(i38, string40, string41);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumberFromFirstAvailableSource);
                    return true;
                case 51:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPhoneNumber(i39, i40, string42, string43, string44);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int usageSetting = setUsageSetting(i41, i42, string45);
                    parcel2.writeNoException();
                    parcel2.writeInt(usageSetting);
                    return true;
                case 53:
                    int i43 = parcel.readInt();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGroupOwner(i43, string46);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionUserHandle = setSubscriptionUserHandle(userHandle, i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionUserHandle);
                    return true;
                case 55:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    UserHandle subscriptionUserHandle2 = getSubscriptionUserHandle(i45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(subscriptionUserHandle2, 1);
                    return true;
                case 56:
                    int i46 = parcel.readInt();
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSubscriptionAssociatedWithCallingUser = isSubscriptionAssociatedWithCallingUser(i46, string47, string48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubscriptionAssociatedWithCallingUser);
                    return true;
                case 57:
                    int i47 = parcel.readInt();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSubscriptionAssociatedWithUser = isSubscriptionAssociatedWithUser(i47, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubscriptionAssociatedWithUser);
                    return true;
                case 58:
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<SubscriptionInfo> subscriptionInfoListAssociatedWithUser = getSubscriptionInfoListAssociatedWithUser(userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionInfoListAssociatedWithUser, 1);
                    return true;
                case 59:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    restoreAllSimSpecificSettingsFromBackup(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTransferStatus(i48, i49);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfo(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubscriptionInfo) parcelObtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfoForIccId(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubscriptionInfo) parcelObtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubscriptionInfo) parcelObtain2.readTypedObject(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getActiveSubscriptionInfoList(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCount(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCountMax() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getAvailableSubscriptionInfoList(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getAccessibleSubscriptionInfoList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void requestEmbeddedSubscriptionInfoListRefresh(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int addSubInfo(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean removeSubInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setIconTint(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNameUsingSrc(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNumber(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDataRoaming(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setOpportunistic(boolean z, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public ParcelUuid createSubscriptionGroup(int[] iArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelUuid) parcelObtain2.readTypedObject(ParcelUuid.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPreferredDataSubscriptionId(int i, boolean z, ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iSetOpportunisticDataCallback);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPreferredDataSubscriptionId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getOpportunisticSubscriptions(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void removeSubscriptionsFromGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(parcelUuid, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void addSubscriptionsIntoGroup(int[] iArr, ParcelUuid parcelUuid, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(parcelUuid, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid parcelUuid, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelUuid, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSlotIndex(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubIdAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultDataSubId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultDataSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubIdAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultVoiceSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubIdAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultSmsSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int[] getActiveSubIdList(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setSubscriptionProperty(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getSubscriptionProperty(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getEnabledSubscriptionId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isActiveSubId(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveDataSubscriptionId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean canDisablePhysicalSubscription() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setUiccApplicationsEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharing(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharingContacts(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getPhoneNumber(int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public String getPhoneNumberFromFirstAvailableSource(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPhoneNumber(int i, int i2, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setUsageSetting(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setGroupOwner(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setSubscriptionUserHandle(UserHandle userHandle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public UserHandle getSubscriptionUserHandle(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (UserHandle) parcelObtain2.readTypedObject(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithCallingUser(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithUser(int i, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public List<SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SubscriptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setTransferStatus(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setTransferStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_EMBEDDED_SUBSCRIPTIONS, getCallingPid(), getCallingUid());
        }
    }
}
