package com.android.systemui.qs.tiles.impl.modes.domain.interactor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.ModesTile;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class ModesTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public final ZenModeInteractor zenModeInteractor;

    /* renamed from: com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return ModesTileDataInteractor.this.getCurrentTileModel(this);
        }
    }

    public ModesTileDataInteractor(Context context, ZenModeInteractor zenModeInteractor, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.zenModeInteractor = zenModeInteractor;
        this.bgDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }

    public final ModesTileModel buildTileData(ActiveZenModes activeZenModes) {
        ZenModeInfo zenModeInfo = activeZenModes.mainMode;
        ModesTile.Companion.getClass();
        int i = ModesTile.ICON_RES_ID;
        Integer numValueOf = Integer.valueOf(i);
        Drawable drawable = this.context.getDrawable(i);
        drawable.getClass();
        return new ModesTileModel(!activeZenModes.modeNames.isEmpty(), activeZenModes.modeNames, new Icon.Loaded(drawable, null, numValueOf));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getCurrentTileModel(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objBuildActiveZenModes = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objBuildActiveZenModes);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            ZenModeInteractor zenModeInteractor = this.zenModeInteractor;
            objBuildActiveZenModes = zenModeInteractor.buildActiveZenModes(((ZenModeRepositoryImpl) zenModeInteractor.zenModeRepository).backend.getModes(), anonymousClass1);
            if (objBuildActiveZenModes == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (ModesTileDataInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objBuildActiveZenModes);
        }
        return this.buildTileData((ActiveZenModes) objBuildActiveZenModes);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(new ModesTileDataInteractor$tileData$$inlined$map$1(this.zenModeInteractor.activeModes, this), this.bgDispatcher));
    }
}
