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

/* loaded from: classes.dex */
public class LaunchActivityItem extends ClientTransactionItem {
    public static final Parcelable.Creator<LaunchActivityItem> CREATOR = new Parcelable.Creator<LaunchActivityItem>() { // from class: android.app.servertransaction.LaunchActivityItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LaunchActivityItem createFromParcel(Parcel parcel) {
            return new LaunchActivityItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LaunchActivityItem[] newArray(int i) {
            return new LaunchActivityItem[i];
        }
    };
    private final IActivityClientController mActivityClientController;
    private final IBinder mActivityToken;
    private final ActivityWindowInfo mActivityWindowInfo;
    private final IBinder mAssistToken;
    private final Configuration mCurConfig;
    private final int mDeviceId;
    private final int mIdent;
    private ActivityInfo mInfo;
    private final IBinder mInitialCallerInfoAccessToken;
    private Intent mIntent;
    private final boolean mIsForward;
    private final boolean mLaunchedFromBubble;
    private final Configuration mOverrideConfig;
    private final List<ReferrerIntent> mPendingNewIntents;
    private final List<ResultInfo> mPendingResults;
    private final PersistableBundle mPersistentState;
    private final int mProcState;
    private final ProfilerInfo mProfilerInfo;
    private final String mReferrer;
    private final ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private final IBinder mShareableActivityToken;
    private final Bundle mState;
    private final IBinder mTaskFragmentToken;
    private final IVoiceInteractor mVoiceInteractor;

    public LaunchActivityItem(IBinder iBinder, Intent intent, int i, ActivityInfo activityInfo, Configuration configuration, Configuration configuration2, int i2, String str, IVoiceInteractor iVoiceInteractor, int i3, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, ProfilerInfo profilerInfo, IBinder iBinder2, IActivityClientController iActivityClientController, IBinder iBinder3, boolean z2, IBinder iBinder4, IBinder iBinder5, ActivityWindowInfo activityWindowInfo) {
        this(iBinder, i, new Configuration(configuration), new Configuration(configuration2), i2, str, iVoiceInteractor, i3, bundle != null ? new Bundle(bundle) : null, persistableBundle != null ? new PersistableBundle(persistableBundle) : null, list != null ? new ArrayList(list) : null, list2 != null ? new ArrayList(list2) : null, sceneTransitionInfo, z, profilerInfo != null ? new ProfilerInfo(profilerInfo) : null, iBinder2, iActivityClientController, iBinder3, z2, iBinder4, iBinder5, new ActivityWindowInfo(activityWindowInfo));
        this.mIntent = new Intent(intent);
        this.mInfo = new ActivityInfo(activityInfo);
    }

    private LaunchActivityItem(IBinder iBinder, int i, Configuration configuration, Configuration configuration2, int i2, String str, IVoiceInteractor iVoiceInteractor, int i3, Bundle bundle, PersistableBundle persistableBundle, List<ResultInfo> list, List<ReferrerIntent> list2, ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, ProfilerInfo profilerInfo, IBinder iBinder2, IActivityClientController iActivityClientController, IBinder iBinder3, boolean z2, IBinder iBinder4, IBinder iBinder5, ActivityWindowInfo activityWindowInfo) {
        this.mActivityToken = iBinder;
        this.mIdent = i;
        this.mCurConfig = configuration;
        this.mOverrideConfig = configuration2;
        this.mDeviceId = i2;
        this.mReferrer = str;
        this.mVoiceInteractor = iVoiceInteractor;
        this.mProcState = i3;
        this.mState = bundle;
        this.mPersistentState = persistableBundle;
        this.mPendingResults = list;
        this.mPendingNewIntents = list2;
        this.mSceneTransitionInfo = sceneTransitionInfo;
        this.mIsForward = z;
        this.mProfilerInfo = profilerInfo;
        this.mAssistToken = iBinder2;
        this.mActivityClientController = iActivityClientController;
        this.mShareableActivityToken = iBinder3;
        this.mLaunchedFromBubble = z2;
        this.mTaskFragmentToken = iBinder4;
        this.mInitialCallerInfoAccessToken = iBinder5;
        this.mActivityWindowInfo = activityWindowInfo;
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void preExecute(ClientTransactionHandler clientTransactionHandler) {
        clientTransactionHandler.countLaunchingActivities(1);
        clientTransactionHandler.updateProcessState(this.mProcState, false);
        CompatibilityInfo.applyOverrideIfNeeded(this.mCurConfig);
        CompatibilityInfo.applyOverrideIfNeeded(this.mOverrideConfig);
        clientTransactionHandler.updatePendingConfiguration(this.mCurConfig);
        IActivityClientController iActivityClientController = this.mActivityClientController;
        if (iActivityClientController != null) {
            ActivityClient.setActivityClientController(iActivityClientController);
        }
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void execute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityStart");
        clientTransactionHandler.handleLaunchActivity(new ActivityThread.ActivityClientRecord(this.mActivityToken, this.mIntent, this.mIdent, this.mInfo, this.mOverrideConfig, this.mReferrer, this.mVoiceInteractor, this.mState, this.mPersistentState, this.mPendingResults, this.mPendingNewIntents, this.mSceneTransitionInfo, this.mIsForward, this.mProfilerInfo, clientTransactionHandler, this.mAssistToken, this.mShareableActivityToken, this.mLaunchedFromBubble, this.mTaskFragmentToken, this.mInitialCallerInfoAccessToken, this.mActivityWindowInfo), pendingTransactionActions, this.mDeviceId, null);
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.BaseClientRequest
    public void postExecute(ClientTransactionHandler clientTransactionHandler, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.countLaunchingActivities(-1);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mActivityToken);
        parcel.writeInt(this.mIdent);
        parcel.writeTypedObject(this.mCurConfig, i);
        parcel.writeTypedObject(this.mOverrideConfig, i);
        parcel.writeInt(this.mDeviceId);
        parcel.writeString(this.mReferrer);
        parcel.writeStrongInterface(this.mVoiceInteractor);
        parcel.writeInt(this.mProcState);
        parcel.writeBundle(this.mState);
        parcel.writePersistableBundle(this.mPersistentState);
        parcel.writeTypedList(this.mPendingResults, i);
        parcel.writeTypedList(this.mPendingNewIntents, i);
        parcel.writeTypedObject(this.mSceneTransitionInfo, i);
        parcel.writeBoolean(this.mIsForward);
        parcel.writeTypedObject(this.mProfilerInfo, i);
        parcel.writeStrongBinder(this.mAssistToken);
        parcel.writeStrongInterface(this.mActivityClientController);
        parcel.writeStrongBinder(this.mShareableActivityToken);
        parcel.writeBoolean(this.mLaunchedFromBubble);
        parcel.writeStrongBinder(this.mTaskFragmentToken);
        parcel.writeStrongBinder(this.mInitialCallerInfoAccessToken);
        parcel.writeTypedObject(this.mActivityWindowInfo, i);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mInfo, i);
    }

    private LaunchActivityItem(Parcel parcel) {
        this(parcel.readStrongBinder(), parcel.readInt(), (Configuration) Objects.requireNonNull((Configuration) parcel.readTypedObject(Configuration.CREATOR)), (Configuration) Objects.requireNonNull((Configuration) parcel.readTypedObject(Configuration.CREATOR)), parcel.readInt(), parcel.readString(), IVoiceInteractor.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readBundle(parcel.getClass().getClassLoader()), parcel.readPersistableBundle(parcel.getClass().getClassLoader()), parcel.createTypedArrayList(ResultInfo.CREATOR), parcel.createTypedArrayList(ReferrerIntent.CREATOR), (ActivityOptions.SceneTransitionInfo) parcel.readTypedObject(ActivityOptions.SceneTransitionInfo.CREATOR), parcel.readBoolean(), (ProfilerInfo) parcel.readTypedObject(ProfilerInfo.CREATOR), parcel.readStrongBinder(), IActivityClientController.Stub.asInterface(parcel.readStrongBinder()), parcel.readStrongBinder(), parcel.readBoolean(), parcel.readStrongBinder(), parcel.readStrongBinder(), (ActivityWindowInfo) Objects.requireNonNull((ActivityWindowInfo) parcel.readTypedObject(ActivityWindowInfo.CREATOR)));
        this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            LaunchActivityItem launchActivityItem = (LaunchActivityItem) obj;
            Intent intent = this.mIntent;
            if (((intent == null && launchActivityItem.mIntent == null) || (intent != null && intent.filterEquals(launchActivityItem.mIntent))) && Objects.equals(this.mActivityToken, launchActivityItem.mActivityToken) && this.mIdent == launchActivityItem.mIdent && activityInfoEqual(launchActivityItem.mInfo) && Objects.equals(this.mCurConfig, launchActivityItem.mCurConfig) && Objects.equals(this.mOverrideConfig, launchActivityItem.mOverrideConfig) && this.mDeviceId == launchActivityItem.mDeviceId && Objects.equals(this.mReferrer, launchActivityItem.mReferrer) && this.mProcState == launchActivityItem.mProcState && areBundlesEqualRoughly(this.mState, launchActivityItem.mState) && areBundlesEqualRoughly(this.mPersistentState, launchActivityItem.mPersistentState) && Objects.equals(this.mPendingResults, launchActivityItem.mPendingResults) && Objects.equals(this.mPendingNewIntents, launchActivityItem.mPendingNewIntents)) {
                if ((this.mSceneTransitionInfo == null) == (launchActivityItem.mSceneTransitionInfo == null) && this.mIsForward == launchActivityItem.mIsForward && Objects.equals(this.mProfilerInfo, launchActivityItem.mProfilerInfo) && Objects.equals(this.mAssistToken, launchActivityItem.mAssistToken) && Objects.equals(this.mShareableActivityToken, launchActivityItem.mShareableActivityToken) && Objects.equals(this.mTaskFragmentToken, launchActivityItem.mTaskFragmentToken) && Objects.equals(this.mInitialCallerInfoAccessToken, launchActivityItem.mInitialCallerInfoAccessToken) && Objects.equals(this.mActivityWindowInfo, launchActivityItem.mActivityWindowInfo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((527 + Objects.hashCode(this.mActivityToken)) * 31) + this.mIntent.filterHashCode()) * 31) + this.mIdent) * 31) + Objects.hashCode(this.mCurConfig)) * 31) + Objects.hashCode(this.mOverrideConfig)) * 31) + this.mDeviceId) * 31) + Objects.hashCode(this.mReferrer)) * 31) + Objects.hashCode(Integer.valueOf(this.mProcState))) * 31) + getRoughBundleHashCode(this.mState)) * 31) + getRoughBundleHashCode(this.mPersistentState)) * 31) + Objects.hashCode(this.mPendingResults)) * 31) + Objects.hashCode(this.mPendingNewIntents)) * 31) + (this.mSceneTransitionInfo != null ? 1 : 0)) * 31) + (this.mIsForward ? 1 : 0)) * 31) + Objects.hashCode(this.mProfilerInfo)) * 31) + Objects.hashCode(this.mAssistToken)) * 31) + Objects.hashCode(this.mShareableActivityToken)) * 31) + Objects.hashCode(this.mTaskFragmentToken)) * 31) + Objects.hashCode(this.mInitialCallerInfoAccessToken)) * 31) + Objects.hashCode(this.mActivityWindowInfo);
    }

    private boolean activityInfoEqual(ActivityInfo activityInfo) {
        ActivityInfo activityInfo2 = this.mInfo;
        return activityInfo2 == null ? activityInfo == null : activityInfo != null && activityInfo2.flags == activityInfo.flags && this.mInfo.getMaxAspectRatio() == activityInfo.getMaxAspectRatio() && Objects.equals(this.mInfo.launchToken, activityInfo.launchToken) && Objects.equals(this.mInfo.getComponentName(), activityInfo.getComponentName());
    }

    private static int getRoughBundleHashCode(BaseBundle baseBundle) {
        return (baseBundle == null || baseBundle.isDefinitelyEmpty()) ? 0 : 1;
    }

    private static boolean areBundlesEqualRoughly(BaseBundle baseBundle, BaseBundle baseBundle2) {
        return getRoughBundleHashCode(baseBundle) == getRoughBundleHashCode(baseBundle2);
    }

    public String toString() {
        return "LaunchActivityItem{activityToken=" + this.mActivityToken + ",intent=" + this.mIntent + ",ident=" + this.mIdent + ",info=" + this.mInfo + ",curConfig=" + this.mCurConfig + ",overrideConfig=" + this.mOverrideConfig + ",deviceId=" + this.mDeviceId + ",referrer=" + this.mReferrer + ",procState=" + this.mProcState + ",state=" + this.mState + ",persistentState=" + this.mPersistentState + ",pendingResults=" + this.mPendingResults + ",pendingNewIntents=" + this.mPendingNewIntents + ",sceneTransitionInfo=" + this.mSceneTransitionInfo + ",profilerInfo=" + this.mProfilerInfo + ",assistToken=" + this.mAssistToken + ",shareableActivityToken=" + this.mShareableActivityToken + ",activityWindowInfo=" + this.mActivityWindowInfo + "}";
    }
}
