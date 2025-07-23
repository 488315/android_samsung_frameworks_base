package androidx.compose.ui.text;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LinkAnnotation implements AnnotatedString.Annotation {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Clickable extends LinkAnnotation {
        public final TextLinkStyles styles;
        public final String tag;

        public /* synthetic */ Clickable(String str, TextLinkStyles textLinkStyles, LinkInteractionListener linkInteractionListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : textLinkStyles, linkInteractionListener);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Clickable)) {
                return false;
            }
            Clickable clickable = (Clickable) obj;
            if (!Intrinsics.areEqual(this.tag, clickable.tag)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.styles, clickable.styles)) {
                return false;
            }
            clickable.getClass();
            return true;
        }

        @Override // androidx.compose.ui.text.LinkAnnotation
        public final TextLinkStyles getStyles() {
            return this.styles;
        }

        public final int hashCode() {
            int hashCode = this.tag.hashCode() * 31;
            TextLinkStyles textLinkStyles = this.styles;
            return (hashCode + (textLinkStyles != null ? textLinkStyles.hashCode() : 0)) * 31;
        }

        public final String toString() {
            return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("LinkAnnotation.Clickable(tag="), this.tag, ')');
        }

        public Clickable(String str, TextLinkStyles textLinkStyles, LinkInteractionListener linkInteractionListener) {
            super(null);
            this.tag = str;
            this.styles = textLinkStyles;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Url extends LinkAnnotation {
        public final TextLinkStyles styles;
        public final String url;

        public /* synthetic */ Url(String str, TextLinkStyles textLinkStyles, LinkInteractionListener linkInteractionListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : textLinkStyles, (i & 4) != 0 ? null : linkInteractionListener);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Url)) {
                return false;
            }
            Url url = (Url) obj;
            if (!Intrinsics.areEqual(this.url, url.url)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.styles, url.styles)) {
                return false;
            }
            url.getClass();
            return true;
        }

        @Override // androidx.compose.ui.text.LinkAnnotation
        public final TextLinkStyles getStyles() {
            return this.styles;
        }

        public final int hashCode() {
            int hashCode = this.url.hashCode() * 31;
            TextLinkStyles textLinkStyles = this.styles;
            return (hashCode + (textLinkStyles != null ? textLinkStyles.hashCode() : 0)) * 31;
        }

        public final String toString() {
            return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("LinkAnnotation.Url(url="), this.url, ')');
        }

        public Url(String str, TextLinkStyles textLinkStyles, LinkInteractionListener linkInteractionListener) {
            super(null);
            this.url = str;
            this.styles = textLinkStyles;
        }
    }

    public /* synthetic */ LinkAnnotation(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract TextLinkStyles getStyles();

    private LinkAnnotation() {
    }
}
