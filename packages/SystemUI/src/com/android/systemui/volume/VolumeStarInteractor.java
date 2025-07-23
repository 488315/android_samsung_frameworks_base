package com.android.systemui.volume;

import android.content.Context;
import com.samsung.systemui.splugins.volume.VolumeStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeStarInteractor {
    public final Context context;
    public VolumeStarInteractor$start$1 listener;
    public VolumeStar volumeStar;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public VolumeStarInteractor(Context context) {
        this.context = context;
    }
}
