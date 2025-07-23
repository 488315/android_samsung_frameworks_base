package com.android.systemui.statusbar.featurepods.media.domain.interactor;

import android.graphics.drawable.Icon;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.statusbar.featurepods.media.shared.model.MediaControlChipModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MediaControlChipInteractor$mediaControlChipModelForScene$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MediaControlChipInteractor$mediaControlChipModelForScene$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaControlChipInteractor$mediaControlChipModelForScene$1 mediaControlChipInteractor$mediaControlChipModelForScene$1 = new MediaControlChipInteractor$mediaControlChipModelForScene$1((Continuation) obj3);
        mediaControlChipInteractor$mediaControlChipModelForScene$1.L$0 = (List) obj;
        mediaControlChipInteractor$mediaControlChipModelForScene$1.L$1 = (Map) obj2;
        return mediaControlChipInteractor$mediaControlChipModelForScene$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Map map = (Map) this.L$1;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaData mediaData = (MediaData) map.get(((MediaCommonModel) it.next()).mediaLoadedModel.instanceId);
            if (mediaData != null) {
                arrayList.add(mediaData);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj2 = null;
                break;
            }
            obj2 = arrayList.get(i);
            i++;
            if (((MediaData) obj2).active) {
                break;
            }
        }
        MediaData mediaData2 = (MediaData) obj2;
        if (mediaData2 == null) {
            return null;
        }
        Icon icon = mediaData2.appIcon;
        CharSequence charSequence = mediaData2.song;
        MediaButton mediaButton = mediaData2.semanticActions;
        return new MediaControlChipModel(icon, mediaData2.app, charSequence, mediaButton != null ? mediaButton.playOrPause : null);
    }
}
