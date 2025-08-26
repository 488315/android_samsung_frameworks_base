package com.android.systemui.accessibility.data.repository;

import android.os.UserHandle;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class ColorInversionRepositoryImpl implements ColorInversionRepository {
    public final CoroutineContext bgCoroutineContext;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final Map userMap = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl$setIsEnabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isEnabled;
        final /* synthetic */ UserHandle $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$isEnabled = z;
            this.$userHandle = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ColorInversionRepositoryImpl.this.new AnonymousClass2(this.$isEnabled, this.$userHandle, continuation);
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
            SecureSettings secureSettings = ColorInversionRepositoryImpl.this.secureSettings;
            boolean z = this.$isEnabled;
            return Boolean.valueOf(secureSettings.putIntForUser(SettingsHelper.INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, z ? 1 : 0, this.$userHandle.getIdentifier()));
        }
    }

    static {
        new Companion(null);
    }

    public ColorInversionRepositoryImpl(CoroutineContext coroutineContext, CoroutineScope coroutineScope, SecureSettings secureSettings) {
        this.bgCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.secureSettings = secureSettings;
    }

    public final Flow isEnabled(final UserHandle userHandle) {
        Map map = this.userMap;
        Integer numValueOf = Integer.valueOf(userHandle.getIdentifier());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object objShareIn = linkedHashMap.get(numValueOf);
        if (objShareIn == null) {
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ColorInversionRepositoryImpl$isEnabled$1$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, userHandle.getIdentifier(), SettingsHelper.INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED));
            objShareIn = FlowKt.shareIn(FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl$isEnabled$lambda$1$$inlined$map$1

                /* renamed from: com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl$isEnabled$lambda$1$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserHandle $userHandle$inlined;
                    public final /* synthetic */ ColorInversionRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.accessibility.data.repository.ColorInversionRepositoryImpl$isEnabled$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, ColorInversionRepositoryImpl colorInversionRepositoryImpl, UserHandle userHandle) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = colorInversionRepositoryImpl;
                        this.$userHandle$inlined = userHandle;
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
                            Boolean boolValueOf = Boolean.valueOf(this.this$0.secureSettings.getIntForUser(SettingsHelper.INDEX_ACCESSIBILITY_DISPLAY_INVERSION_ENABLED, 0, this.$userHandle$inlined.getIdentifier()) == 1);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this, userHandle), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), this.bgCoroutineContext), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
            linkedHashMap.put(numValueOf, objShareIn);
        }
        return (Flow) objShareIn;
    }

    public final Object setIsEnabled(UserHandle userHandle, Continuation continuation, boolean z) {
        return BuildersKt.withContext(this.bgCoroutineContext, new AnonymousClass2(z, userHandle, null), continuation);
    }
}
