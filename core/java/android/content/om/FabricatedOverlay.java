package android.content.om;

import android.content.res.AssetFileDescriptor;
import android.os.FabricatedOverlayInternal;
import android.os.FabricatedOverlayInternalEntry;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
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

        public Builder(String owningPackage, String name, String targetPackage) {
            Preconditions.checkStringNotEmpty(owningPackage, "'owningPackage' must not be empty nor null");
            Preconditions.checkStringNotEmpty(name, "'name'' must not be empty nor null");
            Preconditions.checkStringNotEmpty(targetPackage, "'targetPackage' must not be empty nor null");
            this.mOwningPackage = owningPackage;
            this.mName = name;
            this.mTargetPackage = targetPackage;
        }

        public Builder setTargetOverlayable(String targetOverlayable) {
            this.mTargetOverlayable = TextUtils.emptyIfNull(targetOverlayable);
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, int dataType, int value) {
            return setResourceValue(resourceName, dataType, value, (String) null);
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, int dataType, int value, String configuration) {
            FabricatedOverlay.ensureValidResourceName(resourceName);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(resourceName, dataType, value, configuration));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, int dataType, String value) {
            return setResourceValue(resourceName, dataType, value, (String) null);
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, int dataType, String value, String configuration) {
            FabricatedOverlay.ensureValidResourceName(resourceName);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(resourceName, dataType, value, configuration));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, ParcelFileDescriptor value, String configuration) {
            FabricatedOverlay.ensureValidResourceName(resourceName);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(resourceName, value, configuration, false));
            return this;
        }

        @Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public Builder setResourceValue(String resourceName, AssetFileDescriptor value, String configuration) {
            FabricatedOverlay.ensureValidResourceName(resourceName);
            this.mEntries.add(FabricatedOverlay.generateFabricatedOverlayInternalEntry(resourceName, value, configuration));
            return this;
        }

        public FabricatedOverlay build() {
            return new FabricatedOverlay(FabricatedOverlay.generateFabricatedOverlayInternal(this.mOwningPackage, this.mName, this.mTargetPackage, this.mTargetOverlayable, this.mEntries));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternal generateFabricatedOverlayInternal(String owningPackage, String overlayName, String targetPackageName, String targetOverlayable, ArrayList<FabricatedOverlayInternalEntry> entries) {
        FabricatedOverlayInternal overlay = new FabricatedOverlayInternal();
        overlay.packageName = owningPackage;
        overlay.overlayName = overlayName;
        overlay.targetPackageName = targetPackageName;
        overlay.targetOverlayable = TextUtils.emptyIfNull(targetOverlayable);
        overlay.entries = new ArrayList();
        overlay.entries.addAll(entries);
        return overlay;
    }

    private FabricatedOverlay(FabricatedOverlayInternal overlay) {
        this.mOverlay = overlay;
    }

    public FabricatedOverlay(String overlayName, String targetPackage) {
        this(generateFabricatedOverlayInternal("", OverlayManagerImpl.checkOverlayNameValid(overlayName), (String) Preconditions.checkStringNotEmpty(targetPackage, "'targetPackage' must not be empty nor null"), null, new ArrayList()));
    }

    public void setOwningPackage(String owningPackage) {
        this.mOverlay.packageName = owningPackage;
    }

    public void setTargetOverlayable(String targetOverlayable) {
        this.mOverlay.targetOverlayable = TextUtils.emptyIfNull(targetOverlayable);
    }

    public String getTargetOverlayable() {
        return this.mOverlay.targetOverlayable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String ensureValidResourceName(String name) {
        Objects.requireNonNull(name);
        int slashIndex = name.indexOf(47);
        int colonIndex = name.indexOf(58);
        Preconditions.checkArgument(slashIndex >= 0 && colonIndex != 0 && slashIndex - colonIndex > 2, "\"%s\" is invalid resource name", name);
        return name;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String resourceName, int dataType, int value, String configuration) {
        FabricatedOverlayInternalEntry entry = new FabricatedOverlayInternalEntry();
        entry.resourceName = resourceName;
        entry.dataType = Preconditions.checkArgumentInRange(dataType, 16, 31, "dataType");
        entry.data = value;
        entry.configuration = configuration;
        return entry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String resourceName, int dataType, String value, String configuration) {
        FabricatedOverlayInternalEntry entry = new FabricatedOverlayInternalEntry();
        entry.resourceName = resourceName;
        entry.dataType = Preconditions.checkArgumentInRange(dataType, 3, 6, "dataType");
        entry.stringData = (String) Objects.requireNonNull(value);
        entry.configuration = configuration;
        return entry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String resourceName, ParcelFileDescriptor parcelFileDescriptor, String configuration, boolean isNinePatch) {
        FabricatedOverlayInternalEntry entry = new FabricatedOverlayInternalEntry();
        entry.resourceName = resourceName;
        entry.binaryData = (ParcelFileDescriptor) Objects.requireNonNull(parcelFileDescriptor);
        entry.configuration = configuration;
        entry.binaryDataOffset = 0L;
        entry.binaryDataSize = parcelFileDescriptor.getStatSize();
        entry.isNinePatch = isNinePatch;
        return entry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(String resourceName, AssetFileDescriptor assetFileDescriptor, String configuration) {
        FabricatedOverlayInternalEntry entry = new FabricatedOverlayInternalEntry();
        entry.resourceName = resourceName;
        entry.binaryData = (ParcelFileDescriptor) Objects.requireNonNull(assetFileDescriptor.getParcelFileDescriptor());
        entry.binaryDataOffset = assetFileDescriptor.getStartOffset();
        entry.binaryDataSize = assetFileDescriptor.getLength();
        entry.configuration = configuration;
        return entry;
    }

    public void setResourceValue(String resourceName, int dataType, int value, String configuration) {
        ensureValidResourceName(resourceName);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(resourceName, dataType, value, configuration));
    }

    public void setResourceValue(String resourceName, int dataType, String value, String configuration) {
        ensureValidResourceName(resourceName);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(resourceName, dataType, value, configuration));
    }

    public void setResourceValue(String resourceName, ParcelFileDescriptor value, String configuration) {
        ensureValidResourceName(resourceName);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(resourceName, value, configuration, false));
    }

    public void setNinePatchResourceValue(String resourceName, ParcelFileDescriptor value, String configuration) {
        ensureValidResourceName(resourceName);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(resourceName, value, configuration, true));
    }

    public void setResourceValue(String resourceName, AssetFileDescriptor value, String configuration) {
        ensureValidResourceName(resourceName);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(resourceName, value, configuration));
    }
}
