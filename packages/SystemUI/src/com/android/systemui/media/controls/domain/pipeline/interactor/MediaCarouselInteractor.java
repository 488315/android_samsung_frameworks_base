package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataCombineLatest;
import com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaCarouselInteractor implements MediaDataManager, CoreStartable {
    public static final Companion Companion = null;
    public final ReadonlyStateFlow currentMedia;
    public final ReadonlyStateFlow hasActiveMediaOrRecommendation;
    public final ReadonlyStateFlow hasAnyMediaOrRecommendation;
    public final MediaDataFilterImpl mediaDataFilter;
    public final MediaDataProcessor mediaDataProcessor;
    public final MediaDeviceManager mediaDeviceManager;
    public final MediaFilterRepository mediaFilterRepository;

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
    }

    public MediaCarouselInteractor(CoroutineScope coroutineScope, MediaDataProcessor mediaDataProcessor, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilterImpl mediaDataFilterImpl, MediaFilterRepository mediaFilterRepository) {
        this.mediaDataProcessor = mediaDataProcessor;
        this.mediaDeviceManager = mediaDeviceManager;
        this.mediaDataFilter = mediaDataFilterImpl;
        this.mediaFilterRepository = mediaFilterRepository;
        final ReadonlyStateFlow readonlyStateFlow = mediaFilterRepository.selectedUserEntries;
        Flow flow = new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L6a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Map r5 = (java.util.Map) r5
                        boolean r6 = r5.isEmpty()
                        r2 = 0
                        if (r6 == 0) goto L3c
                        goto L5b
                    L3c:
                        java.util.Set r5 = r5.entrySet()
                        java.util.Iterator r5 = r5.iterator()
                    L44:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L5b
                        java.lang.Object r6 = r5.next()
                        java.util.Map$Entry r6 = (java.util.Map.Entry) r6
                        java.lang.Object r6 = r6.getValue()
                        com.android.systemui.media.controls.shared.model.MediaData r6 = (com.android.systemui.media.controls.shared.model.MediaData) r6
                        boolean r6 = r6.active
                        if (r6 == 0) goto L44
                        r2 = r3
                    L5b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L6a
                        return r1
                    L6a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.hasActiveMediaOrRecommendation = FlowKt.stateIn(flow, coroutineScope, WhileSubscribed$default, bool);
        final ReadonlyStateFlow readonlyStateFlow2 = mediaFilterRepository.selectedUserEntries;
        this.hasAnyMediaOrRecommendation = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Map r5 = (java.util.Map) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.currentMedia = mediaFilterRepository.currentMedia;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void addListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.add(listener);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean dismissMediaData(String str, long j, boolean z) {
        return this.mediaDataProcessor.dismissMediaData(str, j, z);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mediaDeviceManager.dump(printWriter);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasActiveMediaOrRecommendation() {
        Map map = (Map) this.mediaFilterRepository._selectedUserEntries.getValue();
        if (map.isEmpty()) {
            return false;
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (((MediaData) ((Map.Entry) it.next()).getValue()).active) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final boolean hasAnyMediaOrRecommendation() {
        return !((Map) this.mediaFilterRepository._selectedUserEntries.getValue()).entrySet().isEmpty();
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        this.mediaDataProcessor.onNotificationAdded(str, statusBarNotification);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onNotificationRemoved(String str) {
        this.mediaDataProcessor.onNotificationRemoved(str);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void onSwipeToDismiss() {
        MediaDataFilterImpl mediaDataFilterImpl = this.mediaDataFilter;
        mediaDataFilterImpl.getClass();
        Log.d("MediaDataFilter", "Media carousel swiped away");
        MediaFilterRepository mediaFilterRepository = mediaDataFilterImpl.mediaFilterRepository;
        for (Map.Entry entry : ((Map) mediaFilterRepository.allUserEntries.$$delegate_0.getValue()).entrySet()) {
            if (((Map) mediaFilterRepository.selectedUserEntries.$$delegate_0.getValue()).containsKey(((MediaData) entry.getValue()).instanceId)) {
                throw null;
            }
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager
    public final void removeListener(MediaDataManager.Listener listener) {
        this.mediaDataFilter._listeners.remove(listener);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
