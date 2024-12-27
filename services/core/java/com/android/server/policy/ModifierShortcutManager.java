package com.android.server.policy;

import android.R;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import com.android.internal.util.XmlUtils;
import com.android.server.policy.PhoneWindowManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ModifierShortcutManager {
    public static final SparseArray sApplicationLaunchKeyCategories;
    public static final SparseArray sApplicationLaunchKeyRoles;
    public final Context mContext;
    public final Handler mHandler;
    public final PackageManager mPackageManager;
    public final PhoneWindowManagerExt mPolicyExt;
    public final RoleManager mRoleManager;
    public final SparseArray mIntentShortcuts = new SparseArray();
    public final SparseArray mShiftShortcuts = new SparseArray();
    public final SparseArray mRoleShortcuts = new SparseArray();
    public final SparseArray mShiftRoleShortcuts = new SparseArray();
    public final Map mRoleIntents = new HashMap();
    public final LongSparseArray mShortcutKeyServices = new LongSparseArray();
    public boolean mSearchKeyShortcutPending = false;
    public boolean mConsumeSearchKeyUp = true;

    static {
        SparseArray sparseArray = new SparseArray();
        sApplicationLaunchKeyRoles = sparseArray;
        SparseArray sparseArray2 = new SparseArray();
        sApplicationLaunchKeyCategories = sparseArray2;
        sparseArray.append(64, "android.app.role.BROWSER");
        sparseArray2.append(65, "android.intent.category.APP_EMAIL");
        sparseArray2.append(207, "android.intent.category.APP_CONTACTS");
        sparseArray2.append(208, "android.intent.category.APP_CALENDAR");
        sparseArray2.append(209, "android.intent.category.APP_MUSIC");
        sparseArray2.append(210, "android.intent.category.APP_CALCULATOR");
    }

    public ModifierShortcutManager(Context context, PhoneWindowManager.PolicyHandler policyHandler, PhoneWindowManagerExt phoneWindowManagerExt) {
        Intent intent;
        this.mPolicyExt = phoneWindowManagerExt;
        this.mContext = context;
        this.mHandler = policyHandler;
        this.mPackageManager = context.getPackageManager();
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.mRoleManager = roleManager;
        roleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), new OnRoleHoldersChangedListener() { // from class: com.android.server.policy.ModifierShortcutManager$$ExternalSyntheticLambda1
            public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                ((HashMap) ModifierShortcutManager.this.mRoleIntents).remove(str);
            }
        }, UserHandle.ALL);
        try {
            XmlResourceParser xml = context.getResources().getXml(R.xml.config_webview_packages);
            XmlUtils.beginDocument(xml, "bookmarks");
            while (true) {
                XmlUtils.nextElement(xml);
                if (xml.getEventType() != 1 && "bookmark".equals(xml.getName())) {
                    String attributeValue = xml.getAttributeValue(null, "package");
                    String attributeValue2 = xml.getAttributeValue(null, "class");
                    String attributeValue3 = xml.getAttributeValue(null, "shortcut");
                    String attributeValue4 = xml.getAttributeValue(null, "category");
                    String attributeValue5 = xml.getAttributeValue(null, "shift");
                    String attributeValue6 = xml.getAttributeValue(null, "role");
                    if (TextUtils.isEmpty(attributeValue3)) {
                        Log.w("ModifierShortcutManager", "Shortcut required for bookmark with category=" + attributeValue4 + " packageName=" + attributeValue + " className=" + attributeValue2 + " role=" + attributeValue6 + "shiftName=" + attributeValue5);
                    } else {
                        char charAt = attributeValue3.charAt(0);
                        boolean z = attributeValue5 != null && attributeValue5.equals("true");
                        if (attributeValue == null || attributeValue2 == null) {
                            if (attributeValue4 != null) {
                                if (attributeValue6 != null) {
                                    Log.w("ModifierShortcutManager", "Cannot specify role bookmark when category is present for bookmark shortcutChar=" + ((int) charAt) + " category= " + attributeValue4);
                                } else {
                                    intent = Intent.makeMainSelectorActivity("android.intent.action.MAIN", attributeValue4);
                                }
                            } else if (attributeValue6 == null) {
                                Log.w("ModifierShortcutManager", "Unable to add bookmark for shortcut " + attributeValue3 + ": missing package/class, category or role attributes");
                            } else if (z) {
                                this.mShiftRoleShortcuts.put(charAt, attributeValue6);
                            } else {
                                this.mRoleShortcuts.put(charAt, attributeValue6);
                            }
                        } else if (attributeValue6 == null && attributeValue4 == null) {
                            ComponentName componentName = new ComponentName(attributeValue, attributeValue2);
                            try {
                                this.mPackageManager.getActivityInfo(componentName, 794624);
                            } catch (PackageManager.NameNotFoundException unused) {
                                ComponentName componentName2 = new ComponentName(this.mPackageManager.canonicalToCurrentPackageNames(new String[]{attributeValue})[0], attributeValue2);
                                try {
                                    this.mPackageManager.getActivityInfo(componentName2, 794624);
                                    componentName = componentName2;
                                } catch (PackageManager.NameNotFoundException unused2) {
                                    Log.w("ModifierShortcutManager", "Unable to add bookmark: " + attributeValue + "/" + attributeValue2 + " not found.");
                                }
                            }
                            intent = new Intent("android.intent.action.MAIN");
                            intent.addCategory("android.intent.category.LAUNCHER");
                            intent.setComponent(componentName);
                        } else {
                            Log.w("ModifierShortcutManager", "Cannot specify role or category when package and class are present for bookmark packageName=" + attributeValue + " className=" + attributeValue2 + " shortcutChar=" + ((int) charAt));
                        }
                        if (z) {
                            this.mShiftShortcuts.put(charAt, intent);
                        } else {
                            this.mIntentShortcuts.put(charAt, intent);
                        }
                    }
                }
                return;
            }
        } catch (IOException | XmlPullParserException e) {
            Log.e("ModifierShortcutManager", "Got exception parsing bookmarks.", e);
        }
    }

    public final Intent getRoleLaunchIntent(String str) {
        Intent intent = (Intent) ((HashMap) this.mRoleIntents).get(str);
        if (intent != null) {
            return intent;
        }
        if (!this.mRoleManager.isRoleAvailable(str)) {
            Log.w("ModifierShortcutManager", "Role " + str + " is not available.");
            return intent;
        }
        String defaultApplication = this.mRoleManager.getDefaultApplication(str);
        if (defaultApplication == null) {
            Log.w("ModifierShortcutManager", "No default application for role ".concat(str));
            return intent;
        }
        Intent launchIntentForPackage = this.mPackageManager.getLaunchIntentForPackage(defaultApplication);
        launchIntentForPackage.putExtra("com.android.server.policy.ModifierShortcutManager.EXTRA_ROLE", str);
        ((HashMap) this.mRoleIntents).put(str, launchIntentForPackage);
        return launchIntentForPackage;
    }
}
