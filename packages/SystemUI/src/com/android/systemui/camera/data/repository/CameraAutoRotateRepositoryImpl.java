package com.android.systemui.camera.data.repository;

import android.os.UserHandle;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public final class CameraAutoRotateRepositoryImpl implements CameraAutoRotateRepository {
    public final CoroutineScope applicationScope;
    public final CoroutineContext bgCoroutineContext;
    public final SecureSettings secureSettings;
    public final Map userMap = new LinkedHashMap();

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

    public CameraAutoRotateRepositoryImpl(SecureSettings secureSettings, CoroutineContext coroutineContext, CoroutineScope coroutineScope) {
        this.secureSettings = secureSettings;
        this.bgCoroutineContext = coroutineContext;
        this.applicationScope = coroutineScope;
    }

    public final StateFlow isCameraAutoRotateSettingEnabled(final UserHandle userHandle) {
        Map map = this.userMap;
        Integer numValueOf = Integer.valueOf(userHandle.getIdentifier());
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object objStateIn = linkedHashMap.get(numValueOf);
        if (objStateIn == null) {
            final Flow flowObserverFlow = SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, userHandle.getIdentifier(), "camera_autorotate");
            objStateIn = FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$1$2(this, userHandle, null), new Flow() { // from class: com.android.systemui.camera.data.repository.CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$lambda$1$$inlined$map$1

                /* renamed from: com.android.systemui.camera.data.repository.CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$lambda$1$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserHandle $userHandle$inlined;
                    public final /* synthetic */ CameraAutoRotateRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.camera.data.repository.CameraAutoRotateRepositoryImpl$isCameraAutoRotateSettingEnabled$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, CameraAutoRotateRepositoryImpl cameraAutoRotateRepositoryImpl, UserHandle userHandle) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = cameraAutoRotateRepositoryImpl;
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
                            Boolean boolValueOf = Boolean.valueOf(this.this$0.secureSettings.getIntForUser("camera_autorotate", 0, this.$userHandle$inlined.getIdentifier()) == 1);
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
                    Object objCollect = flowObserverFlow.collect(new AnonymousClass2(flowCollector, this, userHandle), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), this.bgCoroutineContext), this.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
            linkedHashMap.put(numValueOf, objStateIn);
        }
        return (StateFlow) objStateIn;
    }
}
