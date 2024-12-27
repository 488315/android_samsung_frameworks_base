package com.android.systemui.wallpapers.data.repository;

import android.R;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowKt;

public final class WallpaperRepositoryImpl implements WallpaperRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final WallpaperRepositoryImpl$special$$inlined$filter$1 selectedUser;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 wallpaperChanged;
    public final ReadonlyStateFlow wallpaperInfo;
    public final WallpaperManager wallpaperManager;
    public final ReadonlyStateFlow wallpaperSupportsAmbientMode;

    public WallpaperRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BroadcastDispatcher broadcastDispatcher, UserRepository userRepository, WallpaperManager wallpaperManager, Context context) {
        final ReadonlyStateFlow asStateFlow;
        this.bgDispatcher = coroutineDispatcher;
        this.wallpaperManager = wallpaperManager;
        boolean z = context.getResources().getBoolean(R.bool.config_dreamsEnabledByDefault);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new WallpaperRepositoryImpl$wallpaperChanged$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"), UserHandle.ALL, 0, null, 12));
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        Flow flow = new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$filter$1

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
        if (wallpaperManager.isWallpaperSupported() && z) {
            Flow buffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flow, WallpaperRepositoryImpl$wallpaperInfo$2.INSTANCE), new WallpaperRepositoryImpl$wallpaperInfo$3(this, null)), -1);
            SharingStarted.Companion.getClass();
            asStateFlow = FlowKt.stateIn(buffer$default, coroutineScope, SharingStarted.Companion.Eagerly, null);
        } else {
            asStateFlow = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(null));
        }
        this.wallpaperInfo = asStateFlow;
        Flow flow2 = new Flow() { // from class: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

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
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.app.WallpaperInfo r5 = (android.app.WallpaperInfo) r5
                        r6 = 0
                        if (r5 == 0) goto L3e
                        boolean r5 = r5.supportsAmbientMode()
                        if (r5 != r3) goto L3e
                        r6 = r3
                    L3e:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.data.repository.WallpaperRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        WallpaperInfo wallpaperInfo = (WallpaperInfo) asStateFlow.$$delegate_0.getValue();
        boolean z2 = false;
        if (wallpaperInfo != null && wallpaperInfo.supportsAmbientMode()) {
            z2 = true;
        }
        this.wallpaperSupportsAmbientMode = FlowKt.stateIn(flow2, coroutineScope, startedEagerly, Boolean.valueOf(z2));
    }
}
