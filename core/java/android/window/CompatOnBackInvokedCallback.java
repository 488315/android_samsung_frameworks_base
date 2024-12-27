package android.window;

public interface CompatOnBackInvokedCallback extends OnBackInvokedCallback {
    @Override // android.window.OnBackInvokedCallback
    void onBackInvoked();
}
