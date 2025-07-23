package android.app;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.servertransaction.PendingTransactionActions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public class LocalActivityManager {
    static final int CREATED = 2;
    static final int DESTROYED = 5;
    static final int INITIALIZING = 1;
    static final int RESTORED = 0;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    private static final String TAG = "LocalActivityManager";
    private static final boolean localLOGV = false;
    private boolean mFinishing;
    private final Activity mParent;
    private LocalActivityRecord mResumed;
    private boolean mSingleMode;
    private final Map<String, LocalActivityRecord> mActivities = new HashMap();
    private final ArrayList<LocalActivityRecord> mActivityArray = new ArrayList<>();
    private int mCurState = 1;
    private final ActivityThread mActivityThread = ActivityThread.currentActivityThread();

    private static class LocalActivityRecord extends Binder {
        Activity activity;
        ActivityInfo activityInfo;
        int curState = 0;
        final String id;
        Bundle instanceState;
        Intent intent;
        Window window;

        LocalActivityRecord(String str, Intent intent) {
            this.id = str;
            this.intent = intent;
        }
    }

    public LocalActivityManager(Activity activity, boolean z) {
        this.mParent = activity;
        this.mSingleMode = z;
    }

    private void moveToState(LocalActivityRecord localActivityRecord, int i) {
        Activity.NonConfigurationInstances nonConfigurationInstances;
        PendingTransactionActions pendingTransactionActions;
        if (localActivityRecord.curState == 0 || localActivityRecord.curState == 5) {
            return;
        }
        if (localActivityRecord.curState == 1) {
            HashMap<String, Object> lastNonConfigurationChildInstances = this.mParent.getLastNonConfigurationChildInstances();
            Object obj = lastNonConfigurationChildInstances != null ? lastNonConfigurationChildInstances.get(localActivityRecord.id) : null;
            if (obj != null) {
                Activity.NonConfigurationInstances nonConfigurationInstances2 = new Activity.NonConfigurationInstances();
                nonConfigurationInstances2.activity = obj;
                nonConfigurationInstances = nonConfigurationInstances2;
            } else {
                nonConfigurationInstances = null;
            }
            if (localActivityRecord.activityInfo == null) {
                localActivityRecord.activityInfo = this.mActivityThread.resolveActivityInfo(localActivityRecord.intent);
            }
            localActivityRecord.activity = this.mActivityThread.startActivityNow(this.mParent, localActivityRecord.id, localActivityRecord.intent, localActivityRecord.activityInfo, localActivityRecord, localActivityRecord.instanceState, nonConfigurationInstances, localActivityRecord, localActivityRecord);
            if (localActivityRecord.activity == null) {
                return;
            }
            localActivityRecord.window = localActivityRecord.activity.getWindow();
            localActivityRecord.instanceState = null;
            ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(localActivityRecord);
            if (localActivityRecord.activity.mFinished) {
                pendingTransactionActions = null;
            } else {
                pendingTransactionActions = new PendingTransactionActions();
                pendingTransactionActions.setOldState(activityClient.state);
                pendingTransactionActions.setRestoreInstanceState(true);
                pendingTransactionActions.setCallOnPostCreate(true);
            }
            this.mActivityThread.handleStartActivity(activityClient, pendingTransactionActions, null);
            localActivityRecord.curState = 3;
            if (i == 4) {
                this.mActivityThread.performResumeActivity(activityClient, true, "moveToState-INITIALIZING");
                localActivityRecord.curState = 4;
                return;
            }
            return;
        }
        ActivityThread.ActivityClientRecord activityClient2 = this.mActivityThread.getActivityClient(localActivityRecord);
        if (activityClient2 == null) {
            Log.w(TAG, "Can't get activity record for " + localActivityRecord.id);
            return;
        }
        int i2 = localActivityRecord.curState;
        if (i2 == 2) {
            if (i == 3) {
                this.mActivityThread.performRestartActivity(activityClient2, true);
                localActivityRecord.curState = 3;
            }
            if (i == 4) {
                this.mActivityThread.performRestartActivity(activityClient2, true);
                this.mActivityThread.performResumeActivity(activityClient2, true, "moveToState-CREATED");
                localActivityRecord.curState = 4;
                return;
            }
            return;
        }
        if (i2 == 3) {
            if (i == 4) {
                this.mActivityThread.performResumeActivity(activityClient2, true, "moveToState-STARTED");
                localActivityRecord.instanceState = null;
                localActivityRecord.curState = 4;
            }
            if (i == 2) {
                this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-STARTED");
                localActivityRecord.curState = 2;
                return;
            }
            return;
        }
        if (i2 != 4) {
            return;
        }
        if (i == 3) {
            performPause(localActivityRecord, this.mFinishing);
            localActivityRecord.curState = 3;
        }
        if (i == 2) {
            performPause(localActivityRecord, this.mFinishing);
            this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-RESUMED");
            localActivityRecord.curState = 2;
        }
    }

    private void performPause(LocalActivityRecord localActivityRecord, boolean z) {
        boolean z2 = localActivityRecord.instanceState == null;
        Bundle performPauseActivity = this.mActivityThread.performPauseActivity(localActivityRecord, z, "performPause", (PendingTransactionActions) null);
        if (z2) {
            localActivityRecord.instanceState = performPauseActivity;
        }
    }

    public Window startActivity(String str, Intent intent) {
        boolean z;
        LocalActivityRecord localActivityRecord;
        if (this.mCurState == 1) {
            throw new IllegalStateException("Activities can't be added until the containing group has been created.");
        }
        LocalActivityRecord localActivityRecord2 = this.mActivities.get(str);
        boolean z2 = false;
        ActivityInfo activityInfo = null;
        if (localActivityRecord2 == null) {
            localActivityRecord2 = new LocalActivityRecord(str, intent);
            z = false;
            z2 = true;
        } else if (localActivityRecord2.intent != null) {
            z = localActivityRecord2.intent.filterEquals(intent);
            if (z) {
                activityInfo = localActivityRecord2.activityInfo;
            }
        } else {
            z = false;
        }
        if (activityInfo == null) {
            activityInfo = this.mActivityThread.resolveActivityInfo(intent);
        }
        if (this.mSingleMode && (localActivityRecord = this.mResumed) != null && localActivityRecord != localActivityRecord2 && this.mCurState == 4) {
            moveToState(localActivityRecord, 3);
        }
        if (z2) {
            this.mActivities.put(str, localActivityRecord2);
            this.mActivityArray.add(localActivityRecord2);
        } else if (localActivityRecord2.activityInfo != null) {
            if (activityInfo == localActivityRecord2.activityInfo || (activityInfo.name.equals(localActivityRecord2.activityInfo.name) && activityInfo.packageName.equals(localActivityRecord2.activityInfo.packageName))) {
                if (activityInfo.launchMode != 0 || (intent.getFlags() & 536870912) != 0) {
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(new ReferrerIntent(intent, this.mParent.getPackageName()));
                    this.mActivityThread.handleNewIntent(this.mActivityThread.getActivityClient(localActivityRecord2), arrayList);
                    localActivityRecord2.intent = intent;
                    moveToState(localActivityRecord2, this.mCurState);
                    if (this.mSingleMode) {
                        this.mResumed = localActivityRecord2;
                    }
                    return localActivityRecord2.window;
                }
                if (z && (intent.getFlags() & 67108864) == 0) {
                    localActivityRecord2.intent = intent;
                    moveToState(localActivityRecord2, this.mCurState);
                    if (this.mSingleMode) {
                        this.mResumed = localActivityRecord2;
                    }
                    return localActivityRecord2.window;
                }
            }
            performDestroy(localActivityRecord2, true);
        }
        localActivityRecord2.intent = intent;
        localActivityRecord2.curState = 1;
        localActivityRecord2.activityInfo = activityInfo;
        moveToState(localActivityRecord2, this.mCurState);
        if (this.mSingleMode) {
            this.mResumed = localActivityRecord2;
        }
        return localActivityRecord2.window;
    }

    private Window performDestroy(LocalActivityRecord localActivityRecord, boolean z) {
        Window window = localActivityRecord.window;
        if (localActivityRecord.curState == 4 && !z) {
            performPause(localActivityRecord, z);
        }
        ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(localActivityRecord);
        if (activityClient != null) {
            this.mActivityThread.performDestroyActivity(activityClient, z, false, "LocalActivityManager::performDestroy");
        }
        localActivityRecord.activity = null;
        localActivityRecord.window = null;
        if (z) {
            localActivityRecord.instanceState = null;
        }
        localActivityRecord.curState = 5;
        return window;
    }

    public Window destroyActivity(String str, boolean z) {
        LocalActivityRecord localActivityRecord = this.mActivities.get(str);
        if (localActivityRecord == null) {
            return null;
        }
        Window performDestroy = performDestroy(localActivityRecord, z);
        if (z) {
            this.mActivities.remove(str);
            this.mActivityArray.remove(localActivityRecord);
        }
        return performDestroy;
    }

    public Activity getCurrentActivity() {
        LocalActivityRecord localActivityRecord = this.mResumed;
        if (localActivityRecord != null) {
            return localActivityRecord.activity;
        }
        return null;
    }

    public String getCurrentId() {
        LocalActivityRecord localActivityRecord = this.mResumed;
        if (localActivityRecord != null) {
            return localActivityRecord.id;
        }
        return null;
    }

    public Activity getActivity(String str) {
        LocalActivityRecord localActivityRecord = this.mActivities.get(str);
        if (localActivityRecord != null) {
            return localActivityRecord.activity;
        }
        return null;
    }

    public void dispatchCreate(Bundle bundle) {
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                try {
                    Bundle bundle2 = bundle.getBundle(str);
                    LocalActivityRecord localActivityRecord = this.mActivities.get(str);
                    if (localActivityRecord != null) {
                        localActivityRecord.instanceState = bundle2;
                    } else {
                        LocalActivityRecord localActivityRecord2 = new LocalActivityRecord(str, null);
                        localActivityRecord2.instanceState = bundle2;
                        this.mActivities.put(str, localActivityRecord2);
                        this.mActivityArray.add(localActivityRecord2);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception thrown when restoring LocalActivityManager state", e);
                }
            }
        }
        this.mCurState = 2;
    }

    public Bundle saveInstanceState() {
        int size = this.mActivityArray.size();
        Bundle bundle = null;
        for (int i = 0; i < size; i++) {
            LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            if (bundle == null) {
                bundle = new Bundle();
            }
            if ((localActivityRecord.instanceState != null || localActivityRecord.curState == 4) && localActivityRecord.activity != null) {
                Bundle bundle2 = new Bundle();
                localActivityRecord.activity.performSaveInstanceState(bundle2);
                localActivityRecord.instanceState = bundle2;
            }
            if (localActivityRecord.instanceState != null) {
                bundle.putBundle(localActivityRecord.id, localActivityRecord.instanceState);
            }
        }
        return bundle;
    }

    public void dispatchResume() {
        this.mCurState = 4;
        if (this.mSingleMode) {
            LocalActivityRecord localActivityRecord = this.mResumed;
            if (localActivityRecord != null) {
                moveToState(localActivityRecord, 4);
                return;
            }
            return;
        }
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            moveToState(this.mActivityArray.get(i), 4);
        }
    }

    public void dispatchPause(boolean z) {
        if (z) {
            this.mFinishing = true;
        }
        this.mCurState = 3;
        if (this.mSingleMode) {
            LocalActivityRecord localActivityRecord = this.mResumed;
            if (localActivityRecord != null) {
                moveToState(localActivityRecord, 3);
                return;
            }
            return;
        }
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            LocalActivityRecord localActivityRecord2 = this.mActivityArray.get(i);
            if (localActivityRecord2.curState == 4) {
                moveToState(localActivityRecord2, 3);
            }
        }
    }

    public void dispatchStop() {
        this.mCurState = 2;
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            moveToState(this.mActivityArray.get(i), 2);
        }
    }

    public HashMap<String, Object> dispatchRetainNonConfigurationInstance() {
        Object onRetainNonConfigurationInstance;
        int size = this.mActivityArray.size();
        HashMap<String, Object> hashMap = null;
        for (int i = 0; i < size; i++) {
            LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            if (localActivityRecord != null && localActivityRecord.activity != null && (onRetainNonConfigurationInstance = localActivityRecord.activity.onRetainNonConfigurationInstance()) != null) {
                if (hashMap == null) {
                    hashMap = new HashMap<>();
                }
                hashMap.put(localActivityRecord.id, onRetainNonConfigurationInstance);
            }
        }
        return hashMap;
    }

    public void removeAllActivities() {
        dispatchDestroy(true);
    }

    public void dispatchDestroy(boolean z) {
        int size = this.mActivityArray.size();
        for (int i = 0; i < size; i++) {
            ActivityThread.ActivityClientRecord activityClient = this.mActivityThread.getActivityClient(this.mActivityArray.get(i));
            if (activityClient != null) {
                this.mActivityThread.performDestroyActivity(activityClient, z, false, "LocalActivityManager::dispatchDestroy");
            }
        }
        this.mActivities.clear();
        this.mActivityArray.clear();
    }
}
