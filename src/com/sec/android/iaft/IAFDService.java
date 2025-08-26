package com.sec.android.iaft;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import com.sec.android.iaft.IIAFDService;

/* loaded from: classes6.dex */
public class IAFDService extends Service {
    static final int CMD_ADDB_HOTFIXDB_UPDATE = 11;
    static final int CMD_HIGHBDB_HOTFIXDB_UPDATE = 12;
    static final int CMD_HOTFIX_DATA_GET = 9;
    static final int CMD_IAFDDB_HOTFIXDB_UPDATE = 10;
    static final int CMD_IAFD_DETECT = 13;
    static final int CMD_TYPE_GETUPDATESTATUS = 5;
    static final int CMD_TYPE_GETUPDATESTATUS_RESULT = 6;
    static final int CMD_TYPE_PARSE = 3;
    static final int CMD_TYPE_REPAIR = 2;
    static final int CMD_TYPE_SHOW = 4;
    static final int CMD_TYPE_START = 1;
    static final int CMD_TYPE_START_SmartManagerApp = 7;
    static final int CMD_TYPE_START_VocApp = 8;
    private static final String TAG = "IAFDService";
    private IAFDBinder mBinder;
    private Context mContext;
    private IAFDServiceImpl mIAFDServiceImpl;

    public class IAFDBinder extends IIAFDService.Stub {
        public IAFDBinder(IAFDService iAFDService) {
        }

        @Override // com.sec.android.iaft.IIAFDService
        public boolean IAFDParse(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) {
            return IAFDDiagnosis.getInstance().parseExpType(str, str2, i, i2, i3, str3, str4, str5);
        }

        @Override // com.sec.android.iaft.IIAFDService
        public void IAFDShow(int i, int i2, String str) {
            IAFDDiagnosis.getInstance().showIAFDCrashDialogs(i, i2, str);
        }
    }

    public void IAFDServiceInit(Context context) {
        this.mContext = context;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        IAFDServiceInit(this);
        this.mBinder = new IAFDBinder(this);
        this.mIAFDServiceImpl = new IAFDServiceImpl(this.mContext, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002d  */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 1;
        }
        Bundle extras = intent.getExtras();
        Message message = new Message();
        if (extras != null) {
            extras.getInt("pkgUserId", -1);
            extras.getString("checkSum", "");
            int i3 = extras.getInt("commandType", 0);
            if (i3 == 1 || i3 == 2) {
                message.what = i3;
                message.setData(extras);
                this.mIAFDServiceImpl.IAFDServiceHandlerMessage(message);
            } else {
                switch (i3) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        break;
                    default:
                        return 1;
                }
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
