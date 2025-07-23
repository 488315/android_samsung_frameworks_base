package androidx.window.embedding;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.WindowMetrics;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.Bounds;
import androidx.window.embedding.EmbeddingBounds;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.core.util.function.Function;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.ActivityStackAttributes;
import androidx.window.extensions.embedding.ActivityStackAttributesCalculatorParams;
import androidx.window.extensions.embedding.WindowAttributes;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.HardwareFoldingFeature;
import androidx.window.layout.WindowMetricsCalculator;
import androidx.window.layout.adapter.extensions.ExtensionsWindowLayoutInfoAdapter;
import androidx.window.layout.util.DensityCompatHelper;
import androidx.window.layout.util.DensityCompatHelperApi34Impl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class OverlayControllerImpl {
    public final EmbeddingAdapter adapter;
    public final ActivityEmbeddingComponent embeddingExtension;
    public final ReentrantLock globalLock = new ReentrantLock();
    public final Map overlayTagToDefaultAttributesMap = new ArrayMap();
    public final ArrayMap overlayTagToCurrentAttributesMap = new ArrayMap();
    public final ArrayMap overlayTagToContainerMap = new ArrayMap();

    public OverlayControllerImpl(ActivityEmbeddingComponent activityEmbeddingComponent, EmbeddingAdapter embeddingAdapter) {
        this.embeddingExtension = activityEmbeddingComponent;
        this.adapter = embeddingAdapter;
        new ArrayMap();
        WindowSdkExtensions.Companion.getClass();
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(6);
        activityEmbeddingComponent.setActivityStackAttributesCalculator(new Function() { // from class: androidx.window.embedding.OverlayControllerImpl$$ExternalSyntheticLambda0
            public final Object apply(Object obj) {
                OverlayControllerImpl overlayControllerImpl = OverlayControllerImpl.this;
                ActivityStackAttributesCalculatorParams activityStackAttributesCalculatorParams = (ActivityStackAttributesCalculatorParams) obj;
                ReentrantLock reentrantLock = overlayControllerImpl.globalLock;
                reentrantLock.lock();
                try {
                    androidx.window.extensions.embedding.ParentContainerInfo parentContainerInfo = activityStackAttributesCalculatorParams.getParentContainerInfo();
                    DensityCompatHelper.Companion.getClass();
                    DensityCompatHelperApi34Impl densityCompatHelperApi34Impl = DensityCompatHelperApi34Impl.INSTANCE;
                    parentContainerInfo.getConfiguration();
                    WindowMetrics windowMetrics = parentContainerInfo.getWindowMetrics();
                    densityCompatHelperApi34Impl.getClass();
                    windowMetrics.getDensity();
                    WindowMetricsCalculator.Companion companion = WindowMetricsCalculator.Companion;
                    WindowMetrics windowMetrics2 = parentContainerInfo.getWindowMetrics();
                    companion.getClass();
                    androidx.window.layout.WindowMetrics translateWindowMetrics$window_release = WindowMetricsCalculator.Companion.translateWindowMetrics$window_release(windowMetrics2);
                    String activityStackTag = activityStackAttributesCalculatorParams.getActivityStackTag();
                    ActivityEmbeddingOptionsImpl activityEmbeddingOptionsImpl = ActivityEmbeddingOptionsImpl.INSTANCE;
                    Bundle launchOptions = activityStackAttributesCalculatorParams.getLaunchOptions();
                    activityEmbeddingOptionsImpl.getClass();
                    Bundle bundle = launchOptions.getBundle("androidx.window.embedding.EmbeddingBounds");
                    OverlayAttributes overlayAttributes = null;
                    EmbeddingBounds embeddingBounds = bundle == null ? null : new EmbeddingBounds(new EmbeddingBounds.Alignment(bundle.getInt("androidx.window.embedding.EmbeddingBounds.alignment")), ActivityEmbeddingOptionsImpl.getDimension(bundle, "androidx.window.embedding.EmbeddingBounds.width"), ActivityEmbeddingOptionsImpl.getDimension(bundle, "androidx.window.embedding.EmbeddingBounds.height"));
                    if (embeddingBounds != null) {
                        overlayAttributes = new OverlayAttributes(embeddingBounds);
                    }
                    WindowMetricsCalculator.Companion.translateWindowMetrics$window_release(activityStackAttributesCalculatorParams.getParentContainerInfo().getWindowMetrics());
                    activityStackAttributesCalculatorParams.getParentContainerInfo().getConfiguration();
                    ExtensionsWindowLayoutInfoAdapter extensionsWindowLayoutInfoAdapter = ExtensionsWindowLayoutInfoAdapter.INSTANCE;
                    WindowLayoutInfo windowLayoutInfo = parentContainerInfo.getWindowLayoutInfo();
                    extensionsWindowLayoutInfoAdapter.getClass();
                    ExtensionsWindowLayoutInfoAdapter.translate$window_release(translateWindowMetrics$window_release, windowLayoutInfo);
                    OverlayAttributes overlayAttributes2 = (OverlayAttributes) ((ArrayMap) overlayControllerImpl.overlayTagToDefaultAttributesMap).get(activityStackTag);
                    if (overlayAttributes2 != null) {
                        overlayAttributes = overlayAttributes2;
                    } else if (overlayAttributes == null) {
                        throw new IllegalArgumentException("Can't retrieve overlay attributes from launch options");
                    }
                    ReentrantLock reentrantLock2 = overlayControllerImpl.globalLock;
                    reentrantLock2.lock();
                    reentrantLock2.unlock();
                    overlayControllerImpl.overlayTagToCurrentAttributesMap.put(activityStackTag, overlayAttributes);
                    return overlayControllerImpl.toActivityStackAttributes(overlayAttributes, parentContainerInfo);
                } finally {
                    reentrantLock.unlock();
                }
            }
        });
        activityEmbeddingComponent.registerActivityStackCallback(new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new Consumer() { // from class: androidx.window.embedding.OverlayControllerImpl$$ExternalSyntheticLambda1
            public final void accept(Object obj) {
                OverlayControllerImpl overlayControllerImpl = OverlayControllerImpl.this;
                List list = (List) obj;
                ReentrantLock reentrantLock = overlayControllerImpl.globalLock;
                reentrantLock.lock();
                try {
                    Set keySet = overlayControllerImpl.overlayTagToContainerMap.keySet();
                    overlayControllerImpl.overlayTagToContainerMap.clear();
                    ArrayMap arrayMap = overlayControllerImpl.overlayTagToContainerMap;
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : list) {
                        if (((androidx.window.extensions.embedding.ActivityStack) obj2).getTag() != null) {
                            arrayList.add(obj2);
                        }
                    }
                    List<androidx.window.extensions.embedding.ActivityStack> list2 = CollectionsKt___CollectionsKt.toList(arrayList);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                    for (androidx.window.extensions.embedding.ActivityStack activityStack : list2) {
                        String tag = activityStack.getTag();
                        tag.getClass();
                        arrayList2.add(new Pair(tag, activityStack));
                    }
                    MapsKt__MapsKt.putAll(arrayMap, arrayList2);
                    overlayControllerImpl.cleanUpDismissedOverlayContainerRecords(keySet);
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                } catch (Throwable th) {
                    reentrantLock.unlock();
                    throw th;
                }
            }
        });
    }

    public final void cleanUpDismissedOverlayContainerRecords(Set set) {
        if (set.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Set keySet = this.overlayTagToContainerMap.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!keySet.contains(str) && this.embeddingExtension.getActivityStackToken(str) == null) {
                arrayList.add(str);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str2 = (String) obj;
            ((ArrayMap) this.overlayTagToDefaultAttributesMap).remove(str2);
            this.overlayTagToCurrentAttributesMap.remove(str2);
        }
    }

    public final ActivityStackAttributes toActivityStackAttributes(OverlayAttributes overlayAttributes, androidx.window.extensions.embedding.ParentContainerInfo parentContainerInfo) {
        EmbeddingBounds.Dimension dimension;
        EmbeddingBounds.Dimension dimension2;
        int i;
        Object obj;
        int i2;
        Bounds offset;
        ActivityStackAttributes.Builder builder = new ActivityStackAttributes.Builder();
        EmbeddingBounds.Companion companion = EmbeddingBounds.Companion;
        this.adapter.getClass();
        Configuration configuration = parentContainerInfo.getConfiguration();
        DensityCompatHelper.Companion.getClass();
        DensityCompatHelperApi34Impl densityCompatHelperApi34Impl = DensityCompatHelperApi34Impl.INSTANCE;
        parentContainerInfo.getConfiguration();
        WindowMetrics windowMetrics = parentContainerInfo.getWindowMetrics();
        densityCompatHelperApi34Impl.getClass();
        float density = windowMetrics.getDensity();
        WindowMetricsCalculator.Companion companion2 = WindowMetricsCalculator.Companion;
        WindowMetrics windowMetrics2 = parentContainerInfo.getWindowMetrics();
        companion2.getClass();
        androidx.window.layout.WindowMetrics translateWindowMetrics$window_release = WindowMetricsCalculator.Companion.translateWindowMetrics$window_release(windowMetrics2);
        Bounds bounds = new Bounds(translateWindowMetrics$window_release._bounds.toRect());
        ExtensionsWindowLayoutInfoAdapter extensionsWindowLayoutInfoAdapter = ExtensionsWindowLayoutInfoAdapter.INSTANCE;
        WindowLayoutInfo windowLayoutInfo = parentContainerInfo.getWindowLayoutInfo();
        extensionsWindowLayoutInfoAdapter.getClass();
        ParentContainerInfo parentContainerInfo2 = new ParentContainerInfo(bounds, ExtensionsWindowLayoutInfoAdapter.translate$window_release(translateWindowMetrics$window_release, windowLayoutInfo), translateWindowMetrics$window_release._windowInsetsCompat, configuration, density);
        companion.getClass();
        EmbeddingBounds embeddingBounds = overlayAttributes.bounds;
        EmbeddingBounds.Dimension dimension3 = embeddingBounds.width;
        EmbeddingBounds.Dimension.Ratio ratio = EmbeddingBounds.Dimension.DIMENSION_EXPANDED;
        boolean areEqual = Intrinsics.areEqual(dimension3, ratio);
        EmbeddingBounds.Dimension dimension4 = embeddingBounds.height;
        if (areEqual && Intrinsics.areEqual(dimension4, ratio)) {
            Bounds.Companion.getClass();
            offset = Bounds.EMPTY_BOUNDS;
        } else {
            androidx.window.layout.WindowLayoutInfo windowLayoutInfo2 = parentContainerInfo2.windowLayoutInfo;
            if (embeddingBounds.shouldUseFallbackDimensionForWidth$window_release(windowLayoutInfo2)) {
                EmbeddingBounds.Dimension.Companion.getClass();
                dimension = new EmbeddingBounds.Dimension.Ratio(0.5f);
            } else {
                dimension = embeddingBounds.width;
            }
            if (embeddingBounds.shouldUseFallbackDimensionForHeight$window_release(windowLayoutInfo2)) {
                EmbeddingBounds.Dimension.Companion.getClass();
                dimension4 = new EmbeddingBounds.Dimension.Ratio(0.5f);
            }
            EmbeddingBounds.Alignment alignment = embeddingBounds.alignment;
            EmbeddingBounds embeddingBounds2 = new EmbeddingBounds(alignment, dimension, dimension4);
            Bounds bounds2 = parentContainerInfo2.windowBounds;
            int width = bounds2.getWidth();
            boolean shouldUseFallbackDimensionForWidth$window_release = embeddingBounds2.shouldUseFallbackDimensionForWidth$window_release(windowLayoutInfo2);
            EmbeddingBounds.Dimension dimension5 = embeddingBounds2.width;
            if (shouldUseFallbackDimensionForWidth$window_release) {
                EmbeddingBounds.Dimension.Companion.getClass();
                dimension2 = new EmbeddingBounds.Dimension.Ratio(0.5f);
            } else {
                dimension2 = dimension5;
            }
            boolean z = dimension2 instanceof EmbeddingBounds.Dimension.Ratio;
            EmbeddingBounds.Alignment alignment2 = embeddingBounds2.alignment;
            if (z) {
                i = (int) (((EmbeddingBounds.Dimension.Ratio) dimension2).value * width);
            } else if (dimension2 instanceof EmbeddingBounds.Dimension.Pixel) {
                i = Math.min(width, ((EmbeddingBounds.Dimension.Pixel) dimension2).value);
            } else {
                if (!Intrinsics.areEqual(dimension2, EmbeddingBounds.Dimension.DIMENSION_HINGE)) {
                    throw new IllegalArgumentException("Unhandled width dimension=" + dimension5);
                }
                FoldingFeature onlyFoldingFeatureOrNull = EmbeddingBounds.getOnlyFoldingFeatureOrNull(windowLayoutInfo2);
                onlyFoldingFeatureOrNull.getClass();
                Rect rect = ((HardwareFoldingFeature) onlyFoldingFeatureOrNull).featureBounds.toRect();
                if (Intrinsics.areEqual(alignment2, EmbeddingBounds.Alignment.ALIGN_LEFT)) {
                    i = rect.left - bounds2.left;
                } else {
                    if (!Intrinsics.areEqual(alignment2, EmbeddingBounds.Alignment.ALIGN_RIGHT)) {
                        throw new IllegalStateException("Unhandled condition to get height in pixel! embeddingBounds=" + embeddingBounds2 + " taskBounds=" + bounds2 + " windowLayoutInfo=" + windowLayoutInfo2);
                    }
                    i = bounds2.right - rect.right;
                }
            }
            int height = bounds2.getHeight();
            if (embeddingBounds2.shouldUseFallbackDimensionForHeight$window_release(windowLayoutInfo2)) {
                EmbeddingBounds.Dimension.Companion.getClass();
                obj = new EmbeddingBounds.Dimension.Ratio(0.5f);
            } else {
                obj = embeddingBounds2.height;
            }
            if (obj instanceof EmbeddingBounds.Dimension.Ratio) {
                i2 = (int) (((EmbeddingBounds.Dimension.Ratio) obj).value * height);
            } else if (obj instanceof EmbeddingBounds.Dimension.Pixel) {
                i2 = Math.min(height, ((EmbeddingBounds.Dimension.Pixel) obj).value);
            } else {
                if (!Intrinsics.areEqual(obj, EmbeddingBounds.Dimension.DIMENSION_HINGE)) {
                    throw new IllegalArgumentException("Unhandled width dimension=" + dimension5);
                }
                FoldingFeature onlyFoldingFeatureOrNull2 = EmbeddingBounds.getOnlyFoldingFeatureOrNull(windowLayoutInfo2);
                onlyFoldingFeatureOrNull2.getClass();
                Rect rect2 = ((HardwareFoldingFeature) onlyFoldingFeatureOrNull2).featureBounds.toRect();
                if (Intrinsics.areEqual(alignment2, EmbeddingBounds.Alignment.ALIGN_TOP)) {
                    i2 = rect2.top - bounds2.top;
                } else {
                    if (!Intrinsics.areEqual(alignment2, EmbeddingBounds.Alignment.ALIGN_BOTTOM)) {
                        throw new IllegalStateException("Unhandled condition to get height in pixel! embeddingBounds=" + embeddingBounds2 + " taskBounds=" + bounds2 + " windowLayoutInfo=" + windowLayoutInfo2);
                    }
                    i2 = bounds2.bottom - rect2.bottom;
                }
            }
            int width2 = bounds2.getWidth();
            int height2 = bounds2.getHeight();
            if (i == width2 && i2 == height2) {
                Bounds.Companion.getClass();
                offset = Bounds.EMPTY_BOUNDS;
            } else {
                Bounds bounds3 = new Bounds(0, 0, i, i2);
                if (Intrinsics.areEqual(alignment, EmbeddingBounds.Alignment.ALIGN_TOP)) {
                    offset = EmbeddingBounds.Companion.offset(bounds3, (width2 - i) / 2, 0);
                } else if (Intrinsics.areEqual(alignment, EmbeddingBounds.Alignment.ALIGN_LEFT)) {
                    offset = EmbeddingBounds.Companion.offset(bounds3, 0, (height2 - i2) / 2);
                } else if (Intrinsics.areEqual(alignment, EmbeddingBounds.Alignment.ALIGN_BOTTOM)) {
                    offset = EmbeddingBounds.Companion.offset(bounds3, (width2 - i) / 2, height2 - i2);
                } else {
                    if (!Intrinsics.areEqual(alignment, EmbeddingBounds.Alignment.ALIGN_RIGHT)) {
                        throw new IllegalArgumentException("Unknown alignment: " + alignment);
                    }
                    offset = EmbeddingBounds.Companion.offset(bounds3, width2 - i, (height2 - i2) / 2);
                }
            }
        }
        ActivityStackAttributes.Builder relativeBounds = builder.setRelativeBounds(offset.toRect());
        WindowSdkExtensions.Companion.getClass();
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(5);
        return relativeBounds.setWindowAttributes(new WindowAttributes(Intrinsics.areEqual((Object) null, EmbeddingConfiguration$DimAreaBehavior.ON_ACTIVITY_STACK) ? 1 : 2)).build();
    }
}
