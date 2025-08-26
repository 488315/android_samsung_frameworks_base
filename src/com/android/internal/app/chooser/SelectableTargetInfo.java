package com.android.internal.app.chooser;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.service.chooser.ChooserTarget;
import android.text.SpannableStringBuilder;
import android.util.Log;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverListAdapter;
import com.android.internal.app.SimpleIconFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class SelectableTargetInfo implements ChooserTargetInfo {
    private static final String TAG = "SelectableTargetInfo";
    private final ResolveInfo mBackupResolveInfo;
    private CharSequence mBadgeContentDescription;
    private Drawable mBadgeIcon;
    private final ChooserTarget mChooserTarget;
    private final Context mContext;
    private Drawable mDisplayIcon;
    private final String mDisplayLabel;
    private final int mFillInFlags;
    private final Intent mFillInIntent;
    private final boolean mIsPinned;
    private boolean mIsSuspended;
    private final float mModifiedScore;
    private final PackageManager mPm;
    private final SelectableTargetInfoCommunicator mSelectableTargetInfoCommunicator;
    private ShortcutInfo mShortcutInfo;
    private final DisplayResolveInfo mSourceInfo;

    public interface SelectableTargetInfoCommunicator {
        Intent getReferrerFillInIntent();

        Intent getTargetIntent();

        ResolverListAdapter.ActivityInfoPresentationGetter makePresentationGetter(ActivityInfo activityInfo);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getExtendedInfo() {
        return null;
    }

    public SelectableTargetInfo(Context context, DisplayResolveInfo displayResolveInfo, ChooserTarget chooserTarget, float f, SelectableTargetInfoCommunicator selectableTargetInfoCommunicator, ShortcutInfo shortcutInfo) {
        ResolveInfo resolveInfo;
        ActivityInfo activityInfo;
        this.mBadgeIcon = null;
        this.mIsSuspended = false;
        this.mContext = context;
        this.mSourceInfo = displayResolveInfo;
        this.mChooserTarget = chooserTarget;
        this.mModifiedScore = f;
        this.mPm = context.getPackageManager();
        this.mSelectableTargetInfoCommunicator = selectableTargetInfoCommunicator;
        this.mShortcutInfo = shortcutInfo;
        this.mIsPinned = shortcutInfo != null && shortcutInfo.isPinned();
        if (displayResolveInfo != null && (resolveInfo = displayResolveInfo.getResolveInfo()) != null && (activityInfo = resolveInfo.activityInfo) != null && activityInfo.applicationInfo != null) {
            PackageManager packageManager = context.getPackageManager();
            this.mBadgeIcon = packageManager.getApplicationIcon(activityInfo.applicationInfo);
            this.mBadgeContentDescription = packageManager.getApplicationLabel(activityInfo.applicationInfo);
            this.mIsSuspended = (activityInfo.applicationInfo.flags & 1073741824) != 0;
        }
        if (displayResolveInfo != null) {
            this.mBackupResolveInfo = null;
        } else {
            this.mBackupResolveInfo = context.getPackageManager().resolveActivity(getResolvedIntent(), 0);
        }
        this.mFillInIntent = null;
        this.mFillInFlags = 0;
        this.mDisplayLabel = sanitizeDisplayLabel(chooserTarget.getTitle());
    }

    private SelectableTargetInfo(SelectableTargetInfo selectableTargetInfo, Intent intent, int i) {
        this.mBadgeIcon = null;
        this.mIsSuspended = false;
        this.mContext = selectableTargetInfo.mContext;
        this.mPm = selectableTargetInfo.mPm;
        this.mSelectableTargetInfoCommunicator = selectableTargetInfo.mSelectableTargetInfoCommunicator;
        this.mSourceInfo = selectableTargetInfo.mSourceInfo;
        this.mBackupResolveInfo = selectableTargetInfo.mBackupResolveInfo;
        ChooserTarget chooserTarget = selectableTargetInfo.mChooserTarget;
        this.mChooserTarget = chooserTarget;
        this.mBadgeIcon = selectableTargetInfo.mBadgeIcon;
        this.mBadgeContentDescription = selectableTargetInfo.mBadgeContentDescription;
        synchronized (selectableTargetInfo) {
            this.mShortcutInfo = selectableTargetInfo.mShortcutInfo;
            this.mDisplayIcon = selectableTargetInfo.mDisplayIcon;
        }
        this.mFillInIntent = intent;
        this.mFillInFlags = i;
        this.mModifiedScore = selectableTargetInfo.mModifiedScore;
        this.mIsPinned = selectableTargetInfo.mIsPinned;
        this.mDisplayLabel = sanitizeDisplayLabel(chooserTarget.getTitle());
    }

    private String sanitizeDisplayLabel(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        spannableStringBuilder.clearSpans();
        return spannableStringBuilder.toString();
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isSuspended() {
        return this.mIsSuspended;
    }

    public DisplayResolveInfo getDisplayResolveInfo() {
        return this.mSourceInfo;
    }

    public boolean loadIcon() throws Throwable {
        ShortcutInfo shortcutInfo;
        Drawable drawable;
        synchronized (this) {
            shortcutInfo = this.mShortcutInfo;
            drawable = this.mDisplayIcon;
        }
        boolean z = drawable == null && shortcutInfo != null;
        if (!z) {
            return z;
        }
        Drawable chooserTargetIconDrawable = getChooserTargetIconDrawable(this.mChooserTarget, shortcutInfo);
        synchronized (this) {
            this.mDisplayIcon = chooserTargetIconDrawable;
            this.mShortcutInfo = null;
        }
        return z;
    }

    private Drawable getChooserTargetIconDrawable(ChooserTarget chooserTarget, ShortcutInfo shortcutInfo) throws Throwable {
        Drawable shortcutIconDrawable;
        ActivityInfo activityInfo;
        Icon icon = chooserTarget.getIcon();
        if (icon != null) {
            shortcutIconDrawable = icon.loadDrawable(this.mContext);
        } else {
            shortcutIconDrawable = shortcutInfo != null ? ((LauncherApps) this.mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getShortcutIconDrawable(shortcutInfo, 0) : null;
        }
        if (shortcutIconDrawable == null) {
            return null;
        }
        try {
            activityInfo = this.mPm.getActivityInfo(chooserTarget.getComponentName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "Could not find activity associated with ChooserTarget");
            activityInfo = null;
        }
        if (activityInfo == null) {
            return null;
        }
        Bitmap iconBitmap = this.mSelectableTargetInfoCommunicator.makePresentationGetter(activityInfo).getIconBitmap(null);
        SimpleIconFactory simpleIconFactoryObtain = SimpleIconFactory.obtain(this.mContext);
        Bitmap bitmapCreateAppBadgedIconBitmap = simpleIconFactoryObtain.createAppBadgedIconBitmap(shortcutIconDrawable, iconBitmap);
        simpleIconFactoryObtain.recycle();
        return new BitmapDrawable(this.mContext.getResources(), bitmapCreateAppBadgedIconBitmap);
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public float getModifiedScore() {
        return this.mModifiedScore;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public Intent getResolvedIntent() {
        DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
        if (displayResolveInfo != null) {
            return displayResolveInfo.getResolvedIntent();
        }
        Intent intent = new Intent(this.mSelectableTargetInfoCommunicator.getTargetIntent());
        intent.setComponent(this.mChooserTarget.getComponentName());
        intent.putExtras(this.mChooserTarget.getIntentExtras());
        return intent;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ComponentName getResolvedComponentName() {
        DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
        if (displayResolveInfo != null) {
            return displayResolveInfo.getResolvedComponentName();
        }
        if (this.mBackupResolveInfo != null) {
            return new ComponentName(this.mBackupResolveInfo.activityInfo.packageName, this.mBackupResolveInfo.activityInfo.name);
        }
        return null;
    }

    private Intent getBaseIntentToSend() {
        Intent resolvedIntent = getResolvedIntent();
        if (resolvedIntent == null) {
            Log.e(TAG, "ChooserTargetInfo: no base intent available to send");
            return resolvedIntent;
        }
        Intent intent = new Intent(resolvedIntent);
        Intent intent2 = this.mFillInIntent;
        if (intent2 != null) {
            intent.fillIn(intent2, this.mFillInFlags);
        }
        intent.fillIn(this.mSelectableTargetInfoCommunicator.getReferrerFillInIntent(), 0);
        return intent;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean start(Activity activity, Bundle bundle) {
        throw new RuntimeException("ChooserTargets should be started as caller.");
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int i) {
        Intent baseIntentToSend = getBaseIntentToSend();
        boolean z = false;
        if (baseIntentToSend == null) {
            return false;
        }
        baseIntentToSend.setComponent(this.mChooserTarget.getComponentName());
        baseIntentToSend.putExtras(this.mChooserTarget.getIntentExtras());
        TargetInfo.prepareIntentForCrossProfileLaunch(baseIntentToSend, i);
        TargetInfo.refreshIntentCreatorToken(baseIntentToSend);
        DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
        if (displayResolveInfo != null && displayResolveInfo.getResolvedComponentName().getPackageName().equals(this.mChooserTarget.getComponentName().getPackageName())) {
            z = true;
        }
        resolverActivity.startActivityAsCaller(baseIntentToSend, bundle, z, i);
        return true;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
        throw new RuntimeException("ChooserTargets should be started as caller.");
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public ResolveInfo getResolveInfo() {
        DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
        return displayResolveInfo != null ? displayResolveInfo.getResolveInfo() : this.mBackupResolveInfo;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public CharSequence getDisplayLabel() {
        return this.mDisplayLabel;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public synchronized Drawable getDisplayIcon(Context context) {
        return this.mDisplayIcon;
    }

    public synchronized boolean hasDisplayIcon() {
        return this.mDisplayIcon != null;
    }

    @Override // com.android.internal.app.chooser.ChooserTargetInfo
    public ChooserTarget getChooserTarget() {
        return this.mChooserTarget;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public TargetInfo cloneFilledIn(Intent intent, int i) {
        return new SelectableTargetInfo(this, intent, i);
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public List<Intent> getAllSourceIntents() {
        ArrayList arrayList = new ArrayList();
        DisplayResolveInfo displayResolveInfo = this.mSourceInfo;
        if (displayResolveInfo != null) {
            arrayList.add(displayResolveInfo.getAllSourceIntents().get(0));
        }
        return arrayList;
    }

    @Override // com.android.internal.app.chooser.TargetInfo
    public boolean isPinned() {
        return this.mIsPinned;
    }
}
