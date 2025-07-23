package android.accessibilityservice;

import android.accessibilityservice.util.AccessibilityUtils;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class AccessibilityShortcutInfo {
    public static final String META_DATA = "android.accessibilityshortcut.target";
    private static final String TAG_ACCESSIBILITY_SHORTCUT = "accessibility-shortcut-target";
    private final ActivityInfo mActivityInfo;
    private final int mAnimatedImageRes;
    private final ComponentName mComponentName;
    private final int mDescriptionResId;
    private final int mHtmlDescriptionRes;
    private final int mIntroResId;
    private String mSettingsActivityName;
    private final int mSummaryResId;
    private String mTileServiceName;

    public AccessibilityShortcutInfo(Context context, ActivityInfo activityInfo) throws XmlPullParserException, IOException {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = activityInfo.getComponentName();
        this.mComponentName = componentName;
        this.mActivityInfo = activityInfo;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(packageManager, META_DATA);
            try {
                long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
                if (elapsedRealtime2 > 100) {
                    Log.i("AccessibilityShortcutInfo", "took more than 100ms mComponentName : " + componentName + ", elapsedTime : " + elapsedRealtime2);
                }
                if (loadXmlMetaData == null) {
                    throw new XmlPullParserException("Meta-data accessibility-shortcut-target does not exist");
                }
                for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
                }
                if (!TAG_ACCESSIBILITY_SHORTCUT.equals(loadXmlMetaData.getName())) {
                    throw new XmlPullParserException("Meta-data does not start withaccessibility-shortcut-target tag");
                }
                TypedArray obtainAttributes = packageManager.getResourcesForApplication(this.mActivityInfo.applicationInfo).obtainAttributes(Xml.asAttributeSet(loadXmlMetaData), R.styleable.AccessibilityShortcutTarget);
                this.mDescriptionResId = obtainAttributes.getResourceId(0, 0);
                this.mSummaryResId = obtainAttributes.getResourceId(1, 0);
                this.mAnimatedImageRes = obtainAttributes.getResourceId(3, 0);
                this.mHtmlDescriptionRes = obtainAttributes.getResourceId(4, 0);
                this.mSettingsActivityName = obtainAttributes.getString(2);
                this.mTileServiceName = obtainAttributes.getString(5);
                this.mIntroResId = obtainAttributes.getResourceId(6, 0);
                obtainAttributes.recycle();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } finally {
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new XmlPullParserException("Unable to create context for: " + this.mActivityInfo.packageName);
        }
    }

    public ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String loadSummary(PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mSummaryResId);
    }

    public String loadIntro(PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mIntroResId);
    }

    public String loadDescription(PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mDescriptionResId);
    }

    public int getAnimatedImageRes() {
        return this.mAnimatedImageRes;
    }

    public Drawable loadAnimatedImage(Context context) {
        if (this.mAnimatedImageRes == 0) {
            return null;
        }
        return AccessibilityUtils.loadSafeAnimatedImage(context, this.mActivityInfo.applicationInfo, this.mAnimatedImageRes);
    }

    public String loadHtmlDescription(PackageManager packageManager) {
        String loadResourceString = loadResourceString(packageManager, this.mActivityInfo, this.mHtmlDescriptionRes);
        if (loadResourceString != null) {
            return AccessibilityUtils.getFilteredHtmlText(loadResourceString);
        }
        return null;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String getTileServiceName() {
        return this.mTileServiceName;
    }

    private String loadResourceString(PackageManager packageManager, ActivityInfo activityInfo, int i) {
        CharSequence text;
        if (i == 0 || (text = packageManager.getText(activityInfo.packageName, i, activityInfo.applicationInfo)) == null) {
            return null;
        }
        return text.toString().trim();
    }

    public int hashCode() {
        ComponentName componentName = this.mComponentName;
        return (componentName == null ? 0 : componentName.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityShortcutInfo accessibilityShortcutInfo = (AccessibilityShortcutInfo) obj;
        ComponentName componentName = this.mComponentName;
        if (componentName == null) {
            if (accessibilityShortcutInfo.mComponentName != null) {
                return false;
            }
        } else if (!componentName.equals(accessibilityShortcutInfo.mComponentName)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "AccessibilityShortcutInfo[activityInfo: " + this.mActivityInfo + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
