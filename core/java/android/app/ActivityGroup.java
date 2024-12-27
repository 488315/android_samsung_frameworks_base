package android.app;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

@Deprecated
/* loaded from: classes.dex */
public class ActivityGroup extends Activity {
    static final String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";
    private static final String STATES_KEY = "android:states";
    protected LocalActivityManager mLocalActivityManager;

    public ActivityGroup() {
        this(true);
    }

    public ActivityGroup(boolean singleActivityMode) {
        this.mLocalActivityManager = new LocalActivityManager(this, singleActivityMode);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle states =
                savedInstanceState != null ? savedInstanceState.getBundle(STATES_KEY) : null;
        this.mLocalActivityManager.dispatchCreate(states);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mLocalActivityManager.dispatchResume();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle state = this.mLocalActivityManager.saveInstanceState();
        if (state != null) {
            outState.putBundle(STATES_KEY, state);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mLocalActivityManager.dispatchPause(isFinishing());
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mLocalActivityManager.dispatchStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mLocalActivityManager.dispatchDestroy(isFinishing());
    }

    @Override // android.app.Activity
    public HashMap<String, Object> onRetainNonConfigurationChildInstances() {
        return this.mLocalActivityManager.dispatchRetainNonConfigurationInstance();
    }

    public Activity getCurrentActivity() {
        return this.mLocalActivityManager.getCurrentActivity();
    }

    public final LocalActivityManager getLocalActivityManager() {
        return this.mLocalActivityManager;
    }

    @Override // android.app.Activity
    void dispatchActivityResult(
            String who,
            int requestCode,
            int resultCode,
            Intent data,
            ComponentCaller caller,
            String reason) {
        Activity act;
        if (who != null && (act = this.mLocalActivityManager.getActivity(who)) != null) {
            act.onActivityResult(requestCode, resultCode, data);
        } else {
            super.dispatchActivityResult(who, requestCode, resultCode, data, caller, reason);
        }
    }
}
