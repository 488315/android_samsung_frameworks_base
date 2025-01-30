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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Slog;
import android.view.DragAndDropPermissions;
import android.view.WindowManager;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.widget.ResolverDrawerLayout;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DropResolverActivity extends ResolverActivity {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public String mCallingPackage;
    public String mContentType;
    public DragAndDropPermissions mPermissions;
    public UserHandle mUserHandle;
    public int mWindowingMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TargetInfoWrapper implements TargetInfo {
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
        boolean z = DEBUG;
        if (z) {
            Slog.d("DropResolverActivity", "addPreferredActivity. intent=" + intent);
        }
        Intent intent2 = new Intent(intent);
        ComponentName component = intent2.getComponent();
        intent2.setComponent(null);
        UserHandle userHandle = this.mUserHandle;
        int identifier = userHandle != null ? userHandle.getIdentifier() : getUserId();
        if (z) {
            Slog.d("DropResolverActivity", "addPreferredActivity. userId=" + getUserId());
        }
        ComponentName[] componentNameArr2 = (ComponentName[]) packageManager.queryIntentActivitiesAsUser(intent2, 0, getUserId()).stream().filter(new DropResolverActivity$$ExternalSyntheticLambda0()).map(new DropResolverActivity$$ExternalSyntheticLambda1()).toArray(new DropResolverActivity$$ExternalSyntheticLambda2());
        if (z) {
            Slog.d("DropResolverActivity", "addPreferredActivity. set.length=" + componentNameArr2.length + " set=" + Arrays.asList(componentNameArr2));
        }
        packageManager.addPreferredActivityAsUser(intentFilter, i, componentNameArr2, component, identifier);
    }

    public final int appliedThemeResId() {
        return R.style.Theme.ExpandedMenu;
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
        String string = getResources().getString(17043316);
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.intent.extra.ALTERNATE_INTENTS");
        if (parcelableArrayExtra != null) {
            Intent[] intentArr3 = new Intent[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                Parcelable parcelable = parcelableArrayExtra[i];
                if (!(parcelable instanceof Intent)) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("EXTRA_ALTERNATE_INTENTS array entry #", i, " is not an Intent: ");
                    m1m.append(parcelableArrayExtra[i]);
                    Log.w("DropResolverActivity", m1m.toString());
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
            getWindow().setFlags(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
        }
        super.onCreate(bundle, intent2, string, intentArr, parcelableArrayListExtra, intent.getBooleanExtra("dropResolverActivity.extra.supportsAlwaysUseOption", false));
        ResolverDrawerLayout findViewById = findViewById(R.id.content_preview_image_3_small);
        if (findViewById != null) {
            if (this.mWindowingMode == 5) {
                ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(com.android.systemui.R.color.drop_resolver_freeform_background, null));
                colorDrawable.setColorFilter(new PorterDuffColorFilter(getColor(com.android.systemui.R.color.drop_resolver_background), PorterDuff.Mode.SRC_OVER));
                findViewById.setBackground(colorDrawable);
            } else {
                findViewById.setBackgroundColor(getColor(com.android.systemui.R.color.drop_resolver_background));
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
            String packageName = component != null ? component.getPackageName() : resolvedIntent.getPackage();
            CoreSaLogger.logForAdvanced("1042", this.mContentType + "," + this.mCallingPackage + "," + packageName);
            if (DEBUG) {
                Slog.d("DropResolverActivity", "safelyStartActivity: SALogging... contentType=" + this.mContentType + ", callingPackage=" + this.mCallingPackage + ", calleePackage=" + packageName);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
