package com.google.android.mms.pdu;

import android.net.Uri;
import android.speech.RecognizerResultsIntent;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class PduPart {
    public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String P_7BIT = "7bit";
    public static final String P_8BIT = "8bit";
    public static final String P_BASE64 = "base64";
    public static final String P_BINARY = "binary";
    public static final int P_CHARSET = 129;
    public static final int P_COMMENT = 155;
    public static final int P_CONTENT_DISPOSITION = 197;
    public static final int P_CONTENT_ID = 192;
    public static final int P_CONTENT_LOCATION = 142;
    public static final int P_CONTENT_TRANSFER_ENCODING = 200;
    public static final int P_CONTENT_TYPE = 145;
    public static final int P_CREATION_DATE = 147;
    public static final int P_CT_MR_TYPE = 137;
    public static final int P_DEP_COMMENT = 140;
    public static final int P_DEP_CONTENT_DISPOSITION = 174;
    public static final int P_DEP_DOMAIN = 141;
    public static final int P_DEP_FILENAME = 134;
    public static final int P_DEP_NAME = 133;
    public static final int P_DEP_PATH = 143;
    public static final int P_DEP_START = 138;
    public static final int P_DEP_START_INFO = 139;
    public static final int P_DIFFERENCES = 135;
    public static final int P_DISPOSITION_ATTACHMENT = 129;
    public static final int P_DISPOSITION_FROM_DATA = 128;
    public static final int P_DISPOSITION_INLINE = 130;
    public static final int P_DOMAIN = 156;
    public static final int P_FILENAME = 152;
    public static final int P_LEVEL = 130;
    public static final int P_MAC = 146;
    public static final int P_MAX_AGE = 142;
    public static final int P_MODIFICATION_DATE = 148;
    public static final int P_NAME = 151;
    public static final int P_PADDING = 136;
    public static final int P_PATH = 157;
    public static final int P_Q = 128;
    public static final String P_QUOTED_PRINTABLE = "quoted-printable";
    public static final int P_READ_DATE = 149;
    public static final int P_SEC = 145;
    public static final int P_SECURE = 144;
    public static final int P_SIZE = 150;
    public static final int P_START = 153;
    public static final int P_START_INFO = 154;
    public static final int P_TYPE = 131;
    private static final String TAG = "PduPart";
    private Map<Integer, Object> mPartHeader;
    static final byte[] DISPOSITION_FROM_DATA = "from-data".getBytes();
    static final byte[] DISPOSITION_ATTACHMENT = "attachment".getBytes();
    static final byte[] DISPOSITION_INLINE = RecognizerResultsIntent.URI_SCHEME_INLINE.getBytes();
    private Uri mUri = null;
    private byte[] mPartData = null;

    public PduPart() {
        this.mPartHeader = null;
        this.mPartHeader = new HashMap();
    }

    public void setData(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        byte[] bArr2 = new byte[bArr.length];
        this.mPartData = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }

    public byte[] getData() {
        byte[] bArr = this.mPartData;
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public int getDataLength() {
        byte[] bArr = this.mPartData;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public void setDataUri(Uri uri) {
        this.mUri = uri;
    }

    public Uri getDataUri() {
        return this.mUri;
    }

    public void setContentId(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Content-Id may not be null or empty.");
        }
        if (bArr.length > 1 && ((char) bArr[0]) == '<' && ((char) bArr[bArr.length - 1]) == '>') {
            this.mPartHeader.put(192, bArr);
            return;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 2];
        bArr2[0] = 60;
        bArr2[length + 1] = 62;
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        this.mPartHeader.put(192, bArr2);
    }

    public byte[] getContentId() {
        return (byte[]) this.mPartHeader.get(192);
    }

    public void setCharset(int i) {
        this.mPartHeader.put(129, Integer.valueOf(i));
    }

    public int getCharset() {
        Integer num = (Integer) this.mPartHeader.get(129);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public void setContentLocation(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-location");
        }
        this.mPartHeader.put(142, bArr);
    }

    public byte[] getContentLocation() {
        return (byte[]) this.mPartHeader.get(142);
    }

    public void setContentDisposition(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-disposition");
        }
        this.mPartHeader.put(197, bArr);
    }

    public byte[] getContentDisposition() {
        return (byte[]) this.mPartHeader.get(197);
    }

    public void setContentType(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-type");
        }
        this.mPartHeader.put(145, bArr);
    }

    public byte[] getContentType() {
        return (byte[]) this.mPartHeader.get(145);
    }

    public void setContentTransferEncoding(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-transfer-encoding");
        }
        this.mPartHeader.put(200, bArr);
    }

    public byte[] getContentTransferEncoding() {
        return (byte[]) this.mPartHeader.get(200);
    }

    public void setName(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-id");
        }
        this.mPartHeader.put(151, bArr);
    }

    public byte[] getName() {
        return (byte[]) this.mPartHeader.get(151);
    }

    public void setFilename(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("null content-id");
        }
        this.mPartHeader.put(152, bArr);
    }

    public byte[] getFilename() {
        return (byte[]) this.mPartHeader.get(152);
    }

    public String generateLocation() {
        byte[] bArr = (byte[]) this.mPartHeader.get(151);
        if (bArr == null && (bArr = (byte[]) this.mPartHeader.get(152)) == null) {
            bArr = (byte[]) this.mPartHeader.get(142);
        }
        if (bArr == null) {
            return "cid:".concat(new String((byte[]) this.mPartHeader.get(192)));
        }
        return new String(bArr);
    }
}
