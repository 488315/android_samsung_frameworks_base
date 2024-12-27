package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SoundAssistantManagerWrapper {
    public final SemSoundAssistantManager satMananger;

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

    public SoundAssistantManagerWrapper(Context context) {
        this.satMananger = new SemSoundAssistantManager(context);
    }
}
