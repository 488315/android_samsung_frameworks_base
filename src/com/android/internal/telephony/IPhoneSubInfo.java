package com.android.internal.telephony;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ImsiEncryptionInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPhoneSubInfo extends IInterface {

    public static class Default implements IPhoneSubInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getDeviceId(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getDeviceIdForPhone(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getDeviceIdWithFeature(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getDeviceSvn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getDeviceSvnUsingSubId(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getGroupIdLevel1ForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIccSerialNumber(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIccSerialNumberForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIccSerialNumberWithFeature(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIccSimChallengeResponse(int i, int i2, int i3, String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getImeiForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public List<String> getImsPcscfAddresses(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getImsPrivateUserIdentity(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public List<Uri> getImsPublicUserIdentities(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIsimDomain(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIsimImpi(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String[] getIsimImpu(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getIsimIst(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String[] getIsimPcscf(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getLine1AlphaTag(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getLine1AlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getLine1Number(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getLine1NumberForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getMsisdn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getMsisdnForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getNaiForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getSimServiceTable(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public Uri getSmscIdentity(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getSubscriberId(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getSubscriberIdForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getSubscriberIdWithFeature(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getVoiceMailAlphaTag(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getVoiceMailAlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getVoiceMailNumber(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public String getVoiceMailNumberForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public void resetCarrierKeysForImsiEncryption(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneSubInfo
        public void setCarrierInfoForImsiEncryption(int i, String str, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
        }
    }

    ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, String str) throws RemoteException;

    @Deprecated
    String getDeviceId(String str) throws RemoteException;

    String getDeviceIdForPhone(int i, String str, String str2) throws RemoteException;

    String getDeviceIdWithFeature(String str, String str2) throws RemoteException;

    String getDeviceSvn(String str, String str2) throws RemoteException;

    String getDeviceSvnUsingSubId(int i, String str, String str2) throws RemoteException;

    String getGroupIdLevel1ForSubscriber(int i, String str, String str2) throws RemoteException;

    String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException;

    String getIccSerialNumber(String str) throws RemoteException;

    String getIccSerialNumberForSubscriber(int i, String str, String str2) throws RemoteException;

    String getIccSerialNumberWithFeature(String str, String str2) throws RemoteException;

    String getIccSimChallengeResponse(int i, int i2, int i3, String str, String str2, String str3) throws RemoteException;

    String getImeiForSubscriber(int i, String str, String str2) throws RemoteException;

    List<String> getImsPcscfAddresses(int i, String str) throws RemoteException;

    String getImsPrivateUserIdentity(int i, String str, String str2) throws RemoteException;

    List<Uri> getImsPublicUserIdentities(int i, String str) throws RemoteException;

    String getIsimDomain(int i) throws RemoteException;

    String getIsimImpi(int i) throws RemoteException;

    String[] getIsimImpu(int i) throws RemoteException;

    String getIsimIst(int i) throws RemoteException;

    String[] getIsimPcscf(int i) throws RemoteException;

    String getLine1AlphaTag(String str, String str2) throws RemoteException;

    String getLine1AlphaTagForSubscriber(int i, String str, String str2) throws RemoteException;

    String getLine1Number(String str, String str2) throws RemoteException;

    String getLine1NumberForSubscriber(int i, String str, String str2) throws RemoteException;

    String getMsisdn(String str, String str2) throws RemoteException;

    String getMsisdnForSubscriber(int i, String str, String str2) throws RemoteException;

    String getNaiForSubscriber(int i, String str, String str2) throws RemoteException;

    String getSimServiceTable(int i, int i2) throws RemoteException;

    Uri getSmscIdentity(int i, int i2) throws RemoteException;

    @Deprecated
    String getSubscriberId(String str) throws RemoteException;

    String getSubscriberIdForSubscriber(int i, String str, String str2) throws RemoteException;

    String getSubscriberIdWithFeature(String str, String str2) throws RemoteException;

    String getVoiceMailAlphaTag(String str, String str2) throws RemoteException;

    String getVoiceMailAlphaTagForSubscriber(int i, String str, String str2) throws RemoteException;

    String getVoiceMailNumber(String str, String str2) throws RemoteException;

    String getVoiceMailNumberForSubscriber(int i, String str, String str2) throws RemoteException;

    void resetCarrierKeysForImsiEncryption(int i, String str) throws RemoteException;

    void setCarrierInfoForImsiEncryption(int i, String str, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IPhoneSubInfo {
        public static final String DESCRIPTOR = "com.android.internal.telephony.IPhoneSubInfo";
        static final int TRANSACTION_getCarrierInfoForImsiEncryption = 24;
        static final int TRANSACTION_getDeviceId = 1;
        static final int TRANSACTION_getDeviceIdForPhone = 4;
        static final int TRANSACTION_getDeviceIdWithFeature = 2;
        static final int TRANSACTION_getDeviceSvn = 6;
        static final int TRANSACTION_getDeviceSvnUsingSubId = 7;
        static final int TRANSACTION_getGroupIdLevel1ForSubscriber = 11;
        static final int TRANSACTION_getGroupIdLevel2ForSubscriber = 12;
        static final int TRANSACTION_getIccSerialNumber = 13;
        static final int TRANSACTION_getIccSerialNumberForSubscriber = 15;
        static final int TRANSACTION_getIccSerialNumberWithFeature = 14;
        static final int TRANSACTION_getIccSimChallengeResponse = 37;
        static final int TRANSACTION_getImeiForSubscriber = 5;
        static final int TRANSACTION_getImsPcscfAddresses = 36;
        static final int TRANSACTION_getImsPrivateUserIdentity = 30;
        static final int TRANSACTION_getImsPublicUserIdentities = 33;
        static final int TRANSACTION_getIsimDomain = 31;
        static final int TRANSACTION_getIsimImpi = 29;
        static final int TRANSACTION_getIsimImpu = 32;
        static final int TRANSACTION_getIsimIst = 34;
        static final int TRANSACTION_getIsimPcscf = 35;
        static final int TRANSACTION_getLine1AlphaTag = 18;
        static final int TRANSACTION_getLine1AlphaTagForSubscriber = 19;
        static final int TRANSACTION_getLine1Number = 16;
        static final int TRANSACTION_getLine1NumberForSubscriber = 17;
        static final int TRANSACTION_getMsisdn = 20;
        static final int TRANSACTION_getMsisdnForSubscriber = 21;
        static final int TRANSACTION_getNaiForSubscriber = 3;
        static final int TRANSACTION_getSimServiceTable = 39;
        static final int TRANSACTION_getSmscIdentity = 38;
        static final int TRANSACTION_getSubscriberId = 8;
        static final int TRANSACTION_getSubscriberIdForSubscriber = 10;
        static final int TRANSACTION_getSubscriberIdWithFeature = 9;
        static final int TRANSACTION_getVoiceMailAlphaTag = 27;
        static final int TRANSACTION_getVoiceMailAlphaTagForSubscriber = 28;
        static final int TRANSACTION_getVoiceMailNumber = 22;
        static final int TRANSACTION_getVoiceMailNumberForSubscriber = 23;
        static final int TRANSACTION_resetCarrierKeysForImsiEncryption = 26;
        static final int TRANSACTION_setCarrierInfoForImsiEncryption = 25;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneSubInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPhoneSubInfo)) {
                return (IPhoneSubInfo) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getDeviceId";
                case 2:
                    return "getDeviceIdWithFeature";
                case 3:
                    return "getNaiForSubscriber";
                case 4:
                    return "getDeviceIdForPhone";
                case 5:
                    return "getImeiForSubscriber";
                case 6:
                    return "getDeviceSvn";
                case 7:
                    return "getDeviceSvnUsingSubId";
                case 8:
                    return "getSubscriberId";
                case 9:
                    return "getSubscriberIdWithFeature";
                case 10:
                    return "getSubscriberIdForSubscriber";
                case 11:
                    return "getGroupIdLevel1ForSubscriber";
                case 12:
                    return "getGroupIdLevel2ForSubscriber";
                case 13:
                    return "getIccSerialNumber";
                case 14:
                    return "getIccSerialNumberWithFeature";
                case 15:
                    return "getIccSerialNumberForSubscriber";
                case 16:
                    return "getLine1Number";
                case 17:
                    return "getLine1NumberForSubscriber";
                case 18:
                    return "getLine1AlphaTag";
                case 19:
                    return "getLine1AlphaTagForSubscriber";
                case 20:
                    return "getMsisdn";
                case 21:
                    return "getMsisdnForSubscriber";
                case 22:
                    return "getVoiceMailNumber";
                case 23:
                    return "getVoiceMailNumberForSubscriber";
                case 24:
                    return "getCarrierInfoForImsiEncryption";
                case 25:
                    return "setCarrierInfoForImsiEncryption";
                case 26:
                    return "resetCarrierKeysForImsiEncryption";
                case 27:
                    return "getVoiceMailAlphaTag";
                case 28:
                    return "getVoiceMailAlphaTagForSubscriber";
                case 29:
                    return "getIsimImpi";
                case 30:
                    return "getImsPrivateUserIdentity";
                case 31:
                    return "getIsimDomain";
                case 32:
                    return "getIsimImpu";
                case 33:
                    return "getImsPublicUserIdentities";
                case 34:
                    return "getIsimIst";
                case 35:
                    return "getIsimPcscf";
                case 36:
                    return "getImsPcscfAddresses";
                case 37:
                    return "getIccSimChallengeResponse";
                case 38:
                    return "getSmscIdentity";
                case 39:
                    return "getSimServiceTable";
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
                    parcel.enforceNoDataAvail();
                    String deviceId = getDeviceId(string);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceIdWithFeature = getDeviceIdWithFeature(string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdWithFeature);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String naiForSubscriber = getNaiForSubscriber(i3, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeString(naiForSubscriber);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceIdForPhone = getDeviceIdForPhone(i4, string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdForPhone);
                    return true;
                case 5:
                    int i5 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String imeiForSubscriber = getImeiForSubscriber(i5, string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeString(imeiForSubscriber);
                    return true;
                case 6:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceSvn = getDeviceSvn(string10, string11);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvn);
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceSvnUsingSubId = getDeviceSvnUsingSubId(i6, string12, string13);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvnUsingSubId);
                    return true;
                case 8:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberId = getSubscriberId(string14);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberId);
                    return true;
                case 9:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdWithFeature = getSubscriberIdWithFeature(string15, string16);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdWithFeature);
                    return true;
                case 10:
                    int i7 = parcel.readInt();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdForSubscriber = getSubscriberIdForSubscriber(i7, string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdForSubscriber);
                    return true;
                case 11:
                    int i8 = parcel.readInt();
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel1ForSubscriber = getGroupIdLevel1ForSubscriber(i8, string19, string20);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel1ForSubscriber);
                    return true;
                case 12:
                    int i9 = parcel.readInt();
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel2ForSubscriber = getGroupIdLevel2ForSubscriber(i9, string21, string22);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel2ForSubscriber);
                    return true;
                case 13:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumber = getIccSerialNumber(string23);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumber);
                    return true;
                case 14:
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumberWithFeature = getIccSerialNumberWithFeature(string24, string25);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberWithFeature);
                    return true;
                case 15:
                    int i10 = parcel.readInt();
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumberForSubscriber = getIccSerialNumberForSubscriber(i10, string26, string27);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberForSubscriber);
                    return true;
                case 16:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1Number = getLine1Number(string28, string29);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 17:
                    int i11 = parcel.readInt();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1NumberForSubscriber = getLine1NumberForSubscriber(i11, string30, string31);
                    parcel2.writeNoException();
                    parcel2.writeString(line1NumberForSubscriber);
                    return true;
                case 18:
                    String string32 = parcel.readString();
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1AlphaTag = getLine1AlphaTag(string32, string33);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTag);
                    return true;
                case 19:
                    int i12 = parcel.readInt();
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1AlphaTagForSubscriber = getLine1AlphaTagForSubscriber(i12, string34, string35);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTagForSubscriber);
                    return true;
                case 20:
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String msisdn = getMsisdn(string36, string37);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdn);
                    return true;
                case 21:
                    int i13 = parcel.readInt();
                    String string38 = parcel.readString();
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String msisdnForSubscriber = getMsisdnForSubscriber(i13, string38, string39);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdnForSubscriber);
                    return true;
                case 22:
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumber = getVoiceMailNumber(string40, string41);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 23:
                    int i14 = parcel.readInt();
                    String string42 = parcel.readString();
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumberForSubscriber = getVoiceMailNumberForSubscriber(i14, string42, string43);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumberForSubscriber);
                    return true;
                case 24:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ImsiEncryptionInfo carrierInfoForImsiEncryption = getCarrierInfoForImsiEncryption(i15, i16, string44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierInfoForImsiEncryption, 1);
                    return true;
                case 25:
                    int i17 = parcel.readInt();
                    String string45 = parcel.readString();
                    ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) parcel.readTypedObject(ImsiEncryptionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryption(i17, string45, imsiEncryptionInfo);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i18 = parcel.readInt();
                    String string46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetCarrierKeysForImsiEncryption(i18, string46);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string47 = parcel.readString();
                    String string48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailAlphaTag = getVoiceMailAlphaTag(string47, string48);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTag);
                    return true;
                case 28:
                    int i19 = parcel.readInt();
                    String string49 = parcel.readString();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailAlphaTagForSubscriber = getVoiceMailAlphaTagForSubscriber(i19, string49, string50);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTagForSubscriber);
                    return true;
                case 29:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimImpi = getIsimImpi(i20);
                    parcel2.writeNoException();
                    parcel2.writeString(isimImpi);
                    return true;
                case 30:
                    int i21 = parcel.readInt();
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String imsPrivateUserIdentity = getImsPrivateUserIdentity(i21, string51, string52);
                    parcel2.writeNoException();
                    parcel2.writeString(imsPrivateUserIdentity);
                    return true;
                case 31:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimDomain = getIsimDomain(i22);
                    parcel2.writeNoException();
                    parcel2.writeString(isimDomain);
                    return true;
                case 32:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] isimImpu = getIsimImpu(i23);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimImpu);
                    return true;
                case 33:
                    int i24 = parcel.readInt();
                    String string53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Uri> imsPublicUserIdentities = getImsPublicUserIdentities(i24, string53);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(imsPublicUserIdentities, 1);
                    return true;
                case 34:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimIst = getIsimIst(i25);
                    parcel2.writeNoException();
                    parcel2.writeString(isimIst);
                    return true;
                case 35:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] isimPcscf = getIsimPcscf(i26);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimPcscf);
                    return true;
                case 36:
                    int i27 = parcel.readInt();
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> imsPcscfAddresses = getImsPcscfAddresses(i27, string54);
                    parcel2.writeNoException();
                    parcel2.writeStringList(imsPcscfAddresses);
                    return true;
                case 37:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    String string55 = parcel.readString();
                    String string56 = parcel.readString();
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSimChallengeResponse = getIccSimChallengeResponse(i28, i29, i30, string55, string56, string57);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSimChallengeResponse);
                    return true;
                case 38:
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Uri smscIdentity = getSmscIdentity(i31, i32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(smscIdentity, 1);
                    return true;
                case 39:
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String simServiceTable = getSimServiceTable(i33, i34);
                    parcel2.writeNoException();
                    parcel2.writeString(simServiceTable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPhoneSubInfo {
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

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceIdWithFeature(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getNaiForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceIdForPhone(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getImeiForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceSvn(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceSvnUsingSubId(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberIdWithFeature(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberIdForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getGroupIdLevel1ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumber(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumberWithFeature(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1Number(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1NumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1AlphaTag(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1AlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getMsisdn(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getMsisdnForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailNumber(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailNumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsiEncryptionInfo) parcelObtain2.readTypedObject(ImsiEncryptionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void setCarrierInfoForImsiEncryption(int i, String str, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(imsiEncryptionInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void resetCarrierKeysForImsiEncryption(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailAlphaTag(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailAlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimImpi(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getImsPrivateUserIdentity(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimDomain(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String[] getIsimImpu(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public List<Uri> getImsPublicUserIdentities(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimIst(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String[] getIsimPcscf(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public List<String> getImsPcscfAddresses(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSimChallengeResponse(int i, int i2, int i3, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public Uri getSmscIdentity(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSimServiceTable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
