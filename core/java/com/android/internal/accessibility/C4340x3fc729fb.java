package com.android.internal.accessibility;

import android.speech.tts.TextToSpeech;
import java.util.function.Consumer;

/* compiled from: D8$$SyntheticClass */
/* renamed from: com.android.internal.accessibility.AccessibilityDirectAccessController$TtsPrompt$$ExternalSyntheticLambda2 */
/* loaded from: classes4.dex */
public final /* synthetic */ class C4340x3fc729fb implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((TextToSpeech) obj).shutdown();
    }
}
