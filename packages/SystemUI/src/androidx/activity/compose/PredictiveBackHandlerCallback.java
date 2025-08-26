package androidx.activity.compose;

import androidx.activity.BackEventCompat;
import androidx.activity.OnBackPressedCallback;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes.dex */
public final class PredictiveBackHandlerCallback extends OnBackPressedCallback {
    public Function2 currentOnBack;
    public boolean isActive;
    public OnBackInstance onBackInstance;
    public CoroutineScope onBackScope;

    public PredictiveBackHandlerCallback(boolean z, CoroutineScope coroutineScope, Function2 function2) {
        super(z);
        this.onBackScope = coroutineScope;
        this.currentOnBack = function2;
    }

    @Override // androidx.activity.OnBackPressedCallback
    public final void handleOnBackCancelled() {
        OnBackInstance onBackInstance = this.onBackInstance;
        if (onBackInstance != null) {
            onBackInstance.cancel();
        }
        OnBackInstance onBackInstance2 = this.onBackInstance;
        if (onBackInstance2 != null) {
            onBackInstance2.isPredictiveBack = false;
        }
        this.isActive = false;
    }

    @Override // androidx.activity.OnBackPressedCallback
    public final void handleOnBackPressed() {
        OnBackInstance onBackInstance = this.onBackInstance;
        if (onBackInstance != null && !onBackInstance.isPredictiveBack) {
            onBackInstance.cancel();
            this.onBackInstance = null;
        }
        if (this.onBackInstance == null) {
            this.onBackInstance = new OnBackInstance(this.onBackScope, false, this.currentOnBack, this);
        }
        OnBackInstance onBackInstance2 = this.onBackInstance;
        if (onBackInstance2 != null) {
            onBackInstance2.channel.close(null);
        }
        OnBackInstance onBackInstance3 = this.onBackInstance;
        if (onBackInstance3 != null) {
            onBackInstance3.isPredictiveBack = false;
        }
        this.isActive = false;
    }

    @Override // androidx.activity.OnBackPressedCallback
    public final void handleOnBackProgressed(BackEventCompat backEventCompat) {
        OnBackInstance onBackInstance = this.onBackInstance;
        if (onBackInstance != null) {
            ChannelResult.m3477boximpl(onBackInstance.channel.mo3476trySendJP2dKIU(backEventCompat));
        }
    }

    @Override // androidx.activity.OnBackPressedCallback
    public final void handleOnBackStarted() {
        OnBackInstance onBackInstance = this.onBackInstance;
        if (onBackInstance != null) {
            onBackInstance.cancel();
        }
        if (this.isEnabled) {
            this.onBackInstance = new OnBackInstance(this.onBackScope, true, this.currentOnBack, this);
        }
        this.isActive = true;
    }
}
