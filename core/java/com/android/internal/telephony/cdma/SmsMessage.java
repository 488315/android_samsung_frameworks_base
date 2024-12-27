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

/* loaded from: classes5.dex */
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

    public static class SubmitPdu extends SmsMessageBase.SubmitPduBase {}

    public SmsMessage(SmsAddress addr, SmsEnvelope env) {
        this.mOriginatingAddress = addr;
        this.mEnvelope = env;
        createPdu();
    }

    public SmsMessage() {}

    public static SmsMessage createFromPdu(byte[] pdu) {
        return semCreateFromPdu(SmsManager.getDefaultSmsSubscriptionId(), pdu);
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.mIndexOnIcc = index;
            if ((data[0] & 1) == 0) {
                Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            msg.mStatusOnIcc = data[0] & 7;
            if (msg.mStatusOnIcc != 1 && msg.mStatusOnIcc != 3) {
                msg.mMti = 1;
                int size = data[1] & 255;
                Rlog.d(
                        LOG_TAG,
                        "msg[" + index + "]statusOnIcc: " + msg.mStatusOnIcc + " size:" + size);
                byte[] pdu = new byte[size];
                System.arraycopy(data, 2, pdu, 0, size);
                msg.parsePduFromEfRecord(pdu);
                return msg;
            }
            msg.mMti = 0;
            int size2 = data[1] & 255;
            Rlog.d(
                    LOG_TAG,
                    "msg[" + index + "]statusOnIcc: " + msg.mStatusOnIcc + " size:" + size2);
            byte[] pdu2 = new byte[size2];
            System.arraycopy(data, 2, pdu2, 0, size2);
            msg.parsePduFromEfRecord(pdu2);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(String pdu) {
        Rlog.w(LOG_TAG, "getTPLayerLengthForPDU: is not supported in CDMA mode.");
        return 0;
    }

    public static SubmitPdu getSubmitPdu(
            String scAddr,
            String destAddr,
            String message,
            boolean statusReportRequested,
            SmsHeader smsHeader) {
        return getSubmitPdu(scAddr, destAddr, message, statusReportRequested, smsHeader, -1);
    }

    public static SubmitPdu getSubmitPdu(
            String scAddr,
            String destAddr,
            String message,
            boolean statusReportRequested,
            SmsHeader smsHeader,
            int priority) {
        if (message == null || destAddr == null) {
            return null;
        }
        UserData uData = new UserData();
        uData.payloadStr = message;
        uData.userDataHeader = smsHeader;
        return privateGetSubmitPdu(destAddr, statusReportRequested, uData, priority);
    }

    public static SubmitPdu getSubmitPdu(
            String scAddr,
            String destAddr,
            int destPort,
            byte[] data,
            boolean statusReportRequested) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = destPort;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        UserData uData = new UserData();
        int phoneId = SubscriptionManager.getPhoneId(SmsManager.getDefault().getSubscriptionId());
        if (TelephonyFeatures.isCountrySpecific(phoneId, "CHN")) {
            uData.msgEncoding = 4;
            uData.msgEncodingSet = false;
            uData.payloadStr = new String(data);
        } else {
            uData.userDataHeader = smsHeader;
            uData.msgEncoding = 0;
            uData.msgEncodingSet = true;
            uData.payload = data;
        }
        return privateGetSubmitPdu(destAddr, statusReportRequested, uData);
    }

    public static SubmitPdu getSubmitPdu(
            String destAddr, UserData userData, boolean statusReportRequested) {
        return privateGetSubmitPdu(destAddr, statusReportRequested, userData);
    }

    public static SubmitPdu getSubmitPdu(
            String destAddr, UserData userData, boolean statusReportRequested, int priority) {
        return privateGetSubmitPdu(destAddr, statusReportRequested, userData, priority);
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
        return this.mBearerData != null && this.mBearerData.numberOfMessages == 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWISetMessage() {
        return this.mBearerData != null && this.mBearerData.numberOfMessages > 0;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMwiDontStore() {
        return this.mBearerData != null
                && this.mBearerData.numberOfMessages > 0
                && this.mBearerData.userData == null;
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

    public static GsmAlphabet.TextEncodingDetails calculateLength(
            CharSequence messageBody, boolean use7bitOnly, boolean isEntireMsg) {
        return BearerData.calcTextEncodingDetails(messageBody, use7bitOnly, isEntireMsg);
    }

    public int getTeleService() {
        return this.mEnvelope.teleService;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageType() {
        Rlog.d(LOG_TAG, "getMessageType = " + this.mMti);
        return this.mMti;
    }

    private void parsePdu(byte[] pdu) {
        int length;
        ByteArrayInputStream bais = new ByteArrayInputStream(pdu);
        DataInputStream dis = new DataInputStream(bais);
        SmsEnvelope env = new SmsEnvelope();
        CdmaSmsAddress addr = new CdmaSmsAddress();
        CdmaSmsSubaddress subaddr = new CdmaSmsSubaddress();
        try {
            env.messageType = dis.readInt();
            env.teleService = dis.readInt();
            env.serviceCategory = dis.readInt();
            addr.digitMode = dis.readByte();
            addr.numberMode = dis.readByte();
            addr.ton = dis.readByte();
            addr.numberPlan = dis.readByte();
            length = dis.readUnsignedByte();
            addr.numberOfDigits = length;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "createFromPdu: conversion from byte array to object failed: " + ex, ex);
        } catch (Exception ex2) {
            Rlog.e(LOG_TAG, "createFromPdu: conversion from byte array to object failed: " + ex2);
        }
        if (length > pdu.length) {
            throw new RuntimeException(
                    "createFromPdu: Invalid pdu, addr.numberOfDigits "
                            + length
                            + " > pdu len "
                            + pdu.length);
        }
        addr.origBytes = new byte[length];
        dis.read(addr.origBytes, 0, length);
        env.bearerReply = dis.readInt();
        env.replySeqNo = dis.readByte();
        env.errorClass = dis.readByte();
        env.causeCode = dis.readByte();
        int bearerDataLength = dis.readInt();
        if (bearerDataLength > pdu.length) {
            throw new RuntimeException(
                    "createFromPdu: Invalid pdu, bearerDataLength "
                            + bearerDataLength
                            + " > pdu len "
                            + pdu.length);
        }
        env.bearerData = new byte[bearerDataLength];
        dis.read(env.bearerData, 0, bearerDataLength);
        dis.close();
        this.mOriginatingAddress = addr;
        env.origAddress = addr;
        env.origSubaddress = subaddr;
        this.mEnvelope = env;
        this.mPdu = pdu;
        parseSms();
        if (SmsManager.getDefault().getMnoName().toUpperCase().contains("KDDI")
                && this.mBearerData.callbackNumber != null) {
            this.mOriginatingAddress = this.mBearerData.callbackNumber;
            env.origAddress = this.mBearerData.callbackNumber;
        }
    }

    private void parsePduFromEfRecord(byte[] pdu) {
        CdmaSmsSubaddress subAddr;
        ByteArrayInputStream bais = new ByteArrayInputStream(pdu);
        DataInputStream dis = new DataInputStream(bais);
        SmsEnvelope env = new SmsEnvelope();
        CdmaSmsAddress addr = new CdmaSmsAddress();
        CdmaSmsSubaddress subAddr2 = new CdmaSmsSubaddress();
        try {
            env.messageType = dis.readByte();
        } catch (Exception e) {
            ex = e;
        }
        while (dis.available() > 0) {
            int parameterId = dis.readByte();
            int parameterLen = dis.readUnsignedByte();
            byte[] parameterData = new byte[parameterLen];
            switch (parameterId) {
                case 0:
                    subAddr = subAddr2;
                    env.teleService = dis.readUnsignedShort();
                    Rlog.i(LOG_TAG, "teleservice = " + env.teleService);
                    continue;
                    subAddr2 = subAddr;
                case 1:
                    subAddr = subAddr2;
                    env.serviceCategory = dis.readUnsignedShort();
                    continue;
                    subAddr2 = subAddr;
                case 2:
                case 4:
                    dis.read(parameterData, 0, parameterLen);
                    BitwiseInputStream addrBis = new BitwiseInputStream(parameterData);
                    addr.digitMode = addrBis.read(1);
                    addr.numberMode = addrBis.read(1);
                    int numberType = 0;
                    if (addr.digitMode == 1) {
                        numberType = addrBis.read(3);
                        addr.ton = numberType;
                        if (addr.numberMode == 0) {
                            addr.numberPlan = addrBis.read(4);
                        }
                    }
                    addr.numberOfDigits = addrBis.read(8);
                    byte[] data = new byte[addr.numberOfDigits];
                    if (addr.digitMode == 0) {
                        int index = 0;
                        while (true) {
                            subAddr = subAddr2;
                            try {
                                if (index < addr.numberOfDigits) {
                                    byte b = (byte) (addrBis.read(4) & 15);
                                    data[index] = convertDtmfToAscii(b);
                                    index++;
                                    subAddr2 = subAddr;
                                }
                            } catch (Exception e2) {
                                ex = e2;
                                break;
                            }
                        }
                    } else {
                        subAddr = subAddr2;
                        if (addr.digitMode != 1) {
                            Rlog.e(LOG_TAG, "Incorrect Digit mode");
                        } else if (addr.numberMode == 0) {
                            int index2 = 0;
                            while (index2 < addr.numberOfDigits) {
                                byte b2 = (byte) (addrBis.read(8) & 255);
                                data[index2] = b2;
                                index2++;
                                parameterLen = parameterLen;
                            }
                        } else if (addr.numberMode == 1) {
                            if (numberType == 2) {
                                Rlog.e(LOG_TAG, "TODO: Addr is email id");
                            } else {
                                Rlog.e(LOG_TAG, "TODO: Addr is data network address");
                            }
                        } else {
                            Rlog.e(LOG_TAG, "Addr is of incorrect type");
                        }
                    }
                    addr.origBytes = data;
                    Rlog.pii(LOG_TAG, "Addr=" + addr.toString());
                    if (parameterId == 2) {
                        env.origAddress = addr;
                        this.mOriginatingAddress = addr;
                        continue;
                    } else {
                        env.destAddress = addr;
                        this.mRecipientAddress = addr;
                    }
                    subAddr2 = subAddr;
                case 3:
                case 5:
                    dis.read(parameterData, 0, parameterLen);
                    BitwiseInputStream subAddrBis = new BitwiseInputStream(parameterData);
                    subAddr2.type = subAddrBis.read(3);
                    subAddr2.odd = subAddrBis.readByteArray(1)[0];
                    int subAddrLen = subAddrBis.read(8);
                    byte[] subdata = new byte[subAddrLen];
                    int index3 = 0;
                    while (index3 < subAddrLen) {
                        int subAddrLen2 = subAddrLen;
                        byte b3 = (byte) (subAddrBis.read(4) & 255);
                        subdata[index3] = convertDtmfToAscii(b3);
                        index3++;
                        subAddrLen = subAddrLen2;
                    }
                    subAddr2.origBytes = subdata;
                    if (parameterId == 3) {
                        env.origSubaddress = subAddr2;
                        subAddr = subAddr2;
                        continue;
                    } else {
                        env.destSubaddress = subAddr2;
                        subAddr = subAddr2;
                    }
                    subAddr2 = subAddr;
                case 6:
                    dis.read(parameterData, 0, parameterLen);
                    BitwiseInputStream replyOptBis = new BitwiseInputStream(parameterData);
                    env.bearerReply = replyOptBis.read(6);
                    subAddr = subAddr2;
                    continue;
                    subAddr2 = subAddr;
                case 7:
                    dis.read(parameterData, 0, parameterLen);
                    BitwiseInputStream ccBis = new BitwiseInputStream(parameterData);
                    env.replySeqNo = ccBis.readByteArray(6)[0];
                    env.errorClass = ccBis.readByteArray(2)[0];
                    if (env.errorClass == 0) {
                        subAddr = subAddr2;
                    } else {
                        env.causeCode = ccBis.readByteArray(8)[0];
                        subAddr = subAddr2;
                        continue;
                    }
                    subAddr2 = subAddr;
                case 8:
                    try {
                        dis.read(parameterData, 0, parameterLen);
                        env.bearerData = parameterData;
                        subAddr = subAddr2;
                        continue;
                        subAddr2 = subAddr;
                    } catch (Exception e3) {
                        ex = e3;
                        break;
                    }
                default:
                    throw new Exception(
                            "unsupported parameterId ("
                                    + parameterId
                                    + NavigationBarInflaterView.KEY_CODE_END);
            }
            Rlog.e(LOG_TAG, "parsePduFromEfRecord: conversion from pdu to SmsMessage failed" + ex);
            this.mEnvelope = env;
            this.mPdu = pdu;
            parseSms();
        }
        bais.close();
        dis.close();
        this.mEnvelope = env;
        this.mPdu = pdu;
        parseSms();
    }

    public boolean preprocessCdmaFdeaWap() {
        try {
            BitwiseInputStream inStream = new BitwiseInputStream(this.mUserData);
            if (inStream.read(8) != 0) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAMETER_ID");
                return false;
            }
            if (inStream.read(8) != 3) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier SUBPARAM_LEN");
                return false;
            }
            this.mBearerData.messageType = inStream.read(4);
            int msgId = (inStream.read(8) << 8) | inStream.read(8);
            this.mBearerData.messageId = msgId;
            this.mMessageRef = msgId;
            this.mBearerData.hasUserDataHeader = inStream.read(1) == 1;
            if (this.mBearerData.hasUserDataHeader) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header Message Identifier HEADER_IND");
                return false;
            }
            inStream.skip(3);
            if (inStream.read(8) != 1) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data SUBPARAMETER_ID");
                return false;
            }
            int userDataLen = inStream.read(8) * 8;
            this.mBearerData.userData.msgEncoding = inStream.read(5);
            if (this.mBearerData.userData.msgEncoding != 0) {
                Rlog.e(LOG_TAG, "Invalid FDEA WDP Header User Data MSG_ENCODING");
                return false;
            }
            this.mBearerData.userData.numFields = inStream.read(8);
            int consumedBits = 5 + 8;
            int remainingBits = userDataLen - consumedBits;
            int dataBits = this.mBearerData.userData.numFields * 8;
            this.mBearerData.userData.payload =
                    inStream.readByteArray(dataBits < remainingBits ? dataBits : remainingBits);
            this.mUserData = this.mBearerData.userData.payload;
            return true;
        } catch (BitwiseInputStream.AccessException ex) {
            Rlog.e(LOG_TAG, "Fail to preprocess FDEA WAP: " + ex);
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
            Rlog.d(
                    LOG_TAG,
                    "MT raw BearerData = '" + HexDump.toHexString(this.mEnvelope.bearerData) + "'");
            Rlog.d(LOG_TAG, "MT (decoded) BearerData = " + this.mBearerData);
        }
        this.mMessageRef = this.mBearerData.messageId;
        if (this.mBearerData.userData != null) {
            this.mUserData = this.mBearerData.userData.payload;
            this.mUserDataHeader = this.mBearerData.userData.userDataHeader;
            this.mMessageBody = this.mBearerData.userData.payloadStr;
            if (this.mBearerData.userData.msgEncodingSet) {
                this.mReceivedEncodingType = this.mBearerData.userData.msgEncoding;
            }
            if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_WAP_PUSH_FORMAT_SMS)
                    && this.mEnvelope.teleService == 4098
                    && this.mBearerData.userData.msgEncoding == 0) {
                this.mUserDataCtcFota = new byte[this.mUserData.length];
                System.arraycopy(
                        this.mUserData, 0, this.mUserDataCtcFota, 0, this.mUserData.length);
            }
        }
        if (this.mBearerData.callbackNumber != null) {
            Rlog.d(LOG_TAG, "parseSms() callback = " + this.mBearerData.callbackNumber);
            CdmaSmsAddress cback = this.mBearerData.callbackNumber;
            this.callbackNumber = cback.address;
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
                Rlog.e(
                        LOG_TAG,
                        "SMS CALL BACK NUMBER: getDisplayOriginatingAddress(): "
                                + getDisplayOriginatingAddress());
            } else {
                this.replyAddress = null;
                Rlog.e(
                        LOG_TAG,
                        "SMS CALL BACK NUMBER: null  getDisplayOriginatingAddress(): "
                                + getDisplayOriginatingAddress());
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
                Rlog.d(
                        LOG_TAG,
                        "DELIVERY_ACK message without msgStatus ("
                                + (this.mUserData == null ? "also missing" : "does have")
                                + " userData).");
                this.status = 2;
            } else {
                this.status = this.mBearerData.errorClass << 8;
                this.status |= this.mBearerData.messageStatus;
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

    private void decodeSmsDisplayAddress(SmsAddress addr) {
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_SPECIAL_ADDRESS_HANDLING_FOR)) {
            if (addr.ton == 0) {
                String origAddress = new String(addr.origBytes);
                if (!TextUtils.isEmpty(origAddress) && origAddress.startsWith("00852")) {
                    Rlog.d(LOG_TAG, "receive sms from HK number Before Address= " + origAddress);
                    String origAddress2 = origAddress.substring(2);
                    addr.address = "+";
                    addr.address += origAddress2;
                    Rlog.d(LOG_TAG, "After Address Replacement = " + addr.address);
                    return;
                }
                addr.address = origAddress;
                return;
            }
            if (addr.ton == 1) {
                addr.address = new String(addr.origBytes);
                if (!TextUtils.isEmpty(addr.address) && addr.address.charAt(0) != '+') {
                    addr.address = "+" + addr.address;
                    return;
                }
                return;
            }
            addr.address = new String(addr.origBytes);
            return;
        }
        String idd = TelephonyProperties.operator_idp_string().orElse(null);
        addr.address = new String(addr.origBytes);
        if (!TextUtils.isEmpty(idd) && addr.address.startsWith(idd)) {
            addr.address = "+" + addr.address.substring(idd.length());
        } else if (addr.ton == 1
                && !TextUtils.isEmpty(addr.address)
                && addr.address.charAt(0) != '+') {
            addr.address = "+" + addr.address;
        }
        Rlog.pii(LOG_TAG, " decodeSmsDisplayAddress = " + addr.address);
    }

    public SmsCbMessage parseBroadcastSms(String plmn, int slotIndex, int subId) {
        BearerData bData =
                BearerData.decode(this.mEnvelope.bearerData, this.mEnvelope.serviceCategory);
        if (bData == null) {
            Rlog.w(LOG_TAG, "BearerData.decode() returned null");
            return null;
        }
        if (bData.userData != null) {
            this.mReceivedEncodingType = bData.userData.msgEncoding;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(
                    LOG_TAG,
                    "MT raw BearerData = " + HexDump.toHexString(this.mEnvelope.bearerData));
        }
        SmsCbLocation location = new SmsCbLocation(plmn);
        return new SmsCbMessage(
                2,
                1,
                bData.messageId,
                location,
                this.mEnvelope.serviceCategory,
                bData.getLanguage(),
                bData.userData.payloadStr,
                bData.priority,
                null,
                bData.cmasWarningInfo,
                slotIndex,
                subId);
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
        int msgId;
        synchronized (SmsMessage.class) {
            msgId = TelephonyProperties.cdma_msg_id().orElse(1).intValue();
            int nextMsgId = (msgId % 65535) + 1;
            try {
                TelephonyProperties.cdma_msg_id(Integer.valueOf(nextMsgId));
                if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
                    Rlog.d(LOG_TAG, "next persist.radio.cdma.msgid = " + nextMsgId);
                    Rlog.d(LOG_TAG, "readback gets " + TelephonyProperties.cdma_msg_id().orElse(1));
                }
            } catch (RuntimeException ex) {
                Rlog.e(LOG_TAG, "set nextMessage ID failed: " + ex);
            }
        }
        return msgId;
    }

    private static SubmitPdu privateGetSubmitPdu(
            String destAddrStr, boolean statusReportRequested, UserData userData) {
        return privateGetSubmitPdu(destAddrStr, statusReportRequested, userData, -1);
    }

    private static SubmitPdu privateGetSubmitPdu(
            String destAddrStr, boolean statusReportRequested, UserData userData, int priority) {
        if (destAddrStr == null || destAddrStr.length() == 0) {
            Log.e(LOG_TAG, "privateGetSubmitPdu - destAddrStr is invalid");
            return null;
        }
        CdmaSmsAddress destAddr =
                CdmaSmsAddress.parse(
                        PhoneNumberUtils.cdmaCheckAndProcessPlusCodeForSms(destAddrStr));
        if (destAddr == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = statusReportRequested;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        if (priority >= 0 && priority <= 3) {
            bearerData.priorityIndicatorSet = true;
            bearerData.priority = priority;
        }
        bearerData.userData = userData;
        byte[] encodedBearerData = BearerData.encode(bearerData);
        if (encodedBearerData == null) {
            return null;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            Rlog.d(LOG_TAG, "MO raw BearerData = '" + HexDump.toHexString(encodedBearerData) + "'");
        }
        int teleservice =
                (!bearerData.hasUserDataHeader || userData.msgEncoding == 2) ? 4098 : 4101;
        SmsEnvelope envelope = new SmsEnvelope();
        envelope.messageType = 0;
        envelope.teleService = teleservice;
        envelope.destAddress = destAddr;
        envelope.bearerReply = 1;
        envelope.bearerData = encodedBearerData;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(envelope.teleService);
            dos.writeInt(0);
            dos.writeInt(0);
            dos.write(destAddr.digitMode);
            dos.write(destAddr.numberMode);
            dos.write(destAddr.ton);
            dos.write(destAddr.numberPlan);
            dos.write(destAddr.numberOfDigits);
            dos.write(destAddr.origBytes, 0, destAddr.origBytes.length);
            dos.write(0);
            dos.write(0);
            dos.write(0);
            dos.write(encodedBearerData.length);
            dos.write(encodedBearerData, 0, encodedBearerData.length);
            dos.close();
            SubmitPdu pdu = new SubmitPdu();
            pdu.encodedMessage = baos.toByteArray();
            pdu.encodedScAddress = null;
            return pdu;
        } catch (IOException ex) {
            Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + ex);
            return null;
        }
    }

    public static SubmitPdu getDeliverPdu(String origAddr, String message, long date) {
        CdmaSmsAddress addr;
        if (origAddr == null
                || message == null
                || (addr = CdmaSmsAddress.parse(origAddr)) == null) {
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
        bearerData.userData.payloadStr = message;
        bearerData.msgCenterTimeStamp = BearerData.TimeStamp.fromMillis(date);
        byte[] encodedBearerData = BearerData.encode(bearerData);
        if (encodedBearerData == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(4098);
            dos.writeInt(0);
            dos.writeInt(0);
            dos.write(addr.digitMode);
            dos.write(addr.numberMode);
            dos.write(addr.ton);
            dos.write(addr.numberPlan);
            dos.write(addr.numberOfDigits);
            dos.write(addr.origBytes, 0, addr.origBytes.length);
            dos.write(0);
            dos.write(0);
            dos.write(0);
            dos.write(encodedBearerData.length);
            dos.write(encodedBearerData, 0, encodedBearerData.length);
            dos.close();
            SubmitPdu pdu = new SubmitPdu();
            pdu.encodedMessage = baos.toByteArray();
            pdu.encodedScAddress = null;
            return pdu;
        } catch (IOException ex) {
            Rlog.e(LOG_TAG, "creating Deliver PDU failed: " + ex);
            return null;
        }
    }

    public void createPdu() {
        SmsEnvelope env = this.mEnvelope;
        CdmaSmsAddress addr = env.origAddress;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(baos));
        try {
            dos.writeInt(env.messageType);
            dos.writeInt(env.teleService);
            dos.writeInt(env.serviceCategory);
            dos.writeByte(addr.digitMode);
            dos.writeByte(addr.numberMode);
            dos.writeByte(addr.ton);
            dos.writeByte(addr.numberPlan);
            dos.writeByte(addr.numberOfDigits);
            dos.write(addr.origBytes, 0, addr.origBytes.length);
            dos.writeInt(env.bearerReply);
            dos.writeByte(env.replySeqNo);
            dos.writeByte(env.errorClass);
            dos.writeByte(env.causeCode);
            dos.writeInt(env.bearerData.length);
            dos.write(env.bearerData, 0, env.bearerData.length);
            dos.close();
            this.mPdu = baos.toByteArray();
        } catch (IOException ex) {
            Rlog.e(LOG_TAG, "createPdu: conversion from object to byte array failed: " + ex);
        }
    }

    public static byte convertDtmfToAscii(byte dtmfDigit) {
        switch (dtmfDigit) {
            case 0:
                return SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90;
            case 1:
                return SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33;
            case 2:
                return (byte) 50;
            case 3:
                return (byte) 51;
            case 4:
                return (byte) 52;
            case 5:
                return (byte) 53;
            case 6:
                return (byte) 54;
            case 7:
                return (byte) 55;
            case 8:
                return (byte) 56;
            case 9:
                return (byte) 57;
            case 10:
                return SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90;
            case 11:
                return SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33;
            case 12:
                return (byte) 35;
            case 13:
                return (byte) 65;
            case 14:
                return (byte) 66;
            case 15:
                return (byte) 67;
            default:
                return (byte) 32;
        }
    }

    public int getNumOfVoicemails() {
        return this.mBearerData.numberOfMessages;
    }

    public byte[] getIncomingSmsFingerprint() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(this.mEnvelope.serviceCategory);
        output.write(this.mEnvelope.teleService);
        output.write(
                this.mEnvelope.origAddress.origBytes,
                0,
                this.mEnvelope.origAddress.origBytes.length);
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)
                && this.mEnvelope.teleService == 4098) {
            if (this.mBearerData.userData != null) {
                output.write(
                        this.mBearerData.userData.payload,
                        0,
                        this.mBearerData.userData.payload.length);
            }
            if (this.mBearerData.msgCenterTimeStamp != null) {
                output.write(
                        this.mBearerData.msgCenterTimeStamp.toString().getBytes(),
                        0,
                        this.mBearerData.msgCenterTimeStamp.toString().length());
            }
            if (this.mBearerData.callbackNumber != null) {
                output.write(
                        this.mBearerData.callbackNumber.toString().getBytes(),
                        0,
                        this.mBearerData.callbackNumber.toString().length());
            }
        } else {
            output.write(this.mEnvelope.bearerData, 0, this.mEnvelope.bearerData.length);
            if (this.mEnvelope.origSubaddress != null
                    && this.mEnvelope.origSubaddress.origBytes != null) {
                output.write(
                        this.mEnvelope.origSubaddress.origBytes,
                        0,
                        this.mEnvelope.origSubaddress.origBytes.length);
            }
        }
        return output.toByteArray();
    }

    public ArrayList<CdmaSmsCbProgramData> getSmsCbProgramData() {
        return this.mBearerData.serviceCategoryProgramData;
    }

    public static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu) {
        SmsMessage msg = new SmsMessage();
        try {
            msg.setSubId(getSubId(phoneId));
            msg.parsePdu(pdu);
            return msg;
        } catch (OutOfMemoryError e) {
            Log.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public int getServiceCategory() {
        return this.mEnvelope.serviceCategory;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageIdentifier() {
        if (this.mBearerData != null) {
            return this.mBearerData.messageId;
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

    public static SmsMessage newFromParcel(Parcel p) {
        SmsMessage msg = new SmsMessage();
        SmsEnvelope env = new SmsEnvelope();
        CdmaSmsAddress addr = new CdmaSmsAddress();
        CdmaSmsSubaddress subaddr = new CdmaSmsSubaddress();
        env.teleService = p.readInt();
        if (p.readByte() != 0) {
            env.messageType = 1;
        } else if (env.teleService == 0) {
            env.messageType = 2;
        } else {
            env.messageType = 0;
        }
        env.serviceCategory = p.readInt();
        int addressDigitMode = p.readInt();
        addr.digitMode = (byte) (addressDigitMode & 255);
        addr.numberMode = (byte) (p.readInt() & 255);
        addr.ton = p.readInt();
        addr.numberPlan = (byte) (p.readInt() & 255);
        int readByte = p.readByte();
        addr.numberOfDigits = readByte;
        byte[] data = new byte[readByte];
        for (int index = 0; index < readByte; index++) {
            data[index] = p.readByte();
            if (addressDigitMode == 0) {
                data[index] = convertDtmfToAscii(data[index]);
            }
        }
        addr.origBytes = data;
        subaddr.type = p.readInt();
        subaddr.odd = p.readByte();
        int readByte2 = p.readByte();
        if (readByte2 < 0) {
            readByte2 = 0;
        }
        byte[] data2 = new byte[readByte2];
        for (int index2 = 0; index2 < readByte2; index2++) {
            data2[index2] = p.readByte();
        }
        subaddr.origBytes = data2;
        int countInt = p.readInt();
        if (countInt < 0) {
            countInt = 0;
        }
        byte[] data3 = new byte[countInt];
        for (int index3 = 0; index3 < countInt; index3++) {
            data3[index3] = p.readByte();
        }
        env.bearerData = data3;
        env.origAddress = addr;
        env.origSubaddress = subaddr;
        msg.mOriginatingAddress = addr;
        msg.mEnvelope = env;
        msg.createPdu();
        return msg;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getCDMAMessageType() {
        if (this.mEnvelope.serviceCategory != 0) {
            return 1;
        }
        return 0;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthForEms(
            CharSequence messageBody, boolean use7bitOnly, boolean isEms) {
        return BearerData.calcTextEncodingDetails(messageBody, use7bitOnly, true, isEms);
    }

    public static SubmitPdu getSubmitPdu(
            int subId,
            String scAddr,
            String destAddr,
            String message,
            boolean statusReportRequested,
            SmsHeader smsHeader,
            String callbackNumber,
            int priority) {
        if (message == null || destAddr == null) {
            return null;
        }
        UserData uData = new UserData();
        uData.payloadStr = message;
        uData.userDataHeader = smsHeader;
        return privateGetSubmitPdu(
                subId, destAddr, statusReportRequested, uData, callbackNumber, priority);
    }

    public static SubmitPdu getSubmitPdu(
            int subId,
            String destAddr,
            UserData userData,
            boolean statusReportRequested,
            String callbackNumber,
            int priority) {
        return privateGetSubmitPdu(
                subId, destAddr, statusReportRequested, userData, callbackNumber, priority);
    }

    public static SubmitPdu getSubmitPduForAutoLogin(
            String scAddr,
            String destAddr,
            String message,
            boolean statusReportRequested,
            SmsHeader smsHeader,
            int priority) {
        if (message == null || destAddr == null) {
            return null;
        }
        UserData uData = new UserData();
        uData.payloadStr = message;
        uData.userDataHeader = smsHeader;
        uData.isAutoLogin = true;
        return privateGetSubmitPdu(destAddr, statusReportRequested, uData, priority);
    }

    public static SubmitPdu getSubmitPduForCCTUnlock(String resultText) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = SmsHeader.PORT_CCT_UNLOCK;
        portAddrs.origPort = SmsHeader.PORT_CCT_UNLOCK;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        UserData uData = new UserData();
        uData.userDataHeader = smsHeader;
        uData.msgEncoding = 0;
        uData.msgEncodingSet = true;
        uData.payloadStr = resultText;
        return privateGetSubmitPdu("20868", false, uData);
    }

    public void parseCtcFota() {
        this.mIsCtcFota = false;
        for (int i = 0; i < this.mUserDataCtcFota.length; i++) {
            if (this.mUserDataCtcFota[i] == 1 && this.mUserDataCtcFota[i + 1] == 6) {
                int datalen = this.mUserDataCtcFota.length - i;
                byte[] payload = new byte[datalen];
                System.arraycopy(this.mUserDataCtcFota, i, payload, 0, datalen);
                this.mUserData = payload;
                this.mIsCtcFota = true;
                return;
            }
        }
    }

    public boolean isCtcFota() {
        return this.mIsCtcFota;
    }

    private static SubmitPdu privateGetSubmitPdu(
            int subId,
            String destAddrStr,
            boolean statusReportRequested,
            UserData userData,
            String callbackNumber,
            int priority) {
        CdmaSmsAddress destAddr =
                CdmaSmsAddress.parse(
                        PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(
                                destAddrStr, 1, 1));
        if (destAddr == null) {
            return null;
        }
        BearerData bearerData = new BearerData();
        bearerData.messageType = 2;
        bearerData.messageId = getNextMessageId();
        bearerData.deliveryAckReq = statusReportRequested;
        bearerData.userAckReq = false;
        bearerData.readAckReq = false;
        bearerData.reportReq = false;
        if (callbackNumber != null && callbackNumber.length() > 0) {
            Rlog.d(LOG_TAG, "callback number is set: " + callbackNumber);
            CdmaSmsAddress cbNumber = CdmaSmsAddress.parse(callbackNumber);
            if (cbNumber != null) {
                bearerData.callbackNumber = cbNumber;
            }
        }
        if (priority == 2) {
            Rlog.d(LOG_TAG, "priority is set to high");
            bearerData.priorityIndicatorSet = true;
            bearerData.priority = priority;
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId)
                .getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
            bearerData.languageIndicatorSet = true;
            bearerData.language = 64;
        }
        bearerData.userData = userData;
        byte[] encodedBearerData = BearerData.encode(bearerData);
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            Rlog.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            if (encodedBearerData != null) {
                Rlog.d(
                        LOG_TAG,
                        "MO raw BearerData = '" + HexDump.toHexString(encodedBearerData) + "'");
            }
        }
        if (encodedBearerData == null) {
            return null;
        }
        int teleservice = bearerData.hasUserDataHeader ? 4101 : 4098;
        SmsEnvelope envelope = new SmsEnvelope();
        envelope.messageType = 0;
        envelope.teleService = teleservice;
        envelope.destAddress = destAddr;
        envelope.bearerReply = 1;
        envelope.bearerData = encodedBearerData;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(envelope.teleService);
            dos.writeInt(0);
            dos.writeInt(0);
            dos.write(destAddr.digitMode);
            dos.write(destAddr.numberMode);
            dos.write(destAddr.ton);
            dos.write(destAddr.numberPlan);
            dos.write(destAddr.numberOfDigits);
            dos.write(destAddr.origBytes, 0, destAddr.origBytes.length);
            dos.write(0);
            dos.write(0);
            dos.write(0);
            dos.write(encodedBearerData.length);
            dos.write(encodedBearerData, 0, encodedBearerData.length);
            dos.close();
            SubmitPdu pdu = new SubmitPdu();
            pdu.encodedMessage = baos.toByteArray();
            pdu.encodedScAddress = null;
            return pdu;
        } catch (IOException ex) {
            Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + ex);
            return null;
        }
    }

    public static SubmitPdu getDomainChangeNotification(byte type, String doChgAddr) {
        CdmaSmsAddress destAddr =
                CdmaSmsAddress.parse(
                        PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(
                                doChgAddr, 1, 1));
        if (destAddr == null) {
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
        UserData uData = new UserData();
        uData.msgEncoding = 0;
        uData.msgEncodingSet = true;
        uData.payload = new byte[8];
        uData.payload[0] = 0;
        uData.payload[1] = (byte) (bearerData.messageId % 256);
        uData.payload[2] = 8;
        uData.payload[3] = type;
        long scTimeMillis = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(scTimeMillis);
        long years = cal.get(1);
        int months = cal.get(2);
        int dates = cal.get(5);
        int hours = cal.get(10);
        int minutes = cal.get(12);
        int seconds = cal.get(13);
        long scTimeMillis2 = months;
        long UtcTimeStamp =
                ((years - 1900) * 31556926)
                        + (scTimeMillis2 * 2629743)
                        + (dates * 86400)
                        + (hours * 3600)
                        + (minutes * 60)
                        + seconds;
        uData.payload[7] = (byte) (UtcTimeStamp & 255);
        uData.payload[6] = (byte) ((UtcTimeStamp >> 8) & 255);
        uData.payload[5] = (byte) ((UtcTimeStamp >> 16) & 255);
        uData.payload[4] = (byte) (255 & (UtcTimeStamp >> 24));
        bearerData.userData = uData;
        byte[] encodedBearerData = BearerData.encode(bearerData);
        if (Log.isLoggable(LOGGABLE_TAG, 2)) {
            Log.d(LOG_TAG, "MO (encoded) BearerData = " + bearerData);
            if (encodedBearerData != null) {
                Log.d(
                        LOG_TAG,
                        "MO raw BearerData = '" + HexDump.toHexString(encodedBearerData) + "'");
            }
        }
        if (encodedBearerData == null) {
            return null;
        }
        SmsEnvelope envelope = new SmsEnvelope();
        envelope.messageType = 0;
        envelope.teleService = 4242;
        envelope.destAddress = destAddr;
        envelope.bearerReply = 1;
        envelope.bearerData = encodedBearerData;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
            DataOutputStream dos = new DataOutputStream(baos);
            try {
                int seconds2 = envelope.teleService;
                dos.writeInt(seconds2);
                dos.writeInt(0);
                dos.writeInt(0);
                dos.write(destAddr.digitMode);
                dos.write(destAddr.numberMode);
                dos.write(destAddr.ton);
                dos.write(destAddr.numberPlan);
                dos.write(destAddr.numberOfDigits);
                try {
                    try {
                        dos.write(destAddr.origBytes, 0, destAddr.origBytes.length);
                        dos.write(0);
                        dos.write(0);
                        dos.write(0);
                        dos.write(encodedBearerData.length);
                        dos.write(encodedBearerData, 0, encodedBearerData.length);
                        dos.close();
                        SubmitPdu pdu = new SubmitPdu();
                        pdu.encodedMessage = baos.toByteArray();
                        pdu.encodedScAddress = null;
                        return pdu;
                    } catch (IOException e) {
                        ex = e;
                        Rlog.e(LOG_TAG, "creating SubmitPdu failed: " + ex);
                        return null;
                    }
                } catch (IOException e2) {
                    ex = e2;
                }
            } catch (IOException e3) {
                ex = e3;
            }
        } catch (IOException e4) {
            ex = e4;
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(
            CharSequence messageBody, boolean use7bitOnly, int maxEmailLen) {
        return BearerData.calcTextEncodingDetailsWithEmail(messageBody, use7bitOnly, maxEmailLen);
    }

    public int getMessageEncoding() {
        return this.mReceivedEncodingType;
    }
}
