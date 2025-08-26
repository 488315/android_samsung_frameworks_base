package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.DistinctSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.sequences.TransformingSequence;
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
            MediaSessionViewModel mediaSessionViewModel = this.f$0;
            return new NoSessionController(mediaSessionViewModel.context, mediaSessionViewModel.audioManager);
        }
    });
    public final String packageName;
    public final MediaSessionControllerFactory sessionControllerFactory;
    public final MediaSessionViewModel$special$$inlined$map$1 sessionControllersFlow;
    public final StateFlowImpl updateAction;
    public final StateFlowImpl updateCurrent;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ Ref$ObjectRef $actionJob;
            public final /* synthetic */ MediaSessionViewModel this$0;

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
                                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3$1$6$invokeSuspend$$inlined$combine$1.2
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return new Long[flowArr2.length];
                                    }
                                }, new AnonymousClass3(null), flowCollector, continuation);
                                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                            }
                        };
                        final MediaSessionViewModel mediaSessionViewModel = this.this$0;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel.3.1.6.3
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                mediaSessionViewModel.updateAction.updateState(null, new Long(System.currentTimeMillis()));
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

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /* JADX WARN: Type inference failed for: r12v11, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(List list, Continuation continuation) {
                MediaSessionViewModel$3$1$emit$1 mediaSessionViewModel$3$1$emit$1;
                LinkedHashSet linkedHashSet;
                if (continuation instanceof MediaSessionViewModel$3$1$emit$1) {
                    mediaSessionViewModel$3$1$emit$1 = (MediaSessionViewModel$3$1$emit$1) continuation;
                    int i = mediaSessionViewModel$3$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        mediaSessionViewModel$3$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        mediaSessionViewModel$3$1$emit$1 = new MediaSessionViewModel$3$1$emit$1(this, continuation);
                    }
                }
                Object obj = mediaSessionViewModel$3$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = mediaSessionViewModel$3$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Log.d("MediaSessionViewModel", "emit() - media session changed");
                    MediaSessionViewModel mediaSessionViewModel = this.this$0;
                    List list2 = (List) mediaSessionViewModel._sessionControllersFlow.getValue();
                    linkedHashSet = new LinkedHashSet();
                    Companion companion = MediaSessionViewModel.Companion;
                    MediaSessionManager mediaSessionManager = mediaSessionViewModel.mediaSessionManager;
                    final List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) linkedHashSet, (Collection) list2);
                    companion.getClass();
                    DistinctSequence distinctSequence = new DistinctSequence(new SequencesKt___SequencesKt$sortedWith$1(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(mediaSessionManager.getActiveSessionsForUser(null, ((UserTrackerImpl) mediaSessionViewModel.userTracker).getUserHandle())), new Comparator() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$$inlined$sortedBy$1
                        /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
                        @Override // java.util.Comparator
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final int compare(Object obj2, Object obj3) {
                            Integer num;
                            PlaybackState playbackState = ((MediaController) obj2).getPlaybackState();
                            Integer num2 = 1;
                            if (playbackState == null) {
                                num = num2;
                            } else {
                                if (playbackState.getState() != 3) {
                                    playbackState = null;
                                }
                                if (playbackState != null) {
                                    num = 0;
                                }
                            }
                            PlaybackState playbackState2 = ((MediaController) obj3).getPlaybackState();
                            if (playbackState2 != null) {
                                if ((playbackState2.getState() == 3 ? playbackState2 : null) != null) {
                                    num2 = 0;
                                }
                            }
                            return ComparisonsKt__ComparisonsKt.compareValues(num, num2);
                        }
                    }), new MediaSessionViewModel$Companion$$ExternalSyntheticLambda0());
                    final MediaSessionControllerFactory mediaSessionControllerFactory = mediaSessionViewModel.sessionControllerFactory;
                    CollectionsKt__MutableCollectionsKt.addAll(SequencesKt___SequencesKt.toList(new TransformingSequence(distinctSequence, new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            List list3 = listPlus;
                            MediaController mediaController = (MediaController) obj2;
                            Log.d("MediaSessionViewModel", "MediaSessionManager - " + mediaController.getSessionToken() + ", " + mediaController.getPackageName() + ", " + mediaController.getMetadata() + ", " + mediaController.getPlaybackState());
                            MediaSessionViewModel.Companion companion2 = MediaSessionViewModel.Companion;
                            MediaSession.Token sessionToken = mediaController.getSessionToken();
                            companion2.getClass();
                            Object obj3 = null;
                            if (sessionToken != null) {
                                Iterator it = list3.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Object next = it.next();
                                    if (((com.android.systemui.media.mediaoutput.controller.media.MediaSession) next).isSameToken(sessionToken)) {
                                        obj3 = next;
                                        break;
                                    }
                                }
                                obj3 = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) obj3;
                            }
                            return obj3 == null ? mediaSessionControllerFactory.create(mediaController) : obj3;
                        }
                    })), linkedHashSet);
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : linkedHashSet) {
                        com.android.systemui.media.mediaoutput.controller.media.MediaSession mediaSession = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) obj2;
                        if (!mediaSession.isClosed()) {
                            SessionController.Companion companion2 = SessionController.Companion;
                            String packageName = mediaSession.getPackageName();
                            companion2.getClass();
                            if (SessionController.Companion.MEDIA_SESSION_BLOCKED_LIST.contains(packageName)) {
                            }
                        }
                        arrayList.add(obj2);
                    }
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj3 = arrayList.get(i3);
                        i3++;
                        ((com.android.systemui.media.mediaoutput.controller.media.MediaSession) obj3).close();
                    }
                    linkedHashSet.removeAll(CollectionsKt___CollectionsKt.toSet(arrayList));
                    Iterator it = linkedHashSet.iterator();
                    while (it.hasNext()) {
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", ((com.android.systemui.media.mediaoutput.controller.media.MediaSession) it.next()).toLogText(), "MediaSessionViewModel");
                    }
                    Iterator it2 = CollectionsKt___CollectionsKt.minus((Iterable) list2, (Iterable) linkedHashSet).iterator();
                    while (it2.hasNext()) {
                        ((com.android.systemui.media.mediaoutput.controller.media.MediaSession) it2.next()).close();
                    }
                    List list3 = CollectionsKt___CollectionsKt.toList(linkedHashSet);
                    mediaSessionViewModel$3$1$emit$1.L$0 = this;
                    mediaSessionViewModel$3$1$emit$1.L$1 = linkedHashSet;
                    mediaSessionViewModel$3$1$emit$1.label = 1;
                    mediaSessionViewModel._sessionControllersFlow.setValue(list3);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    LinkedHashSet linkedHashSet2 = (LinkedHashSet) mediaSessionViewModel$3$1$emit$1.L$1;
                    AnonymousClass1 anonymousClass1 = (AnonymousClass1) mediaSessionViewModel$3$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    linkedHashSet = linkedHashSet2;
                    this = anonymousClass1;
                }
                Job job = (Job) this.$actionJob.element;
                if (job != null) {
                    job.cancel(null);
                }
                MediaSessionViewModel mediaSessionViewModel2 = this.this$0;
                this.$actionJob.element = BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(mediaSessionViewModel2), null, null, new AnonymousClass6(linkedHashSet, mediaSessionViewModel2, null), 3);
                return Unit.INSTANCE;
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
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaSessionViewModel$Companion$activeMediaChanges$1(mediaSessionManager, null)), -1, 2);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaSessionViewModel.this, ref$ObjectRef);
                this.label = 1;
                if (flowBuffer$default.collect(anonymousClass1, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public MediaSessionViewModel(UserTracker userTracker, Context context, CommonNotifCollection commonNotifCollection, MediaSessionManager mediaSessionManager, AudioManager audioManager, ActivityStarter activityStarter, MediaSessionControllerFactory mediaSessionControllerFactory, SavedStateHandle savedStateHandle) {
        this.userTracker = userTracker;
        this.context = context;
        this.mediaSessionManager = mediaSessionManager;
        this.audioManager = audioManager;
        this.activityStarter = activityStarter;
        this.sessionControllerFactory = mediaSessionControllerFactory;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(0L);
        this.updateAction = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._sessionControllersFlow = stateFlowImplMutableStateFlow2;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow, stateFlowImplMutableStateFlow2, new MediaSessionViewModel$sessionControllersFlow$1(null));
        ?? r2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$1

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
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (List) obj) {
                            com.android.systemui.media.mediaoutput.controller.media.MediaSession mediaSession = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) obj3;
                            if (!mediaSession.isClosed() && ((mediaSession.isSupportAction(4L) | mediaSession.isSupportAction(2L) | mediaSession.isSupportAction(512L)) || mediaSession.isSupportAction(32L) || mediaSession.isSupportAction(16L))) {
                                arrayList.add(obj3);
                            }
                        }
                        boolean zIsEmpty = arrayList.isEmpty();
                        List listSingletonList = arrayList;
                        if (zIsEmpty) {
                            listSingletonList = null;
                        }
                        if (listSingletonList == null) {
                            listSingletonList = Collections.singletonList((NoSessionController) this.this$0.noSessionController$delegate.getValue());
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(listSingletonList, anonymousClass1) == coroutineSingletons) {
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
        };
        this.sessionControllersFlow = r2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(0L);
        this.updateCurrent = stateFlowImplMutableStateFlow3;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow3, r2, new MediaSessionViewModel$currentSessionController$1(null));
        this.currentSessionController = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    MediaSessionViewModel mediaSessionViewModel;
                    Object obj2;
                    Object next;
                    Object next2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj3 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj3);
                        List list = (List) obj;
                        Iterator it = list.iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            mediaSessionViewModel = this.this$0;
                            obj2 = null;
                            if (!zHasNext) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            if (Intrinsics.areEqual(((com.android.systemui.media.mediaoutput.controller.media.MediaSession) next).getPackageName(), mediaSessionViewModel.packageName)) {
                                break;
                            }
                        }
                        SessionController sessionController = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) next;
                        if (sessionController == null) {
                            Iterator it2 = list.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    next2 = null;
                                    break;
                                }
                                next2 = it2.next();
                                if (((com.android.systemui.media.mediaoutput.controller.media.MediaSession) next2).isPlaying()) {
                                    break;
                                }
                            }
                            sessionController = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) next2;
                            if (sessionController == null) {
                                Iterator it3 = list.iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        break;
                                    }
                                    Object next3 = it3.next();
                                    if (((com.android.systemui.media.mediaoutput.controller.media.MediaSession) next3).isError()) {
                                        obj2 = next3;
                                        break;
                                    }
                                }
                                sessionController = (com.android.systemui.media.mediaoutput.controller.media.MediaSession) obj2;
                                if (sessionController == null) {
                                    sessionController = (NoSessionController) mediaSessionViewModel.noSessionController$delegate.getValue();
                                    sessionController.run();
                                }
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sessionController, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj3);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$12.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        String str = "";
        this.packageName = "";
        Log.d("MediaSessionViewModel", "init()");
        if (savedStateHandle != null && (str = (String) savedStateHandle.get("packageName")) != null) {
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
            ((com.android.systemui.media.mediaoutput.controller.media.MediaSession) it.next()).close();
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
