package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
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
                        TileVisibilityInteractorImpl tileVisibilityInteractorImpl2 = tileVisibilityInteractorImpl;
                        tileVisibilityInteractorImpl2.getClass();
                        String stringExtra = intent.getStringExtra("operation");
                        String stringExtra2 = intent.getStringExtra("componentName");
                        String stringExtra3 = intent.getStringExtra("packageName");
                        String stringExtra4 = intent.getStringExtra("tileName");
                        int intExtra = intent.getIntExtra("index", -1);
                        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("TileVisibilityUpdate : [", stringExtra, "] ", stringExtra4, ":");
                        MoveResult$$ExternalSyntheticOutline0.m(sbM, stringExtra3, ", ", stringExtra2, ", ");
                        RecyclerView$$ExternalSyntheticOutline0.m(intExtra, "TileVisibilityInteractor", sbM);
                        if (stringExtra3 != null && stringExtra2 != null && stringExtra4 != null) {
                            ComponentName componentName = new ComponentName(stringExtra3, stringExtra2);
                            if ("add".equals(stringExtra)) {
                                CurrentTilesInteractor currentTilesInteractor = tileVisibilityInteractorImpl2.qsTilesInteractor;
                                List defaultTiles = currentTilesInteractor.getDefaultTiles();
                                CurrentTilesInteractor currentTilesInteractor2 = tileVisibilityInteractorImpl2.qqsTilesInteractor;
                                List defaultTiles2 = currentTilesInteractor2.getDefaultTiles();
                                List list = (List) ((RemovedTilesInteractorImpl) tileVisibilityInteractorImpl2.removedTilesInteractor).removedTiles.$$delegate_0.getValue();
                                TileSpec.Companion.getClass();
                                TileSpec.CustomTileSpec customTileSpecCreate = TileSpec.Companion.create(componentName);
                                boolean zContains = list.contains(customTileSpecCreate);
                                QSPipelineLogger qSPipelineLogger = tileVisibilityInteractorImpl2.logger;
                                if (!zContains && ((ArrayList) defaultTiles).contains(customTileSpecCreate)) {
                                    int tileIndex = tileVisibilityInteractorImpl2.getTileIndex("QsWifiCallingTileIndex", intExtra, componentName, stringExtra4, defaultTiles);
                                    currentTilesInteractor.addTile(customTileSpecCreate, tileIndex);
                                    qSPipelineLogger.logTileVisibilityUpdated(true, customTileSpecCreate, tileIndex, QSType.QS);
                                }
                                boolean z = Prefs.getBoolean(tileVisibilityInteractorImpl2.context, "QQsHasEditedQuickTileList", false);
                                int i3 = Prefs.getInt(tileVisibilityInteractorImpl2.context, "QQsWifiCallingTileIndex", -1);
                                Context context = tileVisibilityInteractorImpl2.context;
                                tileVisibilityInteractorImpl2.resourcePicker.resourcePickHelper.getTargetPicker().getClass();
                                SecQSPanelResourceCommon.Companion.getClass();
                                int iM2902int = SecQSPanelResourceCommon.Companion.m2902int(R.integer.sec_quick_qs_panel_max_columns, context);
                                int tileIndex2 = tileVisibilityInteractorImpl2.getTileIndex("QQsWifiCallingTileIndex", intExtra, componentName, stringExtra4, defaultTiles2);
                                if ("WifiCalling".equals(stringExtra4)) {
                                    if (z || i3 != -1) {
                                        if (i3 != -1 && ((List) currentTilesInteractor2.getCurrentTiles().getValue()).size() < iM2902int) {
                                            currentTilesInteractor2.addTile(customTileSpecCreate, tileIndex2);
                                        }
                                    } else if (tileIndex2 != -1 && tileIndex2 < iM2902int) {
                                        currentTilesInteractor2.addTile(customTileSpecCreate, tileIndex2);
                                    }
                                    qSPipelineLogger.logTileVisibilityUpdated(true, customTileSpecCreate, tileIndex2, QSType.QQS);
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
        int iIndexOf = list.indexOf(TileSpec.Companion.create(componentName));
        return iIndexOf != -1 ? iIndexOf : i;
    }

    public final void removeByIntent(CurrentTilesInteractor currentTilesInteractor, String str, String str2, ComponentName componentName, String str3) {
        int iIndexOf;
        TileSpec.Companion.getClass();
        TileSpec.CustomTileSpec customTileSpecCreate = TileSpec.Companion.create(componentName);
        List currentTilesSpecs = currentTilesInteractor.getCurrentTilesSpecs();
        if ("WifiCalling".equals(str3) && Prefs.getBoolean(this.context, str, false) && (iIndexOf = ((ArrayList) currentTilesSpecs).indexOf(customTileSpecCreate)) != -1) {
            Prefs.putInt(this.context, str2, iIndexOf);
        }
        boolean zEquals = currentTilesInteractor.equals(this.qsTilesInteractor);
        QSPipelineLogger qSPipelineLogger = this.logger;
        if (zEquals) {
            qSPipelineLogger.logTileVisibilityUpdated(false, customTileSpecCreate, ((ArrayList) currentTilesSpecs).indexOf(customTileSpecCreate), QSType.QS);
        } else if (currentTilesInteractor.equals(this.qqsTilesInteractor)) {
            qSPipelineLogger.logTileVisibilityUpdated(false, customTileSpecCreate, ((ArrayList) currentTilesSpecs).indexOf(customTileSpecCreate), QSType.QQS);
        }
        currentTilesInteractor.removeTiles(Collections.singletonList(customTileSpecCreate));
    }
}
