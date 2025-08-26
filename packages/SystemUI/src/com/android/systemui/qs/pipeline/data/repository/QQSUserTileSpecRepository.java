package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.Log;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.ScRune;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class QQSUserTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _tiles;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;
    public final int userId;

    public final class ChangeTiles implements UserTileSpecRepository.ChangeAction {
        public final List newTiles;

        public ChangeTiles(List<? extends TileSpec> list) {
            this.newTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            List list2 = this.newTiles;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list2) {
                if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                    arrayList.add(obj);
                }
            }
            return !arrayList.isEmpty() ? arrayList : list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ChangeTiles) && Intrinsics.areEqual(this.newTiles, ((ChangeTiles) obj).newTiles);
        }

        public final int hashCode() {
            return this.newTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ChangeTiles(newTiles=", this.newTiles, ")");
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        QQSUserTileSpecRepository create(int i);
    }

    public final class PrependDefault implements UserTileSpecRepository.ChangeAction {
        public final List defaultTiles;

        public PrependDefault(List<? extends TileSpec> list) {
            this.defaultTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            return CollectionsKt___CollectionsKt.plus((Iterable) list, (Collection) this.defaultTiles);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PrependDefault) && Intrinsics.areEqual(this.defaultTiles, ((PrependDefault) obj).defaultTiles);
        }

        public final int hashCode() {
            return this.defaultTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("PrependDefault(defaultTiles=", this.defaultTiles, ")");
        }
    }

    public final class RemoveTiles implements UserTileSpecRepository.ChangeAction {
        public final Collection tileSpecs;

        public RemoveTiles(Collection<? extends TileSpec> collection) {
            this.tileSpecs = collection;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            ArrayList arrayList = new ArrayList(list);
            arrayList.removeAll(this.tileSpecs);
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RemoveTiles) && Intrinsics.areEqual(this.tileSpecs, ((RemoveTiles) obj).tileSpecs);
        }

        public final int hashCode() {
            return this.tileSpecs.hashCode();
        }

        public final String toString() {
            return "RemoveTiles(tileSpecs=" + this.tileSpecs + ")";
        }
    }

    public final class ResetToDefault implements UserTileSpecRepository.ChangeAction {
        public final List defaultTiles;

        public ResetToDefault(List<? extends TileSpec> list) {
            this.defaultTiles = list;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            return this.defaultTiles;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ResetToDefault) && Intrinsics.areEqual(this.defaultTiles, ((ResetToDefault) obj).defaultTiles);
        }

        public final int hashCode() {
            return this.defaultTiles.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("ResetToDefault(defaultTiles=", this.defaultTiles, ")");
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            QQSUserTileSpecRepository qQSUserTileSpecRepository = QQSUserTileSpecRepository.this;
            Companion companion = QQSUserTileSpecRepository.Companion;
            return qQSUserTileSpecRepository.loadTilesFromSettings(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettings$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $userId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$userId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QQSUserTileSpecRepository.this.new AnonymousClass2(this.$userId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            String stringForUser = QQSUserTileSpecRepository.this.secureSettings.getStringForUser("sysui_quick_qs_tiles", this.$userId);
            return stringForUser == null ? "" : stringForUser;
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$loadTilesFromSettingsAndParse$1, reason: invalid class name and case insensitive filesystem */
    final class C09831 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C09831(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            QQSUserTileSpecRepository qQSUserTileSpecRepository = QQSUserTileSpecRepository.this;
            Companion companion = QQSUserTileSpecRepository.Companion;
            return qQSUserTileSpecRepository.loadTilesFromSettingsAndParse(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$1, reason: invalid class name and case insensitive filesystem */
    final class C09841 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09841(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return QQSUserTileSpecRepository.this.tiles(this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$tiles$2, reason: invalid class name and case insensitive filesystem */
    final class C09852 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C09852(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C09852 c09852 = QQSUserTileSpecRepository.this.new C09852((Continuation) obj3);
            c09852.L$0 = (List) obj;
            c09852.L$1 = (UserTileSpecRepository.ChangeAction) obj2;
            return c09852.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            UserTileSpecRepository.ChangeAction changeAction = (UserTileSpecRepository.ChangeAction) this.L$1;
            List listApply = changeAction.apply(list);
            QQSUserTileSpecRepository qQSUserTileSpecRepository = QQSUserTileSpecRepository.this;
            if (!Intrinsics.areEqual(list, listApply)) {
                Log.d("QQSUserTileSpecRepository", "changed qqs_tiles from " + list + " to " + listApply);
                qQSUserTileSpecRepository.logger.logProcessTileChange(changeAction, listApply, qQSUserTileSpecRepository.userId, QSType.QQS);
            }
            return CollectionsKt___CollectionsKt.distinct(listApply);
        }
    }

    public QQSUserTileSpecRepository(int i, Resources resources, DefaultTilesRepository defaultTilesRepository, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.userId = i;
        this.resources = resources;
        this.defaultTilesRepository = defaultTilesRepository;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
    }

    public static final Object access$storeTiles(QQSUserTileSpecRepository qQSUserTileSpecRepository, int i, List list, Continuation continuation) throws Throwable {
        qQSUserTileSpecRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object objWithContext = BuildersKt.withContext(qQSUserTileSpecRepository.backgroundDispatcher, new QQSUserTileSpecRepository$storeTiles$2(qQSUserTileSpecRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.QQSUserTileSpecRepository$storeTiles$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), i, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadTilesFromSettings(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        QQSUserTileSpecRepository qQSUserTileSpecRepository;
        Companion companion;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            boolean z = ScRune.QUICK_MANAGE_TILE_LIST_TEST;
            Companion companion2 = Companion;
            if (z) {
                TestTileDataRepositoryImpl testTileDataRepositoryImpl = (TestTileDataRepositoryImpl) this.testTileDataRepository;
                if (testTileDataRepositoryImpl.isFotaTest()) {
                    TileDataSource tileDataSource = testTileDataRepositoryImpl.tileDataSource;
                    String qqsTiles = (tileDataSource != null ? tileDataSource : null).getQqsTiles();
                    Resources resources = this.resources;
                    companion2.getClass();
                    TilesSettingConverter.INSTANCE.getClass();
                    return TilesSettingConverter.toTilesList(resources, qqsTiles);
                }
            }
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(i, null);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = companion2;
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, anonymousClass2, anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            qQSUserTileSpecRepository = this;
            companion = companion2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            companion = (Companion) anonymousClass1.L$1;
            qQSUserTileSpecRepository = (QQSUserTileSpecRepository) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        Resources resources2 = qQSUserTileSpecRepository.resources;
        companion.getClass();
        TilesSettingConverter.INSTANCE.getClass();
        return TilesSettingConverter.toTilesList(resources2, (String) objWithContext);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadTilesFromSettingsAndParse(int i, ContinuationImpl continuationImpl) throws Throwable {
        C09831 c09831;
        if (continuationImpl instanceof C09831) {
            c09831 = (C09831) continuationImpl;
            int i2 = c09831.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c09831.label = i2 - Integer.MIN_VALUE;
            } else {
                c09831 = new C09831(continuationImpl);
            }
        }
        Object objLoadTilesFromSettings = c09831.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c09831.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objLoadTilesFromSettings);
            c09831.L$0 = this;
            c09831.I$0 = i;
            c09831.label = 1;
            objLoadTilesFromSettings = loadTilesFromSettings(i, c09831);
            if (objLoadTilesFromSettings == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = c09831.I$0;
            this = (QQSUserTileSpecRepository) c09831.L$0;
            ResultKt.throwOnFailure(objLoadTilesFromSettings);
        }
        List list = (List) objLoadTilesFromSettings;
        this.getClass();
        boolean zIsEmpty = list.isEmpty();
        QSPipelineLogger qSPipelineLogger = this.logger;
        if (!zIsEmpty) {
            qSPipelineLogger.logParsedTiles(list, false, i, QSType.QQS);
            return list;
        }
        List defaultTiles = this.defaultTilesRepository.getDefaultTiles();
        qSPipelineLogger.logParsedTiles(defaultTiles, true, i, QSType.QQS);
        return defaultTiles;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00a4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object tiles(ContinuationImpl continuationImpl) throws Throwable {
        C09841 c09841;
        QQSUserTileSpecRepository qQSUserTileSpecRepository;
        Flow flow;
        QQSUserTileSpecRepository qQSUserTileSpecRepository2;
        QQSUserTileSpecRepository qQSUserTileSpecRepository3;
        QQSUserTileSpecRepository qQSUserTileSpecRepository4;
        StateFlow stateFlow;
        if (continuationImpl instanceof C09841) {
            c09841 = (C09841) continuationImpl;
            int i = c09841.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09841.label = i - Integer.MIN_VALUE;
            } else {
                c09841 = new C09841(continuationImpl);
            }
        }
        Object objStateIn = c09841.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09841.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStateIn);
            if (this._tiles == null) {
                SharedFlowImpl sharedFlowImpl = this.changeEvents;
                c09841.L$0 = this;
                c09841.L$1 = this;
                c09841.L$2 = sharedFlowImpl;
                c09841.label = 1;
                Object objLoadTilesFromSettingsAndParse = loadTilesFromSettingsAndParse(this.userId, c09841);
                if (objLoadTilesFromSettingsAndParse != coroutineSingletons) {
                    qQSUserTileSpecRepository = this;
                    flow = sharedFlowImpl;
                    objStateIn = objLoadTilesFromSettingsAndParse;
                    qQSUserTileSpecRepository2 = qQSUserTileSpecRepository;
                }
                return coroutineSingletons;
            }
            stateFlow = this._tiles;
            if (stateFlow == null) {
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                qQSUserTileSpecRepository3 = (QQSUserTileSpecRepository) c09841.L$1;
                qQSUserTileSpecRepository4 = (QQSUserTileSpecRepository) c09841.L$0;
                ResultKt.throwOnFailure(objStateIn);
                StateFlow stateFlow2 = (StateFlow) objStateIn;
                qQSUserTileSpecRepository4.getClass();
                QQSUserTileSpecRepository$startFlowCollections$1 qQSUserTileSpecRepository$startFlowCollections$1 = new QQSUserTileSpecRepository$startFlowCollections$1(stateFlow2, qQSUserTileSpecRepository4, null);
                BuildersKt.launch$default(qQSUserTileSpecRepository4.applicationScope, qQSUserTileSpecRepository4.backgroundDispatcher, null, qQSUserTileSpecRepository$startFlowCollections$1, 2);
                qQSUserTileSpecRepository3._tiles = stateFlow2;
                this = qQSUserTileSpecRepository4;
                stateFlow = this._tiles;
                if (stateFlow == null) {
                    return null;
                }
                return stateFlow;
            }
            flow = (Flow) c09841.L$2;
            qQSUserTileSpecRepository2 = (QQSUserTileSpecRepository) c09841.L$1;
            qQSUserTileSpecRepository = (QQSUserTileSpecRepository) c09841.L$0;
            ResultKt.throwOnFailure(objStateIn);
        }
        Flow flowFlowOn = FlowKt.flowOn(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(objStateIn, flow, qQSUserTileSpecRepository.new C09852(null)), qQSUserTileSpecRepository.backgroundDispatcher);
        c09841.L$0 = qQSUserTileSpecRepository;
        c09841.L$1 = qQSUserTileSpecRepository2;
        c09841.L$2 = null;
        c09841.label = 2;
        objStateIn = FlowKt.stateIn(flowFlowOn, qQSUserTileSpecRepository.applicationScope, c09841);
        if (objStateIn != coroutineSingletons) {
            qQSUserTileSpecRepository3 = qQSUserTileSpecRepository2;
            qQSUserTileSpecRepository4 = qQSUserTileSpecRepository;
            StateFlow stateFlow22 = (StateFlow) objStateIn;
            qQSUserTileSpecRepository4.getClass();
            QQSUserTileSpecRepository$startFlowCollections$1 qQSUserTileSpecRepository$startFlowCollections$12 = new QQSUserTileSpecRepository$startFlowCollections$1(stateFlow22, qQSUserTileSpecRepository4, null);
            BuildersKt.launch$default(qQSUserTileSpecRepository4.applicationScope, qQSUserTileSpecRepository4.backgroundDispatcher, null, qQSUserTileSpecRepository$startFlowCollections$12, 2);
            qQSUserTileSpecRepository3._tiles = stateFlow22;
            this = qQSUserTileSpecRepository4;
            stateFlow = this._tiles;
            if (stateFlow == null) {
            }
        }
        return coroutineSingletons;
    }

    public final class AddTile implements UserTileSpecRepository.ChangeAction {
        public final int position;
        public final TileSpec tileSpec;

        public AddTile(TileSpec tileSpec, int i) {
            this.tileSpec = tileSpec;
            this.position = i;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            ArrayList arrayList = new ArrayList(list);
            TileSpec tileSpec = this.tileSpec;
            if (!arrayList.contains(tileSpec)) {
                int i = this.position;
                if (i >= 0 && i < arrayList.size()) {
                    arrayList.add(i, tileSpec);
                    return arrayList;
                }
                arrayList.add(tileSpec);
            }
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AddTile)) {
                return false;
            }
            AddTile addTile = (AddTile) obj;
            return Intrinsics.areEqual(this.tileSpec, addTile.tileSpec) && this.position == addTile.position;
        }

        public final int hashCode() {
            return Integer.hashCode(this.position) + (this.tileSpec.hashCode() * 31);
        }

        public final String toString() {
            return "AddTile(tileSpec=" + this.tileSpec + ", position=" + this.position + ")";
        }

        public /* synthetic */ AddTile(TileSpec tileSpec, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSpec, (i2 & 2) != 0 ? -1 : i);
        }
    }
}
