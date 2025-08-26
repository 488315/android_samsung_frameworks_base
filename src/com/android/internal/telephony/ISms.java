package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISms extends IInterface {

    public static class Default implements ISms {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public int checkSmsShortCodeDestination(int i, String str, String str2, String str3, String str4) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public void clearStorageMonitorMemoryStatusOverride(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public Bundle getCarrierConfigValuesForSubscriber(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public byte[] getCbSettingsForSubscriber(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public String getImsSmsFormatForSubscriber(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public String getMnoNameForSubscriber(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPreferredSmsSubscription() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermission(String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermissionForSubscriber(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean getSMSPAvailableForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getSmsCapacityOnIccForSubscriber(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean getSmsSettingForSubscriber(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public String getSmscAddressFromIccEfForSubscriber(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public long getWapMessageSize(String str) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ISms
        public void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isImsSmsSupportedForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isMsgBlockedForOneNumberServiceForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSMSPromptEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSmsSimPickActivityNeeded(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean resetAllCellBroadcastRanges(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void resetSimFullStatusForSubscriber(int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendDataForSubscriber(int i, String str, String str2, String str3, String str4, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendDatawithOrigPortForSubscriber(int i, String str, String str2, String str3, int i2, int i3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextwithCBPForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str4, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextwithOptionsForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendOTADomesticForSubscriber(int i, String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredMultipartText(int i, String str, String str2, Uri uri, String str3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredText(int i, String str, String str2, Uri uri, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextAutoLoginForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, long j) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextNSRIForSubscriber(int i, String str, String str2, String str3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithCBPForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str5, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithOptionsForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithOptionsReadconfirmForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermission(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean setSmscAddressOnIccEfForSubscriber(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException {
            return false;
        }
    }

    int checkSmsShortCodeDestination(int i, String str, String str2, String str3, String str4) throws RemoteException;

    void clearStorageMonitorMemoryStatusOverride(int i) throws RemoteException;

    boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) throws RemoteException;

    String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) throws RemoteException;

    boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException;

    boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException;

    boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException;

    boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException;

    List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, String str) throws RemoteException;

    Bundle getCarrierConfigValuesForSubscriber(int i) throws RemoteException;

    byte[] getCbSettingsForSubscriber(int i) throws RemoteException;

    String getImsSmsFormatForSubscriber(int i) throws RemoteException;

    String getMnoNameForSubscriber(int i) throws RemoteException;

    int getPreferredSmsSubscription() throws RemoteException;

    int getPremiumSmsPermission(String str) throws RemoteException;

    int getPremiumSmsPermissionForSubscriber(int i, String str) throws RemoteException;

    boolean getSMSPAvailableForSubscriber(int i) throws RemoteException;

    int getSmsCapacityOnIccForSubscriber(int i) throws RemoteException;

    boolean getSmsSettingForSubscriber(int i, String str) throws RemoteException;

    String getSmscAddressFromIccEfForSubscriber(int i, String str) throws RemoteException;

    long getWapMessageSize(String str) throws RemoteException;

    void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) throws RemoteException;

    boolean isImsSmsSupportedForSubscriber(int i) throws RemoteException;

    boolean isMsgBlockedForOneNumberServiceForSubscriber(int i) throws RemoteException;

    boolean isSMSPromptEnabled() throws RemoteException;

    boolean isSmsSimPickActivityNeeded(int i) throws RemoteException;

    boolean resetAllCellBroadcastRanges(int i) throws RemoteException;

    void resetSimFullStatusForSubscriber(int i) throws RemoteException;

    void sendDataForSubscriber(int i, String str, String str2, String str3, String str4, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendDatawithOrigPortForSubscriber(int i, String str, String str2, String str3, int i2, int i3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, long j) throws RemoteException;

    void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws RemoteException;

    void sendMultipartTextwithCBPForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str4, int i2) throws RemoteException;

    void sendMultipartTextwithOptionsForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, int i3, int i4) throws RemoteException;

    void sendOTADomesticForSubscriber(int i, String str, String str2, String str3, String str4) throws RemoteException;

    void sendStoredMultipartText(int i, String str, String str2, Uri uri, String str3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException;

    void sendStoredText(int i, String str, String str2, Uri uri, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendTextAutoLoginForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException;

    void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, long j) throws RemoteException;

    void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws RemoteException;

    void sendTextNSRIForSubscriber(int i, String str, String str2, String str3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i2, int i3) throws RemoteException;

    void sendTextwithCBPForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str5, int i2) throws RemoteException;

    void sendTextwithOptionsForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4) throws RemoteException;

    void sendTextwithOptionsReadconfirmForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4, int i5) throws RemoteException;

    void setPremiumSmsPermission(String str, int i) throws RemoteException;

    void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) throws RemoteException;

    boolean setSmscAddressOnIccEfForSubscriber(String str, int i, String str2) throws RemoteException;

    void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws RemoteException;

    boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISms {
        public static final String DESCRIPTOR = "com.android.internal.telephony.ISms";
        static final int TRANSACTION_checkSmsShortCodeDestination = 30;
        static final int TRANSACTION_clearStorageMonitorMemoryStatusOverride = 29;
        static final int TRANSACTION_copyMessageToIccEfForSubscriber = 3;
        static final int TRANSACTION_createAppSpecificSmsToken = 26;
        static final int TRANSACTION_createAppSpecificSmsTokenWithPackageInfo = 27;
        static final int TRANSACTION_disableCellBroadcastForSubscriber = 11;
        static final int TRANSACTION_disableCellBroadcastRangeForSubscriber = 13;
        static final int TRANSACTION_enableCellBroadcastForSubscriber = 10;
        static final int TRANSACTION_enableCellBroadcastRangeForSubscriber = 12;
        static final int TRANSACTION_getAllMessagesFromIccEfForSubscriber = 1;
        static final int TRANSACTION_getCarrierConfigValuesForSubscriber = 25;
        static final int TRANSACTION_getCbSettingsForSubscriber = 36;
        static final int TRANSACTION_getImsSmsFormatForSubscriber = 21;
        static final int TRANSACTION_getMnoNameForSubscriber = 43;
        static final int TRANSACTION_getPreferredSmsSubscription = 20;
        static final int TRANSACTION_getPremiumSmsPermission = 14;
        static final int TRANSACTION_getPremiumSmsPermissionForSubscriber = 15;
        static final int TRANSACTION_getSMSPAvailableForSubscriber = 42;
        static final int TRANSACTION_getSmsCapacityOnIccForSubscriber = 33;
        static final int TRANSACTION_getSmsSettingForSubscriber = 44;
        static final int TRANSACTION_getSmscAddressFromIccEfForSubscriber = 31;
        static final int TRANSACTION_getWapMessageSize = 35;
        static final int TRANSACTION_injectSmsPduForSubscriber = 7;
        static final int TRANSACTION_isImsSmsSupportedForSubscriber = 18;
        static final int TRANSACTION_isMsgBlockedForOneNumberServiceForSubscriber = 50;
        static final int TRANSACTION_isSMSPromptEnabled = 22;
        static final int TRANSACTION_isSmsSimPickActivityNeeded = 19;
        static final int TRANSACTION_resetAllCellBroadcastRanges = 34;
        static final int TRANSACTION_resetSimFullStatusForSubscriber = 49;
        static final int TRANSACTION_sendDataForSubscriber = 4;
        static final int TRANSACTION_sendDatawithOrigPortForSubscriber = 45;
        static final int TRANSACTION_sendMultipartTextForSubscriber = 8;
        static final int TRANSACTION_sendMultipartTextForSubscriberWithOptions = 9;
        static final int TRANSACTION_sendMultipartTextwithCBPForSubscriber = 40;
        static final int TRANSACTION_sendMultipartTextwithOptionsForSubscriber = 41;
        static final int TRANSACTION_sendOTADomesticForSubscriber = 46;
        static final int TRANSACTION_sendStoredMultipartText = 24;
        static final int TRANSACTION_sendStoredText = 23;
        static final int TRANSACTION_sendTextAutoLoginForSubscriber = 48;
        static final int TRANSACTION_sendTextForSubscriber = 5;
        static final int TRANSACTION_sendTextForSubscriberWithOptions = 6;
        static final int TRANSACTION_sendTextNSRIForSubscriber = 47;
        static final int TRANSACTION_sendTextwithCBPForSubscriber = 37;
        static final int TRANSACTION_sendTextwithOptionsForSubscriber = 38;
        static final int TRANSACTION_sendTextwithOptionsReadconfirmForSubscriber = 39;
        static final int TRANSACTION_setPremiumSmsPermission = 16;
        static final int TRANSACTION_setPremiumSmsPermissionForSubscriber = 17;
        static final int TRANSACTION_setSmscAddressOnIccEfForSubscriber = 32;
        static final int TRANSACTION_setStorageMonitorMemoryStatusOverride = 28;
        static final int TRANSACTION_updateMessageOnIccEfForSubscriber = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 49;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISms asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISms)) {
                return (ISms) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllMessagesFromIccEfForSubscriber";
                case 2:
                    return "updateMessageOnIccEfForSubscriber";
                case 3:
                    return "copyMessageToIccEfForSubscriber";
                case 4:
                    return "sendDataForSubscriber";
                case 5:
                    return "sendTextForSubscriber";
                case 6:
                    return "sendTextForSubscriberWithOptions";
                case 7:
                    return "injectSmsPduForSubscriber";
                case 8:
                    return "sendMultipartTextForSubscriber";
                case 9:
                    return "sendMultipartTextForSubscriberWithOptions";
                case 10:
                    return "enableCellBroadcastForSubscriber";
                case 11:
                    return "disableCellBroadcastForSubscriber";
                case 12:
                    return "enableCellBroadcastRangeForSubscriber";
                case 13:
                    return "disableCellBroadcastRangeForSubscriber";
                case 14:
                    return "getPremiumSmsPermission";
                case 15:
                    return "getPremiumSmsPermissionForSubscriber";
                case 16:
                    return "setPremiumSmsPermission";
                case 17:
                    return "setPremiumSmsPermissionForSubscriber";
                case 18:
                    return "isImsSmsSupportedForSubscriber";
                case 19:
                    return "isSmsSimPickActivityNeeded";
                case 20:
                    return "getPreferredSmsSubscription";
                case 21:
                    return "getImsSmsFormatForSubscriber";
                case 22:
                    return "isSMSPromptEnabled";
                case 23:
                    return "sendStoredText";
                case 24:
                    return "sendStoredMultipartText";
                case 25:
                    return "getCarrierConfigValuesForSubscriber";
                case 26:
                    return "createAppSpecificSmsToken";
                case 27:
                    return "createAppSpecificSmsTokenWithPackageInfo";
                case 28:
                    return "setStorageMonitorMemoryStatusOverride";
                case 29:
                    return "clearStorageMonitorMemoryStatusOverride";
                case 30:
                    return "checkSmsShortCodeDestination";
                case 31:
                    return "getSmscAddressFromIccEfForSubscriber";
                case 32:
                    return "setSmscAddressOnIccEfForSubscriber";
                case 33:
                    return "getSmsCapacityOnIccForSubscriber";
                case 34:
                    return "resetAllCellBroadcastRanges";
                case 35:
                    return "getWapMessageSize";
                case 36:
                    return "getCbSettingsForSubscriber";
                case 37:
                    return "sendTextwithCBPForSubscriber";
                case 38:
                    return "sendTextwithOptionsForSubscriber";
                case 39:
                    return "sendTextwithOptionsReadconfirmForSubscriber";
                case 40:
                    return "sendMultipartTextwithCBPForSubscriber";
                case 41:
                    return "sendMultipartTextwithOptionsForSubscriber";
                case 42:
                    return "getSMSPAvailableForSubscriber";
                case 43:
                    return "getMnoNameForSubscriber";
                case 44:
                    return "getSmsSettingForSubscriber";
                case 45:
                    return "sendDatawithOrigPortForSubscriber";
                case 46:
                    return "sendOTADomesticForSubscriber";
                case 47:
                    return "sendTextNSRIForSubscriber";
                case 48:
                    return "sendTextAutoLoginForSubscriber";
                case 49:
                    return "resetSimFullStatusForSubscriber";
                case 50:
                    return "isMsgBlockedForOneNumberServiceForSubscriber";
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SmsRawData> allMessagesFromIccEfForSubscriber = getAllMessagesFromIccEfForSubscriber(i3, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allMessagesFromIccEfForSubscriber, 1);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateMessageOnIccEfForSubscriber = updateMessageOnIccEfForSubscriber(i4, string2, i5, i6, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateMessageOnIccEfForSubscriber);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i8 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zCopyMessageToIccEfForSubscriber = copyMessageToIccEfForSubscriber(i7, string3, i8, bArrCreateByteArray2, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCopyMessageToIccEfForSubscriber);
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i10 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDataForSubscriber(i9, string4, string5, string6, string7, i10, bArrCreateByteArray4, pendingIntent, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i11 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean z = parcel.readBoolean();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriber(i11, string8, string9, string10, string11, string12, pendingIntent3, pendingIntent4, z, j);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i12 = parcel.readInt();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    int i13 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriberWithOptions(i12, string13, string14, string15, string16, string17, pendingIntent5, pendingIntent6, z2, i13, z3, i14);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i15 = parcel.readInt();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    String string18 = parcel.readString();
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSmsPduForSubscriber(i15, bArrCreateByteArray5, string18, pendingIntent7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i16 = parcel.readInt();
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriber(i16, string19, string20, string21, string22, arrayListCreateStringArrayList, arrayListCreateTypedArrayList, arrayListCreateTypedArrayList2, z4, j2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int i17 = parcel.readInt();
                    String string23 = parcel.readString();
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    int i18 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriberWithOptions(i17, string23, string24, string25, string26, arrayListCreateStringArrayList2, arrayListCreateTypedArrayList3, arrayListCreateTypedArrayList4, z5, i18, z6, i19);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableCellBroadcastForSubscriber = enableCellBroadcastForSubscriber(i20, i21, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableCellBroadcastForSubscriber);
                    return true;
                case 11:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDisableCellBroadcastForSubscriber = disableCellBroadcastForSubscriber(i23, i24, i25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableCellBroadcastForSubscriber);
                    return true;
                case 12:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnableCellBroadcastRangeForSubscriber = enableCellBroadcastRangeForSubscriber(i26, i27, i28, i29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableCellBroadcastRangeForSubscriber);
                    return true;
                case 13:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDisableCellBroadcastRangeForSubscriber = disableCellBroadcastRangeForSubscriber(i30, i31, i32, i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableCellBroadcastRangeForSubscriber);
                    return true;
                case 14:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermission = getPremiumSmsPermission(string27);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermission);
                    return true;
                case 15:
                    int i34 = parcel.readInt();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermissionForSubscriber = getPremiumSmsPermissionForSubscriber(i34, string28);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermissionForSubscriber);
                    return true;
                case 16:
                    String string29 = parcel.readString();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermission(string29, i35);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i36 = parcel.readInt();
                    String string30 = parcel.readString();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermissionForSubscriber(i36, string30, i37);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsImsSmsSupportedForSubscriber = isImsSmsSupportedForSubscriber(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImsSmsSupportedForSubscriber);
                    return true;
                case 19:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSmsSimPickActivityNeeded = isSmsSimPickActivityNeeded(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSmsSimPickActivityNeeded);
                    return true;
                case 20:
                    int preferredSmsSubscription = getPreferredSmsSubscription();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredSmsSubscription);
                    return true;
                case 21:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String imsSmsFormatForSubscriber = getImsSmsFormatForSubscriber(i40);
                    parcel2.writeNoException();
                    parcel2.writeString(imsSmsFormatForSubscriber);
                    return true;
                case 22:
                    boolean zIsSMSPromptEnabled = isSMSPromptEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSMSPromptEnabled);
                    return true;
                case 23:
                    int i41 = parcel.readInt();
                    String string31 = parcel.readString();
                    String string32 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string33 = parcel.readString();
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredText(i41, string31, string32, uri, string33, pendingIntent8, pendingIntent9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i42 = parcel.readInt();
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string36 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList5 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayListCreateTypedArrayList6 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMultipartText(i42, string34, string35, uri2, string36, arrayListCreateTypedArrayList5, arrayListCreateTypedArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle carrierConfigValuesForSubscriber = getCarrierConfigValuesForSubscriber(i43);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierConfigValuesForSubscriber, 1);
                    return true;
                case 26:
                    int i44 = parcel.readInt();
                    String string37 = parcel.readString();
                    PendingIntent pendingIntent10 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strCreateAppSpecificSmsToken = createAppSpecificSmsToken(i44, string37, pendingIntent10);
                    parcel2.writeNoException();
                    parcel2.writeString(strCreateAppSpecificSmsToken);
                    return true;
                case 27:
                    int i45 = parcel.readInt();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    PendingIntent pendingIntent11 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strCreateAppSpecificSmsTokenWithPackageInfo = createAppSpecificSmsTokenWithPackageInfo(i45, string38, string39, pendingIntent11);
                    parcel2.writeNoException();
                    parcel2.writeString(strCreateAppSpecificSmsTokenWithPackageInfo);
                    return true;
                case 28:
                    int i46 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStorageMonitorMemoryStatusOverride(i46, z7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearStorageMonitorMemoryStatusOverride(i47);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i48 = parcel.readInt();
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckSmsShortCodeDestination = checkSmsShortCodeDestination(i48, string40, string41, string42, string43);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckSmsShortCodeDestination);
                    return true;
                case 31:
                    int i49 = parcel.readInt();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String smscAddressFromIccEfForSubscriber = getSmscAddressFromIccEfForSubscriber(i49, string44);
                    parcel2.writeNoException();
                    parcel2.writeString(smscAddressFromIccEfForSubscriber);
                    return true;
                case 32:
                    String string45 = parcel.readString();
                    int i50 = parcel.readInt();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean smscAddressOnIccEfForSubscriber = setSmscAddressOnIccEfForSubscriber(string45, i50, string46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smscAddressOnIccEfForSubscriber);
                    return true;
                case 33:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int smsCapacityOnIccForSubscriber = getSmsCapacityOnIccForSubscriber(i51);
                    parcel2.writeNoException();
                    parcel2.writeInt(smsCapacityOnIccForSubscriber);
                    return true;
                case 34:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zResetAllCellBroadcastRanges = resetAllCellBroadcastRanges(i52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetAllCellBroadcastRanges);
                    return true;
                case 35:
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long wapMessageSize = getWapMessageSize(string47);
                    parcel2.writeNoException();
                    parcel2.writeLong(wapMessageSize);
                    return true;
                case 36:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] cbSettingsForSubscriber = getCbSettingsForSubscriber(i53);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(cbSettingsForSubscriber);
                    return true;
                case 37:
                    int i54 = parcel.readInt();
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    String string50 = parcel.readString();
                    String string51 = parcel.readString();
                    PendingIntent pendingIntent12 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent13 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string52 = parcel.readString();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithCBPForSubscriber(i54, string48, string49, string50, string51, pendingIntent12, pendingIntent13, string52, i55);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i56 = parcel.readInt();
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    PendingIntent pendingIntent14 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent15 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithOptionsForSubscriber(i56, string53, string54, string55, string56, pendingIntent14, pendingIntent15, z8, i57, i58, i59);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i60 = parcel.readInt();
                    String string57 = parcel.readString();
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    String string60 = parcel.readString();
                    PendingIntent pendingIntent16 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent17 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    int i61 = parcel.readInt();
                    int i62 = parcel.readInt();
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithOptionsReadconfirmForSubscriber(i60, string57, string58, string59, string60, pendingIntent16, pendingIntent17, z9, i61, i62, i63, i64);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i65 = parcel.readInt();
                    String string61 = parcel.readString();
                    String string62 = parcel.readString();
                    String string63 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    ArrayList arrayListCreateTypedArrayList7 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayListCreateTypedArrayList8 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    String string64 = parcel.readString();
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextwithCBPForSubscriber(i65, string61, string62, string63, arrayListCreateStringArrayList3, arrayListCreateTypedArrayList7, arrayListCreateTypedArrayList8, string64, i66);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i67 = parcel.readInt();
                    String string65 = parcel.readString();
                    String string66 = parcel.readString();
                    String string67 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    ArrayList arrayListCreateTypedArrayList9 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList arrayListCreateTypedArrayList10 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean z10 = parcel.readBoolean();
                    int i68 = parcel.readInt();
                    int i69 = parcel.readInt();
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextwithOptionsForSubscriber(i67, string65, string66, string67, arrayListCreateStringArrayList4, arrayListCreateTypedArrayList9, arrayListCreateTypedArrayList10, z10, i68, i69, i70);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sMSPAvailableForSubscriber = getSMSPAvailableForSubscriber(i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sMSPAvailableForSubscriber);
                    return true;
                case 43:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String mnoNameForSubscriber = getMnoNameForSubscriber(i72);
                    parcel2.writeNoException();
                    parcel2.writeString(mnoNameForSubscriber);
                    return true;
                case 44:
                    int i73 = parcel.readInt();
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean smsSettingForSubscriber = getSmsSettingForSubscriber(i73, string68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smsSettingForSubscriber);
                    return true;
                case 45:
                    int i74 = parcel.readInt();
                    String string69 = parcel.readString();
                    String string70 = parcel.readString();
                    String string71 = parcel.readString();
                    int i75 = parcel.readInt();
                    int i76 = parcel.readInt();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    PendingIntent pendingIntent18 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent19 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDatawithOrigPortForSubscriber(i74, string69, string70, string71, i75, i76, bArrCreateByteArray6, pendingIntent18, pendingIntent19);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i77 = parcel.readInt();
                    String string72 = parcel.readString();
                    String string73 = parcel.readString();
                    String string74 = parcel.readString();
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendOTADomesticForSubscriber(i77, string72, string73, string74, string75);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i78 = parcel.readInt();
                    String string76 = parcel.readString();
                    String string77 = parcel.readString();
                    String string78 = parcel.readString();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    PendingIntent pendingIntent20 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent21 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int i79 = parcel.readInt();
                    int i80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextNSRIForSubscriber(i78, string76, string77, string78, bArrCreateByteArray7, pendingIntent20, pendingIntent21, i79, i80);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i81 = parcel.readInt();
                    String string79 = parcel.readString();
                    String string80 = parcel.readString();
                    String string81 = parcel.readString();
                    String string82 = parcel.readString();
                    PendingIntent pendingIntent22 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent23 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendTextAutoLoginForSubscriber(i81, string79, string80, string81, string82, pendingIntent22, pendingIntent23, z11);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int i82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetSimFullStatusForSubscriber(i82);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int i83 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMsgBlockedForOneNumberServiceForSubscriber = isMsgBlockedForOneNumberServiceForSubscriber(i83);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMsgBlockedForOneNumberServiceForSubscriber);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISms {
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

            @Override // com.android.internal.telephony.ISms
            public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SmsRawData.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDataForSubscriber(int i, String str, String str2, String str3, String str4, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeTypedList(list3, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeTypedList(list3, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermissionForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermission(String str, int i) throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isImsSmsSupportedForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSmsSimPickActivityNeeded(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPreferredSmsSubscription() throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public String getImsSmsFormatForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSMSPromptEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredText(int i, String str, String str2, Uri uri, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredMultipartText(int i, String str, String str2, Uri uri, String str3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public Bundle getCarrierConfigValuesForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public void clearStorageMonitorMemoryStatusOverride(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int checkSmsShortCodeDestination(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getSmscAddressFromIccEfForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean setSmscAddressOnIccEfForSubscriber(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getSmsCapacityOnIccForSubscriber(int i) throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public boolean resetAllCellBroadcastRanges(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public long getWapMessageSize(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public byte[] getCbSettingsForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithCBPForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str5, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsReadconfirmForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithCBPForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str4, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeTypedList(list3, 0);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithOptionsForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeTypedList(list3, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSMSPAvailableForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getMnoNameForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSmsSettingForSubscriber(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDatawithOrigPortForSubscriber(int i, String str, String str2, String str3, int i2, int i3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendOTADomesticForSubscriber(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextNSRIForSubscriber(int i, String str, String str2, String str3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextAutoLoginForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(pendingIntent2, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void resetSimFullStatusForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isMsgBlockedForOneNumberServiceForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
