package android.flags;

/* loaded from: classes.dex */
public interface Flag<T> {
    Flag<T> defineMetaData(String str, String str2, String str3);

    T getDefault();

    String getName();

    String getNamespace();

    default boolean isDynamic() {
        return false;
    }

    default String getLabel() {
        return getName();
    }

    default String getDescription() {
        return null;
    }

    default String getCategoryName() {
        return null;
    }
}
