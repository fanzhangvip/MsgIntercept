package ch.qos.logback.classic;


/**
 * Created by pengwei on 16/3/4.
 */
public interface LogConfig {

    LogConfig configAllowLog(boolean allowLog);

    LogConfig configTagPrefix(String prefix);

    LogConfig configFormatTag(String formatTag);

    LogConfig configShowBorders(boolean showBorder);

    LogConfig configMethodOffset(int offset);

    LogConfig configLevel(@Level.LevelIntType int logLevel);

    LogConfig addParserClass(Class<? extends Parser>... classes);
}
