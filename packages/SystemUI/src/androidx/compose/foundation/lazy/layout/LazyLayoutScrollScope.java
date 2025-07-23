package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.gestures.ScrollScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LazyLayoutScrollScope extends ScrollScope {
    int calculateDistanceTo(int i);

    int getFirstVisibleItemIndex();

    int getFirstVisibleItemScrollOffset();

    int getItemCount();

    int getLastVisibleItemIndex();

    void snapToItem(int i, int i2);
}
