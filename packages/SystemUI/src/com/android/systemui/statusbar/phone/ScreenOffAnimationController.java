package com.android.systemui.statusbar.phone;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes3.dex */
public final class ScreenOffAnimationController implements WakefulnessLifecycle.Observer {
    public final List animations;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public ScreenOffAnimationController(Optional<SysUIUnfoldComponent> optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, WakefulnessLifecycle wakefulnessLifecycle) {
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        SysUIUnfoldComponent sysUIUnfoldComponentOrElse = optional.orElse(null);
        this.animations = ArraysKt___ArraysKt.filterNotNull(new ScreenOffAnimation[]{sysUIUnfoldComponentOrElse != null ? sysUIUnfoldComponentOrElse.getFoldAodAnimationController() : null, unlockedScreenOffAnimationController});
    }

    public final boolean isKeyguardShowDelayed() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).isKeyguardShowDelayed()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        Iterator it = this.animations.iterator();
        while (it.hasNext() && !((ScreenOffAnimation) it.next()).startAnimation()) {
        }
    }

    public final boolean overrideNotificationsFullyDozingOnKeyguard() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).overrideNotificationsDozeAmount()) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldDelayKeyguardShow() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).shouldDelayKeyguardShow()) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldExpandNotifications() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).isAnimationPlaying()) {
                return true;
            }
        }
        return false;
    }

    public final boolean shouldHideLightRevealScrimOnWakeUp() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).shouldHideScrimOnWakeUp()) {
                return true;
            }
        }
        return false;
    }
}
