package com.samsung.android.wifi;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes6.dex */
public final class SemWifiSsid implements Parcelable {
    public static final Parcelable.Creator<SemWifiSsid> CREATOR = new Parcelable.Creator<SemWifiSsid>() { // from class: com.samsung.android.wifi.SemWifiSsid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiSsid createFromParcel(Parcel parcel) {
            SemWifiSsid semWifiSsid = new SemWifiSsid();
            int readInt = parcel.readInt();
            byte[] bArr = new byte[readInt];
            parcel.readByteArray(bArr);
            semWifiSsid.octets.write(bArr, 0, readInt);
            return semWifiSsid;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemWifiSsid[] newArray(int i) {
            return new SemWifiSsid[i];
        }
    };
    private static final int HEX_RADIX = 16;
    public static final String NONE = "<unknown ssid>";
    private static final String TAG = "SemWifiSsid";
    public final ByteArrayOutputStream octets;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SemWifiSsid() {
        this.octets = new ByteArrayOutputStream(32);
    }

    public static SemWifiSsid createFromByteArray(byte[] bArr) {
        SemWifiSsid semWifiSsid = new SemWifiSsid();
        if (bArr != null) {
            semWifiSsid.octets.write(bArr, 0, bArr.length);
        }
        return semWifiSsid;
    }

    public static SemWifiSsid createFromAsciiEncoded(String str) {
        SemWifiSsid semWifiSsid = new SemWifiSsid();
        semWifiSsid.convertToBytes(str);
        return semWifiSsid;
    }

    public static SemWifiSsid createFromHex(String str) {
        int i;
        SemWifiSsid semWifiSsid = new SemWifiSsid();
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
                semWifiSsid.octets.write(i);
                i2 = i3;
            }
        }
        return semWifiSsid;
    }

    private void convertToBytes(String str) {
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                int i2 = i + 1;
                char charAt2 = str.charAt(i2);
                if (charAt2 == '\"') {
                    this.octets.write(34);
                } else if (charAt2 == '\\') {
                    this.octets.write(92);
                } else if (charAt2 == 'e') {
                    this.octets.write(27);
                } else if (charAt2 == 'n') {
                    this.octets.write(10);
                } else if (charAt2 == 'r') {
                    this.octets.write(13);
                } else if (charAt2 == 't') {
                    this.octets.write(9);
                } else if (charAt2 == 'x') {
                    i2 = i + 2;
                    int i3 = i + 4;
                    int i4 = -1;
                    try {
                        i4 = Integer.parseInt(str.substring(i2, i3), 16);
                    } catch (NumberFormatException | StringIndexOutOfBoundsException unused) {
                    }
                    if (i4 < 0) {
                        int digit = Character.digit(str.charAt(i2), 16);
                        if (digit < 0) {
                            i = i2;
                        } else {
                            this.octets.write(digit);
                            i += 3;
                        }
                    } else {
                        this.octets.write(i4);
                        i = i3;
                    }
                } else {
                    switch (charAt2) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                            int charAt3 = str.charAt(i2) - '0';
                            int i5 = i + 2;
                            if (str.charAt(i5) >= '0' && str.charAt(i5) <= '7') {
                                charAt3 = ((charAt3 * 8) + str.charAt(i5)) - 48;
                                i5 = i + 3;
                            }
                            if (str.charAt(i5) >= '0' && str.charAt(i5) <= '7') {
                                charAt3 = ((charAt3 * 8) + str.charAt(i5)) - 48;
                                i5++;
                            }
                            this.octets.write(charAt3);
                            i = i5;
                            continue;
                    }
                    i = i2;
                }
                i += 2;
            } else {
                this.octets.write(charAt);
                i++;
            }
        }
    }

    public String toString() {
        byte[] byteArray = this.octets.toByteArray();
        if (this.octets.size() <= 0 || isArrayAllZeroes(byteArray)) {
            return "";
        }
        CharsetDecoder onUnmappableCharacter = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        CharBuffer allocate = CharBuffer.allocate(32);
        CoderResult decode = onUnmappableCharacter.decode(ByteBuffer.wrap(byteArray), allocate, true);
        allocate.flip();
        if (decode.isError()) {
            return "<unknown ssid>";
        }
        return allocate.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SemWifiSsid) {
            return Arrays.equals(this.octets.toByteArray(), ((SemWifiSsid) obj).octets.toByteArray());
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
        StringBuilder sb = new StringBuilder("0x");
        byte[] octets = getOctets();
        for (int i = 0; i < this.octets.size(); i++) {
            sb.append(String.format(Locale.US, "%02x", Byte.valueOf(octets[i])));
        }
        if (this.octets.size() > 0) {
            return sb.toString();
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.octets.size());
        parcel.writeByteArray(this.octets.toByteArray());
    }
}
