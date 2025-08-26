package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.haptics.msdl.qs.TileHapticsViewModelFactoryProvider;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.panels.domain.interactor.QuickQuickSettingsRowInteractor;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import com.android.systemui.qs.panels.shared.model.TileRowKt;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QSColumnsViewModel;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.DropTakeSequence;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TakeSequence;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
public final class QuickQuickSettingsViewModel extends ExclusiveActivatable {
    public final State currentTiles$delegate;
    public final Hydrator hydrator;
    public final State largeTiles$delegate;
    public final State largeTilesSpan$delegate;
    public final MediaInRowInLandscapeViewModel mediaInRowViewModel;
    public final QSColumnsViewModel qsColumnsViewModel;
    public final State rowsWithoutMedia$delegate;
    public final TileSquishinessViewModel squishinessViewModel;
    public final TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider;
    public final State tileViewModels$delegate;

    public interface Factory {
        QuickQuickSettingsViewModel create();
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return QuickQuickSettingsViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickQuickSettingsViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(QuickQuickSettingsViewModel quickQuickSettingsViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickQuickSettingsViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C03962 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickQuickSettingsViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03962(QuickQuickSettingsViewModel quickQuickSettingsViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickQuickSettingsViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03962(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03962) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    QSColumnsViewModel qSColumnsViewModel = this.this$0.qsColumnsViewModel;
                    this.label = 1;
                    if (qSColumnsViewModel.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickQuickSettingsViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(QuickQuickSettingsViewModel quickQuickSettingsViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickQuickSettingsViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = this.this$0.mediaInRowViewModel;
                    this.label = 1;
                    if (mediaInRowInLandscapeViewModel.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = QuickQuickSettingsViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(QuickQuickSettingsViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C03962(QuickQuickSettingsViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(QuickQuickSettingsViewModel.this, null), 3);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public QuickQuickSettingsViewModel(CurrentTilesInteractor currentTilesInteractor, QSColumnsViewModel.Factory factory, QuickQuickSettingsRowInteractor quickQuickSettingsRowInteractor, MediaInRowInLandscapeViewModel.Factory factory2, TileSquishinessViewModel tileSquishinessViewModel, IconTilesViewModel iconTilesViewModel, TileHapticsViewModelFactoryProvider tileHapticsViewModelFactoryProvider) {
        this.squishinessViewModel = tileSquishinessViewModel;
        this.tileHapticsViewModelFactoryProvider = tileHapticsViewModelFactoryProvider;
        Hydrator hydrator = new Hydrator("QuickQuickSettingsViewModel", null, 2, null);
        this.hydrator = hydrator;
        this.qsColumnsViewModel = factory.create(1);
        this.mediaInRowViewModel = factory2.create(1);
        this.largeTiles$delegate = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTiles(), "largeTiles");
        quickQuickSettingsRowInteractor.getClass();
        this.rowsWithoutMedia$delegate = hydrator.hydratedStateOf("rowsWithoutMedia", 2, quickQuickSettingsRowInteractor.rows);
        this.largeTilesSpan$delegate = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTilesSpan(), "largeTilesSpan");
        this.currentTiles$delegate = hydrator.hydratedStateOf(currentTilesInteractor.getCurrentTiles(), "currentTiles");
        this.tileViewModels$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                QuickQuickSettingsViewModel quickQuickSettingsViewModel = this.f$0;
                List<TileModel> list = (List) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.currentTiles$delegate).getValue();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                for (TileModel tileModel : list) {
                    QSTile qSTile = tileModel.tile;
                    TileSpec tileSpec = tileModel.spec;
                    arrayList.add(new SizedTileImpl(new TileViewModel(qSTile, tileSpec), ((Set) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.largeTiles$delegate).getValue()).contains(tileSpec) ? ((Number) ((SnapshotMutableStateImpl) quickQuickSettingsViewModel.largeTilesSpan$delegate).getValue()).intValue() : 1));
                }
                Sequence sequenceSplitInRowsSequence = TileRowKt.splitInRowsSequence(quickQuickSettingsViewModel.qsColumnsViewModel.getColumns(), arrayList);
                boolean shouldMediaShowInRow = quickQuickSettingsViewModel.mediaInRowViewModel.getShouldMediaShowInRow();
                State state = quickQuickSettingsViewModel.rowsWithoutMedia$delegate;
                int iIntValue = shouldMediaShowInRow ? ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue() * 2 : ((Number) ((SnapshotMutableStateImpl) state).getValue()).intValue();
                if (iIntValue >= 0) {
                    return CollectionsKt__IterablesKt.flatten(SequencesKt___SequencesKt.toList(iIntValue == 0 ? EmptySequence.INSTANCE : sequenceSplitInRowsSequence instanceof DropTakeSequence ? ((DropTakeSequence) sequenceSplitInRowsSequence).take(iIntValue) : new TakeSequence(sequenceSplitInRowsSequence, iIntValue)));
                }
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(iIntValue, "Requested element count ", " is less than zero.").toString());
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
