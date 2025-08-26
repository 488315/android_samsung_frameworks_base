package android.window;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.BinderProxy;
import android.os.Debug;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.view.SurfaceControl;
import android.view.WindowManager;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class TransitionInfo implements Parcelable {
    public static final Parcelable.Creator<TransitionInfo> CREATOR = new Parcelable.Creator<TransitionInfo>() { // from class: android.window.TransitionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionInfo createFromParcel(Parcel parcel) {
            return new TransitionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionInfo[] newArray(int i) {
            return new TransitionInfo[i];
        }
    };
    public static final int FLAG2_TRANSPARENT = 1;
    public static final int FLAGS_IS_NON_APP_WINDOW = 65794;
    public static final int FLAGS_IS_OCCLUDED_NO_ANIMATION = 294912;
    public static final int FLAG_BACK_GESTURE_ANIMATED = 131072;
    public static final int FLAG_CONFIG_AT_END = 4194304;
    public static final int FLAG_CROSS_PROFILE_OWNER_THUMBNAIL = 4096;
    public static final int FLAG_CROSS_PROFILE_WORK_THUMBNAIL = 8192;
    public static final int FLAG_CUSTOM_DISPLAY_CHANGE_TRANSITION = 536870912;
    public static final int FLAG_DISPLAY_HAS_ALERT_WINDOWS = 128;
    public static final int FLAG_EDGE_EXTENSION_RESTRICTION = 134217728;
    public static final int FLAG_FAST_ANIMATION = 1073741824;
    public static final int FLAG_FILLS_TASK = 1024;
    public static final int FLAG_FIRST_CUSTOM = 16777216;
    public static final int FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY = 512;
    public static final int FLAG_IS_ACTIVITY = 64;
    public static final int FLAG_IS_BEHIND_STARTING_WINDOW = 16384;
    public static final int FLAG_IS_DISPLAY = 32;
    public static final int FLAG_IS_FIXED_PORTRAIT = Integer.MIN_VALUE;
    public static final int FLAG_IS_INPUT_METHOD = 256;
    public static final int FLAG_IS_OCCLUDED = 32768;
    public static final int FLAG_IS_SYSTEM_WINDOW = 65536;
    public static final int FLAG_IS_TASK_DISPLAY_AREA = 8388608;
    public static final int FLAG_IS_TRANSIENT_LAUNCH_OVERLAY = 268435456;
    public static final int FLAG_IS_UNFOLD_POP_OVER = 67108864;
    public static final int FLAG_IS_VOICE_INTERACTION = 16;
    public static final int FLAG_IS_WALLPAPER = 2;
    public static final int FLAG_MOVED_TO_TOP = 1048576;
    public static final int FLAG_NONE = 0;
    public static final int FLAG_NO_ANIMATION = 262144;
    public static final int FLAG_SHOW_WALLPAPER = 1;
    public static final int FLAG_STARTING_WINDOW_TRANSFER_RECIPIENT = 8;
    public static final int FLAG_SYNC = 2097152;
    public static final int FLAG_TASK_LAUNCHING_BEHIND = 524288;
    public static final int FLAG_TRANSLUCENT = 4;
    public static final int FLAG_WILL_IME_SHOWN = 2048;
    private static final String TAG = "TransitionInfo";
    private boolean mAnimatePendingSplitWithDisplayChange;
    private boolean mCanMergeAnimation;
    private boolean mCanTransferAnimation;
    private final ArrayList<Change> mChanges;
    private int mDebugId;
    private int mFlags;
    private boolean mMergeAsNoAnimation;
    private IApplicationThread mRemoteAppThread;
    private RemoteTransition mRemoteTransition;
    private final ArrayList<Root> mRoots;
    private boolean mSeparatedFromCustomDisplayChange;
    private boolean mShouldAnimateDefaultDisplay;
    private int mTrack;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChangeFlags2 {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Change getChangeForAppsEdgeActivity() {
        Iterator<Change> it = this.mChanges.iterator();
        while (it.hasNext()) {
            Change next = it.next();
            ActivityManager.RunningTaskInfo taskInfo = next.getTaskInfo();
            if (taskInfo != null && taskInfo.realActivity != null && MultiWindowUtils.isAppsEdgeActivity(taskInfo.realActivity)) {
                return next;
            }
        }
        return null;
    }

    public Change findChange(Predicate<Change> predicate) {
        Iterator<Change> it = this.mChanges.iterator();
        while (it.hasNext()) {
            Change next = it.next();
            if (predicate.test(next)) {
                return next;
            }
        }
        return null;
    }

    public void setRemoteTransition(RemoteTransition remoteTransition) {
        this.mRemoteTransition = remoteTransition;
    }

    public RemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public TransitionInfo(int i, int i2) {
        this.mTrack = 0;
        this.mChanges = new ArrayList<>();
        this.mRoots = new ArrayList<>();
        this.mDebugId = -1;
        this.mType = i;
        this.mFlags = i2;
    }

    private TransitionInfo(Parcel parcel) {
        this.mTrack = 0;
        ArrayList<Change> arrayList = new ArrayList<>();
        this.mChanges = arrayList;
        ArrayList<Root> arrayList2 = new ArrayList<>();
        this.mRoots = arrayList2;
        this.mDebugId = -1;
        this.mType = parcel.readInt();
        this.mFlags = parcel.readInt();
        parcel.readTypedList(arrayList, Change.CREATOR);
        parcel.readTypedList(arrayList2, Root.CREATOR);
        this.mDebugId = parcel.readInt();
        this.mTrack = parcel.readInt();
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            this.mCanMergeAnimation = parcel.readBoolean();
            this.mMergeAsNoAnimation = parcel.readBoolean();
            if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
                this.mCanTransferAnimation = parcel.readBoolean();
            }
        }
        if (CoreRune.MW_PIP_REMOTE_TRANSITION) {
            this.mRemoteTransition = (RemoteTransition) parcel.readTypedObject(RemoteTransition.CREATOR);
        }
        if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
            this.mShouldAnimateDefaultDisplay = parcel.readBoolean();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedList(this.mChanges);
        parcel.writeTypedList(this.mRoots, i);
        parcel.writeInt(this.mDebugId);
        parcel.writeInt(this.mTrack);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            parcel.writeBoolean(this.mCanMergeAnimation);
            parcel.writeBoolean(this.mMergeAsNoAnimation);
            if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
                parcel.writeBoolean(this.mCanTransferAnimation);
            }
        }
        if (CoreRune.MW_PIP_REMOTE_TRANSITION) {
            parcel.writeTypedObject(this.mRemoteTransition, i);
        }
        if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
            parcel.writeBoolean(this.mShouldAnimateDefaultDisplay);
        }
    }

    public void addRootLeash(int i, SurfaceControl surfaceControl, int i2, int i3) {
        this.mRoots.add(new Root(i, surfaceControl, i2, i3));
    }

    public void addRootLeash(int i, SurfaceControl surfaceControl, int i2, int i3, Configuration configuration, boolean z) {
        this.mRoots.add(new Root(i, surfaceControl, i2, i3, configuration, z));
    }

    public void addRoot(Root root) {
        this.mRoots.add(root);
    }

    public int getType() {
        return this.mType;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getRootCount() {
        return this.mRoots.size();
    }

    public Root getRoot(int i) {
        return this.mRoots.get(i);
    }

    public int findRootIndex(int i) {
        for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
            if (this.mRoots.get(i2).mDisplayId == i) {
                return i2;
            }
        }
        return -1;
    }

    @Deprecated
    public SurfaceControl getRootLeash() {
        if (this.mRoots.isEmpty()) {
            throw new IllegalStateException("Trying to get a root leash from a no-op transition.");
        }
        if (this.mRoots.size() > 1) {
            Log.e(TAG, "Assuming one animation root when there are more.", new Throwable());
        }
        return this.mRoots.get(0).mLeash;
    }

    public List<Change> getChanges() {
        return this.mChanges;
    }

    public Change getChange(WindowContainerToken windowContainerToken) {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            if (windowContainerToken.equals(this.mChanges.get(size).mContainer)) {
                return this.mChanges.get(size);
            }
        }
        return null;
    }

    public void addChange(Change change) {
        this.mChanges.add(change);
    }

    public boolean hasChangesOrSideEffects() {
        if (!this.mChanges.isEmpty() || isKeyguardGoingAway()) {
            return true;
        }
        int i = this.mFlags;
        return ((i & 2048) == 0 && (i & 32768) == 0) ? false : true;
    }

    public boolean isKeyguardGoingAway() {
        return (this.mFlags & 256) != 0;
    }

    public int getTrack() {
        return this.mTrack;
    }

    public void setTrack(int i) {
        this.mTrack = i;
    }

    public void setDebugId(int i) {
        this.mDebugId = i;
    }

    public int getDebugId() {
        return this.mDebugId;
    }

    public void setCanMergeAnimation() {
        this.mCanMergeAnimation = true;
    }

    public boolean canMergeAnimation() {
        return this.mCanMergeAnimation;
    }

    public void setMergeAsNoAnimation() {
        this.mMergeAsNoAnimation = true;
    }

    public boolean canMergeAsNoAnimation() {
        return this.mMergeAsNoAnimation;
    }

    public void setRemoteAppThread(IApplicationThread iApplicationThread) {
        this.mRemoteAppThread = iApplicationThread;
    }

    public IApplicationThread getRemoteAppThread() {
        return this.mRemoteAppThread;
    }

    public void setCanTransferAnimation() {
        this.mCanTransferAnimation = true;
    }

    public boolean canTransferAnimation() {
        return this.mCanTransferAnimation;
    }

    public String toString() {
        return toString("");
    }

    public String toString(String str) {
        boolean z = (str.isEmpty() || this.mChanges.isEmpty()) ? false : true;
        String str2 = z ? str + "    " : "";
        String str3 = !z ? "" : ShaderAssembler.NEWLINE + str;
        String str4 = z ? ShaderAssembler.NEWLINE + str2 : "";
        StringBuilder sb = new StringBuilder("{id=");
        sb.append(this.mDebugId);
        sb.append(" t=");
        sb.append(WindowManager.transitTypeToString(this.mType));
        sb.append(" f=0x");
        sb.append(Integer.toHexString(this.mFlags));
        sb.append(" trk=");
        sb.append(this.mTrack);
        sb.append(" r=[");
        for (int i = 0; i < this.mRoots.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.mRoots.get(i).mDisplayId);
            sb.append("@");
            sb.append(this.mRoots.get(i).mOffset);
        }
        sb.append("] c=[");
        sb.append(str4);
        for (int i2 = 0; i2 < this.mChanges.size(); i2++) {
            if (i2 > 0) {
                sb.append(',');
                sb.append(str4);
            }
            sb.append(this.mChanges.get(i2));
        }
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            if (this.mCanMergeAnimation) {
                sb.append("] [merge=true");
            }
            if (this.mMergeAsNoAnimation) {
                sb.append("] [mergeAsNoAnim=true");
            }
            if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && this.mCanTransferAnimation) {
                sb.append("] [transfer=true");
            }
        }
        if (CoreRune.MW_PIP_REMOTE_TRANSITION && this.mRemoteTransition != null) {
            sb.append("] [remote=" + this.mRemoteTransition);
        }
        if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && this.mShouldAnimateDefaultDisplay) {
            sb.append("] [shouldAnimDefault=true");
        }
        sb.append(str3);
        sb.append("]}");
        return sb.toString();
    }

    public static String modeToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "OPEN";
        }
        if (i == 2) {
            return "CLOSE";
        }
        if (i == 3) {
            return "TO_FRONT";
        }
        if (i == 4) {
            return "TO_BACK";
        }
        if (i == 6) {
            return "CHANGE";
        }
        return "<unknown:" + i + ">";
    }

    public static String flagsToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        StringBuilder sb = new StringBuilder();
        if ((i & 1) != 0) {
            sb.append("SHOW_WALLPAPER");
        }
        if ((i & 2) != 0) {
            sb.append("IS_WALLPAPER");
        }
        if ((i & 256) != 0) {
            sb.append("IS_INPUT_METHOD");
        }
        if ((i & 4) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("TRANSLUCENT");
        }
        if ((i & 8) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("STARTING_WINDOW_TRANSFER");
        }
        if ((i & 16) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_VOICE_INTERACTION");
        }
        if ((i & 32) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_DISPLAY");
        }
        if ((i & 128) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("DISPLAY_HAS_ALERT_WINDOWS");
        }
        if ((i & 512) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IN_TASK_WITH_EMBEDDED_ACTIVITY");
        }
        if ((i & 1024) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("FILLS_TASK");
        }
        if ((i & 16384) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_BEHIND_STARTING_WINDOW");
        }
        if ((32768 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_OCCLUDED");
        }
        if ((65536 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("FLAG_IS_SYSTEM_WINDOW");
        }
        if ((131072 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("FLAG_BACK_GESTURE_ANIMATED");
        }
        if ((262144 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("NO_ANIMATION");
        }
        if ((524288 & i) != 0) {
            sb.append((sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).concat("TASK_LAUNCHING_BEHIND"));
        }
        if ((2097152 & i) != 0) {
            sb.append((sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER).concat("SYNC"));
        }
        if ((16777216 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("FIRST_CUSTOM");
        }
        if ((4194304 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("CONFIG_AT_END");
        }
        if ((1048576 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("MOVE_TO_TOP");
        }
        if ((8388608 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("FLAG_IS_TASK_DISPLAY_AREA");
        }
        if (CoreRune.MW_SHELL_TRANSITION && (i & 64) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_ACTIVITY");
        }
        if ((134217728 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("EDGE_EXTENSION_RESTRICTION");
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            if ((536870912 & i) != 0) {
                sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                sb.append("CUSTOM_DISPLAY_CHANGE_TRANSITION");
            }
            if ((1073741824 & i) != 0) {
                sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                sb.append("FAST_ANIMATION");
            }
        }
        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && (268435456 & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("TRANSIENT_LAUNCH_OVERLAY");
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append("IS_FIXED_PORTRAIT");
        }
        if ((i & 67108864) != 0) {
            sb.append(sb.length() != 0 ? NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER : "");
            sb.append("IS_UNFOLD_POP_OVER");
        }
        return sb.toString();
    }

    public static boolean isIndependent(Change change, TransitionInfo transitionInfo) {
        if (change.getParent() == null) {
            return true;
        }
        if (change.getLastParent() != null && !change.getLastParent().equals(change.getParent())) {
            return true;
        }
        if ((change.getMode() == 6 && (change.mConfiguration.windowConfiguration.getWindowingMode() != 5 || !change.mConfiguration.windowConfiguration.isAlwaysOnTop())) || change.hasFlags(512)) {
            return false;
        }
        Change change2 = transitionInfo.getChange(change.getParent());
        while (change2 != null && change2.getMode() == 6) {
            if (change2.getParent() == null) {
                return true;
            }
            change2 = transitionInfo.getChange(change2.getParent());
        }
        return false;
    }

    public void releaseAnimSurfaces() {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            Change change = this.mChanges.get(size);
            if (change.mSnapshot != null) {
                change.mSnapshot.release();
                change.mSnapshot = null;
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && change.mChangeLeash != null && change.mReleaseChangeLeashAllowed) {
                change.mChangeLeash.release();
                change.mChangeLeash = null;
            }
        }
        for (int i = 0; i < this.mRoots.size(); i++) {
            this.mRoots.get(i).mLeash.release();
        }
    }

    public void releaseAllSurfaces() {
        releaseAnimSurfaces();
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            this.mChanges.get(size).getLeash().release();
        }
    }

    public void setUnreleasedWarningCallSiteForAllSurfaces(String str) {
        for (int size = this.mChanges.size() - 1; size >= 0; size--) {
            this.mChanges.get(size).getLeash().setUnreleasedWarningCallSite(str);
        }
    }

    public TransitionInfo localRemoteCopy() {
        TransitionInfo transitionInfo = new TransitionInfo(this.mType, this.mFlags);
        transitionInfo.mTrack = this.mTrack;
        transitionInfo.mDebugId = this.mDebugId;
        for (int i = 0; i < this.mChanges.size(); i++) {
            transitionInfo.mChanges.add(this.mChanges.get(i).localRemoteCopy());
        }
        for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
            transitionInfo.mRoots.add(this.mRoots.get(i2).localRemoteCopy());
        }
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            transitionInfo.mCanMergeAnimation = this.mCanMergeAnimation;
            transitionInfo.mMergeAsNoAnimation = this.mMergeAsNoAnimation;
            if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
                transitionInfo.mCanTransferAnimation = this.mCanTransferAnimation;
            }
        }
        if (CoreRune.MW_PIP_REMOTE_TRANSITION) {
            transitionInfo.mRemoteTransition = this.mRemoteTransition;
        }
        if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
            transitionInfo.mShouldAnimateDefaultDisplay = this.mShouldAnimateDefaultDisplay;
        }
        return transitionInfo;
    }

    public static final class Change implements Parcelable {
        public static final Parcelable.Creator<Change> CREATOR = new Parcelable.Creator<Change>() { // from class: android.window.TransitionInfo.Change.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change createFromParcel(Parcel parcel) {
                return new Change(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Change[] newArray(int i) {
                return new Change[i];
            }
        };
        private ComponentName mActivityComponent;
        private boolean mAffordanceTargetFreeformTask;
        private boolean mAllowAppBackgroundLayer;
        private boolean mAllowEnterPip;
        private AnimationOptions mAnimationOptions;
        private int mBackgroundColor;
        private final Rect mChangeEndOutsets;
        private SurfaceControl mChangeLeash;
        private final Rect mChangeStartOutsets;
        private int mChangeTransitMode;
        private final Configuration mConfiguration;
        private final WindowContainerToken mContainer;
        private final Rect mEndAbsBounds;
        private int mEndDisplayId;
        private int mEndFixedRotation;
        private final Point mEndParentSize;
        private final Point mEndRelOffset;
        private int mEndRotation;
        private boolean mFadeInOutRotationNeeded;
        private int mFlags;
        private int mFlags2;
        private int mForceHidingTransit;
        private float mFreeformStashScale;
        private boolean mHasFixedRotationTransform;
        private final PointF mInitialScale;
        private final Rect mInsetsForRecentsTransition;
        private boolean mIsCellDivider;
        private boolean mIsEnteringPinnedMode;
        private boolean mIsInSplitActivityMode;
        private boolean mIsPopOverAnimationNeeded;
        private boolean mIsTransitionWithDim;
        private WindowContainerToken mLastParent;
        private SurfaceControl mLeash;
        private int mMinimizeAnimState;
        private final PointF mMinimizePoint;
        private int mMode;
        private WindowContainerToken mParent;
        private boolean mReleaseChangeLeashAllowed;
        private boolean mResumedAffordance;
        private int mRotationAnimation;
        private boolean mSkipDefaultTransition;
        private boolean mSkipSetupAnimHierarchy;
        private SurfaceControl mSnapshot;
        private float mSnapshotLuma;
        private final Rect mStartAbsBounds;
        private int mStartDisplayId;
        private int mStartRotation;
        private IBinder mTaskFragmentToken;
        private int mTaskIdForActivity;
        private ActivityManager.RunningTaskInfo mTaskInfo;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void setAffordanceTargetFreeformTask(boolean z) {
            this.mAffordanceTargetFreeformTask = z;
        }

        public boolean getAffordanceTargetFreeformTask() {
            return this.mAffordanceTargetFreeformTask;
        }

        public void setTransitionWithDim(boolean z) {
            this.mIsTransitionWithDim = z;
        }

        public boolean isTransitionWithDim() {
            return this.mIsTransitionWithDim;
        }

        public void setResumedAffordance(boolean z) {
            this.mResumedAffordance = z;
        }

        public boolean getResumedAffordance() {
            return this.mResumedAffordance;
        }

        public Change(WindowContainerToken windowContainerToken, SurfaceControl surfaceControl) {
            this.mMode = 0;
            this.mFlags = 0;
            this.mStartAbsBounds = new Rect();
            this.mEndAbsBounds = new Rect();
            this.mEndRelOffset = new Point();
            this.mEndParentSize = new Point();
            this.mTaskInfo = null;
            this.mStartDisplayId = -1;
            this.mEndDisplayId = -1;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mEndFixedRotation = -1;
            this.mRotationAnimation = -1;
            this.mSnapshot = null;
            this.mActivityComponent = null;
            this.mAnimationOptions = null;
            this.mTaskFragmentToken = null;
            this.mInitialScale = new PointF();
            this.mConfiguration = new Configuration();
            this.mIsCellDivider = false;
            this.mChangeLeash = null;
            this.mChangeTransitMode = 0;
            this.mChangeStartOutsets = new Rect();
            this.mChangeEndOutsets = new Rect();
            this.mReleaseChangeLeashAllowed = true;
            this.mInsetsForRecentsTransition = new Rect();
            this.mForceHidingTransit = 0;
            this.mMinimizeAnimState = 0;
            this.mMinimizePoint = new PointF();
            this.mFreeformStashScale = 1.0f;
            this.mTaskIdForActivity = -1;
            this.mHasFixedRotationTransform = false;
            this.mAllowAppBackgroundLayer = false;
            this.mContainer = windowContainerToken;
            this.mLeash = surfaceControl;
        }

        private Change(Parcel parcel) {
            this.mMode = 0;
            this.mFlags = 0;
            Rect rect = new Rect();
            this.mStartAbsBounds = rect;
            Rect rect2 = new Rect();
            this.mEndAbsBounds = rect2;
            Point point = new Point();
            this.mEndRelOffset = point;
            Point point2 = new Point();
            this.mEndParentSize = point2;
            this.mTaskInfo = null;
            this.mStartDisplayId = -1;
            this.mEndDisplayId = -1;
            this.mStartRotation = -1;
            this.mEndRotation = -1;
            this.mEndFixedRotation = -1;
            this.mRotationAnimation = -1;
            this.mSnapshot = null;
            this.mActivityComponent = null;
            this.mAnimationOptions = null;
            this.mTaskFragmentToken = null;
            PointF pointF = new PointF();
            this.mInitialScale = pointF;
            Configuration configuration = new Configuration();
            this.mConfiguration = configuration;
            this.mIsCellDivider = false;
            this.mChangeLeash = null;
            this.mChangeTransitMode = 0;
            Rect rect3 = new Rect();
            this.mChangeStartOutsets = rect3;
            Rect rect4 = new Rect();
            this.mChangeEndOutsets = rect4;
            this.mReleaseChangeLeashAllowed = true;
            Rect rect5 = new Rect();
            this.mInsetsForRecentsTransition = rect5;
            this.mForceHidingTransit = 0;
            this.mMinimizeAnimState = 0;
            PointF pointF2 = new PointF();
            this.mMinimizePoint = pointF2;
            this.mFreeformStashScale = 1.0f;
            this.mTaskIdForActivity = -1;
            this.mHasFixedRotationTransform = false;
            this.mAllowAppBackgroundLayer = false;
            this.mContainer = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
            this.mParent = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
            this.mLastParent = (WindowContainerToken) parcel.readTypedObject(WindowContainerToken.CREATOR);
            SurfaceControl surfaceControl = new SurfaceControl();
            this.mLeash = surfaceControl;
            surfaceControl.readFromParcel(parcel);
            this.mMode = parcel.readInt();
            this.mFlags = parcel.readInt();
            rect.readFromParcel(parcel);
            rect2.readFromParcel(parcel);
            point.readFromParcel(parcel);
            point2.readFromParcel(parcel);
            this.mTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
            this.mAllowEnterPip = parcel.readBoolean();
            this.mStartDisplayId = parcel.readInt();
            this.mEndDisplayId = parcel.readInt();
            this.mStartRotation = parcel.readInt();
            this.mEndRotation = parcel.readInt();
            this.mEndFixedRotation = parcel.readInt();
            this.mRotationAnimation = parcel.readInt();
            this.mBackgroundColor = parcel.readInt();
            this.mSnapshot = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
            this.mSnapshotLuma = parcel.readFloat();
            this.mActivityComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
            this.mAnimationOptions = (AnimationOptions) parcel.readTypedObject(AnimationOptions.CREATOR);
            this.mTaskFragmentToken = parcel.readStrongBinder();
            if (CoreRune.MW_SHELL_TRANSITION) {
                configuration.readFromParcel(parcel);
                pointF.readFromParcel(parcel);
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                this.mChangeLeash = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                this.mChangeTransitMode = parcel.readInt();
                rect3.readFromParcel(parcel);
                rect4.readFromParcel(parcel);
            }
            rect5.readFromParcel(parcel);
            if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM) {
                this.mIsTransitionWithDim = parcel.readBoolean();
            }
            this.mForceHidingTransit = parcel.readInt();
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                this.mMinimizeAnimState = parcel.readInt();
                pointF2.readFromParcel(parcel);
            }
            if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION) {
                this.mAffordanceTargetFreeformTask = parcel.readBoolean();
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                this.mFreeformStashScale = parcel.readFloat();
            }
            if (CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE) {
                this.mResumedAffordance = parcel.readBoolean();
            }
            this.mFadeInOutRotationNeeded = parcel.readBoolean();
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mIsEnteringPinnedMode = parcel.readBoolean();
            }
            this.mIsPopOverAnimationNeeded = parcel.readBoolean();
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                this.mTaskIdForActivity = parcel.readInt();
                this.mHasFixedRotationTransform = parcel.readBoolean();
            }
            if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER) {
                this.mAllowAppBackgroundLayer = parcel.readBoolean();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Change localRemoteCopy() {
            Change change = new Change(this.mContainer, new SurfaceControl(this.mLeash, "localRemote"));
            change.mParent = this.mParent;
            change.mLastParent = this.mLastParent;
            change.mMode = this.mMode;
            change.mFlags = this.mFlags;
            change.mStartAbsBounds.set(this.mStartAbsBounds);
            change.mEndAbsBounds.set(this.mEndAbsBounds);
            change.mEndRelOffset.set(this.mEndRelOffset);
            change.mEndParentSize.set(this.mEndParentSize);
            change.mTaskInfo = this.mTaskInfo;
            change.mAllowEnterPip = this.mAllowEnterPip;
            change.mStartDisplayId = this.mStartDisplayId;
            change.mEndDisplayId = this.mEndDisplayId;
            change.mStartRotation = this.mStartRotation;
            change.mEndRotation = this.mEndRotation;
            change.mEndFixedRotation = this.mEndFixedRotation;
            change.mRotationAnimation = this.mRotationAnimation;
            change.mBackgroundColor = this.mBackgroundColor;
            change.mSnapshot = this.mSnapshot != null ? new SurfaceControl(this.mSnapshot, "localRemote") : null;
            change.mSnapshotLuma = this.mSnapshotLuma;
            change.mActivityComponent = this.mActivityComponent;
            change.mAnimationOptions = this.mAnimationOptions;
            change.mTaskFragmentToken = this.mTaskFragmentToken;
            if (CoreRune.MW_SHELL_TRANSITION) {
                change.mConfiguration.setTo(this.mConfiguration);
                change.mInitialScale.set(this.mInitialScale);
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                change.mChangeLeash = this.mChangeLeash != null ? new SurfaceControl(this.mChangeLeash, "localRemote") : null;
                change.mChangeTransitMode = this.mChangeTransitMode;
                change.mChangeStartOutsets.set(this.mChangeStartOutsets);
                change.mChangeEndOutsets.set(this.mChangeEndOutsets);
            }
            change.mInsetsForRecentsTransition.set(this.mInsetsForRecentsTransition);
            if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM) {
                change.mIsTransitionWithDim = this.mIsTransitionWithDim;
            }
            change.mForceHidingTransit = this.mForceHidingTransit;
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                change.mMinimizeAnimState = this.mMinimizeAnimState;
                change.mMinimizePoint.set(this.mMinimizePoint);
            }
            if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION) {
                change.mAffordanceTargetFreeformTask = this.mAffordanceTargetFreeformTask;
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                change.mFreeformStashScale = this.mFreeformStashScale;
            }
            if (CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE) {
                change.mResumedAffordance = this.mResumedAffordance;
            }
            change.mFadeInOutRotationNeeded = this.mFadeInOutRotationNeeded;
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                change.mIsEnteringPinnedMode = this.mIsEnteringPinnedMode;
            }
            change.mIsPopOverAnimationNeeded = this.mIsPopOverAnimationNeeded;
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                change.mTaskIdForActivity = this.mTaskIdForActivity;
                change.mHasFixedRotationTransform = this.mHasFixedRotationTransform;
            }
            if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER) {
                change.mAllowAppBackgroundLayer = this.mAllowAppBackgroundLayer;
            }
            return change;
        }

        public void setParent(WindowContainerToken windowContainerToken) {
            this.mParent = windowContainerToken;
        }

        public void setLastParent(WindowContainerToken windowContainerToken) {
            this.mLastParent = windowContainerToken;
        }

        public void setLeash(SurfaceControl surfaceControl) {
            this.mLeash = (SurfaceControl) Objects.requireNonNull(surfaceControl);
        }

        public void setMode(int i) {
            this.mMode = i;
        }

        public void setFlags(int i) {
            this.mFlags = i;
        }

        public void setStartAbsBounds(Rect rect) {
            this.mStartAbsBounds.set(rect);
        }

        public void setEndAbsBounds(Rect rect) {
            this.mEndAbsBounds.set(rect);
        }

        public void setEndRelOffset(int i, int i2) {
            this.mEndRelOffset.set(i, i2);
        }

        public void setEndParentSize(int i, int i2) {
            this.mEndParentSize.set(i, i2);
        }

        public void setTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
            this.mTaskInfo = runningTaskInfo;
        }

        public void setAllowEnterPip(boolean z) {
            this.mAllowEnterPip = z;
        }

        public void setDisplayId(int i, int i2) {
            this.mStartDisplayId = i;
            this.mEndDisplayId = i2;
        }

        public void setRotation(int i, int i2) {
            this.mStartRotation = i;
            this.mEndRotation = i2;
        }

        public void setEndFixedRotation(int i) {
            this.mEndFixedRotation = i;
        }

        public void setRotationAnimation(int i) {
            this.mRotationAnimation = i;
        }

        public void setBackgroundColor(int i) {
            this.mBackgroundColor = i;
        }

        public void setSnapshot(SurfaceControl surfaceControl, float f) {
            this.mSnapshot = surfaceControl;
            this.mSnapshotLuma = f;
        }

        public void setActivityComponent(ComponentName componentName) {
            this.mActivityComponent = componentName;
        }

        public void setAnimationOptions(AnimationOptions animationOptions) {
            this.mAnimationOptions = animationOptions;
        }

        public void setTaskFragmentToken(IBinder iBinder) {
            this.mTaskFragmentToken = iBinder;
        }

        public WindowContainerToken getContainer() {
            return this.mContainer;
        }

        public WindowContainerToken getParent() {
            return this.mParent;
        }

        public WindowContainerToken getLastParent() {
            return this.mLastParent;
        }

        public int getMode() {
            return this.mMode;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean hasFlags(int i) {
            return (this.mFlags & i) != 0;
        }

        public boolean hasAllFlags(int i) {
            return (this.mFlags & i) == i;
        }

        public Rect getStartAbsBounds() {
            return this.mStartAbsBounds;
        }

        public Rect getEndAbsBounds() {
            return this.mEndAbsBounds;
        }

        public Point getEndRelOffset() {
            return this.mEndRelOffset;
        }

        public Point getEndParentSize() {
            return this.mEndParentSize;
        }

        public SurfaceControl getLeash() {
            return this.mLeash;
        }

        public ActivityManager.RunningTaskInfo getTaskInfo() {
            return this.mTaskInfo;
        }

        public boolean isAllowEnterPip() {
            return this.mAllowEnterPip;
        }

        public int getStartDisplayId() {
            return this.mStartDisplayId;
        }

        public int getEndDisplayId() {
            return this.mEndDisplayId;
        }

        public int getStartRotation() {
            return this.mStartRotation;
        }

        public int getEndRotation() {
            return this.mEndRotation;
        }

        public int getEndFixedRotation() {
            return this.mEndFixedRotation;
        }

        public int getRotationAnimation() {
            return this.mRotationAnimation;
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public SurfaceControl getSnapshot() {
            return this.mSnapshot;
        }

        public float getSnapshotLuma() {
            return this.mSnapshotLuma;
        }

        public ComponentName getActivityComponent() {
            return this.mActivityComponent;
        }

        public AnimationOptions getAnimationOptions() {
            return this.mAnimationOptions;
        }

        public IBinder getTaskFragmentToken() {
            return this.mTaskFragmentToken;
        }

        public void setConfiguration(Configuration configuration) {
            this.mConfiguration.updateFrom(configuration);
        }

        public Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public void setInitialScale(float f, float f2) {
            this.mInitialScale.set(f, f2);
        }

        public PointF getInitialScale() {
            return this.mInitialScale;
        }

        public boolean hasValidInitialScale() {
            return this.mInitialScale.x > 0.0f && this.mInitialScale.y > 0.0f;
        }

        public void setInsetsForRecentsTransition(Rect rect) {
            this.mInsetsForRecentsTransition.set(rect);
        }

        public Rect getInsetsForRecentsTransition() {
            return this.mInsetsForRecentsTransition;
        }

        public void setSkipDefaultTransition(boolean z) {
            this.mSkipDefaultTransition = z;
        }

        public boolean shouldSkipDefaultTransition() {
            return this.mSkipDefaultTransition;
        }

        public void setSkipSetupAnimHierarchy(boolean z) {
            this.mSkipSetupAnimHierarchy = z;
        }

        public boolean shouldSkipSetupAnimHierarchy() {
            return this.mSkipSetupAnimHierarchy;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedObject(this.mContainer, i);
            parcel.writeTypedObject(this.mParent, i);
            parcel.writeTypedObject(this.mLastParent, i);
            this.mLeash.writeToParcel(parcel, i);
            parcel.writeInt(this.mMode);
            parcel.writeInt(this.mFlags);
            this.mStartAbsBounds.writeToParcel(parcel, i);
            this.mEndAbsBounds.writeToParcel(parcel, i);
            this.mEndRelOffset.writeToParcel(parcel, i);
            this.mEndParentSize.writeToParcel(parcel, i);
            parcel.writeTypedObject(this.mTaskInfo, i);
            parcel.writeBoolean(this.mAllowEnterPip);
            parcel.writeInt(this.mStartDisplayId);
            parcel.writeInt(this.mEndDisplayId);
            parcel.writeInt(this.mStartRotation);
            parcel.writeInt(this.mEndRotation);
            parcel.writeInt(this.mEndFixedRotation);
            parcel.writeInt(this.mRotationAnimation);
            parcel.writeInt(this.mBackgroundColor);
            parcel.writeTypedObject(this.mSnapshot, i);
            parcel.writeFloat(this.mSnapshotLuma);
            parcel.writeTypedObject(this.mActivityComponent, i);
            parcel.writeTypedObject(this.mAnimationOptions, i);
            parcel.writeStrongBinder(this.mTaskFragmentToken);
            if (CoreRune.MW_SHELL_TRANSITION) {
                this.mConfiguration.writeToParcel(parcel, i);
                this.mInitialScale.writeToParcel(parcel, i);
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                parcel.writeTypedObject(this.mChangeLeash, i);
                parcel.writeInt(this.mChangeTransitMode);
                this.mChangeStartOutsets.writeToParcel(parcel, i);
                this.mChangeEndOutsets.writeToParcel(parcel, i);
            }
            this.mInsetsForRecentsTransition.writeToParcel(parcel, i);
            if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM) {
                parcel.writeBoolean(this.mIsTransitionWithDim);
            }
            parcel.writeInt(this.mForceHidingTransit);
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                parcel.writeInt(this.mMinimizeAnimState);
                this.mMinimizePoint.writeToParcel(parcel, i);
            }
            if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION) {
                parcel.writeBoolean(this.mAffordanceTargetFreeformTask);
            }
            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                parcel.writeFloat(this.mFreeformStashScale);
            }
            if (CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE) {
                parcel.writeBoolean(this.mResumedAffordance);
            }
            parcel.writeBoolean(this.mFadeInOutRotationNeeded);
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                parcel.writeBoolean(this.mIsEnteringPinnedMode);
            }
            parcel.writeBoolean(this.mIsPopOverAnimationNeeded);
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                parcel.writeInt(this.mTaskIdForActivity);
                parcel.writeBoolean(this.mHasFixedRotationTransform);
            }
            if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER) {
                parcel.writeBoolean(this.mAllowAppBackgroundLayer);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            WindowContainerToken windowContainerToken = this.mContainer;
            if (windowContainerToken != null && !(windowContainerToken.asBinder() instanceof BinderProxy)) {
                sb.append(this.mContainer);
                sb.append(" ");
            }
            sb.append("m=");
            sb.append(TransitionInfo.modeToString(this.mMode));
            sb.append(" f=");
            sb.append(TransitionInfo.flagsToString(this.mFlags));
            if (this.mParent != null) {
                sb.append(" p=");
                sb.append(this.mParent);
            }
            if (this.mLeash != null) {
                sb.append(" leash=");
                sb.append(this.mLeash);
            }
            sb.append(" sb=");
            sb.append(this.mStartAbsBounds);
            sb.append(" eb=");
            sb.append(this.mEndAbsBounds);
            if (this.mEndRelOffset.x != 0 || this.mEndRelOffset.y != 0) {
                sb.append(" eo=");
                sb.append(this.mEndRelOffset);
            }
            if (!this.mEndParentSize.equals(0, 0)) {
                sb.append(" epz=");
                sb.append(this.mEndParentSize);
            }
            sb.append(" d=");
            int i = this.mStartDisplayId;
            if (i != this.mEndDisplayId) {
                sb.append(i);
                sb.append(Session.SUBSESSION_SEPARATION_CHAR);
            }
            sb.append(this.mEndDisplayId);
            if (this.mStartRotation != this.mEndRotation) {
                sb.append(" r=");
                sb.append(this.mStartRotation);
                sb.append(Session.SUBSESSION_SEPARATION_CHAR);
                sb.append(this.mEndRotation);
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                sb.append(this.mRotationAnimation);
            }
            if (this.mEndFixedRotation != -1) {
                sb.append(" endFixedRotation=");
                sb.append(this.mEndFixedRotation);
            }
            if (this.mBackgroundColor != 0) {
                sb.append(" bc=");
                sb.append(Integer.toHexString(this.mBackgroundColor));
            }
            if (this.mSnapshot != null) {
                sb.append(" snapshot=");
                sb.append(this.mSnapshot);
            }
            if (this.mLastParent != null) {
                sb.append(" lastParent=");
                sb.append(this.mLastParent);
            }
            if (this.mActivityComponent != null) {
                sb.append(" component=");
                sb.append(this.mActivityComponent.flattenToShortString());
            }
            if (this.mTaskInfo != null) {
                sb.append(" taskParent=");
                sb.append(this.mTaskInfo.parentTaskId);
            }
            if (this.mAnimationOptions != null) {
                sb.append(" opt=");
                sb.append(this.mAnimationOptions);
            }
            if (this.mTaskFragmentToken != null) {
                sb.append(" taskFragmentToken=");
                sb.append(this.mTaskFragmentToken);
            }
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                if (this.mChangeLeash != null) {
                    sb.append(" changeLeash=");
                    sb.append(this.mChangeLeash);
                }
                if (this.mChangeTransitMode != 0) {
                    sb.append(" cm=");
                    sb.append(this.mChangeTransitMode);
                }
                if (hasChangeStartOutsets()) {
                    sb.append(" cso=" + this.mChangeStartOutsets);
                }
                if (hasChangeEndOutsets()) {
                    sb.append(" ceo=" + this.mChangeEndOutsets);
                }
            }
            sb.append(" inset=");
            sb.append(this.mInsetsForRecentsTransition);
            if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM && this.mIsTransitionWithDim) {
                sb.append(" dim=true");
            }
            if (this.mForceHidingTransit != 0) {
                sb.append(" fht=");
                sb.append(this.mForceHidingTransit);
            }
            if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && this.mAffordanceTargetFreeformTask) {
                sb.append(" affordanceTargetFreeformTask=true");
            }
            if (this.mFadeInOutRotationNeeded) {
                sb.append(" fade=");
                sb.append(this.mFadeInOutRotationNeeded);
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mIsEnteringPinnedMode) {
                sb.append(" enter_pip=true");
            }
            if (this.mIsPopOverAnimationNeeded) {
                sb.append(" isPopOverAnimationNeeded=true");
            }
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                if (this.mTaskIdForActivity != -1) {
                    sb.append(" taskIdForActivity=" + this.mTaskIdForActivity);
                }
                if (this.mHasFixedRotationTransform) {
                    sb.append(" hasFixedRotationTransform=true");
                }
            }
            if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER && this.mAllowAppBackgroundLayer) {
                sb.append(" allowAppBgLayer=true");
            }
            sb.append('}');
            return sb.toString();
        }

        public SurfaceControl getChangeLeash() {
            return this.mChangeLeash;
        }

        public void setChangeLeash(SurfaceControl surfaceControl) {
            this.mChangeLeash = surfaceControl;
        }

        public void setReleaseChangeLeashAllowed(boolean z) {
            this.mReleaseChangeLeashAllowed = z;
        }

        public int getChangeTransitMode() {
            return this.mChangeTransitMode;
        }

        public void setChangeTransitMode(int i) {
            this.mChangeTransitMode = i;
        }

        public Rect getChangeStartOutsets() {
            return this.mChangeStartOutsets;
        }

        public void setChangeStartOutsets(Rect rect) {
            this.mChangeStartOutsets.set(rect);
        }

        public boolean hasChangeStartOutsets() {
            return this.mChangeStartOutsets.left > 0 || this.mChangeStartOutsets.top > 0 || this.mChangeStartOutsets.right > 0 || this.mChangeStartOutsets.bottom > 0;
        }

        public Rect getChangeEndOutsets() {
            return this.mChangeEndOutsets;
        }

        public void setChangeEndOutsets(Rect rect) {
            this.mChangeEndOutsets.set(rect);
        }

        public boolean hasChangeEndOutsets() {
            return this.mChangeEndOutsets.left > 0 || this.mChangeEndOutsets.top > 0 || this.mChangeEndOutsets.right > 0 || this.mChangeEndOutsets.bottom > 0;
        }

        public void setForceHidingTransit(int i) {
            this.mForceHidingTransit = i;
        }

        public int getForceHidingTransit() {
            return this.mForceHidingTransit;
        }

        public boolean isForceHidingWithoutAnimation() {
            int i = this.mForceHidingTransit;
            return i == 4 || i == 3;
        }

        public boolean isForceHidingEnter() {
            int i = this.mForceHidingTransit;
            return i == 1 || i == 3;
        }

        public void setMinimizeAnimState(int i) {
            this.mMinimizeAnimState = i;
        }

        public int getMinimizeAnimState() {
            return this.mMinimizeAnimState;
        }

        public void setMinimizePoint(PointF pointF) {
            this.mMinimizePoint.set(pointF);
        }

        public PointF getMinimizePoint() {
            return this.mMinimizePoint;
        }

        public void setIsCellDivider(boolean z) {
            this.mIsCellDivider = z;
        }

        public boolean getIsCellDivider() {
            return this.mIsCellDivider;
        }

        public void setPopOverAnimationNeeded(boolean z) {
            this.mIsPopOverAnimationNeeded = z;
        }

        public boolean getPopOverAnimationNeeded() {
            return this.mIsPopOverAnimationNeeded;
        }

        public void setSplitActivityMode(boolean z) {
            this.mIsInSplitActivityMode = z;
        }

        public boolean getSplitActivityMode() {
            return this.mIsInSplitActivityMode;
        }

        public void setFreeformStashScale(float f) {
            this.mFreeformStashScale = f;
        }

        public float getFreeformStashScale() {
            return this.mFreeformStashScale;
        }

        public void setFadeInOutRotationNeeded() {
            this.mFadeInOutRotationNeeded = true;
        }

        public boolean isFadeInOutRotationNeeded() {
            return this.mFadeInOutRotationNeeded;
        }

        public void setEnteringPinnedMode(boolean z) {
            this.mIsEnteringPinnedMode = z;
        }

        public boolean isEnteringPinnedMode() {
            return this.mIsEnteringPinnedMode;
        }

        public void addFlags2(int i) {
            this.mFlags2 = i | this.mFlags2;
        }

        public int getFlags2() {
            return this.mFlags2;
        }

        public void setTaskIdForActivity(int i) {
            this.mTaskIdForActivity = i;
        }

        public int getTaskIdForActivity() {
            return this.mTaskIdForActivity;
        }

        public void setHasFixedRotationTransform(boolean z) {
            this.mHasFixedRotationTransform = z;
        }

        public boolean hasFixedRotationTransform() {
            return this.mHasFixedRotationTransform;
        }

        public boolean getAllowAppBackgroundLayer() {
            return this.mAllowAppBackgroundLayer;
        }

        public void setAllowAppBackgroundLayer(boolean z) {
            this.mAllowAppBackgroundLayer = z;
        }
    }

    public static final class AnimationOptions implements Parcelable {
        public static final Parcelable.Creator<AnimationOptions> CREATOR = new Parcelable.Creator<AnimationOptions>() { // from class: android.window.TransitionInfo.AnimationOptions.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AnimationOptions createFromParcel(Parcel parcel) {
                return new AnimationOptions(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AnimationOptions[] newArray(int i) {
                return new AnimationOptions[i];
            }
        };
        public static final int DEFAULT_ANIMATION_RESOURCES_ID = -1;
        private int mAnimations;
        private int mBackgroundColor;
        private int mChangeResId;
        private CustomActivityTransition mCustomActivityCloseTransition;
        private CustomActivityTransition mCustomActivityOpenTransition;
        private int mEnterResId;
        private int mExitResId;
        private boolean mOverrideTaskTransition;
        private String mPackageName;
        private HardwareBuffer mThumbnail;
        private final Rect mTransitionBounds;
        private int mType;
        private int mUserId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private AnimationOptions(int i) {
            this.mEnterResId = -1;
            this.mChangeResId = -1;
            this.mExitResId = -1;
            this.mTransitionBounds = new Rect();
            this.mType = i;
        }

        private AnimationOptions(Parcel parcel) {
            this.mEnterResId = -1;
            this.mChangeResId = -1;
            this.mExitResId = -1;
            Rect rect = new Rect();
            this.mTransitionBounds = rect;
            this.mType = parcel.readInt();
            this.mEnterResId = parcel.readInt();
            this.mChangeResId = parcel.readInt();
            this.mExitResId = parcel.readInt();
            this.mOverrideTaskTransition = parcel.readBoolean();
            this.mPackageName = parcel.readString();
            rect.readFromParcel(parcel);
            this.mThumbnail = (HardwareBuffer) parcel.readTypedObject(HardwareBuffer.CREATOR);
            this.mAnimations = parcel.readInt();
            this.mCustomActivityOpenTransition = (CustomActivityTransition) parcel.readTypedObject(CustomActivityTransition.CREATOR);
            this.mCustomActivityCloseTransition = (CustomActivityTransition) parcel.readTypedObject(CustomActivityTransition.CREATOR);
            this.mUserId = parcel.readInt();
        }

        public static AnimationOptions makeCommonAnimOptions(String str) {
            AnimationOptions animationOptions = new AnimationOptions(14);
            animationOptions.mPackageName = str;
            return animationOptions;
        }

        public static AnimationOptions makeAnimOptionsFromLayoutParameters(WindowManager.LayoutParams layoutParams) {
            AnimationOptions animationOptions = new AnimationOptions(14);
            animationOptions.mPackageName = layoutParams.packageName;
            animationOptions.mAnimations = layoutParams.windowAnimations;
            return animationOptions;
        }

        public void addOptionsFromLayoutParameters(WindowManager.LayoutParams layoutParams) {
            this.mAnimations = layoutParams.windowAnimations;
        }

        public void addCustomActivityTransition(boolean z, int i, int i2, int i3) {
            CustomActivityTransition customActivityTransition = z ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
            if (customActivityTransition == null) {
                customActivityTransition = new CustomActivityTransition();
                if (z) {
                    this.mCustomActivityOpenTransition = customActivityTransition;
                } else {
                    this.mCustomActivityCloseTransition = customActivityTransition;
                }
            }
            customActivityTransition.addCustomActivityTransition(i, i2, i3);
        }

        public static AnimationOptions makeCustomAnimOptions(String str, int i, int i2, int i3, boolean z) {
            AnimationOptions animationOptions = new AnimationOptions(1);
            animationOptions.mPackageName = str;
            animationOptions.mEnterResId = i;
            animationOptions.mChangeResId = i2;
            animationOptions.mExitResId = i3;
            animationOptions.mOverrideTaskTransition = z;
            return animationOptions;
        }

        public static AnimationOptions makeClipRevealAnimOptions(int i, int i2, int i3, int i4) {
            AnimationOptions animationOptions = new AnimationOptions(11);
            animationOptions.mTransitionBounds.set(i, i2, i3 + i, i4 + i2);
            return animationOptions;
        }

        public static AnimationOptions makeScaleUpAnimOptions(int i, int i2, int i3, int i4, boolean z) {
            AnimationOptions animationOptions = new AnimationOptions(2);
            animationOptions.mTransitionBounds.set(i, i2, i3 + i, i4 + i2);
            animationOptions.mOverrideTaskTransition = z;
            return animationOptions;
        }

        public static AnimationOptions makeThumbnailAnimOptions(HardwareBuffer hardwareBuffer, int i, int i2, boolean z) {
            AnimationOptions animationOptions = new AnimationOptions(z ? 3 : 4);
            animationOptions.mTransitionBounds.set(i, i2, i, i2);
            animationOptions.mThumbnail = hardwareBuffer;
            return animationOptions;
        }

        public static AnimationOptions makeCrossProfileAnimOptions() {
            return new AnimationOptions(12);
        }

        public static AnimationOptions makeSceneTransitionAnimOptions() {
            return new AnimationOptions(5);
        }

        public void setUserId(int i) {
            this.mUserId = i;
        }

        public int getUserId() {
            return this.mUserId;
        }

        public static AnimationOptions makeCustomDisplayChangeAnimOptions(int i, int i2) {
            AnimationOptions animationOptions = new AnimationOptions(15);
            animationOptions.mEnterResId = i;
            animationOptions.mExitResId = i2;
            return animationOptions;
        }

        public int getType() {
            return this.mType;
        }

        public int getEnterResId() {
            return this.mEnterResId;
        }

        public int getChangeResId() {
            return this.mChangeResId;
        }

        public int getExitResId() {
            return this.mExitResId;
        }

        public boolean getOverrideTaskTransition() {
            return this.mOverrideTaskTransition;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public Rect getTransitionBounds() {
            return this.mTransitionBounds;
        }

        public HardwareBuffer getThumbnail() {
            return this.mThumbnail;
        }

        public int getAnimations() {
            return this.mAnimations;
        }

        public CustomActivityTransition getCustomActivityTransition(boolean z) {
            return z ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mType);
            parcel.writeInt(this.mEnterResId);
            parcel.writeInt(this.mChangeResId);
            parcel.writeInt(this.mExitResId);
            parcel.writeBoolean(this.mOverrideTaskTransition);
            parcel.writeString(this.mPackageName);
            this.mTransitionBounds.writeToParcel(parcel, i);
            parcel.writeTypedObject(this.mThumbnail, i);
            parcel.writeInt(this.mAnimations);
            parcel.writeTypedObject(this.mCustomActivityOpenTransition, i);
            parcel.writeTypedObject(this.mCustomActivityCloseTransition, i);
            parcel.writeInt(this.mUserId);
        }

        private static String typeToString(int i) {
            if (i == 1) {
                return "CUSTOM";
            }
            if (i == 2) {
                return "SCALE_UP";
            }
            if (i == 3) {
                return "THUMBNAIL_SCALE_UP";
            }
            if (i == 4) {
                return "THUMBNAIL_SCALE_DOWN";
            }
            if (i == 5) {
                return "SCENE_TRANSITION";
            }
            if (i == 11) {
                return "CLIP_REVEAL";
            }
            if (i == 12) {
                return "OPEN_CROSS_PROFILE_APPS";
            }
            if (i == 14) {
                return "FROM_STYLE";
            }
            return "<" + i + ">";
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(32);
            sb.append("{t=");
            sb.append(typeToString(this.mType));
            if (this.mOverrideTaskTransition) {
                sb.append(" overrideTask=true");
            }
            if (!this.mTransitionBounds.isEmpty()) {
                sb.append(" bounds=");
                sb.append(this.mTransitionBounds);
            }
            if (this.mEnterResId != -1) {
                sb.append(" enterResId=");
                sb.append(this.mEnterResId);
            }
            if (this.mChangeResId != -1) {
                sb.append(" changeResId=");
                sb.append(this.mChangeResId);
            }
            if (this.mExitResId != -1) {
                sb.append(" exitResId=");
                sb.append(this.mExitResId);
            }
            sb.append(" mUserId=");
            sb.append(this.mUserId);
            sb.append('}');
            return sb.toString();
        }

        public static final class CustomActivityTransition implements Parcelable {
            public static final Parcelable.Creator<CustomActivityTransition> CREATOR = new Parcelable.Creator<CustomActivityTransition>() { // from class: android.window.TransitionInfo.AnimationOptions.CustomActivityTransition.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CustomActivityTransition createFromParcel(Parcel parcel) {
                    return new CustomActivityTransition(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CustomActivityTransition[] newArray(int i) {
                    return new CustomActivityTransition[i];
                }
            };
            private int mCustomBackgroundColor;
            private int mCustomEnterResId;
            private int mCustomExitResId;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public int getCustomEnterResId() {
                return this.mCustomEnterResId;
            }

            public int getCustomExitResId() {
                return this.mCustomExitResId;
            }

            public int getCustomBackgroundColor() {
                return this.mCustomBackgroundColor;
            }

            CustomActivityTransition() {
            }

            CustomActivityTransition(Parcel parcel) {
                this.mCustomEnterResId = parcel.readInt();
                this.mCustomExitResId = parcel.readInt();
                this.mCustomBackgroundColor = parcel.readInt();
            }

            public void addCustomActivityTransition(int i, int i2, int i3) {
                this.mCustomEnterResId = i;
                this.mCustomExitResId = i2;
                this.mCustomBackgroundColor = i3;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.mCustomEnterResId);
                parcel.writeInt(this.mCustomExitResId);
                parcel.writeInt(this.mCustomBackgroundColor);
            }
        }
    }

    public static final class Root implements Parcelable {
        public static final Parcelable.Creator<Root> CREATOR = new Parcelable.Creator<Root>() { // from class: android.window.TransitionInfo.Root.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Root createFromParcel(Parcel parcel) {
                return new Root(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Root[] newArray(int i) {
                return new Root[i];
            }
        };
        private final Configuration mConfiguration;
        private final int mDisplayId;
        private boolean mIsActivityRootLeash;
        private final SurfaceControl mLeash;
        private final Point mOffset;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Root(int i, SurfaceControl surfaceControl, int i2, int i3) {
            Point point = new Point();
            this.mOffset = point;
            this.mConfiguration = new Configuration();
            this.mDisplayId = i;
            this.mLeash = surfaceControl;
            point.set(i2, i3);
        }

        public Root(int i, SurfaceControl surfaceControl, int i2, int i3, Configuration configuration, boolean z) {
            this(i, surfaceControl, i2, i3);
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                if (configuration != null) {
                    this.mConfiguration.setTo(configuration);
                }
                this.mIsActivityRootLeash = z;
            }
        }

        private Root(Parcel parcel) {
            Point point = new Point();
            this.mOffset = point;
            Configuration configuration = new Configuration();
            this.mConfiguration = configuration;
            this.mDisplayId = parcel.readInt();
            SurfaceControl surfaceControl = new SurfaceControl();
            this.mLeash = surfaceControl;
            surfaceControl.readFromParcel(parcel);
            surfaceControl.setUnreleasedWarningCallSite("TransitionInfo.Root");
            point.readFromParcel(parcel);
            if (CoreRune.MW_SHELL_TRANSITION) {
                configuration.readFromParcel(parcel);
                this.mIsActivityRootLeash = parcel.readBoolean();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Root localRemoteCopy() {
            if (CoreRune.MW_SHELL_TRANSITION) {
                return new Root(this.mDisplayId, new SurfaceControl(this.mLeash, "localRemote"), this.mOffset.x, this.mOffset.y, this.mConfiguration, this.mIsActivityRootLeash);
            }
            return new Root(this.mDisplayId, new SurfaceControl(this.mLeash, "localRemote"), this.mOffset.x, this.mOffset.y);
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public SurfaceControl getLeash() {
            return this.mLeash;
        }

        public Point getOffset() {
            return this.mOffset;
        }

        public Configuration getConfiguration() {
            return this.mConfiguration;
        }

        public boolean isActivityRootLeash() {
            return this.mIsActivityRootLeash;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mDisplayId);
            this.mLeash.writeToParcel(parcel, i);
            this.mOffset.writeToParcel(parcel, i);
            if (CoreRune.MW_SHELL_TRANSITION) {
                this.mConfiguration.writeToParcel(parcel, i);
                parcel.writeBoolean(this.mIsActivityRootLeash);
            }
        }

        public String toString() {
            return this.mDisplayId + "@" + this.mOffset + ":" + this.mLeash;
        }
    }

    public boolean hasChangeTransition() {
        Iterator<Change> it = this.mChanges.iterator();
        while (it.hasNext()) {
            if (it.next().getChangeLeash() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCustomDisplayChangeTransition() {
        Iterator<Change> it = this.mChanges.iterator();
        while (it.hasNext()) {
            if (it.next().hasFlags(536870912)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSeparatedFromCustomDisplayChange() {
        return this.mSeparatedFromCustomDisplayChange;
    }

    public void setSeparatedFromCustomDisplayChange(boolean z) {
        if (this.mSeparatedFromCustomDisplayChange != z) {
            this.mSeparatedFromCustomDisplayChange = z;
            Log.d(TAG, "setSeparatedFromCustomDisplayChange: " + z + ", Callers=" + Debug.getCallers(3));
        }
    }

    public boolean isAnimatePendingSplitWithDisplayChange() {
        return this.mAnimatePendingSplitWithDisplayChange;
    }

    public void setAnimatePendingSplitWithDisplayChange(boolean z) {
        if (this.mAnimatePendingSplitWithDisplayChange != z) {
            this.mAnimatePendingSplitWithDisplayChange = z;
            Log.d(TAG, "setAnimatePendingSplitWithDisplayChange: " + z + ", Callers=" + Debug.getCallers(3));
        }
    }

    public Change getSplitActivityChangeTransition() {
        Iterator<Change> it = this.mChanges.iterator();
        while (it.hasNext()) {
            Change next = it.next();
            if (next.getSplitActivityMode() && next.getMode() == 6) {
                return next;
            }
        }
        return null;
    }

    public void setShouldAnimateDefaultDisplay(boolean z) {
        this.mShouldAnimateDefaultDisplay = z;
    }

    public boolean shouldAnimateDefaultDisplay() {
        return this.mShouldAnimateDefaultDisplay;
    }
}
