package com.android.systemui.keyguard;

import android.database.Cursor;
import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.CustomizationProvider$query$1", m277f = "CustomizationProvider.kt", m278l = {149, 150, 151, 152}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class CustomizationProvider$query$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $uri;
    int label;
    final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$query$1(CustomizationProvider customizationProvider, Uri uri, Continuation<? super CustomizationProvider$query$1> continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomizationProvider$query$1(this.this$0, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProvider$query$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                return (Cursor) obj;
            }
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                return (Cursor) obj;
            }
            if (i == 3) {
                ResultKt.throwOnFailure(obj);
                return (Cursor) obj;
            }
            if (i != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return (Cursor) obj;
        }
        ResultKt.throwOnFailure(obj);
        int match = this.this$0.uriMatcher.match(this.$uri);
        if (match == 1) {
            CustomizationProvider customizationProvider = this.this$0;
            this.label = 2;
            obj = CustomizationProvider.access$querySlots(customizationProvider, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            return (Cursor) obj;
        }
        if (match == 2) {
            CustomizationProvider customizationProvider2 = this.this$0;
            this.label = 1;
            obj = CustomizationProvider.access$queryAffordances(customizationProvider2, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            return (Cursor) obj;
        }
        if (match == 3) {
            CustomizationProvider customizationProvider3 = this.this$0;
            this.label = 3;
            obj = CustomizationProvider.access$querySelections(customizationProvider3, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            return (Cursor) obj;
        }
        if (match != 4) {
            return null;
        }
        CustomizationProvider customizationProvider4 = this.this$0;
        this.label = 4;
        obj = CustomizationProvider.access$queryFlags(customizationProvider4, this);
        if (obj == coroutineSingletons) {
            return coroutineSingletons;
        }
        return (Cursor) obj;
    }
}
