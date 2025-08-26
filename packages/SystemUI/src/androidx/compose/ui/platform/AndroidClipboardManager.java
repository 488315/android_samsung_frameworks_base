package androidx.compose.ui.platform;

import android.content.ClipboardManager;
import android.content.Context;

/* loaded from: classes.dex */
public final class AndroidClipboardManager {
    public final ClipboardManager clipboardManager;

    public AndroidClipboardManager(ClipboardManager clipboardManager) {
        this.clipboardManager = clipboardManager;
    }

    public AndroidClipboardManager(Context context) {
        this((ClipboardManager) context.getSystemService("clipboard"));
    }
}
