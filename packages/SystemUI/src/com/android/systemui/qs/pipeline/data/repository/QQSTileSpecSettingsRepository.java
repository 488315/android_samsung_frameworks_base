package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.SparseArray;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.retail.data.repository.impl.RetailModeSettingsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class QQSTileSpecSettingsRepository implements TileSpecRepository {
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final RetailModeRepository retailModeRepository;
    public final Lazy retailModeTiles$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.QQSTileSpecSettingsRepository$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            int i = 0;
            List<String> listSplit$default = StringsKt__StringsKt.split$default(this.f$0.resources.getString(R.string.quick_settings_tiles_retail_mode), new String[]{","}, 0, 6);
            TileSpec.Companion companion = TileSpec.Companion;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
            for (String str : listSplit$default) {
                companion.getClass();
                arrayList.add(TileSpec.Companion.create(str));
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                    arrayList2.add(obj);
                }
            }
            return arrayList2;
        }
    });
    public final SparseArray userTileRepositories = new SparseArray();
    public final QQSUserTileSpecRepository.Factory userTileSpecRepositoryFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSTileSpecSettingsRepository$tilesSpecs$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return QQSTileSpecSettingsRepository.this.tilesSpecs(0, this);
        }
    }

    static {
        new Companion(null);
    }

    public QQSTileSpecSettingsRepository(Resources resources, QSPipelineLogger qSPipelineLogger, RetailModeRepository retailModeRepository, QQSUserTileSpecRepository.Factory factory) {
        this.resources = resources;
        this.logger = qSPipelineLogger;
        this.retailModeRepository = retailModeRepository;
        this.userTileSpecRepositoryFactory = factory;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object addTile(int i, TileSpec tileSpec, int i2, SuspendLambda suspendLambda) {
        Object objEmit;
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        boolean z = tileSpec instanceof TileSpec.Invalid;
        if (z) {
            return Unit.INSTANCE;
        }
        QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
        if (qQSUserTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        if (z || (objEmit = qQSUserTileSpecRepository.changeEvents.emit(new QQSUserTileSpecRepository.AddTile(tileSpec, i2), suspendLambda)) != CoroutineSingletons.COROUTINE_SUSPENDED) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object prependDefault(int i, SuspendLambda suspendLambda) throws Throwable {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
        if (qQSUserTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = qQSUserTileSpecRepository.changeEvents.emit(new QQSUserTileSpecRepository.PrependDefault(qQSUserTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objEmit != coroutineSingletons) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == coroutineSingletons ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Unit reconcileRestore() {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object removeTiles(int i, Collection collection, SuspendLambda suspendLambda) throws Throwable {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
        if (qQSUserTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = qQSUserTileSpecRepository.changeEvents.emit(new QQSUserTileSpecRepository.RemoveTiles(collection), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objEmit != coroutineSingletons) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == coroutineSingletons ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object resetToDefault(int i, SuspendLambda suspendLambda) throws Throwable {
        QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
        if (qQSUserTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = qQSUserTileSpecRepository.changeEvents.emit(new QQSUserTileSpecRepository.ResetToDefault(qQSUserTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objEmit != coroutineSingletons) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == coroutineSingletons ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object setTiles(int i, List list, SuspendLambda suspendLambda) throws Throwable {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
        if (qQSUserTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = qQSUserTileSpecRepository.changeEvents.emit(new QQSUserTileSpecRepository.ChangeTiles(list), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objEmit != coroutineSingletons) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == coroutineSingletons ? objEmit : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object tilesSpecs(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objTiles = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objTiles);
            if (!this.userTileRepositories.contains(i)) {
                this.userTileRepositories.put(i, this.userTileSpecRepositoryFactory.create(i));
            }
            QQSUserTileSpecRepository qQSUserTileSpecRepository = (QQSUserTileSpecRepository) this.userTileRepositories.get(i);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objTiles = qQSUserTileSpecRepository.tiles(anonymousClass1);
            if (objTiles == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (QQSTileSpecSettingsRepository) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objTiles);
        }
        return FlowKt.transformLatest(((RetailModeSettingsRepository) this.retailModeRepository).retailMode, new QQSTileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(null, this, (Flow) objTiles));
    }
}
