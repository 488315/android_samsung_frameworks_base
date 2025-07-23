package com.android.systemui.wallpapers.domain.interactor;

import android.content.Context;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WallpaperFocalAreaInteractor {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(WallpaperFocalAreaInteractor.class).getSimpleName();
    public final Context context;
    public final ReadonlyStateFlow hasFocalArea;
    public final Flow wallpaperFocalAreaBounds;
    public final WallpaperFocalAreaRepository wallpaperFocalAreaRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public WallpaperFocalAreaInteractor(Context context, WallpaperFocalAreaRepository wallpaperFocalAreaRepository, ShadeRepository shadeRepository) {
        this.context = context;
        this.wallpaperFocalAreaRepository = wallpaperFocalAreaRepository;
        WallpaperFocalAreaRepositoryImpl wallpaperFocalAreaRepositoryImpl = (WallpaperFocalAreaRepositoryImpl) wallpaperFocalAreaRepository;
        this.hasFocalArea = wallpaperFocalAreaRepositoryImpl.hasFocalArea;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide, wallpaperFocalAreaRepositoryImpl.notificationStackAbsoluteBottom, wallpaperFocalAreaRepositoryImpl.shortcutAbsoluteTop, wallpaperFocalAreaRepositoryImpl.notificationDefaultTop, new WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1(this, null));
        this.wallpaperFocalAreaBounds = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        android.graphics.RectF r7 = (android.graphics.RectF) r7
                        float r2 = r7.width()
                        r4 = 0
                        int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                        if (r2 < 0) goto L51
                        float r7 = r7.height()
                        int r7 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
                        if (r7 < 0) goto L51
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }
}
