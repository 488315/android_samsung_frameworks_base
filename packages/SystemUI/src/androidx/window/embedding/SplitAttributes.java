package androidx.window.embedding;

import androidx.window.core.AndroidLogger;
import androidx.window.core.FailedSpecification;
import androidx.window.core.SpecificationComputer;
import androidx.window.core.ValidSpecification;
import androidx.window.core.VerificationMode;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SplitAttributes {
    public static final String TAG;
    public final EmbeddingAnimationBackground animationBackground;
    public final DividerAttributes dividerAttributes;
    public final LayoutDirection layoutDirection;
    public final SplitType splitType;

    public final class Builder {
        public SplitType splitType = SplitType.SPLIT_TYPE_EQUAL;
        public LayoutDirection layoutDirection = LayoutDirection.LOCALE;
        public EmbeddingAnimationBackground animationBackground = EmbeddingAnimationBackground.DEFAULT;
        public DividerAttributes dividerAttributes = DividerAttributes.NO_DIVIDER;
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class LayoutDirection {
        public static final LayoutDirection BOTTOM_TO_TOP;
        public static final LayoutDirection LEFT_TO_RIGHT;
        public static final LayoutDirection LOCALE;
        public static final LayoutDirection RIGHT_TO_LEFT;
        public static final LayoutDirection TOP_TO_BOTTOM;
        public final String description;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            LOCALE = new LayoutDirection("LOCALE", 0);
            LEFT_TO_RIGHT = new LayoutDirection("LEFT_TO_RIGHT", 1);
            RIGHT_TO_LEFT = new LayoutDirection("RIGHT_TO_LEFT", 2);
            TOP_TO_BOTTOM = new LayoutDirection("TOP_TO_BOTTOM", 3);
            BOTTOM_TO_TOP = new LayoutDirection("BOTTOM_TO_TOP", 4);
        }

        private LayoutDirection(String str, int i) {
            this.description = str;
        }

        public final String toString() {
            return this.description;
        }
    }

    public final class SplitType {
        public final String description;
        public final float value;
        public static final Companion Companion = new Companion(null);
        public static final SplitType SPLIT_TYPE_EXPAND = new SplitType("expandContainers", 0.0f);
        public static final SplitType SPLIT_TYPE_EQUAL = Companion.ratio(0.5f);
        public static final SplitType SPLIT_TYPE_HINGE = new SplitType("hinge", -1.0f);

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v2, types: [androidx.window.core.FailedSpecification] */
            public static SplitType ratio(final float f) {
                SpecificationComputer.Companion companion = SpecificationComputer.Companion;
                Float fValueOf = Float.valueOf(f);
                String str = SplitAttributes.TAG;
                VerificationMode verificationMode = VerificationMode.STRICT;
                AndroidLogger androidLogger = AndroidLogger.INSTANCE;
                companion.getClass();
                ValidSpecification validSpecification = new ValidSpecification(fValueOf, str, verificationMode, androidLogger);
                if (!((Boolean) new Function1() { // from class: androidx.window.embedding.SplitAttributes$SplitType$Companion$ratio$checkedRatio$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ((Number) obj).floatValue();
                        double d = f;
                        return Boolean.valueOf(0.0d <= d && d <= 1.0d && ArraysKt___ArraysKt.indexOf(new Float[]{Float.valueOf(0.0f), Float.valueOf(1.0f)}, Float.valueOf(f)) < 0);
                    }
                }.mo781invoke(validSpecification.value)).booleanValue()) {
                    validSpecification = new FailedSpecification(validSpecification.value, validSpecification.tag, "Ratio must be in range (0.0, 1.0). Use SplitType.expandContainers() instead of 0 or 1.", validSpecification.logger, validSpecification.verificationMode);
                }
                Object objCompute = validSpecification.compute();
                objCompute.getClass();
                float fFloatValue = ((Number) objCompute).floatValue();
                return new SplitType("ratio:" + fFloatValue, fFloatValue);
            }

            private Companion() {
            }
        }

        public SplitType(String str, float f) {
            this.description = str;
            this.value = f;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitType)) {
                return false;
            }
            SplitType splitType = (SplitType) obj;
            return this.value == splitType.value && Intrinsics.areEqual(this.description, splitType.description);
        }

        public final int hashCode() {
            return (Float.hashCode(this.value) * 31) + this.description.hashCode();
        }

        public final String toString() {
            return this.description;
        }
    }

    static {
        new Companion(null);
        TAG = "SplitAttributes";
    }

    public SplitAttributes() {
        this(null, null, null, null, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitAttributes)) {
            return false;
        }
        SplitAttributes splitAttributes = (SplitAttributes) obj;
        return Intrinsics.areEqual(this.splitType, splitAttributes.splitType) && Intrinsics.areEqual(this.layoutDirection, splitAttributes.layoutDirection) && Intrinsics.areEqual(this.animationBackground, splitAttributes.animationBackground) && Intrinsics.areEqual(this.dividerAttributes, splitAttributes.dividerAttributes);
    }

    public final int hashCode() {
        return this.dividerAttributes.hashCode() + ((this.animationBackground.hashCode() + ((this.layoutDirection.hashCode() + (this.splitType.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SplitAttributes:{splitType=" + this.splitType + ", layoutDir=" + this.layoutDirection + ", animationBackground=" + this.animationBackground + ", dividerAttributes=" + this.dividerAttributes + " }";
    }

    public SplitAttributes(SplitType splitType) {
        this(splitType, null, null, null, 14, null);
    }

    public SplitAttributes(SplitType splitType, LayoutDirection layoutDirection) {
        this(splitType, layoutDirection, null, null, 12, null);
    }

    public SplitAttributes(SplitType splitType, LayoutDirection layoutDirection, EmbeddingAnimationBackground embeddingAnimationBackground) {
        this(splitType, layoutDirection, embeddingAnimationBackground, null, 8, null);
    }

    public SplitAttributes(SplitType splitType, LayoutDirection layoutDirection, EmbeddingAnimationBackground embeddingAnimationBackground, DividerAttributes dividerAttributes) {
        this.splitType = splitType;
        this.layoutDirection = layoutDirection;
        this.animationBackground = embeddingAnimationBackground;
        this.dividerAttributes = dividerAttributes;
    }

    public /* synthetic */ SplitAttributes(SplitType splitType, LayoutDirection layoutDirection, EmbeddingAnimationBackground embeddingAnimationBackground, DividerAttributes dividerAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SplitType.SPLIT_TYPE_EQUAL : splitType, (i & 2) != 0 ? LayoutDirection.LOCALE : layoutDirection, (i & 4) != 0 ? EmbeddingAnimationBackground.DEFAULT : embeddingAnimationBackground, (i & 8) != 0 ? DividerAttributes.NO_DIVIDER : dividerAttributes);
    }
}
