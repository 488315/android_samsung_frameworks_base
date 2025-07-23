package com.android.systemui.util;

import androidx.lifecycle.LiveData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface RingerModeTracker {
    LiveData getRingerMode();

    LiveData getRingerModeInternal();
}
