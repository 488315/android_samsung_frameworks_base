package com.google.android.material.bottomsheet;

import android.view.View;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.animation.AnimationUtils;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class InsetsAnimationCallback extends WindowInsetsAnimationCompat.Callback {
    public int startTranslationY;
    public int startY;
    public final int[] tmpLocation;
    public final View view;

    public InsetsAnimationCallback(View view) {
        super(0);
        this.tmpLocation = new int[2];
        this.view = view;
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final void onEnd(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
        if ((windowInsetsAnimationCompat.mImpl.mWrapped.getTypeMask() & 8) != 0) {
            this.view.setTranslationY(0.0f);
        }
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final void onPrepare(WindowInsetsAnimationCompat windowInsetsAnimationCompat) {
        if ((windowInsetsAnimationCompat.mImpl.mWrapped.getTypeMask() & 8) != 0) {
            View view = this.view;
            int[] iArr = this.tmpLocation;
            view.getLocationOnScreen(iArr);
            this.startY = iArr[1];
        }
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List list) {
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if ((((WindowInsetsAnimationCompat) it.next()).mImpl.mWrapped.getTypeMask() & 8) != 0) {
                this.view.setTranslationY(AnimationUtils.lerp(r0.mImpl.mWrapped.getInterpolatedFraction(), this.startTranslationY, 0));
                break;
            }
        }
        return windowInsetsCompat;
    }

    @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
    public final WindowInsetsAnimationCompat.BoundsCompat onStart(WindowInsetsAnimationCompat windowInsetsAnimationCompat, WindowInsetsAnimationCompat.BoundsCompat boundsCompat) {
        if ((windowInsetsAnimationCompat.mImpl.mWrapped.getTypeMask() & 8) != 0) {
            View view = this.view;
            int[] iArr = this.tmpLocation;
            view.getLocationOnScreen(iArr);
            int i = this.startY - iArr[1];
            this.startTranslationY = i;
            this.view.setTranslationY(i);
        }
        return boundsCompat;
    }
}
