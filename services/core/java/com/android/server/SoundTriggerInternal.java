package com.android.server;

import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.permission.Identity;
import android.os.IBinder;
import java.util.List;

/* loaded from: classes.dex */
public interface SoundTriggerInternal {

    public interface Session {
        void detach();

        SoundTrigger.ModuleProperties getModuleProperties();

        int getParameter(int i, int i2);

        SoundTrigger.ModelParamRange queryParameter(int i, int i2);

        int setParameter(int i, int i2, int i3);

        int startRecognition(int i, SoundTrigger.KeyphraseSoundModel keyphraseSoundModel, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z);

        int stopRecognition(int i, IRecognitionStatusCallback iRecognitionStatusCallback);

        int unloadKeyphraseModel(int i);
    }

    Session attach(IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties, boolean z);

    List listModuleProperties(Identity identity);
}
