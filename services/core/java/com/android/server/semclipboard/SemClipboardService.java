package com.android.server.semclipboard;

import android.app.AppOpsManager;
import android.app.IUriGrantsManager;
import android.app.UriGrantsManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.IClipboard;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.clipboard.IClipboardDataPasteEvent;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.data.file.WrapFileClipData;
import android.sec.clipboard.util.ClipboardPolicyObserver;
import android.sec.clipboard.util.CompatabilityHelper;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.sec.clipboard.util.SemClipboardPolicy;
import com.android.server.LocalServices;
import com.android.server.uri.UriGrantsManagerInternal;
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
import java.util.Iterator;

/* loaded from: classes3.dex */
public class SemClipboardService extends IClipboardService.Stub {
    public static IClipboard sService;
    public final AppOpsManager mAppOps;
    public IClipboardDataPasteEvent mClPasteEvent;
    public final RemoteCallbackList mClipboardEventListeners = new RemoteCallbackList();
    public Context mContext;
    public int mEnableFormatId;
    public final IBinder mPermissionOwner;
    public final IUriGrantsManager mUgm;
    public final UriGrantsManagerInternal mUgmInternal;

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public class ClipboardEventListenerInfo {
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
        UriGrantsManagerInternal uriGrantsManagerInternal = (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mUgmInternal = uriGrantsManagerInternal;
        this.mPermissionOwner = uriGrantsManagerInternal.newUriPermissionOwner("clipboard");
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.RCP_CONTENT_URI, true, ClipboardPolicyObserver.getInstance(this.mContext));
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        this.mContext.getContentResolver().registerContentObserver(ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI, true, ClipboardPolicyObserver.getInstance(this.mContext), -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.knox.clipboard.sync");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        this.mContext.registerReceiver(new KNOXReceiver(), intentFilter);
    }

    public class KNOXReceiver extends BroadcastReceiver {
        public KNOXReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                Log.secI("SemClipboardService", "ACTION_BOOT_COMPLETED. Check and Start to migrate semclipboard to keyboard.");
                SemClipboardService.this.migrationClipboardToKeyboard();
            }
        }
    }

    public void addClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener, String str) {
        try {
            synchronized (this.mClipboardEventListeners) {
                this.mClipboardEventListeners.register(iOnClipboardEventListener, new ClipboardEventListenerInfo(Binder.getCallingUid(), str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeClipboardEventListener(IOnClipboardEventListener iOnClipboardEventListener) {
        try {
            synchronized (this.mClipboardEventListeners) {
                this.mClipboardEventListeners.unregister(iOnClipboardEventListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFilter() {
        return this.mEnableFormatId;
    }

    public void updateFilter(int i, IClipboardDataPasteEvent iClipboardDataPasteEvent) {
        this.mEnableFormatId = i;
        this.mClPasteEvent = iClipboardDataPasteEvent;
        notifyFilterUpdated(i);
    }

    public static IClipboard getService() {
        IClipboard iClipboard = sService;
        if (iClipboard != null) {
            return iClipboard;
        }
        IClipboard asInterface = IClipboard.Stub.asInterface(ServiceManager.getService("clipboard"));
        sService = asInterface;
        if (asInterface == null) {
            Log.secE("SemClipboardService", "Original clipboard service is null!");
        }
        return sService;
    }

    public final void notifyFilterUpdated(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mClipboardEventListeners) {
                int beginBroadcast = this.mClipboardEventListeners.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        ClipboardEventListenerInfo clipboardEventListenerInfo = (ClipboardEventListenerInfo) this.mClipboardEventListeners.getBroadcastCookie(i2);
                        if (this.mAppOps.checkOpNoThrow(29, clipboardEventListenerInfo.mUid, clipboardEventListenerInfo.mPackageName) == 0) {
                            this.mClipboardEventListeners.getBroadcastItem(i2).onUpdateFilter(i);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryClip(ClipData clipData, int i) {
        Log.secI("SemClipboardService", "copied from " + i);
        if (clipData == null || !isEnabled(UserHandle.semGetUserId(i))) {
            return;
        }
        insertToClipboard(clipData, i);
        notifyClipboardEventListeners(clipData.getDescription().getExtras() != null && clipData.getDescription().getExtras().getBoolean("direct_clip") ? 7 : 1);
    }

    public final Uri insertToClipboard(ClipData clipData, int i) {
        int itemCount = clipData.getItemCount();
        if (itemCount <= 0) {
            return null;
        }
        ClipDescription description = clipData.getDescription();
        CharSequence label = description.getLabel();
        PersistableBundle extras = description.getExtras();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("caller_app_uid", Integer.valueOf(i));
            contentValues.put("clip_label", label != null ? label.toString() : null);
            if (extras != null) {
                contentValues.put("android.content.extra.IS_SENSITIVE", Boolean.valueOf(extras.getBoolean("android.content.extra.IS_SENSITIVE")));
                contentValues.put("com.microsoft.appmanager", Boolean.valueOf(extras.containsKey("com.microsoft.appmanager")));
                contentValues.put("direct_clip", Boolean.valueOf(extras.getBoolean("direct_clip")));
            }
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < description.getMimeTypeCount(); i2++) {
                if (i2 != 0) {
                    sb.append(",");
                }
                sb.append(description.getMimeType(i2));
            }
            contentValues.put("clip_mimetypes", sb.toString());
            ClipData.Item itemAt = clipData.getItemAt(0);
            contentValues.put("clip_text", itemAt.getText() != null ? itemAt.getText().toString() : null);
            contentValues.put("clip_html", itemAt.getHtmlText());
            String uri = itemAt.getUri() != null ? itemAt.getUri().toString() : null;
            if (uri != null) {
                grantUriPermission(Uri.parse(uri), i, KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME, 0);
            }
            contentValues.put("clip_uri", uri);
            if ("startDoPDrag".equals(label) || itemCount > 1) {
                StringBuilder sb2 = new StringBuilder();
                for (int i3 = 0; i3 < itemCount; i3++) {
                    if (i3 != 0) {
                        sb2.append(",");
                    }
                    sb2.append(clipData.getItemAt(i3).getUri().toString());
                }
                contentValues.put("clip_uri_list", sb2.toString());
            }
            return this.mContext.getContentResolver().insert(Uri.parse("content://com.samsung.android.honeyboard.provider.RichcontentProvider/clipboard"), contentValues);
        } catch (Exception e) {
            Log.e("SemClipboardService", "Exception occurs in insertContentUri because " + e.getMessage());
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setPrimarySemClip(SemClipData semClipData, String str, int i) {
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
            service.setPrimaryClip(convertSemClipToClip, str, this.mContext.getAttributionTag(), i, this.mContext.getDeviceId());
        } catch (Exception e) {
            Log.e("SemClipboardService", "setPrimarySemClip, Exception : " + e.getMessage());
        }
    }

    public SemClipData getPrimarySemClip(String str, int i) {
        try {
            IClipboard service = getService();
            if (service != null) {
                return convertClipToSemClip(service.getPrimaryClip(str, this.mContext.getAttributionTag(), i, this.mContext.getDeviceId()));
            }
            return null;
        } catch (Exception e) {
            Log.e("SemClipboardService", "getPrimarySemClip, Exception : " + e.getMessage());
            return null;
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
            if ((parcelFileDescriptor == null || !FileHelper.getInstance().fileCopy(parcelFileDescriptor, file)) && !FileHelper.getInstance().fileCopy(new File(bitmapPath), file)) {
                return null;
            }
            semImageClipData.setImagePath(file.getAbsolutePath());
            FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
            semImageClipData.insertContentUri(this.mContext, semImageClipData.getBitmapPath());
            return ClipData.newUri(this.mContext.getContentResolver(), "SemImageClipData", semImageClipData.getContentUri());
        }
        if (clipType == 4) {
            SemHtmlClipData semHtmlClipData = (SemHtmlClipData) semClipData;
            return ClipData.newHtmlText(semHtmlClipData.getLabel(), semHtmlClipData.getPlainText(), semHtmlClipData.getHtml());
        }
        if (clipType == 8) {
            SemIntentClipData semIntentClipData = (SemIntentClipData) semClipData;
            return ClipData.newIntent(semIntentClipData.getLabel(), semIntentClipData.getIntent());
        }
        if (clipType == 16) {
            SemUriClipData semUriClipData = (SemUriClipData) semClipData;
            return ClipData.newUri(this.mContext.getContentResolver(), semUriClipData.getLabel(), semUriClipData.getUri());
        }
        if (clipType != 32) {
            return null;
        }
        ArrayList uriList = ((SemUriListClipData) semClipData).getUriList();
        ClipData clipData = new ClipData("SemUriListClipData", new String[]{"text/uri-list"}, new ClipData.Item((Uri) uriList.get(0)));
        for (int i = 1; i < uriList.size(); i++) {
            clipData.addItem(new ClipData.Item((Uri) uriList.get(i)));
        }
        return clipData;
    }

    public final SemClipData convertClipToSemClip(ClipData clipData) {
        SemUriListClipData semUriListClipData = null;
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipDescription description = clipData.getDescription();
            CharSequence label = description.getLabel();
            if ("SemUriListClipData".equals(label) || "startDoPDrag".equals(label)) {
                semUriListClipData = new SemUriListClipData();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    arrayList.add(clipData.getItemAt(i).getUri());
                }
                semUriListClipData.setUriList(arrayList);
                semUriListClipData.setClipData(clipData);
            } else {
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
        }
        return semUriListClipData;
    }

    public boolean hasPrimaryClip(String str, int i) {
        try {
            IClipboard service = getService();
            if (service != null) {
                return service.hasPrimaryClip(str, this.mContext.getAttributionTag(), i, this.mContext.getDeviceId());
            }
            return false;
        } catch (Exception e) {
            Log.e("SemClipboardService", "hasPrimaryClip, Exception : " + e.getMessage());
            return false;
        }
    }

    public boolean pasteClipData(ClipData clipData, String str, int i) {
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

    public boolean isEnabled(int i) {
        return ClipboardPolicyObserver.getInstance(this.mContext).isClipboardAllowed(i);
    }

    public final void notifyClipboardEventListeners(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mClipboardEventListeners) {
                int beginBroadcast = this.mClipboardEventListeners.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        ClipboardEventListenerInfo clipboardEventListenerInfo = (ClipboardEventListenerInfo) this.mClipboardEventListeners.getBroadcastCookie(i2);
                        if (this.mAppOps.checkOpNoThrow(29, clipboardEventListenerInfo.mUid, clipboardEventListenerInfo.mPackageName) == 0) {
                            this.mClipboardEventListeners.getBroadcastItem(i2).onClipboardEvent(i, (SemClipData) null);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void grantUriPermission(Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgm.grantUriPermissionFromOwner(this.mPermissionOwner, i, str, ContentProvider.getUriWithoutUserId(uri), 1, ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)), i2);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void migrationClipboardToKeyboard() {
        try {
            File file = new File("/data/semclipboard");
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    migrationSemClipboardAsUser(file, 0);
                    for (int i = 10; i < 200; i++) {
                        File file2 = new File("/data/semclipboard/" + i);
                        File[] listFiles2 = file2.listFiles();
                        if (file2.exists() && listFiles2 != null && listFiles2.length > 0) {
                            migrationSemClipboardAsUser(file2, i);
                        }
                    }
                    CompatabilityHelper.recursiveDelete(file);
                    Log.d("SemClipboardService", "clipboard to keyboard migration is success.");
                    return;
                }
                Log.d("SemClipboardService", "semclipboard folder is empty.");
                return;
            }
            Log.d("SemClipboardService", "semclipboard folder is not exist.");
        } catch (Exception e) {
            Log.e("SemClipboardService", "Exception occurs in migrationClipboardToKeyboard." + e);
        }
    }

    public final void migrationSemClipboardAsUser(File file, int i) {
        ArrayList load;
        File file2 = new File(file, "clips.info");
        if (!file2.exists() || (load = load(file2)) == null) {
            return;
        }
        for (int i2 = 0; i2 < load.size(); i2++) {
            insertToClipboardForMigration(((WrapFileClipData) load.get(i2)).getClipData(), i);
        }
    }

    public ArrayList load(File file) {
        ArrayList arrayList = new ArrayList(40);
        if (FileHelper.getInstance().checkFile(file)) {
            Log.d("SemClipboardService", "migration load ...info file");
            try {
                arrayList = (ArrayList) FileHelper.getInstance().loadObjectFile(file);
            } catch (Exception unused) {
                Log.e("SemClipboardService", "failed to load clips.info file");
                return null;
            }
        }
        return loadDataList(arrayList);
    }

    public final ArrayList loadDataList(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(40);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            WrapFileClipData wrapFileClipData = (WrapFileClipData) it.next();
            try {
                if (wrapFileClipData.load()) {
                    arrayList2.add(wrapFileClipData);
                }
            } catch (Exception unused) {
                Log.e("SemClipboardService", "failed to load WrapFileClipData.");
            }
        }
        return arrayList2;
    }

    public final void insertToClipboardForMigration(SemClipData semClipData, int i) {
        if (semClipData != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("caller_app_uid", Integer.valueOf((int) semClipData.getCallerUid()));
                contentValues.put("type", Integer.valueOf(semClipData.getClipType()));
                contentValues.put("time_stamp", Long.valueOf(semClipData.getTimestamp()));
                contentValues.put("is_migration", Boolean.TRUE);
                contentValues.put("user_id", Integer.valueOf(i));
                contentValues.put("locked", Boolean.valueOf(semClipData.isProtected()));
                if (semClipData instanceof SemTextClipData) {
                    contentValues.put("clip_mimetypes", "text/plain");
                    contentValues.put("clip_text", ((SemTextClipData) semClipData).getText().toString());
                } else if (semClipData instanceof SemHtmlClipData) {
                    contentValues.put("clip_mimetypes", "text/html");
                    contentValues.put("clip_text", ((SemHtmlClipData) semClipData).getPlainText());
                    contentValues.put("clip_html", ((SemHtmlClipData) semClipData).getHtml());
                } else if (semClipData instanceof SemUriClipData) {
                    String uri = ((SemUriClipData) semClipData).getUri().toString();
                    grantUriPermission(Uri.parse(uri), (int) semClipData.getCallerUid(), KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME, 0);
                    contentValues.put("clip_mimetypes", "text/uri-list");
                    contentValues.put("clip_uri", uri);
                } else if (semClipData instanceof SemImageClipData) {
                    String uri2 = ((SemImageClipData) semClipData).getContentUri().toString();
                    grantUriPermission(Uri.parse(uri2), (int) semClipData.getCallerUid(), KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME, 0);
                    contentValues.put("clip_mimetypes", "image/jpeg");
                    contentValues.put("clip_uri", uri2);
                }
                this.mContext.getContentResolver().insert(Uri.parse("content://com.samsung.android.honeyboard.provider.RichcontentProvider/clipboard"), contentValues);
            } catch (Exception e) {
                Log.e("SemClipboardService", "Exception occurs in insertContentUri because " + e.getMessage());
            }
        }
    }
}
