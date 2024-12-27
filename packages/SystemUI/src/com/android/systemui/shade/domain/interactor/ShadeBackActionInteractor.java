package com.android.systemui.shade.domain.interactor;

public interface ShadeBackActionInteractor {
    void animateCollapseQs(boolean z);

    boolean canBeCollapsed();

    boolean closeUserSwitcherIfOpen();

    void onBackPressed();
}
