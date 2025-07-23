package com.android.systemui.wallpapers.data.repository;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperRepositoryImpl implements WallpaperRepository {
    public static final boolean DEBUG;
    public static final String TAG;
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public final ReadonlyStateFlow lockscreenWallpaperInfo;
    public View rootView;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final WallpaperRepositoryImpl$special$$inlined$filter$1 selectedUser;
    public final ReadonlyStateFlow shouldSendFocalArea;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 wallpaperChanged;
    public final ReadonlyStateFlow wallpaperInfo;
    public final WallpaperManager wallpaperManager;
    public final Flow wallpaperSupportsAmbientMode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(WallpaperRepositoryImpl.class).getSimpleName();
        DEBUG = true;
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1] */
    public WallpaperRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BroadcastDispatcher broadcastDispatcher, UserRepository userRepository, WallpaperManager wallpaperManager, Context context, SecureSettings secureSettings, ConfigurationInteractor configurationInteractor) {
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.wallpaperManager = wallpaperManager;
        this.context = context;
        this.secureSettings = secureSettings;
        this.wallpaperChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new WallpaperRepositoryImpl$wallpaperChanged$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"), UserHandle.ALL, 12));
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        this.selectedUser = new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.user.data.model.SelectedUserModel r6 = (com.android.systemui.user.data.model.SelectedUserModel) r6
                        com.android.systemui.user.data.model.SelectionStatus r6 = r6.selectionStatus
                        com.android.systemui.user.data.model.SelectionStatus r2 = com.android.systemui.user.data.model.SelectionStatus.SELECTION_COMPLETE
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.wallpaperInfo = getWallpaperInfo(1);
        final ReadonlyStateFlow wallpaperInfo = getWallpaperInfo(2);
        this.lockscreenWallpaperInfo = wallpaperInfo;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, -1, "doze_always_on_wallpaper_enabled")), ((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange, WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4.INSTANCE);
        this.wallpaperSupportsAmbientMode = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ WallpaperRepositoryImpl this$0;

                /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, WallpaperRepositoryImpl wallpaperRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = wallpaperRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl r5 = r4.this$0
                        com.android.systemui.util.settings.SecureSettings r6 = r5.secureSettings
                        java.lang.String r2 = "doze_always_on_wallpaper_enabled"
                        int r6 = r6.getInt(r2, r3)
                        if (r6 != r3) goto L4c
                        android.content.Context r5 = r5.context
                        android.content.res.Resources r5 = r5.getResources()
                        r6 = 17891673(0x1110159, float:2.663326E-38)
                        r5.getBoolean(r6)
                    L4c:
                        r5 = 0
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.shouldSendFocalArea = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ WallpaperRepositoryImpl this$0;

                /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, WallpaperRepositoryImpl wallpaperRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = wallpaperRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.app.WallpaperInfo r5 = (android.app.WallpaperInfo) r5
                        com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl r6 = r4.this$0
                        android.content.Context r6 = r6.context
                        android.content.res.Resources r6 = r6.getResources()
                        r2 = 2131953584(0x7f1307b0, float:1.9543643E38)
                        java.lang.String r6 = r6.getString(r2)
                        if (r5 == 0) goto L50
                        android.content.ComponentName r5 = r5.getComponent()
                        if (r5 == 0) goto L50
                        java.lang.String r5 = r5.getClassName()
                        goto L51
                    L50:
                        r5 = 0
                    L51:
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    public final ReadonlyStateFlow getWallpaperInfo(int i) {
        if (!this.wallpaperManager.isWallpaperSupported()) {
            return FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(null));
        }
        Flow buffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.wallpaperChanged, this.selectedUser, WallpaperRepositoryImpl$getWallpaperInfo$3.INSTANCE), new WallpaperRepositoryImpl$getWallpaperInfo$4(this, i, null)), -1, 2);
        SharingStarted.Companion.getClass();
        return FlowKt.stateIn(buffer$default, this.scope, SharingStarted.Companion.Eagerly, null);
    }
}
