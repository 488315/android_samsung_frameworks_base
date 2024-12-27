package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface ISoundTriggerHal {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface GlobalCallback {
        void onResourcesAvailable();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ModelCallback {
        void modelUnloaded(int i);

        void phraseRecognitionCallback(int i, PhraseRecognitionEventSys phraseRecognitionEventSys);

        void recognitionCallback(int i, RecognitionEventSys recognitionEventSys);
    }

    void clientAttached(IBinder iBinder);

    void clientDetached(IBinder iBinder);

    void detach();

    void forceRecognitionEvent(int i);

    int getModelParameter(int i, int i2);

    Properties getProperties();

    void linkToDeath(IBinder.DeathRecipient deathRecipient);

    int loadPhraseSoundModel(PhraseSoundModel phraseSoundModel, ModelCallback modelCallback);

    int loadSoundModel(SoundModel soundModel, ModelCallback modelCallback);

    ModelParameterRange queryParameter(int i, int i2);

    void reboot();

    void registerCallback(GlobalCallback globalCallback);

    void setModelParameter(int i, int i2, int i3);

    void startRecognition(int i, int i2, int i3, RecognitionConfig recognitionConfig);

    void stopRecognition(int i);

    void unloadSoundModel(int i);
}
