package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IIncidentCompanion;
import android.os.IIncidentDumpCallback;
import android.os.IIncidentManager;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Slog;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes3.dex */
public class IncidentManager {
    public static final int FLAG_ALLOW_CONSENTLESS_BUGREPORT = 2;
    public static final int FLAG_CONFIRMATION_DIALOG = 1;
    public static final int PRIVACY_POLICY_AUTO = 200;
    public static final int PRIVACY_POLICY_EXPLICIT = 100;
    public static final int PRIVACY_POLICY_LOCAL = 0;
    private static final String TAG = "IncidentManager";
    public static final String URI_AUTHORITY = "android.os.IncidentManager";
    public static final String URI_PARAM_CALLING_PACKAGE = "pkg";
    public static final String URI_PARAM_FLAGS = "flags";
    public static final String URI_PARAM_ID = "id";
    public static final String URI_PARAM_RECEIVER_CLASS = "receiver";
    public static final String URI_PARAM_REPORT_ID = "r";
    public static final String URI_PARAM_TIMESTAMP = "t";
    public static final String URI_PATH = "/pending";
    public static final String URI_SCHEME = "content";
    private IIncidentCompanion mCompanionService;
    private final Context mContext;
    private IIncidentManager mIncidentService;
    private Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PendingReportFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivacyPolicy {
    }

    @SystemApi
    public static class PendingReport {
        private final int mFlags;
        private final String mRequestingPackage;
        private final long mTimestamp;
        private final Uri mUri;

        public PendingReport(Uri uri) throws UnsupportedEncodingException {
            try {
                this.mFlags = Integer.parseInt(uri.getQueryParameter("flags"));
                String queryParameter = uri.getQueryParameter("pkg");
                if (queryParameter == null) {
                    throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
                }
                this.mRequestingPackage = queryParameter;
                try {
                    this.mTimestamp = Long.parseLong(uri.getQueryParameter("t"));
                    this.mUri = uri;
                } catch (NumberFormatException unused) {
                    throw new RuntimeException("Invalid URI: No t parameter. " + uri);
                }
            } catch (NumberFormatException unused2) {
                throw new RuntimeException("Invalid URI: No flags parameter. " + uri);
            }
        }

        public String getRequestingPackage() {
            return this.mRequestingPackage;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public String toString() {
            return "PendingReport(" + getUri().toString() + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingReport)) {
                return false;
            }
            PendingReport pendingReport = (PendingReport) obj;
            return this.mUri.equals(pendingReport.mUri) && this.mFlags == pendingReport.mFlags && this.mRequestingPackage.equals(pendingReport.mRequestingPackage) && this.mTimestamp == pendingReport.mTimestamp;
        }
    }

    @SystemApi
    public static class IncidentReport implements Parcelable, Closeable {
        public static final Parcelable.Creator<IncidentReport> CREATOR = new Parcelable.Creator() { // from class: android.os.IncidentManager.IncidentReport.1
            @Override // android.os.Parcelable.Creator
            public IncidentReport[] newArray(int i) {
                return new IncidentReport[i];
            }

            @Override // android.os.Parcelable.Creator
            public IncidentReport createFromParcel(Parcel parcel) {
                return new IncidentReport(parcel);
            }
        };
        private ParcelFileDescriptor mFileDescriptor;
        private final int mPrivacyPolicy;
        private final long mTimestampNs;

        public IncidentReport(Parcel parcel) {
            this.mTimestampNs = parcel.readLong();
            this.mPrivacyPolicy = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
            } else {
                this.mFileDescriptor = null;
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                    this.mFileDescriptor = null;
                }
            } catch (IOException unused) {
            }
        }

        public long getTimestamp() {
            return this.mTimestampNs / 1000000;
        }

        public long getPrivacyPolicy() {
            return this.mPrivacyPolicy;
        }

        public InputStream getInputStream() throws IOException {
            if (this.mFileDescriptor == null) {
                return null;
            }
            return new ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mTimestampNs);
            parcel.writeInt(this.mPrivacyPolicy);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(1);
                this.mFileDescriptor.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public static class AuthListener {
        IIncidentAuthListener.Stub mBinder = new AnonymousClass1();
        Executor mExecutor;

        public void onReportApproved() {
        }

        public void onReportDenied() {
        }

        /* renamed from: android.os.IncidentManager$AuthListener$1, reason: invalid class name */
        class AnonymousClass1 extends IIncidentAuthListener.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportApproved() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReportApproved$0();
                        }
                    });
                } else {
                    AuthListener.this.onReportApproved();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportApproved$0() {
                AuthListener.this.onReportApproved();
            }

            @Override // android.os.IIncidentAuthListener
            public void onReportDenied() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$AuthListener$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onReportDenied$1();
                        }
                    });
                } else {
                    AuthListener.this.onReportDenied();
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReportDenied$1() {
                AuthListener.this.onReportDenied();
            }
        }
    }

    public static class DumpCallback {
        IIncidentDumpCallback.Stub mBinder = new AnonymousClass1();
        private Executor mExecutor;
        private int mId;

        public void onDumpSection(int i, OutputStream outputStream) {
        }

        /* renamed from: android.os.IncidentManager$DumpCallback$1, reason: invalid class name */
        class AnonymousClass1 extends IIncidentDumpCallback.Stub {
            AnonymousClass1() {
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(final ParcelFileDescriptor parcelFileDescriptor) {
                if (DumpCallback.this.mExecutor != null) {
                    DumpCallback.this.mExecutor.execute(new Runnable() { // from class: android.os.IncidentManager$DumpCallback$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onDumpSection$0(parcelFileDescriptor);
                        }
                    });
                } else {
                    DumpCallback dumpCallback = DumpCallback.this;
                    dumpCallback.onDumpSection(dumpCallback.mId, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDumpSection$0(ParcelFileDescriptor parcelFileDescriptor) {
                DumpCallback dumpCallback = DumpCallback.this;
                dumpCallback.onDumpSection(dumpCallback.mId, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
            }
        }
    }

    public IncidentManager(Context context) {
        this.mContext = context;
    }

    public void reportIncident(IncidentReportArgs incidentReportArgs) {
        reportIncidentInternal(incidentReportArgs);
    }

    public void requestAuthorization(int i, String str, int i2, AuthListener authListener) {
        requestAuthorization(i, str, i2, this.mContext.getMainExecutor(), authListener);
    }

    public void requestAuthorization(int i, String str, int i2, Executor executor, AuthListener authListener) {
        try {
            if (authListener.mExecutor != null) {
                throw new RuntimeException("Do not reuse AuthListener objects when calling requestAuthorization");
            }
            authListener.mExecutor = executor;
            getCompanionServiceLocked().authorizeReport(i, str, null, null, i2, authListener.mBinder);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelAuthorization(AuthListener authListener) {
        try {
            getCompanionServiceLocked().cancelAuthorization(authListener.mBinder);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PendingReport> getPendingReports() {
        try {
            List<String> pendingReports = getCompanionServiceLocked().getPendingReports();
            int size = pendingReports.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(new PendingReport(Uri.parse(pendingReports.get(i))));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void approveReport(Uri uri) {
        try {
            getCompanionServiceLocked().approveReport(uri.toString());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void denyReport(Uri uri) {
        try {
            getCompanionServiceLocked().denyReport(uri.toString());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerSection(int i, String str, Executor executor, DumpCallback dumpCallback) {
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(dumpCallback, "callback cannot be null");
        try {
            if (dumpCallback.mExecutor != null) {
                throw new RuntimeException("Do not reuse DumpCallback objects when calling registerSection");
            }
            dumpCallback.mExecutor = executor;
            dumpCallback.mId = i;
            IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                Slog.e(TAG, "registerSection can't find incident binder service");
            } else {
                iIncidentManagerLocked.registerSection(i, str, dumpCallback.mBinder);
            }
        } catch (RemoteException e) {
            Slog.e(TAG, "registerSection failed", e);
        }
    }

    public void unregisterSection(int i) {
        try {
            IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                Slog.e(TAG, "unregisterSection can't find incident binder service");
            } else {
                iIncidentManagerLocked.unregisterSection(i);
            }
        } catch (RemoteException e) {
            Slog.e(TAG, "unregisterSection failed", e);
        }
    }

    public List<Uri> getIncidentReportList(String str) {
        try {
            List<String> incidentReportList = getCompanionServiceLocked().getIncidentReportList(this.mContext.getPackageName(), str);
            int size = incidentReportList.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(Uri.parse(incidentReportList.get(i)));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw new RuntimeException("System server or incidentd going down", e);
        }
    }

    public IncidentReport getIncidentReport(Uri uri) throws UnsupportedEncodingException {
        String queryParameter = uri.getQueryParameter("r");
        if (queryParameter == null) {
            return null;
        }
        String queryParameter2 = uri.getQueryParameter("pkg");
        if (queryParameter2 == null) {
            throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        String queryParameter3 = uri.getQueryParameter("receiver");
        if (queryParameter3 == null) {
            throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        try {
            return getCompanionServiceLocked().getIncidentReport(queryParameter2, queryParameter3, queryParameter);
        } catch (RemoteException e) {
            throw new RuntimeException("System server or incidentd going down", e);
        }
    }

    public void deleteIncidentReports(Uri uri) throws UnsupportedEncodingException {
        if (uri == null) {
            try {
                getCompanionServiceLocked().deleteAllIncidentReports(this.mContext.getPackageName());
                return;
            } catch (RemoteException e) {
                throw new RuntimeException("System server or incidentd going down", e);
            }
        }
        String queryParameter = uri.getQueryParameter("pkg");
        if (queryParameter == null) {
            throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
        }
        String queryParameter2 = uri.getQueryParameter("receiver");
        if (queryParameter2 == null) {
            throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
        }
        String queryParameter3 = uri.getQueryParameter("r");
        if (queryParameter3 == null) {
            throw new RuntimeException("Invalid URI: No r parameter. " + uri);
        }
        try {
            getCompanionServiceLocked().deleteIncidentReports(queryParameter, queryParameter2, queryParameter3);
        } catch (RemoteException e2) {
            throw new RuntimeException("System server or incidentd going down", e2);
        }
    }

    private void reportIncidentInternal(IncidentReportArgs incidentReportArgs) {
        try {
            IIncidentManager iIncidentManagerLocked = getIIncidentManagerLocked();
            if (iIncidentManagerLocked == null) {
                Slog.e(TAG, "reportIncident can't find incident binder service");
            } else {
                iIncidentManagerLocked.reportIncident(incidentReportArgs);
            }
        } catch (RemoteException e) {
            Slog.e(TAG, "reportIncident failed", e);
        }
    }

    private IIncidentManager getIIncidentManagerLocked() throws RemoteException {
        IIncidentManager iIncidentManager = this.mIncidentService;
        if (iIncidentManager != null) {
            return iIncidentManager;
        }
        synchronized (this.mLock) {
            IIncidentManager iIncidentManager2 = this.mIncidentService;
            if (iIncidentManager2 != null) {
                return iIncidentManager2;
            }
            IIncidentManager iIncidentManagerAsInterface = IIncidentManager.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_SERVICE));
            this.mIncidentService = iIncidentManagerAsInterface;
            if (iIncidentManagerAsInterface != null) {
                iIncidentManagerAsInterface.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        this.f$0.lambda$getIIncidentManagerLocked$0();
                    }
                }, 0);
            }
            return this.mIncidentService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIIncidentManagerLocked$0() {
        synchronized (this.mLock) {
            this.mIncidentService = null;
        }
    }

    private IIncidentCompanion getCompanionServiceLocked() throws RemoteException {
        IIncidentCompanion iIncidentCompanion = this.mCompanionService;
        if (iIncidentCompanion != null) {
            return iIncidentCompanion;
        }
        synchronized (this) {
            IIncidentCompanion iIncidentCompanion2 = this.mCompanionService;
            if (iIncidentCompanion2 != null) {
                return iIncidentCompanion2;
            }
            IIncidentCompanion iIncidentCompanionAsInterface = IIncidentCompanion.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_COMPANION_SERVICE));
            this.mCompanionService = iIncidentCompanionAsInterface;
            if (iIncidentCompanionAsInterface != null) {
                iIncidentCompanionAsInterface.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.os.IncidentManager$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        this.f$0.lambda$getCompanionServiceLocked$1();
                    }
                }, 0);
            }
            return this.mCompanionService;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCompanionServiceLocked$1() {
        synchronized (this.mLock) {
            this.mCompanionService = null;
        }
    }
}
