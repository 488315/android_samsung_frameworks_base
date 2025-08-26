package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class MediaOutputInteractor$getMediaControllers$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Collection<MediaController> $controllers;
    int label;
    final /* synthetic */ MediaOutputInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputInteractor$getMediaControllers$2(Collection<MediaController> collection, MediaOutputInteractor mediaOutputInteractor, Continuation continuation) {
        super(2, continuation);
        this.$controllers = collection;
        this.this$0 = mediaOutputInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputInteractor$getMediaControllers$2(this.$controllers, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputInteractor$getMediaControllers$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        MediaController mediaControllerAccess$chooseController = null;
        MediaController mediaControllerAccess$chooseController2 = null;
        for (MediaController mediaController : this.$controllers) {
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            if (playbackInfo != null) {
                int playbackType = playbackInfo.getPlaybackType();
                if (playbackType != 1) {
                    if (playbackType == 2) {
                        if (StringsKt__StringsJVMKt.equals(mediaControllerAccess$chooseController != null ? mediaControllerAccess$chooseController.getPackageName() : null, mediaController.getPackageName(), false)) {
                            mediaControllerAccess$chooseController = null;
                        }
                        if (!linkedHashSet.contains(mediaController.getPackageName())) {
                            linkedHashSet.add(mediaController.getPackageName());
                            mediaControllerAccess$chooseController2 = MediaOutputInteractor.access$chooseController(this.this$0, mediaControllerAccess$chooseController2, mediaController);
                        }
                    }
                } else if (!linkedHashSet.contains(mediaController.getPackageName())) {
                    mediaControllerAccess$chooseController = MediaOutputInteractor.access$chooseController(this.this$0, mediaControllerAccess$chooseController, mediaController);
                }
            }
        }
        return new MediaOutputInteractor.MediaControllers(mediaControllerAccess$chooseController, mediaControllerAccess$chooseController2);
    }
}
