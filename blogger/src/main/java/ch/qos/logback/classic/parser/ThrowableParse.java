package ch.qos.logback.classic.parser;

import android.util.Log;

import ch.qos.logback.classic.Parser;


/**
 * Created by pengwei on 16/3/8.
 */
public class ThrowableParse implements Parser<Throwable> {
    @Override
    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    @Override
    public String parseString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
