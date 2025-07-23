package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.ComposerImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
