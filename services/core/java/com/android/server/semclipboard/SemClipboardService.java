package com.android.server.semclipboard;

import android.app.AppOpsManager;
import android.app.IUriGrantsManager;
import android.app.UriGrantsManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.Context;
import android.content.IClipboard;
import android.net.Uri;
import android.os.Binder;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.clipboard.IClipboardDataPasteEvent;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.ClipboardPolicyObserver;
import android.sec.clipboard.util.CompatabilityHelper;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.sec.clipboard.util.SemClipboardPolicy;

import com.android.server.LocalServices;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;

import com.samsung.android.content.clipboard.IOnClipboardEventListener;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemIntentClipData;
import com.samsung.android.content.clipboard.data.SemTextClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import com.samsung.android.content.clipboard.data.SemUriListClipData;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemClipboardService extends IClipboardService.Stub {
    public static IClipboard sService;
    public final AppOpsManager mAppOps;
    public IClipboardDataPasteEvent mClPasteEvent;
    public final RemoteCallbackList mClipboardEventListeners = new RemoteCallbackList();
    public final Context mContext;
    public int mEnableFormatId;
    public final IBinder mPermissionOwner;
    public final IUriGrantsManager mUgm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClipboardEventListenerInfo {
        public final String mPackageName;
        public final int mUid;

        public ClipboardEventListenerInfo(int i, String str) {
            this.mUid = i;
            this.mPackageName = str;
        }
    }

    public SemClipboardService(Context context) {
        Log.d("SemClipboardService", "SemClipboardService start");
        this.mContext = context;
        CompatabilityHelper.migrationClipboard();
        this.mUgm = UriGrantsManager.getService();
        this.mPermissionOwner =
                ((UriGrantsManagerService.LocalService)
                                ((UriGrantsManagerInternal)
                                        LocalServices.getService(UriGrantsManagerInternal.class)))
                        .newUriPermissionOwner("clipboard");
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.RCP_CONTENT_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context));
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
    }

    public static SemClipData convertClipToSemClip(ClipData clipData) {
        SemUriListClipData semUriListClipData = null;
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipDescription description = clipData.getDescription();
            CharSequence label = description.getLabel();
            if (!"SemUriListClipData".equals(label) && !"startDoPDrag".equals(label)) {
                ClipData.Item itemAt = clipData.getItemAt(0);
                if (description.hasMimeType("text/html")) {
                    SemHtmlClipData semHtmlClipData = new SemHtmlClipData();
                    semHtmlClipData.setHtml(itemAt.getHtmlText());
                    semHtmlClipData.setClipData(clipData);
                    return semHtmlClipData;
                }
                if (itemAt.getText() != null) {
                    SemTextClipData semTextClipData = new SemTextClipData();
                    semTextClipData.setText(itemAt.getText());
                    semTextClipData.setClipData(clipData);
                    return semTextClipData;
                }
                if (description.hasMimeType("text/vnd.android.intent")) {
                    SemIntentClipData semIntentClipData = new SemIntentClipData();
                    semIntentClipData.setIntent(itemAt.getIntent());
                    semIntentClipData.setClipData(clipData);
                    return semIntentClipData;
                }
                if (!description.hasMimeType("text/uri-list") && itemAt.getUri() == null) {
                    return null;
                }
                SemUriClipData semUriClipData = new SemUriClipData();
                semUriClipData.setUri(itemAt.getUri());
                semUriClipData.setClipData(clipData);
                return semUriClipData;
            }
            semUriListClipData = new SemUriListClipData();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                arrayList.add(clipData.getItemAt(i).getUri());
            }
            semUriListClipData.setUriList(arrayList);
            semUriListClipData.setClipData(clipData);
        }
        return semUriListClipData;
    }

    public static IClipboard getService() {
        IClipboard iClipboard = sService;
        if (iClipboard != null) {
            return iClipboard;
        }
        IClipboard asInterface =
                IClipboard.Stub.asInterface(ServiceManager.getService("clipboard"));
        sService = asInterface;
        if (asInterface == null) {
            Log.secE("SemClipboardService", "Original clipboard service is null!");
        }
        return sService;
    }

    public final void addClipboardEventListener(
            IOnClipboardEventListener iOnClipboardEventListener, String str) {
        try {
            synchronized (this.mClipboardEventListeners) {
                this.mClipboardEventListeners.register(
                        iOnClipboardEventListener,
                        new ClipboardEventListenerInfo(Binder.getCallingUid(), str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final ClipData convertSemClipToClip(SemClipData semClipData) {
        if (semClipData == null) {
            return null;
        }
        int clipType = semClipData.getClipType();
        if (clipType == 1) {
            SemTextClipData semTextClipData = (SemTextClipData) semClipData;
            return ClipData.newPlainText(semTextClipData.getLabel(), semTextClipData.getText());
        }
        if (clipType == 2) {
            SemImageClipData semImageClipData = (SemImageClipData) semClipData;
            String bitmapPath = semImageClipData.getBitmapPath();
            File file = new File("/data/semclipboard", "temp");
            ParcelFileDescriptor parcelFileDescriptor = semImageClipData.getParcelFileDescriptor();
            if ((parcelFileDescriptor == null
                            || !FileHelper.getInstance().fileCopy(parcelFileDescriptor, file))
                    && !FileHelper.getInstance().fileCopy(new File(bitmapPath), file)) {
                return null;
            }
            semImageClipData.setImagePath(file.getAbsolutePath());
            FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
            semImageClipData.insertContentUri(this.mContext, semImageClipData.getBitmapPath());
            return ClipData.newUri(
                    this.mContext.getContentResolver(),
                    "SemImageClipData",
                    semImageClipData.getContentUri());
        }
        if (clipType == 4) {
            SemHtmlClipData semHtmlClipData = (SemHtmlClipData) semClipData;
            return ClipData.newHtmlText(
                    semHtmlClipData.getLabel(),
                    semHtmlClipData.getPlainText(),
                    semHtmlClipData.getHtml());
        }
        if (clipType == 8) {
            SemIntentClipData semIntentClipData = (SemIntentClipData) semClipData;
            return ClipData.newIntent(semIntentClipData.getLabel(), semIntentClipData.getIntent());
        }
        if (clipType == 16) {
            SemUriClipData semUriClipData = (SemUriClipData) semClipData;
            return ClipData.newUri(
                    this.mContext.getContentResolver(),
                    semUriClipData.getLabel(),
                    semUriClipData.getUri());
        }
        if (clipType != 32) {
            return null;
        }
        ArrayList uriList = ((SemUriListClipData) semClipData).getUriList();
        ClipData clipData =
                new ClipData(
                        "SemUriListClipData",
                        new String[] {"text/uri-list"},
                        new ClipData.Item((Uri) uriList.get(0)));
        for (int i = 1; i < uriList.size(); i++) {
            clipData.addItem(new ClipData.Item((Uri) uriList.get(i)));
        }
        return clipData;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {}

    public final int getFilter() {
        return this.mEnableFormatId;
    }

    public final SemClipData getPrimarySemClip(String str, int i) {
        try {
            IClipboard service = getService();
            if (service != null) {
                return convertClipToSemClip(
                        service.getPrimaryClip(
                                str,
                                this.mContext.getAttributionTag(),
                                i,
                                this.mContext.getDeviceId()));
            }
            return null;
        } catch (Exception e) {
            Log.e("SemClipboardService", "getPrimarySemClip, Exception : " + e.getMessage());
            return null;
        }
    }

    public final void grantUriPermission(int i, Uri uri) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgm.grantUriPermissionFromOwner(
                    this.mPermissionOwner,
                    i,
                    KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME,
                    ContentProvider.getUriWithoutUserId(uri),
                    1,
                    ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)),
                    0);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean hasPrimaryClip(String str, int i) {
        try {
            IClipboard service = getService();
            if (service != null) {
                return service.hasPrimaryClip(
                        str, this.mContext.getAttributionTag(), i, this.mContext.getDeviceId());
            }
            return false;
        } catch (Exception e) {
            Log.e("SemClipboardService", "hasPrimaryClip, Exception : " + e.getMessage());
            return false;
        }
    }

    public final boolean isEnabled(int i) {
        return ClipboardPolicyObserver.getInstance(this.mContext).isClipboardAllowed(i);
    }

    public final boolean pasteClipData(ClipData clipData, String str, int i) {
        if (this.mClPasteEvent == null) {
            Log.secW("SemClipboardService", "ClipboardDataPasteEvent is null.");
            return false;
        }
        try {
            SemClipData convertClipToSemClip = convertClipToSemClip(clipData);
            if (convertClipToSemClip != null) {
                this.mClPasteEvent.onPaste(convertClipToSemClip);
                return true;
            }
        } catch (Exception e) {
            Log.e("SemClipboardService", "pasteClipData, Exception : " + e.getMessage());
            updateFilter(0, null);
        }
        return false;
    }

    public final void removeClipboardEventListener(
            IOnClipboardEventListener iOnClipboardEventListener) {
        try {
            synchronized (this.mClipboardEventListeners) {
                this.mClipboardEventListeners.unregister(iOnClipboardEventListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0179 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPrimaryClip(android.content.ClipData r17, int r18) {
        /*
            Method dump skipped, instructions count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.semclipboard.SemClipboardService.setPrimaryClip(android.content.ClipData,"
                    + " int):void");
    }

    public final void setPrimarySemClip(SemClipData semClipData, String str, int i) {
        try {
            if (!SemClipboardPolicy.getInstance().canAccessSemClipboard(this.mContext, str, i)) {
                Log.e("SemClipboardService", "setPrimarySemClip failed : isNotSemApp");
                return;
            }
            ClipData convertSemClipToClip = convertSemClipToClip(semClipData);
            IClipboard service = getService();
            if (convertSemClipToClip == null || service == null) {
                return;
            }
            service.setPrimaryClip(
                    convertSemClipToClip,
                    str,
                    this.mContext.getAttributionTag(),
                    i,
                    this.mContext.getDeviceId());
        } catch (Exception e) {
            Log.e("SemClipboardService", "setPrimarySemClip, Exception : " + e.getMessage());
        }
    }

    public final void updateFilter(int i, IClipboardDataPasteEvent iClipboardDataPasteEvent) {
        this.mEnableFormatId = i;
        this.mClPasteEvent = iClipboardDataPasteEvent;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mClipboardEventListeners) {
                try {
                    int beginBroadcast = this.mClipboardEventListeners.beginBroadcast();
                    for (int i2 = 0; i2 < beginBroadcast; i2++) {
                        try {
                            ClipboardEventListenerInfo clipboardEventListenerInfo =
                                    (ClipboardEventListenerInfo)
                                            this.mClipboardEventListeners.getBroadcastCookie(i2);
                            if (this.mAppOps.checkOpNoThrow(
                                            29,
                                            clipboardEventListenerInfo.mUid,
                                            clipboardEventListenerInfo.mPackageName)
                                    == 0) {
                                this.mClipboardEventListeners
                                        .getBroadcastItem(i2)
                                        .onUpdateFilter(i);
                            }
                        } catch (RemoteException unused) {
                        } catch (Throwable th) {
                            this.mClipboardEventListeners.finishBroadcast();
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    this.mClipboardEventListeners.finishBroadcast();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
