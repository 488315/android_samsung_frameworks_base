package com.android.internal.telephony;

import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.HexDump;
import com.android.telephony.Rlog;
import com.samsung.android.feature.SemCscFeature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
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
    public static final int PORT_TMOUS_DIAGNOSTICS_DEST = 3246;
    public static final int PORT_TMOUS_DIAGNOSTICS_SOURCE = 9201;
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
    public boolean linkWarningIndication = false;

    public static class KTReadConfirm {
        public int id;
        public int readConfirmID;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SmsHeader smsHeader = (SmsHeader) obj;
            if (this.languageTable == smsHeader.languageTable && this.languageShiftTable == smsHeader.languageShiftTable && Objects.equals(this.portAddrs, smsHeader.portAddrs) && Objects.equals(this.concatRef, smsHeader.concatRef) && Objects.equals(this.specialSmsMsgList, smsHeader.specialSmsMsgList) && Objects.equals(this.miscEltList, smsHeader.miscEltList)) {
                return true;
            }
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                PortAddrs portAddrs = (PortAddrs) obj;
                if (this.destPort == portAddrs.destPort && this.origPort == portAddrs.origPort && this.areEightBits == portAddrs.areEightBits) {
                    return true;
                }
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ConcatRef concatRef = (ConcatRef) obj;
                if (this.refNumber == concatRef.refNumber && this.seqNumber == concatRef.seqNumber && this.msgCount == concatRef.msgCount && this.isEightBits == concatRef.isEightBits) {
                    return true;
                }
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                SpecialSmsMsg specialSmsMsg = (SpecialSmsMsg) obj;
                if (this.msgIndType == specialSmsMsg.msgIndType && this.msgCount == specialSmsMsg.msgCount) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.msgIndType), Integer.valueOf(this.msgCount));
        }
    }

    public static class MiscElt {
        public byte[] data;
        public int id;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                MiscElt miscElt = (MiscElt) obj;
                if (this.id == miscElt.id && Arrays.equals(this.data, miscElt.data)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (Objects.hash(Integer.valueOf(this.id)) * 31) + Arrays.hashCode(this.data);
        }
    }

    public static SmsHeader fromByteArray(byte[] bArr) {
        return semFromByteArray(SmsManager.getDefaultSmsSubscriptionId(), bArr);
    }

    public static byte[] toByteArray(SmsHeader smsHeader) {
        if (smsHeader.portAddrs == null && smsHeader.concatRef == null && smsHeader.specialSmsMsgList.isEmpty() && smsHeader.miscEltList.isEmpty() && smsHeader.languageShiftTable == 0 && smsHeader.ktReadConfirm == null && smsHeader.languageTable == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(140);
        ConcatRef concatRef = smsHeader.concatRef;
        if (concatRef != null) {
            if (concatRef.isEightBits) {
                byteArrayOutputStream.write(0);
                byteArrayOutputStream.write(3);
                byteArrayOutputStream.write(concatRef.refNumber);
            } else {
                byteArrayOutputStream.write(8);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(concatRef.refNumber >>> 8);
                byteArrayOutputStream.write(concatRef.refNumber & 255);
            }
            byteArrayOutputStream.write(concatRef.msgCount);
            byteArrayOutputStream.write(concatRef.seqNumber);
        }
        PortAddrs portAddrs = smsHeader.portAddrs;
        if (portAddrs != null) {
            if (portAddrs.areEightBits) {
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(2);
                byteArrayOutputStream.write(portAddrs.destPort);
                byteArrayOutputStream.write(portAddrs.origPort);
            } else {
                byteArrayOutputStream.write(5);
                byteArrayOutputStream.write(4);
                byteArrayOutputStream.write(portAddrs.destPort >>> 8);
                byteArrayOutputStream.write(portAddrs.destPort & 255);
                byteArrayOutputStream.write(portAddrs.origPort >>> 8);
                byteArrayOutputStream.write(portAddrs.origPort & 255);
            }
        }
        if (smsHeader.languageShiftTable != 0) {
            byteArrayOutputStream.write(36);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(smsHeader.languageShiftTable);
        }
        if (smsHeader.languageTable != 0) {
            byteArrayOutputStream.write(37);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(smsHeader.languageTable);
        }
        Iterator<SpecialSmsMsg> it = smsHeader.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            SpecialSmsMsg next = it.next();
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(2);
            byteArrayOutputStream.write(next.msgIndType & 255);
            byteArrayOutputStream.write(next.msgCount & 255);
        }
        Iterator<MiscElt> it2 = smsHeader.miscEltList.iterator();
        while (it2.hasNext()) {
            MiscElt next2 = it2.next();
            byteArrayOutputStream.write(next2.id);
            byteArrayOutputStream.write(next2.data.length);
            byteArrayOutputStream.write(next2.data, 0, next2.data.length);
        }
        KTReadConfirm kTReadConfirm = smsHeader.ktReadConfirm;
        if (kTReadConfirm != null) {
            byteArrayOutputStream.write(68);
            byteArrayOutputStream.write(1);
            byteArrayOutputStream.write(kTReadConfirm.readConfirmID);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UserDataHeader { ConcatRef ");
        if (this.concatRef == null) {
            sb.append("unset");
        } else {
            sb.append("{ refNumber=" + this.concatRef.refNumber);
            sb.append(", msgCount=" + this.concatRef.msgCount);
            sb.append(", seqNumber=" + this.concatRef.seqNumber);
            sb.append(", isEightBits=" + this.concatRef.isEightBits);
            sb.append(" }");
        }
        sb.append(", PortAddrs ");
        if (this.portAddrs == null) {
            sb.append("unset");
        } else {
            sb.append("{ destPort=" + this.portAddrs.destPort);
            sb.append(", origPort=" + this.portAddrs.origPort);
            sb.append(", areEightBits=" + this.portAddrs.areEightBits);
            sb.append(" }");
        }
        if (this.languageShiftTable != 0) {
            sb.append(", languageShiftTable=" + this.languageShiftTable);
        }
        if (this.languageTable != 0) {
            sb.append(", languageTable=" + this.languageTable);
        }
        Iterator<SpecialSmsMsg> it = this.specialSmsMsgList.iterator();
        while (it.hasNext()) {
            SpecialSmsMsg next = it.next();
            sb.append(", SpecialSmsMsg ");
            sb.append("{ msgIndType=" + next.msgIndType);
            sb.append(", msgCount=" + next.msgCount);
            sb.append(" }");
        }
        Iterator<MiscElt> it2 = this.miscEltList.iterator();
        while (it2.hasNext()) {
            MiscElt next2 = it2.next();
            sb.append(", MiscElt ");
            sb.append("{ id=" + next2.id);
            sb.append(", length=" + next2.data.length);
            sb.append(", data=" + HexDump.toHexString(next2.data));
            sb.append(" }");
        }
        sb.append(" }");
        return sb.toString();
    }

    public static SmsHeader semFromByteArray(int i, byte[] bArr) {
        String upperCase = SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName().toUpperCase();
        if (bArr != null) {
            Rlog.i("SmsHeader", "semFromByteArray: Mno = " + upperCase + " UDH = " + IccUtils.bytesToHexString(bArr));
        } else {
            Rlog.i("SmsHeader", "semFromByteArray: Mno = " + upperCase + " No UDH Info");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        SmsHeader smsHeader = new SmsHeader();
        while (byteArrayInputStream.available() > 0) {
            int read = byteArrayInputStream.read();
            int read2 = byteArrayInputStream.read();
            if (read == 0) {
                ConcatRef concatRef = new ConcatRef();
                concatRef.refNumber = byteArrayInputStream.read();
                concatRef.msgCount = byteArrayInputStream.read();
                concatRef.seqNumber = byteArrayInputStream.read();
                concatRef.isEightBits = true;
                if (concatRef.msgCount != 0 && concatRef.seqNumber != 0 && concatRef.seqNumber <= concatRef.msgCount) {
                    smsHeader.concatRef = concatRef;
                }
            } else if (read != 1) {
                if (read == 4) {
                    PortAddrs portAddrs = new PortAddrs();
                    portAddrs.destPort = byteArrayInputStream.read();
                    portAddrs.origPort = byteArrayInputStream.read();
                    portAddrs.areEightBits = true;
                    smsHeader.portAddrs = portAddrs;
                } else if (read == 5) {
                    PortAddrs portAddrs2 = new PortAddrs();
                    portAddrs2.destPort = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    portAddrs2.origPort = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    portAddrs2.areEightBits = false;
                    smsHeader.portAddrs = portAddrs2;
                } else if (read == 8) {
                    ConcatRef concatRef2 = new ConcatRef();
                    concatRef2.refNumber = (byteArrayInputStream.read() << 8) | byteArrayInputStream.read();
                    concatRef2.msgCount = byteArrayInputStream.read();
                    concatRef2.seqNumber = byteArrayInputStream.read();
                    concatRef2.isEightBits = false;
                    if (concatRef2.msgCount != 0 && concatRef2.seqNumber != 0 && concatRef2.seqNumber <= concatRef2.msgCount) {
                        smsHeader.concatRef = concatRef2;
                    }
                } else if (read == 68) {
                    KTReadConfirm kTReadConfirm = new KTReadConfirm();
                    kTReadConfirm.id = read;
                    kTReadConfirm.readConfirmID = byteArrayInputStream.read();
                    smsHeader.ktReadConfirm = kTReadConfirm;
                    Rlog.i("SmsHeader", "id:" + kTReadConfirm.id + "readConfirmID" + kTReadConfirm.readConfirmID);
                } else if (read != 192) {
                    if (read == 36) {
                        smsHeader.languageShiftTable = byteArrayInputStream.read();
                    } else if (read == 37) {
                        smsHeader.languageTable = byteArrayInputStream.read();
                    } else {
                        MiscElt miscElt = new MiscElt();
                        miscElt.id = read;
                        miscElt.data = new byte[read2];
                        byteArrayInputStream.read(miscElt.data, 0, read2);
                        smsHeader.miscEltList.add(miscElt);
                    }
                } else if (upperCase.contains("SKT_KR") || upperCase.contains("KT_KR") || upperCase.contains("LGU+_KR")) {
                    int read3 = byteArrayInputStream.read();
                    String telephonyProperty = SemTelephonyUtils.getTelephonyProperty(SubscriptionManager.getPhoneId(i), "ril.simtype", "0");
                    if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_SAFE_MESSAGE_INDICATION)) {
                        if ((telephonyProperty.equals("4") || telephonyProperty.equals("3")) && read3 == 1) {
                            smsHeader.safeMessageIndication = true;
                        } else if (telephonyProperty.equals("2") && (read3 & 2) == 2) {
                            smsHeader.safeMessageIndication = true;
                        }
                        Rlog.i("SafeMessageIndication", "Received smsHeader.safeMessageIndication: " + smsHeader.safeMessageIndication + " simType: " + telephonyProperty);
                    }
                    if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_LINK_WARNING_INDICATION)) {
                        if (telephonyProperty.equals("2") && (read3 & 4) == 4) {
                            smsHeader.linkWarningIndication = true;
                        }
                        Rlog.i("LinkWarningIndication", "Received smsHeader.linkWarningIndication: " + smsHeader.linkWarningIndication + " simType: " + telephonyProperty);
                    }
                    if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService")) {
                        if ((read3 & 1) == 1) {
                            smsHeader.twoPhoneIndication = true;
                        }
                        Rlog.i("TwoPhoneIndication", "Received smsHeader.twoPhoneIndication: " + smsHeader.twoPhoneIndication);
                    }
                } else {
                    MiscElt miscElt2 = new MiscElt();
                    miscElt2.id = read;
                    miscElt2.data = new byte[read2];
                    byteArrayInputStream.read(miscElt2.data, 0, read2);
                    smsHeader.miscEltList.add(miscElt2);
                }
            } else if (TelephonyFeatures.isCountrySpecific(SubscriptionManager.getPhoneId(i), "KOR")) {
                MiscElt miscElt3 = new MiscElt();
                miscElt3.id = read;
                miscElt3.data = new byte[read2];
                byteArrayInputStream.read(miscElt3.data, 0, read2);
                smsHeader.miscEltList.add(miscElt3);
            } else {
                SpecialSmsMsg specialSmsMsg = new SpecialSmsMsg();
                specialSmsMsg.msgIndType = byteArrayInputStream.read();
                specialSmsMsg.msgCount = byteArrayInputStream.read();
                smsHeader.specialSmsMsgList.add(specialSmsMsg);
            }
        }
        return smsHeader;
    }
}
