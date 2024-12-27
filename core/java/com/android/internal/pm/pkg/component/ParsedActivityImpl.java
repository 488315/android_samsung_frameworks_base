package com.android.internal.pm.pkg.component;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class ParsedActivityImpl extends ParsedMainComponentImpl implements ParsedActivity, Parcelable {
    public static final Parcelable.Creator<ParsedActivityImpl> CREATOR = new Parcelable.Creator<ParsedActivityImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedActivityImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedActivityImpl createFromParcel(Parcel source) {
            return new ParsedActivityImpl(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParsedActivityImpl[] newArray(int size) {
            return new ParsedActivityImpl[size];
        }
    };
    private int colorMode;
    private int configChanges;
    private int documentLaunchMode;
    private int launchMode;
    private int lockTaskLaunchMode;
    private Set<String> mKnownActivityEmbeddingCerts;
    private int mRequireContentUriPermissionFromCaller;
    private String mRequiredDisplayCategory;
    private float maxAspectRatio;
    private int maxRecents;
    private float minAspectRatio;
    private String parentActivityName;
    private String permission;
    private int persistableMode;
    private int privateFlags;
    private String requestedVrComponent;
    private int resizeMode;
    private int rotationAnimation;
    private int screenOrientation;
    private int softInputMode;
    private boolean supportsSizeChanges;
    private String targetActivity;
    private String taskAffinity;
    private int theme;
    private int uiOptions;
    private ActivityInfo.WindowLayout windowLayout;

    public ParsedActivityImpl(ParsedActivityImpl other) {
        super(other);
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = other.theme;
        this.uiOptions = other.uiOptions;
        this.targetActivity = other.targetActivity;
        this.parentActivityName = other.parentActivityName;
        this.taskAffinity = other.taskAffinity;
        this.privateFlags = other.privateFlags;
        this.permission = other.permission;
        this.launchMode = other.launchMode;
        this.documentLaunchMode = other.documentLaunchMode;
        this.maxRecents = other.maxRecents;
        this.configChanges = other.configChanges;
        this.softInputMode = other.softInputMode;
        this.persistableMode = other.persistableMode;
        this.lockTaskLaunchMode = other.lockTaskLaunchMode;
        this.screenOrientation = other.screenOrientation;
        this.resizeMode = other.resizeMode;
        this.maxAspectRatio = other.maxAspectRatio;
        this.minAspectRatio = other.minAspectRatio;
        this.supportsSizeChanges = other.supportsSizeChanges;
        this.requestedVrComponent = other.requestedVrComponent;
        this.rotationAnimation = other.rotationAnimation;
        this.colorMode = other.colorMode;
        this.windowLayout = other.windowLayout;
        this.mKnownActivityEmbeddingCerts = other.mKnownActivityEmbeddingCerts;
        this.mRequiredDisplayCategory = other.mRequiredDisplayCategory;
        this.mRequireContentUriPermissionFromCaller = other.mRequireContentUriPermissionFromCaller;
    }

    public static ParsedActivityImpl makeAppDetailsActivity(String packageName, String processName, int uiOptions, String taskAffinity, boolean hardwareAccelerated) {
        ParsedActivityImpl activity = new ParsedActivityImpl();
        activity.setPackageName(packageName);
        activity.theme = 16973909;
        activity.setExported(true);
        activity.setName(PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME);
        activity.setProcessName(processName);
        activity.uiOptions = uiOptions;
        activity.taskAffinity = taskAffinity;
        activity.launchMode = 0;
        activity.documentLaunchMode = 0;
        activity.maxRecents = ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        activity.configChanges = ParsedActivityUtils.getActivityConfigChanges(0, 0);
        activity.softInputMode = 0;
        activity.persistableMode = 1;
        activity.screenOrientation = -1;
        activity.resizeMode = 4;
        activity.lockTaskLaunchMode = 0;
        activity.setDirectBootAware(false);
        activity.rotationAnimation = -1;
        activity.colorMode = 0;
        if (hardwareAccelerated) {
            activity.setFlags(activity.getFlags() | 512);
        }
        return activity;
    }

    static ParsedActivityImpl makeAlias(String targetActivityName, ParsedActivity target) {
        ParsedActivityImpl alias = new ParsedActivityImpl();
        alias.setPackageName(target.getPackageName());
        alias.setTargetActivity(targetActivityName);
        alias.configChanges = target.getConfigChanges();
        alias.setFlags(target.getFlags());
        alias.privateFlags = target.getPrivateFlags();
        alias.setIcon(target.getIcon());
        alias.setLogo(target.getLogo());
        alias.setBanner(target.getBanner());
        alias.setLabelRes(target.getLabelRes());
        alias.setNonLocalizedLabel(target.getNonLocalizedLabel());
        alias.launchMode = target.getLaunchMode();
        alias.lockTaskLaunchMode = target.getLockTaskLaunchMode();
        alias.documentLaunchMode = target.getDocumentLaunchMode();
        alias.setDescriptionRes(target.getDescriptionRes());
        alias.screenOrientation = target.getScreenOrientation();
        alias.taskAffinity = target.getTaskAffinity();
        alias.theme = target.getTheme();
        alias.softInputMode = target.getSoftInputMode();
        alias.uiOptions = target.getUiOptions();
        alias.parentActivityName = target.getParentActivityName();
        alias.maxRecents = target.getMaxRecents();
        alias.windowLayout = target.getWindowLayout();
        alias.resizeMode = target.getResizeMode();
        alias.maxAspectRatio = target.getMaxAspectRatio();
        alias.minAspectRatio = target.getMinAspectRatio();
        alias.supportsSizeChanges = target.isSupportsSizeChanges();
        alias.requestedVrComponent = target.getRequestedVrComponent();
        alias.setDirectBootAware(target.isDirectBootAware());
        alias.setProcessName(target.getProcessName());
        alias.setRequiredDisplayCategory(target.getRequiredDisplayCategory());
        alias.setRequireContentUriPermissionFromCaller(target.getRequireContentUriPermissionFromCaller());
        return alias;
    }

    public ParsedActivityImpl setMaxAspectRatio(int resizeMode, float maxAspectRatio) {
        if (resizeMode == 2 || resizeMode == 1) {
            return this;
        }
        if (maxAspectRatio < 1.0f && maxAspectRatio != 0.0f) {
            return this;
        }
        this.maxAspectRatio = maxAspectRatio;
        return this;
    }

    public ParsedActivityImpl setMinAspectRatio(int resizeMode, float minAspectRatio) {
        if (resizeMode == 2 || resizeMode == 1) {
            return this;
        }
        if (minAspectRatio < 1.0f && minAspectRatio != 0.0f) {
            return this;
        }
        this.minAspectRatio = minAspectRatio;
        return this;
    }

    public ParsedActivityImpl setTargetActivity(String targetActivity) {
        this.targetActivity = TextUtils.safeIntern(targetActivity);
        return this;
    }

    public ParsedActivityImpl setPermission(String permission) {
        this.permission = TextUtils.isEmpty(permission) ? null : permission.intern();
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public Set<String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts == null ? Collections.emptySet() : this.mKnownActivityEmbeddingCerts;
    }

    public void setKnownActivityEmbeddingCerts(Set<String> knownActivityEmbeddingCerts) {
        this.mKnownActivityEmbeddingCerts = new ArraySet();
        for (String knownCert : knownActivityEmbeddingCerts) {
            this.mKnownActivityEmbeddingCerts.add(knownCert.toUpperCase(Locale.US));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Activity{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.theme);
        dest.writeInt(this.uiOptions);
        dest.writeString(this.targetActivity);
        dest.writeString(this.parentActivityName);
        dest.writeString(this.taskAffinity);
        dest.writeInt(this.privateFlags);
        PackageImpl.sForInternedString.parcel(this.permission, dest, flags);
        dest.writeInt(this.launchMode);
        dest.writeInt(this.documentLaunchMode);
        dest.writeInt(this.maxRecents);
        dest.writeInt(this.configChanges);
        dest.writeInt(this.softInputMode);
        dest.writeInt(this.persistableMode);
        dest.writeInt(this.lockTaskLaunchMode);
        dest.writeInt(this.screenOrientation);
        dest.writeInt(this.resizeMode);
        dest.writeValue(Float.valueOf(this.maxAspectRatio));
        dest.writeValue(Float.valueOf(this.minAspectRatio));
        dest.writeBoolean(this.supportsSizeChanges);
        dest.writeString(this.requestedVrComponent);
        dest.writeInt(this.rotationAnimation);
        dest.writeInt(this.colorMode);
        dest.writeBundle(getMetaData());
        if (this.windowLayout != null) {
            dest.writeInt(1);
            this.windowLayout.writeToParcel(dest);
        } else {
            dest.writeBoolean(false);
        }
        PackageImpl.sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, dest, flags);
        dest.writeString8(this.mRequiredDisplayCategory);
        dest.writeInt(this.mRequireContentUriPermissionFromCaller);
    }

    public ParsedActivityImpl() {
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
    }

    protected ParsedActivityImpl(Parcel in) {
        super(in);
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = in.readInt();
        this.uiOptions = in.readInt();
        this.targetActivity = in.readString();
        this.parentActivityName = in.readString();
        this.taskAffinity = in.readString();
        this.privateFlags = in.readInt();
        this.permission = PackageImpl.sForInternedString.unparcel(in);
        this.launchMode = in.readInt();
        this.documentLaunchMode = in.readInt();
        this.maxRecents = in.readInt();
        this.configChanges = in.readInt();
        this.softInputMode = in.readInt();
        this.persistableMode = in.readInt();
        this.lockTaskLaunchMode = in.readInt();
        this.screenOrientation = in.readInt();
        this.resizeMode = in.readInt();
        this.maxAspectRatio = ((Float) in.readValue(Float.class.getClassLoader())).floatValue();
        this.minAspectRatio = ((Float) in.readValue(Float.class.getClassLoader())).floatValue();
        this.supportsSizeChanges = in.readBoolean();
        this.requestedVrComponent = in.readString();
        this.rotationAnimation = in.readInt();
        this.colorMode = in.readInt();
        setMetaData(in.readBundle());
        if (in.readBoolean()) {
            this.windowLayout = new ActivityInfo.WindowLayout(in);
        }
        this.mKnownActivityEmbeddingCerts = PackageImpl.sForStringSet.unparcel(in);
        this.mRequiredDisplayCategory = in.readString8();
        this.mRequireContentUriPermissionFromCaller = in.readInt();
    }

    public ParsedActivityImpl(int theme, int uiOptions, String targetActivity, String parentActivityName, String taskAffinity, int privateFlags, String permission, Set<String> knownActivityEmbeddingCerts, int launchMode, int documentLaunchMode, int maxRecents, int configChanges, int softInputMode, int persistableMode, int lockTaskLaunchMode, int screenOrientation, int resizeMode, float maxAspectRatio, float minAspectRatio, boolean supportsSizeChanges, String requestedVrComponent, int rotationAnimation, int colorMode, ActivityInfo.WindowLayout windowLayout, String requiredDisplayCategory, int requireContentUriPermissionFromCaller) {
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = theme;
        this.uiOptions = uiOptions;
        this.targetActivity = targetActivity;
        this.parentActivityName = parentActivityName;
        this.taskAffinity = taskAffinity;
        this.privateFlags = privateFlags;
        this.permission = permission;
        this.mKnownActivityEmbeddingCerts = knownActivityEmbeddingCerts;
        this.launchMode = launchMode;
        this.documentLaunchMode = documentLaunchMode;
        this.maxRecents = maxRecents;
        this.configChanges = configChanges;
        this.softInputMode = softInputMode;
        this.persistableMode = persistableMode;
        this.lockTaskLaunchMode = lockTaskLaunchMode;
        this.screenOrientation = screenOrientation;
        this.resizeMode = resizeMode;
        this.maxAspectRatio = maxAspectRatio;
        this.minAspectRatio = minAspectRatio;
        this.supportsSizeChanges = supportsSizeChanges;
        this.requestedVrComponent = requestedVrComponent;
        this.rotationAnimation = rotationAnimation;
        this.colorMode = colorMode;
        this.windowLayout = windowLayout;
        this.mRequiredDisplayCategory = requiredDisplayCategory;
        this.mRequireContentUriPermissionFromCaller = requireContentUriPermissionFromCaller;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getTheme() {
        return this.theme;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getUiOptions() {
        return this.uiOptions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getTargetActivity() {
        return this.targetActivity;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getParentActivityName() {
        return this.parentActivityName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getTaskAffinity() {
        return this.taskAffinity;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getPrivateFlags() {
        return this.privateFlags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getPermission() {
        return this.permission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getLaunchMode() {
        return this.launchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getDocumentLaunchMode() {
        return this.documentLaunchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getMaxRecents() {
        return this.maxRecents;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getConfigChanges() {
        return this.configChanges;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getSoftInputMode() {
        return this.softInputMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getPersistableMode() {
        return this.persistableMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getLockTaskLaunchMode() {
        return this.lockTaskLaunchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getScreenOrientation() {
        return this.screenOrientation;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getResizeMode() {
        return this.resizeMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public float getMaxAspectRatio() {
        return this.maxAspectRatio;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public float getMinAspectRatio() {
        return this.minAspectRatio;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public boolean isSupportsSizeChanges() {
        return this.supportsSizeChanges;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getRequestedVrComponent() {
        return this.requestedVrComponent;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getRotationAnimation() {
        return this.rotationAnimation;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getColorMode() {
        return this.colorMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public ActivityInfo.WindowLayout getWindowLayout() {
        return this.windowLayout;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public String getRequiredDisplayCategory() {
        return this.mRequiredDisplayCategory;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getRequireContentUriPermissionFromCaller() {
        return this.mRequireContentUriPermissionFromCaller;
    }

    public ParsedActivityImpl setTheme(int value) {
        this.theme = value;
        return this;
    }

    public ParsedActivityImpl setUiOptions(int value) {
        this.uiOptions = value;
        return this;
    }

    public ParsedActivityImpl setParentActivityName(String value) {
        this.parentActivityName = value;
        return this;
    }

    public ParsedActivityImpl setTaskAffinity(String value) {
        this.taskAffinity = value;
        return this;
    }

    public ParsedActivityImpl setPrivateFlags(int value) {
        this.privateFlags = value;
        return this;
    }

    public ParsedActivityImpl setLaunchMode(int value) {
        this.launchMode = value;
        return this;
    }

    public ParsedActivityImpl setDocumentLaunchMode(int value) {
        this.documentLaunchMode = value;
        return this;
    }

    public ParsedActivityImpl setMaxRecents(int value) {
        this.maxRecents = value;
        return this;
    }

    public ParsedActivityImpl setConfigChanges(int value) {
        this.configChanges = value;
        return this;
    }

    public ParsedActivityImpl setSoftInputMode(int value) {
        this.softInputMode = value;
        return this;
    }

    public ParsedActivityImpl setPersistableMode(int value) {
        this.persistableMode = value;
        return this;
    }

    public ParsedActivityImpl setLockTaskLaunchMode(int value) {
        this.lockTaskLaunchMode = value;
        return this;
    }

    public ParsedActivityImpl setScreenOrientation(int value) {
        this.screenOrientation = value;
        return this;
    }

    public ParsedActivityImpl setResizeMode(int value) {
        this.resizeMode = value;
        return this;
    }

    public ParsedActivityImpl setMaxAspectRatio(float value) {
        this.maxAspectRatio = value;
        return this;
    }

    public ParsedActivityImpl setMinAspectRatio(float value) {
        this.minAspectRatio = value;
        return this;
    }

    public ParsedActivityImpl setSupportsSizeChanges(boolean value) {
        this.supportsSizeChanges = value;
        return this;
    }

    public ParsedActivityImpl setRequestedVrComponent(String value) {
        this.requestedVrComponent = value;
        return this;
    }

    public ParsedActivityImpl setRotationAnimation(int value) {
        this.rotationAnimation = value;
        return this;
    }

    public ParsedActivityImpl setColorMode(int value) {
        this.colorMode = value;
        return this;
    }

    public ParsedActivityImpl setWindowLayout(ActivityInfo.WindowLayout value) {
        this.windowLayout = value;
        return this;
    }

    public ParsedActivityImpl setRequiredDisplayCategory(String value) {
        this.mRequiredDisplayCategory = value;
        return this;
    }

    public ParsedActivityImpl setRequireContentUriPermissionFromCaller(int value) {
        this.mRequireContentUriPermissionFromCaller = value;
        return this;
    }

    @Deprecated
    private void __metadata() {
    }
}
