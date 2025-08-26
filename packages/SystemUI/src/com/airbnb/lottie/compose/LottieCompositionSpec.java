package com.airbnb.lottie.compose;

import android.net.Uri;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public interface LottieCompositionSpec {

    public final class Asset implements LottieCompositionSpec {
        public final String assetName;

        public final boolean equals(Object obj) {
            if (obj instanceof Asset) {
                return Intrinsics.areEqual(this.assetName, ((Asset) obj).assetName);
            }
            return false;
        }

        public final int hashCode() {
            return this.assetName.hashCode();
        }

        public final String toString() {
            return "Asset(assetName=" + this.assetName + ')';
        }
    }

    public final class ContentProvider implements LottieCompositionSpec {
        public final Uri uri;

        public final boolean equals(Object obj) {
            return (obj instanceof ContentProvider) && Intrinsics.areEqual(this.uri, ((ContentProvider) obj).uri);
        }

        public final int hashCode() {
            return this.uri.hashCode();
        }

        public final String toString() {
            return "ContentProvider(uri=" + this.uri + ')';
        }
    }

    public final class File implements LottieCompositionSpec {
        public final String fileName;

        public final boolean equals(Object obj) {
            if (obj instanceof File) {
                return Intrinsics.areEqual(this.fileName, ((File) obj).fileName);
            }
            return false;
        }

        public final int hashCode() {
            return this.fileName.hashCode();
        }

        public final String toString() {
            return "File(fileName=" + this.fileName + ')';
        }
    }

    public final class JsonString implements LottieCompositionSpec {
        public final String jsonString;

        public final boolean equals(Object obj) {
            if (obj instanceof JsonString) {
                return Intrinsics.areEqual(this.jsonString, ((JsonString) obj).jsonString);
            }
            return false;
        }

        public final int hashCode() {
            return this.jsonString.hashCode();
        }

        public final String toString() {
            return "JsonString(jsonString=" + this.jsonString + ')';
        }
    }

    public final class RawRes implements LottieCompositionSpec {
        public final int resId;

        private /* synthetic */ RawRes(int i) {
            this.resId = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ RawRes m901boximpl(int i) {
            return new RawRes(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof RawRes) {
                return this.resId == ((RawRes) obj).resId;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.resId);
        }

        public final String toString() {
            return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("RawRes(resId="), this.resId, ')');
        }
    }

    public final class Url implements LottieCompositionSpec {
        public final String url;

        public final boolean equals(Object obj) {
            if (obj instanceof Url) {
                return Intrinsics.areEqual(this.url, ((Url) obj).url);
            }
            return false;
        }

        public final int hashCode() {
            return this.url.hashCode();
        }

        public final String toString() {
            return "Url(url=" + this.url + ')';
        }
    }
}
