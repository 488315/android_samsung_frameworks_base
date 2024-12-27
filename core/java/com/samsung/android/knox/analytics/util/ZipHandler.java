package com.samsung.android.knox.analytics.util;

import com.samsung.android.knox.analytics.model.EventList;

import org.json.JSONException;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipHandler {
    private static final String TAG = ZipHandler.class.getSimpleName();

    public static ZipResult deflate(byte[] content) {
        if (content.length <= 0) {
            Log.e(TAG, "deflate(): Empty object byte array");
            return null;
        }
        Deflater deflater = new Deflater(9);
        deflater.setInput(content);
        deflater.finish();
        byte[] tmpBuffer = new byte[32767];
        int zippedLength = deflater.deflate(tmpBuffer);
        byte[] zippedContent = new byte[zippedLength];
        System.arraycopy(tmpBuffer, 0, zippedContent, 0, zippedLength);
        deflater.end();
        Log.d(
                TAG,
                "deflate(): bytes size: "
                        + content.length
                        + ", bytes size after compression: "
                        + zippedLength
                        + ",  bytes saved: "
                        + (content.length - zippedLength));
        return new ZipResult(zippedContent, zippedLength, content.length);
    }

    public static EventList inflate(ZipResult zip) throws JSONException, DataFormatException {
        Inflater inflate = new Inflater();
        inflate.setInput(zip.getContent(), 0, zip.getLength());
        byte[] original = new byte[zip.getOriginalLength()];
        int origLength = inflate.inflate(original);
        Log.d(
                TAG,
                "inflate(): actual number of uncompressed bytes: "
                        + origLength
                        + " original number of uncompressed bytes: "
                        + zip.getOriginalLength());
        inflate.end();
        if (origLength <= 0) {
            Log.d(TAG, "inflate(): Could not return to decompress data");
            return null;
        }
        return new EventList(original);
    }
}
