package android.view;

public interface InternalInsetsAnimationController extends WindowInsetsAnimationController {
    boolean applyChangeInsets(InsetsState insetsState);

    void setReadyDispatched(boolean z);
}
