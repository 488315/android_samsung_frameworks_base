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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISms)) {
                return (ISms) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<SmsRawData> allMessagesFromIccEfForSubscriber = getAllMessagesFromIccEfForSubscriber(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allMessagesFromIccEfForSubscriber, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean updateMessageOnIccEfForSubscriber = updateMessageOnIccEfForSubscriber(readInt2, readString2, readInt3, readInt4, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateMessageOnIccEfForSubscriber);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean copyMessageToIccEfForSubscriber = copyMessageToIccEfForSubscriber(readInt5, readString3, readInt6, createByteArray2, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copyMessageToIccEfForSubscriber);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDataForSubscriber(readInt7, readString4, readString5, readString6, readString7, readInt8, createByteArray4, pendingIntent, pendingIntent2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriber(readInt9, readString8, readString9, readString10, readString11, readString12, pendingIntent3, pendingIntent4, readBoolean, readLong);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt10 = parcel.readInt();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextForSubscriberWithOptions(readInt10, readString13, readString14, readString15, readString16, readString17, pendingIntent5, pendingIntent6, readBoolean2, readInt11, readBoolean3, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt13 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    String readString18 = parcel.readString();
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSmsPduForSubscriber(readInt13, createByteArray5, readString18, pendingIntent7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt14 = parcel.readInt();
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriber(readInt14, readString19, readString20, readString21, readString22, createStringArrayList, createTypedArrayList, createTypedArrayList2, readBoolean4, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt15 = parcel.readInt();
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextForSubscriberWithOptions(readInt15, readString23, readString24, readString25, readString26, createStringArrayList2, createTypedArrayList3, createTypedArrayList4, readBoolean5, readInt16, readBoolean6, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableCellBroadcastForSubscriber = enableCellBroadcastForSubscriber(readInt18, readInt19, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCellBroadcastForSubscriber);
                    return true;
                case 11:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableCellBroadcastForSubscriber = disableCellBroadcastForSubscriber(readInt21, readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableCellBroadcastForSubscriber);
                    return true;
                case 12:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enableCellBroadcastRangeForSubscriber = enableCellBroadcastRangeForSubscriber(readInt24, readInt25, readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCellBroadcastRangeForSubscriber);
                    return true;
                case 13:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean disableCellBroadcastRangeForSubscriber = disableCellBroadcastRangeForSubscriber(readInt28, readInt29, readInt30, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableCellBroadcastRangeForSubscriber);
                    return true;
                case 14:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermission = getPremiumSmsPermission(readString27);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermission);
                    return true;
                case 15:
                    int readInt32 = parcel.readInt();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int premiumSmsPermissionForSubscriber = getPremiumSmsPermissionForSubscriber(readInt32, readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(premiumSmsPermissionForSubscriber);
                    return true;
                case 16:
                    String readString29 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermission(readString29, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt34 = parcel.readInt();
                    String readString30 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPremiumSmsPermissionForSubscriber(readInt34, readString30, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImsSmsSupportedForSubscriber = isImsSmsSupportedForSubscriber(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImsSmsSupportedForSubscriber);
                    return true;
                case 19:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSmsSimPickActivityNeeded = isSmsSimPickActivityNeeded(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSmsSimPickActivityNeeded);
                    return true;
                case 20:
                    int preferredSmsSubscription = getPreferredSmsSubscription();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredSmsSubscription);
                    return true;
                case 21:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String imsSmsFormatForSubscriber = getImsSmsFormatForSubscriber(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeString(imsSmsFormatForSubscriber);
                    return true;
                case 22:
                    boolean isSMSPromptEnabled = isSMSPromptEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSMSPromptEnabled);
                    return true;
                case 23:
                    int readInt39 = parcel.readInt();
                    String readString31 = parcel.readString();
                    String readString32 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString33 = parcel.readString();
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredText(readInt39, readString31, readString32, uri, readString33, pendingIntent8, pendingIntent9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt40 = parcel.readInt();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString36 = parcel.readString();
                    ArrayList createTypedArrayList5 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList createTypedArrayList6 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendStoredMultipartText(readInt40, readString34, readString35, uri2, readString36, createTypedArrayList5, createTypedArrayList6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle carrierConfigValuesForSubscriber = getCarrierConfigValuesForSubscriber(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierConfigValuesForSubscriber, 1);
                    return true;
                case 26:
                    int readInt42 = parcel.readInt();
                    String readString37 = parcel.readString();
                    PendingIntent pendingIntent10 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    String createAppSpecificSmsToken = createAppSpecificSmsToken(readInt42, readString37, pendingIntent10);
                    parcel2.writeNoException();
                    parcel2.writeString(createAppSpecificSmsToken);
                    return true;
                case 27:
                    int readInt43 = parcel.readInt();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    PendingIntent pendingIntent11 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    String createAppSpecificSmsTokenWithPackageInfo = createAppSpecificSmsTokenWithPackageInfo(readInt43, readString38, readString39, pendingIntent11);
                    parcel2.writeNoException();
                    parcel2.writeString(createAppSpecificSmsTokenWithPackageInfo);
                    return true;
                case 28:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStorageMonitorMemoryStatusOverride(readInt44, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearStorageMonitorMemoryStatusOverride(readInt45);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt46 = parcel.readInt();
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkSmsShortCodeDestination = checkSmsShortCodeDestination(readInt46, readString40, readString41, readString42, readString43);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSmsShortCodeDestination);
                    return true;
                case 31:
                    int readInt47 = parcel.readInt();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String smscAddressFromIccEfForSubscriber = getSmscAddressFromIccEfForSubscriber(readInt47, readString44);
                    parcel2.writeNoException();
                    parcel2.writeString(smscAddressFromIccEfForSubscriber);
                    return true;
                case 32:
                    String readString45 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean smscAddressOnIccEfForSubscriber = setSmscAddressOnIccEfForSubscriber(readString45, readInt48, readString46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smscAddressOnIccEfForSubscriber);
                    return true;
                case 33:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int smsCapacityOnIccForSubscriber = getSmsCapacityOnIccForSubscriber(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeInt(smsCapacityOnIccForSubscriber);
                    return true;
                case 34:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetAllCellBroadcastRanges = resetAllCellBroadcastRanges(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetAllCellBroadcastRanges);
                    return true;
                case 35:
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long wapMessageSize = getWapMessageSize(readString47);
                    parcel2.writeNoException();
                    parcel2.writeLong(wapMessageSize);
                    return true;
                case 36:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] cbSettingsForSubscriber = getCbSettingsForSubscriber(readInt51);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(cbSettingsForSubscriber);
                    return true;
                case 37:
                    int readInt52 = parcel.readInt();
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    String readString51 = parcel.readString();
                    PendingIntent pendingIntent12 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent13 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString52 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithCBPForSubscriber(readInt52, readString48, readString49, readString50, readString51, pendingIntent12, pendingIntent13, readString52, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt54 = parcel.readInt();
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    PendingIntent pendingIntent14 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent15 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithOptionsForSubscriber(readInt54, readString53, readString54, readString55, readString56, pendingIntent14, pendingIntent15, readBoolean8, readInt55, readInt56, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt58 = parcel.readInt();
                    String readString57 = parcel.readString();
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    String readString60 = parcel.readString();
                    PendingIntent pendingIntent16 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent17 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextwithOptionsReadconfirmForSubscriber(readInt58, readString57, readString58, readString59, readString60, pendingIntent16, pendingIntent17, readBoolean9, readInt59, readInt60, readInt61, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt63 = parcel.readInt();
                    String readString61 = parcel.readString();
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    ArrayList createTypedArrayList7 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList createTypedArrayList8 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    String readString64 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextwithCBPForSubscriber(readInt63, readString61, readString62, readString63, createStringArrayList3, createTypedArrayList7, createTypedArrayList8, readString64, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt65 = parcel.readInt();
                    String readString65 = parcel.readString();
                    String readString66 = parcel.readString();
                    String readString67 = parcel.readString();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    ArrayList createTypedArrayList9 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    ArrayList createTypedArrayList10 = parcel.createTypedArrayList(PendingIntent.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendMultipartTextwithOptionsForSubscriber(readInt65, readString65, readString66, readString67, createStringArrayList4, createTypedArrayList9, createTypedArrayList10, readBoolean10, readInt66, readInt67, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sMSPAvailableForSubscriber = getSMSPAvailableForSubscriber(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sMSPAvailableForSubscriber);
                    return true;
                case 43:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String mnoNameForSubscriber = getMnoNameForSubscriber(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeString(mnoNameForSubscriber);
                    return true;
                case 44:
                    int readInt71 = parcel.readInt();
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean smsSettingForSubscriber = getSmsSettingForSubscriber(readInt71, readString68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smsSettingForSubscriber);
                    return true;
                case 45:
                    int readInt72 = parcel.readInt();
                    String readString69 = parcel.readString();
                    String readString70 = parcel.readString();
                    String readString71 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    byte[] createByteArray6 = parcel.createByteArray();
                    PendingIntent pendingIntent18 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent19 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDatawithOrigPortForSubscriber(readInt72, readString69, readString70, readString71, readInt73, readInt74, createByteArray6, pendingIntent18, pendingIntent19);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt75 = parcel.readInt();
                    String readString72 = parcel.readString();
                    String readString73 = parcel.readString();
                    String readString74 = parcel.readString();
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendOTADomesticForSubscriber(readInt75, readString72, readString73, readString74, readString75);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt76 = parcel.readInt();
                    String readString76 = parcel.readString();
                    String readString77 = parcel.readString();
                    String readString78 = parcel.readString();
                    byte[] createByteArray7 = parcel.createByteArray();
                    PendingIntent pendingIntent20 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent21 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTextNSRIForSubscriber(readInt76, readString76, readString77, readString78, createByteArray7, pendingIntent20, pendingIntent21, readInt77, readInt78);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt79 = parcel.readInt();
                    String readString79 = parcel.readString();
                    String readString80 = parcel.readString();
                    String readString81 = parcel.readString();
                    String readString82 = parcel.readString();
                    PendingIntent pendingIntent22 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent pendingIntent23 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendTextAutoLoginForSubscriber(readInt79, readString79, readString80, readString81, readString82, pendingIntent22, pendingIntent23, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetSimFullStatusForSubscriber(readInt80);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMsgBlockedForOneNumberServiceForSubscriber = isMsgBlockedForOneNumberServiceForSubscriber(readInt81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMsgBlockedForOneNumberServiceForSubscriber);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SmsRawData.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDataForSubscriber(int i, String str, String str2, String str3, String str4, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermissionForSubscriber(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermission(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isImsSmsSupportedForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSmsSimPickActivityNeeded(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPreferredSmsSubscription() throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public String getImsSmsFormatForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSMSPromptEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredText(int i, String str, String str2, Uri uri, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredMultipartText(int i, String str, String str2, Uri uri, String str3, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str3);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public Bundle getCarrierConfigValuesForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setStorageMonitorMemoryStatusOverride(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void clearStorageMonitorMemoryStatusOverride(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int checkSmsShortCodeDestination(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getSmscAddressFromIccEfForSubscriber(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean setSmscAddressOnIccEfForSubscriber(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getSmsCapacityOnIccForSubscriber(int i) throws RemoteException {
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

            @Override // com.android.internal.telephony.ISms
            public boolean resetAllCellBroadcastRanges(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public long getWapMessageSize(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public byte[] getCbSettingsForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithCBPForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str5, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeString(str5);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsReadconfirmForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithCBPForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str4, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithOptionsForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeTypedList(list3, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSMSPAvailableForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getMnoNameForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSmsSettingForSubscriber(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDatawithOrigPortForSubscriber(int i, String str, String str2, String str3, int i2, int i3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendOTADomesticForSubscriber(int i, String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextNSRIForSubscriber(int i, String str, String str2, String str3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextAutoLoginForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void resetSimFullStatusForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isMsgBlockedForOneNumberServiceForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
