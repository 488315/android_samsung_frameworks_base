package notification.src.com.android.systemui;

import android.content.Context;
import android.util.Log;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;
import com.samsung.android.sdk.scs.ai.language.AppInfo;
import com.samsung.android.sdk.scs.ai.language.Result;
import com.samsung.android.sdk.scs.ai.language.SmartReplyCategory;
import com.samsung.android.sdk.scs.ai.language.SmartReplyer;
import com.samsung.android.sdk.scs.ai.language.SmartReplyer$$ExternalSyntheticLambda1;
import com.samsung.android.sdk.scs.ai.language.SmartReplyer$$ExternalSyntheticLambda3;
import com.samsung.android.sdk.scs.ai.language.SmartReplyer$$ExternalSyntheticLambda5;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceRunnable;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SrPromptProcessor implements BasePromptProcessor {
    public final String apkSigningKey;
    public final AppInfo appInfo;
    public final Context context;
    public String notificationKey;
    public SmartReplyer smartreplyer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:0|1|(2:2|3)|(9:9|10|11|12|(2:15|13)|16|17|18|19)|26|10|11|12|(1:13)|16|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005d, code lost:
    
        com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Exception getLongHash: ", r7, "SrPromptProcessor");
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004b A[Catch: Exception -> 0x0052, LOOP:0: B:13:0x0048->B:15:0x004b, LOOP_END, TryCatch #1 {Exception -> 0x0052, blocks: (B:12:0x0039, B:15:0x004b, B:17:0x0054), top: B:11:0x0039 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SrPromptProcessor(android.content.Context r7) {
        /*
            r6 = this;
            java.lang.String r0 = "SrPromptProcessor"
            r6.<init>()
            r6.context = r7
            r1 = 0
            r2 = 0
            android.content.pm.PackageManager r3 = r7.getPackageManager()     // Catch: java.lang.Exception -> L30
            java.lang.String r7 = r7.getPackageName()     // Catch: java.lang.Exception -> L30
            r4 = 134217728(0x8000000, float:3.85186E-34)
            android.content.pm.PackageInfo r7 = r3.getPackageInfo(r7, r4)     // Catch: java.lang.Exception -> L30
            android.content.pm.SigningInfo r7 = r7.signingInfo     // Catch: java.lang.Exception -> L30
            if (r7 == 0) goto L36
            android.content.pm.Signature[] r7 = r7.getApkContentsSigners()     // Catch: java.lang.Exception -> L30
            if (r7 == 0) goto L36
            int r3 = r7.length     // Catch: java.lang.Exception -> L30
            if (r3 <= 0) goto L36
            r7 = r7[r1]     // Catch: java.lang.Exception -> L30
            char[] r7 = r7.toChars()     // Catch: java.lang.Exception -> L30
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Exception -> L30
            r3.<init>(r7)     // Catch: java.lang.Exception -> L30
            goto L37
        L30:
            r7 = move-exception
            java.lang.String r3 = "getApkSigningKey: "
            com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m(r3, r7, r0)
        L36:
            r3 = r2
        L37:
            r6.apkSigningKey = r3
            java.lang.String r7 = "SHA-256"
            java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r7)     // Catch: java.lang.Exception -> L52
            java.lang.String r3 = "74NEkgeVa8SprJ7p"
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L52
            byte[] r3 = r3.getBytes(r4)     // Catch: java.lang.Exception -> L52
            r4 = r1
        L48:
            r5 = 7
            if (r4 >= r5) goto L54
            byte[] r3 = r7.digest(r3)     // Catch: java.lang.Exception -> L52
            int r4 = r4 + 1
            goto L48
        L52:
            r7 = move-exception
            goto L5d
        L54:
            java.util.HexFormat r7 = java.util.HexFormat.of()     // Catch: java.lang.Exception -> L52
            java.lang.String r2 = r7.formatHex(r3)     // Catch: java.lang.Exception -> L52
            goto L62
        L5d:
            java.lang.String r3 = "Exception getLongHash: "
            com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m(r3, r7, r0)
        L62:
            com.samsung.android.sdk.scs.ai.language.AppInfo$Builder r7 = new com.samsung.android.sdk.scs.ai.language.AppInfo$Builder
            r7.<init>()
            com.samsung.android.sdk.scs.ai.language.AppInfo$RequestType r0 = com.samsung.android.sdk.scs.ai.language.AppInfo.RequestType.ONDEVICE
            r7.requestType = r0
            r7.apiKey = r2
            java.lang.String r0 = r6.apkSigningKey
            r7.signingKey = r0
            com.samsung.android.sdk.scs.ai.language.AppInfo r0 = new com.samsung.android.sdk.scs.ai.language.AppInfo
            r0.<init>(r7, r1)
            r6.appInfo = r0
            com.samsung.android.sdk.scs.ai.language.AppInfo$Builder r7 = new com.samsung.android.sdk.scs.ai.language.AppInfo$Builder
            r7.<init>()
            com.samsung.android.sdk.scs.ai.language.AppInfo$RequestType r0 = com.samsung.android.sdk.scs.ai.language.AppInfo.RequestType.CLOUD
            r7.requestType = r0
            r7.apiKey = r2
            java.lang.String r6 = r6.apkSigningKey
            r7.signingKey = r6
            com.samsung.android.sdk.scs.ai.language.AppInfo r6 = new com.samsung.android.sdk.scs.ai.language.AppInfo
            r6.<init>(r7, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: notification.src.com.android.systemui.SrPromptProcessor.<init>(android.content.Context):void");
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final String getNotificationKey() {
        return this.notificationKey;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void releaseSmartReply() {
        Log.d("SrPromptProcessor", "call releaseSmartReply");
        SmartReplyer smartReplyer = this.smartreplyer;
        if (smartReplyer != null) {
            smartReplyer.release();
        }
        this.smartreplyer = null;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void setNotificationKey(String str) {
        this.notificationKey = str;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void textPrompting(String str, String str2, final SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1) {
        Log.d("SrPromptProcessor", "textPrompting");
        this.smartreplyer = new SmartReplyer(this.context);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("target_language", str2);
        boolean z = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SEVENTH;
        Task task = null;
        AppInfo appInfo = this.appInfo;
        if (z) {
            Log.d("SrPromptProcessor", "textPrompting - 3B");
            SmartReplyer smartReplyer = this.smartreplyer;
            if (smartReplyer != null) {
                SmartReplyCategory smartReplyCategory = SmartReplyCategory.SHORT;
                appInfo.getClass();
                LlmServiceRunnable llmServiceRunnable = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new SmartReplyer$$ExternalSyntheticLambda3(smartReplyer, appInfo, str, smartReplyCategory, linkedHashMap), new SmartReplyer$$ExternalSyntheticLambda1(1));
                smartReplyer.mServiceExecutor.execute(llmServiceRunnable);
                task = llmServiceRunnable.getTask();
            }
        } else {
            SmartReplyer smartReplyer2 = this.smartreplyer;
            if (smartReplyer2 != null) {
                appInfo.getClass();
                LlmServiceRunnable llmServiceRunnable2 = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new SmartReplyer$$ExternalSyntheticLambda5(smartReplyer2, appInfo, str, linkedHashMap), new SmartReplyer$$ExternalSyntheticLambda1(1));
                smartReplyer2.mServiceExecutor.execute(llmServiceRunnable2);
                task = llmServiceRunnable2.getTask();
            }
        }
        if (task != null) {
            task.addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.SrPromptProcessor$textPrompting$1
                @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                public final void onComplete(Task task2) {
                    Result result;
                    Log.d("SrPromptProcessor", "smartReply onComplete isSuccessful : " + task2.isSuccessful());
                    boolean isSuccessful = task2.isSuccessful();
                    PromptCallback promptCallback = PromptCallback.this;
                    if (!isSuccessful) {
                        Log.d("SrPromptProcessor", " error : " + task2.getException());
                        Exception exception = task2.getException();
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(exception != null ? exception.getMessage() : null);
                        return;
                    }
                    Log.d("SrPromptProcessor", " result");
                    List list = (List) task2.getResult();
                    if (list != null && (result = (Result) list.get(0)) != null) {
                        String str3 = result.content;
                        if (str3 == null) {
                            str3 = "";
                        }
                        r2 = str3;
                    }
                    ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(new StringBuilder(r2));
                }
            });
        }
    }
}
