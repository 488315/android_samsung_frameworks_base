package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LabsViewModel$openActivity$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isDex;
    int label;
    final /* synthetic */ LabsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LabsViewModel$openActivity$1(LabsViewModel labsViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = labsViewModel;
        this.$isDex = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LabsViewModel$openActivity$1(this.this$0, this.$isDex, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LabsViewModel$openActivity$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((PanelInteractorImpl) this.this$0.panelInteractor).collapsePanels();
        Context context = this.this$0.context;
        Intent intent = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
        LabsViewModel labsViewModel = this.this$0;
        boolean z = this.$isDex;
        intent.setPackage(labsViewModel.context.getPackageName());
        intent.addFlags(268435456);
        intent.putExtra(z ? "extra_is_dex" : "extra_is_cover", true);
        intent.putExtra("android.intent.extra.FREEFORM_WINDOW", true);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        LabsViewModel labsViewModel2 = this.this$0;
        boolean z2 = this.$isDex;
        int i = labsViewModel2.context.getResources().getDisplayMetrics().widthPixels;
        int i2 = labsViewModel2.context.getResources().getDisplayMetrics().heightPixels;
        float f = labsViewModel2.context.getResources().getDisplayMetrics().density;
        Rect rect = new Rect();
        float f2 = i;
        rect.union(MathKt__MathJVMKt.roundToInt(z2 ? f2 * 0.8f : Math.min(f2, 352.0f * f)), MathKt__MathJVMKt.roundToInt(z2 ? i2 * 0.8f : Math.min(i2, f * 337.51f)));
        rect.offset((i - rect.width()) / 2, (i2 - rect.height()) / 2);
        makeBasic.setLaunchBounds(rect);
        Unit unit = Unit.INSTANCE;
        context.startActivity(intent, makeBasic.toBundle());
        return Unit.INSTANCE;
    }
}
