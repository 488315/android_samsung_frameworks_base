package com.android.systemui.qs.bar;

import android.os.Handler;
import android.os.Looper;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

final class VolumeToggleSeekBar$mainThreadHandler$2 extends Lambda implements Function0 {
    public static final VolumeToggleSeekBar$mainThreadHandler$2 INSTANCE = new VolumeToggleSeekBar$mainThreadHandler$2();

    public VolumeToggleSeekBar$mainThreadHandler$2() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new Handler(Looper.getMainLooper());
    }
}
