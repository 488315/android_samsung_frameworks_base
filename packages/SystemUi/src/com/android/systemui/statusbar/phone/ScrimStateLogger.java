package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.scrim.ScrimViewBase;
import com.android.systemui.statusbar.phone.ScrimController;
import java.util.Locale;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrimStateLogger {
    public final Callback mCallback;
    public final ScrimViewBase[] mScrimViews;
    public final int[] mColors = new int[3];
    public final float[] mAlphas = new float[3];
    public boolean mForceChanged = false;
    public int mScrimVisibility = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    public ScrimStateLogger(ScrimViewBase scrimViewBase, ScrimViewBase scrimViewBase2, ScrimViewBase scrimViewBase3, Callback callback) {
        this.mScrimViews = new ScrimViewBase[]{scrimViewBase, scrimViewBase2, scrimViewBase3};
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        this.mCallback = callback;
        ((ScrimView) scrimViewBase).mVisibilityChangedListener = new IntConsumer(this) { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            public final /* synthetic */ ScrimStateLogger f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                switch (i) {
                    case 0:
                        ScrimStateLogger scrimStateLogger = this.f$0;
                        scrimStateLogger.mForceChanged = true;
                        scrimStateLogger.logScrimColor(true);
                        break;
                    case 1:
                        ScrimStateLogger scrimStateLogger2 = this.f$0;
                        scrimStateLogger2.mForceChanged = true;
                        scrimStateLogger2.logScrimColor(true);
                        break;
                    default:
                        ScrimStateLogger scrimStateLogger3 = this.f$0;
                        scrimStateLogger3.mForceChanged = true;
                        scrimStateLogger3.logScrimColor(true);
                        break;
                }
            }
        };
        ((ScrimView) scrimViewBase2).mVisibilityChangedListener = new IntConsumer(this) { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            public final /* synthetic */ ScrimStateLogger f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                switch (i2) {
                    case 0:
                        ScrimStateLogger scrimStateLogger = this.f$0;
                        scrimStateLogger.mForceChanged = true;
                        scrimStateLogger.logScrimColor(true);
                        break;
                    case 1:
                        ScrimStateLogger scrimStateLogger2 = this.f$0;
                        scrimStateLogger2.mForceChanged = true;
                        scrimStateLogger2.logScrimColor(true);
                        break;
                    default:
                        ScrimStateLogger scrimStateLogger3 = this.f$0;
                        scrimStateLogger3.mForceChanged = true;
                        scrimStateLogger3.logScrimColor(true);
                        break;
                }
            }
        };
        ((ScrimView) scrimViewBase3).mVisibilityChangedListener = new IntConsumer(this) { // from class: com.android.systemui.statusbar.phone.ScrimStateLogger$$ExternalSyntheticLambda0
            public final /* synthetic */ ScrimStateLogger f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                switch (i3) {
                    case 0:
                        ScrimStateLogger scrimStateLogger = this.f$0;
                        scrimStateLogger.mForceChanged = true;
                        scrimStateLogger.logScrimColor(true);
                        break;
                    case 1:
                        ScrimStateLogger scrimStateLogger2 = this.f$0;
                        scrimStateLogger2.mForceChanged = true;
                        scrimStateLogger2.logScrimColor(true);
                        break;
                    default:
                        ScrimStateLogger scrimStateLogger3 = this.f$0;
                        scrimStateLogger3.mForceChanged = true;
                        scrimStateLogger3.logScrimColor(true);
                        break;
                }
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0057 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void logScrimColor(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        ScrimController.C31063 c31063 = (ScrimController.C31063) this.mCallback;
        int i = ScrimController.this.mScrimsVisibility;
        if (this.mForceChanged) {
            this.mForceChanged = false;
        } else if (this.mScrimVisibility == i) {
            z2 = false;
            ScrimViewBase[] scrimViewBaseArr = this.mScrimViews;
            if (!z2) {
                if (this.mForceChanged) {
                    this.mForceChanged = false;
                } else if (this.mScrimVisibility == i) {
                    z3 = false;
                    if (!z3) {
                        int i2 = 0;
                        for (ScrimViewBase scrimViewBase : scrimViewBaseArr) {
                            ScrimView scrimView = (ScrimView) scrimViewBase;
                            if (scrimView.getMainColor() == this.mColors[i2] && scrimView.mViewAlpha == this.mAlphas[i2]) {
                                i2++;
                            }
                        }
                        z4 = false;
                        if (z4 || !z) {
                            return;
                        }
                    }
                    z4 = true;
                    if (z4) {
                        return;
                    } else {
                        return;
                    }
                }
                z3 = true;
                if (!z3) {
                }
                z4 = true;
                if (z4) {
                }
            }
            ScrimView scrimView2 = (ScrimView) scrimViewBaseArr[0];
            ScrimView scrimView3 = (ScrimView) scrimViewBaseArr[1];
            ScrimView scrimView4 = (ScrimView) scrimViewBaseArr[2];
            String format = String.format(Locale.US, "updateScrimColor main=0x%x front=0x%x|%f|%d noti=0x%x|%f|%d behind=0x%x|%f|%d vis=%d", Integer.valueOf(ScrimController.this.mColors.getMainColor()), Integer.valueOf(scrimView2.getMainColor()), Float.valueOf(scrimView2.mViewAlpha), Integer.valueOf(scrimView2.getVisibility()), Integer.valueOf(scrimView3.getMainColor()), Float.valueOf(scrimView3.mViewAlpha), Integer.valueOf(scrimView3.getVisibility()), Integer.valueOf(scrimView4.getMainColor()), Float.valueOf(scrimView4.mViewAlpha), Integer.valueOf(scrimView4.getVisibility()), Integer.valueOf(i));
            c31063.getClass();
            Log.d("ScrimController", format);
        }
        z2 = true;
        ScrimViewBase[] scrimViewBaseArr2 = this.mScrimViews;
        if (!z2) {
        }
        ScrimView scrimView22 = (ScrimView) scrimViewBaseArr2[0];
        ScrimView scrimView32 = (ScrimView) scrimViewBaseArr2[1];
        ScrimView scrimView42 = (ScrimView) scrimViewBaseArr2[2];
        String format2 = String.format(Locale.US, "updateScrimColor main=0x%x front=0x%x|%f|%d noti=0x%x|%f|%d behind=0x%x|%f|%d vis=%d", Integer.valueOf(ScrimController.this.mColors.getMainColor()), Integer.valueOf(scrimView22.getMainColor()), Float.valueOf(scrimView22.mViewAlpha), Integer.valueOf(scrimView22.getVisibility()), Integer.valueOf(scrimView32.getMainColor()), Float.valueOf(scrimView32.mViewAlpha), Integer.valueOf(scrimView32.getVisibility()), Integer.valueOf(scrimView42.getMainColor()), Float.valueOf(scrimView42.mViewAlpha), Integer.valueOf(scrimView42.getVisibility()), Integer.valueOf(i));
        c31063.getClass();
        Log.d("ScrimController", format2);
    }
}
