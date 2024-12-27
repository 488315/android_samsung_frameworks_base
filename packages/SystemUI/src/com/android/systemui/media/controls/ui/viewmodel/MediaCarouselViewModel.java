package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.util.Utils;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaCarouselViewModel {
    public boolean allowReorder;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final MediaControlInteractorFactory controlInteractorFactory;
    public final MediaCarouselInteractor interactor;
    public final MediaUiEventLogger logger;
    public final Map mediaControlByInstanceId;
    public final MediaFlags mediaFlags;
    public final ReadonlyStateFlow mediaItems;
    public MediaCommonViewModel.MediaRecommendations mediaRecs;
    public final Set modelsPendingRemoval;
    public final MediaRecommendationsViewModel recommendationsViewModel;
    public Function0 updateHostVisibility;
    public final VisualStabilityProvider visualStabilityProvider;

    public MediaCarouselViewModel(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, VisualStabilityProvider visualStabilityProvider, MediaCarouselInteractor mediaCarouselInteractor, MediaControlInteractorFactory mediaControlInteractorFactory, MediaRecommendationsViewModel mediaRecommendationsViewModel, MediaUiEventLogger mediaUiEventLogger, MediaFlags mediaFlags) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.visualStabilityProvider = visualStabilityProvider;
        this.interactor = mediaCarouselInteractor;
        this.controlInteractorFactory = mediaControlInteractorFactory;
        this.recommendationsViewModel = mediaRecommendationsViewModel;
        this.logger = mediaUiEventLogger;
        this.mediaFlags = mediaFlags;
        final ReadonlyStateFlow readonlyStateFlow = mediaCarouselInteractor.currentMedia;
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaCarouselViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaCarouselViewModel mediaCarouselViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaCarouselViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r24, kotlin.coroutines.Continuation r25) {
                    /*
                        Method dump skipped, instructions count: 376
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
        this.updateHostVisibility = new Function0() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$updateHostVisibility$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.mediaControlByInstanceId = new LinkedHashMap();
        this.modelsPendingRemoval = new LinkedHashSet();
    }

    public static final void access$onMediaControlAddedOrUpdated(MediaCarouselViewModel mediaCarouselViewModel, MediaCommonViewModel mediaCommonViewModel, MediaCommonModel.MediaControl mediaControl) {
        mediaCarouselViewModel.getClass();
        if (!mediaControl.canBeRemoved || Utils.useMediaResumption(mediaCarouselViewModel.applicationContext)) {
            mediaCarouselViewModel.modelsPendingRemoval.remove(mediaControl);
        } else if (mediaCarouselViewModel.visualStabilityProvider.isReorderingAllowed) {
            mediaCommonViewModel.getOnRemoved().invoke(Boolean.TRUE);
        } else {
            mediaCarouselViewModel.modelsPendingRemoval.add(mediaControl);
        }
    }
}
