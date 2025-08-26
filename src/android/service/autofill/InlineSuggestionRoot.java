package android.service.autofill;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
public class InlineSuggestionRoot extends FrameLayout {
    private static final String TAG = "InlineSuggestionRoot";
    private final IInlineSuggestionUiCallback mCallback;
    private float mDownX;
    private float mDownY;
    private final int mTouchSlop;

    public InlineSuggestionRoot(Context context, IInlineSuggestionUiCallback iInlineSuggestionUiCallback) {
        super(context);
        this.mCallback = iInlineSuggestionUiCallback;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setFocusable(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownX = motionEvent.getX();
            this.mDownY = motionEvent.getY();
        } else {
            if (actionMasked == 2) {
            }
            return super.dispatchTouchEvent(motionEvent);
        }
        float fDist = MathUtils.dist(this.mDownX, this.mDownY, motionEvent.getX(), motionEvent.getY());
        if ((2 & motionEvent.getFlags()) != 0 || fDist > this.mTouchSlop) {
            try {
                this.mCallback.onTransferTouchFocusToImeWindow(getViewRootImpl().getInputToken(), getContext().getDisplayId());
            } catch (RemoteException unused) {
                Log.w(TAG, "RemoteException transferring touch focus to IME");
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
