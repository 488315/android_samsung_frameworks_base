package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.sec.ims.settings.ImsProfile;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultLargeTilesRepositoryImpl implements DefaultLargeTilesRepository {
    public final Set defaultLargeTiles;

    public DefaultLargeTilesRepositoryImpl() {
        TileSpec.Companion.getClass();
        this.defaultLargeTiles = ArraysKt___ArraysKt.toSet(new TileSpec[]{TileSpec.Companion.create(ImsProfile.PDN_INTERNET), TileSpec.Companion.create("bt"), TileSpec.Companion.create("dnd"), TileSpec.Companion.create("cast")});
    }
}
