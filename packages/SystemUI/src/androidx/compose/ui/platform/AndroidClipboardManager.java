package androidx.compose.ui.platform;

import android.content.ClipboardManager;
import android.content.Context;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
