package android.app.supervision;

import android.os.PersistableBundle;

/* loaded from: classes.dex */
public abstract class SupervisionManagerInternal {
    public abstract boolean isActiveSupervisionApp(int i);

    public abstract boolean isSupervisionEnabledForUser(int i);

    public abstract boolean isSupervisionLockscreenEnabledForUser(int i);

    public abstract void setSupervisionEnabledForUser(int i, boolean z);

    public abstract void setSupervisionLockscreenEnabledForUser(int i, boolean z, PersistableBundle persistableBundle);
}
