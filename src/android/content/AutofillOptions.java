package android.content;

import android.app.ActivityThread;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.autofill.AutofillManager;
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public final class AutofillOptions implements Parcelable {
    public static final Parcelable.Creator<AutofillOptions> CREATOR = new Parcelable.Creator<AutofillOptions>() { // from class: android.content.AutofillOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AutofillOptions createFromParcel(Parcel parcel) {
            AutofillOptions autofillOptions = new AutofillOptions(parcel.readInt(), parcel.readBoolean());
            autofillOptions.augmentedAutofillEnabled = parcel.readBoolean();
            autofillOptions.whitelistedActivitiesForAugmentedAutofill = parcel.readArraySet(null);
            autofillOptions.appDisabledExpiration = parcel.readLong();
            int i = parcel.readInt();
            if (i > 0) {
                autofillOptions.disabledActivities = new ArrayMap<>();
                for (int i2 = 0; i2 < i; i2++) {
                    autofillOptions.disabledActivities.put(parcel.readString(), Long.valueOf(parcel.readLong()));
                }
            }
            return autofillOptions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AutofillOptions[] newArray(int i) {
            return new AutofillOptions[i];
        }
    };
    private static final String TAG = "AutofillOptions";
    public long appDisabledExpiration;
    public boolean augmentedAutofillEnabled;
    public final boolean compatModeEnabled;
    public ArrayMap<String, Long> disabledActivities;
    public final int loggingLevel;
    public ArraySet<ComponentName> whitelistedActivitiesForAugmentedAutofill;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AutofillOptions(int i, boolean z) {
        this.loggingLevel = i;
        this.compatModeEnabled = z;
    }

    public boolean isAugmentedAutofillEnabled(Context context) {
        AutofillManager.AutofillClient autofillClient;
        if (!this.augmentedAutofillEnabled || (autofillClient = context.getAutofillClient()) == null) {
            return false;
        }
        ComponentName componentNameAutofillClientGetComponentName = autofillClient.autofillClientGetComponentName();
        ArraySet<ComponentName> arraySet = this.whitelistedActivitiesForAugmentedAutofill;
        return arraySet == null || arraySet.contains(componentNameAutofillClientGetComponentName);
    }

    public boolean isAutofillDisabledLocked(ComponentName componentName) {
        Long l;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        String strFlattenToString = componentName.flattenToString();
        if (this.appDisabledExpiration >= jElapsedRealtime) {
            return true;
        }
        ArrayMap<String, Long> arrayMap = this.disabledActivities;
        if (arrayMap != null && (l = arrayMap.get(strFlattenToString)) != null) {
            if (l.longValue() >= jElapsedRealtime) {
                return true;
            }
            this.disabledActivities.remove(strFlattenToString);
        }
        this.appDisabledExpiration = 0L;
        return false;
    }

    public static AutofillOptions forWhitelistingItself() {
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread == null) {
            throw new IllegalStateException("No ActivityThread");
        }
        String packageName = activityThreadCurrentActivityThread.getApplication().getPackageName();
        if (!"android.autofillservice.cts".equals(packageName)) {
            Log.e(TAG, "forWhitelistingItself(): called by " + packageName);
            throw new SecurityException("Thou shall not pass!");
        }
        AutofillOptions autofillOptions = new AutofillOptions(4, true);
        autofillOptions.augmentedAutofillEnabled = true;
        Log.i(TAG, "forWhitelistingItself(" + packageName + "): " + autofillOptions);
        return autofillOptions;
    }

    public String toString() {
        return "AutofillOptions [loggingLevel=" + this.loggingLevel + ", compatMode=" + this.compatModeEnabled + ", augmentedAutofillEnabled=" + this.augmentedAutofillEnabled + ", appDisabledExpiration=" + this.appDisabledExpiration + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dumpShort(PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        printWriter.print(", compatMode=");
        printWriter.print(this.compatModeEnabled);
        printWriter.print(", augmented=");
        printWriter.print(this.augmentedAutofillEnabled);
        if (this.whitelistedActivitiesForAugmentedAutofill != null) {
            printWriter.print(", whitelistedActivitiesForAugmentedAutofill=");
            printWriter.print(this.whitelistedActivitiesForAugmentedAutofill);
        }
        printWriter.print(", appDisabledExpiration=");
        printWriter.print(this.appDisabledExpiration);
        if (this.disabledActivities != null) {
            printWriter.print(", disabledActivities=");
            printWriter.print(this.disabledActivities);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(this.loggingLevel);
        parcel.writeBoolean(this.compatModeEnabled);
        parcel.writeBoolean(this.augmentedAutofillEnabled);
        parcel.writeArraySet(this.whitelistedActivitiesForAugmentedAutofill);
        parcel.writeLong(this.appDisabledExpiration);
        ArrayMap<String, Long> arrayMap = this.disabledActivities;
        int size = arrayMap != null ? arrayMap.size() : 0;
        parcel.writeInt(size);
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                String strKeyAt = this.disabledActivities.keyAt(i2);
                parcel.writeString(strKeyAt);
                parcel.writeLong(this.disabledActivities.get(strKeyAt).longValue());
            }
        }
    }
}
