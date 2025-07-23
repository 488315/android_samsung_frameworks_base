package android.service.autofill;

import android.content.Context;
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

    /* JADX WARN: Code restructure failed: missing block: B:3:0x0007, code lost:
    
        if (r0 != 2) goto L14;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            r1 = 2
            if (r0 == 0) goto La
            if (r0 == r1) goto L16
            goto L51
        La:
            float r0 = r6.getX()
            r5.mDownX = r0
            float r0 = r6.getY()
            r5.mDownY = r0
        L16:
            float r0 = r5.mDownX
            float r2 = r5.mDownY
            float r3 = r6.getX()
            float r4 = r6.getY()
            float r0 = android.util.MathUtils.dist(r0, r2, r3, r4)
            int r2 = r6.getFlags()
            r1 = r1 & r2
            if (r1 != 0) goto L34
            int r1 = r5.mTouchSlop
            float r1 = (float) r1
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L51
        L34:
            android.service.autofill.IInlineSuggestionUiCallback r0 = r5.mCallback     // Catch: android.os.RemoteException -> L4a
            android.view.ViewRootImpl r1 = r5.getViewRootImpl()     // Catch: android.os.RemoteException -> L4a
            android.os.IBinder r1 = r1.getInputToken()     // Catch: android.os.RemoteException -> L4a
            android.content.Context r2 = r5.getContext()     // Catch: android.os.RemoteException -> L4a
            int r2 = r2.getDisplayId()     // Catch: android.os.RemoteException -> L4a
            r0.onTransferTouchFocusToImeWindow(r1, r2)     // Catch: android.os.RemoteException -> L4a
            goto L51
        L4a:
            java.lang.String r0 = "InlineSuggestionRoot"
            java.lang.String r1 = "RemoteException transferring touch focus to IME"
            android.util.Log.w(r0, r1)
        L51:
            boolean r5 = super.dispatchTouchEvent(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.service.autofill.InlineSuggestionRoot.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }
}
