package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Trace;
import android.util.LruCache;
import android.util.Pair;
import android.view.DisplayCutout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.leak.RotationUtils;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarContentInsetsProvider implements CallbackController, ConfigurationController.ConfigurationListener, Dumpable {
    public final ConfigurationController configurationController;
    public final Context context;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final LruCache insetsCache = new LruCache(16);
    public final Set listeners = new LinkedHashSet();
    public final Lazy isPrivacyDotEnabled$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.PUBLICATION, new Function0() { // from class: com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider$isPrivacyDotEnabled$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(StatusBarContentInsetsProvider.this.context.getResources().getBoolean(R.bool.config_enablePrivacyDot));
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public StatusBarContentInsetsProvider(Context context, ConfigurationController configurationController, DumpManager dumpManager, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.configurationController = configurationController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.indicatorScaleGardener = indicatorScaleGardener;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        DumpManager.registerDumpable$default(dumpManager, "StatusBarInsetsProvider", this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.listeners.add((StatusBarContentInsetsChangedListener) obj);
    }

    public final boolean currentRotationHasCornerCutout() {
        Context context = this.context;
        DisplayCutout cutout = context.getDisplay().getCutout();
        if (cutout == null) {
            return false;
        }
        Rect boundingRectTop = cutout.getBoundingRectTop();
        Point point = new Point();
        context.getDisplay().getRealSize(point);
        return boundingRectTop.left <= 0 || boundingRectTop.right >= point.x;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        LruCache lruCache = this.insetsCache;
        for (Map.Entry entry : lruCache.snapshot().entrySet()) {
            printWriter.println(((CacheKey) entry.getKey()) + " -> " + ((Rect) entry.getValue()));
        }
        printWriter.println(lruCache);
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0188, code lost:
    
        r10 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0186, code lost:
    
        if (r8.right >= r13) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x014f, code lost:
    
        r14 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect getAndSetCalculatedAreaForRotation(int i, DisplayCutout displayCutout, Resources resources, CacheKey cacheKey) {
        int max;
        Rect rect;
        int i2;
        Rect rect2;
        boolean z;
        boolean z2;
        Context context = this.context;
        int exactRotation = RotationUtils.getExactRotation(context);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
        Lazy lazy = this.isPrivacyDotEnabled$delegate;
        int dimensionPixelSize2 = ((Boolean) lazy.getValue()).booleanValue() ? resources.getDimensionPixelSize(R.dimen.ongoing_appops_dot_min_padding) : 0;
        int dimensionPixelSize3 = ((Boolean) lazy.getValue()).booleanValue() ? resources.getDimensionPixelSize(R.dimen.ongoing_appops_dot_diameter) : 0;
        ConfigurationControllerImpl configurationControllerImpl = (ConfigurationControllerImpl) this.configurationController;
        if (configurationControllerImpl.isLayoutRtl()) {
            int max2 = Math.max(dimensionPixelSize2, dimensionPixelSize);
            max = dimensionPixelSize;
            dimensionPixelSize = max2;
        } else {
            max = Math.max(dimensionPixelSize2, dimensionPixelSize);
        }
        Rect maxBounds = context.getResources().getConfiguration().windowConfiguration.getMaxBounds();
        int statusBarHeightForRotation = SystemBarUtils.getStatusBarHeightForRotation(context, i);
        boolean isLayoutRtl = configurationControllerImpl.isLayoutRtl();
        Rect rect3 = (exactRotation == 0 || exactRotation == 2) ? maxBounds : new Rect(0, 0, maxBounds.bottom, maxBounds.right);
        int i3 = rect3.right;
        int i4 = rect3.bottom;
        int width = maxBounds.width();
        int height = maxBounds.height();
        if (i == 1 || i == 3) {
            i3 = i4;
        }
        List<Rect> boundingRects = displayCutout != null ? displayCutout.getBoundingRects() : null;
        if (boundingRects == null || boundingRects.isEmpty()) {
            rect = new Rect(dimensionPixelSize, 0, i3 - max, statusBarHeightForRotation);
        } else {
            int i5 = exactRotation - i;
            if (i5 < 0) {
                i5 += 4;
            }
            Pair pair = new Pair(Integer.valueOf(width), Integer.valueOf(height));
            Integer num = (Integer) pair.first;
            Integer num2 = (Integer) pair.second;
            if (i5 == 0) {
                i2 = 0;
                rect2 = new Rect(0, 0, num.intValue(), statusBarHeightForRotation);
            } else if (i5 == 1) {
                i2 = 0;
                rect2 = new Rect(0, 0, statusBarHeightForRotation, num2.intValue());
            } else if (i5 != 2) {
                i2 = 0;
                rect2 = new Rect(num.intValue() - statusBarHeightForRotation, 0, num.intValue(), num2.intValue());
            } else {
                i2 = 0;
                rect2 = new Rect(0, num2.intValue() - statusBarHeightForRotation, num.intValue(), num2.intValue());
            }
            Iterator<Rect> it = boundingRects.iterator();
            while (it.hasNext()) {
                Rect next = it.next();
                if (width < height ? rect2.intersects(i2, next.top, width, next.bottom) : width > height ? rect2.intersects(next.left, i2, next.right, height) : false) {
                    boolean z3 = i5 != 0 ? false : false;
                    if (z3) {
                        int width2 = (i5 == 0 || i5 == 2) ? next.width() : next.height();
                        if (isLayoutRtl) {
                            width2 += dimensionPixelSize3;
                        }
                        dimensionPixelSize = Math.max(width2, dimensionPixelSize);
                    } else {
                        if (i5 != 0) {
                            z = true;
                            z2 = i5 == 1 ? false : false;
                        } else {
                            z = true;
                        }
                        if (z2) {
                            int width3 = (i5 == 0 || i5 == 2) ? next.width() : next.height();
                            if (!isLayoutRtl) {
                                width3 += dimensionPixelSize3;
                            }
                            max = Math.max(max, width3);
                        }
                        i2 = 0;
                    }
                }
                i2 = 0;
            }
            rect = new Rect(dimensionPixelSize, 0, i3 - max, statusBarHeightForRotation);
        }
        this.insetsCache.put(cacheKey, rect);
        return rect;
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
        Context context = this.context;
        DisplayCutout cutout = context.getDisplay().getCutout();
        CacheKey cacheKey = getCacheKey(i, cutout);
        Rect rect = (Rect) this.insetsCache.get(cacheKey);
        return rect == null ? getAndSetCalculatedAreaForRotation(i, cutout, RotationUtils.getResourcesForRotation(i, context), cacheKey) : rect;
    }

    public final Pair getStatusBarContentInsetsForCurrentRotation() {
        Context context = this.context;
        int exactRotation = RotationUtils.getExactRotation(context);
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        LruCache lruCache = this.insetsCache;
        if (!isTagEnabled) {
            DisplayCutout cutout = context.getDisplay().getCutout();
            CacheKey cacheKey = getCacheKey(exactRotation, cutout);
            Rect maxBounds = context.getResources().getConfiguration().windowConfiguration.getMaxBounds();
            Point point = new Point(maxBounds.width(), maxBounds.height());
            int exactRotation2 = RotationUtils.getExactRotation(context);
            if (exactRotation2 != 0 && exactRotation2 != 2) {
                int i = point.y;
                point.y = point.x;
                point.x = i;
            }
            int i2 = (exactRotation == 0 || exactRotation == 2) ? point.x : point.y;
            Rect rect = (Rect) lruCache.get(cacheKey);
            if (rect == null) {
                rect = getAndSetCalculatedAreaForRotation(exactRotation, cutout, RotationUtils.getResourcesForRotation(exactRotation, context), cacheKey);
            }
            return new Pair(Integer.valueOf(rect.left), Integer.valueOf(i2 - rect.right));
        }
        Trace.traceBegin(4096L, "StatusBarContentInsetsProvider.getStatusBarContentInsetsForRotation");
        try {
            DisplayCutout cutout2 = context.getDisplay().getCutout();
            CacheKey cacheKey2 = getCacheKey(exactRotation, cutout2);
            Rect maxBounds2 = context.getResources().getConfiguration().windowConfiguration.getMaxBounds();
            Point point2 = new Point(maxBounds2.width(), maxBounds2.height());
            int exactRotation3 = RotationUtils.getExactRotation(context);
            if (exactRotation3 != 0 && exactRotation3 != 2) {
                int i3 = point2.y;
                point2.y = point2.x;
                point2.x = i3;
            }
            int i4 = (exactRotation == 0 || exactRotation == 2) ? point2.x : point2.y;
            Rect rect2 = (Rect) lruCache.get(cacheKey2);
            if (rect2 == null) {
                rect2 = getAndSetCalculatedAreaForRotation(exactRotation, cutout2, RotationUtils.getResourcesForRotation(exactRotation, context), cacheKey2);
            }
            return new Pair(Integer.valueOf(rect2.left), Integer.valueOf(i4 - rect2.right));
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    public final int getStatusBarPaddingTop() {
        return this.indicatorGardenPresenter.gardenAlgorithm.calculateCameraTopMargin() + MathKt__MathJVMKt.roundToInt(r0.getResources().getDimensionPixelSize(R.dimen.privacy_dot_padding_top) * this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        this.insetsCache.evictAll();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((StatusBarContentInsetsChangedListener) it.next()).onStatusBarContentInsetsChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onMaxBoundsChanged() {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((StatusBarContentInsetsChangedListener) it.next()).onStatusBarContentInsetsChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        this.insetsCache.evictAll();
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((StatusBarContentInsetsChangedListener) it.next()).onStatusBarContentInsetsChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.listeners.remove((StatusBarContentInsetsChangedListener) obj);
    }
}
