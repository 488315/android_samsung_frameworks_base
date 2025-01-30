package com.android.server.integrity.serializer;

import android.util.Xml;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.integrity.model.RuleMetadata;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public abstract class RuleMetadataSerializer {
  public static void serialize(RuleMetadata ruleMetadata, OutputStream outputStream) {
    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
    serializeTaggedValue(resolveSerializer, "P", ruleMetadata.getRuleProvider());
    serializeTaggedValue(resolveSerializer, "V", ruleMetadata.getVersion());
    resolveSerializer.endDocument();
  }

  public static void serializeTaggedValue(
      TypedXmlSerializer typedXmlSerializer, String str, String str2) {
    typedXmlSerializer.startTag((String) null, str);
    typedXmlSerializer.text(str2);
    typedXmlSerializer.endTag((String) null, str);
  }
}
