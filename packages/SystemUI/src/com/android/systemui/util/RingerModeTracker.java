package com.android.systemui.util;

import androidx.lifecycle.LiveData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface RingerModeTracker {
    LiveData getRingerMode();

    LiveData getRingerModeInternal();
}
