package com.android.systemui.fragments;

import android.app.Fragment;
import com.android.systemui.fragments.FragmentHostManager;
import java.util.function.Consumer;

public final /* synthetic */ class FragmentHostManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Fragment f$1;

    public /* synthetic */ FragmentHostManager$$ExternalSyntheticLambda0(String str, Fragment fragment, int i) {
        this.$r8$classId = i;
        this.f$1 = fragment;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Fragment fragment = this.f$1;
        FragmentHostManager.FragmentListener fragmentListener = (FragmentHostManager.FragmentListener) obj;
        switch (i) {
            case 0:
                fragmentListener.onFragmentViewCreated(fragment);
                break;
            default:
                fragmentListener.onFragmentViewDestroyed(fragment);
                break;
        }
    }
}
