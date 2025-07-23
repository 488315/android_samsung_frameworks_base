package com.android.systemui.keyboard.backlight.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardBacklightDialog extends Dialog {
    public static final int BACKLIGHT_ICON_ID;
    public static final int[] LEFT_CORNERS_INDICES;
    public static final int[] RIGHT_CORNERS_INDICES;
    public final int backgroundColor;
    public int currentLevel;
    public final int defaultIconBackgroundColor;
    public final int defaultIconColor;
    public final int dialogBottomMargin;
    public final int dimmedIconBackgroundColor;
    public final int dimmedIconColor;
    public final int emptyRectangleColor;
    public final int filledRectangleColor;
    public BacklightIconProperties iconProperties;
    public final String levelContentDescription;
    public int maxLevel;
    public RootProperties rootProperties;
    public LinearLayout rootView;
    public StepViewProperties stepProperties;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class BacklightIconProperties {
        public final int height;
        public final int padding;
        public final int width;

        public BacklightIconProperties(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.padding = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BacklightIconProperties)) {
                return false;
            }
            BacklightIconProperties backlightIconProperties = (BacklightIconProperties) obj;
            return this.width == backlightIconProperties.width && this.height == backlightIconProperties.height && this.padding == backlightIconProperties.padding;
        }

        public final int hashCode() {
            return Integer.hashCode(this.padding) + ReorderTile$$ExternalSyntheticOutline0.m(this.height, Integer.hashCode(this.width) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("BacklightIconProperties(width=");
            sb.append(this.width);
            sb.append(", height=");
            sb.append(this.height);
            sb.append(", padding=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.padding, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RootProperties {
        public final float cornerRadius;
        public final int horizontalPadding;
        public final int verticalPadding;

        public RootProperties(float f, int i, int i2) {
            this.cornerRadius = f;
            this.verticalPadding = i;
            this.horizontalPadding = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RootProperties)) {
                return false;
            }
            RootProperties rootProperties = (RootProperties) obj;
            return Float.compare(this.cornerRadius, rootProperties.cornerRadius) == 0 && this.verticalPadding == rootProperties.verticalPadding && this.horizontalPadding == rootProperties.horizontalPadding;
        }

        public final int hashCode() {
            return Integer.hashCode(this.horizontalPadding) + ReorderTile$$ExternalSyntheticOutline0.m(this.verticalPadding, Float.hashCode(this.cornerRadius) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("RootProperties(cornerRadius=");
            sb.append(this.cornerRadius);
            sb.append(", verticalPadding=");
            sb.append(this.verticalPadding);
            sb.append(", horizontalPadding=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.horizontalPadding, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StepViewProperties {
        public final int height;
        public final int horizontalMargin;
        public final float largeRadius;
        public final float smallRadius;
        public final int width;

        public StepViewProperties(int i, int i2, int i3, float f, float f2) {
            this.width = i;
            this.height = i2;
            this.horizontalMargin = i3;
            this.smallRadius = f;
            this.largeRadius = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StepViewProperties)) {
                return false;
            }
            StepViewProperties stepViewProperties = (StepViewProperties) obj;
            return this.width == stepViewProperties.width && this.height == stepViewProperties.height && this.horizontalMargin == stepViewProperties.horizontalMargin && Float.compare(this.smallRadius, stepViewProperties.smallRadius) == 0 && Float.compare(this.largeRadius, stepViewProperties.largeRadius) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.largeRadius) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.smallRadius, ReorderTile$$ExternalSyntheticOutline0.m(this.horizontalMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.height, Integer.hashCode(this.width) * 31, 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("StepViewProperties(width=");
            sb.append(this.width);
            sb.append(", height=");
            sb.append(this.height);
            sb.append(", horizontalMargin=");
            sb.append(this.horizontalMargin);
            sb.append(", smallRadius=");
            sb.append(this.smallRadius);
            sb.append(", largeRadius=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.largeRadius, ")", sb);
        }
    }

    static {
        new Companion(null);
        BACKLIGHT_ICON_ID = R.id.backlight_icon;
        LEFT_CORNERS_INDICES = new int[]{0, 1, 6, 7};
        RIGHT_CORNERS_INDICES = new int[]{2, 3, 4, 5};
    }

    public /* synthetic */ KeyboardBacklightDialog(Context context, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, i, i2, (i4 & 8) != 0 ? R.style.Theme_SystemUI_Dialog : i3);
    }

    public static void updateColor(ShapeDrawable shapeDrawable, int i) {
        if (shapeDrawable.getPaint().getColor() != i) {
            shapeDrawable.getPaint().setColor(i);
            shapeDrawable.invalidateSelf();
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(1);
            window.setType(2017);
            window.addFlags(655360);
            window.clearFlags(2);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.getAttributes().setTitle("KeyboardBacklightDialog");
        }
        setCanceledOnTouchOutside(true);
        Window window2 = getWindow();
        if (window2 != null) {
            window2.setGravity(81);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window2.getAttributes());
            layoutParams.y = this.dialogBottomMargin;
            window2.setAttributes(layoutParams);
        }
        Window window3 = getWindow();
        if (window3 != null) {
            window3.setTitle(getContext().getString(R.string.keyboard_backlight_dialog_title));
        }
        Resources resources = getContext().getResources();
        this.rootProperties = new RootProperties(resources.getDimensionPixelSize(R.dimen.backlight_indicator_root_corner_radius), resources.getDimensionPixelSize(R.dimen.backlight_indicator_root_vertical_padding), resources.getDimensionPixelSize(R.dimen.backlight_indicator_root_horizontal_padding));
        this.iconProperties = new BacklightIconProperties(resources.getDimensionPixelSize(R.dimen.backlight_indicator_icon_width), resources.getDimensionPixelSize(R.dimen.backlight_indicator_icon_height), resources.getDimensionPixelSize(R.dimen.backlight_indicator_icon_padding));
        this.stepProperties = new StepViewProperties(resources.getDimensionPixelSize(R.dimen.backlight_indicator_step_width), resources.getDimensionPixelSize(R.dimen.backlight_indicator_step_height), resources.getDimensionPixelSize(R.dimen.backlight_indicator_step_horizontal_margin), resources.getDimensionPixelSize(R.dimen.backlight_indicator_step_small_radius), resources.getDimensionPixelSize(R.dimen.backlight_indicator_step_large_radius));
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(R.id.keyboard_backlight_dialog_container);
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        RootProperties rootProperties = this.rootProperties;
        int i = (rootProperties == null ? null : rootProperties).horizontalPadding;
        int i2 = (rootProperties == null ? null : rootProperties).verticalPadding;
        int i3 = (rootProperties == null ? null : rootProperties).horizontalPadding;
        if (rootProperties == null) {
            rootProperties = null;
        }
        linearLayout.setPadding(i, i2, i3, rootProperties.verticalPadding);
        float[] fArr = new float[8];
        for (int i4 = 0; i4 < 8; i4++) {
            RootProperties rootProperties2 = this.rootProperties;
            if (rootProperties2 == null) {
                rootProperties2 = null;
            }
            fArr[i4] = rootProperties2.cornerRadius;
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(this.backgroundColor);
        linearLayout.setBackground(shapeDrawable);
        this.rootView = linearLayout;
        setContentView(linearLayout);
        super.onCreate(bundle);
        updateState(this.currentLevel, this.maxLevel, true);
    }

    public final void updateState(int i, int i2, boolean z) {
        if (this.maxLevel != i2 || z) {
            this.maxLevel = i2;
            LinearLayout linearLayout = this.rootView;
            if (linearLayout == null) {
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            LinearLayout linearLayout2 = this.rootView;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            StepViewProperties stepViewProperties = this.stepProperties;
            if (stepViewProperties == null) {
                stepViewProperties = null;
            }
            int i3 = stepViewProperties.height;
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.setIntrinsicHeight(i3);
            shapeDrawable.setIntrinsicWidth(i3);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.ic_keyboard_backlight);
            imageView.setId(BACKLIGHT_ICON_ID);
            imageView.setColorFilter(this.defaultIconColor);
            BacklightIconProperties backlightIconProperties = this.iconProperties;
            if (backlightIconProperties == null) {
                backlightIconProperties = null;
            }
            int i4 = backlightIconProperties.padding;
            imageView.setPadding(i4, i4, i4, i4);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(i3, i3);
            StepViewProperties stepViewProperties2 = this.stepProperties;
            int i5 = (stepViewProperties2 == null ? null : stepViewProperties2).horizontalMargin;
            if (stepViewProperties2 == null) {
                stepViewProperties2 = null;
            }
            int i6 = 0;
            marginLayoutParams.setMargins(i5, 0, stepViewProperties2.horizontalMargin, 0);
            imageView.setLayoutParams(marginLayoutParams);
            imageView.setBackground(shapeDrawable);
            linearLayout2.addView(imageView);
            IntRange intRange = new IntRange(1, this.maxLevel);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(intRange, 10));
            IntProgressionIterator it = intRange.iterator();
            while (it.hasNext) {
                int nextInt = it.nextInt();
                FrameLayout frameLayout = new FrameLayout(getContext());
                StepViewProperties stepViewProperties3 = this.stepProperties;
                int i7 = (stepViewProperties3 == null ? null : stepViewProperties3).width;
                if (stepViewProperties3 == null) {
                    stepViewProperties3 = null;
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i7, stepViewProperties3.height);
                StepViewProperties stepViewProperties4 = this.stepProperties;
                int i8 = (stepViewProperties4 == null ? null : stepViewProperties4).horizontalMargin;
                if (stepViewProperties4 == null) {
                    stepViewProperties4 = null;
                }
                layoutParams.setMargins(i8, 0, stepViewProperties4.horizontalMargin, 0);
                frameLayout.setLayoutParams(layoutParams);
                int i9 = this.maxLevel;
                StepViewProperties stepViewProperties5 = this.stepProperties;
                float f = (stepViewProperties5 == null ? null : stepViewProperties5).smallRadius;
                if (stepViewProperties5 == null) {
                    stepViewProperties5 = null;
                }
                float f2 = stepViewProperties5.largeRadius;
                float[] fArr = new float[8];
                for (int i10 = 0; i10 < 8; i10++) {
                    fArr[i10] = f;
                }
                if (nextInt == 1) {
                    for (int i11 : LEFT_CORNERS_INDICES) {
                        fArr[i11] = f2;
                    }
                }
                if (nextInt == i9) {
                    for (int i12 : RIGHT_CORNERS_INDICES) {
                        fArr[i12] = f2;
                    }
                }
                ShapeDrawable shapeDrawable2 = new ShapeDrawable(new RoundRectShape(fArr, null, null));
                shapeDrawable2.getPaint().setColor(this.emptyRectangleColor);
                frameLayout.setBackground(shapeDrawable2);
                arrayList.add(frameLayout);
            }
            int size = arrayList.size();
            while (i6 < size) {
                Object obj = arrayList.get(i6);
                i6++;
                FrameLayout frameLayout2 = (FrameLayout) obj;
                LinearLayout linearLayout3 = this.rootView;
                if (linearLayout3 == null) {
                    linearLayout3 = null;
                }
                linearLayout3.addView(frameLayout2);
            }
        }
        this.currentLevel = i;
        LinearLayout linearLayout4 = this.rootView;
        if (linearLayout4 == null) {
            linearLayout4 = null;
        }
        ImageView imageView2 = (ImageView) linearLayout4.requireViewById(BACKLIGHT_ICON_ID);
        ShapeDrawable shapeDrawable3 = (ShapeDrawable) imageView2.getBackground();
        if (this.currentLevel == 0) {
            imageView2.setColorFilter(this.dimmedIconColor);
            updateColor(shapeDrawable3, this.dimmedIconBackgroundColor);
        } else {
            imageView2.setColorFilter(this.defaultIconColor);
            updateColor(shapeDrawable3, this.defaultIconBackgroundColor);
        }
        LinearLayout linearLayout5 = this.rootView;
        if (linearLayout5 == null) {
            linearLayout5 = null;
        }
        IntProgressionIterator it2 = RangesKt___RangesKt.until(1, linearLayout5.getChildCount()).iterator();
        while (it2.hasNext) {
            int nextInt2 = it2.nextInt();
            LinearLayout linearLayout6 = this.rootView;
            if (linearLayout6 == null) {
                linearLayout6 = null;
            }
            updateColor((ShapeDrawable) linearLayout6.getChildAt(nextInt2).getBackground(), nextInt2 <= this.currentLevel ? this.filledRectangleColor : this.emptyRectangleColor);
        }
        LinearLayout linearLayout7 = this.rootView;
        if (linearLayout7 == null) {
            linearLayout7 = null;
        }
        int i13 = StringCompanionObject.$r8$clinit;
        linearLayout7.setContentDescription(String.format(this.levelContentDescription, Arrays.copyOf(new Object[]{Integer.valueOf(this.currentLevel), Integer.valueOf(this.maxLevel)}, 2)));
        LinearLayout linearLayout8 = this.rootView;
        (linearLayout8 != null ? linearLayout8 : null).sendAccessibilityEvent(4);
    }

    public KeyboardBacklightDialog(Context context, int i, int i2, int i3) {
        super(context, i3);
        this.dialogBottomMargin = 208;
        this.filledRectangleColor = context.getColor(android.R.color.secondary_text_inverse_when_activated_material);
        this.emptyRectangleColor = context.getColor(android.R.color.secondary_material_settings);
        this.backgroundColor = context.getColor(android.R.color.side_fps_toast_background);
        this.defaultIconColor = context.getColor(android.R.color.resolver_profile_tab_text);
        this.defaultIconBackgroundColor = context.getColor(android.R.color.secondary_text_inverse_when_activated_material);
        this.dimmedIconColor = context.getColor(android.R.color.search_url_text_material_light);
        this.dimmedIconBackgroundColor = context.getColor(android.R.color.surface_header_light);
        this.levelContentDescription = context.getString(R.string.keyboard_backlight_value);
        this.currentLevel = i;
        this.maxLevel = i2;
    }
}
