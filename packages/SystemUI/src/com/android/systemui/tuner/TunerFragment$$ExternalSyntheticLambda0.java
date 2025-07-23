package com.android.systemui.tuner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TunerFragment$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TunerFragment f$0;

    @Override // java.lang.Runnable
    public final void run() {
        TunerFragment tunerFragment = this.f$0;
        String[] strArr = TunerFragment.DEBUG_ONLY;
        if (tunerFragment.getActivity() != null) {
            tunerFragment.getActivity().finish();
        }
    }
}
