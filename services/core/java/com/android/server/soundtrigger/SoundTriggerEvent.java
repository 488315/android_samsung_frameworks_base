package com.android.server.soundtrigger;

import android.util.Slog;
import com.android.server.utils.EventLogger;
import java.util.UUID;

/* loaded from: classes3.dex */
public abstract class SoundTriggerEvent extends EventLogger.Event {
  @Override // com.android.server.utils.EventLogger.Event
  public EventLogger.Event printLog(int i, String str) {
    if (i == 0) {
      Slog.i(str, eventToString());
    } else if (i == 1) {
      Slog.e(str, eventToString());
    } else if (i == 2) {
      Slog.w(str, eventToString());
    } else {
      Slog.v(str, eventToString());
    }
    return this;
  }

  public class ServiceEvent extends SoundTriggerEvent {
    public final String mErrorString;
    public final String mPackageName;
    public final Type mType;

    public enum Type {
      ATTACH,
      LIST_MODULE,
      DETACH
    }

    public ServiceEvent(Type type, String str) {
      this(type, str, null);
    }

    public ServiceEvent(Type type, String str, String str2) {
      this.mType = type;
      this.mPackageName = str;
      this.mErrorString = str2;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public String eventToString() {
      StringBuilder sb = new StringBuilder(String.format("%-12s", this.mType.name()));
      if (this.mErrorString != null) {
        sb.append(" ERROR: ");
        sb.append(this.mErrorString);
      }
      if (this.mPackageName != null) {
        sb.append(" for: ");
        sb.append(this.mPackageName);
      }
      return sb.toString();
    }
  }

  public class SessionEvent extends SoundTriggerEvent {
    public final String mErrorString;
    public final UUID mModelUuid;
    public final Type mType;

    public enum Type {
      START_RECOGNITION,
      STOP_RECOGNITION,
      LOAD_MODEL,
      UNLOAD_MODEL,
      UPDATE_MODEL,
      DELETE_MODEL,
      START_RECOGNITION_SERVICE,
      STOP_RECOGNITION_SERVICE,
      GET_MODEL_STATE,
      SET_PARAMETER,
      GET_MODULE_PROPERTIES,
      DETACH,
      RECOGNITION,
      RESUME,
      RESUME_FAILED,
      PAUSE,
      PAUSE_FAILED,
      RESOURCES_AVAILABLE,
      MODULE_DIED
    }

    public SessionEvent(Type type, UUID uuid, String str) {
      this.mType = type;
      this.mModelUuid = uuid;
      this.mErrorString = str;
    }

    public SessionEvent(Type type, UUID uuid) {
      this(type, uuid, null);
    }

    @Override // com.android.server.utils.EventLogger.Event
    public String eventToString() {
      StringBuilder sb = new StringBuilder(String.format("%-25s", this.mType.name()));
      if (this.mErrorString != null) {
        sb.append(" ERROR: ");
        sb.append(this.mErrorString);
      }
      if (this.mModelUuid != null) {
        sb.append(" for: ");
        sb.append(this.mModelUuid);
      }
      return sb.toString();
    }
  }
}
