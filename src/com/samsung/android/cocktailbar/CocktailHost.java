package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.cocktailbar.ICocktailHost;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class CocktailHost {
    static final int HANDLE_COCKTAIL_CLOSE = 5;
    static final int HANDLE_COCKTAIL_PARTIALLY_UPDATE = 2;
    static final int HANDLE_COCKTAIL_PARTIALLY_UPDATE_HELPVIEW = 14;
    static final int HANDLE_COCKTAIL_REMOVE = 3;
    static final int HANDLE_COCKTAIL_SEND_EXTRA_DATA = 12;
    static final int HANDLE_COCKTAIL_SET_PULL_TO_REFRESH = 13;
    static final int HANDLE_COCKTAIL_SHOW = 4;
    static final int HANDLE_COCKTAIL_SWITCH_DEFAULT = 10;
    static final int HANDLE_COCKTAIL_TICKER_DISABLE = 9;
    static final int HANDLE_COCKTAIL_UPDATE = 1;
    static final int HANDLE_COCKTAIL_UPDATE_EXTRA = 8;
    static final int HANDLE_COCKTAIL_UPDATE_TOOL_LAUNCHER = 7;
    static final int HANDLE_COCKTAIL_VIEW_DATA_CHANGED = 6;
    static final int HANDLE_NOTE_PAUSE_COMPONENT = 104;
    static final int HANDLE_NOTE_RESUME_COMPONENT = 103;
    static final int HANDLE_NOTIFY_CHANGE_VISIBLE_EDGE_SERVICE = 102;
    static final int HANDLE_NOTIFY_KEYGUARD_STATE = 100;
    static final int HANDLE_NOTIFY_WAKEUP_STATE = 101;
    static final int HANDLE_PACKAGE_SUSPEND_CHANGED = 105;
    private static final String TAG = "CocktailHost";
    static ICocktailBarService sService;
    static final Object sServiceLock = new Object();
    ICallbackListener mCallbackListener;
    private final Callbacks mCallbacks;
    private String mContextOpPackageName;
    private final Handler mHandler;
    private int mListeningCategory;

    public interface ICallbackListener {
        void onChangeVisibleEdgeService(boolean z, int i);

        void onCloseCocktail(int i, int i2, int i3);

        void onNotePauseComponent(ComponentName componentName);

        void onNoteResumeComponent(ComponentName componentName);

        void onNotifyKeyguardState(boolean z, int i);

        void onNotifyWakeUpModeState(boolean z, int i, int i2);

        void onPackageSuspendChanged(Cocktail cocktail);

        void onPartiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2);

        void onPartiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2);

        void onRemoveCocktail(int i, int i2);

        void onSendExtraDataToCocktailBar(Bundle bundle, int i);

        void onSetDisableTickerView(int i, int i2);

        void onSetPullToRefresh(int i, int i2, PendingIntent pendingIntent);

        void onShowCocktail(int i, int i2);

        void onSwitchDefaultCocktail(int i);

        void onUpdateCocktail(int i, Cocktail cocktail, int i2);

        void onUpdateToolLauncher(int i);

        void onViewDataChanged(int i, int i2, int i3);
    }

    static class Callbacks extends ICocktailHost.Stub {
        private final WeakReference<Handler> mWeakHandler;

        public Callbacks(Handler handler) {
            this.mWeakHandler = new WeakReference<>(handler);
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateCocktail(int i, Cocktail cocktail, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(1, i, i2, cocktail).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(2, i, i2, remoteViews).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(14, i, i2, remoteViews).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void removeCocktail(int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(3, i, i2).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void showCocktail(int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(4, i, i2).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void closeCocktail(int i, int i2, int i3) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(5, i, i2, Integer.valueOf(i3)).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void viewDataChanged(int i, int i2, int i3) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(6, i, i2, Integer.valueOf(i3)).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void updateToolLauncher(int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(7, i, 0).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyKeyguardState(boolean z, int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(100, !z ? 0 : 1, i).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notifyWakeUpState(boolean z, int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(101, z ? 1 : 0, i, Integer.valueOf(i2)).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void switchDefaultCocktail(int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(10, i, 0).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void sendExtraData(int i, Bundle bundle) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(12, i, 0, bundle).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setDisableTickerView(int i, int i2) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(9, i, i2).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void changeVisibleEdgeService(boolean z, int i) {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(102, z ? 1 : 0, i).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(13, i, i2, pendingIntent).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void noteResumeComponent(ComponentName componentName) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(103, componentName).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void notePauseComponent(ComponentName componentName) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(104, componentName).sendToTarget();
        }

        @Override // com.samsung.android.cocktailbar.ICocktailHost
        public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
            Handler handler = this.mWeakHandler.get();
            if (handler == null) {
                return;
            }
            handler.obtainMessage(105, cocktail).sendToTarget();
        }
    }

    class UpdateHandler extends Handler {
        public UpdateHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 9) {
                CocktailHost.this.setDisableTickerView(message.arg1, message.arg2);
                return;
            }
            if (i != 10) {
                switch (i) {
                    case 1:
                        CocktailHost.this.updateCocktail(message.arg1, (Cocktail) message.obj, message.arg2);
                        break;
                    case 2:
                        CocktailHost.this.partiallyUpdateCocktail(message.arg1, (RemoteViews) message.obj, message.arg2);
                        break;
                    case 3:
                        CocktailHost.this.removeCocktail(message.arg1, message.arg2);
                        break;
                    case 4:
                        CocktailHost.this.showCocktail(message.arg1, message.arg2);
                        break;
                    case 5:
                        CocktailHost.this.closeCocktail(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        break;
                    case 6:
                        CocktailHost.this.viewDataChanged(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        break;
                    case 7:
                        CocktailHost.this.updateToolLauncher(message.arg1);
                        break;
                    default:
                        switch (i) {
                            case 12:
                                CocktailHost.this.sendExtraDataToCocktailBar(message.arg1, (Bundle) message.obj);
                                break;
                            case 13:
                                CocktailHost.this.setPullToRefresh(message.arg1, message.arg2, (PendingIntent) message.obj);
                                break;
                            case 14:
                                CocktailHost.this.partiallyUpdateHelpView(message.arg1, (RemoteViews) message.obj, message.arg2);
                                break;
                            default:
                                switch (i) {
                                    case 100:
                                        CocktailHost.this.notifyKeyguardState(message.arg1, message.arg2);
                                        break;
                                    case 101:
                                        CocktailHost.this.notifyWakeUpState(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                                        break;
                                    case 102:
                                        CocktailHost.this.changeVisibleEdgeService(message.arg1, message.arg2);
                                        break;
                                    case 103:
                                        CocktailHost.this.noteResumeComponent((ComponentName) message.obj);
                                        break;
                                    case 104:
                                        CocktailHost.this.notePauseComponent((ComponentName) message.obj);
                                        break;
                                    case 105:
                                        CocktailHost.this.packageSuspendChanged((Cocktail) message.obj);
                                        break;
                                }
                        }
                }
                return;
            }
            CocktailHost.this.switchDefaultCocktail(message.arg1);
        }
    }

    public CocktailHost(Context context, ICallbackListener iCallbackListener) {
        this(context, iCallbackListener, context.getMainLooper());
    }

    public CocktailHost(Context context, int i, ICallbackListener iCallbackListener) {
        this(context, i, iCallbackListener, context.getMainLooper());
    }

    public CocktailHost(Context context, ICallbackListener iCallbackListener, Looper looper) {
        this.mListeningCategory = 0;
        this.mContextOpPackageName = context.getOpPackageName();
        this.mCallbackListener = iCallbackListener;
        UpdateHandler updateHandler = new UpdateHandler(looper);
        this.mHandler = updateHandler;
        this.mCallbacks = new Callbacks(updateHandler);
        bindService(0);
    }

    public CocktailHost(Context context, int i, ICallbackListener iCallbackListener, Looper looper) {
        this.mListeningCategory = 0;
        this.mContextOpPackageName = context.getOpPackageName();
        this.mCallbackListener = iCallbackListener;
        UpdateHandler updateHandler = new UpdateHandler(looper);
        this.mHandler = updateHandler;
        this.mCallbacks = new Callbacks(updateHandler);
        this.mListeningCategory = i;
        bindService(i);
    }

    private void bindService(int i) {
        synchronized (sServiceLock) {
            if (sService == null) {
                sService = ICocktailBarService.Stub.asInterface(ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE));
            }
            try {
                ICocktailBarService iCocktailBarService = sService;
                if (iCocktailBarService != null) {
                    iCocktailBarService.setCocktailHostCallbacks(this.mCallbacks, this.mContextOpPackageName, i);
                } else {
                    Slog.d(TAG, "bindService: can not get ICocktailBarService");
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public void startListening() {
        try {
            sService.startListening(this.mCallbacks, this.mContextOpPackageName, this.mListeningCategory);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void startListening(int i) {
        try {
            this.mListeningCategory = i;
            sService.startListening(this.mCallbacks, this.mContextOpPackageName, i);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    public void stopListening() {
        try {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mCallbackListener = null;
            sService.stopListening(this.mContextOpPackageName);
        } catch (RemoteException e) {
            throw new RuntimeException("system server dead?", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCocktail(int i, Cocktail cocktail, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onUpdateCocktail(i, cocktail, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onPartiallyUpdateCocktail(i, remoteViews, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onPartiallyUpdateHelpView(i, remoteViews, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCocktail(int i, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onRemoveCocktail(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCocktail(int i, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onShowCocktail(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCocktail(int i, int i2, int i3) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onCloseCocktail(i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void viewDataChanged(int i, int i2, int i3) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onViewDataChanged(i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateToolLauncher(int i) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onUpdateToolLauncher(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyKeyguardState(int i, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onNotifyKeyguardState(i == 1, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWakeUpState(int i, int i2, int i3) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onNotifyWakeUpModeState(i == 1, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchDefaultCocktail(int i) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSwitchDefaultCocktail(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendExtraDataToCocktailBar(int i, Bundle bundle) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSendExtraDataToCocktailBar(bundle, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableTickerView(int i, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSetDisableTickerView(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeVisibleEdgeService(int i, int i2) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onChangeVisibleEdgeService(i == 1, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPullToRefresh(int i, int i2, PendingIntent pendingIntent) {
        ICallbackListener iCallbackListener = this.mCallbackListener;
        if (iCallbackListener != null) {
            iCallbackListener.onSetPullToRefresh(i, i2, pendingIntent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noteResumeComponent(ComponentName componentName) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onNoteResumeComponent(componentName);
            }
        } catch (AbstractMethodError unused) {
            Slog.d(TAG, "noteResumeComponent: AbstractMethodError happens");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notePauseComponent(ComponentName componentName) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onNotePauseComponent(componentName);
            }
        } catch (AbstractMethodError unused) {
            Slog.d(TAG, "notePauseComponent: AbstractMethodError happens");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void packageSuspendChanged(Cocktail cocktail) {
        try {
            ICallbackListener iCallbackListener = this.mCallbackListener;
            if (iCallbackListener != null) {
                iCallbackListener.onPackageSuspendChanged(cocktail);
            }
        } catch (AbstractMethodError unused) {
            Slog.d(TAG, "packageSuspended: AbstractMethodError happens");
        }
    }
}
