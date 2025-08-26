package androidx.compose.ui.text.platform;

/* loaded from: classes.dex */
public final class EmojiCompatStatus implements EmojiCompatStatusDelegate {
    public static final EmojiCompatStatus INSTANCE = new EmojiCompatStatus();
    public static final EmojiCompatStatusDelegate delegate = new DefaultImpl();

    private EmojiCompatStatus() {
    }
}
