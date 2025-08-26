package android.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.GestureDetector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public class LetterboxScrollProcessor {
    private final Context mContext;
    private final GestureDetector mScrollDetector;
    private LetterboxScrollState mState = LetterboxScrollState.AWAITING_GESTURE_START;
    private final List<MotionEvent> mProcessedEvents = new ArrayList();
    private final Set<Integer> mGeneratedEventIds = new HashSet();

    private enum LetterboxScrollState {
        AWAITING_GESTURE_START,
        GESTURE_STARTED_IN_APP,
        GESTURE_STARTED_OUTSIDE_APP,
        SCROLLING_STARTED_OUTSIDE_APP
    }

    private float calculateOffset(float f, int i) {
        if (f < 0.0f) {
            return -f;
        }
        float f2 = i;
        if (f >= f2) {
            return -((f - f2) + 1.0f);
        }
        return 0.0f;
    }

    public LetterboxScrollProcessor(Context context, Handler handler) {
        this.mContext = context;
        this.mScrollDetector = new GestureDetector(context, new ScrollListener(), handler);
    }

    public List<MotionEvent> processMotionEvent(MotionEvent motionEvent) {
        boolean z;
        if (!motionEvent.isFromSource(2)) {
            return null;
        }
        this.mProcessedEvents.clear();
        Rect appBounds = getAppBounds();
        if (motionEvent.getAction() == 0) {
            if (isOutsideAppBounds(motionEvent, appBounds)) {
                this.mState = LetterboxScrollState.GESTURE_STARTED_OUTSIDE_APP;
            } else {
                this.mState = LetterboxScrollState.GESTURE_STARTED_IN_APP;
            }
        }
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            z = true;
        } else {
            if (iOrdinal == 2) {
                applyOffset(motionEvent, appBounds);
                this.mScrollDetector.onTouchEvent(motionEvent);
                if (this.mState == LetterboxScrollState.SCROLLING_STARTED_OUTSIDE_APP) {
                    this.mProcessedEvents.add(motionEvent);
                }
            } else if (iOrdinal == 3) {
                if (isOutsideAppBounds(motionEvent, appBounds)) {
                    applyOffset(motionEvent, appBounds);
                } else {
                    this.mState = LetterboxScrollState.GESTURE_STARTED_IN_APP;
                }
                this.mProcessedEvents.add(motionEvent);
            }
            z = false;
        }
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            this.mState = LetterboxScrollState.AWAITING_GESTURE_START;
        }
        if (z) {
            return null;
        }
        return this.mProcessedEvents;
    }

    public InputEvent processMotionEventBeforeFinish(MotionEvent motionEvent) {
        if (this.mGeneratedEventIds.remove(Integer.valueOf(motionEvent.getId()))) {
            return null;
        }
        return motionEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getAppBounds() {
        return this.mContext.getResources().getConfiguration().windowConfiguration.getBounds();
    }

    private boolean isOutsideAppBounds(MotionEvent motionEvent, Rect rect) {
        return motionEvent.getRawX() < ((float) rect.left) || motionEvent.getRawX() >= ((float) rect.right) || motionEvent.getRawY() < ((float) rect.top) || motionEvent.getRawY() >= ((float) rect.bottom);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyOffset(MotionEvent motionEvent, Rect rect) {
        motionEvent.offsetLocation(calculateOffset(motionEvent.getX(), rect.width()), calculateOffset(motionEvent.getY(), rect.height()));
    }

    private class ScrollListener extends GestureDetector.SimpleOnGestureListener {
        private ScrollListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            MotionEvent motionEventObtain = MotionEvent.obtain((MotionEvent) Objects.requireNonNull(motionEvent));
            LetterboxScrollProcessor.this.applyOffset(motionEventObtain, LetterboxScrollProcessor.this.getAppBounds());
            LetterboxScrollProcessor.this.mGeneratedEventIds.add(Integer.valueOf(motionEventObtain.getId()));
            LetterboxScrollProcessor.this.mProcessedEvents.add(motionEventObtain);
            LetterboxScrollProcessor.this.mState = LetterboxScrollState.SCROLLING_STARTED_OUTSIDE_APP;
            return super.onScroll(motionEvent, motionEvent2, f, f2);
        }
    }
}
