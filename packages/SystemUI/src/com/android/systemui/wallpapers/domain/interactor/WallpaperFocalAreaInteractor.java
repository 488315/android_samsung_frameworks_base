package com.android.systemui.wallpapers.domain.interactor;

import android.content.Context;
import android.graphics.RectF;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepository;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepositoryImpl;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class WallpaperFocalAreaInteractor {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(WallpaperFocalAreaInteractor.class).getSimpleName();
    public final Context context;
    public final ReadonlyStateFlow hasFocalArea;
    public final Flow wallpaperFocalAreaBounds;
    public final WallpaperFocalAreaRepository wallpaperFocalAreaRepository;

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
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine = FlowKt.combine(((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide, wallpaperFocalAreaRepositoryImpl.notificationStackAbsoluteBottom, wallpaperFocalAreaRepositoryImpl.shortcutAbsoluteTop, wallpaperFocalAreaRepositoryImpl.notificationDefaultTop, new WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1(this, null));
        this.wallpaperFocalAreaBounds = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor$special$$inlined$filter$1

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
                        RectF rectF = (RectF) obj;
                        if (rectF.width() >= 0.0f && rectF.height() >= 0.0f) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }
}
