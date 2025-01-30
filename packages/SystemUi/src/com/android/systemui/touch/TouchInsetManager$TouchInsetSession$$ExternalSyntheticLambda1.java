package com.android.systemui.touch;

import com.android.systemui.touch.TouchInsetManager;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchInsetManager.TouchInsetSession f$0;

    public /* synthetic */ TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(TouchInsetManager.TouchInsetSession touchInsetSession, int i) {
        this.$r8$classId = i;
        this.f$0 = touchInsetSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                final TouchInsetManager.TouchInsetSession touchInsetSession = this.f$0;
                touchInsetSession.getClass();
                final HashMap hashMap = new HashMap();
                HashSet hashSet = touchInsetSession.mTrackedViews;
                if (!hashSet.isEmpty()) {
                    hashSet.stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda4(3, hashMap));
                    final TouchInsetManager touchInsetManager = touchInsetSession.mManager;
                    touchInsetManager.getClass();
                    touchInsetManager.mExecutor.execute(new Runnable() { // from class: com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TouchInsetManager touchInsetManager2 = TouchInsetManager.this;
                            TouchInsetManager.TouchInsetSession touchInsetSession2 = touchInsetSession;
                            HashMap hashMap2 = hashMap;
                            touchInsetManager2.recycleRegions(touchInsetSession2);
                            touchInsetManager2.mSessionRegions.put(touchInsetSession2, hashMap2);
                            touchInsetManager2.updateTouchInsets();
                        }
                    });
                    break;
                }
                break;
            default:
                TouchInsetManager.TouchInsetSession touchInsetSession2 = this.f$0;
                TouchInsetManager touchInsetManager2 = touchInsetSession2.mManager;
                touchInsetManager2.getClass();
                touchInsetManager2.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda2(touchInsetManager2, touchInsetSession2));
                touchInsetSession2.mTrackedViews.clear();
                break;
        }
    }
}
