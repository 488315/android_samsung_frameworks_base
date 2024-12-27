package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.UserHandle;
import com.android.systemui.accessibility.data.repository.NightDisplayRepository;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.util.time.DateFormatUtil;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NightDisplayTileDataInteractor implements QSTileDataInteractor {
    public final Context context;
    public final DateFormatUtil dateFormatUtil;
    public final NightDisplayRepository nightDisplayRepository;

    public NightDisplayTileDataInteractor(Context context, DateFormatUtil dateFormatUtil, NightDisplayRepository nightDisplayRepository) {
        this.context = context;
        this.dateFormatUtil = dateFormatUtil;
        this.nightDisplayRepository = nightDisplayRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(ColorDisplayManager.isNightDisplayAvailable(this.context)));
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        final Flow nightDisplayState = this.nightDisplayRepository.nightDisplayState(userHandle);
        return new Flow() { // from class: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NightDisplayTileDataInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NightDisplayTileDataInteractor nightDisplayTileDataInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = nightDisplayTileDataInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        com.android.systemui.accessibility.data.model.NightDisplayState r11 = (com.android.systemui.accessibility.data.model.NightDisplayState) r11
                        int r12 = r11.autoMode
                        java.time.LocalTime r7 = r11.startTime
                        java.time.LocalTime r8 = r11.endTime
                        com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor r2 = r10.this$0
                        boolean r5 = r11.isActivated
                        boolean r6 = r11.shouldForceAutoMode
                        if (r12 == r3) goto L57
                        r2.getClass()
                        r2 = 2
                        if (r12 == r2) goto L4e
                        com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeOff r11 = new com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeOff
                        r11.<init>(r5, r6)
                        goto L63
                    L4e:
                        com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeTwilight r12 = new com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeTwilight
                        boolean r11 = r11.locationEnabled
                        r12.<init>(r5, r6, r11)
                        r11 = r12
                        goto L63
                    L57:
                        com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeCustom r11 = new com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel$AutoModeCustom
                        com.android.systemui.util.time.DateFormatUtil r12 = r2.dateFormatUtil
                        boolean r9 = r12.is24HourFormat()
                        r4 = r11
                        r4.<init>(r5, r6, r7, r8, r9)
                    L63:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
