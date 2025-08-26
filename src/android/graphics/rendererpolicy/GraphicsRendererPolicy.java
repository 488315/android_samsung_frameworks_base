package android.graphics.rendererpolicy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.GraphicsStatsService;
import android.graphics.rendererpolicy.ScpmApiContract;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class GraphicsRendererPolicy {
    private static final String ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY = "com.samsung.android.scpm.policy.UPDATE.hwui-skiagl-blocklist";
    private static final String AGENT_POLICY_FILE_DIRECTORY = "/data/system/";
    private static final String AGENT_POLICY_UPDATED_FILE_NAME = "graphics_render_engine_policy.json";
    private static final String AGENT_POLICY_UPDATED_TEMP_FILE_NAME = "graphics_render_engine_policy_temp.json";
    private static final String APP_ID = "zhjjzjgalv";
    public static final boolean DEBUG = checkDebugLogEnable();
    private static final String FRAMEWORK_PACKAGE_NAME = "android";
    private static final String SCPM_POLICY_NAME = "hwui-skiagl-blocklist";
    private static final String TAG = "GraphicsRendererPolicy";
    private static final String VERSION = "1.0.0";
    private final BlocklistChecker mBlocklistChecker;
    private final Context mContext;
    private final ScheduledExecutorService mExecutorService;
    private final GraphicsRendererPolicyCipher mGraphicsRendererPolicyCipher;
    private final Uri SCPM_URI_V2 = Uri.parse(ScpmApiContract.URI_STRING);
    private String mScpmToken = null;
    private final BroadcastReceiver mScpmPolicyUpdateReceiver = new AnonymousClass1();

    private void gLogD(String str) {
        if (DEBUG) {
            Slog.d(TAG, str);
        }
    }

    private void gLogW(String str) {
        if (DEBUG) {
            Slog.w(TAG, str);
        }
    }

    private void gLogE(String str) {
        if (DEBUG) {
            Slog.e(TAG, str);
        }
    }

    private static boolean checkDebugLogEnable() {
        if (isDebugLogEnabledByProperties()) {
            return true;
        }
        return isDebuggableBuildType();
    }

    private static boolean isDebugLogEnabledByProperties() {
        return SystemProperties.getBoolean("persist.hwui.scpm.blocklist.log", false);
    }

    private static boolean isDebuggableBuildType() {
        String str = SystemProperties.get("ro.build.type");
        return "userdebug".equals(str) || "eng".equals(str);
    }

    public GraphicsRendererPolicy(Context context) {
        gLogD("Constructor GraphicsRendererPolicy");
        this.mContext = context;
        this.mGraphicsRendererPolicyCipher = new GraphicsRendererPolicyCipher(context, APP_ID);
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.mBlocklistChecker = new BlocklistChecker();
        init();
    }

    private void init() {
        initForScpm();
        this.mExecutorService.execute(new Runnable() { // from class: android.graphics.rendererpolicy.GraphicsRendererPolicy$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.lambda$init$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() throws IOException {
        gLogD("start agent policy loading");
        tryScpmRegister();
        applyPolicyToChecker();
        gLogD("end agent policy loading");
    }

    private void initForScpm() {
        gLogD("register GraphicRendererPolicy to get update render engine signal");
        IntentFilter intentFilter = new IntentFilter(ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY);
        intentFilter.addAction("com.samsung.android.scpm.policy.CLEAR_DATA");
        intentFilter.addAction(Intent.ACTION_LAZY_BOOT_COMPLETED);
        this.mContext.registerReceiver(this.mScpmPolicyUpdateReceiver, intentFilter, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPolicyToChecker() throws IOException {
        gLogD("applyPolicyToChecker");
        InputStream fdFromStoredPolicy = getFdFromStoredPolicy();
        if (fdFromStoredPolicy == null) {
            gLogW("policyStream is null");
        } else {
            this.mBlocklistChecker.parseConfiguration(fdFromStoredPolicy);
        }
    }

    /* renamed from: android.graphics.rendererpolicy.GraphicsRendererPolicy$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, Intent intent) {
            if (GraphicsRendererPolicy.ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY.equals(intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_UPDATE_RENDER_ENGINE");
                GraphicsRendererPolicy.this.mExecutorService.execute(new Runnable() { // from class: android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        this.f$0.lambda$onReceive$0(context);
                    }
                });
            } else if ("com.samsung.android.scpm.policy.CLEAR_DATA".equals(intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_CLEAR_DATA");
                GraphicsRendererPolicy.this.mExecutorService.schedule(new Runnable() { // from class: android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onReceive$1();
                    }
                }, 60L, TimeUnit.SECONDS);
            } else if (Intent.ACTION_LAZY_BOOT_COMPLETED.equals(intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_LAZY_BOOT_COMPLETED");
                GraphicsRendererPolicy.this.mExecutorService.execute(new Runnable() { // from class: android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        this.f$0.lambda$onReceive$2(context);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Context context) throws IOException {
            GraphicsRendererPolicy.this.loadScpmPolicy(context);
            GraphicsRendererPolicy.this.applyPolicyToChecker();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1() {
            GraphicsRendererPolicy.this.tryScpmRegister();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2(Context context) throws IOException {
            GraphicsRendererPolicy.this.tryScpmRegister();
            GraphicsRendererPolicy.this.loadScpmPolicy(context);
            GraphicsRendererPolicy.this.applyPolicyToChecker();
        }
    }

    private boolean isScpmAvailable() {
        return this.mContext.getPackageManager().resolveContentProvider(ScpmApiContract.AUTHORITY, 0) != null;
    }

    private Bundle callScpmApi(Uri uri, String str, Bundle bundle) {
        return this.mContext.getContentResolver().call(uri, str, "android", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryScpmRegister() {
        try {
            this.mScpmToken = registerAndGetScpmToken();
            gLogD("try to register ");
        } catch (Throwable th) {
            gLogE("register failed. " + th);
        }
    }

    public int getRendererType(String str) throws NumberFormatException {
        if (this.mBlocklistChecker.checkSkiaGlBlocklist(makeQueryInfo(str))) {
            Slog.d(TAG, "pkg: " + str + " need to use GL.");
            return GraphicsStatsService.GraphicsStatsRenderEngine.GL.ordinal();
        }
        return GraphicsStatsService.GraphicsStatsRenderEngine.VK.ordinal();
    }

    private QueryInfo makeQueryInfo(String str) throws NumberFormatException {
        String str2 = SystemProperties.get("ro.product.model", "");
        String str3 = SystemProperties.get("ro.soc.model", "");
        int i = Integer.parseInt(SystemProperties.get("ro.build.version.release", "0"));
        gLogD("makeQueryInfo - packageName: " + str + ", modelName: " + str2 + ", chipsetName: " + str3 + ", osVersion: " + i);
        return new QueryInfo(str, str2, str3, i);
    }

    private InputStream getFdFromStoredPolicy() throws IOException {
        try {
            File file = new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_FILE_NAME);
            if (!file.exists()) {
                gLogW("getFdFromConfiguration encrypted File is not exist.");
                return null;
            }
            File file2 = new File(AGENT_POLICY_FILE_DIRECTORY, "tempFile");
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                this.mGraphicsRendererPolicyCipher.decrypt(file, fileOutputStream);
                InputStream inputStreamNewInputStream = Files.newInputStream(file2.toPath(), new OpenOption[0]);
                fileOutputStream.close();
                return inputStreamNewInputStream;
            } finally {
            }
        } catch (Exception e) {
            gLogE("getFdFromConfiguration failed. " + e);
            return null;
        }
    }

    private String registerAndGetScpmToken() {
        if (isScpmAvailable()) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString("packageName", "android");
                bundle.putString(ScpmApiContract.Key.APP_ID, APP_ID);
                bundle.putString("version", "1.0.0");
                bundle.putString(ScpmApiContract.Key.RECEIVER_PACKAGE_NAME, "android");
                Bundle bundleCall = this.mContext.getContentResolver().call(this.SCPM_URI_V2, ScpmApiContract.Method.REGISTER, "android", bundle);
                if (bundleCall != null) {
                    int i = bundleCall.getInt("result", 1);
                    String string = bundleCall.getString("token", null);
                    int i2 = bundleCall.getInt(ScpmApiContract.Key.RCODE, -1);
                    String string2 = bundleCall.getString(ScpmApiContract.Key.RMSG, "");
                    if (i == 1) {
                        gLogD("success to call");
                        return string;
                    }
                    gLogD("failed to call : rCode = " + i2 + ", rMsg = " + string2);
                    return null;
                }
            } catch (Exception e) {
                gLogE("cannot register package. " + e);
            }
        } else {
            gLogD("service is not available.");
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadScpmPolicy(Context context) {
        BufferedReader bufferedReader;
        gLogD("load policy start");
        if (TextUtils.isEmpty(this.mScpmToken)) {
            gLogD("try to get new permission");
            this.mScpmToken = registerAndGetScpmToken();
        }
        if (TextUtils.isEmpty(this.mScpmToken)) {
            gLogD("fail due to permission error");
            return;
        }
        Uri uri = Uri.parse(ScpmApiContract.URI_STRING + this.mScpmToken + "/hwui-skiagl-blocklist");
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            try {
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                    gLogD("pfd is null");
                    Bundle bundle = new Bundle();
                    bundle.putString("token", this.mScpmToken);
                    Bundle bundleCallScpmApi = callScpmApi(uri, ScpmApiContract.Method.GET_LAST_ERROR, bundle);
                    if (bundleCallScpmApi == null) {
                        gLogD("bundle is null");
                        if (parcelFileDescriptorOpenFileDescriptor == null) {
                            return;
                        }
                    } else {
                        gLogD("code=" + bundleCallScpmApi.getInt(ScpmApiContract.Key.RCODE, -1) + ", msg=" + bundleCallScpmApi.getString(ScpmApiContract.Key.RMSG));
                        if (parcelFileDescriptorOpenFileDescriptor == null) {
                            return;
                        }
                    }
                } else {
                    FileDescriptor fileDescriptor = parcelFileDescriptorOpenFileDescriptor.getFileDescriptor();
                    if (fileDescriptor == null) {
                        gLogD("fd is null");
                        if (parcelFileDescriptorOpenFileDescriptor == null) {
                            return;
                        }
                    } else {
                        try {
                            bufferedReader = new BufferedReader(new FileReader(fileDescriptor));
                        } catch (Exception e) {
                            gLogE("failed to store data. " + e);
                        }
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    sb.append(line);
                                }
                            }
                            storeScpmPolicyToFile(sb);
                            bufferedReader.close();
                            if (parcelFileDescriptorOpenFileDescriptor != null) {
                                parcelFileDescriptorOpenFileDescriptor.close();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                }
                parcelFileDescriptorOpenFileDescriptor.close();
            } finally {
            }
        } catch (Exception e2) {
            gLogE("failed to get data. " + e2);
        }
    }

    private void storeScpmPolicyToFile(StringBuilder sb) throws IOException {
        gLogD("HWUI Renderer policy begin ---------------------- ");
        gLogD(sb.toString());
        gLogD(" ------------------------- HWUI Renderer policy end");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(sb.toString().getBytes());
            try {
                File file = new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_TEMP_FILE_NAME);
                this.mGraphicsRendererPolicyCipher.encrypt(byteArrayInputStream, file);
                File file2 = new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_FILE_NAME);
                if (file2.exists() && !file2.delete()) {
                    gLogE("original file deletion failed");
                }
                if (!file.renameTo(file2)) {
                    gLogE("temp file rename failed");
                }
                byteArrayInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            gLogE("failed to store policy. " + e);
        }
    }
}
