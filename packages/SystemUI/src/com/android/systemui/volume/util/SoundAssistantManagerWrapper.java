package com.android.systemui.volume.util;

import android.content.Context;
import com.samsung.android.media.SemSoundAssistantManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SoundAssistantManagerWrapper {
    public final SemSoundAssistantManager satMananger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
