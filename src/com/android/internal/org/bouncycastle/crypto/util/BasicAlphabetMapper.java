package com.android.internal.org.bouncycastle.crypto.util;

import com.android.internal.org.bouncycastle.crypto.AlphabetMapper;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class BasicAlphabetMapper implements AlphabetMapper {
    private Map<Integer, Character> charMap;
    private Map<Character, Integer> indexMap;

    public BasicAlphabetMapper(String str) {
        this(str.toCharArray());
    }

    public BasicAlphabetMapper(char[] cArr) {
        this.indexMap = new HashMap();
        this.charMap = new HashMap();
        for (int i = 0; i != cArr.length; i++) {
            if (this.indexMap.containsKey(Character.valueOf(cArr[i]))) {
                throw new IllegalArgumentException("duplicate key detected in alphabet: " + cArr[i]);
            }
            this.indexMap.put(Character.valueOf(cArr[i]), Integer.valueOf(i));
            this.charMap.put(Integer.valueOf(i), Character.valueOf(cArr[i]));
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AlphabetMapper
    public int getRadix() {
        return this.indexMap.size();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AlphabetMapper
    public byte[] convertToIndexes(char[] cArr) {
        int i = 0;
        if (this.indexMap.size() <= 256) {
            byte[] bArr = new byte[cArr.length];
            while (i != cArr.length) {
                bArr[i] = this.indexMap.get(Character.valueOf(cArr[i])).byteValue();
                i++;
            }
            return bArr;
        }
        byte[] bArr2 = new byte[cArr.length * 2];
        while (i != cArr.length) {
            int intValue = this.indexMap.get(Character.valueOf(cArr[i])).intValue();
            int i2 = i * 2;
            bArr2[i2] = (byte) ((intValue >> 8) & 255);
            bArr2[i2 + 1] = (byte) (intValue & 255);
            i++;
        }
        return bArr2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AlphabetMapper
    public char[] convertToChars(byte[] bArr) {
        int i = 0;
        if (this.charMap.size() <= 256) {
            char[] cArr = new char[bArr.length];
            while (i != bArr.length) {
                cArr[i] = this.charMap.get(Integer.valueOf(bArr[i] & 255)).charValue();
                i++;
            }
            return cArr;
        }
        if ((bArr.length & 1) != 0) {
            throw new IllegalArgumentException("two byte radix and input string odd length");
        }
        char[] cArr2 = new char[bArr.length / 2];
        while (i != bArr.length) {
            cArr2[i / 2] = this.charMap.get(Integer.valueOf(((bArr[i] << 8) & 65280) | (bArr[i + 1] & 255))).charValue();
            i += 2;
        }
        return cArr2;
    }
}
