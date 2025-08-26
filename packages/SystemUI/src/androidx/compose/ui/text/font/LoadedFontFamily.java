package androidx.compose.ui.text.font;

/* loaded from: classes.dex */
public final class LoadedFontFamily extends FontFamily {
    public LoadedFontFamily(Typeface typeface) {
        super(true, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LoadedFontFamily)) {
            return false;
        }
        ((LoadedFontFamily) obj).getClass();
        return true;
    }

    public final int hashCode() {
        throw null;
    }

    public final String toString() {
        return "LoadedFontFamily(typeface=null)";
    }
}
