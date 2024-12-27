package android.webkit;

import android.annotation.SystemApi;

public abstract class WebResourceError {
    public abstract CharSequence getDescription();

    public abstract int getErrorCode();

    @SystemApi
    public WebResourceError() {}
}
