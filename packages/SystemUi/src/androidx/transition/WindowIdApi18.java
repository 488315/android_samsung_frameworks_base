package androidx.transition;

import android.view.View;
import android.view.WindowId;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowIdApi18 implements WindowIdImpl {
    public final WindowId mWindowId;

    public WindowIdApi18(View view) {
        this.mWindowId = view.getWindowId();
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WindowIdApi18) && ((WindowIdApi18) obj).mWindowId.equals(this.mWindowId);
    }

    public final int hashCode() {
        return this.mWindowId.hashCode();
    }
}
