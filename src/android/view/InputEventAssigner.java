package android.view;

/* loaded from: classes4.dex */
public class InputEventAssigner {
    private static final String TAG = "InputEventAssigner";
    private boolean mHasUnprocessedDown = false;
    private int mDownEventId = 0;

    public void notifyFrameProcessed() {
        this.mHasUnprocessedDown = false;
    }

    public int processEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            if (motionEvent.isFromSource(2) || motionEvent.isFromSource(8)) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mHasUnprocessedDown = true;
                    this.mDownEventId = inputEvent.getId();
                }
                if (actionMasked == 3 || actionMasked == 1) {
                    this.mHasUnprocessedDown = false;
                }
                if (this.mHasUnprocessedDown) {
                    return this.mDownEventId;
                }
            }
        }
        return inputEvent.getId();
    }
}
