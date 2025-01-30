package android.content;

import android.p009os.IBinder;

/* loaded from: classes.dex */
public interface ServiceConnection {
    void onServiceConnected(ComponentName componentName, IBinder iBinder);

    void onServiceDisconnected(ComponentName componentName);

    default void onBindingDied(ComponentName name) {
    }

    default void onNullBinding(ComponentName name) {
    }
}
