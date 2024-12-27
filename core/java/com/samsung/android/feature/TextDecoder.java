package com.samsung.android.feature;

import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/* loaded from: classes6.dex */
public class TextDecoder {
    private static final int DECODE_BLOCK = 1048576;
    private static final int FILE_BLOCK = 1024;
    private static final int SALT_LENGTH = 256;
    private static final byte[] salts = {65, -59, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -34, 107, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -107, 55, 78, 17, -81, 6, MidiConstants.STATUS_CONTROL_CHANGE, -121, -35, -23, 72, 122, -63, -43, 68, 119, -78, -111, -60, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 60, 57, 92, -88, -100, -69, -106, 91, 69, 93, 110, 23, 93, 53, -44, -51, 64, MidiConstants.STATUS_CONTROL_CHANGE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 2, -4, 12, -45, 80, -44, -35, -111, -28, -66, -116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 2, -27, -45, -52, 125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 66, -90, 63, -105, -67, 84, -57, -4, -4, 101, -90, 81, 10, -33, 1, 67, -57, -71, 18, -74, 102, SprAttributeBase.TYPE_DURATION, -89, 64, -17, 54, -94, -84, -66, 14, 119, 121, 2, -78, -79, 89, 63, 93, 109, -78, -51, 66, -36, 32, 86, 3, -58, MidiConstants.STATUS_MIDI_TIME_CODE, 92, 58, 2, -89, MidiConstants.STATUS_CONTROL_CHANGE, MidiConstants.STATUS_SONG_SELECT, -1, 122, -4, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 63, -44, 59, 100, -42, -45, 59, -7, -17, -54, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -54, 71, MidiConstants.STATUS_PROGRAM_CHANGE, -26, -87, MidiConstants.STATUS_CONTROL_CHANGE, -17, -44, -38, MidiConstants.STATUS_NOTE_ON, 70, 10, -106, 95, -24, -4, -118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -85, MidiConstants.STATUS_SONG_SELECT, 85, 25, -102, -119, 13, -37, 116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -69, 59, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -90, -38, -105, 101, -119, -36, SprAttributeBase.TYPE_ANIMATOR_SET, -3, -62, -91, -97, -125, 17, 14, 106, -72, -119, 99, 111, 20, 18, -27, 113, 64, -24, 74, -60, -100, 26, 56, -44, -70, 12, -51, -100, MidiConstants.STATUS_PITCH_BEND, -11, 26, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -117, 98, -93, 51, -25, -79, -31, SprAttributeBase.TYPE_ANIMATOR_SET, 87, -105, MidiConstants.STATUS_PROGRAM_CHANGE, 7, MidiConstants.STATUS_SONG_SELECT, -101, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -122, 5, -104, 89, -44, -117, 63, MidiConstants.STATUS_CONTROL_CHANGE, -6, -71, -110, -29, -105, 116, 107, -93, 91, -41, MidiConstants.STATUS_SONG_SELECT, 20, -115, -78, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 79, -122, 6, 102, MidiConstants.STATUS_PITCH_BEND, 52, -118, -51, 72, -104, 41, -38, 124, 72, -126, -35};
    private static final byte[] shifts = {1, 1, 0, 2, 2, 4, 5, 0, 4, 7, 1, 6, 5, 3, 3, 1, 2, 5, 0, 6, 2, 2, 4, 2, 2, 3, 0, 2, 1, 2, 4, 3, 4, 0, 0, 0, 3, 5, 3, 1, 6, 5, 6, 1, 1, 1, 0, 0, 3, 2, 7, 7, 5, 6, 7, 3, 5, 1, 0, 7, 6, 3, 6, 5, 4, 5, 3, 5, 1, 3, 3, 1, 5, 4, 1, 0, 0, 2, 6, 6, 6, 6, 4, 0, 1, 1, 0, 5, 5, 4, 2, 4, 6, 1, 7, 1, 2, 1, 1, 6, 5, 4, 7, 6, 5, 1, 6, 7, 0, 2, 6, 3, 1, 7, 1, 1, 7, 4, 0, 4, 2, 5, 3, 1, 1, 5, 6, 0, 3, 5, 3, 6, 5, 7, 2, 5, 6, 6, 2, 2, 3, 6, 0, 4, 3, 2, 0, 2, 2, 3, 5, 3, 3, 2, 5, 5, 5, 1, 3, 1, 1, 1, 4, 5, 1, 6, 2, 4, 7, 1, 4, 6, 0, 6, 4, 3, 2, 6, 1, 6, 3, 2, 1, 6, 7, 3, 2, 1, 1, 5, 6, 7, 2, 2, 2, 7, 4, 6, 7, 5, 3, 1, 4, 2, 7, 1, 6, 2, 4, 1, 5, 6, 5, 4, 5, 0, 1, 1, 6, 3, 7, 2, 0, 2, 5, 0, 1, 3, 3, 2, 6, 7, 7, 2, 5, 6, 0, 4, 1, 2, 5, 3, 7, 6, 5, 2, 5, 2, 0, 1, 3, 1, 4, 3, 4, 2};

    private static byte[] readBuffer(InputStream fis, int length) throws IOException {
        byte[] source = new byte[length];
        int current = 0;
        while (current < length) {
            int len = fis.read(source, current, length - current);
            if (len == -1) {
                break;
            }
            current += len;
        }
        return Arrays.copyOfRange(source, 0, current);
    }

    private static boolean isEncodedGzip(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = null;
        try {
            if (fis.available() > 0) {
                buffer = readBuffer(fis, 1024);
            }
            boolean z = false;
            if (buffer != null && buffer.length >= 2) {
                if (buffer[0] == 47) {
                    if (buffer[1] == 39) {
                        z = true;
                    }
                }
                fis.close();
                return z;
            }
            fis.close();
            return false;
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static byte[] decodeByte(byte[] source) {
        byte[] results = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            results[i] = (byte) (((source[i] & 255) << shifts[i % 256]) | ((source[i] & 255) >>> (8 - shifts[i % 256])));
            results[i] = (byte) (results[i] ^ salts[i % 256]);
        }
        return results;
    }

    private static byte[] decodeAllBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while (fis.available() > 0) {
                try {
                    byte[] buffer = decodeByte(readBuffer(fis, 1048576));
                    bos.write(buffer, 0, buffer.length);
                } finally {
                }
            }
            byte[] byteArray = bos.toByteArray();
            bos.close();
            fis.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                fis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static byte[] readEncodedGzipAllBytes(File file) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(decodeAllBytes(file));
        try {
            GZIPInputStream gzIs = new GZIPInputStream(bis);
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while (gzIs.available() > 0) {
                    try {
                        byte[] buffer = readBuffer(gzIs, 1048576);
                        bos.write(buffer, 0, buffer.length);
                    } finally {
                    }
                }
                byte[] byteArray = bos.toByteArray();
                bos.close();
                gzIs.close();
                bis.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bis.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static String decode(File file, boolean isTestEnabled) throws IOException {
        byte[] output;
        if (isEncodedGzip(file)) {
            output = readEncodedGzipAllBytes(file);
        } else if (isTestEnabled) {
            output = Files.readAllBytes(file.toPath());
        } else {
            return null;
        }
        return new String(output, StandardCharsets.UTF_8);
    }
}
