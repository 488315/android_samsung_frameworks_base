package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import com.android.systemui.audio.soundcraft.model.ModelProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SoundAliveIntentFactory {
    public final ModelProvider modelProvider;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SoundAliveIntentFactory(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }
}
