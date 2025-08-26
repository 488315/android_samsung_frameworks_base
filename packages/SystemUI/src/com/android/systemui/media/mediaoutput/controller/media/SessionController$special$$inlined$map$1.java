package com.android.systemui.media.mediaoutput.controller.media;

import android.graphics.Color;
import com.android.systemui.media.mediaoutput.entity.MediaInfoExt;
import com.android.systemui.monet.ColorScheme;
import java.util.Arrays;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class SessionController$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.media.mediaoutput.controller.media.SessionController$special$$inlined$map$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector) {
            this.$this_unsafeFlow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            List listAsList;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object obj2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                ColorScheme colorScheme = (ColorScheme) obj;
                if (colorScheme == null) {
                    listAsList = null;
                } else {
                    MediaInfoExt mediaInfoExt = MediaInfoExt.INSTANCE;
                    int s700 = colorScheme.mAccent2.getS700();
                    mediaInfoExt.getClass();
                    float[] fArr = new float[3];
                    Color.colorToHSV(s700, fArr);
                    if (fArr[2] > 0.2f) {
                        fArr[2] = 0.2f;
                        s700 = Color.HSVToColor(fArr);
                    }
                    int s7002 = colorScheme.mAccent1.getS700();
                    float[] fArr2 = new float[3];
                    Color.colorToHSV(s7002, fArr2);
                    if (fArr2[2] > 0.2f) {
                        fArr2[2] = 0.2f;
                        s7002 = Color.HSVToColor(fArr2);
                    }
                    listAsList = Arrays.asList(new Integer(Color.argb(114, Color.red(s700), Color.green(s700), Color.blue(s700))), new Integer(Color.argb(255, Color.red(s7002), Color.green(s7002), Color.blue(s7002))));
                }
                anonymousClass1.label = 1;
                if (this.$this_unsafeFlow.emit(listAsList, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    public SessionController$special$$inlined$map$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
