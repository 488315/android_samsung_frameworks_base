package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;

/* loaded from: classes4.dex */
public class SendLogTaskV2 implements AsyncTaskClient {
    public final int Type;
    public final ContentValues cv;
    public final Context mContext;
    public final Uri commonUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/common");
    public final Uri logUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
    public Uri returnUri = null;

    public SendLogTaskV2(Context context, int i, ContentValues contentValues) {
        this.mContext = context;
        this.Type = i;
        this.cv = contentValues;
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final int onFinish() throws NumberFormatException {
        try {
            Uri uri = this.returnUri;
            if (uri != null) {
                int i = Integer.parseInt(uri.getLastPathSegment());
                Debug.LogD("SendLog Result = " + i);
                boolean z = true;
                if (this.Type == 1) {
                    if (i != 0) {
                        z = false;
                    }
                    Preferences.getPreferences(this.mContext).edit().putBoolean("sendCommonSuccess", z).apply();
                    Debug.LogD("Save Result = " + z);
                }
            }
        } catch (Exception e) {
            Debug.logwingW("failed to get send result" + e.getMessage());
        }
        return 0;
    }

    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
    public final void run() {
        try {
            int i = this.Type;
            if (i == 1) {
                this.returnUri = this.mContext.getContentResolver().insert(this.commonUri, this.cv);
            } else if (i == 2) {
                this.returnUri = this.mContext.getContentResolver().insert(this.logUri, this.cv);
            }
        } catch (Exception e) {
            Debug.logwingW("failed to send log" + e.getMessage());
        }
    }
}
