package com.android.server.texttospeech;

import android.os.IBinder;
import android.speech.tts.ITextToSpeechService;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class TextToSpeechManagerPerUserService$TextToSpeechSessionConnection$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ITextToSpeechService.Stub.asInterface((IBinder) obj);
    }
}
