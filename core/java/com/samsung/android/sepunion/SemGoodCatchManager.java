package com.samsung.android.sepunion;

import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.sepunion.IGoodCatchDispatcher;
import com.samsung.android.sepunion.IGoodCatchManager;
import java.util.List;

/* loaded from: classes6.dex */
public class SemGoodCatchManager {
    public static final String ACTION_GOOD_CATCH_STATE_CHANGED = "com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED";
    private static final String TAG = SemGoodCatchManager.class.getSimpleName();
    private static IGoodCatchManager mService;
    private Context mContext;
    private String[] mFunction;
    private OnStateChangeListener mListener;
    private String mModule;
    private final IBinder mICallback = new Binder();
    private final int MSG_START = 0;
    private final int MSG_STOP = 1;
    private final IGoodCatchDispatcher mGoodCatchDispatcher = new IGoodCatchDispatcher.Stub() { // from class: com.samsung.android.sepunion.SemGoodCatchManager.1
        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStart(String function) {
            SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(0, 0, 0, function));
        }

        @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
        public void onStop(String function) {
            SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(1, 0, 0, function));
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.sepunion.SemGoodCatchManager.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    android.util.Log.d(SemGoodCatchManager.TAG, "MSG_START");
                    if (msg.obj instanceof String) {
                        SemGoodCatchManager.this.mListener.onStart((String) msg.obj);
                        break;
                    }
                    break;
                case 1:
                    android.util.Log.d(SemGoodCatchManager.TAG, "MSG_STOP");
                    if (msg.obj instanceof String) {
                        SemGoodCatchManager.this.mListener.onStop((String) msg.obj);
                        break;
                    }
                    break;
            }
        }
    };

    public interface OnStateChangeListener {
        void onStart(String str);

        void onStop(String str);
    }

    public SemGoodCatchManager(Context context) {
        this.mContext = context;
    }

    public SemGoodCatchManager(Context context, String module, String[] function, OnStateChangeListener l) {
        if (function == null) {
            throw new IllegalArgumentException("Invalid function");
        }
        this.mContext = context;
        this.mModule = module;
        this.mListener = l;
        SemUnionManager um = (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
        IBinder b = um.getSemSystemService(UnionConstants.SERVICE_GOOD_CATCH);
        mService = IGoodCatchManager.Stub.asInterface(b);
        if (mService == null) {
            android.util.Log.w(TAG, "Failed to SemGoodCatchManager; no service.");
            return;
        }
        try {
            mService.registerListener(this.mModule, function, this.mGoodCatchDispatcher, this.mICallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        android.util.Log.d(TAG, "SemGoodCatchManager is created, " + this.mModule);
    }

    public void update(String function, String caller, int state, String msg, String extra) {
        String time = Long.toString(System.currentTimeMillis());
        if (mService == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return;
        }
        try {
            mService.update(new String[]{this.mModule, function, caller, time, Integer.toString(state), msg, extra});
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void update(String function, String caller, String state, String msg, String extra) {
        String time = Long.toString(System.currentTimeMillis());
        if (mService == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return;
        }
        try {
            mService.update(new String[]{this.mModule, function, caller, time, state, msg, extra});
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSelectedSettingKey() {
        List<String> db_keys = null;
        if (mService == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return null;
        }
        try {
            db_keys = mService.getSelectedSettingKey();
            android.util.Log.d(TAG, "getSelectedSettingKey() : db_keys=" + db_keys);
            return db_keys;
        } catch (RemoteException e) {
            e.printStackTrace();
            return db_keys;
        }
    }
}
