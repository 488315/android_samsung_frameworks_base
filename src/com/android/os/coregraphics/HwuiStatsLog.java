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
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeInt(i2);
        if (977 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        builderNewBuilder.writeInt(i3);
        builderNewBuilder.writeBoolean(z);
        builderNewBuilder.writeInt(i4);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }

    public static void write(int i, int i2, long j, int i3) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeInt(i2);
        if (946 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        if (947 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        builderNewBuilder.writeLong(j);
        builderNewBuilder.writeInt(i3);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }

    public static StatsEvent buildStatsEvent(int i, String str, long j, long j2, long j3, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[] bArr, byte[] bArr2, long j4, boolean z, int i10) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeString(str);
        builderNewBuilder.writeLong(j);
        builderNewBuilder.writeLong(j2);
        builderNewBuilder.writeLong(j3);
        builderNewBuilder.writeInt(i2);
        builderNewBuilder.writeInt(i3);
        builderNewBuilder.writeInt(i4);
        builderNewBuilder.writeInt(i5);
        builderNewBuilder.writeInt(i6);
        builderNewBuilder.writeInt(i7);
        builderNewBuilder.writeInt(i8);
        builderNewBuilder.writeInt(i9);
        builderNewBuilder.writeByteArray(bArr == null ? new byte[0] : bArr);
        builderNewBuilder.writeByteArray(bArr2 == null ? new byte[0] : bArr2);
        builderNewBuilder.writeLong(j4);
        builderNewBuilder.writeBoolean(z);
        builderNewBuilder.writeInt(i10);
        if (10068 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        return builderNewBuilder.build();
    }
}
