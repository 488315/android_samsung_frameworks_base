package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseRecognitionExtra;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;

/* loaded from: classes3.dex */
public abstract class AidlUtil {
    public static RecognitionEvent newEmptyRecognitionEvent() {
        RecognitionEvent recognitionEvent = new RecognitionEvent();
        recognitionEvent.data = new byte[0];
        return recognitionEvent;
    }

    public static PhraseRecognitionEvent newEmptyPhraseRecognitionEvent() {
        PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
        phraseRecognitionEvent.common = newEmptyRecognitionEvent();
        phraseRecognitionEvent.phraseExtras = new PhraseRecognitionExtra[0];
        return phraseRecognitionEvent;
    }

    public static RecognitionEventSys newAbortEvent() {
        RecognitionEvent newEmptyRecognitionEvent = newEmptyRecognitionEvent();
        newEmptyRecognitionEvent.type = 1;
        newEmptyRecognitionEvent.status = 1;
        RecognitionEventSys recognitionEventSys = new RecognitionEventSys();
        recognitionEventSys.recognitionEvent = newEmptyRecognitionEvent;
        return recognitionEventSys;
    }

    public static PhraseRecognitionEventSys newAbortPhraseEvent() {
        PhraseRecognitionEvent newEmptyPhraseRecognitionEvent = newEmptyPhraseRecognitionEvent();
        RecognitionEvent recognitionEvent = newEmptyPhraseRecognitionEvent.common;
        recognitionEvent.type = 0;
        recognitionEvent.status = 1;
        PhraseRecognitionEventSys phraseRecognitionEventSys = new PhraseRecognitionEventSys();
        phraseRecognitionEventSys.phraseRecognitionEvent = newEmptyPhraseRecognitionEvent;
        return phraseRecognitionEventSys;
    }
}
