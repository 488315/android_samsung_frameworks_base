package com.android.systemui.shade.domain.interactor;

/* loaded from: classes3.dex */
public interface ShadeBackActionInteractor {
    void animateCollapseQs(boolean z);

    boolean canBeCollapsed();

    void onBackPressed();
}
