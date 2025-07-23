package com.android.internal.accessibility;

import android.speech.tts.TextToSpeech;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes5.dex */
public final /* synthetic */ class AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((TextToSpeech) obj).shutdown();
    }
}
