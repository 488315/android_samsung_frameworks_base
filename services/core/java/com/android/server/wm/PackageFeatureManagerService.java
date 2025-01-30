package com.android.server.wm;

import android.content.Context;
import android.os.Build;
import com.samsung.android.server.corescpm.ScpmController;
import com.samsung.android.server.corescpm.ScpmControllerImpl;
import com.samsung.android.server.packagefeature.PackageFeatureController;
import com.samsung.android.server.packagefeature.core.PackageFeatureControllerImpl;
import com.samsung.android.server.util.CoreLogger;
import com.samsung.android.server.util.SafetySystemService;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class PackageFeatureManagerService {
  public final CoreLogger mLogger =
      CoreLogger.getBuilder()
          .setTag("PackageFeature")
          .setDumpTitle("*** Logs ***")
          .setBufferSize(200)
          .build();
  public final PackageFeatureController mPackageFeatureController =
      PackageFeatureControllerImpl.getController();
  public final ScpmController mScpmController =
      ScpmControllerImpl.getScpmController(new ScpmConsumerInfo());

  public class ScpmConsumerInfo extends ScpmController.ConsumerInfo {
    public static final String VERSION = String.valueOf(Build.VERSION.SEM_PLATFORM_INT);

    public ScpmConsumerInfo() {
      super("android", "android", "hz6wdikdtw", VERSION);
    }
  }

  public PackageFeatureManagerService() {
    SafetySystemService.registerForSystemReady(
        new SafetySystemService
            .Callback() { // from class:
                          // com.android.server.wm.PackageFeatureManagerService$$ExternalSyntheticLambda0
          @Override // com.samsung.android.server.util.SafetySystemService.Callback
          public final void onSystemReady(ActivityTaskManagerService activityTaskManagerService) {
            PackageFeatureManagerService.this.lambda$new$0(activityTaskManagerService);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(ActivityTaskManagerService activityTaskManagerService) {
    Context context = activityTaskManagerService.mContext;
    ActivityTaskManagerService.HandlerC2878H handlerC2878H = activityTaskManagerService.f1734mH;
    this.mPackageFeatureController.startController(context, handlerC2878H, this.mLogger);
    PackageFeatureController packageFeatureController = this.mPackageFeatureController;
    final ScpmController scpmController = this.mScpmController;
    Objects.requireNonNull(scpmController);
    packageFeatureController.setFileDescriptorFunction(
        new Function() { // from class:
                         // com.android.server.wm.PackageFeatureManagerService$$ExternalSyntheticLambda1
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            return ScpmController.this.getFileDescriptor((String) obj);
          }
        });
    this.mScpmController.registerScpm(
        context,
        handlerC2878H,
        this.mPackageFeatureController.getGroupNames(),
        this.mPackageFeatureController,
        this.mLogger);
  }

  public void dump(PrintWriter printWriter) {
    this.mScpmController.dump(printWriter);
    this.mPackageFeatureController.dump(printWriter);
  }

  public boolean executeShellCommand(PrintWriter printWriter, String[] strArr, String str) {
    return this.mPackageFeatureController.executeShellCommand(printWriter, strArr, str);
  }

  public String getScpmVersion(String str) {
    return this.mPackageFeatureController.getScpmVersion(str);
  }
}
