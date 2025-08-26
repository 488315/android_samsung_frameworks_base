package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.MutableState;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaCardKt$AudioPathSection$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $isLogged$delegate;
    final /* synthetic */ List<RouteDevice> $routeDevices;
    final /* synthetic */ MutableState<List<String>> $suggestedDevices$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaCardKt$AudioPathSection$1$1(List<? extends RouteDevice> list, MutableState<Boolean> mutableState, MutableState<List<String>> mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$routeDevices = list;
        this.$isLogged$delegate = mutableState;
        this.$suggestedDevices$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$AudioPathSection$1$1(this.$routeDevices, this.$isLogged$delegate, this.$suggestedDevices$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$AudioPathSection$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String suggestType;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<RouteDevice> list = this.$routeDevices;
        if (list == null) {
            return Unit.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            if (((RouteDevice) obj2).isSuggested()) {
                arrayList.add(obj2);
            }
        }
        MutableState<Boolean> mutableState = this.$isLogged$delegate;
        if (!((Boolean) mutableState.getValue()).booleanValue()) {
            mutableState.setValue(Boolean.TRUE);
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.DisplayedSuggestedDevice displayedSuggestedDevice = SaEvent.DisplayedSuggestedDevice.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.Value(!arrayList.isEmpty() ? "Yes" : "No")};
            moSaLogging.getClass();
            MoSaLogging.send(displayedSuggestedDevice, saCustomArr);
        }
        List list2 = (List) this.$suggestedDevices$delegate.getValue();
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            arrayList2.add(((RouteDevice) obj3).getId());
        }
        if (Intrinsics.areEqual(list2, arrayList2)) {
            arrayList = null;
        }
        if (arrayList != null) {
            MutableState<List<String>> mutableState2 = this.$suggestedDevices$delegate;
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            int size2 = arrayList.size();
            while (i < size2) {
                Object obj4 = arrayList.get(i);
                i++;
                arrayList3.add(((RouteDevice) obj4).getId());
            }
            mutableState2.setValue(arrayList3);
            RouteDevice routeDevice = (RouteDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
            if (routeDevice != null && (suggestType = routeDevice.getSuggestType()) != null) {
                MoSaLogging moSaLogging2 = MoSaLogging.INSTANCE;
                SaEvent.TypeOfSuggestion typeOfSuggestion = SaEvent.TypeOfSuggestion.INSTANCE;
                SaCustom[] saCustomArr2 = {new SaCustom.Type(suggestType)};
                moSaLogging2.getClass();
                MoSaLogging.send(typeOfSuggestion, saCustomArr2);
            }
        }
        return Unit.INSTANCE;
    }
}
