package com.android.systemui.media.mediaoutput.controller.media;

import android.media.MediaMetadata;
import android.media.session.PlaybackState;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.monet.ColorScheme;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

final class MediaSessionController$update$8 extends SuspendLambda implements Function2 {
    final /* synthetic */ Pair<Painter, ColorScheme> $appIcon;
    final /* synthetic */ Ref$ObjectRef<MediaMetadata> $newMetadata;
    final /* synthetic */ Ref$ObjectRef<PlaybackState> $newPlaybackState;
    final /* synthetic */ Pair<ImageBitmap, ColorScheme> $thumbnail;
    int label;
    final /* synthetic */ MediaSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaSessionController$update$8(MediaSessionController mediaSessionController, Ref$ObjectRef<MediaMetadata> ref$ObjectRef, Ref$ObjectRef<PlaybackState> ref$ObjectRef2, Pair<? extends ImageBitmap, ? extends ColorScheme> pair, Pair<? extends Painter, ? extends ColorScheme> pair2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaSessionController;
        this.$newMetadata = ref$ObjectRef;
        this.$newPlaybackState = ref$ObjectRef2;
        this.$thumbnail = pair;
        this.$appIcon = pair2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaSessionController$update$8(this.this$0, this.$newMetadata, this.$newPlaybackState, this.$thumbnail, this.$appIcon, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionController$update$8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:131:0x00f2, code lost:
    
        if (r3 != null) goto L53;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r44) {
        /*
            Method dump skipped, instructions count: 823
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.MediaSessionController$update$8.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
