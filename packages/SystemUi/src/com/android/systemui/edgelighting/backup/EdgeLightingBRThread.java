package com.android.systemui.edgelighting.backup;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Slog;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingBRThread extends Thread {
    public static final String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE"};
    public final Context mContext;
    public final InnerHandler mHandler;
    public final Intent mIntent;
    public String mSessionKey = null;
    public String mSource = null;
    public int mOption = -1;
    public int mSecuritylevel = 0;
    public boolean mLoopEnable = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InnerHandler extends Handler {
        public final WeakReference mThread;

        public InnerHandler(EdgeLightingBRThread edgeLightingBRThread) {
            this.mThread = new WeakReference(edgeLightingBRThread);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            EdgeLightingBRThread edgeLightingBRThread;
            if (message.what == 2 && (edgeLightingBRThread = (EdgeLightingBRThread) this.mThread.get()) != null) {
                edgeLightingBRThread.mLoopEnable = false;
            }
        }
    }

    public EdgeLightingBRThread(Context context, Intent intent) {
        this.mHandler = null;
        this.mContext = context;
        this.mIntent = intent;
        this.mHandler = new InnerHandler(this);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Intent intent = this.mIntent;
        if (intent == null) {
            Slog.e("EdgeLightingBRThread", "intent is null");
            return;
        }
        String stringExtra = intent.getStringExtra("SAVE_PATH");
        int intExtra = this.mIntent.getIntExtra("ACTION", 0);
        this.mSessionKey = this.mIntent.getStringExtra("SESSION_KEY");
        this.mSource = this.mIntent.getStringExtra("SOURCE");
        this.mSecuritylevel = this.mIntent.getIntExtra("SECURITY_LEVEL", 0);
        this.mIntent.getStringExtra("EXPORT_SESSION_TIME");
        if (this.mIntent.getAction().equals("com.samsung.android.intent.action.REQUEST_RESTORE_EDGELIGHTING")) {
            this.mOption = 1;
        }
        if (!(this.mContext.checkSelfPermission(PERMISSIONS[0]) == 0)) {
            Slog.i("EdgeLightingBRThread", "Permission fail");
            sendResponse(1, 4);
            return;
        }
        StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(stringExtra);
        m18m.append(File.separator);
        String sb = m18m.toString();
        if (this.mOption == 1) {
            if (intExtra != 0) {
                sendResponse(1, 3);
                return;
            }
            this.mLoopEnable = true;
            try {
                Encryption.streamCrypt(this.mSessionKey);
                File decrypt = Encryption.decrypt(this.mSecuritylevel, sb);
                if (decrypt != null) {
                    BRUtils.getInstance(this.mContext).restoreSettingValue(this.mLoopEnable, decrypt);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.edgelighting.backup.EdgeLightingBRThread.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BRUtils bRUtils = BRUtils.getInstance(EdgeLightingBRThread.this.mContext);
                        boolean z = EdgeLightingBRThread.this.mLoopEnable;
                        bRUtils.getClass();
                        if (z && Feature.FEATURE_SUPPORT_EDGE_LIGHTING) {
                            EdgeLightingSettingUtils.initColorTypeIndex(bRUtils.mContext);
                        }
                        EdgeLightingBRThread.this.sendResponse(0, 0);
                    }
                });
            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
                sendResponse(1, 1);
            } catch (Exception e2) {
                sendResponse(1, 1);
                e2.printStackTrace();
            }
        }
    }

    public final void sendResponse(int i, int i2) {
        Intent intent = new Intent();
        if (this.mOption == 1) {
            intent.setAction("com.samsung.android.intent.action.RESPONSE_RESTORE_EDGELIGHTING");
        }
        intent.putExtra("RESULT", i);
        intent.putExtra("ERR_CODE", i2);
        intent.putExtra("REQ_SIZE", 0);
        intent.putExtra("SOURCE", this.mSource);
        this.mContext.sendBroadcast(intent, "com.wssnps.permission.COM_WSSNPS");
    }
}
