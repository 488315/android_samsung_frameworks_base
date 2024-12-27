package com.samsung.vekit.Interface;

import com.samsung.vekit.Common.Object.SpeakerIDInfo;

import java.util.HashMap;

public interface SpeakerIDInfoInterface<T> {
    void addSpeakerIDInfo(String str, SpeakerIDInfo speakerIDInfo);

    void clearSpeakerIDInfo();

    SpeakerIDInfo getSpeakerIDInfo(String str);

    HashMap<String, SpeakerIDInfo> getSpeakerIDInfoMap();

    int getSpeakerIDInfoMapSize();

    void removeSpeakerIDInfo(String str);

    void setSpeakerIDInfoMap(HashMap<String, SpeakerIDInfo> hashMap);
}
