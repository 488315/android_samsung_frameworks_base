package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import com.android.systemui.audio.soundcraft.model.ModelProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SoundAliveIntentFactory {
    public final ModelProvider modelProvider;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SoundAliveIntentFactory(ModelProvider modelProvider) {
        this.modelProvider = modelProvider;
    }
}
