package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IInterface;
import android.os.SystemClock;
import android.service.assist.classification.FieldClassificationResponse;
import android.service.assist.classification.IFieldClassificationService;
import android.util.Slog;
import com.android.internal.infra.ServiceConnector;
import com.android.server.autofill.FieldClassificationEventLogger;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
final class RemoteFieldClassificationService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final ComponentName mComponentName;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface FieldClassificationServiceCallbacks {
    }

    /* renamed from: -$$Nest$mlogFieldClassificationEvent, reason: not valid java name */
    public static void m297$$Nest$mlogFieldClassificationEvent(RemoteFieldClassificationService remoteFieldClassificationService, long j, FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks, final int i, FieldClassificationResponse fieldClassificationResponse) {
        if (fieldClassificationServiceCallbacks == null) {
            FieldClassificationEventLogger fieldClassificationEventLogger = new FieldClassificationEventLogger();
            fieldClassificationEventLogger.mEventInternal = Optional.empty();
            fieldClassificationEventLogger.startNewLogForRequest();
            final long elapsedRealtime = SystemClock.elapsedRealtime() - j;
            fieldClassificationEventLogger.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mLatencyClassificationRequestMillis = elapsedRealtime;
                }
            });
            fieldClassificationEventLogger.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda7
                public final /* synthetic */ boolean f$0 = true;

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mIsSessionGc = this.f$0;
                }
            });
            final int i2 = 0;
            fieldClassificationEventLogger.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i3 = i2;
                    int i4 = i;
                    FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                    switch (i3) {
                        case 0:
                            fieldClassificationEventInternal.mStatus = i4;
                            break;
                        case 1:
                            fieldClassificationEventInternal.mNextFillRequestId = i4;
                            break;
                        case 2:
                            fieldClassificationEventInternal.mCountClassifications = i4;
                            break;
                        case 3:
                            fieldClassificationEventInternal.mRequestId = i4;
                            break;
                        case 4:
                            fieldClassificationEventInternal.mAppPackageUid = i4;
                            break;
                        default:
                            fieldClassificationEventInternal.mSessionId = i4;
                            break;
                    }
                }
            });
            fieldClassificationEventLogger.logAndEndEvent();
            return;
        }
        Session session = (Session) fieldClassificationServiceCallbacks;
        FieldClassificationEventLogger fieldClassificationEventLogger2 = new FieldClassificationEventLogger();
        fieldClassificationEventLogger2.mEventInternal = Optional.empty();
        fieldClassificationEventLogger2.startNewLogForRequest();
        final long elapsedRealtime2 = SystemClock.elapsedRealtime() - j;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FieldClassificationEventLogger.FieldClassificationEventInternal) obj).mLatencyClassificationRequestMillis = elapsedRealtime2;
            }
        });
        final int i3 = session.uid;
        final int i4 = 4;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i4;
                int i42 = i3;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        final int i5 = session.mFillRequestIdSnapshot + 1;
        final int i6 = 1;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i6;
                int i42 = i5;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        final int i7 = Session.sIdCounterForPcc.get();
        final int i8 = 3;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i8;
                int i42 = i7;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        final int i9 = session.id;
        final int i10 = 5;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i10;
                int i42 = i9;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        final int size = fieldClassificationResponse != null ? fieldClassificationResponse.getClassifications().size() : -1;
        final int i11 = 0;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i11;
                int i42 = i;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        final int i12 = 2;
        fieldClassificationEventLogger2.mEventInternal.ifPresent(new Consumer() { // from class: com.android.server.autofill.FieldClassificationEventLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i12;
                int i42 = size;
                FieldClassificationEventLogger.FieldClassificationEventInternal fieldClassificationEventInternal = (FieldClassificationEventLogger.FieldClassificationEventInternal) obj;
                switch (i32) {
                    case 0:
                        fieldClassificationEventInternal.mStatus = i42;
                        break;
                    case 1:
                        fieldClassificationEventInternal.mNextFillRequestId = i42;
                        break;
                    case 2:
                        fieldClassificationEventInternal.mCountClassifications = i42;
                        break;
                    case 3:
                        fieldClassificationEventInternal.mRequestId = i42;
                        break;
                    case 4:
                        fieldClassificationEventInternal.mAppPackageUid = i42;
                        break;
                    default:
                        fieldClassificationEventInternal.mSessionId = i42;
                        break;
                }
            }
        });
        fieldClassificationEventLogger2.logAndEndEvent();
        session.mFillRequestIdSnapshot = -2;
    }

    public RemoteFieldClassificationService(Context context, ComponentName componentName, int i) {
        super(context, new Intent("android.service.assist.classification.FieldClassificationService").setComponent(componentName), 0, i, new RemoteFieldClassificationService$$ExternalSyntheticLambda0());
        this.mComponentName = componentName;
        if (Helper.sDebug) {
            Slog.d("AutofillRemoteFieldClassificationService", "About to connect to serviceName: " + componentName);
        }
        connect();
    }

    public final long getAutoDisconnectTimeoutMs() {
        return 0L;
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        IFieldClassificationService iFieldClassificationService = (IFieldClassificationService) iInterface;
        try {
            if (z) {
                iFieldClassificationService.onConnected(false, false);
            } else {
                iFieldClassificationService.onDisconnected();
            }
        } catch (Exception e) {
            Slog.w("AutofillRemoteFieldClassificationService", "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }
}
