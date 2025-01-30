package com.android.systemui.navigationbar.gestural;

import android.content.res.Resources;
import android.util.TypedValue;
import androidx.core.animation.Interpolator;
import androidx.core.animation.PathInterpolator;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.settingslib.udfps.UdfpsOverlayParams$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgePanelParams {
    public BackIndicatorDimens activeIndicator;
    public PathInterpolator activeWidthInterpolator;
    public Interpolator arrowAngleInterpolator;
    public float arrowThickness;
    public BackIndicatorDimens cancelledIndicator;
    public BackIndicatorDimens committedIndicator;
    public float deactivationTriggerThreshold;
    public ClosedFloatRange dynamicTriggerThresholdRange;
    public PathInterpolator edgeCornerInterpolator;
    public BackIndicatorDimens entryIndicator;
    public PathInterpolator entryWidthInterpolator;
    public PathInterpolator entryWidthTowardsEdgeInterpolator;
    public PathInterpolator farCornerInterpolator;
    public int fingerOffset;
    public BackIndicatorDimens flungIndicator;
    public BackIndicatorDimens fullyStretchedIndicator;
    public PathInterpolator heightInterpolator;
    public PathInterpolator horizontalTranslationInterpolator;
    public int minArrowYPosition;
    public BackIndicatorDimens preThresholdIndicator;
    public float reactivationTriggerThreshold;
    public Resources resources;
    public float staticTriggerThreshold;
    public float swipeProgressThreshold;
    public PathInterpolator verticalTranslationInterpolator;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ArrowDimens {
        public final float alpha;
        public final Step alphaInterpolator;
        public final Step alphaSpring;
        public final Float height;
        public final SpringForce heightSpring;
        public final Float length;
        public final SpringForce lengthSpring;

        public ArrowDimens() {
            this(null, null, 0.0f, null, null, null, null, 127, null);
        }

        public static ArrowDimens copy$default(ArrowDimens arrowDimens, Float f, Float f2, SpringForce springForce, SpringForce springForce2) {
            float f3 = arrowDimens.alpha;
            Step step = arrowDimens.alphaSpring;
            Step step2 = arrowDimens.alphaInterpolator;
            arrowDimens.getClass();
            return new ArrowDimens(f, f2, f3, springForce, springForce2, step, step2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ArrowDimens)) {
                return false;
            }
            ArrowDimens arrowDimens = (ArrowDimens) obj;
            return Intrinsics.areEqual(this.length, arrowDimens.length) && Intrinsics.areEqual(this.height, arrowDimens.height) && Float.compare(this.alpha, arrowDimens.alpha) == 0 && Intrinsics.areEqual(this.heightSpring, arrowDimens.heightSpring) && Intrinsics.areEqual(this.lengthSpring, arrowDimens.lengthSpring) && Intrinsics.areEqual(this.alphaSpring, arrowDimens.alphaSpring) && Intrinsics.areEqual(this.alphaInterpolator, arrowDimens.alphaInterpolator);
        }

        public final int hashCode() {
            Float f = this.length;
            int hashCode = (f == null ? 0 : f.hashCode()) * 31;
            Float f2 = this.height;
            int m90m = UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.alpha, (hashCode + (f2 == null ? 0 : f2.hashCode())) * 31, 31);
            SpringForce springForce = this.heightSpring;
            int hashCode2 = (m90m + (springForce == null ? 0 : springForce.hashCode())) * 31;
            SpringForce springForce2 = this.lengthSpring;
            int hashCode3 = (hashCode2 + (springForce2 == null ? 0 : springForce2.hashCode())) * 31;
            Step step = this.alphaSpring;
            int hashCode4 = (hashCode3 + (step == null ? 0 : step.hashCode())) * 31;
            Step step2 = this.alphaInterpolator;
            return hashCode4 + (step2 != null ? step2.hashCode() : 0);
        }

        public final String toString() {
            return "ArrowDimens(length=" + this.length + ", height=" + this.height + ", alpha=" + this.alpha + ", heightSpring=" + this.heightSpring + ", lengthSpring=" + this.lengthSpring + ", alphaSpring=" + this.alphaSpring + ", alphaInterpolator=" + this.alphaInterpolator + ")";
        }

        public ArrowDimens(Float f, Float f2, float f3, SpringForce springForce, SpringForce springForce2, Step step, Step step2) {
            this.length = f;
            this.height = f2;
            this.alpha = f3;
            this.heightSpring = springForce;
            this.lengthSpring = springForce2;
            this.alphaSpring = step;
            this.alphaInterpolator = step2;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public /* synthetic */ ArrowDimens(Float f, Float f2, float f3, SpringForce springForce, SpringForce springForce2, Step step, Step step2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(r11 != 0 ? r1 : f, (i & 2) != 0 ? r1 : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? null : springForce, (i & 16) != 0 ? null : springForce2, (i & 32) != 0 ? null : step, (i & 64) != 0 ? null : step2);
            int i2 = i & 1;
            Float valueOf = Float.valueOf(0.0f);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackgroundDimens {
        public final float alpha;
        public final SpringForce alphaSpring;
        public final float edgeCornerRadius;
        public final SpringForce edgeCornerRadiusSpring;
        public final float farCornerRadius;
        public final SpringForce farCornerRadiusSpring;
        public final float height;
        public final SpringForce heightSpring;
        public final Float width;
        public final SpringForce widthSpring;

        public BackgroundDimens() {
            this(null, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, 1023, null);
        }

        public static BackgroundDimens copy$default(BackgroundDimens backgroundDimens, Float f, SpringForce springForce, SpringForce springForce2, SpringForce springForce3, SpringForce springForce4, SpringForce springForce5, int i) {
            Float f2 = (i & 1) != 0 ? backgroundDimens.width : f;
            float f3 = (i & 2) != 0 ? backgroundDimens.height : 0.0f;
            float f4 = (i & 4) != 0 ? backgroundDimens.edgeCornerRadius : 0.0f;
            float f5 = (i & 8) != 0 ? backgroundDimens.farCornerRadius : 0.0f;
            float f6 = (i & 16) != 0 ? backgroundDimens.alpha : 0.0f;
            SpringForce springForce6 = (i & 32) != 0 ? backgroundDimens.widthSpring : springForce;
            SpringForce springForce7 = (i & 64) != 0 ? backgroundDimens.heightSpring : springForce2;
            SpringForce springForce8 = (i & 128) != 0 ? backgroundDimens.farCornerRadiusSpring : springForce3;
            SpringForce springForce9 = (i & 256) != 0 ? backgroundDimens.edgeCornerRadiusSpring : springForce4;
            SpringForce springForce10 = (i & 512) != 0 ? backgroundDimens.alphaSpring : springForce5;
            backgroundDimens.getClass();
            return new BackgroundDimens(f2, f3, f4, f5, f6, springForce6, springForce7, springForce8, springForce9, springForce10);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BackgroundDimens)) {
                return false;
            }
            BackgroundDimens backgroundDimens = (BackgroundDimens) obj;
            return Intrinsics.areEqual(this.width, backgroundDimens.width) && Float.compare(this.height, backgroundDimens.height) == 0 && Float.compare(this.edgeCornerRadius, backgroundDimens.edgeCornerRadius) == 0 && Float.compare(this.farCornerRadius, backgroundDimens.farCornerRadius) == 0 && Float.compare(this.alpha, backgroundDimens.alpha) == 0 && Intrinsics.areEqual(this.widthSpring, backgroundDimens.widthSpring) && Intrinsics.areEqual(this.heightSpring, backgroundDimens.heightSpring) && Intrinsics.areEqual(this.farCornerRadiusSpring, backgroundDimens.farCornerRadiusSpring) && Intrinsics.areEqual(this.edgeCornerRadiusSpring, backgroundDimens.edgeCornerRadiusSpring) && Intrinsics.areEqual(this.alphaSpring, backgroundDimens.alphaSpring);
        }

        public final int hashCode() {
            Float f = this.width;
            int m90m = UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.alpha, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.farCornerRadius, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.edgeCornerRadius, UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.height, (f == null ? 0 : f.hashCode()) * 31, 31), 31), 31), 31);
            SpringForce springForce = this.widthSpring;
            int hashCode = (m90m + (springForce == null ? 0 : springForce.hashCode())) * 31;
            SpringForce springForce2 = this.heightSpring;
            int hashCode2 = (hashCode + (springForce2 == null ? 0 : springForce2.hashCode())) * 31;
            SpringForce springForce3 = this.farCornerRadiusSpring;
            int hashCode3 = (hashCode2 + (springForce3 == null ? 0 : springForce3.hashCode())) * 31;
            SpringForce springForce4 = this.edgeCornerRadiusSpring;
            int hashCode4 = (hashCode3 + (springForce4 == null ? 0 : springForce4.hashCode())) * 31;
            SpringForce springForce5 = this.alphaSpring;
            return hashCode4 + (springForce5 != null ? springForce5.hashCode() : 0);
        }

        public final String toString() {
            return "BackgroundDimens(width=" + this.width + ", height=" + this.height + ", edgeCornerRadius=" + this.edgeCornerRadius + ", farCornerRadius=" + this.farCornerRadius + ", alpha=" + this.alpha + ", widthSpring=" + this.widthSpring + ", heightSpring=" + this.heightSpring + ", farCornerRadiusSpring=" + this.farCornerRadiusSpring + ", edgeCornerRadiusSpring=" + this.edgeCornerRadiusSpring + ", alphaSpring=" + this.alphaSpring + ")";
        }

        public BackgroundDimens(Float f, float f2, float f3, float f4, float f5, SpringForce springForce, SpringForce springForce2, SpringForce springForce3, SpringForce springForce4, SpringForce springForce5) {
            this.width = f;
            this.height = f2;
            this.edgeCornerRadius = f3;
            this.farCornerRadius = f4;
            this.alpha = f5;
            this.widthSpring = springForce;
            this.heightSpring = springForce2;
            this.farCornerRadiusSpring = springForce3;
            this.edgeCornerRadiusSpring = springForce4;
            this.alphaSpring = springForce5;
        }

        public /* synthetic */ BackgroundDimens(Float f, float f2, float f3, float f4, float f5, SpringForce springForce, SpringForce springForce2, SpringForce springForce3, SpringForce springForce4, SpringForce springForce5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? Float.valueOf(0.0f) : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? 0.0f : f4, (i & 16) == 0 ? f5 : 0.0f, (i & 32) != 0 ? null : springForce, (i & 64) != 0 ? null : springForce2, (i & 128) != 0 ? null : springForce3, (i & 256) != 0 ? null : springForce4, (i & 512) == 0 ? springForce5 : null);
        }
    }

    public EdgePanelParams(Resources resources) {
        this.resources = resources;
        update(resources);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof EdgePanelParams) && Intrinsics.areEqual(this.resources, ((EdgePanelParams) obj).resources);
    }

    public final BackIndicatorDimens getActiveIndicator() {
        BackIndicatorDimens backIndicatorDimens = this.activeIndicator;
        if (backIndicatorDimens != null) {
            return backIndicatorDimens;
        }
        return null;
    }

    public final BackIndicatorDimens getCommittedIndicator() {
        BackIndicatorDimens backIndicatorDimens = this.committedIndicator;
        if (backIndicatorDimens != null) {
            return backIndicatorDimens;
        }
        return null;
    }

    public final float getDimen(int i) {
        return this.resources.getDimension(i);
    }

    public final float getDimenFloat(int i) {
        TypedValue typedValue = new TypedValue();
        this.resources.getValue(i, typedValue, true);
        return typedValue.getFloat();
    }

    public final BackIndicatorDimens getEntryIndicator() {
        BackIndicatorDimens backIndicatorDimens = this.entryIndicator;
        if (backIndicatorDimens != null) {
            return backIndicatorDimens;
        }
        return null;
    }

    public final BackIndicatorDimens getFlungIndicator() {
        BackIndicatorDimens backIndicatorDimens = this.flungIndicator;
        if (backIndicatorDimens != null) {
            return backIndicatorDimens;
        }
        return null;
    }

    public final BackIndicatorDimens getPreThresholdIndicator() {
        BackIndicatorDimens backIndicatorDimens = this.preThresholdIndicator;
        if (backIndicatorDimens != null) {
            return backIndicatorDimens;
        }
        return null;
    }

    public final int hashCode() {
        return this.resources.hashCode();
    }

    public final String toString() {
        return "EdgePanelParams(resources=" + this.resources + ")";
    }

    public final void update(Resources resources) {
        this.resources = resources;
        this.arrowThickness = getDimen(R.dimen.navigation_edge_arrow_thickness);
        this.resources.getDimensionPixelSize(R.dimen.navigation_edge_panel_padding);
        this.minArrowYPosition = this.resources.getDimensionPixelSize(R.dimen.navigation_edge_arrow_min_y);
        this.fingerOffset = this.resources.getDimensionPixelSize(R.dimen.navigation_edge_finger_offset);
        this.staticTriggerThreshold = getDimen(R.dimen.navigation_edge_action_drag_threshold);
        this.reactivationTriggerThreshold = getDimen(R.dimen.navigation_edge_action_reactivation_drag_threshold);
        float dimen = getDimen(R.dimen.navigation_edge_action_deactivation_drag_threshold);
        this.deactivationTriggerThreshold = dimen;
        this.dynamicTriggerThresholdRange = new ClosedFloatRange(this.reactivationTriggerThreshold, -dimen);
        this.swipeProgressThreshold = getDimen(R.dimen.navigation_edge_action_progress_threshold);
        this.entryWidthInterpolator = new PathInterpolator(0.19f, 1.27f, 0.71f, 0.86f);
        this.entryWidthTowardsEdgeInterpolator = new PathInterpolator(1.0f, -3.0f, 1.0f, 1.2f);
        this.activeWidthInterpolator = new PathInterpolator(0.7f, -0.24f, 0.48f, 1.21f);
        PathInterpolator pathInterpolator = this.entryWidthInterpolator;
        if (pathInterpolator == null) {
            pathInterpolator = null;
        }
        this.arrowAngleInterpolator = pathInterpolator;
        this.horizontalTranslationInterpolator = new PathInterpolator(0.2f, 1.0f, 1.0f, 1.0f);
        this.verticalTranslationInterpolator = new PathInterpolator(0.5f, 1.15f, 0.41f, 0.94f);
        this.farCornerInterpolator = new PathInterpolator(0.03f, 0.19f, 0.14f, 1.09f);
        this.edgeCornerInterpolator = new PathInterpolator(0.0f, 1.11f, 0.85f, 0.84f);
        this.heightInterpolator = new PathInterpolator(1.0f, 0.05f, 0.9f, -0.29f);
        SpringForce createSpring = EdgePanelParamsKt.createSpring(1500.0f, 0.29f);
        SpringForce createSpring2 = EdgePanelParamsKt.createSpring(1500.0f, 0.29f);
        SpringForce createSpring3 = EdgePanelParamsKt.createSpring(10000.0f, 1.0f);
        SpringForce createSpring4 = EdgePanelParamsKt.createSpring(10000.0f, 1.0f);
        SpringForce createSpring5 = EdgePanelParamsKt.createSpring(10000.0f, 1.0f);
        SpringForce createSpring6 = EdgePanelParamsKt.createSpring(10000.0f, 1.0f);
        Step step = new Step(0.165f, 1.05f, EdgePanelParamsKt.createSpring(180.0f, 0.9f), EdgePanelParamsKt.createSpring(2000.0f, 0.6f));
        Step step2 = new Step(0.165f, 1.05f, Float.valueOf(1.0f), Float.valueOf(0.0f));
        float dimen2 = getDimen(R.dimen.navigation_edge_entry_margin);
        float dimenFloat = getDimenFloat(R.dimen.navigation_edge_entry_scale);
        float dimen3 = getDimen(R.dimen.navigation_edge_pre_threshold_background_width);
        SpringForce createSpring7 = EdgePanelParamsKt.createSpring(800.0f, 0.76f);
        SpringForce createSpring8 = EdgePanelParamsKt.createSpring(30000.0f, 1.0f);
        SpringForce createSpring9 = EdgePanelParamsKt.createSpring(120.0f, 0.8f);
        float dimen4 = getDimen(R.dimen.navigation_edge_entry_arrow_length);
        float dimen5 = getDimen(R.dimen.navigation_edge_entry_arrow_height);
        this.entryIndicator = new BackIndicatorDimens(Float.valueOf(dimen2), dimenFloat, Float.valueOf(dimen3), new ArrowDimens(Float.valueOf(dimen4), Float.valueOf(dimen5), 0.0f, EdgePanelParamsKt.createSpring(600.0f, 0.4f), EdgePanelParamsKt.createSpring(600.0f, 0.4f), step, step2), new BackgroundDimens(Float.valueOf(getDimen(R.dimen.navigation_edge_entry_background_width)), getDimen(R.dimen.navigation_edge_entry_background_height), getDimen(R.dimen.navigation_edge_entry_edge_corners), getDimen(R.dimen.navigation_edge_entry_far_corners), 1.0f, EdgePanelParamsKt.createSpring(450.0f, 0.65f), EdgePanelParamsKt.createSpring(1500.0f, 0.45f), EdgePanelParamsKt.createSpring(300.0f, 0.5f), EdgePanelParamsKt.createSpring(150.0f, 0.5f), null, 512, null), createSpring8, createSpring7, createSpring9);
        float dimen6 = getDimen(R.dimen.navigation_edge_active_margin);
        float dimenFloat2 = getDimenFloat(R.dimen.navigation_edge_active_scale);
        SpringForce createSpring10 = EdgePanelParamsKt.createSpring(1000.0f, 0.8f);
        boolean z = BasicRune.NAVBAR_GESTURE;
        SpringForce createSpring11 = EdgePanelParamsKt.createSpring(325.0f, z ? 0.85f : 0.55f);
        float dimen7 = getDimen(R.dimen.navigation_edge_active_background_width);
        ArrowDimens arrowDimens = new ArrowDimens(Float.valueOf(getDimen(z ? R.dimen.samsung_navigation_edge_active_arrow_length : R.dimen.navigation_edge_active_arrow_length)), Float.valueOf(getDimen(z ? R.dimen.samsung_navigation_edge_active_arrow_height : R.dimen.navigation_edge_active_arrow_height)), 1.0f, createSpring2, createSpring, step, step2);
        float dimen8 = getDimen(R.dimen.navigation_edge_active_background_width);
        this.activeIndicator = new BackIndicatorDimens(Float.valueOf(dimen6), dimenFloat2, Float.valueOf(dimen7), arrowDimens, new BackgroundDimens(Float.valueOf(dimen8), getDimen(R.dimen.navigation_edge_active_background_height), getDimen(R.dimen.navigation_edge_active_edge_corners), getDimen(R.dimen.navigation_edge_active_far_corners), 1.0f, EdgePanelParamsKt.createSpring(850.0f, 0.75f), EdgePanelParamsKt.createSpring(10000.0f, 1.0f), EdgePanelParamsKt.createSpring(1200.0f, 0.3f), EdgePanelParamsKt.createSpring(2600.0f, 0.855f), null, 512, null), null, createSpring10, createSpring11, 32, null);
        float dimen9 = getDimen(R.dimen.navigation_edge_pre_threshold_margin);
        float dimenFloat3 = getDimenFloat(R.dimen.navigation_edge_pre_threshold_scale);
        float dimen10 = getDimen(R.dimen.navigation_edge_pre_threshold_background_width);
        SpringForce createSpring12 = EdgePanelParamsKt.createSpring(120.0f, 0.8f);
        SpringForce createSpring13 = EdgePanelParamsKt.createSpring(6000.0f, 1.0f);
        float dimen11 = getDimen(R.dimen.navigation_edge_pre_threshold_arrow_length);
        float dimen12 = getDimen(R.dimen.navigation_edge_pre_threshold_arrow_height);
        ArrowDimens arrowDimens2 = new ArrowDimens(Float.valueOf(dimen11), Float.valueOf(dimen12), 1.0f, EdgePanelParamsKt.createSpring(100.0f, 0.6f), EdgePanelParamsKt.createSpring(100.0f, 0.6f), step, step2);
        float dimen13 = getDimen(R.dimen.navigation_edge_pre_threshold_background_width);
        this.preThresholdIndicator = new BackIndicatorDimens(Float.valueOf(dimen9), dimenFloat3, Float.valueOf(dimen10), arrowDimens2, new BackgroundDimens(Float.valueOf(dimen13), getDimen(R.dimen.navigation_edge_pre_threshold_background_height), getDimen(R.dimen.navigation_edge_pre_threshold_edge_corners), getDimen(R.dimen.navigation_edge_pre_threshold_far_corners), 1.0f, EdgePanelParamsKt.createSpring(650.0f, 1.0f), EdgePanelParamsKt.createSpring(1500.0f, 0.45f), EdgePanelParamsKt.createSpring(300.0f, 1.0f), EdgePanelParamsKt.createSpring(250.0f, 0.5f), null, 512, null), null, createSpring13, createSpring12, 32, null);
        this.committedIndicator = BackIndicatorDimens.copy$default(getActiveIndicator(), 0.86f, ArrowDimens.copy$default(getActiveIndicator().arrowDimens, null, null, createSpring2, createSpring), BackgroundDimens.copy$default(getActiveIndicator().backgroundDimens, null, createSpring5, createSpring6, createSpring4, createSpring3, EdgePanelParamsKt.createSpring(1400.0f, 1.0f), 14), null, EdgePanelParamsKt.createSpring(5700.0f, 1.0f), 96);
        this.flungIndicator = BackIndicatorDimens.copy$default(getCommittedIndicator(), 0.0f, ArrowDimens.copy$default(getCommittedIndicator().arrowDimens, getActiveIndicator().arrowDimens.length, getActiveIndicator().arrowDimens.height, EdgePanelParamsKt.createSpring(850.0f, 0.46f), EdgePanelParamsKt.createSpring(850.0f, 0.46f)), BackgroundDimens.copy$default(getCommittedIndicator().backgroundDimens, null, createSpring5, createSpring6, createSpring4, createSpring3, null, 543), null, null, IKnoxCustomManager.Stub.TRANSACTION_removeShortcut);
        this.cancelledIndicator = BackIndicatorDimens.copy$default(getEntryIndicator(), 0.0f, null, BackgroundDimens.copy$default(getEntryIndicator().backgroundDimens, Float.valueOf(0.0f), null, null, null, null, EdgePanelParamsKt.createSpring(450.0f, 1.0f), 494), null, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
        float dimen14 = getDimen(R.dimen.navigation_edge_stretch_margin);
        float dimenFloat4 = getDimenFloat(R.dimen.navigation_edge_stretch_scale);
        float dimen15 = getDimen(R.dimen.navigation_edge_stretched_arrow_length);
        float dimen16 = getDimen(R.dimen.navigation_edge_stretched_arrow_height);
        ArrowDimens arrowDimens3 = new ArrowDimens(Float.valueOf(dimen15), Float.valueOf(dimen16), 1.0f, null, null, null, null, 64, null);
        BackgroundDimens backgroundDimens = new BackgroundDimens(Float.valueOf(getDimen(R.dimen.navigation_edge_stretch_background_width)), getDimen(R.dimen.navigation_edge_stretch_background_height), getDimen(R.dimen.navigation_edge_stretch_edge_corners), getDimen(R.dimen.navigation_edge_stretch_far_corners), 1.0f, null, null, null, null, null);
        this.fullyStretchedIndicator = new BackIndicatorDimens(Float.valueOf(dimen14), dimenFloat4, null, arrowDimens3, backgroundDimens, null, null, null, 4, null);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackIndicatorDimens {
        public final ArrowDimens arrowDimens;
        public final BackgroundDimens backgroundDimens;
        public final Float horizontalTranslation;
        public final SpringForce horizontalTranslationSpring;
        public final float scale;
        public final Float scalePivotX;
        public final SpringForce scaleSpring;
        public final SpringForce verticalTranslationSpring;

        public BackIndicatorDimens(Float f, float f2, Float f3, ArrowDimens arrowDimens, BackgroundDimens backgroundDimens, SpringForce springForce, SpringForce springForce2, SpringForce springForce3) {
            this.horizontalTranslation = f;
            this.scale = f2;
            this.scalePivotX = f3;
            this.arrowDimens = arrowDimens;
            this.backgroundDimens = backgroundDimens;
            this.verticalTranslationSpring = springForce;
            this.horizontalTranslationSpring = springForce2;
            this.scaleSpring = springForce3;
        }

        public static BackIndicatorDimens copy$default(BackIndicatorDimens backIndicatorDimens, float f, ArrowDimens arrowDimens, BackgroundDimens backgroundDimens, SpringForce springForce, SpringForce springForce2, int i) {
            Float f2 = (i & 1) != 0 ? backIndicatorDimens.horizontalTranslation : null;
            float f3 = (i & 2) != 0 ? backIndicatorDimens.scale : f;
            Float f4 = (i & 4) != 0 ? backIndicatorDimens.scalePivotX : null;
            ArrowDimens arrowDimens2 = (i & 8) != 0 ? backIndicatorDimens.arrowDimens : arrowDimens;
            BackgroundDimens backgroundDimens2 = (i & 16) != 0 ? backIndicatorDimens.backgroundDimens : backgroundDimens;
            SpringForce springForce3 = (i & 32) != 0 ? backIndicatorDimens.verticalTranslationSpring : null;
            SpringForce springForce4 = (i & 64) != 0 ? backIndicatorDimens.horizontalTranslationSpring : springForce;
            SpringForce springForce5 = (i & 128) != 0 ? backIndicatorDimens.scaleSpring : springForce2;
            backIndicatorDimens.getClass();
            return new BackIndicatorDimens(f2, f3, f4, arrowDimens2, backgroundDimens2, springForce3, springForce4, springForce5);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BackIndicatorDimens)) {
                return false;
            }
            BackIndicatorDimens backIndicatorDimens = (BackIndicatorDimens) obj;
            return Intrinsics.areEqual(this.horizontalTranslation, backIndicatorDimens.horizontalTranslation) && Float.compare(this.scale, backIndicatorDimens.scale) == 0 && Intrinsics.areEqual(this.scalePivotX, backIndicatorDimens.scalePivotX) && Intrinsics.areEqual(this.arrowDimens, backIndicatorDimens.arrowDimens) && Intrinsics.areEqual(this.backgroundDimens, backIndicatorDimens.backgroundDimens) && Intrinsics.areEqual(this.verticalTranslationSpring, backIndicatorDimens.verticalTranslationSpring) && Intrinsics.areEqual(this.horizontalTranslationSpring, backIndicatorDimens.horizontalTranslationSpring) && Intrinsics.areEqual(this.scaleSpring, backIndicatorDimens.scaleSpring);
        }

        public final int hashCode() {
            Float f = this.horizontalTranslation;
            int m90m = UdfpsOverlayParams$$ExternalSyntheticOutline0.m90m(this.scale, (f == null ? 0 : f.hashCode()) * 31, 31);
            Float f2 = this.scalePivotX;
            int hashCode = (this.backgroundDimens.hashCode() + ((this.arrowDimens.hashCode() + ((m90m + (f2 == null ? 0 : f2.hashCode())) * 31)) * 31)) * 31;
            SpringForce springForce = this.verticalTranslationSpring;
            int hashCode2 = (hashCode + (springForce == null ? 0 : springForce.hashCode())) * 31;
            SpringForce springForce2 = this.horizontalTranslationSpring;
            int hashCode3 = (hashCode2 + (springForce2 == null ? 0 : springForce2.hashCode())) * 31;
            SpringForce springForce3 = this.scaleSpring;
            return hashCode3 + (springForce3 != null ? springForce3.hashCode() : 0);
        }

        public final String toString() {
            return "BackIndicatorDimens(horizontalTranslation=" + this.horizontalTranslation + ", scale=" + this.scale + ", scalePivotX=" + this.scalePivotX + ", arrowDimens=" + this.arrowDimens + ", backgroundDimens=" + this.backgroundDimens + ", verticalTranslationSpring=" + this.verticalTranslationSpring + ", horizontalTranslationSpring=" + this.horizontalTranslationSpring + ", scaleSpring=" + this.scaleSpring + ")";
        }

        public /* synthetic */ BackIndicatorDimens(Float f, float f2, Float f3, ArrowDimens arrowDimens, BackgroundDimens backgroundDimens, SpringForce springForce, SpringForce springForce2, SpringForce springForce3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? Float.valueOf(0.0f) : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? null : f3, arrowDimens, backgroundDimens, (i & 32) != 0 ? null : springForce, (i & 64) != 0 ? null : springForce2, (i & 128) != 0 ? null : springForce3);
        }
    }
}
