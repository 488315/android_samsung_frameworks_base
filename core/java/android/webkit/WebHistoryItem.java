package android.webkit;

import android.annotation.SystemApi;
import android.graphics.Bitmap;

public abstract class WebHistoryItem implements Cloneable {
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract WebHistoryItem m6245clone();

    public abstract Bitmap getFavicon();

    @SystemApi
    @Deprecated
    public abstract int getId();

    public abstract String getOriginalUrl();

    public abstract String getTitle();

    public abstract String getUrl();
}
