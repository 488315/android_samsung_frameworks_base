package com.android.os.coregraphics;

import android.util.StatsEvent;
import android.util.StatsLog;

/* loaded from: classes6.dex */
public class HwuiStatsLog {
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int GRAPHICS_STATS = 10068;
    public static final int GRAPHICS_STATS__PIPELINE__GL = 1;
    public static final int GRAPHICS_STATS__PIPELINE__UNKNOWN = 0;
    public static final int GRAPHICS_STATS__PIPELINE__VULKAN = 2;
    public static final int HARDWARE_RENDERER_EVENT = 946;
    public static final int HARDWARE_RENDERER_EVENT__PREVIOUS_COLOR_MODE__DEFAULT = 0;
    public static final int HARDWARE_RENDERER_EVENT__PREVIOUS_COLOR_MODE__HDR = 2;
    public static final int HARDWARE_RENDERER_EVENT__PREVIOUS_COLOR_MODE__WIDE_COLOR = 1;
    public static final int IMAGE_DECODED = 977;
    public static final int IMAGE_DECODED__COLOR_SPACE_TRANSFER__COLOR_SPACE_TRANSFER_HLGISH = 3;
    public static final int IMAGE_DECODED__COLOR_SPACE_TRANSFER__COLOR_SPACE_TRANSFER_PQISH = 2;
    public static final int IMAGE_DECODED__COLOR_SPACE_TRANSFER__COLOR_SPACE_TRANSFER_SRGBISH = 1;
    public static final int IMAGE_DECODED__COLOR_SPACE_TRANSFER__COLOR_SPACE_TRANSFER_UNKNOWN = 0;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_ARGB_8888 = 3;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_A_8 = 1;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_RGBA_1010102 = 5;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_RGBA_F16 = 4;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_RGB_565 = 2;
    public static final int IMAGE_DECODED__FORMAT__BITMAP_FORMAT_UNKNOWN = 0;
    public static final int TEXTURE_VIEW_EVENT = 947;

    public static void write(int i, int i2, int i3, boolean z, int i4) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        if (977 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        newBuilder.writeInt(i3);
        newBuilder.writeBoolean(z);
        newBuilder.writeInt(i4);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static void write(int i, int i2, long j, int i3) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeInt(i2);
        if (946 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        if (947 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        newBuilder.writeLong(j);
        newBuilder.writeInt(i3);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }

    public static StatsEvent buildStatsEvent(int i, String str, long j, long j2, long j3, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[] bArr, byte[] bArr2, long j4, boolean z, int i10) {
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(i);
        newBuilder.writeString(str);
        newBuilder.writeLong(j);
        newBuilder.writeLong(j2);
        newBuilder.writeLong(j3);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.writeInt(i4);
        newBuilder.writeInt(i5);
        newBuilder.writeInt(i6);
        newBuilder.writeInt(i7);
        newBuilder.writeInt(i8);
        newBuilder.writeInt(i9);
        newBuilder.writeByteArray(bArr == null ? new byte[0] : bArr);
        newBuilder.writeByteArray(bArr2 == null ? new byte[0] : bArr2);
        newBuilder.writeLong(j4);
        newBuilder.writeBoolean(z);
        newBuilder.writeInt(i10);
        if (10068 == i) {
            newBuilder.addBooleanAnnotation((byte) 1, true);
        }
        return newBuilder.build();
    }
}
