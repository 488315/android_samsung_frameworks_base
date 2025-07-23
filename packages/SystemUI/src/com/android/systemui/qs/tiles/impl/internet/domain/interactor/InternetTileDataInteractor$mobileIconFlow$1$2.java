package com.android.systemui.qs.tiles.impl.internet.domain.interactor;

import android.content.Context;
import android.text.Html;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.qs.tiles.impl.internet.domain.interactor.InternetTileDataInteractor;
import com.android.systemui.qs.tiles.impl.internet.domain.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileIconModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class InternetTileDataInteractor$mobileIconFlow$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ InternetTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetTileDataInteractor$mobileIconFlow$1$2(InternetTileDataInteractor internetTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = internetTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        InternetTileDataInteractor$mobileIconFlow$1$2 internetTileDataInteractor$mobileIconFlow$1$2 = new InternetTileDataInteractor$mobileIconFlow$1$2(this.this$0, continuation);
        internetTileDataInteractor$mobileIconFlow$1$2.L$0 = obj;
        return internetTileDataInteractor$mobileIconFlow$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InternetTileDataInteractor$mobileIconFlow$1$2) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Triple triple = (Triple) this.L$0;
        NetworkNameModel networkNameModel = (NetworkNameModel) triple.component1();
        SignalIconModel signalIconModel = (SignalIconModel) triple.component2();
        CharSequence charSequence = (CharSequence) triple.component3();
        if (!(signalIconModel instanceof SignalIconModel.Cellular)) {
            if (!(signalIconModel instanceof SignalIconModel.Satellite)) {
                throw new NoWhenBranchMatchedException();
            }
            ContentDescription.Companion companion = ContentDescription.Companion;
            SignalIconModel.Satellite satellite = (SignalIconModel.Satellite) signalIconModel;
            ContentDescription contentDescription = satellite.icon.contentDescription;
            Context context = this.this$0.context;
            companion.getClass();
            String loadContentDescription = ContentDescription.Companion.loadContentDescription(contentDescription, context);
            return new InternetTileModel.Active(loadContentDescription, null, new InternetTileIconModel.Satellite(satellite.icon), new ContentDescription.Loaded(loadContentDescription), new ContentDescription.Loaded(this.this$0.internetLabel), 2, null);
        }
        InternetTileDataInteractor internetTileDataInteractor = this.this$0;
        CharSequence name = networkNameModel.getName();
        InternetTileDataInteractor.Companion companion2 = InternetTileDataInteractor.Companion;
        internetTileDataInteractor.getClass();
        if (charSequence != null) {
            name = name == null ? Html.fromHtml(charSequence.toString(), 0) : Html.fromHtml(internetTileDataInteractor.context.getString(R.string.mobile_carrier_text_format, name, charSequence), 0);
        } else if (name == null) {
            name = "";
        }
        CharSequence charSequence2 = name;
        return new InternetTileModel.Active(charSequence2, null, new InternetTileIconModel.Cellular(((SignalIconModel.Cellular) signalIconModel).level), new ContentDescription.Loaded(charSequence2.toString()), new ContentDescription.Loaded(this.this$0.internetLabel), 2, null);
    }
}
