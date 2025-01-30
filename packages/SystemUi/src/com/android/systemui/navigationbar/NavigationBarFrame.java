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
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        NavBarStore navBarStore = z ? (NavBarStore) Dependency.get(NavBarStore.class) : null;
        NavBarStateManager navStateManager = z ? ((NavBarStoreImpl) navBarStore).getNavStateManager(getContext().getDisplayId()) : null;
        if (z && i == 0 && navStateManager.isNavBarHidden()) {
            Log.d("NavigationBarFrame", "NavigationBar setVisibility(VISIBLE) Ignored!");
            return;
        }
        super.setVisibility(i);
        if (z) {
            ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateNavBarVisibility(i), ((FrameLayout) this).mContext.getDisplayId());
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
