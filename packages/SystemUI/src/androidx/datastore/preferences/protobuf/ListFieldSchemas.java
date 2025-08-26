package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
public final class ListFieldSchemas {
    public static final ListFieldSchema FULL_SCHEMA;
    public static final ListFieldSchemaLite LITE_SCHEMA;

    static {
        Protobuf protobuf = Protobuf.INSTANCE;
        ListFieldSchema listFieldSchema = null;
        try {
            Class[] clsArr = new Class[0];
            listFieldSchema = (ListFieldSchema) Class.forName("androidx.datastore.preferences.protobuf.ListFieldSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = listFieldSchema;
        LITE_SCHEMA = new ListFieldSchemaLite();
    }

    private ListFieldSchemas() {
    }
}
