package com.android.systemui.media;

import android.media.AudioManager;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$volumeKeyHandler$1;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputView$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaOutputView$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((MediaOutputView) obj).largeScreenHeaderHelper.getLargeScreenHeaderHeight());
            case 1:
                return Integer.valueOf(((Number) ((MediaOutputView) obj).largeScreenShadeHeaderHeight$delegate.getValue()).intValue());
            default:
                int i = MediaOutputView.$r8$clinit;
                Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                return new AudioManagerExtKt$volumeKeyHandler$1((AudioManager) obj);
        }
    }
}
