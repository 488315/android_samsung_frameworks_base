package com.android.systemui.touch;

import com.android.systemui.touch.TouchInsetManager;
import java.util.HashMap;

/* loaded from: classes3.dex */
public final /* synthetic */ class TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchInsetManager.TouchInsetSession f$0;

    public /* synthetic */ TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(TouchInsetManager.TouchInsetSession touchInsetSession, int i) {
        this.$r8$classId = i;
        this.f$0 = touchInsetSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final TouchInsetManager.TouchInsetSession touchInsetSession = this.f$0;
        switch (i) {
            case 0:
                TouchInsetManager touchInsetManager = touchInsetSession.mManager;
                touchInsetManager.mExecutor.execute(new TouchInsetManager$$ExternalSyntheticLambda0(touchInsetManager, touchInsetSession));
                touchInsetSession.mTrackedViews.clear();
                break;
            default:
                touchInsetSession.getClass();
                final HashMap map = new HashMap();
                if (!touchInsetSession.mTrackedViews.isEmpty()) {
                    touchInsetSession.mTrackedViews.stream().forEach(new TouchInsetManager$$ExternalSyntheticLambda1(3, map));
                    final TouchInsetManager touchInsetManager2 = touchInsetSession.mManager;
                    touchInsetManager2.mExecutor.execute(new Runnable() { // from class: com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            TouchInsetManager touchInsetManager3 = touchInsetManager2;
                            TouchInsetManager.TouchInsetSession touchInsetSession2 = touchInsetSession;
                            HashMap map2 = map;
                            touchInsetManager3.recycleRegions(touchInsetSession2);
                            touchInsetManager3.mSessionRegions.put(touchInsetSession2, map2);
                            touchInsetManager3.updateTouchInsets();
                        }
                    });
                    break;
                }
                break;
        }
    }
}
