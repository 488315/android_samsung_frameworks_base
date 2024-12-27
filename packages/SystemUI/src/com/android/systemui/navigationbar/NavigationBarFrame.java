package com.android.systemui.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;

public class NavigationBarFrame extends FrameLayout {
    public DeadZone mDeadZone;

    public NavigationBarFrame(Context context) {
        super(context);
        this.mDeadZone = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DeadZone deadZone;
        return (motionEvent.getAction() != 4 || (deadZone = this.mDeadZone) == null) ? super.dispatchTouchEvent(motionEvent) : deadZone.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        boolean z = BasicRune.NAVBAR_ENABLED;
        NavBarStore navBarStore = z ? (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class) : null;
        NavBarStateManager navStateManager = z ? ((NavBarStoreImpl) navBarStore).getNavStateManager(getContext().getDisplayId()) : null;
        if (z && i == 0 && ((NavBarStateManagerImpl) navStateManager).isNavBarHidden()) {
            Log.d("NavigationBarFrame", "NavigationBar setVisibility(VISIBLE) Ignored!");
            return;
        }
        super.setVisibility(i);
        if (z) {
            navBarStore.handleEvent(this, new EventTypeFactory.EventType.OnUpdateNavBarVisibility(i), ((FrameLayout) this).mContext.getDisplayId());
        }
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDeadZone = null;
    }
}
