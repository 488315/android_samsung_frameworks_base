package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.sec.ims.settings.ImsProfile;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes2.dex */
public final class DefaultLargeTilesRepositoryImpl implements DefaultLargeTilesRepository {
    public final Set defaultLargeTiles;

    public DefaultLargeTilesRepositoryImpl() {
        TileSpec.Companion.getClass();
        this.defaultLargeTiles = ArraysKt___ArraysKt.toSet(new TileSpec[]{TileSpec.Companion.create(ImsProfile.PDN_INTERNET), TileSpec.Companion.create("bt"), TileSpec.Companion.create("dnd"), TileSpec.Companion.create("cast")});
    }
}
