package com.android.systemui.media.mediaoutput.activity;

import android.media.AudioManager;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$volumeKeyHandler$1;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputActivity$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputActivity f$0;

    public /* synthetic */ MediaOutputActivity$$ExternalSyntheticLambda0(MediaOutputActivity mediaOutputActivity, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaOutputActivity;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        MediaOutputActivity mediaOutputActivity = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                AudioManager audioManager = mediaOutputActivity.audioManager;
                Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                return new AudioManagerExtKt$volumeKeyHandler$1(audioManager);
            default:
                mediaOutputActivity.finish();
                return Unit.INSTANCE;
        }
    }
}
