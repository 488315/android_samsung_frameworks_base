package android.webkit;

import android.annotation.SystemApi;

/* loaded from: classes4.dex */
public abstract class WebResourceError {
  public abstract CharSequence getDescription();

  public abstract int getErrorCode();

  @SystemApi
  public WebResourceError() {}
}
