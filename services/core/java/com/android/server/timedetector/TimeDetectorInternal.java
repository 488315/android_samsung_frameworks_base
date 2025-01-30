package com.android.server.timedetector;

/* loaded from: classes3.dex */
public interface TimeDetectorInternal {
    void suggestGnssTime(GnssTimeSuggestion gnssTimeSuggestion);

    void suggestNetworkTime(NetworkTimeSuggestion networkTimeSuggestion);
}
