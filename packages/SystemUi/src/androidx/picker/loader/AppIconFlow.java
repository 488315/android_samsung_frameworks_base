package androidx.picker.loader;

import android.graphics.drawable.Drawable;
import androidx.picker.features.observable.UpdateMutableState;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppIconFlow implements Flow {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final UpdateMutableState base;
    public final Flow defaultIconFlow;

    static {
        MutablePropertyReference0Impl mutablePropertyReference0Impl = new MutablePropertyReference0Impl(AppIconFlow.class, "icon", "<v#0>", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference0Impl};
    }

    public AppIconFlow(UpdateMutableState updateMutableState, Flow flow) {
        this.base = updateMutableState;
        this.defaultIconFlow = flow;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(final FlowCollector flowCollector, Continuation continuation) {
        AppIconFlow$collect$1 appIconFlow$collect$1;
        int i;
        final UpdateMutableState updateMutableState;
        Unit unit;
        if (continuation instanceof AppIconFlow$collect$1) {
            appIconFlow$collect$1 = (AppIconFlow$collect$1) continuation;
            int i2 = appIconFlow$collect$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                appIconFlow$collect$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = appIconFlow$collect$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = appIconFlow$collect$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    KProperty kProperty = $$delegatedProperties[0];
                    updateMutableState = this.base;
                    Drawable drawable = (Drawable) updateMutableState.getValue();
                    if (drawable == null) {
                        unit = null;
                        if (unit != null) {
                            return Unit.INSTANCE;
                        }
                        Flow flow = this.defaultIconFlow;
                        FlowCollector flowCollector2 = new FlowCollector() { // from class: androidx.picker.loader.AppIconFlow$collect$3
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation2) {
                                Drawable drawable2 = (Drawable) obj2;
                                KProperty kProperty2 = AppIconFlow.$$delegatedProperties[0];
                                updateMutableState.setValue(drawable2);
                                Object emit = FlowCollector.this.emit(drawable2, continuation2);
                                return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
                            }
                        };
                        appIconFlow$collect$1.L$0 = null;
                        appIconFlow$collect$1.L$1 = null;
                        appIconFlow$collect$1.L$2 = null;
                        appIconFlow$collect$1.label = 2;
                        if (flow.collect(flowCollector2, appIconFlow$collect$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                    appIconFlow$collect$1.L$0 = this;
                    appIconFlow$collect$1.L$1 = flowCollector;
                    appIconFlow$collect$1.L$2 = updateMutableState;
                    appIconFlow$collect$1.label = 1;
                    if (flowCollector.emit(drawable, appIconFlow$collect$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    UpdateMutableState updateMutableState2 = (UpdateMutableState) appIconFlow$collect$1.L$2;
                    flowCollector = (FlowCollector) appIconFlow$collect$1.L$1;
                    AppIconFlow appIconFlow = (AppIconFlow) appIconFlow$collect$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    updateMutableState = updateMutableState2;
                    this = appIconFlow;
                }
                unit = Unit.INSTANCE;
                if (unit != null) {
                }
            }
        }
        appIconFlow$collect$1 = new AppIconFlow$collect$1(this, continuation);
        Object obj2 = appIconFlow$collect$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = appIconFlow$collect$1.label;
        if (i != 0) {
        }
        unit = Unit.INSTANCE;
        if (unit != null) {
        }
    }
}
