package com.android.systemui.volume;

import android.content.Context;
import com.samsung.systemui.splugins.volume.VolumeStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumeStarInteractor {
    public final Context context;
    public VolumeStarInteractor$start$1 listener;
    public VolumeStar volumeStar;

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

    public VolumeStarInteractor(Context context) {
        this.context = context;
    }
}
