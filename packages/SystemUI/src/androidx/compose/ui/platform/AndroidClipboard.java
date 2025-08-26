package androidx.compose.ui.platform;

import android.content.ClipboardManager;
import android.content.Context;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class AndroidClipboard implements Clipboard {
    public final AndroidClipboardManager androidClipboardManager;

    public AndroidClipboard(AndroidClipboardManager androidClipboardManager) {
        this.androidClipboardManager = androidClipboardManager;
    }

    public final Unit setClipEntry(ClipEntry clipEntry) {
        AndroidClipboardManager androidClipboardManager = this.androidClipboardManager;
        if (clipEntry == null) {
            ClipboardManager clipboardManager = androidClipboardManager.clipboardManager;
            int i = Api28ClipboardManagerClipClear.$r8$clinit;
            clipboardManager.clearPrimaryClip();
        } else {
            androidClipboardManager.clipboardManager.setPrimaryClip(clipEntry.clipData);
        }
        return Unit.INSTANCE;
    }

    public AndroidClipboard(Context context) {
        this(new AndroidClipboardManager(context));
    }
}
