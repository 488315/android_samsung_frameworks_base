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

/* loaded from: classes5.dex */
public final class TransitionFilter implements Parcelable {
    public static final int CONTAINER_ORDER_ANY = 0;
    public static final int CONTAINER_ORDER_TOP = 1;
    public static final Parcelable.Creator<TransitionFilter> CREATOR = new Parcelable.Creator<TransitionFilter>() { // from class: android.window.TransitionFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionFilter createFromParcel(Parcel parcel) {
            return new TransitionFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionFilter[] newArray(int i) {
            return new TransitionFilter[i];
        }
    };
    public int mFlags;
    public int mNotFlags;
    public Requirement[] mRequirements;
    public int[] mTypeSet;

    public @interface ContainerOrder {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TransitionFilter() {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
    }

    private TransitionFilter(Parcel parcel) {
        this.mTypeSet = null;
        this.mFlags = 0;
        this.mNotFlags = 0;
        this.mRequirements = null;
        this.mTypeSet = parcel.createIntArray();
        this.mFlags = parcel.readInt();
        this.mNotFlags = parcel.readInt();
        this.mRequirements = (Requirement[]) parcel.createTypedArray(Requirement.CREATOR);
    }

    public boolean matches(TransitionInfo transitionInfo) {
        if (this.mTypeSet != null) {
            for (int i = 0; i < this.mTypeSet.length; i++) {
                if (transitionInfo.getType() != this.mTypeSet[i]) {
                }
            }
            return false;
        }
        int flags = transitionInfo.getFlags();
        int i2 = this.mFlags;
        if ((flags & i2) != i2 || (transitionInfo.getFlags() & this.mNotFlags) != 0) {
            return false;
        }
        if (this.mRequirements == null) {
            return true;
        }
        int i3 = 0;
        while (true) {
            Requirement[] requirementArr = this.mRequirements;
            if (i3 >= requirementArr.length) {
                return true;
            }
            if (requirementArr[i3].matches(transitionInfo) == this.mRequirements[i3].mNot) {
                return false;
            }
            i3++;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mTypeSet);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mNotFlags);
        parcel.writeTypedArray(this.mRequirements, i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{types=[");
        int i = 0;
        if (this.mTypeSet != null) {
            int i2 = 0;
            while (i2 < this.mTypeSet.length) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i2 == 0 ? "" : ",");
                sb2.append(WindowManager.transitTypeToString(this.mTypeSet[i2]));
                sb.append(sb2.toString());
                i2++;
            }
        }
        sb.append("] flags=0x" + Integer.toHexString(this.mFlags));
        sb.append("] notFlags=0x" + Integer.toHexString(this.mNotFlags));
        sb.append(" checks=[");
        if (this.mRequirements != null) {
            while (i < this.mRequirements.length) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i == 0 ? "" : ",");
                sb3.append(this.mRequirements[i]);
                sb.append(sb3.toString());
                i++;
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public static final class Requirement implements Parcelable {
        public static final Parcelable.Creator<Requirement> CREATOR = new Parcelable.Creator<Requirement>() { // from class: android.window.TransitionFilter.Requirement.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Requirement createFromParcel(Parcel parcel) {
                return new Requirement(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Requirement[] newArray(int i) {
                return new Requirement[i];
            }
        };
        public int mActivityType;
        public Boolean mCustomAnimation;
        public int mFlags;
        public IBinder mLaunchCookie;
        public int[] mModes;
        public boolean mMustBeIndependent;
        public boolean mMustBeTask;
        public boolean mNot;
        public int mOrder;
        public IBinder mTaskFragmentToken;
        public ComponentName mTopActivity;
        public int mWindowingMode;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Requirement() {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
            this.mCustomAnimation = null;
            this.mTaskFragmentToken = null;
            this.mWindowingMode = 0;
        }

        private Requirement(Parcel parcel) {
            this.mActivityType = 0;
            this.mMustBeIndependent = true;
            this.mNot = false;
            Boolean bool = null;
            this.mModes = null;
            this.mFlags = 0;
            this.mMustBeTask = false;
            this.mOrder = 0;
            this.mCustomAnimation = null;
            this.mTaskFragmentToken = null;
            this.mWindowingMode = 0;
            this.mActivityType = parcel.readInt();
            this.mMustBeIndependent = parcel.readBoolean();
            this.mNot = parcel.readBoolean();
            this.mModes = parcel.createIntArray();
            this.mFlags = parcel.readInt();
            this.mMustBeTask = parcel.readBoolean();
            this.mOrder = parcel.readInt();
            this.mTopActivity = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            this.mLaunchCookie = parcel.readStrongBinder();
            int readInt = parcel.readInt();
            if (readInt != 0) {
                bool = Boolean.valueOf(readInt == 2);
            }
            this.mCustomAnimation = bool;
            this.mTaskFragmentToken = parcel.readStrongBinder();
            this.mWindowingMode = parcel.readInt();
        }

        /* JADX WARN: Code restructure failed: missing block: B:38:0x00e4, code lost:
        
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        boolean matches(android.window.TransitionInfo r8) {
            /*
                r7 = this;
                java.util.List r0 = r8.getChanges()
                int r0 = r0.size()
                r1 = 1
                int r0 = r0 - r1
            La:
                r2 = 0
                if (r0 < 0) goto Le9
                java.util.List r3 = r8.getChanges()
                java.lang.Object r3 = r3.get(r0)
                android.window.TransitionInfo$Change r3 = (android.window.TransitionInfo.Change) r3
                android.os.IBinder r4 = r7.mTaskFragmentToken
                if (r4 == 0) goto L27
                android.os.IBinder r5 = r3.getTaskFragmentToken()
                boolean r4 = r4.equals(r5)
                if (r4 != 0) goto L27
                goto Le4
            L27:
                boolean r4 = r7.mMustBeIndependent
                if (r4 == 0) goto L33
                boolean r4 = android.window.TransitionInfo.isIndependent(r3, r8)
                if (r4 != 0) goto L33
                goto Le4
            L33:
                int r4 = r7.mOrder
                if (r4 != r1) goto L3b
                if (r0 <= 0) goto L3b
                goto Le4
            L3b:
                int r4 = r7.mActivityType
                if (r4 == 0) goto L53
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                if (r4 == 0) goto Le4
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                int r4 = r4.getActivityType()
                int r5 = r7.mActivityType
                if (r4 == r5) goto L53
                goto Le4
            L53:
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                android.content.ComponentName r5 = r3.getActivityComponent()
                boolean r4 = r7.matchesTopActivity(r4, r5)
                if (r4 != 0) goto L63
                goto Le4
            L63:
                int[] r4 = r7.mModes
                if (r4 == 0) goto L79
                r4 = r2
            L68:
                int[] r5 = r7.mModes
                int r6 = r5.length
                if (r4 >= r6) goto Le4
                r5 = r5[r4]
                int r6 = r3.getMode()
                if (r5 != r6) goto L76
                goto L79
            L76:
                int r4 = r4 + 1
                goto L68
            L79:
                int r4 = r3.getFlags()
                int r5 = r7.mFlags
                r4 = r4 & r5
                if (r4 == r5) goto L83
                goto Le4
            L83:
                boolean r4 = r7.mMustBeTask
                if (r4 == 0) goto L8e
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                if (r4 != 0) goto L8e
                goto Le4
            L8e:
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                boolean r4 = r7.matchesCookie(r4)
                if (r4 != 0) goto L99
                goto Le4
            L99:
                java.lang.Boolean r4 = r7.mCustomAnimation
                if (r4 == 0) goto Lce
                android.app.ActivityManager$RunningTaskInfo r4 = r3.getTaskInfo()
                if (r4 != 0) goto La9
                android.content.ComponentName r4 = r3.getActivityComponent()
                if (r4 == 0) goto Lce
            La9:
                android.window.TransitionInfo$AnimationOptions r4 = r3.getAnimationOptions()
                if (r4 == 0) goto Lc5
                android.app.ActivityManager$RunningTaskInfo r5 = r3.getTaskInfo()
                if (r5 == 0) goto Lbb
                boolean r4 = r4.getOverrideTaskTransition()
                if (r4 == 0) goto Lbc
            Lbb:
                r2 = r1
            Lbc:
                java.lang.Boolean r4 = r7.mCustomAnimation
                boolean r4 = r4.booleanValue()
                if (r4 == r2) goto Lce
                goto Le4
            Lc5:
                java.lang.Boolean r2 = r7.mCustomAnimation
                boolean r2 = r2.booleanValue()
                if (r2 == 0) goto Lce
                goto Le4
            Lce:
                int r2 = r7.mWindowingMode
                if (r2 == 0) goto Le8
                android.app.ActivityManager$RunningTaskInfo r2 = r3.getTaskInfo()
                if (r2 == 0) goto Le4
                android.app.ActivityManager$RunningTaskInfo r2 = r3.getTaskInfo()
                int r2 = r2.getWindowingMode()
                int r3 = r7.mWindowingMode
                if (r2 == r3) goto Le8
            Le4:
                int r0 = r0 + (-1)
                goto La
            Le8:
                return r1
            Le9:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.window.TransitionFilter.Requirement.matches(android.window.TransitionInfo):boolean");
        }

        private boolean matchesTopActivity(ActivityManager.RunningTaskInfo runningTaskInfo, ComponentName componentName) {
            ComponentName componentName2 = this.mTopActivity;
            if (componentName2 == null) {
                return true;
            }
            if (componentName != null) {
                return componentName2.equals(componentName);
            }
            if (runningTaskInfo != null) {
                return componentName2.equals(runningTaskInfo.topActivity);
            }
            return false;
        }

        private boolean matchesCookie(ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (this.mLaunchCookie == null) {
                return true;
            }
            if (runningTaskInfo == null) {
                return false;
            }
            Iterator<IBinder> it = runningTaskInfo.launchCookies.iterator();
            while (it.hasNext()) {
                if (this.mLaunchCookie.equals(it.next())) {
                    return true;
                }
            }
            return false;
        }

        boolean matches(TransitionRequestInfo transitionRequestInfo) {
            if (this.mActivityType == 0) {
                return true;
            }
            return transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getActivityType() == this.mActivityType && matchesTopActivity(transitionRequestInfo.getTriggerTask(), null) && matchesCookie(transitionRequestInfo.getTriggerTask());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mActivityType);
            parcel.writeBoolean(this.mMustBeIndependent);
            parcel.writeBoolean(this.mNot);
            parcel.writeIntArray(this.mModes);
            parcel.writeInt(this.mFlags);
            parcel.writeBoolean(this.mMustBeTask);
            parcel.writeInt(this.mOrder);
            parcel.writeTypedObject(this.mTopActivity, i);
            parcel.writeStrongBinder(this.mLaunchCookie);
            Boolean bool = this.mCustomAnimation;
            parcel.writeInt(bool == null ? 0 : bool.booleanValue() ? 2 : 1);
            parcel.writeStrongBinder(this.mTaskFragmentToken);
            parcel.writeInt(this.mWindowingMode);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            if (this.mNot) {
                sb.append("NOT ");
            }
            sb.append("atype=" + WindowConfiguration.activityTypeToString(this.mActivityType));
            sb.append(" independent=" + this.mMustBeIndependent);
            sb.append(" modes=[");
            if (this.mModes != null) {
                int i = 0;
                while (i < this.mModes.length) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i == 0 ? "" : ",");
                    sb2.append(TransitionInfo.modeToString(this.mModes[i]));
                    sb.append(sb2.toString());
                    i++;
                }
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            sb.append(" flags=" + TransitionInfo.flagsToString(this.mFlags));
            sb.append(" mustBeTask=" + this.mMustBeTask);
            sb.append(" order=" + TransitionFilter.containerOrderToString(this.mOrder));
            sb.append(" topActivity=");
            sb.append(this.mTopActivity);
            sb.append(" launchCookie=");
            sb.append(this.mLaunchCookie);
            if (this.mCustomAnimation != null) {
                sb.append(" customAnim=");
                sb.append(this.mCustomAnimation.booleanValue());
            }
            if (this.mTaskFragmentToken != null) {
                sb.append(" taskFragmentToken=");
                sb.append(this.mTaskFragmentToken);
            }
            sb.append(" windowingMode=" + WindowConfiguration.windowingModeToString(this.mWindowingMode));
            sb.append("}");
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String containerOrderToString(int i) {
        if (i == 0) {
            return "ANY";
        }
        if (i == 1) {
            return "TOP";
        }
        return "UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
    }
}
