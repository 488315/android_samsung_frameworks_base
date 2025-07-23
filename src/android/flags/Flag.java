package android.flags;

/* loaded from: classes.dex */
public interface Flag<T> {
    Flag<T> defineMetaData(String str, String str2, String str3);

    default String getCategoryName() {
        return null;
    }

    T getDefault();

    default String getDescription() {
        return null;
    }

    String getName();

    String getNamespace();

    default boolean isDynamic() {
        return false;
    }

    default String getLabel() {
        return getName();
    }
}
