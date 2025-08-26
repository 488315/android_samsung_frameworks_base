package com.samsung.android.content.clipboard;

import android.content.ClipData;
import android.content.Context;
import android.hardware.graphics.common.Dataspace;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.sec.clipboard.IClipboardDataPasteEvent;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.IOnClipboardEventListener;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemClipboardManager {
    public static final String ACTION_ADD_CLIP = "com.samsung.android.content.clipboard.action.ADD_CLIP";

    @Deprecated(forRemoval = true, since = "13.0")
    public static final String ACTION_CLIPBOARD_CLOSED = "com.samsung.android.content.clipboard.action.CLIPBOARD_CLOSED";

    @Deprecated(forRemoval = true, since = "13.0")
    public static final String ACTION_CLIPBOARD_OPENED = "com.samsung.android.content.clipboard.action.CLIPBOARD_OPENED";

    @Deprecated(forRemoval = true, since = "13.0")
    public static final String ACTION_DISMISS_CLIPBOARD = "com.samsung.android.content.clipboard.action.DISMISS_CLIPBOARD";
    public static final String ACTION_INTRODUCE_EDGE = "com.samsung.android.content.clipboard.action.INTRODUCE_EDGE";
    public static final String ACTION_REMOVE_CLIP = "com.samsung.android.content.clipboard.action.REMOVE_CLIP";
    public static final int CLIPBOARD_TYPE_FILTER = 255;
    public static final String EXTRA_DARK_THEME = "darkTheme";
    public static final String EXTRA_EXTRA_PATH = "extra_path";
    public static final String EXTRA_NO_TOAST = "noToast";
    public static final String EXTRA_PATH = "path";
    public static final String EXTRA_TYPE = "type";
    private static final String KEY_DATA = "data";
    private static final String KEY_FILTER = "filter";
    private static final String TAG = "SemClipboardManager";
    private static IClipboardService mSemService;
    private Context mContext;
    private Handler mHandler;
    private final InnerOnClipboardEventListener mInnerOnClipboardEventServiceListener;
    private final int SUCCESS_SET_DATA = 0;
    private final int FAIL_SET_DATA = 1;
    private final int SUCCESS_AND_SAVE_BITMAP = 2;
    private final int PROTECTED_DATA_MAX = 3;
    private boolean mIsMaximumSize = false;
    private OnPasteListener mPasteListener = null;
    private final IClipboardDataPasteEvent.Stub mOnPasteServiceListener = new IClipboardDataPasteEvent.Stub() { // from class: com.samsung.android.content.clipboard.SemClipboardManager.1
        @Override // android.sec.clipboard.IClipboardDataPasteEvent
        public void onPaste(SemClipData semClipData) {
            SemClipboardManager.this.requestPaste(semClipData);
        }
    };
    private ArrayList<SemClipboardEventListener> mOnClipboardEventServiceListeners = new ArrayList<>();

    public interface OnPasteListener {
        void onPaste(SemClipData semClipData);
    }

    private static class InnerOnClipboardEventListener extends IOnClipboardEventListener.Stub {
        private final WeakReference<Handler> mWeakHandler;

        public InnerOnClipboardEventListener(Handler handler) {
            this.mWeakHandler = new WeakReference<>(handler);
        }

        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onClipboardEvent(int i, SemClipData semClipData) {
            WeakReference<Handler> weakReference = this.mWeakHandler;
            if (weakReference == null || weakReference.get() == null) {
                Log.secD(SemClipboardManager.TAG, "onClipboardEvent mWeakHandler is null. mWeakHandler=" + this.mWeakHandler);
                return;
            }
            Handler handler = this.mWeakHandler.get();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", semClipData);
            message.what = i;
            message.setData(bundle);
            handler.sendMessage(message);
        }

        @Override // com.samsung.android.content.clipboard.IOnClipboardEventListener
        public void onUpdateFilter(int i) {
            WeakReference<Handler> weakReference = this.mWeakHandler;
            if (weakReference == null || weakReference.get() == null) {
                Log.secD(SemClipboardManager.TAG, "onUpdateFilter mWeakHandler is null. mWeakHandler=" + this.mWeakHandler);
                return;
            }
            Handler handler = this.mWeakHandler.get();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt(SemClipboardManager.KEY_FILTER, i);
            message.what = 5;
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public class ClipboardEvent {
        public static final int CLIPS_REFRESH = 7;
        public static final int CLIP_ADDED = 1;
        public static final int CLIP_DUPLICATED = 8;
        public static final int CLIP_UPDATE_LOCK = 16;
        public static final int CLIP_UPDATE_TIMESTAMP = 256;
        public static final int FILTER_UPDATED = 5;

        private ClipboardEvent(SemClipboardManager semClipboardManager) {
        }
    }

    public static class Type {
        public static final int ALL = -1;
        public static final int HTML = 4;
        public static final int IMAGE = 2;
        public static final int INTENT = 8;
        public static final int NONE = 0;
        public static final int TEXT = 1;
        public static final int URI = 16;
        public static final int URI_LIST = 32;

        private Type() {
        }
    }

    public interface OnAddClipResultListener {
        void onFailure(int i);

        void onSuccess();

        public static class Error {
            public static final int REASON_DUPLICATED = 2;
            public static final int REASON_EMPTY_DATA = 3;
            public static final int REASON_NOT_ALLOWED_TO_USE = 4;
            public static final int REASON_UNKNOWN = 1;

            private Error() {
            }
        }
    }

    public SemClipboardManager(Context context) {
        this.mContext = context;
        this.mHandler = new Handler(this.mContext.getMainLooper()) { // from class: com.samsung.android.content.clipboard.SemClipboardManager.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                SemClipboardManager.this.notifyEvent(message);
            }
        };
        this.mInnerOnClipboardEventServiceListener = new InnerOnClipboardEventListener(this.mHandler);
    }

    private static IClipboardService getSemService() {
        IClipboardService iClipboardService = mSemService;
        if (iClipboardService != null) {
            return iClipboardService;
        }
        IClipboardService iClipboardServiceAsInterface = IClipboardService.Stub.asInterface(ServiceManager.getService(Context.SEM_CLIPBOARD_SERVICE));
        mSemService = iClipboardServiceAsInterface;
        if (iClipboardServiceAsInterface == null) {
            Log.e(TAG, "Failed to get semclipboard service.");
        }
        return mSemService;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public boolean isShowing() {
        Log.d(TAG, "deprecated isShowing, calling package : " + this.mContext.getOpPackageName());
        return false;
    }

    public void addClip(Context context, SemClipData semClipData, OnAddClipResultListener onAddClipResultListener) {
        if (isEnabled("addClip")) {
            setPrimarySemClip(semClipData);
        }
    }

    public int setDataWithoutNoti(Context context, SemClipData semClipData) {
        setPrimarySemClip(semClipData);
        return 0;
    }

    public int setDataWithoutSendingOrginalClipboard(Context context, SemClipData semClipData) {
        setPrimarySemClip(semClipData);
        return 0;
    }

    public void setPrimarySemClip(SemClipData semClipData) {
        if (getSemService() != null) {
            Objects.requireNonNull(semClipData);
            if (!makeFileDescriptor(semClipData)) {
                Log.secE(TAG, "failed making file descriptor!");
                return;
            }
            try {
                getSemService().setPrimarySemClip(semClipData, this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in setPrimarySemClip, " + e.getMessage());
            }
            semClipData.closeParcelFileDescriptor();
        }
    }

    public SemClipData getLatestClip(int i) {
        if (isEnabled("getLatestClip") && getSemService() != null) {
            try {
                return getSemService().getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getLatestClip, " + e.getMessage());
            }
        }
        return null;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public int getCount() {
        if (isEnabled("getCount") && getSemService() != null) {
            try {
                return getSemService().hasPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getUserId()) ? 1 : 0;
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getCount, " + e.getMessage());
            }
        }
        return -1;
    }

    public void registerClipboardEventListener(SemClipboardEventListener semClipboardEventListener) {
        if (isEnabled("registerClipboardEventListener")) {
            if (getSemService() == null) {
                Log.secW(TAG, "registerClipboardUIInterface: Service is null.");
                return;
            }
            synchronized (this.mOnClipboardEventServiceListeners) {
                if (this.mOnClipboardEventServiceListeners.size() == 0) {
                    try {
                        getSemService().addClipboardEventListener(this.mInnerOnClipboardEventServiceListener, this.mContext.getOpPackageName());
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                if (semClipboardEventListener != null && !this.mOnClipboardEventServiceListeners.contains(semClipboardEventListener)) {
                    this.mOnClipboardEventServiceListeners.add(semClipboardEventListener);
                }
            }
        }
    }

    public void unregisterClipboardEventListener(SemClipboardEventListener semClipboardEventListener) {
        synchronized (this.mOnClipboardEventServiceListeners) {
            if (this.mOnClipboardEventServiceListeners.remove(semClipboardEventListener) && this.mOnClipboardEventServiceListeners.size() == 0) {
                try {
                    getSemService().removeClipboardEventListener(this.mInnerOnClipboardEventServiceListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void filterClip(int i, OnPasteListener onPasteListener) {
        if (isEnabled("filterClip") && getSemService() != null) {
            try {
                getSemService().updateFilter(i, this.mOnPasteServiceListener);
                Log.i(TAG, "updateFilter - Format(" + i + "), " + onPasteListener + " by " + this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in filterClip, " + e.getMessage());
            }
            this.mPasteListener = onPasteListener;
        }
    }

    public boolean paste(ClipData clipData) {
        if (isEnabled("paste(ClipData)") && getSemService() != null) {
            try {
                return getSemService().pasteClipData(clipData, this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in paste(ClipData), " + e.getMessage());
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPaste(SemClipData semClipData) {
        OnPasteListener onPasteListener = this.mPasteListener;
        if (onPasteListener == null) {
            Log.secE(TAG, "no app clipboard listener!");
        } else if (semClipData != null) {
            onPasteListener.onPaste(semClipData);
        } else {
            Log.secE(TAG, "clipdata is null");
        }
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public void showDialog() {
        Log.d(TAG, "deprecated showDialog, calling package : " + this.mContext.getOpPackageName());
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public void dismissDialog() {
        Log.d(TAG, "deprecated dismissDialog, calling package : " + this.mContext.getOpPackageName());
    }

    public boolean isEnabled() {
        if (getSemService() == null) {
            return false;
        }
        try {
            return getSemService().isEnabled(this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in isEnabled, " + e.getMessage());
            return false;
        }
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public boolean paste(String str) {
        Log.d(TAG, "deprecated paste(String id), calling package : " + this.mContext.getOpPackageName());
        return false;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public List<SemClipData> getClips() {
        if (!isEnabled("getClips")) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (getSemService() != null) {
            try {
                SemClipData primarySemClip = getSemService().getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
                if (primarySemClip != null) {
                    arrayList.add(primarySemClip);
                    return arrayList;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getClips, " + e.getMessage());
            }
        }
        return arrayList;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public SemClipData getClip(String str) throws RemoteException {
        if (isEnabled("getClip") && getSemService() != null) {
            try {
                return getSemService().getPrimarySemClip(this.mContext.getOpPackageName(), this.mContext.getUserId());
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in getClip, " + e.getMessage());
            }
        }
        return null;
    }

    private boolean makeFileDescriptor(SemClipData semClipData) {
        FileHelper fileHelper = FileHelper.getInstance();
        int clipType = semClipData.getClipType();
        if (clipType == 2) {
            SemImageClipData semImageClipData = (SemImageClipData) semClipData;
            String bitmapPath = semImageClipData.getBitmapPath();
            if (!TextUtils.isEmpty(bitmapPath)) {
                File file = new File(bitmapPath);
                if (file.exists() && !file.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
                    if (fileHelper.checkFile(file)) {
                        try {
                            semImageClipData.setParcelFileDescriptor(ParcelFileDescriptor.open(file, Dataspace.RANGE_MASK));
                        } catch (FileNotFoundException e) {
                            Log.e(TAG, "makeFileDescriptor(1) Exception : " + e.getMessage());
                            return false;
                        }
                    } else {
                        Log.secD(TAG, "it's not file.");
                        return false;
                    }
                } else {
                    Log.secD(TAG, "it's /data/semclipdata/.. path file");
                    return true;
                }
            } else {
                Log.secD(TAG, "no bitmap file");
            }
            if (semImageClipData.hasExtraData()) {
                String extraDataPath = semImageClipData.getExtraDataPath();
                if (!TextUtils.isEmpty(extraDataPath)) {
                    File file2 = new File(extraDataPath);
                    if (fileHelper.checkFile(file2)) {
                        try {
                            semImageClipData.setExtraParcelFileDescriptor(ParcelFileDescriptor.open(file2, Dataspace.RANGE_MASK));
                        } catch (FileNotFoundException e2) {
                            Log.e(TAG, "makeFileDescriptor(2) Exception : " + e2.getMessage());
                            return false;
                        }
                    } else {
                        Log.secD(TAG, "it's not file. : " + file2.getAbsolutePath());
                        return false;
                    }
                }
            } else {
                Log.secD(TAG, "no extra bitmap file");
            }
        } else if (clipType == 4) {
            SemHtmlClipData semHtmlClipData = (SemHtmlClipData) semClipData;
            String thumbnailImagePath = semHtmlClipData.getThumbnailImagePath();
            if (TextUtils.isEmpty(thumbnailImagePath) && fileHelper.setFirstImagePathFromHtmlData(semHtmlClipData)) {
                thumbnailImagePath = semHtmlClipData.getThumbnailImagePath();
            }
            if (!TextUtils.isEmpty(thumbnailImagePath)) {
                File file3 = new File(thumbnailImagePath);
                if (file3.exists() && !file3.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
                    if (fileHelper.checkFile(file3)) {
                        try {
                            semHtmlClipData.setParcelFileDescriptor(ParcelFileDescriptor.open(file3, Dataspace.RANGE_MASK));
                        } catch (FileNotFoundException e3) {
                            Log.e(TAG, "makeFileDescriptor(3) Exception : " + e3.getMessage());
                            return false;
                        }
                    } else {
                        Log.secD(TAG, "it's not file.");
                        return false;
                    }
                } else {
                    Log.secD(TAG, "it's /data/semclipdata/.. path file");
                    return true;
                }
            } else {
                Log.secD(TAG, "no first image file");
            }
        } else if (clipType == 16) {
            SemUriClipData semUriClipData = (SemUriClipData) semClipData;
            String thumbnailPath = semUriClipData.getThumbnailPath();
            if (TextUtils.isEmpty(thumbnailPath) && fileHelper.setThumbnailImagePathFromUriData(semUriClipData)) {
                thumbnailPath = semUriClipData.getThumbnailPath();
            }
            if (!TextUtils.isEmpty(thumbnailPath)) {
                File file4 = new File(thumbnailPath);
                if (file4.exists() && !file4.getAbsolutePath().contains(ClipboardConstants.CLIPBOARD_ROOT_PATH)) {
                    if (fileHelper.checkFile(file4)) {
                        try {
                            semUriClipData.setParcelFileDescriptor(ParcelFileDescriptor.open(file4, Dataspace.RANGE_MASK));
                        } catch (FileNotFoundException e4) {
                            Log.e(TAG, "makeFileDescriptor(4) Exception : " + e4.getMessage());
                            return false;
                        }
                    } else {
                        Log.secD(TAG, "it's not file.");
                        return false;
                    }
                } else {
                    Log.secD(TAG, "it's /data/semclipdata/.. path file");
                    return true;
                }
            } else {
                Log.secD(TAG, "no preview image file");
            }
        }
        return true;
    }

    private boolean isEnabled(String str) {
        if (isEnabled()) {
            return true;
        }
        Log.i(TAG, "not enabled! from " + str);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyEvent(Message message) {
        int i = message.what;
        int i2 = 0;
        if (i != 1) {
            if (i == 5) {
                Bundle data = message.getData();
                int i3 = data != null ? data.getInt(KEY_FILTER) : 0;
                synchronized (this.mOnClipboardEventServiceListeners) {
                    if (this.mOnClipboardEventServiceListeners.size() <= 0) {
                        return;
                    }
                    Object[] array = this.mOnClipboardEventServiceListeners.toArray();
                    while (i2 < array.length) {
                        ((SemClipboardEventListener) array[i2]).onFilterUpdated(i3);
                        i2++;
                    }
                    return;
                }
            }
            if (i != 16 && i != 256 && i != 7 && i != 8) {
                return;
            }
        }
        Bundle data2 = message.getData();
        SemClipData semClipData = data2 != null ? (SemClipData) data2.getParcelable("data") : null;
        synchronized (this.mOnClipboardEventServiceListeners) {
            if (this.mOnClipboardEventServiceListeners.size() <= 0) {
                return;
            }
            Object[] array2 = this.mOnClipboardEventServiceListeners.toArray();
            while (i2 < array2.length) {
                ((SemClipboardEventListener) array2[i2]).onClipboardUpdated(message.what, semClipData);
                i2++;
            }
        }
    }
}
