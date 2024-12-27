package com.android.server.voiceinteraction;

import android.hardware.soundtrigger.SoundTrigger;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface IEnrolledModelDb {
    boolean deleteKeyphraseSoundModel(int i, int i2, String str);

    void dump(PrintWriter printWriter);

    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, int i2, String str);

    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str, String str2);

    boolean updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel);
}
