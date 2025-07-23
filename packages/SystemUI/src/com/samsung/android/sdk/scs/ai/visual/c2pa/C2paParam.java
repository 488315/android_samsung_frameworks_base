package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paParam {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class C2paExistParamBuilder extends Builder {
        public C2paExistParamBuilder() {
        }

        public C2paExistParamBuilder setExtensionType(String str) {
            this.params.putString("mimeType", str);
            return this;
        }

        public C2paExistParamBuilder setPfd(ParcelFileDescriptor parcelFileDescriptor) {
            this.params.putParcelable("Pfd", parcelFileDescriptor);
            return this;
        }

        public C2paExistParamBuilder(Bundle bundle) {
            super(bundle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EmbedParamBuilder extends Builder {
        public EmbedParamBuilder() {
        }

        public EmbedParamBuilder setIngredientExtensionTypes(List<String> list) {
            if (list == null) {
                this.params.putStringArrayList("ingredientMimeTypes", null);
                return this;
            }
            this.params.putStringArrayList("ingredientMimeTypes", new ArrayList<>(list));
            return this;
        }

        public EmbedParamBuilder setIngredientPFD(List<ParcelFileDescriptor> list) {
            if (list == null) {
                this.params.putParcelableArrayList("ingredientPfds", null);
                return this;
            }
            this.params.putParcelableArrayList("ingredientPfds", new ArrayList<>(list));
            return this;
        }

        public EmbedParamBuilder setIngredientPaths(List<String> list) {
            if (list == null) {
                this.params.putStringArrayList("ingredientPaths", null);
                return this;
            }
            this.params.putStringArrayList("ingredientPaths", new ArrayList<>(list));
            return this;
        }

        public EmbedParamBuilder setManifestJson(String str) {
            this.params.putString("jsonStr", str);
            return this;
        }

        public EmbedParamBuilder setParentExtensionType(String str) {
            this.params.putString("parentMimeType", str);
            return this;
        }

        public EmbedParamBuilder setParentPFD(ParcelFileDescriptor parcelFileDescriptor) {
            this.params.putParcelable("parentPfd", parcelFileDescriptor);
            return this;
        }

        public EmbedParamBuilder setParentPath(String str) {
            this.params.putString("parentPath", str);
            return this;
        }

        public EmbedParamBuilder setTargetExtensionType(String str) {
            this.params.putString("targetMimeType", str);
            return this;
        }

        public EmbedParamBuilder setTargetPFD(ParcelFileDescriptor parcelFileDescriptor) {
            this.params.putParcelable("targetPfd", parcelFileDescriptor);
            return this;
        }

        public EmbedParamBuilder setTargetPath(String str) {
            this.params.putString("targetPath", str);
            return this;
        }

        public EmbedParamBuilder(Bundle bundle) {
            super(bundle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExtractParamBuilder extends Builder {
        public ExtractParamBuilder() {
        }

        public ExtractParamBuilder setExtensionType(String str) {
            this.params.putString("mimeType", str);
            return this;
        }

        public ExtractParamBuilder setFilePath(String str) {
            this.params.putString("filePath", str);
            return this;
        }

        public ExtractParamBuilder setPfd(ParcelFileDescriptor parcelFileDescriptor) {
            this.params.putParcelable("Pfd", parcelFileDescriptor);
            return this;
        }

        public ExtractParamBuilder(Bundle bundle) {
            super(bundle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SaveToCacheParamBuilder extends Builder {
        public SaveToCacheParamBuilder() {
        }

        public SaveToCacheParamBuilder setExtensionType(String str) {
            this.params.putString("mimeType", str);
            return this;
        }

        public SaveToCacheParamBuilder setFilePath(String str) {
            this.params.putString("filePath", str);
            return this;
        }

        public SaveToCacheParamBuilder setPfd(ParcelFileDescriptor parcelFileDescriptor) {
            this.params.putParcelable("Pfd", parcelFileDescriptor);
            return this;
        }

        public SaveToCacheParamBuilder(Bundle bundle) {
            super(bundle);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        protected Bundle params;

        public Builder() {
            this.params = new Bundle();
        }

        public Bundle build() {
            return this.params;
        }

        public Builder(Bundle bundle) {
            new Bundle();
            this.params = bundle;
        }
    }
}
