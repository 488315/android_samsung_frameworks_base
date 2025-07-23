package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TileSpecRepository {
    Object addTile(int i, TileSpec tileSpec, int i2, SuspendLambda suspendLambda);

    Object prependDefault(int i, SuspendLambda suspendLambda);

    Unit reconcileRestore();

    Object removeTiles(int i, Collection collection, SuspendLambda suspendLambda);

    Object resetToDefault(int i, SuspendLambda suspendLambda);

    Object setTiles(int i, List list, SuspendLambda suspendLambda);

    Object tilesSpecs(int i, ContinuationImpl continuationImpl);
}
