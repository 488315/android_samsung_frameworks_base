package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TaskStackBuilder {
    private static final String TAG = "TaskStackBuilder";
    private final ArrayList<Intent> mIntents = new ArrayList<>();
    private final Context mSourceContext;

    private TaskStackBuilder(Context context) {
        this.mSourceContext = context;
    }

    public static TaskStackBuilder create(Context context) {
        return new TaskStackBuilder(context);
    }

    public TaskStackBuilder addNextIntent(Intent intent) {
        this.mIntents.add(intent);
        return this;
    }

    public TaskStackBuilder addNextIntentWithParentStack(Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null) {
            component = intent.resolveActivity(this.mSourceContext.getPackageManager());
        }
        if (component != null) {
            addParentStack(component);
        }
        addNextIntent(intent);
        return this;
    }

    public TaskStackBuilder addParentStack(Activity activity) {
        Intent parentActivityIntent = activity.getParentActivityIntent();
        if (parentActivityIntent != null) {
            ComponentName component = parentActivityIntent.getComponent();
            if (component == null) {
                component = parentActivityIntent.resolveActivity(this.mSourceContext.getPackageManager());
            }
            addParentStack(component);
            addNextIntent(parentActivityIntent);
        }
        return this;
    }

    public TaskStackBuilder addParentStack(Class<?> cls) {
        return addParentStack(new ComponentName(this.mSourceContext, cls));
    }

    public TaskStackBuilder addParentStack(ComponentName componentName) {
        Intent component;
        int size = this.mIntents.size();
        PackageManager packageManager = this.mSourceContext.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 0);
            String str = activityInfo.parentActivityName;
            while (str != null) {
                ComponentName componentName2 = new ComponentName(activityInfo.packageName, str);
                activityInfo = packageManager.getActivityInfo(componentName2, 0);
                str = activityInfo.parentActivityName;
                if (str == null && size == 0) {
                    component = Intent.makeMainActivity(componentName2);
                } else {
                    component = new Intent().setComponent(componentName2);
                }
                this.mIntents.add(size, component);
            }
            return this;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    public int getIntentCount() {
        return this.mIntents.size();
    }

    public Intent editIntentAt(int i) {
        return this.mIntents.get(i);
    }

    public void startActivities() {
        startActivities(null);
    }

    public int startActivities(Bundle bundle, UserHandle userHandle) {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        return this.mSourceContext.startActivitiesAsUser(getIntents(), bundle, userHandle);
    }

    public void startActivities(Bundle bundle) {
        startActivities(bundle, this.mSourceContext.getUser());
    }

    public PendingIntent getPendingIntent(int i, int i2) {
        return getPendingIntent(i, i2, null);
    }

    public PendingIntent getPendingIntent(int i, int i2, Bundle bundle) {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        return PendingIntent.getActivities(this.mSourceContext, i, getIntents(), i2, bundle);
    }

    public PendingIntent getPendingIntent(int i, int i2, Bundle bundle, UserHandle userHandle) {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        return PendingIntent.getActivitiesAsUser(this.mSourceContext, i, getIntents(), i2, bundle, userHandle);
    }

    public Intent[] getIntents() {
        int size = this.mIntents.size();
        Intent[] intentArr = new Intent[size];
        if (size != 0) {
            intentArr[0] = new Intent(this.mIntents.get(0)).addFlags(268484608);
            for (int i = 1; i < size; i++) {
                intentArr[i] = new Intent(this.mIntents.get(i));
            }
        }
        return intentArr;
    }
}
