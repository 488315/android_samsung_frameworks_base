package android.accessibilityservice;

import android.graphics.drawable.Drawable;

/* loaded from: classes.dex */
public final class SemAccessibilityShortcutInfo {
    private final Drawable icon;
    private final String title;

    public SemAccessibilityShortcutInfo(String str, Drawable drawable) {
        this.title = str;
        this.icon = drawable;
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getIcon() {
        return this.icon;
    }
}
