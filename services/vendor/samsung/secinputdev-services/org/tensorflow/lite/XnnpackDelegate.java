package org.tensorflow.lite;

class XnnpackDelegate implements Delegate, AutoCloseable {
    private long deleteFunction;
    private long nativeHandle;

    private static native void applyDeleteFunction(long deleteFunction, long nativeHandle);

    XnnpackDelegate(long nativeHandle, long deleteFunction) {
        this.nativeHandle = nativeHandle;
        this.deleteFunction = deleteFunction;
    }

    @Override // org.tensorflow.lite.Delegate
    public long getNativeHandle() {
        return this.nativeHandle;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        applyDeleteFunction(this.deleteFunction, this.nativeHandle);
    }
}
