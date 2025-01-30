package com.android.systemui.keyguard.data.repository;

import android.app.trust.TrustManager;
import android.content.pm.UserInfo;
import com.android.keyguard.logging.TrustRepositoryLogger;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.shared.model.ActiveUnlockModel;
import com.android.systemui.keyguard.shared.model.TrustManagedModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TrustRepositoryImpl implements TrustRepository {
    public final CoroutineScope applicationScope;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 isCurrentUserActiveUnlockRunning;
    public final TrustRepositoryLogger logger;
    public final ReadonlySharedFlow trust;
    public final TrustManager trustManager;
    public final UserRepository userRepository;
    public final Map latestTrustModelForUser = new LinkedHashMap();
    public final Map activeUnlockRunningForUser = new LinkedHashMap();
    public final Map trustManagedForUser = new LinkedHashMap();

    public TrustRepositoryImpl(CoroutineScope coroutineScope, UserRepository userRepository, TrustManager trustManager, TrustRepositoryLogger trustRepositoryLogger) {
        this.applicationScope = coroutineScope;
        this.userRepository = userRepository;
        this.trustManager = trustManager;
        this.logger = trustRepositoryLogger;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        TrustRepositoryImpl$trust$1 trustRepositoryImpl$trust$1 = new TrustRepositoryImpl$trust$1(this, null);
        conflatedCallbackFlow.getClass();
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(ConflatedCallbackFlow.conflatedCallbackFlow(trustRepositoryImpl$trust$1), new TrustRepositoryImpl$trust$2(this, null));
        SharingStarted.Companion.getClass();
        ReadonlySharedFlow shareIn = FlowKt.shareIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, 1);
        this.trust = shareIn;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shareIn, ((UserRepositoryImpl) userRepository).selectedUserInfo, TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$2.INSTANCE);
        this.isCurrentUserActiveUnlockRunning = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$5(this, null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$1$2 */
            public final class C16442 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TrustRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$1$2", m277f = "TrustRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16442.this.emit(null, this);
                    }
                }

                public C16442(FlowCollector flowCollector, TrustRepositoryImpl trustRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = trustRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                ActiveUnlockModel activeUnlockModel = (ActiveUnlockModel) ((LinkedHashMap) this.this$0.activeUnlockRunningForUser).get(new Integer(((UserInfo) ((Pair) obj).getSecond()).id));
                                Boolean valueOf = Boolean.valueOf(activeUnlockModel != null ? activeUnlockModel.isRunning : false);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16442(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new TrustRepositoryImpl$isCurrentUserActiveUnlockRunning$4(this, null)));
    }

    public final ReadonlyStateFlow isCurrentUserTrustManaged() {
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.trust, ((UserRepositoryImpl) this.userRepository).selectedUserInfo, TrustRepositoryImpl$isCurrentUserTrustManaged$2.INSTANCE);
        return FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new TrustRepositoryImpl$isCurrentUserTrustManaged$5(null), new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$2$2 */
            public final class C16452 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TrustRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$2$2", m277f = "TrustRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.TrustRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16452.this.emit(null, this);
                    }
                }

                public C16452(FlowCollector flowCollector, TrustRepositoryImpl trustRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = trustRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                TrustManagedModel trustManagedModel = (TrustManagedModel) ((LinkedHashMap) this.this$0.trustManagedForUser).get(Integer.valueOf(((UserInfo) ((Pair) obj).getSecond()).id));
                                Boolean valueOf = Boolean.valueOf(trustManagedModel != null ? trustManagedModel.isTrustManaged : false);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16452(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new TrustRepositoryImpl$isCurrentUserTrustManaged$4(this, null))), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), Boolean.FALSE);
    }
}
