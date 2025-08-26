package android.content.om;

import android.content.res.AssetFileDescriptor;
import android.os.FabricatedOverlayInternal;
import android.os.FabricatedOverlayInternalEntry;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.TypedValue;
import com.android.internal.content.om.OverlayManagerImpl;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class FabricatedOverlay {
    final FabricatedOverlayInternal mOverlay;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StringTypeOverlayResource {
    }

    public OverlayIdentifier getIdentifier() {
        return new OverlayIdentifier(this.mOverlay.packageName, TextUtils.nullIfEmpty(this.mOverlay.overlayName));
    }

    public static final class Builder {
        private final String mName;
        private final String mOwningPackage;
        private final String mTargetPackage;
        private String mTargetOverlayable = "";
        private final ArrayList<FabricatedOverlayInternalEntry> mEntries = new ArrayList<>();

        public Builder(String str, String str2, String str3) {
            Preconditions.checkStringNotEmpty(str, "'owningPackage' must not be empty nor null");
            Preconditions.checkStringNotEmpty(str2, "'name'' must not be empty nor null");
            Preconditions.checkStringNotEmpty(str3, "'targetPackage' must not be empty nor null");
            this.mOwningPackage = str;
            this.mName = str2;
            this.mTargetPackage = str3;
        }

        public Builder setTargetOverlayable(String str) {
            this.mTargetOverlayable = TextUtils.emptyIfNull(str);
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, int i, int i2) {
            return setResourceValue(str, i, i2, (String) null);
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, int i, int i2, String str2) {
            FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, i, i2, str2));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, int i, String str2) {
            return setResourceValue(str, i, str2, (String) null);
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, int i, String str2, String str3) {
            FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, i, str2, str3));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) {
            FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, false));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String str, AssetFileDescriptor assetFileDescriptor, String str2) {
            FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, assetFileDescriptor, str2));
            return this;
        }

        public FabricatedOverlay build() {
            return new FabricatedOverlay(FabricatedOverlay.generateFabricatedOverlayInternal(this.mOwningPackage, this.mName, this.mTargetPackage, this.mTargetOverlayable, this.mEntries));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternal generateFabricatedOverlayInternal(String str, String str2, String str3, String str4, ArrayList<FabricatedOverlayInternalEntry> arrayList) {
        FabricatedOverlayInternal fabricatedOverlayInternal = new FabricatedOverlayInternal();
        fabricatedOverlayInternal.packageName = str;
        fabricatedOverlayInternal.overlayName = str2;
        fabricatedOverlayInternal.targetPackageName = str3;
        fabricatedOverlayInternal.targetOverlayable = TextUtils.emptyIfNull(str4);
        fabricatedOverlayInternal.entries = new ArrayList();
        fabricatedOverlayInternal.entries.addAll(arrayList);
        return fabricatedOverlayInternal;
    }

    private FabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) {
        this.mOverlay = fabricatedOverlayInternal;
    }

    public FabricatedOverlay(String str, String str2) {
        this(generateFabricatedOverlayInternal("", OverlayManagerImpl.checkOverlayNameValid(str), (String) Preconditions.checkStringNotEmpty(str2, "'targetPackage' must not be empty nor null"), null, new ArrayList()));
    }

    public void setOwningPackage(String str) {
        this.mOverlay.packageName = str;
    }

    public void setTargetOverlayable(String str) {
        this.mOverlay.targetOverlayable = TextUtils.emptyIfNull(str);
    }

    public String getTargetOverlayable() {
        return this.mOverlay.targetOverlayable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String ensureValidResourceName(String str) {
        Objects.requireNonNull(str);
        int iIndexOf = str.indexOf(47);
        int iIndexOf2 = str.indexOf(58);
        Preconditions.checkArgument(iIndexOf >= 0 && iIndexOf2 != 0 && iIndexOf - iIndexOf2 > 2, "\"%s\" is invalid resource name", str);
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, int i, int i2, String str2) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = Preconditions.checkArgumentInRange(i, 16, 31, "dataType");
        fabricatedOverlayInternalEntry.data = i2;
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, int i, String str2, String str3) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = Preconditions.checkArgumentInRange(i, 3, 6, "dataType");
        fabricatedOverlayInternalEntry.stringData = (String) Objects.requireNonNull(str2);
        fabricatedOverlayInternalEntry.configuration = str3;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, ParcelFileDescriptor parcelFileDescriptor, String str2, boolean z) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.binaryData = (ParcelFileDescriptor) Objects.requireNonNull(parcelFileDescriptor);
        fabricatedOverlayInternalEntry.configuration = str2;
        fabricatedOverlayInternalEntry.binaryDataOffset = 0L;
        fabricatedOverlayInternalEntry.binaryDataSize = parcelFileDescriptor.getStatSize();
        fabricatedOverlayInternalEntry.isNinePatch = z;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, AssetFileDescriptor assetFileDescriptor, String str2) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.binaryData = (ParcelFileDescriptor) Objects.requireNonNull(assetFileDescriptor.getParcelFileDescriptor());
        fabricatedOverlayInternalEntry.binaryDataOffset = assetFileDescriptor.getStartOffset();
        fabricatedOverlayInternalEntry.binaryDataSize = assetFileDescriptor.getLength();
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    private static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, float f, int i, String str2) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = 5;
        Preconditions.checkArgumentInRange(i, 0, 5, "dimensionUnit");
        fabricatedOverlayInternalEntry.data = TypedValue.createComplexDimension(f, i);
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    private static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String str, float f, String str2) {
        FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = 4;
        fabricatedOverlayInternalEntry.data = Float.floatToIntBits(f);
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    public void setResourceValue(String str, int i, int i2, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, i, i2, str2));
    }

    public void setResourceValue(String str, int i, String str2, String str3) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, i, str2, str3));
    }

    public void setResourceValue(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, false));
    }

    public void setNinePatchResourceValue(String str, ParcelFileDescriptor parcelFileDescriptor, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, true));
    }

    public void setResourceValue(String str, AssetFileDescriptor assetFileDescriptor, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, assetFileDescriptor, str2));
    }

    public void setResourceValue(String str, float f, int i, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, f, i, str2));
    }

    public void setResourceValue(String str, float f, String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, f, str2));
    }
}
