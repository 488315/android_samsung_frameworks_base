package com.android.server.audio;

import com.android.server.utils.EventLogger;

public final class AudioServiceEvents$LoudnessEvent extends EventLogger.Event {
    public final int mEventType;
    public final int mIntValue1;
    public final int mIntValue2;

    public AudioServiceEvents$LoudnessEvent(int i, int i2, int i3) {
        this.mEventType = i;
        this.mIntValue1 = i2;
        this.mIntValue2 = i3;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        int i = this.mIntValue1;
        int i2 = this.mIntValue2;
        int i3 = this.mEventType;
        if (i3 == 0) {
            return String.format(
                    "Start loudness updates for piid %d for client pid %d",
                    Integer.valueOf(i), Integer.valueOf(i2));
        }
        if (i3 == 1) {
            return String.format(
                    "Stop loudness updates for piid %d for client pid %d",
                    Integer.valueOf(i), Integer.valueOf(i2));
        }
        if (i3 == 2) {
            return String.format("Loudness client with pid %d died", Integer.valueOf(i2));
        }
        return "FIXME invalid event type:" + i3;
    }
}
