package com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor;

import android.app.UiModeManager;
import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.model.UiModeNightTileModel;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class UiModeNightTileDataInteractor implements QSTileDataInteractor {
    public final BatteryController batteryController;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DateFormatUtil dateFormatUtil;
    public final LocationController locationController;
    public final UiModeManager uiModeManager;

    /* renamed from: com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = UiModeNightTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$configurationCallback$1, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$batteryCallback$1, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$locationCallback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(UiModeNightTileDataInteractor.access$createModel(UiModeNightTileDataInteractor.this));
                final UiModeNightTileDataInteractor uiModeNightTileDataInteractor = UiModeNightTileDataInteractor.this;
                final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$configurationCallback$1
                    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                    public final void onUiModeChanged() {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(UiModeNightTileDataInteractor.access$createModel(uiModeNightTileDataInteractor));
                    }
                };
                ((ConfigurationControllerImpl) UiModeNightTileDataInteractor.this.configurationController).addCallback(r1);
                final UiModeNightTileDataInteractor uiModeNightTileDataInteractor2 = UiModeNightTileDataInteractor.this;
                final ?? r3 = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$batteryCallback$1
                    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
                    public final void onPowerSaveChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(UiModeNightTileDataInteractor.access$createModel(uiModeNightTileDataInteractor2));
                    }
                };
                ((BatteryControllerImpl) UiModeNightTileDataInteractor.this.batteryController).addCallback(r3);
                final UiModeNightTileDataInteractor uiModeNightTileDataInteractor3 = UiModeNightTileDataInteractor.this;
                final ?? r4 = new LocationController.LocationChangeCallback() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$locationCallback$1
                    @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
                    public final void onLocationSettingsChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(UiModeNightTileDataInteractor.access$createModel(uiModeNightTileDataInteractor3));
                    }
                };
                ((LocationControllerImpl) UiModeNightTileDataInteractor.this.locationController).addCallback(r4);
                final UiModeNightTileDataInteractor uiModeNightTileDataInteractor4 = UiModeNightTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.UiModeNightTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        UiModeNightTileDataInteractor uiModeNightTileDataInteractor5 = uiModeNightTileDataInteractor4;
                        ((ConfigurationControllerImpl) uiModeNightTileDataInteractor5.configurationController).removeCallback(r1);
                        ((BatteryControllerImpl) uiModeNightTileDataInteractor5.batteryController).removeCallback(r3);
                        ((LocationControllerImpl) uiModeNightTileDataInteractor5.locationController).removeCallback(r4);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

    public UiModeNightTileDataInteractor(Context context, ConfigurationController configurationController, UiModeManager uiModeManager, BatteryController batteryController, LocationController locationController, DateFormatUtil dateFormatUtil) {
        this.context = context;
        this.configurationController = configurationController;
        this.uiModeManager = uiModeManager;
        this.batteryController = batteryController;
        this.locationController = locationController;
        this.dateFormatUtil = dateFormatUtil;
    }

    public static final UiModeNightTileModel access$createModel(UiModeNightTileDataInteractor uiModeNightTileDataInteractor) {
        return new UiModeNightTileModel(uiModeNightTileDataInteractor.uiModeManager.getNightMode(), (uiModeNightTileDataInteractor.context.getResources().getConfiguration().uiMode & 48) == 32, ((BatteryControllerImpl) uiModeNightTileDataInteractor.batteryController).mPowerSave, ((LocationControllerImpl) uiModeNightTileDataInteractor.locationController).isLocationEnabled$1(), uiModeNightTileDataInteractor.uiModeManager.getNightModeCustomType(), uiModeNightTileDataInteractor.dateFormatUtil.is24HourFormat(), uiModeNightTileDataInteractor.uiModeManager.getCustomNightModeEnd(), uiModeNightTileDataInteractor.uiModeManager.getCustomNightModeStart());
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
    }
}
