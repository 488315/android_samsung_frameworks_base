package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.repository.TileVisibilityRepository;
import com.android.systemui.qs.pipeline.data.repository.TileVisibilityRepositoryImpl;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileVisibilityInteractorImpl implements TileVisibilityInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final QSPipelineLogger logger;
    public final CurrentTilesInteractor qqsTilesInteractor;
    public final CurrentTilesInteractor qsTilesInteractor;
    public final RemovedTilesInteractor removedTilesInteractor;
    public final SecQSPanelResourcePicker resourcePicker;
    public final TileVisibilityRepository tileVisibilityRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileVisibilityInteractorImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return TileVisibilityInteractorImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final TileVisibilityInteractorImpl tileVisibilityInteractorImpl = TileVisibilityInteractorImpl.this;
                ChannelAsFlow channelAsFlow = ((TileVisibilityRepositoryImpl) tileVisibilityInteractorImpl.tileVisibilityRepository).visibilityIntentByApp;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.domain.interactor.TileVisibilityInteractorImpl.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Intent intent = (Intent) obj2;
                        int i2 = TileVisibilityInteractorImpl.$r8$clinit;
                        TileVisibilityInteractorImpl tileVisibilityInteractorImpl2 = TileVisibilityInteractorImpl.this;
                        tileVisibilityInteractorImpl2.getClass();
                        String stringExtra = intent.getStringExtra("operation");
                        String stringExtra2 = intent.getStringExtra("componentName");
                        String stringExtra3 = intent.getStringExtra("packageName");
                        String stringExtra4 = intent.getStringExtra("tileName");
                        int intExtra = intent.getIntExtra("index", -1);
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TileVisibilityUpdate : [", stringExtra, "] ", stringExtra4, ":");
                        MoveResult$$ExternalSyntheticOutline0.m(m, stringExtra3, ", ", stringExtra2, ", ");
                        RecyclerView$$ExternalSyntheticOutline0.m(intExtra, "TileVisibilityInteractor", m);
                        if (stringExtra3 != null && stringExtra2 != null && stringExtra4 != null) {
                            ComponentName componentName = new ComponentName(stringExtra3, stringExtra2);
                            if ("add".equals(stringExtra)) {
                                CurrentTilesInteractor currentTilesInteractor = tileVisibilityInteractorImpl2.qsTilesInteractor;
                                List defaultTiles = currentTilesInteractor.getDefaultTiles();
                                CurrentTilesInteractor currentTilesInteractor2 = tileVisibilityInteractorImpl2.qqsTilesInteractor;
                                List defaultTiles2 = currentTilesInteractor2.getDefaultTiles();
                                List list = (List) ((RemovedTilesInteractorImpl) tileVisibilityInteractorImpl2.removedTilesInteractor).removedTiles.$$delegate_0.getValue();
                                TileSpec.Companion.getClass();
                                TileSpec.CustomTileSpec create = TileSpec.Companion.create(componentName);
                                boolean contains = list.contains(create);
                                QSPipelineLogger qSPipelineLogger = tileVisibilityInteractorImpl2.logger;
                                if (!contains && ((ArrayList) defaultTiles).contains(create)) {
                                    int tileIndex = tileVisibilityInteractorImpl2.getTileIndex("QsWifiCallingTileIndex", intExtra, componentName, stringExtra4, defaultTiles);
                                    currentTilesInteractor.addTile(create, tileIndex);
                                    qSPipelineLogger.logTileVisibilityUpdated(true, create, tileIndex, QSType.QS);
                                }
                                boolean z = Prefs.getBoolean(tileVisibilityInteractorImpl2.context, "QQsHasEditedQuickTileList", false);
                                int i3 = Prefs.getInt(tileVisibilityInteractorImpl2.context, "QQsWifiCallingTileIndex", -1);
                                int quickQsTileNum = tileVisibilityInteractorImpl2.resourcePicker.resourcePickHelper.getTargetPicker().getQuickQsTileNum(tileVisibilityInteractorImpl2.context);
                                int tileIndex2 = tileVisibilityInteractorImpl2.getTileIndex("QQsWifiCallingTileIndex", intExtra, componentName, stringExtra4, defaultTiles2);
                                if ("WifiCalling".equals(stringExtra4)) {
                                    if (z || i3 != -1) {
                                        if (i3 != -1 && ((List) currentTilesInteractor2.getCurrentTiles().getValue()).size() < quickQsTileNum) {
                                            currentTilesInteractor2.addTile(create, tileIndex2);
                                        }
                                    } else if (tileIndex2 != -1 && tileIndex2 < quickQsTileNum) {
                                        currentTilesInteractor2.addTile(create, tileIndex2);
                                    }
                                    qSPipelineLogger.logTileVisibilityUpdated(true, create, tileIndex2, QSType.QQS);
                                }
                            } else if ("remove".equals(stringExtra)) {
                                tileVisibilityInteractorImpl2.removeByIntent(tileVisibilityInteractorImpl2.qsTilesInteractor, "QsHasEditedQuickTileList", "QsWifiCallingTileIndex", componentName, stringExtra4);
                                tileVisibilityInteractorImpl2.removeByIntent(tileVisibilityInteractorImpl2.qqsTilesInteractor, "QQsHasEditedQuickTileList", "QQsWifiCallingTileIndex", componentName, stringExtra4);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (channelAsFlow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

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

    public TileVisibilityInteractorImpl(CurrentTilesInteractor currentTilesInteractor, CurrentTilesInteractor currentTilesInteractor2, BroadcastDispatcher broadcastDispatcher, Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, RemovedTilesInteractor removedTilesInteractor, SecQSPanelResourcePicker secQSPanelResourcePicker, TileVisibilityRepository tileVisibilityRepository, QSPipelineLogger qSPipelineLogger) {
        this.qsTilesInteractor = currentTilesInteractor;
        this.qqsTilesInteractor = currentTilesInteractor2;
        this.context = context;
        this.removedTilesInteractor = removedTilesInteractor;
        this.resourcePicker = secQSPanelResourcePicker;
        this.tileVisibilityRepository = tileVisibilityRepository;
        this.logger = qSPipelineLogger;
        BuildersKt.launch$default(coroutineScope, coroutineDispatcher, null, new AnonymousClass1(null), 2);
    }

    public final int getTileIndex(String str, int i, ComponentName componentName, String str2, List list) {
        if ("WifiCalling".equals(str2)) {
            int i2 = Prefs.getInt(this.context, str, -1);
            ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "WFC savedIndex is ", "TileVisibilityInteractor");
            if (i2 != -1) {
                return i2;
            }
        }
        TileSpec.Companion.getClass();
        int indexOf = list.indexOf(TileSpec.Companion.create(componentName));
        return indexOf != -1 ? indexOf : i;
    }

    public final void removeByIntent(CurrentTilesInteractor currentTilesInteractor, String str, String str2, ComponentName componentName, String str3) {
        int indexOf;
        TileSpec.Companion.getClass();
        TileSpec.CustomTileSpec create = TileSpec.Companion.create(componentName);
        List currentTilesSpecs = currentTilesInteractor.getCurrentTilesSpecs();
        if ("WifiCalling".equals(str3) && Prefs.getBoolean(this.context, str, false) && (indexOf = ((ArrayList) currentTilesSpecs).indexOf(create)) != -1) {
            Prefs.putInt(this.context, str2, indexOf);
        }
        boolean equals = currentTilesInteractor.equals(this.qsTilesInteractor);
        QSPipelineLogger qSPipelineLogger = this.logger;
        if (equals) {
            qSPipelineLogger.logTileVisibilityUpdated(false, create, ((ArrayList) currentTilesSpecs).indexOf(create), QSType.QS);
        } else if (currentTilesInteractor.equals(this.qqsTilesInteractor)) {
            qSPipelineLogger.logTileVisibilityUpdated(false, create, ((ArrayList) currentTilesSpecs).indexOf(create), QSType.QQS);
        }
        currentTilesInteractor.removeTiles(Collections.singletonList(create));
    }
}
