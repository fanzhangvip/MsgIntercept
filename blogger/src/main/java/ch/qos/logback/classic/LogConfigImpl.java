package ch.qos.logback.classic;


import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.pattern.LogPattern;

/**
 * Created by pengwei on 16/3/4.
 * Log config
 */
class LogConfigImpl implements LogConfig {

    private boolean enable = true;
    private String tagPrefix;
    private boolean showBorder = true;
    @Level.LevelIntType
    private int logLevel = Level.TRACE_INT;
    private List<Parser> parseList;
    private String formatTag;
    private int methodOffset = 0;

    private static LogConfigImpl singleton;

    private LogConfigImpl() {
        parseList = new ArrayList<>();
    }

    static LogConfigImpl getInstance() {
        if (singleton == null) {
            synchronized (LogConfigImpl.class) {
                if (singleton == null) {
                    singleton = new LogConfigImpl();
                }
            }
        }
        return singleton;
    }

    @Override
    public LogConfig configAllowLog(boolean allowLog) {
        this.enable = allowLog;
        return this;
    }

    @Override
    public LogConfig configTagPrefix(String prefix) {
        this.tagPrefix = prefix;
        return this;
    }

    @Override
    public LogConfig configFormatTag(String formatTag) {
        this.formatTag = formatTag;
        return this;
    }

    public String getFormatTag(StackTraceElement caller) {
        if (TextUtils.isEmpty(formatTag)) {
            return null;
        }
        return LogPattern.compile(formatTag).apply(caller);
    }

    @Override
    public LogConfig configShowBorders(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    @Override
    public LogConfig configMethodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    @Override
    public LogConfig configLevel(@Level.LevelIntType int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    @Override
    public LogConfig addParserClass(Class<? extends Parser>... classes) {
        // TODO: 16/3/12 判断解析类的继承关系
        for (Class<? extends Parser> cla : classes) {
            try {
                parseList.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getTagPrefix() {
        if (TextUtils.isEmpty(tagPrefix)) {
            return "LogUtils";
        }

        return tagPrefix;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public List<Parser> getParseList() {
        return parseList;
    }
}
