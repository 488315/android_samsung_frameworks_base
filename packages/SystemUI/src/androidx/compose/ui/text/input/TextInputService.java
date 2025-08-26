package androidx.compose.ui.text.input;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class TextInputService {
    public final AtomicReference _currentInputSession = new AtomicReference(null);
    public final PlatformTextInputService platformTextInputService;

    public TextInputService(PlatformTextInputService platformTextInputService) {
        this.platformTextInputService = platformTextInputService;
    }
}
