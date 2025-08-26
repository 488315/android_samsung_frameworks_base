package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.model.MediaCommonModel;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.util.Utils;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    final MediaCarouselViewModel mediaCarouselViewModel;
                    MediaControlViewModel mediaControlViewModel;
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
                        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                        Iterator it = ((List) obj).iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            mediaCarouselViewModel = this.this$0;
                            if (!zHasNext) {
                                break;
                            }
                            final MediaCommonModel mediaCommonModel = (MediaCommonModel) it.next();
                            if (!mediaCarouselViewModel.allowReorder || !mediaCarouselViewModel.modelsPendingRemoval.contains(mediaCommonModel)) {
                                final InstanceId instanceId = mediaCommonModel.mediaLoadedModel.instanceId;
                                MediaControlViewModel mediaControlViewModel2 = (MediaControlViewModel) ((LinkedHashMap) mediaCarouselViewModel.mediaControlByInstanceId).get(instanceId);
                                if (mediaControlViewModel2 != null) {
                                    mediaControlViewModel = new MediaControlViewModel(mediaControlViewModel2.applicationContext, mediaControlViewModel2.backgroundDispatcher, mediaControlViewModel2.backgroundExecutor, mediaControlViewModel2.interactor, mediaControlViewModel2.logger, mediaControlViewModel2.instanceId, mediaControlViewModel2.onAdded, mediaControlViewModel2.onRemoved, mediaControlViewModel2.onUpdated, mediaCommonModel.updateTime);
                                } else {
                                    final int i3 = 0;
                                    final int i4 = 1;
                                    mediaControlViewModel = new MediaControlViewModel(mediaCarouselViewModel.applicationContext, mediaCarouselViewModel.backgroundDispatcher, mediaCarouselViewModel.backgroundExecutor, mediaCarouselViewModel.controlInteractorFactory.create(instanceId), mediaCarouselViewModel.logger, instanceId, 
                                    /*  JADX ERROR: Method code generation error
                                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00ca: CONSTRUCTOR (r9v0 'mediaControlViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel) = 
                                          (wrap:android.content.Context:0x00a1: IGET (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel) A[WRAPPED] (LINE:162) com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel.applicationContext android.content.Context)
                                          (wrap:kotlinx.coroutines.CoroutineDispatcher:0x00be: IGET (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel) A[WRAPPED] (LINE:191) com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel.backgroundDispatcher kotlinx.coroutines.CoroutineDispatcher)
                                          (wrap:java.util.concurrent.Executor:0x00a3: IGET (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel) A[WRAPPED] (LINE:164) com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel.backgroundExecutor java.util.concurrent.Executor)
                                          (wrap:com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor:0x00a7: INVOKE 
                                          (wrap:com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory:0x00a5: IGET (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel) A[WRAPPED] (LINE:166) com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel.controlInteractorFactory com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory)
                                          (r15v0 'instanceId' com.android.internal.logging.InstanceId)
                                         INTERFACE call: com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory.create(com.android.internal.logging.InstanceId):com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor A[MD:(com.android.internal.logging.InstanceId):com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor (m), WRAPPED] (LINE:168))
                                          (wrap:com.android.systemui.media.controls.util.MediaUiEventLogger:0x00c0: IGET (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel) A[WRAPPED] (LINE:193) com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel.logger com.android.systemui.media.controls.util.MediaUiEventLogger)
                                          (r15v0 'instanceId' com.android.internal.logging.InstanceId)
                                          (wrap:kotlin.jvm.functions.Function1:0x00ad: CONSTRUCTOR 
                                          (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel A[DONT_INLINE])
                                          (r15v0 'instanceId' com.android.internal.logging.InstanceId A[DONT_INLINE])
                                          (r6v2 'mediaCommonModel' com.android.systemui.media.controls.shared.model.MediaCommonModel A[DONT_INLINE])
                                         A[MD:(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, com.android.internal.logging.InstanceId, com.android.systemui.media.controls.shared.model.MediaCommonModel):void (m), WRAPPED] (LINE:174) call: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda1.<init>(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, com.android.internal.logging.InstanceId, com.android.systemui.media.controls.shared.model.MediaCommonModel):void type: CONSTRUCTOR)
                                          (wrap:kotlin.jvm.functions.Function1:0x00b3: CONSTRUCTOR 
                                          (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel A[DONT_INLINE])
                                          (r15v0 'instanceId' com.android.internal.logging.InstanceId A[DONT_INLINE])
                                          (r11v0 'i3' int A[DONT_INLINE])
                                         A[MD:(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, java.lang.Object, int):void (m), WRAPPED] (LINE:180) call: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda2.<init>(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, java.lang.Object, int):void type: CONSTRUCTOR)
                                          (wrap:kotlin.jvm.functions.Function1:0x00b9: CONSTRUCTOR 
                                          (r7v0 'mediaCarouselViewModel' com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel A[DONT_INLINE])
                                          (r6v2 'mediaCommonModel' com.android.systemui.media.controls.shared.model.MediaCommonModel A[DONT_INLINE])
                                          (r14v0 'i4' int A[DONT_INLINE])
                                         A[MD:(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, java.lang.Object, int):void (m), WRAPPED] (LINE:186) call: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda2.<init>(com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel, java.lang.Object, int):void type: CONSTRUCTOR)
                                          (wrap:long:0x00c4: IGET (r6v2 'mediaCommonModel' com.android.systemui.media.controls.shared.model.MediaCommonModel) A[WRAPPED] (LINE:197) com.android.systemui.media.controls.shared.model.MediaCommonModel.updateTime long)
                                         A[MD:(android.content.Context, kotlinx.coroutines.CoroutineDispatcher, java.util.concurrent.Executor, com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor, com.android.systemui.media.controls.util.MediaUiEventLogger, com.android.internal.logging.InstanceId, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, long):void (m)] (LINE:203) call: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.<init>(android.content.Context, kotlinx.coroutines.CoroutineDispatcher, java.util.concurrent.Executor, com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor, com.android.systemui.media.controls.util.MediaUiEventLogger, com.android.internal.logging.InstanceId, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, long):void type: CONSTRUCTOR in method: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
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
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:175)
                                        	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:171)
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
                                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$$ExternalSyntheticLambda1, state: NOT_LOADED
                                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                        	... 43 more
                                        */
                                    /*
                                        Method dump skipped, instructions count: 260
                                        To view this dump add '--comments-level debug' option
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                            mediaControlViewModel.onRemoved.mo781invoke(Boolean.TRUE);
                        } else {
                            this.modelsPendingRemoval.add(mediaCommonModel);
                        }
                    }
                }
