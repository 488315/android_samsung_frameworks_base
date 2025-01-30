package com.android.server.audio;

import com.android.server.utils.EventLogger;

/* loaded from: classes.dex */
public final class AudioServiceEvents$RingerZenMutedStreamsEvent extends EventLogger.Event {
  public final int mRingerZenMutedStreams;
  public final String mSource;

  public AudioServiceEvents$RingerZenMutedStreamsEvent(int i, String str) {
    this.mRingerZenMutedStreams = i;
    this.mSource = str;
  }

  @Override // com.android.server.utils.EventLogger.Event
  public String eventToString() {
    return "RingerZenMutedStreams 0x"
        + Integer.toHexString(this.mRingerZenMutedStreams)
        + " from "
        + this.mSource;
  }
}
