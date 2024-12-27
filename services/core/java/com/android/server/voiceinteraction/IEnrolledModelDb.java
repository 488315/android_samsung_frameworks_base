package com.android.server.voiceinteraction;

import android.hardware.soundtrigger.SoundTrigger;

import java.io.PrintWriter;

public interface IEnrolledModelDb {
    boolean deleteKeyphraseSoundModel(int i, int i2, String str);

    void dump(PrintWriter printWriter);

    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, int i2, String str);

    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str, String str2);

    boolean updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel);
}
