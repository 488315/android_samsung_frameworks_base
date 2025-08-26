package androidx.compose.material.ripple;

/* loaded from: classes.dex */
public final /* synthetic */ class RippleHostView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ RippleHostView f$0;

    @Override // java.lang.Runnable
    public final void run() {
        RippleHostView rippleHostView = this.f$0;
        UnprojectedRipple unprojectedRipple = rippleHostView.ripple;
        if (unprojectedRipple != null) {
            unprojectedRipple.setState(RippleHostView.RestingState);
        }
        rippleHostView.resetRippleRunnable = null;
    }
}
