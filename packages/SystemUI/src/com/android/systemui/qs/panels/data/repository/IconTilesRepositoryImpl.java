package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.sec.ims.settings.ImsProfile;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class IconTilesRepositoryImpl implements IconTilesRepository {
    public static final Set LARGE_TILES;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TileSpec.Companion.getClass();
        LARGE_TILES = SetsKt__SetsKt.setOf(TileSpec.Companion.create(ImsProfile.PDN_INTERNET), TileSpec.Companion.create("bt"), TileSpec.Companion.create("dnd"), TileSpec.Companion.create("cast"));
    }
}
