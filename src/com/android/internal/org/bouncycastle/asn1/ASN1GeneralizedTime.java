package com.android.internal.org.bouncycastle.asn1;

import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/* loaded from: classes5.dex */
public class ASN1GeneralizedTime extends ASN1Primitive {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1GeneralizedTime.class, 24) { // from class: com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime.1
        @Override // com.android.internal.org.bouncycastle.asn1.ASN1UniversalType
        ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1GeneralizedTime.createPrimitive(dEROctetString.getOctets());
        }
    };
    final byte[] contents;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final boolean encodeConstructed() {
        return false;
    }

    public static ASN1GeneralizedTime getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1GeneralizedTime)) {
            return (ASN1GeneralizedTime) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1GeneralizedTime) {
                return (ASN1GeneralizedTime) aSN1Primitive;
            }
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1GeneralizedTime) TYPE.fromByteArray((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1GeneralizedTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return (ASN1GeneralizedTime) TYPE.getContextInstance(aSN1TaggedObject, z);
    }

    public ASN1GeneralizedTime(String str) {
        this.contents = Strings.toByteArray(str);
        try {
            getDate();
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1GeneralizedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z));
        this.contents = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.US);
        simpleDateFormat.setCalendar(Calendar.getInstance(Locale.US));
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z));
        this.contents = Strings.toByteArray(simpleDateFormat.format(date));
    }

    ASN1GeneralizedTime(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("GeneralizedTime string too short");
        }
        this.contents = bArr;
        if (!isDigit(0) || !isDigit(1) || !isDigit(2) || !isDigit(3)) {
            throw new IllegalArgumentException("illegal characters in GeneralizedTime string");
        }
    }

    public String getTimeString() {
        return Strings.fromByteArray(this.contents);
    }

    public String getTime() {
        String fromByteArray = Strings.fromByteArray(this.contents);
        if (fromByteArray.charAt(fromByteArray.length() - 1) == 'Z') {
            return fromByteArray.substring(0, fromByteArray.length() - 1) + "GMT+00:00";
        }
        int length = fromByteArray.length();
        char charAt = fromByteArray.charAt(length - 6);
        if ((charAt == '-' || charAt == '+') && fromByteArray.indexOf("GMT") == length - 9) {
            return fromByteArray;
        }
        int length2 = fromByteArray.length();
        int i = length2 - 5;
        char charAt2 = fromByteArray.charAt(i);
        if (charAt2 == '-' || charAt2 == '+') {
            StringBuilder sb = new StringBuilder();
            sb.append(fromByteArray.substring(0, i));
            sb.append("GMT");
            int i2 = length2 - 2;
            sb.append(fromByteArray.substring(i, i2));
            sb.append(":");
            sb.append(fromByteArray.substring(i2));
            return sb.toString();
        }
        int length3 = fromByteArray.length() - 3;
        char charAt3 = fromByteArray.charAt(length3);
        if (charAt3 == '-' || charAt3 == '+') {
            return fromByteArray.substring(0, length3) + "GMT" + fromByteArray.substring(length3) + ":00";
        }
        return fromByteArray + calculateGMTOffset(fromByteArray);
    }

    private String calculateGMTOffset(String str) {
        String str2;
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset >= 0) {
            str2 = "+";
        } else {
            rawOffset = -rawOffset;
            str2 = NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        int i = rawOffset / Build.VERSION_CODES_FULL.BAKLAVA;
        int i2 = (rawOffset - (Build.VERSION_CODES_FULL.BAKLAVA * i)) / 60000;
        try {
            if (timeZone.useDaylightTime()) {
                if (hasFractionalSeconds()) {
                    str = pruneFractionalSeconds(str);
                }
                if (timeZone.inDaylightTime(calculateGMTDateFormat().parse(str + "GMT" + str2 + convert(i) + ":" + convert(i2)))) {
                    i += str2.equals("+") ? 1 : -1;
                }
            }
        } catch (ParseException unused) {
        }
        return "GMT" + str2 + convert(i) + ":" + convert(i2);
    }

    private SimpleDateFormat calculateGMTDateFormat() {
        SimpleDateFormat simpleDateFormat;
        if (hasFractionalSeconds()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");
        } else if (hasSeconds()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz");
        } else if (hasMinutes()) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmz");
        } else {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHz");
        }
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z));
        return simpleDateFormat;
    }

    private String pruneFractionalSeconds(String str) {
        char charAt;
        String substring = str.substring(14);
        int i = 1;
        while (i < substring.length() && '0' <= (charAt = substring.charAt(i)) && charAt <= '9') {
            i++;
        }
        int i2 = i - 1;
        if (i2 > 3) {
            return str.substring(0, 14) + (substring.substring(0, 4) + substring.substring(i));
        }
        if (i2 == 1) {
            return str.substring(0, 14) + (substring.substring(0, i) + "00" + substring.substring(i));
        }
        if (i2 != 2) {
            return str;
        }
        return str.substring(0, 14) + (substring.substring(0, i) + "0" + substring.substring(i));
    }

    private String convert(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return Integer.toString(i);
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat calculateGMTDateFormat;
        SimpleDateFormat simpleDateFormat;
        String fromByteArray = Strings.fromByteArray(this.contents);
        if (fromByteArray.endsWith(GnssSignalType.CODE_TYPE_Z)) {
            if (hasFractionalSeconds()) {
                calculateGMTDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'", LocaleUtil.EN_Locale);
            } else if (hasSeconds()) {
                calculateGMTDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", LocaleUtil.EN_Locale);
            } else if (hasMinutes()) {
                calculateGMTDateFormat = new SimpleDateFormat("yyyyMMddHHmm'Z'", LocaleUtil.EN_Locale);
            } else {
                calculateGMTDateFormat = new SimpleDateFormat("yyyyMMddHH'Z'", LocaleUtil.EN_Locale);
            }
            calculateGMTDateFormat.setTimeZone(new SimpleTimeZone(0, GnssSignalType.CODE_TYPE_Z));
        } else if (fromByteArray.indexOf(45) > 0 || fromByteArray.indexOf(43) > 0) {
            fromByteArray = getTime();
            calculateGMTDateFormat = calculateGMTDateFormat();
        } else {
            if (hasFractionalSeconds()) {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS", Locale.US);
            } else if (hasSeconds()) {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            } else if (hasMinutes()) {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.US);
            } else {
                simpleDateFormat = new SimpleDateFormat("yyyyMMddHH", Locale.US);
            }
            calculateGMTDateFormat = simpleDateFormat;
            calculateGMTDateFormat.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }
        if (hasFractionalSeconds()) {
            fromByteArray = pruneFractionalSeconds(fromByteArray);
        }
        return calculateGMTDateFormat.parse(fromByteArray);
    }

    protected boolean hasFractionalSeconds() {
        int i = 0;
        while (true) {
            byte[] bArr = this.contents;
            if (i == bArr.length) {
                return false;
            }
            if (bArr[i] == 46 && i == 14) {
                return true;
            }
            i++;
        }
    }

    protected boolean hasSeconds() {
        return isDigit(12) && isDigit(13);
    }

    protected boolean hasMinutes() {
        return isDigit(10) && isDigit(11);
    }

    private boolean isDigit(int i) {
        byte b;
        byte[] bArr = this.contents;
        return bArr.length > i && (b = bArr[i]) >= 48 && b <= 57;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength(boolean z) {
        return ASN1OutputStream.getLengthOfEncodingDL(z, this.contents.length);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream aSN1OutputStream, boolean z) throws IOException {
        aSN1OutputStream.writeEncodingDL(z, 24, this.contents);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return new DERGeneralizedTime(this.contents);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1GeneralizedTime) {
            return Arrays.areEqual(this.contents, ((ASN1GeneralizedTime) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    static ASN1GeneralizedTime createPrimitive(byte[] bArr) {
        return new ASN1GeneralizedTime(bArr);
    }
}
