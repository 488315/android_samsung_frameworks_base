package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;
import com.android.systemui.monet.Chroma;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Hue;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'SPRITZ' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Style {
    public static final /* synthetic */ Style[] $VALUES;
    public static final Style CONTENT;
    public static final Style EXPRESSIVE;
    public static final Style FRUIT_SALAD;
    public static final Style MONOCHROMATIC;
    public static final Style RAINBOW;
    public static final Style SPRITZ;
    public static final Style TONAL_SPOT;
    public static final Style VIBRANT;
    private final CoreSpec coreSpec;

    static {
        final double d = 12.0d;
        final double d2 = 8.0d;
        final double d3 = 16.0d;
        final double d4 = 2.0d;
        Style style = new Style("SPRITZ", 0, new CoreSpec(new TonalSpec(new HueSource(), new Chroma(d) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d2) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d2;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d3) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d3;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d4) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d4;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d4) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d4;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        SPRITZ = style;
        final double d5 = 36.0d;
        final double d6 = 60.0d;
        final double d7 = 24.0d;
        final double d8 = 6.0d;
        Style style2 = new Style("TONAL_SPOT", 1, new CoreSpec(new TonalSpec(new HueSource(), new Chroma(d5) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d5;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d3) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d3;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new Hue(d6) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d6;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d7) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d7;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d8) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d8;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d2) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d2;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        TONAL_SPOT = style2;
        final double d9 = 32.0d;
        final double d10 = 10.0d;
        final double d11 = 12.0d;
        Style style3 = new Style("VIBRANT", 2, new CoreSpec(new TonalSpec(new HueSource(), new Chroma() { // from class: com.android.systemui.monet.ChromaMaxOut
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                Chroma.Companion.getClass();
                return Chroma.Companion.MAX_VALUE + 10.0d;
            }
        }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueVibrantSecondary
            public final List hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair(0, 18), new Pair(41, 15), new Pair(61, 10), new Pair(101, 12), new Pair(131, 15), new Pair(181, 18), new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut), 15), new Pair(301, 12), new Pair(360, 12));

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                return Hue.DefaultImpls.getHueRotation(cam.getHue(), this.hueToRotations);
            }
        }, new Chroma(d7) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d7;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueVibrantTertiary
            public final List hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair(0, 35), new Pair(41, 30), new Pair(61, 20), new Pair(101, 25), new Pair(131, 30), new Pair(181, 35), new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut), 30), new Pair(301, 25), new Pair(360, 25));

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                return Hue.DefaultImpls.getHueRotation(cam.getHue(), this.hueToRotations);
            }
        }, new Chroma(d9) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d9;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d10) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d10;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d11) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d11;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        VIBRANT = style3;
        final double d12 = 240.0d;
        final double d13 = 40.0d;
        TonalSpec tonalSpec = new TonalSpec(new Hue(d12) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d12;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d13) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d13;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        TonalSpec tonalSpec2 = new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueExpressiveSecondary
            public final List hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair(0, 45), new Pair(21, 95), new Pair(51, 45), new Pair(121, 20), new Pair(151, 45), new Pair(191, 90), new Pair(271, 45), new Pair(321, 45), new Pair(360, 45));

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                return Hue.DefaultImpls.getHueRotation(cam.getHue(), this.hueToRotations);
            }
        }, new Chroma(d7) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d7;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d14 = 32.0d;
        TonalSpec tonalSpec3 = new TonalSpec(new Hue() { // from class: com.android.systemui.monet.HueExpressiveTertiary
            public final List hueToRotations = CollectionsKt__CollectionsKt.listOf(new Pair(0, 120), new Pair(21, 120), new Pair(51, 20), new Pair(121, 45), new Pair(151, 20), new Pair(191, 15), new Pair(271, 20), new Pair(321, 120), new Pair(360, 120));

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                return Hue.DefaultImpls.getHueRotation(cam.getHue(), this.hueToRotations);
            }
        }, new Chroma(d14) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d14;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d15 = 15.0d;
        final double d16 = 8.0d;
        final double d17 = 12.0d;
        Style style4 = new Style("EXPRESSIVE", 3, new CoreSpec(tonalSpec, tonalSpec2, tonalSpec3, new TonalSpec(new Hue(d15) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d15;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d16) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d16;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new Hue(d15) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d15;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d17) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d17;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        EXPRESSIVE = style4;
        final double d18 = 48.0d;
        TonalSpec tonalSpec4 = new TonalSpec(new HueSource(), new Chroma(d18) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d18;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d19 = 16.0d;
        TonalSpec tonalSpec5 = new TonalSpec(new HueSource(), new Chroma(d19) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d19;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d20 = 60.0d;
        final double d21 = 24.0d;
        TonalSpec tonalSpec6 = new TonalSpec(new Hue(d20) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d20;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d21) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d21;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d22 = 0.0d;
        Style style5 = new Style("RAINBOW", 4, new CoreSpec(tonalSpec4, tonalSpec5, tonalSpec6, new TonalSpec(new HueSource(), new Chroma(d22) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d22;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d22) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d22;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        RAINBOW = style5;
        final double d23 = 50.0d;
        final double d24 = 48.0d;
        TonalSpec tonalSpec7 = new TonalSpec(new Hue(d23) { // from class: com.android.systemui.monet.HueSubtract
            public final double amountDegrees;

            {
                this.amountDegrees = d23;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() - this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d24) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d24;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d25 = 36.0d;
        TonalSpec tonalSpec8 = new TonalSpec(new Hue(d23) { // from class: com.android.systemui.monet.HueSubtract
            public final double amountDegrees;

            {
                this.amountDegrees = d23;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue = cam.getHue() - this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue);
            }
        }, new Chroma(d25) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d25;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        TonalSpec tonalSpec9 = new TonalSpec(new HueSource(), new Chroma(d25) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d25;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        });
        final double d26 = 10.0d;
        final double d27 = 16.0d;
        Style style6 = new Style("FRUIT_SALAD", 5, new CoreSpec(tonalSpec7, tonalSpec8, tonalSpec9, new TonalSpec(new HueSource(), new Chroma(d26) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d26;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d27) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d27;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        FRUIT_SALAD = style6;
        TonalSpec tonalSpec10 = new TonalSpec(new HueSource(), new Chroma() { // from class: com.android.systemui.monet.ChromaSource
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma();
            }
        });
        final double d28 = 0.33d;
        TonalSpec tonalSpec11 = new TonalSpec(new HueSource(), new Chroma(d28) { // from class: com.android.systemui.monet.ChromaMultiple
            public final double multiple;

            {
                this.multiple = d28;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() * this.multiple;
            }
        });
        final double d29 = 0.66d;
        TonalSpec tonalSpec12 = new TonalSpec(new HueSource(), new Chroma(d29) { // from class: com.android.systemui.monet.ChromaMultiple
            public final double multiple;

            {
                this.multiple = d29;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() * this.multiple;
            }
        });
        final double d30 = 0.0833d;
        final double d31 = 0.1666d;
        Style style7 = new Style("CONTENT", 6, new CoreSpec(tonalSpec10, tonalSpec11, tonalSpec12, new TonalSpec(new HueSource(), new Chroma(d30) { // from class: com.android.systemui.monet.ChromaMultiple
            public final double multiple;

            {
                this.multiple = d30;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() * this.multiple;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d31) { // from class: com.android.systemui.monet.ChromaMultiple
            public final double multiple;

            {
                this.multiple = d31;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() * this.multiple;
            }
        })));
        CONTENT = style7;
        final double d32 = 0.0d;
        Style style8 = new Style("MONOCHROMATIC", 7, new CoreSpec(new TonalSpec(new HueSource(), new Chroma(d32) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d32;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d32) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d32;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d32) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d32;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d32) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d32;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d32) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d32;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        MONOCHROMATIC = style8;
        HueSource hueSource = new HueSource();
        final Chroma chroma = new Chroma() { // from class: com.android.systemui.monet.ChromaSource
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma();
            }
        };
        final double d33 = 20.0d;
        Chroma.Companion.getClass();
        final double d34 = Chroma.Companion.MAX_VALUE;
        TonalSpec tonalSpec13 = new TonalSpec(hueSource, new Chroma(chroma, d33, d34) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma;
                this.minVal = d33;
                this.maxVal = d34;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        });
        final double d35 = 10.0d;
        Hue hue = new Hue(d35) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d35;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue2 = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue2);
            }
        };
        final double d36 = 0.85d;
        final Chroma chroma2 = new Chroma(d36) { // from class: com.android.systemui.monet.ChromaMultiple
            public final double multiple;

            {
                this.multiple = d36;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() * this.multiple;
            }
        };
        final double d37 = 17.0d;
        final double d38 = 40.0d;
        TonalSpec tonalSpec14 = new TonalSpec(hue, new Chroma(chroma2, d37, d38) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma2;
                this.minVal = d37;
                this.maxVal = d38;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        });
        final double d39 = 20.0d;
        Hue hue2 = new Hue(d39) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d39;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue22 = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue22);
            }
        };
        final Chroma chroma3 = new Chroma(d39) { // from class: com.android.systemui.monet.ChromaAdd
            public final double amount;

            {
                this.amount = d39;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma() + this.amount;
            }
        };
        final double d40 = 50.0d;
        final double d41 = 0.0d;
        final double d42 = 20.0d;
        Style style9 = new Style("CLOCK", 8, new CoreSpec(tonalSpec13, tonalSpec14, new TonalSpec(hue2, new Chroma(chroma3, d40, d34) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma3;
                this.minVal = d40;
                this.maxVal = d34;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        }), new TonalSpec(new HueSource(), new Chroma(d41) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d41;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d41) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d41;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })));
        HueSource hueSource2 = new HueSource();
        final Chroma chroma4 = new Chroma() { // from class: com.android.systemui.monet.ChromaSource
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma();
            }
        };
        final double d43 = 70.0d;
        TonalSpec tonalSpec15 = new TonalSpec(hueSource2, new Chroma(chroma4, d43, d34) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma4;
                this.minVal = d43;
                this.maxVal = d34;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        });
        Hue hue3 = new Hue(d42) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d42;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue22 = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue22);
            }
        };
        final Chroma chroma5 = new Chroma() { // from class: com.android.systemui.monet.ChromaSource
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma();
            }
        };
        TonalSpec tonalSpec16 = new TonalSpec(hue3, new Chroma(chroma5, d43, d34) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma5;
                this.minVal = d43;
                this.maxVal = d34;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        });
        final double d44 = 60.0d;
        Hue hue4 = new Hue(d44) { // from class: com.android.systemui.monet.HueAdd
            public final double amountDegrees;

            {
                this.amountDegrees = d44;
            }

            @Override // com.android.systemui.monet.Hue
            public final double get(Cam cam) {
                ColorScheme.Companion companion = ColorScheme.Companion;
                double hue22 = cam.getHue() + this.amountDegrees;
                companion.getClass();
                return ColorScheme.Companion.wrapDegreesDouble(hue22);
            }
        };
        final Chroma chroma6 = new Chroma() { // from class: com.android.systemui.monet.ChromaSource
            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return cam.getChroma();
            }
        };
        final double d45 = 0.0d;
        $VALUES = new Style[]{style, style2, style3, style4, style5, style6, style7, style8, style9, new Style("CLOCK_VIBRANT", 9, new CoreSpec(tonalSpec15, tonalSpec16, new TonalSpec(hue4, new Chroma(chroma6, d43, d34) { // from class: com.android.systemui.monet.ChromaBound
            public final Chroma baseChroma;
            public final double maxVal;
            public final double minVal;

            {
                this.baseChroma = chroma6;
                this.minVal = d43;
                this.maxVal = d34;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return Math.min(Math.max(this.baseChroma.get(cam), this.minVal), this.maxVal);
            }
        }), new TonalSpec(new HueSource(), new Chroma(d45) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d45;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        }), new TonalSpec(new HueSource(), new Chroma(d45) { // from class: com.android.systemui.monet.ChromaConstant
            public final double chroma;

            {
                this.chroma = d45;
            }

            @Override // com.android.systemui.monet.Chroma
            public final double get(Cam cam) {
                return this.chroma;
            }
        })))};
    }

    private Style(String str, int i, CoreSpec coreSpec) {
        this.coreSpec = coreSpec;
    }

    public static Style valueOf(String str) {
        return (Style) Enum.valueOf(Style.class, str);
    }

    public static Style[] values() {
        return (Style[]) $VALUES.clone();
    }

    /* renamed from: getCoreSpec$frameworks__base__packages__SystemUI__monet__android_common__monet */
    public final CoreSpec m163x334ec08f() {
        return this.coreSpec;
    }
}
