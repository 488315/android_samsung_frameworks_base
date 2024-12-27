package android.graphics.rendererpolicy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.GraphicsStatsService;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GraphicsRendererPolicy {
    private static final String ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY =
            "com.samsung.android.scpm.policy.UPDATE.hwui-skiagl-blocklist";
    private static final String AGENT_POLICY_FILE_DIRECTORY = "/data/system/";
    private static final String AGENT_POLICY_UPDATED_FILE_NAME =
            "graphics_render_engine_policy.json";
    private static final String AGENT_POLICY_UPDATED_TEMP_FILE_NAME =
            "graphics_render_engine_policy_temp.json";
    private static final String APP_ID = "zhjjzjgalv";
    private static final String FRAMEWORK_PACKAGE_NAME = "android";
    private static final String SCPM_POLICY_NAME = "hwui-skiagl-blocklist";
    private static final String VERSION = "1.0.0";
    private final BlocklistChecker mBlocklistChecker;
    private final Context mContext;
    private final ScheduledExecutorService mExecutorService;
    private final GraphicsRendererPolicyCipher mGraphicsRendererPolicyCipher;
    private static final String TAG = GraphicsRendererPolicy.class.getSimpleName();
    public static final boolean DEBUG = checkDebugLogEnable();
    private final Uri SCPM_URI_V2 = Uri.parse(ScpmApiContract.URI_STRING);
    private String mScpmToken = null;
    private final BroadcastReceiver mScpmPolicyUpdateReceiver = new AnonymousClass1();

    private void gLogD(String message) {
        if (DEBUG) {
            Slog.d(TAG, message);
        }
    }

    private void gLogW(String message) {
        if (DEBUG) {
            Slog.w(TAG, message);
        }
    }

    private void gLogE(String message) {
        if (DEBUG) {
            Slog.e(TAG, message);
        }
    }

    private static boolean checkDebugLogEnable() {
        if (isDebugLogEnabledByProperties()) {
            return true;
        }
        return isDebuggableBuildType();
    }

    private static boolean isDebugLogEnabledByProperties() {
        return SystemProperties.getBoolean("debug.hwui.scpm.blocklist.log", false);
    }

    private static boolean isDebuggableBuildType() {
        String buildType = SystemProperties.get("ro.build.type");
        return "userdebug".equals(buildType) || "eng".equals(buildType);
    }

    public GraphicsRendererPolicy(Context context) {
        gLogD("Constructor GraphicsRendererPolicy");
        this.mContext = context;
        this.mGraphicsRendererPolicyCipher =
                new GraphicsRendererPolicyCipher(this.mContext, APP_ID);
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.mBlocklistChecker = new BlocklistChecker();
        init();
    }

    private void init() {
        initForScpm();
        this.mExecutorService.execute(
                new Runnable() { // from class:
                    // android.graphics.rendererpolicy.GraphicsRendererPolicy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GraphicsRendererPolicy.this.lambda$init$0();
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        gLogD("start agent policy loading");
        tryScpmRegister();
        applyPolicyToChecker();
        gLogD("end agent policy loading");
    }

    private void initForScpm() {
        gLogD("register GraphicRendererPolicy to get update render engine signal");
        IntentFilter filter = new IntentFilter(ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY);
        filter.addAction("com.samsung.android.scpm.policy.CLEAR_DATA");
        filter.addAction(Intent.ACTION_LAZY_BOOT_COMPLETED);
        this.mContext.registerReceiver(this.mScpmPolicyUpdateReceiver, filter, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPolicyToChecker() {
        gLogD("applyPolicyToChecker");
        InputStream policyStream = getFdFromStoredPolicy();
        if (policyStream == null) {
            gLogW("policyStream is null");
        } else {
            this.mBlocklistChecker.parseConfiguration(policyStream);
        }
    }

    /* renamed from: android.graphics.rendererpolicy.GraphicsRendererPolicy$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context context, Intent intent) {
            if (GraphicsRendererPolicy.ACTION_SCPM_UPDATE_RENDER_ENGINE_POLICY.equals(
                    intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_UPDATE_RENDER_ENGINE");
                GraphicsRendererPolicy.this.mExecutorService.execute(
                        new Runnable() { // from class:
                            // android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                GraphicsRendererPolicy.AnonymousClass1.this.lambda$onReceive$0(
                                        context);
                            }
                        });
            } else if ("com.samsung.android.scpm.policy.CLEAR_DATA".equals(intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_CLEAR_DATA");
                GraphicsRendererPolicy.this.mExecutorService.schedule(
                        new Runnable() { // from class:
                            // android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                GraphicsRendererPolicy.AnonymousClass1.this.lambda$onReceive$1();
                            }
                        },
                        60L,
                        TimeUnit.SECONDS);
            } else if (Intent.ACTION_LAZY_BOOT_COMPLETED.equals(intent.getAction())) {
                Slog.d(GraphicsRendererPolicy.TAG, "ACTION_LAZY_BOOT_COMPLETED");
                GraphicsRendererPolicy.this.mExecutorService.execute(
                        new Runnable() { // from class:
                            // android.graphics.rendererpolicy.GraphicsRendererPolicy$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                GraphicsRendererPolicy.AnonymousClass1.this.lambda$onReceive$2(
                                        context);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(Context context) {
            GraphicsRendererPolicy.this.loadScpmPolicy(context);
            GraphicsRendererPolicy.this.applyPolicyToChecker();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1() {
            GraphicsRendererPolicy.this.tryScpmRegister();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$2(Context context) {
            GraphicsRendererPolicy.this.tryScpmRegister();
            GraphicsRendererPolicy.this.loadScpmPolicy(context);
            GraphicsRendererPolicy.this.applyPolicyToChecker();
        }
    }

    private boolean isScpmAvailable() {
        PackageManager pm = this.mContext.getPackageManager();
        return pm.resolveContentProvider(ScpmApiContract.AUTHORITY, 0) != null;
    }

    private Bundle callScpmApi(Uri uri, String methodName, Bundle extras) {
        return this.mContext.getContentResolver().call(uri, methodName, "android", extras);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryScpmRegister() {
        try {
            this.mScpmToken = registerAndGetScpmToken();
            gLogD("try to register ");
        } catch (Throwable e) {
            gLogE("register failed. " + e);
        }
    }

    public int getRendererType(String packageName) {
        QueryInfo queryInfo = makeQueryInfo(packageName);
        if (this.mBlocklistChecker.checkSkiaGlBlocklist(queryInfo)) {
            Slog.d(TAG, "pkg: " + packageName + " need to use GL.");
            return GraphicsStatsService.GraphicsStatsRenderEngine.GL.ordinal();
        }
        return GraphicsStatsService.GraphicsStatsRenderEngine.VK.ordinal();
    }

    private QueryInfo makeQueryInfo(String packageName) {
        String modelName = SystemProperties.get("ro.product.model", "");
        String chipsetName = SystemProperties.get("ro.soc.model", "");
        String osVersionString = SystemProperties.get("ro.build.version.release", "0");
        int osVersion = Integer.parseInt(osVersionString);
        gLogD(
                "makeQueryInfo - packageName: "
                        + packageName
                        + ", modelName: "
                        + modelName
                        + ", chipsetName: "
                        + chipsetName
                        + ", osVersion: "
                        + osVersion);
        return new QueryInfo(packageName, modelName, chipsetName, osVersion);
    }

    private InputStream getFdFromStoredPolicy() {
        try {
            File encryptedFile =
                    new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_FILE_NAME);
            if (!encryptedFile.exists()) {
                gLogW("getFdFromConfiguration encrypted File is not exist.");
                return null;
            }
            File tempFile = new File(AGENT_POLICY_FILE_DIRECTORY, "tempFile");
            FileOutputStream tempFileStream = new FileOutputStream(tempFile);
            this.mGraphicsRendererPolicyCipher.decrypt(encryptedFile, tempFileStream);
            return Files.newInputStream(tempFile.toPath(), new OpenOption[0]);
        } catch (Exception e) {
            gLogE("getFdFromConfiguration failed. " + e);
            return null;
        }
    }

    private String registerAndGetScpmToken() {
        if (isScpmAvailable()) {
            try {
                Bundle extras = new Bundle();
                extras.putString("packageName", "android");
                extras.putString(ScpmApiContract.Key.APP_ID, APP_ID);
                extras.putString("version", "1.0.0");
                extras.putString(ScpmApiContract.Key.RECEIVER_PACKAGE_NAME, "android");
                Bundle bundle =
                        this.mContext
                                .getContentResolver()
                                .call(
                                        this.SCPM_URI_V2,
                                        ScpmApiContract.Method.REGISTER,
                                        "android",
                                        extras);
                if (bundle != null) {
                    int result = bundle.getInt("result", 1);
                    String token = bundle.getString("token", null);
                    int rCode = bundle.getInt(ScpmApiContract.Key.RCODE, -1);
                    String rMsg = bundle.getString(ScpmApiContract.Key.RMSG, "");
                    if (result == 1) {
                        gLogD("success to call");
                        return token;
                    }
                    gLogD("failed to call : rCode = " + rCode + ", rMsg = " + rMsg);
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
        gLogD("load policy start");
        if (TextUtils.isEmpty(this.mScpmToken)) {
            gLogD("try to get new permission");
            this.mScpmToken = registerAndGetScpmToken();
        }
        if (TextUtils.isEmpty(this.mScpmToken)) {
            gLogD("fail due to permission error");
            return;
        }
        try {
            ParcelFileDescriptor pfd = getScpmParcelFile(context, this.mScpmToken);
            try {
                if (pfd == null) {
                    gLogD("pfd is null");
                    if (pfd != null) {
                        pfd.close();
                        return;
                    }
                    return;
                }
                FileDescriptor fd = pfd.getFileDescriptor();
                if (fd != null) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(fd));
                        try {
                            StringBuilder jsonBuilder = new StringBuilder();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    jsonBuilder.append(line);
                                }
                            }
                            storeScpmPolicyToFile(jsonBuilder);
                            bufferedReader.close();
                        } catch (Throwable th) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Exception e) {
                        gLogE("failed to store data. " + e);
                    }
                }
                if (pfd != null) {
                    pfd.close();
                }
            } finally {
            }
        } catch (Exception e2) {
            gLogE("failed to get data. " + e2);
        }
    }

    private void storeScpmPolicyToFile(StringBuilder jsonBuilder) {
        gLogD("HWUI Renderer policy begin ---------------------- ");
        gLogD(jsonBuilder.toString());
        gLogD(" ------------------------- HWUI Renderer policy end");
        try {
            InputStream inputStream = new ByteArrayInputStream(jsonBuilder.toString().getBytes());
            try {
                File tempFile =
                        new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_TEMP_FILE_NAME);
                this.mGraphicsRendererPolicyCipher.encrypt(inputStream, tempFile);
                File originalFile =
                        new File(AGENT_POLICY_FILE_DIRECTORY, AGENT_POLICY_UPDATED_FILE_NAME);
                if (originalFile.exists() && !originalFile.delete()) {
                    gLogE("original file deletion failed");
                }
                if (!tempFile.renameTo(originalFile)) {
                    gLogE("temp file rename failed");
                }
                inputStream.close();
            } finally {
            }
        } catch (Exception e) {
            gLogE("failed to store policy. " + e);
        }
    }

    private ParcelFileDescriptor getScpmParcelFile(Context context, String token) {
        gLogD("begin of the get data");
        Uri uri = Uri.parse(ScpmApiContract.URI_STRING + token + "/" + SCPM_POLICY_NAME);
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null) {
                Bundle extras = new Bundle();
                extras.putString("token", token);
                Bundle bundle = callScpmApi(uri, ScpmApiContract.Method.GET_LAST_ERROR, extras);
                if (bundle == null) {
                    gLogD("bundle is null");
                    return null;
                }
                gLogD(
                        "code="
                                + bundle.getInt(ScpmApiContract.Key.RCODE, -1)
                                + ", msg="
                                + bundle.getString(ScpmApiContract.Key.RMSG));
                return null;
            }
            gLogD("end of the get data");
            return pfd;
        } catch (Exception e) {
            gLogE("failed to get policy. " + e);
            return null;
        }
    }
}
