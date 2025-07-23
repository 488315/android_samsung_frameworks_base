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
        ContentProviderClient acquireUnstableContentProviderClient;
        try {
            acquireUnstableContentProviderClient = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            try {
            } finally {
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get slice descendants", e);
        }
        if (acquireUnstableContentProviderClient == null) {
            Log.w(TAG, TextUtils.formatSimple("Unknown URI: %s", uri));
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
            return Collections.EMPTY_LIST;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("slice_uri", uri);
        ArrayList parcelableArrayList = acquireUnstableContentProviderClient.call(SliceProvider.METHOD_GET_DESCENDANTS, null, bundle).getParcelableArrayList(SliceProvider.EXTRA_SLICE_DESCENDANTS, Uri.class);
        if (acquireUnstableContentProviderClient != null) {
            acquireUnstableContentProviderClient.close();
        }
        return parcelableArrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0022, code lost:
    
        if (r3 != null) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.slice.Slice bindSlice(android.net.Uri r4, java.util.Set<android.app.slice.SliceSpec> r5) {
        /*
            r3 = this;
            java.lang.String r0 = "uri"
            java.util.Objects.requireNonNull(r4, r0)
            android.content.Context r3 = r3.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            r0 = 0
            android.content.ContentProviderClient r3 = r3.acquireUnstableContentProviderClient(r4)     // Catch: android.os.RemoteException -> L6b
            if (r3 != 0) goto L28
            java.lang.String r5 = "SliceManager"
            java.lang.String r1 = "Unknown URI: %s"
            java.lang.Object[] r4 = new java.lang.Object[]{r4}     // Catch: java.lang.Throwable -> L5f
            java.lang.String r4 = java.lang.String.format(r1, r4)     // Catch: java.lang.Throwable -> L5f
            android.util.Log.w(r5, r4)     // Catch: java.lang.Throwable -> L5f
            if (r3 == 0) goto L27
        L24:
            r3.close()     // Catch: android.os.RemoteException -> L6b
        L27:
            return r0
        L28:
            android.os.Bundle r1 = new android.os.Bundle     // Catch: java.lang.Throwable -> L5f
            r1.<init>()     // Catch: java.lang.Throwable -> L5f
            java.lang.String r2 = "slice_uri"
            r1.putParcelable(r2, r4)     // Catch: java.lang.Throwable -> L5f
            java.lang.String r4 = "supported_specs"
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L5f
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L5f
            r1.putParcelableArrayList(r4, r2)     // Catch: java.lang.Throwable -> L5f
            java.lang.String r4 = "bind_slice"
            android.os.Bundle r4 = r3.call(r4, r0, r1)     // Catch: java.lang.Throwable -> L5f
            r5 = 1
            android.os.Bundle.setDefusable(r4, r5)     // Catch: java.lang.Throwable -> L5f
            if (r4 != 0) goto L4e
            if (r3 == 0) goto L4d
            goto L24
        L4d:
            return r0
        L4e:
            java.lang.String r5 = "slice"
            java.lang.Class<android.app.slice.Slice> r1 = android.app.slice.Slice.class
            java.lang.Object r4 = r4.getParcelable(r5, r1)     // Catch: java.lang.Throwable -> L5f
            android.app.slice.Slice r4 = (android.app.slice.Slice) r4     // Catch: java.lang.Throwable -> L5f
            if (r3 == 0) goto L5e
            r3.close()     // Catch: android.os.RemoteException -> L6b
        L5e:
            return r4
        L5f:
            r4 = move-exception
            if (r3 == 0) goto L6a
            r3.close()     // Catch: java.lang.Throwable -> L66
            goto L6a
        L66:
            r3 = move-exception
            r4.addSuppressed(r3)     // Catch: android.os.RemoteException -> L6b
        L6a:
            throw r4     // Catch: android.os.RemoteException -> L6b
        L6b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.bindSlice(android.net.Uri, java.util.Set):android.app.slice.Slice");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003d, code lost:
    
        if (r0 != null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.net.Uri mapIntentToUri(android.content.Intent r5) {
        /*
            r4 = this;
            android.content.Context r0 = r4.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r1 = r4.resolveStatic(r5, r0)
            if (r1 == 0) goto Ld
            return r1
        Ld:
            java.lang.String r4 = r4.getAuthority(r5)
            r1 = 0
            if (r4 != 0) goto L15
            return r1
        L15:
            android.net.Uri$Builder r2 = new android.net.Uri$Builder
            r2.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r2 = r2.scheme(r3)
            android.net.Uri$Builder r4 = r2.authority(r4)
            android.net.Uri r4 = r4.build()
            android.content.ContentProviderClient r0 = r0.acquireUnstableContentProviderClient(r4)     // Catch: android.os.RemoteException -> L78
            if (r0 != 0) goto L43
            java.lang.String r5 = "SliceManager"
            java.lang.String r2 = "Unknown URI: %s"
            java.lang.Object[] r4 = new java.lang.Object[]{r4}     // Catch: java.lang.Throwable -> L6c
            java.lang.String r4 = java.lang.String.format(r2, r4)     // Catch: java.lang.Throwable -> L6c
            android.util.Log.w(r5, r4)     // Catch: java.lang.Throwable -> L6c
            if (r0 == 0) goto L42
        L3f:
            r0.close()     // Catch: android.os.RemoteException -> L78
        L42:
            return r1
        L43:
            android.os.Bundle r4 = new android.os.Bundle     // Catch: java.lang.Throwable -> L6c
            r4.<init>()     // Catch: java.lang.Throwable -> L6c
            java.lang.String r2 = "slice_intent"
            r4.putParcelable(r2, r5)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r5 = "map_only"
            android.os.Bundle r4 = r0.call(r5, r1, r4)     // Catch: java.lang.Throwable -> L6c
            if (r4 != 0) goto L5b
            if (r0 == 0) goto L5a
            goto L3f
        L5a:
            return r1
        L5b:
            java.lang.String r5 = "slice"
            java.lang.Class<android.net.Uri> r2 = android.net.Uri.class
            java.lang.Object r4 = r4.getParcelable(r5, r2)     // Catch: java.lang.Throwable -> L6c
            android.net.Uri r4 = (android.net.Uri) r4     // Catch: java.lang.Throwable -> L6c
            if (r0 == 0) goto L6b
            r0.close()     // Catch: android.os.RemoteException -> L78
        L6b:
            return r4
        L6c:
            r4 = move-exception
            if (r0 == 0) goto L77
            r0.close()     // Catch: java.lang.Throwable -> L73
            goto L77
        L73:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch: android.os.RemoteException -> L78
        L77:
            throw r4     // Catch: android.os.RemoteException -> L78
        L78:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.mapIntentToUri(android.content.Intent):android.net.Uri");
    }

    private String getAuthority(Intent intent) {
        Intent intent2 = new Intent(intent);
        if (!intent2.hasCategory(CATEGORY_SLICE)) {
            intent2.addCategory(CATEGORY_SLICE);
        }
        List<ResolveInfo> queryIntentContentProviders = this.mContext.getPackageManager().queryIntentContentProviders(intent2, 0);
        if (queryIntentContentProviders == null || queryIntentContentProviders.isEmpty()) {
            return null;
        }
        return queryIntentContentProviders.get(0).providerInfo.authority;
    }

    private Uri resolveStatic(Intent intent, ContentResolver contentResolver) {
        Objects.requireNonNull(intent, "intent");
        Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        Uri data = intent.getData();
        if (data != null && SliceProvider.SLICE_TYPE.equals(contentResolver.getType(data))) {
            return data;
        }
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 128);
        if (resolveActivity == null || resolveActivity.activityInfo == null || resolveActivity.activityInfo.metaData == null || !resolveActivity.activityInfo.metaData.containsKey(SLICE_METADATA_KEY)) {
            return null;
        }
        return Uri.parse(resolveActivity.activityInfo.metaData.getString(SLICE_METADATA_KEY));
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0065, code lost:
    
        if (r0 != null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.slice.Slice bindSlice(android.content.Intent r5, java.util.Set<android.app.slice.SliceSpec> r6) {
        /*
            r4 = this;
            java.lang.String r0 = "intent"
            java.util.Objects.requireNonNull(r5, r0)
            android.content.ComponentName r0 = r5.getComponent()
            if (r0 != 0) goto L1a
            java.lang.String r0 = r5.getPackage()
            if (r0 != 0) goto L1a
            android.net.Uri r0 = r5.getData()
            if (r0 == 0) goto L18
            goto L1a
        L18:
            r0 = 0
            goto L1b
        L1a:
            r0 = 1
        L1b:
            java.lang.String r1 = "Slice intent must be explicit %s"
            java.lang.Object[] r2 = new java.lang.Object[]{r5}
            com.android.internal.util.Preconditions.checkArgument(r0, r1, r2)
            android.content.Context r0 = r4.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r1 = r4.resolveStatic(r5, r0)
            if (r1 == 0) goto L35
            android.app.slice.Slice r4 = r4.bindSlice(r1, r6)
            return r4
        L35:
            java.lang.String r4 = r4.getAuthority(r5)
            r1 = 0
            if (r4 != 0) goto L3d
            return r1
        L3d:
            android.net.Uri$Builder r2 = new android.net.Uri$Builder
            r2.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r2 = r2.scheme(r3)
            android.net.Uri$Builder r4 = r2.authority(r4)
            android.net.Uri r4 = r4.build()
            android.content.ContentProviderClient r0 = r0.acquireUnstableContentProviderClient(r4)     // Catch: android.os.RemoteException -> Lab
            if (r0 != 0) goto L6b
            java.lang.String r5 = "SliceManager"
            java.lang.String r6 = "Unknown URI: %s"
            java.lang.Object[] r4 = new java.lang.Object[]{r4}     // Catch: java.lang.Throwable -> L9f
            java.lang.String r4 = java.lang.String.format(r6, r4)     // Catch: java.lang.Throwable -> L9f
            android.util.Log.w(r5, r4)     // Catch: java.lang.Throwable -> L9f
            if (r0 == 0) goto L6a
        L67:
            r0.close()     // Catch: android.os.RemoteException -> Lab
        L6a:
            return r1
        L6b:
            android.os.Bundle r4 = new android.os.Bundle     // Catch: java.lang.Throwable -> L9f
            r4.<init>()     // Catch: java.lang.Throwable -> L9f
            java.lang.String r2 = "slice_intent"
            r4.putParcelable(r2, r5)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r5 = "supported_specs"
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L9f
            r2.<init>(r6)     // Catch: java.lang.Throwable -> L9f
            r4.putParcelableArrayList(r5, r2)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r5 = "map_slice"
            android.os.Bundle r4 = r0.call(r5, r1, r4)     // Catch: java.lang.Throwable -> L9f
            if (r4 != 0) goto L8e
            if (r0 == 0) goto L8d
            goto L67
        L8d:
            return r1
        L8e:
            java.lang.String r5 = "slice"
            java.lang.Class<android.app.slice.Slice> r6 = android.app.slice.Slice.class
            java.lang.Object r4 = r4.getParcelable(r5, r6)     // Catch: java.lang.Throwable -> L9f
            android.app.slice.Slice r4 = (android.app.slice.Slice) r4     // Catch: java.lang.Throwable -> L9f
            if (r0 == 0) goto L9e
            r0.close()     // Catch: android.os.RemoteException -> Lab
        L9e:
            return r4
        L9f:
            r4 = move-exception
            if (r0 == 0) goto Laa
            r0.close()     // Catch: java.lang.Throwable -> La6
            goto Laa
        La6:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch: android.os.RemoteException -> Lab
        Laa:
            throw r4     // Catch: android.os.RemoteException -> Lab
        Lab:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.bindSlice(android.content.Intent, java.util.Set):android.app.slice.Slice");
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
