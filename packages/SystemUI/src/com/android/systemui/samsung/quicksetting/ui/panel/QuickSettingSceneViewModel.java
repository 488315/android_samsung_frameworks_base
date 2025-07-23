package com.android.systemui.samsung.quicksetting.ui.panel;

import android.content.Context;
import android.util.Log;
import androidx.compose.ui.unit.IntSize;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor;
import com.android.systemui.samsung.quicksetting.ui.settings.SecPanelSettingsViewModel;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingSceneViewModel extends ViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _brightBarInAction;
    public final StateFlowImpl _currentBrightBar;
    public final StateFlowImpl _currentDetailTile;
    public final StateFlowImpl _currentQuickTileFolder;
    public final StateFlowImpl _editMode;
    public final StateFlowImpl _horizontalPanelItems;
    public final StateFlowImpl _portraitPanelItems;
    public final StateFlowImpl _qsPanelItems;
    public final StateFlowImpl _showAvailableTiles;
    public final StateFlowImpl _tileRadius;
    public final StateFlowImpl _tilesVisible;
    public final ActivityStarter activityStarter;
    public final ReadonlyStateFlow availableTiles;
    public final ReadonlyStateFlow backScene;
    public final ReadonlyStateFlow brightBarInAction;
    public final ReadonlyStateFlow collapsed;
    public final SecQSPanelComposeAdapter composeAdapter;
    public final Context context;
    public final ReadonlyStateFlow currentBrightBar;
    public final ReadonlyStateFlow currentDetailTile;
    public final ReadonlyStateFlow currentQuickTileFolder;
    public final StateFlowImpl currentScreenType;
    public final ReadonlyStateFlow destinationScenes;
    public final ReadonlyStateFlow editMode;
    public final ReadonlyStateFlow expansionFraction;
    public final GlobalActionsComponent globalActionsComponent;
    public final GridTileInteractor gridTileInteractor;
    public final ReadonlyStateFlow horizontalPanelItems;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public final ReadonlyStateFlow portraitPanelItems;
    public final StateFlowImpl queryString;
    public final SecQpBlurController secQpBlurController;
    public final ReadonlyStateFlow showAvailableTiles;
    public final ReadonlyStateFlow slideFraction;
    public final ReadonlyStateFlow tileRadius;
    public final ReadonlyStateFlow tilesVisible;
    public final SharedFlowImpl updateAvailableTilesEvent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QuickSettingSceneViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x00a4, code lost:
        
            if (r9.emit(r1, r8) == r0) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x00a6, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x008f, code lost:
        
            if (r9 == r0) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x007d, code lost:
        
            if (r9 == r0) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0067, code lost:
        
            if (r9 == r0) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
        
            if (r9 == r0) goto L30;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r8.label
                r2 = 5
                r3 = 4
                r4 = 3
                r5 = 2
                r6 = 1
                if (r1 == 0) goto L42
                if (r1 == r6) goto L3a
                if (r1 == r5) goto L32
                if (r1 == r4) goto L2a
                if (r1 == r3) goto L22
                if (r1 != r2) goto L1a
                kotlin.ResultKt.throwOnFailure(r9)
                goto La7
            L1a:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L22:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
                kotlin.ResultKt.throwOnFailure(r9)
                goto L92
            L2a:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
                kotlin.ResultKt.throwOnFailure(r9)
                goto L80
            L32:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
                kotlin.ResultKt.throwOnFailure(r9)
                goto L6a
            L3a:
                java.lang.Object r1 = r8.L$0
                kotlinx.coroutines.flow.MutableStateFlow r1 = (kotlinx.coroutines.flow.MutableStateFlow) r1
                kotlin.ResultKt.throwOnFailure(r9)
                goto L58
            L42:
                kotlin.ResultKt.throwOnFailure(r9)
                com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel r9 = com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel.this
                kotlinx.coroutines.flow.StateFlowImpl r1 = r9._portraitPanelItems
                com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r7 = com.android.systemui.samsung.quicksetting.ui.panel.ScreenType.PORTRAIT
                r8.L$0 = r1
                r8.label = r6
                com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor r9 = r9.gridTileInteractor
                java.lang.Object r9 = r9.loadTiles(r7, r8)
                if (r9 != r0) goto L58
                goto La6
            L58:
                kotlinx.coroutines.flow.Flow r9 = (kotlinx.coroutines.flow.Flow) r9
                r8.L$0 = r1
                r8.label = r5
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.toCollection(r9, r5, r8)
                if (r9 != r0) goto L6a
                goto La6
            L6a:
                r1.setValue(r9)
                com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel r9 = com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel.this
                kotlinx.coroutines.flow.StateFlowImpl r1 = r9._horizontalPanelItems
                com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r5 = com.android.systemui.samsung.quicksetting.ui.panel.ScreenType.LANDSCAPE
                r8.L$0 = r1
                r8.label = r4
                com.android.systemui.samsung.quicksetting.domain.interactor.GridTileInteractor r9 = r9.gridTileInteractor
                java.lang.Object r9 = r9.loadTiles(r5, r8)
                if (r9 != r0) goto L80
                goto La6
            L80:
                kotlinx.coroutines.flow.Flow r9 = (kotlinx.coroutines.flow.Flow) r9
                r8.L$0 = r1
                r8.label = r3
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.toCollection(r9, r3, r8)
                if (r9 != r0) goto L92
                goto La6
            L92:
                r1.setValue(r9)
                com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel r9 = com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel.this
                kotlinx.coroutines.flow.SharedFlowImpl r9 = r9.updateAvailableTilesEvent
                kotlin.Unit r1 = kotlin.Unit.INSTANCE
                r3 = 0
                r8.L$0 = r3
                r8.label = r2
                java.lang.Object r8 = r9.emit(r1, r8)
                if (r8 != r0) goto La7
            La6:
                return r0
            La7:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
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

    public QuickSettingSceneViewModel(CoroutineScope coroutineScope, QSSceneAdapter qSSceneAdapter, SceneBackInteractor sceneBackInteractor, GridTileInteractor gridTileInteractor, MediaCarouselInteractor mediaCarouselInteractor, SelectedUserInteractor selectedUserInteractor, NotificationShadeWindowController notificationShadeWindowController, SecQSPanelComposeAdapter secQSPanelComposeAdapter, ActivityStarter activityStarter, GlobalActionsComponent globalActionsComponent, Context context, SecQpBlurController secQpBlurController, SecPanelSettingsViewModel secPanelSettingsViewModel) {
        this.gridTileInteractor = gridTileInteractor;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.composeAdapter = secQSPanelComposeAdapter;
        this.activityStarter = activityStarter;
        this.globalActionsComponent = globalActionsComponent;
        this.context = context;
        this.secQpBlurController = secQpBlurController;
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = sceneBackInteractor.backScene;
        final Flow flow = new Flow() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 != 0) goto L48
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow2 = new Flow() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        if (r5 != 0) goto L38
                        com.android.compose.animation.scene.SceneKey r5 = com.android.systemui.scene.shared.model.Scenes.Shade
                    L38:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Scenes.Shade);
        this.backScene = stateIn;
        QSSceneAdapterImpl qSSceneAdapterImpl = (QSSceneAdapterImpl) qSSceneAdapter;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(qSSceneAdapterImpl.isCustomizerShowing, stateIn, new QuickSettingSceneViewModel$destinationScenes$1(this));
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        ((Boolean) qSSceneAdapterImpl.isCustomizerShowing.$$delegate_0.getValue()).getClass();
        this.destinationScenes = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, WhileSubscribed$default, new MapBuilder().build());
        FlowKt.stateIn(selectedUserInteractor.selectedUser, ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._showAvailableTiles = MutableStateFlow;
        this.showAvailableTiles = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(TilesVisible.VISIBLE);
        this._tilesVisible = MutableStateFlow2;
        this.tilesVisible = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(ScreenType.PORTRAIT);
        this.currentScreenType = MutableStateFlow3;
        IntSize.Companion companion2 = IntSize.Companion;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(emptyList);
        this._portraitPanelItems = MutableStateFlow4;
        this.portraitPanelItems = FlowKt.asStateFlow(MutableStateFlow4);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(emptyList);
        this._horizontalPanelItems = MutableStateFlow5;
        this.horizontalPanelItems = FlowKt.asStateFlow(MutableStateFlow5);
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow("");
        this.queryString = MutableStateFlow6;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.updateAvailableTilesEvent = MutableSharedFlow$default;
        final ReadonlyStateFlow readonlyStateFlow = secQSPanelComposeAdapter.expansionFraction;
        this.expansionFraction = readonlyStateFlow;
        this.slideFraction = secQSPanelComposeAdapter.slideFraction;
        this.collapsed = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 > 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, ViewModelKt.getViewModelScope(this), SharingStarted.Companion.Eagerly, bool);
        this.availableTiles = FlowKt.stateIn(FlowKt.combine(MutableSharedFlow$default, MutableStateFlow3, MutableStateFlow6, new QuickSettingSceneViewModel$availableTiles$1(this, null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        Log.i("QuickSettingSceneViewModel", "QuickSettingSceneViewModel init");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._brightBarInAction = MutableStateFlow7;
        this.brightBarInAction = FlowKt.asStateFlow(MutableStateFlow7);
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._editMode = MutableStateFlow8;
        this.editMode = FlowKt.asStateFlow(MutableStateFlow8);
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._currentQuickTileFolder = MutableStateFlow9;
        this.currentQuickTileFolder = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._currentBrightBar = MutableStateFlow10;
        this.currentBrightBar = FlowKt.asStateFlow(MutableStateFlow10);
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(null);
        this._currentDetailTile = MutableStateFlow11;
        this.currentDetailTile = FlowKt.asStateFlow(MutableStateFlow11);
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._tileRadius = MutableStateFlow12;
        this.tileRadius = FlowKt.asStateFlow(MutableStateFlow12);
    }
}
