package android.accessibilityservice;

import android.graphics.drawable.Drawable;

public final class SemAccessibilityShortcutInfo {
    private final Drawable icon;
    private final String title;

    public SemAccessibilityShortcutInfo(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public Drawable getIcon() {
        return this.icon;
    }
}
