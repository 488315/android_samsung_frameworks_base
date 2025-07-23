package android.app.admin;

import java.util.Objects;

/* loaded from: classes.dex */
public final class TargetUser {
    public static final int GLOBAL_USER_ID = -3;
    public static final int LOCAL_USER_ID = -1;
    public static final int PARENT_USER_ID = -2;
    public static final int UNKNOWN_USER_ID = -3;
    private final int mUserId;
    public static final TargetUser LOCAL_USER = new TargetUser(-1);
    public static final TargetUser PARENT_USER = new TargetUser(-2);
    public static final TargetUser GLOBAL = new TargetUser(-3);
    public static final TargetUser UNKNOWN_USER = new TargetUser(-3);

    public TargetUser(int i) {
        this.mUserId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mUserId == ((TargetUser) obj).mUserId;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUserId));
    }
}
