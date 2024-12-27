package android.view;

public interface ScrollFeedbackProvider {
    void onScrollLimit(int i, int i2, int i3, boolean z);

    void onScrollProgress(int i, int i2, int i3, int i4);

    void onSnapToItem(int i, int i2, int i3);

    static ScrollFeedbackProvider createProvider(View view) {
        return new HapticScrollFeedbackProvider(view);
    }
}
