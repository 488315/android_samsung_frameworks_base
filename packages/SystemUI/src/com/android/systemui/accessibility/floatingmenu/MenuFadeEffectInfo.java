package com.android.systemui.accessibility.floatingmenu;

/* loaded from: classes.dex */
public final class MenuFadeEffectInfo {
    public final boolean isFadeEffectEnabled;
    public final float opacity;

    public MenuFadeEffectInfo(boolean z, float f) {
        this.isFadeEffectEnabled = z;
        this.opacity = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MenuFadeEffectInfo)) {
            return false;
        }
        MenuFadeEffectInfo menuFadeEffectInfo = (MenuFadeEffectInfo) obj;
        return this.isFadeEffectEnabled == menuFadeEffectInfo.isFadeEffectEnabled && Float.compare(this.opacity, menuFadeEffectInfo.opacity) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.opacity) + (Boolean.hashCode(this.isFadeEffectEnabled) * 31);
    }

    public final String toString() {
        return "MenuFadeEffectInfo(isFadeEffectEnabled=" + this.isFadeEffectEnabled + ", opacity=" + this.opacity + ")";
    }
}
