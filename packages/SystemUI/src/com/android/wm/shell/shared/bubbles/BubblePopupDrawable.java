package com.android.wm.shell.shared.bubbles;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;

/* loaded from: classes3.dex */
public final class BubblePopupDrawable extends Drawable {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final BubblePopupDrawable$special$$inlined$observable$1 arrowDirection$delegate;
    public final BubblePopupDrawable$special$$inlined$observable$2 arrowPosition$delegate;
    public final Config config;
    public final Paint paint;
    public final Path path;
    public boolean shouldUpdatePath;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class ArrowDirection {
        public static final /* synthetic */ ArrowDirection[] $VALUES;
        public static final ArrowDirection DOWN;
        public static final ArrowDirection UP;

        static {
            ArrowDirection arrowDirection = new ArrowDirection(ImsProfile.RCS_PROFILE_UP, 0);
            UP = arrowDirection;
            ArrowDirection arrowDirection2 = new ArrowDirection("DOWN", 1);
            DOWN = arrowDirection2;
            ArrowDirection[] arrowDirectionArr = {arrowDirection, arrowDirection2};
            $VALUES = arrowDirectionArr;
            EnumEntriesKt.enumEntries(arrowDirectionArr);
        }

        private ArrowDirection(String str, int i) {
        }

        public static ArrowDirection valueOf(String str) {
            return (ArrowDirection) Enum.valueOf(ArrowDirection.class, str);
        }

        public static ArrowDirection[] values() {
            return (ArrowDirection[]) $VALUES.clone();
        }
    }

    public abstract class ArrowPosition {

        public final class Center extends ArrowPosition {
            public static final Center INSTANCE = new Center();

            private Center() {
                super(null);
            }
        }

        public final class Custom extends ArrowPosition {
            public final float value;

            public Custom(float f) {
                super(null);
                this.value = f;
            }
        }

        public final class End extends ArrowPosition {
            public static final End INSTANCE = null;

            static {
                new End();
            }

            private End() {
                super(null);
            }
        }

        public final class Start extends ArrowPosition {
            public static final Start INSTANCE = null;

            static {
                new Start();
            }

            private Start() {
                super(null);
            }
        }

        public /* synthetic */ ArrowPosition(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private ArrowPosition() {
        }
    }

    public final class Config {
        public final float arrowHeight;
        public final float arrowRadius;
        public final float arrowWidth;
        public final int color;
        public final int contentPadding;
        public final float cornerRadius;

        public Config(int i, float f, int i2, float f2, float f3, float f4) {
            this.color = i;
            this.cornerRadius = f;
            this.contentPadding = i2;
            this.arrowWidth = f2;
            this.arrowHeight = f3;
            this.arrowRadius = f4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Config)) {
                return false;
            }
            Config config = (Config) obj;
            return this.color == config.color && Float.compare(this.cornerRadius, config.cornerRadius) == 0 && this.contentPadding == config.contentPadding && Float.compare(this.arrowWidth, config.arrowWidth) == 0 && Float.compare(this.arrowHeight, config.arrowHeight) == 0 && Float.compare(this.arrowRadius, config.arrowRadius) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.arrowRadius) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.arrowHeight, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.arrowWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.contentPadding, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.cornerRadius, Integer.hashCode(this.color) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            return "Config(color=" + this.color + ", cornerRadius=" + this.cornerRadius + ", contentPadding=" + this.contentPadding + ", arrowWidth=" + this.arrowWidth + ", arrowHeight=" + this.arrowHeight + ", arrowRadius=" + this.arrowRadius + ")";
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ArrowDirection.values().length];
            try {
                iArr[ArrowDirection.UP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ArrowDirection.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(BubblePopupDrawable.class, "arrowDirection", "getArrowDirection()Lcom/android/wm/shell/shared/bubbles/BubblePopupDrawable$ArrowDirection;", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        MutablePropertyReference1Impl mutablePropertyReference1Impl2 = new MutablePropertyReference1Impl(BubblePopupDrawable.class, "arrowPosition", "getArrowPosition()Lcom/android/wm/shell/shared/bubbles/BubblePopupDrawable$ArrowPosition;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl, mutablePropertyReference1Impl2};
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.shared.bubbles.BubblePopupDrawable$special$$inlined$observable$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.shared.bubbles.BubblePopupDrawable$special$$inlined$observable$2] */
    public BubblePopupDrawable(Config config) {
        this.config = config;
        Delegates delegates = Delegates.INSTANCE;
        final ArrowDirection arrowDirection = ArrowDirection.UP;
        this.arrowDirection$delegate = new ObservableProperty(arrowDirection) { // from class: com.android.wm.shell.shared.bubbles.BubblePopupDrawable$special$$inlined$observable$1
            @Override // kotlin.properties.ObservableProperty
            public final void afterChange(Object obj, Object obj2) {
                this.shouldUpdatePath = true;
            }
        };
        final ArrowPosition.Center center = ArrowPosition.Center.INSTANCE;
        this.arrowPosition$delegate = new ObservableProperty(center) { // from class: com.android.wm.shell.shared.bubbles.BubblePopupDrawable$special$$inlined$observable$2
            @Override // kotlin.properties.ObservableProperty
            public final void afterChange(Object obj, Object obj2) {
                this.shouldUpdatePath = true;
            }
        };
        this.path = new Path();
        Paint paint = new Paint();
        this.paint = paint;
        this.shouldUpdatePath = true;
        paint.setColor(config.color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    public final void addRoundedArrowPositioned(Path path, ArrowPosition arrowPosition) {
        float fWidth;
        Matrix matrix = new Matrix();
        if (arrowPosition instanceof ArrowPosition.Start) {
            fWidth = 0.0f;
        } else if (arrowPosition instanceof ArrowPosition.Center) {
            fWidth = getBounds().width() / 2.0f;
        } else if (arrowPosition instanceof ArrowPosition.End) {
            fWidth = getBounds().width();
        } else {
            if (!(arrowPosition instanceof ArrowPosition.Custom)) {
                throw new NoWhenBranchMatchedException();
            }
            fWidth = ((ArrowPosition.Custom) arrowPosition).value;
        }
        Config config = this.config;
        float f = 2;
        float f2 = fWidth - (config.arrowWidth / f);
        float fWidth2 = getBounds().width();
        Config config2 = this.config;
        matrix.setTranslate(-RangesKt___RangesKt.coerceIn(f2, config.cornerRadius, (fWidth2 - config2.cornerRadius) - config2.arrowWidth), 0.0f);
        path.transform(matrix);
        Config config3 = this.config;
        float f3 = config3.arrowWidth / (config3.arrowHeight * 2.0f);
        double dAtan = (float) Math.atan(f3);
        float degrees = (float) Math.toDegrees(dAtan);
        float fSin = this.config.arrowRadius / ((float) Math.sin(dAtan));
        float f4 = this.config.arrowRadius / f3;
        float fCos = ((float) Math.cos(dAtan)) * f4;
        float fSin2 = f4 * ((float) Math.sin(dAtan));
        Config config4 = this.config;
        float f5 = config4.arrowWidth / 2.0f;
        path.moveTo(0.0f, config4.arrowHeight);
        path.lineTo(f5 - fSin2, fCos);
        float f6 = this.config.arrowRadius;
        float f7 = f6 + fSin;
        float f8 = 180;
        path.arcTo(f5 - f6, fSin - f6, f5 + f6, f7, f8 + degrees, f8 - (f * degrees), false);
        Config config5 = this.config;
        path.lineTo(config5.arrowWidth, config5.arrowHeight);
        path.close();
        matrix.invert(matrix);
        path.transform(matrix);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        updatePathIfNeeded();
        canvas.drawPath(this.path, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return this.paint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        updatePathIfNeeded();
        outline.setPath(this.path);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        int i = this.config.contentPadding;
        rect.set(i, i, i, i);
        BubblePopupDrawable$special$$inlined$observable$1 bubblePopupDrawable$special$$inlined$observable$1 = this.arrowDirection$delegate;
        KProperty kProperty = $$delegatedProperties[0];
        int i2 = WhenMappings.$EnumSwitchMapping$0[((ArrowDirection) bubblePopupDrawable$special$$inlined$observable$1.value).ordinal()];
        if (i2 == 1) {
            rect.top += (int) this.config.arrowHeight;
            return true;
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        rect.bottom += (int) this.config.arrowHeight;
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.shouldUpdatePath = true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    public final void updatePathIfNeeded() {
        if (this.shouldUpdatePath) {
            if (!getBounds().isEmpty()) {
                this.path.reset();
                RectF rectF = new RectF(getBounds());
                BubblePopupDrawable$special$$inlined$observable$1 bubblePopupDrawable$special$$inlined$observable$1 = this.arrowDirection$delegate;
                KProperty[] kPropertyArr = $$delegatedProperties;
                KProperty kProperty = kPropertyArr[0];
                int i = WhenMappings.$EnumSwitchMapping$0[((ArrowDirection) bubblePopupDrawable$special$$inlined$observable$1.value).ordinal()];
                if (i == 1) {
                    Path path = this.path;
                    BubblePopupDrawable$special$$inlined$observable$2 bubblePopupDrawable$special$$inlined$observable$2 = this.arrowPosition$delegate;
                    KProperty kProperty2 = kPropertyArr[1];
                    addRoundedArrowPositioned(path, (ArrowPosition) bubblePopupDrawable$special$$inlined$observable$2.value);
                    rectF.top += this.config.arrowHeight;
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    Matrix matrix = new Matrix();
                    matrix.setScale(1.0f, -1.0f, getBounds().width() / 2.0f, getBounds().height() / 2.0f);
                    this.path.transform(matrix);
                    Path path2 = this.path;
                    BubblePopupDrawable$special$$inlined$observable$2 bubblePopupDrawable$special$$inlined$observable$22 = this.arrowPosition$delegate;
                    KProperty kProperty3 = kPropertyArr[1];
                    addRoundedArrowPositioned(path2, (ArrowPosition) bubblePopupDrawable$special$$inlined$observable$22.value);
                    matrix.invert(matrix);
                    this.path.transform(matrix);
                    rectF.bottom -= this.config.arrowHeight;
                }
                Path path3 = this.path;
                float f = this.config.cornerRadius;
                path3.addRoundRect(rectF, f, f, Path.Direction.CW);
            }
            this.shouldUpdatePath = false;
        }
    }
}
