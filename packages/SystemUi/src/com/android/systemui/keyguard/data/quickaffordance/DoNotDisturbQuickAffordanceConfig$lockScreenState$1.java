package com.android.systemui.keyguard.data.quickaffordance;

import android.graphics.drawable.BitmapDrawable;
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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1", m277f = "DoNotDisturbQuickAffordanceConfig.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$1(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$1> continuation) {
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
            ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig.controller;
            doNotDisturbQuickAffordanceConfig.dndMode = zenModeControllerImpl.mZenMode;
            doNotDisturbQuickAffordanceConfig.isAvailable = zenModeControllerImpl.isZenAvailable();
            ChannelExt channelExt = ChannelExt.INSTANCE;
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = this.this$0;
            if (doNotDisturbQuickAffordanceConfig2.isAvailable) {
                if (doNotDisturbQuickAffordanceConfig2.dndMode == 0) {
                    DoNotDisturbQuickAffordanceConfig.DNDState.Off.INSTANCE.getClass();
                    KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
                    Drawable blendingFgIcon = keyguardShortcutManager.getBlendingFgIcon(null, keyguardShortcutManager.mContext.getResources().getDrawable(R.drawable.fg_do_not_disturb_off), true, false);
                    int i2 = keyguardShortcutManager.mIconSize;
                    BitmapDrawable drawableToScaledBitmapDrawable = keyguardShortcutManager.drawableToScaledBitmapDrawable(blendingFgIcon, i2, i2);
                    Intrinsics.checkNotNull(drawableToScaledBitmapDrawable);
                    visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableToScaledBitmapDrawable, new ContentDescription.Resource(R.string.dnd_is_off)), ActivationState.Inactive.INSTANCE);
                } else {
                    DoNotDisturbQuickAffordanceConfig.DNDState.C1552On.INSTANCE.getClass();
                    KeyguardShortcutManager keyguardShortcutManager2 = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
                    Drawable blendingFgIcon2 = keyguardShortcutManager2.getBlendingFgIcon(null, keyguardShortcutManager2.mContext.getResources().getDrawable(R.drawable.fg_do_not_disturb_off), true, true);
                    int i3 = keyguardShortcutManager2.mIconSize;
                    BitmapDrawable drawableToScaledBitmapDrawable2 = keyguardShortcutManager2.drawableToScaledBitmapDrawable(blendingFgIcon2, i3, i3);
                    Intrinsics.checkNotNull(drawableToScaledBitmapDrawable2);
                    visible = new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableToScaledBitmapDrawable2, new ContentDescription.Resource(R.string.dnd_is_on)), ActivationState.Active.INSTANCE);
                }
                obj2 = visible;
            } else {
                DoNotDisturbQuickAffordanceConfig.DNDState.Unavailable.INSTANCE.getClass();
                obj2 = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
            }
            ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope, obj2, "DoNotDisturbQuickAffordanceConfig");
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig3 = this.this$0;
            ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig3.controller).addCallback(doNotDisturbQuickAffordanceConfig3.callback);
            final DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig4 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig5 = DoNotDisturbQuickAffordanceConfig.this;
                    ((ZenModeControllerImpl) doNotDisturbQuickAffordanceConfig5.controller).removeCallback(doNotDisturbQuickAffordanceConfig5.callback);
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
