package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ECIEncoderSet {
    public static final List ENCODERS = new ArrayList();
    public final CharsetEncoder[] encoders;
    public final int priorityEncoderIndex;

    static {
        String[] strArr = {"IBM437", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-11", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "windows-1250", "windows-1251", "windows-1252", "windows-1256", "Shift_JIS"};
        for (int i = 0; i < 20; i++) {
            String str = strArr[i];
            if (((CharacterSetECI) ((HashMap) CharacterSetECI.NAME_TO_ECI).get(str)) != null) {
                try {
                    ENCODERS.add(Charset.forName(str).newEncoder());
                } catch (UnsupportedCharsetException unused) {
                }
            }
        }
    }

    public ECIEncoderSet(String str, Charset charset, int i) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        arrayList.add(StandardCharsets.ISO_8859_1.newEncoder());
        int i2 = 0;
        boolean z2 = charset != null && charset.name().startsWith("UTF");
        for (int i3 = 0; i3 < str.length(); i3++) {
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList.get(i4);
                i4++;
                CharsetEncoder charsetEncoder = (CharsetEncoder) obj;
                char charAt = str.charAt(i3);
                if (charAt == i || charsetEncoder.canEncode(charAt)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (!z) {
                ArrayList arrayList2 = (ArrayList) ENCODERS;
                int size2 = arrayList2.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        break;
                    }
                    Object obj2 = arrayList2.get(i5);
                    i5++;
                    CharsetEncoder charsetEncoder2 = (CharsetEncoder) obj2;
                    if (charsetEncoder2.canEncode(str.charAt(i3))) {
                        arrayList.add(charsetEncoder2);
                        z = true;
                        break;
                    }
                }
            }
            if (!z) {
                z2 = true;
            }
        }
        if (arrayList.size() != 1 || z2) {
            this.encoders = new CharsetEncoder[arrayList.size() + 2];
            int size3 = arrayList.size();
            int i6 = 0;
            int i7 = 0;
            while (i7 < size3) {
                Object obj3 = arrayList.get(i7);
                i7++;
                this.encoders[i6] = (CharsetEncoder) obj3;
                i6++;
            }
            this.encoders[i6] = StandardCharsets.UTF_8.newEncoder();
            this.encoders[i6 + 1] = StandardCharsets.UTF_16BE.newEncoder();
        } else {
            this.encoders = new CharsetEncoder[]{(CharsetEncoder) arrayList.get(0)};
        }
        if (charset != null) {
            while (true) {
                CharsetEncoder[] charsetEncoderArr = this.encoders;
                if (i2 >= charsetEncoderArr.length) {
                    break;
                }
                if (charsetEncoderArr[i2] != null && charset.name().equals(this.encoders[i2].charset().name())) {
                    break;
                } else {
                    i2++;
                }
            }
            this.priorityEncoderIndex = i2;
        }
        i2 = -1;
        this.priorityEncoderIndex = i2;
    }

    public final boolean canEncode(char c, int i) {
        return this.encoders[i].canEncode("" + c);
    }
}
