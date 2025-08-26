package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import com.android.settingslib.notification.modes.EnableDndDialogFactory;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.modes.shared.ModesUi;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.wallpaper.WallpaperUtils;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class DoNotDisturbQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DoNotDisturbQuickAffordanceConfig$callback$1 callback;
    public final Context context;
    public final ZenModeController controller;
    public final Lazy dialogFactory$delegate;
    public final String key;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 lockScreenState;
    public boolean oldIsAvailable;
    public final SecureSettings secureSettings;
    public int settingsValue;
    public final Uri testConditionId;
    public final UserTracker userTracker;
    public int zenMode;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract class DNDState {

        public final class Off extends DNDState {
            public static final Off INSTANCE = new Off();

            private Off() {
                super(null);
            }
        }

        public final class On extends DNDState {
            public static final On INSTANCE = new On();

            private On() {
                super(null);
            }
        }

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

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1] */
    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, ZenModeInteractor zenModeInteractor, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, Uri uri, EnableDndDialogFactory enableDndDialogFactory) {
        this.context = context;
        this.controller = zenModeController;
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        this.testConditionId = uri;
        LazyKt__LazyJVMKt.lazy(new DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda0());
        this.dialogFactory$delegate = LazyKt__LazyJVMKt.lazy(new DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda1(enableDndDialogFactory, this));
        this.callback = new ZenModeController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$callback$1
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenAvailableChanged(boolean z) {
                this.this$0.oldIsAvailable = z;
                ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                this.this$0.zenMode = i;
                ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
            }
        };
        this.key = PluginLockShortcutTask.DO_NOT_DISTURB_TASK;
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(this, null));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DoNotDisturbQuickAffordanceConfig$lockScreenState$3(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, ((UserTrackerImpl) userTracker).getUserId(), "zen_duration"));
        this.lockScreenState = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowConflatedCallbackFlow, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

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
                        Integer num = new Integer(this.this$0.secureSettings.getInt("zen_duration", 0));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$5(this, null)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$6(null));
    }

    public static final KeyguardQuickAffordanceConfig.LockScreenState access$updateState(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
        doNotDisturbQuickAffordanceConfig.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = ModesUi.$r8$clinit;
        if (!doNotDisturbQuickAffordanceConfig.oldIsAvailable) {
            DNDState.Unavailable.INSTANCE.getClass();
            return KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
        }
        if (doNotDisturbQuickAffordanceConfig.zenMode == 0) {
            DNDState.Off.INSTANCE.getClass();
            KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
            Drawable drawableConvertTaskDrawable = keyguardShortcutManager.convertTaskDrawable(keyguardShortcutManager.context.getDrawable(R.drawable.fg_do_not_disturb_off), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), false, false, false);
            drawableConvertTaskDrawable.getClass();
            return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableConvertTaskDrawable, new ContentDescription.Resource(R.string.dnd_is_off), null, 4, null), ActivationState.Inactive.INSTANCE);
        }
        DNDState.On.INSTANCE.getClass();
        KeyguardShortcutManager keyguardShortcutManager2 = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
        Drawable drawableConvertTaskDrawable2 = keyguardShortcutManager2.convertTaskDrawable(keyguardShortcutManager2.context.getDrawable(R.drawable.fg_do_not_disturb_off), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), true, false, false);
        drawableConvertTaskDrawable2.getClass();
        return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableConvertTaskDrawable2, new ContentDescription.Resource(R.string.dnd_is_on), null, 4, null), ActivationState.Active.INSTANCE);
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
        if (!this.oldIsAvailable) {
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
        }
        int i = this.zenMode;
        ZenModeController zenModeController = this.controller;
        if (i != 0) {
            ((ZenModeControllerImpl) zenModeController).setZen(0, null, "DoNotDisturbQuickAffordanceConfig");
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
        }
        int i2 = this.settingsValue;
        if (i2 == -1) {
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog(((EnableDndDialogFactory) this.dialogFactory$delegate.getValue()).createDialog(), expandable);
        }
        if (i2 == 0) {
            ((ZenModeControllerImpl) zenModeController).setZen(1, null, "DoNotDisturbQuickAffordanceConfig");
            return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
        }
        Uri uri = this.testConditionId;
        if (uri == null) {
            uri = ZenModeConfig.toTimeCondition(this.context, i2, ((UserTrackerImpl) this.userTracker).getUserId(), true).id;
        }
        ((ZenModeControllerImpl) zenModeController).setZen(1, uri, "DoNotDisturbQuickAffordanceConfig");
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.quick_settings_dnd_label);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final void removeListener() {
        ((ZenModeControllerImpl) this.controller).removeCallback(this.callback);
    }

    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, ZenModeInteractor zenModeInteractor, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this(context, zenModeController, zenModeInteractor, secureSettings, userTracker, coroutineDispatcher, coroutineScope, null, null);
    }
}
