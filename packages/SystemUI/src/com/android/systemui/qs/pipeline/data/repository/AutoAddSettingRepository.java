package com.android.systemui.qs.pipeline.data.repository;

import android.util.SparseArray;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object emit;
        UserAutoAddRepository userAutoAddRepository = (UserAutoAddRepository) this.userAutoAddRepositories.get(i);
        if (userAutoAddRepository == null) {
            return Unit.INSTANCE;
        }
        if (tileSpec instanceof TileSpec.Invalid) {
            emit = Unit.INSTANCE;
        } else {
            emit = userAutoAddRepository.changeEvents.emit(new UserAutoAddRepository.MarkTile(tileSpec), continuation);
            if (emit != CoroutineSingletons.COROUTINE_SUSPENDED) {
                emit = Unit.INSTANCE;
            }
        }
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }

    public final Object unmarkTileAdded(int i, TileSpec tileSpec, Continuation continuation) {
        Object emit;
        UserAutoAddRepository userAutoAddRepository = (UserAutoAddRepository) this.userAutoAddRepositories.get(i);
        if (userAutoAddRepository == null) {
            return Unit.INSTANCE;
        }
        if (tileSpec instanceof TileSpec.Invalid) {
            emit = Unit.INSTANCE;
        } else {
            emit = userAutoAddRepository.changeEvents.emit(new UserAutoAddRepository.UnmarkTile(tileSpec), continuation);
            if (emit != CoroutineSingletons.COROUTINE_SUSPENDED) {
                emit = Unit.INSTANCE;
            }
        }
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }
}
