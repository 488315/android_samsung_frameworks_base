package android.view;

import android.Manifest;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemClock;
import android.util.Log;
import android.util.secutil.Slog;
import com.android.internal.util.GcUtils;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class SurfaceControlRegistry {
  private static final int DUMP_LIMIT = 256;
  private static final int MAX_LAYERS_REPORTING_THRESHOLD = 1024;
  private static final int RESET_REPORTING_THRESHOLD = 256;
  private static final String TAG = "SurfaceControlRegistry";
  private static volatile SurfaceControlRegistry sProcessRegistry;
  private static final Object sLock = new Object();
  private static final DefaultReporter sDefaultReporter = new DefaultReporter();
  private int mMaxLayersReportingThreshold = 1024;
  private int mResetReportingThreshold = 256;
  private boolean mHasReportedExceedingMaxThreshold = false;
  private Reporter mReporter = sDefaultReporter;
  private final WeakHashMap<SurfaceControl, Long> mSurfaceControls = new WeakHashMap<>(256);

  public interface Reporter {
    void onMaxLayersExceeded(
        WeakHashMap<SurfaceControl, Long> weakHashMap, int i, PrintWriter printWriter);
  }

  /* JADX INFO: Access modifiers changed from: private */
  static class DefaultReporter implements Reporter {
    private DefaultReporter() {}

    @Override // android.view.SurfaceControlRegistry.Reporter
    public void onMaxLayersExceeded(
        WeakHashMap<SurfaceControl, Long> surfaceControls, int limit, PrintWriter pw) {
      int limit2;
      long now = SystemClock.elapsedRealtime();
      ArrayList<Map.Entry<SurfaceControl, Long>> entries = new ArrayList<>();
      Iterator<Map.Entry<SurfaceControl, Long>> it = surfaceControls.entrySet().iterator();
      while (it.hasNext()) {
        entries.add(it.next());
      }
      try {
        entries.sort(
            new Comparator() { // from class:
                               // android.view.SurfaceControlRegistry$DefaultReporter$$ExternalSyntheticLambda0
              @Override // java.util.Comparator
              public final int compare(Object obj, Object obj2) {
                return SurfaceControlRegistry.DefaultReporter.lambda$onMaxLayersExceeded$0(
                    (Map.Entry) obj, (Map.Entry) obj2);
              }
            });
        limit2 = limit;
      } catch (IllegalArgumentException e) {
        Slog.m138e(
            SurfaceControlRegistry.TAG,
            "IllegalArgumentException while sort entries. use max limit without sorted list");
        limit2 = 256;
        Iterator<Map.Entry<SurfaceControl, Long>> it2 = surfaceControls.entrySet().iterator();
        while (it2.hasNext()) {
          entries.add(it2.next());
        }
      }
      int size = Math.min(entries.size(), limit2);
      pw.println(SurfaceControlRegistry.TAG);
      pw.println("----------------------");
      pw.println("Listing oldest " + size + " of " + surfaceControls.size());
      for (int i = 0; i < size; i++) {
        Map.Entry<SurfaceControl, Long> entry = entries.get(i);
        SurfaceControl sc = entry.getKey();
        if (sc != null) {
          long timeRegistered = entry.getValue().longValue();
          pw.print("  ");
          pw.print(sc.getName());
          pw.print(" (" + sc.getCallsite() + NavigationBarInflaterView.KEY_CODE_END);
          pw.println(" [" + ((now - timeRegistered) / 1000) + "s ago]");
        }
      }
    }

    static /* synthetic */ int lambda$onMaxLayersExceeded$0(Map.Entry o1, Map.Entry o2) {
      return (int) (((Long) o1.getValue()).longValue() - ((Long) o2.getValue()).longValue());
    }

    private static /* synthetic */ int lambda$onMaxLayersExceeded$1(Map.Entry o1, Map.Entry o2) {
      return (int) (((Long) o1.getValue()).longValue() - ((Long) o2.getValue()).longValue());
    }
  }

  private SurfaceControlRegistry() {}

  public void setReportingThresholds(
      int maxLayersReportingThreshold, int resetReportingThreshold, Reporter reporter) {
    synchronized (sLock) {
      if (maxLayersReportingThreshold <= 0
          || resetReportingThreshold >= maxLayersReportingThreshold) {
        throw new IllegalArgumentException(
            "Expected maxLayersReportingThreshold ("
                + maxLayersReportingThreshold
                + ") to be > 0 and resetReportingThreshold ("
                + resetReportingThreshold
                + ") to be < maxLayersReportingThreshold");
      }
      if (reporter == null) {
        throw new IllegalArgumentException("Expected non-null reporter");
      }
      this.mMaxLayersReportingThreshold = maxLayersReportingThreshold;
      this.mResetReportingThreshold = resetReportingThreshold;
      this.mHasReportedExceedingMaxThreshold = false;
      this.mReporter = reporter;
    }
  }

  public static void createProcessInstance(Context context) {
    if (context.checkSelfPermission(Manifest.permission.READ_FRAME_BUFFER) != 0) {
      throw new SecurityException("Expected caller to hold READ_FRAME_BUFFER");
    }
    synchronized (sLock) {
      if (sProcessRegistry == null) {
        sProcessRegistry = new SurfaceControlRegistry();
      }
    }
  }

  public static void destroyProcessInstance() {
    synchronized (sLock) {
      if (sProcessRegistry == null) {
        return;
      }
      sProcessRegistry = null;
    }
  }

  public static SurfaceControlRegistry getProcessInstance() {
    SurfaceControlRegistry surfaceControlRegistry;
    synchronized (sLock) {
      surfaceControlRegistry = sProcessRegistry;
    }
    return surfaceControlRegistry;
  }

  void add(SurfaceControl sc) {
    synchronized (sLock) {
      this.mSurfaceControls.put(sc, Long.valueOf(SystemClock.elapsedRealtime()));
      if (!this.mHasReportedExceedingMaxThreshold
          && this.mSurfaceControls.size() >= this.mMaxLayersReportingThreshold) {
        PrintWriter pw = new PrintWriter((OutputStream) System.out, true);
        this.mReporter.onMaxLayersExceeded(this.mSurfaceControls, 256, pw);
        this.mHasReportedExceedingMaxThreshold = true;
      }
    }
  }

  void remove(SurfaceControl sc) {
    synchronized (sLock) {
      this.mSurfaceControls.remove(sc);
      if (this.mHasReportedExceedingMaxThreshold
          && this.mSurfaceControls.size() <= this.mResetReportingThreshold) {
        this.mHasReportedExceedingMaxThreshold = false;
      }
    }
  }

  public int hashCode() {
    int hashCode;
    synchronized (sLock) {
      hashCode = this.mSurfaceControls.keySet().hashCode();
    }
    return hashCode;
  }

  private static void runGcAndFinalizers() {
    long t = SystemClock.elapsedRealtime();
    GcUtils.runGcAndFinalizersSync();
    Log.m98i(TAG, "Ran gc and finalizers (" + (SystemClock.elapsedRealtime() - t) + "ms)");
  }

  public static void dump(int limit, boolean runGc, PrintWriter pw) {
    if (runGc) {
      runGcAndFinalizers();
    }
    synchronized (sLock) {
      if (sProcessRegistry != null) {
        sDefaultReporter.onMaxLayersExceeded(sProcessRegistry.mSurfaceControls, limit, pw);
      }
    }
  }
}
