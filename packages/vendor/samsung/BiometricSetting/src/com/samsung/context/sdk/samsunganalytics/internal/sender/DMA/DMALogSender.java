package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import com.sec.android.diagmonagent.sa.IDMAInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public final class DMALogSender extends BaseLogSender {
    public final DMABinder dmaBinder;
    public int dmaStatus;

    public DMALogSender(Context context, Configuration configuration) {
        super(context, configuration);
        this.dmaStatus = 0;
        if (PolicyUtils.senderType == 2) {
            final ?? r1 = new Callback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender.1
                @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                public final void onResult(Object obj) {
                    DMALogSender dMALogSender = DMALogSender.this;
                    dMALogSender.sendCommon();
                    dMALogSender.sendAll$1();
                }
            };
            final DMABinder dMABinder = new DMABinder();
            dMABinder.isTokenFail = false;
            dMABinder.isBind = false;
            dMABinder.context = context;
            dMABinder.serviceConnection = new ServiceConnection() { // from class: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMABinder.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    IDMAInterface iDMAInterface;
                    try {
                        DMABinder dMABinder2 = DMABinder.this;
                        int i = IDMAInterface.Stub.$r8$clinit;
                        if (iBinder == null) {
                            iDMAInterface = null;
                        } else {
                            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.android.diagmonagent.sa.IDMAInterface");
                            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDMAInterface)) {
                                IDMAInterface.Stub.Proxy proxy = new IDMAInterface.Stub.Proxy();
                                proxy.mRemote = iBinder;
                                iDMAInterface = proxy;
                            } else {
                                iDMAInterface = (IDMAInterface) queryLocalInterface;
                            }
                        }
                        dMABinder2.dmaInterface = iDMAInterface;
                        IDMAInterface.Stub.Proxy proxy2 = (IDMAInterface.Stub.Proxy) DMABinder.this.dmaInterface;
                        proxy2.getClass();
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                            proxy2.mRemote.transact(1, obtain, obtain2, 0);
                            obtain2.readException();
                            String readString = obtain2.readString();
                            if (readString == null) {
                                DMABinder.this.unBind();
                                DMABinder.this.isTokenFail = true;
                                Debug.LogD("DMABinder", "Token failed");
                            } else {
                                DMABinder.this.isTokenFail = false;
                                r1.onResult(readString);
                                Debug.LogD("DMABinder", "DMA connected");
                            }
                        } finally {
                            obtain2.recycle();
                            obtain.recycle();
                        }
                    } catch (Exception e) {
                        DMABinder.this.unBind();
                        DMABinder.this.isTokenFail = true;
                        Debug.LogException(e.getClass(), e);
                        e.printStackTrace();
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    DMABinder.this.dmaInterface = null;
                }
            };
            this.dmaBinder = dMABinder;
            dMABinder.bind();
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender
    public final int send(Map map) {
        if (PolicyUtils.senderType != 3) {
            DMABinder dMABinder = this.dmaBinder;
            if (dMABinder.isTokenFail) {
                return -8;
            }
            int i = this.dmaStatus;
            if (i != 0) {
                return i;
            }
            insert(map);
            if (!dMABinder.isBind) {
                dMABinder.bind();
            } else if (dMABinder.dmaInterface != null) {
                sendAll$1();
            }
            return this.dmaStatus;
        }
        ContentValues contentValues = new ContentValues();
        Configuration configuration = this.configuration;
        configuration.getClass();
        contentValues.put("tcType", (Integer) 0);
        contentValues.put("tid", configuration.trackingId);
        contentValues.put("logType", BaseLogSender.getLogType(map).getAbbrev());
        contentValues.put("timeStamp", Long.valueOf((String) ((HashMap) map).get("ts")));
        setCommonParamToLog(map);
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        this.delimiterUtil.getClass();
        contentValues.put("body", Delimiter.makeDelimiterString(map, depth));
        SendLogTaskV2 sendLogTaskV2 = new SendLogTaskV2(this.context, 2, contentValues);
        this.executor.getClass();
        SingleThreadExecutor.execute(sendLogTaskV2);
        return 0;
    }

    public final void sendAll$1() {
        if (PolicyUtils.senderType == 2 && this.dmaStatus == 0) {
            Queue queue = this.manager.get(0);
            while (!queue.isEmpty()) {
                IDMAInterface iDMAInterface = this.dmaBinder.dmaInterface;
                SimpleLog simpleLog = (SimpleLog) queue.poll();
                SendLogTask sendLogTask = new SendLogTask();
                sendLogTask.log = simpleLog;
                sendLogTask.dmaInterface = iDMAInterface;
                sendLogTask.configuration = this.configuration;
                this.executor.getClass();
                SingleThreadExecutor.execute(sendLogTask);
            }
        }
    }

    public final void sendCommon() {
        String str;
        Configuration configuration = this.configuration;
        configuration.getClass();
        String str2 = configuration.trackingId;
        HashMap hashMap = new HashMap();
        hashMap.put("av", this.deviceInfo.appVersionName);
        hashMap.put("uv", configuration.version);
        Delimiter.Depth depth = Delimiter.Depth.ONE_DEPTH;
        String makeDelimiterString = Delimiter.makeDelimiterString(hashMap, depth);
        HashMap hashMap2 = new HashMap();
        if (TextUtils.isEmpty(configuration.deviceId)) {
            str = null;
        } else {
            hashMap2.put("auid", configuration.deviceId);
            hashMap2.put("at", String.valueOf(configuration.auidType));
            str = Delimiter.makeDelimiterString(hashMap2, depth);
        }
        if (PolicyUtils.senderType != 3) {
            try {
                this.dmaStatus = ((IDMAInterface.Stub.Proxy) this.dmaBinder.dmaInterface).sendCommon(str2, makeDelimiterString, str);
                return;
            } catch (Exception e) {
                Debug.LogException(e.getClass(), e);
                this.dmaStatus = -9;
                return;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("tcType", (Integer) 0);
        contentValues.put("tid", str2);
        contentValues.put("data", makeDelimiterString);
        contentValues.put("did", str);
        SendLogTaskV2 sendLogTaskV2 = new SendLogTaskV2(this.context, 1, contentValues);
        this.executor.getClass();
        SingleThreadExecutor.execute(sendLogTaskV2);
    }
}
