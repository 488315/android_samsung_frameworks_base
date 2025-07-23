package com.android.wm.shell.draganddrop;

import android.R;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.samsung.android.core.CoreSaLogger;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DropResolverActivity extends ResolverActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mCallingPackage;
    public String mContentType;
    public DragAndDropPermissions mPermissions;
    public UserHandle mUserHandle;
    public int mWindowingMode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TargetInfoWrapper implements TargetInfo {
        public final Bundle mOverrideActivityOptions;
        public final TargetInfo mTargetInfo;
        public final UserHandle mUserHandle;

        private TargetInfoWrapper(TargetInfo targetInfo, Bundle bundle, UserHandle userHandle) {
            this.mTargetInfo = targetInfo;
            this.mOverrideActivityOptions = bundle;
            this.mUserHandle = userHandle;
        }

        public static TargetInfoWrapper create(TargetInfo targetInfo, Bundle bundle, UserHandle userHandle) {
            return new TargetInfoWrapper(targetInfo, bundle, userHandle);
        }

        public final TargetInfo cloneFilledIn(Intent intent, int i) {
            return this.mTargetInfo.cloneFilledIn(intent, i);
        }

        public final List getAllSourceIntents() {
            return this.mTargetInfo.getAllSourceIntents();
        }

        public final Drawable getDisplayIcon(Context context) {
            return this.mTargetInfo.getDisplayIcon(context);
        }

        public final CharSequence getDisplayLabel() {
            return this.mTargetInfo.getDisplayLabel();
        }

        public final CharSequence getExtendedInfo() {
            return this.mTargetInfo.getExtendedInfo();
        }

        public final ResolveInfo getResolveInfo() {
            return this.mTargetInfo.getResolveInfo();
        }

        public final ComponentName getResolvedComponentName() {
            return this.mTargetInfo.getResolvedComponentName();
        }

        public final Intent getResolvedIntent() {
            return this.mTargetInfo.getResolvedIntent();
        }

        public final boolean isPinned() {
            return this.mTargetInfo.isPinned();
        }

        public final boolean isSuspended() {
            return this.mTargetInfo.isSuspended();
        }

        public final boolean start(Activity activity, Bundle bundle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mOverrideActivityOptions);
            return this.mTargetInfo.start(activity, bundle);
        }

        public final boolean startAsCaller(ResolverActivity resolverActivity, Bundle bundle, int i) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mOverrideActivityOptions);
            this.mTargetInfo.setSkipFixUris(true);
            return this.mTargetInfo.startAsCaller(resolverActivity, bundle, i);
        }

        public final boolean startAsUser(Activity activity, Bundle bundle, UserHandle userHandle) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putAll(this.mOverrideActivityOptions);
            this.mTargetInfo.setSkipFixUris(true);
            return this.mTargetInfo.startAsUser(activity, bundle, this.mUserHandle);
        }
    }

    public final void addPreferredActivity(PackageManager packageManager, IntentFilter intentFilter, int i, ComponentName[] componentNameArr, Intent intent) {
        Intent intent2 = new Intent(intent);
        ComponentName component = intent2.getComponent();
        intent2.setComponent(null);
        UserHandle userHandle = this.mUserHandle;
        packageManager.addPreferredActivityAsUser(intentFilter, i, (ComponentName[]) packageManager.queryIntentActivitiesAsUser(intent2, 0, getUserId()).stream().filter(new DropResolverActivity$$ExternalSyntheticLambda0()).map(new DropResolverActivity$$ExternalSyntheticLambda1()).toArray(new DropResolverActivity$$ExternalSyntheticLambda2()), component, userHandle != null ? userHandle.getIdentifier() : getUserId());
    }

    public final int appliedThemeResId() {
        return R.style.Theme.Material.Settings.Dialog.Alert;
    }

    public final void onCreate(Bundle bundle) {
        Intent[] intentArr;
        Intent intent = getIntent();
        Parcelable parcelableExtra = intent.getParcelableExtra("android.intent.extra.INTENT");
        this.mContentType = intent.getStringExtra("dropResolverActivity.extra.contentType");
        this.mCallingPackage = intent.getStringExtra("dropResolverActivity.extra.callingPackage");
        if (!(parcelableExtra instanceof Intent)) {
            Log.w("DropResolverActivity", "Target is not an intent: " + parcelableExtra);
            finish();
            super.onCreate((Bundle) null);
            return;
        }
        Intent intent2 = (Intent) parcelableExtra;
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("dropResolverActivity.extra.rlist");
        ArrayList parcelableArrayListExtra2 = intent.getParcelableArrayListExtra("android.intent.extra.INITIAL_INTENTS");
        if (parcelableArrayListExtra2 != null) {
            Intent[] intentArr2 = new Intent[parcelableArrayListExtra2.size()];
            parcelableArrayListExtra2.toArray(intentArr2);
            intentArr = intentArr2;
        } else {
            intentArr = null;
        }
        String string = getResources().getString(17043687);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.intent.extra.ALTERNATE_INTENTS");
        if (parcelableArrayExtra != null) {
            Intent[] intentArr3 = new Intent[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                Parcelable parcelable = parcelableArrayExtra[i];
                if (!(parcelable instanceof Intent)) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "EXTRA_ALTERNATE_INTENTS array entry #", " is not an Intent: ");
                    m.append(parcelableArrayExtra[i]);
                    Log.w("DropResolverActivity", m.toString());
                    finish();
                    super.onCreate((Bundle) null);
                    return;
                }
                intentArr3[i] = (Intent) parcelable;
            }
            setAdditionalTargets(intentArr3);
        }
        DragAndDropPermissions dragAndDropPermissions = (DragAndDropPermissions) intent.getParcelableExtra("dragPermission");
        this.mPermissions = dragAndDropPermissions;
        if (dragAndDropPermissions != null) {
            try {
                dragAndDropPermissions.takeTransient();
            } catch (Exception e) {
                Log.w("DropResolverActivity", "dnd permission failure");
                e.printStackTrace();
                finish();
            }
        }
        this.mWindowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
        UserHandle of = UserHandle.of(intent.getIntExtra("dropResolverActivity.extra.userid", getUserId()));
        this.mUserHandle = of;
        if (of.getIdentifier() != getUserId()) {
            intent2.prepareToLeaveUser(this.mUserHandle.getIdentifier());
        }
        if (intent.getBooleanExtra("dropResolverActivity.extra.wallpaper", false)) {
            setTranslucent(false);
            getWindow().setFlags(1048576, 1048576);
        }
        super.onCreate(bundle, intent2, string, intentArr, parcelableArrayListExtra, intent.getBooleanExtra("dropResolverActivity.extra.supportsAlwaysUseOption", false));
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            if (this.mWindowingMode == 5) {
                ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(com.android.systemui.R.color.drop_resolver_freeform_background, null));
                colorDrawable.setColorFilter(new PorterDuffColorFilter(getColor(com.android.systemui.R.color.drop_resolver_background), PorterDuff.Mode.SRC_OVER));
                decorView.setBackground(colorDrawable);
            } else {
                decorView.setBackgroundColor(getColor(com.android.systemui.R.color.drop_resolver_background));
            }
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.samsungFlags |= 16777216;
        attributes.layoutInDisplayCutoutMode = 3;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setSystemUiVisibility(1024);
    }

    public final void onDestroy() {
        super.onDestroy();
        DragAndDropPermissions dragAndDropPermissions = this.mPermissions;
        if (dragAndDropPermissions != null) {
            dragAndDropPermissions.release();
            Slog.d("DropResolverActivity", "Release permissions");
        }
    }

    public final void safelyStartActivity(TargetInfo targetInfo) {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setStartedFromWindowTypeLauncher(true);
        int i = this.mWindowingMode;
        if (i == 5) {
            makeBasic.setForceLaunchWindowingMode(i);
        }
        try {
            super.safelyStartActivity(TargetInfoWrapper.create(targetInfo, makeBasic.toBundle(), this.mUserHandle));
            Intent resolvedIntent = targetInfo.getResolvedIntent();
            ComponentName component = resolvedIntent.getComponent();
            CoreSaLogger.logForAdvanced("1042", this.mContentType + "," + this.mCallingPackage + "," + (component != null ? component.getPackageName() : resolvedIntent.getPackage()));
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
