package android.window;

import android.app.ActivityManager;
import android.app.IApplicationThread;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.NtpTrustedTime;
import android.view.SurfaceControl;
import android.view.WindowManager;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.share.SemShareConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class TransitionInfo implements Parcelable {
  public static final Parcelable.Creator<TransitionInfo> CREATOR =
      new Parcelable.Creator<TransitionInfo>() { // from class: android.window.TransitionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionInfo createFromParcel(Parcel in) {
          return new TransitionInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransitionInfo[] newArray(int size) {
          return new TransitionInfo[size];
        }
      };
  public static final int FLAGS_IS_NON_APP_WINDOW = 65794;
  public static final int FLAG_ABOVE_TRANSIENT_LAUNCH = 1073741824;
  public static final int FLAG_BACK_GESTURE_ANIMATED = 131072;
  public static final int FLAG_CROSS_PROFILE_OWNER_THUMBNAIL = 4096;
  public static final int FLAG_CROSS_PROFILE_WORK_THUMBNAIL = 8192;
  public static final int FLAG_CUSTOM_DISPLAY_CHANGE_TRANSITION = 268435456;
  public static final int FLAG_DISPLAY_HAS_ALERT_WINDOWS = 128;
  public static final int FLAG_EDGE_EXTENSION_RESTRICTION = 33554432;
  public static final int FLAG_FAST_ANIMATION = 536870912;
  public static final int FLAG_FILLS_TASK = 1024;
  public static final int FLAG_FIRST_CUSTOM = 4194304;
  public static final int FLAG_IN_TASK_WITH_EMBEDDED_ACTIVITY = 512;
  public static final int FLAG_IS_ACTIVITY = 8388608;
  public static final int FLAG_IS_BEHIND_STARTING_WINDOW = 16384;
  public static final int FLAG_IS_DISPLAY = 32;
  public static final int FLAG_IS_INPUT_METHOD = 256;
  public static final int FLAG_IS_OCCLUDED = 32768;
  public static final int FLAG_IS_SYSTEM_WINDOW = 65536;
  public static final int FLAG_IS_TASK_DISPLAY_AREA = 16777216;
  public static final int FLAG_IS_TRANSIENT_LAUNCH_OVERLAY = 134217728;
  public static final int FLAG_IS_VOICE_INTERACTION = 16;
  public static final int FLAG_IS_WALLPAPER = 2;
  public static final int FLAG_LATE_TRANSIENT_LAUNCH = 67108864;
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
  private boolean mCanMergeAnimation;
  private boolean mCanTransferAnimation;
  private final ArrayList<Change> mChanges;
  private int mDebugId;
  private int mDisplayChangeBackColor;
  private int mFlags;
  private AnimationOptions mOptions;
  private IApplicationThread mRemoteAppThread;
  private final ArrayList<Root> mRoots;
  private boolean mSeparatedFromCustomDisplayChange;
  private boolean mSkipMergeAnimation;
  private int mTrack;
  private final int mType;

  public @interface ChangeFlags {}

  public @interface TransitionMode {}

  public TransitionInfo(int type, int flags) {
    this.mTrack = 0;
    this.mChanges = new ArrayList<>();
    this.mRoots = new ArrayList<>();
    this.mDebugId = -1;
    this.mDisplayChangeBackColor = -1;
    this.mType = type;
    this.mFlags = flags;
  }

  private TransitionInfo(Parcel in) {
    this.mTrack = 0;
    ArrayList<Change> arrayList = new ArrayList<>();
    this.mChanges = arrayList;
    ArrayList<Root> arrayList2 = new ArrayList<>();
    this.mRoots = arrayList2;
    this.mDebugId = -1;
    this.mDisplayChangeBackColor = -1;
    this.mType = in.readInt();
    this.mFlags = in.readInt();
    in.readTypedList(arrayList, Change.CREATOR);
    in.readTypedList(arrayList2, Root.CREATOR);
    this.mOptions = (AnimationOptions) in.readTypedObject(AnimationOptions.CREATOR);
    this.mDebugId = in.readInt();
    this.mTrack = in.readInt();
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
      this.mCanMergeAnimation = in.readBoolean();
      this.mSkipMergeAnimation = in.readBoolean();
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER) {
        this.mCanTransferAnimation = in.readBoolean();
      }
    }
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mType);
    dest.writeInt(this.mFlags);
    dest.writeTypedList(this.mChanges);
    dest.writeTypedList(this.mRoots, flags);
    dest.writeTypedObject(this.mOptions, flags);
    dest.writeInt(this.mDebugId);
    dest.writeInt(this.mTrack);
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
      dest.writeBoolean(this.mCanMergeAnimation);
      dest.writeBoolean(this.mSkipMergeAnimation);
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER) {
        dest.writeBoolean(this.mCanTransferAnimation);
      }
    }
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public void addRootLeash(int displayId, SurfaceControl leash, int offsetLeft, int offsetTop) {
    this.mRoots.add(new Root(displayId, leash, offsetLeft, offsetTop));
  }

  public void addRootLeash(
      int displayId,
      SurfaceControl leash,
      int offsetLeft,
      int offsetTop,
      Configuration rootConfig,
      boolean isActivityRootLeash) {
    this.mRoots.add(
        new Root(displayId, leash, offsetLeft, offsetTop, rootConfig, isActivityRootLeash));
  }

  public void addRoot(Root other) {
    this.mRoots.add(other);
  }

  public void setAnimationOptions(AnimationOptions options) {
    this.mOptions = options;
  }

  public int getType() {
    return this.mType;
  }

  public void setFlags(int flags) {
    this.mFlags = flags;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public int getRootCount() {
    return this.mRoots.size();
  }

  public Root getRoot(int idx) {
    return this.mRoots.get(idx);
  }

  public int findRootIndex(int displayId) {
    for (int i = 0; i < this.mRoots.size(); i++) {
      if (this.mRoots.get(i).mDisplayId == displayId) {
        return i;
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
      Log.m97e(TAG, "Assuming one animation root when there are more.", new Throwable());
    }
    return this.mRoots.get(0).mLeash;
  }

  public AnimationOptions getAnimationOptions() {
    return this.mOptions;
  }

  public List<Change> getChanges() {
    return this.mChanges;
  }

  public Change getChange(WindowContainerToken token) {
    for (int i = this.mChanges.size() - 1; i >= 0; i--) {
      if (token.equals(this.mChanges.get(i).mContainer)) {
        return this.mChanges.get(i);
      }
    }
    return null;
  }

  public void addChange(Change change) {
    this.mChanges.add(change);
  }

  public boolean hasChangesOrSideEffects() {
    return (this.mChanges.isEmpty() && !isKeyguardGoingAway() && (this.mFlags & 2048) == 0)
        ? false
        : true;
  }

  public boolean isKeyguardGoingAway() {
    return (this.mFlags & 256) != 0;
  }

  public int getTrack() {
    return this.mTrack;
  }

  public void setTrack(int track) {
    this.mTrack = track;
  }

  public void setDebugId(int id) {
    this.mDebugId = id;
  }

  public int getDebugId() {
    return this.mDebugId;
  }

  public boolean hasChangeTransition() {
    Iterator<Change> it = this.mChanges.iterator();
    while (it.hasNext()) {
      Change change = it.next();
      if (change.getChangeLeash() != null) {
        return true;
      }
    }
    return false;
  }

  public Change getChangeForAppsEdgeActivity() {
    Iterator<Change> it = this.mChanges.iterator();
    while (it.hasNext()) {
      Change change = it.next();
      ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
      if (taskInfo != null
          && taskInfo.realActivity != null
          && MultiWindowUtils.isAppsEdgeActivity(taskInfo.realActivity)) {
        return change;
      }
    }
    return null;
  }

  public boolean hasCustomDisplayChangeTransition() {
    Iterator<Change> it = this.mChanges.iterator();
    while (it.hasNext()) {
      Change change = it.next();
      if (change.hasFlags(268435456)) {
        return true;
      }
    }
    return false;
  }

  public Change findChange(Predicate<Change> callback) {
    Iterator<Change> it = this.mChanges.iterator();
    while (it.hasNext()) {
      Change change = it.next();
      if (callback.test(change)) {
        return change;
      }
    }
    return null;
  }

  public boolean isSeparatedFromCustomDisplayChange() {
    return this.mSeparatedFromCustomDisplayChange;
  }

  public void setSeparatedFromCustomDisplayChange(boolean separated) {
    if (this.mSeparatedFromCustomDisplayChange != separated) {
      this.mSeparatedFromCustomDisplayChange = separated;
      Log.m94d(
          TAG,
          "setSeparatedFromCustomDisplayChange: " + separated + ", Callers=" + Debug.getCallers(3));
    }
  }

  public void setCanMergeAnimation() {
    this.mCanMergeAnimation = true;
  }

  public boolean canMergeAnimation() {
    return this.mCanMergeAnimation;
  }

  public void setSkipMergeAnimation() {
    this.mSkipMergeAnimation = true;
  }

  public boolean canSkipMergeAnimation() {
    return this.mSkipMergeAnimation;
  }

  public void setRemoteAppThread(IApplicationThread appThread) {
    this.mRemoteAppThread = appThread;
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

  public void overrideDisplayChangeBackColor(int displayChangeBackColor) {
    this.mDisplayChangeBackColor = displayChangeBackColor;
  }

  public int getOverrideDisplayChangeBackColor() {
    return this.mDisplayChangeBackColor;
  }

  public String toString() {
    return toString("");
  }

  public String toString(String prefix) {
    String changesLineStart;
    boolean shouldPrettyPrint = (prefix.isEmpty() || this.mChanges.isEmpty()) ? false : true;
    String perChangeLineStart = "";
    String innerPrefix = shouldPrettyPrint ? prefix + "    " : "";
    if (!shouldPrettyPrint) {
      changesLineStart = "";
    } else {
      changesLineStart = "\n" + prefix;
    }
    if (shouldPrettyPrint) {
      perChangeLineStart = "\n" + innerPrefix;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{id=")
        .append(this.mDebugId)
        .append(" t=")
        .append(WindowManager.transitTypeToString(this.mType))
        .append(" f=0x")
        .append(Integer.toHexString(this.mFlags))
        .append(" trk=")
        .append(this.mTrack)
        .append(" r=[");
    for (int i = 0; i < this.mRoots.size(); i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(this.mRoots.get(i).mDisplayId).append("@").append(this.mRoots.get(i).mOffset);
    }
    sb.append("] c=[");
    sb.append(perChangeLineStart);
    for (int i2 = 0; i2 < this.mChanges.size(); i2++) {
      if (i2 > 0) {
        sb.append(',');
        sb.append(perChangeLineStart);
      }
      sb.append(this.mChanges.get(i2));
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
      if (this.mCanMergeAnimation) {
        sb.append("] [merge=true");
      }
      if (this.mSkipMergeAnimation) {
        sb.append("] [skipMerge=true");
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER && this.mCanTransferAnimation) {
        sb.append("] [transfer=true");
      }
    }
    sb.append(changesLineStart);
    sb.append("]}");
    return sb.toString();
  }

  public static String modeToString(int mode) {
    switch (mode) {
      case 0:
        return KeyProperties.DIGEST_NONE;
      case 1:
        return SemShareConstants.DMA_SURVEY_FEATURE_OPEN;
      case 2:
        return "CLOSE";
      case 3:
        return "TO_FRONT";
      case 4:
        return "TO_BACK";
      case 5:
      default:
        return "<unknown:" + mode + ">";
      case 6:
        return "CHANGE";
    }
  }

  public static String flagsToString(int flags) {
    if (flags == 0) {
      return KeyProperties.DIGEST_NONE;
    }
    StringBuilder sb = new StringBuilder();
    if ((flags & 1) != 0) {
      sb.append("SHOW_WALLPAPER");
    }
    if ((flags & 2) != 0) {
      sb.append("IS_WALLPAPER");
    }
    if ((flags & 256) != 0) {
      sb.append("IS_INPUT_METHOD");
    }
    if ((flags & 4) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("TRANSLUCENT");
    }
    if ((flags & 8) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("STARTING_WINDOW_TRANSFER");
    }
    if ((flags & 16) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("IS_VOICE_INTERACTION");
    }
    if ((flags & 32) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("IS_DISPLAY");
    }
    if ((flags & 128) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("DISPLAY_HAS_ALERT_WINDOWS");
    }
    if ((flags & 512) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("IN_TASK_WITH_EMBEDDED_ACTIVITY");
    }
    if ((flags & 1024) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("FILLS_TASK");
    }
    if ((flags & 16384) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("IS_BEHIND_STARTING_WINDOW");
    }
    if ((32768 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("IS_OCCLUDED");
    }
    if ((65536 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("FLAG_IS_SYSTEM_WINDOW");
    }
    if ((131072 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("FLAG_BACK_GESTURE_ANIMATED");
    }
    if ((262144 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("NO_ANIMATION");
    }
    if ((524288 & flags) != 0) {
      sb.append(
          (sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
              + "TASK_LAUNCHING_BEHIND");
    }
    if ((2097152 & flags) != 0) {
      sb.append(
          (sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) + "SYNC");
    }
    if ((4194304 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("FIRST_CUSTOM");
    }
    if ((1048576 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("MOVE_TO_TOP");
    }
    if (CoreRune.MW_SHELL_TRANSITION) {
      if ((8388608 & flags) != 0) {
        sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
            .append("IS_ACTIVITY");
      }
      if ((16777216 & flags) != 0) {
        sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
            .append("IS_TASK_DISPLAY_AREA");
      }
    }
    if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
      if ((268435456 & flags) != 0) {
        sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
            .append("CUSTOM_DISPLAY_CHANGE_TRANSITION");
      }
      if ((536870912 & flags) != 0) {
        sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
            .append("FAST_ANIMATION");
      }
    }
    if ((33554432 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("EDGE_EXTENSION_RESTRICTION");
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH && (67108864 & flags) != 0) {
      sb.append(sb.length() == 0 ? "" : NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)
          .append("LATE_TRANSIENT_LAUNCH");
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && (134217728 & flags) != 0) {
      sb.append(sb.length() != 0 ? NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER : "")
          .append("TRANSIENT_LAUNCH_OVERLAY");
    }
    return sb.toString();
  }

  public static boolean isIndependent(Change change, TransitionInfo info) {
    if (change.getParent() == null) {
      return true;
    }
    if (change.getMode() == 6) {
      return false;
    }
    Change parentChg = info.getChange(change.getParent());
    while (parentChg != null && parentChg.getMode() == 6) {
      if (parentChg.getParent() == null) {
        return true;
      }
      parentChg = info.getChange(parentChg.getParent());
    }
    return false;
  }

  public void releaseAnimSurfaces() {
    for (int i = this.mChanges.size() - 1; i >= 0; i--) {
      Change c = this.mChanges.get(i);
      if (c.mSnapshot != null) {
        c.mSnapshot.release();
        c.mSnapshot = null;
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION && c.mChangeLeash != null) {
        c.mChangeLeash.release();
        c.mChangeLeash = null;
      }
    }
    for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
      this.mRoots.get(i2).mLeash.release();
    }
  }

  public void releaseAllSurfaces() {
    releaseAnimSurfaces();
    for (int i = this.mChanges.size() - 1; i >= 0; i--) {
      this.mChanges.get(i).getLeash().release();
    }
  }

  public void setUnreleasedWarningCallSiteForAllSurfaces(String callsite) {
    for (int i = this.mChanges.size() - 1; i >= 0; i--) {
      this.mChanges.get(i).getLeash().setUnreleasedWarningCallSite(callsite);
    }
  }

  public TransitionInfo localRemoteCopy() {
    TransitionInfo out = new TransitionInfo(this.mType, this.mFlags);
    out.mTrack = this.mTrack;
    out.mDebugId = this.mDebugId;
    for (int i = 0; i < this.mChanges.size(); i++) {
      out.mChanges.add(this.mChanges.get(i).localRemoteCopy());
    }
    for (int i2 = 0; i2 < this.mRoots.size(); i2++) {
      out.mRoots.add(this.mRoots.get(i2).localRemoteCopy());
    }
    out.mOptions = this.mOptions;
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
      out.mCanMergeAnimation = this.mCanMergeAnimation;
      out.mSkipMergeAnimation = this.mSkipMergeAnimation;
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER) {
        out.mCanTransferAnimation = this.mCanTransferAnimation;
      }
    }
    return out;
  }

  public static final class Change implements Parcelable {
    public static final Parcelable.Creator<Change> CREATOR =
        new Parcelable.Creator<Change>() { // from class: android.window.TransitionInfo.Change.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Change createFromParcel(Parcel in) {
            return new Change(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Change[] newArray(int size) {
            return new Change[size];
          }
        };
    private boolean mAffordanceTargetFreeformTask;
    private boolean mAllowEnterPip;
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
    private final Point mEndRelOffset;
    private int mEndRotation;
    private boolean mFadeInOutRotationNeeded;
    private int mFlags;
    private int mForceHidingTransit;
    private float mFreeformStashScale;
    private final Rect mInsetsForRecentsTransition;
    private boolean mIsEnteringPinnedMode;
    private boolean mIsInSplitActivityMode;
    private boolean mIsPopOverAnimationNeeded;
    private boolean mIsRotationAnimation;
    private boolean mIsTransitionWithDim;
    private WindowContainerToken mLastParent;
    private final SurfaceControl mLeash;
    private int mMinimizeAnimState;
    private final PointF mMinimizePoint;
    private int mMode;
    private WindowContainerToken mParent;
    private boolean mResumedAffordance;
    private int mRotationAnimation;
    private boolean mSkipDefaultTransition;
    private SurfaceControl mSnapshot;
    private float mSnapshotLuma;
    private final Rect mStartAbsBounds;
    private int mStartDisplayId;
    private int mStartRotation;
    private ActivityManager.RunningTaskInfo mTaskInfo;
    private final Rect mTentModeClipRect;

    public void setTentModeClipRect(Rect rect) {
      this.mTentModeClipRect.set(rect);
    }

    public Rect getTentModeClipRect() {
      return this.mTentModeClipRect;
    }

    public void setTransitionWithDim(boolean isTransitionWithDim) {
      this.mIsTransitionWithDim = isTransitionWithDim;
    }

    public boolean isTransitionWithDim() {
      return this.mIsTransitionWithDim;
    }

    public void setResumedAffordance(boolean resumedAffordance) {
      this.mResumedAffordance = resumedAffordance;
    }

    public boolean getResumedAffordance() {
      return this.mResumedAffordance;
    }

    public void setAffordanceTargetFreeformTask(boolean isAffordanceTargetFreeformTask) {
      this.mAffordanceTargetFreeformTask = isAffordanceTargetFreeformTask;
    }

    public boolean getAffordanceTargetFreeformTask() {
      return this.mAffordanceTargetFreeformTask;
    }

    public void setRotationAnimation(boolean isRotationAnimation) {
      this.mIsRotationAnimation = isRotationAnimation;
    }

    public boolean isRotationAnimation() {
      return this.mIsRotationAnimation;
    }

    public Change(WindowContainerToken container, SurfaceControl leash) {
      this.mMode = 0;
      this.mFlags = 0;
      this.mStartAbsBounds = new Rect();
      this.mEndAbsBounds = new Rect();
      this.mEndRelOffset = new Point();
      this.mTaskInfo = null;
      this.mStartDisplayId = -1;
      this.mEndDisplayId = -1;
      this.mStartRotation = -1;
      this.mEndRotation = -1;
      this.mEndFixedRotation = -1;
      this.mRotationAnimation = -1;
      this.mSnapshot = null;
      this.mConfiguration = new Configuration();
      this.mChangeLeash = null;
      this.mChangeTransitMode = 0;
      this.mChangeStartOutsets = new Rect();
      this.mChangeEndOutsets = new Rect();
      this.mTentModeClipRect = new Rect();
      this.mMinimizeAnimState = 0;
      this.mMinimizePoint = new PointF();
      this.mFreeformStashScale = 1.0f;
      this.mForceHidingTransit = 0;
      this.mInsetsForRecentsTransition = new Rect();
      this.mContainer = container;
      this.mLeash = leash;
    }

    private Change(Parcel in) {
      this.mMode = 0;
      this.mFlags = 0;
      Rect rect = new Rect();
      this.mStartAbsBounds = rect;
      Rect rect2 = new Rect();
      this.mEndAbsBounds = rect2;
      Point point = new Point();
      this.mEndRelOffset = point;
      this.mTaskInfo = null;
      this.mStartDisplayId = -1;
      this.mEndDisplayId = -1;
      this.mStartRotation = -1;
      this.mEndRotation = -1;
      this.mEndFixedRotation = -1;
      this.mRotationAnimation = -1;
      this.mSnapshot = null;
      Configuration configuration = new Configuration();
      this.mConfiguration = configuration;
      this.mChangeLeash = null;
      this.mChangeTransitMode = 0;
      Rect rect3 = new Rect();
      this.mChangeStartOutsets = rect3;
      Rect rect4 = new Rect();
      this.mChangeEndOutsets = rect4;
      this.mTentModeClipRect = new Rect();
      this.mMinimizeAnimState = 0;
      PointF pointF = new PointF();
      this.mMinimizePoint = pointF;
      this.mFreeformStashScale = 1.0f;
      this.mForceHidingTransit = 0;
      Rect rect5 = new Rect();
      this.mInsetsForRecentsTransition = rect5;
      this.mContainer = (WindowContainerToken) in.readTypedObject(WindowContainerToken.CREATOR);
      this.mParent = (WindowContainerToken) in.readTypedObject(WindowContainerToken.CREATOR);
      this.mLastParent = (WindowContainerToken) in.readTypedObject(WindowContainerToken.CREATOR);
      SurfaceControl surfaceControl = new SurfaceControl();
      this.mLeash = surfaceControl;
      surfaceControl.readFromParcel(in);
      this.mMode = in.readInt();
      this.mFlags = in.readInt();
      rect.readFromParcel(in);
      rect2.readFromParcel(in);
      point.readFromParcel(in);
      this.mTaskInfo =
          (ActivityManager.RunningTaskInfo)
              in.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
      this.mAllowEnterPip = in.readBoolean();
      this.mStartDisplayId = in.readInt();
      this.mEndDisplayId = in.readInt();
      this.mStartRotation = in.readInt();
      this.mEndRotation = in.readInt();
      this.mEndFixedRotation = in.readInt();
      this.mRotationAnimation = in.readInt();
      this.mBackgroundColor = in.readInt();
      this.mSnapshot = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
      this.mSnapshotLuma = in.readFloat();
      if (CoreRune.MW_SHELL_TRANSITION) {
        configuration.readFromParcel(in);
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
        this.mChangeLeash = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
        this.mChangeTransitMode = in.readInt();
        rect3.readFromParcel(in);
        rect4.readFromParcel(in);
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM) {
        this.mIsTransitionWithDim = in.readBoolean();
      }
      if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
        this.mMinimizeAnimState = in.readInt();
        pointF.readFromParcel(in);
      }
      this.mFadeInOutRotationNeeded = in.readBoolean();
      this.mResumedAffordance = in.readBoolean();
      this.mAffordanceTargetFreeformTask = in.readBoolean();
      if (CoreRune.MW_PIP_SHELL_TRANSITION) {
        this.mIsEnteringPinnedMode = in.readBoolean();
      }
      this.mIsPopOverAnimationNeeded = in.readBoolean();
      this.mFreeformStashScale = in.readFloat();
      this.mForceHidingTransit = in.readInt();
      rect5.readFromParcel(in);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Change localRemoteCopy() {
      Change out = new Change(this.mContainer, new SurfaceControl(this.mLeash, "localRemote"));
      out.mParent = this.mParent;
      out.mLastParent = this.mLastParent;
      out.mMode = this.mMode;
      out.mFlags = this.mFlags;
      out.mStartAbsBounds.set(this.mStartAbsBounds);
      out.mEndAbsBounds.set(this.mEndAbsBounds);
      out.mEndRelOffset.set(this.mEndRelOffset);
      out.mTaskInfo = this.mTaskInfo;
      out.mAllowEnterPip = this.mAllowEnterPip;
      out.mStartDisplayId = this.mStartDisplayId;
      out.mEndDisplayId = this.mEndDisplayId;
      out.mStartRotation = this.mStartRotation;
      out.mEndRotation = this.mEndRotation;
      out.mEndFixedRotation = this.mEndFixedRotation;
      out.mRotationAnimation = this.mRotationAnimation;
      out.mBackgroundColor = this.mBackgroundColor;
      out.mSnapshot =
          this.mSnapshot != null ? new SurfaceControl(this.mSnapshot, "localRemote") : null;
      out.mSnapshotLuma = this.mSnapshotLuma;
      if (CoreRune.MW_SHELL_TRANSITION) {
        out.mConfiguration.setTo(this.mConfiguration);
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
        out.mChangeLeash =
            this.mChangeLeash != null ? new SurfaceControl(this.mChangeLeash, "localRemote") : null;
        out.mChangeTransitMode = this.mChangeTransitMode;
        out.mChangeStartOutsets.set(this.mChangeStartOutsets);
        out.mChangeEndOutsets.set(this.mChangeEndOutsets);
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM) {
        out.mIsTransitionWithDim = this.mIsTransitionWithDim;
      }
      if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
        out.mMinimizeAnimState = this.mMinimizeAnimState;
        out.mMinimizePoint.set(this.mMinimizePoint);
      }
      out.mFadeInOutRotationNeeded = this.mFadeInOutRotationNeeded;
      out.mResumedAffordance = this.mResumedAffordance;
      out.mAffordanceTargetFreeformTask = this.mAffordanceTargetFreeformTask;
      if (CoreRune.MW_PIP_SHELL_TRANSITION) {
        out.mIsEnteringPinnedMode = this.mIsEnteringPinnedMode;
      }
      out.mIsPopOverAnimationNeeded = this.mIsPopOverAnimationNeeded;
      out.mFreeformStashScale = this.mFreeformStashScale;
      out.mForceHidingTransit = this.mForceHidingTransit;
      out.mInsetsForRecentsTransition.set(this.mInsetsForRecentsTransition);
      return out;
    }

    public void setParent(WindowContainerToken parent) {
      this.mParent = parent;
    }

    public void setLastParent(WindowContainerToken lastParent) {
      this.mLastParent = lastParent;
    }

    public void setMode(int mode) {
      this.mMode = mode;
    }

    public void setFlags(int flags) {
      this.mFlags = flags;
    }

    public void setStartAbsBounds(Rect rect) {
      this.mStartAbsBounds.set(rect);
    }

    public void setEndAbsBounds(Rect rect) {
      this.mEndAbsBounds.set(rect);
    }

    public void setEndRelOffset(int left, int top) {
      this.mEndRelOffset.set(left, top);
    }

    public void setTaskInfo(ActivityManager.RunningTaskInfo taskInfo) {
      this.mTaskInfo = taskInfo;
    }

    public void setAllowEnterPip(boolean allowEnterPip) {
      this.mAllowEnterPip = allowEnterPip;
    }

    public void setDisplayId(int start, int end) {
      this.mStartDisplayId = start;
      this.mEndDisplayId = end;
    }

    public void setRotation(int start, int end) {
      this.mStartRotation = start;
      this.mEndRotation = end;
    }

    public void setEndFixedRotation(int endFixedRotation) {
      this.mEndFixedRotation = endFixedRotation;
    }

    public void setRotationAnimation(int anim) {
      this.mRotationAnimation = anim;
    }

    public void setBackgroundColor(int backgroundColor) {
      this.mBackgroundColor = backgroundColor;
    }

    public void setSnapshot(SurfaceControl snapshot, float luma) {
      this.mSnapshot = snapshot;
      this.mSnapshotLuma = luma;
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

    public boolean hasFlags(int flags) {
      return (this.mFlags & flags) != 0;
    }

    public boolean hasAllFlags(int flags) {
      return (this.mFlags & flags) == flags;
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

    public SurfaceControl getLeash() {
      return this.mLeash;
    }

    public ActivityManager.RunningTaskInfo getTaskInfo() {
      return this.mTaskInfo;
    }

    public boolean getAllowEnterPip() {
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

    public void setConfiguration(Configuration configuration) {
      this.mConfiguration.updateFrom(configuration);
    }

    public Configuration getConfiguration() {
      return this.mConfiguration;
    }

    public SurfaceControl getChangeLeash() {
      return this.mChangeLeash;
    }

    public void setChangeLeash(SurfaceControl changeLeash) {
      this.mChangeLeash = changeLeash;
    }

    public int getChangeTransitMode() {
      return this.mChangeTransitMode;
    }

    public void setChangeTransitMode(int changeTransitMode) {
      this.mChangeTransitMode = changeTransitMode;
    }

    public void setChangeStartOutsets(Rect startOutsets) {
      this.mChangeStartOutsets.set(startOutsets);
    }

    public void setChangeEndOutsets(Rect endOutsets) {
      this.mChangeEndOutsets.set(endOutsets);
    }

    public Rect getChangeStartOutsets() {
      return this.mChangeStartOutsets;
    }

    public Rect getChangeEndOutsets() {
      return this.mChangeEndOutsets;
    }

    public boolean hasChangeStartOutsets() {
      return this.mChangeStartOutsets.left > 0
          || this.mChangeStartOutsets.top > 0
          || this.mChangeStartOutsets.right > 0
          || this.mChangeStartOutsets.bottom > 0;
    }

    public boolean hasChangeEndOutsets() {
      return this.mChangeEndOutsets.left > 0
          || this.mChangeEndOutsets.top > 0
          || this.mChangeEndOutsets.right > 0
          || this.mChangeEndOutsets.bottom > 0;
    }

    public void setMinimizeAnimState(int minimizeAnimState) {
      this.mMinimizeAnimState = minimizeAnimState;
    }

    public int getMinimizeAnimState() {
      return this.mMinimizeAnimState;
    }

    public void setMinimizePoint(PointF minimizePoint) {
      this.mMinimizePoint.set(minimizePoint);
    }

    public PointF getMinimizePoint() {
      return this.mMinimizePoint;
    }

    public void setFadeInOutRotationNeeded() {
      this.mFadeInOutRotationNeeded = true;
    }

    public boolean isFadeInOutRotationNeeded() {
      return this.mFadeInOutRotationNeeded;
    }

    public void setSplitActivityMode(boolean splitActivityMode) {
      this.mIsInSplitActivityMode = splitActivityMode;
    }

    public boolean getSplitActivityMode() {
      return this.mIsInSplitActivityMode;
    }

    public void setEnteringPinnedMode(boolean enteringPinnedMode) {
      this.mIsEnteringPinnedMode = enteringPinnedMode;
    }

    public boolean isEnteringPinnedMode() {
      return this.mIsEnteringPinnedMode;
    }

    public void setFreeformStashScale(float freeformStashScale) {
      this.mFreeformStashScale = freeformStashScale;
    }

    public float getFreeformStashScale() {
      return this.mFreeformStashScale;
    }

    public void setForceHidingTransit(int forceHidingTransit) {
      this.mForceHidingTransit = forceHidingTransit;
    }

    public int getForceHidingTransit() {
      return this.mForceHidingTransit;
    }

    public void setPopOverAnimationNeeded(boolean popOverAnimationNeeded) {
      this.mIsPopOverAnimationNeeded = popOverAnimationNeeded;
    }

    public boolean getPopOverAnimationNeeded() {
      return this.mIsPopOverAnimationNeeded;
    }

    public void setInsetsForRecentsTransition(Rect rect) {
      this.mInsetsForRecentsTransition.set(rect);
    }

    public Rect getInsetsForRecentsTransition() {
      return this.mInsetsForRecentsTransition;
    }

    public void setSkipDefaultTransition(boolean skipDefaultTransition) {
      this.mSkipDefaultTransition = skipDefaultTransition;
    }

    public boolean shouldSkipDefaultTransition() {
      return this.mSkipDefaultTransition;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeTypedObject(this.mContainer, flags);
      dest.writeTypedObject(this.mParent, flags);
      dest.writeTypedObject(this.mLastParent, flags);
      this.mLeash.writeToParcel(dest, flags);
      dest.writeInt(this.mMode);
      dest.writeInt(this.mFlags);
      this.mStartAbsBounds.writeToParcel(dest, flags);
      this.mEndAbsBounds.writeToParcel(dest, flags);
      this.mEndRelOffset.writeToParcel(dest, flags);
      dest.writeTypedObject(this.mTaskInfo, flags);
      dest.writeBoolean(this.mAllowEnterPip);
      dest.writeInt(this.mStartDisplayId);
      dest.writeInt(this.mEndDisplayId);
      dest.writeInt(this.mStartRotation);
      dest.writeInt(this.mEndRotation);
      dest.writeInt(this.mEndFixedRotation);
      dest.writeInt(this.mRotationAnimation);
      dest.writeInt(this.mBackgroundColor);
      dest.writeTypedObject(this.mSnapshot, flags);
      dest.writeFloat(this.mSnapshotLuma);
      if (CoreRune.MW_SHELL_TRANSITION) {
        this.mConfiguration.writeToParcel(dest, flags);
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
        dest.writeTypedObject(this.mChangeLeash, flags);
        dest.writeInt(this.mChangeTransitMode);
        this.mChangeStartOutsets.writeToParcel(dest, flags);
        this.mChangeEndOutsets.writeToParcel(dest, flags);
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM) {
        dest.writeBoolean(this.mIsTransitionWithDim);
      }
      if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
        dest.writeInt(this.mMinimizeAnimState);
        this.mMinimizePoint.writeToParcel(dest, flags);
      }
      dest.writeBoolean(this.mFadeInOutRotationNeeded);
      dest.writeBoolean(this.mResumedAffordance);
      dest.writeBoolean(this.mAffordanceTargetFreeformTask);
      if (CoreRune.MW_PIP_SHELL_TRANSITION) {
        dest.writeBoolean(this.mIsEnteringPinnedMode);
      }
      dest.writeBoolean(this.mIsPopOverAnimationNeeded);
      dest.writeFloat(this.mFreeformStashScale);
      dest.writeInt(this.mForceHidingTransit);
      this.mInsetsForRecentsTransition.writeToParcel(dest, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('{');
      sb.append(this.mContainer);
      sb.append(" m=");
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
      if (this.mEndRelOffset.f85x != 0 || this.mEndRelOffset.f86y != 0) {
        sb.append(" eo=");
        sb.append(this.mEndRelOffset);
      }
      sb.append(" d=");
      int i = this.mStartDisplayId;
      if (i != this.mEndDisplayId) {
        sb.append(i).append(Session.SUBSESSION_SEPARATION_CHAR);
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
      if (this.mSnapshot != null) {
        sb.append(" snapshot=");
        sb.append(this.mSnapshot);
      }
      if (this.mLastParent != null) {
        sb.append(" lastParent=");
        sb.append(this.mLastParent);
      }
      if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
        if (this.mChangeLeash != null) {
          sb.append(" changeLeash=").append(this.mChangeLeash);
        }
        if (this.mChangeTransitMode != 0) {
          sb.append(" cm=").append(this.mChangeTransitMode);
        }
        if (hasChangeStartOutsets()) {
          sb.append(" cso=" + this.mChangeStartOutsets);
        }
        if (hasChangeEndOutsets()) {
          sb.append(" ceo=" + this.mChangeEndOutsets);
        }
      }
      if (this.mForceHidingTransit != 0) {
        sb.append(" fht=").append(this.mForceHidingTransit);
      }
      if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mIsEnteringPinnedMode) {
        sb.append(" enter_pip=true");
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_WITH_DIM && this.mIsTransitionWithDim) {
        sb.append(" dim=").append("true");
      }
      if (this.mFadeInOutRotationNeeded) {
        sb.append(" fade=").append(this.mFadeInOutRotationNeeded);
      }
      if (this.mResumedAffordance) {
        sb.append(" resumedAffordance=").append("true");
      }
      if (this.mAffordanceTargetFreeformTask) {
        sb.append(" affordanceTargetFreeformTask=").append("true");
      }
      sb.append(" inset=");
      sb.append(this.mInsetsForRecentsTransition);
      sb.append('}');
      return sb.toString();
    }
  }

  public static final class AnimationOptions implements Parcelable {
    public static final Parcelable.Creator<AnimationOptions> CREATOR =
        new Parcelable.Creator<
            AnimationOptions>() { // from class: android.window.TransitionInfo.AnimationOptions.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public AnimationOptions createFromParcel(Parcel in) {
            return new AnimationOptions(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public AnimationOptions[] newArray(int size) {
            return new AnimationOptions[size];
          }
        };
    private int mAnimations;
    private int mBackgroundColor;
    private CustomActivityTransition mCustomActivityCloseTransition;
    private CustomActivityTransition mCustomActivityOpenTransition;
    private int mEnterResId;
    private int mExitResId;
    private boolean mOverrideTaskTransition;
    private String mPackageName;
    private HardwareBuffer mThumbnail;
    private final Rect mTransitionBounds;
    private int mType;

    private AnimationOptions(int type) {
      this.mTransitionBounds = new Rect();
      this.mType = type;
    }

    public AnimationOptions(Parcel in) {
      Rect rect = new Rect();
      this.mTransitionBounds = rect;
      this.mType = in.readInt();
      this.mEnterResId = in.readInt();
      this.mExitResId = in.readInt();
      this.mBackgroundColor = in.readInt();
      this.mOverrideTaskTransition = in.readBoolean();
      this.mPackageName = in.readString();
      rect.readFromParcel(in);
      this.mThumbnail = (HardwareBuffer) in.readTypedObject(HardwareBuffer.CREATOR);
      this.mAnimations = in.readInt();
      this.mCustomActivityOpenTransition =
          (CustomActivityTransition) in.readTypedObject(CustomActivityTransition.CREATOR);
      this.mCustomActivityCloseTransition =
          (CustomActivityTransition) in.readTypedObject(CustomActivityTransition.CREATOR);
    }

    public static AnimationOptions makeCommonAnimOptions(String packageName) {
      AnimationOptions options = new AnimationOptions(14);
      options.mPackageName = packageName;
      return options;
    }

    public static AnimationOptions makeAnimOptionsFromLayoutParameters(
        WindowManager.LayoutParams lp) {
      AnimationOptions options = new AnimationOptions(14);
      options.mPackageName = lp.packageName;
      options.mAnimations = lp.windowAnimations;
      return options;
    }

    public void addOptionsFromLayoutParameters(WindowManager.LayoutParams lp) {
      this.mAnimations = lp.windowAnimations;
    }

    public void addCustomActivityTransition(
        boolean isOpen, int enterResId, int exitResId, int backgroundColor) {
      CustomActivityTransition customTransition =
          isOpen ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
      if (customTransition == null) {
        customTransition = new CustomActivityTransition();
        if (isOpen) {
          this.mCustomActivityOpenTransition = customTransition;
        } else {
          this.mCustomActivityCloseTransition = customTransition;
        }
      }
      customTransition.addCustomActivityTransition(enterResId, exitResId, backgroundColor);
    }

    public static AnimationOptions makeCustomAnimOptions(
        String packageName,
        int enterResId,
        int exitResId,
        int backgroundColor,
        boolean overrideTaskTransition) {
      AnimationOptions options = new AnimationOptions(1);
      options.mPackageName = packageName;
      options.mEnterResId = enterResId;
      options.mExitResId = exitResId;
      options.mBackgroundColor = backgroundColor;
      options.mOverrideTaskTransition = overrideTaskTransition;
      return options;
    }

    public static AnimationOptions makeClipRevealAnimOptions(
        int startX, int startY, int width, int height) {
      AnimationOptions options = new AnimationOptions(11);
      options.mTransitionBounds.set(startX, startY, startX + width, startY + height);
      return options;
    }

    public static AnimationOptions makeScaleUpAnimOptions(
        int startX, int startY, int width, int height) {
      AnimationOptions options = new AnimationOptions(2);
      options.mTransitionBounds.set(startX, startY, startX + width, startY + height);
      return options;
    }

    public static AnimationOptions makeThumbnailAnimOptions(
        HardwareBuffer srcThumb, int startX, int startY, boolean scaleUp) {
      AnimationOptions options = new AnimationOptions(scaleUp ? 3 : 4);
      options.mTransitionBounds.set(startX, startY, startX, startY);
      options.mThumbnail = srcThumb;
      return options;
    }

    public static AnimationOptions makeCrossProfileAnimOptions() {
      AnimationOptions options = new AnimationOptions(12);
      return options;
    }

    public static AnimationOptions makeSceneTransitionAnimOptions() {
      AnimationOptions options = new AnimationOptions(5);
      return options;
    }

    public static AnimationOptions makeCustomDisplayChangeAnimOptions(
        int enterResId, int exitResId) {
      AnimationOptions options = new AnimationOptions(15);
      options.mEnterResId = enterResId;
      options.mExitResId = exitResId;
      return options;
    }

    public int getType() {
      return this.mType;
    }

    public int getEnterResId() {
      return this.mEnterResId;
    }

    public int getExitResId() {
      return this.mExitResId;
    }

    public int getBackgroundColor() {
      return this.mBackgroundColor;
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

    public CustomActivityTransition getCustomActivityTransition(boolean open) {
      return open ? this.mCustomActivityOpenTransition : this.mCustomActivityCloseTransition;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mType);
      dest.writeInt(this.mEnterResId);
      dest.writeInt(this.mExitResId);
      dest.writeInt(this.mBackgroundColor);
      dest.writeBoolean(this.mOverrideTaskTransition);
      dest.writeString(this.mPackageName);
      this.mTransitionBounds.writeToParcel(dest, flags);
      dest.writeTypedObject(this.mThumbnail, flags);
      dest.writeInt(this.mAnimations);
      dest.writeTypedObject(this.mCustomActivityOpenTransition, flags);
      dest.writeTypedObject(this.mCustomActivityCloseTransition, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    private static String typeToString(int mode) {
      switch (mode) {
        case 1:
          return "ANIM_CUSTOM";
        case 2:
          return "ANIM_SCALE_UP";
        case 3:
          return "ANIM_THUMBNAIL_SCALE_UP";
        case 4:
          return "ANIM_THUMBNAIL_SCALE_DOWN";
        case 11:
          return "ANIM_CLIP_REVEAL";
        case 12:
          return "ANIM_OPEN_CROSS_PROFILE_APPS";
        default:
          return "<unknown:" + mode + ">";
      }
    }

    public String toString() {
      return "{ AnimationOptions type= "
          + typeToString(this.mType)
          + " package="
          + this.mPackageName
          + " override="
          + this.mOverrideTaskTransition
          + " b="
          + this.mTransitionBounds
          + "}";
    }

    public static class CustomActivityTransition implements Parcelable {
      public static final Parcelable.Creator<CustomActivityTransition> CREATOR =
          new Parcelable.Creator<
              CustomActivityTransition>() { // from class:
                                            // android.window.TransitionInfo.AnimationOptions.CustomActivityTransition.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomActivityTransition createFromParcel(Parcel in) {
              return new CustomActivityTransition(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomActivityTransition[] newArray(int size) {
              return new CustomActivityTransition[size];
            }
          };
      private int mCustomBackgroundColor;
      private int mCustomEnterResId;
      private int mCustomExitResId;

      public int getCustomEnterResId() {
        return this.mCustomEnterResId;
      }

      public int getCustomExitResId() {
        return this.mCustomExitResId;
      }

      public int getCustomBackgroundColor() {
        return this.mCustomBackgroundColor;
      }

      CustomActivityTransition() {}

      CustomActivityTransition(Parcel in) {
        this.mCustomEnterResId = in.readInt();
        this.mCustomExitResId = in.readInt();
        this.mCustomBackgroundColor = in.readInt();
      }

      public void addCustomActivityTransition(int enterResId, int exitResId, int backgroundColor) {
        this.mCustomEnterResId = enterResId;
        this.mCustomExitResId = exitResId;
        this.mCustomBackgroundColor = backgroundColor;
      }

      @Override // android.os.Parcelable
      public int describeContents() {
        return 0;
      }

      @Override // android.os.Parcelable
      public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mCustomEnterResId);
        dest.writeInt(this.mCustomExitResId);
        dest.writeInt(this.mCustomBackgroundColor);
      }
    }
  }

  public static final class Root implements Parcelable {
    public static final Parcelable.Creator<Root> CREATOR =
        new Parcelable.Creator<Root>() { // from class: android.window.TransitionInfo.Root.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Root createFromParcel(Parcel in) {
            return new Root(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public Root[] newArray(int size) {
            return new Root[size];
          }
        };
    private final Configuration mConfiguration;
    private final int mDisplayId;
    private boolean mIsActivityRootLeash;
    private final SurfaceControl mLeash;
    private final Point mOffset;

    public Root(int displayId, SurfaceControl leash, int offsetLeft, int offsetTop) {
      this(displayId, leash, offsetLeft, offsetTop, null, false);
    }

    public Root(
        int displayId,
        SurfaceControl leash,
        int offsetLeft,
        int offsetTop,
        Configuration rootConfig,
        boolean isActivityRootLeash) {
      Point point = new Point();
      this.mOffset = point;
      Configuration configuration = new Configuration();
      this.mConfiguration = configuration;
      if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
        if (rootConfig != null) {
          configuration.setTo(rootConfig);
        }
        this.mIsActivityRootLeash = isActivityRootLeash;
      }
      this.mDisplayId = displayId;
      this.mLeash = leash;
      point.set(offsetLeft, offsetTop);
    }

    private Root(Parcel in) {
      Point point = new Point();
      this.mOffset = point;
      Configuration configuration = new Configuration();
      this.mConfiguration = configuration;
      this.mDisplayId = in.readInt();
      SurfaceControl surfaceControl = new SurfaceControl();
      this.mLeash = surfaceControl;
      surfaceControl.readFromParcel(in);
      surfaceControl.setUnreleasedWarningCallSite("TransitionInfo.Root");
      point.readFromParcel(in);
      if (CoreRune.MW_SHELL_TRANSITION) {
        configuration.readFromParcel(in);
        this.mIsActivityRootLeash = in.readBoolean();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Root localRemoteCopy() {
      if (CoreRune.MW_SHELL_TRANSITION) {
        return new Root(
            this.mDisplayId,
            new SurfaceControl(this.mLeash, "localRemote"),
            this.mOffset.f85x,
            this.mOffset.f86y,
            this.mConfiguration,
            this.mIsActivityRootLeash);
      }
      return new Root(
          this.mDisplayId,
          new SurfaceControl(this.mLeash, "localRemote"),
          this.mOffset.f85x,
          this.mOffset.f86y);
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
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mDisplayId);
      this.mLeash.writeToParcel(dest, flags);
      this.mOffset.writeToParcel(dest, flags);
      if (CoreRune.MW_SHELL_TRANSITION) {
        this.mConfiguration.writeToParcel(dest, flags);
        dest.writeBoolean(this.mIsActivityRootLeash);
      }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    public String toString() {
      return this.mDisplayId + "@" + this.mOffset + ":" + this.mLeash;
    }
  }
}
