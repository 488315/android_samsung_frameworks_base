package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.TransactionTooLargeException;
import android.util.Log;
import com.android.internal.util.IndentingPrintWriter;
import java.io.StringWriter;

/* loaded from: classes.dex */
public class PendingTransactionActions {
  private boolean mCallOnPostCreate;
  private Bundle mOldState;
  private boolean mRestoreInstanceState;
  private StopInfo mStopInfo;

  public PendingTransactionActions() {
    clear();
  }

  public void clear() {
    this.mRestoreInstanceState = false;
    this.mCallOnPostCreate = false;
    this.mOldState = null;
    this.mStopInfo = null;
  }

  public boolean shouldRestoreInstanceState() {
    return this.mRestoreInstanceState;
  }

  public void setRestoreInstanceState(boolean restoreInstanceState) {
    this.mRestoreInstanceState = restoreInstanceState;
  }

  public boolean shouldCallOnPostCreate() {
    return this.mCallOnPostCreate;
  }

  public void setCallOnPostCreate(boolean callOnPostCreate) {
    this.mCallOnPostCreate = callOnPostCreate;
  }

  public Bundle getOldState() {
    return this.mOldState;
  }

  public void setOldState(Bundle oldState) {
    this.mOldState = oldState;
  }

  public StopInfo getStopInfo() {
    return this.mStopInfo;
  }

  public void setStopInfo(StopInfo stopInfo) {
    this.mStopInfo = stopInfo;
  }

  public static class StopInfo implements Runnable {
    private static final String TAG = "ActivityStopInfo";
    private ActivityThread.ActivityClientRecord mActivity;
    private CharSequence mDescription;
    private PersistableBundle mPersistentState;
    private Bundle mState;

    public void setActivity(ActivityThread.ActivityClientRecord activity) {
      this.mActivity = activity;
    }

    public void setState(Bundle state) {
      this.mState = state;
    }

    public void setPersistentState(PersistableBundle persistentState) {
      this.mPersistentState = persistentState;
    }

    public void setDescription(CharSequence description) {
      this.mDescription = description;
    }

    private String collectBundleStates() {
      StringWriter writer = new StringWriter();
      IndentingPrintWriter pw = new IndentingPrintWriter(writer, "  ");
      pw.println("Bundle stats:");
      Bundle.dumpStats(pw, this.mState);
      pw.println("PersistableBundle stats:");
      Bundle.dumpStats(pw, this.mPersistentState);
      return writer.toString().stripTrailing();
    }

    @Override // java.lang.Runnable
    public void run() {
      try {
        ActivityClient.getInstance()
            .activityStopped(
                this.mActivity.token, this.mState, this.mPersistentState, this.mDescription);
      } catch (RuntimeException runtimeException) {
        String bundleStats = collectBundleStates();
        if (runtimeException.getCause() instanceof TransactionTooLargeException) {
          String message = runtimeException.getMessage() + "\n" + bundleStats;
          RuntimeException ex = new RuntimeException(message, runtimeException.getCause());
          if (this.mActivity.packageInfo.getTargetSdkVersion() < 24) {
            Log.m97e(TAG, "App sent too much data in instance state, so it was ignored", ex);
            return;
          }
          throw ex;
        }
        Log.m102w(TAG, bundleStats);
        throw runtimeException;
      }
    }
  }
}
