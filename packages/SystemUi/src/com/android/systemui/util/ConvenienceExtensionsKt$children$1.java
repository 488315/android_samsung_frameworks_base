package com.android.systemui.util;

import android.view.View;
import android.view.ViewGroup;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.ConvenienceExtensionsKt$children$1", m277f = "ConvenienceExtensions.kt", m278l = {28}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class ConvenienceExtensionsKt$children$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ ViewGroup $this_children;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConvenienceExtensionsKt$children$1(ViewGroup viewGroup, Continuation<? super ConvenienceExtensionsKt$children$1> continuation) {
        super(2, continuation);
        this.$this_children = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConvenienceExtensionsKt$children$1 convenienceExtensionsKt$children$1 = new ConvenienceExtensionsKt$children$1(this.$this_children, continuation);
        convenienceExtensionsKt$children$1.L$0 = obj;
        return convenienceExtensionsKt$children$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConvenienceExtensionsKt$children$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x0040 -> B:5:0x0043). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        int childCount;
        int i;
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            childCount = this.$this_children.getChildCount();
            i = 0;
            sequenceScope = sequenceScope2;
            if (i < childCount) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            childCount = this.I$1;
            i = this.I$0;
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            i++;
            if (i < childCount) {
                View childAt = this.$this_children.getChildAt(i);
                this.L$0 = sequenceScope;
                this.I$0 = i;
                this.I$1 = childCount;
                this.label = 1;
                if (sequenceScope.yield(childAt, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i++;
                if (i < childCount) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
