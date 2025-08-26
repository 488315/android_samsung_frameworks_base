package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import com.android.systemui.ScRune;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class RemovedTileSpecRepositoryImpl implements RemovedTileSpecRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final TestTileDataRepository testTileDataRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$1, reason: invalid class name */
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
            return RemovedTileSpecRepositoryImpl.this.loadTilesFromSettings(0, this);
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$loadTilesFromSettings$2, reason: invalid class name */
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
            return RemovedTileSpecRepositoryImpl.this.new AnonymousClass2(this.$userId, continuation);
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
            String stringForUser = RemovedTileSpecRepositoryImpl.this.secureSettings.getStringForUser("sysui_removed_qs_tiles", this.$userId);
            return stringForUser == null ? "" : stringForUser;
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$storeTiles$2, reason: invalid class name and case insensitive filesystem */
    final class C09862 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $forUser;
        final /* synthetic */ String $toStore;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09862(String str, int i, Continuation continuation) {
            super(2, continuation);
            this.$toStore = str;
            this.$forUser = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RemovedTileSpecRepositoryImpl.this.new C09862(this.$toStore, this.$forUser, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09862) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(RemovedTileSpecRepositoryImpl.this.secureSettings.putStringForUser("sysui_removed_qs_tiles", this.$toStore, null, false, this.$forUser, true));
        }
    }

    public RemovedTileSpecRepositoryImpl(Resources resources, SecureSettings secureSettings, CoroutineDispatcher coroutineDispatcher, TestTileDataRepository testTileDataRepository) {
        this.resources = resources;
        this.secureSettings = secureSettings;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testTileDataRepository = testTileDataRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadTilesFromSettings(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        RemovedTileSpecRepositoryImpl removedTileSpecRepositoryImpl;
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
                    String removedTiles = (tileDataSource != null ? tileDataSource : null).getRemovedTiles();
                    Resources resources = this.resources;
                    companion2.getClass();
                    TilesSettingConverter.INSTANCE.getClass();
                    return TilesSettingConverter.toTilesList(resources, removedTiles);
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
            removedTileSpecRepositoryImpl = this;
            companion = companion2;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            companion = (Companion) anonymousClass1.L$1;
            removedTileSpecRepositoryImpl = (RemovedTileSpecRepositoryImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        Resources resources2 = removedTileSpecRepositoryImpl.resources;
        companion.getClass();
        TilesSettingConverter.INSTANCE.getClass();
        return TilesSettingConverter.toTilesList(resources2, (String) objWithContext);
    }

    public final Object storeTiles(int i, List list, SuspendLambda suspendLambda) throws Throwable {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                arrayList.add(obj);
            }
        }
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C09862(CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.qs.pipeline.data.repository.RemovedTileSpecRepositoryImpl$storeTiles$toStore$2
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj2) {
                return ((TileSpec) obj2).getSpec();
            }
        }, 30), i, null), suspendLambda);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
