package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenOffAnimationController implements WakefulnessLifecycle.Observer {
    public final List animations;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public ScreenOffAnimationController(Optional<SysUIUnfoldComponent> optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, WakefulnessLifecycle wakefulnessLifecycle) {
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        SysUIUnfoldComponent orElse = optional.orElse(null);
        this.animations = ArraysKt___ArraysKt.filterNotNull(new ScreenOffAnimation[]{orElse != null ? orElse.getFoldAodAnimationController() : null, unlockedScreenOffAnimationController});
    }

    public final void animateInKeyguard$1(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        Object obj;
        boolean z;
        Iterator it = ((ArrayList) this.animations).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            ScreenOffAnimation screenOffAnimation = (ScreenOffAnimation) obj;
            if (screenOffAnimation.shouldAnimateInKeyguard()) {
                screenOffAnimation.animateInKeyguard(view, keyguardVisibilityHelper$$ExternalSyntheticLambda0);
                z = true;
            } else {
                z = false;
            }
            if (z) {
                break;
            }
        }
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

    public final boolean shouldAnimateInKeyguard() {
        List list = this.animations;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ScreenOffAnimation) it.next()).shouldAnimateInKeyguard()) {
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

    public final boolean shouldHideNotificationsFooter() {
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

    public final boolean shouldIgnoreKeyguardTouches() {
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
}
