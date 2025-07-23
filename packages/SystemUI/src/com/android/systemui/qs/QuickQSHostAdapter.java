package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickQSHostAdapter implements QSHost {
    public final Map callbacksMap = new LinkedHashMap();
    public final Context context;
    public final CurrentTilesInteractor interactor;
    public final CoroutineScope scope;
    public final TileFeatureChecker tileFeatureChecker;

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

    public QuickQSHostAdapter(CurrentTilesInteractor currentTilesInteractor, Context context, CoroutineScope coroutineScope, DumpManager dumpManager, TileFeatureChecker tileFeatureChecker) {
        this.interactor = currentTilesInteractor;
        this.context = context;
        this.scope = coroutineScope;
        this.tileFeatureChecker = tileFeatureChecker;
        dumpManager.registerNormalDumpable("QuickQSHost", currentTilesInteractor);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        StandaloneCoroutine launch$default = BuildersKt.launch$default(this.scope, null, null, new QuickQSHostAdapter$addCallback$job$1(this, callback, null), 3);
        synchronized (this.callbacksMap) {
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        TileSpec.Companion.getClass();
        this.interactor.addTile(TileSpec.Companion.create(componentName), z ? -1 : 0);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2) {
        List<String> list3 = list2;
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        for (String str : list3) {
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str));
        }
        this.interactor.setTiles(arrayList);
    }

    @Override // com.android.systemui.qs.QSHost
    public final QSTile createTile(String str) {
        TileSpec.Companion.getClass();
        return this.interactor.createTileSync(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final ArrayList getBarTilesByType(int i, int i2) {
        return this.interactor.getBarTilesByType(i, i2);
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getContext() {
        return this.context;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getDefaultTileList() {
        List defaultTiles = this.interactor.getDefaultTiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(defaultTiles, 10));
        Iterator it = defaultTiles.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileSpec) it.next()).getSpec());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getSpecs() {
        List currentTilesSpecs = this.interactor.getCurrentTilesSpecs();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(currentTilesSpecs, 10));
        ArrayList arrayList2 = (ArrayList) currentTilesSpecs;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(((TileSpec) obj).getSpec());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost
    public final Collection getTiles() {
        return this.interactor.getCurrentQSTiles();
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getUserContext() {
        return (Context) this.interactor.getUserContext().getValue();
    }

    @Override // com.android.systemui.qs.QSHost
    public final int getUserId() {
        return ((Number) this.interactor.getUserId().getValue()).intValue();
    }

    @Override // com.android.systemui.qs.QSHost
    public final int indexOf(String str) {
        return ((ArrayList) getSpecs()).indexOf(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableCustomTile(String str) {
        TileSpec.Companion.getClass();
        return this.tileFeatureChecker.isAvailableCustomTile(TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableForSearch(String str) {
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        synchronized (this.callbacksMap) {
            Job job = (Job) this.callbacksMap.remove(callback);
            if (job != null) {
                job.cancel(null);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTile(String str) {
        TileSpec.Companion.getClass();
        this.interactor.removeTiles(Collections.singletonList(TileSpec.Companion.create(str)));
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        TileSpec.Companion.getClass();
        this.interactor.removeTiles(Collections.singletonList(TileSpec.Companion.create(componentName)));
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldBeHiddenByKnox(String str) {
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldUnavailableByKnox(String str) {
        return false;
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2, boolean z) {
        TileSpec.Companion companion = TileSpec.Companion;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            companion.getClass();
            arrayList.add(TileSpec.Companion.create(str));
        }
        this.interactor.setTiles(arrayList);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void refreshTileList() {
    }
}
