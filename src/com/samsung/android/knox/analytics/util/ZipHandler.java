package com.samsung.android.knox.analytics.util;

import com.samsung.android.knox.analytics.model.EventList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class ZipHandler {
    private static final String TAG = "ZipHandler";

    public static ZipResult deflate(byte[] bArr) {
        if (bArr.length <= 0) {
            Log.e(TAG, "deflate(): Empty object byte array");
            return null;
        }
        Deflater deflater = new Deflater(9);
        deflater.setInput(bArr);
        deflater.finish();
        byte[] bArr2 = new byte[32767];
        int iDeflate = deflater.deflate(bArr2);
        byte[] bArr3 = new byte[iDeflate];
        System.arraycopy(bArr2, 0, bArr3, 0, iDeflate);
        deflater.end();
        Log.d(TAG, "deflate(): bytes size: " + bArr.length + ", bytes size after compression: " + iDeflate + ",  bytes saved: " + (bArr.length - iDeflate));
        return new ZipResult(bArr3, iDeflate, bArr.length);
    }

    public static EventList inflate(ZipResult zipResult) throws JSONException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(zipResult.getContent(), 0, zipResult.getLength());
        byte[] bArr = new byte[zipResult.getOriginalLength()];
        int iInflate = inflater.inflate(bArr);
        String str = TAG;
        Log.d(str, "inflate(): actual number of uncompressed bytes: " + iInflate + " original number of uncompressed bytes: " + zipResult.getOriginalLength());
        inflater.end();
        if (iInflate <= 0) {
            Log.d(str, "inflate(): Could not return to decompress data");
            return null;
        }
        return new EventList(bArr);
    }
}
