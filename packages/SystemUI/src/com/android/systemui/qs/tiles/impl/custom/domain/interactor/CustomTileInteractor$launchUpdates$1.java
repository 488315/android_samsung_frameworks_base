package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
final class CustomTileInteractor$launchUpdates$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileInteractor this$0;

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ CustomTileInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileInteractor;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$user, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Tile) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
        
            if (r7 == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            Tile tile;
            CustomTileRepository customTileRepository;
            UserHandle userHandle;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                tile = (Tile) this.L$0;
                customTileRepository = this.this$0.customTileRepository;
                UserHandle userHandle2 = this.$user;
                this.L$0 = customTileRepository;
                this.L$1 = userHandle2;
                this.L$2 = tile;
                this.label = 1;
                Object objIsTileActive = ((CustomTileRepositoryImpl) customTileRepository).isTileActive(this);
                if (objIsTileActive != coroutineSingletons) {
                    userHandle = userHandle2;
                    obj = objIsTileActive;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            tile = (Tile) this.L$2;
            userHandle = (UserHandle) this.L$1;
            customTileRepository = (CustomTileRepository) this.L$0;
            ResultKt.throwOnFailure(obj);
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
            CustomTileRepositoryImpl customTileRepositoryImpl = (CustomTileRepositoryImpl) customTileRepository;
            customTileRepositoryImpl.getClass();
            Object objUpdateTile = customTileRepositoryImpl.updateTile(userHandle, zBooleanValue, new CustomTileRepositoryImpl$$ExternalSyntheticLambda1(tile), this);
            if (objUpdateTile != coroutineSingletons) {
                objUpdateTile = Unit.INSTANCE;
            }
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ CustomTileInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileInteractor;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$user, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CustomTileDefaults) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0076, code lost:
        
            if (r7 == r0) goto L22;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) throws Throwable {
            CustomTileDefaults customTileDefaults;
            CustomTileRepository customTileRepository;
            UserHandle userHandle;
            Object objUpdateTile;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                customTileDefaults = (CustomTileDefaults) this.L$0;
                customTileRepository = this.this$0.customTileRepository;
                UserHandle userHandle2 = this.$user;
                this.L$0 = customTileRepository;
                this.L$1 = userHandle2;
                this.L$2 = customTileDefaults;
                this.label = 1;
                Object objIsTileActive = ((CustomTileRepositoryImpl) customTileRepository).isTileActive(this);
                if (objIsTileActive != coroutineSingletons) {
                    userHandle = userHandle2;
                    obj = objIsTileActive;
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            customTileDefaults = (CustomTileDefaults) this.L$2;
            userHandle = (UserHandle) this.L$1;
            customTileRepository = (CustomTileRepository) this.L$0;
            ResultKt.throwOnFailure(obj);
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 2;
            final CustomTileRepositoryImpl customTileRepositoryImpl = (CustomTileRepositoryImpl) customTileRepository;
            customTileRepositoryImpl.getClass();
            if (customTileDefaults instanceof CustomTileDefaults.Result) {
                final CustomTileDefaults.Result result = (CustomTileDefaults.Result) customTileDefaults;
                objUpdateTile = customTileRepositoryImpl.updateTile(userHandle, zBooleanValue, new Function1() { // from class: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda0
                    /* JADX WARN: Removed duplicated region for block: B:6:0x0016  */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object mo781invoke(Object obj2) {
                        Tile tile = (Tile) obj2;
                        int i2 = CustomTileRepositoryImpl.$r8$clinit;
                        Icon icon = tile.getIcon();
                        CustomTileDefaults.Result result2 = result;
                        boolean z = true;
                        if (icon != null) {
                            Icon icon2 = tile.getIcon();
                            Icon icon3 = result2.icon;
                            if (icon3 != null) {
                                boolean zAreEqual = icon2 == icon3 ? true : (icon2.getType() == 2 && icon3.getType() == 2 && icon2.getResId() == icon3.getResId()) ? Intrinsics.areEqual(icon2.getResPackage(), icon3.getResPackage()) : false;
                                if (!zAreEqual) {
                                    z = false;
                                }
                            }
                        }
                        if (z) {
                            tile.setIcon(result2.icon);
                        }
                        tile.setDefaultLabel(result2.label);
                        return Unit.INSTANCE;
                    }
                }, this);
                if (objUpdateTile != coroutineSingletons) {
                    objUpdateTile = Unit.INSTANCE;
                }
            } else {
                objUpdateTile = Unit.INSTANCE;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileInteractor$launchUpdates$1(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileInteractor;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTileInteractor$launchUpdates$1 customTileInteractor$launchUpdates$1 = new CustomTileInteractor$launchUpdates$1(this.this$0, this.$user, continuation);
        customTileInteractor$launchUpdates$1.L$0 = obj;
        return customTileInteractor$launchUpdates$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileInteractor$launchUpdates$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CustomTileInteractor customTileInteractor = this.this$0;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(customTileInteractor.tileUpdates, new AnonymousClass1(customTileInteractor, this.$user, null)), this.this$0.backgroundContext), coroutineScope);
        CustomTileDefaultsRepository customTileDefaultsRepository = this.this$0.defaultsRepository;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1(((CustomTileDefaultsRepositoryImpl) customTileDefaultsRepository).defaults, this.$user)), new AnonymousClass2(this.this$0, this.$user, null)), this.this$0.backgroundContext), coroutineScope);
        return Unit.INSTANCE;
    }
}
