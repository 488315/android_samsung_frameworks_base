package android.database.sqlite;

/* loaded from: classes.dex */
public class DatabaseObjectNotClosedException extends RuntimeException {

  /* renamed from: s */
  private static final String f56s =
      "Application did not close the cursor or database object that was opened here";

  public DatabaseObjectNotClosedException() {
    super(f56s);
  }
}
