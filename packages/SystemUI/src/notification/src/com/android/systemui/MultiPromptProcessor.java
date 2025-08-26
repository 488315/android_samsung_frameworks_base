package notification.src.com.android.systemui;

import android.content.Context;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelB5$mSrResponseCallback$1;
import com.android.systemui.util.SettingsHelper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.scs.ai.language.AppInfo;
import com.samsung.android.sdk.scs.ai.language.ErrorClassifier$ErrorCode;
import com.samsung.android.sdk.scs.ai.language.ResultErrorException;
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
import kotlin.Result;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes4.dex */
public final class MultiPromptProcessor implements BasePromptProcessor {
    public final String apkSigningKey;
    public final AppInfo appInfoCloud;
    public final AppInfo appInfoDevice;
    public final Context context;
    public String notificationKey;
    private final SettingsHelper settingsHelper;
    public SmartReplyer smartreplyer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("Output(response=", this.response, ")");
        }
    }

    static {
        new Companion(null);
    }

    public MultiPromptProcessor(Context context, SettingsHelper settingsHelper) throws NoSuchAlgorithmException {
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        this.context = context;
        this.settingsHelper = settingsHelper;
        String hex = null;
        try {
            signingInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 134217728).signingInfo;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getApkSigningKey: ", e, "MultiPromptProcessor");
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
            EmergencyButton$$ExternalSyntheticOutline0.m("Exception getLongHash: ", e2, "MultiPromptProcessor");
        }
        AppInfo.Builder builder = new AppInfo.Builder();
        builder.requestType = AppInfo.RequestType.ONDEVICE;
        builder.apiKey = hex;
        builder.signingKey = this.apkSigningKey;
        this.appInfoDevice = new AppInfo(builder, 0);
        AppInfo.Builder builder2 = new AppInfo.Builder();
        builder2.requestType = AppInfo.RequestType.CLOUD;
        builder2.apiKey = hex;
        builder2.signingKey = this.apkSigningKey;
        this.appInfoCloud = new AppInfo(builder2, 0);
    }

    public static final String access$getErrorMessage(MultiPromptProcessor multiPromptProcessor, int i) {
        return i != 1 ? i != 2 ? multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_error_other_chn) : multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_error_safety_filter_chn) : multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_no_network);
    }

    public static final List access$parseOutput(MultiPromptProcessor multiPromptProcessor, String str) {
        Object failure;
        List response;
        List listDistinct;
        multiPromptProcessor.getClass();
        try {
            int i = Result.$r8$clinit;
            failure = (Output) new Gson().fromJson(str, Output.class);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Output output = (Output) failure;
        return (output == null || (response = output.getResponse()) == null || (listDistinct = CollectionsKt___CollectionsKt.distinct(response)) == null) ? EmptyList.INSTANCE : listDistinct;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final String getNotificationKey() {
        return this.notificationKey;
    }

    @Override // notification.src.com.android.systemui.BasePromptProcessor
    public final void releaseSmartReply() {
        Log.d("MultiPromptProcessor", "call releaseSmartReply");
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0077 A[PHI: r11
      0x0077: PHI (r11v4 com.samsung.android.sdk.scs.base.tasks.Task) = (r11v2 com.samsung.android.sdk.scs.base.tasks.Task), (r11v5 com.samsung.android.sdk.scs.base.tasks.Task) binds: [B:23:0x007d, B:19:0x0055] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // notification.src.com.android.systemui.BasePromptProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void textPrompting(String str, String str2, final SubscreenDeviceModelB5$mSrResponseCallback$1 subscreenDeviceModelB5$mSrResponseCallback$1) {
        AppInfo appInfo;
        Task task;
        Task task2;
        Log.d("MultiPromptProcessor", "textPrompting");
        this.smartreplyer = new SmartReplyer(this.context);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String aiCoreEnabledFeatures = this.settingsHelper.getAiCoreEnabledFeatures();
        final boolean z = aiCoreEnabledFeatures != null && StringsKt__StringsKt.contains(aiCoreEnabledFeatures, "on_device_main", false) && StringsKt__StringsKt.contains(aiCoreEnabledFeatures, "smart_reply", false);
        if (z) {
            linkedHashMap.put("feature_type", "smart_reply");
            appInfo = this.appInfoDevice;
        } else {
            linkedHashMap.put("feature_type", "systemui_notification");
            appInfo = this.appInfoCloud;
        }
        AppInfo appInfo2 = appInfo;
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_SEVENTH && z) {
            Log.d("MultiPromptProcessor", "textPrompting - 3B");
            task = null;
            SmartReplyer smartReplyer = this.smartreplyer;
            if (smartReplyer != null) {
                SmartReplyCategory smartReplyCategory = SmartReplyCategory.SHORT;
                appInfo2.getClass();
                LlmServiceRunnable llmServiceRunnable = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new SmartReplyer$$ExternalSyntheticLambda3(smartReplyer, appInfo2, str, smartReplyCategory, linkedHashMap), new SmartReplyer$$ExternalSyntheticLambda1(1));
                smartReplyer.mServiceExecutor.execute(llmServiceRunnable);
                task2 = llmServiceRunnable.getTask();
            }
        } else {
            task = null;
            SmartReplyer smartReplyer2 = this.smartreplyer;
            if (smartReplyer2 != null) {
                appInfo2.getClass();
                LlmServiceRunnable llmServiceRunnable2 = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new SmartReplyer$$ExternalSyntheticLambda5(smartReplyer2, appInfo2, str, linkedHashMap), new SmartReplyer$$ExternalSyntheticLambda1(1));
                smartReplyer2.mServiceExecutor.execute(llmServiceRunnable2);
                task2 = llmServiceRunnable2.getTask();
            } else {
                task2 = task;
            }
        }
        if (task2 != null) {
            task2.addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.MultiPromptProcessor.textPrompting.1
                @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                public final void onComplete(Task task3) {
                    String str3;
                    com.samsung.android.sdk.scs.ai.language.Result result;
                    Log.d("MultiPromptProcessor", "smartReply onComplete isSuccessful : " + task3.isSuccessful());
                    boolean zIsSuccessful = task3.isSuccessful();
                    PromptCallback promptCallback = subscreenDeviceModelB5$mSrResponseCallback$1;
                    MultiPromptProcessor multiPromptProcessor = MultiPromptProcessor.this;
                    if (!zIsSuccessful) {
                        Log.e("MultiPromptProcessor", "SCS failed: " + task3.getException());
                        String strAccess$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 3);
                        Exception exception = task3.getException();
                        if (exception != null && (exception instanceof ResultErrorException)) {
                            ResultErrorException resultErrorException = (ResultErrorException) exception;
                            if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.DEVICE_NETWORK_ERROR) {
                                strAccess$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 1);
                            } else if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.SAFETY_FILTER_ERROR) {
                                strAccess$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 2);
                            }
                        }
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(strAccess$getErrorMessage);
                        return;
                    }
                    Log.d("MultiPromptProcessor", "SCS success: " + task3.getResult());
                    try {
                        List list = (List) task3.getResult();
                        int i = 0;
                        if (list == null || (result = (com.samsung.android.sdk.scs.ai.language.Result) list.get(0)) == null) {
                            str3 = null;
                        } else {
                            str3 = result.content;
                            if (str3 == null) {
                                str3 = "";
                            }
                        }
                        if (str3 == null) {
                            Log.e("MultiPromptProcessor", "SCS content is null");
                            ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 3));
                            return;
                        }
                        if (z) {
                            ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(new StringBuilder(str3));
                            return;
                        }
                        List listAccess$parseOutput = MultiPromptProcessor.access$parseOutput(multiPromptProcessor, str3);
                        StringBuilder sb = new StringBuilder("");
                        for (Object obj : listAccess$parseOutput) {
                            int i2 = i + 1;
                            if (i < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            sb.append((String) obj);
                            if (i != listAccess$parseOutput.size() - 1) {
                                sb.append("\n");
                            }
                            i = i2;
                        }
                        Log.d("MultiPromptProcessor", "SCS result: " + ((Object) sb));
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onComplete(sb);
                    } catch (Exception e) {
                        Log.e("MultiPromptProcessor", e.toString());
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 3));
                    }
                }
            });
        }
    }
}
