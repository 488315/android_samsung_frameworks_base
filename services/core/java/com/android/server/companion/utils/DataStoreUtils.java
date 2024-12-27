package com.android.server.companion.utils;

import android.util.AtomicFile;
import android.util.Slog;

import com.android.internal.util.FunctionalUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class DataStoreUtils {
    public static byte[] fileToByteArray(AtomicFile atomicFile) {
        if (!atomicFile.getBaseFile().exists()) {
            Slog.d("CDM_DataStoreUtils", "File does not exist");
            return new byte[0];
        }
        try {
            FileInputStream openRead = atomicFile.openRead();
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openRead.read(bArr);
                    if (read == -1) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        openRead.close();
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
            } finally {
            }
        } catch (IOException e) {
            Slog.e("CDM_DataStoreUtils", "Error while reading requests file", e);
            return new byte[0];
        }
    }

    public static boolean isEndOfTag(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getEventType() == 3 && str.equals(xmlPullParser.getName());
    }

    public static boolean isStartOfTag(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getEventType() == 2 && str.equals(xmlPullParser.getName());
    }

    public static void writeToFileSafely(
            AtomicFile atomicFile, FunctionalUtils.ThrowingConsumer throwingConsumer) {
        try {
            atomicFile.write(throwingConsumer);
        } catch (Exception e) {
            Slog.e("CDM_DataStoreUtils", "Error while writing to file " + atomicFile, e);
        }
    }
}
