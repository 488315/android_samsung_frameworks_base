package com.google.ux.material.libmonet.dynamiccolor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MaterialDynamicColors {
    public final boolean isExtendedFidelity;

    public MaterialDynamicColors() {
        this.isExtendedFidelity = false;
    }

    public static DynamicColor inverseSurface() {
        return new DynamicColor("inverse_surface", new MaterialDynamicColors$$ExternalSyntheticLambda7(6), new MaterialDynamicColors$$ExternalSyntheticLambda7(7), false, null, null, null, null);
    }

    public static boolean isMonochrome(DynamicScheme dynamicScheme) {
        return dynamicScheme.variant == Variant.MONOCHROME;
    }

    public static DynamicColor surfaceContainerLow() {
        return new DynamicColor("surface_container_low", new MaterialDynamicColors$$ExternalSyntheticLambda3(22), new MaterialDynamicColors$$ExternalSyntheticLambda3(23), true, null, null, null, null);
    }

    public final DynamicColor error() {
        return new DynamicColor("error", new MaterialDynamicColors$$ExternalSyntheticLambda0(29), new MaterialDynamicColors$$ExternalSyntheticLambda1(1), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 15));
    }

    public final DynamicColor errorContainer() {
        return new DynamicColor("error_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(10), new MaterialDynamicColors$$ExternalSyntheticLambda0(11), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 7));
    }

    public final boolean isFidelity(DynamicScheme dynamicScheme) {
        Variant variant;
        Variant variant2;
        return !(!this.isExtendedFidelity || (variant2 = dynamicScheme.variant) == Variant.MONOCHROME || variant2 == Variant.NEUTRAL) || (variant = dynamicScheme.variant) == Variant.FIDELITY || variant == Variant.CONTENT;
    }

    public final DynamicColor onPrimaryFixed() {
        return new DynamicColor("on_primary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda7(16), new MaterialDynamicColors$$ExternalSyntheticLambda7(25), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 4), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 9), new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    public final DynamicColor onSurface() {
        return new DynamicColor("on_surface", new MaterialDynamicColors$$ExternalSyntheticLambda0(0), new MaterialDynamicColors$$ExternalSyntheticLambda1(0), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(4.5d, 7.0d, 11.0d, 21.0d), null);
    }

    public final DynamicColor outlineVariant() {
        return new DynamicColor("outline_variant", new MaterialDynamicColors$$ExternalSyntheticLambda1(14), new MaterialDynamicColors$$ExternalSyntheticLambda1(15), false, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), null);
    }

    public final DynamicColor primary() {
        return new DynamicColor("primary", new MaterialDynamicColors$$ExternalSyntheticLambda1(20), new MaterialDynamicColors$$ExternalSyntheticLambda1(21), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 25));
    }

    public final DynamicColor primaryContainer() {
        return new DynamicColor("primary_container", new MaterialDynamicColors$$ExternalSyntheticLambda3(5), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 28), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 29));
    }

    public final DynamicColor primaryFixed() {
        return new DynamicColor("primary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda1(16), new MaterialDynamicColors$$ExternalSyntheticLambda1(17), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 24));
    }

    public final DynamicColor primaryFixedDim() {
        return new DynamicColor("primary_fixed_dim", new MaterialDynamicColors$$ExternalSyntheticLambda3(3), new MaterialDynamicColors$$ExternalSyntheticLambda3(4), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 27));
    }

    public final DynamicColor secondary() {
        return new DynamicColor("secondary", new MaterialDynamicColors$$ExternalSyntheticLambda3(1), new MaterialDynamicColors$$ExternalSyntheticLambda3(2), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 26));
    }

    public final DynamicColor secondaryContainer() {
        return new DynamicColor("secondary_container", new MaterialDynamicColors$$ExternalSyntheticLambda0(20), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 14), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 20));
    }

    public final DynamicColor secondaryFixed() {
        return new DynamicColor("secondary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda0(17), new MaterialDynamicColors$$ExternalSyntheticLambda0(18), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 13));
    }

    public final DynamicColor secondaryFixedDim() {
        return new DynamicColor("secondary_fixed_dim", new MaterialDynamicColors$$ExternalSyntheticLambda1(10), new MaterialDynamicColors$$ExternalSyntheticLambda1(11), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 23));
    }

    public final DynamicColor tertiary() {
        return new DynamicColor("tertiary", new MaterialDynamicColors$$ExternalSyntheticLambda1(2), new MaterialDynamicColors$$ExternalSyntheticLambda1(3), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(3.0d, 4.5d, 7.0d, 7.0d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 16));
    }

    public final DynamicColor tertiaryContainer() {
        return new DynamicColor("tertiary_container", new MaterialDynamicColors$$ExternalSyntheticLambda1(4), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 17), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 18));
    }

    public final DynamicColor tertiaryFixed() {
        return new DynamicColor("tertiary_fixed", new MaterialDynamicColors$$ExternalSyntheticLambda0(12), new MaterialDynamicColors$$ExternalSyntheticLambda0(13), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 8));
    }

    public final DynamicColor tertiaryFixedDim() {
        return new DynamicColor("tertiary_fixed_dim", new MaterialDynamicColors$$ExternalSyntheticLambda7(8), new MaterialDynamicColors$$ExternalSyntheticLambda7(9), true, new MaterialDynamicColors$$ExternalSyntheticLambda2(this, 0), null, new ContrastCurve(1.0d, 1.0d, 3.0d, 4.5d), new MaterialDynamicColors$$ExternalSyntheticLambda5(this, 11));
    }

    public MaterialDynamicColors(boolean z) {
        this.isExtendedFidelity = z;
    }
}
