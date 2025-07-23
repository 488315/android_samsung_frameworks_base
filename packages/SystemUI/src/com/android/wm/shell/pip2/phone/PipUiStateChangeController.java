package com.android.wm.shell.pip2.phone;

import android.app.PictureInPictureUiState;
import android.os.Bundle;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipUiStateChangeController implements PipTransitionState.PipTransitionStateChangedListener {
    public Consumer mPictureInPictureUiStateConsumer;
    public final PipTransitionState mPipTransitionState;

    public PipUiStateChangeController(PipTransitionState pipTransitionState) {
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPictureInPictureUiStateConsumer = new PipUiStateChangeController$$ExternalSyntheticLambda0();
    }

    public final void onIsTransitioningToPipUiStateChange(boolean z) {
        Consumer consumer = this.mPictureInPictureUiStateConsumer;
        if (consumer != null) {
            consumer.accept(new PictureInPictureUiState.Builder().setTransitioningToPip(z).build());
        }
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        if (i2 == 1) {
            onIsTransitioningToPipUiStateChange(true);
            return;
        }
        if (i2 == 2 && !this.mPipTransitionState.mInSwipePipToHomeTransition) {
            onIsTransitioningToPipUiStateChange(true);
        } else if (i2 == 3) {
            onIsTransitioningToPipUiStateChange(false);
        }
    }

    public void setPictureInPictureUiStateConsumer(Consumer<PictureInPictureUiState> consumer) {
        this.mPictureInPictureUiStateConsumer = consumer;
    }
}
