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
    private static final String TAG = "SemGoodCatchManager";
    private static IGoodCatchManager mService;
    private final int MSG_START;
    private final int MSG_STOP;
    private Context mContext;
    private String[] mFunction;
    private final IGoodCatchDispatcher mGoodCatchDispatcher;
    private final Handler mHandler;
    private final IBinder mICallback;
    private OnStateChangeListener mListener;
    private String mModule;

    public interface OnStateChangeListener {
        void onStart(String str);

        void onStop(String str);
    }

    public SemGoodCatchManager(Context context) {
        this.mICallback = new Binder();
        this.MSG_START = 0;
        this.MSG_STOP = 1;
        this.mGoodCatchDispatcher = new IGoodCatchDispatcher.Stub() { // from class: com.samsung.android.sepunion.SemGoodCatchManager.1
            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStart(String str) {
                SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(0, 0, 0, str));
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStop(String str) {
                SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(1, 0, 0, str));
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.sepunion.SemGoodCatchManager.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    android.util.Log.d(SemGoodCatchManager.TAG, "MSG_START");
                    if (message.obj instanceof String) {
                        SemGoodCatchManager.this.mListener.onStart((String) message.obj);
                        return;
                    }
                    return;
                }
                if (i != 1) {
                    return;
                }
                android.util.Log.d(SemGoodCatchManager.TAG, "MSG_STOP");
                if (message.obj instanceof String) {
                    SemGoodCatchManager.this.mListener.onStop((String) message.obj);
                }
            }
        };
        this.mContext = context;
    }

    public SemGoodCatchManager(Context context, String str, String[] strArr, OnStateChangeListener onStateChangeListener) {
        Binder binder = new Binder();
        this.mICallback = binder;
        this.MSG_START = 0;
        this.MSG_STOP = 1;
        IGoodCatchDispatcher.Stub stub = new IGoodCatchDispatcher.Stub() { // from class: com.samsung.android.sepunion.SemGoodCatchManager.1
            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStart(String str2) {
                SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(0, 0, 0, str2));
            }

            @Override // com.samsung.android.sepunion.IGoodCatchDispatcher
            public void onStop(String str2) {
                SemGoodCatchManager.this.mHandler.sendMessage(SemGoodCatchManager.this.mHandler.obtainMessage(1, 0, 0, str2));
            }
        };
        this.mGoodCatchDispatcher = stub;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.sepunion.SemGoodCatchManager.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 0) {
                    android.util.Log.d(SemGoodCatchManager.TAG, "MSG_START");
                    if (message.obj instanceof String) {
                        SemGoodCatchManager.this.mListener.onStart((String) message.obj);
                        return;
                    }
                    return;
                }
                if (i != 1) {
                    return;
                }
                android.util.Log.d(SemGoodCatchManager.TAG, "MSG_STOP");
                if (message.obj instanceof String) {
                    SemGoodCatchManager.this.mListener.onStop((String) message.obj);
                }
            }
        };
        if (strArr == null) {
            throw new IllegalArgumentException("Invalid function");
        }
        this.mContext = context;
        this.mModule = str;
        this.mListener = onStateChangeListener;
        IGoodCatchManager asInterface = IGoodCatchManager.Stub.asInterface(((SemUnionManager) context.getSystemService(Context.SEP_UNION_SERVICE)).getSemSystemService(UnionConstants.SERVICE_GOOD_CATCH));
        mService = asInterface;
        if (asInterface == null) {
            android.util.Log.w(TAG, "Failed to SemGoodCatchManager; no service.");
            return;
        }
        try {
            asInterface.registerListener(this.mModule, strArr, stub, binder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        android.util.Log.d(TAG, "SemGoodCatchManager is created, " + this.mModule);
    }

    public void update(String str, String str2, int i, String str3, String str4) {
        String l = Long.toString(System.currentTimeMillis());
        IGoodCatchManager iGoodCatchManager = mService;
        if (iGoodCatchManager == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return;
        }
        try {
            iGoodCatchManager.update(new String[]{this.mModule, str, str2, l, Integer.toString(i), str3, str4});
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void update(String str, String str2, String str3, String str4, String str5) {
        String l = Long.toString(System.currentTimeMillis());
        IGoodCatchManager iGoodCatchManager = mService;
        if (iGoodCatchManager == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return;
        }
        try {
            iGoodCatchManager.update(new String[]{this.mModule, str, str2, l, str3, str4, str5});
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSelectedSettingKey() {
        IGoodCatchManager iGoodCatchManager = mService;
        List<String> list = null;
        if (iGoodCatchManager == null) {
            android.util.Log.w(TAG, "Failed to update; no service.");
            return null;
        }
        try {
            list = iGoodCatchManager.getSelectedSettingKey();
            android.util.Log.d(TAG, "getSelectedSettingKey() : db_keys=" + list);
            return list;
        } catch (RemoteException e) {
            e.printStackTrace();
            return list;
        }
    }
}
