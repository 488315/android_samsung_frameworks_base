package android.window;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowManager;

import java.util.Iterator;

public final class TransitionFilter implements Parcelable {
    public static final int CONTAINER_ORDER_ANY = 0;
    public static final int CONTAINER_ORDER_TOP = 1;
    public static final Parcelable.Creator<TransitionFilter> CREATOR =
            new Parcelable.Creator<
                    TransitionFilter>() { // from class: android.window.TransitionFilter.1
                @Override // android.os.Parcelable.Creator
                public TransitionFilter createFromParcel(Parcel in) {
                    return new TransitionFilter(in);
                }

                @Override // android.os.Parcelable.Creator
                public TransitionFilter[] newArray(int size) {
                    return new TransitionFilter[size];
                }
            };
    public int mFlags;
    public int mNotFlags;
    public Requirement[] mRequirements;
    public int[] mTypeSet;

    public @interface ContainerOrder {}

    public TransitionFilter() {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
    }

    private TransitionFilter(Parcel in) {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
        this.mTypeSet = in.createIntArray();
        this.mFlags = in.readInt();
        this.mNotFlags = in.readInt();
        this.mRequirements = (Requirement[]) in.createTypedArray(Requirement.CREATOR);
    }

    public boolean matches(TransitionInfo info) {
        if (this.mTypeSet != null) {
            boolean typePass = false;
            int i = 0;
            while (true) {
                if (i >= this.mTypeSet.length) {
                    break;
                }
                if (info.getType() != this.mTypeSet[i]) {
                    i++;
                } else {
                    typePass = true;
                    break;
                }
            }
            if (!typePass) {
                return false;
            }
        }
        if ((info.getFlags() & this.mFlags) != this.mFlags
                || (info.getFlags() & this.mNotFlags) != 0) {
            return false;
        }
        if (this.mRequirements != null) {
            for (int i2 = 0; i2 < this.mRequirements.length; i2++) {
                boolean matches = this.mRequirements[i2].matches(info);
                if (matches == this.mRequirements[i2].mNot) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.mTypeSet);
        dest.writeInt(this.mFlags);
        dest.writeInt(this.mNotFlags);
        dest.writeTypedArray(this.mRequirements, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{types=[");
        if (this.mTypeSet != null) {
            int i = 0;
            while (i < this.mTypeSet.length) {
                sb.append(
                        (i == 0 ? "" : ",") + WindowManager.transitTypeToString(this.mTypeSet[i]));
                i++;
            }
        }
        sb.append("] flags=0x" + Integer.toHexString(this.mFlags));
        sb.append("] notFlags=0x" + Integer.toHexString(this.mNotFlags));
        sb.append(" checks=[");
        if (this.mRequirements != null) {
            int i2 = 0;
            while (i2 < this.mRequirements.length) {
                sb.append((i2 == 0 ? "" : ",") + this.mRequirements[i2]);
                i2++;
            }
        }
        return sb.append("]}").toString();
    }

    public static final class Requirement implements Parcelable {
        public static final Parcelable.Creator<Requirement> CREATOR =
                new Parcelable.Creator<
                        Requirement>() { // from class:
                                         // android.window.TransitionFilter.Requirement.1
                    @Override // android.os.Parcelable.Creator
                    public Requirement createFromParcel(Parcel in) {
                        return new Requirement(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public Requirement[] newArray(int size) {
                        return new Requirement[size];
                    }
                };
        public int mActivityType;
        public int mFlags;
        public IBinder mLaunchCookie;
        public int[] mModes;
        public boolean mMustBeIndependent;
        public boolean mMustBeTask;
        public boolean mNot;
        public int mOrder;
        public ComponentName mTopActivity;

        public Requirement() {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
        }

        private Requirement(Parcel in) {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
            this.mActivityType = in.readInt();
            this.mMustBeIndependent = in.readBoolean();
            this.mNot = in.readBoolean();
            this.mModes = in.createIntArray();
            this.mFlags = in.readInt();
            this.mMustBeTask = in.readBoolean();
            this.mOrder = in.readInt();
            this.mTopActivity = (ComponentName) in.readTypedObject(ComponentName.CREATOR);
            this.mLaunchCookie = in.readStrongBinder();
        }

        boolean matches(TransitionInfo info) {
            for (int i = info.getChanges().size() - 1; i >= 0; i--) {
                TransitionInfo.Change change = info.getChanges().get(i);
                if ((!this.mMustBeIndependent || TransitionInfo.isIndependent(change, info))
                        && ((this.mOrder != 1 || i <= 0)
                                && ((this.mActivityType == 0
                                                || (change.getTaskInfo() != null
                                                        && change.getTaskInfo().getActivityType()
                                                                == this.mActivityType))
                                        && matchesTopActivity(
                                                change.getTaskInfo(),
                                                change.getActivityComponent())))) {
                    if (this.mModes != null) {
                        boolean pass = false;
                        int m = 0;
                        while (true) {
                            if (m >= this.mModes.length) {
                                break;
                            }
                            if (this.mModes[m] != change.getMode()) {
                                m++;
                            } else {
                                pass = true;
                                break;
                            }
                        }
                        if (!pass) {
                            continue;
                        }
                    }
                    if ((change.getFlags() & this.mFlags) == this.mFlags
                            && ((!this.mMustBeTask || change.getTaskInfo() != null)
                                    && matchesCookie(change.getTaskInfo()))) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean matchesTopActivity(
                ActivityManager.RunningTaskInfo taskInfo, ComponentName activityComponent) {
            if (this.mTopActivity == null) {
                return true;
            }
            if (activityComponent != null) {
                return this.mTopActivity.equals(activityComponent);
            }
            if (taskInfo != null) {
                return this.mTopActivity.equals(taskInfo.topActivity);
            }
            return false;
        }

        private boolean matchesCookie(ActivityManager.RunningTaskInfo info) {
            if (this.mLaunchCookie == null) {
                return true;
            }
            if (info == null) {
                return false;
            }
            Iterator<IBinder> it = info.launchCookies.iterator();
            while (it.hasNext()) {
                IBinder cookie = it.next();
                if (this.mLaunchCookie.equals(cookie)) {
                    return true;
                }
            }
            return false;
        }

        boolean matches(TransitionRequestInfo request) {
            if (this.mActivityType == 0) {
                return true;
            }
            return request.getTriggerTask() != null
                    && request.getTriggerTask().getActivityType() == this.mActivityType
                    && matchesTopActivity(request.getTriggerTask(), null)
                    && matchesCookie(request.getTriggerTask());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mActivityType);
            dest.writeBoolean(this.mMustBeIndependent);
            dest.writeBoolean(this.mNot);
            dest.writeIntArray(this.mModes);
            dest.writeInt(this.mFlags);
            dest.writeBoolean(this.mMustBeTask);
            dest.writeInt(this.mOrder);
            dest.writeTypedObject(this.mTopActivity, flags);
            dest.writeStrongBinder(this.mLaunchCookie);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder out = new StringBuilder();
            out.append('{');
            if (this.mNot) {
                out.append("NOT ");
            }
            out.append("atype=" + WindowConfiguration.activityTypeToString(this.mActivityType));
            out.append(" independent=" + this.mMustBeIndependent);
            out.append(" modes=[");
            if (this.mModes != null) {
                int i = 0;
                while (i < this.mModes.length) {
                    out.append((i == 0 ? "" : ",") + TransitionInfo.modeToString(this.mModes[i]));
                    i++;
                }
            }
            out.append(NavigationBarInflaterView.SIZE_MOD_END);
            out.append(" flags=" + TransitionInfo.flagsToString(this.mFlags));
            out.append(" mustBeTask=" + this.mMustBeTask);
            out.append(" order=" + TransitionFilter.containerOrderToString(this.mOrder));
            out.append(" topActivity=").append(this.mTopActivity);
            out.append(" launchCookie=").append(this.mLaunchCookie);
            out.append("}");
            return out.toString();
        }
    }

    public static String containerOrderToString(int order) {
        switch (order) {
            case 0:
                return "ANY";
            case 1:
                return "TOP";
            default:
                return "UNKNOWN(" + order + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
