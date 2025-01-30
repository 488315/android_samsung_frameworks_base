package com.android.server.wm;

import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Rect;
import com.samsung.android.core.CompatSandbox;
import com.samsung.android.core.SizeCompatInfo;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public abstract class SizeCompatPolicy
    implements SizeCompatPolicyCasting, SizeCompatPolicySupports {
  public final int mHeight;
  public final Task mTask;
  public final int mWidth;
  public boolean mEnabled = true;
  public int mLastTaskOrientation = 0;
  public final Rect[] mNonDecorInsets = new Rect[4];
  public final Rect[] mStableInsets = new Rect[4];

  public abstract void dump(PrintWriter printWriter, String str);

  public abstract void ensureConfiguration();

  public abstract void ensureTransaction(int i, WindowConfiguration windowConfiguration, int i2);

  public abstract void onOrientationChanged(
      DisplayContent displayContent, Configuration configuration);

  public abstract int resolveOverrideConfiguration(
      ActivityRecord activityRecord, Configuration configuration);

  public abstract boolean shouldIgnoreMinimalTaskDimensions();

  public abstract boolean shouldRotateBounds();

  public abstract int updateResolvedBoundsPosition(
      ActivityRecord activityRecord, Configuration configuration);

  public SizeCompatPolicy(Task task, int i, int i2, DisplayPolicy displayPolicy) {
    this.mTask = task;
    this.mWidth = i;
    this.mHeight = i2;
    int i3 = 0;
    while (i3 < 4) {
      this.mNonDecorInsets[i3] = new Rect();
      this.mStableInsets[i3] = new Rect();
      if (displayPolicy != null) {
        boolean z = i3 == 1 || i3 == 3;
        DisplayPolicy.DecorInsets.Info decorInsetsInfo =
            displayPolicy.getDecorInsetsInfo(
                i3, z ? this.mHeight : this.mWidth, z ? this.mWidth : this.mHeight);
        this.mNonDecorInsets[i3].set(decorInsetsInfo.mNonDecorInsets);
        this.mStableInsets[i3].set(decorInsetsInfo.mConfigInsets);
      }
      i3++;
    }
  }

  public boolean isEnabled() {
    if (!this.mEnabled || !SizeCompatPolicyManager.get().isModeEnabled(this.mTask, getMode())) {
      return false;
    }
    if (supportsFullScreen() && this.mTask.inFullscreenWindowingMode()) {
      return true;
    }
    return supportsFreeform() && this.mTask.inFreeformWindowingMode();
  }

  public final void setEnabled(boolean z) {
    this.mEnabled = z;
  }

  public void applyCompatScaleIfNeeded(ActivityRecord activityRecord, Rect rect, float f) {
    if (!(f == 1.0f && activityRecord.mSizeCompatAttributes == null)
        && getSizeCompatAttributesOrCreate(activityRecord).updateScale(rect, f, this)) {
      activityRecord.forAllWindows(
          (Consumer) new ActivityRecord$$ExternalSyntheticLambda6(), false);
    }
  }

  public final SizeCompatAttributes getSizeCompatAttributesOrCreate(ActivityRecord activityRecord) {
    if (activityRecord.mSizeCompatAttributes == null) {
      activityRecord.mSizeCompatAttributes = new SizeCompatAttributes(activityRecord, this);
    }
    return activityRecord.mSizeCompatAttributes;
  }

  public final void getBoundsByRotation(Rect rect, int i) {
    boolean z = true;
    if (i != 1 && i != 3) {
      z = false;
    }
    rect.set(0, 0, z ? this.mHeight : this.mWidth, z ? this.mWidth : this.mHeight);
  }

  public final void getFrameByOrientation(Rect rect, int i) {
    int max = Math.max(this.mWidth, this.mHeight);
    int min = Math.min(this.mWidth, this.mHeight);
    boolean z = i == 2;
    int i2 = z ? max : min;
    if (z) {
      max = min;
    }
    rect.set(0, 0, i2, max);
  }

  public void fillTaskInfo(TaskInfo taskInfo, SizeCompatInfo sizeCompatInfo) {
    taskInfo.topActivityInSizeCompat = false;
    sizeCompatInfo.setMode(getMode());
  }

  public final void cleanUp() {
    this.mTask.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.SizeCompatPolicy$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            SizeCompatPolicy.this.lambda$cleanUp$0((ActivityRecord) obj);
          }
        });
    this.mTask.mSizeCompatPolicy = null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$cleanUp$0(ActivityRecord activityRecord) {
    SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
    if (sizeCompatAttributes != null) {
      sizeCompatAttributes.cleanUp(this);
    }
  }

  public final int getCompatSandboxFlags(ActivityRecord activityRecord) {
    int i = supportsSandboxDisplay(activityRecord) ? 2 : 0;
    if (supportsSandboxViewBounds(activityRecord)) {
      i |= 4;
    }
    if (supportsSandboxMotionEvent(activityRecord)) {
      i |= 8;
    }
    if (supportsSandboxInsetsHint(activityRecord)) {
      i |= 64;
    }
    return supportsMockFullScreen() ? i | 16 : i;
  }

  public final float getCompatSandboxScale(ActivityRecord activityRecord, int i) {
    if ((i & 76) == 0) {
      return -1.0f;
    }
    SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
    if (sizeCompatAttributes == null || !sizeCompatAttributes.hasBounds()) {
      return 1.0f;
    }
    return activityRecord.mSizeCompatAttributes.getScale();
  }

  public final Rect getCompatSandboxBounds(ActivityRecord activityRecord, int i) {
    if ((i & 12) == 0) {
      return null;
    }
    SizeCompatAttributes sizeCompatAttributes = activityRecord.mSizeCompatAttributes;
    return (sizeCompatAttributes == null || !sizeCompatAttributes.hasBounds())
        ? CompatSandbox.getEmptyRect()
        : activityRecord.mSizeCompatAttributes.getBounds();
  }

  public final void dumpInternal(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.print("SizeCompatPolicy: ");
    printWriter.print("Mode=" + SizeCompatInfo.sizeCompatModeToString(getMode()));
    if (!isEnabled()) {
      printWriter.print(", Enabled=false");
    }
    printWriter.print(", Size=" + this.mWidth + "x" + this.mHeight);
    printWriter.println();
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append("  ");
    dump(printWriter, sb.toString());
  }
}
