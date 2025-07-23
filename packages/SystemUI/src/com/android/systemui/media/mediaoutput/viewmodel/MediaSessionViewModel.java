package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.util.Log;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.controller.media.MediaSession;
import com.android.systemui.media.mediaoutput.controller.media.NoSessionController;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.dagger.MediaSessionControllerFactory;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaSessionViewModel extends ViewModel implements MediaInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _sessionControllersFlow;
    public final ActivityStarter activityStarter;
    public final AudioManager audioManager;
    public final Context context;
    public final MediaSessionViewModel$special$$inlined$map$2 currentSessionController;
    public final MediaSessionManager mediaSessionManager;
    public final Lazy noSessionController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            MediaSessionViewModel.Companion companion = MediaSessionViewModel.Companion;
            MediaSessionViewModel mediaSessionViewModel = MediaSessionViewModel.this;
            return new NoSessionController(mediaSessionViewModel.context, mediaSessionViewModel.audioManager);
        }
    });
    public final String packageName;
    public final MediaSessionControllerFactory sessionControllerFactory;
    public final MediaSessionViewModel$special$$inlined$map$1 sessionControllersFlow;
    public final StateFlowImpl updateAction;
    public final StateFlowImpl updateCurrent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ Ref$ObjectRef $actionJob;
            public final /* synthetic */ MediaSessionViewModel this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ LinkedHashSet<MediaSession> $newSessionController;
                int label;
                final /* synthetic */ MediaSessionViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(LinkedHashSet<MediaSession> linkedHashSet, MediaSessionViewModel mediaSessionViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$newSessionController = linkedHashSet;
                    this.this$0 = mediaSessionViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.$newSessionController, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        LinkedHashSet<MediaSession> linkedHashSet = this.$newSessionController;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(linkedHashSet, 10));
                        Iterator<T> it = linkedHashSet.iterator();
                        while (it.hasNext()) {
                            arrayList.add(((MediaSession) it.next()).getActionsFlow());
                        }
                        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
                        Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1$6$invokeSuspend$$inlined$combine$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1$6$invokeSuspend$$inlined$combine$1$3, reason: invalid class name */
                            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                                private /* synthetic */ Object L$0;
                                /* synthetic */ Object L$1;
                                int label;

                                public AnonymousClass3(Continuation continuation) {
                                    super(3, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                                    anonymousClass3.L$0 = (FlowCollector) obj;
                                    anonymousClass3.L$1 = (Object[]) obj2;
                                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                                        Unit unit = Unit.INSTANCE;
                                        this.label = 1;
                                        if (flowCollector.emit(unit, this) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                final Flow[] flowArr2 = flowArr;
                                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1$6$invokeSuspend$$inlined$combine$1.2
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return new Long[flowArr2.length];
                                    }
                                }, new AnonymousClass3(null), flowCollector, continuation);
                                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                            }
                        };
                        final MediaSessionViewModel mediaSessionViewModel = this.this$0;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel.3.1.6.3
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                MediaSessionViewModel.this.updateAction.updateState(null, new Long(System.currentTimeMillis()));
                                Unit unit = Unit.INSTANCE;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                return unit;
                            }
                        };
                        this.label = 1;
                        if (flow.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            public AnonymousClass1(MediaSessionViewModel mediaSessionViewModel, Ref$ObjectRef<Job> ref$ObjectRef) {
                this.this$0 = mediaSessionViewModel;
                this.$actionJob = ref$ObjectRef;
            }

            /* JADX WARN: Removed duplicated region for block: B:12:0x0135  */
            /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            /* JADX WARN: Type inference failed for: r12v11, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(java.util.List r12, kotlin.coroutines.Continuation r13) {
                /*
                    Method dump skipped, instructions count: 335
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel.AnonymousClass3.AnonymousClass1.emit(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaSessionViewModel.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Companion companion = MediaSessionViewModel.Companion;
                MediaSessionManager mediaSessionManager = MediaSessionViewModel.this.mediaSessionManager;
                companion.getClass();
                Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaSessionViewModel$Companion$activeMediaChanges$1(mediaSessionManager, null)), -1, 2);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaSessionViewModel.this, ref$ObjectRef);
                this.label = 1;
                if (buffer$default.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public MediaSessionViewModel(Context context, CommonNotifCollection commonNotifCollection, MediaSessionManager mediaSessionManager, AudioManager audioManager, ActivityStarter activityStarter, MediaSessionControllerFactory mediaSessionControllerFactory, SavedStateHandle savedStateHandle) {
        this.context = context;
        this.mediaSessionManager = mediaSessionManager;
        this.audioManager = audioManager;
        this.activityStarter = activityStarter;
        this.sessionControllerFactory = mediaSessionControllerFactory;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0L);
        this.updateAction = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._sessionControllersFlow = MutableStateFlow2;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow, MutableStateFlow2, new MediaSessionViewModel$sessionControllersFlow$1(null));
        ?? r2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaSessionViewModel this$0;

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaSessionViewModel mediaSessionViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaSessionViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto La1
                    L28:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L30:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.util.List r9 = (java.util.List) r9
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r10 = new java.util.ArrayList
                        r10.<init>()
                        java.util.Iterator r9 = r9.iterator()
                    L40:
                        boolean r2 = r9.hasNext()
                        if (r2 == 0) goto L7e
                        java.lang.Object r2 = r9.next()
                        r4 = r2
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r4 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r4
                        boolean r5 = r4.isClosed()
                        if (r5 != 0) goto L40
                        r5 = 4
                        boolean r5 = r4.isSupportAction(r5)
                        r6 = 2
                        boolean r6 = r4.isSupportAction(r6)
                        r5 = r5 | r6
                        r6 = 512(0x200, double:2.53E-321)
                        boolean r6 = r4.isSupportAction(r6)
                        r5 = r5 | r6
                        if (r5 != 0) goto L7a
                        r5 = 32
                        boolean r5 = r4.isSupportAction(r5)
                        if (r5 != 0) goto L7a
                        r5 = 16
                        boolean r4 = r4.isSupportAction(r5)
                        if (r4 != 0) goto L7a
                        goto L40
                    L7a:
                        r10.add(r2)
                        goto L40
                    L7e:
                        boolean r9 = r10.isEmpty()
                        if (r9 != 0) goto L85
                        goto L86
                    L85:
                        r10 = 0
                    L86:
                        if (r10 != 0) goto L96
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel r9 = r8.this$0
                        kotlin.Lazy r9 = r9.noSessionController$delegate
                        java.lang.Object r9 = r9.getValue()
                        com.android.systemui.media.mediaoutput.controller.media.NoSessionController r9 = (com.android.systemui.media.mediaoutput.controller.media.NoSessionController) r9
                        java.util.List r10 = java.util.Collections.singletonList(r9)
                    L96:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r10, r0)
                        if (r8 != r1) goto La1
                        return r1
                    La1:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.sessionControllersFlow = r2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(0L);
        this.updateCurrent = MutableStateFlow3;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow3, r2, new MediaSessionViewModel$currentSessionController$1(null));
        this.currentSessionController = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaSessionViewModel this$0;

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaSessionViewModel mediaSessionViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaSessionViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto Lae
                    L28:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L30:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.util.List r9 = (java.util.List) r9
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.Iterator r10 = r9.iterator()
                    L3b:
                        boolean r2 = r10.hasNext()
                        com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel r4 = r8.this$0
                        r5 = 0
                        if (r2 == 0) goto L58
                        java.lang.Object r2 = r10.next()
                        r6 = r2
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r6 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r6
                        java.lang.String r6 = r6.getPackageName()
                        java.lang.String r7 = r4.packageName
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
                        if (r6 == 0) goto L3b
                        goto L59
                    L58:
                        r2 = r5
                    L59:
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r2 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r2
                        if (r2 != 0) goto La3
                        java.util.Iterator r10 = r9.iterator()
                    L61:
                        boolean r2 = r10.hasNext()
                        if (r2 == 0) goto L75
                        java.lang.Object r2 = r10.next()
                        r6 = r2
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r6 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r6
                        boolean r6 = r6.isPlaying()
                        if (r6 == 0) goto L61
                        goto L76
                    L75:
                        r2 = r5
                    L76:
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r2 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r2
                        if (r2 != 0) goto La3
                        java.util.Iterator r9 = r9.iterator()
                    L7e:
                        boolean r10 = r9.hasNext()
                        if (r10 == 0) goto L92
                        java.lang.Object r10 = r9.next()
                        r2 = r10
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r2 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r2
                        boolean r2 = r2.isError()
                        if (r2 == 0) goto L7e
                        r5 = r10
                    L92:
                        r2 = r5
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession r2 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) r2
                        if (r2 != 0) goto La3
                        kotlin.Lazy r9 = r4.noSessionController$delegate
                        java.lang.Object r9 = r9.getValue()
                        r2 = r9
                        com.android.systemui.media.mediaoutput.controller.media.NoSessionController r2 = (com.android.systemui.media.mediaoutput.controller.media.NoSessionController) r2
                        r2.run()
                    La3:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r2, r0)
                        if (r8 != r1) goto Lae
                        return r1
                    Lae:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        String str = "";
        this.packageName = "";
        Log.d("MediaSessionViewModel", "init()");
        if (savedStateHandle != null && (r3 = (String) savedStateHandle.get("packageName")) != null) {
            String str2 = StringsKt__StringsKt.isBlank(str2) ? null : str2;
            if (str2 != null) {
                str2 = str2.length() <= 5 ? null : str2;
                if (str2 != null) {
                    str = str2;
                }
            }
        }
        if (!Intrinsics.areEqual(this.packageName, str)) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "MediaSessionViewModel");
            str = StringsKt__StringsKt.isBlank(str) ? null : str;
            this.packageName = str == null ? "no_session_media_id" : str;
            BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new MediaSessionViewModel$packageName$2(this, null), 3);
        }
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass3(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final Flow getCurrentSessionController() {
        return this.currentSessionController;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("MediaSessionViewModel", "onCleared()");
        Iterator it = ((Iterable) this._sessionControllersFlow.getValue()).iterator();
        while (it.hasNext()) {
            ((MediaSession) it.next()).close();
        }
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final void openCpApp(String str) {
        Intent launchIntentForPackage;
        PendingIntent activity;
        PackageManager packageManager = this.context.getPackageManager();
        if (packageManager != null) {
            SessionController.Companion.getClass();
            if (SessionController.Companion.LAUNCH_BLOCKED_LIST.contains(str)) {
                packageManager = null;
            }
            if (packageManager == null || (launchIntentForPackage = packageManager.getLaunchIntentForPackage(str)) == null || (activity = PendingIntent.getActivity(this.context, 0, launchIntentForPackage, 67108864)) == null) {
                return;
            }
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.AppIcon appIcon = SaEvent.AppIcon.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.App(str)};
            moSaLogging.getClass();
            MoSaLogging.send(appIcon, saCustomArr);
            this.activityStarter.postStartActivityDismissingKeyguard(activity, true);
        }
    }
}
