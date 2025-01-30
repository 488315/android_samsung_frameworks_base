package notification.src.com.android.systemui;

import android.content.Context;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.Log;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.p048ai.language.AppInfo;
import com.samsung.android.sdk.scs.p048ai.language.Result;
import com.samsung.android.sdk.scs.p048ai.language.SmartReplyer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SrPromptProcessor implements BasePromptProcessor {
    public final String API_KEY = "74NEkgeVa8SprJ7p";
    public final String apkSigningKey;
    public final AppInfo appInfo;
    public final Context context;
    public String notificationKey;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:0|1|(2:2|3)|(9:9|10|11|12|(2:15|13)|16|17|18|19)|25|10|11|12|(1:13)|16|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005f, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0060, code lost:
    
        androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Exception getLongHash: ", r7, "SrPromptProcessor");
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004f A[Catch: Exception -> 0x005f, LOOP:0: B:13:0x004c->B:15:0x004f, LOOP_END, TryCatch #0 {Exception -> 0x005f, blocks: (B:12:0x003d, B:15:0x004f, B:17:0x0056), top: B:11:0x003d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SrPromptProcessor(Context context) {
        String str;
        int i;
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        this.context = context;
        String str2 = null;
        try {
            signingInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo;
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("getApkSigningKey: ", e, "SrPromptProcessor");
        }
        if (signingInfo != null && (apkContentsSigners = signingInfo.getApkContentsSigners()) != null && apkContentsSigners.length > 0) {
            str = new String(apkContentsSigners[0].toChars());
            this.apkSigningKey = str;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = this.API_KEY.getBytes(StandardCharsets.UTF_8);
            for (i = 0; i < 7; i++) {
                bytes = messageDigest.digest(bytes);
            }
            str2 = HexFormat.of().formatHex(bytes);
            AppInfo.Builder builder = new AppInfo.Builder();
            builder.requestType = AppInfo.RequestType.ONDEVICE;
            builder.apiKey = str2;
            builder.signingKey = this.apkSigningKey;
            this.appInfo = new AppInfo(builder, 0);
        }
        str = null;
        this.apkSigningKey = str;
        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
        byte[] bytes2 = this.API_KEY.getBytes(StandardCharsets.UTF_8);
        while (i < 7) {
        }
        str2 = HexFormat.of().formatHex(bytes2);
        AppInfo.Builder builder2 = new AppInfo.Builder();
        builder2.requestType = AppInfo.RequestType.ONDEVICE;
        builder2.apiKey = str2;
        builder2.signingKey = this.apkSigningKey;
        this.appInfo = new AppInfo(builder2, 0);
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final String getNotificationKey() {
        return this.notificationKey;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void setNotificationKey(String str) {
        this.notificationKey = str;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void textPrompting(String str, String str2, final SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1) {
        Log.d("SrPromptProcessor", "textPrompting");
        SmartReplyer smartReplyer = new SmartReplyer(this.context);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("target_language", str2);
        smartReplyer.smartReply(this.appInfo, str, linkedHashMap).addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.SrPromptProcessor$textPrompting$1
            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                Result result;
                Log.d("SrPromptProcessor", "smartReply onComplete isSuccessful : " + task.isSuccessful());
                boolean isSuccessful = task.isSuccessful();
                PromptCallback promptCallback = PromptCallback.this;
                if (isSuccessful) {
                    Log.d("SrPromptProcessor", " result");
                    List list = (List) task.getResult();
                    if (list != null && (result = (Result) list.get(0)) != null) {
                        r2 = result.content;
                    }
                    ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(new StringBuilder(r2));
                    return;
                }
                Log.d("SrPromptProcessor", " error : " + task.getException());
                Exception exception = task.getException();
                ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(exception != null ? exception.getMessage() : null);
            }
        });
    }
}
