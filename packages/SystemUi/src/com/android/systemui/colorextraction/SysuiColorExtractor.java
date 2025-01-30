package com.android.systemui.colorextraction;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SysuiColorExtractor extends ColorExtractor implements Dumpable, ConfigurationController.ConfigurationListener {
    public final ColorExtractor.GradientColors mNeutralColorsLock;
    public final Tonal mTonal;

    public SysuiColorExtractor(Context context, ConfigurationController configurationController, DumpManager dumpManager) {
        this(context, new Tonal(context), configurationController, (WallpaperManager) context.getSystemService(WallpaperManager.class), dumpManager, false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysuiColorExtractor:");
        printWriter.println("  Current wallpaper colors:");
        printWriter.println("    system: " + ((ColorExtractor) this).mSystemColors);
        printWriter.println("    lock: " + ((ColorExtractor) this).mLockColors);
        ColorExtractor.GradientColors[] gradientColorsArr = (ColorExtractor.GradientColors[]) ((ColorExtractor) this).mGradientColors.get(1);
        ColorExtractor.GradientColors[] gradientColorsArr2 = (ColorExtractor.GradientColors[]) ((ColorExtractor) this).mGradientColors.get(2);
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "  Gradients:", "    system: ");
        m75m.append(Arrays.toString(gradientColorsArr));
        printWriter.println(m75m.toString());
        printWriter.println("    lock: " + Arrays.toString(gradientColorsArr2));
        printWriter.println("  Neutral colors: " + this.mNeutralColorsLock);
        printWriter.println("  Has media backdrop: false");
    }

    public final void extractWallpaperColors() {
        ColorExtractor.GradientColors gradientColors;
        super.extractWallpaperColors();
        Tonal tonal = this.mTonal;
        if (tonal == null || (gradientColors = this.mNeutralColorsLock) == null) {
            return;
        }
        WallpaperColors wallpaperColors = ((ColorExtractor) this).mLockColors;
        if (wallpaperColors == null) {
            wallpaperColors = ((ColorExtractor) this).mSystemColors;
        }
        tonal.applyFallback(wallpaperColors, gradientColors);
    }

    public final ColorExtractor.GradientColors getColors(int i, int i2) {
        return super.getColors(i, i2);
    }

    public final void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        if (i2 != KeyguardUpdateMonitor.getCurrentUser()) {
            return;
        }
        if ((i & 2) != 0) {
            this.mTonal.applyFallback(wallpaperColors, this.mNeutralColorsLock);
        }
        onColorsChanged(wallpaperColors, i);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        extractWallpaperColors();
        triggerColorsChanged(3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SysuiColorExtractor(Context context, ExtractionType extractionType, ConfigurationController configurationController, WallpaperManager wallpaperManager, DumpManager dumpManager, boolean z) {
        super(context, extractionType, z, wallpaperManager);
        this.mTonal = extractionType instanceof Tonal ? (Tonal) extractionType : new Tonal(context);
        this.mNeutralColorsLock = new ColorExtractor.GradientColors();
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "SysuiColorExtractor", this);
        new ColorExtractor.GradientColors().setMainColor(EmergencyPhoneWidget.BG_COLOR);
        if (wallpaperManager.isWallpaperSupported()) {
            wallpaperManager.removeOnColorsChangedListener(this);
            wallpaperManager.addOnColorsChangedListener(this, null, -1);
        }
    }
}
