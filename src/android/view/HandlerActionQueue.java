package android.view;

import android.os.Handler;
import com.android.internal.util.GrowingArrayUtils;

/* loaded from: classes4.dex */
public class HandlerActionQueue {
    private HandlerAction[] mActions;
    private int mCount;

    public void post(Runnable runnable) {
        postDelayed(runnable, 0L);
    }

    public void postDelayed(Runnable runnable, long j) {
        HandlerAction handlerAction = new HandlerAction(runnable, j);
        synchronized (this) {
            if (this.mActions == null) {
                this.mActions = new HandlerAction[4];
            }
            this.mActions = (HandlerAction[]) GrowingArrayUtils.append(this.mActions, this.mCount, handlerAction);
            this.mCount++;
        }
    }

    public void removeCallbacks(Runnable runnable) {
        synchronized (this) {
            int i = this.mCount;
            HandlerAction[] handlerActionArr = this.mActions;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                if (!handlerActionArr[i3].matches(runnable)) {
                    if (i2 != i3) {
                        handlerActionArr[i2] = handlerActionArr[i3];
                    }
                    i2++;
                }
            }
            this.mCount = i2;
            while (i2 < i) {
                handlerActionArr[i2] = null;
                i2++;
            }
        }
    }

    public void executeActions(Handler handler) {
        synchronized (this) {
            HandlerAction[] handlerActionArr = this.mActions;
            int i = this.mCount;
            for (int i2 = 0; i2 < i; i2++) {
                HandlerAction handlerAction = handlerActionArr[i2];
                handler.postDelayed(handlerAction.action, handlerAction.delay);
            }
            this.mActions = null;
            this.mCount = 0;
        }
    }

    public int size() {
        return this.mCount;
    }

    public Runnable getRunnable(int i) {
        if (i >= this.mCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.mActions[i].action;
    }

    public long getDelay(int i) {
        if (i >= this.mCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.mActions[i].delay;
    }

    private static class HandlerAction {
        final Runnable action;
        final long delay;

        public HandlerAction(Runnable runnable, long j) {
            this.action = runnable;
            this.delay = j;
        }

        public boolean matches(Runnable runnable) {
            if (runnable == null && this.action == null) {
                return true;
            }
            Runnable runnable2 = this.action;
            return runnable2 != null && runnable2.equals(runnable);
        }
    }
}
