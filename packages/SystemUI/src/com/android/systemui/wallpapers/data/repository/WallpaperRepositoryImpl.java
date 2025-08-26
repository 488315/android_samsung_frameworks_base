package com.android.systemui.wallpapers.data.repository;

import android.R;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.UserHandle;
import android.view.View;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.model.SelectionStatus;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$getWallpaperInfo$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            String str = WallpaperRepositoryImpl.TAG;
            return new Pair((Unit) obj, (SelectedUserModel) obj2);
        }
    }

    /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$getWallpaperInfo$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $which;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(int i, Continuation continuation) {
            super(2, continuation);
            this.$which = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = WallpaperRepositoryImpl.this.new AnonymousClass4(this.$which, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            SelectedUserModel selectedUserModel = (SelectedUserModel) ((Pair) this.L$0).component2();
            WallpaperRepositoryImpl wallpaperRepositoryImpl = WallpaperRepositoryImpl.this;
            int i2 = this.$which;
            this.label = 1;
            String str = WallpaperRepositoryImpl.TAG;
            wallpaperRepositoryImpl.getClass();
            Object objWithContext = BuildersKt.withContext(wallpaperRepositoryImpl.bgDispatcher, new WallpaperRepositoryImpl$getWallpaper$2(i2, wallpaperRepositoryImpl, selectedUserModel, null), this);
            return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((SelectedUserModel) obj).selectionStatus == SelectionStatus.SELECTION_COMPLETE) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.wallpaperInfo = getWallpaperInfo(1);
        final ReadonlyStateFlow wallpaperInfo = getWallpaperInfo(2);
        this.lockscreenWallpaperInfo = wallpaperInfo;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, -1, "doze_always_on_wallpaper_enabled")), ((ConfigurationInteractorImpl) configurationInteractor).onAnyConfigurationChange, WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4.INSTANCE);
        this.wallpaperSupportsAmbientMode = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Resources.NotFoundException {
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
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        WallpaperRepositoryImpl wallpaperRepositoryImpl = this.this$0;
                        if (wallpaperRepositoryImpl.secureSettings.getInt("doze_always_on_wallpaper_enabled", 1) == 1) {
                            wallpaperRepositoryImpl.context.getResources().getBoolean(R.bool.config_earcFeatureDisabled_allowed);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(false, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.shouldSendFocalArea = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Resources.NotFoundException {
                    AnonymousClass1 anonymousClass1;
                    ComponentName component;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        WallpaperInfo wallpaperInfo = (WallpaperInfo) obj;
                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((wallpaperInfo == null || (component = wallpaperInfo.getComponent()) == null) ? null : component.getClassName(), this.this$0.context.getResources().getString(com.android.systemui.R.string.focal_area_target)));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = wallpaperInfo.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }

    public final ReadonlyStateFlow getWallpaperInfo(int i) {
        if (!this.wallpaperManager.isWallpaperSupported()) {
            return FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(null));
        }
        Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.wallpaperChanged, this.selectedUser, AnonymousClass3.INSTANCE), new AnonymousClass4(i, null)), -1, 2);
        SharingStarted.Companion.getClass();
        return FlowKt.stateIn(flowBuffer$default, this.scope, SharingStarted.Companion.Eagerly, null);
    }
}
