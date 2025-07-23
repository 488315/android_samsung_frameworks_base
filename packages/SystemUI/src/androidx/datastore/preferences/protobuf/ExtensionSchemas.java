package androidx.datastore.preferences.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtensionSchemas {
    public static final ExtensionSchema FULL_SCHEMA;
    public static final ExtensionSchemaLite LITE_SCHEMA = new ExtensionSchemaLite();

    static {
        Protobuf protobuf = Protobuf.INSTANCE;
        ExtensionSchema extensionSchema = null;
        try {
            Class[] clsArr = new Class[0];
            extensionSchema = (ExtensionSchema) Class.forName("androidx.datastore.preferences.protobuf.ExtensionSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = extensionSchema;
    }

    private ExtensionSchemas() {
    }
}
