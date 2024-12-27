package com.samsung.vekit.Interface;

import com.samsung.vekit.Common.Object.AudioSegment;

import java.util.HashMap;

public interface AudioSegmentInterface<T> {
    void addAudioSegment(String str, AudioSegment audioSegment);

    void clearAudioSegment();

    AudioSegment getAudioSegment(String str);

    HashMap<String, AudioSegment> getAudioSegmentMap();

    int getAudioSegmentMapSize();

    void removeAudioSegment(String str);

    void setAudioSegmentMap(HashMap<String, AudioSegment> hashMap);
}
