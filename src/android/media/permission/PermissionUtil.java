package android.media.permission;

import android.content.Context;
import android.content.PermissionChecker;
import android.os.Binder;
import java.util.Objects;

/* loaded from: classes3.dex */
public class PermissionUtil {
    public static SafeCloseable establishIdentityIndirect(Context context, String str, Identity identity, Identity identity2) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(str);
        Objects.requireNonNull(identity);
        Objects.requireNonNull(identity2);
        identity.pid = Binder.getCallingPid();
        identity.uid = Binder.getCallingUid();
        context.enforcePermission(str, identity.pid, identity.uid, String.format("Middleman must have the %s permision.", str));
        return new CompositeSafeCloseable(IdentityContext.create(identity2), ClearCallingIdentityContext.create());
    }

    public static SafeCloseable establishIdentityDirect(Identity identity) {
        Objects.requireNonNull(identity);
        identity.uid = Binder.getCallingUid();
        identity.pid = Binder.getCallingPid();
        return new CompositeSafeCloseable(IdentityContext.create(identity), ClearCallingIdentityContext.create());
    }

    public static int checkPermissionForDataDelivery(Context context, Identity identity, String str, String str2) {
        return PermissionChecker.checkPermissionForDataDelivery(context, str, identity.pid, identity.uid, identity.packageName, identity.attributionTag, str2);
    }

    public static int checkPermissionForPreflight(Context context, Identity identity, String str) {
        return PermissionChecker.checkPermissionForPreflight(context, str, identity.pid, identity.uid, identity.packageName);
    }
}
