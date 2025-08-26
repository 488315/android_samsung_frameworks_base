package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$getTiles$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$getTiles$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class CustomTileInteractor {
    public final CoroutineContext backgroundContext;
    public UserHandle currentUser;
    public final CustomTileRepository customTileRepository;
    public final CustomTileDefaultsRepository defaultsRepository;
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;
    public StandaloneCoroutine updatesJob;
    public final MutexImpl userMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileUpdates = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$initForUser$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomTileInteractor.this.initForUser(null, this);
        }
    }

    public CustomTileInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileRepository customTileRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.customTileRepository = customTileRepository;
        this.tileScope = coroutineScope;
        this.backgroundContext = coroutineContext;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v17, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v2, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v20, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r11v21 */
    /* JADX WARN: Type inference failed for: r11v24 */
    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v30 */
    /* JADX WARN: Type inference failed for: r11v31 */
    /* JADX WARN: Type inference failed for: r11v33 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r2v18, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object initForUser(UserHandle userHandle, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        MutexImpl mutexImpl;
        ?? r11;
        Object objIsTileActive;
        CustomTileInteractor customTileInteractor;
        CustomTileRepository customTileRepository;
        UserHandle userHandle2;
        boolean zBooleanValue;
        Object obj;
        UserHandle userHandle3;
        CustomTileInteractor customTileInteractor2;
        CustomTileRepositoryImpl$getTiles$$inlined$map$1 customTileRepositoryImpl$getTiles$$inlined$map$1;
        CustomTileInteractor customTileInteractor3;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj2 = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj2);
                    mutexImpl = this.userMutex;
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = userHandle;
                    anonymousClass1.L$2 = mutexImpl;
                    anonymousClass1.label = 1;
                    this = this;
                    if (mutexImpl.lock(anonymousClass1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            if (i2 != 4) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            Mutex mutex = (Mutex) anonymousClass1.L$2;
                            userHandle3 = (UserHandle) anonymousClass1.L$1;
                            customTileInteractor3 = (CustomTileInteractor) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(obj2);
                            this = mutex;
                            customTileInteractor3.currentUser = userHandle3;
                            Unit unit = Unit.INSTANCE;
                            this.unlock(null);
                            return Unit.INSTANCE;
                        }
                        Object obj3 = (Mutex) anonymousClass1.L$2;
                        userHandle3 = (UserHandle) anonymousClass1.L$1;
                        customTileInteractor2 = (CustomTileInteractor) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        obj = obj3;
                        customTileRepositoryImpl$getTiles$$inlined$map$1 = new CustomTileRepositoryImpl$getTiles$$inlined$map$1(new CustomTileRepositoryImpl$getTiles$$inlined$filter$1(((CustomTileRepositoryImpl) customTileInteractor2.customTileRepository).tileWithUserState, userHandle3));
                        anonymousClass1.L$0 = customTileInteractor2;
                        anonymousClass1.L$1 = userHandle3;
                        anonymousClass1.L$2 = obj;
                        anonymousClass1.label = 4;
                        if (FlowKt.firstOrNull(customTileRepositoryImpl$getTiles$$inlined$map$1, anonymousClass1) != coroutineSingletons) {
                            customTileInteractor3 = customTileInteractor2;
                            this = obj;
                            customTileInteractor3.currentUser = userHandle3;
                            Unit unit2 = Unit.INSTANCE;
                            this.unlock(null);
                            return Unit.INSTANCE;
                        }
                        return coroutineSingletons;
                    }
                    customTileRepository = (CustomTileRepository) anonymousClass1.L$4;
                    userHandle = (UserHandle) anonymousClass1.L$3;
                    ?? r2 = (Mutex) anonymousClass1.L$2;
                    UserHandle userHandle4 = (UserHandle) anonymousClass1.L$1;
                    customTileInteractor = (CustomTileInteractor) anonymousClass1.L$0;
                    try {
                        ResultKt.throwOnFailure(obj2);
                        objIsTileActive = obj2;
                        mutexImpl = r2;
                        userHandle2 = userHandle4;
                        zBooleanValue = ((Boolean) objIsTileActive).booleanValue();
                        anonymousClass1.L$0 = customTileInteractor;
                        anonymousClass1.L$1 = userHandle2;
                        anonymousClass1.L$2 = mutexImpl;
                        anonymousClass1.L$3 = null;
                        anonymousClass1.L$4 = null;
                        anonymousClass1.label = 3;
                    } catch (Throwable th) {
                        th = th;
                        this = r2;
                        this.unlock(null);
                        throw th;
                    }
                    try {
                        if (((CustomTileRepositoryImpl) customTileRepository).restoreForTheUserIfNeeded(userHandle, anonymousClass1, zBooleanValue) != coroutineSingletons) {
                            obj = mutexImpl;
                            userHandle3 = userHandle2;
                            customTileInteractor2 = customTileInteractor;
                            customTileRepositoryImpl$getTiles$$inlined$map$1 = new CustomTileRepositoryImpl$getTiles$$inlined$map$1(new CustomTileRepositoryImpl$getTiles$$inlined$filter$1(((CustomTileRepositoryImpl) customTileInteractor2.customTileRepository).tileWithUserState, userHandle3));
                            anonymousClass1.L$0 = customTileInteractor2;
                            anonymousClass1.L$1 = userHandle3;
                            anonymousClass1.L$2 = obj;
                            anonymousClass1.label = 4;
                            if (FlowKt.firstOrNull(customTileRepositoryImpl$getTiles$$inlined$map$1, anonymousClass1) != coroutineSingletons) {
                            }
                        }
                        return coroutineSingletons;
                    } catch (Throwable th2) {
                        th = th2;
                        th = th;
                        this = mutexImpl;
                        this.unlock(null);
                        throw th;
                    }
                }
                ?? r112 = (Mutex) anonymousClass1.L$2;
                userHandle = (UserHandle) anonymousClass1.L$1;
                CustomTileInteractor customTileInteractor4 = (CustomTileInteractor) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj2);
                mutexImpl = r112;
                r11 = customTileInteractor4;
                if (Intrinsics.areEqual(r11.currentUser, userHandle)) {
                    Unit unit3 = Unit.INSTANCE;
                    mutexImpl.unlock(null);
                    return unit3;
                }
                StandaloneCoroutine standaloneCoroutine = r11.updatesJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                try {
                    ((CustomTileDefaultsRepositoryImpl) r11.defaultsRepository).defaultsRequests.tryEmit(new CustomTileDefaultsRepositoryImpl.DefaultsRequest(userHandle, r11.tileSpec.componentName, false));
                    try {
                        r11.updatesJob = CoroutineTracingKt.launchTraced$default(r11.tileScope, null, null, new CustomTileInteractor$launchUpdates$1(r11, userHandle, null), 7);
                        CustomTileRepository customTileRepository2 = r11.customTileRepository;
                        anonymousClass1.L$0 = r11;
                        anonymousClass1.L$1 = userHandle;
                        anonymousClass1.L$2 = mutexImpl;
                        anonymousClass1.L$3 = userHandle;
                        anonymousClass1.L$4 = customTileRepository2;
                        anonymousClass1.label = 2;
                        try {
                            objIsTileActive = ((CustomTileRepositoryImpl) customTileRepository2).isTileActive(anonymousClass1);
                            if (objIsTileActive != coroutineSingletons) {
                                customTileInteractor = r11;
                                customTileRepository = customTileRepository2;
                                userHandle2 = userHandle;
                                zBooleanValue = ((Boolean) objIsTileActive).booleanValue();
                                anonymousClass1.L$0 = customTileInteractor;
                                anonymousClass1.L$1 = userHandle2;
                                anonymousClass1.L$2 = mutexImpl;
                                anonymousClass1.L$3 = null;
                                anonymousClass1.L$4 = null;
                                anonymousClass1.label = 3;
                                if (((CustomTileRepositoryImpl) customTileRepository).restoreForTheUserIfNeeded(userHandle, anonymousClass1, zBooleanValue) != coroutineSingletons) {
                                }
                            }
                            return coroutineSingletons;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            this = mutexImpl;
                            this.unlock(null);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
                this = mutexImpl;
                this.unlock(null);
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
        }
    }
}
