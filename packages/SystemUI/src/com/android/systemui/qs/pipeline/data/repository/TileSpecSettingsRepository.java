package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.SparseArray;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TilesUpgradePath;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.retail.data.repository.impl.RetailModeSettingsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository implements TileSpecRepository {
    public final CoroutineScope applicationScope;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final RetailModeRepository retailModeRepository;
    public final UserTileSpecRepository.Factory userTileSpecRepositoryFactory;
    public final Lazy retailModeTiles$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$$ExternalSyntheticLambda0
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
    public final BufferedChannel _tilesUpgradePath = ChannelKt.Channel$default(5, null, null, 6);
    public final SparseArray userTileRepositories = new SparseArray();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1, reason: invalid class name */
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
            return TileSpecSettingsRepository.this.tilesSpecs(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $userId;
        final /* synthetic */ UserTileSpecRepository $userTileRepository;
        Object L$0;
        int label;
        final /* synthetic */ TileSpecSettingsRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserTileSpecRepository userTileSpecRepository, TileSpecSettingsRepository tileSpecSettingsRepository, int i, Continuation continuation) {
            super(2, continuation);
            this.$userTileRepository = userTileSpecRepository;
            this.this$0 = tileSpecSettingsRepository;
            this.$userId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$userTileRepository, this.this$0, this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x0046  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0067  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0064 -> B:11:0x0033). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BufferedChannel bufferedChannel = this.$userTileRepository.tilesUpgradePath;
                bufferedChannel.getClass();
                bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
            } else {
                if (i == 1) {
                    bufferedChannelIterator = (BufferedChannel.BufferedChannelIterator) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (((Boolean) obj).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    TilesUpgradePath tilesUpgradePath = (TilesUpgradePath) bufferedChannelIterator.next();
                    BufferedChannel bufferedChannel2 = this.this$0._tilesUpgradePath;
                    Pair pair = new Pair(tilesUpgradePath, new Integer(this.$userId));
                    this.L$0 = bufferedChannelIterator;
                    this.label = 2;
                    if (bufferedChannel2.send(pair, this) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                bufferedChannelIterator = (BufferedChannel.BufferedChannelIterator) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = bufferedChannelIterator;
            this.label = 1;
            obj = bufferedChannelIterator.hasNext(this);
            if (obj != coroutineSingletons) {
                if (((Boolean) obj).booleanValue()) {
                }
            }
            return coroutineSingletons;
        }
    }

    static {
        new Companion(null);
    }

    public TileSpecSettingsRepository(Resources resources, QSPipelineLogger qSPipelineLogger, RetailModeRepository retailModeRepository, UserTileSpecRepository.Factory factory, CoroutineScope coroutineScope) {
        this.resources = resources;
        this.logger = qSPipelineLogger;
        this.retailModeRepository = retailModeRepository;
        this.userTileSpecRepositoryFactory = factory;
        this.applicationScope = coroutineScope;
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
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        if (z || (objEmit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.AddTile(tileSpec, i2), suspendLambda)) != CoroutineSingletons.COROUTINE_SUSPENDED) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object prependDefault(int i, SuspendLambda suspendLambda) throws Throwable {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.PrependDefault(userTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
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
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.RemoveTiles(collection), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objEmit != coroutineSingletons) {
            objEmit = Unit.INSTANCE;
        }
        return objEmit == coroutineSingletons ? objEmit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object resetToDefault(int i, SuspendLambda suspendLambda) throws Throwable {
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.ResetToDefault(userTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
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
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object objEmit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.ChangeTiles(list), suspendLambda);
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
                UserTileSpecRepository userTileSpecRepositoryCreate = this.userTileSpecRepositoryFactory.create(i);
                this.userTileRepositories.put(i, userTileSpecRepositoryCreate);
                CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass2(userTileSpecRepositoryCreate, this, i, null), 6);
            }
            UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objTiles = userTileSpecRepository.tiles(anonymousClass1);
            if (objTiles == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (TileSpecSettingsRepository) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objTiles);
        }
        return FlowKt.transformLatest(((RetailModeSettingsRepository) this.retailModeRepository).retailMode, new TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1(null, this, (Flow) objTiles));
    }
}
