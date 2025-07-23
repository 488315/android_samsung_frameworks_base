package com.android.internal.telephony.gsm;

import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsAddress;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class GsmSmsAddress extends SmsAddress {
    static final String DOCOMO = "DOCOMO";
    static final String DOCOMO_SMS = "DOCOMO SMS";
    private static final String LOG_TAG = "GsmSmsAddress";
    static final String NTT_DOCOMO = "NTT DOCOMO";
    static final int OFFSET_ADDRESS_LENGTH = 0;
    static final int OFFSET_ADDRESS_VALUE = 2;
    static final int OFFSET_TOA = 1;
    String partofaddress;

    public GsmSmsAddress(byte[] bArr, int i, int i2) throws ParseException {
        this.origBytes = new byte[i2];
        System.arraycopy(bArr, i, this.origBytes, 0, i2);
        byte b = this.origBytes[0];
        int i3 = b & 255;
        byte b2 = this.origBytes[1];
        int i4 = b2 & 255;
        this.ton = (i4 >> 4) & 7;
        if ((b2 & 128) != 128) {
            throw new ParseException("Invalid TOA - high bit must be set. toa = " + i4, i + 1);
        }
        if (isAlphanumeric()) {
            this.address = GsmAlphabet.gsm7BitPackedToString(this.origBytes, 2, (i3 * 4) / 7);
            return;
        }
        int i5 = i2 - 1;
        byte b3 = this.origBytes[i5];
        if ((b & 1) == 1) {
            byte[] bArr2 = this.origBytes;
            bArr2[i5] = (byte) (bArr2[i5] | 240);
        }
        this.address = PhoneNumberUtils.calledPartyBCDToString(this.origBytes, 1, i5, 2);
        this.origBytes[i5] = b3;
    }

    public GsmSmsAddress(int i, byte[] bArr, int i2, int i3) throws ParseException {
        this.origBytes = new byte[i3];
        System.arraycopy(bArr, i2, this.origBytes, 0, i3);
        byte b = this.origBytes[0];
        int i4 = b & 255;
        byte b2 = this.origBytes[1];
        int i5 = b2 & 255;
        this.ton = (i5 >> 4) & 7;
        if ((b2 & 128) != 128) {
            throw new ParseException("Invalid TOA - high bit must be set. toa = " + i5, i2 + 1);
        }
        if (isAlphanumeric()) {
            this.address = GsmAlphabet.gsm7BitPackedToString(this.origBytes, 2, (i4 * 4) / 7);
        } else {
            int i6 = i3 - 1;
            byte b3 = this.origBytes[i6];
            if ((b & 1) == 1) {
                byte[] bArr2 = this.origBytes;
                bArr2[i6] = (byte) (bArr2[i6] | 240);
            }
            this.address = PhoneNumberUtils.calledPartyBCDToString(this.origBytes, 1, i6, 2);
            this.origBytes[i6] = b3;
        }
        if (this.address != null) {
            if ((SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName().toUpperCase().contains("SKT") || SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName().toUpperCase().contains("KT_KR") || SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName().toUpperCase().contains("LGU")) && this.address.indexOf("+") != -1) {
                Log.d(LOG_TAG, "Address Before Replacement = " + this.address);
                StringBuffer stringBuffer = new StringBuffer(this.address);
                stringBuffer.deleteCharAt(this.address.indexOf("+"));
                stringBuffer.insert(0, "+");
                this.address = stringBuffer.toString();
                Log.d(LOG_TAG, "Address after Replacement = " + this.address);
            }
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName().toUpperCase().contains(DOCOMO)) {
                if (this.address.length() >= 6) {
                    this.partofaddress = this.address.substring(0, 6);
                } else {
                    this.partofaddress = this.address;
                }
                if (NTT_DOCOMO.equalsIgnoreCase(this.address)) {
                    if (NTT_DOCOMO.equals(this.address)) {
                        return;
                    }
                    this.address = NTT_DOCOMO;
                } else if (DOCOMO_SMS.equalsIgnoreCase(this.address)) {
                    if (DOCOMO_SMS.equals(this.address)) {
                        return;
                    }
                    this.address = NTT_DOCOMO;
                } else if (DOCOMO.equalsIgnoreCase(this.address)) {
                    if (DOCOMO.equals(this.address)) {
                        return;
                    }
                    this.address = NTT_DOCOMO;
                } else {
                    if (!DOCOMO.equalsIgnoreCase(this.partofaddress) || DOCOMO.equals(this.partofaddress)) {
                        return;
                    }
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
        if (isCphsVoiceMessageIndicatorAddress()) {
            return (this.origBytes[2] & 255) == 17 || (this.origBytes[2] & 255) == 145;
        }
        return false;
    }

    public boolean isCphsVoiceMessageClear() {
        if (isCphsVoiceMessageIndicatorAddress()) {
            return (this.origBytes[2] & 255) == 16 || (this.origBytes[2] & 255) == 144;
        }
        return false;
    }
}
