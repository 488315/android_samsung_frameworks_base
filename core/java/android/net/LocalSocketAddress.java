package android.net;

/* loaded from: classes2.dex */
public class LocalSocketAddress {
  private final String name;
  private final Namespace namespace;

  public enum Namespace {
    ABSTRACT(0),
    RESERVED(1),
    FILESYSTEM(2);

    /* renamed from: id */
    private int f312id;

    Namespace(int id) {
      this.f312id = id;
    }

    int getId() {
      return this.f312id;
    }
  }

  public LocalSocketAddress(String name, Namespace namespace) {
    this.name = name;
    this.namespace = namespace;
  }

  public LocalSocketAddress(String name) {
    this(name, Namespace.ABSTRACT);
  }

  public String getName() {
    return this.name;
  }

  public Namespace getNamespace() {
    return this.namespace;
  }
}
