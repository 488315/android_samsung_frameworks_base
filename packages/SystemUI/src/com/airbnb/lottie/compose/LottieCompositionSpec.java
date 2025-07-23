package com.airbnb.lottie.compose;

import android.net.Uri;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LottieCompositionSpec {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RawRes implements LottieCompositionSpec {
        public final int resId;

        private /* synthetic */ RawRes(int i) {
            this.resId = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ RawRes m899boximpl(int i) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
