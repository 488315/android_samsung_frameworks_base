package android.net;

/* loaded from: classes3.dex */
public class LocalSocketAddress {
    private final String name;
    private final Namespace namespace;

    public enum Namespace {
        ABSTRACT(0),
        RESERVED(1),
        FILESYSTEM(2);

        private int id;

        Namespace(int i) {
            this.id = i;
        }

        int getId() {
            return this.id;
        }
    }

    public LocalSocketAddress(String str, Namespace namespace) {
        this.name = str;
        this.namespace = namespace;
    }

    public LocalSocketAddress(String str) {
        this(str, Namespace.ABSTRACT);
    }

    public String getName() {
        return this.name;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }
}
