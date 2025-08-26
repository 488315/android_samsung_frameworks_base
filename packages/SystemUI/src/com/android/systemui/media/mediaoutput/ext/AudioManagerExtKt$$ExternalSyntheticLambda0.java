package com.android.systemui.media.mediaoutput.ext;

import android.media.audiopolicy.AudioProductStrategy;
import java.util.Iterator;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class AudioManagerExtKt$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        AudioProductStrategy audioProductStrategy;
        Iterator it = AudioProductStrategy.getAudioProductStrategies().iterator();
        do {
            audioProductStrategy = null;
            if (!it.hasNext()) {
                break;
            }
            AudioProductStrategy audioProductStrategy2 = (AudioProductStrategy) it.next();
            if (audioProductStrategy2.getAudioAttributesForLegacyStreamType(3) != null) {
                audioProductStrategy = audioProductStrategy2;
            }
        } while (audioProductStrategy == null);
        return audioProductStrategy;
    }
}
