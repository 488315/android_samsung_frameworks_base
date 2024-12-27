package com.samsung.android.server.vibrator;

import android.app.AlarmManager;
import android.os.SemHqmManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VibratorHqmHelper {
    public static final String[] BIG_DATA = {"FW_RVPC", "FW_AVPC", "FW_NVPC", "FW_TVPC", "FW_EVPC"};
    public static VibratorHqmHelper sInstance;
    public AlarmManager mAlarmManager;
    public SemHqmManager mSemHqmManager;
}
