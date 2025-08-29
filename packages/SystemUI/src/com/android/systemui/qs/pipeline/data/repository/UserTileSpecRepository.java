package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.ScRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TilesUpgradePath;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
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
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class UserTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public StateFlow _tiles;
    public final BufferedChannel _tilesUpgradePath;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl changeEvents;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;
    public final BufferedChannel tilesUpgradePath;
    public final int userId;

    public interface ChangeAction {
        List apply(List list);
    }

    public final class ChangeTiles implements ChangeAction {
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
            return arrayList;
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
        UserTileSpecRepository create(int i);
    }

    public final class PrependDefault implements ChangeAction {
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

    public final class RemoveTiles implements ChangeAction {
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

    public final class ResetToDefault implements ChangeAction {
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

    public final class RestoreTiles implements ChangeAction {
        public final Set currentAutoAdded;
        public final RestoreData restoreData;

        public RestoreTiles(RestoreData restoreData, Set<? extends TileSpec> set) {
            this.restoreData = restoreData;
            this.currentAutoAdded = set;
        }

        @Override // com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository.ChangeAction
        public final List apply(List list) {
            Companion companion = UserTileSpecRepository.Companion;
            Set set = this.currentAutoAdded;
            companion.getClass();
            RestoreData restoreData = this.restoreData;
            ArrayList arrayList = new ArrayList(restoreData.restoredTiles);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : set) {
                if (!restoreData.restoredAutoAddedTiles.contains((TileSpec) obj)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList2.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                TileSpec tileSpec = (TileSpec) obj2;
                if (list.contains(tileSpec) && !restoreData.restoredTiles.contains(tileSpec)) {
                    arrayList3.add(obj2);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            int size2 = arrayList3.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj3 = arrayList3.get(i3);
                i3++;
                TileSpec tileSpec2 = (TileSpec) obj3;
                arrayList4.add(new Pair(tileSpec2, Integer.valueOf(list.indexOf(tileSpec2))));
            }
            for (Object obj4 : CollectionsKt___CollectionsKt.sortedWith(arrayList4, new Comparator() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Companion$reconcileTiles$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj5, Object obj6) {
                    return ComparisonsKt__ComparisonsKt.compareValues((Integer) ((Pair) obj5).getSecond(), (Integer) ((Pair) obj6).getSecond());
                }
            })) {
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                Pair pair = (Pair) obj4;
                TileSpec tileSpec3 = (TileSpec) pair.component1();
                int iIntValue = ((Number) pair.component2()).intValue() + i;
                if (iIntValue > arrayList.size()) {
                    arrayList.add(tileSpec3);
                } else {
                    arrayList.add(iIntValue, tileSpec3);
                }
                i = i4;
            }
            return arrayList;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RestoreTiles)) {
                return false;
            }
            RestoreTiles restoreTiles = (RestoreTiles) obj;
            return Intrinsics.areEqual(this.restoreData, restoreTiles.restoreData) && Intrinsics.areEqual(this.currentAutoAdded, restoreTiles.currentAutoAdded);
        }

        public final int hashCode() {
            return this.currentAutoAdded.hashCode() + (this.restoreData.hashCode() * 31);
        }

        public final String toString() {
            return "RestoreTiles(restoreData=" + this.restoreData + ", currentAutoAdded=" + this.currentAutoAdded + ")";
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$1, reason: invalid class name */
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
            UserTileSpecRepository userTileSpecRepository = UserTileSpecRepository.this;
            Companion companion = UserTileSpecRepository.Companion;
            return userTileSpecRepository.loadTilesFromSettings(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettings$2, reason: invalid class name */
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
            return UserTileSpecRepository.this.new AnonymousClass2(this.$userId, continuation);
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
            String stringForUser = UserTileSpecRepository.this.secureSettings.getStringForUser("sysui_qs_tiles", this.$userId);
            return stringForUser == null ? "" : stringForUser;
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$loadTilesFromSettingsAndParse$1, reason: invalid class name and case insensitive filesystem */
    final class C09901 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C09901(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            UserTileSpecRepository userTileSpecRepository = UserTileSpecRepository.this;
            Companion companion = UserTileSpecRepository.Companion;
            return userTileSpecRepository.loadTilesFromSettingsAndParse(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$1, reason: invalid class name and case insensitive filesystem */
    final class C09911 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09911(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UserTileSpecRepository.this.tiles(this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$tiles$2, reason: invalid class name and case insensitive filesystem */
    final class C09922 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C09922(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C09922 c09922 = UserTileSpecRepository.this.new C09922((Continuation) obj3);
            c09922.L$0 = (List) obj;
            c09922.L$1 = (ChangeAction) obj2;
            return c09922.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x00d6  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            List listApply;
            List list;
            List listSingletonList;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                List list2 = (List) this.L$0;
                ChangeAction changeAction = (ChangeAction) this.L$1;
                listApply = changeAction.apply(list2);
                UserTileSpecRepository userTileSpecRepository = UserTileSpecRepository.this;
                if (!Intrinsics.areEqual(list2, listApply)) {
                    Log.d("UserTileSpecRepository", "changed qs_tiles from " + list2 + " to " + listApply);
                    if (changeAction instanceof RestoreTiles) {
                        QSPipelineLogger qSPipelineLogger = userTileSpecRepository.logger;
                        qSPipelineLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(6);
                        LogBuffer logBuffer = qSPipelineLogger.tileListLogBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.str1 = list2.toString();
                        logMessageImpl.str2 = listApply.toString();
                        logMessageImpl.int1 = userTileSpecRepository.userId;
                        logBuffer.commit(logMessageObtain);
                    } else {
                        QSPipelineLogger qSPipelineLogger2 = userTileSpecRepository.logger;
                        qSPipelineLogger2.getClass();
                        boolean z = ScRune.QUICK_MANAGE_MULTI_QSHOST;
                        int i2 = userTileSpecRepository.userId;
                        if (z) {
                            qSPipelineLogger2.logProcessTileChange(changeAction, listApply, i2, QSType.QS);
                        } else {
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda02 = new QSPipelineLogger$$ExternalSyntheticLambda0(3);
                            LogBuffer logBuffer2 = qSPipelineLogger2.tileListLogBuffer;
                            LogMessage logMessageObtain2 = logBuffer2.obtain("QSTileListLog", logLevel2, qSPipelineLogger$$ExternalSyntheticLambda02, null);
                            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                            logMessageImpl2.str1 = changeAction.toString();
                            logMessageImpl2.str2 = listApply.toString();
                            logMessageImpl2.int1 = i2;
                            logBuffer2.commit(logMessageObtain2);
                        }
                    }
                }
                if (changeAction instanceof RestoreTiles) {
                    BufferedChannel bufferedChannel = userTileSpecRepository._tilesUpgradePath;
                    TilesUpgradePath.RestoreFromBackup restoreFromBackupM2912boximpl = TilesUpgradePath.RestoreFromBackup.m2912boximpl(CollectionsKt___CollectionsKt.toSet(listApply));
                    this.L$0 = listApply;
                    this.label = 1;
                    if (bufferedChannel.send(restoreFromBackupM2912boximpl, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    list = listApply;
                }
                listSingletonList = listApply;
                if (listSingletonList.isEmpty()) {
                    listSingletonList = Collections.singletonList(TileSpec.Empty.INSTANCE);
                }
                return CollectionsKt___CollectionsKt.distinct(listSingletonList);
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
            listApply = list;
            listSingletonList = listApply;
            if (listSingletonList.isEmpty()) {
            }
            return CollectionsKt___CollectionsKt.distinct(listSingletonList);
        }
    }

    public UserTileSpecRepository(int i, Resources resources, DefaultTilesRepository defaultTilesRepository, SecureSettings secureSettings, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.userId = i;
        this.resources = resources;
        this.defaultTilesRepository = defaultTilesRepository;
        this.secureSettings = secureSettings;
        this.logger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
        BufferedChannel bufferedChannelChannel$default = ChannelKt.Channel$default(3, null, null, 6);
        this._tilesUpgradePath = bufferedChannelChannel$default;
        this.tilesUpgradePath = bufferedChannelChannel$default;
        this.changeEvents = SharedFlowKt.MutableSharedFlow$default(0, 10, null, 5);
    }

    public static final Object access$storeTiles(UserTileSpecRepository userTileSpecRepository, int i, List list, Continuation continuation) throws Throwable {
        userTileSpecRepository.getClass();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object objWithContext = BuildersKt.withContext(userTileSpecRepository.backgroundDispatcher, new UserTileSpecRepository$storeTiles$2(userTileSpecRepository, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$storeTiles$toStore$2
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
        UserTileSpecRepository userTileSpecRepository;
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
                    String tiles = (tileDataSource != null ? tileDataSource : null).getTiles();
                    Resources resources = this.resources;
                    companion2.getClass();
                    TilesSettingConverter.INSTANCE.getClass();
                    return TilesSettingConverter.toTilesList(resources, tiles);
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
            userTileSpecRepository = this;
            companion = companion2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            companion = (Companion) anonymousClass1.L$1;
            userTileSpecRepository = (UserTileSpecRepository) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        Resources resources2 = userTileSpecRepository.resources;
        companion.getClass();
        TilesSettingConverter.INSTANCE.getClass();
        return TilesSettingConverter.toTilesList(resources2, (String) objWithContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x007e, code lost:
    
        if (r2.send(r3, r0) == r1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0081, code lost:
    
        r0 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0095, code lost:
    
        if (r2.send(r4, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadTilesFromSettingsAndParse(int i, ContinuationImpl continuationImpl) throws Throwable {
        C09901 c09901;
        boolean zIsEmpty;
        if (continuationImpl instanceof C09901) {
            c09901 = (C09901) continuationImpl;
            int i2 = c09901.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c09901.label = i2 - Integer.MIN_VALUE;
            } else {
                c09901 = new C09901(continuationImpl);
            }
        }
        Object objLoadTilesFromSettings = c09901.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c09901.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objLoadTilesFromSettings);
            c09901.L$0 = this;
            c09901.I$0 = i;
            c09901.label = 1;
            objLoadTilesFromSettings = loadTilesFromSettings(i, c09901);
            if (objLoadTilesFromSettings != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 != 1) {
            if (i3 != 2 && i3 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i4 = c09901.I$0;
            List list = (List) c09901.L$1;
            UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) c09901.L$0;
            ResultKt.throwOnFailure(objLoadTilesFromSettings);
            userTileSpecRepository.getClass();
            zIsEmpty = list.isEmpty();
            QSPipelineLogger qSPipelineLogger = userTileSpecRepository.logger;
            if (zIsEmpty) {
                qSPipelineLogger.logParsedTiles(list, false, i4);
                return list;
            }
            List defaultTiles = userTileSpecRepository.defaultTilesRepository.getDefaultTiles();
            qSPipelineLogger.logParsedTiles(defaultTiles, true, i4);
            return defaultTiles;
        }
        i = c09901.I$0;
        this = (UserTileSpecRepository) c09901.L$0;
        ResultKt.throwOnFailure(objLoadTilesFromSettings);
        List list2 = (List) objLoadTilesFromSettings;
        if (list2.isEmpty()) {
            BufferedChannel bufferedChannel = this._tilesUpgradePath;
            TilesUpgradePath.DefaultSet defaultSet = TilesUpgradePath.DefaultSet.INSTANCE;
            c09901.L$0 = this;
            c09901.L$1 = list2;
            c09901.I$0 = i;
            c09901.label = 3;
        } else {
            BufferedChannel bufferedChannel2 = this._tilesUpgradePath;
            TilesUpgradePath.ReadFromSettings readFromSettingsM2911boximpl = TilesUpgradePath.ReadFromSettings.m2911boximpl(CollectionsKt___CollectionsKt.toSet(list2));
            c09901.L$0 = this;
            c09901.L$1 = list2;
            c09901.I$0 = i;
            c09901.label = 2;
        }
        userTileSpecRepository.getClass();
        zIsEmpty = list.isEmpty();
        QSPipelineLogger qSPipelineLogger2 = userTileSpecRepository.logger;
        if (zIsEmpty) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00a5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object tiles(ContinuationImpl continuationImpl) throws Throwable {
        C09911 c09911;
        UserTileSpecRepository userTileSpecRepository;
        Flow flow;
        UserTileSpecRepository userTileSpecRepository2;
        UserTileSpecRepository userTileSpecRepository3;
        UserTileSpecRepository userTileSpecRepository4;
        StateFlow stateFlow;
        if (continuationImpl instanceof C09911) {
            c09911 = (C09911) continuationImpl;
            int i = c09911.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09911.label = i - Integer.MIN_VALUE;
            } else {
                c09911 = new C09911(continuationImpl);
            }
        }
        Object objStateIn = c09911.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09911.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objStateIn);
            if (this._tiles == null) {
                SharedFlowImpl sharedFlowImpl = this.changeEvents;
                c09911.L$0 = this;
                c09911.L$1 = this;
                c09911.L$2 = sharedFlowImpl;
                c09911.label = 1;
                Object objLoadTilesFromSettingsAndParse = loadTilesFromSettingsAndParse(this.userId, c09911);
                if (objLoadTilesFromSettingsAndParse != coroutineSingletons) {
                    userTileSpecRepository = this;
                    flow = sharedFlowImpl;
                    objStateIn = objLoadTilesFromSettingsAndParse;
                    userTileSpecRepository2 = userTileSpecRepository;
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
                userTileSpecRepository3 = (UserTileSpecRepository) c09911.L$1;
                userTileSpecRepository4 = (UserTileSpecRepository) c09911.L$0;
                ResultKt.throwOnFailure(objStateIn);
                StateFlow stateFlow2 = (StateFlow) objStateIn;
                userTileSpecRepository4.getClass();
                CoroutineTracingKt.launchTraced$default(userTileSpecRepository4.applicationScope, userTileSpecRepository4.backgroundDispatcher, null, new UserTileSpecRepository$startFlowCollections$1(stateFlow2, userTileSpecRepository4, null), 5);
                userTileSpecRepository3._tiles = stateFlow2;
                this = userTileSpecRepository4;
                stateFlow = this._tiles;
                if (stateFlow == null) {
                    return null;
                }
                return stateFlow;
            }
            flow = (Flow) c09911.L$2;
            userTileSpecRepository2 = (UserTileSpecRepository) c09911.L$1;
            userTileSpecRepository = (UserTileSpecRepository) c09911.L$0;
            ResultKt.throwOnFailure(objStateIn);
        }
        Flow flowFlowOn = FlowKt.flowOn(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(objStateIn, flow, userTileSpecRepository.new C09922(null)), userTileSpecRepository.backgroundDispatcher);
        c09911.L$0 = userTileSpecRepository;
        c09911.L$1 = userTileSpecRepository2;
        c09911.L$2 = null;
        c09911.label = 2;
        objStateIn = FlowKt.stateIn(flowFlowOn, userTileSpecRepository.applicationScope, c09911);
        if (objStateIn != coroutineSingletons) {
            userTileSpecRepository3 = userTileSpecRepository2;
            userTileSpecRepository4 = userTileSpecRepository;
            StateFlow stateFlow22 = (StateFlow) objStateIn;
            userTileSpecRepository4.getClass();
            CoroutineTracingKt.launchTraced$default(userTileSpecRepository4.applicationScope, userTileSpecRepository4.backgroundDispatcher, null, new UserTileSpecRepository$startFlowCollections$1(stateFlow22, userTileSpecRepository4, null), 5);
            userTileSpecRepository3._tiles = stateFlow22;
            this = userTileSpecRepository4;
            stateFlow = this._tiles;
            if (stateFlow == null) {
            }
        }
        return coroutineSingletons;
    }

    public final class AddTile implements ChangeAction {
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
