package com.android.systemui.tuner;

import com.android.systemui.fragments.FragmentService;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TunerActivity$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TunerActivity$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = TunerActivity.$r8$clinit;
                Iterator it = ((FragmentService) obj).mHosts.values().iterator();
                while (it.hasNext()) {
                    ((FragmentService.FragmentHostState) it.next()).mFragmentHostManager.mFragments.dispatchDestroy();
                }
                break;
            default:
                DisplayLifecycle displayLifecycle = (DisplayLifecycle) obj;
                displayLifecycle.mDisplayManager.unregisterDisplayListener(displayLifecycle.mDisplayListener);
                break;
        }
    }
}
