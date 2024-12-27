package com.android.systemui.volume.config;

import android.content.Context;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
