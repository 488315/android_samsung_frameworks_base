package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.ComposerImpl;

/* loaded from: classes.dex */
public interface LazyLayoutItemProvider {
    void Item(int i, Object obj, ComposerImpl composerImpl);

    default Object getContentType(int i) {
        return null;
    }

    int getIndex(Object obj);

    int getItemCount();

    Object getKey(int i);
}
