package com.android.systemui.touch;

import android.graphics.Region;
import android.util.Log;
import android.view.View;
import com.android.systemui.touch.TouchInsetManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TouchInsetManager {
    public final Executor mExecutor;
    public final HashMap mSessionRegions = new HashMap();
    public final HashMap mLastAffectedSurfaces = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TouchInsetSession {
        public final Executor mExecutor;
        public final TouchInsetManager mManager;
        public final TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda0 mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.touch.TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TouchInsetManager.TouchInsetSession.this.updateTouchRegions();
            }
        };
        public final AnonymousClass1 mAttachListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.touch.TouchInsetManager.TouchInsetSession.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                TouchInsetSession.this.updateTouchRegions();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                TouchInsetSession.this.updateTouchRegions();
            }
        };
        public final HashSet mTrackedViews = new HashSet();

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.touch.TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.touch.TouchInsetManager$TouchInsetSession$1] */
        public TouchInsetSession(TouchInsetManager touchInsetManager, Executor executor) {
            this.mManager = touchInsetManager;
            this.mExecutor = executor;
        }

        public final void updateTouchRegions() {
            this.mExecutor.execute(new TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(this, 1));
        }
    }

    public TouchInsetManager(Executor executor) {
        this.mExecutor = executor;
    }

    public final TouchInsetSession createSession() {
        return new TouchInsetSession(this, this.mExecutor);
    }

    public final void recycleRegions(TouchInsetSession touchInsetSession) {
        if (this.mSessionRegions.containsKey(touchInsetSession)) {
            Iterator it = ((HashMap) this.mSessionRegions.get(touchInsetSession)).values().iterator();
            while (it.hasNext()) {
                ((Region) it.next()).recycle();
            }
        } else {
            Log.w("TouchInsetManager", "Removing a session with no regions:" + touchInsetSession);
        }
    }

    public final void updateTouchInsets() {
        HashMap hashMap = new HashMap();
        this.mSessionRegions.values().stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda1(0, hashMap));
        hashMap.entrySet().stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda2());
        this.mLastAffectedSurfaces.entrySet().forEach(new TouchInsetManager$$ExternalSyntheticLambda1(1, hashMap));
        this.mLastAffectedSurfaces.clear();
        this.mLastAffectedSurfaces.putAll(hashMap);
    }
}
