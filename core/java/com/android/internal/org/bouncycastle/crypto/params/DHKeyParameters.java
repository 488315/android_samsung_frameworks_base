package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes5.dex */
public class DHKeyParameters extends AsymmetricKeyParameter {
  private DHParameters params;

  protected DHKeyParameters(boolean isPrivate, DHParameters params) {
    super(isPrivate);
    this.params = params;
  }

  public DHParameters getParameters() {
    return this.params;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof DHKeyParameters)) {
      return false;
    }
    DHKeyParameters dhKey = (DHKeyParameters) obj;
    DHParameters dHParameters = this.params;
    if (dHParameters == null) {
      return dhKey.getParameters() == null;
    }
    return dHParameters.equals(dhKey.getParameters());
  }

  public int hashCode() {
    int i = !isPrivate() ? 1 : 0;
    DHParameters dHParameters = this.params;
    if (dHParameters != null) {
      return i ^ dHParameters.hashCode();
    }
    return i;
  }
}
