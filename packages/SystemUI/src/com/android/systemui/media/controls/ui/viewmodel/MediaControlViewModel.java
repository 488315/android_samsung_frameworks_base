package com.android.systemui.media.controls.ui.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.shared.model.MediaControlModel;
import com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Integer numValueOf = Integer.valueOf(R.id.actionPlayPause);
        Integer numValueOf2 = Integer.valueOf(R.id.actionPrev);
        Integer numValueOf3 = Integer.valueOf(R.id.actionNext);
        SEMANTIC_ACTIONS_COMPACT = Arrays.asList(numValueOf, numValueOf2, numValueOf3);
        SEMANTIC_ACTIONS_HIDE_WHEN_SCRUBBING = Arrays.asList(numValueOf2, numValueOf3);
        SEMANTIC_ACTIONS_ALL = Arrays.asList(numValueOf, numValueOf2, numValueOf3, Integer.valueOf(R.id.action0), Integer.valueOf(R.id.action1));
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    MediaPlayerViewModel mediaPlayerViewModel;
                    int i;
                    Icon resource;
                    boolean z;
                    boolean z2;
                    boolean zAllMatch;
                    boolean z3;
                    CharSequence charSequence;
                    ArrayList arrayList;
                    CharSequence string;
                    boolean z4;
                    PendingIntent pendingIntent;
                    Icon resource2;
                    Drawable drawable;
                    CharSequence charSequence2;
                    PlaybackState playbackState;
                    final int i2 = 3;
                    final int i3 = 1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i4 = anonymousClass1.label;
                        if ((i4 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i4 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i5 = anonymousClass1.label;
                    if (i5 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        final MediaControlModel mediaControlModel = (MediaControlModel) obj;
                        if (mediaControlModel != null) {
                            MediaControlViewModel.Companion companion = MediaControlViewModel.Companion;
                            final MediaControlViewModel mediaControlViewModel = this.this$0;
                            mediaControlViewModel.getClass();
                            MediaSession.Token token = mediaControlModel.token;
                            MediaController mediaController = token != null ? new MediaController(mediaControlViewModel.applicationContext, token) : null;
                            String string2 = mediaControlModel.isDismissible ? mediaControlViewModel.applicationContext.getString(R.string.controls_media_close_session, mediaControlModel.appName) : mediaControlViewModel.applicationContext.getString(R.string.controls_media_active_session);
                            string2.getClass();
                            Function0 function0 = 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0079: CONSTRUCTOR (r14v0 'function0' kotlin.jvm.functions.Function0) = 
                                  (r9v0 'mediaControlViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel A[DONT_INLINE])
                                  (r7v2 'mediaControlModel' com.android.systemui.media.controls.shared.model.MediaControlModel A[DONT_INLINE])
                                  (r2v0 'i2' int A[DONT_INLINE])
                                 A[DECLARE_VAR, MD:(com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel, com.android.systemui.media.controls.shared.model.MediaControlModel, int):void (m)] (LINE:122) call: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda3.<init>(com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel, com.android.systemui.media.controls.shared.model.MediaControlModel, int):void type: CONSTRUCTOR in method: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$$ExternalSyntheticLambda3, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                	... 27 more
                                */
                            /*
                                Method dump skipped, instructions count: 890
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
