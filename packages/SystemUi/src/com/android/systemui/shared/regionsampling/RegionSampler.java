package com.android.systemui.shared.regionsampling;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.View;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RegionSampler implements WallpaperManager.LocalWallpaperColorConsumer {
    public final Executor bgExecutor;
    public int darkForegroundColor;
    public final Point displaySize;
    public WallpaperColors initialSampling;
    public final boolean isLockscreen;
    public final RegionSampler$layoutChangedListener$1 layoutChangedListener;
    public int lightForegroundColor;
    public RegionDarkness regionDarkness;
    public final boolean regionSamplingEnabled;
    public final View sampledView;
    public final Rect samplingBounds;
    public final int[] tmpScreenLocation;
    public final Function0 updateForegroundColor;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public RegionSampler(View view, Executor executor, Executor executor2, boolean z, Function0 function0) {
        this(view, executor, executor2, z, false, null, function0, 48, null);
    }

    public final RectF calculateScreenLocation(View view) {
        if (!view.isLaidOut()) {
            return null;
        }
        int[] iArr = this.tmpScreenLocation;
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        Rect rect = this.samplingBounds;
        rect.left = i;
        rect.top = i2;
        rect.right = view.getWidth() + i;
        this.samplingBounds.bottom = view.getHeight() + i2;
        return new RectF(this.samplingBounds);
    }

    public final RectF convertBounds(RectF rectF) {
        Point point = this.displaySize;
        int i = point.x;
        int i2 = point.y;
        RectF rectF2 = new RectF();
        float f = i;
        float f2 = 0;
        float f3 = 1;
        rectF2.left = ((rectF.left / f) + f2) / f3;
        rectF2.right = ((rectF.right / f) + f2) / f3;
        float f4 = i2;
        rectF2.top = rectF.top / f4;
        rectF2.bottom = rectF.bottom / f4;
        return rectF2;
    }

    public final void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) {
        boolean z = false;
        if (wallpaperColors != null && (wallpaperColors.getColorHints() & 1) == 1) {
            z = true;
        }
        this.regionDarkness = z ^ true ? RegionDarkness.DARK : RegionDarkness.LIGHT;
        this.updateForegroundColor.invoke();
    }

    public RegionSampler(View view, Executor executor, Executor executor2, boolean z, boolean z2, Function0 function0) {
        this(view, executor, executor2, z, z2, null, function0, 32, null);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.shared.regionsampling.RegionSampler$layoutChangedListener$1] */
    public RegionSampler(View view, Executor executor, Executor executor2, boolean z, boolean z2, WallpaperManager wallpaperManager, Function0 function0) {
        Display display;
        this.sampledView = view;
        this.bgExecutor = executor2;
        this.regionSamplingEnabled = z;
        this.isLockscreen = z2;
        this.wallpaperManager = wallpaperManager;
        this.updateForegroundColor = function0;
        this.regionDarkness = RegionDarkness.DEFAULT;
        this.samplingBounds = new Rect();
        this.tmpScreenLocation = new int[2];
        this.lightForegroundColor = -1;
        this.darkForegroundColor = EmergencyPhoneWidget.BG_COLOR;
        Point point = new Point();
        this.displaySize = point;
        this.layoutChangedListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.shared.regionsampling.RegionSampler$layoutChangedListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (RegionSampler.this.sampledView.getLocationOnScreen()[0] >= 0) {
                    if (RegionSampler.this.sampledView.getLocationOnScreen()[1] >= 0 && !Intrinsics.areEqual(new Rect(i, i2, i3, i4), new Rect(i5, i6, i7, i8))) {
                        RegionSampler regionSampler = RegionSampler.this;
                        WallpaperManager wallpaperManager2 = regionSampler.wallpaperManager;
                        if (wallpaperManager2 != null) {
                            wallpaperManager2.removeOnColorsChangedListener(regionSampler);
                        }
                        regionSampler.sampledView.removeOnLayoutChangeListener(regionSampler.layoutChangedListener);
                        final RegionSampler regionSampler2 = RegionSampler.this;
                        if (regionSampler2.regionSamplingEnabled) {
                            regionSampler2.sampledView.addOnLayoutChangeListener(regionSampler2.layoutChangedListener);
                            RectF calculateScreenLocation = regionSampler2.calculateScreenLocation(regionSampler2.sampledView);
                            if (calculateScreenLocation == null || calculateScreenLocation.isEmpty()) {
                                return;
                            }
                            final RectF convertBounds = regionSampler2.convertBounds(calculateScreenLocation);
                            if (convertBounds.left < 0.0d || convertBounds.right > 1.0d || convertBounds.top < 0.0d || convertBounds.bottom > 1.0d) {
                                return;
                            }
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(convertBounds);
                            WallpaperManager wallpaperManager3 = regionSampler2.wallpaperManager;
                            if (wallpaperManager3 != null) {
                                wallpaperManager3.addOnColorsChangedListener(regionSampler2, arrayList, regionSampler2.isLockscreen ? 2 : 1);
                            }
                            Executor executor3 = regionSampler2.bgExecutor;
                            if (executor3 != null) {
                                executor3.execute(new Runnable() { // from class: com.android.systemui.shared.regionsampling.RegionSampler$startRegionSampler$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        WallpaperColors wallpaperColors;
                                        RegionSampler regionSampler3 = RegionSampler.this;
                                        WallpaperManager wallpaperManager4 = regionSampler3.wallpaperManager;
                                        if (wallpaperManager4 != null) {
                                            wallpaperColors = wallpaperManager4.getWallpaperColors(regionSampler3.isLockscreen ? 2 : 1);
                                        } else {
                                            wallpaperColors = null;
                                        }
                                        regionSampler3.initialSampling = wallpaperColors;
                                        RegionSampler regionSampler4 = RegionSampler.this;
                                        regionSampler4.onColorsChanged(convertBounds, regionSampler4.initialSampling);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        };
        Context context = view.getContext();
        if (context == null || (display = context.getDisplay()) == null) {
            return;
        }
        display.getSize(point);
    }

    public /* synthetic */ RegionSampler(View view, Executor executor, Executor executor2, boolean z, boolean z2, WallpaperManager wallpaperManager, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, executor, executor2, z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? WallpaperManager.getInstance(view.getContext()) : wallpaperManager, function0);
    }

    public static /* synthetic */ void getDisplaySize$annotations() {
    }
}
