package com.android.modules.expresslog;

import android.util.StatsEvent;
import android.util.StatsLog;

/* loaded from: classes6.dex */
public class StatsExpressLog {
    public static final byte ANNOTATION_ID_DEFAULT_STATE = 6;
    public static final byte ANNOTATION_ID_EXCLUSIVE_STATE = 4;
    public static final byte ANNOTATION_ID_IS_UID = 1;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD = 3;
    public static final byte ANNOTATION_ID_PRIMARY_FIELD_FIRST_UID = 5;
    public static final byte ANNOTATION_ID_STATE_NESTED = 8;
    public static final byte ANNOTATION_ID_TRIGGER_STATE_RESET = 7;
    public static final byte ANNOTATION_ID_TRUNCATE_TIMESTAMP = 2;
    public static final int EXPRESS_EVENT_REPORTED = 528;
    public static final int EXPRESS_HISTOGRAM_SAMPLE_REPORTED = 593;
    public static final int EXPRESS_UID_EVENT_REPORTED = 644;
    public static final int EXPRESS_UID_HISTOGRAM_SAMPLE_REPORTED = 658;

    public static void write(int i, long j, long j2) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeLong(j);
        builderNewBuilder.writeLong(j2);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }

    public static void write(int i, long j, long j2, int i2) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeLong(j);
        builderNewBuilder.writeLong(j2);
        builderNewBuilder.writeInt(i2);
        if (644 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }

    public static void write(int i, long j, long j2, int i2, int i3) {
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(i);
        builderNewBuilder.writeLong(j);
        builderNewBuilder.writeLong(j2);
        builderNewBuilder.writeInt(i2);
        builderNewBuilder.writeInt(i3);
        if (658 == i) {
            builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        }
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }
}
