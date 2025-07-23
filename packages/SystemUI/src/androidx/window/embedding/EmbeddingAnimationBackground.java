package androidx.window.embedding;

import android.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class EmbeddingAnimationBackground {
    public static final Companion Companion = new Companion(null);
    public static final DefaultBackground DEFAULT = new DefaultBackground();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ColorBackground extends EmbeddingAnimationBackground {
        public final int color;

        public ColorBackground(int i) {
            super(null);
            this.color = i;
            if (Color.alpha(i) != 255) {
                throw new IllegalArgumentException("Background color must be opaque");
            }
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof ColorBackground) {
                return this.color == ((ColorBackground) obj).color;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.color);
        }

        public final String toString() {
            return "ColorBackground{color:" + Integer.toHexString(this.color) + '}';
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
    public final class DefaultBackground extends EmbeddingAnimationBackground {
        public DefaultBackground() {
            super(null);
        }

        public final String toString() {
            return "DefaultBackground";
        }
    }

    public /* synthetic */ EmbeddingAnimationBackground(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private EmbeddingAnimationBackground() {
    }
}
