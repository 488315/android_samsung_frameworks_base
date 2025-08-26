package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
public final class ExtensionRegistryFactory {
    public static final Class EXTENSION_REGISTRY_CLASS;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("androidx.datastore.preferences.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        EXTENSION_REGISTRY_CLASS = cls;
    }
}
