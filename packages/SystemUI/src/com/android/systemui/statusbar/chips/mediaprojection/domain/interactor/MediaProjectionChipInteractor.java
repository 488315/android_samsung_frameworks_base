package com.android.systemui.statusbar.chips.mediaprojection.domain.interactor;

import android.content.pm.PackageManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mediaprojection.MediaProjectionUtils;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.util.Utils;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class MediaProjectionChipInteractor {
    public static final String TAG;
    public final LogBuffer logger;
    public final MediaProjectionRepository mediaProjectionRepository;
    public final PackageManager packageManager;
    public final ReadonlyStateFlow projection;
    public final MediaProjectionManagerRepository$special$$inlined$map$1 projectionStartedDuringCallAndActivePostCallEvent;
    public final CoroutineScope scope;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$stopProjecting$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaProjectionChipInteractor.this.new AnonymousClass1(continuation);
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
                MediaProjectionRepository mediaProjectionRepository = MediaProjectionChipInteractor.this.mediaProjectionRepository;
                this.label = 1;
                if (((MediaProjectionManagerRepository) mediaProjectionRepository).stopProjecting(this) == coroutineSingletons) {
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

    static {
        new Companion(null);
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "MediaProjection");
    }

    public MediaProjectionChipInteractor(CoroutineScope coroutineScope, MediaProjectionRepository mediaProjectionRepository, PackageManager packageManager, LogBuffer logBuffer) {
        this.scope = coroutineScope;
        this.mediaProjectionRepository = mediaProjectionRepository;
        this.packageManager = packageManager;
        this.logger = logBuffer;
        MediaProjectionManagerRepository mediaProjectionManagerRepository = (MediaProjectionManagerRepository) mediaProjectionRepository;
        this.projectionStartedDuringCallAndActivePostCallEvent = mediaProjectionManagerRepository.projectionStartedDuringCallAndActivePostCallEvent;
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionManagerRepository.mediaProjectionState;
        this.projection = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaProjectionChipInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaProjectionChipInteractor mediaProjectionChipInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaProjectionChipInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    ProjectionChipModel.ContentType contentType;
                    Object projecting;
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
                        MediaProjectionState mediaProjectionState = (MediaProjectionState) obj;
                        boolean z = mediaProjectionState instanceof MediaProjectionState.NotProjecting;
                        MediaProjectionChipInteractor mediaProjectionChipInteractor = this.this$0;
                        if (z) {
                            LogBuffer logBuffer = mediaProjectionChipInteractor.logger;
                            logBuffer.commit(logBuffer.obtain(MediaProjectionChipInteractor.TAG, LogLevel.INFO, MediaProjectionChipInteractor$projection$1$2.INSTANCE, null));
                            projecting = ProjectionChipModel.NotProjecting.INSTANCE;
                        } else {
                            if (!(mediaProjectionState instanceof MediaProjectionState.Projecting)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            MediaProjectionUtils mediaProjectionUtils = MediaProjectionUtils.INSTANCE;
                            PackageManager packageManager = mediaProjectionChipInteractor.packageManager;
                            MediaProjectionState.Projecting projecting2 = (MediaProjectionState.Projecting) mediaProjectionState;
                            String hostPackage = projecting2.getHostPackage();
                            mediaProjectionUtils.getClass();
                            ProjectionChipModel.Receiver receiver = Utils.isHeadlessRemoteDisplayProvider(packageManager, hostPackage) ? ProjectionChipModel.Receiver.CastToOtherDevice : ProjectionChipModel.Receiver.ShareToApp;
                            if ((projecting2 instanceof MediaProjectionState.Projecting.EntireScreen) || (projecting2 instanceof MediaProjectionState.Projecting.SingleTask)) {
                                contentType = ProjectionChipModel.ContentType.Screen;
                            } else {
                                if (!(projecting2 instanceof MediaProjectionState.Projecting.NoScreen)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                contentType = ProjectionChipModel.ContentType.Audio;
                            }
                            String str = MediaProjectionChipInteractor.TAG;
                            LogLevel logLevel = LogLevel.INFO;
                            MediaProjectionChipInteractor$projection$1$4 mediaProjectionChipInteractor$projection$1$4 = MediaProjectionChipInteractor$projection$1$4.INSTANCE;
                            LogBuffer logBuffer2 = mediaProjectionChipInteractor.logger;
                            LogMessage logMessageObtain = logBuffer2.obtain(str, logLevel, mediaProjectionChipInteractor$projection$1$4, null);
                            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                            logMessageImpl.bool1 = receiver == ProjectionChipModel.Receiver.CastToOtherDevice;
                            logMessageImpl.bool2 = contentType == ProjectionChipModel.ContentType.Screen;
                            logMessageImpl.str1 = projecting2.getHostPackage();
                            logMessageImpl.str2 = projecting2.getHostDeviceName();
                            logBuffer2.commit(logMessageObtain);
                            projecting = new ProjectionChipModel.Projecting(receiver, contentType, projecting2);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(projecting, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), ProjectionChipModel.NotProjecting.INSTANCE);
    }

    public final void stopProjecting() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
