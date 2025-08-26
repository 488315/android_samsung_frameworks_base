package android.filterfw.io;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.ProtocolException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class TextGraphReader extends GraphReader {
    private KeyValueMap mBoundReferences;
    private ArrayList<Command> mCommands = new ArrayList<>();
    private Filter mCurrentFilter;
    private FilterGraph mCurrentGraph;
    private FilterFactory mFactory;
    private KeyValueMap mSettings;

    private interface Command {
        void execute(TextGraphReader textGraphReader) throws GraphIOException;
    }

    private class ImportPackageCommand implements Command {
        private String mPackageName;

        public ImportPackageCommand(TextGraphReader textGraphReader, String str) {
            this.mPackageName = str;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            try {
                textGraphReader.mFactory.addPackage(this.mPackageName);
            } catch (IllegalArgumentException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class AddLibraryCommand implements Command {
        private String mLibraryName;

        public AddLibraryCommand(TextGraphReader textGraphReader, String str) {
            this.mLibraryName = str;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(TextGraphReader textGraphReader) {
            FilterFactory unused = textGraphReader.mFactory;
            FilterFactory.addFilterLibrary(this.mLibraryName);
        }
    }

    private class AllocateFilterCommand implements Command {
        private String mClassName;
        private String mFilterName;

        public AllocateFilterCommand(TextGraphReader textGraphReader, String str, String str2) {
            this.mClassName = str;
            this.mFilterName = str2;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            try {
                textGraphReader.mCurrentFilter = textGraphReader.mFactory.createFilterByClassName(this.mClassName, this.mFilterName);
            } catch (IllegalArgumentException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class InitFilterCommand implements Command {
        private KeyValueMap mParams;

        public InitFilterCommand(KeyValueMap keyValueMap) {
            this.mParams = keyValueMap;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(TextGraphReader textGraphReader) throws GraphIOException {
            try {
                textGraphReader.mCurrentFilter.initWithValueMap(this.mParams);
                textGraphReader.mCurrentGraph.addFilter(TextGraphReader.this.mCurrentFilter);
            } catch (ProtocolException e) {
                throw new GraphIOException(e.getMessage());
            }
        }
    }

    private class ConnectCommand implements Command {
        private String mSourceFilter;
        private String mSourcePort;
        private String mTargetFilter;
        private String mTargetName;

        public ConnectCommand(TextGraphReader textGraphReader, String str, String str2, String str3, String str4) {
            this.mSourceFilter = str;
            this.mSourcePort = str2;
            this.mTargetFilter = str3;
            this.mTargetName = str4;
        }

        @Override // android.filterfw.io.TextGraphReader.Command
        public void execute(TextGraphReader textGraphReader) {
            textGraphReader.mCurrentGraph.connect(this.mSourceFilter, this.mSourcePort, this.mTargetFilter, this.mTargetName);
        }
    }

    @Override // android.filterfw.io.GraphReader
    public FilterGraph readGraphString(String str) throws GraphIOException {
        FilterGraph filterGraph = new FilterGraph();
        reset();
        this.mCurrentGraph = filterGraph;
        parseString(str);
        applySettings();
        executeCommands();
        reset();
        return filterGraph;
    }

    private void reset() {
        this.mCurrentGraph = null;
        this.mCurrentFilter = null;
        this.mCommands.clear();
        this.mBoundReferences = new KeyValueMap();
        this.mSettings = new KeyValueMap();
        this.mFactory = new FilterFactory();
    }

    private void parseString(String str) throws GraphIOException {
        String strEat;
        String str2;
        String str3;
        Pattern pattern;
        PatternScanner patternScanner;
        String strSubstring;
        Pattern pattern2;
        String str4;
        Pattern pattern3;
        Pattern pattern4;
        String strEat2;
        Pattern pattern5;
        String str5;
        String str6;
        Pattern pattern6;
        String str7;
        Pattern patternCompile = Pattern.compile("@[a-zA-Z]+");
        Pattern patternCompile2 = Pattern.compile("\\}");
        Pattern patternCompile3 = Pattern.compile("\\{");
        Pattern patternCompile4 = Pattern.compile("(\\s+|//[^\\n]*\\n)+");
        Pattern patternCompile5 = Pattern.compile("[a-zA-Z\\.]+");
        Pattern patternCompile6 = Pattern.compile("[a-zA-Z\\./:]+");
        Pattern patternCompile7 = Pattern.compile("\\[[a-zA-Z0-9\\-_]+\\]");
        Pattern patternCompile8 = Pattern.compile("=>");
        String str8 = NavigationBarInflaterView.GRAVITY_SEPARATOR;
        Pattern patternCompile9 = Pattern.compile(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        Pattern patternCompile10 = Pattern.compile("[a-zA-Z0-9\\-_]+");
        PatternScanner patternScanner2 = new PatternScanner(str, patternCompile4);
        String str9 = null;
        String strEat3 = null;
        String str10 = null;
        String str11 = null;
        char c = 0;
        while (true) {
            Pattern pattern7 = patternCompile;
            if (patternScanner2.atEnd()) {
                if (c != 16 && c != 0) {
                    throw new GraphIOException("Unexpected end of input!");
                }
                return;
            }
            switch (c) {
                case 0:
                    Pattern pattern8 = patternCompile5;
                    strEat = str9;
                    str2 = strEat3;
                    str3 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = pattern8;
                    str4 = str8;
                    pattern3 = patternCompile10;
                    pattern4 = pattern7;
                    String strEat4 = patternScanner.eat(pattern4, "<command>");
                    if (strEat4.equals("@import")) {
                        strEat2 = str3;
                        c = 1;
                        strEat3 = str2;
                        str9 = strEat;
                        patternCompile5 = pattern2;
                        str10 = strSubstring;
                        patternScanner2 = patternScanner;
                        patternCompile9 = pattern;
                        str11 = strEat2;
                        patternCompile = pattern4;
                        patternCompile10 = pattern3;
                        str8 = str4;
                    } else {
                        if (strEat4.equals("@library")) {
                            c = 2;
                        } else if (strEat4.equals("@filter")) {
                            c = 3;
                        } else if (strEat4.equals("@connect")) {
                            c = '\b';
                        } else if (strEat4.equals("@set")) {
                            c = '\r';
                        } else if (strEat4.equals("@external")) {
                            c = 14;
                        } else {
                            if (!strEat4.equals("@setting")) {
                                throw new GraphIOException("Unknown command '" + strEat4 + "'!");
                            }
                            c = 15;
                        }
                        strEat2 = str3;
                        strEat3 = str2;
                        str9 = strEat;
                        patternCompile5 = pattern2;
                        str10 = strSubstring;
                        patternScanner2 = patternScanner;
                        patternCompile9 = pattern;
                        str11 = strEat2;
                        patternCompile = pattern4;
                        patternCompile10 = pattern3;
                        str8 = str4;
                    }
                case 1:
                    Pattern pattern9 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    String str12 = strEat3;
                    String str13 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = pattern9;
                    str2 = str12;
                    this.mCommands.add(new ImportPackageCommand(this, patternScanner.eat(pattern2, "<package-name>")));
                    strEat2 = str13;
                    pattern4 = pattern7;
                    c = 16;
                    strEat3 = str2;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 2:
                    pattern5 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str6 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    this.mCommands.add(new AddLibraryCommand(this, patternScanner.eat(patternCompile6, "<library-name>")));
                    strEat2 = str6;
                    pattern2 = pattern5;
                    c = 16;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 3:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    String str14 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    strEat = patternScanner.eat(pattern3, "<class-name>");
                    strEat2 = str14;
                    c = 4;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 4:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str7 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    this.mCommands.add(new AllocateFilterCommand(this, strEat, patternScanner.eat(pattern3, "<filter-name>")));
                    c = 5;
                    strEat2 = str7;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 5:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str7 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    patternScanner.eat(patternCompile3, "{");
                    c = 6;
                    strEat2 = str7;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 6:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str7 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    this.mCommands.add(new InitFilterCommand(readKeyValueAssignments(patternScanner, patternCompile2)));
                    c = 7;
                    strEat2 = str7;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 7:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    String str15 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    patternScanner.eat(patternCompile2, "}");
                    strEat2 = str15;
                    c = 0;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case '\b':
                    Pattern pattern10 = patternCompile5;
                    str4 = str8;
                    String str16 = str11;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    strEat2 = str16;
                    pattern2 = pattern10;
                    strEat3 = patternScanner.eat(pattern3, "<source-filter-name>");
                    c = '\t';
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case '\t':
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str7 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    String strEat5 = patternScanner.eat(patternCompile7, "[<source-port-name>]");
                    strSubstring = strEat5.substring(1, strEat5.length() - 1);
                    c = '\n';
                    strEat2 = str7;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case '\n':
                    pattern6 = patternCompile5;
                    str4 = str8;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    str5 = strEat3;
                    str7 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    patternScanner.eat(patternCompile8, "=>");
                    c = 11;
                    strEat2 = str7;
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 11:
                    pattern6 = patternCompile5;
                    str4 = str8;
                    pattern = patternCompile9;
                    strEat = str9;
                    pattern3 = patternCompile10;
                    patternScanner = patternScanner2;
                    str5 = strEat3;
                    strSubstring = str10;
                    strEat2 = patternScanner.eat(pattern3, "<target-filter-name>");
                    c = '\f';
                    pattern2 = pattern6;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case '\f':
                    String strEat6 = patternScanner2.eat(patternCompile7, "[<target-port-name>]");
                    Pattern pattern11 = patternCompile10;
                    PatternScanner patternScanner3 = patternScanner2;
                    strSubstring = str10;
                    Pattern pattern12 = patternCompile9;
                    patternScanner = patternScanner3;
                    str5 = strEat3;
                    str6 = str11;
                    pattern = pattern12;
                    str4 = str8;
                    pattern3 = pattern11;
                    pattern5 = patternCompile5;
                    strEat = str9;
                    this.mCommands.add(new ConnectCommand(this, str5, strSubstring, str6, strEat6.substring(1, strEat6.length() - 1)));
                    strEat2 = str6;
                    pattern2 = pattern5;
                    c = 16;
                    strEat3 = str5;
                    pattern4 = pattern7;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case '\r':
                    this.mBoundReferences.putAll(readKeyValueAssignments(patternScanner2, patternCompile9));
                    str4 = str8;
                    strEat2 = str11;
                    c = 16;
                    pattern3 = patternCompile10;
                    pattern = patternCompile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = patternCompile5;
                    strEat = str9;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 14:
                    bindExternal(patternScanner2.eat(patternCompile10, "<external-identifier>"));
                    str4 = str8;
                    strEat2 = str11;
                    c = 16;
                    pattern3 = patternCompile10;
                    pattern = patternCompile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = patternCompile5;
                    strEat = str9;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 15:
                    this.mSettings.putAll(readKeyValueAssignments(patternScanner2, patternCompile9));
                    str4 = str8;
                    strEat2 = str11;
                    c = 16;
                    pattern3 = patternCompile10;
                    pattern = patternCompile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = patternCompile5;
                    strEat = str9;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                case 16:
                    patternScanner2.eat(patternCompile9, str8);
                    str4 = str8;
                    c = 0;
                    strEat2 = str11;
                    pattern3 = patternCompile10;
                    pattern = patternCompile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = patternCompile5;
                    strEat = str9;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
                default:
                    Pattern pattern13 = patternCompile5;
                    strEat = str9;
                    str2 = strEat3;
                    str3 = str11;
                    pattern = patternCompile9;
                    patternScanner = patternScanner2;
                    strSubstring = str10;
                    pattern2 = pattern13;
                    str4 = str8;
                    pattern3 = patternCompile10;
                    pattern4 = pattern7;
                    strEat2 = str3;
                    strEat3 = str2;
                    str9 = strEat;
                    patternCompile5 = pattern2;
                    str10 = strSubstring;
                    patternScanner2 = patternScanner;
                    patternCompile9 = pattern;
                    str11 = strEat2;
                    patternCompile = pattern4;
                    patternCompile10 = pattern3;
                    str8 = str4;
            }
        }
    }

    @Override // android.filterfw.io.GraphReader
    public KeyValueMap readKeyValueAssignments(String str) throws GraphIOException {
        return readKeyValueAssignments(new PatternScanner(str, Pattern.compile("\\s+")), null);
    }

    private KeyValueMap readKeyValueAssignments(PatternScanner patternScanner, Pattern pattern) throws GraphIOException {
        Pattern patternCompile = Pattern.compile("=");
        Pattern patternCompile2 = Pattern.compile(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        Pattern patternCompile3 = Pattern.compile("[a-zA-Z]+[a-zA-Z0-9]*");
        Pattern patternCompile4 = Pattern.compile("'[^']*'|\\\"[^\\\"]*\\\"");
        Pattern patternCompile5 = Pattern.compile("[0-9]+");
        Pattern patternCompile6 = Pattern.compile("[0-9]*\\.[0-9]+f?");
        Pattern patternCompile7 = Pattern.compile("\\$[a-zA-Z]+[a-zA-Z0-9]");
        Pattern patternCompile8 = Pattern.compile("true|false");
        KeyValueMap keyValueMap = new KeyValueMap();
        char c = 0;
        String strEat = null;
        while (!patternScanner.atEnd() && (pattern == null || !patternScanner.peek(pattern))) {
            char c2 = 1;
            if (c == 0) {
                strEat = patternScanner.eat(patternCompile3, "<identifier>");
            } else if (c == 1) {
                patternScanner.eat(patternCompile, "=");
                c2 = 2;
            } else if (c == 2) {
                String strTryEat = patternScanner.tryEat(patternCompile4);
                if (strTryEat != null) {
                    keyValueMap.put(strEat, strTryEat.substring(1, strTryEat.length() - 1));
                } else {
                    String strTryEat2 = patternScanner.tryEat(patternCompile7);
                    if (strTryEat2 != null) {
                        String strSubstring = strTryEat2.substring(1, strTryEat2.length());
                        KeyValueMap keyValueMap2 = this.mBoundReferences;
                        Object obj = keyValueMap2 != null ? keyValueMap2.get(strSubstring) : null;
                        if (obj == null) {
                            throw new GraphIOException("Unknown object reference to '" + strSubstring + "'!");
                        }
                        keyValueMap.put(strEat, obj);
                    } else {
                        String strTryEat3 = patternScanner.tryEat(patternCompile8);
                        if (strTryEat3 != null) {
                            keyValueMap.put(strEat, Boolean.valueOf(Boolean.parseBoolean(strTryEat3)));
                        } else {
                            String strTryEat4 = patternScanner.tryEat(patternCompile6);
                            if (strTryEat4 != null) {
                                keyValueMap.put(strEat, Float.valueOf(Float.parseFloat(strTryEat4)));
                            } else {
                                String strTryEat5 = patternScanner.tryEat(patternCompile5);
                                if (strTryEat5 != null) {
                                    keyValueMap.put(strEat, Integer.valueOf(Integer.parseInt(strTryEat5)));
                                } else {
                                    throw new GraphIOException(patternScanner.unexpectedTokenMessage("<value>"));
                                }
                            }
                        }
                    }
                }
                c2 = 3;
            } else if (c != 3) {
                c2 = c;
            } else {
                patternScanner.eat(patternCompile2, NavigationBarInflaterView.GRAVITY_SEPARATOR);
                c2 = 0;
            }
            c = c2;
        }
        if (c == 0 || c == 3) {
            return keyValueMap;
        }
        throw new GraphIOException("Unexpected end of assignments on line " + patternScanner.lineNo() + "!");
    }

    private void bindExternal(String str) throws GraphIOException {
        if (this.mReferences.containsKey(str)) {
            this.mBoundReferences.put(str, this.mReferences.get(str));
        } else {
            throw new GraphIOException("Unknown external variable '" + str + "'! You must add a reference to this external in the host program using addReference(...)!");
        }
    }

    private void checkReferences() throws GraphIOException {
        for (String str : this.mReferences.keySet()) {
            if (!this.mBoundReferences.containsKey(str)) {
                throw new GraphIOException("Host program specifies reference to '" + str + "', which is not declared @external in graph file!");
            }
        }
    }

    private void applySettings() throws GraphIOException {
        for (String str : this.mSettings.keySet()) {
            Object obj = this.mSettings.get(str);
            if (str.equals("autoBranch")) {
                expectSettingClass(str, obj, String.class);
                if (obj.equals("synced")) {
                    this.mCurrentGraph.setAutoBranchMode(1);
                } else if (obj.equals("unsynced")) {
                    this.mCurrentGraph.setAutoBranchMode(2);
                } else if (obj.equals("off")) {
                    this.mCurrentGraph.setAutoBranchMode(0);
                } else {
                    throw new GraphIOException("Unknown autobranch setting: " + obj + "!");
                }
            } else if (str.equals("discardUnconnectedOutputs")) {
                expectSettingClass(str, obj, Boolean.class);
                this.mCurrentGraph.setDiscardUnconnectedOutputs(((Boolean) obj).booleanValue());
            } else {
                throw new GraphIOException("Unknown @setting '" + str + "'!");
            }
        }
    }

    private void expectSettingClass(String str, Object obj, Class cls) throws GraphIOException {
        if (obj.getClass() == cls) {
            return;
        }
        throw new GraphIOException("Setting '" + str + "' must have a value of type " + cls.getSimpleName() + ", but found a value of type " + obj.getClass().getSimpleName() + "!");
    }

    private void executeCommands() throws GraphIOException {
        Iterator<Command> it = this.mCommands.iterator();
        while (it.hasNext()) {
            it.next().execute(this);
        }
    }
}
