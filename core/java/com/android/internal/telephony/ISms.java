package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISms extends IInterface {
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

    public static class Default implements ISms {
        @Override // com.android.internal.telephony.ISms
        public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int subId, String callingPkg) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean updateMessageOnIccEfForSubscriber(int subId, String callingPkg, int messageIndex, int newStatus, byte[] pdu) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean copyMessageToIccEfForSubscriber(int subId, String callingPkg, int status, byte[] pdu, byte[] smsc) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendDataForSubscriber(int subId, String callingPkg, String callingattributionTag, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriber(int subId, String callingPkg, String callingAttributionTag, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, long messageId) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextForSubscriberWithOptions(int subId, String callingPkg, String callingAttributionTag, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void injectSmsPduForSubscriber(int subId, byte[] pdu, String format, PendingIntent receivedIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriber(int subId, String callingPkg, String callingAttributionTag, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp, long messageId) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextForSubscriberWithOptions(int subId, String callingPkg, String callingAttributionTag, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean enableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean disableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermission(String packageName) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPremiumSmsPermissionForSubscriber(int subId, String packageName) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermission(String packageName, int permission) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void setPremiumSmsPermissionForSubscriber(int subId, String packageName, int permission) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isImsSmsSupportedForSubscriber(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSmsSimPickActivityNeeded(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getPreferredSmsSubscription() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public String getImsSmsFormatForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean isSMSPromptEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredText(int subId, String callingPkg, String callingAttributionTag, Uri messageUri, String scAddress, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendStoredMultipartText(int subId, String callingPkg, String callingAttributeTag, Uri messageUri, String scAddress, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public Bundle getCarrierConfigValuesForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public String createAppSpecificSmsToken(int subId, String callingPkg, PendingIntent intent) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public String createAppSpecificSmsTokenWithPackageInfo(int subId, String callingPkg, String prefixes, PendingIntent intent) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public void setStorageMonitorMemoryStatusOverride(int subId, boolean isStorageAvailable) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void clearStorageMonitorMemoryStatusOverride(int subId) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public int checkSmsShortCodeDestination(int subId, String callingApk, String callingFeatureId, String destAddress, String countryIso) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public String getSmscAddressFromIccEfForSubscriber(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean setSmscAddressOnIccEfForSubscriber(String smsc, int subId, String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public int getSmsCapacityOnIccForSubscriber(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean resetAllCellBroadcastRanges(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public long getWapMessageSize(String locationUrl) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.telephony.ISms
        public byte[] getCbSettingsForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithCBPForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, String callbackNumber, int priority) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithOptionsForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextwithOptionsReadconfirmForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType, int confirmID) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextwithCBPForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, String callbackNumber, int priority) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendMultipartTextwithOptionsForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean replyPath, int expiry, int serviceType, int encodingType) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public boolean getSMSPAvailableForSubscriber(int subId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public String getMnoNameForSubscriber(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISms
        public boolean getSmsSettingForSubscriber(int subId, String settingName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISms
        public void sendDatawithOrigPortForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, int destPort, int origPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendOTADomesticForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, String text) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextNSRIForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, byte[] text, PendingIntent sentIntent, PendingIntent deliveryIntent, int msgCount, int msgTotal) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void sendTextAutoLoginForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISms
        public void resetSimFullStatusForSubscriber(int subId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISms asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISms)) {
                return (ISms) iin;
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
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    List<SmsRawData> _result = getAllMessagesFromIccEfForSubscriber(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    byte[] _arg4 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result2 = updateMessageOnIccEfForSubscriber(_arg02, _arg12, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    int _arg22 = data.readInt();
                    byte[] _arg32 = data.createByteArray();
                    byte[] _arg42 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result3 = copyMessageToIccEfForSubscriber(_arg03, _arg13, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    String _arg23 = data.readString();
                    String _arg33 = data.readString();
                    String _arg43 = data.readString();
                    int _arg5 = data.readInt();
                    byte[] _arg6 = data.createByteArray();
                    PendingIntent _arg7 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg8 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    sendDataForSubscriber(_arg04, _arg14, _arg23, _arg33, _arg43, _arg5, _arg6, _arg7, _arg8);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    String _arg15 = data.readString();
                    String _arg24 = data.readString();
                    String _arg34 = data.readString();
                    String _arg44 = data.readString();
                    String _arg52 = data.readString();
                    PendingIntent _arg62 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg72 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    boolean _arg82 = data.readBoolean();
                    long _arg9 = data.readLong();
                    data.enforceNoDataAvail();
                    sendTextForSubscriber(_arg05, _arg15, _arg24, _arg34, _arg44, _arg52, _arg62, _arg72, _arg82, _arg9);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    String _arg16 = data.readString();
                    String _arg25 = data.readString();
                    String _arg35 = data.readString();
                    String _arg45 = data.readString();
                    String _arg53 = data.readString();
                    PendingIntent _arg63 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg73 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    boolean _arg83 = data.readBoolean();
                    int _arg92 = data.readInt();
                    boolean _arg10 = data.readBoolean();
                    int _arg11 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTextForSubscriberWithOptions(_arg06, _arg16, _arg25, _arg35, _arg45, _arg53, _arg63, _arg73, _arg83, _arg92, _arg10, _arg11);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    byte[] _arg17 = data.createByteArray();
                    String _arg26 = data.readString();
                    PendingIntent _arg36 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    injectSmsPduForSubscriber(_arg07, _arg17, _arg26, _arg36);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    String _arg18 = data.readString();
                    String _arg27 = data.readString();
                    String _arg37 = data.readString();
                    String _arg46 = data.readString();
                    List<String> _arg54 = data.createStringArrayList();
                    List<PendingIntent> _arg64 = data.createTypedArrayList(PendingIntent.CREATOR);
                    List<PendingIntent> _arg74 = data.createTypedArrayList(PendingIntent.CREATOR);
                    boolean _arg84 = data.readBoolean();
                    long _arg93 = data.readLong();
                    data.enforceNoDataAvail();
                    sendMultipartTextForSubscriber(_arg08, _arg18, _arg27, _arg37, _arg46, _arg54, _arg64, _arg74, _arg84, _arg93);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    String _arg19 = data.readString();
                    String _arg28 = data.readString();
                    String _arg38 = data.readString();
                    String _arg47 = data.readString();
                    List<String> _arg55 = data.createStringArrayList();
                    List<PendingIntent> _arg65 = data.createTypedArrayList(PendingIntent.CREATOR);
                    List<PendingIntent> _arg75 = data.createTypedArrayList(PendingIntent.CREATOR);
                    boolean _arg85 = data.readBoolean();
                    int _arg94 = data.readInt();
                    boolean _arg102 = data.readBoolean();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    sendMultipartTextForSubscriberWithOptions(_arg09, _arg19, _arg28, _arg38, _arg47, _arg55, _arg65, _arg75, _arg85, _arg94, _arg102, _arg112);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg110 = data.readInt();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = enableCellBroadcastForSubscriber(_arg010, _arg110, _arg29);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg111 = data.readInt();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = disableCellBroadcastForSubscriber(_arg011, _arg111, _arg210);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg113 = data.readInt();
                    int _arg211 = data.readInt();
                    int _arg39 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = enableCellBroadcastRangeForSubscriber(_arg012, _arg113, _arg211, _arg39);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    int _arg114 = data.readInt();
                    int _arg212 = data.readInt();
                    int _arg310 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = disableCellBroadcastRangeForSubscriber(_arg013, _arg114, _arg212, _arg310);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 14:
                    String _arg014 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 = getPremiumSmsPermission(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    String _arg115 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = getPremiumSmsPermissionForSubscriber(_arg015, _arg115);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 16:
                    String _arg016 = data.readString();
                    int _arg116 = data.readInt();
                    data.enforceNoDataAvail();
                    setPremiumSmsPermission(_arg016, _arg116);
                    reply.writeNoException();
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    String _arg117 = data.readString();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    setPremiumSmsPermissionForSubscriber(_arg017, _arg117, _arg213);
                    reply.writeNoException();
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = isImsSmsSupportedForSubscriber(_arg018);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = isSmsSimPickActivityNeeded(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 20:
                    int _result12 = getPreferredSmsSubscription();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 21:
                    int _arg020 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result13 = getImsSmsFormatForSubscriber(_arg020);
                    reply.writeNoException();
                    reply.writeString(_result13);
                    return true;
                case 22:
                    boolean _result14 = isSMSPromptEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 23:
                    int _arg021 = data.readInt();
                    String _arg118 = data.readString();
                    String _arg214 = data.readString();
                    Uri _arg311 = (Uri) data.readTypedObject(Uri.CREATOR);
                    String _arg48 = data.readString();
                    PendingIntent _arg56 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg66 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    sendStoredText(_arg021, _arg118, _arg214, _arg311, _arg48, _arg56, _arg66);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg022 = data.readInt();
                    String _arg119 = data.readString();
                    String _arg215 = data.readString();
                    Uri _arg312 = (Uri) data.readTypedObject(Uri.CREATOR);
                    String _arg49 = data.readString();
                    List<PendingIntent> _arg57 = data.createTypedArrayList(PendingIntent.CREATOR);
                    List<PendingIntent> _arg67 = data.createTypedArrayList(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    sendStoredMultipartText(_arg022, _arg119, _arg215, _arg312, _arg49, _arg57, _arg67);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result15 = getCarrierConfigValuesForSubscriber(_arg023);
                    reply.writeNoException();
                    reply.writeTypedObject(_result15, 1);
                    return true;
                case 26:
                    int _arg024 = data.readInt();
                    String _arg120 = data.readString();
                    PendingIntent _arg216 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    String _result16 = createAppSpecificSmsToken(_arg024, _arg120, _arg216);
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 27:
                    int _arg025 = data.readInt();
                    String _arg121 = data.readString();
                    String _arg217 = data.readString();
                    PendingIntent _arg313 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    String _result17 = createAppSpecificSmsTokenWithPackageInfo(_arg025, _arg121, _arg217, _arg313);
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 28:
                    int _arg026 = data.readInt();
                    boolean _arg122 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setStorageMonitorMemoryStatusOverride(_arg026, _arg122);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    clearStorageMonitorMemoryStatusOverride(_arg027);
                    reply.writeNoException();
                    return true;
                case 30:
                    int _arg028 = data.readInt();
                    String _arg123 = data.readString();
                    String _arg218 = data.readString();
                    String _arg314 = data.readString();
                    String _arg410 = data.readString();
                    data.enforceNoDataAvail();
                    int _result18 = checkSmsShortCodeDestination(_arg028, _arg123, _arg218, _arg314, _arg410);
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 31:
                    int _arg029 = data.readInt();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    String _result19 = getSmscAddressFromIccEfForSubscriber(_arg029, _arg124);
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case 32:
                    String _arg030 = data.readString();
                    int _arg125 = data.readInt();
                    String _arg219 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result20 = setSmscAddressOnIccEfForSubscriber(_arg030, _arg125, _arg219);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 33:
                    int _arg031 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result21 = getSmsCapacityOnIccForSubscriber(_arg031);
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 34:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result22 = resetAllCellBroadcastRanges(_arg032);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 35:
                    String _arg033 = data.readString();
                    data.enforceNoDataAvail();
                    long _result23 = getWapMessageSize(_arg033);
                    reply.writeNoException();
                    reply.writeLong(_result23);
                    return true;
                case 36:
                    int _arg034 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result24 = getCbSettingsForSubscriber(_arg034);
                    reply.writeNoException();
                    reply.writeByteArray(_result24);
                    return true;
                case 37:
                    int _arg035 = data.readInt();
                    String _arg126 = data.readString();
                    String _arg220 = data.readString();
                    String _arg315 = data.readString();
                    String _arg411 = data.readString();
                    PendingIntent _arg58 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg68 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg76 = data.readString();
                    int _arg86 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTextwithCBPForSubscriber(_arg035, _arg126, _arg220, _arg315, _arg411, _arg58, _arg68, _arg76, _arg86);
                    reply.writeNoException();
                    return true;
                case 38:
                    int _arg036 = data.readInt();
                    String _arg127 = data.readString();
                    String _arg221 = data.readString();
                    String _arg316 = data.readString();
                    String _arg412 = data.readString();
                    PendingIntent _arg59 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg69 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    boolean _arg77 = data.readBoolean();
                    int _arg87 = data.readInt();
                    int _arg95 = data.readInt();
                    int _arg103 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTextwithOptionsForSubscriber(_arg036, _arg127, _arg221, _arg316, _arg412, _arg59, _arg69, _arg77, _arg87, _arg95, _arg103);
                    reply.writeNoException();
                    return true;
                case 39:
                    int _arg037 = data.readInt();
                    String _arg128 = data.readString();
                    String _arg222 = data.readString();
                    String _arg317 = data.readString();
                    String _arg413 = data.readString();
                    PendingIntent _arg510 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg610 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    boolean _arg78 = data.readBoolean();
                    int _arg88 = data.readInt();
                    int _arg96 = data.readInt();
                    int _arg104 = data.readInt();
                    int _arg1110 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTextwithOptionsReadconfirmForSubscriber(_arg037, _arg128, _arg222, _arg317, _arg413, _arg510, _arg610, _arg78, _arg88, _arg96, _arg104, _arg1110);
                    reply.writeNoException();
                    return true;
                case 40:
                    int _arg038 = data.readInt();
                    String _arg129 = data.readString();
                    String _arg223 = data.readString();
                    String _arg318 = data.readString();
                    List<String> _arg414 = data.createStringArrayList();
                    List<PendingIntent> _arg511 = data.createTypedArrayList(PendingIntent.CREATOR);
                    List<PendingIntent> _arg611 = data.createTypedArrayList(PendingIntent.CREATOR);
                    String _arg79 = data.readString();
                    int _arg89 = data.readInt();
                    data.enforceNoDataAvail();
                    sendMultipartTextwithCBPForSubscriber(_arg038, _arg129, _arg223, _arg318, _arg414, _arg511, _arg611, _arg79, _arg89);
                    reply.writeNoException();
                    return true;
                case 41:
                    int _arg039 = data.readInt();
                    String _arg130 = data.readString();
                    String _arg224 = data.readString();
                    String _arg319 = data.readString();
                    List<String> _arg415 = data.createStringArrayList();
                    List<PendingIntent> _arg512 = data.createTypedArrayList(PendingIntent.CREATOR);
                    List<PendingIntent> _arg612 = data.createTypedArrayList(PendingIntent.CREATOR);
                    boolean _arg710 = data.readBoolean();
                    int _arg810 = data.readInt();
                    int _arg97 = data.readInt();
                    int _arg105 = data.readInt();
                    data.enforceNoDataAvail();
                    sendMultipartTextwithOptionsForSubscriber(_arg039, _arg130, _arg224, _arg319, _arg415, _arg512, _arg612, _arg710, _arg810, _arg97, _arg105);
                    reply.writeNoException();
                    return true;
                case 42:
                    int _arg040 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result25 = getSMSPAvailableForSubscriber(_arg040);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 43:
                    int _arg041 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result26 = getMnoNameForSubscriber(_arg041);
                    reply.writeNoException();
                    reply.writeString(_result26);
                    return true;
                case 44:
                    int _arg042 = data.readInt();
                    String _arg131 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result27 = getSmsSettingForSubscriber(_arg042, _arg131);
                    reply.writeNoException();
                    reply.writeBoolean(_result27);
                    return true;
                case 45:
                    int _arg043 = data.readInt();
                    String _arg132 = data.readString();
                    String _arg225 = data.readString();
                    String _arg320 = data.readString();
                    int _arg416 = data.readInt();
                    int _arg513 = data.readInt();
                    byte[] _arg613 = data.createByteArray();
                    PendingIntent _arg711 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg811 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    sendDatawithOrigPortForSubscriber(_arg043, _arg132, _arg225, _arg320, _arg416, _arg513, _arg613, _arg711, _arg811);
                    reply.writeNoException();
                    return true;
                case 46:
                    int _arg044 = data.readInt();
                    String _arg133 = data.readString();
                    String _arg226 = data.readString();
                    String _arg321 = data.readString();
                    String _arg417 = data.readString();
                    data.enforceNoDataAvail();
                    sendOTADomesticForSubscriber(_arg044, _arg133, _arg226, _arg321, _arg417);
                    reply.writeNoException();
                    return true;
                case 47:
                    int _arg045 = data.readInt();
                    String _arg134 = data.readString();
                    String _arg227 = data.readString();
                    String _arg322 = data.readString();
                    byte[] _arg418 = data.createByteArray();
                    PendingIntent _arg514 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg614 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    int _arg712 = data.readInt();
                    int _arg812 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTextNSRIForSubscriber(_arg045, _arg134, _arg227, _arg322, _arg418, _arg514, _arg614, _arg712, _arg812);
                    reply.writeNoException();
                    return true;
                case 48:
                    int _arg046 = data.readInt();
                    String _arg135 = data.readString();
                    String _arg228 = data.readString();
                    String _arg323 = data.readString();
                    String _arg419 = data.readString();
                    PendingIntent _arg515 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    PendingIntent _arg615 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    boolean _arg713 = data.readBoolean();
                    data.enforceNoDataAvail();
                    sendTextAutoLoginForSubscriber(_arg046, _arg135, _arg228, _arg323, _arg419, _arg515, _arg615, _arg713);
                    reply.writeNoException();
                    return true;
                case 49:
                    int _arg047 = data.readInt();
                    data.enforceNoDataAvail();
                    resetSimFullStatusForSubscriber(_arg047);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISms {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISms
            public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int subId, String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<SmsRawData> _result = _reply.createTypedArrayList(SmsRawData.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean updateMessageOnIccEfForSubscriber(int subId, String callingPkg, int messageIndex, int newStatus, byte[] pdu) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeInt(messageIndex);
                    _data.writeInt(newStatus);
                    _data.writeByteArray(pdu);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean copyMessageToIccEfForSubscriber(int subId, String callingPkg, int status, byte[] pdu, byte[] smsc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeInt(status);
                    _data.writeByteArray(pdu);
                    _data.writeByteArray(smsc);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDataForSubscriber(int subId, String callingPkg, String callingattributionTag, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(callingattributionTag);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeInt(destPort);
                    _data.writeByteArray(data);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriber(int subId, String callingPkg, String callingAttributionTag, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, long messageId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPkg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(callingAttributionTag);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destAddr);
                    try {
                        _data.writeString(scAddr);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(text);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(sentIntent, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(deliveryIntent, 0);
                    try {
                        _data.writeBoolean(persistMessageForNonDefaultSmsApp);
                        try {
                            _data.writeLong(messageId);
                        } catch (Throwable th8) {
                            th = th8;
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(5, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextForSubscriberWithOptions(int subId, String callingPkg, String callingAttributionTag, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingAttributionTag);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destAddr);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(scAddr);
                    try {
                        _data.writeString(text);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(sentIntent, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(deliveryIntent, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(persistMessageForNonDefaultSmsApp);
                    try {
                        _data.writeInt(priority);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(expectMore);
                        try {
                            _data.writeInt(validityPeriod);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(6, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void injectSmsPduForSubscriber(int subId, byte[] pdu, String format, PendingIntent receivedIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeByteArray(pdu);
                    _data.writeString(format);
                    _data.writeTypedObject(receivedIntent, 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriber(int subId, String callingPkg, String callingAttributionTag, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp, long messageId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPkg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(callingAttributionTag);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destinationAddress);
                    try {
                        _data.writeString(scAddress);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringList(parts);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedList(sentIntents, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedList(deliveryIntents, 0);
                    try {
                        _data.writeBoolean(persistMessageForNonDefaultSmsApp);
                        try {
                            _data.writeLong(messageId);
                        } catch (Throwable th8) {
                            th = th8;
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(8, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextForSubscriberWithOptions(int subId, String callingPkg, String callingAttributionTag, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingAttributionTag);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destinationAddress);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(scAddress);
                    try {
                        _data.writeStringList(parts);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedList(sentIntents, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedList(deliveryIntents, 0);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeBoolean(persistMessageForNonDefaultSmsApp);
                    try {
                        _data.writeInt(priority);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(expectMore);
                        try {
                            _data.writeInt(validityPeriod);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(9, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(messageIdentifier);
                    _data.writeInt(ranType);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(messageIdentifier);
                    _data.writeInt(ranType);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean enableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(startMessageId);
                    _data.writeInt(endMessageId);
                    _data.writeInt(ranType);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean disableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(startMessageId);
                    _data.writeInt(endMessageId);
                    _data.writeInt(ranType);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermission(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPremiumSmsPermissionForSubscriber(int subId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(packageName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermission(String packageName, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(permission);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setPremiumSmsPermissionForSubscriber(int subId, String packageName, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(packageName);
                    _data.writeInt(permission);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isImsSmsSupportedForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSmsSimPickActivityNeeded(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getPreferredSmsSubscription() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getImsSmsFormatForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean isSMSPromptEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredText(int subId, String callingPkg, String callingAttributionTag, Uri messageUri, String scAddress, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(callingAttributionTag);
                    _data.writeTypedObject(messageUri, 0);
                    _data.writeString(scAddress);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendStoredMultipartText(int subId, String callingPkg, String callingAttributeTag, Uri messageUri, String scAddress, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(callingAttributeTag);
                    _data.writeTypedObject(messageUri, 0);
                    _data.writeString(scAddress);
                    _data.writeTypedList(sentIntents, 0);
                    _data.writeTypedList(deliveryIntents, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public Bundle getCarrierConfigValuesForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsToken(int subId, String callingPkg, PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String createAppSpecificSmsTokenWithPackageInfo(int subId, String callingPkg, String prefixes, PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(prefixes);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void setStorageMonitorMemoryStatusOverride(int subId, boolean isStorageAvailable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeBoolean(isStorageAvailable);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void clearStorageMonitorMemoryStatusOverride(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int checkSmsShortCodeDestination(int subId, String callingApk, String callingFeatureId, String destAddress, String countryIso) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingApk);
                    _data.writeString(callingFeatureId);
                    _data.writeString(destAddress);
                    _data.writeString(countryIso);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getSmscAddressFromIccEfForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean setSmscAddressOnIccEfForSubscriber(String smsc, int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(smsc);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public int getSmsCapacityOnIccForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean resetAllCellBroadcastRanges(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public long getWapMessageSize(String locationUrl) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(locationUrl);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public byte[] getCbSettingsForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithCBPForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, String callbackNumber, int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeString(text);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    _data.writeString(callbackNumber);
                    _data.writeInt(priority);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPkg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destAddr);
                    try {
                        _data.writeString(scAddr);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(text);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(sentIntent, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(deliveryIntent, 0);
                    try {
                        _data.writeBoolean(replyPath);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(expiry);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(serviceType);
                        try {
                            _data.writeInt(encodingType);
                            try {
                                this.mRemote.transact(38, _data, _reply, 0);
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th9) {
                                th = th9;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextwithOptionsReadconfirmForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean replyPath, int expiry, int serviceType, int encodingType, int confirmID) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(destAddr);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(scAddr);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(text);
                    try {
                        _data.writeTypedObject(sentIntent, 0);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(deliveryIntent, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(replyPath);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(expiry);
                    try {
                        _data.writeInt(serviceType);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(encodingType);
                        try {
                            _data.writeInt(confirmID);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(39, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithCBPForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, String callbackNumber, int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destinationAddress);
                    _data.writeString(scAddress);
                    _data.writeStringList(parts);
                    _data.writeTypedList(sentIntents, 0);
                    _data.writeTypedList(deliveryIntents, 0);
                    _data.writeString(callbackNumber);
                    _data.writeInt(priority);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendMultipartTextwithOptionsForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean replyPath, int expiry, int serviceType, int encodingType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(callingPkg);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(destinationAddress);
                    try {
                        _data.writeString(scAddress);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringList(parts);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedList(sentIntents, 0);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedList(deliveryIntents, 0);
                    try {
                        _data.writeBoolean(replyPath);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(expiry);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(serviceType);
                        try {
                            _data.writeInt(encodingType);
                            try {
                                this.mRemote.transact(41, _data, _reply, 0);
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th9) {
                                th = th9;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th10) {
                            th = th10;
                        }
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSMSPAvailableForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public String getMnoNameForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public boolean getSmsSettingForSubscriber(int subId, String settingName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(settingName);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendDatawithOrigPortForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, int destPort, int origPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeInt(destPort);
                    _data.writeInt(origPort);
                    _data.writeByteArray(data);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendOTADomesticForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, String text) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destinationAddress);
                    _data.writeString(scAddress);
                    _data.writeString(text);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextNSRIForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, byte[] text, PendingIntent sentIntent, PendingIntent deliveryIntent, int msgCount, int msgTotal) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeByteArray(text);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    _data.writeInt(msgCount);
                    _data.writeInt(msgTotal);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void sendTextAutoLoginForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeString(text);
                    _data.writeTypedObject(sentIntent, 0);
                    _data.writeTypedObject(deliveryIntent, 0);
                    _data.writeBoolean(persistMessageForNonDefaultSmsApp);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISms
            public void resetSimFullStatusForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 48;
        }
    }
}
