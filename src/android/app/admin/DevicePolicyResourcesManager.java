package android.app.admin;

import android.annotation.SystemApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.util.Log;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class DevicePolicyResourcesManager {
    private static boolean DEFAULT_DISABLE_RESOURCES_UPDATABILITY = false;
    private static String DISABLE_RESOURCES_UPDATABILITY_FLAG = "disable_resources_updatability";
    private static String TAG = "DevicePolicyResourcesManager";
    private final Context mContext;
    private final IDevicePolicyManager mService;

    protected DevicePolicyResourcesManager(Context context, IDevicePolicyManager iDevicePolicyManager) {
        this.mContext = context;
        this.mService = iDevicePolicyManager;
    }

    @SystemApi
    public void setDrawables(Set<DevicePolicyDrawableResource> set) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDrawables(new ArrayList(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void resetDrawables(Set<String> set) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.resetDrawables(new ArrayList(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public Drawable getDrawable(String str, String str2, Supplier<Drawable> supplier) {
        return getDrawable(str, str2, DevicePolicyResources.UNDEFINED, supplier);
    }

    public Drawable getDrawable(String str, String str2, String str3, Supplier<Drawable> supplier) {
        Objects.requireNonNull(str, "drawableId can't be null");
        Objects.requireNonNull(str2, "drawableStyle can't be null");
        Objects.requireNonNull(str3, "drawableSource can't be null");
        Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        if (str.equals(DevicePolicyResources.UNDEFINED) || DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return ParcelableResource.loadDefaultDrawable(supplier);
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                ParcelableResource drawable = iDevicePolicyManager.getDrawable(str, str2, str3);
                if (drawable == null) {
                    return ParcelableResource.loadDefaultDrawable(supplier);
                }
                return drawable.getDrawable(this.mContext, 0, supplier);
            } catch (RemoteException e) {
                Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
                return ParcelableResource.loadDefaultDrawable(supplier);
            }
        }
        return ParcelableResource.loadDefaultDrawable(supplier);
    }

    public Drawable getDrawableForDensity(String str, String str2, int i, Supplier<Drawable> supplier) {
        return getDrawableForDensity(str, str2, DevicePolicyResources.UNDEFINED, i, supplier);
    }

    public Drawable getDrawableForDensity(String str, String str2, String str3, int i, Supplier<Drawable> supplier) {
        Objects.requireNonNull(str, "drawableId can't be null");
        Objects.requireNonNull(str2, "drawableStyle can't be null");
        Objects.requireNonNull(str3, "drawableSource can't be null");
        Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        if (str.equals(DevicePolicyResources.UNDEFINED) || DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return ParcelableResource.loadDefaultDrawable(supplier);
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                ParcelableResource drawable = iDevicePolicyManager.getDrawable(str, str2, str3);
                if (drawable == null) {
                    return ParcelableResource.loadDefaultDrawable(supplier);
                }
                return drawable.getDrawable(this.mContext, i, supplier);
            } catch (RemoteException e) {
                Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
                return ParcelableResource.loadDefaultDrawable(supplier);
            }
        }
        return ParcelableResource.loadDefaultDrawable(supplier);
    }

    public Icon getDrawableAsIcon(String str, String str2, String str3, Icon icon) {
        IDevicePolicyManager iDevicePolicyManager;
        Objects.requireNonNull(str, "drawableId can't be null");
        Objects.requireNonNull(str2, "drawableStyle can't be null");
        Objects.requireNonNull(str3, "drawableSource can't be null");
        Objects.requireNonNull(icon, "defaultIcon can't be null");
        if (!str.equals(DevicePolicyResources.UNDEFINED) && !DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY) && (iDevicePolicyManager = this.mService) != null) {
            try {
                ParcelableResource drawable = iDevicePolicyManager.getDrawable(str, str2, str3);
                if (drawable != null) {
                    return Icon.createWithResource(drawable.getPackageName(), drawable.getResourceId());
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Error getting the updated drawable from DevicePolicyManagerService.", e);
            }
        }
        return icon;
    }

    public Icon getDrawableAsIcon(String str, String str2, Icon icon) {
        return getDrawableAsIcon(str, str2, DevicePolicyResources.UNDEFINED, icon);
    }

    @SystemApi
    public void setStrings(Set<DevicePolicyStringResource> set) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setStrings(new ArrayList(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void resetStrings(Set<String> set) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.resetStrings(new ArrayList(set));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public String getString(String str, Supplier<String> supplier) {
        Objects.requireNonNull(str, "stringId can't be null");
        Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        if (str.equals(DevicePolicyResources.UNDEFINED) || DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return ParcelableResource.loadDefaultString(supplier);
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                ParcelableResource string = iDevicePolicyManager.getString(str);
                if (string == null) {
                    return ParcelableResource.loadDefaultString(supplier);
                }
                return string.getString(this.mContext, supplier);
            } catch (RemoteException e) {
                Log.e(TAG, "Error getting the updated string from DevicePolicyManagerService.", e);
                return ParcelableResource.loadDefaultString(supplier);
            }
        }
        return ParcelableResource.loadDefaultString(supplier);
    }

    public String getString(String str, Supplier<String> supplier, Object... objArr) {
        Objects.requireNonNull(str, "stringId can't be null");
        Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        if (str.equals(DevicePolicyResources.UNDEFINED) || DeviceConfig.getBoolean("device_policy_manager", DISABLE_RESOURCES_UPDATABILITY_FLAG, DEFAULT_DISABLE_RESOURCES_UPDATABILITY)) {
            return ParcelableResource.loadDefaultString(supplier);
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                ParcelableResource string = iDevicePolicyManager.getString(str);
                if (string == null) {
                    return ParcelableResource.loadDefaultString(supplier);
                }
                return string.getString(this.mContext, supplier, objArr);
            } catch (RemoteException e) {
                Log.e(TAG, "Error getting the updated string from DevicePolicyManagerService.", e);
                return ParcelableResource.loadDefaultString(supplier);
            }
        }
        return ParcelableResource.loadDefaultString(supplier);
    }
}
