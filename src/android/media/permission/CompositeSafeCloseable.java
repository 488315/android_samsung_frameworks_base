package android.media.permission;

/* loaded from: classes3.dex */
class CompositeSafeCloseable implements SafeCloseable {
    private final SafeCloseable[] mChildren;

    CompositeSafeCloseable(SafeCloseable... safeCloseableArr) {
        this.mChildren = safeCloseableArr;
    }

    @Override // android.media.permission.SafeCloseable, java.lang.AutoCloseable
    public void close() {
        for (int length = this.mChildren.length - 1; length >= 0; length--) {
            this.mChildren[length].close();
        }
    }
}
