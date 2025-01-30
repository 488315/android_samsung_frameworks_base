package com.android.modules.expresslog;

import android.util.StatsEvent;
import android.util.StatsLog;

/* loaded from: classes5.dex */
public final class StatsExpressLog {
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

  public static void write(int code, long arg1, long arg2) {
    StatsEvent.Builder builder = StatsEvent.newBuilder();
    builder.setAtomId(code);
    builder.writeLong(arg1);
    builder.writeLong(arg2);
    builder.usePooledBuffer();
    StatsLog.write(builder.build());
  }

  public static void write(int code, long arg1, long arg2, int arg3) {
    StatsEvent.Builder builder = StatsEvent.newBuilder();
    builder.setAtomId(code);
    builder.writeLong(arg1);
    builder.writeLong(arg2);
    builder.writeInt(arg3);
    if (644 == code) {
      builder.addBooleanAnnotation((byte) 1, true);
    }
    builder.usePooledBuffer();
    StatsLog.write(builder.build());
  }

  public static void write(int code, long arg1, long arg2, int arg3, int arg4) {
    StatsEvent.Builder builder = StatsEvent.newBuilder();
    builder.setAtomId(code);
    builder.writeLong(arg1);
    builder.writeLong(arg2);
    builder.writeInt(arg3);
    builder.writeInt(arg4);
    if (658 == code) {
      builder.addBooleanAnnotation((byte) 1, true);
    }
    builder.usePooledBuffer();
    StatsLog.write(builder.build());
  }
}
