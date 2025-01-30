package com.android.server.soundtrigger_middleware;

import android.content.Context;
import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.Properties;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.os.IBinder;
import android.os.SystemClock;
import android.p005os.BatteryStatsInternal;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.LatencyTracker;
import com.android.server.LocalServices;
import com.android.server.utils.EventLogger;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SoundTriggerMiddlewareLogging implements ISoundTriggerMiddlewareInternal, Dumpable {
  public final Supplier mBatteryStatsInternalSupplier;
  public final ISoundTriggerMiddlewareInternal mDelegate;
  public final Deque mDetachedSessionEventLoggers;
  public final LatencyTracker mLatencyTracker;
  public final EventLogger mServiceEventLogger;
  public final AtomicInteger mSessionCount;
  public final Set mSessionEventLoggers;

  public abstract class BatteryStatsHolder {
    public static final BatteryStatsInternal INSTANCE =
        (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);

    /* renamed from: -$$Nest$sfgetINSTANCE, reason: not valid java name */
    public static /* bridge */ /* synthetic */ BatteryStatsInternal m11546$$Nest$sfgetINSTANCE() {
      return INSTANCE;
    }
  }

  public SoundTriggerMiddlewareLogging(
      Context context, ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
    this(
        LatencyTracker.getInstance(context),
        new Supplier() { // from class:
          // com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda2
          @Override // java.util.function.Supplier
          public final Object get() {
            BatteryStatsInternal m11546$$Nest$sfgetINSTANCE;
            m11546$$Nest$sfgetINSTANCE =
                SoundTriggerMiddlewareLogging.BatteryStatsHolder.m11546$$Nest$sfgetINSTANCE();
            return m11546$$Nest$sfgetINSTANCE;
          }
        },
        iSoundTriggerMiddlewareInternal);
  }

  public SoundTriggerMiddlewareLogging(
      LatencyTracker latencyTracker,
      Supplier supplier,
      ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
    this.mServiceEventLogger = new EventLogger(256, "Service Events");
    this.mSessionEventLoggers = ConcurrentHashMap.newKeySet(4);
    this.mDetachedSessionEventLoggers = new LinkedBlockingDeque(4);
    this.mSessionCount = new AtomicInteger(0);
    this.mDelegate = iSoundTriggerMiddlewareInternal;
    this.mLatencyTracker = latencyTracker;
    this.mBatteryStatsInternalSupplier = supplier;
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
  public SoundTriggerModuleDescriptor[] listModules() {
    try {
      SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
      this.mServiceEventLogger.enqueue(
          ServiceEvent.createForReturn(
                  ServiceEvent.Type.LIST_MODULE,
                  IdentityContext.get().packageName,
                  (ModulePropertySummary[])
                      Arrays.stream(listModules)
                          .map(
                              new Function() { // from class:
                                // com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                  SoundTriggerMiddlewareLogging.ModulePropertySummary
                                      lambda$listModules$1;
                                  lambda$listModules$1 =
                                      SoundTriggerMiddlewareLogging.lambda$listModules$1(
                                          (SoundTriggerModuleDescriptor) obj);
                                  return lambda$listModules$1;
                                }
                              })
                          .toArray(
                              new IntFunction() { // from class:
                                // com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda1
                                @Override // java.util.function.IntFunction
                                public final Object apply(int i) {
                                  SoundTriggerMiddlewareLogging.ModulePropertySummary[]
                                      lambda$listModules$2;
                                  lambda$listModules$2 =
                                      SoundTriggerMiddlewareLogging.lambda$listModules$2(i);
                                  return lambda$listModules$2;
                                }
                              }),
                  new Object[0])
              .printLog(0, "SoundTriggerMiddlewareLogging"));
      return listModules;
    } catch (Exception e) {
      this.mServiceEventLogger.enqueue(
          ServiceEvent.createForException(
                  ServiceEvent.Type.LIST_MODULE,
                  IdentityContext.get().packageName,
                  e,
                  new Object[0])
              .printLog(2, "SoundTriggerMiddlewareLogging"));
      throw e;
    }
  }

  public static /* synthetic */ ModulePropertySummary lambda$listModules$1(
      SoundTriggerModuleDescriptor soundTriggerModuleDescriptor) {
    int i = soundTriggerModuleDescriptor.handle;
    Properties properties = soundTriggerModuleDescriptor.properties;
    return new ModulePropertySummary(i, properties.implementor, properties.version);
  }

  public static /* synthetic */ ModulePropertySummary[] lambda$listModules$2(int i) {
    return new ModulePropertySummary[i];
  }

  @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
  public ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
    try {
      Identity nonNull = IdentityContext.getNonNull();
      StringBuilder sb = new StringBuilder();
      sb.append(nonNull.packageName);
      sb.append(this.mSessionCount.getAndIncrement());
      sb.append(z ? "trusted" : "");
      String sb2 = sb.toString();
      ModuleLogging moduleLogging = new ModuleLogging();
      EventLogger eventLogger = new EventLogger(128, "Session logger for: " + sb2);
      moduleLogging.attach(
          this.mDelegate.attach(
              i, new CallbackLogging(iSoundTriggerCallback, eventLogger, nonNull), z),
          eventLogger);
      this.mServiceEventLogger.enqueue(
          ServiceEvent.createForReturn(
                  ServiceEvent.Type.ATTACH,
                  sb2,
                  moduleLogging,
                  Integer.valueOf(i),
                  iSoundTriggerCallback,
                  Boolean.valueOf(z))
              .printLog(0, "SoundTriggerMiddlewareLogging"));
      this.mSessionEventLoggers.add(eventLogger);
      return moduleLogging;
    } catch (Exception e) {
      this.mServiceEventLogger.enqueue(
          ServiceEvent.createForException(
                  ServiceEvent.Type.ATTACH,
                  IdentityContext.get().packageName,
                  e,
                  Integer.valueOf(i),
                  iSoundTriggerCallback)
              .printLog(2, "SoundTriggerMiddlewareLogging"));
      throw e;
    }
  }

  public String toString() {
    return this.mDelegate.toString();
  }

  public class ModuleLogging implements ISoundTriggerModule {
    public ISoundTriggerModule mDelegate;
    public EventLogger mEventLogger;

    public ModuleLogging() {}

    public void attach(ISoundTriggerModule iSoundTriggerModule, EventLogger eventLogger) {
      this.mDelegate = iSoundTriggerModule;
      this.mEventLogger = eventLogger;
    }

    public int loadModel(SoundModel soundModel) {
      try {
        int loadModel = this.mDelegate.loadModel(soundModel);
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(
                    SessionEvent.Type.LOAD_MODEL, Integer.valueOf(loadModel), soundModel.uuid)
                .printLog(0, "SoundTriggerMiddlewareLogging"));
        return loadModel;
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(SessionEvent.Type.LOAD_MODEL, e, soundModel.uuid)
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
      try {
        int loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(
                    SessionEvent.Type.LOAD_PHRASE_MODEL,
                    Integer.valueOf(loadPhraseModel),
                    phraseSoundModel.common.uuid)
                .printLog(0, "SoundTriggerMiddlewareLogging"));
        return loadPhraseModel;
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.LOAD_PHRASE_MODEL, e, phraseSoundModel.common.uuid)
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void unloadModel(int i) {
      try {
        this.mDelegate.unloadModel(i);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.UNLOAD_MODEL, Integer.valueOf(i))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(SessionEvent.Type.UNLOAD_MODEL, e, Integer.valueOf(i))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
      try {
        IBinder startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(
                    SessionEvent.Type.START_RECOGNITION,
                    startRecognition,
                    Integer.valueOf(i),
                    recognitionConfig)
                .printLog(0, "SoundTriggerMiddlewareLogging"));
        return startRecognition;
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.START_RECOGNITION, e, Integer.valueOf(i), recognitionConfig)
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void stopRecognition(int i) {
      try {
        this.mDelegate.stopRecognition(i);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.STOP_RECOGNITION, Integer.valueOf(i))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.STOP_RECOGNITION, e, Integer.valueOf(i))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void forceRecognitionEvent(int i) {
      try {
        this.mDelegate.forceRecognitionEvent(i);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.FORCE_RECOGNITION, Integer.valueOf(i))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.FORCE_RECOGNITION, e, Integer.valueOf(i))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void setModelParameter(int i, int i2, int i3) {
      try {
        this.mDelegate.setModelParameter(i, i2, i3);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(
                    SessionEvent.Type.SET_MODEL_PARAMETER,
                    Integer.valueOf(i),
                    Integer.valueOf(i2),
                    Integer.valueOf(i3))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.SET_MODEL_PARAMETER,
                    e,
                    Integer.valueOf(i),
                    Integer.valueOf(i2),
                    Integer.valueOf(i3))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public int getModelParameter(int i, int i2) {
      try {
        int modelParameter = this.mDelegate.getModelParameter(i, i2);
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(
                    SessionEvent.Type.GET_MODEL_PARAMETER,
                    Integer.valueOf(modelParameter),
                    Integer.valueOf(i),
                    Integer.valueOf(i2))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
        return modelParameter;
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.GET_MODEL_PARAMETER,
                    e,
                    Integer.valueOf(i),
                    Integer.valueOf(i2))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public ModelParameterRange queryModelParameterSupport(int i, int i2) {
      try {
        ModelParameterRange queryModelParameterSupport =
            this.mDelegate.queryModelParameterSupport(i, i2);
        this.mEventLogger.enqueue(
            SessionEvent.createForReturn(
                    SessionEvent.Type.QUERY_MODEL_PARAMETER,
                    queryModelParameterSupport,
                    Integer.valueOf(i),
                    Integer.valueOf(i2))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
        return queryModelParameterSupport;
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.QUERY_MODEL_PARAMETER,
                    e,
                    Integer.valueOf(i),
                    Integer.valueOf(i2))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void detach() {
      try {
        if (SoundTriggerMiddlewareLogging.this.mSessionEventLoggers.remove(this.mEventLogger)) {
          while (!SoundTriggerMiddlewareLogging.this.mDetachedSessionEventLoggers.offerFirst(
              this.mEventLogger)) {
            SoundTriggerMiddlewareLogging.this.mDetachedSessionEventLoggers.pollLast();
          }
        }
        this.mDelegate.detach();
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.DETACH, new Object[0])
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(SessionEvent.Type.DETACH, e, new Object[0])
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public IBinder asBinder() {
      return this.mDelegate.asBinder();
    }

    public String toString() {
      return Objects.toString(this.mDelegate);
    }
  }

  public class CallbackLogging implements ISoundTriggerCallback {
    public final ISoundTriggerCallback mCallbackDelegate;
    public final EventLogger mEventLogger;
    public final Identity mOriginatorIdentity;

    public CallbackLogging(
        ISoundTriggerCallback iSoundTriggerCallback, EventLogger eventLogger, Identity identity) {
      Objects.requireNonNull(iSoundTriggerCallback);
      this.mCallbackDelegate = iSoundTriggerCallback;
      Objects.requireNonNull(eventLogger);
      this.mEventLogger = eventLogger;
      this.mOriginatorIdentity = identity;
    }

    public void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
      try {
        ((BatteryStatsInternal)
                SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier.get())
            .noteWakingSoundTrigger(SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
        this.mCallbackDelegate.onRecognition(i, recognitionEventSys, i2);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(
                    SessionEvent.Type.RECOGNITION,
                    Integer.valueOf(i),
                    recognitionEventSys,
                    Integer.valueOf(i2))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.RECOGNITION,
                    e,
                    Integer.valueOf(i),
                    recognitionEventSys,
                    Integer.valueOf(i2))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void onPhraseRecognition(
        int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
      try {
        ((BatteryStatsInternal)
                SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier.get())
            .noteWakingSoundTrigger(SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
        SoundTriggerMiddlewareLogging.this.startKeyphraseEventLatencyTracking(
            phraseRecognitionEventSys.phraseRecognitionEvent);
        this.mCallbackDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(
                    SessionEvent.Type.RECOGNITION,
                    Integer.valueOf(i),
                    phraseRecognitionEventSys,
                    Integer.valueOf(i2))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(
                    SessionEvent.Type.RECOGNITION,
                    e,
                    Integer.valueOf(i),
                    phraseRecognitionEventSys,
                    Integer.valueOf(i2))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void onModelUnloaded(int i) {
      try {
        this.mCallbackDelegate.onModelUnloaded(i);
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.MODEL_UNLOADED, Integer.valueOf(i))
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(SessionEvent.Type.MODEL_UNLOADED, e, Integer.valueOf(i))
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void onResourcesAvailable() {
      try {
        this.mCallbackDelegate.onResourcesAvailable();
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.RESOURCES_AVAILABLE, new Object[0])
                .printLog(0, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(SessionEvent.Type.RESOURCES_AVAILABLE, e, new Object[0])
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public void onModuleDied() {
      try {
        this.mCallbackDelegate.onModuleDied();
        this.mEventLogger.enqueue(
            SessionEvent.createForVoid(SessionEvent.Type.MODULE_DIED, new Object[0])
                .printLog(2, "SoundTriggerMiddlewareLogging"));
      } catch (Exception e) {
        this.mEventLogger.enqueue(
            SessionEvent.createForException(SessionEvent.Type.MODULE_DIED, e, new Object[0])
                .printLog(2, "SoundTriggerMiddlewareLogging"));
        throw e;
      }
    }

    public IBinder asBinder() {
      return this.mCallbackDelegate.asBinder();
    }

    public String toString() {
      return Objects.toString(this.mCallbackDelegate);
    }
  }

  public final void startKeyphraseEventLatencyTracking(
      PhraseRecognitionEvent phraseRecognitionEvent) {
    if (phraseRecognitionEvent.common.status != 0
        || ArrayUtils.isEmpty(phraseRecognitionEvent.phraseExtras)) {
      return;
    }
    String str = "KeyphraseId=" + phraseRecognitionEvent.phraseExtras[0].id;
    this.mLatencyTracker.onActionCancel(19);
    this.mLatencyTracker.onActionStart(19, str);
  }

  public static StringBuilder printArgs(StringBuilder sb, Object[] objArr) {
    for (int i = 0; i < objArr.length; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      ObjectPrinter.print(sb, objArr[i]);
    }
    return sb;
  }

  @Override // com.android.server.soundtrigger_middleware.Dumpable
  public void dump(PrintWriter printWriter) {
    printWriter.println("##Service-Wide logs:");
    this.mServiceEventLogger.dump(printWriter, "  ");
    printWriter.println("\n##Active Session dumps:\n");
    Iterator it = this.mSessionEventLoggers.iterator();
    while (it.hasNext()) {
      ((EventLogger) it.next()).dump(printWriter, "  ");
      printWriter.println("");
    }
    printWriter.println("##Detached Session dumps:\n");
    Iterator it2 = this.mDetachedSessionEventLoggers.iterator();
    while (it2.hasNext()) {
      ((EventLogger) it2.next()).dump(printWriter, "  ");
      printWriter.println("");
    }
    ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
    if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
      ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
    }
  }

  public static void printSystemLog(int i, String str, String str2, Exception exc) {
    if (i == 0) {
      Slog.i(str, str2, exc);
      return;
    }
    if (i == 1) {
      Slog.e(str, str2, exc);
    } else if (i == 2) {
      Slog.w(str, str2, exc);
    } else {
      Slog.v(str, str2, exc);
    }
  }

  public class ServiceEvent extends EventLogger.Event {
    public final Exception mException;
    public final String mPackageName;
    public final Object[] mParams;
    public final Object mReturnValue;
    public final Type mType;

    public enum Type {
      ATTACH,
      LIST_MODULE
    }

    public static ServiceEvent createForException(
        Type type, String str, Exception exc, Object... objArr) {
      return new ServiceEvent(exc, type, str, null, objArr);
    }

    public static ServiceEvent createForReturn(
        Type type, String str, Object obj, Object... objArr) {
      return new ServiceEvent(null, type, str, obj, objArr);
    }

    public ServiceEvent(Exception exc, Type type, String str, Object obj, Object... objArr) {
      this.mException = exc;
      this.mType = type;
      this.mPackageName = str;
      this.mReturnValue = obj;
      this.mParams = objArr;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public EventLogger.Event printLog(int i, String str) {
      SoundTriggerMiddlewareLogging.printSystemLog(i, str, eventToString(), this.mException);
      return this;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public String eventToString() {
      StringBuilder sb = new StringBuilder(this.mType.name());
      sb.append(" [client= ");
      ObjectPrinter.print(sb, this.mPackageName);
      sb.append("] (");
      SoundTriggerMiddlewareLogging.printArgs(sb, this.mParams);
      sb.append(") -> ");
      if (this.mException != null) {
        sb.append("ERROR: ");
        ObjectPrinter.print(sb, this.mException);
      } else {
        ObjectPrinter.print(sb, this.mReturnValue);
      }
      return sb.toString();
    }
  }

  public class SessionEvent extends EventLogger.Event {
    public final Exception mException;
    public final Object[] mParams;
    public final Object mReturnValue;
    public final Type mType;

    public enum Type {
      LOAD_MODEL,
      LOAD_PHRASE_MODEL,
      START_RECOGNITION,
      STOP_RECOGNITION,
      FORCE_RECOGNITION,
      UNLOAD_MODEL,
      GET_MODEL_PARAMETER,
      SET_MODEL_PARAMETER,
      QUERY_MODEL_PARAMETER,
      DETACH,
      RECOGNITION,
      MODEL_UNLOADED,
      MODULE_DIED,
      RESOURCES_AVAILABLE
    }

    public static SessionEvent createForException(Type type, Exception exc, Object... objArr) {
      return new SessionEvent(exc, type, null, objArr);
    }

    public static SessionEvent createForReturn(Type type, Object obj, Object... objArr) {
      return new SessionEvent(null, type, obj, objArr);
    }

    public static SessionEvent createForVoid(Type type, Object... objArr) {
      return new SessionEvent(null, type, null, objArr);
    }

    public SessionEvent(Exception exc, Type type, Object obj, Object... objArr) {
      this.mException = exc;
      this.mType = type;
      this.mReturnValue = obj;
      this.mParams = objArr;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public EventLogger.Event printLog(int i, String str) {
      SoundTriggerMiddlewareLogging.printSystemLog(i, str, eventToString(), this.mException);
      return this;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public String eventToString() {
      StringBuilder sb = new StringBuilder(this.mType.name());
      sb.append(" (");
      SoundTriggerMiddlewareLogging.printArgs(sb, this.mParams);
      sb.append(")");
      if (this.mException != null) {
        sb.append(" -> ERROR: ");
        ObjectPrinter.print(sb, this.mException);
      } else if (this.mReturnValue != null) {
        sb.append(" -> ");
        ObjectPrinter.print(sb, this.mReturnValue);
      }
      return sb.toString();
    }
  }

  public final class ModulePropertySummary {
    public int mId;
    public String mImplementor;
    public int mVersion;

    public ModulePropertySummary(int i, String str, int i2) {
      this.mId = i;
      this.mImplementor = str;
      this.mVersion = i2;
    }

    public String toString() {
      return "{Id: "
          + this.mId
          + ", Implementor: "
          + this.mImplementor
          + ", Version: "
          + this.mVersion
          + "}";
    }
  }
}
