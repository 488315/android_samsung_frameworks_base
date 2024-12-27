package com.samsung.android.jdsms;

import android.content.Context;
import android.content.Intent;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;

import java.util.HashMap;

public final class Sender {
    private static final String ACTION_USE_APP_FEATURE_SURVEY =
            "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String COMMERCIALIZED_DEVICE_KEY = "C";
    private static final String DETAIL_KEY = "det";
    private static final String DIAGMONDAGENT_PACKAGE = "com.sec.android.diagmonagent";
    private static final String DIMENSION_KEY = "dimension";
    private static final int DSMS_DENY = -1;
    private static final int DSMS_NOT_SUPPORTED = -19;
    private static final String DSMS_PACKAGE = "com.samsung.android.dsms";
    private static final int DSMS_SUCCESS = 0;
    private static final String EV = "ev";
    private static final String FEATURE_KEY = "feature";
    private static final String PACKAGE_NAME_KEY = "pkg_name";
    private static final String SERVICE_SOURCE = "S";
    private static final String SOURCE_KEY = "S";
    private static final String SUBTAG = "Sender";
    private static final String TRACKING_ID = "4D6-399-555452";
    private static final String TRACKING_ID_KEY = "tracking_id";
    private static final String TYPE_KEY = "type";
    private static final String VALUE_KEY = "value";
    private final boolean DSMS_DISABLED;
    private final Context mContext;

    public Sender(Context context) {
        DsmsLog.d(SUBTAG, "Created. context=" + context);
        if (context != null) {
            DsmsLog.d(
                    SUBTAG,
                    "context.packageName=["
                            + context.getPackageName()
                            + NavigationBarInflaterView.SIZE_MOD_END);
            this.mContext = context;
            if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                DsmsLog.d(SUBTAG, "DSMS disabled");
                this.DSMS_DISABLED = true;
                return;
            } else {
                this.DSMS_DISABLED = false;
                return;
            }
        }
        throw new IllegalArgumentException("DSMS-FRAMEWORK Null context");
    }

    public int send(String featureCode) {
        return sendToDsms(new DsmsMessage(featureCode));
    }

    public int send(String featureCode, String detail) {
        return sendToDsms(new DsmsMessage(featureCode, detail));
    }

    public int send(String featureCode, long value) {
        return sendToDsms(new DsmsMessage(featureCode, Long.valueOf(value)));
    }

    public int send(String featureCode, String detail, long value) {
        return sendToDsms(new DsmsMessage(featureCode, detail, Long.valueOf(value)));
    }

    private int sendToDsms(final DsmsMessage message) {
        if (this.DSMS_DISABLED) {
            DsmsLog.e(SUBTAG, "DSMS not supported");
            return -19;
        }
        DsmsLog.d(SUBTAG, "Sending message " + message);
        if (!PolicyEnforcer.isAValidCaller()) {
            DsmsLog.e(SUBTAG, "Unauthorized caller");
            return -1;
        }
        DsmsThreadPoolExecutor.getInstance()
                .execute(
                        new Runnable() { // from class: com.samsung.android.jdsms.Sender.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Sender.this.sendMessage(message);
                            }
                        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(DsmsMessage message) {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", TRACKING_ID);
        bundle.putString("feature", message.getFeatureCode());
        bundle.putString("pkg_name", DSMS_PACKAGE);
        bundle.putString("type", "ev");
        bundle.putString("value", String.valueOf(message.getValue()));
        HashMap<String, String> dimension = new HashMap<>();
        if (message.getDetail() != null) {
            dimension.put("det", message.getDetail());
        }
        dimension.put(GnssSignalType.CODE_TYPE_S, GnssSignalType.CODE_TYPE_S);
        dimension.put("C", DsmsInfoCache.getInstance().isCommercializedDevice() ? "1" : "0");
        bundle.putSerializable("dimension", dimension);
        Intent intent = new Intent();
        intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
        intent.setPackage("com.sec.android.diagmonagent");
        intent.putExtras(bundle);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        DsmsLog.d(SUBTAG, "Message sent");
    }
}
