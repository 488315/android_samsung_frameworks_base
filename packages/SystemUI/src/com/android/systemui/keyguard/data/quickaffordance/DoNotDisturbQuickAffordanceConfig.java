package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DoNotDisturbQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final DoNotDisturbQuickAffordanceConfig$callback$1 callback;
    public final Context context;
    public final ZenModeController controller;
    public final Lazy dialog$delegate;
    public int dndMode;
    public boolean isAvailable;
    public final String key;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 lockScreenState;
    public final SecureSettings secureSettings;
    public int settingsValue;
    public final Uri testConditionId;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class DNDState {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Off extends DNDState {
            public static final Off INSTANCE = new Off();

            private Off() {
                super(null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class On extends DNDState {
            public static final On INSTANCE = new On();

            private On() {
                super(null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1] */
    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, Uri uri, final EnableZenModeDialog enableZenModeDialog) {
        this.context = context;
        this.controller = zenModeController;
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        this.testConditionId = uri;
        this.dialog$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$dialog$2
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
                return new EnableZenModeDialog(context2, R.style.Theme_SystemUI_Dialog, true, new ZenModeDialogMetricsLogger(context2));
            }
        });
        this.callback = new ZenModeController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenAvailableChanged(boolean z) {
                DoNotDisturbQuickAffordanceConfig.this.isAvailable = z;
                ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                DoNotDisturbQuickAffordanceConfig.this.dndMode = i;
                ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
            }
        };
        this.key = PluginLockShortcutTask.DO_NOT_DISTURB_TASK;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DoNotDisturbQuickAffordanceConfig$lockScreenState$1 doNotDisturbQuickAffordanceConfig$lockScreenState$1 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(doNotDisturbQuickAffordanceConfig$lockScreenState$1);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, ((UserTrackerImpl) userTracker).getUserId(), "zen_duration"));
        this.lockScreenState = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(conflatedCallbackFlow2, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = doNotDisturbQuickAffordanceConfig;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig r5 = r4.this$0
                        com.android.systemui.util.settings.SecureSettings r5 = r5.secureSettings
                        java.lang.String r6 = "zen_duration"
                        r2 = 0
                        int r5 = r5.getInt(r6, r2)
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$4(this, null)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$5(null));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void addListener() {
        ((ZenModeControllerImpl) this.controller).addCallback(this.callback);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Drawable getDrawable() {
        return this.context.getDrawable(R.drawable.fg_do_not_disturb_off);
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
    public final boolean isTaskEnabled() {
        return ((ZenModeControllerImpl) this.controller).mZenMode != 0;
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
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog(((EnableZenModeDialog) this.dialog$delegate.getValue()).createDialog(), expandable);
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

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void removeListener() {
        ((ZenModeControllerImpl) this.controller).removeCallback(this.callback);
    }

    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this(context, zenModeController, secureSettings, userTracker, coroutineDispatcher, null, null);
    }
}
