package android.content;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class AttributionSource implements Parcelable {
    private static final String TAG = "AttributionSource";
    private final AttributionSourceState mAttributionSourceState;
    private AttributionSource mNextCached;
    private Set<String> mRenouncedPermissionsCached;
    private static final String DESCRIPTOR = "android.content.AttributionSource";
    private static final Binder sDefaultToken = new Binder(DESCRIPTOR);
    public static final Parcelable.Creator<AttributionSource> CREATOR = new Parcelable.Creator<AttributionSource>() { // from class: android.content.AttributionSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSource[] newArray(int i) {
            return new AttributionSource[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AttributionSource createFromParcel(Parcel parcel) {
            return new AttributionSource(parcel);
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AttributionSource(int i, String str, String str2) {
        this(i, -1, str, str2, sDefaultToken);
    }

    public AttributionSource(int i, String str, String str2, int i2) {
        this(i, -1, str, str2, sDefaultToken, null, i2, null);
    }

    public AttributionSource(int i, int i2, String str, String str2) {
        this(i, i2, str, str2, sDefaultToken);
    }

    public AttributionSource(int i, String str, String str2, IBinder iBinder) {
        this(i, -1, str, str2, iBinder, null, 0, null);
    }

    public AttributionSource(int i, int i2, String str, String str2, IBinder iBinder) {
        this(i, i2, str, str2, iBinder, null, 0, null);
    }

    public AttributionSource(int i, String str, String str2, Set<String> set, AttributionSource attributionSource) {
        this(i, -1, str, str2, sDefaultToken, set != null ? (String[]) set.toArray(new String[0]) : null, 0, attributionSource);
    }

    public AttributionSource(AttributionSource attributionSource, AttributionSource attributionSource2) {
        this(attributionSource.getUid(), attributionSource.getPid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getToken(), attributionSource.mAttributionSourceState.renouncedPermissions, attributionSource.getDeviceId(), attributionSource2);
    }

    public AttributionSource(int i, int i2, String str, String str2, String[] strArr, int i3, AttributionSource attributionSource) {
        this(i, i2, str, str2, sDefaultToken, strArr, i3, attributionSource);
    }

    public AttributionSource(int i, int i2, String str, String str2, IBinder iBinder, String[] strArr, int i3, AttributionSource attributionSource) {
        AttributionSourceState[] attributionSourceStateArr;
        AttributionSourceState attributionSourceState = new AttributionSourceState();
        this.mAttributionSourceState = attributionSourceState;
        attributionSourceState.uid = i;
        attributionSourceState.pid = i2;
        attributionSourceState.token = iBinder;
        attributionSourceState.packageName = str;
        attributionSourceState.attributionTag = str2;
        attributionSourceState.renouncedPermissions = strArr;
        attributionSourceState.deviceId = i3;
        if (attributionSource != null) {
            attributionSourceStateArr = new AttributionSourceState[]{attributionSource.mAttributionSourceState};
        } else {
            attributionSourceStateArr = new AttributionSourceState[0];
        }
        attributionSourceState.next = attributionSourceStateArr;
    }

    AttributionSource(Parcel parcel) {
        this(AttributionSourceState.CREATOR.createFromParcel(parcel));
        if (!Binder.isDirectlyHandlingTransaction()) {
            throw new SecurityException("AttributionSource should be unparceled during a binder transaction for proper verification.");
        }
        enforceCallingUid();
        if (Binder.getCallingPid() == 0) {
            this.mAttributionSourceState.pid = -1;
        }
        enforceCallingPid();
    }

    public AttributionSource(AttributionSourceState attributionSourceState) {
        this.mAttributionSourceState = attributionSourceState;
    }

    public AttributionSource withNextAttributionSource(AttributionSource attributionSource) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), attributionSource);
    }

    public AttributionSource withPackageName(String str) {
        return new AttributionSource(getUid(), getPid(), str, getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withToken(IBinder iBinder) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), iBinder, this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withDefaultToken() {
        return withToken(sDefaultToken);
    }

    public AttributionSource withPid(int i) {
        return new AttributionSource(getUid(), i, getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, getDeviceId(), getNext());
    }

    public AttributionSource withDeviceId(int i) {
        return new AttributionSource(getUid(), getPid(), getPackageName(), getAttributionTag(), getToken(), this.mAttributionSourceState.renouncedPermissions, i, getNext());
    }

    public AttributionSourceState asState() {
        return this.mAttributionSourceState;
    }

    public ScopedParcelState asScopedParcelState() {
        return new ScopedParcelState(this);
    }

    public static AttributionSource myAttributionSource() {
        AttributionSource currentAttributionSource = ActivityThread.currentAttributionSource();
        if (currentAttributionSource != null) {
            if (!Flags.enforceDefaultDeviceIdInMyAttributionSource() || currentAttributionSource.getDeviceId() == 0) {
                return currentAttributionSource;
            }
            Log.w(TAG, "Avoid using myAttributionSource() to fetch an attributionSource with a non-default device Id");
            return currentAttributionSource.withDeviceId(0);
        }
        int myUid = Process.myUid();
        if (myUid == 0) {
            myUid = 1000;
        }
        try {
            return new Builder(myUid).setPid(Process.myPid()).setDeviceId(0).setPackageName(AppGlobals.getPackageManager().getPackagesForUid(myUid)[0]).build();
        } catch (Exception unused) {
            throw new IllegalStateException("Failed to resolve AttributionSource");
        }
    }

    public static class ScopedParcelState implements AutoCloseable {
        private final Parcel mParcel;

        public Parcel getParcel() {
            return this.mParcel;
        }

        public ScopedParcelState(AttributionSource attributionSource) {
            Parcel obtain = Parcel.obtain();
            this.mParcel = obtain;
            attributionSource.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mParcel.recycle();
        }
    }

    public void enforceCallingUid() {
        if (checkCallingUid()) {
            return;
        }
        throw new SecurityException("Calling uid: " + Binder.getCallingUid() + " doesn't match source uid: " + this.mAttributionSourceState.uid);
    }

    public boolean checkCallingUid() {
        int callingUid = Binder.getCallingUid();
        return callingUid == 0 || UserHandle.getAppId(callingUid) == 1000 || callingUid == this.mAttributionSourceState.uid;
    }

    public void enforceCallingPid() {
        if (checkCallingPid()) {
            return;
        }
        if (Binder.getCallingPid() == 0) {
            throw new SecurityException("Calling pid unavailable due to oneway Binder call.");
        }
        throw new SecurityException("Calling pid: " + Binder.getCallingPid() + " doesn't match source pid: " + this.mAttributionSourceState.pid);
    }

    private boolean checkCallingPid() {
        return this.mAttributionSourceState.pid == -1 || Binder.getCallingPid() == this.mAttributionSourceState.pid;
    }

    public String toString() {
        boolean z = Build.IS_DEBUGGABLE;
        StringBuilder sb = new StringBuilder("AttributionSource { uid = ");
        sb.append(this.mAttributionSourceState.uid);
        sb.append(", packageName = ");
        sb.append(this.mAttributionSourceState.packageName);
        sb.append(", attributionTag = ");
        sb.append(this.mAttributionSourceState.attributionTag);
        sb.append(", token = ");
        sb.append(this.mAttributionSourceState.token);
        sb.append(", deviceId = ");
        sb.append(this.mAttributionSourceState.deviceId);
        sb.append(", next = ");
        sb.append((this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) ? null : new AttributionSource(this.mAttributionSourceState.next[0]).toString());
        sb.append(" }");
        return sb.toString();
    }

    public int getNextUid() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return -1;
        }
        return this.mAttributionSourceState.next[0].uid;
    }

    public String getNextPackageName() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return null;
        }
        return this.mAttributionSourceState.next[0].packageName;
    }

    public String getNextAttributionTag() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return null;
        }
        return this.mAttributionSourceState.next[0].attributionTag;
    }

    public IBinder getNextToken() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return null;
        }
        return this.mAttributionSourceState.next[0].token;
    }

    public int getNextDeviceId() {
        if (this.mAttributionSourceState.next == null || this.mAttributionSourceState.next.length <= 0) {
            return 0;
        }
        return this.mAttributionSourceState.next[0].deviceId;
    }

    public boolean isTrusted(Context context) {
        return this.mAttributionSourceState.token != null && ((PermissionManager) context.getSystemService(PermissionManager.class)).isRegisteredAttributionSource(this);
    }

    @SystemApi
    public Set<String> getRenouncedPermissions() {
        if (this.mRenouncedPermissionsCached == null) {
            if (this.mAttributionSourceState.renouncedPermissions != null) {
                this.mRenouncedPermissionsCached = new ArraySet(this.mAttributionSourceState.renouncedPermissions);
            } else {
                this.mRenouncedPermissionsCached = Collections.EMPTY_SET;
            }
        }
        return this.mRenouncedPermissionsCached;
    }

    public int getUid() {
        return this.mAttributionSourceState.uid;
    }

    public int getPid() {
        return this.mAttributionSourceState.pid;
    }

    public String getPackageName() {
        return this.mAttributionSourceState.packageName;
    }

    public String getAttributionTag() {
        return this.mAttributionSourceState.attributionTag;
    }

    public int getDeviceId() {
        return this.mAttributionSourceState.deviceId;
    }

    public IBinder getToken() {
        return this.mAttributionSourceState.token;
    }

    public AttributionSource getNext() {
        if (this.mNextCached == null && this.mAttributionSourceState.next != null && this.mAttributionSourceState.next.length > 0) {
            this.mNextCached = new AttributionSource(this.mAttributionSourceState.next[0]);
        }
        return this.mNextCached;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AttributionSource attributionSource = (AttributionSource) obj;
            if (equalsExceptToken(attributionSource) && Objects.equals(this.mAttributionSourceState.token, attributionSource.mAttributionSourceState.token)) {
                return true;
            }
        }
        return false;
    }

    public boolean equalsExceptToken(AttributionSource attributionSource) {
        return attributionSource != null && this.mAttributionSourceState.uid == attributionSource.mAttributionSourceState.uid && Objects.equals(this.mAttributionSourceState.packageName, attributionSource.mAttributionSourceState.packageName) && Objects.equals(this.mAttributionSourceState.attributionTag, attributionSource.mAttributionSourceState.attributionTag) && Arrays.equals(this.mAttributionSourceState.renouncedPermissions, attributionSource.mAttributionSourceState.renouncedPermissions) && Objects.equals(getNext(), attributionSource.getNext());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAttributionSourceState.uid), this.mAttributionSourceState.packageName, this.mAttributionSourceState.attributionTag, this.mAttributionSourceState.token, Integer.valueOf(Arrays.hashCode(this.mAttributionSourceState.renouncedPermissions)), getNext());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mAttributionSourceState.writeToParcel(parcel, i);
    }

    public static final class Builder {
        private final AttributionSourceState mAttributionSourceState;
        private boolean mHasBeenUsed;

        public Builder(int i) {
            AttributionSourceState attributionSourceState = new AttributionSourceState();
            this.mAttributionSourceState = attributionSourceState;
            attributionSourceState.uid = i;
            attributionSourceState.pid = -1;
            attributionSourceState.deviceId = 0;
            attributionSourceState.token = AttributionSource.sDefaultToken;
        }

        public Builder(AttributionSource attributionSource) {
            AttributionSourceState attributionSourceState = new AttributionSourceState();
            this.mAttributionSourceState = attributionSourceState;
            if (attributionSource == null) {
                throw new IllegalArgumentException("current AttributionSource can not be null");
            }
            attributionSourceState.uid = attributionSource.getUid();
            attributionSourceState.pid = attributionSource.getPid();
            attributionSourceState.packageName = attributionSource.getPackageName();
            attributionSourceState.attributionTag = attributionSource.getAttributionTag();
            attributionSourceState.renouncedPermissions = attributionSource.mAttributionSourceState.renouncedPermissions;
            attributionSourceState.deviceId = attributionSource.getDeviceId();
            attributionSourceState.next = attributionSource.mAttributionSourceState.next;
            attributionSourceState.token = attributionSource.getToken();
        }

        public Builder setPid(int i) {
            checkNotUsed();
            this.mAttributionSourceState.pid = i;
            return this;
        }

        public Builder setPackageName(String str) {
            checkNotUsed();
            this.mAttributionSourceState.packageName = str;
            return this;
        }

        public Builder setAttributionTag(String str) {
            checkNotUsed();
            this.mAttributionSourceState.attributionTag = str;
            return this;
        }

        @SystemApi
        public Builder setRenouncedPermissions(Set<String> set) {
            checkNotUsed();
            this.mAttributionSourceState.renouncedPermissions = set != null ? (String[]) set.toArray(new String[0]) : null;
            return this;
        }

        public Builder setDeviceId(int i) {
            checkNotUsed();
            this.mAttributionSourceState.deviceId = i;
            return this;
        }

        public Builder setNext(AttributionSource attributionSource) {
            checkNotUsed();
            AttributionSourceState attributionSourceState = this.mAttributionSourceState;
            attributionSourceState.next = attributionSource != null ? new AttributionSourceState[]{attributionSource.mAttributionSourceState} : attributionSourceState.next;
            return this;
        }

        public Builder setNextAttributionSource(AttributionSource attributionSource) {
            checkNotUsed();
            if (attributionSource == null) {
                throw new IllegalArgumentException("Null AttributionSource not permitted.");
            }
            this.mAttributionSourceState.next = new AttributionSourceState[]{attributionSource.mAttributionSourceState};
            return this;
        }

        public AttributionSource build() {
            checkNotUsed();
            this.mHasBeenUsed = true;
            if (this.mAttributionSourceState.next == null) {
                this.mAttributionSourceState.next = new AttributionSourceState[0];
            }
            return new AttributionSource(this.mAttributionSourceState);
        }

        private void checkNotUsed() {
            if (this.mHasBeenUsed) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
