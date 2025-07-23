package androidx.datastore.preferences.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
