package com.android.internal.telephony.gsm;

import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsAddress;
import java.text.ParseException;

/* loaded from: classes5.dex */
public class GsmSmsAddress extends SmsAddress {
    static final String DOCOMO = "DOCOMO";
    static final String DOCOMO_SMS = "DOCOMO SMS";
    private static final String LOG_TAG = "GsmSmsAddress";
    static final String NTT_DOCOMO = "NTT DOCOMO";
    static final int OFFSET_ADDRESS_LENGTH = 0;
    static final int OFFSET_ADDRESS_VALUE = 2;
    static final int OFFSET_TOA = 1;
    String partofaddress;

    public GsmSmsAddress(byte[] data, int offset, int length) throws ParseException {
        this.origBytes = new byte[length];
        System.arraycopy(data, offset, this.origBytes, 0, length);
        int addressLength = this.origBytes[0] & 255;
        int toa = this.origBytes[1] & 255;
        this.ton = (toa >> 4) & 7;
        if ((toa & 128) != 128) {
            throw new ParseException("Invalid TOA - high bit must be set. toa = " + toa, offset + 1);
        }
        if (isAlphanumeric()) {
            int countSeptets = (addressLength * 4) / 7;
            this.address = GsmAlphabet.gsm7BitPackedToString(this.origBytes, 2, countSeptets);
            return;
        }
        byte lastByte = this.origBytes[length - 1];
        if ((addressLength & 1) == 1) {
            byte[] bArr = this.origBytes;
            int i = length - 1;
            bArr[i] = (byte) (bArr[i] | 240);
        }
        this.address = PhoneNumberUtils.calledPartyBCDToString(this.origBytes, 1, length - 1, 2);
        this.origBytes[length - 1] = lastByte;
    }

    public GsmSmsAddress(int subId, byte[] data, int offset, int length) throws ParseException {
        this.origBytes = new byte[length];
        System.arraycopy(data, offset, this.origBytes, 0, length);
        int addressLength = this.origBytes[0] & 255;
        int toa = this.origBytes[1] & 255;
        this.ton = (toa >> 4) & 7;
        if ((toa & 128) != 128) {
            throw new ParseException("Invalid TOA - high bit must be set. toa = " + toa, offset + 1);
        }
        if (isAlphanumeric()) {
            int countSeptets = (addressLength * 4) / 7;
            this.address = GsmAlphabet.gsm7BitPackedToString(this.origBytes, 2, countSeptets);
        } else {
            byte lastByte = this.origBytes[length - 1];
            if ((addressLength & 1) == 1) {
                byte[] bArr = this.origBytes;
                int i = length - 1;
                bArr[i] = (byte) (bArr[i] | 240);
            }
            this.address = PhoneNumberUtils.calledPartyBCDToString(this.origBytes, 1, length - 1, 2);
            this.origBytes[length - 1] = lastByte;
        }
        if (this.address != null) {
            if ((SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("SKT") || SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("KT_KR") || SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("LGU")) && this.address.indexOf("+") != -1) {
                Log.m94d(LOG_TAG, "Address Before Replacement = " + this.address);
                StringBuffer sb = new StringBuffer(this.address);
                sb.deleteCharAt(this.address.indexOf("+"));
                sb.insert(0, "+");
                this.address = sb.toString();
                Log.m94d(LOG_TAG, "Address after Replacement = " + this.address);
            }
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains(DOCOMO)) {
                if (this.address.length() >= 6) {
                    this.partofaddress = this.address.substring(0, 6);
                } else {
                    this.partofaddress = this.address;
                }
                if (NTT_DOCOMO.equalsIgnoreCase(this.address)) {
                    if (!NTT_DOCOMO.equals(this.address)) {
                        this.address = NTT_DOCOMO;
                    }
                } else if (DOCOMO_SMS.equalsIgnoreCase(this.address)) {
                    if (!DOCOMO_SMS.equals(this.address)) {
                        this.address = NTT_DOCOMO;
                    }
                } else if (DOCOMO.equalsIgnoreCase(this.address)) {
                    if (!DOCOMO.equals(this.address)) {
                        this.address = NTT_DOCOMO;
                    }
                } else if (DOCOMO.equalsIgnoreCase(this.partofaddress) && !DOCOMO.equals(this.partofaddress)) {
                    this.address = NTT_DOCOMO;
                }
            }
        }
    }

    @Override // com.android.internal.telephony.SmsAddress
    public String getAddressString() {
        return this.address;
    }

    @Override // com.android.internal.telephony.SmsAddress
    public boolean isAlphanumeric() {
        return this.ton == 5;
    }

    @Override // com.android.internal.telephony.SmsAddress
    public boolean isNetworkSpecific() {
        return this.ton == 3;
    }

    public boolean isCphsVoiceMessageIndicatorAddress() {
        return (this.origBytes[0] & 255) == 4 && isAlphanumeric() && (this.origBytes[1] & 15) == 0;
    }

    public boolean isCphsVoiceMessageSet() {
        return isCphsVoiceMessageIndicatorAddress() && ((this.origBytes[2] & 255) == 17 || (this.origBytes[2] & 255) == 145);
    }

    public boolean isCphsVoiceMessageClear() {
        return isCphsVoiceMessageIndicatorAddress() && ((this.origBytes[2] & 255) == 16 || (this.origBytes[2] & 255) == 144);
    }
}
