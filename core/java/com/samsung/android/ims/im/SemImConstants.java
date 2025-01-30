package com.samsung.android.ims.im;

/* loaded from: classes5.dex */
public interface SemImConstants {

  public interface ChatDirection {
    public static final int INCOMING = 0;
    public static final int IRRELEVANT = 2;
    public static final int OUTGOING = 1;
  }

  public interface ChatState {
    public static final int ACTIVE = 1;
    public static final int CLOSED_BY_USER = 2;
    public static final int CLOSED_INVOLUNTARILY = 3;
    public static final int CLOSED_IN_UNAVAILABLE = 5;
    public static final int CLOSED_VOLUNTARILY = 4;
    public static final int INACTIVE = 0;
    public static final int NONE = -1;
  }

  public interface ConfigRcs {
    public static final String ATT_PHASE2 = "RCS_ATT_PHASE2";
    public static final String TMB_PHASE2 = "RCS_TMB_PHASE2";
  }

  public interface ErrorReason {
    public static final int NO_SESSION = 4;
  }

  public interface MessageNotificationStatus {
    public static final int DELIVERED = 1;
    public static final int DISPLAYED = 2;
    public static final int NONE = 0;
  }

  public interface MessageStatus {
    public static final int BLOCKED = 6;
    public static final int FAILED = 4;
    public static final int IRRELEVANT = 8;
    public static final int QUEUED = 7;
    public static final int READ = 1;
    public static final int SENDING = 2;
    public static final int SENT = 3;
    public static final int TO_SEND = 5;
    public static final int UNREAD = 0;
  }

  public interface MessageType {
    public static final int LOCATION = 2;
    public static final int MULTIMEDIA = 0;

    @Deprecated public static final int MULTIMEDIA_BURN = 9;
    public static final int MULTIMEDIA_PUBLICACCOUNT = 11;
    public static final int SYSTEM = 3;
    public static final int SYSTEM_CONTINUE_ON_ANOTHER_DEVICE = 7;
    public static final int SYSTEM_LEADER_CHANGED = 8;
    public static final int SYSTEM_LEADER_INFORMED = 13;
    public static final int SYSTEM_USER_INVITED = 5;
    public static final int SYSTEM_USER_JOINED = 6;
    public static final int SYSTEM_USER_LEFT = 4;
    public static final int TEXT = 1;

    @Deprecated public static final int TEXT_BURN = 10;
    public static final int TEXT_PUBLICACCOUNT = 12;
  }

  public interface ParticipantStatus {
    public static final int ACCEPTED = 2;
    public static final int DECLINED = 3;
    public static final int FAILED = 7;
    public static final int GONE = 5;
    public static final int INITIAL = 0;
    public static final int INVITED = 1;
    public static final int PENDING = 8;
    public static final int TIMEOUT = 4;
    public static final int TO_INVITE = 6;
  }

  public interface TransferState {
    public static final int ATTACHED = 6;
    public static final int BLOCKED = 8;
    public static final int CANCELED = 4;
    public static final int CANCELED_NEED_TO_NOTIFY = 10;
    public static final int CANCELLING = 7;
    public static final int COMPLETED = 3;
    public static final int CREATED = 0;
    public static final int IN_PROGRESS = 2;
    public static final int PENDING = 1;
    public static final int QUEUED = 5;
    public static final int SENDING = 9;
  }
}
