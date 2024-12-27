package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import android.content.Context;
import android.text.Html;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.SignalIcon;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.InternetTileViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class InternetTileViewModel$mobileIconFlow$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ InternetTileViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetTileViewModel$mobileIconFlow$1$1(InternetTileViewModel internetTileViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = internetTileViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        InternetTileViewModel$mobileIconFlow$1$1 internetTileViewModel$mobileIconFlow$1$1 = new InternetTileViewModel$mobileIconFlow$1$1(this.this$0, (Continuation) obj4);
        internetTileViewModel$mobileIconFlow$1$1.L$0 = (NetworkNameModel) obj;
        internetTileViewModel$mobileIconFlow$1$1.L$1 = (SignalIconModel) obj2;
        internetTileViewModel$mobileIconFlow$1$1.L$2 = (CharSequence) obj3;
        return internetTileViewModel$mobileIconFlow$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int state;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NetworkNameModel networkNameModel = (NetworkNameModel) this.L$0;
        SignalIconModel signalIconModel = (SignalIconModel) this.L$1;
        CharSequence charSequence = (CharSequence) this.L$2;
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
            return new InternetTileModel.Active(loadContentDescription, null, new Integer(satellite.icon.res), null, new ContentDescription.Loaded(loadContentDescription), new ContentDescription.Loaded(this.this$0.internetLabel), 10, null);
        }
        InternetTileViewModel internetTileViewModel = this.this$0;
        CharSequence name = networkNameModel.getName();
        InternetTileViewModel.Companion companion2 = InternetTileViewModel.Companion;
        internetTileViewModel.getClass();
        if (charSequence != null) {
            name = name == null ? Html.fromHtml(charSequence.toString(), 0) : Html.fromHtml(internetTileViewModel.context.getString(R.string.mobile_carrier_text_format, name, charSequence), 0);
        } else if (name == null) {
            name = "";
        }
        CharSequence charSequence2 = name;
        SignalIconModel.Cellular cellular = (SignalIconModel.Cellular) signalIconModel;
        boolean z = cellular.carrierNetworkChange;
        int i = cellular.numberOfLevels;
        if (z) {
            int i2 = SignalDrawable.ICON_RES;
            state = (i << 8) | 196608;
        } else {
            state = SignalDrawable.getState(cellular.level, i, cellular.showExclamationMark);
        }
        return new InternetTileModel.Active(charSequence2, null, null, new SignalIcon(state), new ContentDescription.Loaded(charSequence2.toString()), new ContentDescription.Loaded(this.this$0.internetLabel), 6, null);
    }
}
