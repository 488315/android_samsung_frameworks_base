package com.android.systemui.fragments;

import android.app.Fragment;
import com.android.systemui.fragments.FragmentHostManager;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class FragmentHostManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Fragment f$1;

    public /* synthetic */ FragmentHostManager$$ExternalSyntheticLambda0(String str, Fragment fragment, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
        this.f$1 = fragment;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((FragmentHostManager.FragmentListener) obj).onFragmentViewDestroyed(this.f$1);
                break;
            default:
                ((FragmentHostManager.FragmentListener) obj).onFragmentViewCreated(this.f$1);
                break;
        }
    }
}
