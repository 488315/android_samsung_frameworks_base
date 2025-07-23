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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPhoneSubInfo)) {
                return (IPhoneSubInfo) queryLocalInterface;
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceId = getDeviceId(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceIdWithFeature = getDeviceIdWithFeature(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdWithFeature);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String naiForSubscriber = getNaiForSubscriber(readInt, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeString(naiForSubscriber);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceIdForPhone = getDeviceIdForPhone(readInt2, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceIdForPhone);
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String imeiForSubscriber = getImeiForSubscriber(readInt3, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeString(imeiForSubscriber);
                    return true;
                case 6:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceSvn = getDeviceSvn(readString10, readString11);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvn);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String deviceSvnUsingSubId = getDeviceSvnUsingSubId(readInt4, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceSvnUsingSubId);
                    return true;
                case 8:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberId = getSubscriberId(readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberId);
                    return true;
                case 9:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdWithFeature = getSubscriberIdWithFeature(readString15, readString16);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdWithFeature);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdForSubscriber = getSubscriberIdForSubscriber(readInt5, readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdForSubscriber);
                    return true;
                case 11:
                    int readInt6 = parcel.readInt();
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel1ForSubscriber = getGroupIdLevel1ForSubscriber(readInt6, readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel1ForSubscriber);
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel2ForSubscriber = getGroupIdLevel2ForSubscriber(readInt7, readString21, readString22);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel2ForSubscriber);
                    return true;
                case 13:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumber = getIccSerialNumber(readString23);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumber);
                    return true;
                case 14:
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumberWithFeature = getIccSerialNumberWithFeature(readString24, readString25);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberWithFeature);
                    return true;
                case 15:
                    int readInt8 = parcel.readInt();
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSerialNumberForSubscriber = getIccSerialNumberForSubscriber(readInt8, readString26, readString27);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSerialNumberForSubscriber);
                    return true;
                case 16:
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1Number = getLine1Number(readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 17:
                    int readInt9 = parcel.readInt();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1NumberForSubscriber = getLine1NumberForSubscriber(readInt9, readString30, readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(line1NumberForSubscriber);
                    return true;
                case 18:
                    String readString32 = parcel.readString();
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1AlphaTag = getLine1AlphaTag(readString32, readString33);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTag);
                    return true;
                case 19:
                    int readInt10 = parcel.readInt();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1AlphaTagForSubscriber = getLine1AlphaTagForSubscriber(readInt10, readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeString(line1AlphaTagForSubscriber);
                    return true;
                case 20:
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String msisdn = getMsisdn(readString36, readString37);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdn);
                    return true;
                case 21:
                    int readInt11 = parcel.readInt();
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String msisdnForSubscriber = getMsisdnForSubscriber(readInt11, readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeString(msisdnForSubscriber);
                    return true;
                case 22:
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumber = getVoiceMailNumber(readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 23:
                    int readInt12 = parcel.readInt();
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumberForSubscriber = getVoiceMailNumberForSubscriber(readInt12, readString42, readString43);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumberForSubscriber);
                    return true;
                case 24:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ImsiEncryptionInfo carrierInfoForImsiEncryption = getCarrierInfoForImsiEncryption(readInt13, readInt14, readString44);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(carrierInfoForImsiEncryption, 1);
                    return true;
                case 25:
                    int readInt15 = parcel.readInt();
                    String readString45 = parcel.readString();
                    ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) parcel.readTypedObject(ImsiEncryptionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryption(readInt15, readString45, imsiEncryptionInfo);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt16 = parcel.readInt();
                    String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetCarrierKeysForImsiEncryption(readInt16, readString46);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString47 = parcel.readString();
                    String readString48 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailAlphaTag = getVoiceMailAlphaTag(readString47, readString48);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTag);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailAlphaTagForSubscriber = getVoiceMailAlphaTagForSubscriber(readInt17, readString49, readString50);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailAlphaTagForSubscriber);
                    return true;
                case 29:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimImpi = getIsimImpi(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeString(isimImpi);
                    return true;
                case 30:
                    int readInt19 = parcel.readInt();
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String imsPrivateUserIdentity = getImsPrivateUserIdentity(readInt19, readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeString(imsPrivateUserIdentity);
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimDomain = getIsimDomain(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(isimDomain);
                    return true;
                case 32:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] isimImpu = getIsimImpu(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimImpu);
                    return true;
                case 33:
                    int readInt22 = parcel.readInt();
                    String readString53 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Uri> imsPublicUserIdentities = getImsPublicUserIdentities(readInt22, readString53);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(imsPublicUserIdentities, 1);
                    return true;
                case 34:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isimIst = getIsimIst(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeString(isimIst);
                    return true;
                case 35:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] isimPcscf = getIsimPcscf(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(isimPcscf);
                    return true;
                case 36:
                    int readInt25 = parcel.readInt();
                    String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> imsPcscfAddresses = getImsPcscfAddresses(readInt25, readString54);
                    parcel2.writeNoException();
                    parcel2.writeStringList(imsPcscfAddresses);
                    return true;
                case 37:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    String readString55 = parcel.readString();
                    String readString56 = parcel.readString();
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String iccSimChallengeResponse = getIccSimChallengeResponse(readInt26, readInt27, readInt28, readString55, readString56, readString57);
                    parcel2.writeNoException();
                    parcel2.writeString(iccSimChallengeResponse);
                    return true;
                case 38:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Uri smscIdentity = getSmscIdentity(readInt29, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(smscIdentity, 1);
                    return true;
                case 39:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String simServiceTable = getSimServiceTable(readInt31, readInt32);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceIdWithFeature(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getNaiForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceIdForPhone(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getImeiForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceSvn(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getDeviceSvnUsingSubId(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberIdWithFeature(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSubscriberIdForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getGroupIdLevel1ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumber(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumberWithFeature(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSerialNumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1Number(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1NumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1AlphaTag(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getLine1AlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getMsisdn(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getMsisdnForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailNumber(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailNumberForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsiEncryptionInfo) obtain2.readTypedObject(ImsiEncryptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void setCarrierInfoForImsiEncryption(int i, String str, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(imsiEncryptionInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public void resetCarrierKeysForImsiEncryption(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailAlphaTag(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getVoiceMailAlphaTagForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimImpi(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getImsPrivateUserIdentity(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimDomain(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String[] getIsimImpu(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public List<Uri> getImsPublicUserIdentities(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIsimIst(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String[] getIsimPcscf(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public List<String> getImsPcscfAddresses(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getIccSimChallengeResponse(int i, int i2, int i3, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public Uri getSmscIdentity(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneSubInfo
            public String getSimServiceTable(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
