package android.speech.tts;

import android.speech.tts.TextToSpeech;

/* loaded from: classes3.dex */
public abstract class UtteranceProgressListener {
    public void onAudioAvailable(String str, byte[] bArr) {
    }

    public void onBeginSynthesis(String str, int i, int i2, int i3) {
    }

    public abstract void onDone(String str);

    @Deprecated
    public abstract void onError(String str);

    public abstract void onStart(String str);

    public void onStop(String str, boolean z) {
    }

    @Deprecated
    public void onUtteranceRangeStart(String str, int i, int i2) {
    }

    public void onError(String str, int i) {
        onError(str);
    }

    public void onRangeStart(String str, int i, int i2, int i3) {
        onUtteranceRangeStart(str, i, i2);
    }

    static UtteranceProgressListener from(final TextToSpeech.OnUtteranceCompletedListener onUtteranceCompletedListener) {
        return new UtteranceProgressListener() { // from class: android.speech.tts.UtteranceProgressListener.1
            @Override // android.speech.tts.UtteranceProgressListener
            public void onStart(String str) {
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public synchronized void onDone(String str) {
                onUtteranceCompletedListener.onUtteranceCompleted(str);
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onError(String str) {
                onUtteranceCompletedListener.onUtteranceCompleted(str);
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onStop(String str, boolean z) {
                onUtteranceCompletedListener.onUtteranceCompleted(str);
            }
        };
    }
}
