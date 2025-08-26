package com.android.systemui.qs.pipeline.data.repository;

import android.util.SparseArray;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
public final class AutoAddSettingRepository implements AutoAddRepository {
    public final SparseArray userAutoAddRepositories = new SparseArray();
    public final UserAutoAddRepository.Factory userAutoAddRepositoryFactory;

    public AutoAddSettingRepository(UserAutoAddRepository.Factory factory) {
        this.userAutoAddRepositoryFactory = factory;
    }

    public final Object autoAddedTiles(int i, ContinuationImpl continuationImpl) {
        if (!this.userAutoAddRepositories.contains(i)) {
            this.userAutoAddRepositories.put(i, this.userAutoAddRepositoryFactory.create(i));
        }
        return ((UserAutoAddRepository) this.userAutoAddRepositories.get(i)).autoAdded(continuationImpl);
    }

    public final Object markTileAdded(int i, TileSpec tileSpec, Continuation continuation) {
        Object objEmit;
        UserAutoAddRepository userAutoAddRepository = (UserAutoAddRepository) this.userAutoAddRepositories.get(i);
        if (userAutoAddRepository == null) {
            return Unit.INSTANCE;
        }
        if ((tileSpec instanceof TileSpec.Invalid) || (objEmit = userAutoAddRepository.changeEvents.emit(new UserAutoAddRepository.MarkTile(tileSpec), continuation)) != CoroutineSingletons.COROUTINE_SUSPENDED) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
    }

    public final Object unmarkTileAdded(int i, TileSpec tileSpec, Continuation continuation) {
        Object objEmit;
        UserAutoAddRepository userAutoAddRepository = (UserAutoAddRepository) this.userAutoAddRepositories.get(i);
        if (userAutoAddRepository == null) {
            return Unit.INSTANCE;
        }
        if ((tileSpec instanceof TileSpec.Invalid) || (objEmit = userAutoAddRepository.changeEvents.emit(new UserAutoAddRepository.UnmarkTile(tileSpec), continuation)) != CoroutineSingletons.COROUTINE_SUSPENDED) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
    }
}
