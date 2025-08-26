package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.mms.pdu.CharacterSets;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemMobileWipsWifiSsid implements Parcelable {
    private static final String CHARSET_ALL = "EUC-KR";
    private static final String CHARSET_CN = "gbk";
    private static final String CHARSET_KOR = "ksc5601";
    public static final Parcelable.Creator<SemMobileWipsWifiSsid> CREATOR = new Parcelable.Creator<SemMobileWipsWifiSsid>() { // from class: com.samsung.android.wifi.SemMobileWipsWifiSsid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsWifiSsid createFromParcel(Parcel parcel) {
            SemMobileWipsWifiSsid semMobileWipsWifiSsid = new SemMobileWipsWifiSsid();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            semMobileWipsWifiSsid.octets.write(bArrCreateByteArray, 0, bArrCreateByteArray.length);
            return semMobileWipsWifiSsid;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemMobileWipsWifiSsid[] newArray(int i) {
            return new SemMobileWipsWifiSsid[i];
        }
    };
    private static final int HEX_RADIX = 16;
    public static final String NONE = "<unknown ssid>";
    private static final String TAG = "SemMobileWipsWifiSsid";
    private final String CONFIG_CHARSET;
    public final ByteArrayOutputStream octets;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SemMobileWipsWifiSsid() {
        this.octets = new ByteArrayOutputStream(32);
        this.CONFIG_CHARSET = getCharacterSet();
    }

    public static SemMobileWipsWifiSsid createFromByteArray(byte[] bArr) {
        SemMobileWipsWifiSsid semMobileWipsWifiSsid = new SemMobileWipsWifiSsid();
        if (bArr != null) {
            semMobileWipsWifiSsid.octets.write(bArr, 0, bArr.length);
        }
        return semMobileWipsWifiSsid;
    }

    public static SemMobileWipsWifiSsid createFromAsciiEncoded(String str) throws NumberFormatException {
        SemMobileWipsWifiSsid semMobileWipsWifiSsid = new SemMobileWipsWifiSsid();
        semMobileWipsWifiSsid.convertToBytes(str);
        return semMobileWipsWifiSsid;
    }

    public static SemMobileWipsWifiSsid createFromHex(String str) throws NumberFormatException {
        int i;
        SemMobileWipsWifiSsid semMobileWipsWifiSsid = new SemMobileWipsWifiSsid();
        if (str != null) {
            if (str.startsWith("0x") || str.startsWith("0X")) {
                str = str.substring(2);
            }
            int i2 = 0;
            while (i2 < str.length() - 1) {
                int i3 = i2 + 2;
                try {
                    i = Integer.parseInt(str.substring(i2, i3), 16);
                } catch (NumberFormatException unused) {
                    i = 0;
                }
                semMobileWipsWifiSsid.octets.write(i);
                i2 = i3;
            }
        }
        return semMobileWipsWifiSsid;
    }

    private String getCharacterSet() {
        String string = Locale.getDefault().toString();
        return (string == null || !string.startsWith("zh")) ? "EUC-KR" : CharacterSets.MIMENAME_GBK;
    }

    static boolean isUTF8String(byte[] bArr, long j) {
        int i = 0;
        boolean z = true;
        for (int i2 = 0; i2 < j; i2++) {
            char c = (char) (bArr[i2] & 255);
            if ((c & 128) != 0) {
                z = false;
            }
            if (i == 0) {
                if (c < 128) {
                    continue;
                } else if (c >= 252 && c <= 253) {
                    i = 6;
                } else if (c >= 248) {
                    i = 5;
                } else if (c >= 240) {
                    i = 4;
                } else if (c >= 224) {
                    i = 3;
                } else {
                    if (c < 192) {
                        return false;
                    }
                    i = 2;
                }
            } else if ((c & 192) != 128) {
                return false;
            }
            i--;
        }
        return i <= 0 && !z;
    }

    static boolean isUCNVString(byte[] bArr, int i) {
        int i2;
        boolean z = true;
        int i3 = 0;
        while (i3 < i) {
            char c = (char) (bArr[i3] & 255);
            if (c >= 129 && c < 255 && (i2 = i3 + 1) < i) {
                char c2 = (char) (bArr[i2] & 255);
                if (c2 < '@' || c2 >= 255 || c2 == 127) {
                    return false;
                }
                z = false;
                i3 = i2;
            } else if (c >= 128) {
                return false;
            }
            i3++;
        }
        return !z;
    }

    private void convertToBytes(String str) throws NumberFormatException {
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\\') {
                int i2 = i + 1;
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 == '\"') {
                    this.octets.write(34);
                } else if (cCharAt2 == '\\') {
                    this.octets.write(92);
                } else if (cCharAt2 == 'e') {
                    this.octets.write(27);
                } else if (cCharAt2 == 'n') {
                    this.octets.write(10);
                } else if (cCharAt2 == 'r') {
                    this.octets.write(13);
                } else if (cCharAt2 == 't') {
                    this.octets.write(9);
                } else if (cCharAt2 == 'x') {
                    i2 = i + 2;
                    int i3 = i + 4;
                    int i4 = -1;
                    try {
                        i4 = Integer.parseInt(str.substring(i2, i3), 16);
                    } catch (NumberFormatException | StringIndexOutOfBoundsException unused) {
                    }
                    if (i4 < 0) {
                        int iDigit = Character.digit(str.charAt(i2), 16);
                        if (iDigit < 0) {
                            i = i2;
                        } else {
                            this.octets.write(iDigit);
                            i += 3;
                        }
                    } else {
                        this.octets.write(i4);
                        i = i3;
                    }
                } else {
                    switch (cCharAt2) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                            int iCharAt = str.charAt(i2) - '0';
                            int i5 = i + 2;
                            if (str.charAt(i5) >= '0' && str.charAt(i5) <= '7') {
                                iCharAt = ((iCharAt * 8) + str.charAt(i5)) - 48;
                                i5 = i + 3;
                            }
                            if (str.charAt(i5) >= '0' && str.charAt(i5) <= '7') {
                                iCharAt = ((iCharAt * 8) + str.charAt(i5)) - 48;
                                i5++;
                            }
                            this.octets.write(iCharAt);
                            i = i5;
                            continue;
                    }
                    i = i2;
                }
                i += 2;
            } else {
                this.octets.write(cCharAt);
                i++;
            }
        }
    }

    public String toString() {
        byte[] byteArray = this.octets.toByteArray();
        if (this.octets.size() <= 0 || isArrayAllZeroes(byteArray)) {
            return "";
        }
        CharsetDecoder charsetDecoderOnUnmappableCharacter = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        CharBuffer charBufferAllocate = CharBuffer.allocate(32);
        CoderResult coderResultDecode = charsetDecoderOnUnmappableCharacter.decode(ByteBuffer.wrap(byteArray), charBufferAllocate, true);
        charBufferAllocate.flip();
        if (coderResultDecode.isError()) {
            return "<unknown ssid>";
        }
        String string = charBufferAllocate.toString();
        int size = this.octets.size();
        if (CHARSET_CN.equals(this.CONFIG_CHARSET) || CHARSET_KOR.equals(this.CONFIG_CHARSET) || "EUC-KR".equals(this.CONFIG_CHARSET)) {
            if (!isUTF8String(byteArray, size) && isUCNVString(byteArray, size)) {
                try {
                    if (CHARSET_CN.equals(this.CONFIG_CHARSET)) {
                        return new String(byteArray, CHARSET_CN);
                    }
                    if (CHARSET_KOR.equals(this.CONFIG_CHARSET)) {
                        return new String(byteArray, CHARSET_KOR);
                    }
                    return new String(byteArray, "EUC-KR");
                } catch (Exception unused) {
                    return string;
                }
            }
            return charBufferAllocate.toString();
        }
        return charBufferAllocate.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SemMobileWipsWifiSsid) {
            return Arrays.equals(this.octets.toByteArray(), ((SemMobileWipsWifiSsid) obj).octets.toByteArray());
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.octets.toByteArray());
    }

    private boolean isArrayAllZeroes(byte[] bArr) {
        for (byte b : bArr) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isHidden() {
        return isArrayAllZeroes(this.octets.toByteArray());
    }

    public byte[] getOctets() {
        return this.octets.toByteArray();
    }

    public String getHexString() {
        byte[] octets = getOctets();
        String str = "0x";
        for (int i = 0; i < this.octets.size(); i++) {
            str = str + String.format(Locale.US, "%02x", Byte.valueOf(octets[i]));
        }
        if (this.octets.size() > 0) {
            return str;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeByteArray(this.octets.toByteArray());
        }
    }
}
