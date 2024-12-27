package com.android.server.audio;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

public final class AudioServiceEvents$RingerZenMutedStreamsEvent extends EventLogger.Event {
    public final int mRingerZenMutedStreams;
    public final String mSource;

    public AudioServiceEvents$RingerZenMutedStreamsEvent(int i, String str) {
        this.mRingerZenMutedStreams = i;
        this.mSource = str;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        StringBuilder sb = new StringBuilder("RingerZenMutedStreams 0x");
        BatteryService$$ExternalSyntheticOutline0.m(this.mRingerZenMutedStreams, sb, " from ");
        sb.append(this.mSource);
        return sb.toString();
    }
}
