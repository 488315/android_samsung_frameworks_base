package android.view;

import android.graphics.FrameInfo;

public class ViewFrameInfo {
    public long drawStart;
    public long flags;
    private int mInputEventId;
    private int mViewsMeasuredCounts;

    public void populateFrameInfo(FrameInfo frameInfo) {
        long[] jArr = frameInfo.frameInfo;
        jArr[0] = jArr[0] | this.flags;
        frameInfo.frameInfo[8] = this.drawStart;
        frameInfo.frameInfo[4] = this.mInputEventId;
    }

    public void reset() {
        this.drawStart = 0L;
        this.mInputEventId = 0;
        this.flags = 0L;
        this.mViewsMeasuredCounts = 0;
    }

    public void markDrawStart() {
        this.drawStart = System.nanoTime();
    }

    public int getAndIncreaseViewMeasuredCount() {
        int i = this.mViewsMeasuredCounts + 1;
        this.mViewsMeasuredCounts = i;
        return i;
    }

    public void setInputEvent(int eventId) {
        this.mInputEventId = eventId;
    }
}
