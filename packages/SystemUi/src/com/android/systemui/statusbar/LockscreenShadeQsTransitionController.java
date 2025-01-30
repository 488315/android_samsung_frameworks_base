package com.android.systemui.statusbar;

import android.content.Context;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LockscreenShadeQsTransitionController extends AbstractLockscreenShadeTransitionController {
    public boolean isTransitioningToFullShade;
    public float qsDragDownAmount;
    public final Function0 qsProvider;
    public float qsSquishStartFraction;
    public int qsSquishTransitionDistance;
    public float qsSquishTransitionFraction;
    public int qsTransitionDistance;
    public float qsTransitionFraction;
    public int qsTransitionStartDelay;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        LockscreenShadeQsTransitionController create(Function0 function0);
    }

    public LockscreenShadeQsTransitionController(Context context, ConfigurationController configurationController, DumpManager dumpManager, Function0 function0) {
        super(context, configurationController, dumpManager);
        this.qsProvider = function0;
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        int i = this.qsTransitionDistance;
        int i2 = this.qsTransitionStartDelay;
        int i3 = this.qsSquishTransitionDistance;
        float f = this.qsSquishStartFraction;
        float f2 = this.dragDownAmount;
        float f3 = this.qsDragDownAmount;
        float f4 = this.qsTransitionFraction;
        float f5 = this.qsSquishTransitionFraction;
        boolean z = this.isTransitioningToFullShade;
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("\n            Resources:\n              qsTransitionDistance: ", i, "\n              qsTransitionStartDelay: ", i2, "\n              qsSquishTransitionDistance: ");
        m45m.append(i3);
        m45m.append("\n              qsSquishStartFraction: ");
        m45m.append(f);
        m45m.append("\n            State:\n              dragDownAmount: ");
        m45m.append(f2);
        m45m.append("\n              qsDragDownAmount: ");
        m45m.append(f3);
        m45m.append("\n              qsDragFraction: ");
        m45m.append(f4);
        m45m.append("\n              qsSquishFraction: ");
        m45m.append(f5);
        m45m.append("\n              isTransitioningToFullShade: ");
        m45m.append(z);
        m45m.append("\n        ");
        indentingPrintWriter.println(StringsKt__IndentKt.trimIndent(m45m.toString()));
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void onDragDownAmountChanged(float f) {
        float f2 = f - this.qsTransitionStartDelay;
        this.qsDragDownAmount = f2;
        this.qsTransitionFraction = MathUtils.saturate(f2 / this.qsTransitionDistance);
        this.qsSquishTransitionFraction = MathUtils.lerp(this.qsSquishStartFraction, 1.0f, MathUtils.saturate(this.qsDragDownAmount / this.qsSquishTransitionDistance));
        this.isTransitioningToFullShade = f > 0.0f;
        ((InterfaceC1922QS) this.qsProvider.invoke()).setTransitionToFullShadeProgress(this.isTransitioningToFullShade, this.qsTransitionFraction, this.qsSquishTransitionFraction);
    }

    @Override // com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController
    public final void updateResources() {
        Context context = this.context;
        this.qsTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_distance);
        this.qsTransitionStartDelay = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_transition_delay);
        this.qsSquishTransitionDistance = context.getResources().getDimensionPixelSize(R.dimen.lockscreen_shade_qs_squish_transition_distance);
        float f = context.getResources().getFloat(R.dimen.lockscreen_shade_qs_squish_start_fraction);
        this.qsSquishStartFraction = f;
        this.qsSquishTransitionFraction = Math.max(this.qsSquishTransitionFraction, f);
    }
}
