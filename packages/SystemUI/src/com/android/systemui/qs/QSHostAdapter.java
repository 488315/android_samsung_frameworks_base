package com.android.systemui.qs;

import android.app.IUriGrantsManager;
import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileRequestDialogEventLogger;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class QSHostAdapter implements QSHost {
    public final Map callbacksMap;
    public final CurrentTilesInteractor interactor;
    public final QSTileHost qsTileHost;
    public final CoroutineScope scope;
    public final TileServiceRequestController.Builder tileServiceRequestControllerBuilder;

    /* renamed from: com.android.systemui.qs.QSHostAdapter$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSHostAdapter.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            QSHostAdapter qSHostAdapter = QSHostAdapter.this;
            TileServiceRequestController.Builder builder = qSHostAdapter.tileServiceRequestControllerBuilder;
            builder.getClass();
            TileRequestDialogEventLogger tileRequestDialogEventLogger = new TileRequestDialogEventLogger();
            IUriGrantsManager iUriGrantsManager = builder.iUriGrantsManager;
            new TileServiceRequestController(qSHostAdapter, builder.commandQueue, builder.commandRegistry, tileRequestDialogEventLogger, iUriGrantsManager, null, 32, null).init();
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSHostAdapter(QSTileHost qSTileHost, CurrentTilesInteractor currentTilesInteractor, Context context, TileServiceRequestController.Builder builder, CoroutineScope coroutineScope, QSPipelineFlagsRepository qSPipelineFlagsRepository, DumpManager dumpManager) {
        this.qsTileHost = qSTileHost;
        this.tileServiceRequestControllerBuilder = builder;
        Flags.qsNewPipeline();
        new LinkedHashMap();
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        dumpManager.registerCriticalDumpable("QSTileHost", qSTileHost);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        this.qsTileHost.addCallback(callback);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        this.qsTileHost.addTile(componentName, z);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void changeTilesByUser(List list, List list2) {
        this.qsTileHost.changeTilesByUser(list, list2);
    }

    @Override // com.android.systemui.qs.QSHost
    public final ArrayList getBarTilesByType(int i, int i2) {
        return this.qsTileHost.getBarTilesByType(i, i2);
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getContext() {
        Context context = this.qsTileHost.mContext;
        Intrinsics.checkNotNull(context);
        return context;
    }

    @Override // com.android.systemui.qs.QSHost
    public final String getCustomTileNameFromSpec(String str) {
        return this.qsTileHost.getCustomTileNameFromSpec(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final SecQQSTileHost getQQSTileHost() {
        return this.qsTileHost.mQQSTileHost;
    }

    @Override // com.android.systemui.qs.QSHost
    public final List getSpecs() {
        ArrayList arrayList = this.qsTileHost.mTileSpecs;
        Intrinsics.checkNotNull(arrayList);
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost
    public final Collection getTiles() {
        Collection values = this.qsTileHost.mTiles.values();
        Intrinsics.checkNotNull(values);
        return values;
    }

    @Override // com.android.systemui.qs.QSHost
    public final Context getUserContext() {
        Context context = this.qsTileHost.mUserContext;
        Intrinsics.checkNotNull(context);
        return context;
    }

    @Override // com.android.systemui.qs.QSHost
    public final int getUserId() {
        return this.qsTileHost.mCurrentUser;
    }

    @Override // com.android.systemui.qs.QSHost
    public final int indexOf(String str) {
        return ((ArrayList) getSpecs()).indexOf(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableCustomTile(String str) {
        return this.qsTileHost.isAvailableCustomTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isAvailableForSearch(String str, boolean z) {
        return this.qsTileHost.isAvailableForSearch(str, z);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isBarTile(String str) {
        return this.qsTileHost.isBarTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isBrightnessVolumeBarTile(String str) {
        return this.qsTileHost.isBrightnessVolumeBarTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isLargeBarTile(String str) {
        return this.qsTileHost.isLargeBarTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isNoBgLargeTile(String str) {
        return this.qsTileHost.isNoBgLargeTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        return this.qsTileHost.isUnsupportedTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        this.qsTileHost.removeCallback(callback);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTile(String str) {
        this.qsTileHost.removeTile(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        this.qsTileHost.removeTileByUser(componentName);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void removeTiles(Collection collection) {
        this.qsTileHost.removeTiles(collection);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void sendTileStatusLog(int i, String str) {
        this.qsTileHost.sendTileStatusLog(i, str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldBeHiddenByKnox(String str) {
        return this.qsTileHost.shouldBeHiddenByKnox(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final boolean shouldUnavailableByKnox(String str) {
        return this.qsTileHost.shouldUnavailableByKnox(str);
    }

    @Override // com.android.systemui.qs.QSHost
    public final void addTile(ComponentName componentName) {
        addTile(componentName, false);
    }
}
