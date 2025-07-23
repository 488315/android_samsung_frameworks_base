package android.sec.enterprise.auditlog;

import java.util.Optional;

/* loaded from: classes3.dex */
public interface AuditEvent {
    String getComponent();

    int getGroup();

    String getMessage();

    boolean getOutcome();

    Optional<Integer> getPrivacy();

    String getRedactedMessage();

    int getSeverity();

    int getUserId();

    boolean isPrivileged();
}
