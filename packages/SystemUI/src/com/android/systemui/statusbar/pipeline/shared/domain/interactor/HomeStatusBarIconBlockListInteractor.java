package com.android.systemui.statusbar.pipeline.shared.domain.interactor;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeStatusBarIconBlockListInteractor {
    public final String[] defaultBlockedIcons;
    public final HomeStatusBarIconBlockListInteractor$special$$inlined$map$1 iconBlockList;
    public final String vibrateIconSlot;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1] */
    public HomeStatusBarIconBlockListInteractor(Resources resources, SecureSettingsRepository secureSettingsRepository) {
        this.defaultBlockedIcons = resources.getStringArray(R.array.config_collapsed_statusbar_icon_blocklist);
        this.vibrateIconSlot = resources.getString(17043303);
        final Flow boolSetting = secureSettingsRepository.boolSetting("status_bar_show_vibrate_icon", false);
        this.iconBlockList = new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ HomeStatusBarIconBlockListInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, HomeStatusBarIconBlockListInteractor homeStatusBarIconBlockListInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = homeStatusBarIconBlockListInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L63
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor r8 = r6.this$0
                        java.lang.String[] r2 = r8.defaultBlockedIcons
                        java.util.LinkedHashSet r4 = new java.util.LinkedHashSet
                        int r5 = r2.length
                        int r5 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r5)
                        r4.<init>(r5)
                        kotlin.collections.ArraysKt___ArraysKt.toCollection(r4, r2)
                        java.lang.String r8 = r8.vibrateIconSlot
                        if (r7 == 0) goto L51
                        r4.remove(r8)
                        goto L54
                    L51:
                        r4.add(r8)
                    L54:
                        java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L63
                        return r1
                    L63:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.domain.interactor.HomeStatusBarIconBlockListInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
