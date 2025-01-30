package com.android.server.pm;

import android.util.Slog;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.InputStream;
import java.util.Stack;

/* loaded from: classes3.dex */
public abstract class SettingsXml {

    public interface ChildSection extends ReadSection {
        boolean moveToNext();

        boolean moveToNext(String str);
    }

    public interface ReadSection extends AutoCloseable {
        ChildSection children();

        boolean getBoolean(String str);

        boolean getBoolean(String str, boolean z);

        int getInt(String str);

        int getInt(String str, int i);

        String getName();

        String getString(String str);
    }

    public interface WriteSection extends AutoCloseable {
        WriteSection attribute(String str, int i);

        WriteSection attribute(String str, String str2);

        WriteSection attribute(String str, boolean z);

        @Override // java.lang.AutoCloseable
        void close();

        void finish();

        WriteSection startSection(String str);
    }

    public static Serializer serializer(TypedXmlSerializer typedXmlSerializer) {
        return new Serializer(typedXmlSerializer);
    }

    public static ReadSection parser(TypedXmlPullParser typedXmlPullParser) {
        return new ReadSectionImpl(typedXmlPullParser);
    }

    public class Serializer implements AutoCloseable {
        public final WriteSectionImpl mWriteSection;
        public final TypedXmlSerializer mXmlSerializer;

        public Serializer(TypedXmlSerializer typedXmlSerializer) {
            this.mXmlSerializer = typedXmlSerializer;
            this.mWriteSection = new WriteSectionImpl(typedXmlSerializer);
        }

        public WriteSection startSection(String str) {
            return this.mWriteSection.startSection(str);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mWriteSection.closeCompletely();
            this.mXmlSerializer.flush();
        }
    }

    public class ReadSectionImpl implements ChildSection {
        public final Stack mDepthStack = new Stack();
        public final InputStream mInput = null;
        public final TypedXmlPullParser mParser;

        public ReadSectionImpl(TypedXmlPullParser typedXmlPullParser) {
            this.mParser = typedXmlPullParser;
            moveToFirstTag();
        }

        public final void moveToFirstTag() {
            int next;
            if (this.mParser.getEventType() == 2) {
                return;
            }
            do {
                next = this.mParser.next();
                if (next == 2) {
                    return;
                }
            } while (next != 1);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public String getName() {
            return this.mParser.getName();
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public String getString(String str) {
            return this.mParser.getAttributeValue((String) null, str);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public boolean getBoolean(String str) {
            return getBoolean(str, false);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public boolean getBoolean(String str, boolean z) {
            return this.mParser.getAttributeBoolean((String) null, str, z);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public int getInt(String str) {
            return getInt(str, -1);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public int getInt(String str, int i) {
            return this.mParser.getAttributeInt((String) null, str, i);
        }

        @Override // com.android.server.pm.SettingsXml.ReadSection
        public ChildSection children() {
            this.mDepthStack.push(Integer.valueOf(this.mParser.getDepth()));
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.ChildSection
        public boolean moveToNext() {
            return moveToNextInternal(null);
        }

        @Override // com.android.server.pm.SettingsXml.ChildSection
        public boolean moveToNext(String str) {
            return moveToNextInternal(str);
        }

        public final boolean moveToNextInternal(String str) {
            try {
                int intValue = ((Integer) this.mDepthStack.peek()).intValue();
                boolean z = false;
                while (!z) {
                    int next = this.mParser.next();
                    if (next == 1 || (next == 3 && this.mParser.getDepth() <= intValue)) {
                        break;
                    }
                    if (next == 2 && (str == null || str.equals(this.mParser.getName()))) {
                        z = true;
                    }
                }
                if (!z) {
                    this.mDepthStack.pop();
                }
                return z;
            } catch (Exception unused) {
                return false;
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mDepthStack.isEmpty()) {
                Slog.wtf("SettingsXml", "Children depth stack was not empty, data may have been lost", new Exception());
            }
            InputStream inputStream = this.mInput;
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public class WriteSectionImpl implements WriteSection {
        public final Stack mTagStack;
        public final TypedXmlSerializer mXmlSerializer;

        public WriteSectionImpl(TypedXmlSerializer typedXmlSerializer) {
            this.mTagStack = new Stack();
            this.mXmlSerializer = typedXmlSerializer;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public WriteSection startSection(String str) {
            this.mXmlSerializer.startTag((String) null, str);
            this.mTagStack.push(str);
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public WriteSection attribute(String str, String str2) {
            if (str2 != null) {
                this.mXmlSerializer.attribute((String) null, str, str2);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public WriteSection attribute(String str, int i) {
            if (i != -1) {
                this.mXmlSerializer.attributeInt((String) null, str, i);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public WriteSection attribute(String str, boolean z) {
            if (z) {
                this.mXmlSerializer.attributeBoolean((String) null, str, z);
            }
            return this;
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection
        public void finish() {
            close();
        }

        @Override // com.android.server.pm.SettingsXml.WriteSection, java.lang.AutoCloseable
        public void close() {
            this.mXmlSerializer.endTag((String) null, (String) this.mTagStack.pop());
        }

        public final void closeCompletely() {
            if (this.mTagStack != null) {
                while (!this.mTagStack.isEmpty()) {
                    close();
                }
            }
        }
    }
}
