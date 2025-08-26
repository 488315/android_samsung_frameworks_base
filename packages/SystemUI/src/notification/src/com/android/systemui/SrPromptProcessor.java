package notification.src.com.android.systemui;

import android.content.Context;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.Log;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SrPromptProcessor implements BasePromptProcessor {
    public final String apkSigningKey;
    public final AppInfo appInfo;
    public final Context context;
    public String notificationKey;
    public SmartReplyer smartreplyer;

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

    public SrPromptProcessor(Context context) throws NoSuchAlgorithmException {
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        this.context = context;
        String hex = null;
        try {
            signingInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo;
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("getApkSigningKey: ", e, "SrPromptProcessor");
        }
        String str = (signingInfo == null || (apkContentsSigners = signingInfo.getApkContentsSigners()) == null || apkContentsSigners.length <= 0) ? null : new String(apkContentsSigners[0].toChars());
        this.apkSigningKey = str;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = "74NEkgeVa8SprJ7p".getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < 7; i++) {
                bytes = messageDigest.digest(bytes);
            }
            hex = HexFormat.of().formatHex(bytes);
        } catch (Exception e2) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Exception getLongHash: ", e2, "SrPromptProcessor");
        }
        AppInfo.Builder builder = new AppInfo.Builder();
        builder.requestType = AppInfo.RequestType.ONDEVICE;
        builder.apiKey = hex;
        builder.signingKey = this.apkSigningKey;
        this.appInfo = new AppInfo(builder, 0);
        AppInfo.Builder builder2 = new AppInfo.Builder();
        builder2.requestType = AppInfo.RequestType.CLOUD;
        builder2.apiKey = hex;
        builder2.signingKey = this.apkSigningKey;
        new AppInfo(builder2, 0);
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
            task.addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.SrPromptProcessor.textPrompting.1
                @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                public final void onComplete(Task task2) {
                    Result result;
                    Log.d("SrPromptProcessor", "smartReply onComplete isSuccessful : " + task2.isSuccessful());
                    boolean zIsSuccessful = task2.isSuccessful();
                    PromptCallback promptCallback = subscreenDeviceModelB5$mSrResponseCallback$1;
                    if (!zIsSuccessful) {
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
                        str = str3;
                    }
                    ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(new StringBuilder(str));
                }
            });
        }
    }
}
