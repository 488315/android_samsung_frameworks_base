package com.android.systemui.qs.tiles.impl.modes.domain.interactor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.ModesTile;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import com.android.systemui.statusbar.policy.domain.model.ZenModeInfo;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public final ZenModeInteractor zenModeInteractor;

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
        Integer valueOf = Integer.valueOf(i);
        Drawable drawable = this.context.getDrawable(i);
        drawable.getClass();
        return new ModesTileModel(!activeZenModes.modeNames.isEmpty(), activeZenModes.modeNames, new Icon.Loaded(drawable, null, valueOf));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getCurrentTileModel(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1 r0 = (com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1 r0 = new com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor$getCurrentTileModel$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor r4 = (com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4d
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor r5 = r4.zenModeInteractor
            com.android.settingslib.notification.data.repository.ZenModeRepository r2 = r5.zenModeRepository
            com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl r2 = (com.android.settingslib.notification.data.repository.ZenModeRepositoryImpl) r2
            com.android.settingslib.notification.modes.ZenModesBackend r2 = r2.backend
            java.util.List r2 = r2.getModes()
            java.lang.Object r5 = r5.buildActiveZenModes(r2, r0)
            if (r5 != r1) goto L4d
            return r1
        L4d:
            com.android.systemui.statusbar.policy.domain.model.ActiveZenModes r5 = (com.android.systemui.statusbar.policy.domain.model.ActiveZenModes) r5
            com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel r4 = r4.buildTileData(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor.getCurrentTileModel(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(new ModesTileDataInteractor$tileData$$inlined$map$1(this.zenModeInteractor.activeModes, this), this.bgDispatcher));
    }
}
