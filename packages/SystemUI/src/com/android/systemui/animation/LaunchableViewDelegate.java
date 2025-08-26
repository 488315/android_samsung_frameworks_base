package com.android.systemui.animation;

import android.view.View;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class LaunchableViewDelegate implements LaunchableView {
    public boolean blockVisibilityChanges;
    public int lastVisibility;
    public final Function1 superSetVisibility;
    public final View view;

    public LaunchableViewDelegate(View view, Function1 function1) {
        this.view = view;
        this.superSetVisibility = function1;
        this.lastVisibility = view.getVisibility();
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        if (z == this.blockVisibilityChanges) {
            return;
        }
        this.blockVisibilityChanges = z;
        if (z) {
            this.lastVisibility = this.view.getVisibility();
            return;
        }
        int i = this.lastVisibility;
        Function1 function1 = this.superSetVisibility;
        if (i == 0) {
            function1.mo781invoke(4);
            function1.mo781invoke(0);
        } else {
            function1.mo781invoke(0);
            function1.mo781invoke(Integer.valueOf(this.lastVisibility));
        }
    }

    public final void setVisibility(int i) {
        if (this.blockVisibilityChanges) {
            this.lastVisibility = i;
        } else {
            this.superSetVisibility.mo781invoke(Integer.valueOf(i));
        }
    }
}
