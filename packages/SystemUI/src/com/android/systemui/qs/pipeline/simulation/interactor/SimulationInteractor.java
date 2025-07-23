package com.android.systemui.qs.pipeline.simulation.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.ScRune;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractor;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SimulationInteractor {
    public final TilesBackUpRestoreInteractor backUpRestoreInteractor;
    public final CoroutineScope scope;
    public final TestTileDataRepository testTileDataRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SimulationInteractor(TestTileDataRepository testTileDataRepository, TilesBackUpRestoreInteractor tilesBackUpRestoreInteractor, CoroutineScope coroutineScope) {
        this.testTileDataRepository = testTileDataRepository;
        this.backUpRestoreInteractor = tilesBackUpRestoreInteractor;
        this.scope = coroutineScope;
    }

    public final void init() {
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            BuildersKt.launch$default(this.scope, null, null, new SimulationInteractor$init$1(this, null), 3);
        }
    }

    public final String makeRestoreData(String str) {
        int hashCode = str.hashCode();
        TestTileDataRepository testTileDataRepository = this.testTileDataRepository;
        switch (hashCode) {
            case -1289769360:
                if (!str.equals("removed_tile_list")) {
                    return "";
                }
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m.append((tileDataSource != null ? tileDataSource : null).getRemovedTiles());
                return m.toString();
            case -1020575241:
                if (!str.equals("sep_version")) {
                    return "";
                }
                StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource2 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m2.append((tileDataSource2 != null ? tileDataSource2 : null).getSepVersion());
                return m2.toString();
            case -851078257:
                if (!str.equals("tile_list")) {
                    return "";
                }
                StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource3 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m3.append((tileDataSource3 != null ? tileDataSource3 : null).getTiles());
                return m3.toString();
            case 75675811:
                if (!str.equals("qqs_tile_list")) {
                    return "";
                }
                StringBuilder m4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource4 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m4.append((tileDataSource4 != null ? tileDataSource4 : null).getQqsTiles());
                return m4.toString();
            case 432981722:
                if (!str.equals("qqs_has_edited")) {
                    return "";
                }
                StringBuilder m5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource5 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m5.append((tileDataSource5 != null ? tileDataSource5 : null).getQqsEdited());
                return m5.toString();
            case 1768376686:
                if (!str.equals("has_edited")) {
                    return "";
                }
                StringBuilder m6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource6 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                m6.append((tileDataSource6 != null ? tileDataSource6 : null).getQsEdited());
                return m6.toString();
            default:
                return "";
        }
    }
}
