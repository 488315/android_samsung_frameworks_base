package android.content;

import android.app.ActivityThread;
import android.content.ContentCaptureOptions;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import java.io.IOException;
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
    public static final Parcelable.Creator<ContentCaptureOptions> CREATOR = new Parcelable.Creator<ContentCaptureOptions>() { // from class: android.content.ContentCaptureOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureOptions createFromParcel(Parcel parcel) {
            boolean z = parcel.readBoolean();
            int i = parcel.readInt();
            if (z) {
                return new ContentCaptureOptions(i);
            }
            return new ContentCaptureOptions(i, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), ContentProtectionOptions.createFromParcel(parcel), parcel.readArraySet(null));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureOptions[] newArray(int i) {
            return new ContentCaptureOptions[i];
        }
    };
    private static final String TAG = "ContentCaptureOptions";
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentCaptureOptions(int i) {
        this(true, i, 0, 0, 0, 0, false, false, new ContentProtectionOptions(false, 0, Collections.EMPTY_LIST, Collections.EMPTY_LIST, 0), null);
    }

    public ContentCaptureOptions(int i, int i2, int i3, int i4, int i5, ArraySet<ComponentName> arraySet) {
        this(false, i, i2, i3, i4, i5, false, true, new ContentProtectionOptions(), arraySet);
    }

    public ContentCaptureOptions(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, ContentProtectionOptions contentProtectionOptions, ArraySet<ComponentName> arraySet) {
        this(false, i, i2, i3, i4, i5, z, z2, contentProtectionOptions, arraySet);
    }

    public ContentCaptureOptions(ArraySet<ComponentName> arraySet) {
        this(2, 500, 5000, 1000, 10, false, true, new ContentProtectionOptions(), arraySet);
    }

    private ContentCaptureOptions(boolean z, int i, int i2, int i3, int i4, int i5, boolean z2, boolean z3, ContentProtectionOptions contentProtectionOptions, ArraySet<ComponentName> arraySet) {
        this.lite = z;
        this.loggingLevel = i;
        this.maxBufferSize = i2;
        this.idleFlushingFrequencyMs = i3;
        this.textChangeFlushingFrequencyMs = i4;
        this.logHistorySize = i5;
        this.disableFlushForViewTreeAppearing = z2;
        this.enableReceiver = z3;
        this.contentProtectionOptions = contentProtectionOptions;
        this.whitelistedComponents = arraySet;
    }

    public static ContentCaptureOptions forWhitelistingItself() {
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread == null) {
            throw new IllegalStateException("No ActivityThread");
        }
        String packageName = activityThreadCurrentActivityThread.getApplication().getPackageName();
        if (!"android.contentcaptureservice.cts".equals(packageName) && !"android.translation.cts".equals(packageName)) {
            Log.e(TAG, "forWhitelistingItself(): called by " + packageName);
            throw new SecurityException("Thou shall not pass!");
        }
        ContentCaptureOptions contentCaptureOptions = new ContentCaptureOptions((ArraySet<ComponentName>) null);
        Log.i(TAG, "forWhitelistingItself(" + packageName + "): " + contentCaptureOptions);
        return contentCaptureOptions;
    }

    public boolean isWhitelisted(Context context) {
        if (this.whitelistedComponents == null) {
            return true;
        }
        ContentCaptureManager.ContentCaptureClient contentCaptureClient = context.getContentCaptureClient();
        if (contentCaptureClient == null) {
            Log.w(TAG, "isWhitelisted(): no ContentCaptureClient on " + context);
            return false;
        }
        return this.whitelistedComponents.contains(contentCaptureClient.contentCaptureClientGetComponentName());
    }

    public String toString() {
        if (this.lite) {
            return "ContentCaptureOptions [loggingLevel=" + this.loggingLevel + " (lite)]";
        }
        StringBuilder sb = new StringBuilder("ContentCaptureOptions [loggingLevel=");
        sb.append(this.loggingLevel);
        sb.append(", maxBufferSize=");
        sb.append(this.maxBufferSize);
        sb.append(", idleFlushingFrequencyMs=");
        sb.append(this.idleFlushingFrequencyMs);
        sb.append(", textChangeFlushingFrequencyMs=");
        sb.append(this.textChangeFlushingFrequencyMs);
        sb.append(", logHistorySize=");
        sb.append(this.logHistorySize);
        sb.append(", disableFlushForViewTreeAppearing=");
        sb.append(this.disableFlushForViewTreeAppearing);
        sb.append(", enableReceiver=");
        sb.append(this.enableReceiver);
        sb.append(", contentProtectionOptions=");
        sb.append(this.contentProtectionOptions);
        if (this.whitelistedComponents != null) {
            sb.append(", whitelisted=");
            sb.append(this.whitelistedComponents);
        }
        sb.append(']');
        return sb.toString();
    }

    public void dumpShort(PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        if (this.lite) {
            printWriter.print(", lite");
            return;
        }
        printWriter.print(", bufferSize=");
        printWriter.print(this.maxBufferSize);
        printWriter.print(", idle=");
        printWriter.print(this.idleFlushingFrequencyMs);
        printWriter.print(", textIdle=");
        printWriter.print(this.textChangeFlushingFrequencyMs);
        printWriter.print(", logSize=");
        printWriter.print(this.logHistorySize);
        printWriter.print(", disableFlushForViewTreeAppearing=");
        printWriter.print(this.disableFlushForViewTreeAppearing);
        printWriter.print(", enableReceiver=");
        printWriter.print(this.enableReceiver);
        printWriter.print(", contentProtectionOptions=[");
        this.contentProtectionOptions.dumpShort(printWriter);
        printWriter.print(NavigationBarInflaterView.SIZE_MOD_END);
        if (this.whitelistedComponents != null) {
            printWriter.print(", whitelisted=");
            printWriter.print(this.whitelistedComponents);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
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
            this(false, 150, ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS, ContentCaptureManager.DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS, 0);
        }

        public ContentProtectionOptions(boolean z, int i, List<List<String>> list, List<List<String>> list2, int i2) {
            this.enableReceiver = z;
            this.bufferSize = i;
            this.requiredGroups = list;
            this.optionalGroups = list2;
            this.optionalGroupsThreshold = i2;
        }

        public String toString() {
            return "ContentProtectionOptions [enableReceiver=" + this.enableReceiver + ", bufferSize=" + this.bufferSize + ", requiredGroupsSize=" + this.requiredGroups.size() + ", optionalGroupsSize=" + this.optionalGroups.size() + ", optionalGroupsThreshold=" + this.optionalGroupsThreshold + ']';
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpShort(PrintWriter printWriter) {
            printWriter.print("enableReceiver=");
            printWriter.print(this.enableReceiver);
            printWriter.print(", bufferSize=");
            printWriter.print(this.bufferSize);
            printWriter.print(", requiredGroupsSize=");
            printWriter.print(this.requiredGroups.size());
            printWriter.print(", optionalGroupsSize=");
            printWriter.print(this.optionalGroups.size());
            printWriter.print(", optionalGroupsThreshold=");
            printWriter.print(this.optionalGroupsThreshold);
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
            return new ContentProtectionOptions(parcel.readBoolean(), parcel.readInt(), createGroupsFromParcel(parcel), createGroupsFromParcel(parcel), parcel.readInt());
        }

        private static void writeGroupsToParcel(List<List<String>> list, final Parcel parcel) {
            parcel.writeInt(list.size());
            Objects.requireNonNull(parcel);
            list.forEach(new Consumer() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    parcel.writeStringList((List) obj);
                }
            });
        }

        private static List<List<String>> createGroupsFromParcel(final Parcel parcel) {
            Stream streamMapToObj = IntStream.range(0, parcel.readInt()).mapToObj(new IntFunction() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return ContentCaptureOptions.ContentProtectionOptions.lambda$createGroupsFromParcel$0(i);
                }
            });
            Objects.requireNonNull(parcel);
            return (List) streamMapToObj.peek(new Consumer() { // from class: android.content.ContentCaptureOptions$ContentProtectionOptions$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    parcel.readStringList((ArrayList) obj);
                }
            }).collect(Collectors.toUnmodifiableList());
        }

        static /* synthetic */ ArrayList lambda$createGroupsFromParcel$0(int i) {
            return new ArrayList();
        }
    }
}
