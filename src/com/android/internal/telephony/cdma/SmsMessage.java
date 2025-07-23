package com.android.internal.telephony.cdma;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.sysprop.TelephonyProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.telephony.cdma.CdmaSmsCbProgramData;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.telephony.cdma.sms.BearerData;
import com.android.internal.telephony.cdma.sms.CdmaSmsAddress;
import com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.util.BitwiseInputStream;
import com.android.internal.util.HexDump;
import com.android.telephony.Rlog;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes4.dex */
public class SmsMessage extends SmsMessageBase {
    private static final byte BEARER_DATA = 8;
    private static final byte BEARER_REPLY_OPTION = 6;
    private static final byte CAUSE_CODES = 7;
    private static final byte DESTINATION_ADDRESS = 4;
    private static final byte DESTINATION_SUB_ADDRESS = 5;
    private static final String LOGGABLE_TAG = "CDMA:SMS";
    static final String LOG_TAG = "SmsMessage";
    private static final byte ORIGINATING_ADDRESS = 2;
    private static final byte ORIGINATING_SUB_ADDRESS = 3;
    private static final int PRIORITY_EMERGENCY = 3;
    private static final int PRIORITY_INTERACTIVE = 1;
    private static final int PRIORITY_NORMAL = 0;
    private static final int PRIORITY_URGENT = 2;
    private static final int RETURN_ACK = 1;
    private static final int RETURN_NO_ACK = 0;
    private static final byte SERVICE_CATEGORY = 1;
    private static final byte TELESERVICE_IDENTIFIER = 0;
    private static final boolean VDBG = false;
    private BearerData mBearerData;
    private SmsEnvelope mEnvelope;
    private boolean mIsCtcFota = false;
    private byte[] mUserDataCtcFota;
    private int status;

    public static class SubmitPdu extends SmsMessageBase.SubmitPduBase {
    }

    public static byte convertDtmfToAscii(byte b) {
        switch (b) {
        }
        return SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90;
    }

    public SmsMessage(SmsAddress smsAddress, SmsEnvelope smsEnvelope) {
        this.mOriginatingAddress = smsAddress;
        this.mEnvelope = smsEnvelope;
        createPdu();
    }

    public SmsMessage() {
    }

    public static SmsMessage createFromPdu(byte[] bArr) {
        return semCreateFromPdu(SmsManager.getDefaultSmsSubscriptionId(), bArr);
    }

    public static SmsMessage createFromEfRecord(int i, byte[] bArr) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.mIndexOnIcc = i;
            byte b = bArr[0];
            if ((b & 1) == 0) {
                Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            smsMessage.mStatusOnIcc = b & 7;
            if (smsMessage.mStatusOnIcc != 1 && smsMessage.mStatusOnIcc != 3) {
                smsMessage.mMti = 1;
                int i2 = bArr[1] & 255;
                Rlog.d(LOG_TAG, "msg[" + i + "]statusOnIcc: " + smsMessage.mStatusOnIcc + " size:" + i2);
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, 2, bArr2, 0, i2);
                smsMessage.parsePduFromEfRecord(bArr2);
                return smsMessage;
            }
            smsMessage.mMti = 0;
            int i22 = bArr[1] & 255;
            Rlog.d(LOG_TAG, "msg[" + i + "]statusOnIcc: " + smsMessage.mStatusOnIcc + " size:" + i22);
            byte[] bArr22 = new byte[i22];
            System.arraycopy(bArr, 2, bArr22, 0, i22);
            smsMessage.parsePduFromEfRecord(bArr22);
            return smsMessage;
        } catch (RuntimeException e) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(String str) {
        Rlog.w(LOG_TAG, "getTPLayerLengthForPDU: is not supported in CDMA mode.");
        return 0;
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, SmsHeader smsHeader) {
        return getSubmitPdu(str, str2, str3, z, smsHeader, -1);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, SmsHeader smsHeader, int i) {
        if (str3 == null || str2 == null) {
            return null;
        }
        UserData userData = new UserData();
        userData.payloadStr = str3;
        userData.userDataHeader = smsHeader;
        return privateGetSubmitPdu(str2, z, userData, i);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, int i, byte[] bArr, boolean z) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = i;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        UserData userData = new UserData();
        if (TelephonyFeatures.isCountrySpecific(SubscriptionManager.getPhoneId(SmsManager.getDefault().getSubscriptionId()), "CHN")) {
            userData.msgEncoding = 4;
            userData.msgEncodingSet = false;
            userData.payloadStr = new String(bArr);
        } else {
            userData.userDataHeader = smsHeader;
            userData.msgEncoding = 0;
            userData.msgEncodingSet = true;
            userData.payload = bArr;
        }
        return privateGetSubmitPdu(str2, z, userData);
    }

    public static SubmitPdu getSubmitPdu(String str, UserData userData, boolean z) {
        return privateGetSubmitPdu(str, z, userData);
    }

    public static SubmitPdu getSubmitPdu(String str, UserData userData, boolean z, int i) {
        return privateGetSubmitPdu(str, z, userData, i);
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getProtocolIdentifier() {
        Rlog.w(LOG_TAG, "getProtocolIdentifier: is not supported in CDMA mode.");
        return 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplace() {
        Rlog.w(LOG_TAG, "isReplace: is not supported in CDMA mode.");
        return false;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isCphsMwiMessage() {
        Rlog.w(LOG_TAG, "isCphsMwiMessage: is not supported in CDMA mode.");
        return false;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWIClearMessage() {
        BearerData bearerData = this.mBearerData;
        return bearerData != null && bearerData.numberOfMessages == 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWISetMessage() {
        BearerData bearerData = this.mBearerData;
        return bearerData != null && bearerData.numberOfMessages > 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMwiDontStore() {
        BearerData bearerData = this.mBearerData;
        return bearerData != null && bearerData.numberOfMessages > 0 && this.mBearerData.userData == null;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getStatus() {
        return this.status << 16;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isStatusReportMessage() {
        return this.mBearerData.messageType == 4;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplyPathPresent() {
        Rlog.w(LOG_TAG, "isReplyPathPresent: is not supported in CDMA mode.");
        return false;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean z, boolean z2) {
        return BearerData.calcTextEncodingDetails(charSequence, z, z2);
    }

    public int getTeleService() {
        return this.mEnvelope.teleService;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageType() {
        Rlog.d(LOG_TAG, "getMessageType = " + this.mMti);
        return this.mMti;
    }

    private void parsePdu(byte[] bArr) {
        int readUnsignedByte;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
        try {
            smsEnvelope.messageType = dataInputStream.readInt();
            smsEnvelope.teleService = dataInputStream.readInt();
            smsEnvelope.serviceCategory = dataInputStream.readInt();
            cdmaSmsAddress.digitMode = dataInputStream.readByte();
            cdmaSmsAddress.numberMode = dataInputStream.readByte();
            cdmaSmsAddress.ton = dataInputStream.readByte();
            cdmaSmsAddress.numberPlan = dataInputStream.readByte();
            readUnsignedByte = dataInputStream.readUnsignedByte();
            cdmaSmsAddress.numberOfDigits = readUnsignedByte;
        } catch (IOException e) {
            throw new RuntimeException("createFromPdu: conversion from byte array to object failed: " + e, e);
        } catch (Exception e2) {
            Rlog.e(LOG_TAG, "createFromPdu: conversion from byte array to object failed: " + e2);
        }
        if (readUnsignedByte > bArr.length) {
            throw new RuntimeException("createFromPdu: Invalid pdu, addr.numberOfDigits " + readUnsignedByte + " > pdu len " + bArr.length);
        }
        cdmaSmsAddress.origBytes = new byte[readUnsignedByte];
        dataInputStream.read(cdmaSmsAddress.origBytes, 0, readUnsignedByte);
        smsEnvelope.bearerReply = dataInputStream.readInt();
        smsEnvelope.replySeqNo = dataInputStream.readByte();
        smsEnvelope.errorClass = dataInputStream.readByte();
        smsEnvelope.causeCode = dataInputStream.readByte();
        int readInt = dataInputStream.readInt();
        if (readInt > bArr.length) {
            throw new RuntimeException("createFromPdu: Invalid pdu, bearerDataLength " + readInt + " > pdu len " + bArr.length);
        }
        smsEnvelope.bearerData = new byte[readInt];
        dataInputStream.read(smsEnvelope.bearerData, 0, readInt);
        dataInputStream.close();
        this.mOriginatingAddress = cdmaSmsAddress;
        smsEnvelope.origAddress = cdmaSmsAddress;
        smsEnvelope.origSubaddress = cdmaSmsSubaddress;
        this.mEnvelope = smsEnvelope;
        this.mPdu = bArr;
        parseSms();
        if (!SmsManager.getDefault().getMnoName().toUpperCase().contains("KDDI") || this.mBearerData.callbackNumber == null) {
            return;
        }
        this.mOriginatingAddress = this.mBearerData.callbackNumber;
        smsEnvelope.origAddress = this.mBearerData.callbackNumber;
    }

    private void parsePduFromEfRecord(byte[] bArr) {
        int i;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
        try {
            smsEnvelope.messageType = dataInputStream.readByte();
            while (dataInputStream.available() > 0) {
                byte readByte = dataInputStream.readByte();
                int readUnsignedByte = dataInputStream.readUnsignedByte();
                byte[] bArr2 = new byte[readUnsignedByte];
                int i2 = 0;
                switch (readByte) {
                    case 0:
                        smsEnvelope.teleService = dataInputStream.readUnsignedShort();
                        Rlog.i(LOG_TAG, "teleservice = " + smsEnvelope.teleService);
                        break;
                    case 1:
                        smsEnvelope.serviceCategory = dataInputStream.readUnsignedShort();
                        break;
                    case 2:
                    case 4:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        BitwiseInputStream bitwiseInputStream = new BitwiseInputStream(bArr2);
                        cdmaSmsAddress.digitMode = bitwiseInputStream.read(1);
                        cdmaSmsAddress.numberMode = bitwiseInputStream.read(1);
                        if (cdmaSmsAddress.digitMode == 1) {
                            i = bitwiseInputStream.read(3);
                            cdmaSmsAddress.ton = i;
                            if (cdmaSmsAddress.numberMode == 0) {
                                cdmaSmsAddress.numberPlan = bitwiseInputStream.read(4);
                            }
                        } else {
                            i = 0;
                        }
                        cdmaSmsAddress.numberOfDigits = bitwiseInputStream.read(8);
                        byte[] bArr3 = new byte[cdmaSmsAddress.numberOfDigits];
                        if (cdmaSmsAddress.digitMode == 0) {
                            while (i2 < cdmaSmsAddress.numberOfDigits) {
                                bArr3[i2] = convertDtmfToAscii((byte) (bitwiseInputStream.read(4) & 15));
                                i2++;
                            }
                        } else if (cdmaSmsAddress.digitMode != 1) {
                            Rlog.e(LOG_TAG, "Incorrect Digit mode");
                        } else if (cdmaSmsAddress.numberMode == 0) {
                            for (int i3 = 0; i3 < cdmaSmsAddress.numberOfDigits; i3++) {
                                bArr3[i3] = (byte) (bitwiseInputStream.read(8) & 255);
                            }
                        } else if (cdmaSmsAddress.numberMode != 1) {
                            Rlog.e(LOG_TAG, "Addr is of incorrect type");
                        } else if (i == 2) {
                            Rlog.e(LOG_TAG, "TODO: Addr is email id");
                        } else {
                            Rlog.e(LOG_TAG, "TODO: Addr is data network address");
                        }
                        cdmaSmsAddress.origBytes = bArr3;
                        Rlog.pii(LOG_TAG, "Addr=" + cdmaSmsAddress.toString());
                        if (readByte == 2) {
                            smsEnvelope.origAddress = cdmaSmsAddress;
                            this.mOriginatingAddress = cdmaSmsAddress;
                            break;
                        } else {
                            smsEnvelope.destAddress = cdmaSmsAddress;
                            this.mRecipientAddress = cdmaSmsAddress;
                            break;
                        }
                    case 3:
                    case 5:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        BitwiseInputStream bitwiseInputStream2 = new BitwiseInputStream(bArr2);
                        cdmaSmsSubaddress.type = bitwiseInputStream2.read(3);
                        cdmaSmsSubaddress.odd = bitwiseInputStream2.readByteArray(1)[0];
                        int read = bitwiseInputStream2.read(8);
                        byte[] bArr4 = new byte[read];
                        while (i2 < read) {
                            bArr4[i2] = convertDtmfToAscii((byte) (bitwiseInputStream2.read(4) & 255));
                            i2++;
                        }
                        cdmaSmsSubaddress.origBytes = bArr4;
                        if (readByte == 3) {
                            smsEnvelope.origSubaddress = cdmaSmsSubaddress;
                            break;
                        } else {
                            smsEnvelope.destSubaddress = cdmaSmsSubaddress;
                            break;
                        }
                    case 6:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        smsEnvelope.bearerReply = new BitwiseInputStream(bArr2).read(6);
                        break;
                    case 7:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        BitwiseInputStream bitwiseInputStream3 = new BitwiseInputStream(bArr2);
                        smsEnvelope.replySeqNo = bitwiseInputStream3.readByteArray(6)[0];
                        smsEnvelope.errorClass = bitwiseInputStream3.readByteArray(2)[0];
                        if (smsEnvelope.errorClass != 0) {
                            smsEnvelope.causeCode = bitwiseInputStream3.readByteArray(8)[0];
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        dataInputStream.read(bArr2, 0, readUnsignedByte);
                        smsEnvelope.bearerData = bArr2;
                        break;
                    default:
                        throw new Exception("unsupported parameterId (" + ((int) readByte) + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            byteArrayInputStream.close();
            dataInputStream.close();
        } catch (Exception e) {
            Rlog.e(LOG_TAG, "parsePduFromEfRecord: conversion from pdu to SmsMessage failed" + e);
        }
        this.mEnvelope = smsEnvelope;
        this.mPdu = bArr;
        parseSms();
    }

    public boolean preprocessCdmaFdeaWap() {
        try {
            BitwiseInputStream bitwiseInputStream = new BitwiseInputStream(this.mUserData);
            if (bitwiseInputStream.read(8) != 0) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAMETER_ID");
                return false;
            }
            if (bitwiseInputStream.read(8) != 3) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAM_LEN");
                return false;
            }
            this.mBearerData.messageType = bitwiseInputStream.read(4);
            int read = (bitwiseInputStream.read(8) << 8) | bitwiseInputStream.read(8);
            this.mBearerData.messageId = read;
            this.mMessageRef = read;
            this.mBearerData.hasUserDataHeader = bitwiseInputStream.read(1) == 1;
            if (this.mBearerData.hasUserDataHeader) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier HEADER_IND");
                return false;
            }
            bitwiseInputStream.skip(3);
            if (bitwiseInputStream.read(8) != 1) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data SUBPARAMETER_ID");
                return false;
            }
            int read2 = bitwiseInputStream.read(8) * 8;
            this.mBearerData.userData.msgEncoding = bitwiseInputStream.read(5);
            if (this.mBearerData.userData.msgEncoding != 0) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data MSG_ENCODING");
                return false;
            }
            this.mBearerData.userData.numFields = bitwiseInputStream.read(8);
            int i = read2 - 13;
            int i2 = this.mBearerData.userData.numFields * 8;
            if (i2 < i) {
                i = i2;
            }
            this.mBearerData.userData.payload = bitwiseInputStream.readByteArray(i);
            this.mUserData = this.mBearerData.userData.payload;
            return true;
        } catch (BitwiseInputStream.AccessException e) {
            Rlog.e(LOG_TAG, "Fail to preprocess FDEA WAP: " + e);
            return false;
        }
    }

    public void parseSms() {
        if (this.mEnvelope.teleService == 262144) {
            this.mBearerData = new BearerData();
            if (this.mEnvelope.bearerData != null) {
                this.mBearerData.numberOfMessages = this.mEnvelope.bearerData[0] & 255;
            }
            parseSpecificTid(this.mEnvelope.teleService);
            return;
        }
        this.mBearerData = BearerData.decode(this.mEnvelope.bearerData);
        if (BearerData.mIsfourBytesUnicode) {
            this.mIsfourBytesUnicode = true;
            this.mlastByte = new byte[2];
            this.mBodyOffset = BearerData.mBodyOffset;
            this.mlastByte[0] = BearerData.mlastByte[0];
            this.mlastByte[1] = BearerData.mlastByte[1];
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MT raw BearerData = '" + HexDump.toHexString(this.mEnvelope.bearerData) + "'");
            StringBuilder sb = new StringBuilder("MT (decoded) BearerData = ");
            sb.append(this.mBearerData);
            Rlog.d(LOG_TAG, sb.toString());
        }
        this.mMessageRef = this.mBearerData.messageId;
        if (this.mBearerData.userData != null) {
            this.mUserData = this.mBearerData.userData.payload;
            this.mUserDataHeader = this.mBearerData.userData.userDataHeader;
            this.mMessageBody = this.mBearerData.userData.payloadStr;
            if (this.mBearerData.userData.msgEncodingSet) {
                this.mReceivedEncodingType = this.mBearerData.userData.msgEncoding;
            }
            if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_WAP_PUSH_FORMAT_SMS) && this.mEnvelope.teleService == 4098 && this.mBearerData.userData.msgEncoding == 0) {
                this.mUserDataCtcFota = new byte[this.mUserData.length];
                System.arraycopy(this.mUserData, 0, this.mUserDataCtcFota, 0, this.mUserData.length);
            }
        }
        if (this.mBearerData.callbackNumber != null) {
            Rlog.d(LOG_TAG, "parseSms() callback = " + this.mBearerData.callbackNumber);
            this.callbackNumber = this.mBearerData.callbackNumber.address;
        }
        if (this.mOriginatingAddress != null) {
            decodeSmsDisplayAddress(this.mOriginatingAddress);
        }
        if (this.mRecipientAddress != null) {
            decodeSmsDisplayAddress(this.mRecipientAddress);
        }
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            if (this.mBearerData.callbackNumber != null) {
                Rlog.e(LOG_TAG, "SMS callback number: " + this.mBearerData.callbackNumber.address);
                this.replyAddress = this.mBearerData.callbackNumber;
                Rlog.e(LOG_TAG, "SMS CALL BACK NUMBER: getDisplayOriginatingAddress(): " + getDisplayOriginatingAddress());
            } else {
                this.replyAddress = null;
                Rlog.e(LOG_TAG, "SMS CALL BACK NUMBER: null  getDisplayOriginatingAddress(): " + getDisplayOriginatingAddress());
            }
        }
        if (this.mBearerData.msgCenterTimeStamp != null) {
            this.mScTimeMillis = this.mBearerData.msgCenterTimeStamp.toMillis();
        }
        this.mTeleserviceId = this.mEnvelope.teleService;
        if (SmsManager.getDefault().getMnoName().toUpperCase().contains("LGU")) {
            parseSpecificTid(this.mEnvelope.teleService);
        }
        if (this.mBearerData.messageType == 4) {
            if (!this.mBearerData.messageStatusSet) {
                StringBuilder sb2 = new StringBuilder("DELIVERY_ACK message without msgStatus (");
                sb2.append(this.mUserData == null ? "also missing" : "does have");
                sb2.append(" userData).");
                Rlog.d(LOG_TAG, sb2.toString());
                this.status = 2;
            } else {
                int i = this.mBearerData.errorClass << 8;
                this.status = i;
                this.status = i | this.mBearerData.messageStatus;
            }
        } else if (this.mBearerData.messageType != 1 && this.mBearerData.messageType != 2) {
            throw new RuntimeException("Unsupported message type: " + this.mBearerData.messageType);
        }
        if (this.mMessageBody != null) {
            parseMessageBody();
        } else {
            byte[] bArr = this.mUserData;
        }
    }

    private void decodeSmsDisplayAddress(SmsAddress smsAddress) {
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_SPECIAL_ADDRESS_HANDLING_FOR)) {
            if (smsAddress.ton == 0) {
                String str = new String(smsAddress.origBytes);
                if (!TextUtils.isEmpty(str) && str.startsWith("00852")) {
                    Rlog.d(LOG_TAG, "receive sms from HK number Before Address= ".concat(str));
                    String substring = str.substring(2);
                    smsAddress.address = "+";
                    smsAddress.address += substring;
                    Rlog.d(LOG_TAG, "After Address Replacement = " + smsAddress.address);
                    return;
                }
                smsAddress.address = str;
                return;
            }
            if (smsAddress.ton == 1) {
                smsAddress.address = new String(smsAddress.origBytes);
                if (TextUtils.isEmpty(smsAddress.address) || smsAddress.address.charAt(0) == '+') {
                    return;
                }
                smsAddress.address = "+" + smsAddress.address;
                return;
            }
            smsAddress.address = new String(smsAddress.origBytes);
            return;
        }
        String orElse = TelephonyProperties.operator_idp_string().orElse(null);
        smsAddress.address = new String(smsAddress.origBytes);
        if (!TextUtils.isEmpty(orElse) && smsAddress.address.startsWith(orElse)) {
            smsAddress.address = "+" + smsAddress.address.substring(orElse.length());
        } else if (smsAddress.ton == 1 && !TextUtils.isEmpty(smsAddress.address) && smsAddress.address.charAt(0) != '+') {
            smsAddress.address = "+" + smsAddress.address;
        }
        Rlog.pii(LOG_TAG, " decodeSmsDisplayAddress = " + smsAddress.address);
    }

    public SmsCbMessage parseBroadcastSms(String str, int i, int i2) {
        BearerData decode = BearerData.decode(this.mEnvelope.bearerData, this.mEnvelope.serviceCategory);
        if (decode == null) {
            Rlog.w(LOG_TAG, "BearerData.decode() returned null");
            return null;
        }
        if (decode.userData != null) {
            this.mReceivedEncodingType = decode.userData.msgEncoding;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MT raw BearerData = " + HexDump.toHexString(this.mEnvelope.bearerData));
        }
        return new SmsCbMessage(2, 1, decode.messageId, new SmsCbLocation(str), this.mEnvelope.serviceCategory, decode.getLanguage(), decode.userData.payloadStr, decode.priority, null, decode.cmasWarningInfo, i, i2);
    }

    public byte[] getEnvelopeBearerData() {
        return this.mEnvelope.bearerData;
    }

    public int getEnvelopeServiceCategory() {
        return this.mEnvelope.serviceCategory;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public SmsConstants.MessageClass getMessageClass() {
        if (this.mBearerData.displayMode == 0) {
            return SmsConstants.MessageClass.CLASS_0;
        }
        return SmsConstants.MessageClass.UNKNOWN;
    }

    public static synchronized int getNextMessageId() {
        int intValue;
        synchronized (SmsMessage.class) {
            intValue = TelephonyProperties.cdma_msg_id().orElse(1).intValue();
            int i = (intValue % 65535) + 1;
            try {
                TelephonyProperties.cdma_msg_id(Integer.valueOf(i));
                if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
                    Rlog.d(LOG_TAG, "next persist.radio.cdma.msgid = " + i);
                    Rlog.d(LOG_TAG, "readback gets " + TelephonyProperties.cdma_msg_id().orElse(1));
                }
            } catch (RuntimeException e) {
                Rlog.e(LOG_TAG, "set nextMessage ID failed: " + e);
            }
        }
        return intValue;
    }

    private static SubmitPdu privateGetSubmitPdu(String str, boolean z, UserData userData) {
        return privateGetSubmitPdu(str, z, userData, -1);
    }

    private static SubmitPdu privateGetSubmitPdu(String str, boolean z, UserData userData, int i) {
        if (str == null || str.length() == 0) {
            Log.e(LOG_TAG, "privateGetSubmitPdu - destAddrStr is invalid");
            return null;
        }
        CdmaSmsAddress parse = CdmaSmsAddress.parse(PhoneNumberUtils.cdmaCheckAndProcessPlusCodeForSms(str));
        if (parse == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = z;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        if (i >= 0 && i <= 3) {
            bearerData.priorityIndicatorSet = true;
            bearerData.priority = i;
        }
        bearerData.userData = userData;
        byte[] encode = BearerData.encode(bearerData);
        if (encode == null) {
            return null;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            Rlog.d(LOG_TAG, "MO raw BearerData = '" + HexDump.toHexString(encode) + "'");
        }
        int i2 = (!bearerData.hasUserDataHeader || userData.msgEncoding == 2) ? 4098 : 4101;
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        smsEnvelope.messageType = 0;
        smsEnvelope.teleService = i2;
        smsEnvelope.destAddress = parse;
        smsEnvelope.bearerReply = 1;
        smsEnvelope.bearerData = encode;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            SubmitPdu submitPdu = new SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (IOException e) {
            Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + e);
            return null;
        }
    }

    public static SubmitPdu getDeliverPdu(String str, String str2, long j) {
        CdmaSmsAddress parse;
        if (str == null || str2 == null || (parse = CdmaSmsAddress.parse(str)) == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 1;
        bearerData.messageId = 0;
        bearerData.deliveryAckReq = false;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        bearerData.userData = new UserData();
        bearerData.userData.payloadStr = str2;
        bearerData.msgCenterTimeStamp = BearerData.TimeStamp.fromMillis(j);
        byte[] encode = BearerData.encode(bearerData);
        if (encode == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(4098);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            SubmitPdu submitPdu = new SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (IOException e) {
            Rlog.e(LOG_TAG, "creating Deliver PDU failed: " + e);
            return null;
        }
    }

    public void createPdu() {
        SmsEnvelope smsEnvelope = this.mEnvelope;
        CdmaSmsAddress cdmaSmsAddress = smsEnvelope.origAddress;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
        try {
            dataOutputStream.writeInt(smsEnvelope.messageType);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(smsEnvelope.serviceCategory);
            dataOutputStream.writeByte(cdmaSmsAddress.digitMode);
            dataOutputStream.writeByte(cdmaSmsAddress.numberMode);
            dataOutputStream.writeByte(cdmaSmsAddress.ton);
            dataOutputStream.writeByte(cdmaSmsAddress.numberPlan);
            dataOutputStream.writeByte(cdmaSmsAddress.numberOfDigits);
            dataOutputStream.write(cdmaSmsAddress.origBytes, 0, cdmaSmsAddress.origBytes.length);
            dataOutputStream.writeInt(smsEnvelope.bearerReply);
            dataOutputStream.writeByte(smsEnvelope.replySeqNo);
            dataOutputStream.writeByte(smsEnvelope.errorClass);
            dataOutputStream.writeByte(smsEnvelope.causeCode);
            dataOutputStream.writeInt(smsEnvelope.bearerData.length);
            dataOutputStream.write(smsEnvelope.bearerData, 0, smsEnvelope.bearerData.length);
            dataOutputStream.close();
            this.mPdu = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            Rlog.e(LOG_TAG, "createPdu: conversion from object to byte array failed: " + e);
        }
    }

    public int getNumOfVoicemails() {
        return this.mBearerData.numberOfMessages;
    }

    public byte[] getIncomingSmsFingerprint() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.mEnvelope.serviceCategory);
        byteArrayOutputStream.write(this.mEnvelope.teleService);
        byteArrayOutputStream.write(this.mEnvelope.origAddress.origBytes, 0, this.mEnvelope.origAddress.origBytes.length);
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK) && this.mEnvelope.teleService == 4098) {
            if (this.mBearerData.userData != null) {
                byteArrayOutputStream.write(this.mBearerData.userData.payload, 0, this.mBearerData.userData.payload.length);
            }
            if (this.mBearerData.msgCenterTimeStamp != null) {
                byteArrayOutputStream.write(this.mBearerData.msgCenterTimeStamp.toString().getBytes(), 0, this.mBearerData.msgCenterTimeStamp.toString().length());
            }
            if (this.mBearerData.callbackNumber != null) {
                byteArrayOutputStream.write(this.mBearerData.callbackNumber.toString().getBytes(), 0, this.mBearerData.callbackNumber.toString().length());
            }
        } else {
            byteArrayOutputStream.write(this.mEnvelope.bearerData, 0, this.mEnvelope.bearerData.length);
            if (this.mEnvelope.origSubaddress != null && this.mEnvelope.origSubaddress.origBytes != null) {
                byteArrayOutputStream.write(this.mEnvelope.origSubaddress.origBytes, 0, this.mEnvelope.origSubaddress.origBytes.length);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public ArrayList<CdmaSmsCbProgramData> getSmsCbProgramData() {
        return this.mBearerData.serviceCategoryProgramData;
    }

    public static SmsMessage semCreateFromPdu(int i, byte[] bArr) {
        SmsMessage smsMessage = new SmsMessage();
        try {
            smsMessage.setSubId(getSubId(i));
            smsMessage.parsePdu(bArr);
            return smsMessage;
        } catch (OutOfMemoryError e) {
            Log.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (RuntimeException e2) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e2);
            return null;
        }
    }

    public int getServiceCategory() {
        return this.mEnvelope.serviceCategory;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageIdentifier() {
        BearerData bearerData = this.mBearerData;
        if (bearerData != null) {
            return bearerData.messageId;
        }
        return 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessagePriority() {
        if (this.mBearerData.priorityIndicatorSet) {
            return this.mBearerData.priority;
        }
        return 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public byte[] getBearerData() {
        return this.mEnvelope.bearerData;
    }

    public static SmsMessage newFromParcel(Parcel parcel) {
        SmsMessage smsMessage = new SmsMessage();
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
        smsEnvelope.teleService = parcel.readInt();
        if (parcel.readByte() != 0) {
            smsEnvelope.messageType = 1;
        } else if (smsEnvelope.teleService == 0) {
            smsEnvelope.messageType = 2;
        } else {
            smsEnvelope.messageType = 0;
        }
        smsEnvelope.serviceCategory = parcel.readInt();
        int readInt = parcel.readInt();
        cdmaSmsAddress.digitMode = (byte) (readInt & 255);
        cdmaSmsAddress.numberMode = (byte) (parcel.readInt() & 255);
        cdmaSmsAddress.ton = parcel.readInt();
        cdmaSmsAddress.numberPlan = (byte) (parcel.readInt() & 255);
        int readByte = parcel.readByte();
        cdmaSmsAddress.numberOfDigits = readByte;
        byte[] bArr = new byte[readByte];
        for (int i = 0; i < readByte; i++) {
            byte readByte2 = parcel.readByte();
            bArr[i] = readByte2;
            if (readInt == 0) {
                bArr[i] = convertDtmfToAscii(readByte2);
            }
        }
        cdmaSmsAddress.origBytes = bArr;
        cdmaSmsSubaddress.type = parcel.readInt();
        cdmaSmsSubaddress.odd = parcel.readByte();
        int readByte3 = parcel.readByte();
        if (readByte3 < 0) {
            readByte3 = 0;
        }
        byte[] bArr2 = new byte[readByte3];
        for (int i2 = 0; i2 < readByte3; i2++) {
            bArr2[i2] = parcel.readByte();
        }
        cdmaSmsSubaddress.origBytes = bArr2;
        int readInt2 = parcel.readInt();
        if (readInt2 < 0) {
            readInt2 = 0;
        }
        byte[] bArr3 = new byte[readInt2];
        for (int i3 = 0; i3 < readInt2; i3++) {
            bArr3[i3] = parcel.readByte();
        }
        smsEnvelope.bearerData = bArr3;
        smsEnvelope.origAddress = cdmaSmsAddress;
        smsEnvelope.origSubaddress = cdmaSmsSubaddress;
        smsMessage.mOriginatingAddress = cdmaSmsAddress;
        smsMessage.mEnvelope = smsEnvelope;
        smsMessage.createPdu();
        return smsMessage;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getCDMAMessageType() {
        return this.mEnvelope.serviceCategory != 0 ? 1 : 0;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthForEms(CharSequence charSequence, boolean z, boolean z2) {
        return BearerData.calcTextEncodingDetails(charSequence, z, true, z2);
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, SmsHeader smsHeader, String str4, int i2) {
        if (str3 == null || str2 == null) {
            return null;
        }
        UserData userData = new UserData();
        userData.payloadStr = str3;
        userData.userDataHeader = smsHeader;
        return privateGetSubmitPdu(i, str2, z, userData, str4, i2);
    }

    public static SubmitPdu getSubmitPdu(int i, String str, UserData userData, boolean z, String str2, int i2) {
        return privateGetSubmitPdu(i, str, z, userData, str2, i2);
    }

    public static SubmitPdu getSubmitPduForAutoLogin(String str, String str2, String str3, boolean z, SmsHeader smsHeader, int i) {
        if (str3 == null || str2 == null) {
            return null;
        }
        UserData userData = new UserData();
        userData.payloadStr = str3;
        userData.userDataHeader = smsHeader;
        userData.isAutoLogin = true;
        return privateGetSubmitPdu(str2, z, userData, i);
    }

    public static SubmitPdu getSubmitPduForCCTUnlock(String str) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = SmsHeader.PORT_CCT_UNLOCK;
        portAddrs.origPort = SmsHeader.PORT_CCT_UNLOCK;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        UserData userData = new UserData();
        userData.userDataHeader = smsHeader;
        userData.msgEncoding = 0;
        userData.msgEncodingSet = true;
        userData.payloadStr = str;
        return privateGetSubmitPdu("20868", false, userData);
    }

    public void parseCtcFota() {
        this.mIsCtcFota = false;
        int i = 0;
        while (true) {
            byte[] bArr = this.mUserDataCtcFota;
            if (i >= bArr.length) {
                return;
            }
            if (bArr[i] == 1 && bArr[i + 1] == 6) {
                int length = bArr.length - i;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, i, bArr2, 0, length);
                this.mUserData = bArr2;
                this.mIsCtcFota = true;
                return;
            }
            i++;
        }
    }

    public boolean isCtcFota() {
        return this.mIsCtcFota;
    }

    private static SubmitPdu privateGetSubmitPdu(int i, String str, boolean z, UserData userData, String str2, int i2) {
        CdmaSmsAddress parse = CdmaSmsAddress.parse(PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(str, 1, 1));
        if (parse == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = z;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        if (str2 != null && str2.length() > 0) {
            Rlog.d(LOG_TAG, "callback number is set: " + str2);
            CdmaSmsAddress parse2 = CdmaSmsAddress.parse(str2);
            if (parse2 != null) {
                bearerData.callbackNumber = parse2;
            }
        }
        if (i2 == 2) {
            Rlog.d(LOG_TAG, "priority is set to high");
            bearerData.priorityIndicatorSet = true;
            bearerData.priority = i2;
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
            bearerData.languageIndicatorSet = true;
            bearerData.language = 64;
        }
        bearerData.userData = userData;
        byte[] encode = BearerData.encode(bearerData);
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            if (encode != null) {
                Rlog.d(LOG_TAG, "MO raw BearerData = '" + HexDump.toHexString(encode) + "'");
            }
        }
        if (encode == null) {
            return null;
        }
        int i3 = bearerData.hasUserDataHeader ? 4101 : 4098;
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        smsEnvelope.messageType = 0;
        smsEnvelope.teleService = i3;
        smsEnvelope.destAddress = parse;
        smsEnvelope.bearerReply = 1;
        smsEnvelope.bearerData = encode;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            SubmitPdu submitPdu = new SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (IOException e) {
            Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + e);
            return null;
        }
    }

    public static SubmitPdu getDomainChangeNotification(byte b, String str) {
        CdmaSmsAddress parse = CdmaSmsAddress.parse(PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(str, 1, 1));
        if (parse == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = true;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        bearerData.priorityIndicatorSet = true;
        bearerData.priority = 2;
        UserData userData = new UserData();
        userData.msgEncoding = 0;
        userData.msgEncodingSet = true;
        userData.payload = new byte[8];
        userData.payload[0] = 0;
        userData.payload[1] = (byte) (bearerData.messageId % 256);
        userData.payload[2] = 8;
        userData.payload[3] = b;
        long currentTimeMillis = System.currentTimeMillis();
        Calendar.getInstance().setTimeInMillis(currentTimeMillis);
        long j = ((r10.get(1) - 1900) * 31556926) + (r10.get(2) * 2629743) + (r10.get(5) * 86400) + (r10.get(10) * 3600) + (r10.get(12) * 60) + r10.get(13);
        userData.payload[7] = (byte) (j & 255);
        userData.payload[6] = (byte) ((j >> 8) & 255);
        userData.payload[5] = (byte) ((j >> 16) & 255);
        userData.payload[4] = (byte) ((j >> 24) & 255);
        bearerData.userData = userData;
        byte[] encode = BearerData.encode(bearerData);
        if (Log.isLoggable(LOGGABLE_TAG, 2)) {
            Log.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            if (encode != null) {
                Log.d(LOG_TAG, "MO raw BearerData = '" + HexDump.toHexString(encode) + "'");
            }
        }
        if (encode == null) {
            return null;
        }
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        smsEnvelope.messageType = 0;
        smsEnvelope.teleService = 4242;
        smsEnvelope.destAddress = parse;
        smsEnvelope.bearerReply = 1;
        smsEnvelope.bearerData = encode;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.write(parse.digitMode);
            dataOutputStream.write(parse.numberMode);
            dataOutputStream.write(parse.ton);
            dataOutputStream.write(parse.numberPlan);
            dataOutputStream.write(parse.numberOfDigits);
            dataOutputStream.write(parse.origBytes, 0, parse.origBytes.length);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(0);
            dataOutputStream.write(encode.length);
            dataOutputStream.write(encode, 0, encode.length);
            dataOutputStream.close();
            SubmitPdu submitPdu = new SubmitPdu();
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            submitPdu.encodedScAddress = null;
            return submitPdu;
        } catch (IOException e) {
            Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + e);
            return null;
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence charSequence, boolean z, int i) {
        return BearerData.calcTextEncodingDetailsWithEmail(charSequence, z, i);
    }

    public int getMessageEncoding() {
        return this.mReceivedEncodingType;
    }
}
