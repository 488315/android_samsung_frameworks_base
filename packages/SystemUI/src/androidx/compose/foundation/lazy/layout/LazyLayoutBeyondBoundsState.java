package androidx.compose.foundation.lazy.layout;

/* loaded from: classes.dex */
public interface LazyLayoutBeyondBoundsState {
    int getFirstPlacedIndex();

    boolean getHasVisibleItems();

    int getItemCount();

    int getLastPlacedIndex();

    int itemsPerViewport();
}
