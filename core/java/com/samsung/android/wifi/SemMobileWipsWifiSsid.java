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

public class SemMobileWipsWifiSsid implements Parcelable {
    private static final String CHARSET_ALL = "EUC-KR";
    private static final String CHARSET_CN = "gbk";
    private static final String CHARSET_KOR = "ksc5601";
    public static final Parcelable.Creator<SemMobileWipsWifiSsid> CREATOR =
            new Parcelable.Creator<
                    SemMobileWipsWifiSsid>() { // from class:
                                               // com.samsung.android.wifi.SemMobileWipsWifiSsid.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemMobileWipsWifiSsid createFromParcel(Parcel in) {
                    SemMobileWipsWifiSsid ssid = new SemMobileWipsWifiSsid();
                    byte[] b = in.createByteArray();
                    ssid.octets.write(b, 0, b.length);
                    return ssid;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemMobileWipsWifiSsid[] newArray(int size) {
                    return new SemMobileWipsWifiSsid[size];
                }
            };
    private static final int HEX_RADIX = 16;
    public static final String NONE = "<unknown ssid>";
    private static final String TAG = "SemMobileWipsWifiSsid";
    private final String CONFIG_CHARSET;
    public final ByteArrayOutputStream octets;

    private SemMobileWipsWifiSsid() {
        this.octets = new ByteArrayOutputStream(32);
        this.CONFIG_CHARSET = getCharacterSet();
    }

    public static SemMobileWipsWifiSsid createFromByteArray(byte[] ssid) {
        SemMobileWipsWifiSsid mobileWipsWifiSsid = new SemMobileWipsWifiSsid();
        if (ssid != null) {
            mobileWipsWifiSsid.octets.write(ssid, 0, ssid.length);
        }
        return mobileWipsWifiSsid;
    }

    public static SemMobileWipsWifiSsid createFromAsciiEncoded(String asciiEncoded) {
        SemMobileWipsWifiSsid a = new SemMobileWipsWifiSsid();
        a.convertToBytes(asciiEncoded);
        return a;
    }

    public static SemMobileWipsWifiSsid createFromHex(String hexStr) {
        int val;
        SemMobileWipsWifiSsid a = new SemMobileWipsWifiSsid();
        if (hexStr == null) {
            return a;
        }
        if (hexStr.startsWith("0x") || hexStr.startsWith("0X")) {
            hexStr = hexStr.substring(2);
        }
        for (int i = 0; i < hexStr.length() - 1; i += 2) {
            try {
                val = Integer.parseInt(hexStr.substring(i, i + 2), 16);
            } catch (NumberFormatException e) {
                val = 0;
            }
            a.octets.write(val);
        }
        return a;
    }

    private String getCharacterSet() {
        String localeString = Locale.getDefault().toString();
        return (localeString == null || !localeString.startsWith("zh"))
                ? "EUC-KR"
                : CharacterSets.MIMENAME_GBK;
    }

    static boolean isUTF8String(byte[] str, long length) {
        int nBytes;
        int nBytes2 = 0;
        boolean bAllAscii = true;
        for (int i = 0; i < length; i++) {
            char chr = (char) (str[i] & 255);
            if ((chr & 128) != 0) {
                bAllAscii = false;
            }
            if (nBytes2 != 0) {
                if ((chr & 192) != 128) {
                    return false;
                }
                nBytes2--;
            } else if (chr < 128) {
                continue;
            } else {
                if (chr >= 252 && chr <= 253) {
                    nBytes = 6;
                } else if (chr >= 248) {
                    nBytes = 5;
                } else if (chr >= 240) {
                    nBytes = 4;
                } else if (chr >= 224) {
                    nBytes = 3;
                } else {
                    if (chr < 192) {
                        return false;
                    }
                    nBytes = 2;
                }
                nBytes2 = nBytes - 1;
            }
        }
        return nBytes2 <= 0 && !bAllAscii;
    }

    static boolean isUCNVString(byte[] str, int length) {
        boolean isAllASCII = true;
        int i = 0;
        while (i < length) {
            char byte1 = (char) (str[i] & 255);
            if (byte1 >= 129 && byte1 < 255 && i + 1 < length) {
                char byte2 = (char) (str[i + 1] & 255);
                if (byte2 < '@' || byte2 >= 255 || byte2 == 127) {
                    return false;
                }
                isAllASCII = false;
                i++;
            } else if (byte1 >= 128) {
                return false;
            }
            i++;
        }
        return !isAllASCII;
    }

    private void convertToBytes(String asciiEncoded) {
        int val;
        int i = 0;
        while (i < asciiEncoded.length()) {
            char c = asciiEncoded.charAt(i);
            switch (c) {
                case '\\':
                    i++;
                    switch (asciiEncoded.charAt(i)) {
                        case '\"':
                            this.octets.write(34);
                            i++;
                            break;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                            int val2 = asciiEncoded.charAt(i) - '0';
                            i++;
                            if (asciiEncoded.charAt(i) >= '0' && asciiEncoded.charAt(i) <= '7') {
                                val2 = ((val2 * 8) + asciiEncoded.charAt(i)) - 48;
                                i++;
                            }
                            if (asciiEncoded.charAt(i) >= '0' && asciiEncoded.charAt(i) <= '7') {
                                val2 = ((val2 * 8) + asciiEncoded.charAt(i)) - 48;
                                i++;
                            }
                            this.octets.write(val2);
                            break;
                        case '\\':
                            this.octets.write(92);
                            i++;
                            break;
                        case 'e':
                            this.octets.write(27);
                            i++;
                            break;
                        case 'n':
                            this.octets.write(10);
                            i++;
                            break;
                        case 'r':
                            this.octets.write(13);
                            i++;
                            break;
                        case 't':
                            this.octets.write(9);
                            i++;
                            break;
                        case 'x':
                            i++;
                            try {
                                val = Integer.parseInt(asciiEncoded.substring(i, i + 2), 16);
                            } catch (NumberFormatException e) {
                                val = -1;
                            } catch (StringIndexOutOfBoundsException e2) {
                                val = -1;
                            }
                            if (val < 0) {
                                int val3 = Character.digit(asciiEncoded.charAt(i), 16);
                                if (val3 >= 0) {
                                    this.octets.write(val3);
                                    i++;
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                this.octets.write(val);
                                i += 2;
                                break;
                            }
                    }
                default:
                    this.octets.write(c);
                    i++;
                    break;
            }
        }
    }

    public String toString() {
        String ucnvSsid;
        byte[] ssidBytes = this.octets.toByteArray();
        if (this.octets.size() <= 0 || isArrayAllZeroes(ssidBytes)) {
            return "";
        }
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder =
                charset.newDecoder()
                        .onMalformedInput(CodingErrorAction.REPLACE)
                        .onUnmappableCharacter(CodingErrorAction.REPLACE);
        CharBuffer out = CharBuffer.allocate(32);
        CoderResult result = decoder.decode(ByteBuffer.wrap(ssidBytes), out, true);
        out.flip();
        if (result.isError()) {
            return "<unknown ssid>";
        }
        String decodedSsid = out.toString();
        int length = this.octets.size();
        if (CHARSET_CN.equals(this.CONFIG_CHARSET)
                || CHARSET_KOR.equals(this.CONFIG_CHARSET)
                || "EUC-KR".equals(this.CONFIG_CHARSET)) {
            if (!isUTF8String(ssidBytes, length) && isUCNVString(ssidBytes, length)) {
                try {
                    if (CHARSET_CN.equals(this.CONFIG_CHARSET)) {
                        ucnvSsid = new String(ssidBytes, CHARSET_CN);
                    } else if (CHARSET_KOR.equals(this.CONFIG_CHARSET)) {
                        ucnvSsid = new String(ssidBytes, CHARSET_KOR);
                    } else {
                        ucnvSsid = new String(ssidBytes, "EUC-KR");
                    }
                    String decodedSsid2 = ucnvSsid;
                    return decodedSsid2;
                } catch (Exception e) {
                    return decodedSsid;
                }
            }
            return out.toString();
        }
        return out.toString();
    }

    public boolean equals(Object thatObject) {
        if (this == thatObject) {
            return true;
        }
        if (!(thatObject instanceof SemMobileWipsWifiSsid)) {
            return false;
        }
        SemMobileWipsWifiSsid that = (SemMobileWipsWifiSsid) thatObject;
        return Arrays.equals(this.octets.toByteArray(), that.octets.toByteArray());
    }

    public int hashCode() {
        return Arrays.hashCode(this.octets.toByteArray());
    }

    private boolean isArrayAllZeroes(byte[] ssidBytes) {
        for (byte b : ssidBytes) {
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
        String out = "0x";
        byte[] ssidbytes = getOctets();
        for (int i = 0; i < this.octets.size(); i++) {
            out = out + String.format(Locale.US, "%02x", Byte.valueOf(ssidbytes[i]));
        }
        if (this.octets.size() > 0) {
            return out;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (dest != null) {
            dest.writeByteArray(this.octets.toByteArray());
        }
    }
}
