package com.android.systemui.statusbar.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.LruCache;
import android.view.Display;
import android.view.DisplayCutout;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.SysUICutoutInformation;
import com.android.systemui.SysUICutoutProvider;
import com.android.systemui.SysUICutoutProviderImpl;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.leak.RotationUtils;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarContentInsetsProviderImpl implements StatusBarContentInsetsProvider, ConfigurationController.ConfigurationListener, Dumpable {
    public final String commandName;
    public final CommandRegistry commandRegistry;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DumpManager dumpManager;
    public final String dumpableName;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final Map marginBottomOverrides;
    public final SysUICutoutProvider sysUICutoutProvider;
    public final LruCache insetsCache = new LruCache(16);
    public final CopyOnWriteArraySet listeners = new CopyOnWriteArraySet();
    public final Lazy isPrivacyDotEnabled$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, new StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0(this, 0));

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CacheKey {
        public final DisplayCutout displayCutout;
        public final Rect displaySize;
        public final int rotation;

        public CacheKey(int i, Rect rect, DisplayCutout displayCutout) {
            this.rotation = i;
            this.displaySize = rect;
            this.displayCutout = displayCutout;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) obj;
            return this.rotation == cacheKey.rotation && Intrinsics.areEqual(this.displaySize, cacheKey.displaySize) && Intrinsics.areEqual(this.displayCutout, cacheKey.displayCutout);
        }

        public final int hashCode() {
            int hashCode = (this.displaySize.hashCode() + (Integer.hashCode(this.rotation) * 31)) * 31;
            DisplayCutout displayCutout = this.displayCutout;
            return hashCode + (displayCutout == null ? 0 : displayCutout.hashCode());
        }

        public final String toString() {
            return "CacheKey(rotation=" + this.rotation + ", displaySize=" + this.displaySize + ", displayCutout=" + this.displayCutout + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        StatusBarContentInsetsProviderImpl create(Context context, ConfigurationController configurationController, SysUICutoutProvider sysUICutoutProvider);
    }

    public StatusBarContentInsetsProviderImpl(Context context, ConfigurationController configurationController, DumpManager dumpManager, CommandRegistry commandRegistry, SysUICutoutProvider sysUICutoutProvider, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.configurationController = configurationController;
        this.dumpManager = dumpManager;
        this.commandRegistry = commandRegistry;
        this.sysUICutoutProvider = sysUICutoutProvider;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        String valueOf = context.getDisplayId() == 0 ? "" : String.valueOf(context.getDisplayId());
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("StatusBarInsetsProvider", valueOf);
        this.dumpableName = m;
        String m2 = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("status-bar-insets", valueOf);
        this.commandName = m2;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        dumpManager.registerNormalDumpable(m, this);
        commandRegistry.registerCommand(m2, new StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0(this, 1));
        this.marginBottomOverrides = new LinkedHashMap();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.listeners.add((StatusBarContentInsetsChangedListener) obj);
    }

    public final boolean currentRotationHasCornerCutout() {
        Display display = this.context.getDisplay();
        if (display == null) {
            throw new IllegalStateException("Required value was null.");
        }
        DisplayCutout cutout = display.getCutout();
        if (cutout == null) {
            return false;
        }
        Rect boundingRectTop = cutout.getBoundingRectTop();
        Point point = new Point();
        Display display2 = this.context.getDisplay();
        if (display2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        display2.getRealSize(point);
        return boundingRectTop.left <= 0 || boundingRectTop.right >= point.x;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        for (Map.Entry entry : this.insetsCache.snapshot().entrySet()) {
            printWriter.println(((CacheKey) entry.getKey()) + " -> " + ((Rect) entry.getValue()));
        }
        printWriter.println(this.insetsCache);
        printWriter.println("Bottom margin overrides: " + this.marginBottomOverrides);
    }

    public final Rect getAndSetCalculatedAreaForRotation(int i, SysUICutoutInformation sysUICutoutInformation, Resources resources, CacheKey cacheKey) {
        int i2;
        int max;
        int i3;
        int dimensionPixelSize;
        int exactRotation = RotationUtils.getExactRotation(this.context);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
        Lazy lazy = this.isPrivacyDotEnabled$delegate;
        int dimensionPixelSize3 = ((Boolean) lazy.getValue()).booleanValue() ? resources.getDimensionPixelSize(R.dimen.ongoing_appops_dot_min_padding) : 0;
        int dimensionPixelSize4 = ((Boolean) lazy.getValue()).booleanValue() ? resources.getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) : 0;
        ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.configurationController;
        if (configurationControllerImpl.isLayoutRtl()) {
            max = dimensionPixelSize2;
            i2 = Math.max(dimensionPixelSize3, dimensionPixelSize2);
        } else {
            i2 = dimensionPixelSize2;
            max = Math.max(dimensionPixelSize3, dimensionPixelSize2);
        }
        Integer num = (Integer) ((LinkedHashMap) this.marginBottomOverrides).get(Integer.valueOf(i));
        if (num != null) {
            dimensionPixelSize = num.intValue();
        } else {
            if (i == 0) {
                i3 = R.dimen.status_bar_bottom_aligned_margin_rotation_0;
            } else if (i == 1) {
                i3 = R.dimen.status_bar_bottom_aligned_margin_rotation_90;
            } else if (i == 2) {
                i3 = R.dimen.status_bar_bottom_aligned_margin_rotation_180;
            } else {
                if (i != 3) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown rotation: "));
                }
                i3 = R.dimen.status_bar_bottom_aligned_margin_rotation_270;
            }
            dimensionPixelSize = resources.getDimensionPixelSize(i3);
        }
        Rect calculateInsetsForRotationWithRotatedResources = StatusBarContentInsetsProviderKt.calculateInsetsForRotationWithRotatedResources(exactRotation, i, sysUICutoutInformation, this.context.getResources().getConfiguration().windowConfiguration.getMaxBounds(), SystemBarUtils.getStatusBarHeightForRotation(this.context, i), i2, max, configurationControllerImpl.isLayoutRtl(), dimensionPixelSize4, dimensionPixelSize, resources.getDimensionPixelSize(R.dimen.status_bar_icon_size_sp));
        this.insetsCache.put(cacheKey, calculateInsetsForRotationWithRotatedResources);
        return calculateInsetsForRotationWithRotatedResources;
    }

    public final Rect getBoundingRectForPrivacyChipForRotation(int i, DisplayCutout displayCutout) {
        Rect rect = (Rect) this.insetsCache.get(getCacheKey(i, displayCutout));
        if (rect == null) {
            rect = getStatusBarContentAreaForRotation(i);
        }
        Resources resourcesForRotation = RotationUtils.getResourcesForRotation(i, this.context);
        return StatusBarContentInsetsProviderKt.getPrivacyChipBoundingRectForInsets(rect, resourcesForRotation.getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter), resourcesForRotation.getDimensionPixelSize(R.dimen.ongoing_appops_chip_max_width), ((ConfigurationControllerImpl) this.configurationController).isLayoutRtl());
    }

    public final CacheKey getCacheKey(int i, DisplayCutout displayCutout) {
        return new CacheKey(i, new Rect(this.context.getResources().getConfiguration().windowConfiguration.getMaxBounds()), displayCutout);
    }

    public final Rect getStatusBarContentAreaForRotation(int i) {
        SysUICutoutInformation cutoutInfoForCurrentDisplayAndRotation = ((SysUICutoutProviderImpl) this.sysUICutoutProvider).cutoutInfoForCurrentDisplayAndRotation();
        CacheKey cacheKey = getCacheKey(i, cutoutInfoForCurrentDisplayAndRotation != null ? cutoutInfoForCurrentDisplayAndRotation.cutout : null);
        Rect rect = (Rect) this.insetsCache.get(cacheKey);
        return rect == null ? getAndSetCalculatedAreaForRotation(i, cutoutInfoForCurrentDisplayAndRotation, RotationUtils.getResourcesForRotation(i, this.context), cacheKey) : rect;
    }

    public final Insets getStatusBarContentInsetsForCurrentRotation() {
        int exactRotation = RotationUtils.getExactRotation(this.context);
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("StatusBarContentInsetsProvider.getStatusBarContentInsetsForRotation");
        }
        try {
            SysUICutoutInformation cutoutInfoForCurrentDisplayAndRotation = ((SysUICutoutProviderImpl) this.sysUICutoutProvider).cutoutInfoForCurrentDisplayAndRotation();
            CacheKey cacheKey = getCacheKey(exactRotation, cutoutInfoForCurrentDisplayAndRotation != null ? cutoutInfoForCurrentDisplayAndRotation.cutout : null);
            Rect maxBounds = this.context.getResources().getConfiguration().windowConfiguration.getMaxBounds();
            Point point = new Point(maxBounds.width(), maxBounds.height());
            int exactRotation2 = RotationUtils.getExactRotation(this.context);
            if (exactRotation2 != 0 && exactRotation2 != 2) {
                int i = point.y;
                point.y = point.x;
                point.x = i;
            }
            int i2 = (exactRotation == 0 || exactRotation == 2) ? point.x : point.y;
            Rect rect = (Rect) this.insetsCache.get(cacheKey);
            if (rect == null) {
                rect = getAndSetCalculatedAreaForRotation(exactRotation, cutoutInfoForCurrentDisplayAndRotation, RotationUtils.getResourcesForRotation(exactRotation, this.context), cacheKey);
            }
            Insets of = Insets.of(rect.left, rect.top, i2 - rect.right, 0);
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return of;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final int getStatusBarPaddingTop() {
        int roundToInt = MathKt__MathJVMKt.roundToInt(this.context.getResources().getDimensionPixelSize(R.dimen.privacy_dot_padding_top) * this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio);
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.samsung_status_bar_battery_icon_height);
        int dimensionPixelSize2 = this.context.getResources().getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter);
        IndicatorGardenPresenter indicatorGardenPresenter = this.indicatorGardenPresenter;
        return (MathKt__MathJVMKt.roundToInt(((indicatorGardenPresenter.gardenAlgorithm.calculateCameraTopMargin() + dimensionPixelSize) - indicatorGardenPresenter.gardenAlgorithm.calculateCameraBottomMargin()) * 0.5d) - dimensionPixelSize2) + roundToInt;
    }

    public final void notifyInsetsChanged() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((StatusBarContentInsetsChangedListener) it.next()).onStatusBarContentInsetsChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        this.insetsCache.evictAll();
        notifyInsetsChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onMaxBoundsChanged() {
        notifyInsetsChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        this.insetsCache.evictAll();
        notifyInsetsChanged();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.listeners.remove((StatusBarContentInsetsChangedListener) obj);
    }
}
