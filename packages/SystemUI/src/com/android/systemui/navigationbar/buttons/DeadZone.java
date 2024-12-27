package com.android.systemui.navigationbar.buttons;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.util.FloatProperty;
import android.view.MotionEvent;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarControllerImpl;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;

public final class DeadZone {
    public static final AnonymousClass1 FLASH_PROPERTY = new FloatProperty("DeadZoneFlash") { // from class: com.android.systemui.navigationbar.buttons.DeadZone.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((DeadZone) obj).mFlashFrac);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            DeadZone deadZone = (DeadZone) obj;
            deadZone.mFlashFrac = f;
            deadZone.mNavigationBarView.postInvalidate();
        }
    };
    public int mDecay;
    public final int mDisplayId;
    public int mDisplayRotation;
    public int mHold;
    public long mLastPokeTime;
    public final NavBarStore mNavBarStore;
    public final NavigationBarView mNavigationBarView;
    public boolean mShouldFlash;
    public int mSizeMax;
    public int mSizeMin;
    public final boolean mUseDeadZone;
    public boolean mVertical;
    public float mFlashFrac = 0.0f;
    public final AnonymousClass2 mDebugFlash = new Runnable() { // from class: com.android.systemui.navigationbar.buttons.DeadZone.2
        @Override // java.lang.Runnable
        public final void run() {
            ObjectAnimator.ofFloat(DeadZone.this, DeadZone.FLASH_PROPERTY, 1.0f, 0.0f).setDuration(150L).start();
        }
    };
    public final NavigationBarController mNavBarController = (NavigationBarController) Dependency.sDependency.getDependencyInner(NavigationBarController.class);

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.navigationbar.buttons.DeadZone$2] */
    public DeadZone(NavigationBarView navigationBarView) {
        this.mUseDeadZone = navigationBarView.getResources().getBoolean(R.bool.config_useDeadZone);
        this.mNavigationBarView = navigationBarView;
        this.mDisplayId = navigationBarView.getContext().getDisplayId();
        if (BasicRune.NAVBAR_ENABLED) {
            this.mNavBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
        }
        onConfigurationChanged(0);
    }

    public final float getSize(long j) {
        int m$1;
        int i = this.mSizeMax;
        if (i == 0) {
            return 0.0f;
        }
        long j2 = j - this.mLastPokeTime;
        int i2 = this.mHold;
        int i3 = this.mDecay;
        if (j2 > i2 + i3) {
            m$1 = this.mSizeMin;
        } else {
            if (j2 < i2) {
                return i;
            }
            float f = i;
            m$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mSizeMin, f, (j2 - i2) / i3, f);
        }
        return m$1;
    }

    public final void onConfigurationChanged(int i) {
        boolean z = this.mUseDeadZone;
        if (z) {
            this.mDisplayRotation = i;
            NavigationBarView navigationBarView = this.mNavigationBarView;
            Resources resources = navigationBarView.getResources();
            this.mHold = resources.getInteger(R.integer.navigation_bar_deadzone_hold);
            this.mDecay = resources.getInteger(R.integer.navigation_bar_deadzone_decay);
            this.mSizeMin = resources.getDimensionPixelSize(R.dimen.navigation_bar_deadzone_size);
            this.mSizeMax = resources.getDimensionPixelSize(R.dimen.navigation_bar_deadzone_size_max);
            if (BasicRune.NAVBAR_ENABLED) {
                EventTypeFactory.EventType.GetDeadZoneSize getDeadZoneSize = new EventTypeFactory.EventType.GetDeadZoneSize(false);
                Integer valueOf = Integer.valueOf(resources.getDimensionPixelSize(R.dimen.navigation_bar_deadzone_size));
                NavBarStore navBarStore = this.mNavBarStore;
                int i2 = this.mDisplayId;
                this.mSizeMin = ((Integer) ((NavBarStoreImpl) navBarStore).handleEvent(this, getDeadZoneSize, i2, valueOf)).intValue();
                this.mSizeMax = ((Integer) ((NavBarStoreImpl) navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetDeadZoneSize(true), i2, Integer.valueOf(resources.getDimensionPixelSize(R.dimen.navigation_bar_deadzone_size_max)))).intValue();
            }
            this.mVertical = resources.getInteger(R.integer.navigation_bar_deadzone_orientation) == 1;
            boolean z2 = resources.getBoolean(R.bool.config_dead_zone_flash);
            if (z) {
                this.mShouldFlash = z2;
                this.mFlashFrac = 0.0f;
                navigationBarView.postInvalidate();
            }
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mUseDeadZone || motionEvent.getToolType(0) == 3) {
            return false;
        }
        int action = motionEvent.getAction();
        NavigationBarView navigationBarView = this.mNavigationBarView;
        if (action == 4) {
            this.mLastPokeTime = motionEvent.getEventTime();
            if (this.mShouldFlash) {
                navigationBarView.postInvalidate();
            }
            return true;
        }
        if (action == 0) {
            ((NavigationBarControllerImpl) this.mNavBarController).touchAutoDim(this.mDisplayId);
            int size = (int) getSize(motionEvent.getEventTime());
            if (!this.mVertical ? motionEvent.getY() < size : !(this.mDisplayRotation != 3 ? motionEvent.getX() >= size : motionEvent.getX() <= navigationBarView.getWidth() - size)) {
                motionEvent.getX();
                motionEvent.getY();
                if (this.mShouldFlash) {
                    navigationBarView.post(this.mDebugFlash);
                    navigationBarView.postInvalidate();
                }
                return true;
            }
        }
        return false;
    }
}
