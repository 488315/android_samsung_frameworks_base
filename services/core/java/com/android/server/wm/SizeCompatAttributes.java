package com.android.server.wm;

import android.graphics.Rect;
import android.util.Slog;
import com.samsung.android.core.SizeCompatInfo;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SizeCompatAttributes {
    public final ActivityRecord mActivityRecord;
    public Rect mBounds;
    public boolean mEnabled;
    public SizeCompatPolicy mReason;
    public Rect mReturnBounds;
    public float mScale = 1.0f;

    public SizeCompatAttributes(ActivityRecord activityRecord, SizeCompatPolicy sizeCompatPolicy) {
        this.mActivityRecord = activityRecord;
        this.mReason = sizeCompatPolicy;
    }

    public float getScale() {
        return this.mScale;
    }

    public Rect getBounds() {
        if (!hasBounds()) {
            return null;
        }
        if (this.mReturnBounds == null) {
            this.mReturnBounds = new Rect();
        }
        this.mReturnBounds.set(this.mBounds);
        return this.mReturnBounds;
    }

    public boolean hasBounds() {
        return this.mEnabled && this.mBounds != null;
    }

    public void setEnabled(boolean z) {
        this.mEnabled = z;
    }

    public final boolean isSameReason(SizeCompatPolicy sizeCompatPolicy) {
        return this.mReason.getMode() == sizeCompatPolicy.getMode();
    }

    public boolean updateScale(Rect rect, float f, SizeCompatPolicy sizeCompatPolicy) {
        boolean z = f != 1.0f;
        setEnabled(z);
        if (!isSameReason(sizeCompatPolicy)) {
            Slog.w("SizeCompatPolicy", "The reason is changed from " + SizeCompatInfo.sizeCompatModeToString(this.mReason.getMode()) + " to " + SizeCompatInfo.sizeCompatModeToString(sizeCompatPolicy.getMode()) + ", scale=" + f + ", bounds=" + rect);
            this.mReason = sizeCompatPolicy;
        }
        if (z) {
            if (this.mBounds == null) {
                this.mBounds = new Rect();
            }
            this.mBounds.set(rect);
            this.mBounds.offsetTo(0, 0);
            this.mBounds.scale(f);
        } else {
            this.mBounds = null;
        }
        if (f == this.mScale) {
            return false;
        }
        this.mScale = f;
        return true;
    }

    public void updatePosition(int i, int i2) {
        if (hasBounds()) {
            this.mBounds.offsetTo(i, i2);
        }
    }

    public void cleanUp(SizeCompatPolicy sizeCompatPolicy) {
        if (sizeCompatPolicy == null || isSameReason(sizeCompatPolicy)) {
            cleanUp();
            return;
        }
        Slog.w("SizeCompatPolicy", "Failed to clean up by " + SizeCompatInfo.sizeCompatModeToString(sizeCompatPolicy.getMode()) + ", SizeCompatAttributes created by " + SizeCompatInfo.sizeCompatModeToString(this.mReason.getMode()) + ", r=" + this.mActivityRecord);
    }

    public final void cleanUp() {
        boolean z = this.mScale != 1.0f;
        this.mEnabled = false;
        this.mScale = 1.0f;
        this.mBounds = null;
        this.mReturnBounds = null;
        ActivityRecord activityRecord = this.mActivityRecord;
        activityRecord.mSizeCompatAttributes = null;
        if (z) {
            activityRecord.forAllWindows((Consumer) new ActivityRecord$$ExternalSyntheticLambda6(), false);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("SizeCompatAttributes: ");
        printWriter.print("mScale=" + this.mScale);
        printWriter.print(", mBounds=");
        printWriter.print("(" + this.mBounds.left + "," + this.mBounds.top + ")");
        printWriter.print("(" + this.mBounds.width() + "x" + this.mBounds.height() + ")");
        StringBuilder sb = new StringBuilder();
        sb.append(", mReason=");
        sb.append(SizeCompatInfo.sizeCompatModeToString(this.mReason.getMode()));
        printWriter.print(sb.toString());
        printWriter.println();
    }
}
