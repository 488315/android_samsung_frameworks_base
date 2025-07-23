package androidx.compose.material.ripple;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
