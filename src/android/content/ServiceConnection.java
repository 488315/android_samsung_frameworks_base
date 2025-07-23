package android.content;

import android.os.IBinder;

/* loaded from: classes.dex */
public interface ServiceConnection {
    default void onBindingDied(ComponentName componentName) {
    }

    default void onNullBinding(ComponentName componentName) {
    }

    void onServiceConnected(ComponentName componentName, IBinder iBinder);

    void onServiceDisconnected(ComponentName componentName);
}
