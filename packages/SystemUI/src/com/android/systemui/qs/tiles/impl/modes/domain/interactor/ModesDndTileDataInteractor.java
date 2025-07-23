package com.android.systemui.qs.tiles.impl.modes.domain.interactor;

import android.content.Context;
import android.os.UserHandle;
import com.android.settingslib.notification.modes.ZenModeDescriptions;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ModesDndTileDataInteractor implements QSTileDataInteractor {
    public final ZenModeDescriptions zenModeDescriptions;
    public final ZenModeInteractor zenModeInteractor;

    public ModesDndTileDataInteractor(Context context, ZenModeInteractor zenModeInteractor, CoroutineDispatcher coroutineDispatcher) {
        this.zenModeInteractor = zenModeInteractor;
        this.zenModeDescriptions = new ZenModeDescriptions(context);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(false);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        this.zenModeInteractor.getClass();
        ZenModeInteractor.getDndMode();
        throw null;
    }
}
