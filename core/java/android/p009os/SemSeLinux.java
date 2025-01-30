package android.p009os;

/* loaded from: classes3.dex */
public final class SemSeLinux {
  private SemSeLinux() {}

  public static boolean isEnforced() {
    return SELinux.isSELinuxEnforced();
  }
}
