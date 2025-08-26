package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class SecBlurCustomColorInteractor$hasCustomColorApplied$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SecBlurCustomColorInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecBlurCustomColorInteractor$hasCustomColorApplied$1(SecBlurCustomColorInteractor secBlurCustomColorInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = secBlurCustomColorInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        SecBlurCustomColorInteractor$hasCustomColorApplied$1 secBlurCustomColorInteractor$hasCustomColorApplied$1 = new SecBlurCustomColorInteractor$hasCustomColorApplied$1(this.this$0, (Continuation) obj3);
        secBlurCustomColorInteractor$hasCustomColorApplied$1.L$0 = (Triple) obj;
        secBlurCustomColorInteractor$hasCustomColorApplied$1.Z$0 = zBooleanValue;
        return secBlurCustomColorInteractor$hasCustomColorApplied$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0095  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        boolean z2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Triple triple = (Triple) this.L$0;
        boolean z3 = this.Z$0;
        if (z3) {
            z = true;
        } else {
            if (!this.this$0.context.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                SecBlurCustomColorInteractor secBlurCustomColorInteractor = this.this$0;
                int iIntValue = ((Number) triple.getFirst()).intValue();
                secBlurCustomColorInteractor.getClass();
                if ((iIntValue & 32) != 0) {
                }
            }
            z = false;
        }
        StringBuilder sb = new StringBuilder("configurationChanged = ");
        sb.append(triple);
        sb.append(" , minimalBatteryUse = ");
        sb.append(z3);
        sb.append(", needToIgnore = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "SecBlurCustomColorInteractor");
        this.this$0.updateBackgroundColor.tryEmit(Boolean.TRUE);
        SecBlurCustomColorInteractor secBlurCustomColorInteractor2 = this.this$0;
        Context context = secBlurCustomColorInteractor2.context;
        int i = SecBlurCustomColorInteractor.backgroundColorId;
        if (Intrinsics.areEqual(Integer.toHexString(context.getColor(i)), "ff0f0f0f")) {
            if (Intrinsics.areEqual(Integer.toHexString(secBlurCustomColorInteractor2.context.getColor(SecBlurCustomColorInteractor.bgOverlayColorId)), Integer.toHexString(secBlurCustomColorInteractor2.context.getColor(i)))) {
                z2 = z ? false : true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
