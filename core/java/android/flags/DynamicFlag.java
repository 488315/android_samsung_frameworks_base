package android.flags;

public interface DynamicFlag<T> extends Flag<T> {
    @Override // android.flags.Flag
    default boolean isDynamic() {
        return true;
    }
}
