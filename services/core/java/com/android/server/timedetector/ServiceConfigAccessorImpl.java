package com.android.server.timedetector;

import android.R;
import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.app.timedetector.TimeDetectorHelper;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.IUserRestrictionsListener;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;

import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.timezonedetector.StateChangeListener;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ServiceConfigAccessorImpl implements ServiceConfigAccessor {
    public static final int[] DEFAULT_AUTOMATIC_TIME_ORIGIN_PRIORITIES = {1, 3};
    public static final Set SERVER_FLAGS_KEYS_TO_WATCH =
            Set.of(
                    "time_detector_lower_bound_millis_override",
                    "time_detector_origin_priorities_override");
    public static final Object SLOCK = new Object();
    public static ServiceConfigAccessorImpl sInstance;
    public final ConfigOriginPrioritiesSupplier mConfigOriginPrioritiesSupplier;
    public final List mConfigurationInternalListeners = new ArrayList();
    public final Context mContext;
    public final ContentResolver mCr;
    public final ServerFlags mServerFlags;
    public final ConfigOriginPrioritiesSupplier mServerFlagsOriginPrioritiesSupplier;
    public final int mSystemClockUpdateThresholdMillis;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.timedetector.ServiceConfigAccessorImpl$3, reason: invalid class name */
    public final class AnonymousClass3 extends IUserRestrictionsListener.Stub {
        public final /* synthetic */ Handler val$mainThreadHandler;

        public AnonymousClass3(Handler handler) {
            this.val$mainThreadHandler = handler;
        }

        public final void onUserRestrictionsChanged(
                final int i, final Bundle bundle, final Bundle bundle2) {
            this.val$mainThreadHandler.post(
                    new Runnable(
                            i, bundle,
                            bundle2) { // from class:
                                       // com.android.server.timedetector.ServiceConfigAccessorImpl$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ServiceConfigAccessorImpl.this
                                    .handleConfigurationInternalChangeOnMainThread();
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConfigOriginPrioritiesSupplier implements Supplier {
        public final /* synthetic */ int $r8$classId = 0;
        public final Object mContext;
        public int[] mLastPriorityInts;
        public String[] mLastPriorityStrings;

        public ConfigOriginPrioritiesSupplier(Context context) {
            Objects.requireNonNull(context);
            this.mContext = context;
        }

        public ConfigOriginPrioritiesSupplier(ServerFlags serverFlags) {
            Objects.requireNonNull(serverFlags);
            this.mContext = serverFlags;
        }

        @Override // java.util.function.Supplier
        public final int[] get() {
            String[] stringArray;
            String trim;
            int i;
            Optional of;
            int[] iArr = null;
            switch (this.$r8$classId) {
                case 0:
                    stringArray =
                            ((Context) this.mContext)
                                    .getResources()
                                    .getStringArray(
                                            R.array.config_notificationMsgPkgsAllowedAsConvos);
                    break;
                default:
                    ((ServerFlags) this.mContext).getClass();
                    Optional ofNullable =
                            Optional.ofNullable(
                                    DeviceConfig.getProperty(
                                            "system_time",
                                            "time_detector_origin_priorities_override"));
                    if (ofNullable.isPresent()) {
                        String str = (String) ofNullable.get();
                        of =
                                "_[]_".equals(str)
                                        ? Optional.of(new String[0])
                                        : Optional.of(str.split(","));
                    } else {
                        of = Optional.empty();
                    }
                    stringArray = (String[]) of.orElse(null);
                    break;
            }
            synchronized (this) {
                try {
                    if (Arrays.equals(this.mLastPriorityStrings, stringArray)) {
                        return this.mLastPriorityInts;
                    }
                    if (stringArray != null) {
                        int length = stringArray.length;
                        int[] iArr2 = new int[length];
                        for (int i2 = 0; i2 < length; i2++) {
                            try {
                                String str2 = stringArray[i2];
                                Preconditions.checkArgument(str2 != null);
                                trim = str2.trim();
                                Preconditions.checkArgument(trim != null);
                                trim.getClass();
                                switch (trim) {
                                    case "external":
                                        i = 5;
                                        iArr2[i2] = i;
                                    case "manual":
                                        i = 2;
                                        iArr2[i2] = i;
                                    case "gnss":
                                        i = 4;
                                        iArr2[i2] = i;
                                    case "telephony":
                                        i = 1;
                                        iArr2[i2] = i;
                                    case "network":
                                        i = 3;
                                        iArr2[i2] = i;
                                    default:
                                        throw new IllegalArgumentException(
                                                "originString=".concat(trim));
                                }
                            } catch (IllegalArgumentException unused) {
                            }
                        }
                        iArr = iArr2;
                    }
                    this.mLastPriorityStrings = stringArray;
                    this.mLastPriorityInts = iArr;
                    return iArr;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public ServiceConfigAccessorImpl(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mCr = context.getContentResolver();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        ServerFlags serverFlags = ServerFlags.getInstance(context);
        this.mServerFlags = serverFlags;
        this.mConfigOriginPrioritiesSupplier = new ConfigOriginPrioritiesSupplier(context);
        this.mServerFlagsOriginPrioritiesSupplier = new ConfigOriginPrioritiesSupplier(serverFlags);
        this.mSystemClockUpdateThresholdMillis =
                context.getResources().getInteger(R.integer.device_idle_max_idle_pending_to_ms);
        context.registerReceiverForAllUsers(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.timedetector.ServiceConfigAccessorImpl.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ServiceConfigAccessorImpl.this
                                .handleConfigurationInternalChangeOnMainThread();
                    }
                },
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_SWITCHED"),
                null,
                null);
        Handler mainThreadHandler = context.getMainThreadHandler();
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("auto_time"),
                        true,
                        new ContentObserver(
                                mainThreadHandler) { // from class:
                                                     // com.android.server.timedetector.ServiceConfigAccessorImpl.2
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                ServiceConfigAccessorImpl.this
                                        .handleConfigurationInternalChangeOnMainThread();
                            }
                        });
        serverFlags.addListener(
                new StateChangeListener() { // from class:
                                            // com.android.server.timedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda0
                    @Override // com.android.server.timezonedetector.StateChangeListener
                    public final void onChange() {
                        ServiceConfigAccessorImpl.this
                                .handleConfigurationInternalChangeOnMainThread();
                    }
                },
                SERVER_FLAGS_KEYS_TO_WATCH);
        userManager.addUserRestrictionsListener(new AnonymousClass3(mainThreadHandler));
    }

    public final synchronized ConfigurationInternal getConfigurationInternal(int i) {
        ConfigurationInternal.Builder builder;
        Optional empty;
        TimeDetectorHelper timeDetectorHelper = TimeDetectorHelper.INSTANCE;
        builder = new ConfigurationInternal.Builder(i);
        boolean z = true;
        builder.mUserConfigAllowed =
                !this.mUserManager.hasUserRestriction("no_config_date_time", UserHandle.of(i));
        builder.mAutoDetectionSupported = isAutoDetectionSupported();
        if (Settings.Global.getInt(this.mCr, "auto_time", 1) <= 0) {
            z = false;
        }
        builder.mAutoDetectionEnabledSetting = z;
        builder.mSystemClockUpdateThresholdMillis = this.mSystemClockUpdateThresholdMillis;
        builder.mSystemClockConfidenceThresholdMillis = 1000;
        this.mServerFlags.getClass();
        String property =
                DeviceConfig.getProperty(
                        "system_time", "time_detector_lower_bound_millis_override");
        if (property == null) {
            empty = Optional.empty();
        } else {
            try {
                empty = Optional.of(Instant.ofEpochMilli(Long.parseLong(property)));
            } catch (NumberFormatException | DateTimeException unused) {
                empty = Optional.empty();
            }
        }
        Instant instant =
                (Instant)
                        empty.orElse(
                                TimeDetectorHelper.INSTANCE.getAutoSuggestionLowerBoundDefault());
        Objects.requireNonNull(instant);
        builder.mAutoSuggestionLowerBound = instant;
        Instant manualSuggestionLowerBound = timeDetectorHelper.getManualSuggestionLowerBound();
        Objects.requireNonNull(manualSuggestionLowerBound);
        builder.mManualSuggestionLowerBound = manualSuggestionLowerBound;
        Instant suggestionUpperBound = timeDetectorHelper.getSuggestionUpperBound();
        Objects.requireNonNull(suggestionUpperBound);
        builder.mSuggestionUpperBound = suggestionUpperBound;
        int[] iArr = this.mServerFlagsOriginPrioritiesSupplier.get();
        if (iArr == null && (iArr = this.mConfigOriginPrioritiesSupplier.get()) == null) {
            iArr = DEFAULT_AUTOMATIC_TIME_ORIGIN_PRIORITIES;
        }
        builder.mOriginPriorities = iArr;
        return new ConfigurationInternal(builder);
    }

    public final void handleConfigurationInternalChangeOnMainThread() {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.mConfigurationInternalListeners);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((StateChangeListener) it.next()).onChange();
        }
    }

    public final boolean isAutoDetectionSupported() {
        int[] iArr = this.mServerFlagsOriginPrioritiesSupplier.get();
        if (iArr == null && (iArr = this.mConfigOriginPrioritiesSupplier.get()) == null) {
            iArr = DEFAULT_AUTOMATIC_TIME_ORIGIN_PRIORITIES;
        }
        for (int i : iArr) {
            if (i == 3 || i == 5 || i == 4) {
                return true;
            }
            if (i == 1
                    && this.mContext
                            .getPackageManager()
                            .hasSystemFeature("android.hardware.telephony")) {
                return true;
            }
        }
        return false;
    }

    public final synchronized boolean updateConfiguration(
            int i, TimeConfiguration timeConfiguration) {
        Objects.requireNonNull(timeConfiguration);
        TimeCapabilitiesAndConfig createCapabilitiesAndConfig =
                getConfigurationInternal(i).createCapabilitiesAndConfig();
        TimeConfiguration tryApplyConfigChanges =
                createCapabilitiesAndConfig
                        .getCapabilities()
                        .tryApplyConfigChanges(
                                createCapabilitiesAndConfig.getConfiguration(), timeConfiguration);
        if (tryApplyConfigChanges == null) {
            return false;
        }
        if (isAutoDetectionSupported()) {
            boolean isAutoDetectionEnabled = tryApplyConfigChanges.isAutoDetectionEnabled();
            if ((Settings.Global.getInt(this.mCr, "auto_time", 1) > 0) != isAutoDetectionEnabled) {
                Settings.Global.putInt(this.mCr, "auto_time", isAutoDetectionEnabled ? 1 : 0);
            }
        }
        return true;
    }
}
