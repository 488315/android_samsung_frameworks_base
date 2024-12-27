package android.webkit;

import java.io.Serializable;

public abstract class WebBackForwardList implements Cloneable, Serializable {
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public abstract WebBackForwardList m6244clone();

    public abstract int getCurrentIndex();

    public abstract WebHistoryItem getCurrentItem();

    public abstract WebHistoryItem getItemAtIndex(int i);

    public abstract int getSize();
}
