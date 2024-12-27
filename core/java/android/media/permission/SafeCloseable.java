package android.media.permission;

public interface SafeCloseable extends AutoCloseable {
    @Override // java.lang.AutoCloseable
    void close();
}
