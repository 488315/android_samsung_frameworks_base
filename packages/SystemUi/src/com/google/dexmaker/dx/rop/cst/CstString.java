package com.google.dexmaker.dx.rop.cst;

import com.google.dexmaker.dx.util.ByteArray;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CstString extends TypedConstant {
    public final String string;

    static {
        new CstString("");
    }

    public CstString(String str) {
        if (str == null) {
            throw new NullPointerException("string == null");
        }
        this.string = str.intern();
        int length = str.length();
        byte[] bArr = new byte[length * 3];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt != 0 && charAt < 128) {
                bArr[i] = (byte) charAt;
                i++;
            } else if (charAt < 2048) {
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                bArr[i + 1] = (byte) ((charAt & '?') | 128);
                i += 2;
            } else {
                bArr[i] = (byte) (((charAt >> '\f') & 15) | IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType);
                bArr[i + 1] = (byte) (((charAt >> 6) & 63) | 128);
                bArr[i + 2] = (byte) ((charAt & '?') | 128);
                i += 3;
            }
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        new ByteArray(bArr2);
    }

    public static void throwBadUtf8(int i, int i2) {
        StringBuilder sb = new StringBuilder("bad utf-8 byte ");
        char[] cArr = new char[2];
        for (int i3 = 0; i3 < 2; i3++) {
            cArr[1 - i3] = Character.forDigit(i & 15, 16);
            i >>= 4;
        }
        sb.append(new String(cArr));
        sb.append(" at offset ");
        char[] cArr2 = new char[8];
        for (int i4 = 0; i4 < 8; i4++) {
            cArr2[7 - i4] = Character.forDigit(i2 & 15, 16);
            i2 >>= 4;
        }
        sb.append(new String(cArr2));
        throw new IllegalArgumentException(sb.toString());
    }

    @Override // com.google.dexmaker.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        return this.string.compareTo(((CstString) constant).string);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof CstString) {
            return this.string.equals(((CstString) obj).string);
        }
        return false;
    }

    public final int hashCode() {
        return this.string.hashCode();
    }

    @Override // com.google.dexmaker.dx.util.ToHuman
    public final String toHuman() {
        int length = this.string.length();
        StringBuilder sb = new StringBuilder((length * 3) / 2);
        int i = 0;
        while (i < length) {
            char charAt = this.string.charAt(i);
            if (charAt >= ' ' && charAt < 127) {
                if (charAt == '\'' || charAt == '\"' || charAt == '\\') {
                    sb.append('\\');
                }
                sb.append(charAt);
            } else if (charAt > 127) {
                sb.append("\\u");
                sb.append(Character.forDigit(charAt >> '\f', 16));
                sb.append(Character.forDigit((charAt >> '\b') & 15, 16));
                sb.append(Character.forDigit((charAt >> 4) & 15, 16));
                sb.append(Character.forDigit(charAt & 15, 16));
            } else if (charAt == '\t') {
                sb.append("\\t");
            } else if (charAt == '\n') {
                sb.append("\\n");
            } else if (charAt != '\r') {
                char charAt2 = i < length + (-1) ? this.string.charAt(i + 1) : (char) 0;
                boolean z = charAt2 >= '0' && charAt2 <= '7';
                sb.append('\\');
                for (int i2 = 6; i2 >= 0; i2 -= 3) {
                    char c = (char) (((charAt >> i2) & 7) + 48);
                    if (c != '0' || z) {
                        sb.append(c);
                        z = true;
                    }
                }
                if (!z) {
                    sb.append('0');
                }
            } else {
                sb.append("\\r");
            }
            i++;
        }
        return sb.toString();
    }

    public final String toString() {
        return "string{\"" + toHuman() + "\"}";
    }

    public CstString(ByteArray byteArray) {
        char c;
        if (byteArray != null) {
            int i = byteArray.size;
            char[] cArr = new char[i];
            int i2 = 0;
            int i3 = 0;
            while (i > 0) {
                int unsignedByte = byteArray.getUnsignedByte(i3);
                switch (unsignedByte >> 4) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        i--;
                        if (unsignedByte == 0) {
                            throwBadUtf8(unsignedByte, i3);
                            throw null;
                        }
                        c = (char) unsignedByte;
                        i3++;
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    default:
                        throwBadUtf8(unsignedByte, i3);
                        throw null;
                    case 12:
                    case 13:
                        i -= 2;
                        if (i >= 0) {
                            int i4 = i3 + 1;
                            int unsignedByte2 = byteArray.getUnsignedByte(i4);
                            if ((unsignedByte2 & 192) != 128) {
                                throwBadUtf8(unsignedByte2, i4);
                                throw null;
                            }
                            int i5 = ((unsignedByte & 31) << 6) | (unsignedByte2 & 63);
                            if (i5 != 0 && i5 < 128) {
                                throwBadUtf8(unsignedByte2, i4);
                                throw null;
                            }
                            c = (char) i5;
                            i3 += 2;
                            break;
                        } else {
                            throwBadUtf8(unsignedByte, i3);
                            throw null;
                        }
                    case 14:
                        i -= 3;
                        if (i >= 0) {
                            int i6 = i3 + 1;
                            int unsignedByte3 = byteArray.getUnsignedByte(i6);
                            int i7 = unsignedByte3 & 192;
                            if (i7 == 128) {
                                int i8 = i3 + 2;
                                int unsignedByte4 = byteArray.getUnsignedByte(i8);
                                if (i7 != 128) {
                                    throwBadUtf8(unsignedByte4, i8);
                                    throw null;
                                }
                                int i9 = ((unsignedByte & 15) << 12) | ((unsignedByte3 & 63) << 6) | (unsignedByte4 & 63);
                                if (i9 < 2048) {
                                    throwBadUtf8(unsignedByte4, i8);
                                    throw null;
                                }
                                c = (char) i9;
                                i3 += 3;
                                break;
                            } else {
                                throwBadUtf8(unsignedByte3, i6);
                                throw null;
                            }
                        } else {
                            throwBadUtf8(unsignedByte, i3);
                            throw null;
                        }
                }
                cArr[i2] = c;
                i2++;
            }
            this.string = new String(cArr, 0, i2).intern();
            return;
        }
        throw new NullPointerException("bytes == null");
    }
}
