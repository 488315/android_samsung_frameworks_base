package android.service.voice;

import java.util.function.Consumer;

public final /* synthetic */ class VoiceInteractionService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ VoiceInteractionService f$0;

    public /* synthetic */ VoiceInteractionService$$ExternalSyntheticLambda0(
            VoiceInteractionService voiceInteractionService) {
        this.f$0 = voiceInteractionService;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.onHotwordDetectorDestroyed((AbstractDetector) obj);
    }
}
