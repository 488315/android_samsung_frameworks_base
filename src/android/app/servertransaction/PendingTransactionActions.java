package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityThread;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.TransactionTooLargeException;
import android.util.Log;
import com.android.internal.util.IndentingPrintWriter;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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

    public void setRestoreInstanceState(boolean z) {
        this.mRestoreInstanceState = z;
    }

    public boolean shouldCallOnPostCreate() {
        return this.mCallOnPostCreate;
    }

    public void setCallOnPostCreate(boolean z) {
        this.mCallOnPostCreate = z;
    }

    public Bundle getOldState() {
        return this.mOldState;
    }

    public void setOldState(Bundle bundle) {
        this.mOldState = bundle;
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

        public void setActivity(ActivityThread.ActivityClientRecord activityClientRecord) {
            this.mActivity = activityClientRecord;
        }

        public void setState(Bundle bundle) {
            this.mState = bundle;
        }

        public void setPersistentState(PersistableBundle persistableBundle) {
            this.mPersistentState = persistableBundle;
        }

        public void setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
        }

        private String collectBundleStates() {
            StringWriter stringWriter = new StringWriter();
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(stringWriter, "  ");
            indentingPrintWriter.println("Bundle stats:");
            Bundle.dumpStats(indentingPrintWriter, this.mState);
            indentingPrintWriter.println("PersistableBundle stats:");
            Bundle.dumpStats(indentingPrintWriter, this.mPersistentState);
            return stringWriter.toString().stripTrailing();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                ActivityClient.getInstance().activityStopped(this.mActivity.token, this.mState, this.mPersistentState, this.mDescription);
            } catch (RuntimeException e) {
                String strCollectBundleStates = collectBundleStates();
                if (e.getCause() instanceof TransactionTooLargeException) {
                    RuntimeException runtimeException = new RuntimeException(e.getMessage() + ShaderAssembler.NEWLINE + strCollectBundleStates, e.getCause());
                    if (this.mActivity.packageInfo.getTargetSdkVersion() < 24) {
                        Log.e(TAG, "App sent too much data in instance state, so it was ignored", runtimeException);
                        return;
                    }
                    throw runtimeException;
                }
                Log.w(TAG, strCollectBundleStates);
                throw e;
            }
        }
    }
}
