package androidx.compose.ui.text.android;

/* loaded from: classes.dex */
final class HorizontalPositionCache {
    public int cachedKey = -1;
    public float cachedValue;
    public final TextLayout layout;

    public HorizontalPositionCache(TextLayout textLayout) {
        this.layout = textLayout;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float get(int i, boolean z, boolean z2, boolean z3) {
        boolean z4;
        int i2 = 1;
        TextLayout textLayout = this.layout;
        if (z) {
            int lineForOffset = LayoutCompat_androidKt.getLineForOffset(textLayout.layout, i, z);
            z4 = i == textLayout.layout.getLineStart(lineForOffset) || i == textLayout.getLineEnd(lineForOffset);
        }
        int i3 = i * 4;
        if (!z3) {
            i2 = z4 ? 2 : 3;
        } else if (z4) {
            i2 = 0;
        }
        int i4 = i3 + i2;
        if (this.cachedKey == i4) {
            return this.cachedValue;
        }
        float primaryHorizontal = z3 ? textLayout.getPrimaryHorizontal(i, z) : textLayout.getSecondaryHorizontal(i, z);
        if (z2) {
            this.cachedKey = i4;
            this.cachedValue = primaryHorizontal;
        }
        return primaryHorizontal;
    }
}
