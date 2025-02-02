package androidx.emoji2.text.flatbuffer;

/* loaded from: classes.dex */
public final class Utf8Safe {
    private static Utf8Safe DEFAULT;

    public static void getDefault() {
        if (DEFAULT == null) {
            DEFAULT = new Utf8Safe();
        }
    }
}
