package com.android.systemui.qs.shared;

import android.content.Context;
import com.android.systemui.user.data.repository.UserRepository;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSSettingsPackageRepository {
    public final CoroutineScope backgroundScope;
    public final Context context;
    public String settingsPackageName;
    public final UserRepository userRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public QSSettingsPackageRepository(Context context, CoroutineScope coroutineScope, UserRepository userRepository) {
        this.context = context;
        this.backgroundScope = coroutineScope;
        this.userRepository = userRepository;
    }

    public final String getSettingsPackageName() {
        String str = this.settingsPackageName;
        return str == null ? KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG : str;
    }

    public final void init() {
        BuildersKt.launch$default(this.backgroundScope, null, null, new QSSettingsPackageRepository$init$1(this, null), 3);
    }
}
