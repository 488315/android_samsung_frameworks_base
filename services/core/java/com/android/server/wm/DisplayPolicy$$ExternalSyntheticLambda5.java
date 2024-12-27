package com.android.server.wm;

import com.android.internal.policy.ForceShowNavBarSettingsObserver;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayPolicy$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ForceShowNavBarSettingsObserver f$0;

    public /* synthetic */ DisplayPolicy$$ExternalSyntheticLambda5(
            ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver, int i) {
        this.$r8$classId = i;
        this.f$0 = forceShowNavBarSettingsObserver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver = this.f$0;
        switch (i) {
            case 0:
                forceShowNavBarSettingsObserver.register();
                break;
            default:
                forceShowNavBarSettingsObserver.unregister();
                break;
        }
    }
}
