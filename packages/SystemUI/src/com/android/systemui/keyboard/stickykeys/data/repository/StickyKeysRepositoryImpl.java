package com.android.systemui.keyboard.stickykeys.data.repository;

import android.hardware.input.InputManager;
import android.hardware.input.StickyModifierState;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.keyboard.stickykeys.shared.model.Locked;
import com.android.systemui.keyboard.stickykeys.shared.model.ModifierKey;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class StickyKeysRepositoryImpl implements StickyKeysRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputManager inputManager;
    public final Flow settingEnabled;
    public final Flow stickyKeys;
    public final StickyKeysLogger stickyKeysLogger;

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

    public StickyKeysRepositoryImpl(InputManager inputManager, CoroutineDispatcher coroutineDispatcher, UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, StickyKeysLogger stickyKeysLogger) {
        this.inputManager = inputManager;
        this.stickyKeysLogger = stickyKeysLogger;
        final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new StickyKeysRepositoryImpl$stickyKeys$1(this, null));
        this.stickyKeys = FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ StickyKeysRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyboard.stickykeys.data.repository.StickyKeysRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, StickyKeysRepositoryImpl stickyKeysRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = stickyKeysRepositoryImpl;
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
                        StickyModifierState stickyModifierState = (StickyModifierState) obj;
                        stickyModifierState.getClass();
                        int i3 = StickyKeysRepositoryImpl.$r8$clinit;
                        this.this$0.getClass();
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        if (stickyModifierState.isAltGrModifierOn()) {
                            linkedHashMap.put(ModifierKey.ALT_GR, Locked.m2600boximpl(false));
                        }
                        if (stickyModifierState.isAltGrModifierLocked()) {
                            linkedHashMap.put(ModifierKey.ALT_GR, Locked.m2600boximpl(true));
                        }
                        if (stickyModifierState.isAltModifierOn()) {
                            linkedHashMap.put(ModifierKey.ALT, Locked.m2600boximpl(false));
                        }
                        if (stickyModifierState.isAltModifierLocked()) {
                            linkedHashMap.put(ModifierKey.ALT, Locked.m2600boximpl(true));
                        }
                        if (stickyModifierState.isCtrlModifierOn()) {
                            linkedHashMap.put(ModifierKey.CTRL, Locked.m2600boximpl(false));
                        }
                        if (stickyModifierState.isCtrlModifierLocked()) {
                            linkedHashMap.put(ModifierKey.CTRL, Locked.m2600boximpl(true));
                        }
                        if (stickyModifierState.isMetaModifierOn()) {
                            linkedHashMap.put(ModifierKey.META, Locked.m2600boximpl(false));
                        }
                        if (stickyModifierState.isMetaModifierLocked()) {
                            linkedHashMap.put(ModifierKey.META, Locked.m2600boximpl(true));
                        }
                        if (stickyModifierState.isShiftModifierOn()) {
                            linkedHashMap.put(ModifierKey.SHIFT, Locked.m2600boximpl(false));
                        }
                        if (stickyModifierState.isShiftModifierLocked()) {
                            linkedHashMap.put(ModifierKey.SHIFT, Locked.m2600boximpl(true));
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(linkedHashMap, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new StickyKeysRepositoryImpl$stickyKeys$3(this, null)), coroutineDispatcher);
        this.settingEnabled = FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(userAwareSecureSettingsRepository.boolSetting("accessibility_sticky_keys", false), new StickyKeysRepositoryImpl$settingEnabled$1(this, null)), coroutineDispatcher);
    }
}
