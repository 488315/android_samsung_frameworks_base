package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DoNotDisturbQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final DoNotDisturbQuickAffordanceConfig$callback$1 callback;
    public final Context context;
    public final ZenModeController controller;
    public int dndMode;
    public boolean isAvailable;
    public final String key;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 lockScreenState;
    public final SecureSettings secureSettings;
    public int settingsValue;
    public final Uri testConditionId;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DNDState {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Off extends DNDState {
            public static final Off INSTANCE = new Off();

            private Off() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$DNDState$On */
        public final class C1552On extends DNDState {
            public static final C1552On INSTANCE = new C1552On();

            private C1552On() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Unavailable extends DNDState {
            public static final Unavailable INSTANCE = new Unavailable();

            private Unavailable() {
                super(null);
            }
        }

        public /* synthetic */ DNDState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private DNDState() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1] */
    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, Uri uri, final EnableZenModeDialog enableZenModeDialog) {
        this.context = context;
        this.controller = zenModeController;
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        this.testConditionId = uri;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$dialog$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                EnableZenModeDialog enableZenModeDialog2 = EnableZenModeDialog.this;
                if (enableZenModeDialog2 != null) {
                    return enableZenModeDialog2;
                }
                Context context2 = this.context;
                return new EnableZenModeDialog(context2, 2132018527, true, new ZenModeDialogMetricsLogger(context2));
            }
        });
        this.callback = new ZenModeController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenAvailableChanged(boolean z) {
                DoNotDisturbQuickAffordanceConfig.this.isAvailable = z;
                ((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).updateShortcutIcons();
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                DoNotDisturbQuickAffordanceConfig.this.dndMode = i;
                ((KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class)).updateShortcutIcons();
            }
        };
        this.key = "Dnd";
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DoNotDisturbQuickAffordanceConfig$lockScreenState$1 doNotDisturbQuickAffordanceConfig$lockScreenState$1 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(doNotDisturbQuickAffordanceConfig$lockScreenState$1);
        SettingsProxyExt.INSTANCE.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(null), SettingsProxyExt.observerFlow(secureSettings, ((UserTrackerImpl) userTracker).getUserId(), "zen_duration"));
        this.lockScreenState = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(conflatedCallbackFlow2, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2 */
            public final class C15512 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2", m277f = "DoNotDisturbQuickAffordanceConfig.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C15512.this.emit(null, this);
                    }
                }

                public C15512(FlowCollector flowCollector, DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = doNotDisturbQuickAffordanceConfig;
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
                                Integer num = new Integer(this.this$0.secureSettings.getInt("zen_duration", 0));
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C15512(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$4(this, null)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$5(null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.fg_do_not_disturb_off;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return ((ZenModeControllerImpl) this.controller).isZenAvailable() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Default(new Intent("android.settings.ZEN_MODE_SETTINGS")) : KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        if (!this.isAvailable) {
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }
        int i = this.dndMode;
        ZenModeController zenModeController = this.controller;
        if (i != 0) {
            ((ZenModeControllerImpl) zenModeController).setZen(0, null, "DoNotDisturbQuickAffordanceConfig");
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }
        int i2 = this.settingsValue;
        if (i2 == -1) {
            ((ZenModeControllerImpl) zenModeController).setZen(1, null, "DoNotDisturbQuickAffordanceConfig");
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }
        if (i2 == 0) {
            ((ZenModeControllerImpl) zenModeController).setZen(1, null, "DoNotDisturbQuickAffordanceConfig");
            return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        }
        Uri uri = this.testConditionId;
        if (uri == null) {
            uri = ZenModeConfig.toTimeCondition(this.context, i2, ((UserTrackerImpl) this.userTracker).getUserId(), true).id;
        }
        ((ZenModeControllerImpl) zenModeController).setZen(1, uri, "DoNotDisturbQuickAffordanceConfig");
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.quick_settings_dnd_label);
    }

    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this(context, zenModeController, secureSettings, userTracker, coroutineDispatcher, null, null);
    }
}
