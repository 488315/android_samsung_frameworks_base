package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.animation.PathInterpolator;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitShadeLockScreenOverScroller implements LockScreenShadeOverScroller {
    public static final PathInterpolator RELEASE_OVER_SCROLL_INTERPOLATOR;
    public final Context context;
    public float expansionDragDownAmount;
    public int maxOverScrollAmount;
    public final Function0 nsslControllerProvider;
    public int previousOverscrollAmount;
    public final Function0 qSProvider;
    public Animator releaseOverScrollAnimator;
    public long releaseOverScrollDuration;
    public final ScrimController scrimController;
    public final SysuiStatusBarStateController statusBarStateController;
    public int transitionToFullShadeDistance;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        SplitShadeLockScreenOverScroller create(Function0 function0, Function0 function02);
    }

    static {
        new Companion(null);
        RELEASE_OVER_SCROLL_INTERPOLATOR = new PathInterpolator(0.17f, 0.0f, 0.0f, 1.0f);
    }

    public SplitShadeLockScreenOverScroller(ConfigurationController configurationController, DumpManager dumpManager, Context context, ScrimController scrimController, SysuiStatusBarStateController sysuiStatusBarStateController, Function0 function0, Function0 function02) {
        this.context = context;
        this.scrimController = scrimController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.qSProvider = function0;
        this.nsslControllerProvider = function02;
        updateResources();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.SplitShadeLockScreenOverScroller.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                PathInterpolator pathInterpolator = SplitShadeLockScreenOverScroller.RELEASE_OVER_SCROLL_INTERPOLATOR;
                SplitShadeLockScreenOverScroller.this.updateResources();
            }
        });
        dumpManager.registerCriticalDumpable("SplitShadeLockscreenOverScroller", new Dumpable() { // from class: com.android.systemui.statusbar.SplitShadeLockScreenOverScroller.2
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                SplitShadeLockScreenOverScroller splitShadeLockScreenOverScroller = SplitShadeLockScreenOverScroller.this;
                int i = splitShadeLockScreenOverScroller.transitionToFullShadeDistance;
                int i2 = splitShadeLockScreenOverScroller.maxOverScrollAmount;
                long j = splitShadeLockScreenOverScroller.releaseOverScrollDuration;
                int i3 = splitShadeLockScreenOverScroller.previousOverscrollAmount;
                float f = splitShadeLockScreenOverScroller.expansionDragDownAmount;
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("\n            SplitShadeLockScreenOverScroller:\n                Resources:\n                    transitionToFullShadeDistance: ", i, "\n                    maxOverScrollAmount: ", i2, "\n                    releaseOverScrollDuration: ");
                m45m.append(j);
                m45m.append("\n                State:\n                    previousOverscrollAmount: ");
                m45m.append(i3);
                m45m.append("\n                    expansionDragDownAmount: ");
                m45m.append(f);
                m45m.append("\n            ");
                printWriter.println(StringsKt__IndentKt.trimIndent(m45m.toString()));
            }
        });
    }

    /* renamed from: finishAnimations$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m198xfdeaf2a8() {
        Animator animator = this.releaseOverScrollAnimator;
        if (animator != null) {
            animator.end();
        }
        this.releaseOverScrollAnimator = null;
    }

    @Override // com.android.systemui.statusbar.LockScreenShadeOverScroller
    public final void setExpansionDragDownAmount(float f) {
        if (this.expansionDragDownAmount == f) {
            return;
        }
        this.expansionDragDownAmount = f;
        SysuiStatusBarStateController sysuiStatusBarStateController = this.statusBarStateController;
        if (((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
            Function0 function0 = this.nsslControllerProvider;
            float height = ((NotificationStackScrollLayoutController) function0.invoke()).getHeight();
            int overshootInterpolation = (int) (Interpolators.getOvershootInterpolation(MathUtils.saturate(f / height), this.transitionToFullShadeDistance / height) * this.maxOverScrollAmount);
            ((InterfaceC1922QS) this.qSProvider.invoke()).setOverScrollAmount(overshootInterpolation);
            this.scrimController.mNotificationsScrim.setTranslationY(overshootInterpolation);
            ((NotificationStackScrollLayoutController) function0.invoke()).setOverScrollAmount(overshootInterpolation);
            this.previousOverscrollAmount = overshootInterpolation;
            return;
        }
        if (((((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) || this.previousOverscrollAmount == 0) ? false : true) {
            ValueAnimator ofInt = ValueAnimator.ofInt(this.previousOverscrollAmount, 0);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.SplitShadeLockScreenOverScroller$releaseOverScroll$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    SplitShadeLockScreenOverScroller splitShadeLockScreenOverScroller = SplitShadeLockScreenOverScroller.this;
                    PathInterpolator pathInterpolator = SplitShadeLockScreenOverScroller.RELEASE_OVER_SCROLL_INTERPOLATOR;
                    ((InterfaceC1922QS) splitShadeLockScreenOverScroller.qSProvider.invoke()).setOverScrollAmount(intValue);
                    SplitShadeLockScreenOverScroller.this.scrimController.mNotificationsScrim.setTranslationY(intValue);
                    ((NotificationStackScrollLayoutController) SplitShadeLockScreenOverScroller.this.nsslControllerProvider.invoke()).setOverScrollAmount(intValue);
                }
            });
            ofInt.setInterpolator(RELEASE_OVER_SCROLL_INTERPOLATOR);
            ofInt.setDuration(this.releaseOverScrollDuration);
            ofInt.start();
            this.releaseOverScrollAnimator = ofInt;
            this.previousOverscrollAmount = 0;
        }
    }

    public final void updateResources() {
        Resources resources = this.context.getResources();
        this.transitionToFullShadeDistance = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_full_transition_distance);
        this.maxOverScrollAmount = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_max_over_scroll_amount);
        this.releaseOverScrollDuration = resources.getInteger(R.integer.lockscreen_shade_over_scroll_release_duration);
    }
}
