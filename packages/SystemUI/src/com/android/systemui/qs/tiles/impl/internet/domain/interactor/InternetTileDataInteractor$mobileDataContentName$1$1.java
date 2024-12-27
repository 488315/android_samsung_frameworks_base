package com.android.systemui.qs.tiles.impl.internet.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.qs.tiles.impl.internet.domain.interactor.InternetTileDataInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class InternetTileDataInteractor$mobileDataContentName$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ InternetTileDataInteractor this$0;

    public InternetTileDataInteractor$mobileDataContentName$1$1(InternetTileDataInteractor internetTileDataInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = internetTileDataInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        InternetTileDataInteractor$mobileDataContentName$1$1 internetTileDataInteractor$mobileDataContentName$1$1 = new InternetTileDataInteractor$mobileDataContentName$1$1(this.this$0, (Continuation) obj3);
        internetTileDataInteractor$mobileDataContentName$1$1.Z$0 = booleanValue;
        internetTileDataInteractor$mobileDataContentName$1$1.L$0 = (NetworkTypeIconModel) obj2;
        return internetTileDataInteractor$mobileDataContentName$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) this.L$0;
        InternetTileDataInteractor internetTileDataInteractor = this.this$0;
        int contentDescription = networkTypeIconModel.getContentDescription();
        if (contentDescription != 0) {
            str = internetTileDataInteractor.context.getString(contentDescription);
        } else {
            InternetTileDataInteractor.Companion companion = InternetTileDataInteractor.Companion;
            internetTileDataInteractor.getClass();
            str = null;
        }
        if (!z) {
            return str;
        }
        String string = this.this$0.context.getString(R.string.data_connection_roaming);
        return str != null ? this.this$0.context.getString(R.string.mobile_data_text_format, string, str) : string;
    }
}
