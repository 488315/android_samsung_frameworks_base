package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.content.ComponentName;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import android.util.Log;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersisterImpl;
import com.android.systemui.qs.external.CustomTileStatePersisterKt;
import com.android.systemui.qs.external.PackageManagerAdapter;
import com.android.systemui.qs.external.TileServiceKey;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.impl.custom.shared.model.TileExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;
import org.json.JSONException;

/* loaded from: classes2.dex */
public final class CustomTileRepositoryImpl implements CustomTileRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineContext backgroundContext;
    public final CustomTileStatePersister customTileStatePersister;
    public final PackageManagerAdapter packageManagerAdapter;
    public final TileSpec.CustomTileSpec tileSpec;
    public final MutexImpl tileUpdateMutex = MutexKt.Mutex$default();
    public final SharedFlowImpl tileWithUserState = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class TileWithUser {
        public final Tile tile;
        public final UserHandle user;

        public TileWithUser(UserHandle userHandle, Tile tile) {
            this.user = userHandle;
            this.tile = tile;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TileWithUser)) {
                return false;
            }
            TileWithUser tileWithUser = (TileWithUser) obj;
            return Intrinsics.areEqual(this.user, tileWithUser.user) && Intrinsics.areEqual(this.tile, tileWithUser.tile);
        }

        public final int hashCode() {
            return this.tile.hashCode() + (this.user.hashCode() * 31);
        }

        public final String toString() {
            return "TileWithUser(user=" + this.user + ", tile=" + this.tile + ")";
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$isTileActive$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomTileRepositoryImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Bundle bundle;
            UserHandle userHandle;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = false;
            try {
                CustomTileRepositoryImpl customTileRepositoryImpl = CustomTileRepositoryImpl.this;
                PackageManagerAdapter packageManagerAdapter = customTileRepositoryImpl.packageManagerAdapter;
                ComponentName componentName = customTileRepositoryImpl.tileSpec.componentName;
                TileWithUser currentTileWithUser = customTileRepositoryImpl.getCurrentTileWithUser();
                ServiceInfo serviceInfo = packageManagerAdapter.mIPackageManager.getServiceInfo(componentName, 794752, (currentTileWithUser == null || (userHandle = currentTileWithUser.user) == null) ? -2 : userHandle.getIdentifier());
                if (serviceInfo != null && (bundle = serviceInfo.metaData) != null) {
                    if (bundle.getBoolean("android.service.quicksettings.ACTIVE_TILE", false)) {
                        z = true;
                    }
                }
            } catch (RemoteException unused) {
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$isTileToggleable$2, reason: invalid class name and case insensitive filesystem */
    final class C10142 extends SuspendLambda implements Function2 {
        int label;

        public C10142(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomTileRepositoryImpl.this.new C10142(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10142) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Bundle bundle;
            UserHandle userHandle;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = false;
            try {
                CustomTileRepositoryImpl customTileRepositoryImpl = CustomTileRepositoryImpl.this;
                PackageManagerAdapter packageManagerAdapter = customTileRepositoryImpl.packageManagerAdapter;
                ComponentName componentName = customTileRepositoryImpl.tileSpec.componentName;
                TileWithUser currentTileWithUser = customTileRepositoryImpl.getCurrentTileWithUser();
                ServiceInfo serviceInfo = packageManagerAdapter.mIPackageManager.getServiceInfo(componentName, 794752, (currentTileWithUser == null || (userHandle = currentTileWithUser.user) == null) ? -2 : userHandle.getIdentifier());
                if (serviceInfo != null && (bundle = serviceInfo.metaData) != null) {
                    if (bundle.getBoolean("android.service.quicksettings.TOGGLEABLE_TILE", false)) {
                        z = true;
                    }
                }
            } catch (RemoteException unused) {
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2, reason: invalid class name and case insensitive filesystem */
    final class C10152 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10152(UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomTileRepositoryImpl.this.new C10152(this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10152) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x006d A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            Tile tileFromString;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CustomTileRepositoryImpl customTileRepositoryImpl = CustomTileRepositoryImpl.this;
                String string = ((CustomTileStatePersisterImpl) customTileRepositoryImpl.customTileStatePersister).sharedPreferences.getString(new TileServiceKey(customTileRepositoryImpl.tileSpec.componentName, this.$user.getIdentifier()).string, null);
                if (string == null) {
                    tileFromString = null;
                    if (tileFromString != null) {
                        return null;
                    }
                    CustomTileRepositoryImpl customTileRepositoryImpl2 = CustomTileRepositoryImpl.this;
                    UserHandle userHandle = this.$user;
                    this.label = 1;
                    customTileRepositoryImpl2.getClass();
                    Object objUpdateTile = customTileRepositoryImpl2.updateTile(userHandle, true, new CustomTileRepositoryImpl$$ExternalSyntheticLambda1(tileFromString), this);
                    if (objUpdateTile != CoroutineSingletons.COROUTINE_SUSPENDED) {
                        objUpdateTile = Unit.INSTANCE;
                    }
                    if (objUpdateTile == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    try {
                        tileFromString = CustomTileStatePersisterKt.readTileFromString(string);
                    } catch (JSONException e) {
                        Log.e("TileServicePersistence", "Bad saved state: ".concat(string), e);
                    }
                    if (tileFromString != null) {
                    }
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

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$updateTile$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            CustomTileRepositoryImpl customTileRepositoryImpl = CustomTileRepositoryImpl.this;
            int i = CustomTileRepositoryImpl.$r8$clinit;
            return customTileRepositoryImpl.updateTile(null, false, null, this);
        }
    }

    static {
        new Companion(null);
    }

    public CustomTileRepositoryImpl(TileSpec.CustomTileSpec customTileSpec, CustomTileStatePersister customTileStatePersister, PackageManagerAdapter packageManagerAdapter, CoroutineContext coroutineContext) {
        this.tileSpec = customTileSpec;
        this.customTileStatePersister = customTileStatePersister;
        this.packageManagerAdapter = packageManagerAdapter;
        this.backgroundContext = coroutineContext;
    }

    public final TileWithUser getCurrentTileWithUser() {
        return (TileWithUser) CollectionsKt___CollectionsKt.lastOrNull(this.tileWithUserState.getReplayCache());
    }

    public final Object isTileActive(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundContext, new AnonymousClass2(null), continuationImpl);
    }

    public final Object isTileToggleable(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundContext, new C10142(null), continuation);
    }

    public final Object restoreForTheUserIfNeeded(UserHandle userHandle, Continuation continuation, boolean z) {
        if (z) {
            TileWithUser currentTileWithUser = getCurrentTileWithUser();
            if (!Intrinsics.areEqual(currentTileWithUser != null ? currentTileWithUser.user : null, userHandle)) {
                return BuildersKt.withContext(this.backgroundContext, new C10152(userHandle, null), continuation);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r6v11, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v13, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v3, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateTile(UserHandle userHandle, boolean z, Function1 function1, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        MutexImpl mutexImpl;
        Tile tileCopy;
        CustomTileRepositoryImpl customTileRepositoryImpl;
        UserHandle userHandle2;
        MutexImpl mutexImpl2;
        Tile tile;
        ?? r7;
        Throwable th;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                mutexImpl = this.tileUpdateMutex;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = userHandle;
                anonymousClass1.L$2 = function1;
                anonymousClass1.L$3 = mutexImpl;
                anonymousClass1.Z$0 = z;
                anonymousClass1.label = 1;
                if (mutexImpl.lock(anonymousClass1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                tile = (Tile) anonymousClass1.L$3;
                r7 = (Mutex) anonymousClass1.L$2;
                userHandle2 = (UserHandle) anonymousClass1.L$1;
                customTileRepositoryImpl = (CustomTileRepositoryImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    mutexImpl2 = r7;
                    tileCopy = tile;
                    mutexImpl = mutexImpl2;
                    userHandle = userHandle2;
                    this = customTileRepositoryImpl;
                    this.tileWithUserState.tryEmit(new TileWithUser(userHandle, tileCopy));
                    Unit unit = Unit.INSTANCE;
                    mutexImpl.unlock(null);
                    return unit;
                } catch (Throwable th2) {
                    th = th2;
                    r7.unlock(null);
                    throw th;
                }
            }
            z = anonymousClass1.Z$0;
            ?? r6 = (Mutex) anonymousClass1.L$3;
            function1 = (Function1) anonymousClass1.L$2;
            userHandle = (UserHandle) anonymousClass1.L$1;
            CustomTileRepositoryImpl customTileRepositoryImpl2 = (CustomTileRepositoryImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            mutexImpl = r6;
            this = customTileRepositoryImpl2;
            TileWithUser currentTileWithUser = this.getCurrentTileWithUser();
            tileCopy = Intrinsics.areEqual(currentTileWithUser != null ? currentTileWithUser.user : null, userHandle) ? TileExtKt.copy(currentTileWithUser.tile) : new Tile();
            function1.mo781invoke(tileCopy);
            if (z) {
                CoroutineContext coroutineContext = this.backgroundContext;
                CustomTileRepositoryImpl$updateTile$2$1 customTileRepositoryImpl$updateTile$2$1 = new CustomTileRepositoryImpl$updateTile$2$1(this, userHandle, tileCopy, null);
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = userHandle;
                anonymousClass1.L$2 = mutexImpl;
                anonymousClass1.L$3 = tileCopy;
                anonymousClass1.label = 2;
                if (BuildersKt.withContext(coroutineContext, customTileRepositoryImpl$updateTile$2$1, anonymousClass1) != coroutineSingletons) {
                    customTileRepositoryImpl = this;
                    userHandle2 = userHandle;
                    mutexImpl2 = mutexImpl;
                    tile = tileCopy;
                    tileCopy = tile;
                    mutexImpl = mutexImpl2;
                    userHandle = userHandle2;
                    this = customTileRepositoryImpl;
                }
                return coroutineSingletons;
            }
            this.tileWithUserState.tryEmit(new TileWithUser(userHandle, tileCopy));
            Unit unit2 = Unit.INSTANCE;
            mutexImpl.unlock(null);
            return unit2;
        } catch (Throwable th3) {
            th = th3;
            r7 = mutexImpl;
            r7.unlock(null);
            throw th;
        }
    }
}
