package com.android.server.audio;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
