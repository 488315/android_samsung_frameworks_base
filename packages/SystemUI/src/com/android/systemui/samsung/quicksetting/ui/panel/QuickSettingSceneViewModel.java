package com.android.systemui.samsung.quicksetting.ui.panel;

import android.content.Context;
import android.util.Log;
import androidx.compose.ui.unit.IntSize;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.compose.animation.scene.SceneKey;
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
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

        /* JADX WARN: Code restructure failed: missing block: B:29:0x00a4, code lost:
        
            if (r9.emit(r1, r8) != r0) goto L31;
         */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0080 A[PHI: r1 r9
          0x0080: PHI (r1v5 kotlinx.coroutines.flow.MutableStateFlow) = (r1v4 kotlinx.coroutines.flow.MutableStateFlow), (r1v13 kotlinx.coroutines.flow.MutableStateFlow) binds: [B:23:0x007d, B:13:0x002a] A[DONT_GENERATE, DONT_INLINE]
          0x0080: PHI (r9v11 java.lang.Object) = (r9v10 java.lang.Object), (r9v0 java.lang.Object) binds: [B:23:0x007d, B:13:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0092 A[PHI: r1 r9
          0x0092: PHI (r1v6 kotlinx.coroutines.flow.MutableStateFlow) = (r1v5 kotlinx.coroutines.flow.MutableStateFlow), (r1v15 kotlinx.coroutines.flow.MutableStateFlow) binds: [B:26:0x008f, B:12:0x0022] A[DONT_GENERATE, DONT_INLINE]
          0x0092: PHI (r9v14 java.lang.Object) = (r9v13 java.lang.Object), (r9v0 java.lang.Object) binds: [B:26:0x008f, B:12:0x0022] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            MutableStateFlow mutableStateFlow;
            MutableStateFlow mutableStateFlow2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                QuickSettingSceneViewModel quickSettingSceneViewModel = QuickSettingSceneViewModel.this;
                mutableStateFlow = quickSettingSceneViewModel._portraitPanelItems;
                ScreenType screenType = ScreenType.PORTRAIT;
                this.L$0 = mutableStateFlow;
                this.label = 1;
                obj = quickSettingSceneViewModel.gridTileInteractor.loadTiles(screenType, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                mutableStateFlow = (MutableStateFlow) this.L$0;
                ResultKt.throwOnFailure(obj);
            } else {
                if (i == 2) {
                    mutableStateFlow = (MutableStateFlow) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    mutableStateFlow.setValue(obj);
                    QuickSettingSceneViewModel quickSettingSceneViewModel2 = QuickSettingSceneViewModel.this;
                    mutableStateFlow2 = quickSettingSceneViewModel2._horizontalPanelItems;
                    ScreenType screenType2 = ScreenType.LANDSCAPE;
                    this.L$0 = mutableStateFlow2;
                    this.label = 3;
                    obj = quickSettingSceneViewModel2.gridTileInteractor.loadTiles(screenType2, this);
                    if (obj != coroutineSingletons) {
                        this.L$0 = mutableStateFlow2;
                        this.label = 4;
                        obj = FlowKt.toCollection((Flow) obj, new ArrayList(), this);
                        if (obj != coroutineSingletons) {
                        }
                    }
                    return coroutineSingletons;
                }
                if (i == 3) {
                    mutableStateFlow2 = (MutableStateFlow) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    this.L$0 = mutableStateFlow2;
                    this.label = 4;
                    obj = FlowKt.toCollection((Flow) obj, new ArrayList(), this);
                    if (obj != coroutineSingletons) {
                        mutableStateFlow2.setValue(obj);
                        SharedFlowImpl sharedFlowImpl = QuickSettingSceneViewModel.this.updateAvailableTilesEvent;
                        Unit unit = Unit.INSTANCE;
                        this.L$0 = null;
                        this.label = 5;
                    }
                    return coroutineSingletons;
                }
                if (i != 4) {
                    if (i != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                mutableStateFlow2 = (MutableStateFlow) this.L$0;
                ResultKt.throwOnFailure(obj);
                mutableStateFlow2.setValue(obj);
                SharedFlowImpl sharedFlowImpl2 = QuickSettingSceneViewModel.this.updateAvailableTilesEvent;
                Unit unit2 = Unit.INSTANCE;
                this.L$0 = null;
                this.label = 5;
            }
            this.L$0 = mutableStateFlow;
            this.label = 2;
            obj = FlowKt.toCollection((Flow) obj, new ArrayList(), this);
            if (obj != coroutineSingletons) {
                mutableStateFlow.setValue(obj);
                QuickSettingSceneViewModel quickSettingSceneViewModel22 = QuickSettingSceneViewModel.this;
                mutableStateFlow2 = quickSettingSceneViewModel22._horizontalPanelItems;
                ScreenType screenType22 = ScreenType.LANDSCAPE;
                this.L$0 = mutableStateFlow2;
                this.label = 3;
                obj = quickSettingSceneViewModel22.gridTileInteractor.loadTiles(screenType22, this);
                if (obj != coroutineSingletons) {
                }
            }
            return coroutineSingletons;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (!Intrinsics.areEqual((SceneKey) obj, Scenes.QuickSettings)) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = sceneBackInteractor$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow2 = new Flow() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        SceneKey sceneKey = (SceneKey) obj;
                        if (sceneKey == null) {
                            sceneKey = Scenes.Shade;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sceneKey, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Scenes.Shade);
        this.backScene = readonlyStateFlowStateIn;
        QSSceneAdapterImpl qSSceneAdapterImpl = (QSSceneAdapterImpl) qSSceneAdapter;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(qSSceneAdapterImpl.isCustomizerShowing, readonlyStateFlowStateIn, new QuickSettingSceneViewModel$destinationScenes$1(this));
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        ((Boolean) qSSceneAdapterImpl.isCustomizerShowing.$$delegate_0.getValue()).getClass();
        this.destinationScenes = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedWhileSubscribedWhileSubscribed$default, new MapBuilder().build());
        FlowKt.stateIn(selectedUserInteractor.selectedUser, ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._showAvailableTiles = stateFlowImplMutableStateFlow;
        this.showAvailableTiles = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(TilesVisible.VISIBLE);
        this._tilesVisible = stateFlowImplMutableStateFlow2;
        this.tilesVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(ScreenType.PORTRAIT);
        this.currentScreenType = stateFlowImplMutableStateFlow3;
        IntSize.Companion companion2 = IntSize.Companion;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(emptyList);
        this._portraitPanelItems = stateFlowImplMutableStateFlow4;
        this.portraitPanelItems = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(emptyList);
        this._horizontalPanelItems = stateFlowImplMutableStateFlow5;
        this.horizontalPanelItems = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow("");
        this.queryString = stateFlowImplMutableStateFlow6;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.updateAvailableTilesEvent = sharedFlowImplMutableSharedFlow$default;
        final ReadonlyStateFlow readonlyStateFlow = secQSPanelComposeAdapter.expansionFraction;
        this.expansionFraction = readonlyStateFlow;
        this.slideFraction = secQSPanelComposeAdapter.slideFraction;
        this.collapsed = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.samsung.quicksetting.ui.panel.QuickSettingSceneViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() <= 0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, ViewModelKt.getViewModelScope(this), SharingStarted.Companion.Eagerly, bool);
        this.availableTiles = FlowKt.stateIn(FlowKt.combine(sharedFlowImplMutableSharedFlow$default, stateFlowImplMutableStateFlow3, stateFlowImplMutableStateFlow6, new QuickSettingSceneViewModel$availableTiles$1(this, null)), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptyList);
        Log.i("QuickSettingSceneViewModel", "QuickSettingSceneViewModel init");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._brightBarInAction = stateFlowImplMutableStateFlow7;
        this.brightBarInAction = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._editMode = stateFlowImplMutableStateFlow8;
        this.editMode = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._currentQuickTileFolder = stateFlowImplMutableStateFlow9;
        this.currentQuickTileFolder = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._currentBrightBar = stateFlowImplMutableStateFlow10;
        this.currentBrightBar = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(null);
        this._currentDetailTile = stateFlowImplMutableStateFlow11;
        this.currentDetailTile = FlowKt.asStateFlow(stateFlowImplMutableStateFlow11);
        StateFlowImpl stateFlowImplMutableStateFlow12 = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._tileRadius = stateFlowImplMutableStateFlow12;
        this.tileRadius = FlowKt.asStateFlow(stateFlowImplMutableStateFlow12);
    }
}
