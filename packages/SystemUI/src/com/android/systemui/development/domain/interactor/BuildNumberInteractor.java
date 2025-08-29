package com.android.systemui.development.domain.interactor;

import android.R;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Resources;
import android.os.Build;
import com.android.systemui.development.data.repository.DevelopmentSettingRepository;
import com.android.systemui.development.shared.model.BuildNumber;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.utils.UserScopedService;
import com.android.systemui.user.utils.UserScopedServiceImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class BuildNumberInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow buildNumber;
    public final String buildText;
    public final String clipLabel;
    public final UserScopedService clipboardManagerProvider;
    public final UserRepository userRepository;

    /* renamed from: com.android.systemui.development.domain.interactor.BuildNumberInteractor$copyBuildNumber$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BuildNumberInteractor.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            BuildNumberInteractor buildNumberInteractor = BuildNumberInteractor.this;
            ClipboardManager clipboardManager = (ClipboardManager) ((UserScopedServiceImpl) buildNumberInteractor.clipboardManagerProvider).forUser(((UserRepositoryImpl) buildNumberInteractor.userRepository).getSelectedUserInfo().getUserHandle());
            BuildNumberInteractor buildNumberInteractor2 = BuildNumberInteractor.this;
            clipboardManager.setPrimaryClip(ClipData.newPlainText(buildNumberInteractor2.clipLabel, buildNumberInteractor2.buildText));
            return Unit.INSTANCE;
        }
    }

    public BuildNumberInteractor(DevelopmentSettingRepository developmentSettingRepository, Resources resources, UserRepository userRepository, UserScopedService userScopedService, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.userRepository = userRepository;
        this.clipboardManagerProvider = userScopedService;
        this.backgroundDispatcher = coroutineDispatcher;
        UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        BuildNumberInteractor$buildNumber$1 buildNumberInteractor$buildNumber$1 = new BuildNumberInteractor$buildNumber$1(developmentSettingRepository, null);
        int i = FlowKt__MergeKt.$r8$clinit;
        final FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 = new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flatMapConcat$$inlined$map$1(userRepositoryImpl$special$$inlined$map$2, buildNumberInteractor$buildNumber$1));
        this.buildNumber = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BuildNumberInteractor this$0;

                /* renamed from: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BuildNumberInteractor buildNumberInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = buildNumberInteractor;
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
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        BuildNumber buildNumberM2564boximpl = BuildNumber.m2564boximpl(this.this$0.buildText);
                        if (!zBooleanValue) {
                            buildNumberM2564boximpl = null;
                        }
                        String str = buildNumberM2564boximpl != null ? buildNumberM2564boximpl.value : null;
                        BuildNumber buildNumberM2564boximpl2 = str != null ? BuildNumber.m2564boximpl(str) : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(buildNumberM2564boximpl2, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
        this.buildText = resources.getString(R.string.config_managed_provisioning_package, Build.VERSION.RELEASE_OR_CODENAME, Build.ID);
        this.clipLabel = resources.getString(com.android.systemui.R.string.build_number_clip_data_label);
    }

    public final Object copyBuildNumber(Continuation continuation) {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
