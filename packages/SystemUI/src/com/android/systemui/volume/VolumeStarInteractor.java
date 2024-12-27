package com.android.systemui.volume;

import android.content.Context;
import com.samsung.systemui.splugins.volume.VolumeStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumeStarInteractor {
    public final Context context;
    public VolumeStarInteractor$start$1 listener;
    public VolumeStar volumeStar;

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
