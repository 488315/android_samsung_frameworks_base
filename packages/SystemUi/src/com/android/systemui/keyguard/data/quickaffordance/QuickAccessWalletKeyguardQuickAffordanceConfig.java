package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletCard;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController$$ExternalSyntheticLambda0;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final Context context;
    public final Flow lockScreenState;
    public final QuickAccessWalletController walletController;
    public final String key = "wallet";
    public final int pickerIconResourceId = R.drawable.ic_wallet_lockscreen;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QuickAccessWalletKeyguardQuickAffordanceConfig(Context context, QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter) {
        this.context = context;
        this.walletController = quickAccessWalletController;
        this.activityStarter = activityStarter;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1 = new QuickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        this.lockScreenState = ConflatedCallbackFlow.conflatedCallbackFlow(quickAccessWalletKeyguardQuickAffordanceConfig$lockScreenState$1);
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
        return this.pickerIconResourceId;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerScreenState(Continuation continuation) {
        C1582xbd01004 c1582xbd01004;
        Object obj;
        int i;
        KeyguardQuickAffordanceConfig.PickerScreenState.Disabled disabled;
        if (continuation instanceof C1582xbd01004) {
            c1582xbd01004 = (C1582xbd01004) continuation;
            int i2 = c1582xbd01004.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c1582xbd01004.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = c1582xbd01004.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c1582xbd01004.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    QuickAccessWalletController quickAccessWalletController = this.walletController;
                    if (!quickAccessWalletController.mQuickAccessWalletClient.isWalletServiceAvailable()) {
                        return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
                    }
                    QuickAccessWalletClient quickAccessWalletClient = quickAccessWalletController.mQuickAccessWalletClient;
                    if (!(quickAccessWalletClient.isWalletServiceAvailable() && quickAccessWalletClient.isWalletFeatureAvailable())) {
                        disabled = new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(this.context.getString(R.string.wallet_quick_affordance_unavailable_install_the_app), null, null, 6, null);
                        return disabled;
                    }
                    c1582xbd01004.L$0 = this;
                    c1582xbd01004.label = 1;
                    final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(c1582xbd01004), 1);
                    cancellableContinuationImpl.initCancellability();
                    quickAccessWalletController.queryWalletCards(new QuickAccessWalletClient.OnWalletCardsRetrievedCallback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig$queryCards$2$callback$1
                        public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
                            CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                            int i3 = Result.$r8$clinit;
                            ((CancellableContinuationImpl) cancellableContinuation).resumeWith(EmptyList.INSTANCE);
                        }

                        public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
                            CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                            int i3 = Result.$r8$clinit;
                            List<WalletCard> walletCards = getWalletCardsResponse != null ? getWalletCardsResponse.getWalletCards() : null;
                            if (walletCards == null) {
                                walletCards = EmptyList.INSTANCE;
                            }
                            ((CancellableContinuationImpl) cancellableContinuation).resumeWith(walletCards);
                        }
                    });
                    obj = cancellableContinuationImpl.getResult();
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    this = (QuickAccessWalletKeyguardQuickAffordanceConfig) c1582xbd01004.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (((List) obj).isEmpty()) {
                    return new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
                }
                disabled = new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(this.context.getString(R.string.wallet_quick_affordance_unavailable_configure_the_app), null, null, 6, null);
                return disabled;
            }
        }
        c1582xbd01004 = new C1582xbd01004(this, continuation);
        obj = c1582xbd01004.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c1582xbd01004.label;
        if (i != 0) {
        }
        if (((List) obj).isEmpty()) {
        }
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
        if (expandable != null) {
            Expandable.Companion companion = Expandable.Companion;
            ActivityLaunchAnimator.Controller.Companion.getClass();
            ghostedViewLaunchAnimatorController = ActivityLaunchAnimator.Controller.Companion.fromView(((Expandable$Companion$fromView$1) expandable).$view, null);
        }
        QuickAccessWalletController quickAccessWalletController = this.walletController;
        quickAccessWalletController.mQuickAccessWalletClient.getWalletPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda0(quickAccessWalletController, this.activityStarter, ghostedViewLaunchAnimatorController, true));
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.accessibility_wallet_button);
    }
}
