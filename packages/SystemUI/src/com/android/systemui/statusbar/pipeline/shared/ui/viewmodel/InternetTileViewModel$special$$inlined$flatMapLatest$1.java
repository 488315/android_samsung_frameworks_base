package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.InternetTileViewModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class InternetTileViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetTileViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetTileViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, InternetTileViewModel internetTileViewModel) {
        super(3, continuation);
        this.this$0 = internetTileViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetTileViewModel$special$$inlined$flatMapLatest$1 internetTileViewModel$special$$inlined$flatMapLatest$1 = new InternetTileViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        internetTileViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        internetTileViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return internetTileViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        String str;
        String substring;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$1;
            WifiIcon.Companion companion = WifiIcon.Companion;
            Context context = this.this$0.context;
            companion.getClass();
            WifiIcon fromModel = WifiIcon.Companion.fromModel(wifiNetworkModel, context, true, null);
            if ((wifiNetworkModel instanceof WifiNetworkModel.Active) && (fromModel instanceof WifiIcon.Visible)) {
                InternetTileViewModel.Companion companion2 = InternetTileViewModel.Companion;
                String str2 = ((WifiNetworkModel.Active) wifiNetworkModel).ssid;
                companion2.getClass();
                if (str2 != null) {
                    int length = str2.length();
                    if (length > 1 && str2.charAt(0) == '\"') {
                        int i2 = length - 1;
                        substring = str2.charAt(i2) == '\"' ? str2.substring(1, i2) : null;
                    }
                    str = str2;
                    WifiIcon.Visible visible = (WifiIcon.Visible) fromModel;
                    flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new InternetTileModel.Active(str, null, null, QSTileImpl.ResourceIcon.get(visible.icon.res), visible.contentDescription, new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.internetLabel, ",", str)), 6, null));
                }
                str = substring;
                WifiIcon.Visible visible2 = (WifiIcon.Visible) fromModel;
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new InternetTileModel.Active(str, null, null, QSTileImpl.ResourceIcon.get(visible2.icon.res), visible2.contentDescription, new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.internetLabel, ",", str)), 6, null));
            } else {
                flow = this.this$0.notConnectedFlow;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
