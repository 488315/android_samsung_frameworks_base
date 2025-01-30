package com.android.server.am.pmm;

import android.content.Context;
import com.android.server.am.ActivityManagerService;

/* loaded from: classes.dex */
public class PersonalizedMemoryManager {
  public ActivityManagerService mActivityManagerService;
  public Context mContext;
  public DmaBufLeakDetector mDmaBufLeakDetector;
  public boolean mIsTestMode;

  public abstract class LazyHolder {
    public static final PersonalizedMemoryManager INSTANCE = new PersonalizedMemoryManager();
  }

  public void init(ActivityManagerService activityManagerService, Context context) {
    this.mActivityManagerService = activityManagerService;
    this.mContext = context;
    HeapDumpHelper.cleanUpPath();
    this.mDmaBufLeakDetector = new DmaBufLeakDetector();
  }

  public static PersonalizedMemoryManager getInstance() {
    return LazyHolder.INSTANCE;
  }

  public void setTestMode(boolean z) {
    this.mIsTestMode = z;
    this.mDmaBufLeakDetector.setTestMode(z);
  }

  public void receiveDmabufLeakDetectorSource(String str) {
    DmaBufLeakDetector dmaBufLeakDetector = this.mDmaBufLeakDetector;
    if (dmaBufLeakDetector != null) {
      dmaBufLeakDetector.receiveSource(str);
    }
  }

  /* renamed from: com.android.server.am.pmm.PersonalizedMemoryManager$1 */
  public abstract /* synthetic */ class AbstractC06521 {

    /* renamed from: $SwitchMap$com$android$server$am$pmm$PersonalizedMemoryManager$MemoryEventType */
    public static final /* synthetic */ int[] f1635xd22b3b88;

    static {
      int[] iArr = new int[MemoryEventType.values().length];
      f1635xd22b3b88 = iArr;
      try {
        iArr[MemoryEventType.LMKD_KILL.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1635xd22b3b88[MemoryEventType.APP_LAUNCHED.ordinal()] = 2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        f1635xd22b3b88[MemoryEventType.PREV_PROC_DIED.ordinal()] = 3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        f1635xd22b3b88[MemoryEventType.DEVICE_IDLE.ordinal()] = 4;
      } catch (NoSuchFieldError unused4) {
      }
    }
  }

  public void onMemoryEvent(Context context, MemoryEventType memoryEventType) {
    int i = AbstractC06521.f1635xd22b3b88[memoryEventType.ordinal()];
    if (i == 1 || i == 2) {
      this.mDmaBufLeakDetector.onCheckMemoryLeak(context);
    }
  }

  public enum MemoryEventType {
    LMKD_KILL("LMKD_KILL"),
    PREV_PROC_DIED("PREV_PROC_DIED"),
    DEVICE_IDLE("DEVICE_IDLE"),
    APP_LAUNCHED("APP_LAUNCHED");

    private String mTypeName;

    MemoryEventType(String str) {
      this.mTypeName = str;
    }

    public String getTypeName() {
      return this.mTypeName;
    }

    @Override // java.lang.Enum
    public String toString() {
      return getTypeName();
    }
  }
}
