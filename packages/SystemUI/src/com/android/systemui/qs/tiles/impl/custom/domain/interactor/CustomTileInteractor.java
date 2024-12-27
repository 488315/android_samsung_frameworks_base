package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

public final class CustomTileInteractor {
    public final CoroutineContext backgroundContext;
    public UserHandle currentUser;
    public final CustomTileRepository customTileRepository;
    public final CustomTileDefaultsRepository defaultsRepository;
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;
    public Job updatesJob;
    public final MutexImpl userMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileUpdates = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    public CustomTileInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileRepository customTileRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.customTileRepository = customTileRepository;
        this.tileScope = coroutineScope;
        this.backgroundContext = coroutineContext;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object initForUser(android.os.UserHandle r12, kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor.initForUser(android.os.UserHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
