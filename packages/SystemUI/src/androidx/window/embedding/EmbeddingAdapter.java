package androidx.window.embedding;

import android.os.Binder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.PredicateAdapter;
import androidx.window.embedding.DividerAttributes;
import androidx.window.embedding.EmbeddingAnimationBackground;
import androidx.window.embedding.SplitAttributes;
import androidx.window.extensions.embedding.AnimationBackground;
import androidx.window.extensions.embedding.SplitAttributes;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes.dex */
public final class EmbeddingAdapter {
    public static final String TAG;
    public final VendorApiLevel1Impl api1Impl;
    public final VendorApiLevel2Impl api2Impl = new VendorApiLevel2Impl();
    public final VendorApiLevel3Impl api3Impl = new VendorApiLevel3Impl();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class VendorApiLevel1Impl {
        public VendorApiLevel1Impl(EmbeddingAdapter embeddingAdapter, PredicateAdapter predicateAdapter) {
        }

        public static ActivityStack translateCompat(androidx.window.extensions.embedding.ActivityStack activityStack) {
            return new ActivityStack(activityStack.getActivities(), activityStack.isEmpty());
        }
    }

    public final class VendorApiLevel2Impl {
        public VendorApiLevel2Impl() {
        }
    }

    public final class VendorApiLevel3Impl {
        public VendorApiLevel3Impl() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(EmbeddingAdapter.class).getSimpleName();
        new Binder();
    }

    public EmbeddingAdapter(PredicateAdapter predicateAdapter) {
        this.api1Impl = new VendorApiLevel1Impl(this, predicateAdapter);
    }

    public final List translate(List list) {
        SplitInfo splitInfo;
        List<androidx.window.extensions.embedding.SplitInfo> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (androidx.window.extensions.embedding.SplitInfo splitInfo2 : list2) {
            WindowSdkExtensions.Companion.getClass();
            int i = WindowSdkExtensions.Companion.getInstance().extensionVersion;
            if (i == 1) {
                this.api1Impl.getClass();
                ActivityStack activityStackTranslateCompat = VendorApiLevel1Impl.translateCompat(splitInfo2.getPrimaryActivityStack());
                ActivityStack activityStackTranslateCompat2 = VendorApiLevel1Impl.translateCompat(splitInfo2.getSecondaryActivityStack());
                SplitAttributes.Builder builder = new SplitAttributes.Builder();
                SplitAttributes.SplitType.Companion companion = SplitAttributes.SplitType.Companion;
                float splitRatio = splitInfo2.getSplitRatio();
                companion.getClass();
                SplitAttributes.SplitType splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_EXPAND;
                if (splitRatio != splitTypeRatio.value) {
                    splitTypeRatio = SplitAttributes.SplitType.Companion.ratio(splitRatio);
                }
                builder.splitType = splitTypeRatio;
                builder.layoutDirection = SplitAttributes.LayoutDirection.LOCALE;
                splitInfo = new SplitInfo(activityStackTranslateCompat, activityStackTranslateCompat2, new SplitAttributes(builder.splitType, builder.layoutDirection, builder.animationBackground, builder.dividerAttributes));
            } else if (i == 2) {
                EmbeddingAdapter embeddingAdapter = EmbeddingAdapter.this;
                VendorApiLevel1Impl vendorApiLevel1Impl = embeddingAdapter.api1Impl;
                androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo2.getPrimaryActivityStack();
                vendorApiLevel1Impl.getClass();
                ActivityStack activityStackTranslateCompat3 = VendorApiLevel1Impl.translateCompat(primaryActivityStack);
                androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo2.getSecondaryActivityStack();
                embeddingAdapter.api1Impl.getClass();
                splitInfo = new SplitInfo(activityStackTranslateCompat3, VendorApiLevel1Impl.translateCompat(secondaryActivityStack), translate$window_release(splitInfo2.getSplitAttributes()));
            } else if (3 > i || i >= 5) {
                splitInfo = new SplitInfo(translate$window_release(splitInfo2.getPrimaryActivityStack()), translate$window_release(splitInfo2.getSecondaryActivityStack()), translate$window_release(splitInfo2.getSplitAttributes()), splitInfo2.getSplitInfoToken());
            } else {
                EmbeddingAdapter embeddingAdapter2 = EmbeddingAdapter.this;
                VendorApiLevel1Impl vendorApiLevel1Impl2 = embeddingAdapter2.api1Impl;
                androidx.window.extensions.embedding.ActivityStack primaryActivityStack2 = splitInfo2.getPrimaryActivityStack();
                vendorApiLevel1Impl2.getClass();
                ActivityStack activityStackTranslateCompat4 = VendorApiLevel1Impl.translateCompat(primaryActivityStack2);
                androidx.window.extensions.embedding.ActivityStack secondaryActivityStack2 = splitInfo2.getSecondaryActivityStack();
                embeddingAdapter2.api1Impl.getClass();
                splitInfo = new SplitInfo(activityStackTranslateCompat4, VendorApiLevel1Impl.translateCompat(secondaryActivityStack2), translate$window_release(splitInfo2.getSplitAttributes()), splitInfo2.getToken());
            }
            arrayList.add(splitInfo);
        }
        return arrayList;
    }

    public final ActivityStack translate$window_release(androidx.window.extensions.embedding.ActivityStack activityStack) {
        WindowSdkExtensions.Companion.getClass();
        int i = WindowSdkExtensions.Companion.getInstance().extensionVersion;
        if (1 > i || i >= 5) {
            return new ActivityStack(activityStack.getActivities(), activityStack.isEmpty(), activityStack.getActivityStackToken());
        }
        this.api1Impl.getClass();
        return VendorApiLevel1Impl.translateCompat(activityStack);
    }

    public static SplitAttributes translate$window_release(androidx.window.extensions.embedding.SplitAttributes splitAttributes) {
        SplitAttributes.SplitType splitTypeRatio;
        SplitAttributes.LayoutDirection layoutDirection;
        DividerAttributes fixedDividerAttributes;
        DividerAttributes.DragRange splitRatioDragRange;
        EmbeddingAnimationBackground colorBackground;
        SplitAttributes.Builder builder = new SplitAttributes.Builder();
        SplitAttributes.SplitType.RatioSplitType splitType = splitAttributes.getSplitType();
        if (splitType instanceof SplitAttributes.SplitType.HingeSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_HINGE;
        } else if (splitType instanceof SplitAttributes.SplitType.ExpandContainersSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_EXPAND;
        } else {
            if (!(splitType instanceof SplitAttributes.SplitType.RatioSplitType)) {
                throw new IllegalArgumentException("Unknown split type: " + splitType);
            }
            SplitAttributes.SplitType.Companion companion = SplitAttributes.SplitType.Companion;
            float ratio = splitType.getRatio();
            companion.getClass();
            splitTypeRatio = SplitAttributes.SplitType.Companion.ratio(ratio);
        }
        builder.splitType = splitTypeRatio;
        int layoutDirection2 = splitAttributes.getLayoutDirection();
        if (layoutDirection2 == 0) {
            layoutDirection = SplitAttributes.LayoutDirection.LEFT_TO_RIGHT;
        } else if (layoutDirection2 == 1) {
            layoutDirection = SplitAttributes.LayoutDirection.RIGHT_TO_LEFT;
        } else if (layoutDirection2 == 3) {
            layoutDirection = SplitAttributes.LayoutDirection.LOCALE;
        } else if (layoutDirection2 == 4) {
            layoutDirection = SplitAttributes.LayoutDirection.TOP_TO_BOTTOM;
        } else if (layoutDirection2 == 5) {
            layoutDirection = SplitAttributes.LayoutDirection.BOTTOM_TO_TOP;
        } else {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(layoutDirection2, "Unknown layout direction: "));
        }
        builder.layoutDirection = layoutDirection;
        WindowSdkExtensions.Companion companion2 = WindowSdkExtensions.Companion;
        companion2.getClass();
        if (WindowSdkExtensions.Companion.getInstance().extensionVersion >= 5) {
            AnimationBackground.ColorBackground animationBackground = splitAttributes.getAnimationBackground();
            if (animationBackground instanceof AnimationBackground.ColorBackground) {
                EmbeddingAnimationBackground.Companion companion3 = EmbeddingAnimationBackground.Companion;
                int color = animationBackground.getColor();
                companion3.getClass();
                colorBackground = new EmbeddingAnimationBackground.ColorBackground(color);
            } else {
                colorBackground = EmbeddingAnimationBackground.DEFAULT;
            }
            builder.animationBackground = colorBackground;
        }
        companion2.getClass();
        if (WindowSdkExtensions.Companion.getInstance().extensionVersion >= 6) {
            androidx.window.extensions.embedding.DividerAttributes dividerAttributes = splitAttributes.getDividerAttributes();
            companion2.getClass();
            WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(6);
            if (dividerAttributes == null) {
                fixedDividerAttributes = DividerAttributes.NO_DIVIDER;
            } else {
                int dividerType = dividerAttributes.getDividerType();
                if (dividerType == 1) {
                    DividerAttributes.FixedDividerAttributes.Builder builder2 = new DividerAttributes.FixedDividerAttributes.Builder();
                    int widthDp = dividerAttributes.getWidthDp();
                    DividerAttributes.Companion companion4 = DividerAttributes.Companion;
                    DividerAttributes.Companion.access$validateWidth(companion4, widthDp);
                    builder2.widthDp = widthDp;
                    int dividerColor = dividerAttributes.getDividerColor();
                    DividerAttributes.Companion.access$validateColor(companion4, dividerColor);
                    builder2.color = dividerColor;
                    fixedDividerAttributes = new DividerAttributes.FixedDividerAttributes(builder2.widthDp, builder2.color, null);
                } else if (dividerType != 2) {
                    Log.w(TAG, "Unknown divider type " + dividerAttributes + ".dividerType, default to fixed divider type");
                    DividerAttributes.FixedDividerAttributes.Builder builder3 = new DividerAttributes.FixedDividerAttributes.Builder();
                    int widthDp2 = dividerAttributes.getWidthDp();
                    DividerAttributes.Companion companion5 = DividerAttributes.Companion;
                    DividerAttributes.Companion.access$validateWidth(companion5, widthDp2);
                    builder3.widthDp = widthDp2;
                    int dividerColor2 = dividerAttributes.getDividerColor();
                    DividerAttributes.Companion.access$validateColor(companion5, dividerColor2);
                    builder3.color = dividerColor2;
                    fixedDividerAttributes = new DividerAttributes.FixedDividerAttributes(builder3.widthDp, builder3.color, null);
                } else {
                    DividerAttributes.DraggableDividerAttributes.Builder builder4 = new DividerAttributes.DraggableDividerAttributes.Builder();
                    int widthDp3 = dividerAttributes.getWidthDp();
                    DividerAttributes.Companion companion6 = DividerAttributes.Companion;
                    DividerAttributes.Companion.access$validateWidth(companion6, widthDp3);
                    builder4.widthDp = widthDp3;
                    int dividerColor3 = dividerAttributes.getDividerColor();
                    DividerAttributes.Companion.access$validateColor(companion6, dividerColor3);
                    builder4.color = dividerColor3;
                    if (dividerAttributes.getPrimaryMinRatio() == -1.0f && dividerAttributes.getPrimaryMaxRatio() == -1.0f) {
                        splitRatioDragRange = DividerAttributes.DragRange.DRAG_RANGE_SYSTEM_DEFAULT;
                    } else {
                        splitRatioDragRange = new DividerAttributes.DragRange.SplitRatioDragRange(dividerAttributes.getPrimaryMinRatio(), dividerAttributes.getPrimaryMaxRatio());
                    }
                    builder4.dragRange = splitRatioDragRange;
                    fixedDividerAttributes = new DividerAttributes.DraggableDividerAttributes(builder4.widthDp, builder4.color, builder4.dragRange, null);
                }
            }
            builder.dividerAttributes = fixedDividerAttributes;
        }
        return new SplitAttributes(builder.splitType, builder.layoutDirection, builder.animationBackground, builder.dividerAttributes);
    }
}
