package com.android.systemui.statusbar.phone;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.ArrayMap;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DarkIconDispatcherImpl implements LightBarTransitionsController.DarkIntensityApplier, DarkIconDispatcher, Dumpable {
    public float mDarkIntensity;
    public final int mDarkModeIconColorSingleTone;
    public final DumpManager mDumpManager;
    public final String mDumpableName;
    public final int mLightModeIconColorSingleTone;
    public final SamsungStatusBarGrayIconHelper mSamsungStatusBarGrayIconHelper;
    public final LightBarTransitionsController mTransitionsController;
    public final ArrayList mTintAreas = new ArrayList();
    public final ArrayMap mReceivers = new ArrayMap();
    public int mIconTint = -301989889;
    public int mContrastTint = -16777216;
    public final int mDarkModeContrastColor = -301989889;
    public final int mLightModeContrastColor = -16777216;
    public final StateFlowImpl mDarkChangeFlow = StateFlowKt.MutableStateFlow(SysuiDarkIconDispatcher$DarkChange.EMPTY);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        DarkIconDispatcherImpl create(int i, Context context);
    }

    public DarkIconDispatcherImpl(int i, Context context, LightBarTransitionsController.Factory factory, DumpManager dumpManager, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        this.mDumpManager = dumpManager;
        this.mDarkModeIconColorSingleTone = context.getColor(R.color.dark_mode_icon_color_single_tone);
        this.mLightModeIconColorSingleTone = context.getColor(R.color.light_mode_icon_color_single_tone);
        this.mTransitionsController = factory.create(this);
        String str = getClass().getSimpleName() + (i == 0 ? "" : String.valueOf(i));
        this.mDumpableName = str;
        dumpManager.registerNormalDumpable(str, this);
        this.mSamsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void addDarkReceiver(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.put(darkReceiver, darkReceiver);
        darkReceiver.onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
        darkReceiver.onDarkChangedWithContrast(this.mTintAreas, this.mIconTint, this.mContrastTint);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void applyDark(DarkIconDispatcher.DarkReceiver darkReceiver) {
        ((DarkIconDispatcher.DarkReceiver) this.mReceivers.get(darkReceiver)).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
        ((DarkIconDispatcher.DarkReceiver) this.mReceivers.get(darkReceiver)).onDarkChangedWithContrast(this.mTintAreas, this.mIconTint, this.mContrastTint);
    }

    @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
    public final void applyDarkIntensity(float f) {
        this.mDarkIntensity = f;
        ArgbEvaluator argbEvaluator = ArgbEvaluator.getInstance();
        ArgbEvaluator argbEvaluator2 = ArgbEvaluator.getInstance();
        Integer valueOf = Integer.valueOf(this.mLightModeIconColorSingleTone);
        this.mSamsungStatusBarGrayIconHelper.getClass();
        this.mIconTint = ((Integer) argbEvaluator2.evaluate(f, valueOf, Integer.valueOf(this.mDarkModeIconColorSingleTone))).intValue();
        this.mContrastTint = ((Integer) argbEvaluator.evaluate(f, Integer.valueOf(this.mLightModeContrastColor), Integer.valueOf(this.mDarkModeContrastColor))).intValue();
        applyIconTint();
    }

    public final void applyIconTint() {
        this.mDarkChangeFlow.updateState(null, new SysuiDarkIconDispatcher$DarkChange(this.mTintAreas, this.mDarkIntensity, this.mIconTint));
        for (int i = 0; i < this.mReceivers.size(); i++) {
            ((DarkIconDispatcher.DarkReceiver) this.mReceivers.valueAt(i)).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
            ((DarkIconDispatcher.DarkReceiver) this.mReceivers.valueAt(i)).onDarkChangedWithContrast(this.mTintAreas, this.mIconTint, this.mContrastTint);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "DarkIconDispatcher: ", "  mIconTint: 0x");
        m.append(Integer.toHexString(this.mIconTint));
        printWriter.println(m.toString());
        printWriter.println("  mContrastTint: 0x" + Integer.toHexString(this.mContrastTint));
        printWriter.println("  mDarkModeIconColorSingleTone: 0x" + Integer.toHexString(this.mDarkModeIconColorSingleTone));
        printWriter.println("  mLightModeIconColorSingleTone: 0x" + Integer.toHexString(this.mLightModeIconColorSingleTone));
        printWriter.println("  mDarkModeContrastColor: 0x" + Integer.toHexString(this.mDarkModeContrastColor));
        printWriter.println("  mLightModeContrastColor: 0x" + Integer.toHexString(this.mLightModeContrastColor));
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
        if (arrayList == null && this.mTintAreas.isEmpty()) {
            return;
        }
        this.mTintAreas.clear();
        if (arrayList != null) {
            this.mTintAreas.addAll(arrayList);
        }
        applyIconTint();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher
    public final void stop() {
        this.mDumpManager.unregisterDumpable(this.mDumpableName);
    }
}
