package android.content;

import android.annotation.SystemApi;
import android.content.IClipboard;
import android.content.IOnPrimaryClipChangedListener;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class ClipboardManager extends android.text.ClipboardManager {
    public static final String DEVICE_CONFIG_ALLOW_VIRTUALDEVICE_SILOS = "allow_virtualdevice_silos";
    public static final boolean DEVICE_CONFIG_DEFAULT_ALLOW_VIRTUALDEVICE_SILOS = true;
    public static final boolean DEVICE_CONFIG_DEFAULT_SHOW_ACCESS_NOTIFICATIONS = true;
    public static final String DEVICE_CONFIG_SHOW_ACCESS_NOTIFICATIONS = "show_access_notifications";
    private final Context mContext;
    private final Handler mHandler;
    private final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners = new ArrayList<>();
    private final IOnPrimaryClipChangedListener.Stub mPrimaryClipChangedServiceListener = new AnonymousClass1();
    private final IClipboard mService = IClipboard.Stub.asInterface(ServiceManager.getServiceOrThrow("clipboard"));

    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged();
    }

    /* renamed from: android.content.ClipboardManager$1, reason: invalid class name */
    class AnonymousClass1 extends IOnPrimaryClipChangedListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.content.IOnPrimaryClipChangedListener
        public void dispatchPrimaryClipChanged() {
            ClipboardManager.this.mHandler.post(new Runnable() { // from class: android.content.ClipboardManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$dispatchPrimaryClipChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchPrimaryClipChanged$0() {
            ClipboardManager.this.reportPrimaryClipChanged();
        }
    }

    public ClipboardManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
    }

    @SystemApi
    public boolean areClipboardAccessNotificationsEnabled() {
        try {
            return this.mService.areClipboardAccessNotificationsEnabledForUser(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setClipboardAccessNotificationsEnabled(boolean z) {
        try {
            this.mService.setClipboardAccessNotificationsEnabledForUser(z, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPrimaryClip(ClipData clipData) {
        try {
            Objects.requireNonNull(clipData);
            clipData.prepareToLeaveProcess(true);
            this.mService.setPrimaryClip(clipData, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setPrimaryClipAsPackage(ClipData clipData, String str) {
        try {
            Objects.requireNonNull(clipData);
            Objects.requireNonNull(str);
            clipData.prepareToLeaveProcess(true);
            this.mService.setPrimaryClipAsPackage(clipData, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearPrimaryClip() {
        try {
            this.mService.clearPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ClipData getPrimaryClip() {
        try {
            return this.mService.getPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ClipDescription getPrimaryClipDescription() {
        try {
            return this.mService.getPrimaryClipDescription(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPrimaryClip() {
        try {
            return this.mService.hasPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        synchronized (this.mPrimaryClipChangedListeners) {
            if (this.mPrimaryClipChangedListeners.isEmpty()) {
                try {
                    this.mService.addPrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mPrimaryClipChangedListeners.add(onPrimaryClipChangedListener);
        }
    }

    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        synchronized (this.mPrimaryClipChangedListeners) {
            this.mPrimaryClipChangedListeners.remove(onPrimaryClipChangedListener);
            if (this.mPrimaryClipChangedListeners.isEmpty()) {
                try {
                    this.mService.removePrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // android.text.ClipboardManager
    @Deprecated
    public CharSequence getText() {
        ClipData primaryClip = getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).coerceToText(this.mContext);
    }

    @Override // android.text.ClipboardManager
    @Deprecated
    public void setText(CharSequence charSequence) {
        setPrimaryClip(ClipData.newPlainText(null, charSequence));
    }

    @Override // android.text.ClipboardManager
    @Deprecated
    public boolean hasText() {
        try {
            return this.mService.hasClipboardText(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getPrimaryClipSource() {
        try {
            return this.mService.getPrimaryClipSource(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void reportPrimaryClipChanged() {
        synchronized (this.mPrimaryClipChangedListeners) {
            if (this.mPrimaryClipChangedListeners.size() <= 0) {
                return;
            }
            for (Object obj : this.mPrimaryClipChangedListeners.toArray()) {
                ((OnPrimaryClipChangedListener) obj).onPrimaryClipChanged();
            }
        }
    }
}
