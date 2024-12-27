package com.android.systemui.volume.config;

import android.content.Context;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

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
