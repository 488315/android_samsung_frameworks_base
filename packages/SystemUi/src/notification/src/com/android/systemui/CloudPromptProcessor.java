package notification.src.com.android.systemui;

import android.content.Context;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.scs.base.tasks.OnCompleteListener;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.p048ai.language.AppInfo;
import com.samsung.android.sdk.scs.p048ai.language.ErrorClassifier$ErrorCode;
import com.samsung.android.sdk.scs.p048ai.language.ResultErrorException;
import com.samsung.android.sdk.scs.p048ai.language.SmartReplyer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CloudPromptProcessor implements BasePromptProcessor {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Output {

        @SerializedName("response")
        private final List<String> response;

        public Output(List<String> list) {
            this.response = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Output) && Intrinsics.areEqual(this.response, ((Output) obj).response);
        }

        public final List getResponse() {
            return this.response;
        }

        public final int hashCode() {
            List<String> list = this.response;
            if (list == null) {
                return 0;
            }
            return list.hashCode();
        }

        public final String toString() {
            return "Output(response=" + this.response + ")";
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|2|3|(9:9|10|11|12|(2:15|13)|16|17|18|19)|25|10|11|12|(1:13)|16|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005b, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005c, code lost:
    
        com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m58m("Exception getLongHash: ", r7, "CloudPromptProcessor");
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004b A[Catch: Exception -> 0x005b, LOOP:0: B:13:0x0048->B:15:0x004b, LOOP_END, TryCatch #1 {Exception -> 0x005b, blocks: (B:12:0x0039, B:15:0x004b, B:17:0x0052), top: B:11:0x0039 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CloudPromptProcessor(Context context) {
        String str;
        int i;
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        this.context = context;
        String str2 = null;
        try {
            signingInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getApkSigningKey: ", e, "CloudPromptProcessor");
        }
        if (signingInfo != null && (apkContentsSigners = signingInfo.getApkContentsSigners()) != null && apkContentsSigners.length > 0) {
            str = new String(apkContentsSigners[0].toChars());
            this.apkSigningKey = str;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = "74NEkgeVa8SprJ7p".getBytes(StandardCharsets.UTF_8);
            for (i = 0; i < 7; i++) {
                bytes = messageDigest.digest(bytes);
            }
            str2 = HexFormat.of().formatHex(bytes);
            AppInfo.Builder builder = new AppInfo.Builder();
            builder.requestType = AppInfo.RequestType.CLOUD;
            builder.enableStreaming = false;
            builder.apiKey = str2;
            builder.signingKey = this.apkSigningKey;
            this.appInfo = new AppInfo(builder, 0);
        }
        str = null;
        this.apkSigningKey = str;
        MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
        byte[] bytes2 = "74NEkgeVa8SprJ7p".getBytes(StandardCharsets.UTF_8);
        while (i < 7) {
        }
        str2 = HexFormat.of().formatHex(bytes2);
        AppInfo.Builder builder2 = new AppInfo.Builder();
        builder2.requestType = AppInfo.RequestType.CLOUD;
        builder2.enableStreaming = false;
        builder2.apiKey = str2;
        builder2.signingKey = this.apkSigningKey;
        this.appInfo = new AppInfo(builder2, 0);
    }

    public static final String access$getErrorMessage(CloudPromptProcessor cloudPromptProcessor, int i) {
        Context context = cloudPromptProcessor.context;
        return i != 1 ? i != 2 ? context.getString(R.string.subscreen_notification_smart_reply_server_error) : context.getString(R.string.subscreen_notification_smart_reply_filter) : context.getString(R.string.subscreen_notification_smart_reply_no_network);
    }

    public static final List access$parseOutput(CloudPromptProcessor cloudPromptProcessor, String str) {
        Object failure;
        List response;
        List distinct;
        cloudPromptProcessor.getClass();
        try {
            int i = Result.$r8$clinit;
            failure = (Output) new Gson().fromJson(Output.class, str);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Output output = (Output) failure;
        return (output == null || (response = output.getResponse()) == null || (distinct = CollectionsKt___CollectionsKt.distinct(response)) == null) ? EmptyList.INSTANCE : distinct;
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
        HashMap hashMap = new HashMap();
        hashMap.put("feature_type", "systemui_notification");
        new SmartReplyer(this.context).smartReply(this.appInfo, str, hashMap).addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.CloudPromptProcessor$textPrompting$1
            @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                String access$getErrorMessage;
                com.samsung.android.sdk.scs.p048ai.language.Result result;
                boolean isSuccessful = task.isSuccessful();
                PromptCallback promptCallback = subscreenDeviceModelB5$mSrResponseCallback$1;
                CloudPromptProcessor cloudPromptProcessor = CloudPromptProcessor.this;
                if (!isSuccessful) {
                    Log.e("CloudPromptProcessor", "SCS failed: " + task.getException());
                    String access$getErrorMessage2 = CloudPromptProcessor.access$getErrorMessage(cloudPromptProcessor, 3);
                    Exception exception = task.getException();
                    if (exception != null && (exception instanceof ResultErrorException)) {
                        ResultErrorException resultErrorException = (ResultErrorException) exception;
                        if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.DEVICE_NETORK_ERROR) {
                            access$getErrorMessage = CloudPromptProcessor.access$getErrorMessage(cloudPromptProcessor, 1);
                        } else if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.SAFETY_FILTER_ERROR) {
                            access$getErrorMessage = CloudPromptProcessor.access$getErrorMessage(cloudPromptProcessor, 2);
                        }
                        access$getErrorMessage2 = access$getErrorMessage;
                    }
                    ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(access$getErrorMessage2);
                    return;
                }
                Log.d("CloudPromptProcessor", "SCS success: " + task.getResult());
                try {
                    List list = (List) task.getResult();
                    Unit unit = null;
                    int i = 0;
                    String str3 = (list == null || (result = (com.samsung.android.sdk.scs.p048ai.language.Result) list.get(0)) == null) ? null : result.content;
                    if (str3 != null) {
                        List access$parseOutput = CloudPromptProcessor.access$parseOutput(cloudPromptProcessor, str3);
                        StringBuilder sb = new StringBuilder("");
                        for (Object obj : access$parseOutput) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            sb.append((String) obj);
                            if (i != access$parseOutput.size() - 1) {
                                sb.append("\n");
                            }
                            i = i2;
                        }
                        Log.d("CloudPromptProcessor", "SCS result: " + ((Object) sb));
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(sb);
                        unit = Unit.INSTANCE;
                    }
                    if (unit == null) {
                        Log.e("CloudPromptProcessor", "SCS content is null");
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(CloudPromptProcessor.access$getErrorMessage(cloudPromptProcessor, 3));
                    }
                } catch (Exception e) {
                    Log.e("CloudPromptProcessor", e.toString());
                    ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(CloudPromptProcessor.access$getErrorMessage(cloudPromptProcessor, 3));
                }
            }
        });
    }
}
