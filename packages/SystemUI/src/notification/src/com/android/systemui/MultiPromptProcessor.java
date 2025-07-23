package notification.src.com.android.systemui;

import android.content.Context;
import android.util.Log;
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
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Result;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MultiPromptProcessor implements BasePromptProcessor {
    public final String apkSigningKey;
    public final AppInfo appInfoCloud;
    public final AppInfo appInfoDevice;
    public final Context context;
    public String notificationKey;
    private final SettingsHelper settingsHelper;
    public SmartReplyer smartreplyer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|2|3|(9:9|10|11|12|(2:15|13)|16|17|18|19)|26|10|11|12|(1:13)|16|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
    
        com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m("Exception getLongHash: ", r6, "MultiPromptProcessor");
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004d A[Catch: Exception -> 0x0054, LOOP:0: B:13:0x004a->B:15:0x004d, LOOP_END, TryCatch #0 {Exception -> 0x0054, blocks: (B:12:0x003b, B:15:0x004d, B:17:0x0056), top: B:11:0x003b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MultiPromptProcessor(android.content.Context r6, com.android.systemui.util.SettingsHelper r7) {
        /*
            r5 = this;
            java.lang.String r0 = "MultiPromptProcessor"
            r5.<init>()
            r5.context = r6
            r5.settingsHelper = r7
            r7 = 0
            r1 = 0
            android.content.pm.PackageManager r2 = r6.getPackageManager()     // Catch: java.lang.Exception -> L32
            java.lang.String r6 = r6.getPackageName()     // Catch: java.lang.Exception -> L32
            r3 = 134217728(0x8000000, float:3.85186E-34)
            android.content.pm.PackageInfo r6 = r2.getPackageInfo(r6, r3)     // Catch: java.lang.Exception -> L32
            android.content.pm.SigningInfo r6 = r6.signingInfo     // Catch: java.lang.Exception -> L32
            if (r6 == 0) goto L38
            android.content.pm.Signature[] r6 = r6.getApkContentsSigners()     // Catch: java.lang.Exception -> L32
            if (r6 == 0) goto L38
            int r2 = r6.length     // Catch: java.lang.Exception -> L32
            if (r2 <= 0) goto L38
            r6 = r6[r7]     // Catch: java.lang.Exception -> L32
            char[] r6 = r6.toChars()     // Catch: java.lang.Exception -> L32
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L32
            r2.<init>(r6)     // Catch: java.lang.Exception -> L32
            goto L39
        L32:
            r6 = move-exception
            java.lang.String r2 = "getApkSigningKey: "
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r2, r6, r0)
        L38:
            r2 = r1
        L39:
            r5.apkSigningKey = r2
            java.lang.String r6 = "SHA-256"
            java.security.MessageDigest r6 = java.security.MessageDigest.getInstance(r6)     // Catch: java.lang.Exception -> L54
            java.lang.String r2 = "74NEkgeVa8SprJ7p"
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Exception -> L54
            byte[] r2 = r2.getBytes(r3)     // Catch: java.lang.Exception -> L54
            r3 = r7
        L4a:
            r4 = 7
            if (r3 >= r4) goto L56
            byte[] r2 = r6.digest(r2)     // Catch: java.lang.Exception -> L54
            int r3 = r3 + 1
            goto L4a
        L54:
            r6 = move-exception
            goto L5f
        L56:
            java.util.HexFormat r6 = java.util.HexFormat.of()     // Catch: java.lang.Exception -> L54
            java.lang.String r1 = r6.formatHex(r2)     // Catch: java.lang.Exception -> L54
            goto L64
        L5f:
            java.lang.String r2 = "Exception getLongHash: "
            com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0.m(r2, r6, r0)
        L64:
            com.samsung.android.sdk.scs.ai.language.AppInfo$Builder r6 = new com.samsung.android.sdk.scs.ai.language.AppInfo$Builder
            r6.<init>()
            com.samsung.android.sdk.scs.ai.language.AppInfo$RequestType r0 = com.samsung.android.sdk.scs.ai.language.AppInfo.RequestType.ONDEVICE
            r6.requestType = r0
            r6.apiKey = r1
            java.lang.String r0 = r5.apkSigningKey
            r6.signingKey = r0
            com.samsung.android.sdk.scs.ai.language.AppInfo r0 = new com.samsung.android.sdk.scs.ai.language.AppInfo
            r0.<init>(r6, r7)
            r5.appInfoDevice = r0
            com.samsung.android.sdk.scs.ai.language.AppInfo$Builder r6 = new com.samsung.android.sdk.scs.ai.language.AppInfo$Builder
            r6.<init>()
            com.samsung.android.sdk.scs.ai.language.AppInfo$RequestType r0 = com.samsung.android.sdk.scs.ai.language.AppInfo.RequestType.CLOUD
            r6.requestType = r0
            r6.apiKey = r1
            java.lang.String r0 = r5.apkSigningKey
            r6.signingKey = r0
            com.samsung.android.sdk.scs.ai.language.AppInfo r0 = new com.samsung.android.sdk.scs.ai.language.AppInfo
            r0.<init>(r6, r7)
            r5.appInfoCloud = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: notification.src.com.android.systemui.MultiPromptProcessor.<init>(android.content.Context, com.android.systemui.util.SettingsHelper):void");
    }

    public static final String access$getErrorMessage(MultiPromptProcessor multiPromptProcessor, int i) {
        return i != 1 ? i != 2 ? multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_error_other_chn) : multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_error_safety_filter_chn) : multiPromptProcessor.context.getString(R.string.subscreen_notification_smart_reply_no_network);
    }

    public static final List access$parseOutput(MultiPromptProcessor multiPromptProcessor, String str) {
        Object failure;
        List response;
        List distinct;
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
        return (output == null || (response = output.getResponse()) == null || (distinct = CollectionsKt___CollectionsKt.distinct(response)) == null) ? EmptyList.INSTANCE : distinct;
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

    @Override // notification.src.com.android.systemui.BasePromptProcessor
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
            task2 = task;
        } else {
            task = null;
            SmartReplyer smartReplyer2 = this.smartreplyer;
            if (smartReplyer2 != null) {
                appInfo2.getClass();
                LlmServiceRunnable llmServiceRunnable2 = new LlmServiceRunnable("FEATURE_AI_GEN_SMART_REPLY", false, new SmartReplyer$$ExternalSyntheticLambda5(smartReplyer2, appInfo2, str, linkedHashMap), new SmartReplyer$$ExternalSyntheticLambda1(1));
                smartReplyer2.mServiceExecutor.execute(llmServiceRunnable2);
                task2 = llmServiceRunnable2.getTask();
            }
            task2 = task;
        }
        if (task2 != null) {
            task2.addOnCompleteListener(new OnCompleteListener() { // from class: notification.src.com.android.systemui.MultiPromptProcessor$textPrompting$1
                @Override // com.samsung.android.sdk.scs.base.tasks.OnCompleteListener
                public final void onComplete(Task task3) {
                    String str3;
                    com.samsung.android.sdk.scs.ai.language.Result result;
                    Log.d("MultiPromptProcessor", "smartReply onComplete isSuccessful : " + task3.isSuccessful());
                    boolean isSuccessful = task3.isSuccessful();
                    PromptCallback promptCallback = subscreenDeviceModelB5$mSrResponseCallback$1;
                    MultiPromptProcessor multiPromptProcessor = MultiPromptProcessor.this;
                    if (!isSuccessful) {
                        Log.e("MultiPromptProcessor", "SCS failed: " + task3.getException());
                        String access$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 3);
                        Exception exception = task3.getException();
                        if (exception != null && (exception instanceof ResultErrorException)) {
                            ResultErrorException resultErrorException = (ResultErrorException) exception;
                            if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.DEVICE_NETWORK_ERROR) {
                                access$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 1);
                            } else if (resultErrorException.getErrorCodeClassified() == ErrorClassifier$ErrorCode.SAFETY_FILTER_ERROR) {
                                access$getErrorMessage = MultiPromptProcessor.access$getErrorMessage(multiPromptProcessor, 2);
                            }
                        }
                        ((SubscreenDeviceModelB5$mSrResponseCallback$1) promptCallback).onFailure(access$getErrorMessage);
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
                        List access$parseOutput = MultiPromptProcessor.access$parseOutput(multiPromptProcessor, str3);
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
