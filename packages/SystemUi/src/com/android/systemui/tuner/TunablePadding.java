package com.android.systemui.tuner;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.tuner.TunerService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TunablePadding implements TunerService.Tunable {
    public final int mDefaultSize;
    public final float mDensity;
    public final int mFlags;
    public final View mView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class TunablePaddingService {
        public TunablePaddingService(TunerService tunerService) {
        }
    }

    private TunablePadding(String str, int i, int i2, View view, TunerService tunerService) {
        this.mDefaultSize = i;
        this.mFlags = i2;
        this.mView = view;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) view.getContext().getSystemService(WindowManager.class)).getDefaultDisplay().getMetrics(displayMetrics);
        this.mDensity = displayMetrics.density;
        tunerService.addTunable(this, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.tuner.TunerService.Tunable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTuningChanged(String str, String str2) {
        int parseInt;
        int i;
        if (str2 != null) {
            try {
                parseInt = (int) (Integer.parseInt(str2) * this.mDensity);
            } catch (NumberFormatException unused) {
            }
            View view = this.mView;
            int i2 = !view.isLayoutRtl() ? 2 : 1;
            int i3 = view.isLayoutRtl() ? 1 : 2;
            i = this.mFlags;
            int i4 = (i2 & i) == 0 ? parseInt : 0;
            int i5 = (i & 4) == 0 ? parseInt : 0;
            int i6 = (i3 & i) == 0 ? parseInt : 0;
            if ((i & 8) == 0) {
                parseInt = 0;
            }
            view.setPadding(i4, i5, i6, parseInt);
        }
        parseInt = this.mDefaultSize;
        View view2 = this.mView;
        if (!view2.isLayoutRtl()) {
        }
        if (view2.isLayoutRtl()) {
        }
        i = this.mFlags;
        if ((i2 & i) == 0) {
        }
        if ((i & 4) == 0) {
        }
        if ((i3 & i) == 0) {
        }
        if ((i & 8) == 0) {
        }
        view2.setPadding(i4, i5, i6, parseInt);
    }
}
