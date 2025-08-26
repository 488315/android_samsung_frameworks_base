package com.samsung.android.sesl.transparentvideo.mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.view.Surface;
import java.io.IOException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes4.dex */
public final /* synthetic */ class BasicMediaPlayer$$ExternalSyntheticLambda5 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ BasicMediaPlayer f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BasicMediaPlayer$$ExternalSyntheticLambda5(BasicMediaPlayer basicMediaPlayer, AssetFileDescriptor assetFileDescriptor) {
        this.f$0 = basicMediaPlayer;
        this.f$1 = assetFileDescriptor;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() throws IllegalStateException, IOException, IllegalArgumentException {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mediaPlayer.setSurface((Surface) this.f$1);
                break;
            default:
                this.f$0.mediaPlayer.setDataSource((AssetFileDescriptor) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ BasicMediaPlayer$$ExternalSyntheticLambda5(BasicMediaPlayer basicMediaPlayer, Surface surface) {
        this.f$0 = basicMediaPlayer;
        this.f$1 = surface;
    }
}
