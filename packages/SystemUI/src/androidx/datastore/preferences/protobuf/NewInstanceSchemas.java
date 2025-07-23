package androidx.datastore.preferences.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NewInstanceSchemas {
    public static final NewInstanceSchema FULL_SCHEMA;
    public static final NewInstanceSchemaLite LITE_SCHEMA;

    static {
        Protobuf protobuf = Protobuf.INSTANCE;
        NewInstanceSchema newInstanceSchema = null;
        try {
            Class[] clsArr = new Class[0];
            newInstanceSchema = (NewInstanceSchema) Class.forName("androidx.datastore.preferences.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(null).newInstance(null);
        } catch (Exception unused) {
        }
        FULL_SCHEMA = newInstanceSchema;
        LITE_SCHEMA = new NewInstanceSchemaLite();
    }

    private NewInstanceSchemas() {
    }
}
