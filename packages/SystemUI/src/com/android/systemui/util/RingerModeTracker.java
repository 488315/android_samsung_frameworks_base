package com.android.systemui.util;

import androidx.lifecycle.LiveData;

public interface RingerModeTracker {
    LiveData getRingerMode();

    LiveData getRingerModeInternal();
}
