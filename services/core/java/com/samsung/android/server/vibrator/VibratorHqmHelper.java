package com.samsung.android.server.vibrator;

import android.app.AlarmManager;
import android.os.SemHqmManager;

public final class VibratorHqmHelper {
    public static final String[] BIG_DATA = {"FW_RVPC", "FW_AVPC", "FW_NVPC", "FW_TVPC", "FW_EVPC"};
    public static VibratorHqmHelper sInstance;
    public AlarmManager mAlarmManager;
    public SemHqmManager mSemHqmManager;
}
