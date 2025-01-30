package com.android.systemui.volume.config;

import android.content.Context;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeConfigs {
    public final Context context;
    public final Lazy systemConfig$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.config.VolumeConfigs$systemConfig$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SystemConfigImpl(VolumeConfigs.this.context);
        }
    });

    public VolumeConfigs(Context context) {
        this.context = context;
    }
}
