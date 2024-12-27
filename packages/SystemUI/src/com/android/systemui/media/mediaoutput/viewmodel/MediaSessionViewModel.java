package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.controller.media.MediaSessionController;
import com.android.systemui.media.mediaoutput.controller.media.NoSessionController;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import com.android.systemui.media.mediaoutput.entity.MediaInfoExt;
import com.android.systemui.media.mediaoutput.entity.MediaSessionInfo;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.sequences.TransformingSequence;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class MediaSessionViewModel extends ViewModel implements MediaInteraction {
    public static final Companion Companion = new Companion(null);
    public final MediatorLiveData _mediaInfo;
    public final StateFlowImpl _sessionControllersFlow;
    public final ActivityStarter activityStarter;
    public final AudioManager audioManager;
    public final Context context;
    public final MediatorLiveData currentSessionController;
    public final DataStore dataStore;
    public final Lazy empty$delegate;
    public boolean isGrayscaleThumbnail;
    public final Flow mediaInfo;
    public final MediaSessionManager mediaSessionManager;
    public final Lazy noSessionController$delegate;
    public final CommonNotifCollection notifCollection;
    public String packageName;
    public final CoroutineLiveData sessionControllers;
    public final StateFlowImpl updateMediaInfo;

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

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
            MediaSessionViewModel mediaSessionViewModel;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                mediaSessionViewModel = MediaSessionViewModel.this;
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = mediaSessionViewModel.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$2 dataStoreDebugLabsExt$special$$inlined$map$2 = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore.getData());
                this.L$0 = mediaSessionViewModel;
                this.label = 1;
                obj = FlowKt.firstOrNull(dataStoreDebugLabsExt$special$$inlined$map$2, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                mediaSessionViewModel = (MediaSessionViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            Boolean bool = (Boolean) obj;
            mediaSessionViewModel.isGrayscaleThumbnail = bool != null ? bool.booleanValue() : false;
            DataStoreDebugLabsExt dataStoreDebugLabsExt2 = DataStoreDebugLabsExt.INSTANCE;
            DataStore dataStore2 = MediaSessionViewModel.this.dataStore;
            dataStoreDebugLabsExt2.getClass();
            DataStoreDebugLabsExt$special$$inlined$map$2 dataStoreDebugLabsExt$special$$inlined$map$22 = new DataStoreDebugLabsExt$special$$inlined$map$2(dataStore2.getData());
            final MediaSessionViewModel mediaSessionViewModel2 = MediaSessionViewModel.this;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel.3.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    MediaSessionViewModel mediaSessionViewModel3 = MediaSessionViewModel.this;
                    mediaSessionViewModel3.isGrayscaleThumbnail = booleanValue;
                    List list = (List) mediaSessionViewModel3.sessionControllers.getValue();
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            ((SessionController) it.next()).isGrayscaleThumbnail = mediaSessionViewModel3.isGrayscaleThumbnail;
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = null;
            this.label = 2;
            if (dataStoreDebugLabsExt$special$$inlined$map$22.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ CoroutineScope $$this$launch;
            public final /* synthetic */ MediaSessionViewModel this$0;

            public AnonymousClass1(MediaSessionViewModel mediaSessionViewModel, CoroutineScope coroutineScope) {
                this.this$0 = mediaSessionViewModel;
                this.$$this$launch = coroutineScope;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(List list, Continuation continuation) {
                MediatorLiveData mediatorLiveData;
                Log.d("MediaSessionViewModel", "emit() - media session changed");
                final MediaSessionViewModel mediaSessionViewModel = this.this$0;
                final List list2 = (List) mediaSessionViewModel._sessionControllersFlow.getValue();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Companion companion = MediaSessionViewModel.Companion;
                final Context context = mediaSessionViewModel.context;
                companion.getClass();
                CollectionsKt__MutableCollectionsKt.addAll(SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.mapNotNull(new TransformingSequence(SequencesKt___SequencesKt.filter(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((NotifPipeline) mediaSessionViewModel.notifCollection).getAllNotifs()), new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((NotificationEntry) obj).mSbn;
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(((StatusBarNotification) obj).getNotification().isMediaNotification());
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ((StatusBarNotification) obj).getNotification();
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Notification notification2 = (Notification) obj;
                        MediaSession.Token token = (MediaSession.Token) notification2.extras.getParcelable("android.mediaSession", MediaSession.Token.class);
                        if (token == null) {
                            return null;
                        }
                        List<SessionController> list3 = list2;
                        Context context2 = context;
                        Log.d("MediaSessionViewModel", "CommonNotifCollection - " + notification2);
                        SessionController access$getSessionController = MediaSessionViewModel.Companion.access$getSessionController(MediaSessionViewModel.Companion, list3, token);
                        return access$getSessionController == null ? new MediaSessionController(context2, new MediaController(context2, token)) : access$getSessionController;
                    }
                })), linkedHashSet);
                MediaSessionManager mediaSessionManager = mediaSessionViewModel.mediaSessionManager;
                final Context context2 = mediaSessionViewModel.context;
                final List plus = CollectionsKt___CollectionsKt.plus((Iterable) linkedHashSet, (Collection) list2);
                CollectionsKt__MutableCollectionsKt.addAll(SequencesKt___SequencesKt.toList(new TransformingSequence(new SequencesKt___SequencesKt$sortedWith$1(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(mediaSessionManager.getActiveSessions(null)), new Comparator() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$$inlined$sortedBy$1
                    /* JADX WARN: Removed duplicated region for block: B:10:0x0029  */
                    @Override // java.util.Comparator
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final int compare(java.lang.Object r5, java.lang.Object r6) {
                        /*
                            r4 = this;
                            android.media.session.MediaController r5 = (android.media.session.MediaController) r5
                            android.media.session.PlaybackState r4 = r5.getPlaybackState()
                            r5 = 1
                            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                            r0 = 0
                            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                            r1 = 0
                            r2 = 3
                            if (r4 == 0) goto L20
                            int r3 = r4.getState()
                            if (r3 != r2) goto L1b
                            goto L1c
                        L1b:
                            r4 = r1
                        L1c:
                            if (r4 == 0) goto L20
                            r4 = r0
                            goto L21
                        L20:
                            r4 = r5
                        L21:
                            android.media.session.MediaController r6 = (android.media.session.MediaController) r6
                            android.media.session.PlaybackState r6 = r6.getPlaybackState()
                            if (r6 == 0) goto L33
                            int r3 = r6.getState()
                            if (r3 != r2) goto L30
                            r1 = r6
                        L30:
                            if (r1 == 0) goto L33
                            r5 = r0
                        L33:
                            int r4 = kotlin.comparisons.ComparisonsKt__ComparisonsKt.compareValues(r4, r5)
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$$inlined$sortedBy$1.compare(java.lang.Object, java.lang.Object):int");
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$newSessionControllerWith$6
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        MediaController mediaController = (MediaController) obj;
                        Log.d("MediaSessionViewModel", "MediaSessionManager - " + mediaController.getSessionToken() + ", " + mediaController.getPackageName() + ", " + mediaController.getMetadata() + ", " + mediaController.getPlaybackState());
                        SessionController access$getSessionController = MediaSessionViewModel.Companion.access$getSessionController(MediaSessionViewModel.Companion, plus, mediaController.getSessionToken());
                        return access$getSessionController == null ? new MediaSessionController(context2, mediaController) : access$getSessionController;
                    }
                })), linkedHashSet);
                linkedHashSet.removeIf(new Predicate() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1$emit$2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((SessionController) obj).isClosed;
                    }
                });
                linkedHashSet.removeIf(new Predicate() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1$emit$3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        SessionController.Companion companion2 = SessionController.Companion;
                        String packageName = ((SessionController) obj).getPackageName();
                        companion2.getClass();
                        return SessionController.MEDIA_SESSION_BLOCKED_LIST.contains(packageName);
                    }
                });
                if (linkedHashSet.isEmpty()) {
                    mediaSessionViewModel.getNoSessionController().update$1();
                }
                List list3 = list2;
                Iterator it = CollectionsKt___CollectionsKt.minus((Iterable) list3, (Iterable) linkedHashSet).iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    mediatorLiveData = mediaSessionViewModel._mediaInfo;
                    if (!hasNext) {
                        break;
                    }
                    SessionController sessionController = (SessionController) it.next();
                    MediatorLiveData.Source source = (MediatorLiveData.Source) mediatorLiveData.mSources.remove(sessionController.mediaInfo);
                    if (source != null) {
                        source.mLiveData.removeObserver(source);
                    }
                    sessionController.close();
                }
                Iterator it2 = SetsKt___SetsKt.minus((Set) linkedHashSet, (Iterable) CollectionsKt___CollectionsKt.toSet(list3)).iterator();
                while (it2.hasNext()) {
                    MutableLiveData mutableLiveData = ((SessionController) it2.next()).mediaInfo;
                    final CoroutineScope coroutineScope = this.$$this$launch;
                    mediatorLiveData.addSource(mutableLiveData, new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1$emit$5$1

                        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1$emit$5$1$1, reason: invalid class name */
                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ MediaSessionViewModel.AnonymousClass4.AnonymousClass1 this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass1(MediaSessionViewModel.AnonymousClass4.AnonymousClass1 anonymousClass1, Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = anonymousClass1;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new AnonymousClass1(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    MediaSessionViewModel.AnonymousClass4.AnonymousClass1 anonymousClass1 = this.this$0;
                                    EmptyList emptyList = EmptyList.INSTANCE;
                                    this.label = 1;
                                    if (anonymousClass1.emit((List) emptyList, (Continuation) this) == coroutineSingletons) {
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

                        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$4$1$emit$5$1$2, reason: invalid class name */
                        final class AnonymousClass2 extends SuspendLambda implements Function2 {
                            int label;
                            final /* synthetic */ MediaSessionViewModel this$0;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public AnonymousClass2(MediaSessionViewModel mediaSessionViewModel, Continuation continuation) {
                                super(2, continuation);
                                this.this$0 = mediaSessionViewModel;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new AnonymousClass2(this.this$0, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    StateFlowImpl stateFlowImpl = this.this$0.updateMediaInfo;
                                    Long l = new Long(System.currentTimeMillis());
                                    this.label = 1;
                                    stateFlowImpl.updateState(null, l);
                                    if (Unit.INSTANCE == coroutineSingletons) {
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

                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            MediaInfo mediaInfo = (MediaInfo) obj;
                            MediaSessionViewModel mediaSessionViewModel2 = MediaSessionViewModel.this;
                            if (mediaInfo == null) {
                                Log.e("MediaSessionViewModel", "mediaInfo is null");
                                BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(mediaSessionViewModel2), null, null, new AnonymousClass1(this, null), 3);
                            } else if (mediaInfo.isSame(mediaSessionViewModel2.packageName) && !mediaInfo.isInvalidAction()) {
                                mediaSessionViewModel2._mediaInfo.setValue(mediaInfo);
                            } else {
                                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(mediaSessionViewModel2, null), 3);
                            }
                        }
                    });
                }
                Iterator it3 = linkedHashSet.iterator();
                while (it3.hasNext()) {
                    ((SessionController) it3.next()).isGrayscaleThumbnail = mediaSessionViewModel.isGrayscaleThumbnail;
                }
                Iterator it4 = linkedHashSet.iterator();
                while (it4.hasNext()) {
                    Log.d("MediaSessionViewModel", "\t" + ((SessionController) it4.next()).mediaInfo.getValue());
                }
                mediaSessionViewModel._sessionControllersFlow.setValue(CollectionsKt___CollectionsKt.toList(linkedHashSet));
                Unit unit = Unit.INSTANCE;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                return unit;
            }
        }

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = MediaSessionViewModel.this.new AnonymousClass4(continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Companion companion = MediaSessionViewModel.Companion;
                MediaSessionManager mediaSessionManager = MediaSessionViewModel.this.mediaSessionManager;
                companion.getClass();
                Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MediaSessionViewModel$Companion$activeMediaChanges$1(mediaSessionManager, null)), -1);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaSessionViewModel.this, coroutineScope);
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

    public final class Companion {
        private Companion() {
        }

        public static final SessionController access$getSessionController(Companion companion, List list, Object obj) {
            companion.getClass();
            Object obj2 = null;
            if (obj instanceof MediaSession.Token) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (Intrinsics.areEqual(((SessionController) next).token, obj)) {
                        obj2 = next;
                        break;
                    }
                }
                return (SessionController) obj2;
            }
            if (!(obj instanceof String)) {
                return null;
            }
            Iterator it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next2 = it2.next();
                MediaInfo mediaInfo = (MediaInfo) ((SessionController) next2).mediaInfo.getValue();
                if (mediaInfo != null && mediaInfo.isSame((String) obj)) {
                    obj2 = next2;
                    break;
                }
            }
            return (SessionController) obj2;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaSessionViewModel(Context context, CommonNotifCollection commonNotifCollection, MediaSessionManager mediaSessionManager, AudioManager audioManager, ActivityStarter activityStarter, DataStore dataStore, SavedStateHandle savedStateHandle) {
        this.context = context;
        this.notifCollection = commonNotifCollection;
        this.mediaSessionManager = mediaSessionManager;
        this.audioManager = audioManager;
        this.activityStarter = activityStarter;
        this.dataStore = dataStore;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._sessionControllersFlow = MutableStateFlow;
        CoroutineLiveData asLiveData$default = FlowLiveDataConversions.asLiveData$default(MutableStateFlow);
        this.sessionControllers = asLiveData$default;
        this.noSessionController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$noSessionController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaSessionViewModel mediaSessionViewModel = MediaSessionViewModel.this;
                return new NoSessionController(mediaSessionViewModel.context, mediaSessionViewModel.audioManager);
            }
        });
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(0L);
        this.updateMediaInfo = MutableStateFlow2;
        final MediatorLiveData mediatorLiveData = new MediatorLiveData(getNoSessionController());
        mediatorLiveData.addSource(asLiveData$default, new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$currentSessionController$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediatorLiveData.this.setValue(MediaSessionViewModel.access$currentSessionController$lambda$3$selectController((List) obj, this));
            }
        });
        mediatorLiveData.addSource(FlowLiveDataConversions.asLiveData$default(MutableStateFlow2), new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$currentSessionController$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediaSessionViewModel mediaSessionViewModel = this;
                MediatorLiveData.this.setValue(MediaSessionViewModel.access$currentSessionController$lambda$3$selectController((List) mediaSessionViewModel.sessionControllers.getValue(), mediaSessionViewModel));
            }
        });
        this.currentSessionController = mediatorLiveData;
        this.empty$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$empty$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaSessionInfo.Companion.getClass();
                return MediaSessionInfo.empty;
            }
        });
        final MediatorLiveData mediatorLiveData2 = new MediatorLiveData(getEmpty());
        mediatorLiveData2.addSource(mediatorLiveData, new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$_mediaInfo$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediaInfo mediaInfo = (MediaInfo) ((SessionController) obj).mediaInfo.getValue();
                if (mediaInfo != null) {
                    if (!(!mediaInfo.isInvalidAction())) {
                        mediaInfo = null;
                    }
                    if (mediaInfo != null) {
                        MediatorLiveData.this.setValue(mediaInfo);
                    }
                }
            }
        });
        mediatorLiveData2.addSource(getNoSessionController().mediaInfo, new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$_mediaInfo$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediaInfo mediaInfo = (MediaInfo) obj;
                MediaSessionViewModel mediaSessionViewModel = MediaSessionViewModel.this;
                if (Intrinsics.areEqual(mediaSessionViewModel.currentSessionController.getValue(), mediaSessionViewModel.getNoSessionController())) {
                    mediatorLiveData2.setValue(mediaInfo);
                }
            }
        });
        this._mediaInfo = mediatorLiveData2;
        this.mediaInfo = FlowLiveDataConversions.asFlow(Transformations.map(mediatorLiveData2, new Function1() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$mediaInfo$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (MediaInfo) obj;
            }
        }));
        String str = "";
        this.packageName = "";
        Log.d("MediaSessionViewModel", "init()");
        if (savedStateHandle != null && (r3 = (String) savedStateHandle.get("packageName")) != null) {
            String str2 = StringsKt__StringsJVMKt.isBlank(str2) ^ true ? str2 : null;
            if (str2 != null) {
                str2 = str2.length() <= 5 ? null : str2;
                if (str2 != null) {
                    str = str2;
                }
            }
        }
        setPackageName(str);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass3(null), 3);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass4(null), 3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.Object] */
    public static final SessionController access$currentSessionController$lambda$3$selectController(List list, MediaSessionViewModel mediaSessionViewModel) {
        Iterable iterable;
        SessionController sessionController;
        Object obj;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("selectController() - ", mediaSessionViewModel.packageName, "MediaSessionViewModel");
        if (list != null) {
            iterable = new ArrayList();
            for (Object obj2 : list) {
                if (!((SessionController) obj2).isClosed) {
                    iterable.add(obj2);
                }
            }
        } else {
            iterable = EmptyList.INSTANCE;
        }
        Iterable iterable2 = iterable;
        Iterator it = iterable2.iterator();
        while (true) {
            sessionController = null;
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            MediaInfo mediaInfo = (MediaInfo) ((SessionController) obj).mediaInfo.getValue();
            if (mediaInfo != null && mediaInfo.isSame(mediaSessionViewModel.packageName)) {
                break;
            }
        }
        SessionController sessionController2 = (SessionController) obj;
        if (sessionController2 != null) {
            return sessionController2;
        }
        for (?? r1 : iterable2) {
            MediaInfo mediaInfo2 = (MediaInfo) ((SessionController) r1).mediaInfo.getValue();
            if (mediaInfo2 != null) {
                MediaInfoExt mediaInfoExt = MediaInfoExt.INSTANCE;
                int playbackState = mediaInfo2.getPlaybackState();
                mediaInfoExt.getClass();
                if (playbackState == 3 || playbackState == 6) {
                    sessionController = r1;
                    break;
                }
            }
        }
        SessionController sessionController3 = sessionController;
        if (sessionController3 != null) {
            return sessionController3;
        }
        SessionController sessionController4 = (SessionController) CollectionsKt___CollectionsKt.firstOrNull((List) iterable);
        return sessionController4 == null ? mediaSessionViewModel.getNoSessionController() : sessionController4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0079, code lost:
    
        if (r1 == null) goto L25;
     */
    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void execute(com.android.systemui.media.mediaoutput.entity.MediaInfo r5, long r6, long r8) {
        /*
            r4 = this;
            java.lang.String r0 = "execute() - "
            java.lang.String r1 = " : "
            java.lang.StringBuilder r0 = androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0.m(r0, r6, r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "MediaSessionViewModel"
            android.util.Log.d(r1, r0)
            java.lang.String r5 = r5.getPackageName()
            r0 = -2
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            r1 = 0
            if (r0 != 0) goto L4f
            android.content.Context r6 = r4.context
            android.content.pm.PackageManager r6 = r6.getPackageManager()
            if (r6 == 0) goto L89
            com.android.systemui.media.mediaoutput.controller.media.SessionController$Companion r7 = com.android.systemui.media.mediaoutput.controller.media.SessionController.Companion
            r7.getClass()
            java.util.List r7 = com.android.systemui.media.mediaoutput.controller.media.SessionController.LAUNCH_BLOCKED_LIST
            boolean r7 = r7.contains(r5)
            if (r7 != 0) goto L35
            r1 = r6
        L35:
            if (r1 == 0) goto L89
            android.content.Intent r5 = r1.getLaunchIntentForPackage(r5)
            if (r5 == 0) goto L89
            android.content.Context r6 = r4.context
            r7 = 0
            r8 = 67108864(0x4000000, float:1.5046328E-36)
            android.app.PendingIntent r5 = android.app.PendingIntent.getActivity(r6, r7, r5, r8)
            if (r5 == 0) goto L89
            com.android.systemui.plugins.ActivityStarter r4 = r4.activityStarter
            r6 = 1
            r4.postStartActivityDismissingKeyguard(r5, r6)
            goto L89
        L4f:
            androidx.lifecycle.CoroutineLiveData r0 = r4.sessionControllers
            java.lang.Object r0 = r0.getValue()
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L7b
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
        L5f:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L77
            java.lang.Object r2 = r0.next()
            r3 = r2
            com.android.systemui.media.mediaoutput.controller.media.SessionController r3 = (com.android.systemui.media.mediaoutput.controller.media.SessionController) r3
            java.lang.String r3 = r3.getPackageName()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r5)
            if (r3 == 0) goto L5f
            r1 = r2
        L77:
            com.android.systemui.media.mediaoutput.controller.media.SessionController r1 = (com.android.systemui.media.mediaoutput.controller.media.SessionController) r1
            if (r1 != 0) goto L7f
        L7b:
            com.android.systemui.media.mediaoutput.controller.media.NoSessionController r1 = r4.getNoSessionController()
        L7f:
            boolean r0 = r1 instanceof com.android.systemui.media.mediaoutput.controller.media.NoSessionController
            if (r0 == 0) goto L86
            r4.setPackageName(r5)
        L86:
            r1.execute(r6, r8)
        L89:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel.execute(com.android.systemui.media.mediaoutput.entity.MediaInfo, long, long):void");
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final MediaInfo getEmpty() {
        return (MediaInfo) this.empty$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final Flow getMediaInfo() {
        return this.mediaInfo;
    }

    public final NoSessionController getNoSessionController() {
        return (NoSessionController) this.noSessionController$delegate.getValue();
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("MediaSessionViewModel", "onCleared()");
        List<SessionController> list = (List) this.sessionControllers.getValue();
        MediatorLiveData mediatorLiveData = this._mediaInfo;
        if (list != null) {
            for (SessionController sessionController : list) {
                MediatorLiveData.Source source = (MediatorLiveData.Source) mediatorLiveData.mSources.remove(sessionController.mediaInfo);
                if (source != null) {
                    source.mLiveData.removeObserver(source);
                }
                sessionController.close();
            }
        }
        MediatorLiveData.Source source2 = (MediatorLiveData.Source) mediatorLiveData.mSources.remove(getNoSessionController().mediaInfo);
        if (source2 != null) {
            source2.mLiveData.removeObserver(source2);
        }
    }

    public final void setPackageName(String str) {
        if (Intrinsics.areEqual(this.packageName, str)) {
            return;
        }
        MWBixbyController$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "MediaSessionViewModel");
        if (!(!StringsKt__StringsJVMKt.isBlank(str))) {
            str = null;
        }
        if (str == null) {
            str = "no_session_media_id";
        }
        this.packageName = str;
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new MediaSessionViewModel$packageName$2(this, null), 3);
    }
}
