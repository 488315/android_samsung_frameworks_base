package android.content;

import android.app.ActivityThread;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public final class ContentCaptureOptions implements Parcelable {
    public final ContentProtectionOptions contentProtectionOptions;
    public final boolean disableFlushForViewTreeAppearing;
    public final boolean enableReceiver;
    public final int idleFlushingFrequencyMs;
    public final boolean lite;
    public final int logHistorySize;
    public final int loggingLevel;
    public final int maxBufferSize;
    public final int textChangeFlushingFrequencyMs;
    public final ArraySet<ComponentName> whitelistedComponents;
    private static final String TAG = ContentCaptureOptions.class.getSimpleName();
    public static final Parcelable.Creator<ContentCaptureOptions> CREATOR =
            new Parcelable.Creator<ContentCaptureOptions>() { // from class:
                // android.content.ContentCaptureOptions.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ContentCaptureOptions createFromParcel(Parcel parcel) {
                    boolean lite = parcel.readBoolean();
                    int loggingLevel = parcel.readInt();
                    if (lite) {
                        return new ContentCaptureOptions(loggingLevel);
                    }
                    int maxBufferSize = parcel.readInt();
                    int idleFlushingFrequencyMs = parcel.readInt();
                    int textChangeFlushingFrequencyMs = parcel.readInt();
                    int logHistorySize = parcel.readInt();
                    boolean disableFlushForViewTreeAppearing = parcel.readBoolean();
                    boolean enableReceiver = parcel.readBoolean();
                    ContentProtectionOptions contentProtectionOptions =
                            ContentProtectionOptions.createFromParcel(parcel);
                    return new ContentCaptureOptions(
                            loggingLevel,
                            maxBufferSize,
                            idleFlushingFrequencyMs,
                            textChangeFlushingFrequencyMs,
                            logHistorySize,
                            disableFlushForViewTreeAppearing,
                            enableReceiver,
                            contentProtectionOptions,
                            parcel.readArraySet(null));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ContentCaptureOptions[] newArray(int size) {
                    return new ContentCaptureOptions[size];
                }
            };

    public ContentCaptureOptions(int loggingLevel) {
        this(
                true,
                loggingLevel,
                0,
                0,
                0,
                0,
                false,
                false,
                new ContentProtectionOptions(
                        false, 0, Collections.emptyList(), Collections.emptyList(), 0),
                null);
    }

    public ContentCaptureOptions(
            int loggingLevel,
            int maxBufferSize,
            int idleFlushingFrequencyMs,
            int textChangeFlushingFrequencyMs,
            int logHistorySize,
            ArraySet<ComponentName> whitelistedComponents) {
        this(
                false,
                loggingLevel,
                maxBufferSize,
                idleFlushingFrequencyMs,
                textChangeFlushingFrequencyMs,
                logHistorySize,
                false,
                true,
                new ContentProtectionOptions(),
                whitelistedComponents);
    }

    public ContentCaptureOptions(
            int loggingLevel,
            int maxBufferSize,
            int idleFlushingFrequencyMs,
            int textChangeFlushingFrequencyMs,
            int logHistorySize,
            boolean disableFlushForViewTreeAppearing,
            boolean enableReceiver,
            ContentProtectionOptions contentProtectionOptions,
            ArraySet<ComponentName> whitelistedComponents) {
        this(
                false,
                loggingLevel,
                maxBufferSize,
                idleFlushingFrequencyMs,
                textChangeFlushingFrequencyMs,
                logHistorySize,
                disableFlushForViewTreeAppearing,
                enableReceiver,
                contentProtectionOptions,
                whitelistedComponents);
    }

    public ContentCaptureOptions(ArraySet<ComponentName> whitelistedComponents) {
        this(
                2,
                500,
                5000,
                1000,
                10,
                false,
                true,
                new ContentProtectionOptions(),
                whitelistedComponents);
    }

    private ContentCaptureOptions(
            boolean lite,
            int loggingLevel,
            int maxBufferSize,
            int idleFlushingFrequencyMs,
            int textChangeFlushingFrequencyMs,
            int logHistorySize,
            boolean disableFlushForViewTreeAppearing,
            boolean enableReceiver,
            ContentProtectionOptions contentProtectionOptions,
            ArraySet<ComponentName> whitelistedComponents) {
        this.lite = lite;
        this.loggingLevel = loggingLevel;
        this.maxBufferSize = maxBufferSize;
        this.idleFlushingFrequencyMs = idleFlushingFrequencyMs;
        this.textChangeFlushingFrequencyMs = textChangeFlushingFrequencyMs;
        this.logHistorySize = logHistorySize;
        this.disableFlushForViewTreeAppearing = disableFlushForViewTreeAppearing;
        this.enableReceiver = enableReceiver;
        this.contentProtectionOptions = contentProtectionOptions;
        this.whitelistedComponents = whitelistedComponents;
    }

    public static ContentCaptureOptions forWhitelistingItself() {
        ActivityThread at = ActivityThread.currentActivityThread();
        if (at == null) {
            throw new IllegalStateException("No ActivityThread");
        }
        String packageName = at.getApplication().getPackageName();
        if (!"android.contentcaptureservice.cts".equals(packageName)
                && !"android.translation.cts".equals(packageName)) {
            Log.e(TAG, "forWhitelistingItself(): called by " + packageName);
            throw new SecurityException("Thou shall not pass!");
        }
        ContentCaptureOptions options = new ContentCaptureOptions((ArraySet<ComponentName>) null);
        Log.i(TAG, "forWhitelistingItself(" + packageName + "): " + options);
        return options;
    }

    public boolean isWhitelisted(Context context) {
        if (this.whitelistedComponents == null) {
            return true;
        }
        ContentCaptureManager.ContentCaptureClient client = context.getContentCaptureClient();
        if (client == null) {
            Log.w(TAG, "isWhitelisted(): no ContentCaptureClient on " + context);
            return false;
        }
        return this.whitelistedComponents.contains(client.contentCaptureClientGetComponentName());
    }

    public String toString() {
        if (this.lite) {
            return "ContentCaptureOptions [loggingLevel=" + this.loggingLevel + " (lite)]";
        }
        StringBuilder string = new StringBuilder("ContentCaptureOptions [");
        string.append("loggingLevel=")
                .append(this.loggingLevel)
                .append(", maxBufferSize=")
                .append(this.maxBufferSize)
                .append(", idleFlushingFrequencyMs=")
                .append(this.idleFlushingFrequencyMs)
                .append(", textChangeFlushingFrequencyMs=")
                .append(this.textChangeFlushingFrequencyMs)
                .append(", logHistorySize=")
                .append(this.logHistorySize)
                .append(", disableFlushForViewTreeAppearing=")
                .append(this.disableFlushForViewTreeAppearing)
                .append(", enableReceiver=")
                .append(this.enableReceiver)
                .append(", contentProtectionOptions=")
                .append(this.contentProtectionOptions);
        if (this.whitelistedComponents != null) {
            string.append(", whitelisted=").append(this.whitelistedComponents);
        }
        return string.append(']').toString();
    }

    public void dumpShort(PrintWriter pw) {
        pw.print("logLvl=");
        pw.print(this.loggingLevel);
        if (this.lite) {
            pw.print(", lite");
            return;
        }
        pw.print(", bufferSize=");
        pw.print(this.maxBufferSize);
        pw.print(", idle=");
        pw.print(this.idleFlushingFrequencyMs);
        pw.print(", textIdle=");
        pw.print(this.textChangeFlushingFrequencyMs);
        pw.print(", logSize=");
        pw.print(this.logHistorySize);
        pw.print(", disableFlushForViewTreeAppearing=");
        pw.print(this.disableFlushForViewTreeAppearing);
        pw.print(", enableReceiver=");
        pw.print(this.enableReceiver);
        pw.print(", contentProtectionOptions=[");
        this.contentProtectionOptions.dumpShort(pw);
        pw.print(NavigationBarInflaterView.SIZE_MOD_END);
        if (this.whitelistedComponents != null) {
            pw.print(", whitelisted=");
            pw.print(this.whitelistedComponents);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBoolean(this.lite);
        parcel.writeInt(this.loggingLevel);
        if (this.lite) {
            return;
        }
        parcel.writeInt(this.maxBufferSize);
        parcel.writeInt(this.idleFlushingFrequencyMs);
        parcel.writeInt(this.textChangeFlushingFrequencyMs);
        parcel.writeInt(this.logHistorySize);
        parcel.writeBoolean(this.disableFlushForViewTreeAppearing);
        parcel.writeBoolean(this.enableReceiver);
        this.contentProtectionOptions.writeToParcel(parcel);
        parcel.writeArraySet(this.whitelistedComponents);
    }

    public static class ContentProtectionOptions {
        public final int bufferSize;
        public final boolean enableReceiver;
        public final List<List<String>> optionalGroups;
        public final int optionalGroupsThreshold;
        public final List<List<String>> requiredGroups;

        public ContentProtectionOptions() {
            this(
                    false,
                    150,
                    ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS,
                    ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS,
                    0);
        }

        public ContentProtectionOptions(
                boolean enableReceiver,
                int bufferSize,
                List<List<String>> requiredGroups,
                List<List<String>> optionalGroups,
                int optionalGroupsThreshold) {
            this.enableReceiver = enableReceiver;
            this.bufferSize = bufferSize;
            this.requiredGroups = requiredGroups;
            this.optionalGroups = optionalGroups;
            this.optionalGroupsThreshold = optionalGroupsThreshold;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ContentProtectionOptions [");
            stringBuilder
                    .append("enableReceiver=")
                    .append(this.enableReceiver)
                    .append(", bufferSize=")
                    .append(this.bufferSize)
                    .append(", requiredGroupsSize=")
                    .append(this.requiredGroups.size())
                    .append(", optionalGroupsSize=")
                    .append(this.optionalGroups.size())
                    .append(", optionalGroupsThreshold=")
                    .append(this.optionalGroupsThreshold);
            return stringBuilder.append(']').toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpShort(PrintWriter pw) {
            pw.print("enableReceiver=");
            pw.print(this.enableReceiver);
            pw.print(", bufferSize=");
            pw.print(this.bufferSize);
            pw.print(", requiredGroupsSize=");
            pw.print(this.requiredGroups.size());
            pw.print(", optionalGroupsSize=");
            pw.print(this.optionalGroups.size());
            pw.print(", optionalGroupsThreshold=");
            pw.print(this.optionalGroupsThreshold);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeBoolean(this.enableReceiver);
            parcel.writeInt(this.bufferSize);
            writeGroupsToParcel(this.requiredGroups, parcel);
            writeGroupsToParcel(this.optionalGroups, parcel);
            parcel.writeInt(this.optionalGroupsThreshold);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ContentProtectionOptions createFromParcel(Parcel parcel) {
            boolean enableReceiver = parcel.readBoolean();
            int bufferSize = parcel.readInt();
            List<List<String>> requiredGroups = createGroupsFromParcel(parcel);
            List<List<String>> optionalGroups = createGroupsFromParcel(parcel);
            int optionalGroupsThreshold = parcel.readInt();
            return new ContentProtectionOptions(
                    enableReceiver,
                    bufferSize,
                    requiredGroups,
                    optionalGroups,
                    optionalGroupsThreshold);
        }

        private static void writeGroupsToParcel(List<List<String>> groups, final Parcel parcel) {
            parcel.writeInt(groups.size());
            Objects.requireNonNull(parcel);
            groups.forEach(
                    new Consumer() { // from class:
                        // android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Parcel.this.writeStringList((List) obj);
                        }
                    });
        }

        private static List<List<String>> createGroupsFromParcel(final Parcel parcel) {
            int size = parcel.readInt();
            Stream mapToObj =
                    IntStream.range(0, size)
                            .mapToObj(
                                    new IntFunction() { // from class:
                                        // android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda1
                                        @Override // java.util.function.IntFunction
                                        public final Object apply(int i) {
                                            return ContentCaptureOptions.ContentProtectionOptions
                                                    .lambda$createGroupsFromParcel$0(i);
                                        }
                                    });
            Objects.requireNonNull(parcel);
            return (List)
                    mapToObj.peek(
                                    new Consumer() { // from class:
                                        // android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            Parcel.this.readStringList((ArrayList) obj);
                                        }
                                    })
                            .collect(Collectors.toUnmodifiableList());
        }

        static /* synthetic */ ArrayList lambda$createGroupsFromParcel$0(int i) {
            return new ArrayList();
        }
    }
}
