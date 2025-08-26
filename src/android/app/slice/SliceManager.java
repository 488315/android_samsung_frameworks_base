package android.app.slice;

import android.app.slice.ISliceManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Deprecated
/* loaded from: classes.dex */
public class SliceManager {
    public static final String ACTION_REQUEST_SLICE_PERMISSION = "com.android.intent.action.REQUEST_SLICE_PERMISSION";
    public static final String CATEGORY_SLICE = "android.app.slice.category.SLICE";
    public static final String SLICE_METADATA_KEY = "android.metadata.SLICE_URI";
    private static final String TAG = "SliceManager";
    private final Context mContext;
    private final IBinder mToken = new Binder();
    private final ISliceManager mService = ISliceManager.Stub.asInterface(ServiceManager.getServiceOrThrow("slice"));

    public SliceManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public void pinSlice(Uri uri, Set<SliceSpec> set) {
        try {
            this.mService.pinSlice(this.mContext.getPackageName(), uri, (SliceSpec[]) set.toArray(new SliceSpec[set.size()]), this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unpinSlice(Uri uri) {
        try {
            this.mService.unpinSlice(this.mContext.getPackageName(), uri, this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasSliceAccess() {
        try {
            return this.mService.hasSliceAccess(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<SliceSpec> getPinnedSpecs(Uri uri) {
        try {
            return new ArraySet(Arrays.asList(this.mService.getPinnedSpecs(uri, this.mContext.getPackageName())));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Uri> getPinnedSlices() {
        try {
            return Arrays.asList(this.mService.getPinnedSlices(this.mContext.getPackageName()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Collection<Uri> getSliceDescendants(Uri uri) {
        ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient;
        try {
            contentProviderClientAcquireUnstableContentProviderClient = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            try {
            } finally {
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get slice descendants", e);
        }
        if (contentProviderClientAcquireUnstableContentProviderClient == null) {
            Log.w(TAG, TextUtils.formatSimple("Unknown URI: %s", uri));
            if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                contentProviderClientAcquireUnstableContentProviderClient.close();
            }
            return Collections.EMPTY_LIST;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("slice_uri", uri);
        ArrayList parcelableArrayList = contentProviderClientAcquireUnstableContentProviderClient.call(SliceProvider.METHOD_GET_DESCENDANTS, null, bundle).getParcelableArrayList(SliceProvider.EXTRA_SLICE_DESCENDANTS, Uri.class);
        if (contentProviderClientAcquireUnstableContentProviderClient != null) {
            contentProviderClientAcquireUnstableContentProviderClient.close();
        }
        return parcelableArrayList;
    }

    public Slice bindSlice(Uri uri, Set<SliceSpec> set) {
        Objects.requireNonNull(uri, "uri");
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            try {
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    Log.w(TAG, String.format("Unknown URI: %s", uri));
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    }
                    return null;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("slice_uri", uri);
                bundle.putParcelableArrayList(SliceProvider.EXTRA_SUPPORTED_SPECS, new ArrayList<>(set));
                Bundle bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call(SliceProvider.METHOD_SLICE, null, bundle);
                Bundle.setDefusable(bundleCall, true);
                if (bundleCall != null) {
                    Slice slice = (Slice) bundleCall.getParcelable("slice", Slice.class);
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    }
                    return slice;
                }
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    return null;
                }
                contentProviderClientAcquireUnstableContentProviderClient.close();
                return null;
            } finally {
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    public Uri mapIntentToUri(Intent intent) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriResolveStatic = resolveStatic(intent, contentResolver);
        if (uriResolveStatic != null) {
            return uriResolveStatic;
        }
        String authority = getAuthority(intent);
        if (authority == null) {
            return null;
        }
        Uri uriBuild = new Uri.Builder().scheme("content").authority(authority).build();
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = contentResolver.acquireUnstableContentProviderClient(uriBuild);
            try {
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    Log.w(TAG, String.format("Unknown URI: %s", uriBuild));
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    }
                    return null;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(SliceProvider.EXTRA_INTENT, intent);
                Bundle bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call(SliceProvider.METHOD_MAP_ONLY_INTENT, null, bundle);
                if (bundleCall != null) {
                    Uri uri = (Uri) bundleCall.getParcelable("slice", Uri.class);
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    }
                    return uri;
                }
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    return null;
                }
                contentProviderClientAcquireUnstableContentProviderClient.close();
                return null;
            } finally {
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    private String getAuthority(Intent intent) {
        Intent intent2 = new Intent(intent);
        if (!intent2.hasCategory(CATEGORY_SLICE)) {
            intent2.addCategory(CATEGORY_SLICE);
        }
        List<ResolveInfo> listQueryIntentContentProviders = this.mContext.getPackageManager().queryIntentContentProviders(intent2, 0);
        if (listQueryIntentContentProviders == null || listQueryIntentContentProviders.isEmpty()) {
            return null;
        }
        return listQueryIntentContentProviders.get(0).providerInfo.authority;
    }

    private Uri resolveStatic(Intent intent, ContentResolver contentResolver) {
        Objects.requireNonNull(intent, "intent");
        Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        Uri data = intent.getData();
        if (data != null && SliceProvider.SLICE_TYPE.equals(contentResolver.getType(data))) {
            return data;
        }
        ResolveInfo resolveInfoResolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 128);
        if (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null || resolveInfoResolveActivity.activityInfo.metaData == null || !resolveInfoResolveActivity.activityInfo.metaData.containsKey(SLICE_METADATA_KEY)) {
            return null;
        }
        return Uri.parse(resolveInfoResolveActivity.activityInfo.metaData.getString(SLICE_METADATA_KEY));
    }

    public Slice bindSlice(Intent intent, Set<SliceSpec> set) {
        Objects.requireNonNull(intent, "intent");
        Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uriResolveStatic = resolveStatic(intent, contentResolver);
        if (uriResolveStatic != null) {
            return bindSlice(uriResolveStatic, set);
        }
        String authority = getAuthority(intent);
        if (authority == null) {
            return null;
        }
        Uri uriBuild = new Uri.Builder().scheme("content").authority(authority).build();
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = contentResolver.acquireUnstableContentProviderClient(uriBuild);
            try {
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    Log.w(TAG, String.format("Unknown URI: %s", uriBuild));
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                    }
                    return null;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(SliceProvider.EXTRA_INTENT, intent);
                bundle.putParcelableArrayList(SliceProvider.EXTRA_SUPPORTED_SPECS, new ArrayList<>(set));
                Bundle bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call(SliceProvider.METHOD_MAP_INTENT, null, bundle);
                if (bundleCall != null) {
                    Slice slice = (Slice) bundleCall.getParcelable("slice", Slice.class);
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    }
                    return slice;
                }
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    return null;
                }
                contentProviderClientAcquireUnstableContentProviderClient.close();
                return null;
            } finally {
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int checkSlicePermission(Uri uri, int i, int i2) {
        try {
            return this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), i, i2, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantSlicePermission(String str, Uri uri) {
        try {
            this.mService.grantSlicePermission(this.mContext.getPackageName(), str, uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeSlicePermission(String str, Uri uri) {
        try {
            this.mService.revokeSlicePermission(this.mContext.getPackageName(), str, uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enforceSlicePermission(Uri uri, int i, int i2, String[] strArr) {
        try {
            if (!UserHandle.isSameApp(i2, Process.myUid()) && this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), i, i2, strArr) == -1) {
                throw new SecurityException("User " + i2 + " does not have slice permission for " + uri + MediaMetrics.SEPARATOR);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermissionFromUser(Uri uri, String str, boolean z) {
        try {
            this.mService.grantPermissionFromUser(uri, str, this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
