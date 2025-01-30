package com.android.systemui.volume;

import android.content.Context;
import com.samsung.systemui.splugins.volume.VolumeStar;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeStarInteractor {
    public final Context context;
    public VolumeStarInteractor$start$1 listener;
    public VolumeStar volumeStar;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
