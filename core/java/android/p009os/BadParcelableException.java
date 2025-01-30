package android.p009os;

import android.util.AndroidRuntimeException;

/* loaded from: classes3.dex */
public class BadParcelableException extends AndroidRuntimeException {
  public BadParcelableException(String msg) {
    super(msg);
  }

  public BadParcelableException(Exception cause) {
    super(cause);
  }

  public BadParcelableException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
