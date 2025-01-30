package com.android.server.policy;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.IInstalld;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import com.android.internal.policy.IShortcutService;
import com.android.internal.util.XmlUtils;
import com.android.server.wm.StartingSurfaceController;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class ModifierShortcutManager {
  public static SparseArray sApplicationLaunchKeyCategories;
  public final Context mContext;
  public PhoneWindowManagerExt mPolicyExt;
  public final SparseArray mIntentShortcuts = new SparseArray();
  public final SparseArray mShiftShortcuts = new SparseArray();
  public LongSparseArray mShortcutKeyServices = new LongSparseArray();
  public boolean mSearchKeyShortcutPending = false;
  public boolean mConsumeSearchKeyUp = true;

  static {
    SparseArray sparseArray = new SparseArray();
    sApplicationLaunchKeyCategories = sparseArray;
    sparseArray.append(64, "android.intent.category.APP_BROWSER");
    sApplicationLaunchKeyCategories.append(65, "android.intent.category.APP_EMAIL");
    sApplicationLaunchKeyCategories.append(207, "android.intent.category.APP_CONTACTS");
    sApplicationLaunchKeyCategories.append(208, "android.intent.category.APP_CALENDAR");
    sApplicationLaunchKeyCategories.append(209, "android.intent.category.APP_MUSIC");
    sApplicationLaunchKeyCategories.append(210, "android.intent.category.APP_CALCULATOR");
  }

  public ModifierShortcutManager(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
    this.mContext = context;
    this.mPolicyExt = phoneWindowManagerExt;
    loadShortcuts();
  }

  public final Intent getIntent(KeyCharacterMap keyCharacterMap, int i, int i2) {
    char lowerCase;
    boolean metaStateHasModifiers = KeyEvent.metaStateHasModifiers(i2, 1);
    if (!metaStateHasModifiers && !KeyEvent.metaStateHasNoModifiers(i2)) {
      return null;
    }
    SparseArray sparseArray = metaStateHasModifiers ? this.mShiftShortcuts : this.mIntentShortcuts;
    int i3 = keyCharacterMap.get(i, i2);
    ShortcutInfo shortcutInfo = i3 != 0 ? (ShortcutInfo) sparseArray.get(i3) : null;
    if (shortcutInfo == null
        && (lowerCase = Character.toLowerCase(keyCharacterMap.getDisplayLabel(i))) != 0) {
      shortcutInfo = (ShortcutInfo) sparseArray.get(lowerCase);
    }
    if (shortcutInfo != null) {
      return shortcutInfo.intent;
    }
    return null;
  }

  public final void loadShortcuts() {
    Intent intent;
    String str;
    ActivityInfo activityInfo;
    PackageManager packageManager = this.mContext.getPackageManager();
    try {
      XmlResourceParser xml = this.mContext.getResources().getXml(R.xml.config_webview_packages);
      XmlUtils.beginDocument(xml, "bookmarks");
      while (true) {
        XmlUtils.nextElement(xml);
        if (xml.getEventType() != 1 && "bookmark".equals(xml.getName())) {
          String attributeValue = xml.getAttributeValue(null, "package");
          String attributeValue2 = xml.getAttributeValue(null, "class");
          String attributeValue3 = xml.getAttributeValue(null, "shortcut");
          String attributeValue4 = xml.getAttributeValue(null, "category");
          String attributeValue5 = xml.getAttributeValue(null, "shift");
          if (TextUtils.isEmpty(attributeValue3)) {
            Log.w(
                StartingSurfaceController.TAG,
                "Unable to get shortcut for: " + attributeValue + "/" + attributeValue2);
          } else {
            char charAt = attributeValue3.charAt(0);
            boolean z = attributeValue5 != null && attributeValue5.equals("true");
            if (attributeValue != null && attributeValue2 != null) {
              ComponentName componentName = new ComponentName(attributeValue, attributeValue2);
              try {
                activityInfo = packageManager.getActivityInfo(componentName, 794624);
              } catch (PackageManager.NameNotFoundException unused) {
                ComponentName componentName2 =
                    new ComponentName(
                        packageManager
                            .canonicalToCurrentPackageNames(new String[] {attributeValue})[0],
                        attributeValue2);
                try {
                  activityInfo = packageManager.getActivityInfo(componentName2, 794624);
                  componentName = componentName2;
                } catch (PackageManager.NameNotFoundException unused2) {
                  Log.w(
                      StartingSurfaceController.TAG,
                      "Unable to add bookmark: "
                          + attributeValue
                          + "/"
                          + attributeValue2
                          + " not found.");
                }
              }
              intent = new Intent("android.intent.action.MAIN");
              intent.addCategory("android.intent.category.LAUNCHER");
              intent.setComponent(componentName);
              str = activityInfo.loadLabel(packageManager).toString();
            } else if (attributeValue4 != null) {
              intent =
                  Intent.makeMainSelectorActivity("android.intent.action.MAIN", attributeValue4);
              str = "";
            } else {
              Log.w(
                  StartingSurfaceController.TAG,
                  "Unable to add bookmark for shortcut "
                      + attributeValue3
                      + ": missing package/class or category attributes");
            }
            ShortcutInfo shortcutInfo = new ShortcutInfo(str, intent);
            if (z) {
              this.mShiftShortcuts.put(charAt, shortcutInfo);
            } else {
              this.mIntentShortcuts.put(charAt, shortcutInfo);
            }
          }
        }
        return;
      }
    } catch (IOException | XmlPullParserException e) {
      Log.e(StartingSurfaceController.TAG, "Got exception parsing bookmarks.", e);
    }
  }

  public void registerShortcutKey(long j, IShortcutService iShortcutService) {
    IShortcutService iShortcutService2 = (IShortcutService) this.mShortcutKeyServices.get(j);
    if (iShortcutService2 != null && iShortcutService2.asBinder().pingBinder()) {
      throw new RemoteException("Key already exists.");
    }
    this.mShortcutKeyServices.put(j, iShortcutService);
  }

  public final boolean handleShortcutService(int i, int i2) {
    long j = i;
    if ((i2 & IInstalld.FLAG_USE_QUOTA) != 0) {
      j |= 17592186044416L;
    }
    if ((i2 & 2) != 0) {
      j |= 8589934592L;
    }
    if ((i2 & 1) != 0) {
      j |= 4294967296L;
    }
    if ((65536 & i2) != 0) {
      j |= 281474976710656L;
    }
    IShortcutService iShortcutService = (IShortcutService) this.mShortcutKeyServices.get(j);
    if (iShortcutService == null) {
      return false;
    }
    try {
      iShortcutService.notifyShortcutKeyPressed(j);
      return true;
    } catch (RemoteException unused) {
      this.mShortcutKeyServices.delete(j);
      return true;
    }
  }

  public final boolean handleIntentShortcut(KeyCharacterMap keyCharacterMap, int i, int i2) {
    if (this.mSearchKeyShortcutPending) {
      if (!keyCharacterMap.isPrintingKey(i)) {
        return false;
      }
      this.mConsumeSearchKeyUp = true;
      this.mSearchKeyShortcutPending = false;
    } else {
      if ((458752 & i2) == 0) {
        String str = (String) sApplicationLaunchKeyCategories.get(i);
        if (str == null) {
          return false;
        }
        Intent makeMainSelectorActivity =
            Intent.makeMainSelectorActivity("android.intent.action.MAIN", str);
        makeMainSelectorActivity.setFlags(268435456);
        try {
          this.mContext.startActivityAsUser(makeMainSelectorActivity, UserHandle.CURRENT);
        } catch (ActivityNotFoundException unused) {
          Slog.w(
              StartingSurfaceController.TAG,
              "Dropping application launch key because the activity to which it is registered was"
                  + " not found: keyCode="
                  + KeyEvent.keyCodeToString(i)
                  + ", category="
                  + str);
        }
        return true;
      }
      i2 &= -458753;
    }
    Intent intent = getIntent(keyCharacterMap, i, i2);
    if (intent != null) {
      intent.addFlags(268435456);
      try {
        Log.d(
            StartingSurfaceController.TAG,
            "startActivity shortcut, keyCode=" + i + " intent=" + intent);
        Intent fillInIntent = this.mPolicyExt.getFillInIntent();
        if (this.mPolicyExt.showCoverToast(fillInIntent, intent)) {
          PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
          phoneWindowManagerExt.setPendingIntentAfterUnlock(
              phoneWindowManagerExt.getPendingIntentActivityAsUser(intent, UserHandle.CURRENT),
              fillInIntent);
        } else {
          this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        }
      } catch (ActivityNotFoundException unused2) {
        Slog.w(
            StartingSurfaceController.TAG,
            "Dropping shortcut key combination because the activity to which it is registered was"
                + " not found: META+ or SEARCH"
                + KeyEvent.keyCodeToString(i));
      }
      return true;
    }
    Log.d(StartingSurfaceController.TAG, "handleIntentShortcut, keyCode=" + i + " metaState=" + i2);
    return false;
  }

  public boolean interceptKey(KeyEvent keyEvent) {
    if (keyEvent.getRepeatCount() != 0) {
      return false;
    }
    int modifiers = keyEvent.getModifiers();
    int keyCode = keyEvent.getKeyCode();
    if (keyCode == 84) {
      if (keyEvent.getAction() == 0) {
        this.mSearchKeyShortcutPending = true;
        this.mConsumeSearchKeyUp = false;
      } else {
        this.mSearchKeyShortcutPending = false;
        if (this.mConsumeSearchKeyUp) {
          this.mConsumeSearchKeyUp = false;
          return true;
        }
      }
      return false;
    }
    if (keyEvent.getAction() != 0) {
      return false;
    }
    return handleIntentShortcut(keyEvent.getKeyCharacterMap(), keyCode, modifiers)
        || handleShortcutService(keyCode, modifiers);
  }

  public final class ShortcutInfo {
    public final Intent intent;
    public final String title;

    public ShortcutInfo(String str, Intent intent) {
      this.title = str;
      this.intent = intent;
    }
  }
}
