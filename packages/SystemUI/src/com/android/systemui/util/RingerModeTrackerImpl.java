package com.android.systemui.util;

import android.media.AudioManager;
import androidx.lifecycle.LiveData;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;

public final class RingerModeTrackerImpl implements RingerModeTracker {
    public static final int $stable = 8;
    private final LiveData ringerMode;
    private final LiveData ringerModeInternal;

    public RingerModeTrackerImpl(AudioManager audioManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.ringerMode = new RingerModeLiveData(broadcastDispatcher, executor, "android.media.RINGER_MODE_CHANGED", new RingerModeTrackerImpl$ringerMode$1(audioManager));
        this.ringerModeInternal = new RingerModeLiveData(broadcastDispatcher, executor, "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", new RingerModeTrackerImpl$ringerModeInternal$1(audioManager));
    }

    @Override // com.android.systemui.util.RingerModeTracker
    public LiveData getRingerMode() {
        return this.ringerMode;
    }

    @Override // com.android.systemui.util.RingerModeTracker
    public LiveData getRingerModeInternal() {
        return this.ringerModeInternal;
    }
}
