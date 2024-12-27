package com.android.systemui.statusbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.animation.PathInterpolator;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SplitShadeLockScreenOverScroller {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public int maxOverScrollAmount;
    public final Function0 nsslControllerProvider;
    public final Function0 qSProvider;
    public Animator releaseOverScrollAnimator;
    public long releaseOverScrollDuration;
    public final ScrimController scrimController;
    public final SysuiStatusBarStateController statusBarStateController;
    public int transitionToFullShadeDistance;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        SplitShadeLockScreenOverScroller create(Function0 function0, Function0 function02);
    }

    static {
        new Companion(null);
        new PathInterpolator(0.17f, 0.0f, 0.0f, 1.0f);
    }

    public SplitShadeLockScreenOverScroller(ConfigurationController configurationController, DumpManager dumpManager, Context context, ScrimController scrimController, SysuiStatusBarStateController sysuiStatusBarStateController, Function0 function0, Function0 function02) {
        this.context = context;
        Resources resources = context.getResources();
        this.transitionToFullShadeDistance = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_full_transition_distance);
        this.maxOverScrollAmount = resources.getDimensionPixelSize(R.dimen.lockscreen_shade_max_over_scroll_amount);
        this.releaseOverScrollDuration = resources.getInteger(R.integer.lockscreen_shade_over_scroll_release_duration);
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.SplitShadeLockScreenOverScroller.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                int i = SplitShadeLockScreenOverScroller.$r8$clinit;
                SplitShadeLockScreenOverScroller splitShadeLockScreenOverScroller = SplitShadeLockScreenOverScroller.this;
                Resources resources2 = splitShadeLockScreenOverScroller.context.getResources();
                splitShadeLockScreenOverScroller.transitionToFullShadeDistance = resources2.getDimensionPixelSize(R.dimen.lockscreen_shade_full_transition_distance);
                splitShadeLockScreenOverScroller.maxOverScrollAmount = resources2.getDimensionPixelSize(R.dimen.lockscreen_shade_max_over_scroll_amount);
                splitShadeLockScreenOverScroller.releaseOverScrollDuration = resources2.getInteger(R.integer.lockscreen_shade_over_scroll_release_duration);
            }
        });
        dumpManager.registerCriticalDumpable("SplitShadeLockscreenOverScroller", new Dumpable() { // from class: com.android.systemui.statusbar.SplitShadeLockScreenOverScroller.2
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                SplitShadeLockScreenOverScroller splitShadeLockScreenOverScroller = SplitShadeLockScreenOverScroller.this;
                int i = splitShadeLockScreenOverScroller.transitionToFullShadeDistance;
                int i2 = splitShadeLockScreenOverScroller.maxOverScrollAmount;
                long j = splitShadeLockScreenOverScroller.releaseOverScrollDuration;
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "\n            SplitShadeLockScreenOverScroller:\n                Resources:\n                    transitionToFullShadeDistance: ", "\n                    maxOverScrollAmount: ", "\n                    releaseOverScrollDuration: ");
                m.append(j);
                m.append("\n                State:\n                    previousOverscrollAmount: 0\n                    expansionDragDownAmount: 0.0\n            ");
                printWriter.println(StringsKt__IndentKt.trimIndent(m.toString()));
            }
        });
    }

    public final void finishAnimations$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        Animator animator = this.releaseOverScrollAnimator;
        if (animator != null) {
            animator.end();
        }
        this.releaseOverScrollAnimator = null;
    }
}
