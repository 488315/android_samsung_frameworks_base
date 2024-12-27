package com.android.systemui.keyguard.data.quickaffordance;

import android.graphics.drawable.Drawable;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.wallpaper.WallpaperUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DoNotDisturbQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$1(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = doNotDisturbQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$1 doNotDisturbQuickAffordanceConfig$lockScreenState$1 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DoNotDisturbQuickAffordanceConfig$lockScreenState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        KeyguardQuickAffordanceConfig.LockScreenState.Visible visible;
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = this.this$0;
            doNotDisturbQuickAffordanceConfig.dndMode = ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig.controller).mZenMode;
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = this.this$0;
            doNotDisturbQuickAffordanceConfig2.isAvailable = ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig2.controller).isZenAvailable();
            ChannelExt channelExt = ChannelExt.INSTANCE;
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig3 = this.this$0;
            if (doNotDisturbQuickAffordanceConfig3.isAvailable) {
                if (doNotDisturbQuickAffordanceConfig3.dndMode == 0) {
                    DoNotDisturbQuickAffordanceConfig.DNDState.Off.INSTANCE.getClass();
                    KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                    Intrinsics.checkNotNull(keyguardShortcutManager);
                    Drawable convertTaskDrawable = keyguardShortcutManager.convertTaskDrawable(keyguardShortcutManager.context.getDrawable(R.drawable.fg_do_not_disturb_off), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), false, false);
                    Intrinsics.checkNotNull(convertTaskDrawable);
                    visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(convertTaskDrawable, new ContentDescription.Resource(R.string.dnd_is_off)), ActivationState.Inactive.INSTANCE);
                } else {
                    DoNotDisturbQuickAffordanceConfig.DNDState.On.INSTANCE.getClass();
                    KeyguardShortcutManager keyguardShortcutManager2 = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                    Intrinsics.checkNotNull(keyguardShortcutManager2);
                    Drawable convertTaskDrawable2 = keyguardShortcutManager2.convertTaskDrawable(keyguardShortcutManager2.context.getDrawable(R.drawable.fg_do_not_disturb_off), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), true, false);
                    Intrinsics.checkNotNull(convertTaskDrawable2);
                    visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(convertTaskDrawable2, new ContentDescription.Resource(R.string.dnd_is_on)), ActivationState.Active.INSTANCE);
                }
                obj2 = visible;
            } else {
                DoNotDisturbQuickAffordanceConfig.DNDState.Unavailable.INSTANCE.getClass();
                obj2 = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
            }
            ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope, obj2, "DoNotDisturbQuickAffordanceConfig");
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig4 = this.this$0;
            ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig4.controller).addCallback(doNotDisturbQuickAffordanceConfig4.callback);
            final DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig5 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig6 = DoNotDisturbQuickAffordanceConfig.this;
                    ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig6.controller).removeCallback(doNotDisturbQuickAffordanceConfig6.callback);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
