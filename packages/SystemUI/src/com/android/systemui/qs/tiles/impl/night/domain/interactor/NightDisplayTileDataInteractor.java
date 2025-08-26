package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import android.os.UserHandle;
import com.android.systemui.accessibility.data.model.NightDisplayState;
import com.android.systemui.accessibility.data.repository.NightDisplayRepository;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel;
import com.android.systemui.util.time.DateFormatUtil;
import java.time.LocalTime;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class NightDisplayTileDataInteractor implements QSTileDataInteractor {
    public final Context context;
    public final DateFormatUtil dateFormatUtil;
    public final NightDisplayRepository nightDisplayRepository;

    public NightDisplayTileDataInteractor(Context context, DateFormatUtil dateFormatUtil, NightDisplayRepository nightDisplayRepository) {
        this.context = context;
        this.dateFormatUtil = dateFormatUtil;
        this.nightDisplayRepository = nightDisplayRepository;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(ColorDisplayManager.isNightDisplayAvailable(this.context)));
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        final Flow flowNightDisplayState = this.nightDisplayRepository.nightDisplayState(userHandle);
        return new Flow() { // from class: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1

            /* renamed from: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileDataInteractor$tileData$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NightDisplayTileDataInteractor this$0;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object autoModeCustom;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        NightDisplayState nightDisplayState = (NightDisplayState) obj;
                        int i3 = nightDisplayState.autoMode;
                        LocalTime localTime = nightDisplayState.startTime;
                        LocalTime localTime2 = nightDisplayState.endTime;
                        NightDisplayTileDataInteractor nightDisplayTileDataInteractor = this.this$0;
                        boolean z = nightDisplayState.isActivated;
                        boolean z2 = nightDisplayState.shouldForceAutoMode;
                        if (i3 != 1) {
                            nightDisplayTileDataInteractor.getClass();
                            autoModeCustom = i3 != 2 ? new NightDisplayTileModel.AutoModeOff(z, z2) : new NightDisplayTileModel.AutoModeTwilight(z, z2, nightDisplayState.locationEnabled);
                        } else {
                            autoModeCustom = new NightDisplayTileModel.AutoModeCustom(z, z2, localTime, localTime2, nightDisplayTileDataInteractor.dateFormatUtil.is24HourFormat());
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(autoModeCustom, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowNightDisplayState.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
