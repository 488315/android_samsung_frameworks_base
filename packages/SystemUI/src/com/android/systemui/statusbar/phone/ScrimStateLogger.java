package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimViewBase;
import com.android.systemui.statusbar.phone.ScrimController;
import java.util.Locale;
import java.util.function.IntConsumer;

/* loaded from: classes3.dex */
public class ScrimStateLogger {
    public final Callback mCallback;
    public final ScrimViewBase[] mScrimViews;
    public final int[] mColors = new int[3];
    public final float[] mAlphas = new float[3];
    public boolean mForceChanged = false;
    public int mScrimVisibility = -1;

    public interface Callback {
    }

    public ScrimStateLogger(ScrimViewBase scrimViewBase, ScrimViewBase scrimViewBase2, ScrimViewBase scrimViewBase3, Callback callback) {
        this.mScrimViews = new ScrimViewBase[]{scrimViewBase, scrimViewBase2, scrimViewBase3};
        this.mCallback = callback;
        ((ScrimView) scrimViewBase).mVisibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                ScrimStateLogger scrimStateLogger = this.f$0;
                scrimStateLogger.mForceChanged = true;
                scrimStateLogger.logScrimColor(true);
            }
        };
        ((ScrimView) scrimViewBase2).mVisibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                ScrimStateLogger scrimStateLogger = this.f$0;
                scrimStateLogger.mForceChanged = true;
                scrimStateLogger.logScrimColor(true);
            }
        };
        ((ScrimView) scrimViewBase3).mVisibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                ScrimStateLogger scrimStateLogger = this.f$0;
                scrimStateLogger.mForceChanged = true;
                scrimStateLogger.logScrimColor(true);
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void logScrimColor(boolean z) {
        boolean z2;
        ScrimController.AnonymousClass3 anonymousClass3 = (ScrimController.AnonymousClass3) this.mCallback;
        int i = ScrimController.this.mScrimsVisibility;
        if (!this.mForceChanged) {
            if (this.mScrimVisibility == i) {
                z2 = false;
            }
            ScrimViewBase[] scrimViewBaseArr = this.mScrimViews;
            if (!z2) {
                if (this.mForceChanged) {
                    this.mForceChanged = false;
                } else if (this.mScrimVisibility == i) {
                    int i2 = 0;
                    for (ScrimViewBase scrimViewBase : scrimViewBaseArr) {
                        ScrimView scrimView = (ScrimView) scrimViewBase;
                        if (scrimView.getMainColor() == this.mColors[i2] && scrimView.mViewAlpha == this.mAlphas[i2]) {
                            i2++;
                        }
                    }
                    return;
                }
                if (!z) {
                    return;
                }
            }
            ScrimView scrimView2 = (ScrimView) scrimViewBaseArr[0];
            ScrimView scrimView3 = (ScrimView) scrimViewBaseArr[1];
            ScrimView scrimView4 = (ScrimView) scrimViewBaseArr[2];
            String str = String.format(Locale.US, "updateScrimColor main=0x%x front=0x%x|%f|%d noti=0x%x|%f|%d behind=0x%x|%f|%d vis=%d", Integer.valueOf(ScrimController.this.mColors.getMainColor()), Integer.valueOf(scrimView2.getMainColor()), Float.valueOf(scrimView2.mViewAlpha), Integer.valueOf(scrimView2.getVisibility()), Integer.valueOf(scrimView3.getMainColor()), Float.valueOf(scrimView3.mViewAlpha), Integer.valueOf(scrimView3.getVisibility()), Integer.valueOf(scrimView4.getMainColor()), Float.valueOf(scrimView4.mViewAlpha), Integer.valueOf(scrimView4.getVisibility()), Integer.valueOf(i));
            anonymousClass3.getClass();
            Log.d("ScrimController", str);
        }
        this.mForceChanged = false;
        z2 = true;
        ScrimViewBase[] scrimViewBaseArr2 = this.mScrimViews;
        if (!z2) {
        }
        ScrimView scrimView22 = (ScrimView) scrimViewBaseArr2[0];
        ScrimView scrimView32 = (ScrimView) scrimViewBaseArr2[1];
        ScrimView scrimView42 = (ScrimView) scrimViewBaseArr2[2];
        String str2 = String.format(Locale.US, "updateScrimColor main=0x%x front=0x%x|%f|%d noti=0x%x|%f|%d behind=0x%x|%f|%d vis=%d", Integer.valueOf(ScrimController.this.mColors.getMainColor()), Integer.valueOf(scrimView22.getMainColor()), Float.valueOf(scrimView22.mViewAlpha), Integer.valueOf(scrimView22.getVisibility()), Integer.valueOf(scrimView32.getMainColor()), Float.valueOf(scrimView32.mViewAlpha), Integer.valueOf(scrimView32.getVisibility()), Integer.valueOf(scrimView42.getMainColor()), Float.valueOf(scrimView42.mViewAlpha), Integer.valueOf(scrimView42.getVisibility()), Integer.valueOf(i));
        anonymousClass3.getClass();
        Log.d("ScrimController", str2);
    }
}
