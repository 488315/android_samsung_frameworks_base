package com.android.systemui.touch;

import android.graphics.Region;
import android.util.Log;
import android.view.View;
import com.android.systemui.touch.TouchInsetManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TouchInsetManager {
    public final Executor mExecutor;
    public final HashMap mSessionRegions = new HashMap();
    public final HashMap mLastAffectedSurfaces = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TouchInsetSession {
        public final Executor mExecutor;
        public final TouchInsetManager mManager;
        public final TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda0 mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.touch.TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TouchInsetManager.TouchInsetSession.this.updateTouchRegions();
            }
        };
        public final ViewOnAttachStateChangeListenerC34861 mAttachListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.touch.TouchInsetManager.TouchInsetSession.1
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
            this.mExecutor.execute(new TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(this, 0));
        }
    }

    public TouchInsetManager(Executor executor) {
        this.mExecutor = executor;
    }

    public final TouchInsetSession createSession() {
        return new TouchInsetSession(this, this.mExecutor);
    }

    public final void recycleRegions(TouchInsetSession touchInsetSession) {
        HashMap hashMap = this.mSessionRegions;
        if (hashMap.containsKey(touchInsetSession)) {
            Iterator it = ((HashMap) hashMap.get(touchInsetSession)).values().iterator();
            while (it.hasNext()) {
                ((Region) it.next()).recycle();
            }
        } else {
            Log.w("TouchInsetManager", "Removing a session with no regions:" + touchInsetSession);
        }
    }

    public final void updateTouchInsets() {
        HashMap hashMap = new HashMap();
        this.mSessionRegions.values().stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda4(0, hashMap));
        hashMap.entrySet().stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda5());
        HashMap hashMap2 = this.mLastAffectedSurfaces;
        hashMap2.entrySet().forEach(new TouchInsetManager$$ExternalSyntheticLambda4(1, hashMap));
        hashMap2.clear();
        hashMap2.putAll(hashMap);
    }
}
