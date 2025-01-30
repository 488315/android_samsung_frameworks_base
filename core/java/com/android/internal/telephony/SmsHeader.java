package com.android.internal.telephony;

import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import com.android.internal.util.HexDump;
import com.samsung.android.feature.SemCscFeature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes5.dex */
public class SmsHeader {
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_16_BIT = 5;
    public static final int ELT_ID_APPLICATION_PORT_ADDRESSING_8_BIT = 4;
    public static final int ELT_ID_CHARACTER_SIZE_WVG_OBJECT = 25;
    public static final int ELT_ID_COMPRESSION_CONTROL = 22;
    public static final int ELT_ID_CONCATENATED_16_BIT_REFERENCE = 8;
    public static final int ELT_ID_CONCATENATED_8_BIT_REFERENCE = 0;
    public static final int ELT_ID_ENHANCED_VOICE_MAIL_INFORMATION = 35;
    public static final int ELT_ID_EXTENDED_OBJECT = 20;
    public static final int ELT_ID_EXTENDED_OBJECT_DATA_REQUEST_CMD = 26;
    public static final int ELT_ID_HYPERLINK_FORMAT_ELEMENT = 33;
    public static final int ELT_ID_KT_READ_CONFIRM = 68;
    public static final int ELT_ID_LARGE_ANIMATION = 14;
    public static final int ELT_ID_LARGE_PICTURE = 16;
    public static final int ELT_ID_NATIONAL_LANGUAGE_LOCKING_SHIFT = 37;
    public static final int ELT_ID_NATIONAL_LANGUAGE_SINGLE_SHIFT = 36;
    public static final int ELT_ID_OBJECT_DISTR_INDICATOR = 23;
    public static final int ELT_ID_OPERATOR_CONTROL_ELEMENT = 192;
    public static final int ELT_ID_PREDEFINED_ANIMATION = 13;
    public static final int ELT_ID_PREDEFINED_SOUND = 11;
    public static final int ELT_ID_REPLY_ADDRESS_ELEMENT = 34;
    public static final int ELT_ID_REUSED_EXTENDED_OBJECT = 21;
    public static final int ELT_ID_RFC_822_EMAIL_HEADER = 32;
    public static final int ELT_ID_SMALL_ANIMATION = 15;
    public static final int ELT_ID_SMALL_PICTURE = 17;
    public static final int ELT_ID_SMSC_CONTROL_PARAMS = 6;
    public static final int ELT_ID_SPECIAL_SMS_MESSAGE_INDICATION = 1;
    public static final int ELT_ID_STANDARD_WVG_OBJECT = 24;
    public static final int ELT_ID_TEXT_FORMATTING = 10;
    public static final int ELT_ID_UDH_SOURCE_INDICATION = 7;
    public static final int ELT_ID_USER_DEFINED_SOUND = 12;
    public static final int ELT_ID_USER_PROMPT_INDICATOR = 19;
    public static final int ELT_ID_VARIABLE_PICTURE = 18;
    public static final int ELT_ID_WIRELESS_CTRL_MSG_PROTOCOL = 9;
    public static final int PORT_CCT_UNLOCK = 9300;
    public static final int PORT_KT_APP_MANAGER_MAX = 49686;
    public static final int PORT_KT_APP_MANAGER_MIN = 49680;
    public static final int PORT_KT_MOBILECARE_DATA_MESSAGE = 49702;
    public static final int PORT_KT_MOBILECARE_DATA_ROAMING_MESSAGE = 49703;
    public static final int PORT_KT_MOBILECARE_NETWORK_MESSAGE = 49700;
    public static final int PORT_KT_MOBILECARE_REBOOT_MESSAGE = 49699;
    public static final int PORT_KT_MOBILECARE_ROAMING_MESSAGE = 49701;
    public static final int PORT_KT_MOBILECARE_ROAMING_MNO_SELECTION_MESSAGE = 49704;
    public static final int PORT_KT_MOBILECARE_USB_TETHERING_MESSAGE = 49705;
    public static final int PORT_KT_TWO_PHONE_CANCEL = 50178;
    public static final int PORT_KT_TWO_PHONE_CHANGE = 50179;
    public static final int PORT_KT_TWO_PHONE_SUBSCRIBE = 50177;
    public static final int PORT_KT_WPS_MESSAGE = 49697;
    public static final int PORT_LGT_SUPL_SMS = 7275;
    public static final int PORT_RCS_OTP = 37273;
    public static final int PORT_SKT_COMMON_PUSH_SMS = 16988;
    public static final int PORT_SKT_FINDING_FRIENDS = 7275;
    public static final int PORT_SKT_FOTA_SMS = 16964;
    public static final int PORT_WAP_PUSH = 2948;
    public static final int PORT_WAP_WSP = 9200;
    public ConcatRef concatRef;
    public KTReadConfirm ktReadConfirm;
    public int languageShiftTable;
    public int languageTable;
    public PortAddrs portAddrs;
    public ArrayList<SpecialSmsMsg> specialSmsMsgList = new ArrayList<>();
    public ArrayList<MiscElt> miscEltList = new ArrayList<>();
    public boolean safeMessageIndication = false;
    public boolean twoPhoneIndication = false;

    public static class KTReadConfirm {

        /* renamed from: id */
        public int f1007id;
        public int readConfirmID;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsHeader smsHeader = (SmsHeader) o;
        if (this.languageTable == smsHeader.languageTable && this.languageShiftTable == smsHeader.languageShiftTable && Objects.equals(this.portAddrs, smsHeader.portAddrs) && Objects.equals(this.concatRef, smsHeader.concatRef) && Objects.equals(this.specialSmsMsgList, smsHeader.specialSmsMsgList) && Objects.equals(this.miscEltList, smsHeader.miscEltList)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.portAddrs, this.concatRef, this.specialSmsMsgList, this.miscEltList, Integer.valueOf(this.languageTable), Integer.valueOf(this.languageShiftTable));
    }

    public static class PortAddrs {
        public boolean areEightBits;
        public int destPort;
        public int origPort;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PortAddrs portAddrs = (PortAddrs) o;
            if (this.destPort == portAddrs.destPort && this.origPort == portAddrs.origPort && this.areEightBits == portAddrs.areEightBits) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.destPort), Integer.valueOf(this.origPort), Boolean.valueOf(this.areEightBits));
        }
    }

    public static class ConcatRef {
        public boolean isEightBits;
        public int msgCount;
        public int refNumber;
        public int seqNumber;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ConcatRef concatRef = (ConcatRef) o;
            if (this.refNumber == concatRef.refNumber && this.seqNumber == concatRef.seqNumber && this.msgCount == concatRef.msgCount && this.isEightBits == concatRef.isEightBits) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.refNumber), Integer.valueOf(this.seqNumber), Integer.valueOf(this.msgCount), Boolean.valueOf(this.isEightBits));
        }
    }

    public static class SpecialSmsMsg {
        public int msgCount;
        public int msgIndType;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SpecialSmsMsg that = (SpecialSmsMsg) o;
            if (this.msgIndType == that.msgIndType && this.msgCount == that.msgCount) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.msgIndType), Integer.valueOf(this.msgCount));
        }
    }

    public static class MiscElt {
        public byte[] data;

        /* renamed from: id */
        public int f1008id;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MiscElt miscElt = (MiscElt) o;
            if (this.f1008id == miscElt.f1008id && Arrays.equals(this.data, miscElt.data)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = Objects.hash(Integer.valueOf(this.f1008id));
            return (result * 31) + Arrays.hashCode(this.data);
        }
    }

    public static SmsHeader fromByteArray(byte[] data) {
        return semFromByteArray(SmsManager.getDefaultSmsSubscriptionId(), data);
    }

    public static byte[] toByteArray(SmsHeader smsHeader) {
        if (smsHeader.portAddrs == null && smsHeader.concatRef == null && smsHeader.specialSmsMsgList.isEmpty() && smsHeader.miscEltList.isEmpty() && smsHeader.languageShiftTable == 0 && smsHeader.ktReadConfirm == null && smsHeader.languageTable == 0) {
            return null;
        }
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(140);
        ConcatRef concatRef = smsHeader.concatRef;
        if (concatRef != null) {
            if (concatRef.isEightBits) {
                outStream.write(0);
                outStream.write(3);
                outStream.write(concatRef.refNumber);
            } else {
                outStream.write(8);
                outStream.write(4);
                outStream.write(concatRef.refNumber >>> 8);
                outStream.write(concatRef.refNumber & 255);
            }
            outStream.write(concatRef.msgCount);
            outStream.write(concatRef.seqNumber);
        }
        PortAddrs portAddrs = smsHeader.portAddrs;
        if (portAddrs != null) {
            if (portAddrs.areEightBits) {
                outStream.write(4);
                outStream.write(2);
                outStream.write(portAddrs.destPort);
                outStream.write(portAddrs.origPort);
            } else {
                outStream.write(5);
                outStream.write(4);
                outStream.write(portAddrs.destPort >>> 8);
                outStream.write(portAddrs.destPort & 255);
                outStream.write(portAddrs.origPort >>> 8);
                outStream.write(portAddrs.origPort & 255);
            }
        }
        if (smsHeader.languageShiftTable != 0) {
            outStream.write(36);
            outStream.write(1);
            outStream.write(smsHeader.languageShiftTable);
        }
        if (smsHeader.languageTable != 0) {
            outStream.write(37);
            outStream.write(1);
            outStream.write(smsHeader.languageTable);
        }
        Iterator<SpecialSmsMsg> it = smsHeader.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            SpecialSmsMsg specialSmsMsg = it.next();
            outStream.write(1);
            outStream.write(2);
            outStream.write(specialSmsMsg.msgIndType & 255);
            outStream.write(specialSmsMsg.msgCount & 255);
        }
        Iterator<MiscElt> it2 = smsHeader.miscEltList.iterator();
        while (it2.hasNext()) {
            MiscElt miscElt = it2.next();
            outStream.write(miscElt.f1008id);
            outStream.write(miscElt.data.length);
            outStream.write(miscElt.data, 0, miscElt.data.length);
        }
        KTReadConfirm ktReadConfirm = smsHeader.ktReadConfirm;
        if (ktReadConfirm != null) {
            outStream.write(68);
            outStream.write(1);
            outStream.write(ktReadConfirm.readConfirmID);
        }
        return outStream.toByteArray();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDataHeader ");
        builder.append("{ ConcatRef ");
        if (this.concatRef == null) {
            builder.append("unset");
        } else {
            builder.append("{ refNumber=" + this.concatRef.refNumber);
            builder.append(", msgCount=" + this.concatRef.msgCount);
            builder.append(", seqNumber=" + this.concatRef.seqNumber);
            builder.append(", isEightBits=" + this.concatRef.isEightBits);
            builder.append(" }");
        }
        builder.append(", PortAddrs ");
        if (this.portAddrs == null) {
            builder.append("unset");
        } else {
            builder.append("{ destPort=" + this.portAddrs.destPort);
            builder.append(", origPort=" + this.portAddrs.origPort);
            builder.append(", areEightBits=" + this.portAddrs.areEightBits);
            builder.append(" }");
        }
        if (this.languageShiftTable != 0) {
            builder.append(", languageShiftTable=" + this.languageShiftTable);
        }
        if (this.languageTable != 0) {
            builder.append(", languageTable=" + this.languageTable);
        }
        Iterator<SpecialSmsMsg> it = this.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            SpecialSmsMsg specialSmsMsg = it.next();
            builder.append(", SpecialSmsMsg ");
            builder.append("{ msgIndType=" + specialSmsMsg.msgIndType);
            builder.append(", msgCount=" + specialSmsMsg.msgCount);
            builder.append(" }");
        }
        Iterator<MiscElt> it2 = this.miscEltList.iterator();
        while (it2.hasNext()) {
            MiscElt miscElt = it2.next();
            builder.append(", MiscElt ");
            builder.append("{ id=" + miscElt.f1008id);
            builder.append(", length=" + miscElt.data.length);
            builder.append(", data=" + HexDump.toHexString(miscElt.data));
            builder.append(" }");
        }
        builder.append(" }");
        return builder.toString();
    }

    public static SmsHeader semFromByteArray(int subId, byte[] data) {
        String mnoName = SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase();
        Rlog.m77d("SmsHeader", "semFromByteArray: Mno = " + mnoName);
        ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        SmsHeader smsHeader = new SmsHeader();
        while (inStream.available() > 0) {
            int id = inStream.read();
            int length = inStream.read();
            switch (id) {
                case 0:
                    ConcatRef concatRef = new ConcatRef();
                    concatRef.refNumber = inStream.read();
                    concatRef.msgCount = inStream.read();
                    concatRef.seqNumber = inStream.read();
                    concatRef.isEightBits = true;
                    if (concatRef.msgCount == 0) {
                        continue;
                    } else if (concatRef.seqNumber != 0 && concatRef.seqNumber <= concatRef.msgCount) {
                        smsHeader.concatRef = concatRef;
                        break;
                    }
                    break;
                case 1:
                    if (TelephonyFeatures.isCountrySpecific(SubscriptionManager.getPhoneId(subId), "KOR")) {
                        MiscElt miscElt = new MiscElt();
                        miscElt.f1008id = id;
                        miscElt.data = new byte[length];
                        inStream.read(miscElt.data, 0, length);
                        smsHeader.miscEltList.add(miscElt);
                        break;
                    } else {
                        SpecialSmsMsg specialSmsMsg = new SpecialSmsMsg();
                        specialSmsMsg.msgIndType = inStream.read();
                        specialSmsMsg.msgCount = inStream.read();
                        smsHeader.specialSmsMsgList.add(specialSmsMsg);
                        continue;
                    }
                case 4:
                    PortAddrs portAddrs = new PortAddrs();
                    portAddrs.destPort = inStream.read();
                    portAddrs.origPort = inStream.read();
                    portAddrs.areEightBits = true;
                    smsHeader.portAddrs = portAddrs;
                    continue;
                case 5:
                    PortAddrs portAddrs2 = new PortAddrs();
                    portAddrs2.destPort = (inStream.read() << 8) | inStream.read();
                    portAddrs2.origPort = (inStream.read() << 8) | inStream.read();
                    portAddrs2.areEightBits = false;
                    smsHeader.portAddrs = portAddrs2;
                    continue;
                case 8:
                    ConcatRef concatRef2 = new ConcatRef();
                    concatRef2.refNumber = (inStream.read() << 8) | inStream.read();
                    concatRef2.msgCount = inStream.read();
                    concatRef2.seqNumber = inStream.read();
                    concatRef2.isEightBits = false;
                    if (concatRef2.msgCount == 0) {
                        continue;
                    } else if (concatRef2.seqNumber != 0 && concatRef2.seqNumber <= concatRef2.msgCount) {
                        smsHeader.concatRef = concatRef2;
                        break;
                    }
                    break;
                case 36:
                    smsHeader.languageShiftTable = inStream.read();
                    continue;
                case 37:
                    smsHeader.languageTable = inStream.read();
                    continue;
                case 68:
                    KTReadConfirm ktReadConfirm = new KTReadConfirm();
                    ktReadConfirm.f1007id = id;
                    ktReadConfirm.readConfirmID = inStream.read();
                    smsHeader.ktReadConfirm = ktReadConfirm;
                    Rlog.m81i("SmsHeader", "id:" + ktReadConfirm.f1007id + "readConfirmID" + ktReadConfirm.readConfirmID);
                    continue;
                case 192:
                    if (!mnoName.contains("SKT_KR") && !mnoName.contains("KT_KR") && !mnoName.contains("LGU+_KR")) {
                        break;
                    } else {
                        int operatorControlElementValue = inStream.read();
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_SAFE_MESSAGE_INDICATION)) {
                            int phoneId = SubscriptionManager.getPhoneId(subId);
                            String simType = SemTelephonyUtils.getTelephonyProperty(phoneId, "ril.simtype", "0");
                            if ((simType.equals("4") || simType.equals("3")) && operatorControlElementValue == 1) {
                                smsHeader.safeMessageIndication = true;
                            } else if (simType.equals("2") && (operatorControlElementValue & 2) == 2) {
                                smsHeader.safeMessageIndication = true;
                            }
                            Rlog.m81i("SafeMessageIndication", "Received smsHeader.safeMessageIndication: " + smsHeader.safeMessageIndication + " simType: " + simType);
                        }
                        if (!SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService")) {
                            break;
                        } else {
                            if ((operatorControlElementValue & 1) == 1) {
                                smsHeader.twoPhoneIndication = true;
                            }
                            Rlog.m81i("TwoPhoneIndication", "Received smsHeader.twoPhoneIndication: " + smsHeader.twoPhoneIndication);
                            break;
                        }
                    }
                    break;
            }
            MiscElt miscElt2 = new MiscElt();
            miscElt2.f1008id = id;
            miscElt2.data = new byte[length];
            inStream.read(miscElt2.data, 0, length);
            smsHeader.miscEltList.add(miscElt2);
        }
        return smsHeader;
    }
}
