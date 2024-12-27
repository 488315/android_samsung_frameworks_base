package com.samsung.android.wallpaper.colortheme.monet;

import android.hardware.scontext.SContextConstants;
import android.util.Pair;

import com.android.internal.graphics.cam.Cam;

import java.util.Arrays;
import java.util.List;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'SPRITZ' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes6.dex */
public final class Style {
    private static final /* synthetic */ Style[] $VALUES;
    public static final Style CONTENT;
    public static final Style EXPRESSIVE;
    public static final Style FRUIT_SALAD;
    public static final Style RAINBOW;
    public static final Style SPRITZ;
    public static final Style TONAL_SPOT;
    public static final Style VIBRANT;
    private final CoreSpec coreSpec;

    private static /* synthetic */ Style[] $values() {
        return new Style[] {SPRITZ, TONAL_SPOT, VIBRANT, EXPRESSIVE, RAINBOW, FRUIT_SALAD, CONTENT};
    }

    public static Style valueOf(String name) {
        return (Style) Enum.valueOf(Style.class, name);
    }

    public static Style[] values() {
        return (Style[]) $VALUES.clone();
    }

    static {
        final double d = 12.0d;
        final double d2 = 8.0d;
        final double d3 = 16.0d;
        final double d4 = 2.0d;
        SPRITZ =
                new Style(
                        "SPRITZ",
                        0,
                        new CoreSpec(
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d) { // from class:
                                                     // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d2) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d2;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d3) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d3;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d4) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d4;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d4) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d4;
                                            }
                                        })));
        final double d5 = 36.0d;
        TonalSpec tonalSpec =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d5) { // from class:
                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d5;
                            }
                        });
        TonalSpec tonalSpec2 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d3) { // from class:
                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d3;
                            }
                        });
        final double d6 = 60.0d;
        final double d7 = 24.0d;
        TonalSpec tonalSpec3 =
                new TonalSpec(
                        new Hue(
                                d6) { // from class:
                                      // com.samsung.android.wallpaper.colortheme.monet.HueAdd
                            private final double amountDegrees;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return ColorScheme.wrapDegreesDouble(
                                        sourceColor.getHue() + this.amountDegrees);
                            }

                            public final double getAmountDegrees() {
                                return this.amountDegrees;
                            }

                            {
                                this.amountDegrees = d6;
                            }
                        },
                        new Chroma(
                                d7) { // from class:
                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d7;
                            }
                        });
        final double d8 = 4.0d;
        TONAL_SPOT =
                new Style(
                        "TONAL_SPOT",
                        1,
                        new CoreSpec(
                                tonalSpec,
                                tonalSpec2,
                                tonalSpec3,
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d8) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d8;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d2) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d2;
                                            }
                                        })));
        final double d9 = 32.0d;
        final double d10 = 10.0d;
        final double d11 = 12.0d;
        VIBRANT =
                new Style(
                        "VIBRANT",
                        2,
                        new CoreSpec(
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma() { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaMaxOut
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return 130.0d;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueVibrantSecondary
                                            private final List<Pair> hueToRotations =
                                                    Arrays.asList(
                                                            new Pair(0, 18),
                                                            new Pair(41, 15),
                                                            new Pair(61, 10),
                                                            new Pair(101, 12),
                                                            new Pair(131, 15),
                                                            new Pair(181, 18),
                                                            new Pair(251, 15),
                                                            new Pair(301, 12),
                                                            new Pair(360, 12));

                                            public final List<Pair> getHueToRotations() {
                                                return this.hueToRotations;
                                            }

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return getHueRotation(
                                                        sourceColor.getHue(), this.hueToRotations);
                                            }
                                        },
                                        new Chroma(
                                                d7) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d7;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueVibrantTertiary
                                            private final List<Pair> hueToRotations =
                                                    Arrays.asList(
                                                            new Pair(0, 35),
                                                            new Pair(41, 30),
                                                            new Pair(61, 20),
                                                            new Pair(101, 25),
                                                            new Pair(131, 30),
                                                            new Pair(181, 35),
                                                            new Pair(251, 30),
                                                            new Pair(301, 25),
                                                            new Pair(360, 25));

                                            public final List<Pair> getHueToRotations() {
                                                return this.hueToRotations;
                                            }

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return getHueRotation(
                                                        sourceColor.getHue(), this.hueToRotations);
                                            }
                                        },
                                        new Chroma(
                                                d9) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d9;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d10) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d10;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d11) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d11;
                                            }
                                        })));
        final double d12 = 240.0d;
        final double d13 = 40.0d;
        final double d14 = 15.0d;
        final double d15 = 8.0d;
        final double d16 = 12.0d;
        EXPRESSIVE =
                new Style(
                        "EXPRESSIVE",
                        3,
                        new CoreSpec(
                                new TonalSpec(
                                        new Hue(
                                                d12) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.HueAdd
                                            private final double amountDegrees;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return ColorScheme.wrapDegreesDouble(
                                                        sourceColor.getHue() + this.amountDegrees);
                                            }

                                            public final double getAmountDegrees() {
                                                return this.amountDegrees;
                                            }

                                            {
                                                this.amountDegrees = d12;
                                            }
                                        },
                                        new Chroma(
                                                d13) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d13;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueExpressiveSecondary
                                            private final List<Pair> hueToRotations =
                                                    Arrays.asList(
                                                            new Pair(0, 45),
                                                            new Pair(21, 95),
                                                            new Pair(51, 45),
                                                            new Pair(121, 20),
                                                            new Pair(151, 45),
                                                            new Pair(191, 90),
                                                            new Pair(271, 45),
                                                            new Pair(321, 45),
                                                            new Pair(360, 45));

                                            public final List<Pair> getHueToRotations() {
                                                return this.hueToRotations;
                                            }

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return getHueRotation(
                                                        sourceColor.getHue(), this.hueToRotations);
                                            }
                                        },
                                        new Chroma(
                                                d7) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d7;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueExpressiveTertiary
                                            private final List<Pair> hueToRotations =
                                                    Arrays.asList(
                                                            new Pair(0, 120),
                                                            new Pair(21, 120),
                                                            new Pair(51, 20),
                                                            new Pair(121, 45),
                                                            new Pair(151, 20),
                                                            new Pair(191, 15),
                                                            new Pair(271, 20),
                                                            new Pair(321, 120),
                                                            new Pair(360, 120));

                                            public final List<Pair> getHueToRotations() {
                                                return this.hueToRotations;
                                            }

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return getHueRotation(
                                                        sourceColor.getHue(), this.hueToRotations);
                                            }
                                        },
                                        new Chroma(
                                                d9) { // from class:
                                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d9;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue(
                                                d14) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.HueAdd
                                            private final double amountDegrees;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return ColorScheme.wrapDegreesDouble(
                                                        sourceColor.getHue() + this.amountDegrees);
                                            }

                                            public final double getAmountDegrees() {
                                                return this.amountDegrees;
                                            }

                                            {
                                                this.amountDegrees = d14;
                                            }
                                        },
                                        new Chroma(
                                                d15) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d15;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue(
                                                d14) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.HueAdd
                                            private final double amountDegrees;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return ColorScheme.wrapDegreesDouble(
                                                        sourceColor.getHue() + this.amountDegrees);
                                            }

                                            public final double getAmountDegrees() {
                                                return this.amountDegrees;
                                            }

                                            {
                                                this.amountDegrees = d14;
                                            }
                                        },
                                        new Chroma(
                                                d16) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d16;
                                            }
                                        })));
        final double d17 = 48.0d;
        TonalSpec tonalSpec4 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d17) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d17;
                            }
                        });
        final double d18 = 16.0d;
        TonalSpec tonalSpec5 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d18) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d18;
                            }
                        });
        final double d19 = 60.0d;
        TonalSpec tonalSpec6 =
                new TonalSpec(
                        new Hue(
                                d19) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.HueAdd
                            private final double amountDegrees;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return ColorScheme.wrapDegreesDouble(
                                        sourceColor.getHue() + this.amountDegrees);
                            }

                            public final double getAmountDegrees() {
                                return this.amountDegrees;
                            }

                            {
                                this.amountDegrees = d19;
                            }
                        },
                        new Chroma(
                                d7) { // from class:
                                      // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d7;
                            }
                        });
        Hue hue =
                new Hue() { // from class: com.samsung.android.wallpaper.colortheme.monet.HueSource
                    @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                    public double get(Cam sourceColor) {
                        return sourceColor.getHue();
                    }
                };
        final double d20 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        RAINBOW =
                new Style(
                        "RAINBOW",
                        4,
                        new CoreSpec(
                                tonalSpec4,
                                tonalSpec5,
                                tonalSpec6,
                                new TonalSpec(
                                        hue,
                                        new Chroma(
                                                d20) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d20;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d20) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d20;
                                            }
                                        })));
        final double d21 = 50.0d;
        TonalSpec tonalSpec7 =
                new TonalSpec(
                        new Hue(
                                d21) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.HueSubtract
                            private final double amountDegrees;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return ColorScheme.wrapDegreesDouble(
                                        sourceColor.getHue() - this.amountDegrees);
                            }

                            public final double getAmountDegrees() {
                                return this.amountDegrees;
                            }

                            {
                                this.amountDegrees = d21;
                            }
                        },
                        new Chroma(
                                d17) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d17;
                            }
                        });
        final double d22 = 36.0d;
        TonalSpec tonalSpec8 =
                new TonalSpec(
                        new Hue(
                                d21) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.HueSubtract
                            private final double amountDegrees;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return ColorScheme.wrapDegreesDouble(
                                        sourceColor.getHue() - this.amountDegrees);
                            }

                            public final double getAmountDegrees() {
                                return this.amountDegrees;
                            }

                            {
                                this.amountDegrees = d21;
                            }
                        },
                        new Chroma(
                                d22) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d22;
                            }
                        });
        TonalSpec tonalSpec9 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d22) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                            private final double chroma;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return this.chroma;
                            }

                            public final double getChroma() {
                                return this.chroma;
                            }

                            {
                                this.chroma = d22;
                            }
                        });
        final double d23 = 10.0d;
        final double d24 = 16.0d;
        FRUIT_SALAD =
                new Style(
                        "FRUIT_SALAD",
                        5,
                        new CoreSpec(
                                tonalSpec7,
                                tonalSpec8,
                                tonalSpec9,
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d23) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d23;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d24) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaConstant
                                            private final double chroma;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return this.chroma;
                                            }

                                            public final double getChroma() {
                                                return this.chroma;
                                            }

                                            {
                                                this.chroma = d24;
                                            }
                                        })));
        TonalSpec tonalSpec10 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma() { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return sourceColor.getChroma();
                            }
                        });
        final double d25 = 0.33d;
        TonalSpec tonalSpec11 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d25) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaMultiple
                            private final double multiple;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return sourceColor.getChroma() * this.multiple;
                            }

                            public final double getMultiple() {
                                return this.multiple;
                            }

                            {
                                this.multiple = d25;
                            }
                        });
        final double d26 = 0.66d;
        TonalSpec tonalSpec12 =
                new TonalSpec(
                        new Hue() { // from class:
                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                            public double get(Cam sourceColor) {
                                return sourceColor.getHue();
                            }
                        },
                        new Chroma(
                                d26) { // from class:
                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaMultiple
                            private final double multiple;

                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                            public double get(Cam sourceColor) {
                                return sourceColor.getChroma() * this.multiple;
                            }

                            public final double getMultiple() {
                                return this.multiple;
                            }

                            {
                                this.multiple = d26;
                            }
                        });
        final double d27 = 0.0833d;
        final double d28 = 0.1666d;
        CONTENT =
                new Style(
                        "CONTENT",
                        6,
                        new CoreSpec(
                                tonalSpec10,
                                tonalSpec11,
                                tonalSpec12,
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d27) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaMultiple
                                            private final double multiple;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getChroma() * this.multiple;
                                            }

                                            public final double getMultiple() {
                                                return this.multiple;
                                            }

                                            {
                                                this.multiple = d27;
                                            }
                                        }),
                                new TonalSpec(
                                        new Hue() { // from class:
                                                    // com.samsung.android.wallpaper.colortheme.monet.HueSource
                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Hue
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getHue();
                                            }
                                        },
                                        new Chroma(
                                                d28) { // from class:
                                                       // com.samsung.android.wallpaper.colortheme.monet.ChromaMultiple
                                            private final double multiple;

                                            @Override // com.samsung.android.wallpaper.colortheme.monet.Chroma
                                            public double get(Cam sourceColor) {
                                                return sourceColor.getChroma() * this.multiple;
                                            }

                                            public final double getMultiple() {
                                                return this.multiple;
                                            }

                                            {
                                                this.multiple = d28;
                                            }
                                        })));
        $VALUES = $values();
    }

    public final CoreSpec getCoreSpec() {
        return this.coreSpec;
    }

    private Style(String str, int i, CoreSpec coreSpec) {
        this.coreSpec = coreSpec;
    }
}
