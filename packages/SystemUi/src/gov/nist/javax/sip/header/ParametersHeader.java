package gov.nist.javax.sip.header;

import gov.nist.core.DuplicateNameValueList;
import gov.nist.core.GenericObject;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import java.util.Iterator;
import javax.sip.header.Parameters;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ParametersHeader extends SIPHeader implements Parameters {
    protected DuplicateNameValueList duplicates;
    protected NameValueList parameters;

    public ParametersHeader() {
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    @Override // gov.nist.core.GenericObject
    public Object clone() {
        ParametersHeader parametersHeader = (ParametersHeader) super.clone();
        NameValueList nameValueList = this.parameters;
        if (nameValueList != null) {
            parametersHeader.parameters = (NameValueList) nameValueList.clone();
        }
        return parametersHeader;
    }

    public final boolean equalParameters(Parameters parameters) {
        if (this == parameters) {
            return true;
        }
        Iterator parameterNames = getParameterNames();
        while (parameterNames.hasNext()) {
            String str = (String) parameterNames.next();
            String parameter = getParameter(str);
            String parameter2 = parameters.getParameter(str);
            if ((parameter == null) ^ (parameter2 == null)) {
                return false;
            }
            if (parameter != null && !parameter.equalsIgnoreCase(parameter2)) {
                return false;
            }
        }
        Iterator parameterNames2 = parameters.getParameterNames();
        while (parameterNames2.hasNext()) {
            String str2 = (String) parameterNames2.next();
            String parameter3 = parameters.getParameter(str2);
            String parameter4 = getParameter(str2);
            if ((parameter3 == null) ^ (parameter4 == null)) {
                return false;
            }
            if (parameter3 != null && !parameter3.equalsIgnoreCase(parameter4)) {
                return false;
            }
        }
        return true;
    }

    public final NameValue getNameValue() {
        return this.parameters.getNameValue("icid-value");
    }

    @Override // javax.sip.header.Parameters
    public String getParameter(String str) {
        Object value = this.parameters.getValue(str);
        if (value == null) {
            return null;
        }
        return value instanceof GenericObject ? ((GenericObject) value).encode() : value.toString();
    }

    @Override // javax.sip.header.Parameters
    public Iterator getParameterNames() {
        return this.parameters.getNames();
    }

    public final void removeParameters() {
        this.parameters = new NameValueList();
    }

    public final void setMultiParameter(NameValue nameValue) {
        this.duplicates.set(nameValue);
    }

    public void setParameter(String str, String str2) {
        NameValue nameValue = this.parameters.getNameValue(str);
        if (nameValue != null) {
            nameValue.setValueAsObject(str2);
        } else {
            this.parameters.set(new NameValue(str, str2));
        }
    }

    public ParametersHeader(String str) {
        super(str);
        this.parameters = new NameValueList();
        this.duplicates = new DuplicateNameValueList();
    }

    public final void setParameter(NameValue nameValue) {
        this.parameters.set(nameValue);
    }

    public ParametersHeader(String str, boolean z) {
        super(str);
        this.parameters = new NameValueList(z);
        this.duplicates = new DuplicateNameValueList();
    }
}
