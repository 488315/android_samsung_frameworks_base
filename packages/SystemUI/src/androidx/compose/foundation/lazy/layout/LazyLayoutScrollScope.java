package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.gestures.ScrollScope;

/* loaded from: classes.dex */
public interface LazyLayoutScrollScope extends ScrollScope {
    int calculateDistanceTo(int i);

    int getFirstVisibleItemIndex();

    int getFirstVisibleItemScrollOffset();

    int getItemCount();

    int getLastVisibleItemIndex();

    void snapToItem(int i, int i2);
}
