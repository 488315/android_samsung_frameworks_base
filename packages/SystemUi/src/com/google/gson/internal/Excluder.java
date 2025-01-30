package com.google.gson.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    public final double version = -1.0d;
    public final int modifiers = 136;
    public final boolean serializeInnerClasses = true;
    public final List serializationStrategies = Collections.emptyList();
    public final List deserializationStrategies = Collections.emptyList();

    public static boolean isAnonymousOrNonStaticLocal(Class cls) {
        if (Enum.class.isAssignableFrom(cls)) {
            return false;
        }
        if ((cls.getModifiers() & 8) != 0) {
            return false;
        }
        return cls.isAnonymousClass() || cls.isLocalClass();
    }

    public final Object clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(final Gson gson, final TypeToken typeToken) {
        final boolean z;
        final boolean z2;
        boolean excludeClassChecks = excludeClassChecks(typeToken.rawType);
        if (excludeClassChecks) {
            z = true;
        } else {
            excludeClassInStrategy(true);
            z = false;
        }
        if (excludeClassChecks) {
            z2 = true;
        } else {
            excludeClassInStrategy(false);
            z2 = false;
        }
        if (z || z2) {
            return new TypeAdapter() { // from class: com.google.gson.internal.Excluder.1
                public TypeAdapter delegate;

                @Override // com.google.gson.TypeAdapter
                /* renamed from: read */
                public final Object mo2768read(JsonReader jsonReader) {
                    if (z2) {
                        jsonReader.skipValue();
                        return null;
                    }
                    TypeAdapter typeAdapter = this.delegate;
                    if (typeAdapter == null) {
                        typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                        this.delegate = typeAdapter;
                    }
                    return typeAdapter.mo2768read(jsonReader);
                }

                @Override // com.google.gson.TypeAdapter
                public final void write(JsonWriter jsonWriter, Object obj) {
                    if (z) {
                        jsonWriter.nullValue();
                        return;
                    }
                    TypeAdapter typeAdapter = this.delegate;
                    if (typeAdapter == null) {
                        typeAdapter = gson.getDelegateAdapter(Excluder.this, typeToken);
                        this.delegate = typeAdapter;
                    }
                    typeAdapter.write(jsonWriter, obj);
                }
            };
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean excludeClassChecks(Class cls) {
        boolean z;
        if (this.version != -1.0d && !isValidVersion((Since) cls.getAnnotation(Since.class), (Until) cls.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.serializeInnerClasses) {
            if (cls.isMemberClass()) {
                if (!((cls.getModifiers() & 8) != 0)) {
                    z = true;
                    if (z) {
                        return true;
                    }
                }
            }
            z = false;
            if (z) {
            }
        }
        return isAnonymousOrNonStaticLocal(cls);
    }

    public final void excludeClassInStrategy(boolean z) {
        Iterator it = (z ? this.serializationStrategies : this.deserializationStrategies).iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it.next());
            throw null;
        }
    }

    public final boolean isValidVersion(Since since, Until until) {
        if (since == null || since.value() <= this.version) {
            return until == null || (until.value() > this.version ? 1 : (until.value() == this.version ? 0 : -1)) > 0;
        }
        return false;
    }
}
