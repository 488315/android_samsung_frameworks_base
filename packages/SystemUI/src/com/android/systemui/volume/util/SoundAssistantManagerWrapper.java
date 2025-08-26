package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SoundAssistantManagerWrapper {
    public final SemSoundAssistantManager satMananger;

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

    public SoundAssistantManagerWrapper(Context context) {
        this.satMananger = new SemSoundAssistantManager(context);
    }
}
