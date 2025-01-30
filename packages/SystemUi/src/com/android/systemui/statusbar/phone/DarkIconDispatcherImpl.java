package com.android.systemui.statusbar.phone;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.ArrayMap;
import android.widget.ImageView;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DarkIconDispatcherImpl implements SysuiDarkIconDispatcher, LightBarTransitionsController.DarkIntensityApplier {
    public float mDarkIntensity;
    public final int mDarkModeIconColorSingleTone;
    public final int mLightModeIconColorSingleTone;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final LightBarTransitionsController mTransitionsController;
    public final ArrayList mTintAreas = new ArrayList();
    public final ArrayMap mReceivers = new ArrayMap();
    public int mIconTint = -301989889;

    public DarkIconDispatcherImpl(Context context, LightBarTransitionsController.Factory factory, DumpManager dumpManager, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        this.mDarkModeIconColorSingleTone = context.getColor(R.color.dark_mode_icon_color_single_tone);
        this.mLightModeIconColorSingleTone = context.getColor(R.color.light_mode_icon_color_single_tone);
        this.mTransitionsController = factory.create(this);
        dumpManager.registerDumpable("DarkIconDispatcherImpl", this);
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void addDarkReceiver(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.put(darkReceiver, darkReceiver);
        darkReceiver.onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void applyDark(DarkIconDispatcher.DarkReceiver darkReceiver) {
        ((DarkIconDispatcher.DarkReceiver) this.mReceivers.get(darkReceiver)).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }

    @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
    public final void applyDarkIntensity(float f) {
        int i;
        this.mDarkIntensity = f;
        ArgbEvaluator argbEvaluator = ArgbEvaluator.getInstance();
        Integer valueOf = Integer.valueOf(this.mLightModeIconColorSingleTone);
        SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper = this.mSamsungStatusBarGrayIconHelper;
        if (samsungStatusBarGrayIconHelper.isGrayAppearance) {
            try {
                i = Settings.System.getInt(samsungStatusBarGrayIconHelper.context.getContentResolver(), "need_dark_statusbar");
            } catch (Exception unused) {
                i = 1;
            }
        } else {
            i = this.mDarkModeIconColorSingleTone;
        }
        this.mIconTint = ((Integer) argbEvaluator.evaluate(f, valueOf, Integer.valueOf(i))).intValue();
        int i2 = 0;
        while (true) {
            ArrayMap arrayMap = this.mReceivers;
            if (i2 >= arrayMap.size()) {
                return;
            }
            ((DarkIconDispatcher.DarkReceiver) arrayMap.valueAt(i2)).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
            i2++;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "DarkIconDispatcher: ", "  mIconTint: 0x");
        m75m.append(Integer.toHexString(this.mIconTint));
        printWriter.println(m75m.toString());
        printWriter.println("  mDarkIntensity: " + this.mDarkIntensity + "f");
        StringBuilder sb = new StringBuilder("  mTintAreas: ");
        sb.append(this.mTintAreas);
        printWriter.println(sb.toString());
    }

    @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
    public final int getTintAnimationDuration() {
        return 120;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void removeDarkReceiver(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.remove(darkReceiver);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void setIconsDarkArea(ArrayList arrayList) {
        ArrayList<Rect> arrayList2 = this.mTintAreas;
        if (arrayList == null && arrayList2.isEmpty()) {
            return;
        }
        arrayList2.clear();
        if (arrayList != null) {
            arrayList2.addAll(arrayList);
        }
        int i = 0;
        while (true) {
            ArrayMap arrayMap = this.mReceivers;
            if (i >= arrayMap.size()) {
                return;
            }
            ((DarkIconDispatcher.DarkReceiver) arrayMap.valueAt(i)).onDarkChanged(arrayList2, this.mDarkIntensity, this.mIconTint);
            i++;
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void removeDarkReceiver(ImageView imageView) {
        this.mReceivers.remove(imageView);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void addDarkReceiver(final ImageView imageView) {
        DarkIconDispatcher.DarkReceiver darkReceiver = new DarkIconDispatcher.DarkReceiver() { // from class: com.android.systemui.statusbar.phone.DarkIconDispatcherImpl$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
            public final void onDarkChanged(ArrayList arrayList, float f, int i) {
                DarkIconDispatcherImpl darkIconDispatcherImpl = DarkIconDispatcherImpl.this;
                ArrayList arrayList2 = darkIconDispatcherImpl.mTintAreas;
                int i2 = darkIconDispatcherImpl.mIconTint;
                ImageView imageView2 = imageView;
                imageView2.setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(arrayList2, imageView2, i2)));
            }
        };
        this.mReceivers.put(imageView, darkReceiver);
        darkReceiver.onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }
}
