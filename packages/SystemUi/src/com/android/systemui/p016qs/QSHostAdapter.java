package com.android.systemui.p016qs;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.external.TileRequestDialogEventLogger;
import com.android.systemui.p016qs.external.TileServiceRequestController;
import com.android.systemui.p016qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.p013qs.QSTileView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSHostAdapter implements QSHost {
    public final CurrentTilesInteractor interactor;
    public final QSTileHost qsTileHost;
    public final TileServiceRequestController.Builder tileServiceRequestControllerBuilder;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.qs.QSHostAdapter$1", m277f = "QSHostAdapter.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.qs.QSHostAdapter$1 */
    public static final class C20501 extends SuspendLambda implements Function2 {
        int label;

        public C20501(Continuation<? super C20501> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSHostAdapter.this.new C20501(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C20501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            new TileServiceRequestController(qSHostAdapter, builder.commandQueue, builder.commandRegistry, new TileRequestDialogEventLogger(), null, 16, null).init();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public QSHostAdapter(QSTileHost qSTileHost, CurrentTilesInteractor currentTilesInteractor, Context context, TileServiceRequestController.Builder builder, CoroutineScope coroutineScope, FeatureFlags featureFlags, DumpManager dumpManager) {
        this.qsTileHost = qSTileHost;
        this.interactor = currentTilesInteractor;
        this.tileServiceRequestControllerBuilder = builder;
        Flags flags = Flags.INSTANCE;
        new LinkedHashMap();
        BuildersKt.launch$default(coroutineScope, null, null, new C20501(null), 3);
        dumpManager.registerCriticalDumpable("QSTileHost", qSTileHost);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addCallback(QSHost.Callback callback) {
        this.qsTileHost.addCallback(callback);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addTile(ComponentName componentName, boolean z) {
        this.qsTileHost.addTile(componentName, z);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void changeTilesByUser(List list, List list2) {
        this.qsTileHost.changeTilesByUser(list, list2);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        return this.qsTileHost.createTileView(context, qSTile, z);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final ArrayList getBarTilesByType(int i, Context context) {
        return this.qsTileHost.getBarTilesByType(i, context);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Context getContext() {
        return this.qsTileHost.mContext;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final String getCustomTileNameFromSpec(String str) {
        return this.qsTileHost.getCustomTileNameFromSpec(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final SecQQSTileHost getQQSTileHost() {
        return this.qsTileHost.mQQSTileHost;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Collection getTiles() {
        return this.qsTileHost.getTiles();
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final Context getUserContext() {
        return this.qsTileHost.mUserContext;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final int getUserId() {
        return this.qsTileHost.mCurrentUser;
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final int indexOf(String str) {
        return this.qsTileHost.mTileSpecs.indexOf(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isAvailableCustomTile(String str) {
        return this.qsTileHost.isAvailableCustomTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isAvailableForSearch(String str, boolean z) {
        return this.qsTileHost.isAvailableForSearch(str, z);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isBarTile(String str) {
        return this.qsTileHost.isBarTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isBrightnessBarTile(String str) {
        return this.qsTileHost.isBrightnessBarTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isLargeBarTile(String str) {
        return this.qsTileHost.isLargeBarTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean isUnsupportedTile(String str) {
        return this.qsTileHost.isUnsupportedTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeCallback(QSHost.Callback callback) {
        this.qsTileHost.removeCallback(callback);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTile(String str) {
        this.qsTileHost.removeTile(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTileByUser(ComponentName componentName) {
        this.qsTileHost.removeTileByUser(componentName);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void removeTiles(Collection collection) {
        this.qsTileHost.removeTiles(collection);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean shouldBeHiddenByKnox(String str) {
        return this.qsTileHost.shouldBeHiddenByKnox(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final boolean shouldUnavailableByKnox(String str) {
        return this.qsTileHost.shouldUnavailableByKnox(str);
    }

    @Override // com.android.systemui.p016qs.QSHost
    public final void addTile(ComponentName componentName) {
        addTile(componentName, false);
    }
}
