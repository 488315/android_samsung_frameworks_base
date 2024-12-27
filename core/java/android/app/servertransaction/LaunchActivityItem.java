package android.app.servertransaction;

import android.app.ActivityClient;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.IActivityClientController;
import android.app.ProfilerInfo;
import android.app.ResultInfo;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Trace;
import android.window.ActivityWindowInfo;

import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.ReferrerIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LaunchActivityItem extends ClientTransactionItem {
    public static final Parcelable.Creator<LaunchActivityItem> CREATOR =
            new Parcelable.Creator<LaunchActivityItem>() { // from class:
                // android.app.servertransaction.LaunchActivityItem.1
                @Override // android.os.Parcelable.Creator
                public LaunchActivityItem createFromParcel(Parcel in) {
                    return new LaunchActivityItem(in);
                }

                @Override // android.os.Parcelable.Creator
                public LaunchActivityItem[] newArray(int size) {
                    return new LaunchActivityItem[size];
                }
            };
    private IActivityClientController mActivityClientController;
    private IBinder mActivityToken;
    private ActivityWindowInfo mActivityWindowInfo;
    private IBinder mAssistToken;
    private Configuration mCurConfig;
    private int mDeviceId;
    private int mIdent;
    private ActivityInfo mInfo;
    private IBinder mInitialCallerInfoAccessToken;
    private Intent mIntent;
    private boolean mIsForward;
    private boolean mLaunchedFromBubble;
    private Configuration mOverrideConfig;
    private List<ReferrerIntent> mPendingNewIntents;
    private List<ResultInfo> mPendingResults;
    private PersistableBundle mPersistentState;
    private int mProcState;
    private ProfilerInfo mProfilerInfo;
    private String mReferrer;
    private ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private IBinder mShareableActivityToken;
    private Bundle mState;
    private IBinder mTaskFragmentToken;
    private IVoiceInteractor mVoiceInteractor;

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler client) {
        client.countLaunchingActivities(1);
        client.updateProcessState(this.mProcState, false);
        CompatibilityInfo.applyOverrideScaleIfNeeded(this.mCurConfig);
        CompatibilityInfo.applyOverrideScaleIfNeeded(this.mOverrideConfig);
        client.updatePendingConfiguration(this.mCurConfig);
        if (this.mActivityClientController != null) {
            ActivityClient.setActivityClientController(this.mActivityClientController);
        }
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        Trace.traceBegin(64L, "activityStart");
        ActivityThread.ActivityClientRecord r =
                new ActivityThread.ActivityClientRecord(
                        this.mActivityToken,
                        this.mIntent,
                        this.mIdent,
                        this.mInfo,
                        this.mOverrideConfig,
                        this.mReferrer,
                        this.mVoiceInteractor,
                        this.mState,
                        this.mPersistentState,
                        this.mPendingResults,
                        this.mPendingNewIntents,
                        this.mSceneTransitionInfo,
                        this.mIsForward,
                        this.mProfilerInfo,
                        client,
                        this.mAssistToken,
                        this.mShareableActivityToken,
                        this.mLaunchedFromBubble,
                        this.mTaskFragmentToken,
                        this.mInitialCallerInfoAccessToken,
                        this.mActivityWindowInfo);
        client.handleLaunchActivity(r, pendingActions, this.mDeviceId, null);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(
            ClientTransactionHandler client, PendingTransactionActions pendingActions) {
        client.countLaunchingActivities(-1);
    }

    private LaunchActivityItem() {}

    public static LaunchActivityItem obtain(
            IBinder activityToken,
            Intent intent,
            int ident,
            ActivityInfo info,
            Configuration curConfig,
            Configuration overrideConfig,
            int deviceId,
            String referrer,
            IVoiceInteractor voiceInteractor,
            int procState,
            Bundle state,
            PersistableBundle persistentState,
            List<ResultInfo> pendingResults,
            List<ReferrerIntent> pendingNewIntents,
            ActivityOptions.SceneTransitionInfo sceneTransitionInfo,
            boolean isForward,
            ProfilerInfo profilerInfo,
            IBinder assistToken,
            IActivityClientController activityClientController,
            IBinder shareableActivityToken,
            boolean launchedFromBubble,
            IBinder taskFragmentToken,
            IBinder initialCallerInfoAccessToken,
            ActivityWindowInfo activityWindowInfo) {
        Bundle bundle;
        LaunchActivityItem instance =
                (LaunchActivityItem) ObjectPool.obtain(LaunchActivityItem.class);
        if (instance == null) {
            instance = new LaunchActivityItem();
        }
        Intent intent2 = new Intent(intent);
        ActivityInfo activityInfo = new ActivityInfo(info);
        Configuration configuration = new Configuration(curConfig);
        Configuration configuration2 = new Configuration(overrideConfig);
        if (state != null) {
            bundle = new Bundle(state);
        } else {
            bundle = null;
        }
        setValues(
                instance,
                activityToken,
                intent2,
                ident,
                activityInfo,
                configuration,
                configuration2,
                deviceId,
                referrer,
                voiceInteractor,
                procState,
                bundle,
                persistentState != null ? new PersistableBundle(persistentState) : null,
                pendingResults != null ? new ArrayList(pendingResults) : null,
                pendingNewIntents != null ? new ArrayList(pendingNewIntents) : null,
                sceneTransitionInfo,
                isForward,
                profilerInfo != null ? new ProfilerInfo(profilerInfo) : null,
                assistToken,
                activityClientController,
                shareableActivityToken,
                launchedFromBubble,
                taskFragmentToken,
                initialCallerInfoAccessToken,
                new ActivityWindowInfo(activityWindowInfo));
        return instance;
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        setValues(
                this, null, null, 0, null, null, null, 0, null, null, 0, null, null, null, null,
                null, false, null, null, null, null, false, null, null, null);
        ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mActivityToken);
        dest.writeTypedObject(this.mIntent, flags);
        dest.writeInt(this.mIdent);
        dest.writeTypedObject(this.mInfo, flags);
        dest.writeTypedObject(this.mCurConfig, flags);
        dest.writeTypedObject(this.mOverrideConfig, flags);
        dest.writeInt(this.mDeviceId);
        dest.writeString(this.mReferrer);
        dest.writeStrongInterface(this.mVoiceInteractor);
        dest.writeInt(this.mProcState);
        dest.writeBundle(this.mState);
        dest.writePersistableBundle(this.mPersistentState);
        dest.writeTypedList(this.mPendingResults, flags);
        dest.writeTypedList(this.mPendingNewIntents, flags);
        dest.writeTypedObject(this.mSceneTransitionInfo, flags);
        dest.writeBoolean(this.mIsForward);
        dest.writeTypedObject(this.mProfilerInfo, flags);
        dest.writeStrongBinder(this.mAssistToken);
        dest.writeStrongInterface(this.mActivityClientController);
        dest.writeStrongBinder(this.mShareableActivityToken);
        dest.writeBoolean(this.mLaunchedFromBubble);
        dest.writeStrongBinder(this.mTaskFragmentToken);
        dest.writeStrongBinder(this.mInitialCallerInfoAccessToken);
        dest.writeTypedObject(this.mActivityWindowInfo, flags);
    }

    private LaunchActivityItem(Parcel in) {
        setValues(
                this,
                in.readStrongBinder(),
                (Intent) in.readTypedObject(Intent.CREATOR),
                in.readInt(),
                (ActivityInfo) in.readTypedObject(ActivityInfo.CREATOR),
                (Configuration) in.readTypedObject(Configuration.CREATOR),
                (Configuration) in.readTypedObject(Configuration.CREATOR),
                in.readInt(),
                in.readString(),
                IVoiceInteractor.Stub.asInterface(in.readStrongBinder()),
                in.readInt(),
                in.readBundle(getClass().getClassLoader()),
                in.readPersistableBundle(getClass().getClassLoader()),
                in.createTypedArrayList(ResultInfo.CREATOR),
                in.createTypedArrayList(ReferrerIntent.CREATOR),
                (ActivityOptions.SceneTransitionInfo)
                        in.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR),
                in.readBoolean(),
                (ProfilerInfo) in.readTypedObject(ProfilerInfo.CREATOR),
                in.readStrongBinder(),
                IActivityClientController.Stub.asInterface(in.readStrongBinder()),
                in.readStrongBinder(),
                in.readBoolean(),
                in.readStrongBinder(),
                in.readStrongBinder(),
                (ActivityWindowInfo) in.readTypedObject(ActivityWindowInfo.CREATOR));
    }

    public boolean equals(Object o) {
        boolean intentsEqual;
        boolean z;
        boolean z2;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LaunchActivityItem other = (LaunchActivityItem) o;
        if ((this.mIntent == null && other.mIntent == null)
                || (this.mIntent != null && this.mIntent.filterEquals(other.mIntent))) {
            intentsEqual = true;
        } else {
            intentsEqual = false;
        }
        if (intentsEqual
                && Objects.equals(this.mActivityToken, other.mActivityToken)
                && this.mIdent == other.mIdent
                && activityInfoEqual(other.mInfo)
                && Objects.equals(this.mCurConfig, other.mCurConfig)
                && Objects.equals(this.mOverrideConfig, other.mOverrideConfig)
                && this.mDeviceId == other.mDeviceId
                && Objects.equals(this.mReferrer, other.mReferrer)
                && this.mProcState == other.mProcState
                && areBundlesEqualRoughly(this.mState, other.mState)
                && areBundlesEqualRoughly(this.mPersistentState, other.mPersistentState)
                && Objects.equals(this.mPendingResults, other.mPendingResults)
                && Objects.equals(this.mPendingNewIntents, other.mPendingNewIntents)) {
            if (this.mSceneTransitionInfo == null) {
                z = true;
            } else {
                z = false;
            }
            if (other.mSceneTransitionInfo == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z == z2
                    && this.mIsForward == other.mIsForward
                    && Objects.equals(this.mProfilerInfo, other.mProfilerInfo)
                    && Objects.equals(this.mAssistToken, other.mAssistToken)
                    && Objects.equals(this.mShareableActivityToken, other.mShareableActivityToken)
                    && Objects.equals(this.mTaskFragmentToken, other.mTaskFragmentToken)
                    && Objects.equals(
                            this.mInitialCallerInfoAccessToken, other.mInitialCallerInfoAccessToken)
                    && Objects.equals(this.mActivityWindowInfo, other.mActivityWindowInfo)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((((17 * 31)
                                                                                                                                                                                                                                                                                                                                + Objects
                                                                                                                                                                                                                                                                                                                                        .hashCode(
                                                                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                                                                        .mActivityToken))
                                                                                                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                                                                                                + this
                                                                                                                                                                                                                                                                                                                        .mIntent
                                                                                                                                                                                                                                                                                                                        .filterHashCode())
                                                                                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                                                                                + this
                                                                                                                                                                                                                                                                                                        .mIdent)
                                                                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                                                                + Objects
                                                                                                                                                                                                                                                                                        .hashCode(
                                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                                        .mCurConfig))
                                                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                                                + Objects
                                                                                                                                                                                                                                                                        .hashCode(
                                                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                                                        .mOverrideConfig))
                                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                                + this
                                                                                                                                                                                                                                                        .mDeviceId)
                                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                                + Objects
                                                                                                                                                                                                                                        .hashCode(
                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                        .mReferrer))
                                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                                + Objects
                                                                                                                                                                                                                        .hashCode(
                                                                                                                                                                                                                                Integer
                                                                                                                                                                                                                                        .valueOf(
                                                                                                                                                                                                                                                this
                                                                                                                                                                                                                                                        .mProcState)))
                                                                                                                                                                                                        * 31)
                                                                                                                                                                                                + getRoughBundleHashCode(
                                                                                                                                                                                                        this
                                                                                                                                                                                                                .mState))
                                                                                                                                                                                        * 31)
                                                                                                                                                                                + getRoughBundleHashCode(
                                                                                                                                                                                        this
                                                                                                                                                                                                .mPersistentState))
                                                                                                                                                                        * 31)
                                                                                                                                                                + Objects
                                                                                                                                                                        .hashCode(
                                                                                                                                                                                this
                                                                                                                                                                                        .mPendingResults))
                                                                                                                                                        * 31)
                                                                                                                                                + Objects
                                                                                                                                                        .hashCode(
                                                                                                                                                                this
                                                                                                                                                                        .mPendingNewIntents))
                                                                                                                                        * 31)
                                                                                                                                + (this
                                                                                                                                                        .mSceneTransitionInfo
                                                                                                                                                != null
                                                                                                                                        ? 1
                                                                                                                                        : 0))
                                                                                                                        * 31)
                                                                                                                + (this
                                                                                                                                .mIsForward
                                                                                                                        ? 1
                                                                                                                        : 0))
                                                                                                        * 31)
                                                                                                + Objects
                                                                                                        .hashCode(
                                                                                                                this
                                                                                                                        .mProfilerInfo))
                                                                                        * 31)
                                                                                + Objects.hashCode(
                                                                                        this
                                                                                                .mAssistToken))
                                                                        * 31)
                                                                + Objects.hashCode(
                                                                        this
                                                                                .mShareableActivityToken))
                                                        * 31)
                                                + Objects.hashCode(this.mTaskFragmentToken))
                                        * 31)
                                + Objects.hashCode(this.mInitialCallerInfoAccessToken))
                        * 31)
                + Objects.hashCode(this.mActivityWindowInfo);
    }

    private boolean activityInfoEqual(ActivityInfo other) {
        return this.mInfo == null
                ? other == null
                : other != null
                        && this.mInfo.flags == other.flags
                        && this.mInfo.getMaxAspectRatio() == other.getMaxAspectRatio()
                        && Objects.equals(this.mInfo.launchToken, other.launchToken)
                        && Objects.equals(this.mInfo.getComponentName(), other.getComponentName());
    }

    private static int getRoughBundleHashCode(BaseBundle bundle) {
        return (bundle == null || bundle.isDefinitelyEmpty()) ? 0 : 1;
    }

    private static boolean areBundlesEqualRoughly(BaseBundle a, BaseBundle b) {
        return getRoughBundleHashCode(a) == getRoughBundleHashCode(b);
    }

    public String toString() {
        return "LaunchActivityItem{activityToken="
                + this.mActivityToken
                + ",intent="
                + this.mIntent
                + ",ident="
                + this.mIdent
                + ",info="
                + this.mInfo
                + ",curConfig="
                + this.mCurConfig
                + ",overrideConfig="
                + this.mOverrideConfig
                + ",deviceId="
                + this.mDeviceId
                + ",referrer="
                + this.mReferrer
                + ",procState="
                + this.mProcState
                + ",state="
                + this.mState
                + ",persistentState="
                + this.mPersistentState
                + ",pendingResults="
                + this.mPendingResults
                + ",pendingNewIntents="
                + this.mPendingNewIntents
                + ",sceneTransitionInfo="
                + this.mSceneTransitionInfo
                + ",profilerInfo="
                + this.mProfilerInfo
                + ",assistToken="
                + this.mAssistToken
                + ",shareableActivityToken="
                + this.mShareableActivityToken
                + ",activityWindowInfo="
                + this.mActivityWindowInfo
                + "}";
    }

    private static void setValues(
            LaunchActivityItem instance,
            IBinder activityToken,
            Intent intent,
            int ident,
            ActivityInfo info,
            Configuration curConfig,
            Configuration overrideConfig,
            int deviceId,
            String referrer,
            IVoiceInteractor voiceInteractor,
            int procState,
            Bundle state,
            PersistableBundle persistentState,
            List<ResultInfo> pendingResults,
            List<ReferrerIntent> pendingNewIntents,
            ActivityOptions.SceneTransitionInfo sceneTransitionInfo,
            boolean isForward,
            ProfilerInfo profilerInfo,
            IBinder assistToken,
            IActivityClientController activityClientController,
            IBinder shareableActivityToken,
            boolean launchedFromBubble,
            IBinder taskFragmentToken,
            IBinder initialCallerInfoAccessToken,
            ActivityWindowInfo activityWindowInfo) {
        instance.mActivityToken = activityToken;
        instance.mIntent = intent;
        instance.mIdent = ident;
        instance.mInfo = info;
        instance.mCurConfig = curConfig;
        instance.mOverrideConfig = overrideConfig;
        instance.mDeviceId = deviceId;
        instance.mReferrer = referrer;
        instance.mVoiceInteractor = voiceInteractor;
        instance.mProcState = procState;
        instance.mState = state;
        instance.mPersistentState = persistentState;
        instance.mPendingResults = pendingResults;
        instance.mPendingNewIntents = pendingNewIntents;
        instance.mSceneTransitionInfo = sceneTransitionInfo;
        instance.mIsForward = isForward;
        instance.mProfilerInfo = profilerInfo;
        instance.mAssistToken = assistToken;
        instance.mActivityClientController = activityClientController;
        instance.mShareableActivityToken = shareableActivityToken;
        instance.mLaunchedFromBubble = launchedFromBubble;
        instance.mTaskFragmentToken = taskFragmentToken;
        instance.mInitialCallerInfoAccessToken = initialCallerInfoAccessToken;
        instance.mActivityWindowInfo = activityWindowInfo;
    }
}
