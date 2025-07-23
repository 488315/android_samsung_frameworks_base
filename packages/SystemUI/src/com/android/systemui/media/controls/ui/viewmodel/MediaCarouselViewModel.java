package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaCarouselViewModel {
    public boolean allowReorder;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final MediaControlInteractorFactory controlInteractorFactory;
    public final ReadonlyStateFlow hasActiveMediaOrRecommendations;
    public final ReadonlyStateFlow hasAnyMediaOrRecommendations;
    public final MediaCarouselInteractor interactor;
    public final MediaUiEventLogger logger;
    public final Map mediaControlByInstanceId;
    public final ReadonlyStateFlow mediaItems;
    public final MediaLogger mediaLogger;
    public final Set modelsPendingRemoval;
    public Function0 updateHostVisibility;
    public final VisualStabilityProvider visualStabilityProvider;

    public MediaCarouselViewModel(CoroutineScope coroutineScope, Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, VisualStabilityProvider visualStabilityProvider, MediaCarouselInteractor mediaCarouselInteractor, MediaControlInteractorFactory mediaControlInteractorFactory, MediaUiEventLogger mediaUiEventLogger, MediaLogger mediaLogger) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.visualStabilityProvider = visualStabilityProvider;
        this.interactor = mediaCarouselInteractor;
        this.controlInteractorFactory = mediaControlInteractorFactory;
        this.logger = mediaUiEventLogger;
        this.mediaLogger = mediaLogger;
        this.hasAnyMediaOrRecommendations = mediaCarouselInteractor.hasAnyMediaOrRecommendation;
        this.hasActiveMediaOrRecommendations = mediaCarouselInteractor.hasActiveMediaOrRecommendation;
        final ReadonlyStateFlow readonlyStateFlow = mediaCarouselInteractor.currentMedia;
        this.mediaItems = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaCarouselViewModel this$0;

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
                public final java.lang.Object emit(java.lang.Object r29, kotlin.coroutines.Continuation r30) {
                    /*
                        Method dump skipped, instructions count: 260
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
        this.updateHostVisibility = new MediaCarouselViewModel$$ExternalSyntheticLambda0();
        this.mediaControlByInstanceId = new LinkedHashMap();
        this.modelsPendingRemoval = new LinkedHashSet();
    }

    public final void onMediaControlAddedOrUpdated(MediaControlViewModel mediaControlViewModel, MediaCommonModel mediaCommonModel) {
        if (!mediaCommonModel.canBeRemoved || Utils.useMediaResumption(this.applicationContext)) {
            this.modelsPendingRemoval.remove(mediaCommonModel);
        } else if (this.visualStabilityProvider.isReorderingAllowed) {
            mediaControlViewModel.onRemoved.mo779invoke(Boolean.TRUE);
        } else {
            this.modelsPendingRemoval.add(mediaCommonModel);
        }
    }
}
