package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaControlViewModel {
    public static final Companion Companion = null;
    public static final List SEMANTIC_ACTIONS_ALL;
    public static final List SEMANTIC_ACTIONS_COMPACT;
    public static final List SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor backgroundExecutor;
    public final InstanceId instanceId;
    public final MediaControlInteractor interactor;
    public boolean isAnyButtonClicked;
    public boolean isPlaying;
    public final MediaUiEventLogger logger;
    public final Function1 onAdded;
    public final Function1 onRemoved;
    public final Function1 onUpdated;
    public final Flow player;
    public final long updateTime;

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
        Integer valueOf = Integer.valueOf(R.id.actionPlayPause);
        Integer valueOf2 = Integer.valueOf(R.id.actionPrev);
        Integer valueOf3 = Integer.valueOf(R.id.actionNext);
        SEMANTIC_ACTIONS_COMPACT = Arrays.asList(valueOf, valueOf2, valueOf3);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = Arrays.asList(valueOf2, valueOf3);
        SEMANTIC_ACTIONS_ALL = Arrays.asList(valueOf, valueOf2, valueOf3, Integer.valueOf(R.id.action0), Integer.valueOf(R.id.action1));
    }

    public MediaControlViewModel(Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, MediaControlInteractor mediaControlInteractor, MediaUiEventLogger mediaUiEventLogger, InstanceId instanceId, Function1 function1, Function1 function12, Function1 function13, long j) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.interactor = mediaControlInteractor;
        this.logger = mediaUiEventLogger;
        this.instanceId = instanceId;
        this.onAdded = function1;
        this.onRemoved = function12;
        this.onUpdated = function13;
        this.updateTime = j;
        final Flow flow = mediaControlInteractor.mediaControl;
        this.player = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControlViewModel this$0;

                /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaControlViewModel mediaControlViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControlViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r46, kotlin.coroutines.Continuation r47) {
                    /*
                        Method dump skipped, instructions count: 890
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MediaControlViewModel$$ExternalSyntheticLambda0()), coroutineDispatcher);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaControlViewModel)) {
            return false;
        }
        MediaControlViewModel mediaControlViewModel = (MediaControlViewModel) obj;
        return Intrinsics.areEqual(this.applicationContext, mediaControlViewModel.applicationContext) && Intrinsics.areEqual(this.backgroundDispatcher, mediaControlViewModel.backgroundDispatcher) && Intrinsics.areEqual(this.backgroundExecutor, mediaControlViewModel.backgroundExecutor) && Intrinsics.areEqual(this.interactor, mediaControlViewModel.interactor) && Intrinsics.areEqual(this.logger, mediaControlViewModel.logger) && Intrinsics.areEqual(this.instanceId, mediaControlViewModel.instanceId) && Intrinsics.areEqual(this.onAdded, mediaControlViewModel.onAdded) && Intrinsics.areEqual(this.onRemoved, mediaControlViewModel.onRemoved) && Intrinsics.areEqual(this.onUpdated, mediaControlViewModel.onUpdated) && this.updateTime == mediaControlViewModel.updateTime;
    }

    public final int hashCode() {
        return Long.hashCode(this.updateTime) + ((this.onUpdated.hashCode() + ((this.onRemoved.hashCode() + ((this.onAdded.hashCode() + ((this.instanceId.hashCode() + ((this.logger.hashCode() + ((this.interactor.hashCode() + ((this.backgroundExecutor.hashCode() + ((this.backgroundDispatcher.hashCode() + (this.applicationContext.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "MediaControlViewModel(applicationContext=" + this.applicationContext + ", backgroundDispatcher=" + this.backgroundDispatcher + ", backgroundExecutor=" + this.backgroundExecutor + ", interactor=" + this.interactor + ", logger=" + this.logger + ", instanceId=" + this.instanceId + ", onAdded=" + this.onAdded + ", onRemoved=" + this.onRemoved + ", onUpdated=" + this.onUpdated + ", updateTime=" + this.updateTime + ")";
    }

    public /* synthetic */ MediaControlViewModel(Context context, CoroutineDispatcher coroutineDispatcher, Executor executor, MediaControlInteractor mediaControlInteractor, MediaUiEventLogger mediaUiEventLogger, InstanceId instanceId, Function1 function1, Function1 function12, Function1 function13, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, coroutineDispatcher, executor, mediaControlInteractor, mediaUiEventLogger, instanceId, function1, function12, function13, (i & 512) != 0 ? 0L : j);
    }
}
