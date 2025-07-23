package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.lazy.LazyListInterval;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.EntityString;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda25 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ EntityString f$1;

    public /* synthetic */ MediaCardKt$$ExternalSyntheticLambda25(Object obj, EntityString entityString, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = entityString;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final List list = (List) ((MutableState) this.f$0).getValue();
                final MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1 mediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj2) {
                        return null;
                    }
                };
                int size = list.size();
                Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        return Function1.this.mo779invoke(list.get(((Number) obj2).intValue()));
                    }
                };
                final SessionController sessionController = (SessionController) this.f$1;
                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-632812321, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c1, code lost:
                    
                        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L33;
                     */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r20, java.lang.Object r21, java.lang.Object r22, java.lang.Object r23) {
                        /*
                            Method dump skipped, instructions count: 269
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$4.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                });
                LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) ((LazyListScope) obj);
                lazyListIntervalContent.getClass();
                lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(null, function1, composableLambdaImpl));
                break;
            default:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                AudioDevice audioDevice = (AudioDevice) this.f$1;
                AudioPathInteraction audioPathInteraction = (AudioPathInteraction) this.f$0;
                if (booleanValue) {
                    audioPathInteraction.select(audioDevice);
                } else if (audioDevice.getDeselectable()) {
                    audioPathInteraction.deselect(audioDevice);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
