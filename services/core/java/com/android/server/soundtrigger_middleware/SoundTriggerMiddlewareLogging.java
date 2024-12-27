package com.android.server.soundtrigger_middleware;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.soundtrigger.ModelParameterRange;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import android.os.BatteryStatsInternal;
import android.os.IBinder;
import android.os.SystemClock;
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
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SoundTriggerMiddlewareLogging
        implements ISoundTriggerMiddlewareInternal, Dumpable {
    public final Supplier mBatteryStatsInternalSupplier;
    public final ISoundTriggerMiddlewareInternal mDelegate;
    public final LatencyTracker mLatencyTracker;
    public final EventLogger mServiceEventLogger = new EventLogger(256, "Service Events");
    public final Set mSessionEventLoggers = ConcurrentHashMap.newKeySet(4);
    public final Deque mDetachedSessionEventLoggers = new LinkedBlockingDeque(4);
    public final AtomicInteger mSessionCount = new AtomicInteger(0);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class BatteryStatsHolder {
        public static final BatteryStatsInternal INSTANCE =
                (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CallbackLogging implements ISoundTriggerCallback {
        public final ISoundTriggerCallback mCallbackDelegate;
        public final EventLogger mEventLogger;
        public final Identity mOriginatorIdentity;

        public CallbackLogging(
                ISoundTriggerCallback iSoundTriggerCallback,
                EventLogger eventLogger,
                Identity identity) {
            Objects.requireNonNull(iSoundTriggerCallback);
            this.mCallbackDelegate = iSoundTriggerCallback;
            this.mEventLogger = eventLogger;
            this.mOriginatorIdentity = identity;
        }

        public final IBinder asBinder() {
            return this.mCallbackDelegate.asBinder();
        }

        public final void onModelUnloaded(int i) {
            SessionEvent.Type type = SessionEvent.Type.MODEL_UNLOADED;
            try {
                this.mCallbackDelegate.onModelUnloaded(i);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, Integer.valueOf(i));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, Integer.valueOf(i));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void onModuleDied() {
            SessionEvent.Type type = SessionEvent.Type.MODULE_DIED;
            try {
                this.mCallbackDelegate.onModuleDied();
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, new Object[0]);
                createForVoid.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, new Object[0]);
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void onPhraseRecognition(
                int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) {
            SessionEvent.Type type = SessionEvent.Type.RECOGNITION;
            try {
                ((BatteryStatsInternal)
                                SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier
                                        .get())
                        .noteWakingSoundTrigger(
                                SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
                SoundTriggerMiddlewareLogging.m899$$Nest$mstartKeyphraseEventLatencyTracking(
                        SoundTriggerMiddlewareLogging.this,
                        phraseRecognitionEventSys.phraseRecognitionEvent);
                this.mCallbackDelegate.onPhraseRecognition(i, phraseRecognitionEventSys, i2);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid =
                        SessionEvent.createForVoid(
                                type,
                                Integer.valueOf(i),
                                phraseRecognitionEventSys,
                                Integer.valueOf(i2));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type,
                                e,
                                Integer.valueOf(i),
                                phraseRecognitionEventSys,
                                Integer.valueOf(i2));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) {
            SessionEvent.Type type = SessionEvent.Type.RECOGNITION;
            try {
                ((BatteryStatsInternal)
                                SoundTriggerMiddlewareLogging.this.mBatteryStatsInternalSupplier
                                        .get())
                        .noteWakingSoundTrigger(
                                SystemClock.elapsedRealtime(), this.mOriginatorIdentity.uid);
                this.mCallbackDelegate.onRecognition(i, recognitionEventSys, i2);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid =
                        SessionEvent.createForVoid(
                                type, Integer.valueOf(i), recognitionEventSys, Integer.valueOf(i2));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type,
                                e,
                                Integer.valueOf(i),
                                recognitionEventSys,
                                Integer.valueOf(i2));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void onResourcesAvailable() {
            SessionEvent.Type type = SessionEvent.Type.RESOURCES_AVAILABLE;
            try {
                this.mCallbackDelegate.onResourcesAvailable();
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, new Object[0]);
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, new Object[0]);
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final String toString() {
            return Objects.toString(this.mCallbackDelegate);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModuleLogging implements ISoundTriggerModule {
        public ISoundTriggerModule mDelegate;
        public EventLogger mEventLogger;

        public ModuleLogging() {}

        public final IBinder asBinder() {
            return this.mDelegate.asBinder();
        }

        public final void detach() {
            SessionEvent.Type type = SessionEvent.Type.DETACH;
            try {
                if (SoundTriggerMiddlewareLogging.this.mSessionEventLoggers.remove(
                        this.mEventLogger)) {
                    while (true) {
                        if (((LinkedBlockingDeque)
                                        SoundTriggerMiddlewareLogging.this
                                                .mDetachedSessionEventLoggers)
                                .offerFirst(this.mEventLogger)) {
                            break;
                        } else {
                            ((LinkedBlockingDeque)
                                            SoundTriggerMiddlewareLogging.this
                                                    .mDetachedSessionEventLoggers)
                                    .pollLast();
                        }
                    }
                }
                this.mDelegate.detach();
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, new Object[0]);
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, new Object[0]);
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void forceRecognitionEvent(int i) {
            SessionEvent.Type type = SessionEvent.Type.FORCE_RECOGNITION;
            try {
                this.mDelegate.forceRecognitionEvent(i);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, Integer.valueOf(i));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, Integer.valueOf(i));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final int getModelParameter(int i, int i2) {
            SessionEvent.Type type = SessionEvent.Type.GET_MODEL_PARAMETER;
            try {
                int modelParameter = this.mDelegate.getModelParameter(i, i2);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForReturn =
                        SessionEvent.createForReturn(
                                type,
                                Integer.valueOf(modelParameter),
                                Integer.valueOf(i),
                                Integer.valueOf(i2));
                createForReturn.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForReturn);
                return modelParameter;
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type, e, Integer.valueOf(i), Integer.valueOf(i2));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final int loadModel(SoundModel soundModel) {
            SessionEvent.Type type = SessionEvent.Type.LOAD_MODEL;
            try {
                int loadModel = this.mDelegate.loadModel(soundModel);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForReturn =
                        SessionEvent.createForReturn(
                                type, Integer.valueOf(loadModel), soundModel.uuid);
                createForReturn.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForReturn);
                return loadModel;
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForReturn2 =
                        SessionEvent.createForReturn(type, e, soundModel.uuid);
                createForReturn2.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForReturn2);
                throw e;
            }
        }

        public final int loadPhraseModel(PhraseSoundModel phraseSoundModel) {
            SessionEvent.Type type = SessionEvent.Type.LOAD_PHRASE_MODEL;
            try {
                int loadPhraseModel = this.mDelegate.loadPhraseModel(phraseSoundModel);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForReturn =
                        SessionEvent.createForReturn(
                                type,
                                Integer.valueOf(loadPhraseModel),
                                phraseSoundModel.common.uuid);
                createForReturn.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForReturn);
                return loadPhraseModel;
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, phraseSoundModel.common.uuid);
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final ModelParameterRange queryModelParameterSupport(int i, int i2) {
            SessionEvent.Type type = SessionEvent.Type.QUERY_MODEL_PARAMETER;
            try {
                ModelParameterRange queryModelParameterSupport =
                        this.mDelegate.queryModelParameterSupport(i, i2);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForReturn =
                        SessionEvent.createForReturn(
                                type,
                                queryModelParameterSupport,
                                Integer.valueOf(i),
                                Integer.valueOf(i2));
                createForReturn.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForReturn);
                return queryModelParameterSupport;
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type, e, Integer.valueOf(i), Integer.valueOf(i2));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void setModelParameter(int i, int i2, int i3) {
            SessionEvent.Type type = SessionEvent.Type.SET_MODEL_PARAMETER;
            try {
                this.mDelegate.setModelParameter(i, i2, i3);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid =
                        SessionEvent.createForVoid(
                                type, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type,
                                e,
                                Integer.valueOf(i),
                                Integer.valueOf(i2),
                                Integer.valueOf(i3));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final IBinder startRecognition(int i, RecognitionConfig recognitionConfig) {
            SessionEvent.Type type = SessionEvent.Type.START_RECOGNITION;
            try {
                IBinder startRecognition = this.mDelegate.startRecognition(i, recognitionConfig);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForReturn =
                        SessionEvent.createForReturn(
                                type, startRecognition, Integer.valueOf(i), recognitionConfig);
                createForReturn.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForReturn);
                return startRecognition;
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(
                                type, e, Integer.valueOf(i), recognitionConfig);
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final void stopRecognition(int i) {
            SessionEvent.Type type = SessionEvent.Type.STOP_RECOGNITION;
            try {
                this.mDelegate.stopRecognition(i);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, Integer.valueOf(i));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, Integer.valueOf(i));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }

        public final String toString() {
            return Objects.toString(this.mDelegate);
        }

        public final void unloadModel(int i) {
            SessionEvent.Type type = SessionEvent.Type.UNLOAD_MODEL;
            try {
                this.mDelegate.unloadModel(i);
                EventLogger eventLogger = this.mEventLogger;
                SessionEvent createForVoid = SessionEvent.createForVoid(type, Integer.valueOf(i));
                createForVoid.printLog(0, "SoundTriggerMiddlewareLogging");
                eventLogger.enqueue(createForVoid);
            } catch (Exception e) {
                EventLogger eventLogger2 = this.mEventLogger;
                SessionEvent createForException =
                        SessionEvent.createForException(type, e, Integer.valueOf(i));
                createForException.printLog(2, "SoundTriggerMiddlewareLogging");
                eventLogger2.enqueue(createForException);
                throw e;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModulePropertySummary {
        public int mId;
        public String mImplementor;
        public int mVersion;

        public final String toString() {
            StringBuilder sb = new StringBuilder("{Id: ");
            sb.append(this.mId);
            sb.append(", Implementor: ");
            sb.append(this.mImplementor);
            sb.append(", Version: ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mVersion, sb, "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceEvent extends EventLogger.Event {
        public final Exception mException;
        public final String mPackageName;
        public final Object[] mParams;
        public final Object mReturnValue;
        public final Type mType;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Type {
            public static final /* synthetic */ Type[] $VALUES;
            public static final Type ATTACH;
            public static final Type LIST_MODULE;

            static {
                Type type = new Type("ATTACH", 0);
                ATTACH = type;
                Type type2 = new Type("LIST_MODULE", 1);
                LIST_MODULE = type2;
                $VALUES = new Type[] {type, type2};
            }

            public static Type valueOf(String str) {
                return (Type) Enum.valueOf(Type.class, str);
            }

            public static Type[] values() {
                return (Type[]) $VALUES.clone();
            }
        }

        public ServiceEvent(Exception exc, Type type, String str, Object obj, Object... objArr) {
            this.mException = exc;
            this.mType = type;
            this.mPackageName = str;
            this.mReturnValue = obj;
            this.mParams = objArr;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            StringBuilder sb = new StringBuilder(this.mType.name());
            sb.append(" [client= ");
            ObjectPrinter.print$1(sb, this.mPackageName);
            sb.append("] (");
            int i = 0;
            while (true) {
                Object[] objArr = this.mParams;
                if (i >= objArr.length) {
                    break;
                }
                if (i > 0) {
                    sb.append(", ");
                }
                ObjectPrinter.print$1(sb, objArr[i]);
                i++;
            }
            sb.append(") -> ");
            if (this.mException != null) {
                sb.append("ERROR: ");
                ObjectPrinter.print$1(sb, this.mException);
            } else {
                ObjectPrinter.print$1(sb, this.mReturnValue);
            }
            return sb.toString();
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final EventLogger.Event printLog(int i, String str) {
            String eventToString = eventToString();
            Exception exc = this.mException;
            if (i == 0) {
                Slog.i(str, eventToString, exc);
            } else if (i == 1) {
                Slog.e(str, eventToString, exc);
            } else if (i != 2) {
                Slog.v(str, eventToString, exc);
            } else {
                Slog.w(str, eventToString, exc);
            }
            return this;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionEvent extends EventLogger.Event {
        public final Exception mException;
        public final Object[] mParams;
        public final Object mReturnValue;
        public final Type mType;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Type {
            public static final /* synthetic */ Type[] $VALUES;
            public static final Type DETACH;
            public static final Type FORCE_RECOGNITION;
            public static final Type GET_MODEL_PARAMETER;
            public static final Type LOAD_MODEL;
            public static final Type LOAD_PHRASE_MODEL;
            public static final Type MODEL_UNLOADED;
            public static final Type MODULE_DIED;
            public static final Type QUERY_MODEL_PARAMETER;
            public static final Type RECOGNITION;
            public static final Type RESOURCES_AVAILABLE;
            public static final Type SET_MODEL_PARAMETER;
            public static final Type START_RECOGNITION;
            public static final Type STOP_RECOGNITION;
            public static final Type UNLOAD_MODEL;

            static {
                Type type = new Type("LOAD_MODEL", 0);
                LOAD_MODEL = type;
                Type type2 = new Type("LOAD_PHRASE_MODEL", 1);
                LOAD_PHRASE_MODEL = type2;
                Type type3 = new Type("START_RECOGNITION", 2);
                START_RECOGNITION = type3;
                Type type4 = new Type("STOP_RECOGNITION", 3);
                STOP_RECOGNITION = type4;
                Type type5 = new Type("FORCE_RECOGNITION", 4);
                FORCE_RECOGNITION = type5;
                Type type6 = new Type("UNLOAD_MODEL", 5);
                UNLOAD_MODEL = type6;
                Type type7 = new Type("GET_MODEL_PARAMETER", 6);
                GET_MODEL_PARAMETER = type7;
                Type type8 = new Type("SET_MODEL_PARAMETER", 7);
                SET_MODEL_PARAMETER = type8;
                Type type9 = new Type("QUERY_MODEL_PARAMETER", 8);
                QUERY_MODEL_PARAMETER = type9;
                Type type10 = new Type("DETACH", 9);
                DETACH = type10;
                Type type11 = new Type("RECOGNITION", 10);
                RECOGNITION = type11;
                Type type12 = new Type("MODEL_UNLOADED", 11);
                MODEL_UNLOADED = type12;
                Type type13 = new Type("MODULE_DIED", 12);
                MODULE_DIED = type13;
                Type type14 = new Type("RESOURCES_AVAILABLE", 13);
                RESOURCES_AVAILABLE = type14;
                $VALUES =
                        new Type[] {
                            type, type2, type3, type4, type5, type6, type7, type8, type9, type10,
                            type11, type12, type13, type14
                        };
            }

            public static Type valueOf(String str) {
                return (Type) Enum.valueOf(Type.class, str);
            }

            public static Type[] values() {
                return (Type[]) $VALUES.clone();
            }
        }

        public SessionEvent(Exception exc, Type type, Object obj, Object... objArr) {
            this.mException = exc;
            this.mType = type;
            this.mReturnValue = obj;
            this.mParams = objArr;
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

        @Override // com.android.server.utils.EventLogger.Event
        public final String eventToString() {
            StringBuilder sb = new StringBuilder(this.mType.name());
            sb.append(" (");
            int i = 0;
            while (true) {
                Object[] objArr = this.mParams;
                if (i >= objArr.length) {
                    break;
                }
                if (i > 0) {
                    sb.append(", ");
                }
                ObjectPrinter.print$1(sb, objArr[i]);
                i++;
            }
            sb.append(")");
            if (this.mException != null) {
                sb.append(" -> ERROR: ");
                ObjectPrinter.print$1(sb, this.mException);
            } else {
                Object obj = this.mReturnValue;
                if (obj != null) {
                    sb.append(" -> ");
                    ObjectPrinter.print$1(sb, obj);
                }
            }
            return sb.toString();
        }

        @Override // com.android.server.utils.EventLogger.Event
        public final EventLogger.Event printLog(int i, String str) {
            String eventToString = eventToString();
            Exception exc = this.mException;
            if (i == 0) {
                Slog.i(str, eventToString, exc);
            } else if (i == 1) {
                Slog.e(str, eventToString, exc);
            } else if (i != 2) {
                Slog.v(str, eventToString, exc);
            } else {
                Slog.w(str, eventToString, exc);
            }
            return this;
        }
    }

    /* renamed from: -$$Nest$mstartKeyphraseEventLatencyTracking, reason: not valid java name */
    public static void m899$$Nest$mstartKeyphraseEventLatencyTracking(
            SoundTriggerMiddlewareLogging soundTriggerMiddlewareLogging,
            PhraseRecognitionEvent phraseRecognitionEvent) {
        soundTriggerMiddlewareLogging.getClass();
        if (phraseRecognitionEvent.common.status != 0
                || ArrayUtils.isEmpty(phraseRecognitionEvent.phraseExtras)) {
            return;
        }
        String str = "KeyphraseId=" + phraseRecognitionEvent.phraseExtras[0].id;
        soundTriggerMiddlewareLogging.mLatencyTracker.onActionCancel(19);
        soundTriggerMiddlewareLogging.mLatencyTracker.onActionStart(19, str);
    }

    public SoundTriggerMiddlewareLogging(
            LatencyTracker latencyTracker,
            Supplier supplier,
            ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal) {
        this.mDelegate = iSoundTriggerMiddlewareInternal;
        this.mLatencyTracker = latencyTracker;
        this.mBatteryStatsInternalSupplier = supplier;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final ISoundTriggerModule attach(
            int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z) {
        ServiceEvent.Type type = ServiceEvent.Type.ATTACH;
        EventLogger eventLogger = this.mServiceEventLogger;
        try {
            Identity nonNull = IdentityContext.getNonNull();
            StringBuilder sb = new StringBuilder();
            sb.append(nonNull.packageName);
            sb.append(this.mSessionCount.getAndIncrement());
            sb.append(z ? "trusted" : "");
            String sb2 = sb.toString();
            ModuleLogging moduleLogging = new ModuleLogging();
            EventLogger eventLogger2 = new EventLogger(128, "Session logger for: " + sb2);
            moduleLogging.mDelegate =
                    this.mDelegate.attach(
                            i,
                            new CallbackLogging(iSoundTriggerCallback, eventLogger2, nonNull),
                            z);
            moduleLogging.mEventLogger = eventLogger2;
            ServiceEvent serviceEvent =
                    new ServiceEvent(
                            null,
                            type,
                            sb2,
                            moduleLogging,
                            Integer.valueOf(i),
                            iSoundTriggerCallback,
                            Boolean.valueOf(z));
            serviceEvent.printLog(0, "SoundTriggerMiddlewareLogging");
            eventLogger.enqueue(serviceEvent);
            this.mSessionEventLoggers.add(eventLogger2);
            return moduleLogging;
        } catch (Exception e) {
            ServiceEvent serviceEvent2 =
                    new ServiceEvent(
                            e,
                            type,
                            IdentityContext.get().packageName,
                            null,
                            Integer.valueOf(i),
                            iSoundTriggerCallback);
            serviceEvent2.printLog(2, "SoundTriggerMiddlewareLogging");
            eventLogger.enqueue(serviceEvent2);
            throw e;
        }
    }

    @Override // com.android.server.soundtrigger_middleware.Dumpable
    public final void dump(PrintWriter printWriter) {
        printWriter.println("##Service-Wide logs:");
        this.mServiceEventLogger.dump(printWriter, "  ");
        printWriter.println("\n##Active Session dumps:\n");
        Iterator it = this.mSessionEventLoggers.iterator();
        while (it.hasNext()) {
            ((EventLogger) it.next()).dump(printWriter, "  ");
            printWriter.println("");
        }
        printWriter.println("##Detached Session dumps:\n");
        Iterator it2 = ((LinkedBlockingDeque) this.mDetachedSessionEventLoggers).iterator();
        while (it2.hasNext()) {
            ((EventLogger) it2.next()).dump(printWriter, "  ");
            printWriter.println("");
        }
        ISoundTriggerMiddlewareInternal iSoundTriggerMiddlewareInternal = this.mDelegate;
        if (iSoundTriggerMiddlewareInternal instanceof Dumpable) {
            ((Dumpable) iSoundTriggerMiddlewareInternal).dump(printWriter);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerMiddlewareInternal
    public final SoundTriggerModuleDescriptor[] listModules() {
        ServiceEvent.Type type = ServiceEvent.Type.LIST_MODULE;
        EventLogger eventLogger = this.mServiceEventLogger;
        try {
            SoundTriggerModuleDescriptor[] listModules = this.mDelegate.listModules();
            ServiceEvent serviceEvent =
                    new ServiceEvent(
                            null,
                            type,
                            IdentityContext.get().packageName,
                            (ModulePropertySummary[])
                                    Arrays.stream(listModules)
                                            .map(
                                                    new SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda1())
                                            .toArray(
                                                    new SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda2()),
                            new Object[0]);
            serviceEvent.printLog(0, "SoundTriggerMiddlewareLogging");
            eventLogger.enqueue(serviceEvent);
            return listModules;
        } catch (Exception e) {
            ServiceEvent serviceEvent2 =
                    new ServiceEvent(
                            e, type, IdentityContext.get().packageName, null, new Object[0]);
            serviceEvent2.printLog(2, "SoundTriggerMiddlewareLogging");
            eventLogger.enqueue(serviceEvent2);
            throw e;
        }
    }

    public final String toString() {
        return this.mDelegate.toString();
    }
}
