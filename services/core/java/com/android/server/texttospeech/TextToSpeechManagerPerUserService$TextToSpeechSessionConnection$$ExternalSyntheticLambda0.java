package com.android.server.texttospeech;

import android.os.IBinder;
import android.speech.tts.ITextToSpeechService;

import java.util.function.Function;

public final /* synthetic */
class TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ITextToSpeechService.Stub.asInterface((IBinder) obj);
    }
}
