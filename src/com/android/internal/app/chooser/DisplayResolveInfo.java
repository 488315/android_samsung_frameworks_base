package com.android.internal.app.chooser;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class DisplayResolveInfo implements TargetInfo, Parcelable {
    public static final Parcelable.Creator<DisplayResolveInfo> CREATOR = new Parcelable.Creator<DisplayResolveInfo>() { // from class: com.android.internal.app.chooser.DisplayResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayResolveInfo createFromParcel(Parcel parcel) {
            return new DisplayResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayResolveInfo[] newArray(int i) {
            return new DisplayResolveInfo[i];
        }
    };
    private Drawable mDisplayIcon;
    private CharSequence mDisplayLabel;
    private CharSequence mExtendedInfo;
    private boolean mIsSkipFixUris;
    private boolean mIsSuspended;
    private boolean mPinned;
    private final ResolveInfo mResolveInfo;
    private ResolverListAdapter.ResolveInfoPresentationGetter mResolveInfoPresentationGetter;
    private final Intent mResolvedIntent;
    private ArrayList<DisplayResolveInfo> mSimilarList;
    private final List<Intent> mSourceIntents;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisplayResolveInfo(Intent intent, ResolveInfo resolveInfo, Intent intent2, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        this(intent, resolveInfo, null, null, intent2, resolveInfoPresentationGetter);
    }

    public DisplayResolveInfo(Intent intent, ResolveInfo resolveInfo, CharSequence charSequence, CharSequence charSequence2, Intent intent2, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        ArrayList arrayList = new ArrayList();
        this.mSourceIntents = arrayList;
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        arrayList.add(intent);
        this.mResolveInfo = resolveInfo;
        this.mDisplayLabel = charSequence;
        this.mExtendedInfo = charSequence2;
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
        Intent intent3 = new Intent(intent2);
        intent3.addFlags(50331648);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        intent3.setComponent(new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
        this.mIsSuspended = (activityInfo.applicationInfo.flags & 1073741824) != 0;
        this.mResolvedIntent = intent3;
    }

    private DisplayResolveInfo(DisplayResolveInfo displayResolveInfo, Intent intent, int i, ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter) {
        ArrayList arrayList = new ArrayList();
        this.mSourceIntents = arrayList;
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        arrayList.addAll(displayResolveInfo.getAllSourceIntents());
        this.mResolveInfo = displayResolveInfo.mResolveInfo;
        this.mDisplayLabel = displayResolveInfo.mDisplayLabel;
        this.mDisplayIcon = displayResolveInfo.mDisplayIcon;
        this.mExtendedInfo = displayResolveInfo.mExtendedInfo;
        Intent intent2 = new Intent(displayResolveInfo.mResolvedIntent);
        this.mResolvedIntent = intent2;
        intent2.fillIn(intent, i);
        this.mResolveInfoPresentationGetter = resolveInfoPresentationGetter;
    }

    DisplayResolveInfo(DisplayResolveInfo displayResolveInfo) {
        ArrayList arrayList = new ArrayList();
        this.mSourceIntents = arrayList;
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        arrayList.addAll(displayResolveInfo.getAllSourceIntents());
        this.mResolveInfo = displayResolveInfo.mResolveInfo;
        this.mDisplayLabel = displayResolveInfo.mDisplayLabel;
        this.mDisplayIcon = displayResolveInfo.mDisplayIcon;
        this.mExtendedInfo = displayResolveInfo.mExtendedInfo;
        this.mResolvedIntent = displayResolveInfo.mResolvedIntent;
        this.mResolveInfoPresentationGetter = displayResolveInfo.mResolveInfoPresentationGetter;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getDisplayLabel() {
        ResolverListAdapter.ResolveInfoPresentationGetter resolveInfoPresentationGetter;
        if (this.mDisplayLabel == null && (resolveInfoPresentationGetter = this.mResolveInfoPresentationGetter) != null) {
            this.mDisplayLabel = resolveInfoPresentationGetter.getLabel();
            this.mExtendedInfo = this.mResolveInfoPresentationGetter.getSubLabel();
        }
        return this.mDisplayLabel;
    }

    public boolean hasDisplayLabel() {
        return this.mDisplayLabel != null;
    }

    public void setDisplayLabel(CharSequence charSequence) {
        this.mDisplayLabel = charSequence;
    }

    public void setExtendedInfo(CharSequence charSequence) {
        this.mExtendedInfo = charSequence;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public Drawable getDisplayIcon(Context context) {
        return this.mDisplayIcon;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public TargetInfo cloneFilledIn(Intent intent, int i) {
        return new DisplayResolveInfo(this, intent, i, this.mResolveInfoPresentationGetter);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public List<Intent> getAllSourceIntents() {
        return this.mSourceIntents;
    }

    public void addAlternateSourceIntent(Intent intent) {
        this.mSourceIntents.add(intent);
    }

    public void setDisplayIcon(Drawable drawable) {
        this.mDisplayIcon = drawable;
    }

    public boolean hasDisplayIcon() {
        return this.mDisplayIcon != null;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getExtendedInfo() {
        return this.mExtendedInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public Intent getResolvedIntent() {
        return this.mResolvedIntent;
    }

    public ArrayList<DisplayResolveInfo> getSimilarList() {
        return this.mSimilarList;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ComponentName getResolvedComponentName() {
        return new ComponentName(this.mResolveInfo.activityInfo.packageName, this.mResolveInfo.activityInfo.name);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(Activity activity, Bundle bundle) {
        activity.startActivity(this.mResolvedIntent, bundle);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int i) {
        if (!this.mIsSkipFixUris) {
            TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, i);
        }
        TargetInfo.refreshIntentCreatorToken(this.mResolvedIntent);
        resolverActivity.startActivityAsCaller(this.mResolvedIntent, bundle, false, i);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
        if (!this.mIsSkipFixUris) {
            TargetInfo.prepareIntentForCrossProfileLaunch(this.mResolvedIntent, userHandle.getIdentifier());
        }
        TargetInfo.refreshIntentCreatorToken(this.mResolvedIntent);
        activity.startActivityAsUser(this.mResolvedIntent, bundle, userHandle);
        return false;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return this.mPinned;
    }

    public void setPinned(boolean z) {
        this.mPinned = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mDisplayLabel);
        parcel.writeCharSequence(this.mExtendedInfo);
        parcel.writeParcelable(this.mResolvedIntent, 0);
        parcel.writeTypedList(this.mSourceIntents);
        parcel.writeBoolean(this.mIsSuspended);
        parcel.writeBoolean(this.mPinned);
        parcel.writeParcelable(this.mResolveInfo, 0);
    }

    private DisplayResolveInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mSourceIntents = arrayList;
        this.mPinned = false;
        this.mSimilarList = new ArrayList<>();
        this.mIsSkipFixUris = false;
        this.mDisplayLabel = parcel.readCharSequence();
        this.mExtendedInfo = parcel.readCharSequence();
        this.mResolvedIntent = (Intent) parcel.readParcelable(null, Intent.class);
        parcel.readTypedList(arrayList, Intent.CREATOR);
        this.mIsSuspended = parcel.readBoolean();
        this.mPinned = parcel.readBoolean();
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable(null, ResolveInfo.class);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public void setSkipFixUris(boolean z) {
        this.mIsSkipFixUris = true;
    }
}
