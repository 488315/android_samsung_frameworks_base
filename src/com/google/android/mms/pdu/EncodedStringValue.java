package com.google.android.mms.pdu;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class EncodedStringValue implements Cloneable {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "EncodedStringValue";
    private int mCharacterSet;
    private byte[] mData;

    public EncodedStringValue(int i, byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("EncodedStringValue: Text-string is null.");
        }
        this.mCharacterSet = i;
        byte[] bArr2 = new byte[bArr.length];
        this.mData = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }

    public EncodedStringValue(byte[] bArr) {
        this(106, bArr);
    }

    public EncodedStringValue(String str) {
        try {
            this.mData = str.getBytes("utf-8");
            this.mCharacterSet = 106;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Default encoding must be supported.", e);
        }
    }

    public int getCharacterSet() {
        return this.mCharacterSet;
    }

    public void setCharacterSet(int i) {
        this.mCharacterSet = i;
    }

    public byte[] getTextString() {
        byte[] bArr = this.mData;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public void setTextString(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("EncodedStringValue: Text-string is null.");
        }
        byte[] bArr2 = new byte[bArr.length];
        this.mData = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }

    public String getString() {
        int i = this.mCharacterSet;
        if (i == 0) {
            return new String(this.mData);
        }
        try {
            try {
                return new String(this.mData, CharacterSets.getMimeName(i));
            } catch (UnsupportedEncodingException unused) {
                return new String(this.mData);
            }
        } catch (UnsupportedEncodingException unused2) {
            return new String(this.mData, CharacterSets.MIMENAME_ISO_8859_1);
        }
    }

    public void appendTextString(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("Text-string is null.");
        }
        if (this.mData == null) {
            byte[] bArr2 = new byte[bArr.length];
            this.mData = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.mData);
            byteArrayOutputStream.write(bArr);
            this.mData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException("appendTextString: failed when write a new Text-string");
        }
    }

    public Object clone() throws CloneNotSupportedException {
        byte[] bArr = this.mData;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        try {
            return new EncodedStringValue(this.mCharacterSet, bArr2);
        } catch (Exception e) {
            Log.e(TAG, "failed to clone an EncodedStringValue: " + this);
            e.printStackTrace();
            throw new CloneNotSupportedException(e.getMessage());
        }
    }

    public EncodedStringValue[] split(String str) {
        String[] strArrSplit = getString().split(str);
        int length = strArrSplit.length;
        EncodedStringValue[] encodedStringValueArr = new EncodedStringValue[length];
        for (int i = 0; i < length; i++) {
            try {
                encodedStringValueArr[i] = new EncodedStringValue(this.mCharacterSet, strArrSplit[i].getBytes());
            } catch (NullPointerException unused) {
                return null;
            }
        }
        return encodedStringValueArr;
    }

    public static EncodedStringValue[] extract(String str) {
        String[] strArrSplit = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < strArrSplit.length; i++) {
            if (strArrSplit[i].length() > 0) {
                arrayList.add(new EncodedStringValue(strArrSplit[i]));
            }
        }
        int size = arrayList.size();
        if (size > 0) {
            return (EncodedStringValue[]) arrayList.toArray(new EncodedStringValue[size]);
        }
        return null;
    }

    public static String concat(EncodedStringValue[] encodedStringValueArr) {
        StringBuilder sb = new StringBuilder();
        int length = encodedStringValueArr.length - 1;
        for (int i = 0; i <= length; i++) {
            sb.append(encodedStringValueArr[i].getString());
            if (i < length) {
                sb.append(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public static EncodedStringValue copy(EncodedStringValue encodedStringValue) {
        if (encodedStringValue == null) {
            return null;
        }
        return new EncodedStringValue(encodedStringValue.mCharacterSet, encodedStringValue.mData);
    }

    public static EncodedStringValue[] encodeStrings(String[] strArr) {
        int length = strArr.length;
        if (length <= 0) {
            return null;
        }
        EncodedStringValue[] encodedStringValueArr = new EncodedStringValue[length];
        for (int i = 0; i < length; i++) {
            encodedStringValueArr[i] = new EncodedStringValue(strArr[i]);
        }
        return encodedStringValueArr;
    }

    public EncodedStringValue(int i, String str) {
        try {
            this.mData = str.getBytes("utf-8");
            this.mCharacterSet = i;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Default encoding must be supported.", e);
        }
    }
}
