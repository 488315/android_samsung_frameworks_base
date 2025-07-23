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
        String str2;
        String str3;
        String str4;
        Pattern pattern;
        PatternScanner patternScanner;
        String str5;
        Pattern pattern2;
        String str6;
        Pattern pattern3;
        Pattern pattern4;
        String str7;
        Pattern pattern5;
        String str8;
        String str9;
        Pattern pattern6;
        String str10;
        Pattern compile = Pattern.compile("@[a-zA-Z]+");
        Pattern compile2 = Pattern.compile("\\}");
        Pattern compile3 = Pattern.compile("\\{");
        Pattern compile4 = Pattern.compile("(\\s+|//[^\\n]*\\n)+");
        Pattern compile5 = Pattern.compile("[a-zA-Z\\.]+");
        Pattern compile6 = Pattern.compile("[a-zA-Z\\./:]+");
        Pattern compile7 = Pattern.compile("\\[[a-zA-Z0-9\\-_]+\\]");
        Pattern compile8 = Pattern.compile("=>");
        String str11 = NavigationBarInflaterView.GRAVITY_SEPARATOR;
        Pattern compile9 = Pattern.compile(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        Pattern compile10 = Pattern.compile("[a-zA-Z0-9\\-_]+");
        PatternScanner patternScanner2 = new PatternScanner(str, compile4);
        String str12 = null;
        String str13 = null;
        String str14 = null;
        String str15 = null;
        char c = 0;
        while (true) {
            Pattern pattern7 = compile;
            if (patternScanner2.atEnd()) {
                if (c != 16 && c != 0) {
                    throw new GraphIOException("Unexpected end of input!");
                }
                return;
            }
            switch (c) {
                case 0:
                    Pattern pattern8 = compile5;
                    str2 = str12;
                    str3 = str13;
                    str4 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = pattern8;
                    str6 = str11;
                    pattern3 = compile10;
                    pattern4 = pattern7;
                    String eat = patternScanner.eat(pattern4, "<command>");
                    if (eat.equals("@import")) {
                        str7 = str4;
                        c = 1;
                        str13 = str3;
                        str12 = str2;
                        compile5 = pattern2;
                        str14 = str5;
                        patternScanner2 = patternScanner;
                        compile9 = pattern;
                        str15 = str7;
                        compile = pattern4;
                        compile10 = pattern3;
                        str11 = str6;
                    } else {
                        if (eat.equals("@library")) {
                            c = 2;
                        } else if (eat.equals("@filter")) {
                            c = 3;
                        } else if (eat.equals("@connect")) {
                            c = '\b';
                        } else if (eat.equals("@set")) {
                            c = '\r';
                        } else if (eat.equals("@external")) {
                            c = 14;
                        } else {
                            if (!eat.equals("@setting")) {
                                throw new GraphIOException("Unknown command '" + eat + "'!");
                            }
                            c = 15;
                        }
                        str7 = str4;
                        str13 = str3;
                        str12 = str2;
                        compile5 = pattern2;
                        str14 = str5;
                        patternScanner2 = patternScanner;
                        compile9 = pattern;
                        str15 = str7;
                        compile = pattern4;
                        compile10 = pattern3;
                        str11 = str6;
                    }
                case 1:
                    Pattern pattern9 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    String str16 = str13;
                    String str17 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = pattern9;
                    str3 = str16;
                    this.mCommands.add(new ImportPackageCommand(this, patternScanner.eat(pattern2, "<package-name>")));
                    str7 = str17;
                    pattern4 = pattern7;
                    c = 16;
                    str13 = str3;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 2:
                    pattern5 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str9 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    this.mCommands.add(new AddLibraryCommand(this, patternScanner.eat(compile6, "<library-name>")));
                    str7 = str9;
                    pattern2 = pattern5;
                    c = 16;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 3:
                    pattern6 = compile5;
                    str6 = str11;
                    pattern3 = compile10;
                    str8 = str13;
                    String str18 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    str2 = patternScanner.eat(pattern3, "<class-name>");
                    str7 = str18;
                    c = 4;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 4:
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str10 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    this.mCommands.add(new AllocateFilterCommand(this, str2, patternScanner.eat(pattern3, "<filter-name>")));
                    c = 5;
                    str7 = str10;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 5:
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str10 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    patternScanner.eat(compile3, "{");
                    c = 6;
                    str7 = str10;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 6:
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str10 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    this.mCommands.add(new InitFilterCommand(readKeyValueAssignments(patternScanner, compile2)));
                    c = 7;
                    str7 = str10;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 7:
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    String str19 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    patternScanner.eat(compile2, "}");
                    str7 = str19;
                    c = 0;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case '\b':
                    Pattern pattern10 = compile5;
                    str6 = str11;
                    String str20 = str15;
                    str2 = str12;
                    pattern3 = compile10;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    str7 = str20;
                    pattern2 = pattern10;
                    str13 = patternScanner.eat(pattern3, "<source-filter-name>");
                    c = '\t';
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case '\t':
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str10 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    String eat2 = patternScanner.eat(compile7, "[<source-port-name>]");
                    str5 = eat2.substring(1, eat2.length() - 1);
                    c = '\n';
                    str7 = str10;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case '\n':
                    pattern6 = compile5;
                    str6 = str11;
                    str2 = str12;
                    pattern3 = compile10;
                    str8 = str13;
                    str10 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    patternScanner.eat(compile8, "=>");
                    c = 11;
                    str7 = str10;
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 11:
                    pattern6 = compile5;
                    str6 = str11;
                    pattern = compile9;
                    str2 = str12;
                    pattern3 = compile10;
                    patternScanner = patternScanner2;
                    str8 = str13;
                    str5 = str14;
                    str7 = patternScanner.eat(pattern3, "<target-filter-name>");
                    c = '\f';
                    pattern2 = pattern6;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case '\f':
                    String eat3 = patternScanner2.eat(compile7, "[<target-port-name>]");
                    Pattern pattern11 = compile10;
                    PatternScanner patternScanner3 = patternScanner2;
                    str5 = str14;
                    Pattern pattern12 = compile9;
                    patternScanner = patternScanner3;
                    str8 = str13;
                    str9 = str15;
                    pattern = pattern12;
                    str6 = str11;
                    pattern3 = pattern11;
                    pattern5 = compile5;
                    str2 = str12;
                    this.mCommands.add(new ConnectCommand(this, str8, str5, str9, eat3.substring(1, eat3.length() - 1)));
                    str7 = str9;
                    pattern2 = pattern5;
                    c = 16;
                    str13 = str8;
                    pattern4 = pattern7;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case '\r':
                    this.mBoundReferences.putAll(readKeyValueAssignments(patternScanner2, compile9));
                    str6 = str11;
                    str7 = str15;
                    c = 16;
                    pattern3 = compile10;
                    pattern = compile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = compile5;
                    str2 = str12;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 14:
                    bindExternal(patternScanner2.eat(compile10, "<external-identifier>"));
                    str6 = str11;
                    str7 = str15;
                    c = 16;
                    pattern3 = compile10;
                    pattern = compile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = compile5;
                    str2 = str12;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 15:
                    this.mSettings.putAll(readKeyValueAssignments(patternScanner2, compile9));
                    str6 = str11;
                    str7 = str15;
                    c = 16;
                    pattern3 = compile10;
                    pattern = compile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = compile5;
                    str2 = str12;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                case 16:
                    patternScanner2.eat(compile9, str11);
                    str6 = str11;
                    c = 0;
                    str7 = str15;
                    pattern3 = compile10;
                    pattern = compile9;
                    pattern4 = pattern7;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = compile5;
                    str2 = str12;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
                default:
                    Pattern pattern13 = compile5;
                    str2 = str12;
                    str3 = str13;
                    str4 = str15;
                    pattern = compile9;
                    patternScanner = patternScanner2;
                    str5 = str14;
                    pattern2 = pattern13;
                    str6 = str11;
                    pattern3 = compile10;
                    pattern4 = pattern7;
                    str7 = str4;
                    str13 = str3;
                    str12 = str2;
                    compile5 = pattern2;
                    str14 = str5;
                    patternScanner2 = patternScanner;
                    compile9 = pattern;
                    str15 = str7;
                    compile = pattern4;
                    compile10 = pattern3;
                    str11 = str6;
            }
        }
    }

    @Override // android.filterfw.io.GraphReader
    public KeyValueMap readKeyValueAssignments(String str) throws GraphIOException {
        return readKeyValueAssignments(new PatternScanner(str, Pattern.compile("\\s+")), null);
    }

    private KeyValueMap readKeyValueAssignments(PatternScanner patternScanner, Pattern pattern) throws GraphIOException {
        Pattern compile = Pattern.compile("=");
        Pattern compile2 = Pattern.compile(NavigationBarInflaterView.GRAVITY_SEPARATOR);
        Pattern compile3 = Pattern.compile("[a-zA-Z]+[a-zA-Z0-9]*");
        Pattern compile4 = Pattern.compile("'[^']*'|\\\"[^\\\"]*\\\"");
        Pattern compile5 = Pattern.compile("[0-9]+");
        Pattern compile6 = Pattern.compile("[0-9]*\\.[0-9]+f?");
        Pattern compile7 = Pattern.compile("\\$[a-zA-Z]+[a-zA-Z0-9]");
        Pattern compile8 = Pattern.compile("true|false");
        KeyValueMap keyValueMap = new KeyValueMap();
        char c = 0;
        String str = null;
        while (!patternScanner.atEnd() && (pattern == null || !patternScanner.peek(pattern))) {
            char c2 = 1;
            if (c == 0) {
                str = patternScanner.eat(compile3, "<identifier>");
            } else if (c == 1) {
                patternScanner.eat(compile, "=");
                c2 = 2;
            } else if (c == 2) {
                String tryEat = patternScanner.tryEat(compile4);
                if (tryEat != null) {
                    keyValueMap.put(str, tryEat.substring(1, tryEat.length() - 1));
                } else {
                    String tryEat2 = patternScanner.tryEat(compile7);
                    if (tryEat2 != null) {
                        String substring = tryEat2.substring(1, tryEat2.length());
                        KeyValueMap keyValueMap2 = this.mBoundReferences;
                        Object obj = keyValueMap2 != null ? keyValueMap2.get(substring) : null;
                        if (obj == null) {
                            throw new GraphIOException("Unknown object reference to '" + substring + "'!");
                        }
                        keyValueMap.put(str, obj);
                    } else {
                        String tryEat3 = patternScanner.tryEat(compile8);
                        if (tryEat3 != null) {
                            keyValueMap.put(str, Boolean.valueOf(Boolean.parseBoolean(tryEat3)));
                        } else {
                            String tryEat4 = patternScanner.tryEat(compile6);
                            if (tryEat4 != null) {
                                keyValueMap.put(str, Float.valueOf(Float.parseFloat(tryEat4)));
                            } else {
                                String tryEat5 = patternScanner.tryEat(compile5);
                                if (tryEat5 != null) {
                                    keyValueMap.put(str, Integer.valueOf(Integer.parseInt(tryEat5)));
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
                patternScanner.eat(compile2, NavigationBarInflaterView.GRAVITY_SEPARATOR);
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
