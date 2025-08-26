package com.android.systemui.tuner;

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
