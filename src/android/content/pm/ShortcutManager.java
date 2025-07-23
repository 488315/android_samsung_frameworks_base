package android.content.pm;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.IShortcutService;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes.dex */
public class ShortcutManager {
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;
    private static final String TAG = "ShortcutManager";
    private final Context mContext;
    private final IShortcutService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutMatchFlags {
    }

    public ShortcutManager(Context context, IShortcutService iShortcutService) {
        this.mContext = context;
        this.mService = iShortcutService;
    }

    public ShortcutManager(Context context) {
        this(context, IShortcutService.Stub.asInterface(ServiceManager.getService("shortcut")));
    }

    public boolean setDynamicShortcuts(List<ShortcutInfo> list) {
        try {
            return this.mService.setDynamicShortcuts(this.mContext.getPackageName(), new ParceledListSlice(list), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getDynamicShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 2, injectMyUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getManifestShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 1, injectMyUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getShortcuts(int i) {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), i, injectMyUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addDynamicShortcuts(List<ShortcutInfo> list) {
        try {
            return this.mService.addDynamicShortcuts(this.mContext.getPackageName(), new ParceledListSlice(list), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeDynamicShortcuts(List<String> list) {
        try {
            this.mService.removeDynamicShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllDynamicShortcuts() {
        try {
            this.mService.removeAllDynamicShortcuts(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeLongLivedShortcuts(List<String> list) {
        try {
            this.mService.removeLongLivedShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ShortcutInfo> getPinnedShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 4, injectMyUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateShortcuts(List<ShortcutInfo> list) {
        try {
            return this.mService.updateShortcuts(this.mContext.getPackageName(), new ParceledListSlice(list), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, 0, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list, int i) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, i, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(List<String> list, String str) {
        disableShortcuts(list, (CharSequence) str);
    }

    public void disableShortcuts(List<String> list, CharSequence charSequence) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, charSequence, 0, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableShortcuts(List<String> list) {
        try {
            this.mService.enableShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxShortcutCountForActivity() {
        return getMaxShortcutCountPerActivity();
    }

    public int getMaxShortcutCountPerActivity() {
        try {
            return this.mService.getMaxShortcutCountPerActivity(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRemainingCallCount() {
        try {
            return this.mService.getRemainingCallCount(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getRateLimitResetTime() {
        try {
            return this.mService.getRateLimitResetTime(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRateLimitingActive() {
        try {
            return this.mService.getRemainingCallCount(this.mContext.getPackageName(), injectMyUserId()) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIconMaxWidth() {
        try {
            return this.mService.getIconMaxDimensions(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIconMaxHeight() {
        try {
            return this.mService.getIconMaxDimensions(this.mContext.getPackageName(), injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportShortcutUsed(String str) {
        try {
            this.mService.reportShortcutUsed(this.mContext.getPackageName(), str, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinShortcutSupported() {
        try {
            return this.mService.isRequestPinItemSupported(injectMyUserId(), 1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinShortcut(ShortcutInfo shortcutInfo, IntentSender intentSender) {
        try {
            return this.mService.requestPinShortcutAsDisplay(this.mContext.getPackageName(), shortcutInfo, intentSender, injectMyUserId(), this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent createShortcutResultIntent(ShortcutInfo shortcutInfo) {
        try {
            Intent createShortcutResultIntent = this.mService.createShortcutResultIntent(this.mContext.getPackageName(), shortcutInfo, injectMyUserId());
            if (createShortcutResultIntent != null) {
                createShortcutResultIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
            }
            return createShortcutResultIntent;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onApplicationActive(String str, int i) {
        try {
            this.mService.onApplicationActive(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected int injectMyUserId() {
        return this.mContext.getUserId();
    }

    @SystemApi
    public List<ShareShortcutInfo> getShareTargets(IntentFilter intentFilter) {
        try {
            return this.mService.getShareTargets(this.mContext.getPackageName(), intentFilter, injectMyUserId()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static final class ShareShortcutInfo implements Parcelable {
        public static final Parcelable.Creator<ShareShortcutInfo> CREATOR = new Parcelable.Creator<ShareShortcutInfo>() { // from class: android.content.pm.ShortcutManager.ShareShortcutInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ShareShortcutInfo createFromParcel(Parcel parcel) {
                return new ShareShortcutInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ShareShortcutInfo[] newArray(int i) {
                return new ShareShortcutInfo[i];
            }
        };
        private final ShortcutInfo mShortcutInfo;
        private final ComponentName mTargetComponent;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ShareShortcutInfo(ShortcutInfo shortcutInfo, ComponentName componentName) {
            if (shortcutInfo == null) {
                throw new NullPointerException("shortcut info is null");
            }
            if (componentName == null) {
                throw new NullPointerException("target component is null");
            }
            this.mShortcutInfo = shortcutInfo;
            this.mTargetComponent = componentName;
        }

        private ShareShortcutInfo(Parcel parcel) {
            this.mShortcutInfo = (ShortcutInfo) parcel.readParcelable(ShortcutInfo.class.getClassLoader(), ShortcutInfo.class);
            this.mTargetComponent = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader(), ComponentName.class);
        }

        public ShortcutInfo getShortcutInfo() {
            return this.mShortcutInfo;
        }

        public ComponentName getTargetComponent() {
            return this.mTargetComponent;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mShortcutInfo, i);
            parcel.writeParcelable(this.mTargetComponent, i);
        }
    }

    @SystemApi
    public boolean hasShareTargets(String str) {
        try {
            return this.mService.hasShareTargets(this.mContext.getPackageName(), str, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pushDynamicShortcut(ShortcutInfo shortcutInfo) {
        try {
            this.mService.pushDynamicShortcut(this.mContext.getPackageName(), shortcutInfo, injectMyUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
